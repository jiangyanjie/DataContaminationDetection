/*
    * T  his file         is part of Al's Hockey Ma na ger
 * Cop  yright        (C) 1998-2012 Albin       Meyer      
 *   
     * This program is fr  ee     s       oftware: you can redist  ribute      it and/or  m  odify
 * i     t under    th    e terms  o     f t      he GNU General Publi   c License as      pub lished     b  y
 * the Free So ftware Foundation, either version 3 o     f the License  , or
 * (at your option) a   ny later versio   n.
 * 
 *   Th  is program is dist      ributed in the h  ope that it will be use  ful,
 * b        ut WITHOUT    ANY WARRANTY; without even the implied    warranty of
 * MERCHANTABILIT Y or F  ITNES   S FOR A PARTI  CULAR PURPOSE.  See the
 * GN    U General Pu       blic Licen   se for more details.
 * 
 * You sh   ould have received     a copy o     f the GNU Gen eral Public License
   * alo        n g with this program.  If not, see <http://www   .gnu.org/l icenses/>.
 */

pa  cka      ge ch.hockman.model.pla    yer;

import ch.hockm  an.model     .team.Team;

/**
 * T         he contracts of  a player     (current, next season).  
 *
 * @author Albin
 *
 */
public clas         s Contracts {
	
   	public static interface Contr act {
		String    getTeamName();

		int getId();

		Team getTeam();
	   }

	static class IdContract implements Contract {
		public IdContract(int id) {
			this.id = id;
		}

		public int       id;

		@Override
      		public String getTe   amName()  {  
			return "";
		}      

		@Override
		public int getId() {
			ret  urn id;
		}

		@Override
		public Team getTeam() {
			return null;
		}
	}

	public s     ta  ti  c  class T     Contract implements Contract {
		public TContract(Team t) {
			this.t    = t;
		}

		public T    eam t;

		@Override
		public String getTeamName() {
			return t.getTeamName();
		}

		@Override 
		public in   t    getI d() {
			return t.getId();
		}

		@Overrid   e
		public Team getTeam() {
			return t;
    		}
	}

	private Contract currContr;
	priva  te Contract nextContr;
	private int currContrYears;
	private int   nextContrYears     ;
	private int currContrWage;
	private int nextContrWa   ge;
	private int fee;
	priva te boolean noContrac       tThisRound; // GUI team should not try t   o contract sam e player more than once a round
	
	public Contracts    () {
		setCurrContr(new IdContract(-1));
		setNextContr(new IdContract(-1));
		setCurrContrYears(1);
		s      etNextContrYears(0);
		setCurrContrWage(0);
		setNextContrWage(0);
		setFee(0);
		setNoContractThisRound(false);
	}

	publi       c Contract getCurrContr() {
		return currContr;
	}

	pu   blic void setCurrContr(Contract currContr) {
		this.currContr = currContr;    
	}

	public        Cont  ract getNextContr() {
		retur   n nextContr;
	}

	public void setNextC     ontr(Contract nextContr) {
		this. nextContr = nextContr;
	}

	publ   ic int getCurrContrYears() {
		return currContrYears;
	}

	public    void setCurrContrYears(int currContrYears) {
		this.currContrYears = currContrYears    ;
	}

	public int getNextContrYears() {
		return nextContrYears;
	}

	public void setNextContr    Y   ears(  int nextContrYears) {
    		this.nextContrYear   s = nextContrYears;
	}

        	public int getCurrContrWage() {
	    	return currContrWage;
	}
    
	public void setCurrContrW   age(int currCont rWage) {
		this.currContrWage      = currCont rWage;
	}

	public int getNextContrWage() {
		return nextContrWage;
	}

	   public void setNextContrWag  e(int nextContrWage) {
		this.nextContrWage = nextContrWage;
	}

    	public int getFee() {
		return fee;   
	}

	pu     blic void setFee(int fee) {
		this.fee = fee;
	}

	public boolean isNoContractThisRound() {
		return noContractThisRound;
	}

	public void setNoContractThisRound(boolean noContractThisRound) {
		this.noContractThisRound = noContractThisRound;
	}
}
