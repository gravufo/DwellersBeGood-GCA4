package com.dwellersbegood.Map;

import com.dwellersbegood.BitmapManager;

import android.graphics.Canvas;

public class HoleMiddleSegment extends MapSegment
{
	public HoleMiddleSegment()
	{
		m_image = BitmapManager.getInstance().getBitmap(BitmapManager.HoleMiddle);
	}
	
	@Override
	public void draw(Canvas canvas)
	{
		canvas.drawBitmap(m_image, this.getM_position().getX(), this.getM_position().getY(), null);
		
	}
	
	@Override
	public void update(long elapsedTime)
	{
		// TODO Auto-generated method stub
		
	}
	
}
