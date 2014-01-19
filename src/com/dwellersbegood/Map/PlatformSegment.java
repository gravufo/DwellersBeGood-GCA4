package com.dwellersbegood.Map;

import com.dwellersbegood.BitmapManager;

import android.graphics.Canvas;

public class PlatformSegment extends MapSegment
{
	private static int loopIndex = 0;
	
	public PlatformSegment()
	{
		switch(loopIndex)
		{
			case 0:
				m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Platform0);
				break;
			case 1:
				m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Platform1);
				break;
		}
		
		loopIndex = (loopIndex + 1)%2;
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
