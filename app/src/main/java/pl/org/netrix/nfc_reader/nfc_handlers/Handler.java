package pl.org.netrix.nfc_reader.nfc_handlers;

import pl.org.netrix.nfc_reader.Logger;
import pl.org.netrix.nfc_reader.NfcStatus;

public abstract class Handler implements IHandler {

	protected Logger mLogger = null;
	protected NfcStatus mStatus = null;
	
	protected Handler(Logger logger, NfcStatus status)
	{
		mLogger = logger;
		mStatus = status;
	}

}
