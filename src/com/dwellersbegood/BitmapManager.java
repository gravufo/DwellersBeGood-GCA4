package com.dwellersbegood;

import java.util.Vector;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

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
	public static final int Floor3 = 4;
	public static final int Floor4 = 5;
	public static final int Floor5 = 6;
	public static final int Floor6 = 7;
	public static final int Floor7 = 8;
	public static final int Coin0 = 9;
	public static final int HoleBeginning = 10;
	public static final int HoleEnding = 11;
	public static final int HoleMiddle = 12;
	public static final int Platform0 = 13;
	public static final int Platform1 = 14;
	public static final int Laser2 = 15;
	public static final int Enemy0 = 16;
	public static final int Enemy1 = 17;
	public static final int Enemy2 = 18;
	public static final int Rock0 = 19;
	public static final int Rock1 = 20;
	public static final int Rock2 = 21;
	public static final int MortStatue = 22;
	public static final int Coin1 = 23;
	public static final int Coin2 = 24;
	public static final int Torch1 = 25;
	public static final int Torch2 = 26;
	
	public void loadBitmaps(Resources res, int width, int height)
	{
		m_bitmapCollection.add(scaleToSizeByHeight(BitmapFactory.decodeResource(res, R.drawable.loop_bg_cut),1,height));
		
		/*Bitmap plate = BitmapFactory.decodeResource(res, R.drawable.plateforme1_2);
		float ratio = getFloorScaling(plate, (float)0.1, width);
		m_bitmapCollection.add(scaleByPercentage(plate,ratio));*/
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.plateforme1_2));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.plateforme2));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.plateforme3));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.plateforme4));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.plateforme5));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.plateforme6));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.plateforme7));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.plateforme8));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.coin));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.hole_beginning));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.hole_end));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.hole));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.plateforme_flottante1));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.plateforme_flottante2));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.anim_laser2));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.ennemi1));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.ennemi2));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.ennemi3));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.roche1));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.roche2));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.roche3));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.anim_mort_statue));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.sac));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.gem));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.torche1));
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.torche2));
		Log.d("BitmapManager", "Loaded all bitmap");
	}
	
	private Bitmap scaleToSizeByWidth(Bitmap sprite, float widthPercentage, int screenWidth)
	{
		Matrix matrix = new Matrix();
		float scale = ((float) screenWidth*widthPercentage / (float) sprite.getWidth());
		
		matrix.postScale(scale, scale);
		return Bitmap.createBitmap(sprite, 0, 0, sprite.getWidth(), sprite.getHeight(), matrix, true);
	}
	
	private Bitmap scaleToSizeByHeight(Bitmap sprite, float heightPercentage, int screenHeight)
	{
		Matrix matrix = new Matrix();
		float scale = ((float) screenHeight*heightPercentage / (float) sprite.getHeight());
		
		matrix.postScale(scale, scale);
		return Bitmap.createBitmap(sprite, 0, 0, sprite.getWidth(), sprite.getHeight(), matrix, true);
	}
	
	private Bitmap scaleByPercentage(Bitmap sprite, float scale)
	{
		Matrix matrix = new Matrix();
		
		matrix.postScale(scale, scale);
		return Bitmap.createBitmap(sprite, 0, 0, sprite.getWidth(), sprite.getHeight(), matrix, true);
	}
	
	private float getFloorScaling(Bitmap sprite, float widthPercentage, int screenWidth)
	{
		return ((float) screenWidth*widthPercentage / (float) sprite.getWidth());
	}
	
	public Bitmap getBitmap(int index)
	{
		return m_bitmapCollection.get(index);
	}
}
