package ch.unibas.spectrum.ssorb.callbacks;

import com.aprisma.spectrum.core.idl.CsCAttrValWatchCBPOA;
import com.aprisma.spectrum.core.idl.CsCAttribute.CsCAttrValList;
import com.aprisma.spectrum.core.idl.CsCAttribute.CsCAttrValue;
import com.aprisma.spectrum.core.idl.CsCAttribute.CsCValue;
import com.aprisma.spectrum.core.idl.CsCModelDomainPackage.CsCAttrValListOfModels;
import com.aprisma.spectrum.core.idl.CsCModelDomainPackage.CsCModelAttrValList;

public class AttrValCallback extends CsCAttrValWatchCBPOA {

	@Override
	public void attrValsChanged(CsCAttrValListOfModels avlom) {
		System.out.println("Overall:" + avlom.error.toString());
		for (int i = 0; i < avlom.list.length; i++) {
			CsCModelAttrValList mavl = avlom.list[i];
			System.out.println("Model ID:" + mavl.modelID);
			CsCAttrValList avl = mavl.attrValList;
			for (int j = 0; j < avl.list.length; j++) {
				CsCAttrValue av = avl.list[j];
				CsCValue v = av.value;
				// In this case we know that the attribute
				// requested was 0x1006e Model_Name which is
				// of type text string so that is the
				// method called.
				System.out.println("Attr ID:" + av.attributeID + " --> Value:" + v.textString());
			}
		}
	}

}
