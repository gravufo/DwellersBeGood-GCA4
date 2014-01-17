package com.example.dwellersbegood;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
	
	private GameActivity m_Activity;
	private GameThread m_Thread;
	private int m_ScreenWidth;
	private int m_ScreenHeight;
	private int posX, posY;
	private Paint paint;

	public GameView(GameActivity activity, Context context) {
		super(context);
		m_Activity = activity;
		getHolder().addCallback(this);
		setFocusable(true);
		posX = posY = 0;
		
		paint = new Paint();
		paint.setColor(Color.BLACK);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.m_ScreenWidth = this.getWidth();
	    this.m_ScreenHeight = this.getHeight();
	    
	    Log.d("GameView", "Starting thread");
	    this.m_Thread = new GameThread(this, holder);
		this.m_Thread.setRunning(true);
		this.m_Thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		this.m_Thread.setRunning(false);
		while (retry) {
			try {
				this.m_Thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// we will try it again and again...
			}
		}
	}
	
	@Override
	// On draw tout les composant graphiques
	public void onDraw(Canvas canvas) {
		if (canvas != null)
		{
			Log.d("GameView", "Drawing");
			// Dessinage de la scene
			
			canvas.drawColor(Color.WHITE);
			canvas.drawCircle(posX, posY, 15, paint);
		}
	}
	 
	public void Update()
	{
		// On update la partie si celle-ci n'est pas terminé

	}
	 
	
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		Log.d("GameView", "OnTouchEvent at " + (int)event.getX() + ", " + (int)event.getY());
		if (event.getAction() == MotionEvent.ACTION_DOWN) 
		{
			posX = (int)event.getX();
			posY = (int)event.getY();
		}
		return super.onTouchEvent(event);
	}

}
