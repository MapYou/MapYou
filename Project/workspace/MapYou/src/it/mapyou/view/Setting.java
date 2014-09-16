/**
 * 
 */
package it.mapyou.view;

import android.app.Activity;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public abstract class Setting {

	protected int idView;
	protected Activity act;

	/**
	 * @param idView
	 */
	public Setting(Activity act, int idView) {
		super();
		this.idView = idView;
		this.act = act;
	}
	
	/**
	 * @return the act
	 */
	public Activity getAct() {
		return act;
	}
	
	/**
	 * @return the idView
	 */
	public int getIdView() {
		return idView;
	}

	/**
	 * @param idView the idView to set
	 */
	public void setIdView(int idView) {
		this.idView = idView;
	}

	public abstract void performeSetting();
}
