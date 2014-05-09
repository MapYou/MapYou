/**
 * 
 */
package it.mapyou.network;

import it.mapyou.model.SubjectModel;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface Server {

	public boolean connect(String...parameters);
	
	public boolean disconnect();
	
	public ResultSet<? extends SubjectModel> query(String...condition);
}
