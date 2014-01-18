package com.example.dwellersbegood;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;


public class Object {
	private PointF m_position;
	private PointF m_speed;
	private int m_screenWidth;
	private int m_screenHeight;
	
	public Object(){
		this.m_position = null;
		this.m_speed = null;
		this.m_screenWidth = 0;
		this.m_screenHeight = 0;
	}
	
	public Object(float posX, float posY, float speedX, float speedY, int screenWidth, int screenHeight){
		this.m_position = null;
		this.m_speed = null;
		this.m_screenWidth = 0;
		this.m_screenHeight = 0;
	}
	
	public void draw(Canvas canvas){}
	public void update(long ellapsedTime){}
}
