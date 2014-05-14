package it.mapyou.persistence;

import java.util.Random;


public class StringGenerator {

	private static char[] alp = {
		'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p'
		,'q','r','s','t','u','v','y','x','w','z','1','2','3','4','5','6','7','8','9','0',
		'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P'
		,'Q','R','S','T','U','V','Y','X','W','Z'
		};

	public static String generateString(){
		Random r = new Random();
		
		int dim = r.nextInt(10)+4;
		StringBuilder s = new StringBuilder();
		for(int i=0; i<dim; i++)
			s.append(alp[r.nextInt(26)]);
		return s.toString();
	}
	
	

}
