/**
 * 
 */
package it.mapyou.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class Mapping implements Parcelable{

	private User user;
	private Route route;
	public static final Parcelable.Creator<Mapping> CREATOR = new Creator<Mapping>() {
		
		@Override
		public Mapping[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Mapping[size];
		}
		
		@Override
		public Mapping createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			Mapping m = new Mapping();
			m.setUser((User) source.readSerializable());
			m.setRoute((Route) source.readParcelable(null));
			return m;
		}
	};
	
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
	 * @return the route
	 */
	public Route getRoute() {
		return route;
	}
	
	/**
	 * @param route the route to set
	 */
	public void setRoute(Route route) {
		this.route = route;
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
		dest.writeSerializable(user);
		dest.writeParcelable(route, flags);
	}

}
