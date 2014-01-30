package com.dwellersbegood.Map;

import java.util.Random;

import android.graphics.Canvas;

import com.dwellersbegood.BitmapManager;
import com.dwellersbegood.GAnimation;
import com.dwellersbegood.GameView;
import com.dwellersbegood.Vector2D;

public class FireSegment extends MapSegment {
	
	private Random seed;
	private int torchType;
	private GAnimation m_fireAnim;
	private Vector2D firePos;
	private static Vector2D m_fireOffset0;
	private static Vector2D m_fireOffset1;
	
	public FireSegment(){
		m_Type = MapSegmentGenerator.Fire;
		seed = new Random();
		m_fireAnim = new GAnimation(BitmapManager.getInstance().getBitmap(BitmapManager.FireAnim), 12, 6);
		
		switch(seed.nextInt(2))
		{
			case 0:
				torchType = 0;
				m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Torch1);
				break;
			case 1:
				torchType = 1;
				m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Torch2);
				break;
		}
		
		
		m_fireOffset0 = new Vector2D(m_image.getWidth()/2 - m_fireAnim.getWidth()/(float)1.7, (float)(-this.m_fireAnim.getHeight()*0.75));
		m_fireOffset1 = new Vector2D((float) (-m_fireAnim.getWidth()/(float)2.2), -m_fireAnim.getWidth()/3);
		
		botHeightOffset = (int)(m_fireAnim.getHeight()/13);
		topHeightOffset = (int)(m_fireAnim.getHeight()/4);
		leftWidthOffset = (int)(m_fireAnim.getWidth()/3.2);
		rightWidthOffset = (int)(m_fireAnim.getWidth()/5.7);
		
		
	}

	@Override
	public void draw(Canvas canvas) {

		m_fireAnim.draw(canvas, firePos, null);
		canvas.drawBitmap(m_image, m_position.getX(), m_position.getY(), null);
		
		if (GameView.ENABLED_DEBUG)
		{
			canvas.drawRect(boundingBox, m_debugPaint);
		}
	}

	@Override
	public void update(long elapsedTime) {
		switch(torchType)
		{
			case 0:
				firePos = m_position.add(m_fireOffset0);
				break;
			case 1:
				firePos = m_position.add(m_fireOffset1);
				break;
		}
		calculateBoundingBox(firePos, m_fireAnim.getWidth(), m_fireAnim.getHeight());
		m_fireAnim.update(elapsedTime);

	}

}
