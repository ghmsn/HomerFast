package cn.homeron.homerfast.common.service;

import cn.homeron.homerfast.common.enmu.TradeType;
import cn.homeron.homerfast.common.utils.DatetimeUtil;
import cn.homeron.homerfast.common.utils.MD5Utils;
import cn.homeron.homerfast.common.utils.StringUtil;
import cn.homeron.homerfast.common.utils.XmlUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.*;

@Service
public class PaymentService extends SuperService {

	private static final String ORDER_PAY = "https://api.mch.weixin.qq.com/pay/unifiedorder"; // 统一下单

	private static final String ORDER_PAY_QUERY = "https://api.mch.weixin.qq.com/pay/orderquery"; // 支付订单查询

	private static final String ORDER_REFUND = "https://api.mch.weixin.qq.com/secapi/pay/refund"; // 申请退款

	private static final String ORDER_REFUND_QUERY = "https://api.mch.weixin.qq.com/pay/refundquery"; // 订单退款查询

	@Value("${wx.app.appid}")
	private String appAppId;

	@Value("${wx.pay.mchid}")
	private String wxPayMchid;

	@Value("${wx.pay.api.secret}")
	private String wxPayApiSecret;

	@Value("${wx.pay.notifyurl}")
	private String wxPayNotifyUrl;

	public Map<String, String> preWxPay(String openid, String tradeType, double fee) {

		String tradeid = PaymentService.getTradeNo();
		String tradeName = "学习资料";

		logger.debug("[/placeOrder] " + tradeid + " begin...");

		Map<String, String> payMap = new HashMap<String, String>();

		try {
			String total_fee = BigDecimal.valueOf(fee).multiply(BigDecimal.valueOf(100))
					.setScale(0, BigDecimal.ROUND_HALF_UP).toString();
			Map<String, String> parm = new HashMap<String, String>();
			parm.put("appid", appAppId);
			parm.put("device_info", "WEB");
			parm.put("nonce_str", PaymentService.getNonceStr());
			parm.put("body", tradeName);
			parm.put("total_fee", total_fee);
			InetAddress address = InetAddress.getLocalHost();
			String hostAddress = address.getHostAddress();
			parm.put("spbill_create_ip", hostAddress);
			parm.put("notify_url", wxPayNotifyUrl);
			parm.put("trade_type", tradeType);
			parm.put("product_id", tradeid);
			if (TradeType.JSAPI.toString().equals(tradeType)) {
				parm.put("openid", openid);
			}
			parm.put("sign", PaymentService.getSign(parm, wxPayApiSecret));
			String restxml = HttpUtil.post(ORDER_PAY, XmlUtil.xmlFormat(parm, false));
			Map<String, String> restmap = XmlUtil.xmlParse(restxml);

			if (CollectionUtil.isNotEmpty(restmap) && "SUCCESS".equals(restmap.get("result_code"))) {
				payMap.put("appid", appAppId);
				payMap.put("prepayid", restmap.get("prepay_id"));
				payMap.put("package", "Sign=WXPay");
				payMap.put("noncestr", PaymentService.getNonceStr());
				payMap.put("timestamp", PaymentService.payTimestamp());
				payMap.put("code_url", restmap.get("code_url"));
				payMap.put("mweb_url", restmap.get("mweb_url"));
				payMap.put("out_trade_no", tradeid);
				logger.debug("[/placeOrder] " + tradeid + " end...");
				
				payMap.put("code", "success");
				payMap.put("desc", "");				
			} else if (CollectionUtil.isNotEmpty(restmap)) {
				logger.error("[/placeOrder] " + tradeid + " err_code:" + restmap.get("err_code") + ",err_code_des:"
						+ restmap.get("err_code_des"));
				payMap.put("code", restmap.get("err_code"));
				payMap.put("desc", restmap.get("err_code_des"));
			} else {
				payMap.put("code", "notGeneratedOrder");
				payMap.put("desc", "未生成订单.");
			}
		} catch (Exception ex) {
			logger.error("[/placeOrder] " + tradeid + " exception...", ex);
			payMap.put("code", "error");
			payMap.put("desc", "未知错误：" + ex.getMessage());
		}
		return payMap;
	}

	/**
	 * 生成订单号
	 * 
	 * @return
	 */
	public static String getTradeNo() {
		return "TDNO" + DatetimeUtil.formatDate(new Date(), DatetimeUtil.TIME_STAMP_PATTERN)
				+ RandomUtil.randomString(RandomUtil.BASE_NUMBER, 32);
	}

	/**
	 * 退款单号
	 * 
	 * @return
	 */
	public static String getRefundNo() {
		// 自增8位数 00000001
		return "RFNO" + DatetimeUtil.formatDate(new Date(), DatetimeUtil.TIME_STAMP_PATTERN)
				+ RandomUtil.randomString(RandomUtil.BASE_NUMBER, 32);
	}

	/**
	 * 退款单号
	 * 
	 * @return
	 */
	public static String getTransferNo() {
		// 自增8位数 00000001
		return "TFNO" + DatetimeUtil.formatDate(new Date(), DatetimeUtil.TIME_STAMP_PATTERN)
				+ RandomUtil.randomString(RandomUtil.BASE_NUMBER, 32);
	}

	/**
	 * 创建支付随机字符串
	 * 
	 * @return
	 */
	public static String getNonceStr() {
		return RandomUtil.randomString(RandomUtil.BASE_CHAR, 64);
	}

	/**
	 * 支付时间戳
	 * 
	 * @return
	 */
	public static String payTimestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	public static String getSign(Map<String, String> params, String paternerKey) throws UnsupportedEncodingException {
		return MD5Utils.getMD5(createSign(params, false) + "&key=" + paternerKey).toUpperCase();
	}

	/**
	 * 构造签名
	 * 
	 * @param params
	 * @param encode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String createSign(Map<String, String> params, boolean encode) throws UnsupportedEncodingException {
		Set<String> keysSet = params.keySet();
		Object[] keys = keysSet.toArray();
		Arrays.sort(keys);
		StringBuffer temp = new StringBuffer();
		boolean first = true;
		for (Object key : keys) {
			if (key == null || StringUtil.isEmpty(params.get(key))) {
				continue;
			}
			if (first) {
				first = false;
			} else {
				temp.append("&");
			}
			temp.append(key).append("=");
			Object value = params.get(key);
			String valueStr = "";
			if (null != value) {
				valueStr = value.toString();
			}
			if (encode) {
				temp.append(URLEncoder.encode(valueStr, "UTF-8"));
			} else {
				temp.append(valueStr);
			}
		}
		return temp.toString();
	}

}
