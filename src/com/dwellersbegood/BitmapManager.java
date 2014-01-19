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
	public static final int Floor0 = 1;
	public static final int Floor1 = 2;
	public static final int Floor2 = 3;
	public static final int Coin = 4;
	public static final int HoleBeginning = 5;
	public static final int HoleEnding = 6;
	public static final int HoleMiddle = 7;
	public static final int Platform0 = 8;
	public static final int Platform1 = 9;
	public static final int Fire = 10;
	public static final int Rock = 11;
	public static final int Enemy = 12;
	
	public void loadBitmaps(Resources res)
	{
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.loop_bg_cut));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.floorexample));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.floorexample2));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.floorexample3));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.coinexample));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.holebeginning));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.holeending));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.holemiddle));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.platform1));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.platform2));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.fireexample));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.rockexample));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.enemyexample));
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
