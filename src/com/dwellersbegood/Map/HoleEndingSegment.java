package com.dwellersbegood.Map;

import com.dwellersbegood.BitmapManager;

import android.graphics.Canvas;

public class HoleEndingSegment extends MapSegment {

	public HoleEndingSegment()
	{
		m_Type = MapSegmentGenerator.HoleEnding;
		m_image = BitmapManager.getInstance().getBitmap(BitmapManager.HoleEnding);
		topHeightOffset = getHeight()/7;
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(m_image, this.getM_position().getX(), this.getM_position().getY(), null);
		
	}

	@Override
	public void update(long ellapsedTime) {
		// TODO Auto-generated method stub
		calculateBoundingBox(m_position, getWidth(), getHeight());
		
	}


}
