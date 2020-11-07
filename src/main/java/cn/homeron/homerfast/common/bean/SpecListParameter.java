package cn.homeron.homerfast.common.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpecListParameter extends SpecCountParameter {
    @ApiModelProperty(value = "排序，同一排序方向多个值排序，sort和sorts只能使用一个：使用sort时，sorts不生效")
    private MySort sort;
    @ApiModelProperty(value = "排序，多个排序方向，每个排序方向单个值，sort和sorts只能使用一个：使用sorts时，不要传sort")
    private List<MySorts> sorts;
}
