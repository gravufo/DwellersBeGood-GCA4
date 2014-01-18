package com.dwellersbegood;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Player extends GObject
{
	
	private Resources m_res;
	private final Paint m_paint;
	private GAnimation m_runningAnim;
	private boolean m_jumping;
	
	public Player()
	{
		super();
		m_paint = new Paint();
		m_paint.setColor(Color.BLACK);
	}
	
	public Player(float posX, float posY, float speedX, float speedY, int screenWidth, int screenHeight, Resources res)
	{
		super(posX, posY, speedX, speedY, screenWidth, screenHeight);
		m_res = res;
		
		m_jumping = false;
		
		m_paint = new Paint();
		m_paint.setColor(Color.BLACK);
		
		this.m_runningAnim = new GAnimation(BitmapFactory.decodeResource(this.m_res, R.drawable.player), 60, 10);
		
		boundingBox.set((int) m_position.getX() + leftWidthOffset, (int) m_position.getY() + topHeightOffset, (int) m_position.getX() + m_runningAnim.getWidth() - rightWidthOffset, (int) m_position.getY() + m_runningAnim.getHeight() - botHeightOffset);
	}
	
	@Override
	public void draw(Canvas canvas)
	{
		this.m_runningAnim.draw(canvas, m_position, m_paint);
	}
	
	@Override
	public void update(long elapsedTime)
	{
		m_position = m_position.add(m_speed.multiply((float) (elapsedTime / GameThread.nano)));
		this.m_runningAnim.update(elapsedTime);
		boundingBox.set((int) m_position.getX() + leftWidthOffset, (int) m_position.getY() + topHeightOffset, (int) m_position.getX() + m_runningAnim.getWidth() - rightWidthOffset, (int) m_position.getY() + m_runningAnim.getHeight() - botHeightOffset);
		
		if (this.m_runningAnim.getBmpToDraw() != null)
		{
			if (m_position.getY() > GameView.PLAYER_MIN_Y)
			{
				m_position.setY(GameView.PLAYER_MIN_Y);
				m_speed.setY(0);
				m_jumping = false;
			}
		}
		
		m_speed.setY(m_speed.getY() + GameView.GRAVITY * ((float) (elapsedTime / GameThread.nano)));
	}
	
	public void jump()
	{
		if (!m_jumping)
		{
			m_speed.setY(-300);
			m_jumping = true;
		}
	}
	
}
