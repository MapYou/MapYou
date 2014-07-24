/**
 * 
 */
package it.mapyou.core;

import java.util.Enumeration;
import java.util.Vector;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public abstract class Subject {

	private Vector<ObserverSubject> observers;
	
	public void addObserver(ObserverSubject...observerSubjects){
		for(int i=0; i<observerSubjects.length; i++)
			observers.add(observerSubjects[i]);
	}
	
	public boolean removeObserver(ObserverSubject o){
		try {
			return observers.remove(o);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	public void removeAllObserver(){
		Enumeration<ObserverSubject> en = observers.elements();
		while(en.hasMoreElements())
			removeObserver(en.nextElement());
	}
	
	public void notifyObserver(ObserverSubject o){
		if(observers.contains(o))
			o.update(this);
	}
	
	public void notifyAllObserver(){
		Enumeration<ObserverSubject> en = observers.elements();
		while(en.hasMoreElements())
			en.nextElement().update(this);
	}
}
