package tests;

import java.util.Map;

import junit.framework.TestCase;
import ch.unibas.spectrum.ssorb.access.ServiceAccess;
import ch.unibas.spectrum.ssorb.exception.SSOrbConnectException;
import ch.unibas.spectrum.ssorb.model.Model;
import ch.unibas.spectrum.ssorb.model.ServiceModel;

import com.aprisma.spectrum.core.idl.CsCException.CsCSpectrumException;

public class ServiceTest extends TestCase {

	private static final String E_MAIL = "e-mail";
	private ServiceModel root;

	protected void setUp() throws Exception {
		super.setUp();

		root = (ServiceModel) ServiceAccess.getRoot().getChildren().get("URZ Services");
	}

	public void testGetRoot() throws CsCSpectrumException, SSOrbConnectException {
		assertEquals("Services", root.getName());
	}

	public void testGetChildren() throws CsCSpectrumException, SSOrbConnectException {
		Map<String, Model> children = root.getChildren();
		assertEquals(14, children.size());
		assertEquals(E_MAIL, children.get(E_MAIL).getName());
	}

	public void testGetChildrenChildren() throws CsCSpectrumException, SSOrbConnectException {
		ServiceModel email = (ServiceModel) root.getChildren().get(E_MAIL);
		Map<String, Model> children = email.getChildren();
		assertEquals(4, children.size());
		assertEquals("SMTP", children.get("SMTP").getName());
	}

	public void testCodition() throws Exception {
		ServiceModel email = (ServiceModel) root.getChildren().get(E_MAIL);
		assertNotSame(-1, email.getStatus());
		assertNotSame("gray", email.getStatusColor());
	}

	public void testAvailibity() throws Exception {
		ServiceModel email = (ServiceModel) root.getChildren().get(E_MAIL);
		Model m = email.getChildren(true).values().iterator().next();
		if (m instanceof ServiceModel) {
			email = (ServiceModel) m;

		}
		assertNotSame(-1, email.getAvailability());
	}

}
