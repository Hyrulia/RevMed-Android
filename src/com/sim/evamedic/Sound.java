package com.sim.evamedic;

import com.sim.evamedic.R;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;


public class Sound {
	
	public static void play(int resource) {
		if(MyApp.isSoundEnable()) {
			MediaPlayer mp = MediaPlayer.create(MyApp.getContext(), resource);
             mp.setOnCompletionListener(new OnCompletionListener() {

                 @Override
                 public void onCompletion(MediaPlayer mp) {
                     mp.release();
                 }
             });   
             mp.start();
		}
	}
		
	public static void correct() {	
		Sound.play(R.raw.correct);
	}
	
	public static void wrong() {
		Sound.play(R.raw.wrong);
	}
}
