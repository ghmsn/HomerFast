package cn.homeron.homerfast.common.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 基础控制器
 * 
 * @author guhm
 * @date 2020年11月4日 上午11:50:57
 */
@ApiIgnore
public class SuperController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected HttpServletResponse response;

	@Autowired
	protected HttpSession session;

	@Autowired
	protected ServletContext application;

/*	@Resource
	protected TSysLogService tSysLogService;*/

	/**
	 * 重定向至地址 url
	 * 
	 * @param url
	 *            请求地址
	 * @return
	 */
	protected String redirectTo(String url) {
		StringBuffer rto = new StringBuffer("redirect:");
		rto.append(url);
		return rto.toString();
	}

	/**
	 * 
	 * 返回 JSON 格式对象
	 * 
	 * @param object
	 *            转换对象
	 * @return
	 */
	protected String toJson(Object object) {
		return JSON.toJSONString(object, SerializerFeature.BrowserCompatible);
	}

	/**
	 * 
	 * 返回 JSON 格式对象
	 * 
	 * @param object
	 *            转换对象
	 * @param features
	 *            序列化特点
	 * @return
	 */
	protected String toJson(Object object, String format) {
		if (format == null) {
			return toJson(object);
		}
		return JSON.toJSONStringWithDateFormat(object, format, SerializerFeature.WriteDateUseDateFormat);
	}

	protected JSONObject genSpec(Integer page, Integer size, JSONArray criteria, JSONObject sort) {

		JSONObject jsonParam = new JSONObject();
		if (null != page)
			jsonParam.put("page", page);
		if (null != size)
			jsonParam.put("size", size);

		jsonParam.put("criteria", criteria);

		if (null != sort)
			jsonParam.put("sort", sort);

		return jsonParam;
	}

	protected JSONObject genSpec(JSONArray criteria, JSONObject sort) {

		return genSpec(null, null, criteria, sort);
	}

	protected JSONObject genSpec(JSONArray criteria) {

		return genSpec(criteria, null);
	}

}
