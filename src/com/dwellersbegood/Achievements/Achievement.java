package com.dwellersbegood.Achievements;

import com.dwellersbegood.GameThread;
import com.dwellersbegood.GameView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Paint.Align;

public class Achievement
{
	private String m_achievementText;
	private Paint m_textPaint;
	private Rect m_textBounds;
	private boolean m_earned;
	public void setEarned(boolean earned){m_earned = earned;}
	private boolean m_shown;
	private Rect m_box;
	private Paint m_boxPaint;
	
	private float m_speedY;
	private long m_duration;
	private long m_shownTime;
	
	private GameView m_gameView;
	
	public Achievement(GameView view, String text)
	{
		m_gameView = view;
		
		m_box = new Rect(3*GameView.g_ScreenWidth/8,-GameView.g_ScreenHeight/5,5*GameView.g_ScreenWidth/8,0);
		m_boxPaint = new Paint();
		m_boxPaint.setColor(Color.argb(200, 0, 0, 200));
		
		m_achievementText = text;
		m_textPaint = new Paint();
		this.m_textPaint = new Paint();
		this.m_textPaint.setColor(Color.YELLOW);
		this.m_textPaint.setTextSize(36);
		this.m_textPaint.setTextAlign(Align.CENTER);
		Typeface font = Typeface.createFromAsset(m_gameView.getResources().getAssets(), "fonts/aztechipster.ttf");
		this.m_textPaint.setTypeface(font);
		this.m_textBounds = new Rect();
		this.m_textPaint.getTextBounds(m_achievementText, 0, m_achievementText.length()-1, this.m_textBounds);
		
		m_earned = false;
		m_shown = false;
		
		m_speedY = 300;
		m_duration = 2;
		m_shownTime = 0;
	
	}
	
	public void Update(long elapsedTime){
		if(!m_earned)
			return;
		if(!m_shown){
			if(m_shownTime < m_duration*GameThread.nano){
				if(m_box.top < GameView.g_ScreenHeight/15)
					m_box.offset(0, (int) (m_speedY * ((double)(elapsedTime)/GameThread.nano)));
				else
					m_shownTime += elapsedTime;
			}
			else{
				if(m_box.bottom >= 0)
					m_box.offset(0, (int) (-m_speedY * ((double)(elapsedTime)/GameThread.nano)));
				else
					m_shown = true;
			}
		}
	}
	
	public void Draw(Canvas canvas){
		if(m_earned && !m_shown){
			canvas.drawRect(m_box, m_boxPaint);
			canvas.drawText(m_achievementText, m_box.left + m_box.width()/2/*-m_textBounds.width()/2*/, m_box.top + m_box.height()/2+m_textBounds.height()/2, m_textPaint);
		}
	}
}
