package com.streaker.dao;

import com.streaker.entity.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统日志接口的定义
 *
 * @author StreakerHan
 * @version 2018/9/6 11:24:19
 **/
@Mapper
public interface LogDao {

    /**
     * 添加日志接口
     */
    int addLog(Log log);

    /**
     * 通过主键id删除日志接口
     */
    /*int deleteLog(@Param("lid") Integer lid);*/

    /**
     * 查询日志列表接口
     */
    List<Log> getLogList();

}
