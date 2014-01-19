package com.dwellersbegood;

import java.util.ArrayList;

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
	
	private ArrayList<Bitmap> m_bitmapCollection = new ArrayList<>();
	
	public static final float FirstFloorWidth = (float) 0.2;
	public static final float CoinsHeight = (float) 0.12;
	public static final float FloatingPlatformsWidth = (float) 0.2;
	public static final float EnemyHeight = (float) 0.35;
	public static final float Rock1Width = (float) 0.15;
	public static final float Rock2Width = (float) 0.10;
	public static final float Rock3Width = (float) 0.20;
	public static final float Torch1Height = (float) 0.30;
	public static final float Torch2Width = (float) 0.30;
	public static final float FireAnimeHeight = (float) 0.15;
	public static final float Laser2AnimWidth = (float) 0.10 * 5;
	public static final float PlayerHeight = (float) 0.3;
	
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
	public static final int FireAnim = 27;
	public static final int PlayerRun = 28;
	public static final int PlayerJump = 29;
	
	public void loadBitmaps(Resources res, int width, int height)
	{
		m_bitmapCollection.add(scaleToSizeByHeight(BitmapFactory.decodeResource(res, R.drawable.loop_bg_color), 1, height));
		
		Bitmap plate = BitmapFactory.decodeResource(res, R.drawable.plateforme1_2);
		float ratio = getFloorScaling(plate, FirstFloorWidth, width);
		// //
		m_bitmapCollection.add(scaleByPercentage(BitmapFactory.decodeResource(res, R.drawable.plateforme1_2), ratio));
		m_bitmapCollection.add(scaleByPercentage(BitmapFactory.decodeResource(res, R.drawable.plateforme2), ratio));
		m_bitmapCollection.add(scaleByPercentage(BitmapFactory.decodeResource(res, R.drawable.plateforme3), ratio));
		m_bitmapCollection.add(scaleByPercentage(BitmapFactory.decodeResource(res, R.drawable.plateforme4), ratio));
		m_bitmapCollection.add(scaleByPercentage(BitmapFactory.decodeResource(res, R.drawable.plateforme5), ratio));
		m_bitmapCollection.add(scaleByPercentage(BitmapFactory.decodeResource(res, R.drawable.plateforme6), ratio));
		m_bitmapCollection.add(scaleByPercentage(BitmapFactory.decodeResource(res, R.drawable.plateforme7), ratio));
		m_bitmapCollection.add(scaleByPercentage(BitmapFactory.decodeResource(res, R.drawable.plateforme8), ratio));
		// //
		m_bitmapCollection.add(scaleToSizeByHeight(BitmapFactory.decodeResource(res, R.drawable.coin), CoinsHeight, height));
		// //
		m_bitmapCollection.add(scaleByPercentage(BitmapFactory.decodeResource(res, R.drawable.hole_beginning), ratio));
		m_bitmapCollection.add(scaleByPercentage(BitmapFactory.decodeResource(res, R.drawable.hole_end), ratio));
		m_bitmapCollection.add(scaleByPercentage(BitmapFactory.decodeResource(res, R.drawable.hole), ratio));
		// //
		m_bitmapCollection.add(scaleToSizeByWidth(BitmapFactory.decodeResource(res, R.drawable.plateforme_flottante1), FloatingPlatformsWidth, width));
		m_bitmapCollection.add(scaleToSizeByWidth(BitmapFactory.decodeResource(res, R.drawable.plateforme_flottante2), FloatingPlatformsWidth, width));
		// //
		m_bitmapCollection.add(BitmapFactory.decodeResource(res, R.drawable.anim_laser2));
		// /
		m_bitmapCollection.add(scaleToSizeByHeight(BitmapFactory.decodeResource(res, R.drawable.ennemi1), EnemyHeight, height));
		m_bitmapCollection.add(scaleToSizeByHeight(BitmapFactory.decodeResource(res, R.drawable.ennemi2), EnemyHeight, height));
		m_bitmapCollection.add(scaleToSizeByHeight(BitmapFactory.decodeResource(res, R.drawable.ennemi3), EnemyHeight, height));
		// /
		m_bitmapCollection.add(scaleToSizeByWidth(BitmapFactory.decodeResource(res, R.drawable.roche1), Rock1Width, width));
		m_bitmapCollection.add(scaleToSizeByWidth(BitmapFactory.decodeResource(res, R.drawable.roche2), Rock2Width, width));
		m_bitmapCollection.add(scaleToSizeByWidth(BitmapFactory.decodeResource(res, R.drawable.roche3), Rock3Width, width));
		// /
		m_bitmapCollection.add(scaleToSizeByHeight(BitmapFactory.decodeResource(res, R.drawable.anim_mort_statue), EnemyHeight, height));
		m_bitmapCollection.add(scaleToSizeByHeight(BitmapFactory.decodeResource(res, R.drawable.sac), CoinsHeight, height));
		m_bitmapCollection.add(scaleToSizeByHeight(BitmapFactory.decodeResource(res, R.drawable.gem), CoinsHeight, height));
		// /
		m_bitmapCollection.add(scaleToSizeByHeight(BitmapFactory.decodeResource(res, R.drawable.torche1), Torch1Height, height));
		m_bitmapCollection.add(scaleToSizeByWidth(BitmapFactory.decodeResource(res, R.drawable.torche2), Torch2Width, width));
		m_bitmapCollection.add(scaleToSizeByHeight(BitmapFactory.decodeResource(res, R.drawable.fireanim), FireAnimeHeight, height));
		//
		m_bitmapCollection.add(scaleToSizeByHeight(BitmapFactory.decodeResource(res, R.drawable.player_run), PlayerHeight, height));
		m_bitmapCollection.add(scaleToSizeByHeight(BitmapFactory.decodeResource(res, R.drawable.player_jump), PlayerHeight, height));
		
		Log.d("BitmapManager", "Loaded all bitmap");
	}
	
	private Bitmap scaleToSizeByWidth(Bitmap sprite, float widthPercentage, int screenWidth)
	{
		Matrix matrix = new Matrix();
		float scale = (screenWidth * widthPercentage / sprite.getWidth());
		
		matrix.postScale(scale, scale);
		return Bitmap.createBitmap(sprite, 0, 0, sprite.getWidth(), sprite.getHeight(), matrix, true);
	}
	
	private Bitmap scaleToSizeByHeight(Bitmap sprite, float heightPercentage, int screenHeight)
	{
		Matrix matrix = new Matrix();
		float scale = (screenHeight * heightPercentage / sprite.getHeight());
		
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
		return (screenWidth * widthPercentage / sprite.getWidth());
	}
	
	public Bitmap getBitmap(int index)
	{
		return m_bitmapCollection.get(index);
	}
}
