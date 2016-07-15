package com.example.xmlpullparser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    /*
     * 这是一个使用XmlPullParser解析XML文件的案例
     * 1. 通过调用Xml.newPullParser();得到一个XmlPullParser对象
     * 2. 通过parser.getEventType()获取到当前的事件类型
     * 3. 通过while循环判断当前操作事件类型是否为文档结束，是则跳出while循环
     * 4. while循环中通过switch语句判断当前事件类型是否为开始标签，是则获取该标签中的内容
     */

    private TextView select_city, select_weather, select_temp, select_wind, select_pm;
    private Map<String, String> map;
    private List<Map<String, String>> list;
    private String temp, weather, name, pm, wind;
    private ImageView icon;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        init();
        try {
            List<WeatherInfo> infos = WeatherService.getWeatherInfos(MainActivity.class.
                    getClassLoader().getResourceAsStream("weather.xml"));
            list = new ArrayList<Map<String, String>>();
            for (WeatherInfo info : infos) {
                map = new HashMap<String, String>();
                map.put("temp", info.getTemp());
                map.put("weather", info.getWeather());
                map.put("name", info.getName());
                map.put("pm", info.getPm());
                map.put("wind", info.getWind());
                Log.i(TAG, "init: " + info.getTemp());
                Log.i(TAG, "init: " + info.getWeather());
                Log.i(TAG, "init: " + info.getName());
                Log.i(TAG, "init: " + info.getPm());
                Log.i(TAG, "init: " + info.getWind());
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "onCreate: 文件解析失败");
            Toast.makeText(this, "文件解析失败", Toast.LENGTH_SHORT).show();
        }
        getMap(1, android.R.drawable.sym_def_app_icon);
    }

    private void init() {
        select_city = (TextView) findViewById(R.id.select_city);
        select_weather = (TextView) findViewById(R.id.select_weather);
        select_temp = (TextView) findViewById(R.id.temp);
        select_wind = (TextView) findViewById(R.id.wind);
        select_pm = (TextView) findViewById(R.id.pm);
        icon = (ImageView) findViewById(R.id.icon);
        findViewById(R.id.city_sh).setOnClickListener(this);
        findViewById(R.id.city_bj).setOnClickListener(this);
        findViewById(R.id.Harbin).setOnClickListener(this);
        //findViewById(R.id.city_sh).setAlpha(0);
        //findViewById(R.id.city_bj).setAlpha(0);
        //findViewById(R.id.Harbin).setAlpha(0);
    }

    private void getMap(int number, int iconNumber) {
        Map<String, String> bjMap = list.get(number);
        temp = bjMap.get("temp");
        weather = bjMap.get("weather");
        name = bjMap.get("name");
        pm = bjMap.get("pm");
        wind = bjMap.get("wind");
        select_city.setText(name);
        select_weather.setText(weather);
        select_temp.setText(temp);
        select_wind.setText(wind);
        select_pm.setText(pm);
        icon.setImageResource(iconNumber);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.city_sh:
                getMap(0, android.R.drawable.sym_def_app_icon);
                break;
            case R.id.city_bj:
                getMap(1, android.R.drawable.sym_def_app_icon);
                break;
            case R.id.Harbin:
                getMap(2, android.R.drawable.sym_def_app_icon);
                break;
        }
    }
}
