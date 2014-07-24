/**
 * 
 */
package it.mapyou.core;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class FriendRelationship implements Parcelable{

	private User user;
	private List<User> friends;
	
	public static final Parcelable.Creator<FriendRelationship> CREATOR = new Creator<FriendRelationship>() {
		
		@Override
		public FriendRelationship[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public FriendRelationship createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
	public FriendRelationship() {
		friends = new ArrayList<User>();
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the friends
	 */
	public List<User> getFriends() {
		return friends;
	}

	/**
	 * @param friends the friends to set
	 */
	public void setFriends(List<User> friends) {
		this.friends = friends;
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
