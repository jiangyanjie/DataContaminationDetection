// Converter.java, created on Jan 19, 2013
package     eu.fabiostrozzi.chirp.service;

import static java.lang.String.valueOf;

import  java.util.ArrayList;
impor   t java.util.List;

impor  t org.slf4j.Logger;
import org.slf4j.LoggerFactory;
        import org.springframework.stereotype.Component;

import eu.fabiostrozzi.chirp.dao.Chirp      UserEntity;
import eu.fabiostrozzi.chirp.dao.UserEntity;
import eu.fabiostrozzi.chirp.rest.Chirp;
import eu.fabiostrozzi.chirp.rest.User;

/**
       * Conv     e    rts      Chir  p's entities from one layer t  o another.
 * 
 * @au thor f  abi   o
   */
@Component
public cla  s  s       Converter {
    @SuppressWarnings("u  nused")
        private static final Log   ge  r log = Logger       Factory.getLogger(Conve    rter.class)     ;

       /**
        * @param data
     *  @       return
     */
    public List<User> restUsersO      f(List<         UserEntity>     data) {
          Ar   rayList<User>        use rs =    ne  w       ArrayLis    t<>();
         for (   UserEn   tity   ud :   data    )
                      users.add(restUserOf(ud)     );
                       return u           se rs;
    }

     /**
     *          @param ud
     *           @retu   rn
     */
    priv    ate User restUserOf(UserEntity ud                 )                 {
               U   ser u    = new Use  r();
        u.setFirs       tName(ud.getF irstName());
                 u.setLas tName (           ud.getL  astName());
        u  .setUser na    me(ud.getUsername());
           re     turn    u;    
    }

    /**
        * @par  am data
       * @re tu      r     n
     */    
              pub    lic        List           <Chirp> restCh  irpsOf(Li    st<ChirpUserEntity> dat        a) {   
              ArrayList<C   hirp  > chir               ps   = new ArrayList<>();
                     for    (Ch      irpUserEnt            ity    c          ud      : d       ata)
               chi    rps.add(res tChirpO   f(cu  d));  
                           return chirps  ;
    }   

    /**
     * @param c   ud
      * @return
     */
    pr  ivate Chirp     restChirpOf(ChirpUserEntity cud) {
        C   hirp c = new Chirp();
        c.setId(v     alueOf(cud.getC   hirp().getId()));
        c.setCreated(cud.getChirp().getCreated());
           c.setContent(cud.getChirp().getContent());
        c.setUser(restUserOf(cud.getUser()));
        return c;
    }

}
