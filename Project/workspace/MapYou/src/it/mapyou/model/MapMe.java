/**
 * 
 */
package it.mapyou.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class MapMe extends SubjectModel implements Parcelable {


	private User administrator;
	private List<Mapping> mapping;
	private GregorianCalendar creationDate;
	private int numUsers, maxNumUsers;
	private String name;
	private Route route;
	private String startAddress;
	private String endAddress;
	private int idmapme;
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
		mapping= new ArrayList<Mapping>();
		creationDate = new GregorianCalendar();
	}

	public MapMe(String name) {
		this.name = name;
		mapping= new ArrayList<Mapping>();
		creationDate = new GregorianCalendar();
	}

	public MapMe(Parcel s){
		mapping = new ArrayList<Mapping>();
		setIdmapme(s.readInt());
		setName(s.readString());
		setStartAddress(s.readString());
		setEndAddress(s.readString());
		setNumUsers(s.readInt());
		setMaxNumUsers(s.readInt());
		//		setCreationDate((GregorianCalendar) s.readSerializable());
		setAdministrator((User) s.readSerializable());
		setRoute((Route) s.readParcelable(Route.class.getClassLoader()));
		s.readList(mapping, Mapping.class.getClassLoader());
	}

	/**
	 * @param source
	 */
	public void readFromParcel(Parcel source) {
		// TODO Auto-generated method stub
		//		String nickname = source.readString();
		//		double slat = source.readDouble();
		//		double slong = source.readDouble();
		//		double elat = source.readDouble();
		//		double elong = source.readDouble();
		//		name = source.readString();
		//		startAddress  =source.readString();
		//		endAddress = source.readString();
		//		administrator = new User();
		//		administrator.setNickname(nickname);
		//		StartPoint sp = new StartPoint();
		//		EndPoint ep = new EndPoint();
		//		sp.setLatitude(slat);
		//		sp.setLongitude(slong);
		//		ep.setLatitude(elat);
		//		ep.setLongitude(elong);
		//		route = new Route();
		//		route.setEndPoint(ep);
		//		route.setStartPoint(sp);
	}

	/* (non-Javadoc)
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		//		dest.writeString(administrator.getNickname());
		//		StartPoint sp = route.getStartPoint();
		//		EndPoint ep = route.getEndPoint();
		//		dest.writeDouble(sp.getLatitude());
		//		dest.writeDouble(sp.getLongitude());
		//		dest.writeDouble(ep.getLatitude());
		//		dest.writeDouble(ep.getLongitude());
		dest.writeString(name);
		dest.writeString(startAddress);
		dest.writeString(endAddress);
		dest.writeInt(numUsers);
		dest.writeInt(maxNumUsers);

		dest.writeInt(idmapme);
//		dest.writeValue(creationDate);
 
		//		dest.writeValue(creationDate);
 
		dest.writeSerializable(administrator);
		dest.writeParcelable(route, flags);
		dest.writeList(mapping);
	}
	
	/**
	 * @return the idmapme
	 */
	public int getIdmapme() {
		return idmapme;
	}
	
	/**
	 * @param idmapme the idmapme to set
	 */
	public void setIdmapme(int idmapme) {
		this.idmapme = idmapme;
	}

	/**
	 * @return the startAddress
	 */
	public String getStartAddress() {
		return startAddress;
	}
	/**
	 * @return the endAddress
	 */
	public String getEndAddress() {
		return endAddress;
	}
	/**
	 * @param endAddress the endAddress to set
	 */
	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}
	/**
	 * @param startAddress the startAddress to set
	 */
	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
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

	public boolean insertMapping(Mapping...m){
		try {
			for(int i=0; i<m.length; i++)
				mapping.add(m[i]);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public boolean removeMapping(Mapping...m){
		try {
			for(int i=0; i<m.length; i++)
				mapping.remove(m[i]);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public boolean removeAllMapping(){
		return mapping.removeAll(mapping);
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

	/**
	 * @return the mapping
	 */
	public List<Mapping> getMapping() {
		return mapping;
	}

	public List<Mapping> getDistinctMapping() {
		List<Mapping> m = new ArrayList<Mapping>();
		for(int i=0; i<mapping.size(); i++){
			Mapping mp = mapping.get(i);
			int k=0;
			for(int j=0; j<m.size(); j++){
				Mapping mp2 = m.get(j);
				if(mp2.getUser().getNickname().equals(mp.getUser().getNickname()))
					k++;
			}
			if(k==0)
				m.add(mp);
		}
		return m;
	}

	public List<Mapping> getMapping(User u) {
		List<Mapping> m = new ArrayList<Mapping>();
		for(int i=0; i<mapping.size(); i++){
			Mapping mp = mapping.get(i);
			if(mp.getUser().getNickname().equals(u.getNickname()))
				m.add(mp);
		}
		Collections.sort(m, new Comparator<Mapping>() {
			@Override
			public int compare(Mapping lhs, Mapping rhs) {
				// TODO Auto-generated method stub
				return lhs.getDate().compareTo(rhs.getDate());
			}
		});
		return m;
	}

	/**
	 * @param mapping the mapping to set
	 */
	public void setMapping(List<Mapping> mapping) {
		this.mapping = mapping;
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
