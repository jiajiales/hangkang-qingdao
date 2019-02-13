package com.hotcomm.data.source.parse.amqp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZhenWenMessageParase {
	
	public static Data parse(String message) {
		ObjectMapper mapper = new ObjectMapper();
		Data data = null;
		try {
			DataParseVO vo = mapper.readValue(message, DataParseVO.class);
			String devEui = vo.getExtra().getDevEUI();
			String code = devEui==null||devEui.trim().length()==0?vo.getMacAddr():devEui;
			data = new Data(code, vo.getExtra().getFrameCnt().intValue(), message);
			data.setGwip(vo.getExtra().getGwip());
			data.setCoreData(vo.getData());
			data.setMac(vo.getMacAddr());
		} catch (IOException e) {
			String uno = UUID.randomUUID().toString();
			log.info("{}-数据解析失败,数据内容:{}",uno,message);
			log.error(uno,e);
		}
		return data;
	}
	
	@lombok.Data
	@EqualsAndHashCode(callSuper = false)
	@NoArgsConstructor
	public static class Data {
		private String code;
		private Integer frameCnt;
		private String gwip;
		private String message;
		private String coreData;
		private String mac;
		
		public Data(String code, Integer frameCnt, String message) {
			super();
			this.code = code;
			this.frameCnt = frameCnt;
			this.message = message;
		}
	}
	
	@lombok.Data
	@EqualsAndHashCode(callSuper=false)
	@NoArgsConstructor
	static class DataParseVO{
		private String id;
		private String macAddr;		
		private String data;		
		private String recv;		
		private Extra extra;		
		private String pub;		
		private String dfId;
	}
	
	@lombok.Data
	@EqualsAndHashCode(callSuper=false)
	@NoArgsConstructor
	static class Extra  {
		
		private String commsysType;
		private Double rssi;
		private Double snr;
		private Double frameCnt;
		private String gwid;
		private String gwip;
		private String channel;
		private Double sf;
		private Double fport;
		private String devEUI;
		private String repeater;
		private Integer systype;
		private Integer snr_min;
		private Integer snr_max;
		
	}
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("AppSKey", "100101");
		data.put("NwkSKey", "210210");
		data.put("network_type", "ABP");
		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(data);
		System.out.println(str);
//		String message  = "{\"id\":\"1526960579827-opH1IDBt6UaEny9J38AXGC6k\",\"macAddr\":\"0000000031000003\",\"data\":\"0103012409d80081000000\",\"recv\":\"2018-05-22T03:42:59.000Z\",\"extra\":{\"commsysType\":\"lora\",\"rssi\":-73,\"snr\":15.8,\"frameCnt\":4426,\"gwid\":\"00001c497be69310\",\"gwip\":\"192.168.0.100\",\"channel\":471500000,\"sf\":12,\"fport\":6},\"pub\":\"2018-05-22T03:42:59.830Z\"}";
//		//Data data = parse(message);
//		//System.out.println(data);
//		String eui = "{\"id\":\"1528100121187-uPlGdDrKBKDva1PQdTnaBJKM\",\"macAddr\":\"0000000000112233\",\"data\":\"b210001b0000e1\",\"recv\":\"2018-06-04T08:15:21.000Z\",\"extra\":{\"commsysType\":\"lora\",\"rssi\":-82,\"snr\":23,\"frameCnt\":1,\"gwid\":\"00001c497bca5b10\",\"gwip\":\"192.168.1.107\",\"channel\":470300000,\"sf\":12,\"fport\":1,\"devEUI\":\"ff00751000002291\"},\"pub\":\"2018-06-04T08:15:21.190Z\"}";
//		Data data = parse(eui);
//		System.out.println(data);
//		//System.out.println(eui.contains("macAddr"));
//		//ObjectMapper mapper = new ObjectMapper();
//		//Map<String,Object> p1 = mapper.readValue(eui, new TypeReference<Map<String,Object>>(){});
//		//System.out.println(p1);
//		//System.out.println(p1.get("extra"));
	}
	
}
