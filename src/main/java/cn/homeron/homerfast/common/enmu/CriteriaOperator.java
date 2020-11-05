package cn.homeron.homerfast.common.enmu;

/**
 * 条件查询运算符
 * @author Homer_Gu
 * @version 1.0.0
 * @date 2018-09-01
 */
public enum CriteriaOperator {
	LIKE("LIKE"), NOT_LIKE("NOTLIKE"), EQUAL("="), NOT_EQUAL("!="), NOT_EQUAL2("<>"), IN("IN"), NOT_IN("NOTIN"), 
	BETWEEN("BETWEEN"), NOT_BETWEEN("NOTBETWEEN"), GREATER_THAN(">"), GREATER_THAN_OR_EQUAL_TO(">="), LESS_THAN("<"), LESS_THAN_OR_EQUAL_TO("<="), 
	IS_NULL("ISNULL"), IS_NOT_NULL("ISNOTNULL");

	private final String operator;

	CriteriaOperator(String operator) {
		this.operator = operator.toUpperCase();
	}

	public String getOperator() {
		return operator;
	}

	public static CriteriaOperator getEnumByOperator(String operator) {
		if (null == operator) {
			return null;
		}
		for (CriteriaOperator temp : CriteriaOperator.values()) {
			if (temp.getOperator().equals(operator.toUpperCase())) {
				return temp;
			}
		}
		return null;
	}
}