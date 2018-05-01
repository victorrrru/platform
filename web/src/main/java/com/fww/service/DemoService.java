package com.fww.service;

import com.fww.DemoVO;
import com.fww.FunctionException;
import com.fww.Result;

/**
 * 服务类
 * 
 * @author YangPH
 *
 */
public interface DemoService {
	/**
	 * 根据Id查询
	 */
	public Result selectById(Result result, Integer id) throws FunctionException;

	/**
	 * 新增
	 */
	public Result insert(Result result, DemoVO vo) throws FunctionException;

	/**
	 * 删除
	 */
	public Result delete(Result result, Integer id) throws FunctionException;

	/**
	 * 根据Id修改
	 */
	public Result update(Result result, DemoVO vo) throws FunctionException;

	/**
	 * 查询列表
	 */
	public Result selectList(Result result) throws FunctionException;

	/**
	 * 分页查询
	 */
	public Result selectPage(Result result, int current, int size) throws FunctionException;

	/**
	 * 统计
	 */
	public Result count(Result result, DemoVO vo) throws FunctionException;
	
	/**
	 * 定时任务调用测试
	 */
	public Integer taskTest() throws FunctionException;
	
	/**
	 * 关联查询
	 * 
	 * @param result
	 * @return
	 */
	public Result selectRelation(Result result, DemoVO vo) throws FunctionException;
	
}
