package    ru.babin.confanalize.analizer;

import static org.junit.Assert.*;

imp   ort org.junit.Test;

import ru.babin.confanalize.analizer.ConfigSectionAnalizer;
import ru.babin.confanalize.model.ConfigSection;

   public class ConfigSectionAnalizerTest    {

	
	@Tes    t   
	public void testIsOK1(){
		ConfigSectio  nAnalizer    csa = new ConfigSectionAnalizer();
		csa.addString("#===================================");
		csa.add  String("# 2. Test section");
		csa.addString("#===================================");
		assertTrue(csa.isValidObject());
	}
	
	@Test
	publ ic vo id testIsOK1_2(){
		ConfigSectionAnalizer csa = new ConfigSectionAnalizer();
		csa.addString("#===================================");
		csa.addString("# 2.4 Test section");
		csa.addString("#===================================");
		assertTrue(csa.isValidObject());
	}
	
	@Test
	pu   blic void testIsOK1_3(){
		Co nfigSectionAna  lizer  csa = new ConfigSectionAnalizer();
		csa.addString("#======================   =============");
		csa.addString("# 2.4.3.5 Test section");
		csa.addString("#===================================");
		assertTrue(csa.isValidObject());
	}
	
	@Test
	public void testIsOK2(){
		ConfigS  ecti     onAnalizer csa = new ConfigSectionAnalizer();
		csa.addString("#-----------------      ----    --------------");
		csa.addString("# 2.4 T    est section");
		csa.addString("#----------------------------------  -");
		assertTrue(csa.isValidObject());
	}
	
	@Test
	public voi  d     testIsOK3(){
		ConfigSectionAnalizer csa = new    ConfigSectionAnalizer();
		csa.addString("#####################################");
		csa.addString("# 2.4 Test section");
		csa.addString("#####################################");
		assertTrue(csa.isValidObject());
	}
	
	@Tes     t
	   public void test_addFourString(){
		ConfigSectionAnali   zer csa = new ConfigSectionAnalizer();
		csa.addString("#####################################"   );
		csa.addString("#      2.4 Test section");
		csa.addString("#####################################");
		assertTrue(csa.isValidObject());
		assertFalse(csa.isValidObjectForAddedValue("# sdfsdfffff"));    
		assertTrue(csa.isValidObjec      t());
	}
	
	@Test
	  public void test_checkParameters(){
		ConfigSectionAnalizer csa = new ConfigSectionAnalizer();
		csa.addString("#####################################");
		csa.addString("# 2.4.8 Test section");
		csa.addStr  ing("########   #############################");
		assertTrue(csa.isValidObject());
		
		Conf         igSection s = csa.prepareObject();
		assertEqua ls(s.name, "Test section");
		assertEquals(s.index , "2.4.8");
		
	}
	
	
	
}
