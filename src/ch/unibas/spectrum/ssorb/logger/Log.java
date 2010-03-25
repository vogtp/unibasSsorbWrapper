package ch.unibas.spectrum.ssorb.logger;

import ch.unibas.spectrum.ssorb.exception.SSOrbConnectException;

public class Log {

	public static void warning(SSOrbConnectException e, String string) {
		System.out.println(string);
		System.out.println(e.toString());
	}

}
