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
		// TODO Auto-generated method stub

	}

	@Override
	public void update(long ellapsedTime) {
		// TODO Auto-generated method stub

	}

}
