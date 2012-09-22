package pl.org.netrix.nfc_reader;

public class Common {
    public static String getHexString(byte[] raw) {
        String str = new String();

        for (byte b : raw) {
            str += Integer.toHexString(b & 0xFF);
        }   

        return str;
    } 
}
