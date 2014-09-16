/**
 * 
 */
package it.mapyou.util;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class Utils {

	public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
	
	/**
	 * Calcola la distanza tra due punti geografici (in Km).
	 */
	public static double getDistance(double latA, double lonA, double latB, double lonB){
		double R = 6372.795477598;
		double pi = 3.1415927;

//		double radLatA = pi * 41.129761285949 / 180;
//		double radLonA = pi * 14.782620817423 / 180;
//		double radLatB = pi * 41.560254489813 / 180;
//		double radLonB = pi * 14.662716016173 / 180;
		double radLatA = pi * latA / 180;
		double radLonA = pi * lonA / 180;
		double radLatB = pi * latB / 180;
		double radLonB = pi * lonB / 180;

		double te = Math.abs(radLonA - radLonB);

		double P = Math.acos((Math.sin(radLatA) * Math.sin(radLatB)) + (Math.cos(radLatA) * Math.cos(radLatB) * Math.cos(te)));

		double distance = P * R;
		return distance;
	}
}
