package br.com.wrigg.dnd.hitanddamage.damage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.wrigg.dnd.hitanddamage.DiceType;
import br.com.wrigg.dnd.hitanddamage.Type;
import br.com.wrigg.dnd.hitanddamage.Item.Item;
import br.com.wrigg.dnd.hitanddamage._class.ClassFeature;
import br.com.wrigg.dnd.hitanddamage._class.TurnLevel;
import br.com.wrigg.dnd.hitanddamage.arsenal.Weapon;
import br.com.wrigg.dnd.hitanddamage.character.Attribute;
import br.com.wrigg.dnd.hitanddamage.character.Character;
import br.com.wrigg.dnd.hitanddamage.damage.BasicDamageRollCalculator;
import br.com.wrigg.dnd.hitanddamage.damage.DamageBonus;
import br.com.wrigg.dnd.hitanddamage.feat.Feat;
import br.com.wrigg.dnd.hitanddamage.spell.CasterLevel;
import br.com.wrigg.dnd.hitanddamage.spell.Spell;

public class DamageRollCalculatorTest {

	@Test
	public void calculateWeaponDamageRollTest() {
		Character character = new Character();
		Weapon weapon = new Weapon("Kukri", new DiceType(4));
		character.equip(weapon);
		
		BasicDamageRollCalculator damageRollCalculator = new BasicDamageRollCalculator();
		String damageRoll = damageRollCalculator.calculateDamageRoll(character);
		
		assertEquals("1D4", damageRoll);
		
		weapon = new Weapon("Long Sword", new DiceType(8));
		character.equip(weapon);
		
		damageRoll = damageRollCalculator.calculateDamageRoll(character);

		assertEquals("1D8", damageRoll);
	}
	
	@Test
	public void showldReturnWeaponDamageWhenStrSetButWithoutBonusTest() {
		Character character = new Character();
		Weapon weapon = new Weapon("Kukri", new DiceType(4));
		character.equip(weapon);
		
		Attribute str = new Attribute();
		character.setStrength(str);
		
		BasicDamageRollCalculator damageRollCalculator = new BasicDamageRollCalculator();
		String damageRoll = damageRollCalculator.calculateDamageRoll(character);
		
		assertEquals("1D4", damageRoll);
	}
	
	@Test
	public void calculateWeaponAndStrengthDamageRollTest() {
		Character character = new Character();
		Weapon weapon = new Weapon("Kukri", new DiceType(4));
		character.equip(weapon);

		Attribute str = new Attribute(18);
		character.setStrength(str);
		
		BasicDamageRollCalculator damageRollCalculator = new BasicDamageRollCalculator();
		String damageRoll = damageRollCalculator.calculateDamageRoll(character);
		
		assertEquals("1D4+4", damageRoll);
		
		weapon = new Weapon("Long Sword", new DiceType(8));
		character.equip(weapon);
		
		damageRoll = damageRollCalculator.calculateDamageRoll(character);

		assertEquals("1D8+4", damageRoll);
	}

	@Test
	public void calculateWeaponStrengthAndDivineMetamagicDamageRollTest() {
		Character character = new Character();
		Weapon weapon = new Weapon("Kukri", new DiceType(4));
		character.equip(weapon);

		Attribute str = new Attribute(18);
		character.setStrength(str);

		Feat feat = new Feat("divineMetamagic", "Divine Metamagic", Type.FEATURE_DEPENDENT);
		character.activateFeat(feat);
		
		Attribute cha = new Attribute(21);
		character.setCharisma(cha);

		BasicDamageRollCalculator damageRollCalculator = new BasicDamageRollCalculator();
		String damageRoll = damageRollCalculator.calculateDamageRoll(character);
		
		assertEquals("1D4+9", damageRoll);
	}
	
	@Test
	public void calculateWeaponStrengthAndPowerAttackDamageRollTest() {
		Character character = new Character();
		Weapon weapon = new Weapon("Kukri", new DiceType(4));
		character.equip(weapon);

		Attribute str = new Attribute(18);
		character.setStrength(str);

		Feat powerAttack = new Feat("powerAttack", "Power Attack", Type.VARIABLE_IMPUT);
		powerAttack.setDamageBonus(new DamageBonus(5));
		character.activateFeat(powerAttack);
		
		BasicDamageRollCalculator damageRollCalculator = new BasicDamageRollCalculator();
		String damageRoll = damageRollCalculator.calculateDamageRoll(character);
		
		assertEquals("1D4+9", damageRoll);
	}
	
	@Test
	public void calculateWeaponStrengthAndPowerAttackAndDivineMetamagicDamageRollTest() {
		Character character = new Character();
		Weapon weapon = new Weapon("Kukri", new DiceType(4));
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

		BasicDamageRollCalculator damageRollCalculator = new BasicDamageRollCalculator();
		String damageRoll = damageRollCalculator.calculateDamageRoll(character);
		
		assertEquals("1D4+14", damageRoll);
	}
	
	@Test
	public void calculateMagicalWeaponWithStrengthDamageRollTest() {
		Character character = new Character();
		Weapon weapon = new Weapon("Kukri", new DiceType(4));
		weapon.setBonus(new DamageBonus(1));
		character.equip(weapon);

		Attribute str = new Attribute(18);
		character.setStrength(str);

		BasicDamageRollCalculator damageRollCalculator = new BasicDamageRollCalculator();
		String damageRoll = damageRollCalculator.calculateDamageRoll(character);
		
		assertEquals("1D4+5", damageRoll);
	}
	
	@Test
	public void calculateSpellWithWeaponAndStrengthDamageRollTest() {
		Character character = new Character();
		Weapon weapon = new Weapon("Kukri", new DiceType(4));
		character.equip(weapon);
		
		Attribute str = new Attribute(18);
		character.setStrength(str);
		
		Spell divineFavor = new Spell("divineFavor", "Divine Favor", Type.FEATURE_DEPENDENT);
		character.castSpell(divineFavor);
		character.setCasterLevel(new CasterLevel(1));
		
		BasicDamageRollCalculator damageRollCalculator = new BasicDamageRollCalculator();
		String damageRoll = damageRollCalculator.calculateDamageRoll(character);
		
		assertEquals("1D4+5", damageRoll);
		
	}
	
	@Test
	public void changeCasterLevelNullAfterCastingDivineFavorShouldUpdateDamageBonusToPlusOneTest() {
		Character character = new Character();
		Weapon weapon = new Weapon("Kukri", new DiceType(4));
		character.equip(weapon);

		character.setCasterLevel(new CasterLevel(6));

		Spell divineFavor = new Spell("divineFavor", "Divine Favor", Type.FEATURE_DEPENDENT);
		character.castSpell(divineFavor);
		
		BasicDamageRollCalculator damageRollCalculator = new BasicDamageRollCalculator();
		String damageRoll = damageRollCalculator.calculateDamageRoll(character);
		
		assertEquals("1D4+2", damageRoll);
		
		character.setCasterLevel(null);
		
		damageRoll = damageRollCalculator.calculateDamageRoll(character);
		
		assertEquals("1D4+1", damageRoll);
		
	}
	
	@Test
	public void calculateSmiteWithWeaponAndStrengthDamageRollTest() {
		Character character = new Character();
		Weapon weapon = new Weapon("Kukri", new DiceType(4));
		character.equip(weapon);
		
		Attribute str = new Attribute(18);
		character.setStrength(str);
		
		ClassFeature smite = new ClassFeature("smite", "Smite", Type.FEATURE_DEPENDENT);
		character.activateClassFeature(smite);
		
		character.setTurnLevel(new TurnLevel(5));
		
		BasicDamageRollCalculator damageRollCalculator = new BasicDamageRollCalculator();
		String damageRoll = damageRollCalculator.calculateDamageRoll(character);
		
		assertEquals("1D4+9", damageRoll);
		
	}
	
	@Test
	public void calculateEnlargePersonWithWeaponAndStrengthDamageRollTest() {
		Character character = new Character();
		Weapon weapon = new Weapon("Kukri", new DiceType(4));
		character.equip(weapon);
		
		Attribute str = new Attribute(18);
		character.setStrength(str);
		
		Item enlargePerson = new Item("smite", "Smite");
		character.activateItem(enlargePerson);
		
		BasicDamageRollCalculator damageRollCalculator = new BasicDamageRollCalculator();
		String damageRoll = damageRollCalculator.calculateDamageRoll(character);
		
		assertEquals("1D6+4", damageRoll);
		
	}
	
	@Test
	public void calculateNoWeaponAndStrengthSholdShowNoDamageRollTest() {
		Character character = new Character();
		
		Attribute str = new Attribute(18);
		character.setStrength(str);
		
		BasicDamageRollCalculator damageRollCalculator = new BasicDamageRollCalculator();
		String damageRoll = damageRollCalculator.calculateDamageRoll(character);
		
		assertEquals("", damageRoll);
		
	}
}
