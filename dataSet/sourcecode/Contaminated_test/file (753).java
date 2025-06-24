/**
 *
 */
package stats;

import info.gridworld.actor.Actor;

import java.util.Arrays;

public class DefaultStats extends Actor implements CompleatStats {

    private int level;
    private int nextExp;
    private int exp;

    private int maxHP;
    private int curHP;

    private int strength;
    private int defense;
    private int agility;

    public DefaultStats() {// these will change depending on
// the character
        // we could add some math.random for variance or parameters for the
// Player Character
        super();
        setLevel(1);
        setExp(0);
        setNextExp(100);
        setMaxHp(100);
        setCurrentHp(getMaxHp());
        setStrength(20);
        setDefense(10);
        setAgility(5);
    }

    @Override
    public void act() {
        if (!checkLife()) {
            removeSelfFromGrid();
        }

        while (this.exp > this.nextExp) {
            this.exp -= this.nextExp;
            levelUp();
        }
    }

    @Override
    public int getMaxHp() {
        return this.maxHP;
    }

    @Override
    public void setMaxHp(int hp) {
        this.maxHP = hp;
    }

    @Override
    public int getCurrentHp() {
        return this.curHP;
    }

    @Override
    public void setCurrentHp(int hp) {
        if (hp >= this.maxHP) {
            this.curHP = this.maxHP;
        } else {
            this.curHP = hp;
        }
    }

    @Override
    public boolean checkLife() {
        return this.curHP > 0;
    }

    @Override
    public void takeDamage(int damage) {
        if (Math.random() > getAvoidPercent()) {
            this.curHP -= damage - defense;
        }
    }

    @Override
    public void restoreHealth(int healing) {
        if (this.curHP + healing >= this.maxHP) {
            this.curHP = this.maxHP;
        } else {
            this.curHP += healing;
        }

    }

    @Override
    public int getStrength() {
        return this.strength;
    }

    @Override
    public void setStrength(int str) {
        this.strength = str;

    }

    @Override
    public int getDefense() {
        return this.defense;
    }

    @Override
    public void setDefense(int def) {
        this.defense = def;

    }

    @Override
    public int getAgility() {
        return this.agility;
    }

    @Override
    public void setAgility(int agi) {
        if (agi >= 99) {// agility will be a % chance and must be < 100
            this.agility = 99;
        } else {
            this.agility = agi;
        }
    }

    @Override
    public double getAvoidPercent() {
        return this.agility / 100.0;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(int lev) {
        this.level = lev;
    }

    @Override
    public void levelUp() {
        this.level++;
        this.nextExp *= 1.1;

        int total = 0;
        while (total == 0) {
            if (Math.random() > 0.5) {
                this.maxHP += 3;
                this.curHP += 3;
                total++;
                if (Math.random() > 0.8) {
                    this.maxHP += 2;
                    this.curHP += 2;
                }
            }
            // this.curHP = this.maxHP;

            if (Math.random() > 0.5) {
                this.strength++;
                total++;
                if (Math.random() > 0.8) {
                    this.strength++;
                }

            }

            if (Math.random() > 0.5) {
                this.defense++;
                total++;
                if (Math.random() > 0.8) {
                    this.defense++;
                }
            }

            if (Math.random() > 0.8) {
                this.agility++;
                total++;
            }
        }
    }

    public String levelUpDisplay() {// don't use this in game this is to test
// the basic leveling implementation with an output
        int[] out = new int[5];
        this.level++;
        out[0] = (int) (this.nextExp * 1.1 - this.nextExp);
        this.nextExp *= 1.1;

        int total = 0;
        while (total == 0) {

            if (Math.random() > 0.5) {
                this.maxHP += 3;
                this.curHP += 3;
                total++;
                out[1] += 3;
                if (Math.random() > 0.8) {
                    this.maxHP += 2;
                    this.curHP += 2;
                    out[1] += 2;
                }
            }
            // this.curHP = this.maxHP;

            if (Math.random() > 0.5) {
                this.strength++;
                total++;
                out[2]++;
                if (Math.random() > 0.8) {
                    this.strength++;
                    out[2]++;
                }

            }

            if (Math.random() > 0.5) {
                this.defense++;
                out[3]++;
                total++;
                if (Math.random() > 0.8) {
                    this.defense++;
                    out[3]++;
                }
            }

            if (Math.random() > 0.8) {
                this.agility++;
                total++;
                out[4]++;
            }
        }

        return Arrays.toString(out);
    }

    @Override
    public int getNextExp() {
        return this.nextExp;
    }

    @Override
    public void setNextExp(int exp) {
        this.nextExp = exp;
    }

    @Override
    public int getExp() {
        return this.exp;
    }

    @Override
    public void setExp(int exp) {
        if (exp < this.nextExp) {
            this.exp = exp;
        }
    }

    @Override
    public void addExp(int exp) {
        this.exp += exp;
    }

}
