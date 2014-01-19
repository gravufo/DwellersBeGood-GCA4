package com.dwellersbegood.Map;

import com.dwellersbegood.BitmapManager;
import com.dwellersbegood.GameView;

import android.graphics.Canvas;

public class PlatformSegment extends MapSegment
{
	private static int loopIndex = 0;
	
	public PlatformSegment()
	{
		m_Type = MapSegmentGenerator.Platform;
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
		
		if(GameView.ENABLED_DEBUG){
			canvas.drawRect(boundingBox, m_debugPaint);
		}
	}
	
	@Override
	public void update(long elapsedTime)
	{
		this.boundingBox.set((int)this.getM_position().getX(), (int)this.getM_position().getY(), (int)this.getM_position().getX() + this.getWidth(), (int)this.getM_position().getY() + this.getHeight());
	}
}
