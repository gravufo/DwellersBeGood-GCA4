package com.dwellersbegood;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceHolder;

import com.dwellersbegood.Achievements.Achievement;
import com.dwellersbegood.Map.EnemySegment;
import com.dwellersbegood.Map.Map;
import com.dwellersbegood.Map.MapSegment;
import com.dwellersbegood.Map.MapSegmentGenerator;

public class Game
{
	public static final float GRAVITY = 1000;
	public static float LEVEL_FLOOR = 0;
	public static float LEVEL_PLATFORM = 0;
	public static final boolean ENABLED_DEBUG = false;
	private final float RELOADINGTIME = 1;
	
	private static MediaPlayer mediaShooting = MediaPlayer.create(MainActivity.getContext(), R.raw.laser_good);
	
	private Player m_player;
	public Player getPlayer(){return m_player;}
	
	private GameThread m_Thread;
	private ArrayList<Projectile> m_projectiles;
	private ArrayList<Projectile> m_projectilesToRemove;
	private ArrayList<EnemyProjectile> m_enemyProjectiles;
	private ArrayList<EnemyProjectile> m_enemyProjectilesToRemove;
	private Map m_map;
	
	private long m_rechargeTime;
	private boolean m_canShoot;
	
	private boolean m_musicPlaying;
	
	private Paint m_floorDebugPaint;
	
	private GameView m_gameView;
	
	private int m_coinsCollected;
	public int getCoinsCollected(){return m_coinsCollected;}
	
	private int m_distanceTraveled;
	public int getDistanceTraveled(){return m_distanceTraveled;}
	public void addDistanceTraveled(int distanceTraveled){m_distanceTraveled += distanceTraveled;}
	
	private Achievement testAchievement;
	
	public Game(GameView view){
		this.m_gameView = view;
		
		m_coinsCollected = 0;
		m_distanceTraveled = 0;
		
		this.m_projectiles = new ArrayList<Projectile>();
		this.m_projectilesToRemove = new ArrayList<Projectile>();
		this.m_enemyProjectiles = new ArrayList<EnemyProjectile>();
		this.m_enemyProjectilesToRemove = new ArrayList<EnemyProjectile>();
		
		m_musicPlaying = true;
		
		mediaShooting.setLooping(false);
		mediaShooting.setVolume((float) 0.1, (float) 0.1);
		
		this.m_floorDebugPaint = new Paint();
		this.m_floorDebugPaint.setColor(Color.BLUE);
		this.m_floorDebugPaint.setStrokeWidth(5);
	}
	
	public void InitializeGame(SurfaceHolder holder){
		GameView.g_ScreenWidth = m_gameView.getWidth();
		GameView.g_ScreenHeight = m_gameView.getHeight();
		
		LEVEL_FLOOR = (float) (GameView.g_ScreenHeight * 0.70);
		LEVEL_PLATFORM = (float) (GameView.g_ScreenHeight * 0.60);
		
		// Create map
		m_map = new Map(this);
		m_player = new Player(GameView.g_ScreenWidth / 7, (float) (GameView.g_ScreenHeight * 0.10), 0, 50, GameView.g_ScreenWidth, GameView.g_ScreenHeight);
		
		testAchievement = new Achievement(m_gameView, "You have reached 20M");
		
		Log.d("GameView", "Starting thread");
		this.m_Thread = new GameThread(m_gameView, holder);
		this.m_Thread.setRunning(true);
		this.m_Thread.start();
	}
	
	public void TerminateGame(){
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
	
	public void DrawGame(Canvas canvas){
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
		
		testAchievement.Draw(canvas);
		
		if(GameView.ENABLED_DEBUG)
			canvas.drawLine(0,LEVEL_FLOOR, GameView.g_ScreenWidth,LEVEL_FLOOR,m_floorDebugPaint);
	}
	
	public void UpdateGame(long elapsedTime){
		// On update la partie si celle-ci n'est pas termin�
		if (m_player.getHealth() < 1)
			m_gameView.setGameState(GameView.GAMEOVER);
		m_player.setIsOnFloor(false);
		m_player.setIsOnPlatform(false);
		m_map.update(elapsedTime);
		Vector2D playerPos = new Vector2D(m_player.getM_position().getX() + m_player.getImageWidth() / 2, m_player.getM_position().getY() + m_player.getImageHeight());
		for (MapSegment segment : m_map.getMapSegments())
		{
			if (segment.getM_Type() == MapSegmentGenerator.Enemy)
			{
				EnemySegment enemy = (EnemySegment) segment;
				if (enemy.ShouldDrawShot())
				{
					Vector2D target = m_player.getM_position();
					Vector2D direction = target.substract(enemy.getM_position());
					direction.normalize();
					direction = direction.multiply(1000);
					m_enemyProjectiles.add(new EnemyProjectile(enemy.getM_position().getX(), enemy.getM_position().getY() + enemy.getBoundingBox().height() / 2, direction.getX(), direction.getY(), GameView.g_ScreenWidth, GameView.g_ScreenHeight, m_gameView.getResources()));
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
				
			case MapSegmentGenerator.Enemy:
				synchronized (this.m_projectiles)
				{
					for (Projectile projectile : this.m_projectiles)
					{
						synchronized (this.m_projectiles)
						{
							if(segment.getBoundingBox().intersect(projectile.getBoundingBox()) && !((EnemySegment)segment).isDead()){
								((EnemySegment)segment).setDead(true);
								this.m_projectilesToRemove.add(projectile);
							}
						}
					}
				}
				if (segment.getBoundingBox().intersect(m_player.getBoundingBox()) && !((EnemySegment)segment).isDead())
				{
					if(!segment.wasTouchedByPlayer())
					{
						segment.touchedByPlayer();
						m_player.damage();
					}
				}
				
			case MapSegmentGenerator.Coin:
				if (segment.getBoundingBox().intersect(m_player.getBoundingBox()))
					segment.touchedByPlayer();
				break;
				
			case MapSegmentGenerator.Fire:
			case MapSegmentGenerator.Rock:
				if (segment.getBoundingBox().intersect(m_player.getBoundingBox()))
				{
					if(!segment.wasTouchedByPlayer())
					{
						segment.touchedByPlayer();
						m_player.damage();
					}
				}
				break;
			case MapSegmentGenerator.HoleBegining:
				// GameView.m_player.setIsOnFloor(true);
				break;
			case MapSegmentGenerator.HoleMiddle:
			{
				if (segment.getBoundingBox().intersect(m_player.getBoundingBox()))
				{
					//code to start falling death animation
					segment.touchedByPlayer();
					m_gameView.setGameState(GameView.GAMEOVER);
				}
			}
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
				
				if (projectile.getM_position().getX() > GameView.g_ScreenWidth)
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
		
		synchronized (this.m_enemyProjectiles)
		{
			for (EnemyProjectile projectile : this.m_enemyProjectiles)
			{
				projectile.update(elapsedTime);
				
				if (projectile.getM_position().getX() > GameView.g_ScreenWidth)
				{
					this.m_enemyProjectilesToRemove.add(projectile);
				}
				else if(projectile.getBoundingBox().intersect(m_player.getBoundingBox())){
					m_player.damage();
					this.m_enemyProjectilesToRemove.add(projectile);
				}
			}
		}
		
		synchronized (this.m_enemyProjectiles)
		{
			for (EnemyProjectile projectile : this.m_enemyProjectilesToRemove)
			{
				this.m_enemyProjectiles.remove(projectile);
			}
			this.m_enemyProjectilesToRemove.clear();
		}
		
		testAchievement.Update(elapsedTime);
		if(m_distanceTraveled >= 20)
			testAchievement.setEarned(true);
		
		if (!m_canShoot)
		{
			this.m_rechargeTime += elapsedTime;
			if (this.m_rechargeTime >= RELOADINGTIME * GameThread.nano)
			{
				m_canShoot = true;
				this.m_rechargeTime = 0;
			}
		}
	}
	
	public void fireAtTarget(int posX, int posY)
	{
		
		if (m_canShoot)
		{
			
			synchronized (this.m_projectiles)
			{
				Rect playerBox = m_player.getBoundingBox();
				
				Vector2D target = new Vector2D(posX, posY - (BitmapManager.getInstance().getBitmap(BitmapManager.Laser2).getHeight()));
				
				Vector2D direction = target.substract(new Vector2D(m_player.getM_position().getX() + playerBox.width() / 2, m_player.getM_position().getY() + playerBox.height() / 2));
				direction.normalize();
				direction = direction.multiply(1000);
				m_projectiles.add(new Projectile(m_player.getM_position().getX() + playerBox.width() / 2, m_player.getM_position().getY() + playerBox.height() / 2, direction.getX(), direction.getY(), GameView.g_ScreenWidth, GameView.g_ScreenHeight, m_gameView.getResources()));
			}
			mediaShooting.seekTo(0);
			mediaShooting.start();
			m_canShoot = false;
		}
	}
	
	public void CollectedCoins(int value){
		this.m_coinsCollected += value;
	}
	
	public boolean IsMusicPlaying(){
		return this.m_musicPlaying;
	}
	
	public void SetMusicPlaying(boolean playing){
		this.m_musicPlaying = playing;
	}
	
	public void setPause(boolean pause)
	{
		m_Thread.setRunning(!pause);
	}
}
