package cn.homeron.homerfast.common.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(nullable = false, length = 100)
	@ApiModelProperty(value = "主键")
	private long id;
}
