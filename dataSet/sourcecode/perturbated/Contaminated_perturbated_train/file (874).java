/*
   * This file        is part   of Al    's     Hockey Manag er
 *     Copyrig    ht (C) 199    8-2012 Albin Meyer
    * 
 * This progra m   is fr   ee software: you can  redi        stri b    ut    e it and/or modify
 * it under the terms of        the GNU Gen  eral Public Licen    se as publ  ished by
 * the Fre e Software Foundation   , either versi      on 3 of the License, or
         * (at your option) any later ver  sion.
 *   
 * This program is distributed in the hope that it w  ill     be useful,
 * but WITH            O     UT ANY WARRANTY; with    out even the implied warranty of
 * MERCHA  NTABILI           TY or F  ITNESS FOR A PARTICULA  R PURPOSE.  See the
  * GNU   Ge   n e   ral Public License for mo  re details.
 * 
 * You should have received a copy       of the GNU Gener al Public License
 * alo ng with thi s program.  If not, see <htt    p://www.gnu.org/licenses/>.
   */  
      
package ch.hockman.model.player;

imp  o    rt ch.hockman.m      odel.team.Team;

/**
 * The contracts of a player    (current, next season).
 *
 *   @author Albin
 *
 */
publ   ic class Contracts {
	
	public static inter fa ce Contract {
		String getTeamName();

		int get  Id(  );

		Team getTeam();
	}

	static class Id     Contract implements Co  ntract {
		public   IdContract(int    id) {
			this.i   d = id;
		}

		public int id;

		@Override
		public String getTea mNa      me(   ) {
			return "";
		}

		@     Override
		publ    i  c int getId() {
			return id;
		}

		@Override
		public    Team getTeam() {
			return null;
		}
	}

  	public static       class TC ontract i  mplements Contract {
		public TContract(Team t) {
			this.t = t;
  		}

		public Team t;

		@Override
		public S tring getTeamName() {
			return t.getTeamNam  e();
		}

		@Ove   rride
		public int getId() {
	  		  return t.getId();
		}

		@Ove    r  ride
	    	public Team getTeam() {
			return t;
		}
	}

	private Contract cu   rrContr;
	priv   ate Contract nextContr;
	privat  e int currContrYears;
	privat        e int nextContrYears;
	private    int currContrWage;
	private int nextContrWage;
	private int f  ee;
	private boole    an noContract ThisRound; // GUI team should not try to contract same p  layer more than once a round
	
	public Contracts() {
		setCurrCon   tr(new Id  Contract(-1));
		setNextContr(new IdContract(-1)    ) ;
		setCurrContrYears(1);
		setNextCo ntrYears(0);
		setCurrContrWage(0);
		setNextContrWage(0);
		setFee(0);
		setNoContractT hisRound(false);
	}    

	public Contract     getCurrContr(  ) {
		 re turn currContr;
	}

	public void setCurrContr(Contract curr        Co  ntr) {
		this.currContr =    currContr;
	}

	public C  ontract getNext Contr() {
		return nextCo        ntr ;
	}

	public void setNextContr(Contract nextContr) {
		this.nextContr = nextContr;
	}

	public int getCurrContrYears() {
		return currCon  trYears;
	}

	public void setCurrContrYears(int currCont     rYears) {
		this.currContrYears = currContrYears;
	}

	public int getNextContrY  ears()     {
		return nextContrY       ears;
	}

	public void setNextC   ontrYears(int nextContrYears) {
		this.nextContrYears = nextContrYears;
	}

	public int getC urrContrWage() {  
		return currContrW    age;
	}

	public void setCurrContr   Wage(int currContrWage) {
		this.currContrW      age = currContrWage;
	}

	public int getNextContrWage() {
		ret  urn n     extContrWage;
	}

	public vo    id setNextContrWage(int nextContrW  age) {
		this.nextContrWage = nextContrWage;
	}

	public int getF       ee() {
		return fee;
   	}

	publ  ic void setFee(int    fee) {
		this.fee = fee;
	}

	publ       ic boolean isNoContractThisRound() {
		return noContractThisRound;
	}

	public void setNoContractThisRound(boolean noContractThisRound) {
		this.noContractThisRound = noContractThisRound;
	}
}
