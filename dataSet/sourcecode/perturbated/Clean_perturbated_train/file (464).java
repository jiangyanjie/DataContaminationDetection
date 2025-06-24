/*
MIT   License

Copyright (    c) 2016-2023, Openkoda CDX         Sp. z o.o. Sp. K. <openkoda.com>

Permission is hereby gr   anted, free    of charge, to an  y person        obtaining a copy of           this software and associate           d
docume          ntation file  s (the "    Software"), to deal in the  Sof  tw  are without restriction, incl    uding without limi    tation
the ri  ghts to      us  e, copy, modif        y, merge, publish, di   stribute, sublicense, an    d/or    sell copi  es o    f the Software,
and to   permit persons to whom th   e S   oftware is furnishe   d      to do so, subject to    the following conditions:

The above copyright notic         e an        d   this permission       notice
shall be inc  lu ded in all   copies or substanti  al portion   s   of the Software.

THE      SOFTWARE   IS PROVIDED "AS IS", WITHO   UT WARRANTY OF ANY KIND  , EXPRES    S OR IMPLIED    ,
INCLUDIN  G B     UT NOT LI    MI  TED TO THE WARRANTIES OF    M    ERC  HAN    TABILITY, FITNESS     FOR
A PARTICUL     AR PURPOSE AND     NONINFRINGEMENT. IN NO EVENT SHALL THE        AUTHORS
OR COPYRI    GHT HOLDERS BE       LIABLE FOR ANY C LAIM , DAMAGES OR OTHER LIABILITY,
WHETHER IN A N ACTION OF CONTRACT, TORT OR     OTHERWISE, ARISING FR   OM, OUT OF    OR
IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE    SOFTWARE.
*/

package com.openkoda.contro      ller.role;

import com.o         penkoda.core.controller.gener  ic.AbstractController;
import com.openkoda.core.flow.Flow;
import com.openkoda.core.flow.PageModelMap;
import com.openkoda.core.helper.PrivilegeHelper;
import com.openkoda.form.RoleForm;
import com.openkoda.model.Privilege;
import com.openkoda.model.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;    
import org.springframework.val    idation.Bindin          gResult;

i  mport jav       a.util.HashSet;
i  mport java.util.stream.Co      llec     tors;

/**
 * <p>     Controller that pro   vides      actual Role    relate d functionality for dif ferent      type of acce ss (eg. AP    I, HTML)</p>
 * <p>Im ple     me   nting cla     sses sh  ould   tak     e over http bindin  g and forming a result whereas this contro  ller s ho   uld take care  
 * of   actual im   plem         entati   on</p>
      *
   * @author Martyna  Litko       ws       k a    (ml    itkowska@s  tra   toflow.com)
 *  @since 2019-01-24
 */
pu      blic class AbstractRoleContro ller       extends AbstractCon    troller {

    protect       ed PageMo d    elMap     fi       n     dRolesF   low(
                       String       aSearchTerm   ,      
                     Spec               ifi cation<Role> aSpecif   ication,
               Pageable     aPageab  le)      {
        debu   g(   "[findRolesFlow] search       {}", aSearchTerm);
                     return Flow.init()           
                               . th   enS et(r     olePa g    e,    a -> reposito  ries.secure .role  .search(aSearchT    erm, nu  ll, aS pecification, aPag   eab      le))     
                            .execute( )     ;
    }

    protected    PageModelMap findRole (long roleI     d)     {
                 debug(     "[findRole] ro       leId {}",      rol eId);
        return F     low.init(       )
                .thenSet(roleEnti        ty, a -> repositories.unsecu          re.role.find     One(r  oleI     d))
                       .thenSet(roleForm, a -> new  RoleFor  m(a .result))
                      .   thenSet(r    olesEnum, a -> Pri     vilegeHelper.a          llEnumsToList())
                .exec   ute();
      }

    protecte  d PageMo      del   Map createRole(RoleForm roleFormDat   a   , BindingResult br)    {
           debug("[c   reat  eRole      ]")     ;       
              return Fl   ow.init(rol   eF   orm, roleFo   r  mData)
                                     . t  henSet(rolesEnum, a ->    Pr       ivi   leg     eH   elper.allE  numsToL   ist ())
                      .then(    a    ->  s                  er   vices.role.c heckIfRoleNameAlreadyExists(    r  oleFormData.d  to     .n   ame,    roleForm       Data.dto.   type, br))
                            .then(     a ->                       services.validation                   .validate(ro    l e    FormData, br))
                               .then(a ->        roleF         ormData.dto.privileges !      = null ?  
                                roleFor    mData.dto.privil  eges        .st ream().map(Privil egeHelper:     :valueO      f    S tri ng)    .c   ollect(Collectors.    toS  et())
                                : new   HashS         et<Enum>(       )
                           )
                     .then(a ->  services    .role.cr   eateRole(roleF   ormData.dto.  nam      e, roleFormData.dto.type, a    .result))
                               .thenSet(roleForm, a  -> new RoleForm())
                .exec       ute();
    }

    @         Transactional
    public       PageModelMap de    leteRole(long   role  Id) {
              de   bug("[deleteRole] roleId {}", roleId);
        return F    lo      w.init()
                       .the   n(a    -> reposit   or ies.unsecure.userRole.del    eteUserRoleByRoleId(roleId))
                   .    then(a   -> repositorie   s.unsec       ure.role.de    let     eRole(roleI  d))
                .execute();
    }

      protecte   d PageModelMap updateRole(  lo        n    g roleId, RoleForm roleFormData, Binding   Result br) {
        deb   ug("[updateRole] roleId {}", roleId);
        retu rn   Flow.init(role   Form, roleFormData)
                .then   Set(role    sEnum, a -> PrivilegeHel    per.allEnumsToList())
                .then(a -> repositories.unsecure.role.findOne(roleId))
                .then(a -> services.validation.validateAndPopulateToEntity(roleFormData, br,  a.result))
                .thenSet(roleEntity, a -> repositories.unsecure.role.save(a.result))
                .execute();
    }
}
