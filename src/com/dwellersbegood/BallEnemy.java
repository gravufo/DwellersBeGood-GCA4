package com.dwellersbegood;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class BallEnemy extends GObject
{
	
	private Resources m_res;
	private final Paint m_paint;
	private Bitmap m_sprite;
	private GAnimation m_Anim;
	
	public BallEnemy()
	{
		super();
		m_paint = new Paint();
		m_paint.setColor(Color.BLACK);
	}
	
	public BallEnemy(float posX, float posY, float speedX, float speedY, int screenWidth, int screenHeight, Resources res)
	{
		super(posX, posY, speedX, speedY, screenWidth, screenHeight);
		m_res = res;
		
		m_paint = new Paint();
		m_paint.setColor(Color.BLACK);
		
		m_sprite = BitmapManager.getInstance().scaleToSize(BitmapFactory.decodeResource(res, R.drawable.badstatue), 300, 300);
		// this.m_Anim = new GAnimation(BitmapFactory.decodeResource(this.m_res, R.drawable.player), 60, 10);
		
		boundingBox.set(0 + leftWidthOffset, 0 + topHeightOffset, m_sprite.getWidth() - rightWidthOffset, m_sprite.getHeight() - botHeightOffset);
	}
	
	@Override
	public void draw(Canvas canvas)
	{
		// this.m_Anim.draw(canvas, m_position, m_paint);
		canvas.drawBitmap(m_sprite, m_position.getX(), m_position.getY(), m_paint);
	}
	
	@Override
	public void update(long ellapsedTime)
	{
		// this.m_Anim.update(ellapsedTime);
	}
}
