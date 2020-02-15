package com.jack.chat.common;

import java.io.Serializable;

public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fromUser;
	private String toUser;
	private String messageContent;
	private String dateTime;
	
	public Message(String fromUser, String toUser, String messageContent, String dateTime) {
		super();
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.messageContent = messageContent;
		this.dateTime = dateTime;
	}
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public String getmessageContent() {
		return messageContent;
	}
	public void setmessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	@Override
	public String toString() {
		return "Message [fromUser=" + fromUser + ", toUser=" + toUser + ", messageContent=" + messageContent
				+ ", dateTime=" + dateTime + "]";
	}
}
