package com.dwellersbegood;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread
{
	public static final double nano = 1000000000.0;
	private boolean m_run = false;
	private long m_ElapsedThreadTime;
	private long m_NewTime;
	private long m_OldTime;
	private final GameView m_gameView;
	
	private final SurfaceHolder m_surfaceHolder;
	
	public GameThread(GameView gameView, SurfaceHolder surfaceHolder)
	{
		this.m_NewTime = System.nanoTime();
		this.m_OldTime = m_NewTime;
		this.m_gameView = gameView;
		this.m_surfaceHolder = surfaceHolder;
	}
	
	@SuppressLint("WrongCall")
	@Override
	public void run()
	{
		Canvas c;
		while (this.m_run)
		{
			c = null;
			try
			{
				this.m_NewTime = System.nanoTime();
				this.m_ElapsedThreadTime = this.m_NewTime - this.m_OldTime;
				
				c = this.m_surfaceHolder.lockCanvas(null);
				// if ((this.m_ElaspedThreadTime/1000000000.0) >= 1/60)
				if ((this.m_ElapsedThreadTime / nano) >= 1 / 60)
				{
					
					synchronized (this.m_surfaceHolder)
					{
						
						// On dessine et on update notre panel
						this.m_gameView.update(m_ElapsedThreadTime);
						this.m_gameView.onDraw(c);
					}
				}
				this.m_OldTime = this.m_NewTime;
			}
			finally
			{
				// do this in a finally so that if an exception is thrown
				// during the above, we don't leave the Surface in an
				// inconsistent state
				if (c != null)
				{
					this.m_surfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}
	
	public void setRunning(boolean run)
	{
		this.m_run = run;
	}
	
}
