package com.jack.chat.util;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.IOException;
import java.io.InputStream;


/**
 * @author jack
 */
public class PlaySound {

	private static InputStream inputStream = PlaySound.class.getClassLoader().getResourceAsStream("sound/泡泡音" +
			".wav");
    public static void playSoundWhenReceiveMessage() {
        try {
            AudioStream as = new AudioStream(inputStream);
            AudioPlayer.player.start(as);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
