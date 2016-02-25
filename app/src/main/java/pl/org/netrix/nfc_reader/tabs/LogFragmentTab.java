package pl.org.netrix.nfc_reader.tabs;

import pl.org.netrix.nfc_reader.Main;
import pl.org.netrix.nfc_reader.R;
import pl.org.netrix.nfc_reader.Logger;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LogFragmentTab extends Fragment implements Logger.ILoggerListener {

	private TextView mLoggerView = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.log_fragment, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		Main activity = (Main)getActivity();
		activity.getLogger().registerListener(this);
		
		mLoggerView = (TextView)activity.findViewById(R.id.logger);
	}

	public void update(String text) {
		
		mLoggerView.setText(text);
	}
}
