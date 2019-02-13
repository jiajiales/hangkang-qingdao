package com.hot.analysis.service.ywj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hot.analysis.bean.sj.LKTSJAlarmList;
import com.hot.analysis.bean.ywj.LKTYWJWeatherInfo;
import com.hot.analysis.bean.ywj.LKTYWJselectFlipNum;
import com.hot.analysis.db.ywj.LKTYWJMapper;
import com.hot.analysis.utils.HttpUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.hot.analysis.bean.ywj.YWJAlarmType;

@Transactional
@Service
public class LKTYWJServiceImpl implements LKTYWJService {

	@Autowired
	private LKTYWJMapper lktywjmapper;
	/**
	 * 天气预报查询接口的请求参数（4个）
	 */
	private final static String HOST = "http://jisutianqi.market.alicloudapi.com";
	private final static String PATH = "/weather/query";
	private final static String METHOD = "GET";
	private final static String APPCODE = "dbdd915728814ed99cc9866da4593079";

	@Override
	public List<LKTYWJselectFlipNum> LKTYWJselectFlipNum(String startTime, String endTime, Integer moduleid,
			Integer userid) {
		return lktywjmapper.LKTYWJselectFlipNum(startTime, endTime, moduleid, userid);
	}

	@Override
	public List<LKTYWJWeatherInfo> selectWeatherInfoByCity(String city) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "APPCODE " + APPCODE);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("city", city);
		String jsonStr = "";
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
			HttpResponse response = HttpUtils.doGet(HOST, PATH, METHOD, headers, querys);
			jsonStr = EntityUtils.toString(response.getEntity());
			// System.out.println(response.toString());
			// 获取response的body
			// System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parseJsonStrOfWeatherInfo(jsonStr);
	}

	@Override
	public List<LKTYWJWeatherInfo> selectWeatherInfoByIP(String ip) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "APPCODE " + APPCODE);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("ip", ip);
		String jsonStr = "";
		try {
			HttpResponse response = HttpUtils.doGet(HOST, PATH, METHOD, headers, querys);
			jsonStr = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parseJsonStrOfWeatherInfo(jsonStr);
	}

	/**
	 * 解析天气预报的JSON信息
	 */
	private List<LKTYWJWeatherInfo> parseJsonStrOfWeatherInfo(String jsonStr) {
		List<LKTYWJWeatherInfo> weatherInfoes = new ArrayList<LKTYWJWeatherInfo>();
		JSONObject status = JSONObject.fromObject(jsonStr);
		System.out.println(jsonStr);
		if (jsonStr.isEmpty() || jsonStr.equals("[]") || !status.getString("status").equals("0")) {
			return weatherInfoes;
		}
		JSONObject jsonObject = JSONObject.fromObject(JSONObject.fromObject(jsonStr).get("result"));
		// 查询今天的天气预报信息
		LKTYWJWeatherInfo todayWeatherInfo = new LKTYWJWeatherInfo();
		todayWeatherInfo.setCity(jsonObject.getString("city"));
		todayWeatherInfo.setDate(jsonObject.getString("date"));
		todayWeatherInfo.setWeek(jsonObject.getString("week"));
		todayWeatherInfo.setWeather(jsonObject.getString("weather"));
		todayWeatherInfo.setTemp(jsonObject.getInt("temp"));
		todayWeatherInfo.setTemplow(jsonObject.getInt("templow"));
		todayWeatherInfo.setTemphigh(jsonObject.getInt("temphigh"));
		todayWeatherInfo.setWinddirect(jsonObject.getString("winddirect"));
		todayWeatherInfo.setWindpower(jsonObject.getString("windpower"));
		todayWeatherInfo.setHumidity(jsonObject.getString("humidity"));
		weatherInfoes.add(todayWeatherInfo);
		JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("daily"));
		Object[] objs = jsonArray.toArray();
		int i = 0;
		for (Object obj : objs) {
			JSONObject jsonObj = JSONObject.fromObject(obj);
			String date = jsonObj.getString("date");
			// 此处忽略今天的天气预报信息，因为上面已经查询过了
			if (date.equals(todayWeatherInfo.getDate())) {
				continue;
			}
			LKTYWJWeatherInfo weatherInfo = new LKTYWJWeatherInfo();
			JSONObject dayJsonObj = JSONObject.fromObject(jsonObj.getString("day"));
			JSONObject nightJsonObj = JSONObject.fromObject(jsonObj.getString("night"));
			weatherInfo.setCity(jsonObject.getString("city"));
			weatherInfo.setDate(date);
			weatherInfo.setWeek(jsonObj.getString("week"));
			weatherInfo.setWeather(dayJsonObj.getString("weather"));
			weatherInfo.setTemp(0);
			weatherInfo.setTemplow(nightJsonObj.getInt("templow"));
			weatherInfo.setTemphigh(dayJsonObj.getInt("temphigh"));
			weatherInfo.setWinddirect(dayJsonObj.getString("winddirect"));
			weatherInfo.setWindpower(dayJsonObj.getString("windpower"));
			weatherInfo.setHumidity("0");
			weatherInfoes.add(weatherInfo);
			++i;

			// 只查询到明天和后天的天气预报信息
			if (i == 2) {
				break;
			}
		}

		return weatherInfoes;
	}

	@Override
	public List<LKTSJAlarmList> YWJAlarmType(Integer Time, Integer moduleID, Integer userID) {
		return lktywjmapper.YWJAlarmType(Time, moduleID, userID);
	}

}
