package ch.unibas.spectrum.ssorb.helper;

import java.util.Properties;

import ch.unibas.spectrum.ssorb.exception.SSOrbConnectException;

import com.aprisma.spectrum.core.idl.CsCModelDomain;
import com.aprisma.util.corba.CORBAHelper;

public class DomainHelper {

	private static Properties props;
	private static CORBAHelper helper;
	private static CsCModelDomain modelDomain;
	private static String spectroServer = "spectrum";

	public static void setUsername(String username) {
		setProperty("user.name", username);
	}

	public static void setPassword(String pw) {
		setProperty("CORBAHelper.password", pw);
	}

	private static void setProperty(String key, String value) {
		if (key == null) {
			return;
		}
		if (props == null) {
			props = new Properties();
			props.put("ORBwarn", "2");
		}
		props.setProperty(key, value);
	}

	public static CORBAHelper getHelper() throws SSOrbConnectException {
		if (helper == null) {
			helper = CORBAHelper.getHelperImpl();
			if (helper == null) {
				throw new SSOrbConnectException("Helper stays null");
			}
			initHelper();
		}
		return helper;
	}

	private static void initHelper() throws SSOrbConnectException {
		if (helper == null) {
			return;
		}
		if (!helper.init(null, props)) {
			throw new SSOrbConnectException("Cannot Initalise helper");
		}
	}

	public static CsCModelDomain getModelDomain(String spectroServer)
			throws SSOrbConnectException {
		if (spectroServer != null) {
			setSpectroServer(spectroServer);
		}
		return getModelDomain();
	}

	public static CsCModelDomain getModelDomain() throws SSOrbConnectException {
		if (modelDomain == null) {
			// FIXME make domain a parameter
			try {
				modelDomain = (CsCModelDomain) getHelper()
						.getObjectImplementation(CsCModelDomain.class,
								getSpectroServer());
			} catch (Throwable e) {
				throw new SSOrbConnectException(e);
			}
		}
		return modelDomain;
	}

	public static String getSpectroServer() {
		return spectroServer;
	}

	public static void setSpectroServer(String spectroServer) {
		DomainHelper.spectroServer = spectroServer;
	}

}
