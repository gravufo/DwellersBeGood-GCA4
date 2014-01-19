package com.dwellersbegood;

import java.util.HashMap;

import android.content.res.Resources;
import android.media.MediaPlayer;
import android.util.Log;

public class SoundManager
{
	private static SoundManager m_instance;
	private static HashMap<Integer, MediaPlayer> m_sfxCollection = new HashMap<>();
	public static final int MENU_LOOP = 0;
	public static final int BACKGROUND_MUSIC = 1;
	public static final int FIRE_DEATH = 2;
	public static final int GEM = 3;
	public static final int GOLD_BAG = 4;
	public static final int GOLD_COIN = 5;
	public static final int JUMP_FALL = 6;
	public static final int LASER_EVIL = 7;
	public static final int LASER_GOOD = 8;
	public static final int PLAYER_RUNNING = 9;
	public static final int STATUE_EXPLOSION = 10;
	
	public static SoundManager getInstance()
	{
		if (m_instance == null)
			m_instance = new SoundManager();
		return m_instance;
	}
	
	private SoundManager()
	{
	}
	
	public void loadSfx(Resources res)
	{
		MediaPlayer media = MediaPlayer.create(MainActivity.getContext(), R.raw.menu_music);
		media.setLooping(true);
		m_sfxCollection.put(MENU_LOOP, media);
		
		MediaPlayer media1 = MediaPlayer.create(MainActivity.getContext(), R.raw.background_music);
		media1.setLooping(true);
		m_sfxCollection.put(BACKGROUND_MUSIC, media1);
		
		MediaPlayer media2 = MediaPlayer.create(MainActivity.getContext(), R.raw.fire_death);
		media2.setLooping(false);
		m_sfxCollection.put(FIRE_DEATH, media2);
		
		MediaPlayer media3 = MediaPlayer.create(MainActivity.getContext(), R.raw.gem);
		media3.setLooping(false);
		m_sfxCollection.put(GEM, media3);
		
		MediaPlayer media4 = MediaPlayer.create(MainActivity.getContext(), R.raw.gold_bag);
		media4.setLooping(false);
		m_sfxCollection.put(GOLD_BAG, media4);
		
		MediaPlayer media5 = MediaPlayer.create(MainActivity.getContext(), R.raw.gold_coin);
		media5.setLooping(false);
		m_sfxCollection.put(GOLD_COIN, media5);
		
		MediaPlayer media6 = MediaPlayer.create(MainActivity.getContext(), R.raw.jump_fall);
		media6.setLooping(false);
		media6.setVolume((float) 0.2, (float) 0.2);
		m_sfxCollection.put(JUMP_FALL, media6);
		
		MediaPlayer media7 = MediaPlayer.create(MainActivity.getContext(), R.raw.laser_evil);
		media7.setLooping(false);
		m_sfxCollection.put(LASER_EVIL, media7);
		
		MediaPlayer media8 = MediaPlayer.create(MainActivity.getContext(), R.raw.laser_good);
		media8.setLooping(false);
		m_sfxCollection.put(LASER_GOOD, media8);
		
		MediaPlayer media9 = MediaPlayer.create(MainActivity.getContext(), R.raw.running);
		media9.setLooping(false);
		m_sfxCollection.put(PLAYER_RUNNING, media9);
		
		MediaPlayer media10 = MediaPlayer.create(MainActivity.getContext(), R.raw.statue_explosion);
		media10.setLooping(false);
		m_sfxCollection.put(STATUE_EXPLOSION, media10);
		
		Log.d("BitmapManager", "Loaded all sound effects");
	}
	
	public MediaPlayer getPlayer(int playerID)
	{
		return m_sfxCollection.get(playerID);
	}
}
