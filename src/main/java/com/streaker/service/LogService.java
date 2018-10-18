package com.streaker.service;

import com.streaker.entity.Log;

import java.util.Date;
import java.util.List;

/**
 * 日志逻辑接口类
 *
 * @author StreakerHan
 * @version 2018/10/15 10:53:18
 **/
public interface LogService {

    /**
     * 添加日志接口
     */
    void addLog(Date ldate, Integer uid, String username, String ip, String role);

    /**
     * 通过主键id删除日志接口
     */
    /*int deleteLog(@Param("lid") Integer lid);*/

    /**
     * 查询日志列表接口
     */
    List<Log> getLogList();
}
