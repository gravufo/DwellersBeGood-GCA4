package com.dwellersbegood;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Projectile extends GObject
{
	
	private Resources m_res;
	private Paint m_paint;
	
	public Projectile()
	{
	}
	
	public Projectile(float posX, float posY, float speedX, float speedY, int screenWidth, int screenHeight, Resources res)
	{
		super(posX, posY, speedX, speedY, screenWidth, screenHeight);
		
		m_res = res;
		
		m_paint = new Paint();
		m_paint.setColor(Color.BLACK);
		
		boundingBox.set(0 + leftWidthOffset, 0 + topHeightOffset, 30 - rightWidthOffset, 30 - botHeightOffset);
	}
	
	@Override
	public void draw(Canvas canvas)
	{
		if (canvas != null)
		{
			canvas.drawCircle(m_position.getX(), m_position.getY(), 30, m_paint);
		}
	}
	
	@Override
	public void update(long elapsedTime)
	{
		m_position = m_position.add(m_speed.multiply((float) (elapsedTime / GameThread.nano)));
		
		m_speed.setY(m_speed.getY() + GameView.GRAVITY * ((float) (elapsedTime / GameThread.nano)));
	}
	
}
