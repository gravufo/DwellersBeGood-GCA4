package com.dwellersbegood;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.dwellersbegood.Map.Map;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
	
	public static final float GRAVITY = 300;
	public static final float PLAYER_MIN_Y = 500;
	
	private GameActivity m_Activity;
	private GameThread m_Thread;
	private int m_ScreenWidth;
	private int m_ScreenHeight;
	private int posX, posY;
	private Paint m_collectibleScorePaint;
	private int m_collectibleScore;
	private Resources m_res;
	private Player m_player;
	private BallEnnemy m_ennemy;
	private ArrayList<Projectile> m_projectiles;
	private Map m_map;
	private GData m_Data;

	public GameView(GameActivity activity, Context context) {
		super(context);
		m_Activity = activity;
		getHolder().addCallback(this);
		setFocusable(true);
		posX = posY = 0;
		this.m_projectiles = new ArrayList<Projectile>();
		
		this.m_collectibleScorePaint = new Paint();
		this.m_collectibleScorePaint.setColor(Color.RED);
		this.m_collectibleScorePaint.setTextSize(28);
		this.m_collectibleScorePaint.setTextAlign(Align.LEFT);
		
		this.m_collectibleScore = 0;
		
		m_Data = m_Activity.getData();
		if(m_Data != null)
			m_collectibleScore = m_Data.getDolla();
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.m_ScreenWidth = this.getWidth();
	    this.m_ScreenHeight = this.getHeight();
	    
	    //Create map
	    
	    m_map = new Map(this.m_ScreenWidth, this.m_ScreenHeight);
	    m_player = new Player(200, PLAYER_MIN_Y, 0, 50, m_ScreenWidth, m_ScreenHeight, m_Activity.getResources());
	    m_ennemy = new BallEnnemy(800, 500, 0, 0, m_ScreenWidth, m_ScreenHeight, m_Activity.getResources());
	    
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
			//Log.d("GameView", "Drawing");
			// Dessinage de la scene
			
			canvas.drawColor(Color.WHITE);
			m_map.draw(canvas);
			
			m_player.draw(canvas);
			m_ennemy.draw(canvas);
			
			synchronized(this.m_projectiles){
				for(Projectile projectile : this.m_projectiles){
					projectile.draw(canvas);
				}
			}
			
			canvas.drawText(this.m_collectibleScore+"", 100, 40, this.m_collectibleScorePaint);
		}
	}
	 
	public void update(long ellapsedTime)
	{
		// On update la partie si celle-ci n'est pas termin�
		m_map.update(ellapsedTime);
		m_player.update(ellapsedTime);
		m_ennemy.update(ellapsedTime);
		
		synchronized(this.m_projectiles){
			for(Projectile projectile : this.m_projectiles){
				projectile.update(ellapsedTime);
			}
		}
	}
	 
	
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		//Log.d("GameView", "OnTouchEvent at " + (int)event.getX() + ", " + (int)event.getY());
		if (event.getAction() == MotionEvent.ACTION_DOWN) 
		{
			posX = (int)event.getX();
			posY = (int)event.getY();
			
			// If the touch is on first half of screen, jump
			if(posX < m_ScreenWidth/2){
				m_player.jump();
			}
			
			// If its on other side of screen, throw something
			else
				throwSomething(posX, posY);
		}
		return super.onTouchEvent(event);
	}
	
	public void throwSomething(int posX, int posY){
		
		// should this statement be in a synchronized too ? (it is drawn in diff thread)
		m_collectibleScore++;
		m_Data.setDolla(m_collectibleScore);
		m_Activity.setData(m_Data);
		
		synchronized(this.m_projectiles){
			Vector2D target = new Vector2D(posX, posY);
			Vector2D direction = target.substract(m_player.getM_position());
			direction.normalize();
			direction = direction.multiply(2000);
			m_projectiles.add(new Projectile(m_player.getM_position().getX(), m_player.getM_position().getY(), direction.getX(), direction.getY(), m_ScreenWidth, m_ScreenHeight, m_res));
		}
	}
	
}
