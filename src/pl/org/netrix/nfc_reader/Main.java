package pl.org.netrix.nfc_reader;

import java.io.IOException;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NfcAdapter;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.Tag;
import android.os.Bundle;
import android.widget.TextView;

public class Main extends Activity {

	private IntentFilter[] mfilters = null;
	private String[][] mTechLists = null;
	private TextView mNfcStatusView = null;
	private TextView mAppStatusLog = null;
	private NfcAdapter mAdapter = null;
	private PendingIntent mPending = null;
	
    // Hex help
    private static final byte[] HEX_CHAR_TABLE = { (byte) '0', (byte) '1',
            (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6',
            (byte) '7', (byte) '8', (byte) '9', (byte) 'A', (byte) 'B',
            (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F' };
	
    private void pushStatus(String text) {
    	mAppStatusLog.setText(mAppStatusLog.getText() + "\n" + text);
    }
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		mNfcStatusView = (TextView)findViewById(R.id.text_info);
		mNfcStatusView.setText("Hello, World from code");
		
		mAppStatusLog = (TextView)findViewById(R.id.app_status_log);
		pushStatus("onCreate");
		
		mAdapter = (NfcAdapter)NfcAdapter.getDefaultAdapter(this);
		
		mPending = PendingIntent.getActivity(this, 0, 
				new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		
		IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
		try {
			ndef.addDataType("*/*");
		} catch(MalformedMimeTypeException e) {
			throw new RuntimeException("fail", e);
		}
		
		mfilters = new IntentFilter[] { ndef };
		mTechLists = new String[][] { new String[] { MifareClassic.class.getName() } };
		
		Intent intent = getIntent();
		resolveIntent(intent);
	}
	
	void resolveIntent(Intent intent) {
		
		String action = intent.getAction();
		
		if(NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
			
			Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			byte[] data;
						
//			NfcA nfca = NfcA.get(tagFromIntent);
//			
//			try {
//				nfca.connect();
//				
//				mNfcStatusView.setText("Pending...");
//				
//				data = nfca.getAtqa();
//				mNfcStatusView.setText(getHexString(data, data.length));
//				
//			} catch (IOException e) {
//				mNfcStatusView.setText("dupa");
//			}
						
			MifareClassic mfc = MifareClassic.get(tagFromIntent);
			
			
			try {
				mfc.connect();
				
				boolean auth = false;
				String cardData = null;
				
				mNfcStatusView.setText("Pending...");
				byte[] key = new byte[] { (byte)0xA0, (byte)0xA1, (byte)0xA2, (byte)0xA3, (byte)0xA4, (byte)0xA5 };
				auth = mfc.authenticateSectorWithKeyA(0, MifareClassic.KEY_MIFARE_APPLICATION_DIRECTORY);
				
				if(auth) {
					mNfcStatusView.setText("auth ok");
					data = mfc.readBlock(0);
					cardData = getHexString(data, data.length);
					
					if(cardData != null) {
						mNfcStatusView.setText(mNfcStatusView.getText() + "\n" + cardData);
					} else {
						mNfcStatusView.setText("Failed to read");
					}
				} else {
					mNfcStatusView.setText("auth no ok");
				}
			} catch (IOException e) {
				mNfcStatusView.setText("Dupa");
			}
		} else {
			mNfcStatusView.setText("Scan Tag");
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		pushStatus("onStart");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mAdapter.enableForegroundDispatch(this, mPending, mfilters, mTechLists);
		pushStatus("onResume");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mAdapter.disableForegroundDispatch(this);
		pushStatus("onPause");
	}
	
	@Override
	public void onStop() {
		super.onStop();
		pushStatus("onStop");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		pushStatus("onDestroy");
	}
	
	@Override
	public void onNewIntent(Intent intent) {
		resolveIntent(intent);
		pushStatus("onNewIntent");
	}
	
    public static String getHexString(byte[] raw, int len) {
        byte[] hex = new byte[2 * len];
        int index = 0;
        int pos = 0;

        for (byte b : raw) {
            if (pos >= len)
                break;

            pos++;
            int v = b & 0xFF;
            hex[index++] = HEX_CHAR_TABLE[v >>> 4]; 
            hex[index++] = HEX_CHAR_TABLE[v & 0xF];
        }   

        return new String(hex);
    } 
}
