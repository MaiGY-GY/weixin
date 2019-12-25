package com.mgy;

import com.mgy.po.AccessToken;
import com.mgy.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class WeixinApplicationTests {

    @Test
    void contextLoads() throws IOException {
        AccessToken token = WeixinUtil.getAccessToken();
        System.out.println("票据："+token.getToken());
        System.out.println("有效时间："+token.getExpiresIn());

//        String path = "C:\\Users\\Administrator\\Desktop\\2.jpg";
//        String mediaId = WeixinUtil.upload(path, token.getToken(), "thumb");
//        System.out.println(mediaId);

        String menu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
        int result = WeixinUtil.createMenu(token.getToken(), menu);
        if (result == 0) {
            System.out.println("创建菜单成功");
        } else {
            System.out.println("错误码："+result);
        }
    }

}
