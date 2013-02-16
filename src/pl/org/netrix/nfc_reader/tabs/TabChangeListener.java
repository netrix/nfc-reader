package pl.org.netrix.nfc_reader.tabs;

import android.support.v4.view.ViewPager;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class TabChangeListener implements OnTabChangeListener {

	private TabHost mTabHost = null;
	private ViewPager mViewPager = null;
	
	public TabChangeListener(TabHost tabHost, ViewPager viewPager) {
		mTabHost = tabHost;
		mViewPager = viewPager;
	}
	
	public void onTabChanged(String tag) {
		int pos = mTabHost.getCurrentTab();
		mViewPager.setCurrentItem(pos);
	}

}
