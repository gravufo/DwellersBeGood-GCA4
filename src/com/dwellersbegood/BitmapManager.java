package com.dwellersbegood;

import java.util.Vector;

import android.graphics.Bitmap;

public class BitmapManager {
	
	private BitmapManager m_instance;
	public BitmapManager Instance()
	{
		if(m_instance == null)
			m_instance = new BitmapManager();
		return m_instance;
	}

	public BitmapManager()
	{
		loadBitmaps();
	}
	
	private Vector<Bitmap> m_bitmapCollection = new Vector<Bitmap>();
	
	public static final int Background = 0;
	public static final int PlayerSprite = 1;
	public static final int EnemySprite = 2;
	public static final int Grass = 3;
	
	
	private boolean loadBitmaps()
	{
		return true;
	}
	
	public Bitmap getBitmap(int index)
	{
		return m_bitmapCollection.get(index);
	}
}
