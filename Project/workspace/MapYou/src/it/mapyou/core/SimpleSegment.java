/**
 * 
 */
package it.mapyou.core;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class SimpleSegment extends AbstractSegment {

	public static final Parcelable.Creator<SimpleSegment> CREATOR = new Creator<SimpleSegment>() {
		
		@Override
		public SimpleSegment[] newArray(int size) {
			// TODO Auto-generated method stub
			return new SimpleSegment[size];
		}
		
		@Override
		public SimpleSegment createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			SimpleSegment s = new SimpleSegment();
			s.setStartPoint((Point) source.readSerializable());
			s.setEndPoint((Point) source.readSerializable());
			return s;
		}
	};
	
	/* (non-Javadoc)
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(startPoint);
		dest.writeSerializable(endPoint);
	}

	/* (non-Javadoc)
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

}
