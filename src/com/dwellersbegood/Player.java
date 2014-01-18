package com.dwellersbegood;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Player extends GObject{
	
	private Resources m_res;
	private Paint m_paint;
	
	public Player(){
		super();
		m_paint = new Paint();
		m_paint.setColor(Color.BLACK);
	}
	
	public Player(float posX, float posY, float speedX, float speedY, int screenWidth, int screenHeight, Resources res){
		super(posX, posY, speedX, speedY, screenWidth, screenHeight);
		m_res = res;
		
		m_paint = new Paint();
		m_paint.setColor(Color.BLACK);
	}
	
	@Override
	public void draw(Canvas canvas){
		canvas.drawRect(new RectF(m_position.x - 25,m_position.y - 50, m_position.x + 25, m_position.x + 50), m_paint);
	}

	@Override
	public void update(long ellapsedTime) {
		// TODO Auto-generated method stub
		
	}

}
