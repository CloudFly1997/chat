package com.jack.chat.util;

import com.jack.chat.pojo.Message;

public class MessageHandle {
	private static String splitCode = "@AA@";

	public static String afterHandleMessage(String from, String to, String date, String content) {

		String result = from + splitCode + to + splitCode + date + splitCode + content;
		return result;
	}


	public static String messageShow(String message) {
		String from = message.split(splitCode)[0];
		String date = message.split(splitCode)[2];
		String content = message.split(splitCode)[3];
		message = date + "  " + from + ":\n" + content + "\n\n";
		return content;
	}
	public static String messageShow(String date,String from,String content) {
		return date + "  " + from + ":\n" + content + "\n\n";
	}
	
	public static Message getMessageObject(String message) {
		String from = message.split(splitCode)[0];
		String to = message.split(splitCode)[1];
		String date = message.split(splitCode)[2];
		String content = message.split(splitCode)[3];
		Message messageObj = new Message(from, to, content, date);
		return messageObj;
	}

}
