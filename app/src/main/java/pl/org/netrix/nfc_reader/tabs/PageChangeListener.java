package pl.org.netrix.nfc_reader.tabs;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TabHost;

public class PageChangeListener implements OnPageChangeListener {

	private TabHost mTabHost = null;
	
	public PageChangeListener(TabHost tabHost) {
		mTabHost = tabHost;
	}
	
	public void onPageScrollStateChanged(int arg0) {
	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	public void onPageSelected(int position) {
		mTabHost.setCurrentTab(position);
	}

}
