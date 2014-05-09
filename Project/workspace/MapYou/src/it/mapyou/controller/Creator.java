
/**
 * 
 */
package it.mapyou.controller;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface Creator<T> {

	public T create (Class<? extends T> clazz);
	
	public T create (String type);
}

