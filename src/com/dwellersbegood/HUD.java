package com.dwellersbegood;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;

public class HUD
{
	private Paint m_collectibleScorePaint;
	public Rect m_collectibleScoreTextBounds;
	
	private Paint m_distanceTraveledPaint;
	public static int m_distanceTraveled;
	public Rect m_distanceTraveledTextBounds;
	
	private Bitmap m_playerHealthBitmap;
	private Bitmap m_coinIconBitmap;
	
	private GameView m_gameView;
	
	public HUD(GameView view){
		m_gameView = view;
		
		this.m_collectibleScorePaint = new Paint();
		this.m_collectibleScorePaint.setColor(Color.YELLOW);
		this.m_collectibleScorePaint.setTextSize(84);
		this.m_collectibleScorePaint.setTextAlign(Align.CENTER);
		Typeface font = Typeface.createFromAsset(m_gameView.getResources().getAssets(), "fonts/woodbadge.ttf");
		this.m_collectibleScorePaint.setTypeface(font);
		this.m_collectibleScoreTextBounds = new Rect();
		this.m_collectibleScorePaint.getTextBounds("0", 0, 1, this.m_collectibleScoreTextBounds);
		
		this.m_distanceTraveledPaint = new Paint();
		this.m_distanceTraveledPaint.setColor(Color.BLACK);
		this.m_distanceTraveledPaint.setTextSize(84);
		this.m_distanceTraveledPaint.setTextAlign(Align.CENTER);
		Typeface font1 = Typeface.createFromAsset(m_gameView.getResources().getAssets(), "fonts/aztechipster.ttf");
		this.m_distanceTraveledPaint.setTypeface(font1);
		this.m_distanceTraveledTextBounds = new Rect();
		this.m_distanceTraveledPaint.getTextBounds("0", 0, 1, this.m_distanceTraveledTextBounds);
		
		this.m_playerHealthBitmap = BitmapManager.getInstance().getBitmap(BitmapManager.PlayerHealth);
		this.m_coinIconBitmap = BitmapManager.getInstance().getBitmap(BitmapManager.CoinIcon);
	}
	
	public void DrawHUD(Canvas canvas){
		int newWidth = GameView.g_ScreenWidth / 20;
		float height = (float) (GameView.g_ScreenHeight/12);
		canvas.drawBitmap(m_playerHealthBitmap, newWidth, height, null);
		newWidth += m_playerHealthBitmap.getWidth()*2;
		canvas.drawText(m_gameView.getGame().getCoinsCollected()/100+"", newWidth, height+m_collectibleScoreTextBounds.height(), this.m_collectibleScorePaint);
		newWidth += m_collectibleScoreTextBounds.width() + 5;
		canvas.drawText((m_gameView.getGame().getCoinsCollected()%100)/10+"", newWidth, height+m_collectibleScoreTextBounds.height(), this.m_collectibleScorePaint);
		newWidth += m_collectibleScoreTextBounds.width() + 5;
		canvas.drawText(m_gameView.getGame().getCoinsCollected()%10+"", newWidth, height+m_collectibleScoreTextBounds.height(), this.m_collectibleScorePaint);
		newWidth += m_collectibleScoreTextBounds.width() + 5;
		canvas.drawBitmap(m_coinIconBitmap, newWidth, height, null);
		
		newWidth = GameView.g_ScreenWidth / 2 - (m_distanceTraveledTextBounds.width() * 2);
		canvas.drawText(m_gameView.getGame().getDistanceTraveled()/100+"", newWidth, height+m_distanceTraveledTextBounds.height(), this.m_distanceTraveledPaint);
		newWidth += m_distanceTraveledTextBounds.width() + 5;
		canvas.drawText((m_gameView.getGame().getDistanceTraveled()%100)/10+"", newWidth, height+m_distanceTraveledTextBounds.height(), this.m_distanceTraveledPaint);
		newWidth += m_distanceTraveledTextBounds.width() + 5;
		canvas.drawText(m_gameView.getGame().getDistanceTraveled()%10+"", newWidth, height+m_distanceTraveledTextBounds.height(), this.m_distanceTraveledPaint);
		newWidth += m_distanceTraveledTextBounds.width() + 5;
		canvas.drawText("M", newWidth, height+m_distanceTraveledTextBounds.height(), this.m_distanceTraveledPaint);
	}
}
