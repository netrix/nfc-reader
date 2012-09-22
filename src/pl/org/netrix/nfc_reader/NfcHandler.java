package pl.org.netrix.nfc_reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import pl.org.netrix.nfc_reader.nfc_handlers.IHandler;
import pl.org.netrix.nfc_reader.nfc_handlers.IHandlerFactory;
import pl.org.netrix.nfc_reader.nfc_handlers.MifareClassicFactory;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;

public class NfcHandler {
	
	private Activity mActivity = null;
	private NfcAdapter mAdapter = null;
	
	private PendingIntent mPendingIntent = null;
	private IntentFilter[] mIntentFilters = null;
	private String[][] mTechLists = null;
	
	private NfcStatus mStatus = null;
	private Logger mLogger = null;
	
	private Map<String, IHandlerFactory> mFactoryMap = null;
	
	public NfcHandler(Activity activity, NfcStatus status, Logger logger)
	{
		mActivity = activity;
		mStatus = status;
		mLogger = logger;
		
		// Acquairing adapter
		mAdapter = (NfcAdapter)NfcAdapter.getDefaultAdapter(activity);
		
		// Creating PendingIndent for foreground dispatching
		mPendingIntent = PendingIntent.getActivity(activity, 0, 
				new Intent(activity, activity.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		
		IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
		try {
			ndef.addDataType("*/*");
		} catch(MalformedMimeTypeException e) {
			throw new RuntimeException("fail", e);
		}
		
		mIntentFilters = new IntentFilter[] { ndef };
		mTechLists = new String[][] { getTechList(activity.getResources()) };
		
		// Handlers
		mFactoryMap = new HashMap<String, IHandlerFactory>();
		mFactoryMap.put("android.nfc.tech.MifareClassic", new MifareClassicFactory(mLogger, mStatus));
	}
	
	public void onResume()
	{
		mAdapter.enableForegroundDispatch(mActivity, mPendingIntent, mIntentFilters, mTechLists);
	}
	
	public void onPause()
	{
		mAdapter.disableForegroundDispatch(mActivity);
	}
	
	public boolean isNfcIntent(Intent intent)
	{
		return NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction());
	}
	
	public void handleNfcIntent(Intent intent)
	{
		Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

		for(String tech : tag.getTechList())
		{
			if(mFactoryMap.containsKey(tech))
			{
				IHandler handler = mFactoryMap.get(tech).createHandler();
				handler.handleTag(tag);
			}
		}
		
	}
	
	private static String[] getTechList(Resources resources)
	{
		ArrayList<String> list = new ArrayList<String>();
		XmlResourceParser xml = resources.getXml(R.xml.techlist);

		// Assumed simple parsing because of simple file structure
		for(;;)
		{
			int eventType = XmlPullParser.END_DOCUMENT;
			try {
				eventType = xml.next();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(eventType == XmlPullParser.TEXT)
			{
				list.add(xml.getText());
			}
			else if(eventType == XmlPullParser.END_DOCUMENT)
			{
				break;
			}
			
		}
		
		return list.toArray(new String[list.size()]);
	}
}
