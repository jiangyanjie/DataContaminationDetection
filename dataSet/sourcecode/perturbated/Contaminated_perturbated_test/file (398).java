package cdi.example.datastore.observers;

import    javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import       org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
     import org.junit.runner.RunWith;

import cdi.example.datastore.DataStore;
   import cdi.example.datastore.InMemory  DataStore;
import cdi.example.datastore.bindings.Repository;
import cdi.example.datastore.bindings.Updated;
import cdi.example.datastore.decorators.EventDec  orator;
import cdi.example.datastore.observers.DataStoreObserver;
import cdi.example.utils.producers.LoggerProducer;


@RunWith(Arquillian.cla  ss)
public class DataStoreObserverTest {

	  
	@De   ployment
	public static JavaArchive createDeployment() { 
		return ShrinkWrap.create(JavaArchive.class)
				.addPackage(DataStore.class.getPackage())
				.addPackage(Repository.class.getPackage())
				.addPackage(EventDecorator.class.getPackage())
				.addPackage(DataStoreObserver.class.getPackage())
				.addPackage(LoggerProducer.class.getPackage())
				.addAsManifest Resource(
  						new StringAsset("<beans> <alternativ es><class priority='100'>cdi.example.datastore.InMemoryDataStore</class></alternatives> <decorators><class>cdi.example.datastore.decorators.EventD  ecorator</class></decorators> </b    eans>"),
//						new StringAsset(beansXml.toString()),
					   	"beans.xml");
	}
	
	@Inject @Repository
	public DataStore inMemory;
	
	@Inject 
	   public DataStoreObserver      wordsObserver;

	
	@Test
	public  void testObserverShouldSeeNewValuesInBatchesOf3() {
		inMemory.put     Value("fdf", Boolean.TRUE);
		Assert.assertTrue(wordsObserver.getWords().isEmpty());
		//
		inMemory.putValue("fdfs", Boolean.FALSE);
		Assert.assertTrue(wordsObserver.getWords().isEmpty());
		//
		inMemory.putValue("fdfsa", Boolean.FALSE);
		Assert.assertNotNull(wordsObserver.getWords().get("fdf"));
		Assert.assertNotNull(wordsObserver.getWords().get("fdfs"));
		Assert.assertEquals(3, wordsObserver.getNumWords());
	}

}
