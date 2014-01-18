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
	
	public void setX(float newX) {
		// TODO Auto-generated method stub
		x = newX;
	}
	
	public void setY(float newY) {
		// TODO Auto-generated method stub
		y = newY;
	}
	
	public Vector2D add(Vector2D vector){
		return new Vector2D(x + vector.getX(), y + vector.getY());
	}
	
	public Vector2D substract(Vector2D vector){
		return new Vector2D(x - vector.getX(), y - vector.getY());
	}
	
	public Vector2D multiply(float f){
		return new Vector2D(x * f, y * f);
	}
	
	public Vector2D divide(float f){
		return new Vector2D(x / f, y / f);
	}
	
	public float norm(){
		return (float) Math.sqrt(x*x + y*y);
	}
	
	public void normalize(){
		float normValue = norm();
		x = x / normValue;
		y = y / normValue;
	}

}
