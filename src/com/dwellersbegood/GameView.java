package com.dwellersbegood;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
	public static final int MENU = 0;
	public static final int GAME = 1;
	public static final int GAMEOVER = 2;
	
	public static boolean ENABLED_DEBUG = false;
	
	private GameActivity m_Activity;

	public static int g_ScreenWidth;
	public static int g_ScreenHeight;
	
	private HUD m_hud;
	
	private Resources m_res;
	public Resources getResources(){return m_res;}

	private GData m_Data;
	
	private int[] multiTouchX;
	private int[] multiTouchY;
	private final int MAX_TOUCH_COUNT = 10;
	
	private Rect m_xButtonRect;
	private Bitmap m_xButtonBitmap;
	private Rect m_ResumeButtonRect;
	private Bitmap m_ResumeButtonBitmap;
	private Rect m_RestartButtonRect;
	private Bitmap m_RestartButtonBitmap;
	private Rect m_BackButtonRect;
	private Bitmap m_BackButtonBitmap;
	private Rect m_GameOverButtonRect;
	private Bitmap m_SoundOnButtonBitmap;
	private Bitmap m_SoundOffButtonBitmap;
	private Rect m_SoundButtonRect;
	private Bitmap m_GameOverButtonBitmap;
	private Paint m_buttonPaint;
	
	private int m_gamestate;
	public int getGameState(){return m_gamestate;}
	public void setGameState(int gameState){m_gamestate = gameState;}
	
	private Game m_game;
	
	public GameView(GameActivity activity, Context context)
	{
		super(context);
		m_Activity = activity;
		m_res = m_Activity.getResources();
		getHolder().addCallback(this);
		setFocusable(true);
		
		m_game = new Game(this);
		
		this.m_hud = new HUD(this);
		
		this.m_buttonPaint = new Paint();
		this.m_buttonPaint.setColor(Color.BLACK);
		this.m_xButtonBitmap = BitmapManager.getInstance().getBitmap(BitmapManager.XButton);
		this.m_ResumeButtonBitmap = BitmapManager.getInstance().getBitmap(BitmapManager.ResumeButton);
		this.m_RestartButtonBitmap = BitmapManager.getInstance().getBitmap(BitmapManager.RestartButton);
		this.m_BackButtonBitmap = BitmapManager.getInstance().getBitmap(BitmapManager.BackButton);
		this.m_GameOverButtonBitmap = BitmapManager.getInstance().getBitmap(BitmapManager.GameOver);
		this.m_SoundOnButtonBitmap = BitmapFactory.decodeResource(m_res, R.drawable.soundon);
		this.m_SoundOffButtonBitmap = BitmapFactory.decodeResource(m_res, R.drawable.soundoff);
		
		this.m_gamestate = GAME;
		
		multiTouchX = new int[MAX_TOUCH_COUNT];
		multiTouchY = new int[MAX_TOUCH_COUNT];
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3){}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		GameView.g_ScreenWidth = this.getWidth();
		GameView.g_ScreenHeight = this.getHeight();
		
		m_game.InitializeGame(holder);
		
		int titleHeight = 10;
		
		int newGameHeight = g_ScreenHeight / 10 * 4;
		int exitHeight = g_ScreenHeight / 10 * 7;
		this.m_GameOverButtonRect = new Rect(g_ScreenWidth / 2 - m_GameOverButtonBitmap.getWidth() / 2, titleHeight, g_ScreenWidth / 2 + m_GameOverButtonBitmap.getWidth() / 2, titleHeight + m_GameOverButtonBitmap.getHeight());
		this.m_ResumeButtonRect = new Rect(g_ScreenWidth / 2 - m_ResumeButtonBitmap.getWidth() / 2, newGameHeight, g_ScreenWidth / 2 + m_ResumeButtonBitmap.getWidth() / 2, newGameHeight + m_ResumeButtonBitmap.getHeight());
		this.m_RestartButtonRect = new Rect(g_ScreenWidth / 2 - m_RestartButtonBitmap.getWidth() / 2, newGameHeight, g_ScreenWidth / 2 + m_RestartButtonBitmap.getWidth() / 2, newGameHeight + m_RestartButtonBitmap.getHeight());
		this.m_BackButtonRect = new Rect(g_ScreenWidth / 2 - m_BackButtonBitmap.getWidth() / 2, exitHeight, g_ScreenWidth / 2 + m_BackButtonBitmap.getWidth() / 2, exitHeight + m_BackButtonBitmap.getHeight());
		
		this.m_SoundButtonRect = new Rect(10, g_ScreenHeight - this.m_SoundOnButtonBitmap.getHeight() - 10, 10 + this.m_SoundOnButtonBitmap.getWidth(), g_ScreenHeight - 10);
		
		this.m_xButtonRect = new Rect(g_ScreenWidth - m_xButtonBitmap.getWidth() - 10, 10, g_ScreenWidth - 10, 10 + m_xButtonBitmap.getHeight());
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		m_game.TerminateGame();
	}
	
	@Override
	// On draw tout les composant graphiques
	public void onDraw(Canvas canvas)
	{
		if (canvas != null)
		{
			// Log.d("GameView", "Drawing");
			// Dessinage de la scene
			
			canvas.drawColor(Color.WHITE);

			m_game.DrawGame(canvas);
			
			m_hud.DrawHUD(canvas);
			
			canvas.drawBitmap(m_xButtonBitmap, null, m_xButtonRect, m_buttonPaint);
			
			if (m_gamestate == MENU)
			{
				canvas.drawColor(Color.argb(200, 0, 0, 0));
				
				canvas.drawBitmap(m_ResumeButtonBitmap, null, this.m_ResumeButtonRect, this.m_buttonPaint);
				canvas.drawBitmap(m_BackButtonBitmap, null, this.m_BackButtonRect, this.m_buttonPaint);
				if(m_game.IsMusicPlaying())
					canvas.drawBitmap(m_SoundOnButtonBitmap, null, this.m_SoundButtonRect, this.m_buttonPaint);
				else
					canvas.drawBitmap(m_SoundOffButtonBitmap, null, this.m_SoundButtonRect, this.m_buttonPaint);
			}
			if (m_gamestate == GAMEOVER)
			{
				canvas.drawColor(Color.argb(200, 0, 0, 0));
				canvas.drawBitmap(m_GameOverButtonBitmap, null, this.m_GameOverButtonRect, this.m_buttonPaint);
				canvas.drawBitmap(m_RestartButtonBitmap, null, this.m_RestartButtonRect, this.m_buttonPaint);
				canvas.drawBitmap(m_BackButtonBitmap, null, this.m_BackButtonRect, this.m_buttonPaint);
				if(m_game.IsMusicPlaying())
					canvas.drawBitmap(m_SoundOnButtonBitmap, null, this.m_SoundButtonRect, this.m_buttonPaint);
				else
					canvas.drawBitmap(m_SoundOffButtonBitmap, null, this.m_SoundButtonRect, this.m_buttonPaint);
			}
		}
	}
	
	public void update(long elapsedTime)
	{
		// On update la partie si celle-ci n'est pas termin�
		if (m_gamestate == GAME)
		{
			m_game.UpdateGame(elapsedTime);
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
				multiTouchX[a] = (int) event.getX(a);
				multiTouchY[a] = (int) event.getY(a);
				
				if (m_gamestate == GAME)
				{
					if (m_xButtonRect.contains(multiTouchX[a], multiTouchY[a]))
					{
						m_gamestate = MENU;
					}
					else if (multiTouchX[a] < g_ScreenWidth / 2)
					{
						m_game.getPlayer().jumpStarted();
					}
					else
					{
						m_game.fireAtTarget(multiTouchX[a], multiTouchY[a]);
					}
				}
				else if (m_gamestate == MENU)
				{
					if (m_xButtonRect.contains(multiTouchX[a], multiTouchY[a]))
					{
						m_gamestate = GAME;
					}
					else if (m_ResumeButtonRect.contains(multiTouchX[a], multiTouchY[a]))
					{
						m_gamestate = GAME;
					}
					else if (m_BackButtonRect.contains(multiTouchX[a], multiTouchY[a]))
					{
						// Return to main application
						
						m_Activity.finish();
					}
					else if(m_SoundButtonRect.contains(multiTouchX[a], multiTouchY[a])){
						soundButtonPressed();
					}
				}
				else if (m_gamestate == GAMEOVER)
				{
					if (m_RestartButtonRect.contains(multiTouchX[a], multiTouchY[a]))
					{
						m_Activity.reset();
					}
					else if (m_BackButtonRect.contains(multiTouchX[a], multiTouchY[a]))
					{
						
						m_Activity.finish();
					}
					else if(m_SoundButtonRect.contains(multiTouchX[a], multiTouchY[a])){
						soundButtonPressed();
					}
				}
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			for (int a = 0; a < event.getPointerCount(); a++)
			{
				multiTouchX[a] = (int) event.getX(a);
				multiTouchY[a] = (int) event.getY(a);
			}
			
		default:
		}
		return true;
	}
	
	public void soundButtonPressed()
	{
		if(m_game.IsMusicPlaying()){
			this.m_Activity.pauseMusic();
			m_game.SetMusicPlaying(false);
		}
		else{
			this.m_Activity.startMusic();
			m_game.SetMusicPlaying(true);
		}
	}
	
	public static Vector2D getScreenSize()
	{
		return new Vector2D(g_ScreenWidth, g_ScreenHeight);
	}
	
	public Game getGame(){
		return m_game;
	}
}
