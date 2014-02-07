package com.dwellersbegood.Map;

import java.util.Random;

import android.graphics.Canvas;

import com.dwellersbegood.BitmapManager;
import com.dwellersbegood.GAnimation;
import com.dwellersbegood.GameView;
import com.dwellersbegood.Vector2D;

public class FireSegment extends MapSegment {
	
	private GAnimation m_fireAnim;
	private Vector2D firePos;
	private static Vector2D m_fireOffset;
	
	public FireSegment(){
		m_Type = MapSegmentGenerator.Fire;
		m_fireAnim = new GAnimation(BitmapManager.getInstance().getBitmap(BitmapManager.FireAnim), 12, 6);
		
		m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Torch1);
		
		
		m_fireOffset = new Vector2D(m_image.getWidth()/2 - m_fireAnim.getWidth()/(float)1.7, (float)(-this.m_fireAnim.getHeight()*0.75));
		
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
		firePos = m_position.add(m_fireOffset);
		calculateBoundingBox(firePos, m_fireAnim.getWidth(), m_fireAnim.getHeight());
		m_fireAnim.update(elapsedTime);

	}

}
