package com.dwellersbegood;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Player extends GObject
{
	private Paint m_paint;
	private GAnimation m_runningAnim;
	private GAnimation m_jumpingAnim;
	private boolean m_jumping, m_jumpStarted;
	private float m_jumpSpeed;
	private boolean m_isOnFloor;
	private boolean m_isOnPlatform;
	private float m_platformLevel;
	
	public final int INITIALHEALTH = 3;
	private int m_health;
	
	public Player()
	{
		super();
		m_paint = new Paint();
		m_paint.setColor(Color.BLACK);
		m_health = INITIALHEALTH;
	}
	
	public Player(float posX, float posY, float speedX, float speedY, int screenWidth, int screenHeight)
	{
		super(posX, posY, speedX, speedY, screenWidth, screenHeight);
		
		m_health = INITIALHEALTH;
		
		m_jumping = false;
		m_jumpStarted = false;
		m_jumpSpeed = -800;
		
		m_isOnFloor = false;
		m_isOnPlatform = false;
		
		m_platformLevel = 0;
		
		m_paint = new Paint();
		m_paint.setColor(Color.BLACK);
		
		this.m_runningAnim = new GAnimation(BitmapManager.getInstance().getBitmap(BitmapManager.PlayerRun), 45, 10);
		this.m_jumpingAnim = new GAnimation(BitmapManager.getInstance().getBitmap(BitmapManager.PlayerJump), 24, 5, true);
		
		topHeightOffset = m_runningAnim.getHeight() / 8;
		botHeightOffset = m_runningAnim.getHeight() / 13;
		leftWidthOffset = (int) (m_runningAnim.getWidth() / 2.5);
		rightWidthOffset = (int) (m_runningAnim.getWidth()/6);
		
		calculateBoundingBox(m_position, m_runningAnim.getWidth(), m_runningAnim.getHeight());
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
		if (!m_isOnFloor && !m_isOnPlatform)
			m_speed.setY(m_speed.getY() + Game.GRAVITY * ((float) (elapsedTime / GameThread.nano)));
		
		calculateBoundingBox(m_position, m_runningAnim.getWidth(), m_runningAnim.getHeight());
		
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
			if (m_speed.getY() > 0)
			{
				m_position.setY(Game.LEVEL_FLOOR - m_runningAnim.getHeight() + botHeightOffset);
				m_speed.setY(0);
				m_jumping = false;
				if (m_jumpStarted)
					SoundManager.getInstance().getPlayer(SoundManager.JUMP_FALL).start();
				m_jumpStarted = false;
			}
		}
		
		if (m_isOnPlatform)
		{
			if (m_speed.getY() > 0)
			{
				m_position.setY(m_platformLevel - m_runningAnim.getHeight() + botHeightOffset);
				m_speed.setY(0);
				m_jumping = false;
				m_jumpStarted = false;
			}
		}
	}
	
	public void jumpStarted()
	{
		if (!m_jumping && m_isOnFloor)
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
	
	public boolean IsOnFloor()
	{
		return this.m_isOnFloor;
	}
	
	public void setIsOnPlatform(boolean isOnPlatform)
	{
		this.m_isOnPlatform = isOnPlatform;
	}
	
	public void setIsOnPlatform(boolean isOnPlatform, float platformLevel)
	{
		this.m_isOnPlatform = isOnPlatform;
		this.m_platformLevel = platformLevel;
	}
	
	public boolean IsOnPlatform()
	{
		return this.m_isOnPlatform;
	}
	
	public float getImageWidth(){
		return m_runningAnim.getWidth();
	}
	
	public float getImageHeight(){
		return m_runningAnim.getHeight();
	}
	
	public int getHealth(){
		return m_health;
	}
	
	public void damage(){
		m_health--;
	}
	
	public void hitEnemy()
	{
		
	}
}
