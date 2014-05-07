/**
 * 
 */
package it.mapyou.model;

import java.util.List;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MapRoute extends AbstractSegment {
	
	/**
	 * 
	 */
	public MapRoute() {
		 
	}
	
	public boolean addSegment(Segment s){
		return false;
	}
	public boolean removeSegment(Segment s){
		return false;
	}
	
	public List<Segment> getSegments(){
		return null;
		
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
