package com.dwellersbegood.Map;

import java.util.Vector;

import com.dwellersbegood.GObject;

import android.graphics.Canvas;

public abstract class MapSegment extends GObject{
	
	public abstract void draw(Canvas canvas);

	@Override
	public abstract void update(long ellapsedTime);
}
