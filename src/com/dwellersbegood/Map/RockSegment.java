package com.dwellersbegood.Map;

import com.dwellersbegood.BitmapManager;

import android.graphics.Canvas;

public class RockSegment extends MapSegment {
	
	public RockSegment()
	{
		m_Type = MapSegmentGenerator.Rock;
		m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Rock1);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(m_image, this.getM_position().getX(), this
				.getM_position().getY(), null);

	}

	@Override
	public void update(long ellapsedTime) {
		// TODO Auto-generated method stub

	}

}
