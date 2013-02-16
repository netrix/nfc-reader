package pl.org.netrix.nfc_reader.tabs;

import pl.org.netrix.nfc_reader.Main;
import pl.org.netrix.nfc_reader.NfcHandler.ITagHandleListener;
import pl.org.netrix.nfc_reader.NfcStatus;
import pl.org.netrix.nfc_reader.R;
import pl.org.netrix.nfc_reader.TagInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class BrowserFragmentTab extends Fragment implements NfcStatus.IStatusListener, ITagHandleListener {
	
	private TextView mStatusView = null;
	
	private ListView mTechListView = null;
	private ArrayAdapter<String> mTechListAdapter = null;
	
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
		activity.getNfcHandler().registerTagHandleListener(this);
		
		mStatusView = (TextView)activity.findViewById(R.id.nfc_status);
		mStatusView.setText(activity.getStatus().getText());
		
		mTechListView = (ListView)getActivity().findViewById(R.id.tech_list);
		mTechListAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_row, R.id.rowTextView);
		mTechListAdapter.add("---");
		mTechListView.setAdapter(mTechListAdapter);
	}

	public void update(String text) {
		mStatusView.setText(text);
	}

	public void tagUpdated(TagInfo tagInfo) {
		mTechListAdapter.clear();
		mTechListAdapter.addAll(tagInfo.getTechList());
	}
}
