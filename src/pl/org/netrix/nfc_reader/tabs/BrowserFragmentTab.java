package pl.org.netrix.nfc_reader.tabs;

import pl.org.netrix.nfc_reader.Main;
import pl.org.netrix.nfc_reader.NfcStatus;
import pl.org.netrix.nfc_reader.R;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class BrowserFragmentTab extends Fragment implements NfcStatus.IStatusListener {
	
	private TextView mStatusView = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.browser_fragment, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		Main activity = (Main)getActivity();
		activity.getStatus().registerListener(this);
		
		mStatusView = (TextView)activity.findViewById(R.id.nfc_status);
		mStatusView.setText(activity.getStatus().getText());
	}

	public void update(String text) {
		mStatusView.setText(text);
	}
}
