package com.cfysu.service;

import com.cfysu.model.Video;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/9.
 */
@Service
public class ParseService implements InitializingBean{

    private Invocable invocable;

    @Override
    public void afterPropertiesSet() throws Exception {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        String path = this.getClass().getClassLoader().getResource("script.js").getPath();//获取文件路径
        engine.eval(new FileReader(new File(path)));//执行文件
        invocable = (Invocable) engine;
    }

    /**
     * 解析主页
     *
     * @param html 主页html
     * @return 视频列表
     */
    public List<Video> parseIndex(String html) {
        List<Video> videoList = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        Element body = doc.getElementById("tab-featured");
        Elements itms = body.select("p");
        for (Element element : itms) {
            Video video = new Video();

            String title = element.getElementsByClass("title").first().text();
            video.setTitle(title);

            String imgUrl = element.select("img").first().attr("src");
            video.setImgUrl(imgUrl);

            String duration = element.getElementsByClass("duration").first().text();
            video.setDuration(duration);

            String contentUrl = element.select("a").first().attr("href");
            String viewKey = contentUrl.substring(contentUrl.indexOf("=") + 1);
            video.setViewKey(viewKey);

            String allInfo = element.text();
            int start = allInfo.indexOf("添加时间");
            String info = allInfo.substring(start);

            video.setInfo(info);
            videoList.add(video);
        }
        return videoList;
    }

    /**
     * 解析其他类别
     *
     * @param html 类别
     * @return 列表
     */
//    public static BaseResult parseHot(String html) {
//        int totalPage = 1;
//        List<Video> unLimitItemList = new ArrayList<>();
//        Document doc = Jsoup.parse(html);
//        Element body = doc.getElementById("fullside");
//
//        Elements listchannel = body.getElementsByClass("listchannel");
//        for (Element element : listchannel) {
//            Video unLimitItem = new Video();
//            String contentUrl = element.select("a").first().attr("href");
//            Logger.d(contentUrl);
//            contentUrl = contentUrl.substring(0, contentUrl.indexOf("&"));
//            Logger.d(contentUrl);
//            String viewKey = contentUrl.substring(contentUrl.indexOf("=") + 1);
//            unLimitItem.setViewKey(viewKey);
//
//            String imgUrl = element.select("a").first().select("img").first().attr("src");
//            Logger.d(imgUrl);
//            unLimitItem.setImgUrl(imgUrl);
//
//            String title = element.select("a").first().select("img").first().attr("title");
//            Logger.d(title);
//            unLimitItem.setTitle(title);
//
//
//            String allInfo = element.text();
//
//            int sindex = allInfo.indexOf("时长");
//
//            String duration = allInfo.substring(sindex + 3, sindex + 8);
//            unLimitItem.setDuration(duration);
//
//            int start = allInfo.indexOf("添加时间");
//            String info = allInfo.substring(start);
//            unLimitItem.setInfo(info.replace("还未被评分", ""));
//            Logger.d(info);
//
//            unLimitItemList.add(unLimitItem);
//        }
//        //总页数
//        Element pagingnav = body.getElementById("paging");
//        Elements a = pagingnav.select("a");
//        if (a.size() > 2) {
//            String ppp = a.get(a.size() - 2).text();
//            if (TextUtils.isDigitsOnly(ppp)) {
//                totalPage = Integer.parseInt(ppp);
//                Logger.d("总页数：" + totalPage);
//            }
//        }
//        BaseResult baseResult = new BaseResult();
//        baseResult.setTotalPage(totalPage);
//        baseResult.setUnLimitItemList(unLimitItemList);
//        return baseResult;
//    }

    /**
     * 解析视频播放连接
     *
     * @param html 视频页
     * @return 视频连接
     */
    public String parseVideoPlayUrl(String html) throws Exception {
        if (html.contains("你每天只可观看10个视频")) {
            return "";
        }
        Document doc = Jsoup.parse(html);
        String scriptStr = doc.select("video").first().select("script").get(0).childNode(0).toString();
        String res = scriptStr.substring(scriptStr.indexOf("\""), scriptStr.lastIndexOf("\""));
        String trimedStr = res.replaceAll("\"", "");
        String[] argsStr = trimedStr.split(",");

        String decodedStr = executeScritpt(argsStr);
        Document sourceElement = Jsoup.parse(decodedStr);

        String videoUrl = sourceElement.select("source").first().attr("src");
        return videoUrl;
    }

    public String executeScritpt(String[] args) throws Exception {

        Object result = invocable.invokeFunction("strencode", args);//调用js中函数
        return result.toString();
    }
}
