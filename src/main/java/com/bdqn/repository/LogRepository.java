package com.bdqn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bdqn.entity.Log;


/**
 * @author asus
 *系统日志Repository接口
 */
public interface LogRepository extends JpaRepository<Log,Integer>,JpaSpecificationExecutor<Log>{	
	
}
