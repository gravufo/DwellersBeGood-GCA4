package com.dwellersbegood.Map;

import java.util.Queue;

import com.dwellersbegood.BitmapManager;
import com.dwellersbegood.GObject;
import com.dwellersbegood.GameThread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Map extends GObject {

	private Queue<MapSegment> m_mapSegments;
	private Bitmap m_background;
	private float bgPos;
	private float bgSpeed;
	
	public Map(int screenWidth, int screenHeight){
		bgPos = 0;
		bgSpeed = -400;
		m_background = BitmapManager.Instance().scaleToSize(BitmapManager.Instance().getBitmap(BitmapManager.Background), screenWidth, screenHeight);
	}
	
	public void draw(Canvas canvas)
	{
		if(canvas != null){
			canvas.drawBitmap(m_background,bgPos,0,null);
			canvas.drawBitmap(m_background,bgPos+m_background.getWidth(),0,null);
		}
	}

	@Override
	public void update(long ellapsedTime) {
		// TODO Auto-generated method stub
		if(bgPos * -1 >= m_background.getWidth())
			bgPos = 0;
		bgPos += bgSpeed * (double)(ellapsedTime)/GameThread.nano;
	}
}
