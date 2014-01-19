package com.dwellersbegood.Map;

import com.dwellersbegood.BitmapManager;

import android.graphics.Canvas;

public class FireSegment extends MapSegment {
	
	
	public FireSegment(){
		m_Type = MapSegmentGenerator.Fire;
		m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Fire);
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
