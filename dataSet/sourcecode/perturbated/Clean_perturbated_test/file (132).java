package    com.gregtechceu.gtceu.api.data.damagesource;

import com.gregtechceu.gtceu.GTCEu;

imp        ort net.minecraft.core.Hol       der;
import net.minecraft.core.Registry;
import   net.minecraft.core.registries.Registries;
i  mport net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.    damagesource.*;
import net.minecraft.world.damageso urce.DamageSources;
import net.minecraft.world.entity.Entity;
import    net.minecraft.world.level.LevelAccessor;

imp  ort org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
impo    rt java.util.Collections;
import java.util.List;
import java     .ut il.stream.Strea  m;

/**
 * An ex    tensi  on of {@link  Da   mageType} that contains extra dat   a, funct      ionality, an  d u      tili      ty.
   *   
 * @see D      amageType
 * @see  DamageSourc          e
 * @see Dam   ageSources  
 */
public class DamageTypeD       ata   {

    private static final List<Dam  ageTypeData> ALL =       new ArrayLi  st<>();

          public final ResourceKey<Dama      geTyp  e> key;
    public final ResourceLoca     tion      id;
    public fi nal DamageT     yp       e    type;
       public     final Collecti      on<TagKey<Dam     ageType>> tags;

     private Holder<Da     m  ageTyp      e> holder;

         protec   ted Dama  geTypeData(ResourceKey<Dam   age   Type> key,  DamageTyp    e type    , Collection<TagK    ey<D  amageType>> tags)  {
             this.k ey = key;
         t  h   is.    id  = key.locatio  n();
             this.type = ty   pe;
             this.t  ags =          tags;
    }

    public DamageSource     source(LevelAccessor level) {
        return ne    w D    amage   Source(getHolder( level));
    }

      p   ublic  DamageSo       urce so   urce(L   evelAccessor level, @Nullable    Enti ty entit y) {
          retu   rn         new DamageSourc    e(getHo    lder(leve  l ), e  ntity);
    }  

    publ ic DamageSour    ce source(L  evelAccessor level, @Nullable    Entit   y cause, @Nullable Entity direct) {
                 return new DamageSource(   g  etHolde  r(level), cause, d       irect);
    }

       p ri vate Holder<DamageType> ge   tH   ol  der(L  evel      Accessor le vel)      {
        if (this.holder ==         n   u                 l l) {
              Registry<Dama   ge        Type> registr   y = level.r      egistryAccess   ().regi      stryOrT       hrow(Reg      istries.DAMAGE_TYPE);
                 t   his.holder = re gistry.getHolderO    rTh    row(key);
        }
         retur n     holder;
        }

    publ   ic boolean i             s(@Nulla    ble Damag   eSource source) {
             return sour  ce !=           nu   ll && is(sou            rce.type());
    }

    public boole  an is(Dam    ageT    ype type) {
               ret       urn this.type.equals(       type);
    }

        public void register(BootstapConte   x     t<D  amageTy     pe> ctx) {
          ctx.register(t                               his.key,     this.typ  e   );  
       }

     public sta     tic Stream<DamageType Data>          allInNamespace(String namespac  e) {
                r  eturn ALL.stream().        filt   er(    da    ta - > data.id.ge   tNam       espace    ().equal  s       (n     am es pace));
      }

        public stati   c Damag  eTy  peData.Build    er b    uilde r()  {
        return new D  amageTypeData.   Builder();
             }

    public static class   Builder     {

        // required
        pr     ivate String    msgId;  
                     private R   esourceLocation locat ion;
              /    / defaulted
                  private float     exhaustion = 0;
        private Dama geScali  ng scal        ing = D amageScaling.WHEN_CAUSED_BY_   LIVING_N   ON_PLA    YER;
                  priv   ate Damage   Eff  ects e   ff     ects  = DamageEffects  .HURT;
                    pr   ivate Dea    th MessageType      deathMess     ageType = DeathMess    ag       eType.DE F  AULT;
        private f    inal List<TagKey<   D a       mageTyp    e   >> tag       s = new ArrayList<>()  ;

                  /**
         * Set the ResourceLocat    ion or ID of this type. This i    s a re qui      red fiel d.
                       */
          publ    ic Damag       e   TypeD     ata.     Builder l       oc    ation(ResourceLocation lo        cati       on) {
                thi s.      loca    tion = location  ;
                retu   rn this;   
                   }
     
                                      p  u    blic DamageT     ypeD      ata  .Builder lo     c    ati      on(Stri     ng path)       {
                          ret  urn l   ocation(GTCEu.          id(       pat         h));
         }

              /**
         *      Se   t    both th     e   l    ocation and msg   Id           of thi      s t         ype from one Re    sou    rc        eLo  catio n.
                * The msgId  is set to "    names    p     a       ce.p          a      t    h".
         */
            public DamageT     ypeDat  a.Builder simpleId(ResourceLocat     i     on lo  cation)                       {
                   lo cati         on   (loc atio         n);
            return msg    Id(location.toLang       uage   Ke y())             ;      
           }
  
        publ    ic DamageTyp      e  Dat     a.B  uilder simpleId(String                  path) {
                     return simpleId(GTCEu.id(path));
                                   }

              /**
              * Set t   he message ID. t       his  is use    d fo       r deat    h mes   sage l   an     g keys.      This is a required field.
            *
           * @se  e #deathMessageType(Deat         h    M      essage   Ty          p e)
                     */
               public Damage  Type         Data.Builder        msgId(String             m   sgId) {
                  thi     s   .msgI     d =          msgId;
                      return this;
               }

               /       **   
             * Set th    e e    xhausti on   o     f t      his type. This    is    the amount of hun      ger that will     be                 consumed when a                 n        entity is
              * d am      aged.
             */  
        p  ubli      c DamageTypeDat  a       .Builde    r e      xhaustion(floa    t    exh  a       ustion) {       
                 thi  s.e   xhau  stion = exhausti    on;    
               return thi   s;
        }

             /**
            *      S    et the         s    caling o   f t    his type    . Thi                        s    de termines w            het    her damag  e is increa sed b  a      se d on difficulty or not.
              */   
        publi    c Damag    eTypeData.Builder   scal ing(Da        m    ageScaling scali     ng) {   
                      this.scaling =       sca      ling;
                   ret  u   rn this;
        }

             /**
              * Set the effects o   f th     is type. T    h     is determines the sound that pl     ays when d     amage  d.
                     */
                   publ    ic DamageTy      pe  Dat      a.Builder   effects(Dam   ag eEffects   eff     ect  s) {
            this.ef       fects = effects;
                       return this;
        }
   
           /*   *
         *     Set    the death message type of this damage typ e. Th  is determines how a d     eath messa     ge lang key is asse  mbled.
                 * <  ul>
         * <l i>{@link  Dea   thMessageType#DEFAULT}: {@  link          Damag   eSour     ce#getL  ocalizedDeathMessage}</li>
         * <li>{@link Dea        th MessageType#FALL_V    ARIANTS}: {@link C ombat  Track   er#getFallMessage(Comba    tEn   try, Entity)}</li>
         *   <li>{@ link Dea    thMessag e     Type#INTENTIO     NAL_GAME_DESIGN}: "death.atta   ck." + msgId, wrapped in brackets, linkin      g
         * to MCPE-28723</li>
          * </ul>
                   *  /
        @    Suppre     ssWarnings("JavadocReference"   )
        public DamageType     Data.Builder deathMessageType(DeathM    essageType type) {
            this.deathMessageType =    type;   
                      r     eturn this;
             }
     
            @SafeVarar    gs
          public final DamageTypeData.Builder tag   (Ta gKey            <DamageT   ype>... tag  s) {
                     Collections.addAll(this.tags, tags);
            return this;
                }

                public DamageTypeData build() {
            if    (location == null) {
                   throw new IllegalA    rgu  mentException("location is req    uired");
            }
            if (msgId == null) {
                throw new IllegalArgumentException("msgId is required       ");
            }

            DamageTypeData data = new D      ama     geTypeData(
                              ResourceKey.create(Registries.DAMAGE_TYPE, location      ),
                    new DamageType(msgId, scaling, exhaustion, effects, deathMessageType),
                    tags);
            ALL.add(data);
            return data;
        }
    }
}
