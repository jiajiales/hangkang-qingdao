package com.hotcomm.prevention.service.datashow;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotcomm.prevention.bean.mysql.datashow.vo.WeatherVo;
import com.hotcomm.prevention.config.RedisHelper;
import com.hotcomm.prevention.utils.HttpUtils;
import com.hotcomm.prevention.utils.JSONUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class weatherServiceImpl implements weatherService {

	private String host = "http://api.shujuzhihui.cn";
	private String path = "/api/weather/area";
	private String method = "POST";
//	private String appcode = "36a4b3477a8843298cab905a0e777746";
//	测试专用
//	private String appcode ="79ea8d1b55aa4af8a6090129ecc02cec";
	private String appcode="e5a1038f45634b5faa02e28ddda2203c";
	@Autowired
	private RedisHelper redis;
	@Override
	public WeatherVo selectTheWeatherByCityName(String name) {
		if(redis.get(name)!=null && !redis.get(name).equals("{}")) {
			 String string = redis.get(name);
			 return JSONUtil.toBean(string, WeatherVo.class);
		}else{
			System.out.println(redis.get(name));
			Map<String, String> headers = new HashMap<String, String>();
			// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE
			// 83359fd73fe94948385f570e3c139105
			headers.put("Authorization", "appKey " + appcode);
			Map<String, String> querys = new HashMap<String, String>();
			querys.put("appKey", appcode);
			querys.put("area", name);
			WeatherVo weatherVo=new WeatherVo(); 
			try {
				
				/**
				 * 重要提示如下: HttpUtils请从
				 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/
				 * src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java 下载
				 *
				 * 相应的依赖请参照
				 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/
				 * pom.xml
				 */
				HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
				// 获取response的body
//			System.out.println(EntityUtils.toString(response.getEntity()));
				String str = EntityUtils.toString(response.getEntity());
				
				Date date = new Date();
				SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
				String currSun = dateFm.format(date);
				
				
				
				
				
				
				JSONObject fromObject = JSONObject.fromObject(JSONObject.fromObject(str).get("RESULT"));
				weatherVo.setCity(name);
				JSONObject today=JSONObject.fromObject(JSONObject.fromObject(fromObject.get("weatherInfo")).get("today"));
				JSONObject real=JSONObject.fromObject(JSONObject.fromObject(fromObject.get("weatherInfo")).get("real"));
				weatherVo.setWeather((String) real.get("weather"));
				weatherVo.setHumidity(real.get("humidity").toString());
				weatherVo.setWeek(currSun);
				weatherVo.setDate((String)  today.get("date"));
				weatherVo.setTemp(real.get("tem").toString());
				weatherVo.setWindpower((String)  real.get("wdp"));
				weatherVo.setWinddirect((String)  real.get("wdd"));
				weatherVo.setQuality((String) real.get("aq"));
				
//				weatherVo.setTemplow((String)  fromObject.get("templow"));
//				weatherVo.setTemphigh((String)  fromObject.get("temphigh"));
				
//				Object obArray = JSONArray.fromObject(fromObject.get("daily")).get(0);
//				weatherVo.setSunrise((String) JSONObject.fromObject(obArray).get("sunrise"));
//				weatherVo.setSunset((String) JSONObject.fromObject(obArray).get("sunset"));
				redis.set(name, JSONUtil.toJson(weatherVo), 10*60);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return weatherVo;
		}
	}

}
