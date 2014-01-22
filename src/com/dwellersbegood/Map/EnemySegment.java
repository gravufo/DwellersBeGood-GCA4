package com.dwellersbegood.Map;

import java.util.Random;

import android.graphics.Canvas;

import com.dwellersbegood.BitmapManager;
import com.dwellersbegood.GAnimation;
import com.dwellersbegood.GameThread;
import com.dwellersbegood.GameView;
import com.dwellersbegood.Vector2D;

public class EnemySegment extends MapSegment
{
	
	public static final long TIMETOSHOT = 2500;
	private Random seed;
	private int EnemyType;
	private boolean m_dead;
	private GAnimation m_deadAnim;
	private Vector2D m_animOffset;
	private boolean m_deadAnimDone;
	
	private long m_timeSinceLastShot;
	
	public EnemySegment()
	{
		seed = new Random();
		m_Type = MapSegmentGenerator.Enemy;
		m_timeSinceLastShot = 0;
		
		int ratio = seed.nextInt(3);
		
		switch (ratio)
		{
		case 0:
			m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Enemy0);
			EnemyType = 0;
			break;
		case 1:
			m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Enemy1);
			EnemyType = 1;
			break;
		case 2:
			m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Enemy2);
			EnemyType = 2;
			break;
		}
		
		m_dead = false;
		m_deadAnim = new GAnimation(BitmapManager.getInstance().getBitmap(BitmapManager.MortStatue), 12, 6, true);
		m_animOffset = new Vector2D(m_image.getWidth()/2-m_deadAnim.getWidth()/2, m_image.getHeight()/2-m_deadAnim.getHeight()/2);
		m_deadAnimDone = false;
	}
	
	@Override
	public void draw(Canvas canvas)
	{
		if(!m_deadAnimDone){
			if(!m_dead)
				canvas.drawBitmap(m_image, this.getM_position().getX(), this.getM_position().getY(), null);
			else
				m_deadAnim.draw(canvas, m_position.add(m_animOffset), null);
			
			if (GameView.ENABLED_DEBUG)
			{
				canvas.drawRect(boundingBox, m_debugPaint);
			}
		}
	}
	
	@Override
	public void update(long elapsedTime)
	{
		
		this.boundingBox.set((int) this.getM_position().getX(), (int) this.getM_position().getY(), (int) this.getM_position().getX() + this.getWidth(), (int) this.getM_position().getY() + this.getHeight());
		if(m_dead){
			m_deadAnim.update(elapsedTime);
			m_timeSinceLastShot = 0;
			if(m_deadAnim.getDone())
				m_deadAnimDone = true;
		}
		else
			m_timeSinceLastShot += elapsedTime / GameThread.nano * 1000;
		
	}
	
	public boolean ShouldDrawShot()
	{
		
		return m_timeSinceLastShot >= TIMETOSHOT;
		
	}
	
	public void resetShotTimer()
	{
		m_timeSinceLastShot = 0;
	}
	
	public void setDead(boolean dead){
		m_dead = dead;
	}
}
