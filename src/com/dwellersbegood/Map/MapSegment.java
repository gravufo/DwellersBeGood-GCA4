package com.dwellersbegood.Map;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.dwellersbegood.GObject;
import com.dwellersbegood.Game;
import com.dwellersbegood.Vector2D;

public abstract class MapSegment extends GObject
{
	
	protected int m_Type;
	protected Game m_game;
	
	public int getM_Type()
	{
		return m_Type;
	}
	
	protected Bitmap m_image;
	
	public void moveTopLeftTo(Vector2D position)
	{
		this.setM_position(position);
	}
	
	public int getHeight()
	{
		return m_image.getHeight();
	}
	
	public int getWidth()
	{
		return m_image.getWidth();
	}
	
	public Bitmap getImage(){
		return m_image;
	}
	
	public Vector2D getTopRightCorner()
	{
		Vector2D topRightCorner = new Vector2D(this.getWidth(), 0);
		topRightCorner = topRightCorner.add(this.m_position);
		return topRightCorner;
	}
	
	public Vector2D getTopLeftCorner()
	{
		return this.m_position;
	}
	
	public void setGame(Game game){
		m_game = game;
	}
	
	@Override
	public abstract void draw(Canvas canvas);
	
	@Override
	public abstract void update(long elapsedTime);
	
	@Override
	public void touchedByPlayer()
	{
		super.touchedByPlayer();
	};
}
