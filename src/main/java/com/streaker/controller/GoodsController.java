package com.streaker.controller;

import com.streaker.entity.Home;
import com.streaker.entity.ResponseBo;
import com.streaker.entity.User;
import com.streaker.service.GoodsService;
import com.streaker.service.UserService;
import com.streaker.utils.FtpUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * 商品控制器类
 *
 * @author StreakerHan
 * @version 2018/10/22 10:36:08
 **/
@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;

    @PostMapping("/add-good")
    @ResponseBody
    public ResponseBo addGoods(HttpServletRequest request, @RequestParam(value = "title",required = false) String title,
                               @RequestParam(value = "introduce",required = false) String introduce,
                               @RequestParam(value = "url",required = false) MultipartFile url,
                               @RequestParam(value = "username",required = false) String username,
                               @RequestParam(value = "picurl",required = false) MultipartFile picurl,
                               @RequestParam(value = "hdate",required = false) Date hdate,
                               @RequestParam(value = "uid",required = false) Integer uid) throws IOException {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        username = user.getUsername();
        User user1 = userService.getUserByUsername(username);
        uid = user1.getUid();
        Home home = new Home();
        home.setTitle(title);
        home.setIntroduce(introduce);
        home.setUsername(username);
        //上传封面
        String fileName = picurl.getOriginalFilename();
        InputStream inputStream = picurl.getInputStream();
        String picUrl = FtpUtil.uploadFile(fileName,inputStream);
        home.setPicurl(picUrl);
        //上传模型
        String fileName1 = url.getOriginalFilename();
        InputStream inputStream1 = url.getInputStream();
        String Url = FtpUtil.uploadFileModel(fileName1,inputStream1);
        home.setUrl(Url);

        home.setHdate(new Date());
        home.setUid(uid);
        goodsService.addHome(home);
        return ResponseBo.ok();
    }

}
