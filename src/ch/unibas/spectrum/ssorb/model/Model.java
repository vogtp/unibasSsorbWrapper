package ch.unibas.spectrum.ssorb.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.omg.PortableServer.Servant;

import ch.unibas.spectrum.ssorb.callbacks.AttrValCallback;
import ch.unibas.spectrum.ssorb.constants.Attribute;
import ch.unibas.spectrum.ssorb.exception.SSOrbConnectException;
import ch.unibas.spectrum.ssorb.helper.DomainHelper;

import com.aprisma.spectrum.core.idl.CsCAttrValWatchCB;
import com.aprisma.spectrum.core.idl.CsCAttrValWatchCBHelper;
import com.aprisma.spectrum.core.idl.CsCAttribute.CsCAttrReadMode_e;
import com.aprisma.spectrum.core.idl.CsCAttribute.CsCAttrValList;
import com.aprisma.spectrum.core.idl.CsCAttribute.CsCAttrValue;
import com.aprisma.spectrum.core.idl.CsCAttribute.CsCValue;
import com.aprisma.spectrum.core.idl.CsCError.CsCError_e;
import com.aprisma.spectrum.core.idl.CsCException.CsCSpectrumException;
import com.aprisma.spectrum.core.idl.CsCModelDomainPackage.CsCAttrValListOfModels;
import com.aprisma.spectrum.core.idl.CsCModelDomainPackage.CsCModelAttrValList;
import com.aprisma.spectrum.core.idl.CsCModelDomainPackage.CsCModelProperties;

public class Model {

	public static final int STATUS_UP = 0;
	public static final int STATUS_DOWN = 1;
	public static final int STATUS_MAJOR = 2;
	public static final int STATUS_MINOR = 3;
	public static final int STATUS_MAINTENANCE = 4;

	private int modelID;
	private CsCModelProperties modelProperties;

	protected Map<String, Model> children;

	public Model(int id) {
		this.modelID = id;
	}

	public Model(CsCModelProperties modelProperties) {
		this.modelProperties = modelProperties;
	}

	protected CsCValue readAttribute(int attrID) throws CsCSpectrumException, SSOrbConnectException {

		int[] modelIDList = new int[1];
		int[] extAttrIDList = new int[1];
		modelIDList[0] = modelID;
		extAttrIDList[0] = attrID;

		CsCAttrReadMode_e[] readMode = { CsCAttrReadMode_e.CSC_MOST_CURRENT };
		CsCAttrValListOfModels avlom = DomainHelper.getModelDomain().readAttrValListOfModelsByIDs(modelIDList, extAttrIDList, readMode);
		if (avlom.error == CsCError_e.SUCCESS) {
			for (int i = 0; i < avlom.list.length; i++) {
				CsCModelAttrValList mavl = avlom.list[i];
				CsCAttrValList avl = mavl.attrValList;
				if (avl.error == CsCError_e.SUCCESS) {
					CsCAttrValue aval = avl.list[0];
					CsCError_e err = aval.error;
					if (err == CsCError_e.SUCCESS) {
						// The external attribute read for this example
						// will require a text string attribute due to
						// the print statement below calls textString()
						CsCValue val = aval.value;
						return val;
					}
				}
			}
		}
		return new CsCValue();
	}

	public int getAttributeAsInt(int attrID) {
		try {
			CsCValue attribute = readAttribute(attrID);
			return attribute.intValue();
		} catch (CsCSpectrumException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SSOrbConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public int getAttributeAsTimeTicks(int attrID) {
		try {
			CsCValue attribute = readAttribute(attrID);
			return attribute.timeTicks();
		} catch (CsCSpectrumException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SSOrbConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public String getName() {
		// return readAttribute(Attribute.ModelName);
		try {
			return getModelProperties().name;
		} catch (Throwable e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public String getMType() throws CsCSpectrumException, SSOrbConnectException {
		// CsCModelProperties mprops =
		// DomainHelper.getModelDomain().getModelProperties(modelID);
		return getModelProperties().mTypeName;
	}

	protected CsCModelProperties getModelProperties() throws CsCSpectrumException, SSOrbConnectException {
		if (modelProperties == null) {
			modelProperties = DomainHelper.getModelDomain().getModelProperties(modelID);
		}
		return modelProperties;
	}

	public int getID() {
		return modelID;
	}

	@Override
	public String toString() {
		return getName();
	}

	public Map<String, Model> getChildren() {
		if (children == null) {
			children = getChildren(true);
		}
		return children;
	}

	public Map<String, Model> getChildren(boolean fetch) {
		if (!fetch) {
			return getChildren();
		}
		return new HashMap<String, Model>();
	}

	public int getChildrenCount() {
		return getChildren().size();
	}

	public int getStatus() {
		try {
			int attr = readAttribute(Attribute.Condition).intValue();
			// System.out.println("Got condition " + attr + " for service " +
			// getName());
			return attr;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public String getUserStatusColor() {
		switch (getStatus()) {
		case STATUS_UP:
		case STATUS_MINOR:
			return "green";
		case STATUS_DOWN:
			return "red";
		case STATUS_MAJOR:
			return "yellow";
		case STATUS_MAINTENANCE:
			return "brown";
		default:
			return "gray";
		}
	}

	public String getUserStatusString() {
		switch (getStatus()) {
		case STATUS_UP:
		case STATUS_MINOR:
			return "Up";
		case STATUS_DOWN:
			return "Down";
		case STATUS_MAJOR:
			return "Degraded";
		case STATUS_MAINTENANCE:
			return "Maintenance";
		default:
			return "Unknown";
		}
	}

	public String getStatusColor() {
		switch (getStatus()) {
		case STATUS_UP:
			return "green";
		case STATUS_DOWN:
			return "red";
		case STATUS_MAJOR:
			return "orange";
		case STATUS_MINOR:
			return "yellow";
		case STATUS_MAINTENANCE:
			return "brown";
		default:
			return "gray";
		}
	}

	public String getStatusString() {
		switch (getStatus()) {
		case STATUS_UP:
			return "Up";
		case STATUS_DOWN:
			return "Down";
		case STATUS_MAJOR:
			return "Major Event";
		case STATUS_MINOR:
			return "Minor Event";
		case STATUS_MAINTENANCE:
			return "Maintenance";
		default:
			return "Unknown";
		}
	}

	public int getModelClass() {
		try {
			CsCValue attr = readAttribute(Attribute.ModelClass);
			return attr.intValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public void attrChangeCallback() throws SSOrbConnectException, CsCSpectrumException {
		int[] modelIDs = { modelID };
		// Model name 0x1006e attribute will be requested back when
		// the callback triggers
		int[] attrs = { Attribute.ModelName };
		// int[] attrs = { Attribute.CpuUtilisation };

		// CsCAttrReadMode_e[] readModes = { CsCAttrReadMode_e.CSC_MOST_CURRENT
		// };

		Servant callback = new AttrValCallback();
		CsCAttrValWatchCB cb = CsCAttrValWatchCBHelper.narrow(DomainHelper.getHelper().servant_to_reference(callback));
		// CsCAttrValListOfModels avlm =
		// DomainHelper.getModelDomain().startWatchAttrValsOfModelsByIDs(modelIDs,
		// attrs, readModes, cb);
		System.out.println("Watching started.. Press Enter to exit");
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DomainHelper.getModelDomain().stopWatchAttrValsOfModelsByIDs(cb, attrs, modelIDs);
	}
}
