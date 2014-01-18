package com.dwellersbegood;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Projectile extends GObject{
	
	private Resources m_res;
	private Paint m_paint;
	
	public Projectile(){}
	
	public Projectile(float posX, float posY, float speedX, float speedY, int screenWidth, int screenHeight, Resources res){
		super(posX, posY, speedX, speedY, screenWidth, screenHeight);
		
		m_res = res;
		
		m_paint = new Paint();
		m_paint.setColor(Color.BLACK);
	}

	@Override
	public void draw(Canvas canvas) {
		if(canvas != null){
			canvas.drawCircle(m_position.getX(), m_position.getY(), 30, m_paint);
		}
	}

	@Override
	public void update(long ellapsedTime) {
		// TODO Auto-generated method stub
		
	}

}
