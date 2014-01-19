package com.dwellersbegood.Map;

import java.util.Random;

import com.dwellersbegood.BitmapManager;
import com.dwellersbegood.GameThread;
import com.dwellersbegood.GameView;
import com.dwellersbegood.Vector2D;

import android.graphics.Canvas;

public class CoinSegment extends MapSegment {
	
	private double angle;
	private Random seed;
	private float screenSize;

	public CoinSegment()
	{
		m_Type = MapSegmentGenerator.Coin;
		m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Coin);
		seed = new Random();
		angle = seed.nextFloat()*2*Math.PI;
		screenSize = GameView.getScreenSize().getY();
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(m_image, this.getM_position().getX(), this.getM_position().getY(), null);
		
		if(GameView.ENABLED_DEBUG){
			canvas.drawRect(boundingBox, m_debugPaint);
		}
	}

	@Override
	public void update(long elapsedTime) 
	{
		angle += 2*elapsedTime/GameThread.nano;
		if(angle > 2*Math.PI)
			angle -= 2*Math.PI;
		
		m_position.setY((float)(Math.sin(angle)*0.20*screenSize + screenSize/2));
		
		this.boundingBox.set((int)this.getM_position().getX(), (int)this.getM_position().getY(), (int)this.getM_position().getX() + this.getWidth(), (int)this.getM_position().getY() + this.getHeight());
	}
}
