package pl.org.netrix.nfc_reader.nfc_handlers;

import pl.org.netrix.nfc_reader.Logger;
import pl.org.netrix.nfc_reader.NfcStatus;

public class MifareClassicFactory extends HandlerFactory {
	
	public MifareClassicFactory(Logger logger, NfcStatus status) {
		super(logger, status);
	}

	public IHandler createHandler() {
		return new MifareClassicHandler(mLogger, mStatus);
	}
	
}
