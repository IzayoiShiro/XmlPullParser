package com.example.xmlpullparser;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 杨天宇 on 2016/7/15.
 */
public class WeatherService {
    private static final String TAG = "WeatherService";

    //返回天气信息的集合
    public static List<WeatherInfo> getWeatherInfos(InputStream is) throws Exception {
        //得到pull解析器
        Log.i(TAG, "getWeatherInfos: WeatherService开始运行");
        XmlPullParser parser = Xml.newPullParser();
        Log.i(TAG, "getWeatherInfos: WeatherService开始运行");
        //初始化解析器，第一个参数代表含xml的数据
        parser.setInput(is, "utf-8");
        Log.i(TAG, "getWeatherInfos: WeatherService开始运行");
        List<WeatherInfo> weatherInfos = null;
        WeatherInfo weatherInfo = null;
        //得到当前事件的类型
        int type = parser.getEventType();
        Log.i(TAG, "getWeatherInfos: WeatherService开始运行");
        //END_DOCUMENT文档结束标签
        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                //一个结点的开始标签
                case XmlPullParser.START_TAG:
                    //解析到全局开始的标签infos根结点
                    if ("infos".equals(parser.getName())) {
                        weatherInfos = new ArrayList<WeatherInfo>();
                    } else if ("city".equals(parser.getName())) {
                        weatherInfo = new WeatherInfo();
                        String idStr = parser.getAttributeValue(0);
                        Log.i(TAG, "getWeatherInfos: " + idStr);
                        weatherInfo.setId(Integer.parseInt(idStr));
                    } else if ("temp".equals(parser.getName())) {
                        //parset.nextText();得到该tag结点中的内容
                        String temp = parser.nextText();
                        weatherInfo.setTemp(temp);
                    } else if ("weather".equals(parser.getName())) {
                        String weather = parser.nextText();
                        weatherInfo.setTemp(weather);
                    } else if ("name".equals(parser.getName())) {
                        String name = parser.nextText();
                        weatherInfo.setTemp(name);
                    } else if ("pm".equals(parser.getName())) {
                        String pm = parser.nextText();
                        weatherInfo.setTemp(pm);
                    } else if ("wind".equals(parser.getName())) {
                        String wind = parser.nextText();
                        weatherInfo.setTemp(wind);
                    }
                    break;
                //一个结点结束的标签
                case XmlPullParser.END_TAG:
                    //一个城市的信息处理完毕，city的标签结束
                    if ("city".equals(parser.getName())) {
                        //一个城市的信息已经处理完毕
                        weatherInfos.add(weatherInfo);
                        weatherInfo = null;
                    }
                    break;
            }
            //只要不解析到文档末尾、就解析下一个条目。会得到下一个结点的事件类型
            //注意，这个一定不能忘，否则会成为死循环
            type = parser.next();
        }
        return weatherInfos;
    }
}
