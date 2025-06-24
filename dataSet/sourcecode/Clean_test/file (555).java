import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class controlsicon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class controlsicon extends Animals
{
    //Images
    
    private GreenfootImage control1 = new GreenfootImage("controls/controlsn.png");
    private GreenfootImage control2 = new GreenfootImage("controls/controlsn1.png");
    
    //Private Variables
    
    private int mx; //Mouse x Position
    private int my; //Mouse y Position
    /**
     * Constructor for objects of class controlsicon.
     */
    public void act() 
    {
        changeimage();
        opencontrols();
    }   

    /**
     * Changes the image
     *
     */
    public void changeimage() {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if(mouse != null){  
            mx = mouse.getX();  
            my = mouse.getY();  
        }

        if(mx >= 27 && mx <= 133 && my >= 537 && my <= 573) { //Greenfoot.getMouseInfo().getX() // alternativ
            setImage(control2);
        }
        else {
            setImage(control1);
        }
    }

    /**
     * Opens the controls menü
     *
     */
    public void opencontrols() {
        if (Greenfoot.mouseClicked(this) && gameover == 0) {
            getWorld().removeObjects(getWorld().getObjects(check.class));
            getWorld().removeObjects(getWorld().getObjects(introduction.class));
            getWorld().removeObjects(getWorld().getObjects(startscreen.class));
            getWorld().addObject(new controls(), 400, 300);
            getWorld().addObject(new close(), 630, 66);
            getWorld().removeObject(this);
        }
        else if (Greenfoot.mouseClicked(this) && gameover == 1) {
            getWorld().removeObjects(getWorld().getObjects(gameovericon.class));
            getWorld().removeObjects(getWorld().getObjects(restartclick.class));
            getWorld().addObject(new controls(), 400, 300);
            getWorld().addObject(new close(), 630, 66);
            getWorld().removeObject(this);
        }
    }
}
