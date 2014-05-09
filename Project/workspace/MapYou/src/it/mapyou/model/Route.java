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
	
	public Route(){
		segments = new Vector<Segment>();
	}
	
	public void addSegment(Segment...segmentss){
		for(int i=0; i<segmentss.length; i++)
			segments.add(segmentss[i]);
	}
	
	public void removeSegment(Segment...s){
		for(int i=s.length-1; i>=0; i--)
			segments.remove(s[i]);
	}
	
	public Vector<Segment> getSegments(){
		return segments;
		
	}

	/* (non-Javadoc)
	 * @see it.mapyou.model.Segment#getStartPoint()
	 */
	@Override
	public StartPoint getStartPoint() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.model.Segment#getEndPoint()
	 */
	@Override
	public EndPoint getEndPoint() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.model.Segment#getLenght()
	 */
	@Override
	public double getLenght() {
		// TODO Auto-generated method stub
		return 0;
	}

}
