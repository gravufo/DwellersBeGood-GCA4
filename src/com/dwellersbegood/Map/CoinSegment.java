package com.dwellersbegood.Map;

import com.dwellersbegood.BitmapManager;
import com.dwellersbegood.GameThread;
import com.dwellersbegood.GameView;
import com.dwellersbegood.Vector2D;

import android.graphics.Canvas;

public class CoinSegment extends MapSegment {

	public CoinSegment()
	{
		m_Type = MapSegmentGenerator.Coin;
		m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Coin);
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(m_image, this.getM_position().getX(), this.getM_position().getY(), null);
		
		if(GameView.ENABLED_DEBUG){
			canvas.drawRect(boundingBox, m_debugPaint);
		}
	}

	@Override
	public void update(long elapsedTime) {
		m_position.setY(m_position.getY() - (float)(elapsedTime/GameThread.nano));
		
		this.boundingBox.set((int)this.getM_position().getX(), (int)this.getM_position().getY(), (int)this.getM_position().getX() + this.getWidth(), (int)this.getM_position().getY() + this.getHeight());
	}
}
