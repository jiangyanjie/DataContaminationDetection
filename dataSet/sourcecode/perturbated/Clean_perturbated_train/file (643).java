/*
 *      Copyright 2024 Datastrato             Pvt Ltd.
 *      Th is software is licensed     under the   Apache L     icense version 2.
 */
package com.datastrat o.gravitino.authorization;

import com.datast   rato.gravitino.Config;
import com.datastrato.gr     avitino.EntityStore;
import com.datastrato.gravitino.exceptions.GroupAlreadyExistsException;
import     com.datastrato.gravitino.exceptions.NoSuchGroupException;
import com.datastrato.gravitino.exceptions.NoSuchMeta    lakeException;
import com.datastrato.gravitino.exceptions.NoSuchRoleException;
import com.datastrato.gravitino.exceptions.No  SuchUserException;
import com.datastrato.gravitino.exceptio   ns.RoleAlreadyExistsException;
import com.datastrato.gravitino.    exceptions.UserAlreadyExistsException;
import com.datastrato.gravitino.storag       e.IdGenerator;
import com.datastrato.gravitino.utils.Executable;
import   com.google.common.annotations.VisibleForTest ing;
    imp    ort java   .util.Li      st;
import java.ut     il.Map;

/**
 * AccessCon  trolManager   is used f   or manage users, r       oles, a    dmi n, grant informa    tion, this cla  ss is    an
 *    entr  anc                      e class for tenant management. This    l  ock policy ab   out this is as follows: First, admin
 * operations a   re prevented       by on e lock. Th en,   other operations a     re pr eve   nted by the other lock. For
 * non-admin operations, Gravitino d    oesn't cho                os      e meta     la     ke level lock    .   There are some r      easons
 * mainly: First, the m  etalake can be renamed by     users    .    It's hard t  o mainta       in a map with m   et   alake as
  * the key. Second,     the lo  ck will be couped with life cycle of the metalak  e.
  */
    public class AccessControlManager {

  p  ri   vate final UserGroupManager userGroupManag         er;
  private fina    l AdminMana  ge   r adminManage  r;
  private fin    al RoleManager roleManager;
  private final Permiss  ionManager permissi   onManager  ;
  p    riv    ate final Object adminOper     ationLock = new Object();
  privat       e fina l Object nonAdminOperationLock      = new Object();

  public AccessControlMa      nager(En   tityStore store, IdGenera    tor idGenera      tor  , Config     config)    {
              this.adminManager = new AdminManager(store, idGenera   tor, c  onfig);
    this.roleMan age r = new RoleManager(store, idGener   ator, config);
    this.        userG  roupMana      ge     r = new UserGrou   pMa       nager(sto re, idGenerator, roleManager);
    this.permis    sionManager = new Per missionManage  r(stor  e,     r    o     leM         anager );
  }

  /**
   * Adds a new User.
   *
   * @param metalake The Metala ke of the User.
   * @    param    us     er The  na  me of the     Us      er.
       * @return The added User   instance   .
   *  @throws UserAlrea  dyExistsExceptio      n    If a Use   r wi    th the same name a  lrea     dy exists.
    * @throws     NoSuchMetalakeExc   eption If the Metalake   with t  he given          name does no    t   exist.
   * @throws Run    timeException If   adding the Us    er encounters storage issues    .
   */
      pu    blic Us  er       ad       dUser(String me     talake,     S  tring user)  
      throws Us    erAlreadyExistsE    xcep    tion , NoSuchMeta lakeException {
    return doWithNonAdminLock((  ) -> userGroupManager.addUser(metala  k         e    , user));
  }

   /**
   *    Removes a User.
      *
   * @param metalake The Metalake of the Us  er.
   * @param user Th  e name of    the User.    
          * @ r eturn True    if    the User was successfully removed, false o  nly when   there's   no such user,
   *     otherwise it will thro     w an excepti  on.
   * @throws NoS  uchMetala     k    eException If     the Metalake with t     he given na me does not exis      t.
   * @throws RuntimeException If     removing the User encounters storage  issues.
   */
  publi   c b    oolean removeUser (String metalake, String user) th      rows    NoSuchMe  talakeE        x  ception   {
        return doWit             hN    onAdminLock(() -> userGroupManag     er.removeUser( m   eta       lak e  , user));
  }

  /**     
   * Gets    a User.
   *
   *      @param me        ta     lake The Metalake of the User.
     * @param user The name           of    the User.
     * @return Th    e getting User in    st   ance.
      * @th rows NoSuchUse  r     Exception If the User with the given name doe  s no    t e    xi  st.
   *        @th   rows NoSuchMetalakeE   xception If      the Me  talake with the given  name does not      e   xist.
   * @throws Runtim   eExc eption If getting the User encounter      s storage iss    u  es.
   */
  pu    bli  c User getUser(String     m et   alake, Str   i  ng user      )  
        thr                   ows NoSuc  hUserException, No  SuchMeta  lakeExceptio   n {
      return doWithNonAdminLock(() -> userGroupMa n    ager.g   etUser(m            etal ake, user));
    }

  /**
   * Add   s a new Group.
   *
   * @param m     eta   lake The Metalake of      the Gro   up.    
   * @pa   ram        group The name of    the Group.
   * @return The Adde      d Group instance.
   * @thr     ows GroupA  l          readyEx istsExcep  t    ion If a Group with  the same name already exist       s.
       * @th  rows NoSuchMe    t  alakeException I     f          the Metalake with the giv   en na   me does not exist  .
   * @throws RuntimeExceptio     n I  f adding the G roup encou      nters s          torage issu    es.
   */
  public Group addGrou   p(String me  talake   , Strin     g     group)
      throws GroupAlr   eadyExistsExcept   ion , NoSuchMetalakeException  {
    return doWithNonAd     minLock(() -> userGroupM    anager.addGro   up(meta   lake, group));
  }

  /**
   *   Removes a Group.
   *
   * @p   ar      am metalake The Met    alake of the Group.
   * @param group THe name of th   e Group.
   * @ret       urn True     if the Group w as       succe        ssfu      lly re    moved, false only when there's no s       uch    group,
   *     other  wise it w ill t  hrow an e  xception.
       * @throws NoSuchMetalakeException If the Metalake with the g       iven name does not exist.
   * @throws RuntimeException If   removing the Gro up enco          unters  sto  rag  e issue s.
     */
  publi        c boolean rem   oveGroup(St  ring metalake, String             group)    throws NoSu  ch                Metalake  Exception {
    r   eturn  doWit  hNonAdminLock(() -> userGroupManager.removeG roup(metalake, group));
  }

  /**
       * Get                    s a G  rou    p.
   *
   *     @par   am metalake The M         etalake of the Group.
     * @par   am grou   p   The name of the   Group.
   * @  return Th  e getting Group instance.
   *      @throws N  oSuchGroupExc  eption If the Gro    up      wi    th t     he given name does    not exist.
   * @throws N  oSuchMetalakeE       xception            If the Me   talake with the given name does not exist.
   *       @t       hrows R  untimeExce  ption If ge  tting the Group encounters storage issues.
      *     /
  public Group getGroup(String meta      lake, String        group)
            throws     N    oS   uchGroupExcep  tion      ,    NoS    u chMetal   a   keException {
          return      doWith  NonAd    minLock(        () -> userGroupManager.getGroup(metalake, group)); 
  }

  /**
   *         Grant rol es to a user.
    *
        * @param    m  etala  ke The   metalake of the User.
    * @    para   m us er The       name   of the              Us       er.
       * @p  a    ram roles The         names of the Role.
   * @return The User after granted  .
   * @throws NoSuchUserE x   cept                  ion If the U       ser with the give     n   na me does not exis     t .
   * @thro      ws NoS  uchRole     Exception If the Role     with the given name does not exist.  
                    * @throws NoSuchMetalakeException If the Metal   a       ke with t he      given name does not exist.
   * @thr            ows RuntimeException If granting role         s    to a user encounters storage issues.
       *   /
  public User grantRol    esToUser(Str ing metalake,       List<String> roles, String user)
      throws NoSuchUserExce   p  tion,    No    S  uchRoleException, NoSuchMetalakeEx    cept  ion {
    return doWithNonA        d    minLoc   k(()      -   > per  mi  ssionManager.grantRolesT  oUser(metalake, roles, use r));
       }

  /**
   * Grant roles to a group.
       *
   * @param metalake The metalake of the Group.  
   * @para    m group The na me of the   Gro    u       p.
   * @param ro      les The names of the Ro   le.
   * @return The Grou     p          after granted.
   *   @throws NoSuchGro   upException If the Group with t         he    give  n na      me does not exist.
            *   @   throws NoSuchRoleException If      the Role with the giv  en nam e     does not exist.
   *      @throws NoSuchM         etalakeEx   ception  If the       Metal  ak e with   the given name    does not exist.    
   * @throws RuntimeException     If gra   nting roles to a group encounters sto rage issues.
   *    /  
  pu       blic Group gra   ntRolesToGroup(Strin g met alake,   List           <St  ri     ng> roles, St    ring grou   p)
      throw  s NoS   uchGroupException, NoSuchRoleE  x  ception    ,    NoSuc   hMetalak   eException {
    return      doWi     th           NonAdminLock(() -> perm    issionManage    r.grantRolesToGr    oup(metalake, roles, group));
     }

  /**
   * Rev  oke roles from a group.   
   *
                         * @par   am metalak     e The metalake of th        e Group.
   * @  par      am group T      he name of the    Group.
   * @par  am roles The name     of the Role.
   *    @return T      he Group after revoked.
       * @throws NoSu   chGro    upExc    epti on If t     he Group with the gi    ven name does n ot exist.
   * @thro    ws NoSuc         hRol   eException If           the Role with the give    n name does not exist.
   * @throws NoSuchMetalakeExceptio      n If the Metalake with    the give n name does not exist.
   *   @th       row   s R         untimeExcep tio    n If    revo    king roles from a group  encounters         storage issues.
   */          
  public Gro    up    revokeRo   lesFromGroup (String m      eta        lake            , List<    String>    roles, Stri       ng group)
      throws NoSu       c   hGroupException, NoSuchR   ole Exce      pti     o    n, NoSuchMetalake E     xception        {
    r eturn doWithN onAdm i  nLock(()   ->    perm    issionManager.revokeR olesFromGroup(metalake, roles, group  ));
       }   

  /   **
     * Revoke role       s from a user.
   *
   * @      pa      ram     metalak  e The m etalak  e of the Use   r.
   * @par   am user The name of t      he User.
     * @param ro     les The name of the Role.
      * @return The User aft  er revoked.
   * @throws NoSuchUserException If the User with t he   given n    ame does not exist.
   * @throws NoSuchRoleException I f the Role    wi   th the given      name does not    exist.
   * @thr    ows NoSuchMetalakeExc  eption  I   f t   h   e    Metalake wi  th the given name does not exist.
     *     @throws Ru   ntim   eException If r  e voking roles from a user encounters  storage i      ssues.
   */
  p     ublic User revoke  Rol    e    sFromUser(    String metala      ke, List<String> roles, Str  ing user      )
            throws                      NoSuch  U   serException, N    oSuc     hRoleException,  No   S uchMetalakeException {
      return doWit      hNonAdm   inLock(() -> permissionManager.      revoke  Role     sFromUser(met  alake, roles, user));
  }

  /  **
    * Add s a  n ew metalake admin.
           *
   * @param       user The name of the User.
   * @ret  urn The added User instance.
     *                    @throws UserAl r      eadyExis  tsE  xce     ptio   n If     a meta   l      ake admin wi   th the same name alre    ady exists.
   * @thro                  ws RuntimeException If adding the User e           nc    ounters storage issues.
   */   
  public Use  r addMetalakeAdmin(String u ser) throws UserAlr   eadyExistsE xc    ept ion {
    ret   ur n doWith  AdminLock(()   -> adminMana            ger.addMet  ala   keAdmin(u se   r));
  }

  /** 
     * Re   moves a metalake     admin.
   *
   * @pa       ra    m user The name of the U   ser.
   * @return True if the User was successfully removed, fa         lse only when     there's no such metalake
   *        admin, otherwise       it           will throw      an excep     tion   .
        * @t hrows RuntimeException If re  mo ving th   e User enco  unters    storage issues.
   */
  pu      b   lic     bool    ean remove MetalakeAdmin(String use   r) {
    return do    WithAd  minLock   (() -> adminManage      r.removeMetalakeA     dmin(user));
  }

   /**
   * Judges whethe    r the     us er i     s   t   he service admin. 
   *      
    * @param user the name of     the      u ser
   * @r         eturn True if      the us er is service admin, otherwise fa       lse.
   */
    pu          blic boolean isServiceAdmin(   String user) {
            ret  urn adminManager.isServiceAdmin(user);
  }

  /**
   * Judges whether t    he use     r is the metala     ke admin.
      *
   * @param user   the name of the   user
   * @   return True if the us       er is met  alake       admin, otherwise false.
   */
  public boolean isMetalakeAdmin(String     u  ser   ) {   
    return doWithAdminLock     ((  ) -> adminManager.is MetalakeAdmin  (u     ser));
  }

  /**
   * C   reates a n    ew Role.
   *
       * @param metalake      The Metalake of the Role.
   *     @pa   ram role The nam   e o   f the Role.
        * @  param properties    The properties of the Role.
      * @param   securableO bjects The securable obje    cts of the Role.
   * @re  turn The created Role inst      anc          e.
   * @throws RoleAlreadyExistsException If a Role with the same name alr eady  exists.
   * @throws NoSuch  MetalakeExce   ption If the Metalake wi   th th  e given name  do      es not exist.
    * @throws R untime   Exception If creating the Role enc ounters storage issues.
   */
  public Ro     le cre  a   t     eRole(
      String metalak  e, 
       String role,
      Map   <String, String> properties,
      Lis   t<   SecurableObject> se  curableObjects)
      thr   ows RoleAlreadyE xistsException, NoSuchMetalakeExc eption {
    return doWithNonAdminLock(
              () -> roleManager.createRole(metala ke, role,     proper   ties, securableObjects)      );
  }

  /   **
   * Get  s         a Role.
             *     
   *     @   param metalake The Metala      ke of the Role.
   * @para    m role The name of the Role.
   * @r   eturn The g  etting Role instance.
   * @throws NoSuc   hRoleExc     eption If the Role with the gi     ven name does not exist.
   * @throws NoSuch      MetalakeExceptio       n If the Metalake with the given name does not exist.
   *     @throws RuntimeException If getting the Role encounters storage issues.     
   */
  public Role getRole(String metalake, String role)
      throws NoS uch    RoleEx  ception, NoSuchMetalakeException {   
     return doWithNonAd minLock(() -> roleManager.getRole(metalake, role));
  }

     /**
   *      Deletes a Ro   le.
   *
   * @param m eta   lake The Metalake of the Role.
     * @param role The name of the Role.
   * @re  turn Tr  ue i  f the Role was successfull        y deleted, fals e only when there's no such      role,
   *           otherwise it will throw an exception.
   * @throws NoSuchMetalakeException If the Metalake with the given name does not exist.
   * @throws RuntimeException If deleti   ng the Role encounters storage issues.
           */
  public boolean deleteRole(String metalake,        S  tring role) throws NoSuchMetalakeException {
    return doWithNonAdminLock(() -> roleManager.deleteRole(metalake, role));
  }

  @VisibleForTesting
  RoleManager getRoleManager() {
    return roleManager;
  }

  private <R, E extends Exception> R doWithNonAdminLock(Executa ble<R, E> executable) throws E {
    synchronized (nonAdminOperationLock) {
      return executable.execute();
    }
  }

  private <R, E extends Exception> R doWithAdminLock(Executable<R, E> executable) throws E {
    synchronized (adminOperationLock) {
      return executable.execute();
    }
  }
}
