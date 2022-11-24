package com.bacchuserpshop.common.player;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;

public class MPlayer {
	
    private static final String TAG = "MediaPlayerDemo";	
	public MediaPlayer mMediaPlayer;

    private String voiceFileName;
	private Boolean isStart = false;
    
    Button startBtn;
    Button stopBtn;    
    
    public void MPlayer(Context context) {
    	
    	/*
    	mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
	    {
	        @Override
	        public void onCompletion(MediaPlayer mp)
	        {
	        	mMediaPlayer.stop();
	        	mMediaPlayer.release();
	            //invokeCallback($target, $method);
	        }
	    });
	    */
    }
    
	public void stopAudio() {
		if (isStart) mMediaPlayer.release();	
	}

	public void startAudio(Integer media) throws Exception {
		
		mMediaPlayer = new MediaPlayer();
		FileInputStream fis = new FileInputStream(voiceFileName);
		FileDescriptor fd = fis.getFD();
		mMediaPlayer.setDataSource(fd);
		mMediaPlayer.prepare();
		mMediaPlayer.start();

		isStart = true;
		/*
		try {		
			mMediaPlayer = new MediaPlayer();
			FileInputStream fis = new FileInputStream(voiceFileName);
			FileDescriptor fd = fis.getFD();
			mMediaPlayer.setDataSource(fd);
			mMediaPlayer.prepare();
			mMediaPlayer.start();

			isStart = true;
		} catch (Exception e) {
        	//tx.setText("error Playing audio... " + path);
            Log.e(TAG, "error: " + e.getMessage(), e);
        }
        */
	}

	public String getVoiceFileName() {
		return voiceFileName;
	}

	public void setVoiceFileName(String voiceFileName) {
		this.voiceFileName = voiceFileName;
	}
	
	public void PlayRingTone(Context context) {

	    try {
			Uri ringtoneUri = Uri.parse(voiceFileName.trim());
		    mMediaPlayer.setDataSource(context, ringtoneUri);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);		// RING, MUSIC, VOICE_CALL
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	
	// wav play
	public void PlayShortAudioFileViaAudioTrack(String filePath) throws IOException
	{
		// We keep temporarily filePath globally as we have only two sample sounds now..
		if (filePath==null)
		return;
	
		//Reading the file..
		byte[] byteData = null; 
		File file = null; 
		file = new File(filePath); // for ex. path= "/sdcard/samplesound.pcm" or "/sdcard/samplesound.wav"
		byteData = new byte[(int) file.length()];
		FileInputStream in = null;
		try {
			in = new FileInputStream( file );
			in.read( byteData );
			in.close(); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Set and push to audio track..
		int intSize = android.media.AudioTrack.getMinBufferSize(8000, AudioFormat.CHANNEL_CONFIGURATION_MONO,  AudioFormat.ENCODING_PCM_8BIT); 
		AudioTrack at = new AudioTrack(AudioManager.STREAM_MUSIC, 8000, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_8BIT, intSize, AudioTrack.MODE_STREAM); 
		if (at!=null) { 
			at.play();
			// Write the byte array to the track
			at.write(byteData, 0, byteData.length); 
			at.stop();
			at.release();
		}
		else
			Log.d("TCAudio", "audio track is not initialised ");
	}
	
	
	public void PlayShortAudioFileViaAudioTrack_SRC(String filePath) throws IOException
	{
		// We keep temporarily filePath globally as we have only two sample sounds now..
		if (filePath==null)
		return;
	
		//Reading the file..
		byte[] byteData = null; 
		File file = null; 
		file = new File(filePath); // for ex. path= "/sdcard/samplesound.pcm" or "/sdcard/samplesound.wav"
		byteData = new byte[(int) file.length()];
		FileInputStream in = null;
		try {
			in = new FileInputStream( file );
			in.read( byteData );
			in.close(); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Set and push to audio track..
		int intSize = android.media.AudioTrack.getMinBufferSize(8000, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_8BIT); 
		AudioTrack at = new AudioTrack(AudioManager.STREAM_MUSIC, 8000, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_8BIT, intSize, AudioTrack.MODE_STREAM); 
		if (at!=null) { 
			at.play();
			// Write the byte array to the track
			at.write(byteData, 0, byteData.length); 
			at.stop();
			at.release();
		}
		else
			Log.d("TCAudio", "audio track is not initialised ");
	}
	
	public void PlayAudioFileViaAudioTrack(String filePath) throws IOException
	{
	// We keep temporarily filePath globally as we have only two sample sounds now..
		if (filePath==null)
		return;
	
		int intSize = android.media.AudioTrack.getMinBufferSize(44100, AudioFormat.CHANNEL_CONFIGURATION_STEREO, AudioFormat.ENCODING_PCM_16BIT); 
		AudioTrack at = new AudioTrack(AudioManager.STREAM_MUSIC, 44100, AudioFormat.CHANNEL_CONFIGURATION_STEREO, AudioFormat.ENCODING_PCM_16BIT, intSize, AudioTrack.MODE_STREAM); 

		if (at==null){ 
			Log.d("TCAudio", "audio track is not initialised ");
			return; 
		}
	
		int count = 512 * 1024; // 512 kb
		//Reading the file..
		byte[] byteData = null; 
		File file = null; 
		file = new File(filePath);
	
		byteData = new byte[(int)count];
		FileInputStream in = null;
		try {
			in = new FileInputStream( file );
		} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int bytesread = 0, ret = 0;
		int size = (int) file.length();
		at.play();
		while (bytesread < size) { ret = in.read( byteData,0, count); 
			if (ret != -1) { 
				// Write the byte array to the track 
				at.write(byteData,0, ret); 
				bytesread += ret; 
			} 
			else break; 
		} 
		in.close(); 
		at.stop(); 
		at.release();
	}
}