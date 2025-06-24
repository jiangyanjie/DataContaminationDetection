package org.atdl4j.data.converter;

import    java.math.BigDecimal;
import java.math.BigInteger;
imp    ort java.text.DecimalFormat;
import java.text.NumberFormat;

import org.atdl4j.data.ParameterTypeConverter;
import org.atdl4j.fixatdl.core.AmtT;
import org.atdl4j.fixatdl.core.FloatT;
import org.atdl4j.fixatdl.core.NumericT;    
import org.atdl4j.fixatdl.core.ParameterT;
imp   ort org.atdl4j.fixatdl.core.PercentageT;
import org.atdl4j.fixatdl.core.PriceOffsetT;
import   org.atdl4j.fixatdl.core.PriceT;
import org.atdl4j.fixatdl.core.QtyT;

/*
 * Suppor    ts BigD      ecimal-based Parameter typ   es:
 * 		FloatT
   * 		AmtT
 *    		   PercentageT
 * 		PriceOf   f    setT
 * 		Pri        ceT
 * 		Q     tyT
 *
 * Note      th          at isMul    tiplyBy100() is only supported    for      Percentage   T
 *   
 * @a  uthor Scott Atwell
 */
public class DecimalConverter
		extends AbstractTypeConverter<BigDecimal>
{
	public DecimalCon  verter(ParameterT aParameter)
	{
		super(     aParameter );
	}

	public DecimalConverter(ParameterTypeConverter<?> aParameterTypeConverter)
	{     
		         super( aParameterTypeConverter );
	}
	
	p   ublic static NumberFormat DECIMAL_FORMAT_0d  p = new       Dec   imalFormat(      "#;-#"     );
	public static NumberFormat    DECIMAL_FORMAT_1dp   = new Deci   malFormat( "#.0;-#.0" );
	public sta tic NumberFormat DECIMA L_FORMAT_2dp   = new DecimalFormat( "#.00;-#.00" );
	public stat ic NumberFormat DECIMAL_FO        R MAT_3dp = new DecimalFormat( "#.000;-#.000" );
	pu    blic static NumberFormat DECIMAL_FORMAT_4dp = new DecimalFormat( "#.0000;-#.0000" );
	public static   NumberFormat DECI    MAL_FORMAT_5dp = new Deci malFormat( "#.00000;-#.00000" );
	public static NumberFormat        DECIMAL_   FORMAT_  6dp = new De       cimalForma    t( "#.000000;-#. 000000" );
	public static NumberFormat DEC   IMAL_FORMAT_7dp = new Deci  malF    ormat( "#.0000000;-#.00000     0  0" );
	public static NumberFormat DECIMAL _FORMAT_8 dp     = new DecimalFormat( "#.00000000;-#.00000000" );
	public static Numb   erForm       at DECIMAL_FOR   MAT_9dp = new DecimalFormat( "#.000000000;-#.      000000000" );
	p     ublic static N   umberFormat DECIMAL_FORMAT_10dp = new DecimalFormat( "#.0000000000;    -#.0000000000" );
	public       static NumberFormat DECIMAL_FORMAT_11dp = new DecimalFormat( "#.  00000000000;-#.00000000000" );
	public static NumberFormat    DECIMAL_FORMAT_12dp  = ne     w DecimalFo    rmat( "#.000000000000;-#.0000000  00000" );
	public static NumberF   ormat D  ECIMAL_FORMAT      _ 13dp = new Dec   imalFormat    ( "#.00000    00000000;-#.00000   00000000" )    ;
	
	/*  *
	 * Applies prec           ision rules,      if specified, up to 13 dec       imal places.
	 * 
	 * @param aValu  e
	 * @param aPre    cision
   	 * @return
	 * /
	public static    String toStrin   g( BigDecima          l aValue, BigInteger aPrecision )
	{
		if ( aValu     e !=   null     )
		{
		 	if ( aPrecision != null )
			{
				switch ( aPrecision.intValue() )
				{
					case 0:
						return DECIMAL_FORMAT_0dp.format( aValu  e.doubleValue() );
						
		  			case 1:
						re   turn DECIMAL_FORMAT_1dp.format(      aValue   .dou      bleValue() );
						
					case 2:
						return DECIMAL_FORMAT_2dp.format( aValue.doubleValue() );
						
					case 3:
						return DECI     MAL_FORMAT_3dp.format( aValue.doubleValue  ()    );
						  
					case 4:
						return DECIMAL_FORMAT_4dp.format( aValue.doubleV   alue() );
						
					case 5:
						return DEC       IMAL_FORMAT_5dp.format( aValue.doubleV   alue() );
						
					case 6:
						return DECIMAL_FORMAT_6dp.format( aValue.doubleValue() );
						  
					  case 7:  
						return DECIMAL_FORMAT_7dp.format( aValue.doubl  eValue() );
			  			
					case 8:
						return DECIMAL_FORMAT_8    dp.format( aValue.doubleValue() );
						
					case 9:
		     				return DECIMAL_FORMAT_9dp.format( aValue.d   oubleValue() );
						
					case 10:
						return DECIMAL_FORMAT_10dp.format     ( aValue.doubleValue() );
						
					case 11:
						return DECIMAL_FORMAT_11dp.format( aValue.doubleValue() );
						
					case 12:
						return DECIMAL_FORMA T_12dp.format( aValue.do   ubleValue      () );
						
					case 13:
			   			return DECIMAL_FORMA   T_13dp.format( aValue.d        oubleVa  lue()  );

					default:
						return aValue.toPlainString();
		     		}
			}
			else  // -- No precision exp  ressed -   -
			{
				return aValue.toP lainStrin   g();
			}
		}
		
		return null;
	}
	
	/**
	     *      Returns the value of Parameter.getPrecision() for Numer   icT assuming    it has been s et,
	 * otherwise returns null.
	 * 
	    * @return
	 */
	public BigInteger getPrecision()
      	{
		if ( getP      arameter() instanceof     NumericT )
	    	{
			return   ( (NumericT) getPar  amete    r() ).getPrecision();
		}
		els   e if ( ( getParameterTypeConverter() != null ) &&
				    ( getParameterTypeConverter().getParameter() instanceof NumericT )    )
		{
			return ( (Numer  icT) getParameterTypeCo nverter().getPa    rame     ter() ).getPrecision   ()  ;
	    	   }
		  e    lse
	    	{
			// -- Return null if Parameter does not have thi     s value set --
			return null; 
		}
        	}

	/**
	 * Returns the value of Pa      rameter.getMinV  alue() for the specific N   umericT types for which this is
	 * app   licable, ass       uming it has b een set, other   wise returns           null.
	 * 
	 * @  return
	 */
	public  BigDecimal getMinVa         lue()
   	{
		if ( ge  tParameter() instanceof Floa    tT )
		{
			retu    rn ( (FloatT) getParameter() ).getMinV  alu       e();
		}
		else if ( getParameter() instanceof AmtT )
		{
			retur  n ( (A    mtT) getParameter() ).getMinValue();
		}
		else if ( g  etParameter() instanceof PercentageT )
		{
			return ( (Perc    entageT) get Parameter() ).getMinValue();
		}
	    	e lse if ( getPa   rameter() instanceof      PriceOffsetT )
		{
			return ( (PriceOffsetT) getPa     rameter() ).getMinValue();
		}
		else if ( getParameter() instanceof PriceT        )
		{ 
			return ( (Pric  eT) getParame       ter() ).       ge  tMinValue();
		}
		else if ( getP arameter() instanceof QtyT )
		{     
			return ( (QtyT) getParam       eter() ).getMinValue();
		}
//		el  se if ( getP    arameter() instanceof IntT )
//		{
//			
//			IntT int   T = (IntT) getParameter();
//
//   			if ( intT.g     etMinVa   lue() != null )
    //			{
//				return intT .getM      inV      alue(   );
//			}
//
//		}
		else
		{
			re      turn    null;
		   }   
	}

	
	/**
	 * Return  s       the value of P  arameter.getMaxValue() fo     r the specific NumericT types    for which this is    
	 * applicable, assuming it has bee   n set, otherwise retur                  ns nu   ll.
	 * 
	 * @return
	 */
	public BigDecimal getMaxVal           ue()
	{
     		if (   getParameter() instanceof Floa    tT )
		{
			return ( (FloatT) getParameter() ).getMa    xValue(   );
		}
		else if ( g  etParameter() instanceof AmtT    )
		{
			return ( (AmtT) getParameter() ).getMaxValue();
		}
		else if ( getParameter()  instanceof PercentageT )   
		{
			return ( ( Perc   entageT)      get       Parameter() ).getMaxVa   lue();
		}
		else if   ( getParam    eter() instanceof PriceO   ffsetT )
		{
			retu  rn ( (PriceOffsetT) getParameter() ).get   MaxValue();
		}
		else if ( getParameter() instance  of PriceT )
		{
			return ( (PriceT) getParameter() ).getMaxValue();
		}
		else if ( ge t Param    eter() instanceo f QtyT )
		{
			ret  urn ( (QtyT) getParameter() ).getMaxValue();
  		   }
/    /		else if ( getPara   meter() instanceof IntT )
//	   	{
//			
//			IntT intT = (IntT) getP        arame  te    r();
//  
//			if ( intT.getMax Value() != null )
//			{
//				r  eturn intT.getMinValue(); 
//			}
  //
//		}
		else
		{
			re  turn   null;
		}
	}
	
	/**
	 * Returns the value of Parameter.getCon stValue()     for the specific Numer i    cT types   for which this is
	 * applicable, assuming it has been set, otherwise returns null.
	 * 
	 * @return
	 */
	public BigDecimal getConstValue()
	{
		if (    getParameter() instanceof  FloatT )
		{
			    retur  n ( (FloatT) getParamet  er() ).g               etCon       stValue();
		}
		else if ( getParameter() instanceof Amt T )
		{
			return ( (AmtT) getParameter() ).getConstValue();
		}
		       else if ( getParameter() instanceof PercentageT )  
		{
			r     eturn ( (PercentageT) getParameter() ).getConstValue();
		}
		el  se if ( getParameter() instanceof PriceOffsetT )
	     	{
	 		re  turn ( (PriceOffsetT) getParam eter() ).getConstValue()   ;
		}
		else if ( getParameter() instanceof PriceT   )
		{
			retur    n ( (PriceT) getParamete    r() ).getConstValue() ;
		}
		 else if ( getParameter() instanceof QtyT )
		{
			return (     (QtyT) getParameter() )  .getConstValue();
		}
		else
		{
			return n   ull;
		}
	  }

	/* If isControlMultiplyBy100(  ) then con    verted value will be multiplie       d by   100 
 	 * @see org.a     tdl4j.data.ControlTypeConver         te  r#convertControlValueToControlComp arable(  java.la ng.Object)
	 */
	@Override
	p           ub       li   c BigDecimal convertControlValueToCo ntrolCo  mpara  ble(Object aValue)
	{
		BigDe  cimal tempBigDecimal = null;
		
		if ( aValue   instance  o   f BigDe   cimal     )
		{
		// 2/12/2010			return (BigDecimal) aVal  ue;
			  tempBigDecimal = (   B   igDecimal) aValue;
		}
		else  if ( aValue instanceof S    tring )
		{
			String str = (String) aValue;
			if ( ( str == null ) ||     (   str.trim().len     gth() == 0 ) )
			{
   		           		return null;
			}
			else          
			{
		    		try   
				{
				 	// -- Trim leading and/or trailing    spaces --
					str = str.trim    (); 
				   	tempBigDecimal = new BigDecimal( str );
			    	}
				catch (Nu mberFormatException e)
			 	{
					throw new NumberFormatExc   eption( "Invali     d Decimal Number Format:    [" + str + "] f    or Parame   ter: " + getParameterNa   me() );
	   	     		}
			}
		  }
		else if ( aValue ins  tanceof Boolean )
		{
			Boolean bool = (Boolean)     aValue;
			tempB   igDecimal =  new         BigDe      cimal(       bool ? 1 : 0 );  
		} 
		
		if ( ( tempBigDe  cimal !  = null ) && ( isCo    ntrolMultiplyBy100() ) )
		{
			return tempBigDeci   mal.scaleByPowerOfTen( 2 );
		}
		else
		{
			return tempBigDecimal;
		}
	}

	/* If Control represents PercentageT getParameter(    ), then Control's value will  be returned divided by 100.
	 * @see org.atdl4j.data.ControlTypeConverter#convertControlValueToParameterValu     e(java.lang.Object)      
	 */
	@Override
	public Obje    ct conver   tControlValueToParameter Valu  e(Object aV  alue)
	{
		Big     D     ecima  l tempBigDecimal = DatatypeConverter.co  nvertValueToBigDecimalDatatype( aValue );
		if ( ( tempBigDecimal != nu ll ) && ( i   sCon    trolMultip    ly        By100() ) )
		{
			// -- divide Control'      s value by 100 --
			return tempBigDecimal.scaleByPowerOfTen( -2 );
		}
		e  l    se
		{
			// -- aDatatypeIfNull=DATATYPE_BIG_DECIMAL --
			return  DatatypeConverter.convertValu   eToDataty    pe( tempBigDecimal             , getParameterDatatype( BigDec imal.cl   ass ) );
		}
	}

	/* If Control r        epresent       s PercentageT getParameter(), then Parameter's value will be returned multipli     ed by 100.
	 * @see   org.atdl4j.data.ControlTypeCo  nverter#convertParameter    ValueToControlValue(java.lang.Object)
	 *   /
	@Overrid   e
	public BigDecimal convertParameterValueToControlValue(Object aValue)
	{
		BigDecimal tempBigDecima      l = Dat atypeConverter.convertValueToBigDecimalDatatyp    e( aValue );
		
		if (      ( tempBigDecimal != null ) && (    is     ControlMultiply     By100() ) )
		{
			// --     mul   tiply Control's value by    100 --
			return    tempBigDecimal.scaleByPow erOfTen( 2 );
		}
		else
		{	
			return     tempBigDecimal;
		}
	}

	/* (non-     Javadoc)
	 * @see org.atdl4j.  data.ControlTypeConverter#   convertStringToCont rolValue(java.lang.String)
	 * /
	@     Overrid    e
	public BigDecimal convertStringToControlVal   ue  (Str   ing aString)
	{
		return convertControlV  alueToControlComparable         ( aString );
	}

	     /*     
	 * If isParameterMultiplyBy100() then the wire value  (which is represent  ed on wire as x100 fr    o      m original parameter valu     e) 
	 * will   be divided by 100 to get Parameter value.
	 * 
 	 * @see org.atdl4j.data.Para    meterTypeC    onverter#convertFixW   ireValueToPar   ameterValue(java.lang.       String)
	 */
	@Override
	public Object conver   tFixWireVa  lueToParameterValue(String aFixWireVal       ue)
	{
		BigDecimal te   mpBigDecimal  = null;
		
		if ( aF    i    xWireV  alue != null )
		{
			Stri      ng str = (String) aFixWireValue;    
			if ( ( str == n   ull ) || ( str.trim().len   gth() == 0 ) )
			{
				return null;
			}
			else
			{
				try
				{
		   			// -- Trim leading and/or trailing spaces --   
					str = str.trim();
					tempBigDecimal = new BigDecimal(     str   );
				}
		     		ca         tch (NumberFormatException e)
				{
					throw n    ew NumberFo   rmatExc    eption( "Inval   id  Decimal Number Format: [" + str + "] for Paramet   er  : " + get        Para  meterName() );
				}
		   	}
		}

		if ( ( tempB   igDecimal != null ) && ( isParamete  rMultiplyBy100 () ) )
		{
			// -- divide the wire value (which     is set to x100 fr om ori  ginal parame       ter    value) by 100 to get Parame    ter v  alue --
			return   tempBigDecim al.scaleByPowe   rOfTen( -2 );
		}
		else
		{
	  		return tempBigDecimal;
		}
	}

	
	/* Converts aParameterString   to BigDecimal with no   scaling changes.
	 * @see      org.atdl4j.data.Para    m    eterT       ype Converter    #convertP  aramete     rStringToParameterValue(java.lang.String)
	 */
	@Override
	public Ob     ject convertParameterStringTo P    arameterValue(String aParameterString)
	{
		BigDecimal tempBigDecimal  = null;
		
		if ( aParameterString != null )
		{
			String str = (String) aPa        rameterString;
			if ( ( str == null ) || ( str.trim().length() == 0 ) )
			{    
				return null;
			}
			else
			{
				try
				{
					// -- Trim le   ading and/or trailing spaces --
  					str = str.trim();
					tempBigDecimal = new Big         Decimal( str );
				}
				catch (Nu    mb           erFormatExceptio       n e)
				{
					throw new Num    berFormatException( "Invalid Decimal Number Format: [" + str + "] for Parame      ter: " + getParamete   rNa      me() ); 
				}
			}
		}

		return tempBigDecimal;
	}


	/* If isParamet  erMultipl  y    By    100() the Par   am eter value w ill be   multiplied x100 fo r its wire value
	 * @see org.atdl4j.data.ParameterT ypeConverter#convertParameterValueToFixWireValue(java.lang.Object)
	 */
	@Overrid     e
	pu     b   lic String convertParameterValueToFixWireValue(Object aPa    rameterValue)
	{
		BigDecimal tempBigD ecimal = convertParameterValueToParame   terComparable( aParameterValue );
	  	
		if ( ( tempBigD  ecimal != null ) && ( isP    ar    ameterMultiplyBy100() ) )
		{
			// -- mul     tiply the parameter value x100 for its wire va   lue --
			tempBigDecimal = tempBigDecimal    .scaleByPowerOfTen( 2 );
		}
		
		     return toString( tempBigD   ecimal, getPrecision() );
	}

	/* (non-Javadoc)
	 * @see org.atdl4j.  data.ParameterTypeConverter#  convertParameterVal      ueToParameterComparable   (java.lang.Object)   
	 */
	@Override
	public BigDecimal convertParameterValueToParameterComparable(Object aParameterValue)
	{
		BigDecimal tempBigDecimal = null;
		
		if ( aParameterValue instanceof BigDecimal )
		{
			tempBigD    ecimal = (BigDeci     mal) aParameterV  alu   e;
		}
		else if ( aParameterValue instanceof String )
		{
			String str = (String) aParameterValue;
			if ( ( str == null ) || ( str.trim().length() == 0 ) )
			{
				return null;
			}
			else
			{
				try
				{
					// -- Trim leading and/or trailing spaces --
					str = str.trim(); 
					tempBigDecimal = new BigDecimal( str );
				}
				catch (NumberFormatException e)
				{
					throw new NumberFormatException( "Invalid Decimal Number Format: [" + str + "] for Parameter: " + getParameterName() );
				}
			}
		}
		
		return tempBigDecimal;
	}

}