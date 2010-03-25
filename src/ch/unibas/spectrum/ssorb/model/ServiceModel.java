package ch.unibas.spectrum.ssorb.model;

import java.util.HashMap;
import java.util.Map;

import ch.unibas.spectrum.ssorb.access.ServiceAccess;
import ch.unibas.spectrum.ssorb.constants.Attribute;

import com.aprisma.spectrum.core.idl.CsCModelDomainPackage.CsCModelProperties;

public class ServiceModel extends Model {

	Map<String, Model> children;

	public ServiceModel(CsCModelProperties modelProperties) {
		super(modelProperties);
	}

	public ServiceModel(int id) {
		super(id);
	}

	public Map<String, Model> getChildren(boolean fetch) {
		if (!fetch) {
			return getChildren();
		}
		try {
			return ServiceAccess.getChildren(this);
		} catch (Throwable e) {
			e.printStackTrace();
			return new HashMap<String, Model>();
		}
	}

	public int getStatus() {
		try {
			int attr = readAttribute(Attribute.RMCondition).intValue();
			// System.out.println("Got condition " + attr + " for service " +
			// getName());
			return attr;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	//
	// public String getServiceStatusColor() {
	// // FIXME correct stati numbers
	// switch (getStatus()) {
	// case 0:
	// return "green";
	// case 1:
	// return "yellow";
	// case 2:
	// return "orange";
	// case 3:
	// return "red";
	// default:
	// return "gray";
	// }
	// }
}
