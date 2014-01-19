package com.dwellersbegood;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class HomeMenuView extends SurfaceView implements SurfaceHolder.Callback{
	
	private Bitmap soundon;
	private Bitmap soundoff;
	private Bitmap m_NewGameBitmap;
	private Bitmap m_TitleBitmap;
	private Bitmap m_ExitBitmap;
	private Resources m_Res;
	private int m_ScreenWidth;
	private int m_ScreenHeight;
	private ThreadMenu m_Thread;
	private MainActivity m_Activity;
	private Rect m_ExitRect;
	private Rect m_NewGameRect;
	private Rect m_btnSoundRect;
	private Rect m_TitleRect;
	private float m_ScaleWidth;
	private float m_ScaleHeight;
	private Paint m_Paint;
	private boolean m_SoundOff;
	private Paint m_TestPaint;
	
	public HomeMenuView(Context context,MainActivity _Activity, Resources _Res)
	{
		super(context);
		this.m_Res = _Res;
		getHolder().addCallback(this);
	    setFocusable(true);
		this.m_Activity = _Activity;
		this.m_Activity.setContentView(this);
		this.m_Paint = new Paint();
		this.m_Paint.setColor(Color.BLACK);
		this.m_SoundOff = false;
		
		this.m_TestPaint = new Paint();
		this.m_TestPaint.setColor(Color.BLACK);
		this.m_TestPaint.setAlpha(150);
		
	}

	@Override
	// On dessine la background
	public void onDraw(Canvas canvas) {
		if (canvas != null)
		{
			canvas.drawColor(Color.BLACK);
			if(m_SoundOff)
				canvas.drawBitmap(soundoff, null, m_btnSoundRect, m_Paint);
			else
				canvas.drawBitmap(soundon, null, m_btnSoundRect, m_Paint);
			
			canvas.drawBitmap(m_TitleBitmap, null, m_TitleRect, m_Paint);
			canvas.drawBitmap(m_NewGameBitmap, null, m_NewGameRect, m_Paint);
			canvas.drawBitmap(m_ExitBitmap, null, m_ExitRect, m_Paint);
			this.postInvalidate();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	// On applique un scale à notre image en fonction de la taille de l'écran par rapport à la taille de l'image d'origine
	public void surfaceCreated(SurfaceHolder holder) {

		this.m_ScreenWidth = this.getWidth();
	    this.m_ScreenHeight = this.getHeight();		
	    
		this.m_Thread = new ThreadMenu(getHolder(), this, this.m_Res);
		this.m_Thread.setRunning(true);
		this.m_Thread.start();
	}

	public void InitializeEverything()
	{
		if ((this.m_ScreenWidth != 0) && (this.m_ScreenHeight != 0))
		{
			soundon = BitmapFactory.decodeResource(m_Res, R.drawable.soundon);
			soundoff = BitmapFactory.decodeResource(m_Res, R.drawable.soundoff);
			m_NewGameBitmap = BitmapManager.getInstance().scaleToSizeByHeight(BitmapFactory.decodeResource(m_Res, R.drawable.startgame), 0.2f, m_ScreenHeight);
			m_TitleBitmap = BitmapManager.getInstance().scaleToSizeByHeight(BitmapFactory.decodeResource(m_Res, R.drawable.titre_aztek), 0.3f, m_ScreenHeight);
			m_ExitBitmap = BitmapManager.getInstance().scaleToSizeByHeight(BitmapFactory.decodeResource(m_Res, R.drawable.quit), 0.2f, m_ScreenHeight);
		    
	        int titleHeight = 10;
	        int newGameHeight = m_ScreenHeight/10*4;
	        int exitHeight = m_ScreenHeight/10*7;
	        
	        this.m_btnSoundRect = new Rect(m_ScreenWidth - soundoff.getWidth() - 10, 10, m_ScreenWidth - 10, 10 + soundoff.getHeight());

	        this.m_TitleRect = new Rect(m_ScreenWidth / 2 - m_TitleBitmap.getWidth() / 2, titleHeight, m_ScreenWidth / 2 + m_TitleBitmap.getWidth() / 2, titleHeight + m_TitleBitmap.getHeight());
			this.m_NewGameRect = new Rect(m_ScreenWidth / 2 - m_NewGameBitmap.getWidth() / 2, newGameHeight, m_ScreenWidth / 2 + m_NewGameBitmap.getWidth() / 2, newGameHeight + m_NewGameBitmap.getHeight());
	        this.m_ExitRect = new Rect(m_ScreenWidth / 2 - m_ExitBitmap.getWidth() / 2, exitHeight, m_ScreenWidth / 2 + m_ExitBitmap.getWidth() / 2, exitHeight + m_ExitBitmap.getHeight());

			this.m_Thread.setInitialized(true);
		
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		switch (event.getActionMasked())
		{
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			for (int a = 0; a < event.getPointerCount(); a++)
			{
				Log.d("Main Menu", "OnTouchEvent at " + (int) event.getX() + ", " + (int) event.getY());
				
				Log.d("Main Menu", "Number of pointers " + event.getPointerCount());
				
				if (m_btnSoundRect.contains((int) event.getX(a), (int) event.getY(a)))
				{
					m_Activity.muteMedia();
					m_SoundOff = !m_SoundOff;
				}
				else if (m_NewGameRect.contains((int) event.getX(a), (int) event.getY(a)))
				{
					this.m_Activity.SingleplayerMode();
				}
				else if (m_ExitRect.contains((int) event.getX(a), (int) event.getY(a)))
				{
					m_Activity.finish();
				}
			}
			break;
		default:
		}
		return true;
	}
	
	@Override
	// On détruit le thread lorsque le view est détruit
	public void surfaceDestroyed(SurfaceHolder arg0) {
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

	public Rect getExitRect() {
		return m_ExitRect;
	}

	public Rect getNewGameRect() {
		return m_NewGameRect;
	}
	
	public void setSoundOff(boolean _SoundOn)
	{
		this.m_SoundOff = _SoundOn;
	}
}
