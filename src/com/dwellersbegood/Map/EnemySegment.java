package com.dwellersbegood.Map;

import java.util.Random;

import android.graphics.Canvas;

import com.dwellersbegood.BitmapManager;
import com.dwellersbegood.GameThread;
import com.dwellersbegood.GameView;

public class EnemySegment extends MapSegment
{
	
	public static final long TIMETOSHOT = 2500;
	private Random seed;
	private int EnemyType;
	
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
	}
	
	@Override
	public void draw(Canvas canvas)
	{
		canvas.drawBitmap(m_image, this.getM_position().getX(), this.getM_position().getY(), null);
		
		if (GameView.ENABLED_DEBUG)
		{
			canvas.drawRect(boundingBox, m_debugPaint);
		}
	}
	
	@Override
	public void update(long elapsedTime)
	{
		
		this.boundingBox.set((int) this.getM_position().getX(), (int) this.getM_position().getY(), (int) this.getM_position().getX() + this.getWidth(), (int) this.getM_position().getY() + this.getHeight());
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
}
