package pl.org.netrix.nfc_reader;

import android.widget.TextView;

public class Logger {
	
	private TextView mTextView = null;
	
	public Logger(TextView textView) {
		mTextView = textView;
	}
	
    public void pushStatus(String text) {
    	mTextView.setText(mTextView.getText() + "\n" + text);
    }
}
