/**
 * 
 */
package it.mapyou.cache;

import it.mapyou.util.UtilAndroid;
import it.mapyou.view.Util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class FileControllerCache  {

	private String name_of_file;
	private Context c;

	public FileControllerCache(String n, Context c) {

		this.name_of_file=n;
		this.c=c;
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
			UtilAndroid.makeToast(c.getApplicationContext(), ""+bf.length(), 500);

		} catch (Exception e) {
			UtilAndroid.makeToast(c.getApplicationContext(), "Doesn't read from file", 500);

		}
		finally{
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
			return null;
		}
		finally{

			reader.close();

		}
	}

}
