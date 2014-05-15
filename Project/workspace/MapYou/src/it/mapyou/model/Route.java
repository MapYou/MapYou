/**
 * 
 */
package it.mapyou.model;

import java.util.Vector;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class Route extends AbstractSegment {
	
	private Vector<Segment> segments;
	private double length;
	
	public Route(){
		segments = new Vector<Segment>();
		length = 0.0;
	}
	
	public void addSegment(Segment...segmentss){
		for(int i=0; i<segmentss.length; i++)
			{
			Segment s = segmentss[i];
			length+=s.getLenght();
			segments.add(s);
			}
	}
	
	public void removeSegment(Segment...s){
		for(int i=s.length-1; i>=0; i--)
			segments.remove(s[i]);
	}
	
	public Vector<Segment> getSegments(){
		return segments;
		
	}

	/* (non-Javadoc)
	 * @see it.mapyou.model.Segment#getLenght()
	 */
	@Override
	public double getLenght() {
		// TODO Auto-generated method stub
		return length;
	}

}
