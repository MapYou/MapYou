/**
 * 
 */
package it.mapyou.view.settings;

import android.app.Activity;
import it.mapyou.util.UtilAndroid;
import it.mapyou.view.Setting;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class DeleteAccount extends Setting{

	/**
	 * @param act
	 * @param idView
	 */
	public DeleteAccount(Activity act, int idView) {
		super(act, idView);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void performeSetting() {
		UtilAndroid.makeToast(act, "This is a deleteAccount setting", 5000);
	}

}
