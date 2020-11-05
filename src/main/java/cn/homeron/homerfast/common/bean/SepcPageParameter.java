package cn.homeron.homerfast.common.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SepcPageParameter extends SepcListParameter{
    @ApiModelProperty(value = "查询第几页，从0页开始", example = "0")
    private String page;
    @ApiModelProperty(value = "每页大小", example = "10")
    private String size;
}
