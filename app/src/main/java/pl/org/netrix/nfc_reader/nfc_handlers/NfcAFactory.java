package pl.org.netrix.nfc_reader.nfc_handlers;

import pl.org.netrix.nfc_reader.Logger;
import pl.org.netrix.nfc_reader.NfcStatus;

public class NfcAFactory extends HandlerFactory {

	public NfcAFactory(Logger logger, NfcStatus status) {
		super(logger, status);
	}

	public IHandler createHandler() {
		return new NfcAHandler(mLogger, mStatus);
	}

}
