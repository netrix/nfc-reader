package pl.org.netrix.nfc_reader;

public class Common {
    public static String getHexString(byte[] raw) {
        String str = new String();

        for (byte b : raw) {
        	String hex = Integer.toHexString(b & 0xFF);
        	
        	if(hex.length() == 1)  	{
        		str += "0" + hex;
        	} else {
        		str += hex;
        	}
        }   

        return str;
    } 
}
