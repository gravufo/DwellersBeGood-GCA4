package com.dwellersbegood.Map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.dwellersbegood.BitmapManager;
import com.dwellersbegood.GameView;
import com.dwellersbegood.Vector2D;

public class FloorSegment extends MapSegment {
	
	private Paint paint;

	FloorSegment()
	{
		m_image = BitmapManager.Instance().getBitmap(BitmapManager.Floor);
		m_position = new Vector2D(GameView.getScreenSize().getX()- getWidth(), GameView.getScreenSize().getY() - getHeight());
		
		paint = new Paint();
		paint.setColor(Color.BLACK);
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(m_image, this.getM_position().getX(), this.getM_position().getY(), paint);
		canvas.drawLine(this.getM_position().getX(),GameView.getScreenSize().getY(), GameView.getScreenSize().getX(), 0, paint);
	}

	@Override
	public void update(long ellapsedTime) {
		// TODO Auto-generated method stub

	}

}
