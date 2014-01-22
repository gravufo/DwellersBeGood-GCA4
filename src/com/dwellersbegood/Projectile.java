package com.dwellersbegood;

import android.content.res.Resources;
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
		
		botHeightOffset = (int)(m_floatAnim.getHeight()/3);
		topHeightOffset = (int)(m_floatAnim.getHeight()/3);
		rightWidthOffset = (int)(m_floatAnim.getWidth()/5.5);
		leftWidthOffset = (int)(m_floatAnim.getWidth()/3.7);
		
		calculateBoundingBox(m_position, m_floatAnim.getWidth(), m_floatAnim.getHeight());
		//boundingBox.set((int) m_position.getX() + leftWidthOffset, (int) m_position.getY() + topHeightOffset, (int) m_position.getX() + m_shootAnim.getWidth() - rightWidthOffset, (int) m_position.getY() + m_shootAnim.getHeight() - botHeightOffset);
	}
	
	@Override
	public void draw(Canvas canvas)
	{
		if (canvas != null)
		{
			// canvas.drawCircle(m_position.getX(), m_position.getY(), 30, m_paint);
			
			if (!this.m_shootAnim.getDone())
				this.m_shootAnim.draw(canvas, m_position, m_paint);
			else
				this.m_floatAnim.draw(canvas, m_position, m_paint);
			
			if(GameView.ENABLED_DEBUG)
				canvas.drawRect(boundingBox, m_debugPaint);
		}
	}
	
	@Override
	public void update(long elapsedTime)
	{
		m_position = m_position.add(m_speed.multiply((float) (elapsedTime / GameThread.nano)));
		
		calculateBoundingBox(m_position, m_floatAnim.getWidth(), m_floatAnim.getHeight());
		//boundingBox.set((int) m_position.getX() + leftWidthOffset, (int) m_position.getY() + topHeightOffset, (int) m_position.getX() + m_shootAnim.getWidth() - rightWidthOffset, (int) m_position.getY() + m_shootAnim.getHeight() - botHeightOffset);
		
		if (!this.m_shootAnim.getDone())
			this.m_shootAnim.update(elapsedTime);
		else
			this.m_floatAnim.update(elapsedTime);
		
		//m_speed.setY(m_speed.getY() + GameView.GRAVITY * ((float) (elapsedTime / GameThread.nano)));
	}
	
}
