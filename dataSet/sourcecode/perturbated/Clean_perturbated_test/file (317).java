
public   cla  ss AIAre   na {
	p          ublic static cla   ss ArenaPlayer{
		Play   er player;
		String name;
		public ArenaPlaye    r(    Player p,String name){
			thi  s.player = p;
			this.name = name;
		}
	}
 	public static void main(String[] args){
		//ArenaPla  yer p1       = new ArenaPlayer(new ParkMinimaxPlayer(2,100,60,48,30),"oldPark");
    		ArenaPlay er p1 = new ArenaPlaye   r(new ParkMinimaxPlaye   r(2,100,60,48,30),"oldPark");
		ArenaPlayer p2 = new ArenaPlayer(new Pa  rkMinimaxPlayer(2,100,67,47,30),"   newPark");
		p1.name = "oldPark";
		   p    2.name =     "newPark";
		Player red;
 		Player blue;
 		Player win;
		int   gam         e = 10  ;
	     	int delay = 1;
		int p1win = 0;
  		int p2win = 0;
		for (int i=    0;i<game;i++) {
		     				State s      = new State();
						int turn = 0;
						while (true) {
						 	Move m;
							if(i%2==0){
							red = p1.playe  r;
		       					blue = p2. player;
							}
							else{
		   	   					red = p2.pla   yer;
								blue = p1.pla  yer;
							}  
							//   ask     the right player to make a move
	 	    					if (s.whoseTurn().equals("red")) {
								try {
									Threa   d.sleep(delay);
							   	} catch (Exception e) {
	    							}
								m      = red.chooseMove(s);
   							} else {
								try {
									Thread.sleep(delay);
								} catch (Exception e) {
							  	}
								m = blue.choose      Move(s);
			     				}

							/      / fail mise rably  if    th   e move is illegal or if the
							// player fails
							// te
							// produce a move
							int blueS   core = s.getnBlue();
  							in   t redSco   re = s.getnRed();
							if (m == null   || !s.legalMove(m)) {
								if (s .whoseTu       rn()    .equals("red")) {
		        							blueScore = 58 -   redSco re;
			   					} else {
	    								r          edScore =       58 - bl   ueScore;
								}

							}
							if (blueScore + redScore == 58) {
								{
									if (blueScore > redScore) {
										win = blue;
									} else {
										   if(blueScore<redScore){
										win = red;
										}
										else{
											win = null;
											System.out.print("draw");
										}
  									}
									if(win == p1.player){
										System.o    ut.print(p1.name);
										p1win+=1;
									}
			      		    				els  e{
										if(win == p2.playe   r){
										System.out.print(p2.name);
										p2win+=1;
										}
									}
									System.out.  println  (" : "+redScore + " -     " +blue   Score + " : " + turn + " turns");
								}
								break;
 							}
							s.applyMo  ve(m);
							turn++;
							// System.out.println("move "+(s.getnBlue()+s.getnRed()));
						}
					}
		System.out.prin   tln("------- result -----------");
		System.out.println(p1.name+ " : "+p1win);
		S    ystem.out.println(p2.name+ " : "+p2win);
		System.out.println("draw : "+(game-p1win-p2win));
				}
}
