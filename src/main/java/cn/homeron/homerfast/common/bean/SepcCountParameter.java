package cn.homeron.homerfast.common.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SepcCountParameter {
    @ApiModelProperty(value = "条件查询List")
    private List<MyCriteria> criteria;
}
