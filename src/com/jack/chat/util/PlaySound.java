package com.jack.chat.util;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * @author jack
 */
public class PlaySound {
	public static void playSoundWhenReceiveMessage(){

		try {
			FileInputStream fileInputStream = new FileInputStream(new File(System.getProperty("user.home") + "\\chat" +
					"\\sound\\泡泡音.wav"));
			AudioStream as = new AudioStream(fileInputStream);

			AudioPlayer.player.start(as);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
