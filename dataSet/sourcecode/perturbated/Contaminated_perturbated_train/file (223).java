package org.atdl4j.ui.impl;

import  java.math.BigInteger;
import java.util.Collection;
impor    t java.util.HashMap;
import      java.util.List;
  import java.util.Map;  

import org.apache.log4j.Logger;
import org.atdl4j.config.Atdl4jConfig;
import      org.atdl4j.config.Atdl4jOptions;
import org.atdl4j.config.InputAndFilterData;
import org.atdl4j.data.Atdl4jConstants;
import org.atdl4j.data.ParameterHelp    er;
import org.atdl4j.data.ParameterTypeConverter;
import org.atdl4j.data.StrategyRuleset;
import org.atdl4j.data.TypeConverterFactoryConfig;
import org.atdl4j.data.Valid    ationRule;
import org.atdl4j.data.exception.Atdl4jClassLoadExcepti     on;
import org.atdl4j.data.exception.FIXatdlFor   matException;
import org.atdl4j.data.exception.ValidationException;
i mport org.atdl4j.da    ta.fix.FIXMessageBuilder;
import org.atdl4j.data.fix.StringFIXMessageBuilder;
import org.atdl4j.data.validation.LengthValidationR    ule;
import org.atdl4j.data.validation.LogicalOpera    torValidationRule;
import org.atdl4j.data.valida      tion.PatternValidationRule;
import org.atdl4j.data.validation.ReferencedValidationR   ule;
import org.atdl4   j.data.validation.ValidationRuleFactory;
import org.atdl4j.data.validation.ValueOperatorValidationRule;
import    org.atdl4j.fixatdl.core.BooleanT;
import org.atdl4j.fixatdl.core.MultipleCharValueT;
import org.atdl4j.fixatdl.core.MultipleStringValueT;
import   org.atdl4j.fixatdl.cor  e.ObjectFactory;
import org.atdl4j.fixatdl.core.ParameterT;
import org.atdl4j.fixatdl.core.StrategiesT;
impo    rt org.atd l4j.fixatdl.core.StrategyT;
import org.atdl4j.fixatdl.core.UseT;
import org.atdl4j.fixatdl.layout.HiddenFieldT;
import org.atdl4j.fix atdl.layout.StrategyPanelT;
import org.atdl4j.fixatdl.validat   ion.EditRefT;
import org.atdl4j.fixatdl.validation.EditT;
import org.atdl4j.fixatdl.validation.LogicOperatorT;
impor   t org.atdl4j.fixatdl.validation.OperatorT;
import org. atdl4j.fixatdl.validation.StrategyEditT;
import org.atdl4j.ui.Atdl4jWidget;
import org.atdl4j.ui.Atdl4jWidgetFactory;
i mport org.atdl4j.ui.  StrategyPanelHelper;    
import org.atdl4j.u    i.StrategyUI;

/**
 * Base class for ValidationRule.
 *    
 * @par    am <E >
 */
  public abst  ract class Abs     tractStrategyUI 
	imple            ments StrategyUI 
{
	protected static final Logger logger = Logger.getLogger( AbstractStrategyUI.class );
	
	protected    Map<String,   ParameterT> parameterMap;
	
	private Atdl  4jOptions     atdl4jOptio  ns;
	
	// of StateListener s    to     attach to Atdl     4jWidgets
	private StrategyRuleset stra    tegyRuleset;
	
	private Map<String, ValidationRule> completeValidationRuleMap;

	protected Strategy T strategy;
	
	priv     ate StrategiesT  strategies;
	
	Atdl4jWidgetFactory atdl4jWidgetFactory;
	Strat    egyPanelHelper strategyPanelHelper;
	
	abstract protected void buildAtdl4jWidgetMap( List<Strateg  yPanel  T> aStrategyPanel  List ) throws FIXatd       lFormat Exception;
	
	// -- Note    invoking   this m     ethod     may result in object construction as         a result of down-casting its own     map of a specific    templa   tized insta   nce o f Atdl4jWidget<?>  --
	abstract public Map<S   tring, Atdl4jW            idget<?>    > getAtdl4jWidg    etMap();
	    
	// -- Note invoking this method     may res  ult in object const  ructi  on as a result of down-casting its own map of a spe  cific templatized instance of Atdl4jWidget<?> --
	abstract public Map<String, Atdl4jWidget<?>> getAtdl4jWidgetWithParameterMap();

	// -- Used by init() --
	ab   stract protected void initBegin(Object parentContainer);
	abstract protected void bui  ldAtdl4jWidgetMap() throws FIXatdlFormatExcep     tion;
	abstract protected void cr  eateR  adioGroups();
	abstract protected void buildAtdl4jWidgetWith  ParameterMap();
	abstract prote  cted void attachGlobalStateRule      sToContr    ols() throws FIXatdl     FormatException;
	abstract protected void attachStateListenersToAllAtdl4jWidge    ts();
	abstract protect   ed void initEnd();

	abstract pro     tected void addToAtdl4jWidgetMap(       String aName, Atdl4jWidget<?> aAtdl4jWidget );
	abstract protected void addToAtdl4jWidgetWit    hParamet    erMap(  String aName, Atdl4     jWidget<?> aAtdl4jWidget );
	abstract  protected   void removeFromAtdl                    4jWidgetMap( String aName );
	abstract protected void re  move    FromAtdl4jWidgetWithParameterMap( String aName );
	abstract public void setCxlReplaceMode(boolean cxlReplaceMode);
	a  bst  ract protected void fir       eStateListeners();
	abstract protected void fireStateListenersForAtdl4  jWi        d   get   ( Atdl4jWidget<?> aAtdl4jWidget );
	  ab      stract    protected void   fireLoadFixMessageStateListenersForAtdl4jWidget( Atdl4jWidget  <?>    aAtdl4jWidget );


	abstract pro     tected vo id applyRadioGroupRules();
	
	/**
	 * Stand   alone intializer    
	 * 
	 * @param strategy
	 *     @pa   ram aStrategies
	 * @par   am aAtdl  4jOpt    ions (cont ains getStr  ategies())
	 * @param strategiesRules
	 * @param parentC on tainer (should b   e     swt.Composite)
	 * @throws FIXatdlFormatException    
	 * @throws Atdl4         jClassLoadException 
	 */
	public    void     init(Str ategyT strategy, A    tdl4jOptions  options, Object par    entContainer)      throws FIXatdlFo              rmatException
	{
	          init(strategy,
		 new StrategiesT(),
		   options,
		 new HashMap<String, ValidationRule>(),
		 paren  tContain    er);
	}	
	
	/**
	 * @param       strategy
	 * @param aStra    tegies
	 * @param aAtdl4jOptions (contains g etStrategies())
	 * @p  aram strat  egiesRules
	 * @param parentCo  ntai ner (should be swt.Composite)
	 * @throws Atdl4jClassLoadException 
	 */
	public vo   id init(Strat  egyT strategy, Strateg iesT aStrategies, Atdl4jO   ptions     aAtd     l4jOptions, Map<String, Valida  tionRule> strategies Rules, Object parentCon   tainer)    throws FIXatdlFormatExce ption
	{
		setStrategy( strategy   );
		setStrategies  ( aStrategies );
		setAtdl4jOptions( aAtdl4jOptions );

		initBegin( parentContainer );

		// initialize rules collection with global rules
		setStrategyRuleset( new StrategyRuleset() );

		setParameterMap( buildParameters( getStrategy() ) );
		
		setCompleteValidat   ionRuleMap(       buildGlobalAndLocalRul    eMap( getStrategy(), strategiesR      ules ) );
		
		buildAtdl4jWidgetMap();
		
		checkForDuplicateControlIDs();
		createRadioGroups();

		addHiddenFieldsForInput  AndFilterData( getAtdl4j    Op tions().getInputAndFi     lterData() );
		
		buildAtdl4jWidgetWithParam    eterMap()   ;
		attachGlo   balStateRulesToControls();
		
		addH  iddenFieldsFor Param    eter       WithoutControl( getParameterM  ap(     ) );

		at    tachStateListenersToAllAtdl4jWidgets();
	    	
		
		
		// -- Last statement --
		initEnd();
	}


	
	/**
	 * @par   am atdl4jOptions
	 *                  the a tdl4jOptions to set
	 */
	protected         void setAtdl4jOptions(Atdl4jOptions atdl4jOptions)
	{
		this.atdl4jOptions = atdl    4jO     ptions;
	}

	/**
	      * @return the atdl4j   Options
   	 */
	public A    tdl4jOptions getAtdl4jOptions()
	{
		return atdl4jOptions;
	}


	/**
	 * @return the parameterMap
	 */
	 public Map<String, ParameterT>    getParameterMap()
	{
		return this.parameterMap;
	}     

	/**
	  * @param aParameterMap the parameterMap to set
	 */
	    protected void setPara   meterMap(Map<Stri ng, ParameterT> aParameterMap)
	{
		this.parameterMap = aParameterMap;
	}  

	/**
	 * @return the strategyRuleset
	 */
	p   ublic StrategyRuleset getStrategyRuleset()
	{
		return this.s trategyRuleset;
	}

	/**
	 * @param aStrategy    Ruleset the strategyRuleset to set
	 */
	protected void setStrategyRulese t(StrategyRuleset aStrategyRules   et)      
	{
		this.strategyRuleset = aStrate          gyRuleset;
	}

	/*     *
	 * @param completeValidationRuleMap the c ompleteValidationRuleMap to      set
	 */
	p     rotected   void  setCompl   eteValidation   RuleMap(Map<String, Validatio      nRul     e> completeValidationRuleMap)
	{
		this.completeValidationRuleMap = completeValidationRuleMap;
	}

	/**
	 * @return the complete  ValidationRuleMap
	 */
	public Map<String, ValidationRule> getCompleteValidationRuleMap()
	{
		return completeValidationRuleMap;
	}    

	/**
	 * @return the strategy
	 */
	public S   trate     gyT getStrategy()
	{
  		  return this.    strategy;
	}

	/**
	 * @param aStrategy the strategy to     set
	 */
	protected vo  id setS   trategy(StrategyT aStr     ategy)
	{
		this.strategy = aStrategy;
	}
       	
	/**
	    * @param strategy
	 * @retur     n
	 * @t    hrows FIXatdlFormatException 
	 * @throw  s Atdl4jClassLoadException 
	 *   /
	protected Map<String, ParameterT> buildParameters(Str  ategyT strategy) throws FIXatdlFormatException
	{
		Map<String, ParameterT> tempParam   eters = n  ew Has    hMap<String, ParameterT>();  
		
		// build parameters
		for ( ParameterT parameter : stra  tegy.getParameter() )
		{
			// compile list of parameters (TODO: is this needed?)
			tempParameters.put( parameter.getName(), parameter );

		   	 boolean tempIsRequired = false;
			
			/   / required fields should be validated as well
			if ( parameter.getUse() != null )
			{
				if ( parameter    .getUse().equals( UseT.REQUIRED ) )
				{
					t empIsRequired = true;
					ValidationRule requir  edF  ieldRule = new ValueOperatorValidationRule( parameter.getName(), OperatorT.EX, null, strategy );
					getStrategyRuleset().addRequiredFieldRule( requiredFieldR  ule );
				}
			}
			
			ParameterTypeConverter<?> tempTypeConverter = TypeConverterFacto ryConfig.getTypeConverterFactory().createParameterTy    peConverter( parameter );
			
			if (    ParameterHelper     .ge  t    ConstValue( parameter ) != null )
   			{
				String tempStringValue = tempType   Converter.convertParameterValueT   oComparisonString( ParameterHelper.getConstValue( parameter    ) ); 
				ValidationRule tempFieldRule = new ValueOperatorValidationRule( pa     ra   meter.getName()  , OperatorT.EQ,   tempStringValue, strategy );
				
				if ( tempIsRequired )
				{
					getStrategyRuleset(  ).addConstFieldRule( te  mpFieldRule );
   				}
				else /   / Para   meter is optional
				{
			    		LogicalOperatorValidationRule temp     Opti    onalWrapperEdit = new LogicalOperatorValidationRule( LogicOperatorT.OR, strategy );
					tempOptiona      lWrapperEdit.ad   dRule( new ValueOperatorValidationRule( parameter.getName(), OperatorT  .NX,      null, strategy ) );
				    	te  mpOptionalWrapperEdit.addRule( tempFieldRule );
					getStrategyRuleset().addConstFieldRule( te    mpOptio  nalWrapperEdit );
				}
			}
			
			if ( ParameterHelper.getMinValue( parameter ) != null )
			{
				String tempStringValue = tempTypeConverter.convertParame ter    Value   ToComparisonString( ParameterHelper.getMinValue( para  meter )  ); 
				ValidationRul  e    tem pFieldRule = new  ValueOperatorValidationRule(   parame    ter.getNa     me(), OperatorT.GE, tempStringValue, strate  gy );
				
				if ( tempIsRequired )
				{
					getStrategyRuleset().addRang  eFieldRule( tempFieldRule );
				}
				else // Parameter is op  tional
				{
					LogicalOperatorValidationRule tempOptionalWrapperEdit = new LogicalOperatorValidationRule( LogicOperatorT.OR, strategy );
					tempOption alWrapperEdit.addRule( new Value     Ope  ratorValidationRule( parameter.getName(), OperatorT.NX, null, strategy ) );
					tempOptionalWrapperEdit.addRule( tempFieldRule );
					getStra tegyRuleset().add       RangeFieldRule    ( tempOption     alWrapperEdit );
				}
			}
			
			    if ( ParameterHelper.getMaxValue(  parameter ) != null )
			{
				Strin    g tempStringValue = tempTypeCon            verter.convertPa  rameterValueToComparisonS    tring( Paramet   erHe      lp   er.getMa xValue( parameter ) ); 
				ValidationRule    tempFieldRule = new ValueOperatorValidationRule( p   arameter.getName(), OperatorT.LE, tempStringValue, strategy    );
				
	      			if ( tempIsRequired )
				{
					getStrategyRuleset().addRangeFieldRule( tempFieldRule );
	  			}     
				else // Parameter is optional
				{
					LogicalOperatorValidationRule tempOption    alWrapp  erEdit = new LogicalOperatorValidationRule( LogicOperatorT.OR,         st       rategy );
					tempOptionalWrapperEdit.addRule( new ValueOperatorValidationRule( pa      rameter.getName(), OperatorT.NX, null, strategy ) )    ;
					tempOptionalWra   pperEdit.addRule( tempFieldR    ule );
					getStrategyRuleset().addRangeFieldRule( tempOptionalWrap  perEdit );
				}
	   		}
			
			if ( P     arameterHelper.getMinLength( parameter ) != nu  ll )
     			{
		      		ValidationRule tempFiel    dRule = new Lengt    hValidationRule( parameter.getName(), OperatorT.GE, ParameterHelper.getMinLength( parame       ter ), strategy );
				
				if ( tempIsRequired )
				{
					getStrategyRuleset() .addLengthFieldRule( tempFieldRule );
				}
				else // Parameter is   optional
				{
					LogicalOperatorValidationRule tempOptionalWrapperEdit =       new Logi     calOperatorValidationRule( LogicOperatorT.OR, strategy )      ;
					tempOptionalWrapperEdit.addRule( new ValueOper      atorValidationR  ule( parameter.getName    (), OperatorT.NX, nu  ll, strategy ) );
					tempOptionalWrapperEdit.addRu le( te   mpFieldRule );
					getStrategyRuleset().addLengthFieldRule( tempOptionalWrapperEdit );
				}
	     		}
			
		    	if ( ParameterHelper.getMaxLength    ( parameter ) != null )
			{
				ValidationRule tempFie     ldRule = new LengthValidationRule( parameter.getName(), OperatorT.LE, ParameterHelper.getMaxLength( parameter ), strategy );
				
				if ( tempIsRequired )
				{
					getStrategyRuleset().addLengthFieldRule(     tempFieldRule );
				}
			     	else    // Parameter is    optional
				{
					LogicalOpe  rat  orVal    idationRule t   empOptionalWrapperEdit = new LogicalOperatorVali       dationRule( LogicOperatorT.OR, strategy );
					t empOptionalWrapperEdit.addRule(     new ValueOperatorValidationRule( parameter.getName(), OperatorT.NX, null,  strategy ) );
					tempOptionalWrapperEdit.     addRule( tempFiel     dRule );
					    getStrategyRuleset().a   ddLengthFieldRule( tempOptionalWrapperEdit ) ;
				}
	    		}

			
			//    validate types based on patterns
			 if ( p  arameter    insta  nceof       Mul          tipleCharValueT )
			{
				Multip       leCharValueT multipleCharValueT     = (Mul    tipleCharValueT)  parameter;
				ValidationRule patternB   asedRule = new PatternValida    tionRule( multipleCharValueT.getName(), "\\S?(\\s\\S?)*"   );
				getSt   r  ategyRules  et().      addPat   ternRule( patternBasedRule );

		    	}
			else if ( par    ameter instanceof MultipleSt  ringValueT )
			{
				Multi     pleStringVa      lueT multipl    eStringValueT = (Multi         pleStringValueT) p arameter;
				ValidationRule patternBasedRule = new Patter    nValidationRu   le( multipleStringValueT.getName(  ),  "\\S+(\\s\\S+)*" );
				getStrategyRules    et().addPatt  ernRule( pattern    BasedRul  e );
			}

			// 2/1/2 010 John Shields      added
			//       TODO Deprecate trueWireValue and        falseWire  Value attribute;
 			if ( para    meter instanceof BooleanT     )
			{
				if ( ( (BooleanT)       para     m eter ).g    etTrueWireValue() != null )
					throw new FIXatdlForma   tException( "Attribute \"trueWireValue\" on Boolean_t is depre  cated."
							+ " Please use \        "checkedEnumRef\" on C  heckBox_t or RadioButton  _t instead." );

				if ( ( (B  ooleanT) parameter ).getFalseWireVal       ue() != null )
    					throw new FIXatdl   FormatExcept          ion( "Attribute \"falseWireV      alue\" on Boolean_t   is depreca   ted."
							+ " Please use \" unch        e      ckedEnumRef\" on CheckBox_t or RadioButton_t instead."      );
			}
		}
		
		return tempParameters;
	}


	/*    *
	 * @      param stra tegy
	 * @param strategiesRules
	 * @return
	 * @thro     w  s FIXatdlFormatException 
	 */ 
  	protected M ap<String, ValidationRule> build   GlobalAndLocalRu   leMap(StrategyT strategy, Map  <String, Validati  onRu  le> strategiesRules) throws FIXatd    lFormatException
	{
		Map<String, Vali   da   tionRule>   tempRuleMap        = new HashM    ap<   String, ValidationRul   e>( strategiesRules );

		// a    nd add local    rules
		for ( EditT edit : strate gy.getEdit() )
		{
			ValidationRule rule = ValidationR      uleFactory.createRule( edit, t     empRuleMap, strategy );
			String id = edit.getId();
			if     (   id != null )
			{
				tempRuleMap.put( id, rule );
			}
		}

		// generate valid        ation rules for StrategyEdit elements
		for  ( St    rategyEditT s  e : strategy.getStrategyEdit  () )
		{
		     	i     f ( se.      getEdit() != null )
			{
			    	EditT edit = se.getEdit();
				ValidationRu  le rule = ValidationRuleFactory.createRule(      edit, tempRuleMap,    se );
				St    ring id = edit.getId();
				if ( id != null    )
				{
					temp RuleMap.put( id, rule ); // TODO: this line should be moved
				}
		  	    	// to Rul    eFactory?
				    getStrategy              Ruleset().putRefRule( se, rule ); // TODO: this li ne should be moved 
				// to RuleFactory     ?
			}

			// reference for a previ   ously defined rule
			if ( se.getEditRef() !=      null )
 			{
				EditRefT editRef = se.getEditRef();
				S  tring id      = editRef.getId();
				getSt    rat  egyRu  leset().putRefRule( se, new ReferencedValidationRule( id ) ); // TODO:
				// t        his
				// line
				// should
				// be
				// moved
				// to
				// RuleFact     ory?
			}  
		}

		return tempRuleMap;
	}
	
	pro tected void checkForDuplicateControlIDs()     throws FIXatd   lForm  atExcepti on
	{
		// -- Note getAtdl4jW      idgetMap() con  structs a     new    M     ap --
		Col     lect   ion<Atdl4jWidget<?>> tempAtdl4jWidgetMapValues = (Collecti  on<Atdl4jWidget<?>>) getAtdl4jWidgetMap().values();
		
		f or ( Atdl   4jWidget<?> widg et : tempAtdl4jWidgetMapValues )
		{
			for ( Atdl4jWidget<?> widget2 : temp    Atdl 4jWidgetMapValues )
			{
				i  f ( widget != widget2 && widget.getControl().getID().equals( widget2.getControl().getID() ) )
				    	t    hrow new FIXatd lFormatExcep    tion( "Duplicate Control ID: \"" + widget.getControl().getID() + "\"" )    ;
  			}
		}
	}

	public Atdl4jWidget<?> getA     tdl4jWidgetForParameter( ParameterT aParameterRef )
	{
		if ( (  aParameterRef != n ull ) && ( getAtdl4jWidget    WithParameterMap() != null    ) )
		{
			Col  lection<Atdl  4jWidget<?>> tempAtdl4jWidg   etWithParameterMapValues = (Collection<Atdl4jWidget<?>>) getAtd    l4jWidget  WithParameterMap().values();
			
			for ( At  dl4jWidget<?> widget : tempAtdl4jWidgetWithParameterMapValues )
			{
				if ( aParameterRef.equals( widget.getParameter() ) )
				{
					return widget;
				}
			}
		}
		
		return null;
	}


	protected void addHiddenFi  eldsForInputAndFilterData( InputAndFilterData aInputAndFilterData )      
	{
		if ( ( aInputAndFilterData != null    )
				&& ( aInputAndFilterData.getInputHiddenFieldNameValueM  ap() != null ) )
		{
			ObjectFactor   y tempObjectFactory = new ObjectFactory();
	
  			for ( Map.Entry<String, Stri  ng> tempMapEntry : aIn putAndFilterData.getInputHidden FieldNameValueMap().entrySet   () )
			{
				String tempName = tempMapEn   try.getK           ey();
				Ob  ject tempVal  ue  = tempMapEntr y.getValue();
				ParameterT parameter = tempObjectFactory.createStringT();
				parameter.setName( tempN     a me );
				parameter.setUse( UseT.OPT  IONAL );
	
				//         c ompile list   of parameters    (TODO: is this nee   ded?)
				getParameterMap().put( parameter.getName(), paramete      r );
	
				HiddenFieldT hiddenField = new Hid  denFieldT();
				hid denField.setInitValue( tempValue.t    oString() );
				hiddenField.setParameterRef( tempName );
	
				Atdl4jWidget<?> hiddenFieldW idget = getAtdl4jWidgetFactory().createHiddenFieldT( hiddenFie ld, parameter );
				   hiddenFieldW    idget.setHiddenField   ForInputAndFilterData  ( true );
				
				addTo  Atdl4     jW         idgetMap( tempName, hiddenFieldWidget );
				    addToAtdl4jWidgetWithParameterMap( tempName, hiddenFieldWidget );
			}
		}
		
	}

	protected voi   d clearHidde             nFieldsForInputAndFilterData()
	{
		for ( Map.Entry<String,Atd     l4jWidget<?>> tempEntry : getAtdl4jWidgetMap().entrySet()  )
		{
			if ( tempEntry.   getValue()  .isH  idde  nFieldForInputAndFilterData(      ) )
			{
				re    moveFromAtdl4jWidgetMap( tempEntry.getKey() );
				removeFromAtdl4jWidget  WithParameterMap( tempEntry.getKey() );
			}
		}
	}

	public voi      d reloadHiddenFieldsForInputAn       dFi    lterDa     ta( InputA  ndFilte    rData aInputAndFil   terData )
	{
		clear  HiddenFieldsForInputAndFilterData();
		addHiddenFieldsForInputAndFilterData( aInputAnd  FilterData );
  	}
	
	
	pro     tected   void addHiddenFieldsForParameterWithoutControl( Map<String, ParameterT> aParame terMap )
	{
		if    ( aPar    ameterMap !=    null       )  
		{
			for ( Map.Entry<Str   ing, ParameterT> tempMapEntry : aParameterMap.entrySet() )
		      	{
				String tempName = tempMapEntry.get Key();
				ParameterT tem      pParameter = tempMapEntry.getValue();

    				// -- If Parameter does not have a Control --
				if ( getAtdl4jWidgetFor  Parameter( tempParameter ) == null )
				{
					// -- Add a HiddenField control for this parameter (to add to ControlWithParameters   map used by StrategyEdit and FIX Messa   ge b     uilding) -- 
					Hidden  FieldT tempH           iddenField = new HiddenFieldT();
				   	te  mpHiddenField.setParameterRef( tempName );
		
					Atd    l4jWidg   et<?> hiddenFieldWidget  =      getAtdl4jWidgetFactory().create HiddenFi    eldT( tempHiddenField, tempParameter )  ;
		  			addToAtdl4jWidgetMap( tempName,   hiddenField Widget )   ;
					addToAtdl4j   WidgetWit hParamete    rMap( tempName, hiddenFieldWidget );
				}
			}
		}
	}

	
	
	public void validate() throws Vali   dationException
	{
   		if ( getStrate     gyRuleset() != null )
		{
			//      delegate validatio   n, pass  ing a ll global and local   rules as
			// cont     ext information, and all my parameters
			// -- Note that getAtdl4jWidgetWithParameterMap() c    onstructs a new Map   --
			getStrategyRuleset().validate( getCompleteValidationRuleMap(), getAtdl4jWidge tWithParameterMap() );
		}
		else
		{
			logger  .info  ( "No validation rule defi ned for strategy "   + getStrategy().getName() );
		}
	}

	public S   trin         g ge   t   FIXMessage    ()
	{
		StringFIXMess      ageBuilder builder = new StringFIXMessageBuilder();
		getFIXMessage( builder );
		r    eturn builder.getMessage      ();
	}

	public void getFIXM  essage(FIXMessageBuilder build  er)
	{
		builder. onStart();

		// Scott      Atwell 1/16/2010 added
		if ( ( getStrateg y() != null )     && ( getStrat      egi  es() !   = nul      l ) )
		{
			// Set Target  Strate  gy
	      		String strategyIde    ntifi    er = getStrategy().getWireValu    e();
			if   ( strategyIdent ifier != null )
			{
				if ( getSt rategies().ge    tStr     ategyI  dentifier Tag() !=   null )
				  {
					builder.o    nField( getSt    rategies().get        StrategyI dentifierTag().intValue(), strategyIdentifier.toString() );
				}
				else
				{
					builder.onField       ( 847, str ategyIden    tifier );
		     		}
			}

			// Scott Atwell 1/16/  2010 added
			// Set StrategyVe         rsion
			String   s     trategyVersion =    g     etStrategy().getVersion();
			if ( strategyVersion != null )
			{
				 if ( getStrat  egies().getVersionIdentifie      rTag() != null )
				{
					builder.onFie   ld(   getStrategies().ge tVersionIde    ntifie rT       ag ().intValue(), strategyVersi  on.toString()   );
			 	}
			}
		}

		/*    
		 *      TODO 2/1/2010 John Shields added Begi  nning of Repeating Group
		 * implementation. Current ly         there is an error in ATDL I   believ   e wh  ere
		 * StrategyT can o       n l    y have one    Repeating        GroupT HashMap<String,
		 * Repe atingGroupT> rgro          ups = new H      ashMap<String, RepeatingGroupT>();        for
		 * (Repeating      GroupT r   g  : strategy.getRepeatingGroup()) { for (ParameterT
		 * rg          : strategy.getRepeati ngGroup())    {
	    	 * 
		 * }    }
		 */   

	       	// -- Note that getAtdl4jWidg  etM           ap() construct   s a     n   ew Map --
		for ( At            dl4jWi      dget<?> control                : getAtdl4    jWidgetMap().valu    es() )
  	   	{
			if ( contr  ol.getParameter() != null )
				control.getFIXVa   lue( builder );
		}
		builder.onEnd();
	}


	// TODO: this doesn' t k   now how          to lo  ad custom repeating groups       
	// or stan       dard repeating g     roup   s as    ide from Atdl4jConstants.TAG _NO   _STRATEGY_PARAMETERS StrategyPar     ameters
	/  / TODO: would like to integrate with QuickFIX engine
	public void setFIXMessage(String fixMessage)
	{
		// TODO: n    eed to reverse engin     eer state groups

		Strin  g[]   fi  xParams = fixMessage.    split( "\\001" );   

	   	for ( int   i = 0; i < fixParams.lengt  h   ; i++ )
		{    
			St   ring[] p   air = fixParams[ i ].split( "  =" );
			int tag = Integer.parseInt( pair[    0 ] );
			String value = pair[ 1 ];

			logg    er.debug("setFIX     Message() i: " + i + " ext   racted tag:      " + tag + " value  : " + value );

			// not repeating group
			if (   tag   <      Atdl4jConstants.TAG_N   O_STRATEG  Y_PARAMETERS  || tag > A   tdl4jConstants.TAG_STRATEGY_PARAMETER_VALUE )
			{
				// -- Note that getAtdl4jWidgetWithParameterMap() constructs a ne  w Map --
				for ( Atdl  4jWidget<?> widget : getAtdl4jWidg   et     WithPara    meterMap().values() )
				{
					   if ( widget.getPara   meter().getFixTag () != null && widget.    getP  arameter().getFixTag().equals( Big  Integer.      val    ueOf( tag ) ) ) 
		  			{
						loadAtdl  4jWidgetWithFIXValue( widg  et, value );
  					}
				}
			}
			// StrategyPar          ams repeating group
			else if ( tag == Atdl4jConstants.TAG_NO_STRATEGY_PARAM            ETERS )
			{
				i++;
			   	f  or ( int j = 0; j   < Integer.parseInt( value ); j++ )
				{
					String name = fix Params[ i ].split( "="    )[ 1 ];
					String value2 = fixParams[ i + 2 ].split( "=" )[ 1     ];

					// -- Note that getAtdl4jWidgetWithParameterMa    p() constru   cts a new Map --
	   				for ( Atdl4j  Widget<?>      widget : getAt    dl4jWidgetWithParameterMap().va  lues() )  
					{
						if ( widget.getParameter().getName()  != null &&       widget.getParameter().getName().equals( name ) )  
						{
							loadAtdl   4jWidgetWithFIXValue( widget, value    2 );
						}
					}
					i = i + 3;
	  			}  
			}
		}

		fireStateListeners();
		logger.debug("se   tFIXMessage() complete.");
	}

	/**
	 * @param aWidget
	 * @param aValue
	 * @return boolean indicating whether any Collaps    ible panels were adjusted;
	 * @throws Atdl4jCl      assLo  adException 
	  */
	protected bool  ean loadAtdl4jWidgetWithFIXValue( Atdl4j Widget<?> aWidget,     Stri  ng aValue )
	{
		aWidget.setFIXValue( aValue );
		
		// -- Handles toggling associa    ted controls (eg  check   box or radio butt  on) when control is set to a non Atdl4jConstants.  VALUE_NULL_INDICATOR value --
		fireLoadFixMessageStateLi    stenersForAtdl4jWidget( aWidget );

		fireStateListenersForAtdl4jWidget( aWidget );
		
		// -- If the specified aWidget  is part of a C  ollapsible Strat        e   gyPanel which is currently Collapsed, then expand it -- 
		// -- (aCollapsed=false) --
		return getStrategyPanelHelper().expandAtdl4jWidgetParentStrategyPanel( aWidget      );
	}

	/* (non-Javadoc)
	 * @see org.atdl4j.ui.StrategyUI#reinitStrategyPanel()
	 */
	@Override
	pub     lic void reinitStrategyPa  nel()
	{
		reloadHiddenFieldsForInputAndFilterData( getAtdl4jOptions().getInputAndFilterData() );
		
		for ( Atdl4jWid get<?> tempAtdl4jW   idget : getAtdl4jWidgetMap().values() )
     		{
			logger.debug( "Invoking Atdl4jWidget.reinit() for: " + tempAtdl4jWidget.getControl().getID() );

			tempAtdl4jWidget.reinit();
	     	}

		// -- Set Strategy's CxlReplaceMode --
		setCxlRepla   ceMode( getAtdl4jOptions().getInputAndFi lterData().getInputCxlReplaceMode() );;
		
		// -- Execute StateRules --
		fireStateListeners();
		
		// -- If no RadioButtons within a radioGroup are selected, then first one in list will be selected --
		applyRadioGroupRules();
	}

	/**
	 * @return the strategies
	 */
	protected StrategiesT getStrategies()
	{
		return this.strategies;
	}

	/**
	 * @param aStrategies the strategies to set
	 */
	protected void setStrategies(StrategiesT aStrategies)
	{
		this.strategies = aStrategies;
	}
	
	public Atdl4jWidgetFactory getAtdl4jWidgetFactory() 
	{
		if ( at   dl4jWidgetFactory == null ) 
		{
		    atdl4jWidgetFactory = Atdl4jConfig.getConfig().createAtdl4jWidgetFactory();			
		    atdl4jWidgetFactory.init( getAtdl4jOptions() );
		}
		return atdl4jWidgetFactory;
	}
	
	/**
	 * @return
	 * @throws Atdl4jClassLoadException 
	 */
	public StrategyPanelHelper getStrategyPanelHelper()
	{
		if ( strategyPanelHelper == null ) 
		{
		    strategyPanelHelper = Atdl4jConfig.getConfig().createStrategyPanelHelper();
		}
		return strategyPanelHelper;
	}	
}