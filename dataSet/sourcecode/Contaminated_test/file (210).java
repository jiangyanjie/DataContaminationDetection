package br.com.wrigg.dnd.hitanddamage.damage;

import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.wrigg.dnd.hitanddamage.character.Attribute;
import br.com.wrigg.dnd.hitanddamage.damage.DamageBonusByAttributeFeatureFactoryMethod;
import br.com.wrigg.dnd.hitanddamage.damage.DamageBonusByCasterLevelFactoryMethod;
import br.com.wrigg.dnd.hitanddamage.damage.DamageBonusByFeatureFactoryMethod;
import br.com.wrigg.dnd.hitanddamage.spell.CasterLevel;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ DamageBonusByCasterLevelFactoryMethod.class, DamageBonusByAttributeFeatureFactoryMethod.class })
public class DamageBonusByFeatureFactoryMethodTest {

	@Test
	public void damageBonusByFeatureCreationTest() throws Exception {
		DamageBonusByCasterLevelFactoryMethod damageBonusByCasterLevelFactoryMethodMock = spy(new DamageBonusByCasterLevelFactoryMethod());
	    
		whenNew(DamageBonusByCasterLevelFactoryMethod.class).withNoArguments().thenReturn(damageBonusByCasterLevelFactoryMethodMock);
		
		CasterLevel casterLevelFeature = new CasterLevel(6);
		
		DamageBonusByFeatureFactoryMethod damageBonusByFeature = new DamageBonusByFeatureFactoryMethod();
		damageBonusByFeature.execute(casterLevelFeature);

		//verify(damageBonusByCasterLevelFactoryMethodMock).execute(casterLevelFeature);
		//verifyPrivate(damageBonusByCasterLevelFactoryMethodMock, times(1)).invoke("execute", casterLevelFeature);
//
//		DamageBonusByAttributeFeatureFactoryMethod damageBonusByAttributeFeatureFactoryMethodMock = spy(new DamageBonusByAttributeFeatureFactoryMethod());
//	    
//		whenNew(DamageBonusByAttributeFeatureFactoryMethod.class).withNoArguments().thenReturn(damageBonusByAttributeFeatureFactoryMethodMock);
//
//		Attribute attributeFeature = new Attribute(18);
//		
//		damageBonusByFeature = new DamageBonusByFeatureFactoryMethod();
//		damageBonusByFeature.execute(attributeFeature);
//
//		verifyPrivate(damageBonusByAttributeFeatureFactoryMethodMock, times(1)).invoke("execute", attributeFeature);
	}
}
