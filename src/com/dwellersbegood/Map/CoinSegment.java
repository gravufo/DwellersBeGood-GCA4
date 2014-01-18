package com.dwellersbegood.Map;

import com.dwellersbegood.BitmapManager;
import com.dwellersbegood.GameThread;
import com.dwellersbegood.GameView;
import com.dwellersbegood.Vector2D;

import android.graphics.Canvas;

public class CoinSegment extends MapSegment {

	public CoinSegment()
	{
		m_image = BitmapManager.Instance().getBitmap(BitmapManager.Coin);
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(m_image, this.getM_position().getX(), this.getM_position().getY(), null);
	}

	@Override
	public void update(long elapsedTime) {
		m_position.setY(m_position.getY() + (float)(elapsedTime/GameThread.nano));
	}
	
	
	

}
