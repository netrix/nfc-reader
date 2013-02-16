package pl.org.netrix.nfc_reader.tabs;

import android.content.Context;
import android.view.View;
import android.widget.TabHost.TabContentFactory;

public class TabFactory implements TabContentFactory {

	private Context mContext = null;
	
	public TabFactory(Context context) {
		mContext = context;
	}
	
	public View createTabContent(String tag) {
		View v = new View(mContext);
		v.setMinimumWidth(0);
		v.setMinimumHeight(0);
		return v;
	}

}
