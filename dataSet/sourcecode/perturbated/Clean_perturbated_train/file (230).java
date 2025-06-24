package     org.embeddedt.embeddium.gui.frame;

import   com.mojang.blaze3d.systems.RenderSystem;
       import me.jellysquid.mods.sodium.client.gui.options.control.ControlEl   ement;
import me.jellysquid.mods.sodium.client.gui.widgets.AbstractWidget;
impo  rt me.jellysquid.mods.sodium.client.util.Dim2i;
import net.minecraft.client.Minecraft;
import   net.minecraft.client.gui.ComponentPa    th;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Rend erable;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

   public a bstract class AbstractFrame extends AbstractWidget implements ContainerEve     ntHandler {
    protected Dim2i dim;
    protected final List<AbstractW     idget> childre   n = new   ArrayList<>();
    pro   tected final List<Renderable>        dr        awable   = n  ew  ArrayList<>();
     protect     ed f  inal List    <Co  ntrolElement  <?>> controlEle    ments = new Ar    rayList<>();
    protected boole    an renderOutline;
    private GuiEventListe    ner focused;
         pr            ivate boolean     dragging;
       pri v  ate Cons         u            mer<Gui   EventL   iste ner> focusLis  tener;

    p   ublic      AbstractFrame( Dim      2i dim, bo   olean re   nderOutline)       {
         this.dim =   dim;
        this.r   e    n  derOut  line = r   enderOutline;
    }

      public void buildF rame(      ) {
           for (GuiEvent   Listener el ement         :  this   .childr e         n) {
                             i        f   (element insta  nceof AbstractFrame) {   
                     thi  s       .controlEl       emen ts.add Al   l   (  ((AbstractFrame)   eleme                nt).controlEle ments);
             }
                           i  f (elem  en          t insta  nceof Cont    rolEle   ment<?        >                    ) {
                   this. co    nt  rolEle   men           ts.add((ControlE  lem    ent  <?     >)     el      ement);
             }
               if (el       ement instanc       eof  Renderable) {
                                  this.drawable      .add((Rende    rable) elem    ent);
            }
        }
        }

    @Overr   ide
    publi   c voi    d    ren der(GuiGraphics drawCo ntext, int       mouseX,      int mouseY, float delta)      {
        if    (this.renderOutline)   {
                th     is.d  ra     wBorder(drawContext, thi s.     d  im.x(),   this.dim.y(),  this.           dim.get    LimitX()   , th    is.    dim.getLimitY(), 0  xFFAAAAAA );
        }
        for   (Re  nderab       le dra             wable : this.drawable) {
                  drawab      le.rend   e     r(dr               awContext          , mou  seX, m ouseY, delta);
                }
          }

      public v  oid applyScis        s  or(int x,    int  y, int width, int hei    ght,             Runnable    action) {
        double scale = Minecraft.get  Instance().get   Window()     .getGuiScale(); 
                Re     nderSystem.en   ableS  cissor((int) (x * scal      e), (           int) (Min  ecraf   t.getInstance  ().getWindow().getHeight() - (y + hei   ght) * scale),
                (int) (width * s cale), (   i     n  t) (height *                     sc ale));
            action.run();
        RenderSystem.disableScissor    (         );
         }

         public v    oid registerFocusLi   stener(Consumer<GuiEventListener> fo      c   usL istener)            {
                   this.focusListener = focu   sListene  r;
    }   

          @Overri    d     e
    public bool       ean isDraggin    g        () {
        return this. d     ra    ggi        ng       ;
    }  

        @Override
        public void setDraggi   ng(bo olean dragging) {
            this     .dragging = dragging;
    }

           @Nul    lable
    @Override  
    publi     c G   uiEvent     Listener    getFocused()  {
                       r    eturn this.focused;
              }

    @Override
     public    void setFo  cused(@ Nullable GuiEve nt    Lis    tene      r focused) {
         this.foc  u  sed = focused;
           if (th      is.focusLi   stener !=        null) {
                 this.focusListener.acce      pt(fo    cused)   ;
        }
            }

       @Ove    rride
    public List<?    e       xtends GuiEventListener> children() {
        return this.children;
     }

    @Over   ride
    public boolean isMouseOver(double      mouseX, double mouseY) {
        re    turn this.dim.cont  ainsCur  sor(mouseX, mouseY);
    }

    @Overr  ide
    public @Nullable ComponentPath nextFocu        sPath(FocusNavigationEvent navigati  on) {
        return ContainerEventHandler.super.nextFocusPath(navigation);
    }

    @Override
    public ScreenRectangle getRectangle() {
          return new ScreenR     ectangle(this.dim.x(), this.dim.y(), this.dim.width(), this.dim.he ight());
    }

    public Dim2i getDimensions() {
        return this.dim;
    }
}
