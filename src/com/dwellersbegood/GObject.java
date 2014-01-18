package com.example.dwellersbegood;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;


public abstract class GObject {
	protected PointF m_position;
	protected PointF m_speed;
	protected int m_screenWidth;
	protected int m_screenHeight;
	
	public GObject(){
		this.m_position = null;
		this.m_speed = null;
		this.m_screenWidth = 0;
		this.m_screenHeight = 0;
	}
	
	public GObject(float posX, float posY, float speedX, float speedY, int screenWidth, int screenHeight){
		this.m_position = new PointF(posX, posY);
		this.m_speed = new PointF(speedX, speedY);
		this.m_screenWidth = 0;
		this.m_screenHeight = 0;
	}
	
	public abstract void draw(Canvas canvas);
	public abstract void update(long ellapsedTime);
}
