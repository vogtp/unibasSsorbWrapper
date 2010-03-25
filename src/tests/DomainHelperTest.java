package tests;

import junit.framework.TestCase;
import ch.unibas.spectrum.ssorb.helper.DomainHelper;

import com.aprisma.spectrum.core.idl.CsCModelDomain;

public class DomainHelperTest extends TestCase {

	private CsCModelDomain modelDomain;

	protected void setUp() throws Exception {
		super.setUp();
		modelDomain = DomainHelper.getModelDomain();
	}

	public void testGetModelDomain() throws Exception {
		int domainID = modelDomain.getModelDomainID();
		assertEquals("600000", Integer.toHexString(domainID));
	}

	public void testSetUsername() throws Exception {
		DomainHelper.setUsername("USERNAME");
		modelDomain = DomainHelper.getModelDomain();
		int domainID = modelDomain.getModelDomainID();
		assertEquals("600000", Integer.toHexString(domainID));
		DomainHelper.setUsername("USER2");
		modelDomain = DomainHelper.getModelDomain();
		domainID = modelDomain.getModelDomainID();
		assertEquals("600000", Integer.toHexString(domainID));

	}
}
