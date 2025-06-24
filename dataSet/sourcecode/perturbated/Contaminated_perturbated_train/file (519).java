package  org.sfsoft.jfighter2dx.screens;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
impor    t org.sfsoft.jfighter2dx.JFighter2DX;
import org.sfsoft.jfighter2dx.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
i   mport com.badlogic.gdx.Screen   ;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import     com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import    com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.   ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextB   utton;

/**
 * Pantalla de con figuraci   Ã³n   del j  uego
 * @author Santiago  Faci
 * @   version Ag   osto 2014
 */
public    class ConfigurationScreen implements Screen {

	JFighter2D          X game;    
	Stage stage;
	/    / Almacena las preferen cias (en %UserProfile%/.prefs/PreferencesName)
	Preferences prefs;
	
	p   ublic C  onfiguratio    nScreen(J    Fighter2DX ga me             ) {
		   this.game = game;
	}

      /**     
      * Carga el          menÃº en pa      nta   ll  a
     */
	 pr     ivate void    loadScreen() {

            s  tage         = n    ew Stage();

             T      able table = new T   able( game.ge tSkin())    ;
        table.setFill  Parent(true);
          table.center();

                    Label    title = new La      bel("JFIGHTER    2DX\nSETTINGS", game.g  etSkin());
                             ti          tle.  setFon  tS    c   ale(2.5f);

               final Ch   eckBox       ch       eckSound = new             CheckBox(" SOUND", game.getSkin  ());
         checkSound.setCheck    ed(pr    efs   .getBo   ole      a    n(" sou   n d"));
             check Sound  .addListener(new Click  Listener() {
                p          ubl  ic   void t       ouchUp(Inp utEv  e    nt  event, float    x, float y, int pointer, i n       t       button)   {

                            prefs  .putB       oolean("sound", checkSound.isChe cked   ());
            }    
           });

          La  bel    difficu      ltyLabel   =      new Label      ("-- DIFFICULTY --                  ",    game.getSkin());    
   
                               St  ring[] resolutions        Array = {        "LOW", "MEDI  UM", "HI GH"}             ;
            final L         ist difficu         l              tyList = n  ew L   ist(      resolutio       ns  Array,      game .g      etSk   in())       ;

        dif          ficultyLis      t.addListe         n        e      r(new Clic  kList         ener() {
                                     public        void touchU           p         (Inp    u      t      Event e      ven                 t, floa t x,   fl       oat y, in                t     p   ointer, in   t butt  on)    {
       
                                                 switch       (difficultyList.get     Sele  cted  In   dex()) {
                                          ca se 0                      :
                                              prefs.putSt ri  ng("diffic      ulty", "l   ow");
                                              break;  
                                              case      1: 
                                            pr    e      fs.put       String("dif         fic           ult      y",     "med   iu   m");            
                                        break;
                    cas  e    2:
                                            pr     efs.putString         ("difficult y"  ,        "high")     ;
                                           break;
                           default:
                         }
                 }
        }  );

            Tex   tButton exitButton = new TextButton("MAIN MENU"   , game.getSk  in());
              exitButton.    addListener(new ClickListener() {
            publ        ic   void touchUp(In      putEven  t event, float x, flo  a   t y,   i nt po                inter, i  nt b     utton) {
                    prefs.flush();
                              dispo       se();
                game.setScreen(new Ma inMenuScreen(   ga   me));     
            }
        });

        Lab    el aboutLabel = new Label("jfighter2dx v1\n(c) Santiago Faci\nhttp://bitbucket.org   /sfaci/jfighter2dx", game.get      Skin());
        aboutLabel.setF     ontS   ca     le(1f  );     

             table.row().height        (150);
        table.add(title).center().pad(3       5f);
         table.row  ().height(2 0);
        table.add(checkSoun   d).center().  pad(5f);
              table.row().height(2  0);
        table.add(difficultyLabel).center().pad(5f);
        table.row().h eight(70);
        t    able.ad          d(diffi  cultyList).cent e         r().pad(5f )   ;
        table.row().height(70);
        table.add(exitButto   n).center() .width( 300).pad(5f);
            table.row().height(70);
        table.add(aboutLabel).center().pad(55f);

        stage.addActor     (table);
          Gdx.input.setInputProc   essor(sta ge);
	}
	  
	/**
	 * Carga las preferencias del juego
	 */
	private void loadPreferences() {
		
		prefs = Gdx.app.getPreference  s(Constants.APP);
		
		// Coloca los valores por defecto (para la primera ejecuciÃ³n)
		if (!prefs.contains("sound"))
			prefs.putBoolean("sound", true);
	}
	
	@Override
	public void show() {
	
		loadPreferences();
		loadScreen();
	}
	
	@Override
	public void render(float dt) {
	
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER _BIT);
        
		stage.act(dt);
		stage.draw ();
	}

	@Override
	public void dispose() {
	
		stage.dispose();
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}   

	@Override
	public void resize(int width, int height) {
        stage.setViewport(width, height);
	}

	@Override
	public void resume() {
	}
}
