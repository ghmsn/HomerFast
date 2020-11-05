package cn.homeron.homerfast.common.enmu;

public enum OrderInfoPayStatus {

	/**
	 * 已付款
	 */
	PAID,

	/**
	 * 待付款
	 */
	OBLIGATION,

	/**
	 * 正在退款
	 */
	RETURNING,

	/**
	 * 已退款
	 */
	RETURNED,

	/**
	 * 无需付款
	 */
	IGNORE_PAY;
	
	
	public static String getText(String payStatus){
		if(payStatus.equals(OrderInfoPayStatus.PAID.toString())){
			return "已付款";
		}
		if(payStatus.equals(OrderInfoPayStatus.OBLIGATION.toString())){
			return "待付款";
		}
		return "待付款";
	}
	
	public static boolean isObligation(String payStatus){
		
		if(payStatus.equals(OrderInfoPayStatus.OBLIGATION.toString())){
			return true;
		}
		return false;
	}

}
