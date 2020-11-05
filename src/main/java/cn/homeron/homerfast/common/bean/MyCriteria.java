package cn.homeron.homerfast.common.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MyCriteria {
    @ApiModelProperty(value = "查询对象中的属性", example = "userName")
    private String attribute;
    @ApiModelProperty(value = "查询运算符（可用关系符号：=、 !=、 <>、 LIKE、 NOTLIKE、 >、 >=、 <、 <=、 IN、 NOTIN、 BETWEEN、 NOTBETWEEN、 ISNULL、 ISNOTNULL），其中ISNULL、 ISNOTNULL不需要传value或values", example = "=")
    private String operator;
    @ApiModelProperty(value = "查询条件单个值，使用=、 !=、 <>、 LIKE、 NOTLIKE、 >、 >=、 <、 <=时传value", example = "ZhangSan")
    private String value;
    @ApiModelProperty(value = "查询条件多个值，使用BETWEEN、 NOTBETWEEN时传递两个值，使用IN、 NOTIN时传递多个值")
    private List<String> values;
}
