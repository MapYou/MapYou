S/**
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
	
	@Override
	public boolean equals(Object o){
		if(o instanceof SubjectModel){
			return modelID==((SubjectModel)o).getModelID();
		}else
			return false;
	}
}
