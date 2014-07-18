/**
 * 
 */
package it.mapyou.model;


/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public abstract class AbstractSegment extends SubjectModel implements Segment {

	protected StartPoint startPoint;
	protected EndPoint endPoint;
	protected double length;
	
	/* (non-Javadoc)
	 * @see it.mapyou.model.Segment#getLenght()
	 */
	@Override
	public double getLenght() {
		// TODO Auto-generated method stub
		return length;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.model.Segment#setLenght(double)
	 */
	@Override
	public void setLenght(double l) {
		// TODO Auto-generated method stub
		this.length = l;
	}
	
	/**
	 * @param startPoint the startPoint to set
	 */
	public void setStartPoint(StartPoint startPoint) {
		this.startPoint = startPoint;
	}

	/**
	 * @param endPoint the endPoint to set
	 */
	public void setEndPoint(EndPoint endPoint) {
		this.endPoint = endPoint;
	}
	
	@Override
	public EndPoint getEndPoint() {
		// TODO Auto-generated method stub
		return endPoint;
	}
	
	@Override
	public StartPoint getStartPoint() {
		// TODO Auto-generated method stub
		return startPoint;
	}
	
	
}
