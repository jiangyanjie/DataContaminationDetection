package br.com.wrigg.dnd.hitanddamage.damage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.wrigg.dnd.hitanddamage.DiceType;
import br.com.wrigg.dnd.hitanddamage.Type;
import br.com.wrigg.dnd.hitanddamage._class.ClassFeature;
import br.com.wrigg.dnd.hitanddamage._class.TurnLevel;
import br.com.wrigg.dnd.hitanddamage.arsenal.Weapon;
import br.com.wrigg.dnd.hitanddamage.character.Attribute;
import br.com.wrigg.dnd.hitanddamage.character.Character;
import br.com.wrigg.dnd.hitanddamage.damage.CriticalDamageRollCalculator;
import br.com.wrigg.dnd.hitanddamage.damage.DamageBonus;
import br.com.wrigg.dnd.hitanddamage.feat.Feat;
import br.com.wrigg.dnd.hitanddamage.spell.CasterLevel;
import br.com.wrigg.dnd.hitanddamage.spell.Spell;

public class CriticalDamageRollCalculatorTest {

	@Test
	public void calculateCriticalWeaponDamageRollTest() {
		Character character = new Character();
		Weapon weapon = new Weapon("Kukri", new DiceType(4), 1, 2);
		character.equip(weapon);
		
		CriticalDamageRollCalculator criticaldamageRollCalculator = new CriticalDamageRollCalculator();
		String damageRoll = criticaldamageRollCalculator.calculateDamageRoll(character);
		
		assertEquals("2D4", damageRoll);
		
		weapon = new Weapon("Long Sword", new DiceType(8), 1, 2);
		character.equip(weapon);
		
		damageRoll = criticaldamageRollCalculator.calculateDamageRoll(character);

		assertEquals("2D8", damageRoll);
	}
	
	@Test
	public void calculateCriticalWeaponAndStrengthDamageRollTest() {
		Character character = new Character();
		Weapon weapon = new Weapon("Kukri", new DiceType(4), 1, 2);
		character.equip(weapon);

		Attribute str = new Attribute(18);
		character.setStrength(str);
		
		CriticalDamageRollCalculator criticalDamageRollCalculator = new CriticalDamageRollCalculator();
		String damageRoll = criticalDamageRollCalculator.calculateDamageRoll(character);
		
		assertEquals("2D4+8", damageRoll);
		
		weapon = new Weapon("Long Sword", new DiceType(8), 1, 2);
		character.equip(weapon);
		
		damageRoll = criticalDamageRollCalculator.calculateDamageRoll(character);

		assertEquals("2D8+8", damageRoll);
	}

	@Test
	public void calculateWeaponStrengthAndDivineMetamagicDamageRollTest() {
		Character character = new Character();
		Weapon weapon = new Weapon("Kukri", new DiceType(4), 1, 2);
		character.equip(weapon);
		
		Attribute str = new Attribute(18);
		character.setStrength(str);

		Feat feat = new Feat("divineMetamagic", "Divine Metamagic", Type.FEATURE_DEPENDENT);
		character.activateFeat(feat);
		
		Attribute cha = new Attribute(21);
		character.setCharisma(cha);

		CriticalDamageRollCalculator criticalDamageRollCalculator = new CriticalDamageRollCalculator();
		String damageRoll = criticalDamageRollCalculator.calculateDamageRoll(character);
		
		assertEquals("2D4+18", damageRoll);
	}
	
	@Test
	public void calculateWeaponStrengthAndPowerAttackDamageRollTest() {
		Character character = new Character();
		Weapon weapon = new Weapon("Kukri", new DiceType(4), 1, 2);
		character.equip(weapon);

		Attribute str = new Attribute(18);
		character.setStrength(str);

		Feat powerAttack = new Feat("powerAttack", "Power Attack", Type.VARIABLE_IMPUT);
		powerAttack.setDamageBonus(new DamageBonus(5));
		character.activateFeat(powerAttack);
		
		CriticalDamageRollCalculator criticalDamageRollCalculator = new CriticalDamageRollCalculator();
		String damageRoll = criticalDamageRollCalculator.calculateDamageRoll(character);
		
		assertEquals("2D4+18", damageRoll);
	}
	
	@Test
	public void calculateWeaponStrengthAndPowerAttackAndDivineMetamagicDamageRollTest() {
		Character character = new Character();
		Weapon weapon = new Weapon("Kukri", new DiceType(4), 1, 2);
		character.equip(weapon);

		Attribute str = new Attribute(18);
		character.setStrength(str);

		Feat feat = new Feat("powerAttack", "Power Attack", Type.VARIABLE_IMPUT);
		feat.setDamageBonus(new DamageBonus(5));
		character.activateFeat(feat);
		
		Feat divineMetamagic = new Feat("divineMetamagic", "Divine Metamagic", Type.FEATURE_DEPENDENT);
		character.activateFeat(divineMetamagic);
		
		Attribute cha = new Attribute(21);
		character.setCharisma(cha);

		CriticalDamageRollCalculator criticalDamageRollCalculator = new CriticalDamageRollCalculator();
		String damageRoll = criticalDamageRollCalculator.calculateDamageRoll(character);
		
		assertEquals("2D4+28", damageRoll);
	}
	
	@Test
	public void calculateMagicalWeaponWithStrengthDamageRollTest() {
		Character character = new Character();
		Weapon weapon = new Weapon("Kukri", new DiceType(4), 1, 2);
		weapon.setBonus(new DamageBonus(1));
		character.equip(weapon);

		Attribute str = new Attribute(18);
		character.setStrength(str);

		CriticalDamageRollCalculator criticalDamageRollCalculator = new CriticalDamageRollCalculator();
		String damageRoll = criticalDamageRollCalculator.calculateDamageRoll(character);
		
		assertEquals("2D4+10", damageRoll);
	}
	
	@Test
	public void calculateSpellWithWeaponAndStrengthDamageRollTest() {
		Character character = new Character();
		Weapon weapon = new Weapon("Kukri", new DiceType(4), 1, 2);
		character.equip(weapon);
		
		Attribute str = new Attribute(18);
		character.setStrength(str);
		
		Spell divineFavor = new Spell("divineFavor", "Divine Favor", Type.FEATURE_DEPENDENT);
		character.castSpell(divineFavor);
		character.setCasterLevel(new CasterLevel(1));
		
		CriticalDamageRollCalculator criticalDamageRollCalculator = new CriticalDamageRollCalculator();
		String damageRoll = criticalDamageRollCalculator.calculateDamageRoll(character);
		
		assertEquals("2D4+10", damageRoll);
		
	}
	
	@Test
	public void calculateSmiteWithWeaponAndStrengthDamageRollTest() {
		Character character = new Character();
		Weapon weapon = new Weapon("Kukri", new DiceType(4),1 , 2);
		character.equip(weapon);
		
		Attribute str = new Attribute(18);
		character.setStrength(str);
		
		ClassFeature smite = new ClassFeature("smite", "Smite", Type.FEATURE_DEPENDENT);
		character.activateClassFeature(smite);
		
		character.setTurnLevel(new TurnLevel(5));
		
		CriticalDamageRollCalculator criticalDamageRollCalculator = new CriticalDamageRollCalculator();
		String damageRoll = criticalDamageRollCalculator.calculateDamageRoll(character);
		
		assertEquals("2D4+18", damageRoll);
		
	}
	
	@Test
	public void calculateNoWeaponAndStrengthShouldShowNoDamageRollTest() {
		Character character = new Character();
		
		Attribute str = new Attribute(18);
		character.setStrength(str);
		
		CriticalDamageRollCalculator criticalDamageRollCalculator = new CriticalDamageRollCalculator();
		String damageRoll = criticalDamageRollCalculator.calculateDamageRoll(character);
		
		assertEquals("", damageRoll);
	}

}
