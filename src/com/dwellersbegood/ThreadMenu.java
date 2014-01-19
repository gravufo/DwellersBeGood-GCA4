package com.dwellersbegood;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.SurfaceHolder;


public class ThreadMenu extends Thread{
	
	private SurfaceHolder m_surfaceHolder;			// SurfaceHolder
	private HomeMenuView m_panel;					// Notre home menu
	private boolean m_run;							// Boolean qui détermine si le thread est actif ou non
    private long m_ElaspedThreadTime;
    private long m_NewTime;
    private long m_OldTime;
    private boolean m_Initialized;
		
	public ThreadMenu (SurfaceHolder surfaceHolder, HomeMenuView panel, Resources _Res) {
    	this.m_surfaceHolder = surfaceHolder;
    	this.m_panel = panel;
    	this.m_Initialized = false;
	}

	@SuppressLint("WrongCall")
	@Override
    public void run() {
        Canvas c;
        while (this.m_run) {
            c = null;
            try 
            {
            	if (this.m_Initialized == true)
            	{
	            	this.m_NewTime = System.nanoTime();
	            	this.m_ElaspedThreadTime += this.m_NewTime - this.m_OldTime;
	                c = this.m_surfaceHolder.lockCanvas(null);
	                if ((this.m_ElaspedThreadTime/1000000000.0) >= 1/60)
	                {
	                	this.m_ElaspedThreadTime = 0;
	                	synchronized (this.m_surfaceHolder) 
	                	{
	                		this.m_panel.onDraw(c);
	                	} 
	                }
	                this.m_OldTime = this.m_NewTime;
            	}
            	else
            	{
            		this.m_panel.InitializeEverything();
            	}
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
	
	public void setRunning(boolean run) {
    	this.m_run = run;
    }

	public void setInitialized(boolean _ini)
	{
		this.m_Initialized = _ini;
	}
}
