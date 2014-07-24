/**
 * 
 */
package it.mapyou.core;

import android.os.Parcelable;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface Segment extends Parcelable{
	
	public Point getStartPoint();
	public Point getEndPoint();
	public double calculateLenght();

}
