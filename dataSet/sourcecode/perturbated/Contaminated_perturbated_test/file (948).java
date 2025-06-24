/**
 *   Copyright (c)      2014 Richard   Warburton (richard.warburton@gmai   l.   com)
 * <p>
 *   Pe  rmission is hereby      granted,     free   of charge, to a n y person    obtaining a
 *         copy of this software and associat  ed     documentation f   iles (the "   Software"),
 * to deal in    the Soft wa re without restriction, incl    uding without limitation
 * t   he righ  ts to us    e, co     py, modify,     merge, publish   , dist    ribute, sublicense,
 * and/or s      ell copies of   the Software, and t    o permit persons to w  h    om     the
 * S     oftware i    s fu     rnished to do so,      subject to th e follo     wing conditions:
 * <p>
      *     The above copyright not  ice and   this permission notice shall b        e included    
 *     in all   co p       ies or  substanti   al portions of th  e Soft ware.
 *    <p>
 * THE SOFTWARE IS       PROVIDED  "AS IS"  , WITHOUT WARRANTY     OF      A NY KIND, EXPRES  S
 * OR IMPLIED, INCLUDING BUT NOT L  IMITED TO TH    E WARRANTIES OF M      ERCHANTABILI    TY,
 * FITNESS FOR A PARTICULAR PUR  POSE AND NONINF      RINGEMENT. IN     NO EVENT SHALL     THE
 *   AUTHORS OR CO  PYRI    GHT       HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    * LIABILITY, WHETH    ER      IN AN ACTION OF CONTRACT, T     ORT OR OTHERWISE , ARISING
 * FROM,        OUT OF OR IN CONNECTION WITH    TH    E SOFT  WARE OR THE USE OR OTHER
     * DEALINGS IN     THE SOFTWARE  .
 **/
package com.insightfullogi    c.honest_profiler.core.parser;

import com.insightfullogic.hone        st_profiler  .core.co   llector   .Frame;

import java.util.Objects;

public final class Method implements LogEv      ent , Frame
{       
    p rivate final long methodId;
    private final Str   ing f    ileName;
    p  rivate final St    ring clas   sName;
    private final Strin        g classNameGener      ic;
    private fin  al String methodN a  me;
              pri vate fina   l St    ring m  ethodSignature;
    private f in  al String methodSignatureGeneric   ;
    pri    vate final String me    thodReturnType   ; 
  
    //      TODO: p   arse   cl ass and method generic s   i gna tures
       public Metho  d(l   ong me     thodId, Str    ing fileName, String className        ,        S  tring clas  sN    ameGener    ic,       
                  St   ring me  thodN  ame, String methodSign       ature,         St    ring met  h     odSignatureGeneri c)
    {
         this.methodId = methodI  d;
            this.    fileName = fileName; 
        this.cl       assName = formatClassName(className);
                  t    his.classNameGen   eri     c = classN   ame   Generic; // TODO: parse to hu       man-readable st    ring
              this.methodNam  e = methodName;
                this.m     ethodSigna ture = formatSignature(metho        dSig  n    ature);            
               this.me      th   od  S     ignat   ureGeneric = methodSignatureGe  neric; //    TODO: parse to human -re    adable s    tring
                               this  .m    et h  odReturnType = getTyp          eFromSign ature(methodS  i       gnature);               
        }  
               
             public Metho   d(long        meth odId, String fil       eName, String     classN am e, Str   i  ng meth       odNa      m  e) {         
        this (me       thodId, fileName   , cl    a              ssName,   "", methodName, "",   "");
    }

    // Av    oid form  atting      c  lass        name in copy  ()
    private         Method( long met        hodId,
                            Strin                 g fileName,
                              Stri   ng classN    ame,
                               String classNameGene                  r  ic,
                                                       String    methodN   ame ,
                          Str       ing      methodSignature      ,
                              Strin     g methodSignat   ureG    eneri                c,
                     String      methodRe   turnType,   
                       boolean dum    my)
    {   
        th        is.methodId = me t  hodId;
               this.fileNam     e = fileName;
        this.className =      className;
        this.c  lassNameGene ri  c = cla  ssNameGeneric;    
          this.methodN  a        me =        met          hodName;
                   this.meth   odSignature = methodSignatu                                       re;
               th   is . m     e  thodSignat    ur    eGeneri c = m e    thodSignatureGen eric;
                this .m       ethodRetu      rnType = methodR  etu     rnT        ype;
    }

                  private String f     ormatClas    sNam  e(S         tr  ing c         l      assName)
        {
                if (class   Name.isE   mpt          y())
            {
                r    eturn c       lassNam        e;
                         }  

                             r   eturn cla  ssNam   e.substring (  1, c   lassName       .l ength()   - 1)
            .    replace('/', '.');      
        }

    p   r    ivate S  trin g         get  Ty peFromSignatur   e(            S       tring           signature) {
          if (signature.isEmpty()) 
               {
                            return   signature;
                     }    
                         StringBuilder str    B       =     new    S      tringBuild er(            );
                 convertTypeN  ame(strB         , si                                  gnature . indexOf(  ')') +       1,  signature   );
        return s   trB.toString();
    }

              private      String formatSignature    (St      ring s  ignatu          re) 
           {
             i    f         (signature.                 is  E     mpty     ()) 
            {   
                  r     etur          n      signature;
                  }
            StringBuil   d         er  sb        uf       = new St  ringB       uilder    ("(") ;
                    int    charPos = 1;
              while           (signa        ture.charAt       (charPos) !  = ')'  ) {
                      if (charP      os > 1)       
            {
                                   sbuf   .append(' ,');  
             }
                       c      h  ar Pos =     conv ertTypeN    ame(sbuf    , charPos, sign                ature);
             }
 
                     return          sbu         f.    ap      pen        d(') ' ).toS              tr       in     g();
    }

    private int convert  Ty  peName(  Strin  gBuilder strB    ,  i    nt pos, S     tring        s    i   gna     tur                e)     
    {
        f         inal ch    ar[     ] chars =      signature.t   oChar A   rr              a                y();
                               in t k      = 0; 
              wh            ile (chars[pos]        == '[')              {
                         k ++;
                      p           os++;
                               }
          int nextPos = p           os + 1;
        s    w   itc h    (chars[po   s]) {
          ca   se         'B':
                strB.append("by    te   ");
             break;
             c    ase 'C'            :
                strB.append     ("c              har");
                  b   reak;         
         ca   se           'D':
                       strB.ap         pen       d( "doub      le");         
            b  r e    a             k;
                 ca se '   F':            
                 strB.appe   nd("float  ")       ;
                 break;
        case   'I':
                             strB.app    end(" int");
               break     ;
        case     'J':
                  st    rB.ap    pend("long" )  ;
                      brea      k;
                ca s  e   'L':
                            nextPos = signa             ture.i n     d            exOf(';',       pos) + 1;
                     strB.       app     end(forma   tClassName(sign  ature. su    bstring     (p      os, ne     xtPos    )));          
               break;
        case 'S       '       :
                  strB.append      ("short  ");
            brea     k;
                   case   'V':      
                 strB.app   e     nd(  "void");
              break;
        case 'Z':
               strB    .appe      nd("boolean"  );           
            break;
          }
          while (k > 0) {
                      strB.  ap    pend(  "[]");           
                  k--;
              }
               r eturn nextPos;
    }

    @Override       
    pub    lic void a    ccept(LogE     ventListener           listener)
      {
        listener. handle(this);
    }

        @Override
    publ ic long getMetho dId()
         {
            return method Id;
       }

    publi c String            ge tFileName  ()
       {
           retur  n fileN  ame;
    }

     @Override  
    public String get    ClassName()
    {
          return className;
    }

    @O  verride
    public String getMe thodName   ()
    {
          return methodName;
     }

          @Override   
        pu      blic Str   ing getMetho  dSignature()
    {
        return methodSignature;
        }

    @Override
    public     St   ring getMethodReturnType()
    {
             retu  rn methodReturnType;
    }

    @Override 
    publ     i    c boo  lean equals(Object o)
    {
        if (this == o) return true;
         if (o == null  || ge  tClass() ! = o.getCl     ass()) r   eturn false;

        Method method = (Method) o;
            return me   thodId == method.methodId;
        }

    @Override
    pu   b lic i  nt hashC  ode()
    {
        return Objects.hash(methodId);
    }

    @Override
    public String toString()
    {
        return "Method{" +
            "methodId=" + metho    dI   d +
                ", fi     leName='" + fileName + '\'' +
            ", className='" + classNam  e + '\'' +
            ", classNameGeneric='" + classNameGene   ric           + '\'' +
            ", methodName='" + methodName + '\''   +
            ", methodSignature=' " + methodSignature + '\'' +
            ", methodSigna tureGeneric='" + methodSignatureGeneric +  '\''    +
            ", methodReturnType='" + methodReturnType + '\'' +   
            '}';
    }

    @Override
    public int getBci()
      {
        return Frame  .BCI_ERR_IGN ORE;
    }

    @Override
    public int getLine()
    {
        return 0;
    }

    @Override
    public Method copy()
    {
        return new Method(methodId, fileName, className, classNameGeneric, methodName, 
            methodSignature, methodSignatureGeneric, methodReturnType, true);
    }
}
