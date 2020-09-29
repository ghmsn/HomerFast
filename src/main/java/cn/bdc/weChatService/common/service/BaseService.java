package cn.bdc.weChatService.common.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class BaseService {

	
	@PersistenceContext
	protected EntityManager em;


	/**
	 * 建立分页排序请求
	 * 
	 * @param paramJSON
	 * @return
	 * 
	 * 示例
	 {
	    "page": 0,
	    "size": "10",
	    "userName": "王",
	    "sort": {
	        "direction": "DESC",
	        "properties": [
	            {
	                "property": "creationDate"
	            },
	            {
	                "property": "createdBy"
	            }
	        ]
	    }
	 }
	 */
	protected PageRequest buildPageRequest(JSONObject paramJSON) {

		Integer page = paramJSON.getInteger("page");
		Integer size = paramJSON.getInteger("size");
		JSONObject sortJSON = paramJSON.getJSONObject("sort");		
		String direction = sortJSON.getString("direction");
		JSONArray properties = sortJSON.getJSONArray("properties");
		Sort sort = Sort.by(parseDirection(direction), getSortProperties(properties));
		return PageRequest.of(page, size, sort);
	}
	
	private Direction parseDirection(String direction) {
		if(StringUtils.isBlank(direction))
			return Direction.ASC;
		
		if("DESC".equalsIgnoreCase(direction))
			return Direction.DESC;
		
		if("ASC".equalsIgnoreCase(direction))
			return Direction.ASC;
		
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
	
}
