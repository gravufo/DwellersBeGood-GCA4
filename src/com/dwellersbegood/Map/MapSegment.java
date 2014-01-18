package com.dwellersbegood.Map;

import java.util.Vector;

import android.graphics.Canvas;

<<<<<<< HEAD
public abstract class MapSegment extends GObject{
	
	public abstract void draw(Canvas canvas);

	@Override
	public abstract void update(long ellapsedTime);
=======
import com.dwellersbegood.GObject;

public class MapSegment extends GObject
{
	
	private Vector<ForeGroundObject> m_foreGroundObjects;
	
	public MapSegment()
	{
		
	}
	
	@Override
	public void draw(Canvas canvas)
	{
		// @Todo
	}
	
	@Override
	public void update(long ellapsedTime)
	{
		// TODO Auto-generated method stub
		
	}
>>>>>>> ad861aeec7bf85a0b65fe6987b66331d41a290e6
}
