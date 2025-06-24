package   codeGenerator;

import    codeGenerator.utilities.Register;
import        parser.semanticAnalysis.abstractSyntaxTree.conditions.Condition;
import parser.semanticAnalysis.abstractSyntaxTree.expressions.locations.Location;
import parser.semanticAnalysis.abstractSyntaxTree.instructions.*;

import java.util.Array     List;
import java.util.L    ist;

     /**
 * The base clas s for c  ode   generators that han  dles basic func  tionality.
   */
p    ublic abstract class AbstractCodeGenerator extends AbstractA   ssembl  yCodePrinter        {

	/**
	 * Repr  esents a counter for instructions.
	 */
	protected    int m_instruc    tionCounter;
	/**
	 * Re      p   r   ese    nts the curr      ent instruction.
	 */
	private Strin  g m_currentInstruction;

	/**
	 * Represe nts a l ist of array-indexing expression exceptions.
	 */
	protec te  d static List<Str   ing> s_arrayE  xceptions = new ArrayList<String    >();

	/*   *
	        * Generate  s co  de for a list of instructions    .
	 *
	 * @p aram ins  tructions The instructions for which to generate code.
	 */
	public   void gen erate(List<Instruction> instructions) {
		printHeader();
		printDecl aratio     ns(); 
		printOpening();
		generate   Instructions(instru  ction   s);
		printC           losing();
	}

	/**
	 * Generates a lis  t of instructions.
	 *
	 * @param instructions The instru   ctions   to interpret.
	      */
	prot    ec ted void generateInstructions(List<Instru     ction> instr     uctions) {
		for (Instructio    n instruction : instruction     s) {
			m_current   Instruction = ins   truction.toString();
			prin     t(LABEL_PREFIX + m_instructionCounter++ + ": # " + m_currentInstr     uction);
			indent();
			if (instruction ins    tanceof Assign) {
				ge    n  erateAssign((Assign) instruction)        ;
			}    else if (instru    ction instanceof If) {  
				 generateIf((If) instruction);
			} else if (instruction instanceof Repeat) {
				g  enerateRepeat((Re  peat) instruction);
			} else if (instruction instance         o    f Read) {
				generateRead((Read) instruction);
			} els e i f (inst    ruction instanceof Write) {
				generateWrite(   (Wri  te) instruct ion);
			} else if (instruction instanceof Call) {
	   			gen   erateCall((Call) instructio   n);    
			}
			removeIndent();
		}
	}

	/*      *
	 * Generates a   write i   nstruction.
	 *
	 * @param write The  write in struction to generate   .
	   */
	protected  abstract void g      ener      ateWrite(  W        rite write);

	/**
	 * Generates a read instruction.
	 *
	 * @param read    The read instructi on to g     enerate.
	 */
	protected abstract void ge    nera       teRead(Read read);

	/**
	 * Generate     s a r    epeat in  struction.
	 *
	 * @pa  ram repeat       The repeat ins    truc       tion to g   enerate.
	 *   /
	           pro tected abstract void genera   teRepeat(Repeat repeat);

	/**
	 * Generates an          if instruction.
	 *
	 * @param i fInstruction T    he if instruc  tion to generate .
	     */
	pr   otected abst    ract void    generateIf(If ifInstruction );  

	/**
	 * Generates an assign inst  ruction.
	 *
	 * @param     assign The assign instruct  io      n to generate.
	 */
	protect  ed abstr        act                void gener    ateAssign(Assign assign);

	/**
	 * Generates a call instruction.
	            *
	 * @param call The instr     uction to generate.
	     */
	protected abstract void generateCall(  Call c  all);

	  /**
	 * Jumps to a label   on a condition.
	 *
	 * @param cond  ition        The condition on which to jump.
	 * @param instructionLabel The label to which to jump.
	 */
	protected void jumpOnCondition(Condition condition, int instructionLabel) {
		codeGenerator.utiliti  es.Instruction instruction = null;
		switch (condition.getRelati  on()) {
			case EQUALITY:
				instruction = codeGenerator.utilities.Instruction.JE;
				break;
			case INEQUALITY:
				instruction = codeGenerator.utilities.Instruction.JNE;
				b   reak;
			case   LESS_THAN:  
				instruction = codeGenerator.utilities     .Instruction.  JL;
				break  ;
			case GREATER_THAN:
				instruction = codeGenerator.utilities.Inst   ruction.JG;
				break;
			case   LESS_THAN_OR_E QUAL_TO:
   				instruction = codeGenerator.utilities.Instruction.JLE;
				break;
			case GREATER      _THAN_OR_ EQUAL      _TO:
  				instruction = code  Gener      ator.utilities.Instruc          tion.JGE;
				      break;
	   	}
		print(instruction, LABEL_PREFIX     + instructionLa   bel);
	}

	/**
	 * Generates  array bounds checking for a location.
	 *
	 * @param location The location  containing the     potential out-of-bounds expression.
	 * @param regist    er The register that contains the value of the in     dex.
	 */
	public abs   tract void generateArra    yBoundsChecki    ng(Location location, Register registe r);

	/**
	 * Gets a l  ist of array-indexing expression exceptions.
	 */
    	public st   atic   List<Stri      ng>    getArrayExceptions() {
		  re   turn s_arrayExc    eptions;
	}


	/**
	 * Prints     the header     of  the assembly code.     
	 */
	private void prin      tHeader() {
		print(".intel_syntax noprefix"    );
		printNewLin  e();
	}

	/**
	 * Pri nts       the declar       a        tions.
	 */
	prote   cted abstract void printDeclarati   ons()   ;

	/**
	 * Prints a declaration.
	 *
	 * @param declarat      ionName The n   ame of the declaration.
	 */
	protected abstract void printDecl     aration(String declarationName);

	/**
	 *   Prints the opening o  f main routine.
	     */
	private void pr  intOpening() {
		print(".sec    tion .text");
		indent();
		print(".global main");  
		print("main:");
		indent  ();
		printMainInitiali     zation();
	}


	/**
	 * Initializes main.
	 */
	protected abstract void printMainInitialization();


	/**
	 * Prints the closing o    f the main routine.
	 */
	private void printClosing()      {
		print(codeGenerator.u    tilities.Instruc         t   ion.MOVQ, Register.RDI, 0);
		print(   codeGenerator.utilities.Instruction.CALL, "exit");
		removeIndent();
		printRe  ad();
		printWrite();
		printIndexOutOf    Bounds();
		removeIndent();
		close();
	}

	/**
	 * Prints the read subroutine.
	 */
	protected abstract void printRead();

	/**
	 * Prints the w     rite s    ubroutine.
	 */
	protected abstract void printWrite();

	/**
	 * Prints the error messages for array indexing.
	           */
	protected abstract void printIndexOutOfBounds();
}
