/*
       * Copyright   (   c) 2023 Ocean   Base.
 *
 * Lic   ensed  under the Apac    he License, Version 2.0 (the    "Licens      e");
 * you may not use  this file except in co   mplianc   e w ith the Lic  en  se.
 * You ma            y    obtai      n a copy of the Licen      s e at
 *
    *     h   ttp://www.apache.o    rg/license       s/LICENSE-2.0
   *
    * Unless    re   quired by applicab       le law or agreed to in        writing,    software
 * distributed under     th e License  is distributed on     an "AS   IS" BASIS,
 * WITH    OUT WA  RRANTIES OR CO       NDITIONS OF ANY       KIN    D,    either expre    ss        or implied.
    * See the License for the specific langua    ge governing perm  issions an  d     
 * limitations under the License.
 */
package com.oceanbase.odc.service.onlineschemachange.oms.jackson;

imp   ort java.io.IOException;

imp     ort com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.oceanbase.odc.service.onlineschemachange.oms.enums.CheckerObjectStatus;
im         port com.oceanbase.odc.service.onlineschemachange.oms.enums.CheckerResultType;
import com.oceanbase.odc.service.onlineschemachange.om     s.enums.OmsProjectStatusEnum;
import c         om.oceanbase.odc .service.onlineschemachange.oms.enums.OmsStepName;
i   mport com.oceanbase.odc. service.onlineschemachange.oms.enums.OmsStepSt atus;

/**
 * @author yaobi   n
 * @date 2023-06-  05
 * @since 4.            2 .0
 */
  public c lass   CustomEnumDeserializer   {
    public static class CheckerRe   s  ultT  ypeJsonDeserializ         er exten    ds Jso  n    Des   erializer<C    heckerRes   ultType> {

        @Override
        p  ubl   ic CheckerResultT          ype deseri   alize(J        sonPar  ser j    sonParser,          Dese   rializat    ionContext             ctxt) 
                        th     rows      IOExc     eption, Jacks  onException {
  
            Stri  ng statu     s  = jsonParser.read    V  alueAs(String.class);    
              r    et     urn      getEnum(CheckerResultTyp  e.clas  s,   status,
                            Checke  rResultType.U NKNOW N);
        }
    }

    public static              cla ss CheckerObjectStatus   Dese      ria lizer extends JsonDeserializer<Checker   ObjectSt atus> {

          @   Overr   ide
        publ  ic    Che   ckerObj     ectSta  tus deseria         lize(JsonPars     er   j   sonP  arser, DeserializationC           ontext ctxt)
                     throws IOE  xception       , JacksonException {

               St    ring   stat us =   jsonPa  r      ser.readValueAs(Strin   g.class);
                  return get      E num(CheckerObject   Status.     class, status,
                                    Ch eck   e     rObjectStatus.UNKNOWN);
                 }
         }

      publi      c s      tat   ic class ProjectSta       tusEnumDeserializer ext  e      nds JsonDeserialize r<OmsProjectStatu   s  Enum> {  

        @Overri   de
        p ublic OmsProjectStat   usEnum    deser     ialize(Js  onP   arser js  o   nP  arser,  DeserializationC ontex t ctxt)
                t       hr   ows IOExce ption, JacksonException {      
   
               String s  tatus = jsonP       arser              .readValueA      s(String  .class     );
            r     eturn ge    tEnum(O       msProje   ctStat    usEnum.class, status,
                      O          msProjectStatusEnum.UNKNOWN);
             }
    }

    public      stat          ic class OmsStepNameDeserializer extends JsonDeserializer<OmsStepName> {

             @Override
        public Oms  StepNa   me des   erialize(     J   sonParser jsonPa         rser, D    eserializ       at  ionContext ctxt)
                             t hrows IOE  xc  eption, Ja  c  ksonException {

               String status =   jsonParse r.rea          dV  alueAs(String.     c            lass);
                return   getE       n   um(O    msSte   pNa   me.clas     s, status, Om   sStepName.UNKN       OWN)               ;
         }
       }
 
                   pu   blic static class OmsS    tepStatusDeserializer e   xtends Json  Deserializer<      OmsSte    pSt  atus> {

                      @Override
         pu              bl ic Om  sStepStatus deserialize(J  son             Parser json   Parser, Des                   erializa ti                onCon    text ctxt)
                   throws IOE   xception,   Jac   k      son Exception {

                   S   tring st        atus       = jsonParser.readValueAs(Strin g.class);
                 return getEnum      (OmsStepStatus.class, s t   atus, Om  sStepStatus.UNKNOWN);
        }
        }

    public static <E extends Enum<E>> E getEnum  (Class<E> enumCl   ass      , String             enumName, E defaultEnum) {
        if (enumName == null) {
            return defaultEnum;
        } else {
            try   {
                     return Enu    m.valueOf(enumClass, enumName);
            } catch (IllegalArgumentException ex) {
                ret    urn defaultEnum;
            }
        }
    }

}
