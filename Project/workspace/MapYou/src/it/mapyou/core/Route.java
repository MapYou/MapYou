/**
 * 
 */
package it.mapyou.core;

import java.util.Vector;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class Route extends AbstractSegment{
	
	private Vector<Segment> segments;
	public static final Parcelable.Creator<Route> CREATOR = new Creator<Route>() {
		
		@Override
		public Route[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Route[size];
		}
		
		@Override
		public Route createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Route(source);
		}
	};
	
	public Route(Parcel s){
		segments = new Vector<Segment>();
		s.readList(segments, AbstractSegment.class.getClassLoader());
		
	}
	
	/* (non-Javadoc)
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeList(segments);
	}
	
	public Route(){
		segments = new Vector<Segment>();
	}
	
	public void addSegment(Segment...segmentss){
		for(int i=0; i<segmentss.length; i++)
			{
			Segment s = segmentss[i];
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
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see it.mapyou.core.AbstractSegment#calculateLenght()
	 */
	@Override
	public double calculateLenght() {
		double sum = 0.0;
		for(int i=0; i<segments.size(); i++){
			sum+=segments.get(i).calculateLenght();
		}
		return sum;
	}
	
}
