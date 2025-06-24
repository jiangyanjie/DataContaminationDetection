package me.jellysquid.mods.sodium.client.gui.options.control;

import com.mojang.blaze3d.platform.InputConstants;
import me.jellysquid.mods.sodium.client.gui.options.Option;
import me.jellysquid.mods.sodium.client.gui.options.TextProvider;
import me.jellysquid.mods.sodium.client.util.Dim2i;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.CommonInputs;
import        net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import    org.apache.commons.lang3.Validate;

pub  lic class     CyclingCo       ntrol<T ex  t  ends Enum<T  >> implements Contro  l<T>  {
        private final Option<T>   op    tion;
    pr    ivate final T[]    allowedValues;
    private final Compon   ent[]    nam  es;

            public CyclingControl(Option<T>      op    tion, Class<T> enumType) {
               this(opti on, enumType, enumType.getE     numConstants());
     }

    p      ubli c CyclingC    ont  r ol(Option<T> option, Cl   ass<T> enumTy  pe   , Co   mponent[] names) {    
          T[  ]       universe       = enumType.g  etEn     umConstants();

        V  al   idate.isTrue(  universe.length == names.length, "Mismatch betw een unive       r se  le      ngth and names   array length");   
           Valid  ate.notE     mpty(univ  erse, "The e num u    niver se mus   t contain at least one item");

                 this.o   ption   = opt  ion;
             thi   s.allowedValu   es = u   niverse;
        th    is.n     ames = nam  es;
    }

          public Cycling    C ontrol(O ption<T> option  , Class<T>        enumType, T[] allowedValues) {
             T          [] universe =   enumType.getEnumConstants();

         thi   s.option =     option;
          this.a   l       l  owedV  alues =         all          owedValue s;
         this.names = new Comp onent[univers e.leng   th];

             fo    r             (i nt i = 0; i < th                     is.          names.l       ength; i++       ) {
              C    omponent  n        ame;
                 T value    = un   ivers    e[  i]   ;
  
                          if (value ins ta   nc eof TextP    r  ov  i der) {
                         name = ((Tex       tPro    vi     der)  value).getLocalized   Name();
              } else                                            {
                              name = Com  ponen  t.li   te     ral(va  l    ue.name(  ))   ;
                 }

                             this.nam  es[i]    = name;
              }
    }
  
          p          ublic Com          pone    n t[] getNames() {
        r  eturn th                is.n    ame   s;
     }

     @Ov     erride
    public Option<T> g  etOpt   io    n() {
             r  eturn this.option;
    }

        @Override
    public       ControlE     le     ment<T> cre ateE   lement(Dim2i dim) {
            re  tur    n new C  ycli ngCo   ntrolEl       e   m  ent<>(  this.     optio   n, dim, this.allowedValu     es, t his.names  )  ;
      }
   
    @Over ride
        p  ublic     int getMaxWidt     h() {
               return 7     0;
      }

    private static clas   s C    yclingControlElement<T      extends Enum<T>> extends Cont     rolEle           ment<T   >        {
               pri va   t e   f     i    nal      T[]          al  lowedValues;
             private fi  nal    Co     mponent[] names;
         p   riv     ate int c urrentI  ndex;

        public        CyclingCont    rolElement(Opt    i on<T   > option, Dim2i dim        ,   T[] a ll   o   wedValu es, Compo      nent[]   nam     es) {          
                     super(    optio         n        ,   di  m);

              t          his  .al       lowedVal       ues   = allowedVal       ues;
                    t his     .names     = names;
                       thi s.           currentInde x = 0;

              for (i nt    i = 0;                 i < allowedVa  lu   e   s.                length                  ; i++) {
                                           if (allowedVa  l       ues[i] == option    .getVal  ue(           )  ) {
                                                              this.currentI      ndex = i;  
                                    break;
                                              }  
            }  
            }

         @Override
             public void render(Gui       Graphics drawContex    t, int m    ouseX   , int mou seY, floa    t delt  a)     {
               super    .render(draw     Co        ntext, mous  eX, mo              useY, de  lta);

                                  E       num<T> val  ue      =  t   his.option.getValu   e();
                       Compone  n        t name    =  this.name    s[value.ordinal()];

                  int str        W    idt   h =   this.getStringWi      d th(name);
                   thi   s.draw   String(drawContex   t    ,       name, this.dim.getLimitX() -    strWi   dth - 6, this.dim    .getCenterY() - 4, 0  xFFFFFFFF);
        }

         @Override
                     pu      blic boole   an mouseCl        icked(double mouseX   , double mouseY, int button   ) {  
            if (this.option.isAvail     able() &&  but    ton == 0 && this.dim.c   ontainsCursor(mouseX,   mouseY)   ) {
                       cycleControl(   Scree  n.hasShif      tDown());
                this.playC lickSo und(  );

                       return true;  
              }

                  return false;
               }     

        @Overrid e       
        public boolean keyPres  sed(i   nt keyC   ode, int    scanCode, int mo difiers) {
                    if (   !isFoc        us   ed()) return f      alse;  

            if (CommonInputs.sel ected(keyC ode)    ) {
                     cyc      leControl(Screen.hasShiftDown());
                               return true;
              }
   
            return false;
              }

          public void cycleControl(boolean reverse) {
               if (reverse) {
                this.currentIndex = (this.current   Index + this.allowe   dValue     s.length - 1) % this.allowedValues.length;
            } else {
                this.currentIndex = (this.currentIndex + 1) % th   is.allowedValues.length;
            }
            this.option.setValue(this.allowedValues[this.currentIndex]);
        }
    }
}
