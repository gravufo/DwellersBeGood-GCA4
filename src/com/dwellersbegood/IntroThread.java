package com.dwellersbegood;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class IntroThread extends Thread
{
	
	private final SurfaceHolder m_surfaceHolder; // SurfaceHolder
	private final IntroView m_panel; // Notre home menu
	private boolean m_run; // Boolean qui détermine si le thread est actif ou non
	private long m_ElapsedThreadTime;
	private long m_IntroTime;
	private long m_ShadingTime;
	private long m_NewTime;
	private long m_OldTime;
	
	public IntroThread(SurfaceHolder surfaceHolder, IntroView panel)
	{
		this.m_surfaceHolder = surfaceHolder;
		this.m_panel = panel;
		this.m_IntroTime = -System.currentTimeMillis();
		this.m_ElapsedThreadTime = -System.currentTimeMillis();
		this.m_ShadingTime = -System.currentTimeMillis();
	}
	
	@Override
	public void run()
	{
		Canvas c;
		while (this.m_run)
		{
			c = null;
			try
			{
				this.m_NewTime = System.currentTimeMillis();
				this.m_ElapsedThreadTime += this.m_NewTime - this.m_OldTime;
				this.m_IntroTime += this.m_NewTime - this.m_OldTime;
				this.m_ShadingTime += this.m_NewTime - this.m_OldTime;
				c = this.m_surfaceHolder.lockCanvas(null);
				if ((this.m_ElapsedThreadTime / 1000.0) >= 1 / 60)
				{
					this.m_ElapsedThreadTime = 0;
					synchronized (this.m_surfaceHolder)
					{
						this.m_panel.draw(c);
					}
				}
				if (this.m_ShadingTime / 1000.0 >= 0.01f)
				{
					this.m_panel.Update();
					this.m_ShadingTime = 0;
				}
				if (this.m_IntroTime / 1000.0 >= 3.0f)
				{
					this.m_panel.FinishIntro();
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