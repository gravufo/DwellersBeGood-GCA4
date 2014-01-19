package com.dwellersbegood.Map;

import android.graphics.Canvas;

import com.dwellersbegood.BitmapManager;
import com.dwellersbegood.GameView;
import com.dwellersbegood.Vector2D;

public class FloorSegment extends MapSegment
{
	
	private static int loopIndex = 0;
	
	FloorSegment()
	{
		m_Type = MapSegmentGenerator.Floor;
		switch (loopIndex)
		{
		case 0:
			m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Floor0);
			break;
		case 1:
			m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Floor1);
			break;
		case 2:
			m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Floor2);
			break;
		case 3:
			m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Floor3);
			break;
		case 4:
			m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Floor4);
			break;
		case 5:
			m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Floor5);
			break;
		case 6:
			m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Floor6);
			break;
		case 7:
			m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Floor7);
			break;
		}
		
		m_position = new Vector2D(0, GameView.LEVEL_FLOOR);
		
		loopIndex = (loopIndex + 1) % 8;
	}
	
	@Override
	public void draw(Canvas canvas)
	{
		canvas.drawBitmap(m_image, this.getM_position().getX(), this.getM_position().getY(), null);
		
		if (GameView.ENABLED_DEBUG)
		{
			canvas.drawRect(boundingBox, m_debugPaint);
		}
	}
	
	@Override
	public void update(long ellapsedTime) {
		this.boundingBox.set((int)this.getM_position().getX(), (int)this.getM_position().getY()+50, (int)this.getM_position().getX() + this.getWidth(), (int)this.getM_position().getY() + this.getHeight());
	}
	
}
