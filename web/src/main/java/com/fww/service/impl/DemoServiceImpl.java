package com.fww.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fww.BaseServiceImpl;
import com.fww.DemoVO;
import com.fww.FunctionException;
import com.fww.mapper.DemoMapper;
import com.fww.PO.DemoPO;
import com.fww.Result;
import com.fww.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务实现类
 * 
 * @author YangPH
 *
 */
@Service
public class DemoServiceImpl extends BaseServiceImpl implements DemoService {
	@Autowired
	private DemoMapper demoMapper;

	/**
	 * 新增
	 */
	@Override
	public Result insert(Result result, DemoVO vo) throws FunctionException {
		DemoPO po = super.copyProperties(vo, DemoPO.class);
		
		Integer count = demoMapper.insert(po);
		result.setData(count);
		System.out.println("----->主键为："+po.getId());
		
		return result;
	}
	
	/**
	 * 删除
	 */
	@Override
	public Result delete(Result result, Integer id) throws FunctionException{ 
		Integer count = demoMapper.deleteById(id);
		result.setData(count);
		
		return result;
	}
	
	/**
	 * 根据Id修改
	 */
	@Override
	public Result update(Result result, DemoVO vo) throws FunctionException{ 
		DemoPO po = super.copyProperties(vo, DemoPO.class);
		
		Integer count = demoMapper.updateById(po);
		result.setData(count);
		
		return result;
	}
	
	/**
	 * 根据Id查询
	 */
	@Override
	public Result selectById(Result result,Integer id) throws FunctionException{ 
		//RpcContext.getContext().getAttachments().get("userInfo");
		//demoFinanceServiceRemote.selectById(result, id);
		DemoPO po = demoMapper.selectById(id);
		result.setData(JSONObject.toJSONString(po));
		
		return result;
	}

	/**
	 * 查询列表
	 */
	@Override
	public Result selectList(Result result) throws FunctionException{ 
		Wrapper<DemoPO> po = new Condition();
		po.ge("id", "20");//大于等于
//		po.eq("id", "20");//等于
		
		List lst = demoMapper.selectList(po);
		result.setData(lst);
		
		return result;
	}
	
	/**
	 * 分页查询
	 */
	@Override
	public Result selectPage(Result result,int current, int size) throws FunctionException{
		//查询条件
		Wrapper <DemoPO> po = new Condition(); 
//		po.ge("id", "20");//大于等于
		 
		Page<DemoPO> page = new Page<DemoPO>(current, size);
		
		List lst = demoMapper.selectPage(page, po);
		result.setData(lst);
		
		return result;
	}
	
	/**
	 * 统计
	 */
	@Override
	public Result count(Result result, DemoVO vo) throws FunctionException{
		DemoPO po = super.copyProperties(vo, DemoPO.class);
		
		//添加查询条件
		Wrapper <DemoPO> cond = new Condition();  
		cond.like("user_name", po.getUserName());//user_name like,模糊查询
		
		Integer count = demoMapper.selectCount(cond); 
		result.setData(count);
		
		return result;
	}
	
	/**
	 * 关联查询
	 * 
	 * @param result
	 */
	@Override
	public Result selectRelation(Result result, DemoVO vo) throws FunctionException{
		Map<String,Integer> params = new HashMap<String,Integer>();
		params.put("userId", vo.getId());
		
		List lst = demoMapper.selectRelation(params);
		result.setData(lst);
		
		return result;
	} 
	

	/**
	 * 定时任务调用测试
	 */
	@Override
	public Integer taskTest() throws FunctionException{ 
		Integer count = demoMapper.selectCount(null); 
		
		return count;
	}
}
