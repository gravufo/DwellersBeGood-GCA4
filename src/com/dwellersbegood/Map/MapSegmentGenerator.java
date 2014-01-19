package com.dwellersbegood.Map;

import java.util.Random;

import com.dwellersbegood.GameView;
import com.dwellersbegood.Vector2D;


public class MapSegmentGenerator {
	
	private int lastSegmentType;
	private MapSegment lastSegment;
	private MapSegment newSegment;
	private static MapSegmentGenerator m_instance = null;
	private Random randomSeed;
	private boolean m_generatedFloating;
		
	public static MapSegmentGenerator Instance()
	{
		if (m_instance == null)
			m_instance = new MapSegmentGenerator();
		return m_instance;
	}
	
	private MapSegmentGenerator()
	{
		lastSegmentType = Floor;
		lastSegment = new FloorSegment();
		randomSeed = new Random();
		m_generatedFloating = false;
	};
	
	public MapSegment generate(int difficulty)
	{
		//Coin Creation
		if(randomSeed.nextInt(99) + 1 < 10)
		{
			generateFloatingSegment(Coin);
		}
		// At least one floor before next danger
		else if (lastSegmentType == Fire || lastSegmentType == Rock || lastSegmentType == Enemy)
		{
			generatePathSegment(Floor);
		}
		// A hole middle implies a platform to jump on
		else if(lastSegmentType == HoleMiddle)
		{
			if(!m_generatedFloating){
				generateFloatingSegment(Floor);
			}
			else{
				generatePathSegment(HoleEnding);
			}
		}
		// What to do with holes half of the time close the hole, the other half continue it
		else if(lastSegmentType == HoleBegining || lastSegmentType == HoleMiddle)
		{
			if(randomSeed.nextInt() % 2 == 0){
				generatePathSegment(HoleEnding);
			}
			else
				generatePathSegment(HoleMiddle);
		}
		else
		{
			int danger = randomSeed.nextInt(100) + difficulty;
			
			if(danger > 30)
			{
				switch(randomSeed.nextInt(4))
				{
					case 0:
						generatePathSegment(HoleBegining);
						break;
					case 1:
						generateFloatingSegment(Enemy);
						break;
					case 2:
						generateFloatingSegment(Fire);
						break;
					case 3:
						generateFloatingSegment(Rock);
				}
			}else{
				generatePathSegment(Floor);
				m_generatedFloating = false;
			}
		}
		
		return newSegment;
	}
	
	public MapSegment getLastSegment()
	{
		return lastSegment;
	}
	
	private void generatePathSegment(int type)
	{
		newSegment = makeSegment(type);
		Vector2D lastTopRight = lastSegment.getTopRightCorner();
		lastTopRight.add(new Vector2D(1,0));
		newSegment.moveTopLeftTo(lastTopRight);
		lastSegment = newSegment;
		lastSegmentType = type;
		m_generatedFloating = false;
	}
	
	private void generateFloatingSegment(int type)
	{
		newSegment = makeSegment(type);
		Vector2D newPosition = lastSegment.getTopLeftCorner();
		
		float height = newSegment.getHeight();
		switch(type)
		{
			case Enemy:
			case Fire:
			case Rock:
				break;
			case Coin:
				height += (float)(GameView.getScreenSize().getY()/4.0);
				break;
			case Platform:
				height += (float)(GameView.getScreenSize().getY()/10.0);
				break;
		}
		
		newPosition = newPosition.add(new Vector2D(1,-1*height));
		newSegment.moveTopLeftTo(newPosition);
		m_generatedFloating = true;
	}
	
	private MapSegment makeSegment(int type)
	{
		MapSegment segment = null;
		switch(type)
		{
			case HoleBegining:
				segment = new HoleBeginingSegment();
				break;
			case HoleEnding:
				segment = new HoleEndingSegment();
				break;
			case HoleMiddle:
				segment = new HoleMiddleSegment();
				break;
			case Floor:
				segment = new FloorSegment();
				break;
			case Fire:
				segment = new FireSegment();
				break;
			case Rock:
				segment = new RockSegment();
				break;
			case Platform:
				segment = new PlatformSegment();
				break;
			case Coin:
				segment = new CoinSegment();
				break;
			case Enemy:
				segment = new EnemySegment();
				break;
		}
		return segment;
	}
	
	public final static int HoleBegining = 0;
	public final static int HoleEnding = 1;
	public final static int HoleMiddle = 2;
	public final static int Floor = 3;
	public final static int Fire = 4;
	public final static int Rock = 5;
	public final static int Platform = 6;
	public final static int Coin = 7;
	public final static int Enemy = 8;

	public MapSegment generateStartingFloor() {
		generatePathSegment(Floor);
		return newSegment;
	}
}
