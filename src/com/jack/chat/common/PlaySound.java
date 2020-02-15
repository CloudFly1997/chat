package com.jack.chat.common;

import sun.audio.*;

import java.io.IOException;


public class PlaySound {
	public void playSoundWhenReceiveMessage(){
		try {
			AudioStream as = new AudioStream(this.getClass().getResourceAsStream("../../../../resource/泡泡音.wav"));
			AudioPlayer.player.start(as);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
