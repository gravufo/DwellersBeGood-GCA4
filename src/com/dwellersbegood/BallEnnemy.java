package com.dwellersbegood;

import com.dwellersbegood.R;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class BallEnnemy extends GObject{
	
	private Resources m_res;
	private Paint m_paint;
	private GAnimation m_Anim;
	
	public BallEnnemy(){
		super();
		m_paint = new Paint();
		m_paint.setColor(Color.BLACK);
	}
	
	public BallEnnemy(float posX, float posY, float speedX, float speedY, int screenWidth, int screenHeight, Resources res){
		super(posX, posY, speedX, speedY, screenWidth, screenHeight);
		m_res = res;
		
		m_paint = new Paint();
		m_paint.setColor(Color.BLACK);
		
		this.m_Anim = new GAnimation(BitmapFactory.decodeResource(this.m_res, R.drawable.soccer_animation), 15, 10);
	}

	@Override
	public void draw(Canvas canvas) {
		this.m_Anim.draw(canvas, m_position, m_paint);
	}

	@Override
	public void update(long ellapsedTime) {
		this.m_Anim.update(ellapsedTime);
	}

}
