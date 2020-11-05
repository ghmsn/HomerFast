package cn.homeron.homerfast.common.enmu;

/**
* 微信事件枚举
* 
* @author cdj
* @date 2018年8月3日 下午1:17:08
*/
public enum WxEvent {
	/**
	 * 返回消息类型：文本
	 */
	RESP_MESSAGE_TYPE_TEXT ("text"),
 
	/**
	 * 返回消息类型：音乐
	 */
	RESP_MESSAGE_TYPE_MUSIC ("music"),
 
	/**
	 * 返回消息类型：图文
	 */
	RESP_MESSAGE_TYPE_NEWS ("news"),
 
	/**
	 * 返回消息类型：图片
	 */
	RESP_MESSAGE_TYPE_Image ("image"),
 
	/**
	 * 返回消息类型：语音
	 */
	RESP_MESSAGE_TYPE_Voice ("voice"),
 
	/**
	 * 返回消息类型：视频
	 */
	RESP_MESSAGE_TYPE_Video ("video"),
 
	/**
	 * 请求消息类型：文本
	 */
	REQ_MESSAGE_TYPE_TEXT ("text"),
 
	/**
	 * 请求消息类型：图片
	 */
	REQ_MESSAGE_TYPE_IMAGE ("image"),
 
	/**
	 * 请求消息类型：链接
	 */
	REQ_MESSAGE_TYPE_LINK ("link"),
 
	/**
	 * 请求消息类型：地理位置
	 */
	REQ_MESSAGE_TYPE_LOCATION ("location"),
 
	/**
	 * 请求消息类型：音频
	 */
	REQ_MESSAGE_TYPE_VOICE ("voice"),
 
	/**
	 * 请求消息类型：视频
	 */
	REQ_MESSAGE_TYPE_VIDEO ("video"),
 
	/**
	 * 请求消息类型：推送
	 */
	REQ_MESSAGE_TYPE_EVENT ("event"),
 
	/**
	 * 事件类型：subscribe(订阅)
	 */
	EVENT_TYPE_SUBSCRIBE ("subscribe"),
 
	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	EVENT_TYPE_UNSUBSCRIBE ("unsubscribe"),
 
	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	EVENT_TYPE_CLICK ("CLICK"),
 
	/**
	 * 事件类型：VIEW(自定义菜单URl视图)
	 */
	EVENT_TYPE_VIEW ("VIEW"),
 
	/**
	 * 事件类型：LOCATION(上报地理位置事件)
	 */
	EVENT_TYPE_LOCATION ("LOCATION"),
 
	/**
	 * 事件类型：SCAN(扫描二维码)
	 */
	EVENT_TYPE_SCAN ("SCAN");
	

	private final String event;

	private WxEvent(String event) {
		this.event = event;
	}

	public String getEvent() {
		return event;
	}
}
