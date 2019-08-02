package com.yhb.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.yhb.constant.CommonConstant;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

public abstract class BaseAbstractService<T> implements IBaseService<T>{
	@Autowired
	protected BaseDao<T> baseDao;

	private Class<T> modelClass; // 当前泛型真实类型的Class

	@SuppressWarnings("unchecked")
	public BaseAbstractService() {
		// 获得具体model，通过反射来根据属性条件查找数据
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		modelClass = (Class<T>) pt.getActualTypeArguments()[0];
	}

	@Transactional
	public int save(T model) {
		return baseDao.insertSelective(model);
	}

	/*@Override
	@Transactional
	public int save(List<T> models) {
		return baseDao.insertList(models);
	}*/

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int deleteById(String id) throws InstantiationException, IllegalAccessException, InvocationTargetException {
		T obj = modelClass.newInstance();
		BeanUtils.setProperty(obj, CommonConstant.COLUMN_IS_DEL, CommonConstant.STATUS_DEL);
		BeanUtils.setProperty(obj, CommonConstant.COLUMN_ID, id);
		BeanUtils.setProperty(obj, CommonConstant.COLUMN_UPDATE_TIME, new Date());
		return baseDao.updateByPrimaryKeySelective(obj);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateSelectField(T model) {
		return baseDao.updateByPrimaryKeySelective(model);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateAllField(T model) {
		return baseDao.updateByPrimaryKey(model);
	}

	public T findById(String id) {
		return baseDao.selectByPrimaryKey(id);
	}

	public T findBy(String fieldName, Object value) throws Exception {
		try {
			T obj = modelClass.newInstance();
			BeanUtils.setProperty(obj, fieldName, value);
			BeanUtils.setProperty(obj, CommonConstant.COLUMN_IS_DEL, CommonConstant.STATUS_ACTIVE);
			return baseDao.selectOne(obj);
		} catch (ReflectiveOperationException e) {
			throw new Exception(e.getMessage(), e);
		}
	}
	
	public List<T> findAllBy(String fieldName, Object value) throws Exception {
		try {
			T obj = modelClass.newInstance();
			BeanUtils.setProperty(obj, fieldName, value);
			BeanUtils.setProperty(obj, CommonConstant.COLUMN_IS_DEL, CommonConstant.STATUS_ACTIVE);
			return baseDao.select(obj);
		} catch (ReflectiveOperationException e) {
			throw new Exception(e.getMessage(), e);
		}
	}

	public List<T> findByIds(String ids) {
		return baseDao.selectByIds(ids);
	}

	public List<T> findByParams(Map<String, Object> params, String orderBy, int pageNum, int pageSize) {
		Condition condition = new Condition(modelClass);
		if(null==params) {
			params = new HashMap<String, Object>();
		}
		params.put(CommonConstant.COLUMN_IS_DEL, CommonConstant.STATUS_ACTIVE);
		Criteria criteria = condition.createCriteria();
		criteria.andEqualTo(params);
		if(StringUtils.isNotBlank(orderBy)) {
			condition.setOrderByClause(orderBy);
		}
		PageHelper.startPage(pageNum, pageSize);
		return baseDao.selectByCondition(condition);
	}
}
