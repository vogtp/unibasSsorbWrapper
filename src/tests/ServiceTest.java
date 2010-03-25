package tests;

import java.util.Map;

import junit.framework.TestCase;
import ch.unibas.spectrum.ssorb.access.ServiceAccess;
import ch.unibas.spectrum.ssorb.exception.SSOrbConnectException;
import ch.unibas.spectrum.ssorb.model.Model;
import ch.unibas.spectrum.ssorb.model.ServiceModel;

import com.aprisma.spectrum.core.idl.CsCException.CsCSpectrumException;

public class ServiceTest extends TestCase {

	private ServiceModel root;

	protected void setUp() throws Exception {
		super.setUp();
		root = ServiceAccess.getRoot();
	}

	public void testGetRoot() throws CsCSpectrumException, SSOrbConnectException {
		assertEquals("Services", root.getName());
	}

	public void testGetChildren() throws CsCSpectrumException, SSOrbConnectException {
		Map<String, Model> children = root.getChildren();
		assertEquals(14, children.size());
		assertEquals("e-mail", children.get("e-mail").getName());
	}

	public void testGetChildrenChildren() throws CsCSpectrumException, SSOrbConnectException {
		ServiceModel email = (ServiceModel) root.getChildren().get("e-mail");
		Map<String, Model> children = email.getChildren();
		assertEquals(4, children.size());
		assertEquals("SMTP", children.get("SMTP").getName());
	}

	public void testCodition() throws Exception {
		ServiceModel email = (ServiceModel) root.getChildren().get("e-mail");
		assertNotSame(-1, email.getStatus());
		assertNotSame("gray", email.getStatusColor());
	}

}
