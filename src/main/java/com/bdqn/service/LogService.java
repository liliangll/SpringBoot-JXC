package com.bdqn.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.bdqn.entity.Log;

/**
 * 日志Service接口
 * @author Administrator
 *
 */
public interface LogService {
	/**
	 * @param log
	 * 添加或者修改日志信息
	 */
	public void save(Log log);
	/**
	 * @param Log
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param strings
	 * @return
	 * 根据条件分页查询日志信息
	 */
	public List<Log> list(Log log,Integer page,Integer pageSize,Direction direction,String...properties);
	/**
	 * @param Log
	 * @return
	 * 总记录数
	 */
	public Long getCount(Log log);
	
}
