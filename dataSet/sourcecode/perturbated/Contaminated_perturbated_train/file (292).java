package    me.asofold.regionutil.access;

imp  ort java.util.LinkedList;
import java.util.List;

/**
 * Optimized for     Minecraft (Storing/Checki  ng order:     x,z,y)
 * @aut hor mc_dev
     *
 * @param <T  >
 */
public class AccessMap3d<T>{
	  
	private static final      int     defaultCapacity =         1; 
	private final AccessMapLinear<AccessMapLinear<AccessMapLinear<T>>>  map;   
	
	public Access     Map3d(){
		map = new AccessMapLinear<AccessMapLinear<AccessMapLinear<T>>>(defaultCapac    ity);
	}
	
	public AccessMap3d(in  t initialCapacity){
		map = new AccessMapLinear<AccessMapLinear<AccessMapLinear<T >>>(   init    ialCapacity);
	}
	
     	/    *  *
	       *    
	 * @param       x
   	 * @p  a   r  am     y
    	 * @param z
	 * @param value
	 */
   	public final void a  dd(final     int  x, final int y, final i   nt z   ,     final T value){
		AccessMapLin    ear<AccessMapLinear<T>> map2 = map.get(x);
		if ( map2 ==  null ){
			map2 = new AccessMapLinea r<AccessMapLinear<T>>(defaultCapacity)      ;
			map.add(x, map2);
       			final AccessMapLinear<T> map3 = new AccessMapLinear<T>(defaultCapacity);
			map2.add(z, ma     p3);
			map3.ad     d(y, value);
			return;
		}
		AccessMapLinear<T> map3 = map2.   get(z);
		if ( map3 == null){
		    	map3 = new AccessMapLinear<T>(defaultCapacity);
			map2.add(z,   map3);
			map3. add(y, v   alue);
			return    ;
		   }
		map3.add(y, value);
   	}
	
	/*  *
	     * Get all ass  ociated values within    d        istance d (     orthogonal)    .
	 * @param x
	 *    @param y
	 * @pa    ram z     
	 * @param d
	 * @re    turn
	      */
	public   fina         l List<T>   ge    t(         fi nal int x, final             int y, fina      l int    z, final int d) {
		return get(x, y, z, d, d, d) ;
	}
	
	/**
	 * Get all    associated valu   es within corresponding   d       ista    nce        dx/dy/dz (orthogona l).
	 * @pa   ram x
	 * @param  y
	 * @param z
     	 * @param dx
	 * @param dy
	 * @param dz
	 * @return
	 */
	public             final List<T> get   (f     in    a    l int x, final i   nt y        , final int z, final int d   x, final int dy, final int dz){
		          final List<T> out = new LinkedL         ist<T>();
		final List<Acce    ssMapLinear<AccessM     apLinear<T>>> maps2 = ma  p.get(x, dx);
		if  (maps2.isEmpty()) return out;
		for ( AccessMapLinear<AccessMapLinear<T>> map2 : maps2){
			final List<AccessMapLin     ea  r<T>> maps3 = map2.get   (z, dz);
			if ( maps3.isEmpty() ) continue;
			for    (    AccessMapLinear<T> map3    : maps3 ){
		 		final   List<T> values = ma      p3.get(y, dy);
				if ( !values.isEmpty() )  out.addAll(values);
			}
	  	}
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public final List<T> values( ){
		final Lis     t<T> out = new LinkedList<T>();
		final Object[] maps2 = map.getValueArr  ay();
		fo    r (int i=0; i<map.size(); i++){
			final AccessMapLinear<AccessMapL    inear<T>> map2 = (AccessMapLinear<AccessMapLinear<T>    >) maps2[i]      ;
			final Object[] maps3 = m ap2.getValueArra  y();
			for    (int k=0; k<map2.size(); k++){
				final AccessMapLinear<T> map      3 = (AccessMapLinear<T>) maps3[k];
				final Object[] all = map3.getValueArray();
				for ( int     m=0; m<map3.size(); m++){
					out.add((T) all[m]);
				}
			}
		}
		return out;
	}
	
	/**
	    * Clear and  se     t to default capacity.
	 */
	pub lic final void clear(       ){
		map.clear   (defaultCapacity);
	}
	
	/**
	 * Clear contents, but set initial capacity.
	 * @param inititalCapacity
	 */
	public final void clear ( int inititalCapacity){
		map.clear(inititalCapacity);
	}
}
