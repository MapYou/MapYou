/**
 * 
 */
package it.mapyou.model;

import android.os.Parcelable;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface MapmeState extends Parcelable{

	public static final String ON_CREATE = "on_create";

	public static final String ON_DESTROY = "on_destroy";

	public static final String ON_START = "on_start";
	
	public String getState();
}
