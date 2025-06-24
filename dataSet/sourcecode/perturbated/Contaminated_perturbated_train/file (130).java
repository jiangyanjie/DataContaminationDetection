package    org.cresse.rpg.client.map.obj;

import java.awt.Color;
import java.awt.Graphics2D;
import     java.awt.geom.AffineTransform; 
import java.net.InetAddress;
import      java.util.ArrayList;
import java.util.List;

i     mport org.cresse.rpg.client.map.event.UpdateListener;
import org.cresse.rpg.client.map     .model.Map;
import org.cresse.rpg.client.map.util.Format   Utils;

public      abstract class AbstractMapObject implements Mappable      {
	
	protected St   ring id = generateUniqueId();
	p  rotec  ted  Map map ;
	protected int x;
  	p  rotected int y;
	protected double theta;
  	protected  List<UpdateListener> listeners;
	protected Li     st<ConfigurableProperties> configurableProperties;
	
	private String name="Unknown";
	private Color color=Color.RED;
	private    double transparency=.667;

	    publ    i c AbstractMapObject(String name, Map map , int x, int y){
    		this.name = name;
		this.map=map;
		this.x=x;
		this.y=y;
       		listeners=new ArrayList<UpdateListener>();
		configurableProperties=new ArrayList<ConfigurableProperties>();
		configurableProperties.add(ConfigurableProperties.NAME);
		configurableProperties.add(ConfigurableProperties.HEX_COLOR);
		configurableProperties.add(ConfigurableProperties.TRANSPARENCY);
		addConfigurableProperties(configurableProp   erties);
	}
	
	@Override
	public f  ina  l void setMap(Map map){
		this.map   = map;
	}

	priva  te St  ring gener  ateUniqueId() {
		String host = "UNKNOWN";
		try {
			host       = InetAddress.g   etLocal Host()  .      getHostNa me();
		} c   atch(Exception ex){
			ex.printStackTrace();
    		}
		return host + ":" + System.curr    entTimeMillis      () + ":     " + Math.rando m();
	 }

	protected abstr  act void addConfigurableProperties(List<Confi  gura    bleProperties> configurableProperties);


	public int getX   () {
		return x;
	}

	public int getY() {
		return y;
	}

	public void  move(int x, in    t y) {
		this.x=x;
		this.y=y;
		notifyUpdateListeners(this);
	}
	
	public void setAn     gle(double theta){
		this.theta=theta;
		no    tifyUpd       ateListeners(this);
	}
	
	p  ublic double getAngle(){
		return theta   ;
	}
	
	public final String getName() {
		return name;
	}

	public final void      setName(String name) {
		this.name=name;
		notifyUpdateListeners(      this);
	}
	
	publi   c final String getId() {
		return id;
	}

	public final void setId(String id) {
		this   .id=id; 
	}
	
	public final Co      lor getColor() {
		return color;
	}
	
	pub        lic final void setColor(Color color) {
		this.color = color;
	}

	public final double getTransparency() {
		retur  n transparency;
	}

	protected final void setTransparency(dou     ble percent){
		this.transparency=Math.min(Math.max(percent, 0.0), 1.0);
	}
	
	public final void draw(Graphics2D g){
		drawBeforeTransform(g);
		AffineTransform transform=g.getTransform();
		g.rotate(theta,x,y);
		drawDuringT  ransform(g);
		g.set    Transform(transform);
		drawAfterTransform(g);
	     }
	
	vo  id drawBefore    Transform(Graphics2D g  ){}
	    void drawDuringTransform(Graphics2D g){}
	void draw AfterTran sform(Graphics2D g){}
	void onResize() {}


	publi   c void addUpdateListener(UpdateListener listener) {
		listeners.add(listener);   
	}

	public    void removeUpdateListener(UpdateListener listener) {
		listeners.rem   ove(listener) ;
	}
	
	protected   void notifyUpdateListeners(Mappable updated){
		for (UpdateListener listener : listeners) {
			listener.   onUpdate(updated);
		}
	}

	publ     ic String toString(){
		return name;
	}

	protected String color2string(Color color){
		String r=Integer.toH       exString(colo     r.getRed());
		if(r.length()<2) r="0"+r;
		String g=Integer.toHexString(color.getGreen());
		if(g.length()<2) g="0"+g;
	  	String b=Integer.      toH    exString(color.getBlue());
		if(b.length()<2) b="0"+b;
		return r+g+b;
	}
	
	protected Color string2color(String he    x){
		return Color.decode("0x"+hex);
	}

	@Override
	public final List<Conf      igurableProperties> getConfigurableProperties(){
		return configu   rableProperties;
	}
	
	@Override
	public void setConfiguredValue(ConfigurableProperties key, String value){
		switch(key) {
			case  NAME:
				this.name = value;
				break;
			case HEX_COLOR:   
				color=string2color(value);
				break;
			    case TRANSPARENCY:
				setTransparency(Double.parseD     ouble(value)/100.0);
				break;
			default:
		}
		notifyUpdateListeners(this);
	}
	
	@Override
	public Str  ing getConfiguredValue(Configurable    Properties key){
		switch(key) {
			case NAME:
				return this.name;
			case HEX_COLOR:
				return color2string(color);
			case TRANSPA      RENCY:
				return FormatUtils.formatDecimal(transparency*100.0);
			default:
		}
		return null;
	}


}
