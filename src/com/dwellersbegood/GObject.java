package com.dwellersbegood;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public abstract class GObject
{
	protected Vector2D m_position;
	protected Vector2D m_speed;
	protected int m_screenWidth;
	protected int m_screenHeight;
	protected boolean m_touchedPlayer = false;
	protected boolean m_soundPlayed = false;
	
	protected Rect boundingBox = new Rect();
	protected int rightWidthOffset = 0;
	protected int leftWidthOffset = 0;
	protected int topHeightOffset = 0;
	protected int botHeightOffset = 0;
	
	protected Paint m_debugPaint;
	
	public GObject()
	{
		this.m_position = null;
		this.m_speed = null;
		this.m_screenWidth = 0;
		this.m_screenHeight = 0;
		
		m_debugPaint = new Paint();
		m_debugPaint.setColor(Color.RED);
		m_debugPaint.setStyle(Paint.Style.STROKE);
		m_debugPaint.setStrokeWidth(10);
	}
	
	public GObject(float posX, float posY, float speedX, float speedY, int screenWidth, int screenHeight)
	{
		this.m_position = new Vector2D(posX, posY);
		this.m_speed = new Vector2D(speedX, speedY);
		this.m_screenWidth = 0;
		this.m_screenHeight = 0;
		
		m_debugPaint = new Paint();
		m_debugPaint.setColor(Color.RED);
		m_debugPaint.setStyle(Paint.Style.STROKE);
		m_debugPaint.setStrokeWidth(10);
	}
	
	public abstract void draw(Canvas canvas);
	
	public abstract void update(long elapsedTime);
	
	public Rect getBoundingBox()
	{
		return boundingBox;
	}
	
	public Vector2D getM_position()
	{
		return m_position;
	}
	
	public void setM_position(Vector2D m_position)
	{
		this.m_position = m_position;
	}
	
	public Vector2D getM_speed()
	{
		return m_speed;
	}
	
	public void setM_speed(Vector2D m_speed)
	{
		this.m_speed = m_speed;
	}
	
	public void touchedByPlayer()
	{
		m_touchedPlayer = true;
	};
}
