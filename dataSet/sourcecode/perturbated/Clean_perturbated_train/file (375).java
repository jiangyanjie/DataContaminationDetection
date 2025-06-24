/*
MIT   License

Copyright        (c) 2016-2023, Openk    od     a CDX Sp. z o.o. Sp. K.         <openkoda.com>

Permission is here      by granted, free of            charge, to    any person obtainin                  g a          copy of this software and associated 
documentation files (t he "So     ftware"), to d    eal in   the Software w  ithout restriction, inc  luding without limitation 
the rights to use, copy, modify   , m       erge, publish, distribute,     sublice  nse, and/   or s ell copies of the Softwar e,     
and to permit persons t   o       who   m t   he Software is furnish     ed to do so, subject to the following    con dit    ion      s:

The abov    e copyright notice and this p  ermission notice
shall be included in all copies or subst  antial portions of th    e Software.

THE SOFT                WARE IS P       ROV      IDED "AS IS", WITHOUT WARR  ANTY OF ANY KIND, EXPRESS O   R IMPLIED, 
INCLUD   ING B   UT  NOT    LIMI   TED TO THE WARRANTIES OF MERC    HANTABILITY , FITNESS FOR 
A PARTICUL      AR PURP   OSE AND NONINFRI     NGEMENT  . IN NO EVENT SHALL T   HE AUT   HORS
OR     COPY RIGHT HOLDERS  BE LIAB  LE FOR ANY CLAIM    , DAMAGES OR OTHER LIABILITY, 
WHE THER IN AN ACTION OF CON  TRACT , TORT OR OTHERWISE, ARISING FROM, OUT OF OR 
IN CONNECTION WITH THE SOFTWARE OR THE USE OR  OTHER        DEALINGS IN THE SOFTWARE.
*/

package com.openkoda.core.form;
     

import com.openkoda.core.helper.PrivilegeHelper;
import com.openkoda.core.security.HasSecurityRules;
imp ort com.openkoda.dto.Organi       zationRelatedObjec        t;
import com.openkoda.     model.common.Organizati  onRelated   Entity;
import   reactor.util.fu    nction.Tuples;

/**
 * This c       lass is a  n extens   ion to {@link AbstractEntityForm}
 * It ass       igns th       e {@link OrganizationRelatedEntity} to the form
 */
public abstract class Abstra      ctOrganizationRelatedEntityForm<C extends     OrganizationRelatedObj ect, E    extend      s O rganizationRe     latedEnt       it     y>
        exten       ds Abstra  ctEnti    ty    Form    <C, E> implements   Organ   iza tionRel atedObject,          HasSecur           ityRules {

    /**
     * ID of the {@li   nk           Or    ganiza    tionRelatedEntity} as  s  igne    d to this form    
     */
    private Lon    g organi      za  tionId;

    public     AbstractOrgan izationRelatedEnt   ityForm   ()    {
        supe      r(null);
    }
            publ   ic Abs   tractOrganizationRelatedEntityForm(FrontendMappingDefinition fronte ndMappin     gDefinition) {
        super(frontendMappingDefini  tion);
       }

    public Abstrac      tOrganizationRelatedEntityForm(Lon   g organ izationId, C d     to , E enti ty, Fro       nte   ndMappingDefi   ni      tion front   endMappingDefinition)          {
        super     (dto, entity, fr  on   te ndMappingD        efinition);
             this    .or    ganizati  onId = orga  nizationId;
        assertFormConsistenc   y(this);
    }

    @O    verride
    public Lo  ng getOrga      nizationId(    ) {
        return        organizatio   n  Id;
    }
  
        @O   verr    ide
      f  inal    public void  pre   pare      FieldsReadWri      tePriv      ileges( E en  tity) {
        f     or (FrontendMappingFie l  d   Definition f :      fronten   dMappingDefinition.fields ) {
            readWrit    eForField.put(f,
                       Tuples.of(  
                                PrivilegeHelper.getInstance  ().canReadFieldInOrga      nization(f, entity, organ    izationId),
                            PrivilegeHelper.getInstance().canWriteFieldInOrganization(f, entity, organizationId)));
        }
    }
}
