/**
 * 
 */
package it.mapyou.core;



/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public abstract class AbstractSegment extends SubjectModel implements Segment {

	protected Point startPoint, endPoint;
	
	/**
	 * @param startPoint the startPoint to set
	 */
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	/**
	 * @param endPoint the endPoint to set
	 */
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}
	
	@Override
	public Point getEndPoint() {
		// TODO Auto-generated method stub
		return endPoint;
	}
	
	@Override
	public Point getStartPoint() {
		// TODO Auto-generated method stub
		return startPoint;
	}
	
	/* (non-Javadoc)
	 * @see it.mapyou.core.Segment#calculateLenght()
	 */
	@Override
	public double calculateLenght() {
		// TODO Auto-generated method stub
		if(endPoint==null)
			return -2;
		else{
			if(startPoint==null)
				return -1;
			else
				return 0;
		}
	}
	
}
