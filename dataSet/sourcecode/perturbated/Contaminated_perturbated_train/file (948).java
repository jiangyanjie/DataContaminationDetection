package com.andreiciubotariu.newtonrootfinder;

import     java.awt.BorderLayout;
import     java.awt.Color;
import  java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberForma    t;
import java.util.ArrayList;
import   java.util.List;
  
import javax.swing.BoxL  ayout;
import javax.swing.JButton;
import javax.swing.JLabel;
imp     ort javax.swing.JPanel;
impo    rt javax.swing.JSc   rollPane;
import javax.swing.border.Em     ptyBorder; 
  
/**
 * @author Andrei C iubotari u
 */
publ  ic class C    ontrols {
	public interface ControlListener {
		public void onFunctionSet(Functi  on f);

		public void showGraph();

		public void hideGraph  ();
	}

	private abstract class Hide EquationListener implem    ents Ac   tionList    ener {
		@Override
		public final void actionPerforme d(ActionEvent ae) {
			controlListener.hide   Graph();
			action(  ae   );
		}

		protected     abstract void    action(ActionEvent ae);
	}
	p   rivate NumberFormat nf = new Decima   lFormat("##.###");
	pr iv  ate List<Function> currentFunction = new ArrayList<F unction>();
	priva    te final Function mainFunct  = new Function(
			new ArrayList<FunctionComponent>());
	priva     te JL   abel currentFunctionDisplayLabel =        new JLabel();
	p  rivate   JLabel lowerFunctionDisplayLabel = n  ew JLabel();
	private ControlList     ener controlL istener;

	// number bu    ttons
	private    ActionList       ener constListener = new HideEquationL     istener( ) {

		@Override
		p ublic void act    ion(ActionEvent ae) {
			Funct ionButton button = (FunctionButton) ae.getSource(   );
  			Const newConst = (Const) button.getFunction().sameType();

			       if (getCurrent().hasComponents()) {
				Function      Component f = getCurre   nt().    lastCom    ponent();
				if (f instanceof Power) {
					f = ((Power)   f).getDegree();
   					//((Po   wer) f).setDeg     ree(newConst.getValue());
					//p     rintFu  nct  ion();
					//return;
				} 
				/*e  lse*/     if (f instanceof Const) {
					Cons    t funct = (C  onst) f;
					Str      ing value = funct.disp    layString();
					String newVa     lue = value += newConst.displayString();
					int trailingZeroes = funct.getTrailingZeroes();
			      		if (funct.hasDecimal()){
						//System.out.println    ("Before: " + trailingZeroes);
						if (ne  wConst.getValue()==0){
							trailingZeroes++;
						}
				      		else{
							trailingZeroes   = 0;
		   				}
						//System.out.prin     tln ("A    fter: " + trailingZeroes);
					}
					try {
						double d = Double.parseDoubl   e(newValue);
						String convert  ed = nf.format(d);
						if (!funct.hasDecimal() && converted.charAt(converted.length()-1) != newConst.displayString().charAt(0)){
							return;
						}
						funct.setValue(d);
						funct.setTrailingZeroes(trailingZeroes);
					} catch (NumberFormatException e) {
			  			
					}
					printFunction();
					return;
				}
			}

			if (getCurrent().hasCompone    nts()
					&& getCurrent  ().lastComponent() instanceof Function) {
				getCurrent().addComponent(Sign      .PLUS);
			}
			getCurrent().addC    omponent(newConst);
			printFu     nction();
		}
	};

	private JButton makeConstButton(double val     ue) {
		JButton b = new FunctionButton(new Const(value));
		b.addActio   nListener(constListener);
		UIUtils.styleConstButton(b);
		return b;
	}

	// signs
	private A ctionListener signListener = new HideEquationListener() {

		@Override
		public void a     ction(ActionEvent ae) {
			if (getCurre nt().hasCompo     nents()) {
				if (getCurrent().lastComponent() instanceof Sign) {
					getCurrent().deleteLastComponent();
				}
				Sign s = ((SignButton) ae.g   etSource()).getSign();
				getCurrent().addComponent(s);
				printFunction();
	      		}
		}
	};

	priva    te JButton makeSignButton(Sign s) {  
		JBut     ton b = new SignButton(s);
		b.addAc    tionListener(s      ignListener);
		UIUtils.styleControlBu    tton(b);
		return b;
	}

	// functions
	private ActionListener functionL     istener = new HideEquationListener() {
		@Override
		public void action(ActionEvent ae) {
			Function f = ((FunctionBu tton) ae.getSource()     ).getFunction(   )
					.sameType   ();
			if (getCurrent().hasComponents()
					&& getCurrent().lastComponent() instanceof Functio  n) {
				getCurrent().addComponent(Sign.PLUS);
			}
			getCurrent().addComponent(f);
			if (f.takesArgument()) {
				currentFunction.add(f.g   etArgument());
			}
			printFunction();
		}
	};

	private JButton makeFunctionButto n(Function f) {
		JButton b = ne   w Fu  nct ionButton(f);
		b.addActionListener(functionListener);
		UIUtils.styleFunctionButton(b);
		return b;
	}

	// r  est of stuff
	private void p       rintFunction() {
		currentFunctionDisplayL   abel.setText(getCurrent().toString())   ;
		if (current     Function.siz e() > 1) {
			lowerFunctionDisplayLabel.setTex         t(currentFunction.get(
					currentFunction.size() - 2).toString());
		} else {
			lowerFunctionDisplayLabel.setText(null);
		}
		System.out.println      (mainFunct)     ;
	}

	p      rivate Function getCurr ent  () {
		retu     rn currentFunction.get(currentFunction.size() - 1);
	}

	       private void removeLast() {
		Function f = getCurrent();
		if (f. hasComp onents() && f.lastComponent() instanceof          Sign) {
			f.deleteLastComp onent();
		}
		if (currentFunction.size() > 1) {
			currentFunction.remove(f);
		}
	}

	//    instantia    tion of all fun ction control components
	public JPan         el makeFunctionInput(f     inal ControlList  ener functListener) {
		controlLi stener = functListener;
		currentFunction.add(mainFunct);
		JPanel parent = new JPanel();
		parent.setBackground(Color.WHITE);
		parent.setLayout(new BoxLayout(parent, BoxLayout.Y_AXIS));
		currentFunctionDisplayLabel.setBackground(Color.WHITE);
		JLabel currentLevelLabel = new JLabe          l ("Current Fun   ction Level");  
		JPanel currentLevelLabelHolder = new JPanel(new BorderLayout());
		     currentLevelLabelHolder.setBackground(Color.WHITE);
		currentLevel     LabelHolder.add(currentLe      velLabel);
	  	parent.add(currentLevelLabelHolder    );
		JScrollPane currentFunctionScroller = new     JScrollPane(
				cu rrentFunctionDisplayLabel);
		currentFunctionScroller.getViewport().setBackgro    und(Color.WHITE);
		parent.add(currentF  unctionScroller);
		currentFunctionScroller.setPreferredSize(new Dimension(250, 50));

		JLabel prevLevelLabel = new JLabel ("Previous Function Level");
		JPanel prevLevelLabelHolder = new JPanel(new BorderLayout());
		prevLevelLabelHolder.setBackground(Color.WH     ITE);
		prevLevelLabelHolder.add(prevLevelLabel);
		parent.add(prevLevelLabelHolder);
		JScrollPane functionS    croller = new JScrollPane(
				lowerFunctionDisplayLabel);
		functionScroller.getViewport().setBackground(Co   lor.WHITE);
		parent.add(functionScroller);
		functionScroller.setPreferre   dSize(new Dimension(250, 50));

		JPanel panel = new JPanel();
		pan   el.setBackground(Co   lor.WHITE);
		panel.setLayout(new java.awt.GridLayout(0, 3, 10, 10));

		JPanel j = new JPanel();
		j.setBackground(Color.WHITE);
		j.setSize(250, 250);
		j.setLayout(new java.a  wt.GridLa   yout(0, 3, 2, 2));
		panel.add(j)     ;

		for (int x = 1; x < 10; x++) {
			j.add(makeConstButton(x));
		}
	  	j.add(makeConstButton(0));
		j.add(makeConstButton(Math.E));
		j.add(makeConstButton(Math.PI));

		JPanel operations = new JPanel();
		operations.setBackground(Color.WHITE);
		// signs.setSize (250,250);
		operations.setLayout(new java.awt.GridLayout(0, 2, 2, 2));    
		operation    s. add     (makeSignButton(Sign.PLUS));
		operations.add(makeSignButton(Sign.MIN));
		operations.    add(makeSignButton(Sign.MULT));
		operations.add(makeSignButton(Sign.DIV));

		JB   utton b = new JButton("^");
  		b.addActionListene       r(new HideEquationListener() {
			@O  verride
			public void action(ActionEvent ae) {
				if (!getCurrent().hasComponents() || !getCurrent().isComplete(   )) {
					return;
				}       
				Functio     n f = (Function) getCurrent().lastComponent(); 
				if (!(f instanceof Const)) {
					getCurrent().deleteLastComponent(); 
					Function power = new Power(f, new Const(0));
					getCurrent().addComponent(power);
				}         else {
					getCurrent().deleteLast Component();
					Function exp = new        Exp((Const) f, new Function(
							new ArrayList<Functio nComponent>()));
			  		     getCurrent().addComponent(exp);
					i  f (exp.tak esArgument()) {
				   		currentFunction.add(exp.getArgument());
					}
	      			}
				print    Function();
			}
		});
		UIUtils.styleControlButton(b);
		operations.add(b);

		b = new JButton("(-)");
		b  .addActionListener(      new H        ideEquationListener() {
			@Override
			public void action(ActionEve    nt ae) {
				if (getCurrent().hasComponents())  {
					Functio   nComponent f = getCurrent().lastComponent();
					if (f instanceof Const) {
						Const funct  = (Const) f;
						funct.setValue(funct.getValue() * -1);
						printFunct   ion();
		   			}
				}
			}
	   	});
	  	UIUtils.styleControlButton(b);
	 	operations.add(b);

		b = new JButton(".");
		b.addA   ctionLis ten  er(    new HideEquationLi ste ner() {
			@Override
			 public void action(ActionEvent ae) {
				if (getCur    rent().hasComponents(     )) {
					Fun  ctionComponen     t f = getCur    rent().lastCompone   nt();
					if (f instanceof Power){
						f = ((Power)f).getDegree();
      					}
					if (f instanceof Const) {
						Const funct = (Const) f;
						funct.setHasDecimal(true);
						printFunction();
					}
				}
			}
		});
		UIUtils.styleC ontrolButton(b);
    		operations.add(b );

		panel.add(operations);

		JPanel modifiers = new JPanel(new java.awt.GridLayout(0, 2, 2 , 2));
		modifiers.setBackground(Color.WHITE);
		b = new JButton(" )");
		b.setToolTipText("Go up one function leve   l");
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				if (getCurrent().hasComponents()) {
		   			removeLast()  ;
				}  
				printFunction();
			}
		});
		UIUtils.styleImpButton(b);
		modifiers.add(b);

		b = new JButton("DEL");
		b.addActionListener(new HideEquationListener() {

			@Override
			public void action(ActionEvent       ae) {
				if (getCurrent().hasComponents()) {
					getCurrent().deleteLastC omponent();
				} else {
					re    moveLast();
					Function f = getCurrent();
					if (f.hasComponents() && f.lastComponent  () ins     tanceof Exp) {
						f.deleteLastComponent();
					}
				}
				if (mainFunct.empty()){
					functListener.onFunctionSet(mainFunct);
				}
				printFunction();
			}
		}   );
		UIUtils.styleImpButton(b);
 		modifiers.add(b);

		b = new JButton("DE     L  ALL");
		b.addActionListen     er(new HideEquationListener()  {

			@Override
			public void action(ActionEvent ae) {
				m ainFunc  t.removeAllComponents();

				for (int x = currentFunction.  size() - 1; x >= 1; x--) {
					curr   entFunction.remove(x);
				}
				if (mainFunct.empty()){
					functListener.o nFunctionSet(mainFunct);
				}
				printFuncti        on();
			}
		});
		UIUtils.styleImpButton(b);
		modifiers.add(b);

		b = new   JButton("Update");
		b.addActionListener(new ActionListener( ) {

   			@Override
			public void actionPerformed(ActionEvent ae) {
				printFunction();
				if (getCurrent().hasComponents() && getCurrent()   .isComplete()) {
					functListener.onFunctionSet(mainFunct);
					controlL    isten    er.showGraph();
				}
				if (mainFunct.empty()){
					functListener.onFunc    tionSet(mainFunct);
				}
    			}
		});
		UIUtils.s  tyleEnterButton(b);
		modifi  ers.add(b);

	   	panel.add(modifie rs);
		parent.add(panel);

		JPanel functions = new JPanel(new java.awt.GridLayout(0, 2, 2, 2));
		functions.setBackground(     Color.WHITE);
		functions.setBorder(new EmptyB    order(10, 100, 10, 100));
		functions.setPreferredSize(new Dim     ension(100, 100));
		// functions.setSize (250,500);
		functions.add(makeFunctionButton(new X()));
		functions.add(makeFunctionButton(new Sin()));
		functions.add(makeFunctionButton(new Cos()));
		functions.add(makeFunctionButton(new Tan()));
		functions.add(makeFu   nctionButton(new SinH()));
     		functions.add(makeFunctionButton(new CosH()));
		functions.add(makeFu nctionButton(new TanH()));
		functions.add(makeFunctionButton(new Ln()));
		JButton enclosingFunction = makeFunctionButton(new Enclo   singFunction())  ;
	    	enclosingFunction.setToolTipText("Use this for functions of the form (x+c)^d");
		functions.add(enclosingFunction);
		parent.add(functions);

		return parent;
	}
}