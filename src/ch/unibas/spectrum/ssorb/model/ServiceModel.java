package ch.unibas.spectrum.ssorb.model;

import java.util.HashMap;
import java.util.Map;

import ch.unibas.spectrum.ssorb.access.ServiceAccess;
import ch.unibas.spectrum.ssorb.constants.Attribute;
import ch.unibas.spectrum.ssorb.exception.SSOrbConnectException;

import com.aprisma.spectrum.core.idl.CsCException.CsCSpectrumException;
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
			return ServiceAccess.getServiceChildren(this);
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

	public float getAvailability() throws CsCSpectrumException, SSOrbConnectException {
		float avail = -1;
		Map<String, Model> garantees = ServiceAccess.getGarantee(this);
		for (Model garantee : garantees.values()) {
			System.out.println("g: " + garantee.getName() + "(" + garantee.getMType() + ")");
			if ("SM_Guarantee".equals(garantee.getMType())) {
				int activeTime = garantee.getAttributeAsInt(Attribute.ActiveTime);
				int violationTime = garantee.getAttributeAsTimeTicks(Attribute.ViolationTime);
				avail = ((activeTime * 1f - violationTime * 1f) / activeTime * 1f) * 100f;
				// System.out.println("at: " + activeTime);
				// System.out.println("vt: " + violationTime);
				// System.out.println(" a: " + avail);
			}
		}
		return avail;
	}
}
