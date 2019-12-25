package com.mgy.controller;

import com.mgy.po.TextMessage;
import com.mgy.util.CheckUtil;
import com.mgy.util.MessageUtil;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * @author MGY
 * @data 2019/12/24 20:10
 */
@Controller
@RequestMapping("/wx")
public class WeiXinController {

    @RequestMapping("/verify")
    public void verify(HttpServletRequest req, HttpServletResponse resp) throws IOException, DocumentException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");

        PrintWriter out = resp.getWriter();
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }


        Map<String, String> map = MessageUtil.xmlToMap(req);
        String toUserName = map.get("ToUserName");
        String fromUserName = map.get("FromUserName");
        String msgType = map.get("MsgType");
        String content = map.get("Content");

        String message = null;
        if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
            if ("1".equals(content)) {
                message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
            } else if ("2".equals(content)) {
                message = MessageUtil.initNewsMessage(toUserName,fromUserName);
            } else if ("3".equals(content)) {
                message = MessageUtil.initImageMessage(toUserName,fromUserName);
            } else if ("4".equals(content)) {
                message = MessageUtil.initMusicMessage(toUserName,fromUserName);
            } else if ("?".equals(content) || "？".equals(content)) {
                message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
            }
        } else if (MessageUtil.MESSAGE_EVENT.equals(msgType)) {
            String eventType = map.get("Event");
            if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
                message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
            }
        }

        out.print(message);

        out.close();
    }

}
