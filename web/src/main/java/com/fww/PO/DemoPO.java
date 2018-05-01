package com.fww.PO;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * 样例PO
 * 
 * @author YangPH
 *
 */
@TableName("t_demo")
@Data
public class DemoPO extends BasePO<DemoPO> {

    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	@TableField(value="user_name")
	private String userName;
	@TableField(value="password")
	private String password;

	@Override
	protected Serializable pkVal() {
		return id;
	}

}
