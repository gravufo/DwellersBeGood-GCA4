package com.dwellersbegood.Map;

import java.util.Random;

import android.graphics.Canvas;

import com.dwellersbegood.BitmapManager;

public class RockSegment extends MapSegment {
	
	public RockSegment()
	{
		m_Type = MapSegmentGenerator.Rock;
		Random seed = new Random();
		
		switch(seed.nextInt(3))
		{
			case 0:
				m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Rock0);
				botHeightOffset = getHeight()/11;
				break;
			case 1:
				m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Rock1);
				botHeightOffset = (int)(getHeight()/11.75);
				leftWidthOffset = (int)(getWidth()/7);
				break;
			case 2:
				m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Rock2);
				botHeightOffset = (int)(getHeight()/6);
				topHeightOffset = (int)(getHeight()/5.3);
				leftWidthOffset = (int)(getWidth()/20);
				rightWidthOffset = (int)(getWidth()/20);
				break;
		}
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(m_image, this.getM_position().getX(), this
				.getM_position().getY(), null);
	}

	@Override
	public void update(long ellapsedTime) {
		// TODO Auto-generated method stub
		calculateBoundingBox(m_position, getWidth(), getHeight());
	}

}
