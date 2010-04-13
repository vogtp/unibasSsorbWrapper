package ch.unibas.spectrum.ssorb.helper;

import java.util.HashMap;
import java.util.Map;

import ch.unibas.spectrum.ssorb.exception.SSOrbConnectException;
import ch.unibas.spectrum.ssorb.logger.Debug;
import ch.unibas.spectrum.ssorb.model.Model;

import com.aprisma.spectrum.core.idl.CsCTypeCatalog;
import com.aprisma.spectrum.core.idl.CsCError.CsCError_e;
import com.aprisma.spectrum.core.idl.CsCException.CsCSpectrumException;
import com.aprisma.spectrum.core.idl.CsCRelationPackage.CsCRelPropList;
import com.aprisma.spectrum.core.idl.CsCRelationPackage.CsCRelProperties;
import com.aprisma.spectrum.core.idl.CsCRelationPackage.CsCSide_e;

public class RelationHelper {

	public static Map<String, Model> getAllAssciations(int modelID) throws CsCSpectrumException, SSOrbConnectException {
		CsCTypeCatalog tc = DomainHelper.getModelDomain().getTypeCatalog();
		CsCRelPropList rpl = tc.getAllRelPropList();
		CsCRelProperties relprop = null;
		Map<String, Model> assocModels = new HashMap<String, Model>();
		for (int i = 0; i < rpl.list.length; i++) {
			relprop = rpl.list[i];
			if (relprop.error == CsCError_e.SUCCESS) {
				assocModels.putAll(getAssciations(modelID, relprop.relationID));
			}
		}
		return assocModels;
	}

	public static int[] getRightAssciationsIDs(int modelID, int relationID) throws CsCSpectrumException, SSOrbConnectException {
		return DomainHelper.getModelDomain().getAssocModelIDList(relationID, modelID, CsCSide_e.CSC_RIGHT_SIDE);
	}

	public static int[] getLeftAssciationsIDs(int modelID, int relationID) throws CsCSpectrumException, SSOrbConnectException {
		return DomainHelper.getModelDomain().getAssocModelIDList(relationID, modelID, CsCSide_e.CSC_LEFT_SIDE);
	}

	public static Map<String, Model> getAssciations(int modelID, int relationID) throws CsCSpectrumException, SSOrbConnectException {
		int[] assocModelIDList = getLeftAssciationsIDs(modelID, relationID);
		Map<String, Model> assocModels = new HashMap<String, Model>(assocModelIDList.length);
		for (int j = 0; j < assocModelIDList.length; j++) {
			Model model = new Model(assocModelIDList[j]);
			assocModels.put(model.getName(), model);
			Debug.debug("Model: " + model.getName());
		}
		return assocModels;
	}

	public static Map<String, Model> getAllAssciations(Model model) throws CsCSpectrumException, SSOrbConnectException {
		return getAllAssciations(model.getID());
	}

	public static Map<String, Model> getAssciations(Model model, int relationID) throws CsCSpectrumException, SSOrbConnectException {
		return getAssciations(model.getID(), relationID);
	}

}
