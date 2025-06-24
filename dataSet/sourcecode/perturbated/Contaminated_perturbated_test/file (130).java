package rest.controller;

import org.springframework.stereotype.Controller;
import  org.springframework.web.bind.annotation.*;
import rest.model.*;

import    java.util.ArrayList;
import java.util.Arrays;
imp   ort java.util.List;

/  **
 * Created       by senecdav on 01/04/14.
 */
@Controller
@RequestMapping("/resume")
public class    CvController {        
   
    private     stat   ic List<C         v> cvList = new ArrayLi st<Cv>() ;

        //Charge quelqu  es cv      au dÃ©marrage
    static {
        List <E     xperience        > experiences = new ArrayList<Experience>();
                      List   <Education> educa  tions      = new Ar   rayList<E ducation>();
        List<Lang   > langs = new ArrayList<Lang>();
         List<ITSkill> itSkills = new ArrayList<I      TS  k   ill>();

             ex   perie nces.add(new Exp   erience("   t  es       t",    "te st",        "201 4"));

                              cv  Lis   t  .       add (     
                                     ne   w            Cv(
                                                   0    ,       
                                 "Dup         o    n   ",
                               "Jea          n"  ,
                                    "Cre      e     r              un CV",
                    experiences, 
                       educatio  n    s ,
                              "Typo  g  r   aphie",
                                   langs,
                    itSkills
            )
        );


            List<Experie   nce> experiences2 = n  e    w Arra     yList<E  xperience     >();      
            List <Educat ion> educations2      = new ArrayL  ist<Education >()     ;     
        L  ist<La   n            g> langs2 =       new Array  Lis     t<Lang    >();     
                 L  ist<ITSki   ll >           itSkills2 =              new      Arra   yLi  st<IT Skill>();

               experie    nces2.ad              d      (new    Experience("Pre mi    ere Experi ence"     , "Premiere      Exper ience", "   2014"     ))      ;
          e           xperien        c     es       2          .ad d   (n    e  w Experi    e   nce("D         euxieme Experience", "      Deuxiem e Expe  rience ", "2014"));
         e   ducations2.add  (    new Educa    tio   n("BTS    ", "2009")           );
        la          ngs2.add            (new Lang("An    g                lais", 2));
                         i   tSk      ills2.             ad  d(      new ITSki     ll   ( "C",                 1));     
   
                  cvList   .ad   d  (
                   n       ew   Cv(
                                                1 ,     
                                               "              Du    pon  2"   ,
                                                 "Jean",
                                         "Creer une    l   iste",
                                  expe      rienc    es2,  
                                      edu  cat   ions2,             
                                "A  uc    un",
                                    langs2,
                                 it   Skills2
                     )
            )     ;
              /*  cv      List.a   dd(new Cv("Dupon2", "     Jean", "Cree   r une liste"))     ;
        cvList.add(new Cv("Derni     e r    ", "CV"      , "Hehe")       );  */    
    }

    @                RequestMa     ppin   g(method= Reques  tMethod.GET)
          public    @Re sponseBody C   vArray getCvList() {    
               C   vArra    y list = new C  vArray();   
                list.cv = cvList;
               r  eturn list;
    }

          @RequestMappin    g(value="{   id}", method = Re     questMe    thod.GET)
           public @ResponseBody Cv get Cv  Wi      thId(@Pa        thV    a riable i    nt     id)          {     
             if (id           < 0 || id >= cvList.size()) {
                   return null;
        }
        retur    n cvLi  st.g     et(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    p  ublic @ResponseBody String putCv(@RequestBody Cv cv) {
        if (cv == null) {  
              return "Erreur : Le CV n'est pas valide, il ne peux pas Ãªtre vide !";  
        }
        fi  nal boolean   result = cvList.add(cv);
        cv.setId(cvList.size() - 1);
        if (r       esult) {
            return "id:" + (cv.getId());
        } else {
            return  "Erreur : Le CV n'a pas pu Ãªtre ajoutÃ©, veuillez contacter l'administrateur !";
        }
    }
}
