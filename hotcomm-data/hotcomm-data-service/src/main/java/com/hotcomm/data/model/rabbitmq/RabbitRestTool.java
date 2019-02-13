package com.hotcomm.data.model.rabbitmq;

import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotcomm.data.model.rabbitmq.common.QueueCustomer;
import com.hotcomm.data.utils.HttpApiResult;
import com.hotcomm.framework.web.exception.HKException;

@Component
public class RabbitRestTool {

	@Value("${spring.rabbitmq.send.host}")
	public String host="119.29.184.139";
	
	@Value("${spring.rabbitmq.send.username}")
	public String adminName = "guest";
	
	@Value("${spring.rabbitmq.send.password}")
	public String adminPassword = "guest";
	
	
	public QueueCustomer getQueueMsg(String vhostname,String queuename)throws HKException {
		try {
			HttpApiResult result = getQueueByVhost(vhostname);
			if(result.getStatusCode()!=200) 
				throw new HKException("-1", "获取虚拟机队列信息失败");
			String responseStr = result.getResponseStr();
			ObjectMapper mapper = new ObjectMapper();
			List<Map<String, Object>> queues = mapper.readValue(responseStr.getBytes(), new TypeReference<List<Map<String, Object>>>() {});
			for(Map<String, Object> queue:queues) {
				String  name = queue.get("name").toString();
				if(queuename.equals(name)) {
					Integer messages = Integer.parseInt(queue.get("messages").toString());
					Integer customers = Integer .parseInt(queue.get("consumers").toString());
					return new QueueCustomer(messages, customers);
				}
			}
		} catch (Exception e) {
			throw new HKException("-1", "获取虚拟机队列信息失败");
		}
		throw new HKException("-1", "获取虚拟机队列信息失败");
	}
	
	
	public boolean existsVhost(String vhostname)throws HKException{
		try {
			HttpApiResult result = this.getVhosts();
			if(result.getStatusCode()!=200) 
				throw new RuntimeException();
			String responseStr = result.getResponseStr();
			ObjectMapper mapper = new ObjectMapper();
			List<Map<String, Object>> vhosts = mapper.readValue(responseStr.getBytes(), new TypeReference<List<Map<String, Object>>>() {});
			for(Map<String, Object> vhost:vhosts) {
				String name = vhost.get("name").toString();
				if(vhostname.equals(name)) 
					return true;
			}
		} catch (Exception e) {
			throw new HKException("-1", "获取虚拟机队列信息失败");
		}
		return false;
	}
	
	public boolean existsUser(String username)throws HKException{
		try {
			HttpApiResult result = this.getUsers();
			if(result.getStatusCode()!=200) 
				throw new RuntimeException();
			String responseStr = result.getResponseStr();
			ObjectMapper mapper = new ObjectMapper();
			List<Map<String, Object>> users = mapper.readValue(responseStr.getBytes(), new TypeReference<List<Map<String, Object>>>(){});
			for(Map<String, Object> vhost:users) {
				String name = vhost.get("name").toString();
				if(name.equals("username")) 
					return true;
			}
		} catch (Exception e) {
			throw new HKException("-1", "获取虚拟机队列信息失败");
		}
		return false;
	}
	
	public HttpApiResult getQueueByVhost(String vhostname)throws Exception {
		vhostname = vhostname.contains("/") ? vhostname.replace("/", "") : vhostname;
		return getResult("http://${host}:15672/api/queues/%2F${vhostname}".replace("${host}", host).replace("${vhostname}",vhostname));
	}
	
	public HttpApiResult getUsers()throws Exception{
		return getResult("http://${host}:15672/api/users".replace("${host}", host));
	}
	
	public HttpApiResult getVhosts() throws Exception{
		return getResult("http://${host}:15672/api/vhosts".replace("${host}", host));
	}
	
	public HttpApiResult vhostPermissionsToUser(String vhostname,String username,String tags)throws HKException{
		try {
			vhostname = vhostname.contains("/") ? vhostname.replace("/", "") : vhostname;
			String url = "http://${host}:15672/api/permissions/%2F${vhostname}/${username}".replace("${host}", host).replace("${vhostname}",vhostname).replace("${username}", username);
			if(tags.equals("admin")) {
				return putResult(url, "{\"configure\":\".*\",\"write\":\".*\",\"read\":\".*\"}");
			}else{
				return putResult(url, "{\"configure\":\"\",\"write\":\"\",\"read\":\".*\"}");
			}
		} catch (Exception e) {
			throw new HKException("-1", "获取虚拟机队列信息失败");
		}
	}
	
	public HttpApiResult addRabbitVhost(String vhostname)throws HKException {
		try {
			vhostname = vhostname.contains("/") ? vhostname.replace("/", "") : vhostname;
			String url = "http://${host}:15672/api/vhosts/%2F${vhostname}".replace("${host}", host).replace("${vhostname}",vhostname);
			return putResult(url, null);
		} catch (Exception e) {
			throw new HKException("-1", "获取虚拟机队列信息失败");
		}
	}
	
	public HttpApiResult addRabbitAccount(String userName,String password)throws HKException  {
		try {
			String url = "http://${host}:15672/api/users/${username}".replace("${host}", host).replace("${username}", userName);
			String paramsJson = "{\"password\":\"${password}\",\"tags\":\"None\"}".replace("${password}", password);
			return putResult(url, paramsJson);
		} catch (Exception e) {
			throw new HKException("-1", "获取虚拟机队列信息失败");
		}
	}
	
	public HttpApiResult deleteVhost(String vhostname) {
		try {
			vhostname = vhostname.contains("/") ? vhostname.replace("/", "") : vhostname;
			String url = "http://${host}:15672/api/vhosts/%2F${vhostname}".replace("${host}", host).replace("${vhostname}",vhostname);
			return deleteResult(url);
		} catch (Exception e) {
			throw new HKException("-1", "获取虚拟机队列信息失败");
		}
	}
	
	public HttpApiResult deleteRabbitAccount(String userName) {
		try {
			String url = "http://${host}:15672/api/users/${username}".replace("${host}", host).replace("${username}", userName);
			return deleteResult(url);
		} catch (Exception e) {
			throw new HKException("-1", "获取虚拟机队列信息失败");
		}
	}
	
	private HttpApiResult putResult(String url,String paramsJson)throws Exception{
		HttpApiResult result = new HttpApiResult();
		HttpPut method = new HttpPut(url);
		try(CloseableHttpClient client = HttpClients.custom().build()){
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000).setSocketTimeout(5000).build();
			method.setConfig(requestConfig);
			method.addHeader("Content-Type", "application/json");
			String authorization = "${adminName}:${adminPassword}".replace("${adminName}", adminName).replace("${adminPassword}", adminPassword);
			String encoding = DatatypeConverter.printBase64Binary(authorization.getBytes("UTF-8"));
			method.addHeader("Authorization","Basic "+encoding);
			if(paramsJson!=null&&paramsJson.trim().length()>0) {
				method.setEntity(new StringEntity(paramsJson,ContentType.APPLICATION_JSON));
			}
			HttpResponse response = client.execute(method);
			int statusCode = response.getStatusLine().getStatusCode();
			result.setStatusCode(statusCode);
			if(statusCode==204) {
				return result;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private HttpApiResult getResult(String url)throws Exception{
		HttpApiResult result = new HttpApiResult();
		HttpGet method = new HttpGet(url);
		try(CloseableHttpClient client = HttpClients.custom().build()){
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000).setSocketTimeout(5000).build();
			method.setConfig(requestConfig);
			method.addHeader("Content-Type", "application/json");
			String authorization = "${adminName}:${adminPassword}".replace("${adminName}", adminName).replace("${adminPassword}", adminPassword);
			String encoding = DatatypeConverter.printBase64Binary(authorization.getBytes("UTF-8"));
			method.addHeader("Authorization","Basic "+encoding);
			HttpResponse response = client.execute(method);
			int statusCode = response.getStatusLine().getStatusCode();
			result.setStatusCode(statusCode);
			if(statusCode==200) {
				HttpEntity entity = response.getEntity();
				String responseStr = EntityUtils.toString(entity);
				result.setResponseStr(responseStr);
				return result;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private HttpApiResult deleteResult(String url) {
		HttpApiResult result = new HttpApiResult();
		HttpDelete method = new HttpDelete(url);
		try(CloseableHttpClient client = HttpClients.custom().build()){
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000).setSocketTimeout(5000).build();
			method.setConfig(requestConfig);
			method.addHeader("Content-Type", "application/json");
			String authorization = "${adminName}:${adminPassword}".replace("${adminName}", adminName).replace("${adminPassword}", adminPassword);
			String encoding = DatatypeConverter.printBase64Binary(authorization.getBytes("UTF-8"));
			method.addHeader("Authorization","Basic "+encoding);
			HttpResponse response = client.execute(method);
			int statusCode = response.getStatusLine().getStatusCode();
			result.setStatusCode(statusCode);
			if(statusCode<300) 
				return result;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

}
