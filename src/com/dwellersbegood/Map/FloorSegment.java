package com.dwellersbegood.Map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.dwellersbegood.BitmapManager;
import com.dwellersbegood.GameView;
import com.dwellersbegood.Vector2D;

public class FloorSegment extends MapSegment {

	private static int loopIndex = 0;

	FloorSegment()
	{
		m_Type = MapSegmentGenerator.Floor;
		switch(loopIndex)
		{
			case 0:
				m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Floor0);
				break;
			case 1:
				m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Floor1);
				break;
			case 2:
				m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Floor2);
				break;
		}
		
		m_position = new Vector2D(0, GameView.getScreenSize().getY()*(float)0.75);
		
		loopIndex = (loopIndex + 1)%3;
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(m_image, this.getM_position().getX(), this.getM_position().getY(), null);
		
		if(GameView.ENABLED_DEBUG){
			canvas.drawRect(boundingBox, m_debugPaint);
		}
	}

	@Override
	public void update(long ellapsedTime) {
		this.boundingBox.set((int)this.getM_position().getX(), (int)this.getM_position().getY(), (int)this.getM_position().getX() + this.getWidth(), (int)this.getM_position().getY() + this.getHeight());
		if(this.boundingBox.intersect(GameView.m_player.getBoundingBox())){
			GameView.m_player.setIsOnFloor(true);
		}
	}
	
}
