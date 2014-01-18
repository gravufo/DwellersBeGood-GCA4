package com.example.dwellersbegood.Map;

public class MapSegmentGenerator {

	private MapSegmentGenerator m_instance = null;
	
	public MapSegmentGenerator getInstance()
	{
		if (m_instance == null)
			m_instance = new MapSegmentGenerator();
		return m_instance;
	}
	
	public MapSegment generate()
	{
		//@TODO
		return new MapSegment();
	}
}
