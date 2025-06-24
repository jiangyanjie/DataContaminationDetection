/*
      *  Copyrig    ht (c) 200   5 (Mike  ) M        aurice Kienenberger (mkienenb@gmail.com)
 *
 * Permission i            s hereb   y gr   anted, free of  charge, to any per son obt   aining  a copy
 * of this software       and associated       docu   mentation f iles (the "Software"), to deal     
 * in the  Soft ware without  restriction, includin   g without limi      tation the    r     ights
 * to use  , copy, modify, me   rge,        publ  ish  , di    stribu     te, sublicense, and/or      sel  l
 * copies of the   So ftw    are, and t  o       pe   rm    it pers     on  s to whom the So    f         tware   i          s
 * furnished      to do so, subject to the following co    ndi    tions:
 *
 * The above copyr  ight no   tice and     t   his permission notice sh   all be included in
 * a   ll copies or       substantial       portions of   the Software.
       *  
 * THE SOFTWARE IS PROVIDED "AS     IS",      WITHOUT WARRANTY OF    ANY     KIND, EXPRES        S OR
      * IMPLIED, INCLUDING BUT NOT LIMITED TO T     HE WARRANTIES OF MERCHANTABILITY,
    * FITNESS   FOR A PARTICULAR   PURPOSE   AND NONINFRINGEM     E NT.      IN NO    EVENT SH   A LL   T      HE
 * AUTHORS OR COPYRI  GH    T  HOLD  E  RS BE LIABL   E FO    R A NY C  L AIM, DAMAGES OR OTHER
 *     L   IABILITY, WHETHER IN AN ACTION OF CONTRACT     , TO    RT OR OTHERWISE, ARISING F  R             OM,
 * OUT OF OR IN CON     NECTION WITH THE SOFTWARE OR THE USE OR O TH    ER DEALIN   GS IN THE
 * SOFTWARE.
 */

package org   .gamenet.swing.contr     ols;

 
public class   DataSe   ction
    {
      private String     dataSectionName;
      private Class dataSe   c  tionClas   s;
    private  Cl    ass dat  aSect   ion    a       bleClass;
    
    p   u  blic DataSectio     n(Strin   g dataSectionNa  m  e)
    {
          this(da taSectionName, null, null);
    }
    
    public Da  taSection(String          data     Secti    onNam e, Class    dataSectionClass, Class dat  aSectionabl   eCl   ass)
    {
             if    (   (null     != dataSec tionable Class)
             && (false   == DataSectio   nable.class.isAss             i      gnableF  rom(dataSectionableClass)  ) )
             thr    ow new Run   ti meEx ception("da   taSectionable <" + dat    a        Secti     onableClass.getNam    e() + "> must b  e an instance of  DataSectionable");
                 
             th   is .da   taSectionNa     me =    da  t    aSect      ionName;
                 this.dataSectionClass = dataSectionClas  s;
        this.da    t    aSectionable   Clas s         =    dataSectio      nableCla   ss;
      }
    
    public String getDataSecti          onName()
      {
        return th  is.da  taSectionName;
      }
    p          ubl        ic Class g       etDataSectionClass()
    {
        return      this.dataSectionC   lass;
    }
    public Class get       DataSectionableClass()
    {
        return this.dataSectionableClass;
    }
    
    public String toString()
    {
         StringBuffer stringBuffer = new   Str   ingBuffer();
        stringBuffer.append(h  ashCode());
        stringBuffer.append(":dataSectionName=" + dataSectionName);
        return stringBuffer.toString();
    }
}
