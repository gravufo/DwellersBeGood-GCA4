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
		
		firePos = new Vector2D(0,0);
		
		
	}

	@Override
	public void draw(Canvas canvas) {

		m_fireAnim.draw(canvas, firePos, null);
		canvas.drawBitmap(m_image, m_position.getX(), m_position.getY(), null);
	}

	@Override
	public void update(long elapsedTime) {
		switch(torchType)
		{
			case 0:
				firePos = m_position.substract(new Vector2D((float)0.05*GameView.getScreenSize().getX(),(float)0.05*GameView.getScreenSize().getX()));
				break;
			case 1:
				firePos = m_position.add(new Vector2D((float)0.05*GameView.getScreenSize().getX(),(float)0.05*GameView.getScreenSize().getX()));
				break;
		}
		
		m_fireAnim.update(elapsedTime);

	}

}
