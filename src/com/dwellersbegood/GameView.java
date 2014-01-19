package com.dwellersbegood;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
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
	public static float PLAYER_MIN_Y;
	public static final boolean ENABLED_DEBUG = true;
	private final int MAX_TOUCH_COUNT = 10;
	
	private static MediaPlayer mediaShooting = MediaPlayer.create(MainActivity.getContext(), R.raw.laser_good);
	
	public static Player m_player;
	
	private final GameActivity m_Activity;
	private GameThread m_Thread;
	private static int m_ScreenWidth;
	private static int m_ScreenHeight;
	private final Paint m_collectibleScorePaint;
	private int m_collectibleScore;
	private final Resources m_res;
	private BallEnemy m_enemy;
	private final ArrayList<Projectile> m_projectiles;
	private final ArrayList<EnemyProjectile> m_enemyProjectiles;
	private Map m_map;
	private final GData m_Data;
	private final int[] multiTouchX;
	private final int[] multiTouchY;
	private Rect m_screenBoundingBox;
	
	private long m_jumpHoldTime;
	private boolean m_jumpStarted;
	
	public GameView(GameActivity activity, Context context)
	{
		super(context);
		m_Activity = activity;
		m_res = m_Activity.getResources();
		getHolder().addCallback(this);
		setFocusable(true);
		this.m_projectiles = new ArrayList<Projectile>();
		this.m_enemyProjectiles = new ArrayList<EnemyProjectile>();
		
		this.m_collectibleScorePaint = new Paint();
		this.m_collectibleScorePaint.setColor(Color.RED);
		this.m_collectibleScorePaint.setTextSize(28);
		this.m_collectibleScorePaint.setTextAlign(Align.LEFT);
		
		this.m_collectibleScore = 0;
		
		this.m_jumpHoldTime = 0;
		this.m_jumpStarted = true;
		
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
		this.m_ScreenWidth = this.getWidth();
		this.m_ScreenHeight = this.getHeight();
		
		PLAYER_MIN_Y = m_ScreenHeight * 3 / 4;
		
		m_screenBoundingBox = new Rect(0, 0, this.m_ScreenWidth, this.m_ScreenHeight);
		
		// Create map
		
		m_map = new Map(this.m_ScreenWidth, this.m_ScreenHeight);
		m_player = new Player(m_ScreenWidth / 7, (float) (m_ScreenHeight * 0.10), 0, 50, m_ScreenWidth, m_ScreenHeight, m_res);
		m_enemy = new BallEnemy(800, 500, 0, 0, m_ScreenWidth, m_ScreenHeight, m_res);
		
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
		}
	}
	
	public void update(long elapsedTime)
	{
		// On update la partie si celle-ci n'est pas terminé
		m_player.setIsOnFloor(false);
		m_map.update(elapsedTime);
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
			if (m_player.getBoundingBox().intersect(segment.getBoundingBox()))
				ManageCollision(segment);
		}
		m_player.update(elapsedTime);
		m_enemy.update(elapsedTime);
		
		synchronized (this.m_projectiles)
		{
			for (Projectile projectile : this.m_projectiles)
			{
				projectile.update(elapsedTime);
				
				if (!projectile.getBoundingBox().intersect(this.m_screenBoundingBox))
				{
					this.m_projectiles.remove(this);
				}
			}
		}
		
		for (EnemyProjectile projectile : this.m_enemyProjectiles)
		{
			projectile.update(elapsedTime);
			
			if (!projectile.getBoundingBox().intersect(this.m_screenBoundingBox))
			{
				this.m_projectiles.remove(this);
			}
		}
		
		if (this.m_jumpStarted)
		{
			this.m_jumpHoldTime += elapsedTime;
			if (this.m_jumpHoldTime >= 2 * GameThread.nano)
			{
				this.m_jumpHoldTime = (long) Math.min(2 * GameThread.nano, this.m_jumpHoldTime);
				float ratio = (float) (this.m_jumpHoldTime / (2 * GameThread.nano));
				this.m_player.jumpReleased(ratio);
				this.m_jumpHoldTime = 0;
				this.m_jumpStarted = false;
			}
		}
		
		// Manage physics
		// Player should be tested with every bounding box for physic contact.
		
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
				
				// If the touch is on first half of screen, jump
				if (multiTouchX[a] < m_ScreenWidth / 2)
				{
					this.m_jumpHoldTime = System.currentTimeMillis();
					this.m_player.jumpStarted();
				}
				
				// If its on other side of screen, throw something
				else
				{
					mediaShooting.seekTo(0);
					mediaShooting.start();
					throwSomething(multiTouchX[a], multiTouchY[a]);
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
				if (multiTouchX[a] < m_ScreenWidth / 2 && this.m_jumpHoldTime > 0)
				{
					float ratio = (float) (this.m_jumpHoldTime / (2 * GameThread.nano));
					this.m_player.jumpReleased(ratio);
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
		
		// should this statement be in a synchronized too ? (it is drawn in diff thread)
		m_collectibleScore++;
		m_Data.setDolla(m_collectibleScore);
		m_Activity.setData(m_Data);
		
		synchronized (this.m_projectiles)
		{
			Rect playerBox = this.m_player.getBoundingBox();
			Vector2D target = new Vector2D(posX, posY);
			Vector2D direction = target.substract(m_player.getM_position());
			direction.normalize();
			direction = direction.multiply(1000);
			m_projectiles.add(new Projectile(m_player.getM_position().getX() + playerBox.width() / 2, m_player.getM_position().getY() + playerBox.height() / 2, direction.getX(), direction.getY(), m_ScreenWidth, m_ScreenHeight, m_res));
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
}
