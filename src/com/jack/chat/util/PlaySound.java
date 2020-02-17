package com.jack.chat.util;

import sun.audio.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;


public class PlaySound {
	public void playSoundWhenReceiveMessage(){
		try {

			AudioStream as = new AudioStream(new FileInputStream(new File("/sound/泡泡音.wav")));
			AudioPlayer.player.start(as);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new PlaySound().playSoundWhenReceiveMessage();
	}
}
