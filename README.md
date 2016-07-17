# 用XmlPullParser解析XML文档
## 注意
此程序不能成功运行，原因为以下代码中getWeatherInfos中参数为空  

    List<WeatherInfo> infos = WeatherService.getWeatherInfos(MainActivity.class.
                    getClassLoader().getResourceAsStream("weather.xml"));
## 7月17日更新
在app根目录下创建assets源文件夹，把weather.xml文件放在里面,调用以下方法可以解析XML文件

	AssetManager am = this.getAssets();
    InputStream is = am.open("weather.xml");
    List<WeatherInfo> infos = WeatherService.getWeatherInfos(is);