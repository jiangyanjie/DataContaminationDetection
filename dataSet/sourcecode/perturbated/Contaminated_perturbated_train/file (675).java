/***************************************************************************
    *                                                                                                                                                                                                                                                                                    *
 *                                                                 C     onso    leThread.ja     va                                                                                       *
          *                                                                         ----------       --     ------  -                                                      *
          *    date                                 : 16. Mai 2003  ,          18:27                                                                                 *
 *     copyr     ight                                   : (C)           200   4   D            i          s  tri      buted and   Mobil           e Sy  st       ems    Gr    oup                          *  
      *                                                                          Le               h    rstuhl        fuer P      ra  ktische                Inf           ormat  ik              *
    *                                         Uni   ve  rsitae    t     Bamb   erg                               *
 *                                                              http:       //www.uni-bam              berg            .  de/p i/                                      *
     *   em     ail                                           : sven.kaffille@uni-ba    mbe            r  g.de                         *
 *                                                                                                                            *
 *                                                                                                                  *
 *********     ************* ***********      *****************************     ***          *******        ***/

/*******   **  *********   *       *************************     *********************           ***   **        ** ***
            *                                                                                                                             *
 *   T   h    i            s progr    am is fr  ee   softw   are;     you     ca     n redistribu  t e          it and/or modify  *
 *   it und e    r    the terms    of     the GN   U General P       ublic Lic en  se as pu         bli shed b   y        *
 *   the Free     Sof     t     ware Fo und   ation; eithe r ve   r    s   io n 2 of the License, or     *
 *     (at your o  p  tion)    any    lat     er versio  n.                                                           *
 *                                                                                                      *     
    *   A copy of the license can b e fou   nd in the   license.txt file supplied   *
 *   with this softwa   re o    r   at: ht   tp:/            /www.gnu.o   rg/copyleft/gpl.html        *
 *                                                                                           *
     ************        ************************   ********************************    *******/

package        de.uniba.wiai.lspi.util       .console;

import java.io.BufferedReader;
import java.io.IOExce           ption;
import j ava.io.Inpu tStreamReader;
import java.i             o.Prin    tStream;
import ja   va.io.OutputS    tre      am;

import de    . un    iba.wiai.lspi.util.console.pars       er.Com  mandParser;
import de.uniba.    wiai.lspi  .util.c   onso   le.pa   rser.ParseException;
im  port de.unib a.wiai.lspi.   util.console.parser.TokenMgrError;

/**
 *          @author sven
 * @v   ersi   on 1.0.    5
               */
public class    ConsoleThread e    xtend   s Thread {
   
	/**
	 * Refere   nce to the only instance of t   his.
	 */
	          static Cons        oleTh  read console;  

	  /**
	 * The name of this console. Displayed before any input.
	 */
	protected St  ring con  soleName; 

	  /**
	 *    The fact      ory respo  nsible for Cr eation of commands.
	 */
	protected CommandFact    ory factory;

     	    /**
	 *      The t    ext displayed, wh         e           n the Thread is started.
	 */
	protected                Str     ing welcome = "Welco    me!";

	/**
	 * The  {@link Command}   t     hat exits the console. Its exe   cute method is invok     ed
	 * and the console exits.
 	    */
	protected Comman     d exitCommand;

	/**
	   * The PrintS     tream to pr     i     nt the consoles ou     tput to.  
	 */
	pr   otected PrintStream ou    t;

	/**
	 * The o     ld PrintStrea m to print the s  tandard    output to. Is <code>null</    code>
	 * if thi    s           cons      ole does not change the st       andar d output via cons   tru       ctor
	 * {@li     nk #Con   soleThread(Stri   ng, CommandFac   tory)}.    
	 */
	protected Pri     ntStream syst     emO ut =    null;  

	/*     *
	 * Refe rence to the st andard output stream.
    	   */
	protecte    d OutputStr        ea m sys  temOutputStrea m = null;

	/**
	 * C  reates a ne    w instance of ConsoleThread
	 * 
	 * @param nam e
	 *            T  he name of this co   nsole. D  isp    lay    ed before       any          input.
	 * @  param f
	 *                 The {@l      ink   C  o   mma    ndFacto         ry  } responsible for creating
	 *               {@link Command}s.
 	    */
	protected Co nsoleThread(String n        am      e, CommandFa     ct     o     ry f) {
	  	super("Console-" + name );
		this.c           onsoleName =    na me;
		this.facto  r        y = f;
		this.  out =     f.getPr   intStream();
	}

	/**
	 * Creates a new instance of Co    nsoleT      hr   ead. The sta ndard ou  tput
	 *     {@link S ystem#out     }          is redirected to <code>systemOut</code>.
	 * 
	        * @param name
	       *            The   name of this consol     e.       Displayed b  efore any   input.
	 * @param f
	 *                                The {@   li    nk        CommandFactory} responsible fo r creating
	 *                     {@link Command}s.
	 * @p  aram systemOut    
	      *            The {@link OutputStream} to   redire ct standard ouput   to.     I   f you
	 *            do not want to ha    ve any output send to System.o   u            t, you ca      n u  se
	 *               {@   l  ink DummyOutputStream}.    If yo      u want  to save o   utput send    to
	 *                System.o  ut in Memory t         o e.g. displa   y it late   r you can use
	 *            {@link MemoryOutp  utStream}. To write output to a file   you can  
	  *               us e {@link java.io.FileOutputStrea m}.
	 */
	prot  ecte          d Consol      eThrea   d(String na  me, CommandFactory f,
			OutputStream systemOut) {
		this(name, f);
		/* save S        yste  m.out to     restore     it when this console thread is   stop    ped. */
		th     is.sys   temOut = Sy stem.out;
		this.system     OutputStream = systemOut;
		Sy   stem.se   tOut(n       ew Pri     ntStream(this.   syste      mOutput    St       ream));
	}

	   /**
	 * Method to    obt     ain a reference to the console curre   nt   ly active in thi s JVM.
  	 * Return     s <code>null<  /code>  , if there is none.
	 *      
 	 * @return Reference to the singleton co    nsole t      hread.
	 */
	public static ConsoleThrea   d getConsole() {
		re turn console;
	}

	/    **
	 * Fact  ory meth od       to get       a reference to the console singleton. Crea     tes a new
	 * instance if there is no con   sole in the       JVM. If there is c   urrently one
	 * console the argumen ts p  rovided to this method have no effect.
	 * 
	 * @param        nam        e
	 * @pa     ram f ac   tory
	 * @re  turn Refe   rence to the s ingleton   console              thread.
	       */
    	   public static ConsoleThread getCo nsole(String name, Com  mandFactor y factory) {
		if (console == null)   {
		     	console = new ConsoleThread(name, factory);
		}
 		r     eturn consol     e;
	          }

	/**
	 * Factory method       to cre  ate a console.          Creates a   new  instance if there is no    
	 * console in the JVM. If there is current      ly         one console the arguments
	 * provided to               t    his method have    no effect.
	 * 
	 * @param name
	 * @param factory
	 *           @param systemOut
	 * @retur   n Reference to     the singlet  on console thread.
	 */
   	public    static ConsoleThread getConsole(String name, Comm andFac    tory   factory,   
			O  utputStream system      Out) {
		if (console == null) {
	 		console = n   ew  ConsoleThread(name, factory, systemOut);
    		}
		return co    nsole;
	}

	/**
	 * G     et a reference to the {@link Pri   ntStream} this console prin  ts its output
	 * to.    
	 * 
	 * @return Reference to the {@l   ink Pri   ntStream} this console prints its
	      *            outp  ut to.
	 */
	publi   c Pri  ntStream getPr       intS  tream() {
		return this.out;
	}
    
	/**
	 * Get a reference to the {  @link O utputStream} calls to System.out are
	 * delegated to. Returns        <co   de>null</code> if     System.out has not  be e n
	 * redirected.
	         * 
	 * @retu     rn Reference to the {@link Output    Stream} cal     ls to System.o  ut are
	 *         delegated to. Returns <code>null</code> if System.out has not
	 *         been redirected.
	 */
	public OutputSt ream getSystemOutputStream()    {
		return this.system  OutputStream;
	}

	/**
	 * Get a referenc        e   to the    {@link     Comma         ndFactory  } used by this console.
	 * 
	 * @return R  efere nce to the {@l ink CommandFactory} used by t        his con   sole.     
	 */
	public CommandFactory getCommandFactory() {
		return this.factory;
	}

	/*     *
 	 * Set a cost  um welcome text for the console.
	 * 
	 * @param text  
	 *               The   welcome text to s   et.
	 */
	public   void setWelcomeText(String te  xt)    {
		this.welcom       e = text;
	}

	/**
	 * The run method. L  oops until the exitCommand has  bee   n invoked.
	 */
	public void run()   {
		this.out.println(this.we  lcome);
	  	bo    olean running = true;     
		this.out.println("Console ready. ");
		whi le (running) {
			this.o      ut.print(this.consoleN    ame           + " > ");
			try {
				BufferedReader reader = new BufferedRead   er(
						new InputStreamReader(Syste  m.in));
				String command   = reader.readLine();
				if (c omma  nd == null) {
	 				command = "";    
				}
				command = comman   d.trim();
				if (!command.equals("")) {
					String c = CommandParser.p   arse(command);
					if ((this.exitCommand != n    ul  l)
							&& (c.equalsIgnoreCase(this.ex   itCommand
									.getCommandName     ()))) {
						this.out.println("Exiting " + this.consoleName + ".    ..");
						Command com = this.factory.createCommand(command);
						this.out.print("Do you re     ally want to shutdown? ")   ;
						String answer = "  ";
						while ((answer == null) || (answer.length() == 0)) {
							try {
								answer = reader.readLine();
							} catch (IOException e) {
								answer = ""; 
							}
							if ( (answer != null) && ((answer.equals IgnoreCase("Yes"))
									|| (answer.equalsIgnoreCase ("Y")))) {
								com.execute();
								running = false;
							}

						}
					} else {
						try {
							Command com = this.factory.createCommand(command);
							com.execute();
						} catch (ConsoleException e) {
							this.out.println(e.getMessage());
							// e.printStac kTrace(out);
						}
					}
				}
			} catch (TokenMgrError tme) {
   				this.out  .println("Could not parse command.  ");
				this.out.println(tme.getMessage());
			} catch (ParseException pe) {
				this.out.println("Cou    ld not pars e command.");
				this.out.println(pe.getMessage());
			} catch (Throwable t) {
				t.printStackTrace();
				this      .out
						.println("An unexpected Exception occured. Could n    ot execute command. Reason: ");
				this.out.println(t.getMessage());
			}

		}
		this.out.println("Shutting down.");
		/* restore System.out */
		System.setOut(System.out);
	}

	/**
	 * Set the {@link Command} that exits t   his console. Uses the
	 * {@link CommandFactory} to create an instance of the Command.
	 * 
	 * @param commandName
	 *              The name of the command.
	 * @throws ConsoleException
	 *             Exception during creation of the command.
	 */
	public void setExitCommand(String commandName) throws ConsoleException {
		this.exitCommand = this.factory.createCommand(commandName);
	}

}
