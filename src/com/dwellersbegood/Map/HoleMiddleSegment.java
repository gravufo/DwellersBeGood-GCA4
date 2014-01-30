package com.dwellersbegood.Map;

import com.dwellersbegood.BitmapManager;
import com.dwellersbegood.GameView;

import android.graphics.Canvas;

public class HoleMiddleSegment extends MapSegment
{
	public HoleMiddleSegment()
	{
		m_Type = MapSegmentGenerator.HoleMiddle;
		m_image = BitmapManager.getInstance().getBitmap(BitmapManager.HoleMiddle);
		topHeightOffset = getHeight()/4;
	}
	
	@Override
	public void draw(Canvas canvas)
	{
		canvas.drawBitmap(m_image, this.getM_position().getX(), this.getM_position().getY(), null);
		
		if(GameView.ENABLED_DEBUG){
			canvas.drawRect(boundingBox, m_debugPaint);
		}
	}
	
	@Override
	public void update(long elapsedTime)
	{
		// TODO Auto-generated method stub
		calculateBoundingBox(m_position, getWidth(), getHeight());
		
	}
	
}
