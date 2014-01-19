package com.dwellersbegood;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;

public class Player extends GObject
{
	
	private Resources m_res;
	private final Paint m_paint;
	private GAnimation m_runningAnim;
	private GAnimation m_jumpingAnim;
	private boolean m_jumping, m_jumpStarted;
	private float m_jumpSpeed;
	private boolean m_isOnFloor;
	
	private static MediaPlayer mediaRunning = MediaPlayer.create(MainActivity.getContext(), R.raw.running);
	private static MediaPlayer mediaJumpFall = MediaPlayer.create(MainActivity.getContext(), R.raw.jump_fall);
	
	public Player()
	{
		super();
		m_paint = new Paint();
		m_paint.setColor(Color.BLACK);
	}
	
	public Player(float posX, float posY, float speedX, float speedY, int screenWidth, int screenHeight, Resources res)
	{
		super(posX, posY, speedX, speedY, screenWidth, screenHeight);
		m_res = res;
		
		m_jumping = false;
		m_jumpStarted = false;
		m_jumpSpeed = -500;
		
		m_isOnFloor = false;
		
		m_paint = new Paint();
		m_paint.setColor(Color.BLACK);
		
		this.m_runningAnim = new GAnimation(BitmapFactory.decodeResource(this.m_res, R.drawable.player), 60, 10);
		this.m_jumpingAnim = new GAnimation(BitmapFactory.decodeResource(this.m_res, R.drawable.player_jump), 10, 5, true);
		
		boundingBox.set((int) m_position.getX() + leftWidthOffset, (int) m_position.getY() + topHeightOffset, (int) m_position.getX() + m_runningAnim.getWidth() - rightWidthOffset, (int) m_position.getY() + m_runningAnim.getHeight() - botHeightOffset);
	}
	
	@Override
	public void draw(Canvas canvas)
	{
		if (!m_jumping)
			this.m_runningAnim.draw(canvas, m_position, m_paint);
		else
			this.m_jumpingAnim.draw(canvas, m_position, m_paint);
		
		if (GameView.ENABLED_DEBUG)
		{
			canvas.drawRect(boundingBox, m_debugPaint);
		}
	}
	
	@Override
	public void update(long elapsedTime)
	{
		
		// mediaRunning.start();
		
		m_position = m_position.add(m_speed.multiply((float) (elapsedTime / GameThread.nano)));
		if (!m_isOnFloor)
			m_speed.setY(m_speed.getY() + GameView.GRAVITY * ((float) (elapsedTime / GameThread.nano)));
		boundingBox.set((int) m_position.getX() + leftWidthOffset, (int) m_position.getY() + topHeightOffset, (int) m_position.getX() + m_runningAnim.getWidth() - rightWidthOffset, (int) m_position.getY() + m_runningAnim.getHeight() - botHeightOffset);
		
		if (m_jumping && !m_jumpingAnim.getDone())
			this.m_jumpingAnim.update(elapsedTime);
		else
		{
			this.m_runningAnim.update(elapsedTime);
		}
		if (m_jumping && m_jumpingAnim.getCurrentFrame() == 4 && !m_jumpStarted)
		{
			m_speed.setY(m_jumpSpeed);
			m_jumpStarted = true;
		}
		
		if (m_isOnFloor)
		{
			m_position.setY(GameView.PLAYER_MIN_Y);
			m_speed.setY(0);
			m_jumping = false;
			m_jumpStarted = false;
		}
	}
	
	public void jumpStarted()
	{
		if (!m_jumping)
		{
			m_jumping = true;
			m_jumpingAnim.reset();
		}
	}
	
	public void jumpReleased(float ratio)
	{
		// m_jumpSpeed = -600 * ratio;
	}
	
	public void setIsOnFloor(boolean isOnFloor)
	{
		this.m_isOnFloor = isOnFloor;
	}
	
	public void hitEnemy()
	{
		
	}
	
	public static void stopMedia()
	{
		mediaJumpFall.stop();
		mediaRunning.stop();
	}
}
