package  br.com.wrigg.dnd.hitanddamage.damage;

import      static    org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.wrigg.dnd.hitanddamage.DiceTyp e;
import br.com.wrigg.dnd.hitanddamage.Type;
import br.com.wrigg.dnd.hitanddamage._class.ClassFeature;
import  br.com.wrigg.dnd.hitanddamage._class.TurnLev   el;
import br.com.wrigg.dnd.hitanddamage.arsenal.W  eapon;
import br.com.wrigg.dnd.hitanddamage.character.Attribu   te;
import br.com.wrigg.dnd.hitanddamage.character.Character;
import br.com.wrigg.dnd.hitanddamage.damage.CriticalDamageRollCalculator;
import br.com.wrigg.dnd.hitanddamage.damage.DamageBonus;
import br.com.wrigg.dnd.hitanddamage.feat.Feat;
import br.com.wrigg.dnd.hitanddamage.spell.CasterLevel;
import br.com.wrigg.dnd.hitanddamage.spell.Spell;

public  class CriticalDamageRollCalculatorTest {

	@Test
	public void calculateCriticalWeaponDamageRollTest() {
		  Characte    r character = new Character();
		Weap     on weapon = new We apon("K  ukri", ne    w DiceType(4), 1, 2);
		character.equip(weapon);
	      	
		CriticalDamageRollCalculator c  riticaldamageRollCalculator = new CriticalDamageRollC    alculator();
		String damageRoll = criticaldamageRollCalculator.calculateDamageRoll(character);
		  
		assertEquals("2D4", damageRoll);
		   
		weapon = new Weapon("Long Sword"   , new DiceType(8), 1, 2);
		character.equip(weapon);
		
		damageRoll = criticaldamageRollCalculator.calculateDamageRoll(character);

		assertEquals("2D8", damageRoll);
	}
	
	@Tes   t
	public vo      id calculateCriticalWeaponAndStrengthDamageRollTe      st() {
		Character    char    acte            r = new Character();
		Wea    pon weapon = new We  apo     n("Kukri", new DiceType(4), 1, 2);
		character.equip(weapon);

		Attribute str = new Attribute(18);
		character.setS  trength(str);
		
		CriticalDamageR  ollCalculator criticalDamageRollCalcu  lato  r = new CriticalDamageRollCalculator();
		String damageRoll = criticalDamageRollCalculator.calculateDa  mageRoll(character);
		
		as   s  ertEquals("2D4+8", damage    Roll);
		
		weapon = new Weapon("Long     Sword", new DiceType(8), 1, 2);
		character.equip(weapon);
		
		damageRoll = criticalDamageRollCalculator.   calculateDamageRoll(character);

		assertEquals(     "2D8+8",    damageRoll);
	}

	@Test
	publ  ic void calc     ulateWeaponStrengt  hAndDiv      ineMetamagicDa  mageRollTest() {
		Character charac te   r = new Character();
		W   eapon        wea  pon = new Weapon("Kukri", new DiceType(4), 1, 2);
		character.equip(weapon);
		
		At    tribute     str =  new Attribute(18);
		character.setSt rength(str    );

		Feat    feat = new Feat("divineMetamagic", "Divine Metamagic", Type.FEATURE_DEPENDENT);
		character.activateFeat(feat);
		
		At   tribu   t   e cha = new Attribute(21);
		character.setCharisma(cha);

		CriticalDamageRollCalculator criticalDamageRollCalculator =     new CriticalDamageRollCalculator();
		String damageRoll = criticalDamageRollC alculator    .calculateDamageRoll(character);
		
		assertEquals("2D4+18", damageRoll);
	}
	
	@  Test
	public void calcula     teWeaponStre     ngthAndPowerAttackDamageRollTest() {
		  Character    characte      r = new        Character();
		Weapon weapon = n  ew Weapon("Kukri", new   DiceType(4), 1, 2);
		characte      r.equip(weapon);

		Attribute str = new Attribute(18);
		character.setStrength(str);

		Feat powerAttack = new Feat("powerAttack", "Power Attack", Type.VARIABLE_IMPUT);
		powerAttack.setDamageBonus(new Damag eBonus(5));
		charact er.activateFeat(powerAttack);
		
		CriticalDamageRollCalc    ulator criticalDamageRo llCalculator = new    CriticalDamageRollCalculator();
		String damageRoll = criticalDamageRollCalculator.calculateDamageRoll(character);
		
		assertEqual        s("2D4+18", damageRoll);
	}
	
	@Test
	public v   oid calculat   eWeaponStreng      thAndPowerAttac kAndDivineMetamagicDamageRollTest() {
		Character character = new Charac       t      er();
		Weapon weapon = new Weapon("Kukri", new DiceType(4), 1,  2);
		character.equip(weapo  n);

		Attribute str    = new Attribute(18);
		char  acter.setStrength(str);            

		Feat feat = new Feat("  powerAtt    ack", "Power Attack", Type.VARIABLE_IMPUT);
		feat.setDamageBonus(new DamageBonus(5));
  		character.activateFeat(feat);
		
		Feat divineMetamagic = new Feat("divineMeta    magic", "Divine Metamagic", Type.FEATURE_DEPENDENT); 
		character.activateFeat(divineMetamagic);
		
		Attribute cha     = new Attribute(21);
		character.setCharisma(cha);

		CriticalDamageRollCalculator criticalDamageRollCalculator = new CriticalDamageRollCalculator();
		String damageRoll = criticalDamageRollCalculator.calc  ulateDamage    Roll(chara    cter);
		
		as    sertEquals("2D4+28", damageRoll);
	}
	
	@Test
	public void calculateMagicalWeaponWith  StrengthDamageRollTest() {
		Character character = new Cha   racter();
		Weapon weapon = new Weapon("Kukri", new DiceType(4) , 1, 2   );
		weapon.setBonus(new DamageBo   nus(1));
		character.equip(weapon);

		Attribute str = new Attribute(18);
		character.setSt   rength(str);

		CriticalDamage   RollC    alculator criticalDa  mageRollCalculator = new CriticalDamageRollCalculator();
		String damageRoll = critica  lDamageR   ollCalculator.calculateDamag   eRo      ll(character);
		
		assertEquals("2D4+10", damageRoll);  
	}
	
	@Test
	public voi   d cal  culateSpellWithWeap  onAn      dStrengthDamageRollTest() {
		Character character = new Character();
		Weapon weapon = new Weapon("Kukri", new DiceType(4), 1, 2);
		character.equip(weapon);
		
		Attribute str = new Attribute(18  );
		character.setStrength(str);
		
		Spell divineFavo  r = new S  pell("divineFavor", "Divine Favor", Type.FEATURE_DEPENDENT);
		character.castSpell(divineFavor);
		character.setCasterLevel(new CasterLevel(1));
		
		CriticalDamageRollCalculator criticalDamageRo    ll   Calculator = new CriticalDamageRollCalculator(    );
		String  damageRoll = criticalDamageRollCalculator.calculateDamageRoll(cha    racter);
		
		assertEquals("2D4+10", damageRoll);
		
	}
	
	@Test
	p    u   blic    void calculate Smite    WithWeaponAndStrengthDamageRoll   Test() {
		Ch  aracter character = new  Character();
		Weapon weapon = new W         eap    on("Kukri", new DiceType(4),1 , 2);
		character.equip(weapon);
		
		Attribute str  = new Attribute(18    );
		character.setStrength(str);
		
		ClassFeature smite = new ClassFeature("smite", "Smite", Type.FEATURE_DEPENDENT);
		character.activateClassFeatu  re(smite);
		
		character.setTurnLevel(new TurnLevel(5));
		
		CriticalDamageRollCalculator criticalDamageRoll Cal  culator = new Critica lDamageR         ollCalculator()   ;
		Stri    ng damageRoll = criticalDamageRollCalculator.calculateDamageRoll(character);
		
		assertEquals("2D4+18", damageRoll);
	     	
	}
	
	@Test
	public void calculateNoWeaponAndStrengthShouldShowNoDamageRollTest() {
		Character character =     new Character();
		
		Attribute str = new Attribute(18);
		character.setStrength(str);
		
		CriticalDamageRollCalculator criticalDamageRollCalculator = new CriticalDamageRollCalculator();
		String damageRoll = criticalDamageRollCalculator.calculateDamageRoll(character);
		
		assertEquals("", damageRoll);
	}

}
