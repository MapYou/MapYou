/**
 * 
 */
package it.mapyou.model;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public abstract class SubjectModel extends Subject{

	protected int modelID;
	
	/**
	 * @return the modelID
	 */
	public final int getModelID() {
		return modelID;
	}
	
	/**
	 * @param modelID the modelID to set
	 */
	public final void setModelID(int modelID) {
		this.modelID = modelID;
	}
}
