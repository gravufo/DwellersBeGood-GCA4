package com.dwellersbegood;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;

public class GAnimation {
	
	private Bitmap m_sourceBitmap;
	private int m_frameCount;     // Nombre de frames
	private int m_currentFrame;   // La frame displayé
    private long m_frameTicker;   // Le temps de la dernière update
    private long m_framePeriod;    // milliseconds entre chaque frame (1000/fps)
    private Matrix m_mtxEffect;
    private Bitmap m_bmpToDraw;

	public GAnimation(Bitmap bitmap, double fps, int frameCount)
	{
		this.m_sourceBitmap = bitmap;
		this.m_mtxEffect = new Matrix();
		this.m_frameCount = frameCount;
		this.m_currentFrame = 0;
		this.m_frameTicker = 0;
		this.m_framePeriod = (long) (GameThread.nano / fps);
	}
	
	public void update(long ellapsedTime) 
	{
		
		m_frameTicker += ellapsedTime;
		
		if (m_frameTicker >= this.m_framePeriod) {
			this.m_frameTicker = 0;
			 // increment the frame
			this.m_currentFrame++;
			 if (this.m_currentFrame >= this.m_frameCount) 
			 {
				 this.m_currentFrame = 0;
			 }
			 this.m_bmpToDraw = Bitmap.createBitmap(this.m_sourceBitmap, this.m_currentFrame*(this.m_sourceBitmap.getWidth()/this.m_frameCount), 0, (this.m_sourceBitmap.getWidth()/this.m_frameCount), this.m_sourceBitmap.getHeight(), this.m_mtxEffect, true);
		}
		
	}
	
	public void draw(Canvas canvas, Vector2D _Position, Paint _paint) 
	{
		if ((canvas != null) && (this.m_bmpToDraw != null))
		{
			canvas.drawBitmap(this.m_bmpToDraw, _Position.getX(), _Position.getY(), _paint);
		}
	}
	
	public Bitmap getBmpToDraw(){
		return m_bmpToDraw;
	}

	public int getWidth() {
		return this.m_sourceBitmap.getWidth()/this.m_frameCount;
	}

	public int getHeight() {
		return this.m_sourceBitmap.getHeight();
	}	
	
	public void setRotation(int _Rotation)
	{
		this.m_mtxEffect.setRotate(_Rotation, this.m_sourceBitmap.getWidth()/2, this.m_sourceBitmap.getHeight()/2);
	}
	
	public void setFramePeriod(double fps)
	{
		this.m_framePeriod = (long) (GameThread.nano / fps);
	}
	
	public long getFramePeriod(){
		return this.m_framePeriod;
	}
}