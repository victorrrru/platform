package com.fww.PO;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.SqlMethod;
import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.SqlRunner;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BasePO<T extends BasePO<?>> extends Model<T>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5697076753081145994L;

	@TableField(value="create_user")
	private Integer createUser;
	@TableField(value="update_user")
	private Integer updateUser;
	@TableField(value="create_time")
	private Date createTime;
	@TableField(value="update_time")
	private Date updateTime;

	/**
     * <p>
     * 插入（字段选择插入）
     * </p>
     */
    @Override
	@Transactional
    public boolean insert() {  
        return SqlHelper.retBool(sqlSession().insert(sqlStatement(SqlMethod.INSERT_ONE), this));
    }

    /**
     * <p>
     * 插入（所有字段插入）
     * </p>
     */
    @Override
	@Transactional
    public boolean insertAllColumn() {
        return SqlHelper.retBool(sqlSession().insert(sqlStatement(SqlMethod.INSERT_ONE_ALL_COLUMN), this));
    }

    /**
     * <p>
     * 插入 OR 更新
     * </p>
     */
    @Override
	@Transactional
    public boolean insertOrUpdate() {
        if (StringUtils.checkValNull(pkVal())) {
            // insert
            return insert();
        } else {
            /*
             * 更新成功直接返回，失败执行插入逻辑
			 */
            return updateById() || insert();
        }
    }

    /**
     * <p>
     * 根据 ID 删除
     * </p>
     *
     * @param id 主键ID
     * @return
     */
    @Override
	@Transactional
    public boolean deleteById(Serializable id) {
        return SqlHelper.retBool(sqlSession().delete(sqlStatement(SqlMethod.DELETE_BY_ID), id));
    }

    /**
     * <p>
     * 根据主键删除
     * </p>
     *
     * @return
     */
    @Override
	@Transactional
    public boolean deleteById() {
        if (StringUtils.checkValNull(pkVal())) {
            throw new MybatisPlusException("deleteById primaryKey is null.");
        }
        return deleteById(this.pkVal());
    }

    /**
     * <p>
     * 删除记录
     * </p>
     *
     * @param whereClause 查询条件
     * @param args        查询条件值
     * @return
     */
    @Override
	@Transactional
    public boolean delete(String whereClause, Object... args) {
        return delete(Condition.create().where(whereClause, args));
    }

    /**
     * <p>
     * 删除记录
     * </p>
     *
     * @param wrapper
     * @return
     */
    @Override
	@Transactional
    public boolean delete(Wrapper wrapper) {
        Map<String, Object> map = new HashMap<>();
        // delete
        map.put("ew", wrapper);
        return SqlHelper.retBool(sqlSession().delete(sqlStatement(SqlMethod.DELETE), map));
    }

    /**
     * <p>
     * 更新（字段选择更新）
     * </p>
     */
    @Override
	@Transactional
    public boolean updateById() {
        if (StringUtils.checkValNull(pkVal())) {
            throw new MybatisPlusException("updateById primaryKey is null.");
        }
        // updateById
        Map<String, Object> map = new HashMap<>();
        map.put("et", this);
        return SqlHelper.retBool(sqlSession().update(sqlStatement(SqlMethod.UPDATE_BY_ID), map));
    }

    /**
     * <p>
     * 更新（所有字段更新）
     * </p>
     */
    @Override
	@Transactional
    public boolean updateAllColumnById() {
        if (StringUtils.checkValNull(pkVal())) {
            throw new MybatisPlusException("updateAllColumnById primaryKey is null.");
        }
        // updateAllColumnById
        Map<String, Object> map = new HashMap<>();
        map.put("et", this);
        return SqlHelper.retBool(sqlSession().update(sqlStatement(SqlMethod.UPDATE_ALL_COLUMN_BY_ID), map));
    }

    /**
     * <p>
     * 执行 SQL 更新
     * </p>
     *
     * @param whereClause 查询条件
     * @param args        查询条件值
     * @return
     */
    @Override
	@Transactional
    public boolean update(String whereClause, Object... args) {
        // update
        return update(Condition.create().where(whereClause, args));
    }

    /**
     * <p>
     * 执行 SQL 更新
     * </p>
     *
     * @param wrapper
     * @return
     */
    @Override
	@Transactional
    public boolean update(Wrapper wrapper) {
        Map<String, Object> map = new HashMap<>();
        map.put("et", this);
        map.put("ew", wrapper);
        // update
        return SqlHelper.retBool(sqlSession().update(sqlStatement(SqlMethod.UPDATE), map));
    }

    /**
     * <p>
     * 查询所有
     * </p>
     *
     * @return
     */
    @Override
	public List<T> selectAll() {
        return sqlSession().selectList(sqlStatement(SqlMethod.SELECT_LIST));
    }

    /**
     * <p>
     * 根据 ID 查询
     * </p>
     *
     * @param id 主键ID
     * @return
     */
    @Override
	public T selectById(Serializable id) {
        return sqlSession().selectOne(sqlStatement(SqlMethod.SELECT_BY_ID), id);
    }

    /**
     * <p>
     * 根据主键查询
     * </p>
     *
     * @return
     */
    @Override
	public T selectById() {
        if (StringUtils.checkValNull(pkVal())) {
            throw new MybatisPlusException("selectById primaryKey is null.");
        }
        return selectById(this.pkVal());
    }

    /**
     * <p>
     * 查询总记录数
     * </p>
     *
     * @param wrapper
     * @return
     */

    @Override
	public List<T> selectList(Wrapper wrapper) {
        Map<String, Object> map = new HashMap<>();
        map.put("ew", wrapper);
        return sqlSession().selectList(sqlStatement(SqlMethod.SELECT_LIST), map);
    }

    /**
     * <p>
     * 查询所有
     * </p>
     *
     * @param whereClause
     * @param args
     * @return
     */
    @Override
	public List<T> selectList(String whereClause, Object... args) {
        return selectList(Condition.create().where(whereClause, args));
    }

    /**
     * <p>
     * 查询一条记录
     * </p>
     *
     * @param wrapper
     * @return
     */
    @Override
	public T selectOne(Wrapper wrapper) {
        return SqlHelper.getObject(selectList(wrapper));
    }

    /**
     * <p>
     * 查询一条记录
     * </p>
     *
     * @param whereClause
     * @param args
     * @return
     */
    @Override
	public T selectOne(String whereClause, Object... args) {
        return selectOne(Condition.create().where(whereClause, args));
    }

    /**
     * <p>
     * 翻页查询
     * </p>
     *
     * @param page    翻页查询条件
     * @param wrapper
     * @return
     */
    @Override
	public Page<T> selectPage(Page<T> page, Wrapper<T> wrapper) {
        Map<String, Object> map = new HashMap<>();
        SqlHelper.fillWrapper(page, wrapper);
        map.put("ew", wrapper);
        List<T> tl = sqlSession().selectList(sqlStatement(SqlMethod.SELECT_PAGE), map, page);
        page.setRecords(tl);
        return page;
    }

    /**
     * <p>
     * 查询所有(分页)
     * </p>
     *
     * @param page
     * @param whereClause
     * @param args
     * @return
     */
    @Override
	@SuppressWarnings("unchecked")
    public Page<T> selectPage(Page<T> page, String whereClause, Object... args) {
        return selectPage(page, Condition.create().where(whereClause, args));
    }

    /**
     * <p>
     * 查询总数
     * </p>
     *
     * @param whereClause 查询条件
     * @param args        查询条件值
     * @return
     */
    @Override
	public int selectCount(String whereClause, Object... args) {
        return selectCount(Condition.create().where(whereClause, args));
    }

    /**
     * <p>
     * 查询总数
     * </p>
     *
     * @param wrapper
     * @return
     */
    @Override
	public int selectCount(Wrapper wrapper) {
        Map<String, Object> map = new HashMap<>();
        map.put("ew", wrapper);
        return SqlHelper.retCount(sqlSession().<Integer>selectOne(sqlStatement(SqlMethod.SELECT_COUNT), map));
    }

    /**
     * <p>
     * 执行 SQL
     * </p>
     */
    @Override
	public SqlRunner sql() {
        return new SqlRunner(getClass());
    }

    /**
     * <p>
     * 获取Session 默认自动提交
     * <p/>
     */
    @Override
	protected SqlSession sqlSession() {
        return SqlHelper.sqlSession(getClass());
    }

    /**
     * 获取SqlStatement
     *
     * @param sqlMethod
     * @return
     */
    @Override
	protected String sqlStatement(SqlMethod sqlMethod) {
        return sqlStatement(sqlMethod.getMethod());
    }

    /**
     * 获取SqlStatement
     *
     * @param sqlMethod
     * @return
     */
    @Override
	protected String sqlStatement(String sqlMethod) {
        return SqlHelper.table(getClass()).getSqlStatement(sqlMethod);
    }

    /**
     * 主键值
     */
    @Override
	protected abstract Serializable pkVal();

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public Integer getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
    
    
}
