package com.dwellersbegood.Map;

import java.util.Random;

import android.graphics.Canvas;

import com.dwellersbegood.BitmapManager;
import com.dwellersbegood.Game;
import com.dwellersbegood.GameThread;
import com.dwellersbegood.GameView;
import com.dwellersbegood.SoundManager;

public class CoinSegment extends MapSegment
{
	
	private double angle;
	private Random seed;
	private float screenSize;
	private int value = 0;
	int m_coinType;
	
	public final static int Coin = 0;
	public final static int Bag = 1;
	public final static int Gem = 2;
	
	public CoinSegment()
	{
		m_Type = MapSegmentGenerator.Coin;
		seed = new Random();
		angle = seed.nextFloat() * 2 * Math.PI;
		screenSize = GameView.getScreenSize().getY();
		
		int ratio = seed.nextInt(100);
		
		if (ratio > 10)
		{
			m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Coin0);
			value = 1;
			m_coinType = Coin;
		}
		else if (ratio > 1)
		{
			m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Coin1);
			value = 10;
			m_coinType = Bag;
		}
		else
		{
			m_image = BitmapManager.getInstance().getBitmap(BitmapManager.Coin2);
			value = 100;
			m_coinType = Gem;
		}
		
	}
	
	public int getValue()
	{
		return value;
	}
	
	@Override
	public void draw(Canvas canvas)
	{
		canvas.drawBitmap(m_image, this.getM_position().getX(), this.getM_position().getY(), null);
		
		if (GameView.ENABLED_DEBUG)
		{
			canvas.drawRect(boundingBox, m_debugPaint);
		}
	}
	
	@Override
	public void update(long elapsedTime)
	{
		if (!m_touchedPlayer)
		{
			if (m_coinType == Bag)
			{
				m_position.setY(Game.LEVEL_FLOOR - getHeight());
			}
			else
			{
				
				angle += 2 * elapsedTime / GameThread.nano;
				if (angle > 2 * Math.PI)
					angle -= 2 * Math.PI;
				
				m_position.setY((float) (Math.sin(angle) / 20 * screenSize + screenSize / 2));
			}
			calculateBoundingBox(m_position, getWidth(), getHeight());
			//this.boundingBox.set((int) this.getM_position().getX(), (int) this.getM_position().getY(), (int) this.getM_position().getX() + this.getWidth(), (int) this.getM_position().getY() + this.getHeight());
		}
		else
		{
			m_position.setY(m_position.getY() - (float) (GameView.getScreenSize().getY() * 0.5 * elapsedTime / GameThread.nano));
		}
	}
	
	@Override
	public void touchedByPlayer()
	{
		super.touchedByPlayer();
		
		if (!m_soundPlayed)
		{
			int index;
			
			switch (m_coinType)
			{
			case Bag:
				index = SoundManager.GOLD_BAG;
				break;
			case Gem:
				index = SoundManager.GEM;
				break;
			case Coin:
				index = SoundManager.GOLD_COIN;
				break;
			default:
				index = 0;
			}
			
			m_game.CollectedCoins(value);
			
			SoundManager.getInstance().getPlayer(index).start();
			m_soundPlayed = true;
		}
	}
}
