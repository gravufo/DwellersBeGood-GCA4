package com.dwellersbegood;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Projectile extends GObject
{
	
	private Resources m_res;
	private Paint m_paint;
	private GAnimation m_shootAnim;
	private GAnimation m_floatAnim;
	
	public Projectile()
	{
	}
	
	public Projectile(float posX, float posY, float speedX, float speedY, int screenWidth, int screenHeight, Resources res)
	{
		super(posX, posY, speedX, speedY, screenWidth, screenHeight);
		
		m_res = res;
		
		m_paint = new Paint();
		m_paint.setColor(Color.BLACK);
		
		this.m_shootAnim = new GAnimation(BitmapFactory.decodeResource(this.m_res, R.drawable.laser_shoot), 6, 3, true);
		this.m_floatAnim = new GAnimation(BitmapFactory.decodeResource(this.m_res, R.drawable.laser_float), 30, 4);
		
		boundingBox.set(0 + leftWidthOffset, 0 + topHeightOffset, 30 - rightWidthOffset, 30 - botHeightOffset);
	}
	
	@Override
	public void draw(Canvas canvas)
	{
		if (canvas != null)
		{
			//canvas.drawCircle(m_position.getX(), m_position.getY(), 30, m_paint);
			
			if(!this.m_shootAnim.getDone())
				this.m_shootAnim.draw(canvas, m_position, m_paint);
			else
				this.m_floatAnim.draw(canvas, m_position, m_paint);
		}
	}
	
	@Override
	public void update(long elapsedTime)
	{
		m_position = m_position.add(m_speed.multiply((float) (elapsedTime / GameThread.nano)));
		
		if(!this.m_shootAnim.getDone())
			this.m_shootAnim.update(elapsedTime);
		else
			this.m_floatAnim.update(elapsedTime);
		
		m_speed.setY(m_speed.getY() + GameView.GRAVITY * ((float) (elapsedTime / GameThread.nano)));
	}
	
}
