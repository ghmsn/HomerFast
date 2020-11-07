package cn.homeron.homerfast.common.bean;

import cn.homeron.homerfast.common.valid.AddGroup;
import cn.homeron.homerfast.common.valid.ListValue;
import cn.homeron.homerfast.common.valid.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

/**
 * 
 * @author Homer_Gu
 * @version 1.0.0
 * @date 2018-09-01
 */

@Getter
@Setter
@SuppressWarnings("serial")
@MappedSuperclass
public class BaseBean implements Serializable{

	/**
	 * 使用@Id指定主键
	 *
	 * 使用代码@GeneratedValue(strategy=GenerationType.AUTO)
	 * 指定主键的生成策略，mysql默认的是自增长。
	 *
	 */
	@NotNull(groups = {UpdateGroup.class})
	@Null(groups = {AddGroup.class})
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@ApiModelProperty(value = "主键")
	private Long id;

	@ListValue(values = {0, 1})
	@ApiModelProperty(value = "状态")
	private Integer isDelete;
}
