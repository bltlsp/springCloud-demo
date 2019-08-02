package com.yhb.base;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.TooManyResultsException;

public interface IBaseService<T> {
	
	/**
	 * 保存
	 * @param model
	 * @return
	 */
	int save(T model);
	/**
	 * 批量保存
	 * @param models
	 * @return
	 */
    //int save(List<T> models);
    /**
          * 软删除
     * @param id
     * @return
     */
    int deleteById(String id) throws InstantiationException, IllegalAccessException, InvocationTargetException;
    /**
          * 更新非空字段
     * @param model
     * @return
     */
    int updateSelectField(T model);
    /**
          * 更新所有字段
     * @param model
     * @return
     */
    int updateAllField(T model);
    /**
          * 按照ID查找
     * @param id
     * @return
     */
    T findById(String id);
    /**
          * 按照指定字段查找
     * @param fieldName
     * @param value
     * @return
     * @throws TooManyResultsException
     */
    T findBy(String fieldName, Object value) throws Exception; 
    
    /**
     * 按照指定字段查找
	* @param fieldName
	* @param value
	* @return
	* @throws TooManyResultsException
	*/
	List<T> findAllBy(String fieldName, Object value) throws Exception; 
	    
    /**
          * 按照多个ID查找
     * @param ids
     * @return
     */
    List<T> findByIds(String ids);
    
    /**
          * 根据参数查询   只支持bean属性中已经定义的对象 默认过滤逻辑删除的数据    
     * PageHelper默认count使用了子查询存在性能问题,
          * 数据量大的表建议使用自定义count方法:(查询方法名称_COUNT : selectAll_COUNT) 或者 自己实现分页
     * @param params
     * @return
     */
    List<T> findByParams(Map<String, Object> params, String orderBy, int pageNum, int pageSize);
    
}
