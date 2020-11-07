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

	/**
	 * 
	 * 返回 SysLog 格式对象
	 * 
	 * @param operateContent
	 *            操作内容
	 * @param memberInfo
	 *            登陆用户
	 * @param url
	 *            访问路径
	 * @param parms
	 *            参数
	 * @return
	 */
/*	protected TSysLog insertLog(String operateContent, String parms) {

		Subject subject = SecurityUtils.getSubject();
		TMemberInfo memberInfo = (TMemberInfo) subject.getPrincipal();

		TSysLog sysLog = new TSysLog();
		sysLog.setOperateTime(new Date());
		sysLog.setOperateContent(operateContent);

		sysLog.setUserName(memberInfo == null ? "" : memberInfo.getPhoneNum());
		sysLog.setName(memberInfo == null ? "" : memberInfo.getNickname());
		sysLog.setUserId(memberInfo == null ? "" : memberInfo.getId());
		sysLog.setIpAddr(IpUtil.getIpAddr(request));

		sysLog.setUrl(request.getRequestURI());

		sysLog.setParams(parms);
		logger.debug("记录日志:" + sysLog.toString());

		return tSysLogService.save(sysLog);
	}

	protected String getRequestUrl(String defaultUrl) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		String url = (String) session.getAttribute(MyUserFilter.MY_SHIRO_FILTER_URL_SESSION_KEY);
		if (StringUtils.isBlank(url)) {
			url = defaultUrl;
		}
		return url;
	}*/

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
