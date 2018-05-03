package com.fww.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fww.platform.FunctionException;
import com.fww.PO.DemoPO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * mapper接口
 * 
 * @author YangPH
 *
 */
@Repository
public interface DemoMapper extends BaseMapper<DemoPO> {
	
	/**
	 * 关联查询
	 * 
	 * @param params
	 * @return
	 */
	List<Map> selectRelation(Map params) throws FunctionException;


}