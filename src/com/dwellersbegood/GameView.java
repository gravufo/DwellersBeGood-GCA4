package com.dwellersbegood;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.dwellersbegood.Map.EnemySegment;
import com.dwellersbegood.Map.Map;
import com.dwellersbegood.Map.MapSegment;
import com.dwellersbegood.Map.MapSegmentGenerator;

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
	
	public static final float GRAVITY = 600;
	public static float LEVEL_FLOOR = 0;
	public static float LEVEL_PLATFORM = 0;
	public static final boolean ENABLED_DEBUG = false;
	private final int MAX_TOUCH_COUNT = 10;
	
	private final int MENU = 0;
	private final int GAME = 1;
	private final int GAMEOVER = 2;
	
	private static MediaPlayer mediaShooting = MediaPlayer.create(MainActivity.getContext(), R.raw.laser_good);
	
	public static Player m_player;
	
	private final GameActivity m_Activity;
	private GameThread m_Thread;
	private static int m_ScreenWidth;
	private static int m_ScreenHeight;
	private final Paint m_collectibleScorePaint;
	private int m_collectibleScore;
	private Resources m_res;
	private ArrayList<Projectile> m_projectiles;
	private ArrayList<Projectile> m_projectilesToRemove;
	private ArrayList<EnemyProjectile> m_enemyProjectiles;
	private Map m_map;
	private final GData m_Data;
	private int[] multiTouchX;
	private int[] multiTouchY;
	private Rect m_screenBoundingBox;
	
	private long m_jumpHoldTime;
	private boolean m_jumpStarted;
	
	private long m_rechargeTime;
	private boolean m_canShoot;
	
	private Rect m_xButtonRect;
	private Bitmap m_xButtonBitmap;
	private Rect m_ResumeButtonRect;
	private Bitmap m_ResumeButtonBitmap;
	private Rect m_RestartButtonRect;
	private Bitmap m_RestartButtonBitmap;
	private Rect m_BackButtonRect;
	private Bitmap m_BackButtonBitmap;
	private Rect m_GameOverButtonRect;
	private Bitmap m_GameOverButtonBitmap;
	private Paint m_buttonPaint;
	private int m_gamestate;
	
	private boolean m_leaveGame;
	
	public GameView(GameActivity activity, Context context)
	{
		super(context);
		m_Activity = activity;
		m_res = m_Activity.getResources();
		getHolder().addCallback(this);
		setFocusable(true);
		this.m_projectiles = new ArrayList<Projectile>();
		this.m_projectilesToRemove = new ArrayList<Projectile>();
		this.m_enemyProjectiles = new ArrayList<EnemyProjectile>();
		
		this.m_collectibleScorePaint = new Paint();
		this.m_collectibleScorePaint.setColor(Color.RED);
		this.m_collectibleScorePaint.setTextSize(28);
		this.m_collectibleScorePaint.setTextAlign(Align.LEFT);
		
		this.m_collectibleScore = 0;
		
		this.m_buttonPaint = new Paint();
		this.m_buttonPaint.setColor(Color.BLACK);
		this.m_xButtonBitmap = BitmapFactory.decodeResource(m_res, R.drawable.xbutton);
		this.m_ResumeButtonBitmap = BitmapFactory.decodeResource(m_res, R.drawable.resume);
		this.m_RestartButtonBitmap = BitmapFactory.decodeResource(m_res, R.drawable.restart);
		this.m_BackButtonBitmap = BitmapFactory.decodeResource(m_res, R.drawable.back);
		this.m_GameOverButtonBitmap = BitmapFactory.decodeResource(m_res, R.drawable.gameover);
		this.m_gamestate = GAME;
		
		this.m_jumpHoldTime = 0;
		this.m_jumpStarted = true;
		
		m_leaveGame = false;
		
		mediaShooting.setLooping(false);
		mediaShooting.setVolume((float) 0.1, (float) 0.1);
		
		m_Data = m_Activity.getData();
		if (m_Data != null)
			m_collectibleScore = m_Data.getDolla();
		
		multiTouchX = new int[MAX_TOUCH_COUNT];
		multiTouchY = new int[MAX_TOUCH_COUNT];
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3)
	{
		
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		GameView.m_ScreenWidth = this.getWidth();
		GameView.m_ScreenHeight = this.getHeight();
		
		LEVEL_FLOOR = (float) (GameView.m_ScreenHeight * 0.65);
		LEVEL_PLATFORM = (float) (GameView.m_ScreenHeight * 0.60);
		
		m_screenBoundingBox = new Rect(0, 0, GameView.m_ScreenWidth, GameView.m_ScreenHeight);
		
		int titleHeight = 10;
        int newGameHeight = m_ScreenHeight/10*4;
        int exitHeight = m_ScreenHeight/10*7;
        this.m_GameOverButtonRect = new Rect(m_ScreenWidth / 2 - m_GameOverButtonBitmap.getWidth() / 2, titleHeight, m_ScreenWidth / 2 + m_GameOverButtonBitmap.getWidth() / 2, titleHeight + m_GameOverButtonBitmap.getHeight());
        this.m_ResumeButtonRect = new Rect(m_ScreenWidth / 2 - m_ResumeButtonBitmap.getWidth() / 2, newGameHeight, m_ScreenWidth / 2 + m_ResumeButtonBitmap.getWidth() / 2, newGameHeight + m_ResumeButtonBitmap.getHeight());
        this.m_RestartButtonRect = new Rect(m_ScreenWidth / 2 - m_RestartButtonBitmap.getWidth() / 2, newGameHeight, m_ScreenWidth / 2 + m_RestartButtonBitmap.getWidth() / 2, newGameHeight + m_RestartButtonBitmap.getHeight());
        this.m_BackButtonRect = new Rect(m_ScreenWidth / 2 - m_BackButtonBitmap.getWidth() / 2, exitHeight, m_ScreenWidth / 2 + m_BackButtonBitmap.getWidth() / 2, exitHeight + m_BackButtonBitmap.getHeight());
		
		this.m_xButtonRect = new Rect(m_ScreenWidth - m_xButtonBitmap.getWidth() - 10, 10, m_ScreenWidth - 10, 10 + m_xButtonBitmap.getHeight());
		
		// Create map
		m_map = new Map(GameView.m_ScreenWidth, GameView.m_ScreenHeight);
		m_player = new Player(m_ScreenWidth / 7, (float) (m_ScreenHeight * 0.10), 0, 50, m_ScreenWidth, m_ScreenHeight, m_res);
		
		Log.d("GameView", "Starting thread");
		this.m_Thread = new GameThread(this, holder);
		this.m_Thread.setRunning(true);
		this.m_Thread.start();
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		boolean retry = true;
		this.m_Thread.setRunning(false);
		while (retry)
		{
			try
			{
				this.m_Thread.join();
				retry = false;
			}
			catch (InterruptedException e)
			{
				// we will try it again and again...
			}
		}
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
			m_map.draw(canvas);
			
			m_player.draw(canvas);
			// m_enemy.draw(canvas);
			
			synchronized (this.m_projectiles)
			{
				for (Projectile projectile : this.m_projectiles)
				{
					projectile.draw(canvas);
				}
			}
			
			for (EnemyProjectile projectile : this.m_enemyProjectiles)
			{
				projectile.draw(canvas);
			}
			
			canvas.drawText(this.m_collectibleScore + "", 100, 40, this.m_collectibleScorePaint);
			
			canvas.drawBitmap(m_xButtonBitmap, null, m_xButtonRect, m_buttonPaint);
			
			if (m_gamestate == MENU)
			{
				canvas.drawColor(Color.argb(200, 0, 0, 0));
				
				canvas.drawBitmap(m_ResumeButtonBitmap, null, this.m_ResumeButtonRect, this.m_buttonPaint);
				canvas.drawBitmap(m_BackButtonBitmap, null, this.m_BackButtonRect, this.m_buttonPaint);
			}
			if (m_gamestate == GAMEOVER)
			{
				canvas.drawColor(Color.argb(200, 0, 0, 0));
				canvas.drawBitmap(m_GameOverButtonBitmap, null, this.m_GameOverButtonRect, this.m_buttonPaint);
				canvas.drawBitmap(m_RestartButtonBitmap, null, this.m_RestartButtonRect, this.m_buttonPaint);
				canvas.drawBitmap(m_BackButtonBitmap, null, this.m_BackButtonRect, this.m_buttonPaint);
			}
		}
	}
	
	public void update(long elapsedTime)
	{
		// On update la partie si celle-ci n'est pas terminé
		if (m_gamestate == GAME)
		{
			if (m_player.getM_position().getY() > this.m_ScreenHeight)
				m_gamestate = GAMEOVER;
			m_player.setIsOnFloor(false);
			m_player.setIsOnPlatform(false);
			m_map.update(elapsedTime);
			Vector2D playerPos = new Vector2D(m_player.getM_position().getX() + m_player.getBoundingBox().width() / 2, m_player.getM_position().getY() + m_player.getBoundingBox().height());
			for (MapSegment segment : m_map.getMapSegments())
			{
				if (segment.getM_Type() == MapSegmentGenerator.Enemy)
				{
					EnemySegment enemy = (EnemySegment) segment;
					if (enemy.ShouldDrawShot())
					{
						Vector2D target = GameView.m_player.getM_position();
						Vector2D direction = target.substract(enemy.getM_position());
						direction.normalize();
						direction = direction.multiply(1000);
						m_enemyProjectiles.add(new EnemyProjectile(enemy.getM_position().getX(), enemy.getM_position().getY() + enemy.getBoundingBox().height() / 2, direction.getX(), direction.getY(), m_ScreenWidth, m_ScreenHeight, m_res));
						enemy.resetShotTimer();
					}
				}
				
				switch (segment.getM_Type())
				{
				case MapSegmentGenerator.Floor:
					if (segment.getBoundingBox().contains((int) playerPos.getX(), (int) playerPos.getY()))
					{
						m_player.setIsOnFloor(true);
					}
					break;
				case MapSegmentGenerator.Platform:
					if (segment.getBoundingBox().contains((int) playerPos.getX(), (int) playerPos.getY()))
					{
						m_player.setIsOnPlatform(true, segment.getBoundingBox().top);
					}
					break;
				case MapSegmentGenerator.Fire:
				case MapSegmentGenerator.Rock:
				case MapSegmentGenerator.Coin:
				case MapSegmentGenerator.Enemy:
					if (segment.getBoundingBox().intersect(m_player.getBoundingBox()))
						segment.touchedByPlayer();
					break;
				case MapSegmentGenerator.HoleBegining:
					// GameView.m_player.setIsOnFloor(true);
					break;
				case MapSegmentGenerator.HoleMiddle:
					break;
				case MapSegmentGenerator.HoleEnding:
					// GameView.m_player.setIsOnFloor(true);
					break;
				}
			}
			m_player.update(elapsedTime);
			
			synchronized (this.m_projectiles)
			{
				for (Projectile projectile : this.m_projectiles)
				{
					projectile.update(elapsedTime);
					
					if (projectile.getM_position().getX() > this.m_ScreenWidth)
					{
						synchronized (this.m_projectiles)
						{
							this.m_projectilesToRemove.add(projectile);
						}
					}
				}
			}
			
			synchronized (this.m_projectiles)
			{
				for (Projectile projectile : this.m_projectilesToRemove)
				{
					this.m_projectiles.remove(projectile);
				}
				this.m_projectilesToRemove.clear();
			}
			
			for (EnemyProjectile projectile : this.m_enemyProjectiles)
			{
				projectile.update(elapsedTime);
				
				if (projectile.getM_position().getX() > this.m_ScreenWidth)
				{
					this.m_enemyProjectiles.remove(projectile);
				}
			}
			
			if (this.m_jumpStarted)
			{
				this.m_jumpHoldTime += elapsedTime;
				if (this.m_jumpHoldTime >= 2 * GameThread.nano)
				{
					this.m_jumpHoldTime = (long) Math.min(2 * GameThread.nano, this.m_jumpHoldTime);
					float ratio = (float) (this.m_jumpHoldTime / (2 * GameThread.nano));
					GameView.m_player.jumpReleased(ratio);
					this.m_jumpHoldTime = 0;
					this.m_jumpStarted = false;
				}
			}
			
			if (!m_canShoot)
			{
				this.m_rechargeTime += elapsedTime;
				if (this.m_rechargeTime >= 1 * GameThread.nano)
				{
					m_canShoot = true;
					this.m_rechargeTime = 0;
				}
			}
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
				Log.d("GameView", "OnTouchEvent at " + (int) event.getX() + ", " + (int) event.getY());
				
				Log.d("GameView", "Number of pointers " + event.getPointerCount());
				
				multiTouchX[a] = (int) event.getX(a);
				multiTouchY[a] = (int) event.getY(a);
				
				if (m_gamestate == GAME)
				{
					if (m_xButtonRect.contains(multiTouchX[a], multiTouchY[a]))
					{
						m_gamestate = MENU;
					}
					else if (multiTouchX[a] < m_ScreenWidth / 2)
					{
						this.m_jumpHoldTime = System.currentTimeMillis();
						GameView.m_player.jumpStarted();
					}
					else
					{
						throwSomething(multiTouchX[a], multiTouchY[a]);
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
						Intent i = new Intent(m_Activity,MainActivity.class);

						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
						m_Activity.startActivity(i);

						m_Activity.finish();
					}
				}
				else if (m_gamestate == GAMEOVER)
				{
					if (m_RestartButtonRect.contains(multiTouchX[a], multiTouchY[a]))
					{
						m_gamestate = GAME;
					}
					else if (m_BackButtonRect.contains(multiTouchX[a], multiTouchY[a]))
					{
						Intent i = new Intent(m_Activity,MainActivity.class);

						  i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
						  m_Activity.startActivity(i);

						m_Activity.finish();
					}
				}
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			for (int a = 0; a < event.getPointerCount(); a++)
			{
				
				Log.d("GameView", "OnTouchEvent at " + (int) event.getX() + ", " + (int) event.getY());
				
				Log.d("GameView", "Number of pointers " + event.getPointerCount());
				
				multiTouchX[a] = (int) event.getX(a);
				multiTouchY[a] = (int) event.getY(a);
				
				// If the touch is on first half of screen, jump
				if (multiTouchX[a] < m_ScreenWidth / 2 && this.m_jumpHoldTime > 0 && m_gamestate == GAME)
				{
					float ratio = (float) (this.m_jumpHoldTime / (2 * GameThread.nano));
					GameView.m_player.jumpReleased(ratio);
					this.m_jumpHoldTime = 0;
					this.m_jumpStarted = false;
				}
			}
			
		default:
		}
		return true;
	}
	
	public void throwSomething(int posX, int posY)
	{
		
		if (m_canShoot)
		{
			m_collectibleScore++;
			m_Data.setDolla(m_collectibleScore);
			m_Activity.setData(m_Data);
			
			synchronized (this.m_projectiles)
			{
				Rect playerBox = GameView.m_player.getBoundingBox();
				Vector2D target = new Vector2D(posX, posY-(BitmapManager.getInstance().getBitmap(BitmapManager.Laser2).getHeight()));
				Vector2D direction = target.substract(new Vector2D(m_player.getM_position().getX() + playerBox.width() / 2, m_player.getM_position().getY() + playerBox.height() / 2));
				direction.normalize();
				direction = direction.multiply(1000);
				m_projectiles.add(new Projectile(m_player.getM_position().getX() + playerBox.width() / 2, m_player.getM_position().getY() + playerBox.height() / 2, direction.getX(), direction.getY(), m_ScreenWidth, m_ScreenHeight, m_res));
			}
			mediaShooting.seekTo(0);
			mediaShooting.start();
			m_canShoot = false;
		}
	}
	
	public static Vector2D getScreenSize()
	{
		return new Vector2D(m_ScreenWidth, m_ScreenHeight);
	}
	
	public void ManageCollision(MapSegment segment)
	{
		switch (segment.getM_Type())
		{
		case MapSegmentGenerator.Floor:
			GameView.m_player.setIsOnFloor(true);
			break;
		case MapSegmentGenerator.Platform:
			GameView.m_player.setIsOnFloor(true);
			break;
		case MapSegmentGenerator.Fire:
			break;
		case MapSegmentGenerator.Rock:
			break;
		case MapSegmentGenerator.Coin:
			break;
		case MapSegmentGenerator.Enemy:
			break;
		case MapSegmentGenerator.HoleBegining:
			GameView.m_player.setIsOnFloor(true);
			break;
		case MapSegmentGenerator.HoleMiddle:
			break;
		case MapSegmentGenerator.HoleEnding:
			GameView.m_player.setIsOnFloor(true);
			break;
		}
	}
	
	public void setPause(boolean pause){
		m_Thread.setRunning(!pause);
	}
}
