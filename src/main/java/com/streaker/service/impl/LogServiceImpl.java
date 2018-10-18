package com.streaker.service.impl;

import com.streaker.dao.LogDao;
import com.streaker.entity.Log;
import com.streaker.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 日志逻辑接口实现类
 *
 * @author StreakerHan
 * @version 2018/10/15 10:53:54
 **/
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;


    @Override
    public void addLog(Date ldate, Integer uid, String username, String ip, String role) {
        Log log = new Log();
        log.setLdate(new Date());
        log.setUid(uid);
        log.setUsername(username);
        log.setIp(ip);
        log.setRole(role);
        logDao.addLog(log);
    }

    @Override
    public List<Log> getLogList() {
        List<Log> logList = logDao.getLogList();
        return logList;
    }
}
