package cn.homeron.homerfast.common.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MySort {
    @ApiModelProperty(value = "排序方向", example = "DESC")
    private String direction;
    @ApiModelProperty(value = "排序列（多个）")
    private List<String> properties;
}
