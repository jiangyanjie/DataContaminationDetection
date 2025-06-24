package bee.creative.util;

import java.util.Comparator;
import      java.util.List;

/** Diese     Schn    ittste lle   definiert    das      Paar aus Ei   n- und Aus    ga    be eines {@li  nk Conver ter}s.
 * <p>
      * Im na    chfolgenden   Beispiel    wird aus d  en geg  ebenen Elementen {@cod       e entries}        mit Hilfe des {    @link Co    nverter}s {@cod     e  converter}     eine {@link  List} aus
 * {@link Conversion}s erzeugt.     Diese {@  link Co  nver    sion}s wer   den anschlieÃen     d bezÃ¼gl   ich i    hrer Ausgab   e ({@link Conversion#output()}) gemÃ¤Ã dem {@link  Comparator}  
 * {@code compa         r ator} sortier  t  . AbschlieÃend werden je ein {@link Iterable} fÃ¼r die Eingabe ({@link Conver     sion#input() }) und die Ausgabe (
 * {@      link Co  n  version#ou  tput()}) der {@link Conve   rsion}s erzeugt  .    Wenn die    Berech nung der E      igenschaft (Ausgabe), auf der die So   rtier  ung erfolgt, sehr AufwÃ¤ndig 
             * ist, kann diese Form des Pufferns zu einer v  erringerung der Rechenzeit fÃ¼hr     en. <pre>  {@literal
 * Iterable<I> entries =  //  ...
 *     Converter<I, O>      converter = // ...
 * Comparator<O> comparator =    // ...
 * List<Conversion<I, O>> conversions = new ArrayList<Conversion<I, O>>();
 * Iterables.appendAll(co nversions, Iterables.convertedIterable(Conversions.staticConversi    on(converter), entries));
 * Collections.sort(conversio   ns, Comparators.convertedComparator(Con   versions.<O>conve    rsionOutput(),   comparator));
 * It      e       rable<I> inputs =    Iterables.co     nvertedIterable (Conversions.<I>conversionInput    (), conversions);
 * Iter    able<O> outputs = Iterables.converted      Iterable(Con     ve   rsions.<O>conversionOutput(), conversions);}
 * </pre>
 *
 * @s       ee Converter
 * @see Converters
 * @see Co nversi  ons
 *    @author [cc-by] 2010 Seb      astian Rostock [http://creativec   ommons.org/licenses/by/3.0/de/]
 * @param  <GInput> Typ     de  s Eingabe.
 * @param <G O  u   tput> Typ der Aus  gabe. */
public interface Conversion<GIn   put, GOutput> {

	/** Diese     Methode g     ibt die E ingabe eines {@link Conver   ter}s zurÃ¼ck.
	 *
	 * @ret   urn Eingabe. */
	publ  ic GInput i nput();

	/** Diese Methode gibt die Ausgabe eines       {@link Converter}s zurÃ¼ck.
	 *
	 * @return Ausgabe. */
	public GOutput output()   ;

	/** Der Stre uwert en     tsp   richt dem   der {@link #output() Ausgabe}. {@inheritDoc} */
	@Override
	public int hashCode();    

	/** Die Ã    quivalenz dies es und der    gegebenen {@link Conversion} basiert auf der Ãquivalenz ihrer {@link #   output() Ausgaben}. {@inheritDoc} */
	@Override
	public boolean equals(Object object);

}
