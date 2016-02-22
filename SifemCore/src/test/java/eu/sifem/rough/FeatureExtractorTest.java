package eu.sifem.rough;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import eu.sifem.service.IFeatureExtractorService;

@Test(groups= { "default" })
@ContextConfiguration(locations={"classpath:SifemCore-applicationContext.xml"})
public class FeatureExtractorTest extends AbstractTestNGSpringContextTests{
	
	@Autowired
	private IFeatureExtractorService featureExtractor;
	


	@Test(groups= { "default" })
	public void testServiceIsNotNull() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Assert.assertNotNull(featureExtractor);
	}
	
	@Test(groups= { "default" })
	public void testImageGeneration() throws Exception {
		List<String> datax = new ArrayList<String>();
		datax.add("0.0");
		datax.add("144.321");
		datax.add("159.407");
		datax.add("178.413");
		datax.add("202.557");
		List<String> datay = new ArrayList<String>();
		datay.add("0.0");
		datay.add("0.84");
		datay.add("0.8925");
		datay.add("0.945");
		datay.add("0.9975");
//		int maximaIndex = featureExtractorService.findGlobalMaximaIndex(datax, datay);
		featureExtractor.plot2dGFeatService(datax, "velocity", datay, "distance", "name","C:/SifemWindowsResourceFiles/workspace/output/");
	}

	

}
