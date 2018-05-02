package cn.bdc.weChatService.bean;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * 
 * @author Homer
 *
 * 创建一个实体类。
 * 
 * 如何持久化呢？
 * 
 * 1、使用@Entity进行实体类的持久化操作，当JPA检测到我们的实体类当中有
 * @Entity 注解的时候，会在数据库中生成对应的表结构信息
 * 
 * 如何指定主键以及主键的生成策略？
 * 2、使用@Id指定主键
 *
 */

@Table(name = "CAT")
@Entity
public class CatBean {
	
	/**
	 * 使用@Id指定主键
	 * 
	 * 使用代码@GeneratedValue(strategy=GenerationType.AUTO)
	 * 指定主键的生成策略，mysql默认的是自增长。
	 * 
	 */
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private int id;
	
	private String catName;
	
	private int catAge;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public int getCatAge() {
		return catAge;
	}

	public void setCatAge(int catAge) {
		this.catAge = catAge;
	}

}
