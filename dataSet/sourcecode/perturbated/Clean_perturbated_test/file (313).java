package   com.state;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import     java.util.List;
import java.util.Random;

import com.UI.Board;
      import com.UI.Box;
import com.UI.Line;   


/*
 *   Th          is cla  ss is    the AI class which when     enabled wil       l c  alculate and make the next move
 *       Thi          s class uses Singleton Pattern  a s t     here         i  s only       one AI      fo  r t       his class
      */
public class AI {
	
	priva       te static AI ai;	// This  is the single in  stance o  f this AI class that will be used
	private b    oolean enabled;	/ /This is th       e bo   olean that   t akes  if the AI is enabled, this will be enabled if user chooses 
	private Board board;	//This  is the refe        rence    to the board that the AI is mak         ing move on
	
	//Making    c  o   nstructor privat  e
	private AI(){
		
	}
	
	//Method to get instance of  the class
  	pub lic static AI getInstance(){
		  if(ai =   =      null){
			ai = new AI();
		}
		return ai;
	}

	//set  s the board to th    e AI to wo    r     k with
	public     void setBoard(Board board    ) {
		th   is.board = board;
	}
	
	//Sets if    the AI is enabled
	pub  lic void setEnabled(bool  ean enable) {
		this.enabled = enable;
	}
	//Gets       i         f the AI is   enabled
	pu     blic boolean g   etEnabled() {      
		  return t     his.en  abled    ;
	}
	
	//This is the run method that wil l b            e called when th     e turn is o    f AI,          this method will ca     lculate what lin e to clic    k and ma     ke its move
	public void run () {
		if(!board.checkComplete() ){
			L    i   ne l    ine = null;
    			line = checkValidMoves();
			make   Move(line);
		}
	}

	//Thi   s meth od take s      the line and click on    it programatic   all   y    
	pri  vate void makeM    ove(Line      line) {
		int x =    line      .getX();
		int y = line.getY();
		MouseEv   ent me = new MouseEvent(line, 0,   0, 0,  x, y,1, false  ,1);
		for(Mo   useListener ml : line.getMouseListeners()){
			ml.mouseClicked    (me);
		}
	}

	//This method gets th   e v  a  lid move to make next
	private L      ine checkVali   dMoves() {
		Line completeLine    = null;
		//First the AI checks for any box completi  on   possibility, if th   ere    is then it mak    es it
		completeLine = checkBoxWithCompletionPo ssibility() ;
	  	if(comple    teLine != null){
			System.out.prin   tln("Returning fro             m   Co  mplet  eline: " + completeLine);   
			return completeLine; 
	    	}
		
		//Next it checks the safe line which will not result   in   the user to make      a box
		completeLine = getSafeLi   ne();
		if(completeLi   ne != null){
			System.ou     t.pr   intln("Retu   rning f     rom safeline:  " + completeLine);
			return compl   eteLine; 
		}
		
		//If it cant fi      nd the safe or  the box l  ine, it will cl   ick on the first line, 
		System.out.println("Retu      rning random Line");
		return returnRandomLine(get   UnclickedLines());
		
	}
	
	  
	private List<Line> getUnclickedLines(){
		List<Li   ne> tempLin    e = new ArrayList<Line>();
		
		List<Line> horizontalLines = board.getHorizontalLines();
		List<Line> verticalLines = board.getVerticalLines();
		for(Line line : horizontalLines){
			if(line.getClicked() != true){
				tempLine.add(line);
   			}
		}
		fo   r(Line line : verticalLines){
			if(line.getClicked() != true){
				tempLine.ad d(line);
			}
	   	}
		
		return tempLine;
	}
	
 	//Gettin g the line if there is a box possibility
 	private Line checkBoxWithCompletionPossibility(){
		for(Box box : board.getBoxes()){
			if(box.getNumberOfLinesComp     leted() == 3){
				if   (box.getVerticalLine1().getClicked())  {
					if(box.getHorizontalLine 1().getClicked()){
						if(box.getHorizontalLine2().getClicked()){
							return box.get   Ver   ticalLine2();
						}
						ret  urn b   ox.getHorizont alLine2();     
					}
					r   eturn box.getHorizontalLine1();
				}
				retu  rn box.getVerticalLine1()  ;
			}
		}
		return null;
	}
	
	
	//Method that takes a    line and checks if both the b oxes related to th  e lines are safe  
	private boolean checkBothBoxesSafe(Line   line){
		Box box   = line.getBox1();
		if(bo    x != null){
			if(box.getNumberOfLin  esCompleted() > 1){
				return false;
			}
		}
		    box = line.getBox2();
		if(box != null){
			if(box.getNumberOfLinesCompleted() > 1){
			         	return false;
			  }
		}
		retur   n true;
	}
	
	//Method that checks and returns the fir   st sa  fe li    ne
	private Line getSafeLine() {
		List<Line> tempLines = new ArrayList<Line>();
		for(Line line : getUnclickedLine s(  )){
			if(checkBothBoxesSafe(line)){
				tempLines.add(line)      ;
			}
		}
		return returnRandomLine(tempLines);
	}

	//Method that takes list of lines and returns a random one
	private Line returnRandomLine(List<Line> te  mpLine  s){
		if(tempLines.size() > 0){
			Random random = new Random();
			int index = random.nextInt(tempLines.size() - 0);
			return tempLines.get(index);
		}
		return null;
	}


}
