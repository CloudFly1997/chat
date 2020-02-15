package com.jack.chat.common;

import java.util.HashMap;
import java.util.Map;

public class ClientMap {
	private static Map<String, ClientObj> clientMap = new HashMap<String, ClientObj>();
	private ClientMap() {
		
	}
	
	public static Map<String, ClientObj> getClientMap() {
		return clientMap;
	}
}
