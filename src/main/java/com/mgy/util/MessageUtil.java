package com.mgy.util;

import com.mgy.po.*;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author MGY
 * @data 2019/12/24 21:01
 */
public class MessageUtil {

    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_NEWS = "news";
    public static final String MESSAGE_IMAGE = "image";
    public static final String MESSAGE_MUSIC = "music";
    public static final String MESSAGE_VOICE = "voice";
    public static final String MESSAGE_VIDEO = "video";
    public static final String MESSAGE_LINK = "link";
    public static final String MESSAGE_LOCATION = "location";
    public static final String MESSAGE_EVENT = "event";
    public static final String MESSAGE_SUBSCRIBE = "subscribe";
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
    public static final String MESSAGE_CLICK = "CLICK";
    public static final String MESSAGE_VIEW = "VIEW";

    private static final String MEDIAID = "lYgiNoMCdfZO7DEDzzULr1ygbzYhkTax2akDtK2xyEED7nc8ZAp3a_FA1wBnYcrx";
    private static final String THUMBMEDIAID = "himcyClweI0pQW_yI1w_pjD_tIvGQNagp6jx86vMmVcoWvCrZQDkvgrm_yqcZevB";

    /**
     * xml 转为 map 集合
     * @param req
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static Map<String, String> xmlToMap(HttpServletRequest req) throws IOException, DocumentException {
        Map<String, String> map = new HashMap<>();
        SAXReader reader = new SAXReader();

        InputStream ins = req.getInputStream();
        Document doc = reader.read(ins);

        Element root = doc.getRootElement();

        List<Element> list = root.elements();

        for (Element e : list) {
            map.put(e.getName(), e.getText());
        }
        ins.close();

        return map;
    }

    /**
     * 将文本对象转换成xml
     * @param textMessage
     * @return
     */
    public static String textMessageToXml(TextMessage textMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    public static String initText(String toUserName, String fromUserName, String content) {
        TextMessage text = new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(MessageUtil.MESSAGE_TEXT);
        text.setCreateTime(new Date().getTime());
        text.setContent(content);
        return textMessageToXml(text);
    }

    /**
     * 主菜单
     * @return
     */
    public static String menuText() {
        StringBuffer sb = new StringBuffer();
        sb.append("欢迎您的关注，请按照菜单提示进行操作：\n\n");
        sb.append("1.项目介绍\n");
        sb.append("2.项目结构介绍\n\n");
        sb.append("回复？调出此菜单。");
        return sb.toString();
    }

    public static String firstMenu() {
        StringBuffer sb = new StringBuffer();
        sb.append("主要介绍项目的基本构成部分、项目的操作...");
        return sb.toString();
    }

    public static String secondMenu() {
        StringBuffer sb = new StringBuffer();
        sb.append("主要介绍项目每个部分的构成及每个部分的操作。");
        return sb.toString();
    }

    /**
     * 图文消息转为xml
     * @param newsMessage
     * @return
     */
    public static String newsMessageToXml(NewsMessage newsMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new News().getClass());
        return xstream.toXML(newsMessage);
    }

    /**
     * 图片消息转为xml
     * @param imageMessage
     * @return
     */
    public static String imageMessageToXml(ImageMessage imageMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", imageMessage.getClass());
        return xstream.toXML(imageMessage);
    }

    /**
     * 音乐消息转为xml
     * @param musicMessage
     * @return
     */
    public static String musicMessageToXml(MusicMessage musicMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", musicMessage.getClass());
        return xstream.toXML(musicMessage);
    }

    /**
     * 图文消息的组装
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initNewsMessage(String toUserName, String fromUserName) {
        String message = null;
        List<News> newsList = new ArrayList<>();
        NewsMessage newsMessage = new NewsMessage();

        News news = new News();
        news.setTitle("Git的介绍");
        news.setPicUrl("http://maigy.natapp1.cc/images/tupian.jpg");
        news.setDescription("Git is a free and open source distributed version control system designed to handle everything from small to very large projects with speed and efficiency.");
        news.setUrl("https://git-scm.com/");

        newsList.add(news);

        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MESSAGE_NEWS);
        newsMessage.setArticles(newsList);
        newsMessage.setArticleCount(newsList.size());

        message = newsMessageToXml(newsMessage);
        return message;
    }

    /**
     * 组装图片消息
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initImageMessage(String toUserName, String fromUserName) {
        String message = null;
        Image image = new Image();
        image.setMediaId(MEDIAID);
        ImageMessage imageMessage = new ImageMessage();
        imageMessage.setFromUserName(toUserName);
        imageMessage.setToUserName(fromUserName);
        imageMessage.setMsgType(MESSAGE_IMAGE);
        imageMessage.setCreateTime(new Date().getTime());
        imageMessage.setImage(image);
        message = imageMessageToXml(imageMessage);
        return message;
    }

    /**
     * 组装音乐消息
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initMusicMessage(String toUserName, String fromUserName) {
        String message = null;
        Music music = new Music();
        music.setThumbMediaId(THUMBMEDIAID);
        music.setTitle("每一步--徐小凤");
        music.setDescription("《每一步》是徐小凤加盟宝丽金的第一张唱片,当时该公司对小凤的加盟非常重视");
        music.setMusicUrl("http://maigy.natapp1.cc/music/每一步.mp3");
        music.setHQMusicUrl("http://maigy.natapp1.cc/music/每一步.mp3");

        MusicMessage musicMessage = new MusicMessage();
        musicMessage.setFromUserName(toUserName);
        musicMessage.setToUserName(fromUserName);
        musicMessage.setMsgType(MESSAGE_MUSIC);
        musicMessage.setCreateTime(new Date().getTime());
        musicMessage.setMusic(music);
        message = musicMessageToXml(musicMessage);
        return message;
    }
}
