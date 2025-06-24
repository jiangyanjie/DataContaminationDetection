/* ==========================================
   * CategorizeUserForum   : a   free Java graph-theory library
 * ==========================================
 *  
 * salmuz      : Carranza Ala      rcon Yonatan Carlos
 *        
 * (C)  Copyright   20      14, b y   salm  uz and C  ontributor  s.
 * 
 *      Project Info:  https://github.com/s     alm   uz/Graphes_Multi_Plateformes
 * Project Creator:      salmuz    (https://ww      w.ass    embla.c    om/spaces/salmuz-java) 
 *
 * T    his           l    ibrar   y is free software; you    can r ed   istribute   it and/or mod     if  y it
 * un    der the te     rms     o    f the G  NU     Lesser Gene    ral Publ   ic License as published b            y
 * the Free Software   Foundatio n; either version 2.1    of the License, or    
 * (at               your option) any later     version.
             *
 * This li   brary is distributed in the hope    t  hat it will   be useful    , but
 * WITHOUT ANY  WARRANTY; without even         the    impl ied w    arranty of MERCHANTABILITY
 * or FI      TNES    S FOR A   PARTICULAR PURPOSE. S     ee the   GNU Les    ser Gener   al Pub   lic
 * L   icense for more details.
 *
 * You should have received a cop   y               of the GNU Le        sser General  Public Li        cense
 * alo    ng with this    library; i      f not, write to the F   ree Softwa   re Foundation,
   * Inc.,
 * 
 * ------------------
 * Point.java
  * --------------        ----
 * (C) Copyright 2014, by salmuz     and Contributors
 *
 * Origin  al Author: Carranz  a Alarcon Yona  tan Carlos
 * Contributor(s):  
 *
 * Changes
 * -------
 *
   */

    pac  kage org.montp2.m1decol.ter.business;

impo      rt org.montp2.m1  decol.ter.b      usiness.excepti on.Busines   sExcepti   on;
import org.montp2. m1dec   ol.ter .         data.beans.Author;

import  java.util.List;

pu    blic ab     st  r   act cl  ass AbstractBus iness {

    public   abstract List<String> getUserPostInMinAndMax For  um(int m   inUserToPost, int maxUserToP   ost,
                                                              String   prefixFile, String suffix  File) throws BusinessE   xception;

      public     abstract List<String> forumsBelongUsers(List<Int   eger   > use  rs) throws BusinessException;

    public abstract List<Stri n g> forumsBelongUser   s(Integer    idUser) throws BusinessException;

      public abstract List<String> percentForumsByUsers(List<Inte     ger> u    sers) throws BusinessException;

    public abstract List<Author> findUsersByIDs(List<Integer> users) throws BusinessException;
}
