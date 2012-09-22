package pl.org.netrix.nfc_reader.nfc_handlers;

import pl.org.netrix.nfc_reader.Logger;
import pl.org.netrix.nfc_reader.NfcStatus;

public abstract class HandlerFactory implements IHandlerFactory {

	public Logger mLogger = null;
	public NfcStatus mStatus = null;
	
	public HandlerFactory(Logger logger, NfcStatus status)
	{
		mLogger = logger;
		mStatus = status;
	}
}
