package com.fww;

import com.fww.platform.FunctionException;

import java.util.ArrayList;
import java.util.List;

public class BaseServiceImpl {
	
	/**
	 * 属性复制,从orgi 复制到 destClz
	 * 这里只适合浅度复制，不适合深度复制
	 * 
	 * @param orgi  
	 * @param destClz 
	 * @return
	 * @throws FunctionException
	 */
	protected <X> X copyProperties(Object orgi,Class<?> destClz){
		try {
			Object obj = BeanCopier.copy(orgi, destClz);
			
			return (X)obj;
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 列表属性复制,从orgiLst 复制到 destClz
	 * @param orgiLst
	 * @param destClz
	 * @return
	 */
	protected <X>List<Object> copyProperties(List<Object> orgiLst,Class<?> destClz){
		try {
			List <Object>lst = new ArrayList<Object>();
			for(Object orgi : orgiLst){
				Object obj = BeanCopier.copy(orgi, destClz);
				lst.add(obj);
			}
			return lst;
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return null;
	}

}
