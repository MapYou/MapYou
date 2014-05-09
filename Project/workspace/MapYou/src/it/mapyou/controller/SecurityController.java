/**
 * 
 */
package it.mapyou.controller;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface SecurityController<T, Z> {
	
	public Z encode(T t);
	public T decode(Z z);

}
