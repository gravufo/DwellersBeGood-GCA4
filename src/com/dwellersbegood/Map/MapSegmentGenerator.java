package com.dwellersbegood.Map;

public class MapSegmentGenerator {

	
	private MapSegment last;
	private static MapSegmentGenerator m_instance = null;
	
	public static MapSegmentGenerator Instance()
	{
		if (m_instance == null)
			m_instance = new MapSegmentGenerator();
		return m_instance;
	}
	
	private MapSegmentGenerator(){};
	
	public MapSegment generate()
	{
		//@TODO
		
		
		
		return new HoleBeginingSegment();
	}
}
