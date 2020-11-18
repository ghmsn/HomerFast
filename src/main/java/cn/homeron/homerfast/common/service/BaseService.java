package cn.homeron.homerfast.common.service;

import cn.homeron.homerfast.common.enmu.CriteriaOperator;
import cn.homeron.homerfast.common.repository.BaseRepository;
import cn.homeron.homerfast.common.utils.GenericsUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service基类
 *
 * @author Homer_Gu
 * @version 1.0.0
 * @date 2018-09-01
 */
@Service
@EnableTransactionManagement
public abstract class BaseService<R extends BaseRepository<T, ID>, T, ID> {

	@PersistenceContext
	protected EntityManager em;

	@Autowired
	protected R repository;
	protected Class<T> clz;

	public Iterable<T> getAll() {
		return repository.findAll();
	}

	public Optional<T> getById(ID id) {
		return repository.findById(id);
	}

	@Transactional
	public void delete(ID id) {
		repository.deleteById(id);
	}

	@Transactional
	public <S extends T> S save(S bean) {
		return repository.save(bean);
	}

	@Transactional
	public <S extends T> Iterable<S> saveAll(Iterable<S> beans){
		return repository.saveAll(beans);
	}

	public long getAllCount() {
		return repository.count();
	}


	/**
	 * 复杂查询Count
	 * @param jsonParam
	 * @return
	 * 示例
	{
	"criteria": [{
	"attribute": "phoneNum",
	"operator": "LIKE",
	"value": "139"
	},
	{
	"attribute": "nickname",
	"operator": "=",
	"value": "mike"
	},
	{
	"attribute": "nickname",
	"operator": "NOTIN",
	"values": [
	"carol",
	"mick"
	]
	},
	{
	"attribute": "age",
	"operator": "<=",
	"value": "22"
	},
	{
	"attribute": "nickname",
	"operator": "ISNOTNULL"
	}
	]
	}
	 */
	public long getAllCountBySpec(JSONObject jsonParam) {
		//反射找到泛型Bean的类型
		clz = GenericsUtils.getSuperClassGenricType(getClass(),1);
		return repository.count(new MySpec(jsonParam));
	}

	/**
	 * 复杂查询Page
	 * @param jsonParam
	 * @return
	 * 示例 注意：sort与sorts只有一个生效（优先sort），如果使用sorts，不要传递sort
	{
	"page": 0,
	"size": "10",
	"criteria": [{
	"attribute": "phoneNum",
	"operator": "LIKE",
	"value": "139"
	},
	{
	"attribute": "nickname",
	"operator": "=",
	"value": "mike"
	},
	{
	"attribute": "nickname",
	"operator": "NOTIN",
	"values": [
	"carol",
	"mick"
	]
	},
	{
	"attribute": "age",
	"operator": "<=",
	"value": "22"
	},
	{
	"attribute": "nickname",
	"operator": "ISNOTNULL"
	}
	],
	"sort": {
	"direction": "DESC",
	"properties": ["phoneNum", "nickname"]
	},
	"sorts": [{
	"direction": "DESC",
	"properties": "phoneNum"
	},
	{
	"direction": "ASC",
	"properties": "nickname"
	}
	]
	}
	 */
	public Page<T> getAllBySpec(JSONObject jsonParam) {
		//反射找到泛型Bean的类型
		clz = GenericsUtils.getSuperClassGenricType(getClass(),1);
		PageRequest pageReq = buildPageRequest(jsonParam);
		return repository.findAll(new MySpec(jsonParam), pageReq);
	}

	/**
	 * 复杂查询List
	 * @param jsonParam
	 * @return
	 * 示例 注意：sort与sorts只有一个生效（优先sort），如果使用sorts，不要传递sort
	{
	"criteria": [{
	"attribute": "phoneNum",
	"operator": "LIKE",
	"value": "139"
	},
	{
	"attribute": "nickname",
	"operator": "=",
	"value": "mike"
	},
	{
	"attribute": "nickname",
	"operator": "NOTIN",
	"values": [
	"carol",
	"mick"
	]
	},
	{
	"attribute": "age",
	"operator": "<=",
	"value": "22"
	},
	{
	"attribute": "nickname",
	"operator": "ISNOTNULL"
	}
	],
	"sort": {
	"direction": "DESC",
	"properties": ["phoneNum", "nickname"]
	},
	"sorts": [{
	"direction": "DESC",
	"properties": "phoneNum"
	},
	{
	"direction": "ASC",
	"properties": "nickname"
	}
	]
	}
	 */
	public List<T> getAllListBySpec(JSONObject jsonParam) {
		//反射找到泛型Bean的类型
		clz = GenericsUtils.getSuperClassGenricType(getClass(),1);
		JSONObject sortJSON = jsonParam.getJSONObject("sort");
		JSONArray sortsArray = jsonParam.getJSONArray("sorts");
		if (null != sortJSON) {
			String direction = sortJSON.getString("direction");
			if(StringUtils.isBlank(direction)) {
				direction = "ASC";
			}
			JSONArray properties = sortJSON.getJSONArray("properties");
			Sort sort = Sort.by(parseDirection(direction), getSortProperties(properties));
			return repository.findAll(new MySpec(jsonParam), sort);
		}else if(null != sortsArray && !sortsArray.isEmpty()){
			List<Sort.Order> sortsList = getOrders(sortsArray);
			if(!sortsList.isEmpty()){
				return repository.findAll(new MySpec(jsonParam), Sort.by(sortsList));
			}
		}
		return repository.findAll(new MySpec(jsonParam));
	}

	/**
	 * 建立查询条件
	 */
	protected class MySpec implements Specification<T> {

		private static final long serialVersionUID = 1L;

		private JSONObject criteriaJSON;

		MySpec(JSONObject jsonParam) {
			this.criteriaJSON = jsonParam;
		}

		@Override
		public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

			Predicate predicate = cb.conjunction();
			JSONArray criteria = criteriaJSON.getJSONArray("criteria");
			for (int i = 0; i < criteria.size(); i++) {
				JSONObject jsonObj = criteria.getJSONObject(i);
				String attribute = jsonObj.getString("attribute");
				String operator = jsonObj.getString("operator");
				if (StringUtils.isNotBlank(attribute) && StringUtils.isNotBlank(operator)) {
					Field attributeField;
					try{
						attributeField = clz.getDeclaredField(attribute);
						attributeField.setAccessible(true);
					}catch (NoSuchFieldException ex){
						try {
							Class<?> superClz = clz.getSuperclass();
							attributeField = superClz.getDeclaredField(attribute);
							attributeField.setAccessible(true);
						}catch (NoSuchFieldException e){
							throw new RuntimeException("attribute (" + attribute + ") not found in JavaBean!");
						}
					}
					JSONArray values;
					String strValue;
					List<Predicate> list;
					Predicate[] p;
					CriteriaOperator criteriaOperator = CriteriaOperator.getEnumByOperator(operator);
					if (null != criteriaOperator) {
						switch (criteriaOperator) {
							case LIKE:
								predicate.getExpressions()
										.add(cb.like(root.get(attribute), "%" + jsonObj.getString("value") + "%"));
								break;
							case NOT_LIKE:
								predicate.getExpressions().add(
										cb.notLike(root.get(attribute), "%" + jsonObj.getString("value") + "%"));
								break;
							case EQUAL:
								predicate.getExpressions()
										.add(cb.equal(root.get(attribute), jsonObj.get("value")));
								break;
							case NOT_EQUAL:
							case NOT_EQUAL2:
								predicate.getExpressions()
										.add(cb.notEqual(root.get(attribute), jsonObj.get("value")));
								break;
							case IN:
								list = getInNotInPredicates(root, cb, jsonObj, attribute);
								p = new Predicate[list.size()];
								predicate.getExpressions().add(cb.and(list.toArray(p)));
								break;
							case NOT_IN:
								list = getInNotInPredicates(root, cb, jsonObj, attribute);
								p = new Predicate[list.size()];
								predicate.getExpressions().add(cb.and(list.toArray(p)).not());
								break;
							case BETWEEN:
								values = jsonObj.getJSONArray("values");
								if (values != null && values.size() == 2) {
									if(attributeField.getGenericType().toString().equals("class java.util.Date")){
										predicate.getExpressions().add(cb.between(root.get(attribute), values.getDate(0), values.getDate(1)));
									}else{
										predicate.getExpressions().add(cb.between(root.get(attribute), values.getString(0), values.getString(1)));
									}
								}else{
									throw new RuntimeException("label values must have two values!");
								}
								break;
							case NOT_BETWEEN:
								values = jsonObj.getJSONArray("values");
								if (values != null && values.size() == 2) {
									if(attributeField.getGenericType().toString().equals("class java.util.Date")){
										predicate.getExpressions().add(cb.between(root.get(attribute), values.getDate(0), values.getDate(1)).not());
									}else{
										predicate.getExpressions().add(cb.between(root.get(attribute), values.getString(0), values.getString(1)).not());
									}
								}else{
									throw new RuntimeException("label values must have two values!");
								}
								break;
							case GREATER_THAN:
								strValue = jsonObj.getString("value");
								if (StringUtils.isNotBlank(strValue)) {
									if(attributeField.getGenericType().toString().equals("class java.util.Date")){
										predicate.getExpressions().add(cb.greaterThan(root.get(attribute), jsonObj.getDate("value")));
									}else if(attributeField.getGenericType().toString().equals("class java.lang.Integer")){
										predicate.getExpressions().add(cb.greaterThan(root.get(attribute), jsonObj.getInteger("value")));
									}else if(attributeField.getGenericType().toString().equals("class java.lang.Double")){
										predicate.getExpressions().add(cb.greaterThan(root.get(attribute), jsonObj.getDouble("value")));
									}else if(attributeField.getGenericType().toString().equals("class java.lang.Short")){
										predicate.getExpressions().add(cb.greaterThan(root.get(attribute), jsonObj.getShort("value")));
									}else if(attributeField.getGenericType().toString().equals("class java.lang.Long")){
										predicate.getExpressions().add(cb.greaterThan(root.get(attribute), jsonObj.getLong("value")));
									}else if(attributeField.getGenericType().toString().equals("class java.lang.Float")){
										predicate.getExpressions().add(cb.greaterThan(root.get(attribute), jsonObj.getFloat("value")));
									}else{
										predicate.getExpressions().add(cb.greaterThan(root.get(attribute), strValue));
									}
								}else{
									throw new RuntimeException("label value must not null!");
								}
								break;
							case GREATER_THAN_OR_EQUAL_TO:
								strValue = jsonObj.getString("value");
								if (StringUtils.isNotBlank(strValue)) {
									if(attributeField.getGenericType().toString().equals("class java.util.Date")){
										predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get(attribute), jsonObj.getDate("value")));
									}else if(attributeField.getGenericType().toString().equals("class java.lang.Integer")){
										predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get(attribute), jsonObj.getInteger("value")));
									}else if(attributeField.getGenericType().toString().equals("class java.lang.Double")){
										predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get(attribute), jsonObj.getDouble("value")));
									}else if(attributeField.getGenericType().toString().equals("class java.lang.Short")){
										predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get(attribute), jsonObj.getShort("value")));
									}else if(attributeField.getGenericType().toString().equals("class java.lang.Long")){
										predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get(attribute), jsonObj.getLong("value")));
									}else if(attributeField.getGenericType().toString().equals("class java.lang.Float")){
										predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get(attribute), jsonObj.getFloat("value")));
									}else{
										predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get(attribute), strValue));
									}
								}else{
									throw new RuntimeException("label value must not null!");
								}
								break;
							case LESS_THAN:
								strValue = jsonObj.getString("value");
								if (StringUtils.isNotBlank(strValue)) {
									if(attributeField.getGenericType().toString().equals("class java.util.Date")){
										predicate.getExpressions().add(cb.lessThan(root.get(attribute), jsonObj.getDate("value")));
									}else if(attributeField.getGenericType().toString().equals("class java.lang.Integer")){
										predicate.getExpressions().add(cb.lessThan(root.get(attribute), jsonObj.getInteger("value")));
									}else if(attributeField.getGenericType().toString().equals("class java.lang.Double")){
										predicate.getExpressions().add(cb.lessThan(root.get(attribute), jsonObj.getDouble("value")));
									}else if(attributeField.getGenericType().toString().equals("class java.lang.Short")){
										predicate.getExpressions().add(cb.lessThan(root.get(attribute), jsonObj.getShort("value")));
									}else if(attributeField.getGenericType().toString().equals("class java.lang.Long")){
										predicate.getExpressions().add(cb.lessThan(root.get(attribute), jsonObj.getLong("value")));
									}else if(attributeField.getGenericType().toString().equals("class java.lang.Float")){
										predicate.getExpressions().add(cb.lessThan(root.get(attribute), jsonObj.getFloat("value")));
									}else{
										predicate.getExpressions().add(cb.lessThan(root.get(attribute), strValue));
									}
								}else{
									throw new RuntimeException("label value must not null!");
								}
								break;
							case LESS_THAN_OR_EQUAL_TO:
								strValue = jsonObj.getString("value");
								if (StringUtils.isNotBlank(strValue)) {
									if(attributeField.getGenericType().toString().equals("class java.util.Date")){
										predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get(attribute), jsonObj.getDate("value")));
									}else if(attributeField.getGenericType().toString().equals("class java.lang.Integer")){
										predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get(attribute), jsonObj.getInteger("value")));
									}else if(attributeField.getGenericType().toString().equals("class java.lang.Double")){
										predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get(attribute), jsonObj.getDouble("value")));
									}else if(attributeField.getGenericType().toString().equals("class java.lang.Short")){
										predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get(attribute), jsonObj.getShort("value")));
									}else if(attributeField.getGenericType().toString().equals("class java.lang.Long")){
										predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get(attribute), jsonObj.getLong("value")));
									}else if(attributeField.getGenericType().toString().equals("class java.lang.Float")){
										predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get(attribute), jsonObj.getFloat("value")));
									}else{
										predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get(attribute), strValue));
									}
								}else{
									throw new RuntimeException("label value must not null!");
								}
								break;
							case IS_NULL:
								predicate.getExpressions().add(cb.isNull(root.get(attribute)));
								break;
							case IS_NOT_NULL:
								predicate.getExpressions().add(cb.isNotNull(root.get(attribute)));
								break;

							default:
								throw new RuntimeException("criteria operator not support!");
						}
					} else {
						throw new RuntimeException("criteria operator error!");
					}
				}
			}
			return predicate;
		}

		private List<Predicate> getInNotInPredicates(Root<T> root, CriteriaBuilder cb, JSONObject jsonObj, String attribute) {
			JSONArray values = jsonObj.getJSONArray("values");
			List<Predicate> list = new ArrayList<>();
			if (values != null && values.size() > 0) {
				In<Object> in = cb.in(root.get(attribute));
				for (Object value : values) {
					in.value(value);
				}
				list.add(in);
			}
			return list;
		}
	}

	/**
	 * 建立分页排序请求
	 *
	 * @param jsonParam
	 * @return
	 *
	 */
	protected PageRequest buildPageRequest(JSONObject jsonParam) {

		Integer page = jsonParam.getInteger("page");
		Integer size = jsonParam.getInteger("size");
		if(null == page || null == size){
			throw new RuntimeException("page and size must not null!");
		}
		JSONObject sortJSON = jsonParam.getJSONObject("sort");
		JSONArray sortsArray = jsonParam.getJSONArray("sorts");
		if (null != sortJSON) {
			String direction = sortJSON.getString("direction");
			JSONArray properties = sortJSON.getJSONArray("properties");
			Sort sort = Sort.by(parseDirection(direction), getSortProperties(properties));
			return PageRequest.of(page, size, sort);
		}else if(null != sortsArray && !sortsArray.isEmpty()){
			List<Sort.Order> sortsList = getOrders(sortsArray);
			if(!sortsList.isEmpty()){
				return PageRequest.of(page, size, Sort.by(sortsList));
			}
		}
		return PageRequest.of(page, size);
	}

	private Direction parseDirection(String direction) {
		if(StringUtils.isBlank(direction)){
			return Direction.ASC;
		}

		if("DESC".equalsIgnoreCase(direction)){
			return Direction.DESC;
		}

		return Direction.ASC;
	}

	private String[] getSortProperties(JSONArray properties){
		String[] sortProperties = new String[properties.size()];
		for(int i = 0; i < properties.size(); i++) {
			JSONObject jsonObj = properties.getJSONObject(i);
			sortProperties[i] = jsonObj.getString("property");
		}
		return sortProperties;
	}

	private List<Sort.Order> getOrders(JSONArray sortsArray) {
		List<Sort.Order> sortsList = new ArrayList<>();
		for (int i = 0; i < sortsArray.size(); i++) {
			JSONObject sortObj = sortsArray.getJSONObject(i);
			if (null != sortObj) {
				String direction = sortObj.getString("direction");
				if (StringUtils.isBlank(direction) || (StringUtils.isNotBlank(direction) && !ArrayUtils.contains(new String[]{"ASC", "DESC"}, direction.toUpperCase()))) {
					direction = "ASC";
				}
				String properties = sortObj.getString("properties");
				Sort.Order order = new Sort.Order(Direction.fromString(direction), properties).nullsLast();
				sortsList.add(order);
			}
		}
		return sortsList;
	}

}
