package com.dwellersbegood;
import android.graphics.Canvas;


public abstract class GObject {
	protected Vector2D m_position;
	protected Vector2D m_speed;
	protected int m_screenWidth;
	protected int m_screenHeight;
	
	public GObject(){
		this.m_position = null;
		this.m_speed = null;
		this.m_screenWidth = 0;
		this.m_screenHeight = 0;
	}
	
	public GObject(float posX, float posY, float speedX, float speedY, int screenWidth, int screenHeight){
		this.m_position = new Vector2D(posX, posY);
		this.m_speed = new Vector2D(speedX, speedY);
		this.m_screenWidth = 0;
		this.m_screenHeight = 0;
	}
	
	public abstract void draw(Canvas canvas);
	public abstract void update(long ellapsedTime);
	
	public Vector2D getPosition(){
		return m_position;
	}
	
	public Vector2D getSpeed(){
		return m_speed;
	}
}
