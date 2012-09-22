package pl.org.netrix.nfc_reader;

import android.widget.TextView;

public class NfcStatus {

	private TextView mTextView = null;
	
	public NfcStatus(TextView textView)
	{
		mTextView = textView;
	}
	
	public void setStatus(String status)
	{
		mTextView.setText(status);
	}
}
