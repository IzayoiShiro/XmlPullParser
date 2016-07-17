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
        XmlPullParser parser = Xml.newPullParser();
        //初始化解析器，第一个参数代表含xml的数据
        parser.setInput(is, "utf-8");
        List<WeatherInfo> weatherInfos = null;
        WeatherInfo weatherInfo = null;
        //得到当前事件的类型
        int type = parser.getEventType();
        //END_DOCUMENT文档结束标签
        Log.i(TAG, "getWeatherInfos: "
                +XmlPullParser.START_DOCUMENT+","
                +XmlPullParser.START_TAG+","
                +XmlPullParser.END_TAG+","
                +XmlPullParser.END_DOCUMENT);
        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                //一个结点的开始标签
                case XmlPullParser.START_TAG:
                    //解析到全局开始的标签infos根结点
                    if ("infos".equals(parser.getName())) {
                        Log.i(TAG, "getWeatherInfos: infos的type值"+type);
                        weatherInfos = new ArrayList<WeatherInfo>();
                    } else if ("city".equals(parser.getName())) {
                        Log.i(TAG, "getWeatherInfos: city的type值"+type);
                        weatherInfo = new WeatherInfo();
                        String idStr = parser.getAttributeValue(0);
                        weatherInfo.setId(Integer.parseInt(idStr));
                    } else if ("temp".equals(parser.getName())) {
                        Log.i(TAG, "getWeatherInfos: temp的type值"+type);
                        //parset.nextText();得到该tag结点中的内容
                        String temp = parser.nextText();
                        weatherInfo.setTemp(temp);
                    } else if ("weather".equals(parser.getName())) {
                        Log.i(TAG, "getWeatherInfos: weather的type值"+type);
                        String weather = parser.nextText();
                        weatherInfo.setWeather(weather);
                    } else if ("name".equals(parser.getName())) {
                        Log.i(TAG, "getWeatherInfos: name的type值"+type);
                        String name = parser.nextText();
                        weatherInfo.setName(name);
                    } else if ("pm".equals(parser.getName())) {
                        Log.i(TAG, "getWeatherInfos: pm的type值"+type);
                        String pm = parser.nextText();
                        weatherInfo.setPm(pm);
                    } else if ("wind".equals(parser.getName())) {
                        Log.i(TAG, "getWeatherInfos: wind的type值"+type);
                        String wind = parser.nextText();
                        weatherInfo.setWind(wind);
                    }
                    break;
                //一个结点结束的标签
                case XmlPullParser.END_TAG:
                    //一个城市的信息处理完毕，city的标签结束
                    if ("city".equals(parser.getName())) {
                        Log.i(TAG, "getWeatherInfos: wind的type值"+type);
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
