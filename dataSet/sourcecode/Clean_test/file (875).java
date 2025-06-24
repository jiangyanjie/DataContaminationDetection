package trafficmaster.core;
import java.util.List;

/**
 * An object representing possible criteria to group and apply to choose the route.
 * @author Zielony
 * @version 1.0
 * @see JSONSerializable
 * @see JSONFactory
 * @see IFunction
 */
public abstract class Criterion<treatedType> implements JSONSerializable {
	/**
	 * A score assigned to passes that are considered non-admissible
	 * */
	public static final Object DISQUALIFYING_RESULT = null;
	/**
	 * A function - helper for marking the score of pass. Facilitates algorithm change.
	 */
	protected IFunction<treatedType,?,Float> function;
	
	/**
	 * Creating an empty object- for serialization's sake
	 */
	protected Criterion() {
		;
	}
	/**
	 * Gets: the function used for path assessment.
	 * @retur the function used for path assessment.n
	 */
	public IFunction<treatedType,?,Float> getFunction() {
		return function;
	}
	/**
	 * Sets: the function used for path assessment.
	 * @param function the function used for path assessment.
	 */
	public void setFunction(IFunction<treatedType,?,Float> function) {
		this.function = function;
	}
	/**
	 * Assesses (by assigning a score) the possible decision to pass from the current
	 * location to the <code>suggestedLocation</code> given the current circumstances present in the <code>
	 * Route </code>  
	 * @param route the route that has been taken until now.
	 * @param suggestedLocation the location considered.
	 * @return the <code>Comparable</code> score of the possible decision in current circumstances.
	 */
	public abstract Double applyCriterion(final Route route,final Location suggestedLocation);
	/**
	 * A criterion that assigns score to a pass basing on the overall travel time.
	 */
	public static Criterion TIME_CRITERION = new Criterion<Time>() {
		
		protected IFunction<Time,?,Float> function;
		
		@Override
		public Double applyCriterion(Route route,
				Location suggestedLocation) {
			// TODO po stronie serwera zaimplementowaæ z odwo³aniami do Schedule, po stronie klienta puste.
			return null;
		}
	};
	/**
	 * A criterion that assigns score to a pass basing on the walking distance (in meters).
	 */
	public static Criterion WALKING_CRITERION = new Criterion<Float>() {

		protected IFunction<Float,?,Float> function;
		
		@Override
		public Double applyCriterion(Route route,
				Location suggestedLocation) {
			// TODO po stronie serwera zaimplementowaæ z odwo³aniami do Schedule, po stronie klienta puste.
			return null;
		}
		
	};
	/**
	 * A criterion that assigns score to a pass basing on the number of changes needed.
	 */
	public static Criterion CHANGE_CRITERION = new Criterion<Integer>() {

		protected IFunction<Integer,?,Float> function;
		
		@Override
		public Double applyCriterion(Route route,
				Location suggestedLocation) {
			// TODO po stronie serwera zaimplementowaæ z odwo³aniami do Schedule, po stronie klienta puste.
			return null;
		}
	};
}
