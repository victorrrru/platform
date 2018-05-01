package com.fww;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;


/**
 * YangPH
 * @author 样例VO
 *
 */
public class DemoVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id", required = true)
	private Integer id;
    @ApiModelProperty(value = "用户名称", required = true)
	private String userName;
    @ApiModelProperty(value = "密码", required = true)
	private String password;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
