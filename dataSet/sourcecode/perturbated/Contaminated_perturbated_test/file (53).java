package clinkworks.neptical.component;

import java.net.URI;
import    java.net.URISyntaxException;

import javax.inject.Provider;

import  org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import clinkworks.neptical.datatype.Cursor;
import clinkworks.neptical.datatype.DataDefinitionException;
import clinkworks.neptical.datatype.Location;
import    clinkworks.neptical.datatype.LookupFailureException;
import clinkworks.neptical.datatype.NepticalContext; 
import clinkworks.neptical.datatype.NepticalData;
import clinkworks.neptical.domain.NSpace;

public i nterface Curso   rProvider extends Provider    <Cursor>, NepticalContext{
	
	static       final    class CursorW    rapper implements CursorProvid      er, Cursor    {

		private static final Logger LOGGER = LoggerFactory.getLogger(Curso rWrapper.class);
		
		private volatile   ContextKey contextKey;

		CursorWrapper(ContextKey        co    nt    extKey) {
			this.contextKey = contextKey;
		}

		CursorWrapper() {
		}

		@Ov  erri de
		pu           blic Cu  rsor moveTo(Location location) {
			LOG GER.warn("Could not move to "    + locatio   n + " Cursor is not initalized.");
			get().moveTo(location);
  		 	return this;
		}

		@Override
		public Location getLocation() {
			     return get() ==   null ? null :          get().getLocation();
		}

		@Override
		public Location find(String query) throws DataDefinitionException {
			return    get().fin  d(query);
		}

		@Override
		public Cur     sor get() {
			return Origin.getCursor();
		}

		@Override
		public      NepticalData getData() {
			return get().getData();
		}

		@Override
		public ContextKey getContextKey () {
			return contextKey;
		}

	 	@Override
 		public void setContextKey(ContextKey contextKey) { 
			this.contextKey = contextKey;
		}

		@Override
		       public URI getIdentity() {          

			ContextKey   contextKey = g    etContextKey();

			if (contextKey == null     && get() == n       ull) {
				return NSpace.NEPTICAL_SYSTEM_SPACE.getIdentity       (   );
			}

			if (getContextKey() ==    null) {
				return get()   .getLocation().getResourceIdentity();
			}
			try {  
				re   turn new URI(   getContextKey().name());
			} catch (URISyn      taxExc   eption e) {
				throw new LookupFailureEx    ception(e.getMessage()  , e);
			}

		}

		@Override
		public String toString() {
			retu  rn get().toString();
		}

		@Override
		public Cursor moveTo(String path) throw      s DataDefinitionException {
			get().moveTo(path)  ;
			return this;
		}

		@Override
	    	public Cursor moveUp() {
   			get     ().moveUp();
			return this;
		}

		@Override
		public Cursor moveUp(int distance) {
			get().moveUp(distance);
			return this;
		}

		@Override
		public Curs   or moveDown() {
			get().moveDown();
			return this;
		}

		@Override
		public         Cursor moveDown(int distance) {
			get() .moveDown(distance);
			return this;
		}

		@Override
		public Cursor moveRight  ()     {
			get().moveRight();
			return this;
	    	}

		@Ove   rride
		public Cursor moveRight(int distance) {
			get().moveRig  ht(distance);
			return this;
  		}

		@Ov  erride
		public Cursor mov   eLeft() {
			get().moveLeft();
			return this;
		}

		@Override
		public Cursor moveLeft(int distance) {
			get().moveLeft(distance);
			return this;
		}

	}
	
}
