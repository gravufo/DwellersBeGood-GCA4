package com.dwellersbegood;

public class Vector2D
{
	protected float x;
	protected float y;


	public Vector2D(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public float getX() {
		// TODO Auto-generated method stub
		return x;
	}
	
	public float getY() {
		// TODO Auto-generated method stub
		return y;
	}
	
	public Vector2D add(Vector2D vector){
		return new Vector2D(x + vector.getX(), y + vector.getY());
	}
	
	public Vector2D multiply(float f){
		return new Vector2D(x * f, y * f);
	}

}
