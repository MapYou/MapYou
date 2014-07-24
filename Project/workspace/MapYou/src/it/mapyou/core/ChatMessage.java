/**
 * 
 */
package it.mapyou.core;

import android.os.Parcel;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class ChatMessage extends AbstractNotification<User, User, String>{

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
