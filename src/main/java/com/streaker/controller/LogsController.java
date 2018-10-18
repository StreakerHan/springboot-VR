package com.streaker.controller;

import com.streaker.entity.Log;
import com.streaker.service.LogService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 系统日志控制器类
 *
 * @author StreakerHan
 * @version 2018/10/15 10:50:09
 **/
@Controller
public class LogsController {

    @Autowired
    private LogService logService;

    @GetMapping("/system-logs")
    public String getLogList(HttpServletRequest request){
        List<Log> logs = logService.getLogList();
        request.setAttribute("logs",logs);
        return "system-logs";
    }
}
