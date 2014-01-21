package com.dwellersbegood;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class EnemyProjectile extends GObject
{
	
	private Resources m_res;
	private Paint m_paint;
	private GAnimation m_shootAnim;
	
	public EnemyProjectile()
	{
	}
	
	public EnemyProjectile(float posX, float posY, float speedX, float speedY, int screenWidth, int screenHeight, Resources res)
	{
		super(posX, posY, speedX, speedY, screenWidth, screenHeight);
		
		m_res = res;
		
		m_paint = new Paint();
		m_paint.setColor(Color.BLACK);
		
		this.m_shootAnim = new GAnimation(BitmapManager.getInstance().getBitmap(BitmapManager.Laser2), 8, 5, true);

		boundingBox.set((int) m_position.getX() + leftWidthOffset, (int) m_position.getY() + topHeightOffset, (int) m_position.getX() + m_shootAnim.getWidth() - rightWidthOffset, (int) m_position.getY() + m_shootAnim.getHeight() - botHeightOffset);
	}
	
	@Override
	public void draw(Canvas canvas)
	{
		if (canvas != null)
		{
			this.m_shootAnim.draw(canvas, m_position, m_paint);
			if(GameView.ENABLED_DEBUG)
				canvas.drawRect(boundingBox, m_debugPaint);
		}
	}
	
	@Override
	public void update(long elapsedTime)
	{
		m_position = m_position.add(m_speed.multiply((float) (elapsedTime / GameThread.nano)));
		
		boundingBox.set((int) m_position.getX() + leftWidthOffset, (int) m_position.getY() + topHeightOffset, (int) m_position.getX() + m_shootAnim.getWidth() - rightWidthOffset, (int) m_position.getY() + m_shootAnim.getHeight() - botHeightOffset);
		
		if (!this.m_shootAnim.getDone())
			this.m_shootAnim.update(elapsedTime);
		
		//m_speed.setY(m_speed.getY() + GameView.GRAVITY * ((float) (elapsedTime / GameThread.nano)));
	}
	
}
