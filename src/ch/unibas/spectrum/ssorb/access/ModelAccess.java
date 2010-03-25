package ch.unibas.spectrum.ssorb.access;

import java.util.LinkedList;
import java.util.List;

import ch.unibas.spectrum.ssorb.constants.Attribute;
import ch.unibas.spectrum.ssorb.exception.SSOrbConnectException;
import ch.unibas.spectrum.ssorb.helper.DomainHelper;
import ch.unibas.spectrum.ssorb.model.Model;

import com.aprisma.spectrum.core.idl.CsCAttribute.CsCOperator_e;
import com.aprisma.spectrum.core.idl.CsCAttribute.CsCValue;
import com.aprisma.spectrum.core.idl.CsCException.CsCSpectrumException;
import com.aprisma.spectrum.core.util.CsCorbaAttrFilterHelper;

public class ModelAccess {

	protected static CsCValue[] createAttributeFilter(int modelclassAttrID, int value) throws SSOrbConnectException, CsCSpectrumException {
		return CsCorbaAttrFilterHelper.createIntValueNode(modelclassAttrID, CsCOperator_e.CSC_EQUALS, value).getFilter();
	}

	protected static CsCValue[] createAttributeFilter(int modelclassAttrID, String value) throws SSOrbConnectException, CsCSpectrumException {
		return CsCorbaAttrFilterHelper.createTagStringNode(modelclassAttrID, CsCOperator_e.CSC_EQUALS_IGNORE_CASE, value).getFilter();
	}

	protected static int[] getModelIDs(CsCValue[] filter) throws CsCSpectrumException, SSOrbConnectException {
		return DomainHelper.getModelDomain().getModelIDListByAttrFilter(filter);
	}

	protected static List<Model> copyModels(int[] modelIDs) {
		final List<Model> modelSet = new LinkedList<Model>();
		for (int i = 0; i < modelIDs.length; i++) {
			Model m = new Model(modelIDs[i]);
			modelSet.add(m);
		}
		return modelSet;
	}

	public static List<Model> getModelByName(String modelname) throws CsCSpectrumException, SSOrbConnectException {
		return copyModels(getModelIDsByName(modelname));
	}

	public static int[] getModelIDsByName(String modelname) throws CsCSpectrumException, SSOrbConnectException {
		return getModelIDs(createAttributeFilter(Attribute.ModelName, modelname));
	}

	public static Model getModelByID(int modelID) {
		return new Model(modelID);
	}
}
