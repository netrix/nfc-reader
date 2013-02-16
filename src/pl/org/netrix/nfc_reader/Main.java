package pl.org.netrix.nfc_reader;

import java.util.List;
import java.util.Vector;

import pl.org.netrix.nfc_reader.tabs.BrowserFragmentTab;
import pl.org.netrix.nfc_reader.tabs.LogFragmentTab;
import pl.org.netrix.nfc_reader.tabs.PageChangeListener;
import pl.org.netrix.nfc_reader.tabs.PagerAdapter;
import pl.org.netrix.nfc_reader.tabs.TabChangeListener;
import pl.org.netrix.nfc_reader.tabs.TabFactory;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.TabHost;

public class Main extends FragmentActivity {

	private Logger mLogger = null;
	private NfcHandler mNfcHandler = null;
	private NfcStatus mStatus = null;
	
	private ViewPager mViewPager = null;
	private TabHost mTabHost = null;
	private PagerAdapter mPagerAdapter = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		initializeTabs(savedInstanceState);
		
		// Initializing logger
		mLogger = new Logger();
		mLogger.pushStatus("onCreate");
			

		// NfcStatus
		mStatus = new NfcStatus();
		
		// NfcHandler
		mNfcHandler = new NfcHandler(this, mStatus, mLogger);
		
		// Passing intent
		Intent intent = getIntent();
		resolveIntent(intent);
	}
	
	public Logger getLogger() {
		return mLogger;
	}
	
	public NfcStatus getStatus() {
		return mStatus;
	}
	
	public NfcHandler getNfcHandler() {
		return mNfcHandler;
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("tab", mTabHost.getCurrentTabTag());
		super.onSaveInstanceState(outState);
	}
	
	private void initializeTabs(Bundle savedInstanceState) {
		
		// Initializing TabHost
		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
		mTabHost.setup();
		
		addTab(mTabHost.newTabSpec("Browser").setIndicator("Browser"));
		addTab(mTabHost.newTabSpec("Log").setIndicator("Log"));
				
		if(savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
		
		// Initializing Fragments
		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this, BrowserFragmentTab.class.getName()));
		fragments.add(Fragment.instantiate(this, LogFragmentTab.class.getName()));
		
		mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
		
		mViewPager = (ViewPager)findViewById(R.id.viewpager);
		mViewPager.setAdapter(mPagerAdapter);
		
		// Listeners
		mTabHost.setOnTabChangedListener(new TabChangeListener(mTabHost, mViewPager));
		mViewPager.setOnPageChangeListener(new PageChangeListener(mTabHost));	
	}
	
	private void addTab(TabHost.TabSpec tabSpec) {
		tabSpec.setContent(new TabFactory(this));
		mTabHost.addTab(tabSpec);
	}
	
	private void resolveIntent(Intent intent) {
		
		if(mNfcHandler.isNfcIntent(intent)) {
			mNfcHandler.handleNfcIntent(intent);
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
