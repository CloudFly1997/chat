package com.jack.chat.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.net.Socket;


public class ClientObj {
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public DataInputStream getDis() {
		return dis;
	}
	public void setDis(DataInputStream dis) {
		this.dis = dis;
	}
	public DataOutputStream getDos() {
		return dos;
	}
	public void setDos(DataOutputStream dos) {
		this.dos = dos;
	}
	public ClientObj(Socket socket) {
		this.socket = socket;
	}
}
