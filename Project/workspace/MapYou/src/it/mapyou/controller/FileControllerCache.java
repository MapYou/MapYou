/**
 * 
 */
package it.mapyou.controller;

import it.mapyou.util.UtilAndroid;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class FileControllerCache implements Controller{

	private String name_of_file;
	private Context c;
	private static FileControllerCache instance;

	private FileControllerCache(String n) {
		this.name_of_file = n;
	}
	
	/**
	 * @return the instance
	 */
	public static FileControllerCache getInstance(String n) {
		if(instance==null)
			instance = new FileControllerCache(n);
		return instance;
	}

	public void write(String text) throws Exception{

		FileOutputStream f=null;
		try {
			f= c.openFileOutput(name_of_file, Activity.MODE_PRIVATE);
			f.write(text.toString().getBytes());

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			f.flush();
			f.close();
		}


	}

	public void read() throws Exception{

		BufferedReader reader=null;
		try {
			FileInputStream f= c.openFileInput(name_of_file);
			reader= new BufferedReader(new InputStreamReader(f));
			StringBuffer bf=new StringBuffer();
			String line=null;

			while((line=reader.readLine()) !=null){
				bf.append(line);
			}
			UtilAndroid.makeToast(c.getApplicationContext(), ""+bf.length(), 2000);

		} catch (Exception e) {
			UtilAndroid.makeToast(c.getApplicationContext(), "Doesn't read from file", 500);

		}
		finally{
			if(reader!=null)
				reader.close();

		}
	}

	public String readS() throws Exception{

		BufferedReader reader=null;
		try {
			FileInputStream f= c.openFileInput(name_of_file);
			reader= new BufferedReader(new InputStreamReader(f));
			StringBuffer bf=new StringBuffer();
			String line=null;

			while((line=reader.readLine()) !=null){
				bf.append(line);
			}

			return bf.toString();

		} catch (Exception e) {
			if( e instanceof FileNotFoundException)
				{
				FileOutputStream ff=c.openFileOutput(name_of_file, 0);
				ff.close();
				return readS();
				}
			else return null;
		}
		finally{
			if(reader!=null)
				reader.close();

		}
	}

	/* (non-Javadoc)
	 * @see it.mapyou.controller.Controller#init(java.lang.Object[])
	 */
	@Override
	public void init(Object... parameters) throws Exception {
		Object o = parameters[0];
		if(o instanceof Context){
			this.c = (Context)o;
		}else
			throw new IllegalArgumentException("The first argument of 'parameters' must be a Context object.");
	}

}
