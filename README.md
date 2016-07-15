# 用XmlPullParser解析XML文档
## 注意
此程序不能成功运行，原因为以下代码中getWeatherInfos中参数为空  

    List<WeatherInfo> infos = WeatherService.getWeatherInfos(MainActivity.class.
                    getClassLoader().getResourceAsStream("weather.xml"));