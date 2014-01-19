package com.dwellersbegood.Map;

import com.dwellersbegood.GameView;

import android.graphics.Canvas;

public class EnemySegment extends MapSegment {
	
	public static final long TIMETOSHOT = 2000;
	
	private long m_timeSinceLastShot;
	
	public EnemySegment(){
		m_Type = MapSegmentGenerator.Enemy;
		m_timeSinceLastShot = 0;
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(m_image, this.getM_position().getX(), this.getM_position().getY(), null);
		
		if(GameView.ENABLED_DEBUG){
			canvas.drawRect(boundingBox, m_debugPaint);
		}
	}

	@Override
	public void update(long ellapsedTime) {
		this.boundingBox.set((int)this.getM_position().getX(), (int)this.getM_position().getY(), (int)this.getM_position().getX() + this.getWidth(), (int)this.getM_position().getY() + this.getHeight());
		
		m_timeSinceLastShot += ellapsedTime;
		if(m_timeSinceLastShot >= TIMETOSHOT){
			fireAtPlayer();
		}
		
		if(this.boundingBox.intersect(GameView.m_player.getBoundingBox())){
			GameView.m_player.hitEnemy();
		}
	}
	
	public void fireAtPlayer(){
		
	}

}
