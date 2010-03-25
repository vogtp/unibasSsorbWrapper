package ch.unibas.spectrum.ssorb.access;

import java.util.Map;
import java.util.TreeMap;

import ch.unibas.spectrum.ssorb.constants.Relations;
import ch.unibas.spectrum.ssorb.exception.SSOrbConnectException;
import ch.unibas.spectrum.ssorb.helper.RelationHelper;
import ch.unibas.spectrum.ssorb.model.Model;
import ch.unibas.spectrum.ssorb.model.ServiceModel;

import com.aprisma.spectrum.core.idl.CsCException.CsCSpectrumException;

public class ServiceAccess extends ModelAccess {

	private static final String modelname = "Services";

	public static ServiceModel getRoot() throws CsCSpectrumException, SSOrbConnectException {
		return new ServiceModel(ModelAccess.getModelIDsByName(modelname)[0]);
	}

	public static ServiceModel getServiceByID(int modelID) throws CsCSpectrumException, SSOrbConnectException {
		return new ServiceModel(modelID);
	}

	public static Map<String, Model> getChildren(ServiceModel service) throws CsCSpectrumException, SSOrbConnectException {
		int[] assciationsIDs = RelationHelper.getAssciationsIDs(service.getID(), Relations.SlmContains);
		Map<String, Model> assocs = new TreeMap<String, Model>();
		extracted(assciationsIDs, assocs);
		assciationsIDs = RelationHelper.getAssciationsIDs(service.getID(), Relations.SlmMonitors);
		extracted(assciationsIDs, assocs);
		return assocs;
	}

	private static void extracted(int[] assciationsIDs, Map<String, Model> assocs) throws CsCSpectrumException, SSOrbConnectException {
		for (int i = 0; i < assciationsIDs.length; i++) {
			Model sm = new ServiceModel(assciationsIDs[i]);
			if (!sm.getMType().startsWith("SM")) {
				sm = new Model(assciationsIDs[i]);
			}
			assocs.put(sm.getName(), sm);
		}
	}

}
