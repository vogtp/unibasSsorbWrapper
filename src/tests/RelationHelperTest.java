package tests;

import java.util.Map;

import junit.framework.TestCase;
import ch.unibas.spectrum.ssorb.access.ModelAccess;
import ch.unibas.spectrum.ssorb.constants.Relations;
import ch.unibas.spectrum.ssorb.exception.SSOrbConnectException;
import ch.unibas.spectrum.ssorb.helper.RelationHelper;
import ch.unibas.spectrum.ssorb.model.Model;

import com.aprisma.spectrum.core.idl.CsCException.CsCSpectrumException;

public class RelationHelperTest extends TestCase {

	private Model model;

	protected void setUp() throws Exception {
		super.setUp();
		String modelname = "Services";
		model = ModelAccess.getModelByName(modelname).get(0);
	}

	public void testGetAllAssciations() throws CsCSpectrumException, SSOrbConnectException {
		Map<String, Model> allAssciations = RelationHelper.getAllAssciations(model);
		assertEquals(14, allAssciations.size());
		assertEquals("e-mail", allAssciations.get("e-mail").getName());
		assertEquals("SM_Service", allAssciations.get("e-mail").getMType());
	}

	public void testGetAssciations() throws CsCSpectrumException, SSOrbConnectException {
		Map<String, Model> assciations = RelationHelper.getAssciations(model, Relations.SlmContains);
		assertEquals(14, assciations.size());
		assertEquals("e-mail", assciations.get("e-mail").getName());
		assertEquals("SM_Service", assciations.get("e-mail").getMType());
		assciations = RelationHelper.getAssciations(model, Relations.Collects);
		assertEquals(0, assciations.size());
	}
}
