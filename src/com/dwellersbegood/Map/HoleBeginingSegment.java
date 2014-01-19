package com.dwellersbegood.Map;

import com.dwellersbegood.BitmapManager;

import android.graphics.Canvas;

public class HoleBeginingSegment extends MapSegment {

	public HoleBeginingSegment()
	{
		m_image = BitmapManager.getInstance().getBitmap(BitmapManager.HoleBeginning);
		
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(m_image, this.getM_position().getX(), this.getM_position().getY(), null);
		
	}

	@Override
	public void update(long ellapsedTime) {
		// TODO Auto-generated method stub
		
	}


}
