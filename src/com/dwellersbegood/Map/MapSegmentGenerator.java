package com.dwellersbegood.Map;

<<<<<<< HEAD
public class MapSegmentGenerator {

=======
public class MapSegmentGenerator
{
	// private
	private MapSegmentGenerator m_instance = null;
>>>>>>> ad861aeec7bf85a0b65fe6987b66331d41a290e6
	
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
<<<<<<< HEAD
		//@TODO
		
		
		
		return new HoleBeginingSegment();
=======
		// @TODO
		
		return new MapSegment();
>>>>>>> ad861aeec7bf85a0b65fe6987b66331d41a290e6
	}
}
