package pl.org.netrix.nfc_reader.nfc_handlers;

import java.io.IOException;

import pl.org.netrix.nfc_reader.Common;
import pl.org.netrix.nfc_reader.Logger;
import pl.org.netrix.nfc_reader.NfcStatus;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;

public class MifareClassicHandler extends Handler {

	public MifareClassicHandler(Logger logger, NfcStatus status) {
		super(logger, status);
	}

	public void handleTag(Tag tag) {
		
		MifareClassic mfc = MifareClassic.get(tag);
		byte[] data;
		
		try {
			mfc.connect();
			
			boolean auth = false;
			String cardData = null;
			
			mStatus.setStatus("Pending...");
			auth = mfc.authenticateSectorWithKeyA(0, MifareClassic.KEY_MIFARE_APPLICATION_DIRECTORY);
			
			if(auth) {
				mStatus.setStatus("auth ok");
				data = mfc.readBlock(0);
				cardData = Common.getHexString(data);
				
				if(cardData != null) {
					mStatus.setStatus("auth ok\n" + cardData);
				} else {
					mStatus.setStatus("Failed to read");
				}
			} else {
				mStatus.setStatus("auth no ok");
			}
		} catch (IOException e) {
			mStatus.setStatus("Dupa");
		}

	}

}
