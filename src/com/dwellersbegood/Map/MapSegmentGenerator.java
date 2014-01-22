package com.dwellersbegood.Map;

import java.security.acl.LastOwnerException;
import java.util.Random;

import com.dwellersbegood.GameView;
import com.dwellersbegood.Vector2D;


public class MapSegmentGenerator {
	
	private int lastSegmentType;
	private MapSegment lastSegment;
	private MapSegment newSegment;
	private static MapSegmentGenerator m_instance = null;
	private Random randomSeed;
	private int m_generatedFloating = 0;
		
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
		m_generatedFloating = 0;
	};
	
	public MapSegment generate(int difficulty)
	{
		difficulty = 1;
		//Coin Creation
		if(m_generatedFloating < 1 && randomSeed.nextInt(99) + 1 < (10 + difficulty))
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
			if(m_generatedFloating > 0){
				generateFloatingSegment(Platform);
				m_generatedFloating--;
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
			
			if(danger > 70 && lastSegmentType == Floor && m_generatedFloating == 0)
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
		newSegment.moveTopLeftTo(lastTopRight);
		lastSegment = newSegment;
		lastSegmentType = type;
		if(m_generatedFloating > 0)
			m_generatedFloating--;
	}
	
	private void generateFloatingSegment(int type)
	{
		newSegment = makeSegment(type);
		Vector2D newPosition = lastSegment.getTopLeftCorner();
		
		float height = 0;
		if(type == Platform)
			height = GameView.getScreenSize().getY()/(float)7;
		newPosition = newPosition.add(new Vector2D(0,-1*newSegment.getHeight() - height + lastSegment.getImage().getHeight()/6));
		
		newSegment.moveTopLeftTo(newPosition);
		m_generatedFloating = 2;
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

	public MapSegment restartGenerator()
	{
		lastSegment = makeSegment(Floor);
		lastSegmentType = Floor;
		
		return lastSegment;
	}
}
