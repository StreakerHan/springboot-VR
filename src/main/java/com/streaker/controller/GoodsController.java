package com.streaker.controller;

import com.streaker.annotation.LogAnno;
import com.streaker.entity.Home;
import com.streaker.entity.ResponseBo;
import com.streaker.entity.User;
import com.streaker.service.GoodsService;
import com.streaker.service.LogService;
import com.streaker.service.UserService;
import com.streaker.utils.Constant;
import com.streaker.utils.DateUtils;
import com.streaker.utils.FtpUtil;
import com.streaker.utils.UuidUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private LogService logService;

    /**
     * 添加商品
     * @param request
     * @param title
     * @param introduce
     * @param url
     * @param username
     * @param picurl
     * @param hdate
     * @param uid
     * @return
     * @throws IOException
     */
    @PostMapping("/add-good")
    @ResponseBody
    @LogAnno
    public ResponseBo addGoods(HttpServletRequest request, @RequestParam(value = "title",required = false) String title,
                               @RequestParam(value = "introduce",required = false) String introduce,
                               @RequestParam(value = "url",required = false) MultipartFile url,
                               @RequestParam(value = "username",required = false) String username,
                               @RequestParam(value = "picurl",required = false) MultipartFile picurl,
                               @RequestParam(value = "hdate",required = false) Date hdate,
                               @RequestParam(value = "uid",required = false) Integer uid) throws IOException {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Home home = new Home();
        String uuid = UuidUtil.getGuid();
        home.setHid(uuid);
        home.setTitle(title);
        home.setIntroduce(introduce);
        home.setUsername(user.getUsername());
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
        home.setUid(user.getUid());
        home.setHdate(DateUtils.dateTransToChina(new Date()));
        home.setIsShow("0");
        goodsService.addHome(home);
        logService.addLog(new Date(), user.getUid(), user.getUsername(), request.getRemoteAddr(),user.getRole(), Constant.ADD_GOOD);
        return ResponseBo.ok();
    }

    /**
     * 获取商品历列表
     * @param request
     * @return
     */
    @GetMapping("/good-manage")
    @LogAnno
    public String getGoodList(HttpServletRequest request){
        List<Home> homes = goodsService.getHomeList();
        request.setAttribute("homes",homes);
        return "good-manage";
    }

    /**
     * 删除商品
     * @param request
     * @param hid
     * @return
     */
    @PostMapping("/good/delete")
    @ResponseBody
    @LogAnno
    public ResponseBo delHome(HttpServletRequest request,@RequestParam(value = "hid") String hid){
        goodsService.deleteHome(hid);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        logService.addLog(new Date(), user.getUid(), user.getUsername(), request.getRemoteAddr(),user.getRole(),Constant.DELETE_GOOD);
        return ResponseBo.ok();
    }

    /**
     * 商品下架处理
     * @param request
     * @param hid
     * @return
     */
    @PostMapping("/good/downShow")
    @ResponseBody
    @LogAnno
    public ResponseBo downShow(HttpServletRequest request,@RequestParam(value = "hid") String hid){
        Home home = goodsService.findHomeById(hid);
        //将状态设置为0（下架）
        home.setIsShow("0");
        goodsService.updateHome(home);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        logService.addLog(new Date(), user.getUid(), user.getUsername(), request.getRemoteAddr(),user.getRole(),Constant.DOWN_GOOD);
        return ResponseBo.ok();
    }

    /**
     * 商品上架处理
     * @param request
     * @param hid
     * @return
     */
    @PostMapping("/good/upShow")
    @ResponseBody
    @LogAnno
    public ResponseBo upShow(HttpServletRequest request,@RequestParam(value = "hid") String hid){
        Home home = goodsService.findHomeById(hid);
        //将状态设置为1（上架）
        home.setIsShow("1");
        goodsService.updateHome(home);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        logService.addLog(new Date(), user.getUid(), user.getUsername(), request.getRemoteAddr(),user.getRole(),Constant.UP_GOOD);
        return ResponseBo.ok();
    }
}
