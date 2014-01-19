package com.dwellersbegood.Map;

import java.util.LinkedList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.dwellersbegood.BitmapManager;
import com.dwellersbegood.GObject;
import com.dwellersbegood.GameThread;
import com.dwellersbegood.GameView;
import com.dwellersbegood.Vector2D;

public class Map extends GObject {

	private LinkedList<MapSegment> m_mapSegments;
	private Bitmap m_background;
	private float bgPos;
	private float bgSpeed;
	private int m_difficulty = 0;
	
	public Map(int screenWidth, int screenHeight){
		bgPos = 0;
		bgSpeed = -100;
		m_background = BitmapManager.getInstance().scaleToSize(BitmapManager.getInstance().getBitmap(BitmapManager.Background), screenWidth, screenHeight);
		m_mapSegments = new LinkedList<MapSegment>();
		m_mapSegments.add(MapSegmentGenerator.Instance().getLastSegment());
		addStartingFloors(30);
	}
	
	public void draw(Canvas canvas)
	{
		if(canvas != null){
			canvas.drawBitmap(m_background,bgPos,0,null);
			canvas.drawBitmap(m_background,bgPos+m_background.getWidth(),0,null);
		}
		
		for(MapSegment segment:m_mapSegments)
		{
			segment.draw(canvas);
		}
	}

	@Override
	public void update(long elapsedTime) {
		// TODO Auto-generated method stub
		if(bgPos * -1 >= m_background.getWidth())
			bgPos = 0;
		
		bgPos += bgSpeed * (double)(elapsedTime)/GameThread.nano;
		
		removeDepricatedSegments();
		
		addMappSegments();
		
		for(MapSegment segment:m_mapSegments)
		{
			segment.setM_position(segment.getM_position().substract(new Vector2D((float)(elapsedTime/GameThread.nano)*500,0)));
			segment.update(elapsedTime);
		}
	}
	
	private void removeDepricatedSegments()
	{
		for(int i = 0; i < m_mapSegments.size(); i++)
		{
			if(isOutofScreen(i))
			{
				m_mapSegments.remove(i);
				i--;
			}
		}
	}
	
	private boolean isOutofScreen(int index)
	{
		if(m_mapSegments.get(index).getTopRightCorner().getX() < -10)
			return true;
		return false;
	}
	
	private void addMappSegments()
	{
		float bla = m_mapSegments.get(m_mapSegments.size() - 1).getTopRightCorner().getX();
		float screenSize = (float) (GameView.getScreenSize().getX() * 1.5);
		while( bla < screenSize)
		{
			MapSegment segment = MapSegmentGenerator.Instance().generate(m_difficulty);
			m_mapSegments.add(segment);
			bla = m_mapSegments.get(m_mapSegments.size() - 1).getTopRightCorner().getX();
		}
		System.out.println("");
	}
	
	private void addStartingFloors(int amount)
	{
		for(int i = 0 ; i < amount; i++)
			m_mapSegments.add(MapSegmentGenerator.Instance().generateStartingFloor());
	}
	
	public LinkedList<MapSegment> getMapSegments(){
		return m_mapSegments;
	}
}
