package it.mapyou.persistence;
import it.mapyou.model.EndPoint;
import it.mapyou.model.MapMe;
import it.mapyou.model.Mapping;
import it.mapyou.model.Route;
import it.mapyou.model.SimpleSegment;
import it.mapyou.model.StartPoint;
import it.mapyou.model.User;
import it.mapyou.persistence.DAOManager;

import java.util.Random;


public class DataModelGenerator {

	private DAOManager dao;
	
	public DataModelGenerator(DAOManager dao){
		this.dao = dao;
	}
	
	public void generateUsers(int numUser, int mappingForUser, int numSegmentForMapping,
			int numMapMeForUser){
		
		Random r = new Random();
		for(int i=100; i<numUser; i++){
			User u = new User();
			u.setNickname("nickname_"+i);
			u.setEmail("email_"+i);
			u.setFirstname("firstName_"+i);
			u.setLastname("lastName_"+i);
			u.setModelID(i+1);
			u.setPassword("password_"+i);
			if(dao.getUserDAO().insert(u)){
				for(int j=100; j<numMapMeForUser; j++){
					MapMe m = new MapMe();
					m.setAdministrator(u);
					m.setModelID(j+1);
					for(int k=100; k<mappingForUser; k++){
						Mapping mp = new Mapping();
						Route s = new Route();
						StartPoint sp = new StartPoint();
						sp.setLatitude(r.nextDouble());
						sp.setLongitude(r.nextDouble());
						EndPoint ep = new EndPoint();
						ep.setLatitude(r.nextDouble());
						ep.setLongitude(r.nextDouble());
						s.setEndPoint(ep);
						s.setStartPoint(sp);
						s.setModelID(k+1);
						for(int z=100; z<numSegmentForMapping; z++){
							SimpleSegment s1 = new SimpleSegment();
							StartPoint sp1 = new StartPoint();
							sp1.setLatitude(r.nextDouble());
							sp1.setLongitude(r.nextDouble());
							EndPoint ep1 = new EndPoint();
							ep1.setLatitude(r.nextDouble());
							ep1.setLongitude(r.nextDouble());
							s1.setEndPoint(ep1);
							s1.setStartPoint(sp1);
							s.addSegment(s1);
						}
						mp.setUser(u);
						mp.setRoute(s);
						m.insertMapping(mp);
					}
					dao.getMapMeDAO().insert(m);
				}
			}
			
		}
	}
}
