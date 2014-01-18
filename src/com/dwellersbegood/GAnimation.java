package com.dwellersbegood;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class GAnimation
{
	private Bitmap m_sourceBitmap;
	private int m_frameCount; // Nombre de frames
	private int m_currentFrame; // La frame displayé
	private long m_frameTicker; // Le temps de la dernière update
	private long m_framePeriod; // milliseconds entre chaque frame (1000/fps)
	private Matrix m_mtxEffect;
	private Bitmap m_bmpToDraw;
	private ArrayList<Bitmap> m_bitmapArray;
	
	public GAnimation(Bitmap bitmap, double fps, int frameCount)
	{
		this.m_sourceBitmap = bitmap;
		this.m_mtxEffect = new Matrix();
		this.m_frameCount = frameCount;
		this.m_currentFrame = 0;
		this.m_frameTicker = 0;
		this.m_framePeriod = (long) (GameThread.nano / fps);
		this.m_bitmapArray = new ArrayList<Bitmap>();
		
		for (int i = 0; i < frameCount; i++)
		{
			
			m_bitmapArray.add(Bitmap.createBitmap(bitmap, i * (bitmap.getWidth() / frameCount), 0, (bitmap.getWidth() / frameCount), bitmap.getHeight(), this.m_mtxEffect, true));
		}
	}
	
	public void update(long elapsedTime)
	{
		
		m_frameTicker += elapsedTime;
		
		if (m_frameTicker > this.m_framePeriod)
		{
			this.m_frameTicker = 0;
			// increment the frame
			this.m_currentFrame++;
			if (this.m_currentFrame >= this.m_frameCount)
			{
				this.m_currentFrame = 0;
			}
			this.m_bmpToDraw = m_bitmapArray.get(m_currentFrame);
		}
	}
	
	public void draw(Canvas canvas, Vector2D _Position, Paint _paint)
	{
		if ((canvas != null) && (this.m_bmpToDraw != null))
		{
			canvas.drawBitmap(this.m_bmpToDraw, _Position.getX(), _Position.getY(), _paint);
		}
	}
	
	public Bitmap getBmpToDraw()
	{
		return m_bmpToDraw;
	}
	
	public int getWidth()
	{
		return this.m_sourceBitmap.getWidth() / this.m_frameCount;
	}
	
	public int getHeight()
	{
		return this.m_sourceBitmap.getHeight();
	}
	
	public void setRotation(int _Rotation)
	{
		this.m_mtxEffect.setRotate(_Rotation, this.m_sourceBitmap.getWidth() / 2, this.m_sourceBitmap.getHeight() / 2);
	}
	
	public void setFramePeriod(double fps)
	{
		this.m_framePeriod = (long) (GameThread.nano / fps);
	}
	
	public long getFramePeriod()
	{
		return this.m_framePeriod;
	}
}
