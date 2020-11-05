package cn.homeron.homerfast.common.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MySorts {
    @ApiModelProperty(value = "排序方向", example = "DESC")
    private String direction;
    @ApiModelProperty(value = "排序列", example = "age")
    private String properties;
}
