package com.dwellersbegood;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class GAnimation
{
	protected Bitmap m_sourceBitmap;
	protected int m_frameCount; // Nombre de frames
	protected int m_currentFrame; // La frame display�
	protected long m_frameTicker; // Le temps de la derni�re update
	protected long m_framePeriod; // milliseconds entre chaque frame (1000/fps)
	protected Matrix m_mtxEffect;
	protected Bitmap m_bmpToDraw;
	protected ArrayList<Bitmap> m_bitmapArray;
	protected boolean m_animateOnce;
	protected boolean m_done;
	protected boolean m_pause;
	
	public GAnimation(Bitmap bitmap, double fps, int frameCount)
	{
		this(bitmap, fps, frameCount, false);
	}
	
	public GAnimation(Bitmap bitmap, double fps, int frameCount, boolean animateOnce)
	{
		this.m_sourceBitmap = bitmap;
		this.m_mtxEffect = new Matrix();
		this.m_frameCount = frameCount;
		this.m_currentFrame = 0;
		this.m_frameTicker = 0;
		this.m_framePeriod = (long) (GameThread.nano / fps);
		this.m_bitmapArray = new ArrayList<Bitmap>();
		this.m_animateOnce = animateOnce;
		this.m_done = false;
		this.m_pause = false;
		
		for (int i = 0; i < frameCount; i++)
		{
			m_bitmapArray.add(Bitmap.createBitmap(bitmap, i * (bitmap.getWidth() / frameCount), 0, (bitmap.getWidth() / frameCount), bitmap.getHeight(), this.m_mtxEffect, true));
		}
		this.m_bmpToDraw = m_bitmapArray.get(0);
	}
	
	public void update(long elapsedTime)
	{
		m_frameTicker += elapsedTime;
		
		if (m_frameTicker > this.m_framePeriod && !m_done)
		{
			this.m_frameTicker = 0;
			// increment the frame
			this.m_currentFrame++;
			if (this.m_currentFrame >= this.m_frameCount)
			{
				if(this.m_animateOnce){
					this.m_done = true;
					this.m_currentFrame = this.m_frameCount - 1;
				}
				else
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
	
	public long getCurrentFrame()
	{
		return this.m_currentFrame;
	}
	
	public boolean getDone()
	{
		return this.m_done;
	}
	public void reset(){
		m_frameTicker = 0;
		m_currentFrame = 0;
		m_done = false;
	}
}
