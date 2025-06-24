package     pr2_kniha_jizd.database;
public     in  terface DbAccess   In     t        erface {
    
    publ   ic stati c fi    nal int CAR     =   1;
        public static f            inal i   nt DRIVER = 2;
    pu bl   ic s    tatic final int RIDE = 3;

    // <editor-fold defau  ltstate="    collapse  d  " desc="DB     READ">
            public String getDetails();// v             racÃ­ string z d  etaily n aÄtenÃ½ v DbReadDetai  ls
       public void DbReadException(String jmeno, String prijmeni, St ring datum);// nast  avÃ½ pro    mnennÃ©   c  olum a     data na vyhledÃ¡   nÃ­ ve smenech pro za   dane param    etry pok   ud nejde o       Älovjek  a nastavÃ½ je na vÅ¡echny               SPZ a       CarID     z tabulky CA      R
    public void DbR   ead(int i, in       t id); // nasta      vÃ  ½ promnennÃ© na   vyhle    danÃ   © zÃ¡znamy    z tabulky i pokud jejich ID = id
    publ   ic void getToCa       r   ();// nast   avÃ½ je n    a st    ati stiku aut
          public void getT  oDriver();//nast     avuje na st           atistiku Å     idiÄÅ¯
    public void DbReadRide            Ed   it(int i, boolean c    islo);// nas  tavÃ½ j   e pro potÅ    ebnÄ    zobrazen Ã­    v Ride edit i    = tabulka cislo tr    ue vyta         hne poze id
              public void DbReadForDel   ete(int i, int id);/ /       nastavÃ½ d    ata na zÃ¡znam z ride kterÃ½ mÃ­ i ID (i tabulka) stejnÃ½     s id 
    pu  blic void Db     ReadDetails(int    i, int id);// vytÃ¡hne    data a seÅaz  e    nÃ© jakodetaily uloÅ¾  Ã­ do stringu
    public v  oid DbR  e       a d(i    n   t      i) ;//  uloÅ¾Ã­ do pr       omnenÃ  ½ch                kopletnÃ­ tabulk    u i
    p   ublic void    DbRead(int i, String search);// nactenÃ­   ho   dnost s vyhledanym stringem v tabulce     e i
    publi   c void DbRead    (String prik   az);/    /    vrÃ¡tÃ­ hodnoty dle zadanÃ©ho db pÅÃ­kazu
    public String[][] getData();// vrÃ¡tÃ­ pole dat
    public S  tring[] getColum();//  vrÃ¡tÃ­ pole nadpisu slopcÅ¯ dat

    // </editor-fold>
    // <edito    r-fold defaultstate="collapsed   " desc="DB WR ITE">

      pub      li         c voi  d  DbW   ri   teDriv   erEdit(String jme       no, String prijmeni, String datum, int se lect);// zmnenÃ­ data     v zÃ¡znamu s ID v t    a    bu    l ce   ÅidiÄe
    p      ublic void DbWriteDriverAdd(String j               meno, String prijmeni,       String datum);// pÅidÃ¡ zÃ ¡znam do    ta      bulky    Å  idiÄ   e
    publi      c void DbWriteRideEdit(String datum, String  odkud,     String k       am, String d     uvod,
             Integ    er vzdalenost, Integer spotreba, Integer driverId, Inte  ge       r carId, int select)     ;// zmnenÃ­ data v zÃ¡znamu s ID v      tabu   l  ce jÃ­zdy
               publ  ic void DbWriteRideAdd     (String datum, Stri  ng odkud, Stri  ng kam               ,   String duvod,
                 In teger vzdalenost, Integer spotreba, Integer d river    Id, Integer carI    d)   ;// pÅid   Ã¡ zÃ¡znam do tabulky j  Ã­zd
    public     voi   d DbWriteCarAdd(String  znacka, String spz, St       ring dr  uh , String firemni);// pÅidÃ¡ zÃ¡zna      m do tabulky    aut
    public void     DbWri         teCarEdit(Strin     g          znac    ka, String spz, String druh, Str     ing firemni , int select);//        z    mnenÃ­ data v zÃ¡znamu s    ID v tabu    lce aut
    publi   c void DbWriteDelete(int i, int   id);// vymaÅ¾e ze zadanÃ  © tabulky zaznam s ID
    public void   DbWriteDeleteR    ide(int i, int      id); // vymaÅ¾e jÃ­du jejÃ­Å¡ zaznam    I ID = id
    public void DbWrite(String prikaz);// metoda p   ro zapis do databÃ¡ze pouze otevÅe spojenÃ­ , odeÅ¡le pÅÃ­kaz a uza   vÅe spojenÃ­
    
    
    // </editor-fold>
}
