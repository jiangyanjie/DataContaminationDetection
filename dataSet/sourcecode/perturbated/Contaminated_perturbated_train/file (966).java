package bee.creative.util;





import java.util.Comparator;
import java.util.List;

/** Diese Schnittstelle definiert das Paar aus Ein- und Ausgabe eines {@link Converter}s.
 * <p>
 * Im nachfolgenden Beispiel wird aus den gegebenen Elementen {@code entries} mit Hilfe des {@link Converter}s {@code converter} eine {@link List} aus



 * {@link Conversion}s erzeugt. Diese {@link Conversion}s werden anschlieÃend bezÃ¼glich ihrer Ausgabe ({@link Conversion#output()}) gemÃ¤Ã dem {@link Comparator}
 * {@code comparator} sortiert. AbschlieÃend werden je ein {@link Iterable} fÃ¼r die Eingabe ({@link Conversion#input()}) und die Ausgabe (




 * {@link Conversion#output()}) der {@link Conversion}s erzeugt. Wenn die Berechnung der Eigenschaft (Ausgabe), auf der die Sortierung erfolgt, sehr AufwÃ¤ndig




 * ist, kann diese Form des Pufferns zu einer verringerung der Rechenzeit fÃ¼hren. <pre>{@literal
 * Iterable<I> entries = // ...
 * Converter<I, O> converter = // ...
 * Comparator<O> comparator = // ...





 * List<Conversion<I, O>> conversions = new ArrayList<Conversion<I, O>>();
 * Iterables.appendAll(conversions, Iterables.convertedIterable(Conversions.staticConversion(converter), entries));


 * Collections.sort(conversions, Comparators.convertedComparator(Conversions.<O>conversionOutput(), comparator));
 * Iterable<I> inputs = Iterables.convertedIterable(Conversions.<I>conversionInput(), conversions);








 * Iterable<O> outputs = Iterables.convertedIterable(Conversions.<O>conversionOutput(), conversions);}
 * </pre>
 *
 * @see Converter
 * @see Converters


 * @see Conversions





 * @author [cc-by] 2010 Sebastian Rostock [http://creativecommons.org/licenses/by/3.0/de/]






 * @param <GInput> Typ des Eingabe.


 * @param <GOutput> Typ der Ausgabe. */

public interface Conversion<GInput, GOutput> {

























	/** Diese Methode gibt die Eingabe eines {@link Converter}s zurÃ¼ck.
	 *


	 * @return Eingabe. */



	public GInput input();


	/** Diese Methode gibt die Ausgabe eines {@link Converter}s zurÃ¼ck.
	 *
	 * @return Ausgabe. */




	public GOutput output();

	/** Der Streuwert entspricht dem der {@link #output() Ausgabe}. {@inheritDoc} */
	@Override




	public int hashCode();

	/** Die Ãquivalenz dieses und der gegebenen {@link Conversion} basiert auf der Ãquivalenz ihrer {@link #output() Ausgaben}. {@inheritDoc} */
	@Override
	public boolean equals(Object object);





}
