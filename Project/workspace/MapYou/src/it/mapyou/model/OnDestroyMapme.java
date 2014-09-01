package it.mapyou.model;

import android.os.Parcel;

public class OnDestroyMapme implements MapmeState {

	/* (non-Javadoc)
	 * @see it.mapyou.core.MapmeState#getState()
	 */
	@Override
	public String getState() {
		// TODO Auto-generated method stub
		return "On destroy";
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
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}

}
