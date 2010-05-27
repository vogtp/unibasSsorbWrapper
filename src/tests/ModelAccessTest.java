package tests;

import java.util.List;

import junit.framework.TestCase;
import ch.unibas.spectrum.ssorb.access.ModelAccess;
import ch.unibas.spectrum.ssorb.constants.Attribute;
import ch.unibas.spectrum.ssorb.exception.SSOrbConnectException;
import ch.unibas.spectrum.ssorb.model.Model;

import com.aprisma.spectrum.core.idl.CsCException.CsCSpectrumException;

public class ModelAccessTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	private List<Model> getModel(String modelname, int cnt) throws CsCSpectrumException, SSOrbConnectException {
		List<Model> models = ModelAccess.getModelByName(modelname);
		assertEquals(cnt, models.size());
		for (Model e : models) {
			assertEquals(modelname, e.getName());
		}

		return models;
	}

	public void testGetModelByName() throws CsCSpectrumException, SSOrbConnectException {
		getModel("imap", 1);
		getModel("EVA", 4);
	}

	public void testModelType() throws Exception {
		String modelname = "imap";
		List<Model> models = ModelAccess.getModelByName(modelname);
		assertEquals(1, models.size());
		assertEquals("Host_Device", models.get(0).getMType());
	}

	public void testModelIdByName() throws CsCSpectrumException, SSOrbConnectException {
		String modelname = "imap";
		int[] models = ModelAccess.getModelIDsByName(modelname);
		assertEquals(1, models.length);
		assertEquals(68051, models[0]);

	}

	public void testAttrCallback() throws Exception {
		String modelname = "File Applmon1";
		List<Model> models = ModelAccess.getModelByName(modelname);
		Model model = models.get(0);
		System.out.println("Got model " + model.getName() + " modelId " + Integer.toHexString(model.getID()));
		model.attrChangeCallback();
	}

	public void testAPCTemp() throws Exception {
		int temp = getModel("apc-bernoullistrasse.urz.unibas.ch", 1).get(0).getAttributeFromTable(Attribute.APCTemperature, 2);
		System.out.println(temp);
	}
}
