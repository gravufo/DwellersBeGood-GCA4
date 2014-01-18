package com.dwellersbegood.Map;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.dwellersbegood.GObject;
import com.dwellersbegood.Vector2D;

public abstract class MapSegment extends GObject{
	
	protected Bitmap m_image;
	
	public void moveBottomLeftTo(Vector2D position)
	{
		Vector2D offset = new Vector2D((float) 0.0,this.getHeight());
		this.setM_position(position.substract(offset));
	}
	
	public int getHeight()
	{
		return m_image.getHeight();
	}
	
	public int getWidth()
	{
		return m_image.getWidth();
	}
	
	public Vector2D getBottomRightCorner()
	{
		Vector2D bottomRightCorner = new Vector2D(this.getWidth(),this.getHeight());
		bottomRightCorner = bottomRightCorner.add(this.m_position);
		return bottomRightCorner;
	}
	
	public abstract void draw(Canvas canvas);

	@Override
	public abstract void update(long ellapsedTime);

}
