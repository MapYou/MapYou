/**
 * 
 */
package it.mapyou.model;

import java.util.GregorianCalendar;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MapMe extends SubjectModel implements Parcelable {


	private User administrator;
	private GregorianCalendar creationDate;
	private int numUsers, maxNumUsers;
	private MapmeState state;
	private String name;
	private Segment segment;
	public static final Parcelable.Creator<MapMe> CREATOR = new Creator<MapMe>() {

		@Override
		public MapMe[] newArray(int size) {
			// TODO Auto-generated method stub
			return new MapMe[size];
		}

		@Override
		public MapMe createFromParcel(Parcel s) {
			// TODO Auto-generated method stub
			return new MapMe(s);
		}
	};



	public MapMe() {
		creationDate = new GregorianCalendar();
	}

	/**
	 * @return the state
	 */
	public MapmeState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setMapmeState(MapmeState state) {
		this.state = state;
	}

	public MapMe(String name) {
		this.name = name;
		creationDate = new GregorianCalendar();
	}

	public MapMe(Parcel s){
		setName(s.readString());
		setModelID(s.readInt());
		setMapmeState((MapmeState) s.readParcelable(MapmeState.class.getClassLoader()));
		setNumUsers(s.readInt());
		setMaxNumUsers(s.readInt());
		GregorianCalendar g = new GregorianCalendar();
		g.setTimeInMillis(s.readLong());
		setCreationDate(g);
		setAdministrator((User) s.readSerializable());
		setSegment((Segment) s.readParcelable(Segment.class.getClassLoader()));
	}

	/* (non-Javadoc)
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeInt(modelID);
		dest.writeParcelable(state, flags);
		dest.writeInt(numUsers);
		dest.writeInt(maxNumUsers);
		dest.writeLong(creationDate.getTimeInMillis());
		dest.writeSerializable(administrator);
		dest.writeParcelable(segment, flags);
	}

	/**
	 * @return the segment
	 */
	public Segment getSegment() {
		return segment;
	}

	/**
	 * @param segment the segment to set
	 */
	public void setSegment(Segment segment) {
		this.segment = segment;
	}

	/**
	 * @return the creationDate
	 */
	public GregorianCalendar getCreationDate() {
		return creationDate;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the maxNumUsers
	 */
	public int getMaxNumUsers() {
		return maxNumUsers;
	}

	/**
	 * @return the numUsers
	 */
	public int getNumUsers() {
		return numUsers;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(GregorianCalendar creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @param maxNumUsers the maxNumUsers to set
	 */
	public void setMaxNumUsers(int maxNumUsers) {
		this.maxNumUsers = maxNumUsers;
	}

	/**
	 * @param numUsers the numUsers to set
	 */
	public void setNumUsers(int numUsers) {
		this.numUsers = numUsers;
	}

	/**
	 * @return the administrator
	 */
	public User getAdministrator() {
		return administrator;
	}

	/**
	 * @param administrator the administrator to set
	 */
	public void setAdministrator(User administrator) {
		this.administrator = administrator;
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
