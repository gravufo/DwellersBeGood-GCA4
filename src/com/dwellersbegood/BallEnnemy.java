package com.dwellersbegood;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class BallEnnemy extends GObject{
	
	private Resources m_res;
	private Paint m_paint;
	private Bitmap m_sprite;
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
		
		m_sprite = BitmapManager.Instance().scaleToSize(BitmapFactory.decodeResource(res, R.drawable.badstatue), 300, 300);
		//this.m_Anim = new GAnimation(BitmapFactory.decodeResource(this.m_res, R.drawable.player), 60, 10);
	}

	@Override
	public void draw(Canvas canvas) {
		//this.m_Anim.draw(canvas, m_position, m_paint);
		canvas.drawBitmap(m_sprite, m_position.getX() - m_sprite.getWidth()/2, m_position.getY() - m_sprite.getHeight()/2, m_paint);
	}

	@Override
	public void update(long ellapsedTime) {
		//this.m_Anim.update(ellapsedTime);
	}

}
