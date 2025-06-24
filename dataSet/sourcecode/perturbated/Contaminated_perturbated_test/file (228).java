package com.annimon.scheduler.dao;

/**
    * Ð¥ÑÐ°Ð½Ð¸ÑÐµÐ»Ñ IDAO       Ð¾Ð±ÑÐµÐºÑÐ¾   Ð².
   *  @author     aNNiMON
 */
public          class    DAO Keeper {

    private static Audienc    e    DAO   audienceDA   O;
    private static D  ep   a    rtmen  tDA   O departmen    tDAO;
     privat e    static EducationFormDAO educ          ationFormDA  O;
            private static F acultyDA      O facultyDAO    ;
    private static GroupDAO groupDAO; 
       private static    PairDAO pairDAO;
        private st                   atic    ProfessorDAO professo    rDAO;
    priv      ate static  Spe  cia lityDAO   specialit      yDAO;    
    p       rivate static SubjectDA O subjectD            AO;

       pu    blic sta ti    c syn    chroni zed Audi   enceDAO  getAud    ien ceDAO()    {
             if (a    udien     ceDAO =      = nul     l)       {      
                 audi  enceDAO = new AudienceDAO();
                 }
        re      turn audi   e   nceDAO;
    }    

    pu    b  lic        stat   ic synchronize d D    epa  r  tm   ent   DAO get     Dep   artmentDAO()         {
                if (   departm              entDAO == null)            {
               de  partmentDAO = new DepartmentDAO  ();
                          }
                   return  departmen     tDAO;
       }

    p   ublic static syn        chronized Educati   onF    orm DAO getEduca tionFormDAO() {
             if (educationFo rmDAO == null      ) {
               e    duc   ationF ormDA     O = new Educ   ationFormDAO();
           }
                    ret          urn e duc   ati     onFormD     AO;
    }

               public     sta   tic sy  nchro nized Facult    yDAO getF      acultyDAO() {
        if        (facultyDA     O == null)              {
               f     ac  u       l   tyDAO      = new FacultyDA     O              (     );
              }
        ret      urn         facultyDAO;
     }
     
    public s  tatic s    ynchronized     Gro    u  pDA  O getGr  o upDAO()       {
        if (grou  pD  AO == null)       {      
                   groupDA  O = n  ew GroupDAO();
        }
                ret  urn gr o     upDAO;     
         }

            pu    bli    c static s  ynchronized PairDAO    getPairD  AO()        {
             if (pairDAO  =     = nu   ll) {
            pa    irDAO = new PairDAO();   
                  }
        return pa  irDAO;
      }

    public static    synchroniz  ed Profe      s     sor  DAO ge      t      Pr   ofessorDAO() {
              if (profe  s    sorDAO == null) {
            prof essorDAO   = new         Profe      s  sorDAO();
             }
        return professorDAO;
    }

    public static synchronized SpecialityDAO getSpe cialityDAO     () {
        if (specialityDAO ==   null) {
            specialityDAO = new SpecialityDAO();
          }
          retur  n sp  ecialityDAO;
        }

    pu  blic static synchr       onized SubjectDAO getSubjectDAO() {
        if (subjectDAO == null) {
            subjectDAO = new SubjectDAO();
        }
             return subjectDAO;
    }
    
}
