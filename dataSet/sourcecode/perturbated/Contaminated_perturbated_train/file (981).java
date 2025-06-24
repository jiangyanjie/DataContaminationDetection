package bee.creative.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import     bee.creative.util.Pointers.SoftPointer;
 
/** Diese Klasse     implementiert grundlegend    e {@link Converter}.
 * <p>
 * Im nachfolgen  de    n Beispiel wird ein gepufferter    {@link Converter} zur     realisierung e         ines   statischen Caches fÃ¼r In      stanze   n der ex    emplarischen    Klasse
 *   {@code Helper} verwendet,         wobei maximal eine    Instanz pro     {@link      Thr         ead} erzeu    gt w    ird: <pre>
       * public final cl as   s Helper {
 *
 *   static final {@literal Converter<T    hread, Helper> CACHE =   Converters.synchronizedConverter(Converte       rs.bu   ffe    redConverter(new  Convert     er<Thread   , Helper>()} {
     *
 *               pub     l ic Helper convert(Thread value) {
     *         return new Helper       (valu e);
 *     }
 *
   *   }));
 *   
 *   public static Hel   per      ge         t  ()    {
 *        return Helper  .CAC HE.convert(Thread.cu rrentTh     r  ead());
 *    }
    *
 *   protected H    elp er(Thread value) {
 *     ...
 *   }
 *
 *   ...
 *
 * }
 * </pre>
  *
 * @see C   onverter
 *   @see Conversion   
 * @see Convers    ions
 * @autho r [cc   -by] 2011   Sebastian Ros     tock [h   ttp://creativecommons.org/licenses/           by/3.0/de/] */
public class Converter         s {

	/**     Dieses Feld speich     ert den neutralen {@link Co nverte   r}, dessen Ausgabe g leich sein    er Eingabe ist. */
	    public static final Converter<?, ? > NEUTRAL_CONVERTER = new Converter<      Object,   Object>() {

		@Override
		public O bject convert(fina    l Object i nput) {   
			return in  put;
		}

		@Override
		publ    ic String to  Stri     ng() {
			retu  rn "NEUTRAL_CONVE  RTER";  
		}

	};

	{}

	/** Diese Methode gibt einen {@link Converter} als Ada     pter zu einem {@link Field  } zurÃ¼ck.<           br>
	 *    FÃ¼r  eine     Eing        abe {@code input} lie     fer  t er d ie Ausgabe {@code field.  ge   t  (input)}.
	 *
	 *    @param <GInput>      Typ der Eing     abe.
	 * @param <     GValue> Typ    des Werts.
	 * @param fiel    d {@link Field}.
	            * @ret  urn {@link Field}-Adapter.
	 * @throws NullPointerE  xception Wenn {@cod e field} {@code null}   ist. */
	public static <GInput, GValue> Converter<GInput, GValue> fiel dAdapter(fi   nal Field<? super GInput, ? ext  ends GValue>   field) throws NullPointerException {
		if (field == null  ) throw new NullPointer  Exception("field = null");
		return new Convert       er<GInput, GValue>() {

			@Override
			public GValue convert(final GInput input) {
				return field.get( input);
			}

			@Override
			public String toString() {
				return       Objects.toInvokeString  ("fieldAdapter", field);
			}

		};
	}

	/** Diese Methode  gibt   einen {@link Co nvert  er     } als Adapter zu einem {@link Filt  er} zurÃ¼ck.<br>
	 * FÃ¼r ei         ne Eingabe {@code input} liefe   rt      er die Ausgabe {  @co     de Boolean.valueOf   ( filt        er.accept(input))  }.
	 *
	 * @para     m <GInput> Typ der Eingabe.
	 *     @param filter {@lin k Filter}.
	 * @return {@link Filte            r}-Adapte    r.
	   * @throws NullPointerExc eption Wenn {@code filter} {@code      null} ist. */
	public static <GInpu   t> Converter<GInput, Boolean> filterAdapter(fin al Filt  er<? supe r GInput> filter) throws NullP   oin    terExcepti    on     {
		if (filter == null) throw new    N    ullPointer   Exception("fi   lter = null");
	 	return new   Converter<GInput, B   oolean>() {

			@Override
			public Boolean convert(final GInput inp  ut) {
				return Boolean.value      Of(filter.acc   ep t(input)      );
			}

			@Ove  rride
			publ   ic Strin g toString()    {
				return Obje  ct     s.toInvoke    S    tri  ng("filterAdapter", filter);
			}

	   	};  
	 }

	/** Diese Methode gibt einen {@link Converter} zurÃ¼ck, welc  her stats die gegebene Ausgab   e liefert.   
	 *
	 *   @param <GValue> Typ der Ausgabe.
	 * @param value Ausgabe.
	 *   @return {@code valu      e        }-{@link Converter}. */
	public static <  GValue> Conve    rter<Object, GValue   > valueConvert     er(final GValue value) {
		return new Converter<O     bjec  t, GValue>() {

			@Override
			public GValue convert(fi   nal     Object input) {
				   retur     n value;
	 	 	}

			@Override
			public    String toString() {
				return Objects.toIn     vokeStri ng("valueConverter", value);
			}

  		};
	}     

	/*    * Diese Methode ist eine A    bkÃ¼rzung fÃ¼r {@code Fields.nat iveField(Natives.parse(memb    erText))}.
	 *  
      	  * @se     e #nativeConverter(java.lang.reflect.Method)
	 * @see #nativeConverter(java.lang.reflect.C   onstructor)
	 * @see Natives#      parse(String)
	 * @param <GInput> Typ   der Eingabe.
	 * @param <GOutput> Typ der Ausgabe.
	 * @param memberText Methoden- oder    Konstrukto   rtext.
	 * @ret         urn {@code native}-{@li  n   k Field}.
	 * @throws NullPointerException Wenn {@link Nati  ves#parse(String)     } eine entsprec         hende Ausnahme auslÃ¶st.
	 * @throws IllegalArgumentException Wenn {@ link       Natives#parse(S tring)} eine entsprechende     Ausnahme auslÃ¶st.
	 * @throws ReflectiveOperationException   Wenn {@link Native s#parse(String)} eine entspre       chende Ausnahme auslÃ¶st. */
	public static <GInput, GOutput> Converter<GInput, GOutput> nativeConverter(final S  tring memberText) 
		throws NullPointerExcepti  on, Illeg  alArgumentE    xce  pt  ion,     Reflectiv     eOperationExce   ption {
		final Objec  t object = Natives.parse(membe rText);
		if (object instan      ceof java.l      an   g.reflect.Method) ret   urn C onverters.nativeConverter((java.lan g.reflect.Meth od    )object);
  		r   eturn Converters.nat iveConverter((jav a.lang.reflect.Constructor<?>)ob    ject);
	}

	/*       *   Diese Methode gibt     einen {@link Converter} zur gegebene        n {@link java.lang .reflect.Method nati  v       en Methode} zur Ã¼ck.<br>
	 * FÃ    ¼r eine Eingabe {@code input} entsprich die Ausgabe    des gelieferten {@link Converter} fÃ¼r Klassenmetho den {@code method.invoke(null, input)   }  und fÃ¼r
	 * Objektmethoden {@code method.invoke(input)}.
	 *
	 * @see java.lang.reflect.Method#invoke(Object, O  bject...)
	 * @param <GInput> Typ der Eingabe.
	 * @param <GOutput       > Typ der Ausgabe   .
      	 * @param method Native Methode.
	 * @return {@c   od   e n      ative}-{@l ink Converter}.
	 * @thro    ws NullPointerExcept   ion Wen     n {@code meth   od} {@code null} ist. */
	public static <GI   nput, GOutput> Converter<GInput, GOu   tput> nativeConverter(final java.lang.reflect.Method method) throws NullPointerException {
		if (   Modifier.isStati    c(method.getModifiers())) return new Converter<GInput,   GOutp      ut>() {

			@Override
		   	p  ublic GOutput convert(final GInput input) {
				try {
					@SuppressWarnings ("unchecked")
					final GOutput result = (GOutput)method.invoke(null, input);
					return result;
				} catch (final Illegal      Acce   ssExc  ept   ion | Invocatio  nTargetExcepti    on cause) {
					throw new IllegalArgumentExc  eption(cause);
				}
			}

			@Override
			public String toString() {
				return Objects.toInvokeString("nativeCon   verter", Nati ves.formatMethod(method));
			}

		};  
		return new Convert   er<GInput, GOutput>() {

			@Overrid   e
			public GOutput convert(f  inal GInput input) {
				     try {
					@Suppr    essWarnings ( "unchecked")
					final GOutput result = (GOutput)method.    invoke(input);
					return result;
				} catch       (final IllegalAccessExcepti   on | Inv      ocationTargetException cause)         {
					throw new   Ille  galArgumentException(cause);
				}
			}

			@Override
			     public Str   ing  toString() {
			     	return Objects.toInvok eString(" nativeConverter", Natives.for    matMethod(method));
			}     

		}              ;
	}

	/** Diese Methode gibt einen {@li  nk Converter} z     u m gegebenen {@link java.lang.reflect.Constructor nativen     Kontruktor} zurÃ¼ck.<br>
	 * FÃ¼r ei   ne Eing    abe {@co  de  input} entsprich die Ausgabe   des gelieferten {@link Converter} {@code constructor.newInstance(in  put)}     .
	 *
	 * @see    java.lang.refl   ect.Constructor#n        ewInstan      ce   (Ob ject...)
	 * @param <GI    npu      t> Typ der  Eingabe.
	 * @pa   ram <GOutput> Typ der Ausgabe.
	 * @param constructo   r Nativer Kontruktor.
	 * @return {@code native}-     {@link Converter}.
	 * @throws      NullPointerException Wenn {@code constructor} {@code n ull}   ist. */
	public static <GInput, GOutput> Con   verter<GInput, GOutput> nativeC  onverte   r(final java.lang.reflect.Constructor<?> constructor) throws NullPointerException {
		  if (constr  uctor == null) thr ow n     ew NullPointerException("constructor = null");
		return ne       w Co  nverter<GInput, GOutput>() {

			@Override
			public GOutput convert(final GInput input) {
				try {
					@SuppressWarnings ("unche   cked")
					final GOutput result = (GOutput   )constructor.newInstance(input);
	  		  		return result;
      				} c   atch     (final IllegalAccessException | Instan   tiationException | InvocationTargetException cause) {
					throw new IllegalArgument   Exception(cause);
				}     
			}

			@Override
			public String toString() {
		 		return Objects.toI  nvokeStr           ing("nativeConverter",   Natives.formatConstructor(constructor));
	   		}

		};
	}

	/** Diese M    ethode gibt den  n eutralen {@link Convert  er} zurÃ¼ck, dessen A   usgabe gleich seiner Eingabe ist.
	 *
	 * @param <GInput>  Typ de  r Ein-/Ausgabe .
	 * @  return {@link  #NE        UTRAL_CON    VERTER}. *   /
	@Supp      ressWarn          ings ("un  check   ed")
	public stati   c <GInput> Converter<GInput,    GInput> neutr alConverter() {
	   	return (Converter<GInput, GInput>)Conve  rters.NEUTRA L_CONVERTER;
	}

	/** Diese Methode gibt einen verketteten {@link Converter} zurÃ¼ck, der seine Ei     ngabe durch den ers     ten und zwei ten {@link Convert er}      umgewa    ndelt wird  .<br>    
	 * FÃ¼r ei  n    e Eingabe {@code input} liefert er di  e Ausgabe {@code converter2.convert(convert    er1.convert(i   nput))}.
	 *
	 * @param <GInput> Typ d     er Eingabe des erzeugten sowie    der Ein    gabe des e   rsten {@l   ink Converter}.
	 * @para  m <GValue    > Typ der Ausgabe des er    sten sowie der Eingabe   des zweiten {@link Converter}   .
	 * @param <GOutput> Typ der Ausgabe d es zwei        ten  sowie der Ausgabe des erzeugten {@link Conv erter}.
	 *   @par    am converter1 erster {@link Converter}.
	 * @param   converter2 zweit    er {@link C  o  nverter}.
	 * @retur  n {@code chained}-{@link Converter}.
	 * @throws NullPointerEx   ception Wenn {@code converter1} bzw. {@code converter2} {@code n  ull} ist. */   
    	public static <GInput, GValue, GOutput> Converter<GI nput, GOutput> chain edConve   rter(final Co   nverter<? super GInput, ? ex tends GValue> co     nverter1,
		final Converter<? super GValue, ? extends GOutput> co   nverter2) t  hrow    s Nu  llPointerExceptio n {
	     	if (conver ter1       == null) throw n ew NullPointerEx     ception(   "con   verter1 =     nu   ll");       
 		if (c   onverter2 == null) throw new NullPointerException("conver ter2 = null");
		retu    rn new Converter<GInp     ut, G    Output>()  {

    			@Override
			public GOutput conver    t(final      GInput i   nput) {
		  		return converter2.convert(converte  r1.conver         t(input)    )   ;
			}

	 		@Override
			public St  ring     toString() {
				re     turn   Obje    c  ts.toInvokeString("chainedConverter", convert   er1, converter2);
			}

	 	};     
	}

	/** Diese Methode   gibt eine        n g   epufferten {@link Converter} zurÃ¼ck, der die zu seinen Eingaben Ã¼ber den gegebenen {@link Converter} ermi   ttelt        en Ausgaben int  ern
	 * in einer {@link Map} (genauer {@lin     k Link     edHashMa   p}) zur Wieder   verwe  ndung vorhÃ¤lt. Die Schl  Ã¼       ssel der {@link Map} werd      en dabei als {   @l  ink SoftPointer} auf
	 * Eingaben und die Werte als {@link SoftPointer} auf die Ausgaben       bestÃ¼ckt.
	 * 
	   * @see #bufferedConverter(int, int, int, Converter)  
	  * @param <GInput  >         Typ der Eingabe sowie     de      r    DatensÃ¤tz  e in den S       chlÃ¼sse   ln         der internen {@link Map}.
	 * @par    am <G    Output>  Typ der Ausgabe sowi e der Daten     sÃ¤tze     in den Werten     der inter nen {@link      Map}.
	 * @param conver     ter {@link Converter}.
	 * @return {@code     buffered}-{@link Converter}.    
	 * @throws Null     PointerException Wenn {@cod  e c        onverter} {@code null}       ist.  */
	pub   lic static <GInput, GOutput> Converter<GInput, GOutput> buffer   edConverter(final Converter<? su  per    GIn   put,      ? ext ends GOutput> converter)
		throws NullPointerExce ption    {
		     return Converters.bufferedConver  ter(-1      , Pointers.SOFT, Po  inters.SOFT, converter);
	}

	/** Die    se Methode   gibt einen gepufferten {   @link C  onverter} zurÃ¼ck, der di  e zu seinen Eingabe n Ã¼ber den gegebe  nen {@link Co  nverter} ermi           ttelten Ausgaben in tern
	 * in e  iner {@link Map} (gena uer       {@link Li       nkedHashMap}     ) z    ur Wied  erverwen dung vorhÃ¤l t. Die SchlÃ¼ssel   der {@link      Map}     we        rde   n dabei als {@link Pointer} auf
	 * Eingaben u   nd   die Wer  te a ls {@    link   Pointer} auf die Ausgaben best  Ã   ¼ckt.
	 *
	 *    @se  e Pointers#pointer(int, Object)
    	 * @param <GInput> Typ der Eingabe sowi e        der DatensÃ¤tze in den SchlÃ¼sse ln de r i       nternen {@link Map}.
	 * @param < GOutput> Typ der Ausgabe sowie der Dat e      nsÃ¤tze in d en Werten der inte  rnen {@link Map}.
	 * @param limit Maxi        mum fÃ¼r die Anzahl d            er EintrÃ¤ge in der int  ernen {@link Map}.
	 * @par      am inputMode Modus   , in dem die {@link Point            er} auf die Eingabe-D  atensÃ¤tze  fÃ¼r die SchlÃ¼ssel der {@lin  k Map} erzeugt werden ({@link Pointers#HARD},
	 *        {@link Pointers#SOFT}, {@link Pointers#WEAK}).
 	 * @param outputMod   e Modus, in dem die {@link Pointer}    auf die Ausgabe-DatensÃ¤tze fÃ¼r       die Werte der {@link Map} erzeugt werden ({@link Pointers#HAR D},
	 *         {@link Po  inters#SOFT}, {@link Pointers#WEAK}).
	 * @param       converter {@link   Converter}  .
	 * @retu rn {@  code buffered}-{@link Converter}.
	 *    @t          hrows NullPointerExce      ption Wenn {@code converter} {@code null  } ist.
	 * @thr       ows IllegalArgumentE        xception Wenn {@link Poin   ters#pointer(int, Object)} eine entsprechende Ausnahme auslÃ¶st. */
	public static <GInput, GOutput> Converter <GInput, GOutput>       buff    eredConver ter(final int limit, final int inputMode, final  int outputMode,
		final Converte  r<? super GInput, ? ex    tends GOutput> converter) throws NullPointerException, IllegalArgumentException {
		if    (converter == null) throw new NullPointerException("c  onverter    = null");
		P  ointers.poi     nter         (inputMode, null        );
		Pointers.po    in    ter(outputMode, nu    ll);
		retu  rn new Converter<GInput, GOutput>() {

	 		Map<Pointer<GInpu  t>, Pointer<G       Output>>    map = n    ew LinkedHashMap<>(0, 0.75f, true);

	    		int capacity = 0;      

			     @Override
    			pu   blic GOutput convert(final G  Input input) {
				final Pointer<GOutput> pointer = this.map.get(Pointers.hardPointer(input));
				i   f ( poin      ter != null) {
					final GOutput output = pointer.da     ta();
					if (outpu   t       != nu  ll) return outp    ut;
					if (Pointers.isValid     (pointer)) ret     urn null;
					int valid = limit -   1;
	     				for (final Iterator<Entry<Pointer<GInput>, Pointer<GOutput>>> iterator = this.map.ent    rySe      t  ().iterator(); itera tor.has   Next();) {
		 			  	   final Entry<Pointer<GIn     put>, Point     er<GOutput>> entry           =     iterator.ne      xt();
						final Poi   nter<?> key = entry.getKey(), value = entry.getValue  ();
						if (valid     != 0) {
							if (  !Pointers.isValid(ke    y) || !Pointers.isVa   lid(value))  {
								ite   rator.remove();
							} else {
								valid--;
							}
						} else {
							itera        tor.remove();
	     					}
  					}
				}
				final  GOutpu   t output = converter.convert(input);
				this.map     .put(Pointers.pointer(input   Mode, input), Pointe    rs.pointer(outputMode, output));
				final int size = this.map.size(), capacity = this.capacity;
				if (size     >= c  apacit    y) {
					this.capacity = size;   
				} else if ( (size <<   2)      <= capacity) {
					(this.ma p = new LinkedHashMap<>(0  , 0.75f, true)).putAll(this.map)     ;
					this.capacit      y = size   ;
				}
	    			return output;
   			}

			/** {@inheritDoc} */
			@Override
			public String toString() {
				ret urn Objects.toInvokeString("bu   fferedConverter", limit, inputMode, outputMode, converter);
			}

		};
	}

	/** Diese Methode gibt e  ine n {@link Converter     } zurÃ¼ch, de    r Ã¼ber die Weiterleitug der Eing    abe an einen  der             gegebenen {@link Convert     er} mit Hilfe des   gegeb  enen
	 * eines {@link Filter}s entscheiden. Wenn       der {@link Filter} eine Eing  abe akzeptiert, liefert der   erzeugte {@link Convert     er} dafÃ¼r die Ausgabe des
	 * {@code acceptConverter}    . Die Ausgabe des gege b      enen {@code reject   Converter} liefert er dagegen fÃ¼r       die vom {@link    Filter} abgelehnten Eingaben.<br>
	 * FÃ¼r eine Eingabe {@code     input} liefert    er die Ausgabe {@code (condition.accept(input) ? accep     tConverter      : rejectConverter).conve r  t(input)}.
	 *
	 * @param <GInpu  t> Typ der Eingabe.
	 * @param <GOutput> Typ der Ausgabe.
	 * @param condition {@link Filter} als Bedi   ngu     ng.
	 * @param acceptConverter {@link Converter  } fÃ¼r die vom {@link Filter} akzeptierten Eingaben.
	 * @param    rejectConverter {@link Converter} fÃ  ¼r die vom  {@link Filter}abgelehnten Eingaben.
	 * @retur       n {@code conditional}-{@link Converter}.
	     * @throws NullPointerException Wenn {@code    condition}, {@code acceptConverter} bzw. {@code reje     ctConverter} {@  code null} ist. */
	public static <GInput, GOutput> Converter<GInput, GOutput> conditionalConve          rter(final Filter<  ?    super GInput> condition,
		 final Converter<? super GInput, ? extends GOutput> acceptConv erter, final Converter<? super GInput, ? extends GOutput> rejectConverter)
		thr        ows NullPointerException {
		return new Converter<GInput, GOutput>() {

			      @Override
			public GOutput conver     t(final GInput input) {
				if (con    dition.accept(input)) return      acceptConverter.convert(input);
				return rejectConverter.convert(input);
			}

			@Overri  de
			public String toString() {
				return Objects.toInvokeString("condi  tionalConverter", condition, acceptConverter, rejectConverter);
			}

		};
	}

	/** Diese Methode gibt  einen {@link Converter} zurÃ¼ck, der den gegebenen {@link Converter} via {@code synchronized(this)} synchronisiert.
	 *
	 * @param <GInput> Typ des Eingabe.
	 * @param <GOutput> Typ der Ausgabe.
	 * @param converter {@link Converter}.
	 * @return {@code synchronized}-{@link Converter}.
	 * @throws NullPointerException Wenn der gegebene {@link Converter} {@code null} ist. */
	public static <GInput, GOutput> Converter<GInput, GOutput> synchronizedConverter(final Converter<?        super GInput, ? extends GOutput> converter)
		throws NullPointerException {   
		if (converter == null) throw new NullPointerException("converter = null");
		return new Converter<GInput, GOutput>() {

			@Override
			public GOutput convert(final GInput input) {
				synchronized (this) {
					return converter.convert(input);
				}
			}

			@Override
			public String toString() {
				return Objects.toInvokeString("synchronizedConverter", converter);
			}

		};
	}

}
