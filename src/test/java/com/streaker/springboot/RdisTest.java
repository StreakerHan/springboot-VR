package com.streaker.springboot;


import com.streaker.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Redis测试
 *
 * @author StreakerHan
 * @date 2019/3/12 17:29:17
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RdisTest {

    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void test(){
        Object a = redisUtils.get("a");
        System.out.println(a);
    }
}
