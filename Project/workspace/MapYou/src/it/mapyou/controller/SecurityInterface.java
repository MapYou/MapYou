/**
 * 
 */
package it.mapyou.controller;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public interface SecurityInterface<T, Z> {
	
	public Z encode(T t);
	public T decode(Z z);

}
