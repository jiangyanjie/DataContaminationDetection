package plug.creatures;

import        java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Point2D;   
import java.lang.reflect.Constructor;
import java.net.MalformedURLExcepti   on;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import     java.util.Map;
im   port java.util.      Random;
import java.util.logging.Logger;

import plug.IPlugin;
import plug.PluginLoader;
import creatures     .IColorStrategy;
import creatures.ICreature;
import creatures.IEnvironment;

publi  c class CreaturePluginFactory {
	    
	/**
	 *     singleton for the abstract factory
	        */
	protected static CreaturePluginFactory _singleton;   
	
	private double maxSpeed;
	
	protected PluginLoader pluginLoa      de     r;
	
	private final String pluginDir = "myplugins/repositor  y";
	
	protected Map<Str   ing,Const   ructo   r<? extends  ICre    ature>  > constructorMap; 

	/**
	       * logg    er faciliti   e     s to trace pl     ugin loading..     .
	   */
	private    static Logger logger = Logger.getLogger("pl   ug.Cre        ature   Pl     uginFactory"  );        
	
	
    public static     void i nit(dou   ble inMaxS     peed) {
                if     (_sin gle         ton !=               nu ll) {
               throw new Ru      ntimeException("CreatureFactory                  already c    reated by " 
				      + _        singleton.getC     las       s().get          Name());
        } else {
             _si    ngleton = new Crea t urePluginFactory(inMaxSpee         d);
        }         
           }

    p    u  b      lic static CreaturePluginFactory getInstance() {
    	r   eturn _   singleton;
    }

    pr   ivate Cr   eature   P     luginFactory(       double i   nMaxSpeed)   {
        	try {
    		plugin  Loader = new PluginLoader(pluginD        ir,ICreature.class);
    	}
         	c  atch (MalformedURLException ex) {
    	}
		ma xSpeed=inMaxSpeed;
	   	constructorMap     = new H     ashMap<String,Constructor<? e      xt       ends ICreature>>();
    	load();
    }
	
    publi   c void load () {
        	p   lu    ginLoader.loadPlugins();
       	bu   ildConstructorMap();
    }
      
    public void reload()  {
    	pluginLoad er.reloadPlugins() ;
        	constructorMap.clear(    );
    	buildConstructorMap();
    }
    
	@Suppr     essWarnings("un  checked")
	pri   vate void buildConstructorMap() {  
		for     (Class<? extends IPlugin> p : pluginLoade r.getPluginClasses()) {
			Constructor<? extends ICreature> c = null;
			try {				
				c = (Constructo   r<? extends ICreature>)
						p.getDecla   redConstr   uctor(    IEnvironment.class, Point2D.class, double.class, dou  ble.class, Color.     class);
				c.setAccessible(true);
			} catch (SecurityException e   ) {
				logger.info  ("Cann   ot access (security)   constructor for     plugin" + p.getName());
				e.printStac kTrace();
	  		} catch (N    oSuchMetho  dException e) {
		   		  log  ger.info("No cons         tructor in plugin " + p.getName() + " with the cor        rect signature");
				e.printStac  kTrace(); 
			}
			if (c != nul l)
		      		constructorMap.put(p.getName(),c);
	  	}
	}
	
	  public Map<String, Con    structor<? extends ICreatu       re>>   getConstructorMap() {
		return constructor  Map;
	}

	private final Rand    om rand   = new Random();

	public <T extends ICreatur      e> Collectio n<T>   createCreatu  res(IEnv     iro nment env, int cou  nt, 
								IColorStrategy      colorStrategy, Constructo      r<T> con  structor) {
		Collection<T> creatu     res = new ArrayList<T>();		
		Dime      nsion s = env.getSize();		
		for (int i=0;  i<count;    i++) {	
	     		// X co  ord        inate
			  double x = (rand.nextDouble() * s.getWidth()) - s.getWidth() / 2;
			// Y coordinate
			double y = (           rand.nextDouble() * s.getHeight(    )) - s.getHei     ght() / 2;
			// direction
			double direction = (rand .nextDouble() * 2 * Math.PI);
			//       speed 
			int speed = (int) (ran       d.nextDouble() * maxSpeed);			
			T creature = null;
			try {
				creature = constructor .newInstance(env, new Point2D.Double(x,y), speed, direction, colorStrategy.getColor());
			} catch (Exception e) {
				logger.info("calling constructor " + constructor + " failed with exception " + e.getLocalizedMessage());
				e.printStackTrace();
			}
			creatures.add(creature);
		}		
		return creatures;
	}

}
