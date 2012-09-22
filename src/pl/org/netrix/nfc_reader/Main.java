package pl.org.netrix.nfc_reader;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.content.res.XmlResourceParser;
import android.nfc.NfcAdapter;
import android.nfc.tech.MifareClassic;
import android.nfc.Tag;
import android.os.Bundle;
import android.widget.TextView;

public class Main extends Activity {

	private Logger mLogger = null;
	private NfcStatus mStatus = null;
	private NfcHandler mNfcHandler = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Logger
		mLogger = new Logger((TextView)findViewById(R.id.logger));
		mLogger.pushStatus("onCreate");
		
		// NfcStatus
		mStatus = new NfcStatus((TextView)findViewById(R.id.nfc_status));
		
		// NfcHandler
		mNfcHandler = new NfcHandler(this, mStatus, mLogger);
		
		// Passing intent
		Intent intent = getIntent();
		resolveIntent(intent);
	}
	
	void resolveIntent(Intent intent) {
		
		if(mNfcHandler.isNfcIntent(intent)) {
			mNfcHandler.handleNfcIntent(intent);
		} else {
			mStatus.setStatus("Scan Tag");
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		mLogger.pushStatus("onStart");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mNfcHandler.onResume();
		mLogger.pushStatus("onResume");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mNfcHandler.onPause();
		mLogger.pushStatus("onPause");
	}
	
	@Override
	public void onStop() {
		super.onStop();
		mLogger.pushStatus("onStop");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mLogger.pushStatus("onDestroy");
	}
	
	@Override
	public void onNewIntent(Intent intent) {
		resolveIntent(intent);
		mLogger.pushStatus("onNewIntent");
	}
}
