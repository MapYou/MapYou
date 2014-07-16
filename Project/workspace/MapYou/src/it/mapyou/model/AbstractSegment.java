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
