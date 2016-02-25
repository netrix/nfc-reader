package pl.org.netrix.nfc_reader.nfc_handlers;

import android.nfc.Tag;

public interface IHandler {
	
	public void handleTag(Tag tag);
}
