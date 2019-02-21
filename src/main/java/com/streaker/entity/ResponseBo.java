package com.streaker.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义服务器端向客户端返回的提示信息
 *
 * @author StreakerHan
 * @version 2018/9/7 11:04:27
 **/
public class ResponseBo extends HashMap<String, Object>{
    private static final long serialVersionUID = 1L;

    public ResponseBo() {
        put("code", 200);
        put("msg", "操作成功");
    }

    public static ResponseBo error() {
        return error(400, "操作失败");
    }

    public static ResponseBo error(String msg) {
        return error(500, msg);
    }

    public static ResponseBo error(int code, String msg) {
        ResponseBo ResponseBo = new ResponseBo();
        ResponseBo.put("code", code);
        ResponseBo.put("msg", msg);
        return ResponseBo;
    }

    public static ResponseBo ok(String msg) {
        ResponseBo ResponseBo = new ResponseBo();
        ResponseBo.put("msg", msg);
        return ResponseBo;
    }

    public static ResponseBo ok(Map<String, Object> map) {
        ResponseBo ResponseBo = new ResponseBo();
        ResponseBo.putAll(map);
        return ResponseBo;
    }

    public static ResponseBo ok() {
        return new ResponseBo();
    }

    @Override
    public ResponseBo put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
