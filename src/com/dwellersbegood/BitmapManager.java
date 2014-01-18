package com.dwellersbegood;

import java.util.Vector;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class BitmapManager
{
	
	private static BitmapManager m_instance;
	
	public static BitmapManager getInstance()
	{
		if (m_instance == null)
			m_instance = new BitmapManager();
		return m_instance;
	}
	
	private BitmapManager()
	{
	}
	
	private final Vector<Bitmap> m_bitmapCollection = new Vector<Bitmap>();
	
	public static final int Background = 0;
	public static final int Floor = 1;
	public static final int Coin = 2;
	
	public void loadBitmaps(Resources res)
	{
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.loop_bg_cut));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.floorexample));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.coinexample));
	}
	
	public Bitmap scaleToSize(Bitmap sprite, int desiredWidth, int desiredHeight)
	{
		Matrix scale = new Matrix();
		float scaleWidth = ((float) desiredWidth) / (float) sprite.getWidth();
		float scaleHeight = ((float) desiredHeight) / (float) sprite.getHeight();
		
		scale.postScale(scaleWidth, scaleHeight);
		return Bitmap.createBitmap(sprite, 0, 0, sprite.getWidth(), sprite.getHeight(), scale, true);
	}
	
	public Bitmap getBitmap(int index)
	{
		return m_bitmapCollection.get(index);
	}
}
