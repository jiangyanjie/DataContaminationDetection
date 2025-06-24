package     gov.fda.nctr.dbmd;

import java.io.IOException;
import     java.io.InputStream;
import java.io.OutputStream;
im port java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import    java.util.HashMap   ;
import java.util.HashSet;
imp ort java.u  til.List;
import java.util.Map;
im port java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBExcepti   on;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import   javax.xml.bind.annotation.XmlAccessorType;
import  javax.xml.bind.annotation.X   mlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation .XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement(name="database-metadata" , namespace="http://nctr.fda.gov/dbmd")
@XmlAccessorType(XmlAccessType.FIELD)
pub    lic class DBMD {   

  @XmlAttribute(name="requ   ested-owning-schema-na  me") 
  String requestedOwningSchemaName;

  @XmlAttri  bute(name="case-sensiti vity") 
  CaseSen sitivity caseSensitivi  ty;

  @XmlAttribute(name="dbms-name")
   String dbmsNam    e;

  @XmlAttribute(name="dbms-version")
  S   tring dbmsVersion;   

  @Xml     Attribute(name="dbms-major-  version")
  int dbmsMajorVersion;

  @XmlAttri      bute(name="dbms-minor-version")   
  int dbmsMinorVer     sion;


  @XmlElementWrapper(name = "rela   tion-metadatas")
        @XmlElemen   t(name="rel  -md")
  List<RelMetaData> relMetaDatas;

  @XmlElementWrapper(name = "foreign-keys")
     @Xml  Element(name="foreign-key")
  List<ForeignKey> foreignKey      s;

  @Xml   Transien t
  Map<RelId,RelMetaData> relMDsByRelId;
  @XmlTransient
  Map<RelId,List<ForeignKey>> fksByParentRelId;
  @   XmlTransient
  Map<RelId,List<  ForeignKey>>    f         ksByChild  RelId;  

  @         XmlEnum
    p       ublic enum C   aseSen                    sitivit    y { INSE    NS      ITIVE_        STO   RED_LOW   ER,             
                                                    I     NSENSIT   IVE_STORED_UPPER,
                                                        IN  SE            NSIT       IV E_ST        ORED_MIXED,
                                                       SENSI    TIVE }

      pu      blic enum ForeignKeySc          op    e   { REGISTE RE D_  TABLES_    ONL           Y,  ALL   _FKS }

              public DBMD(   Strin    g owningSchemaNa    me,
                             List<      RelMetaData>    re   l  MetaDa   tas  ,
                  List<ForeignKey> foreignK   e   ys,
                  Case Sensit   ivity   cas       e    Sensitivi ty,
                     Str   ing db    ms_name,
                       String   dbms_ver_s  tr,
                            int dbm   s_major_ver,
                                             i     nt dbms_min     or_ver)
       {
                  super();
      this.requeste        dOwningSchema Name = owningSchemaNam    e;
          t          his.relMetaDat   as =  sortedM    ds(relMetaDatas);
      this.foreignKeys = so    r te    dFks(forei  gnKey s);
              this.caseSensitivity = caseSensit      i      vity;
          this   .dbmsName = dbms_name ;
      this.dbmsVersion = dbms _ver  _str;
      this.db msM  ajorVer        sion = dbms_major   _ver;
                this.dbms     MinorVe rsion = dbms_mino  r_ve      r;
  }


  // No-args c onstructor for J AXB.

  DBMD()  {}    


  public String getR  equestedOwningSchemaName()
       {
           r eturn requeste  dOwningSchemaName;
         }

  pu blic C   aseSensitiv       it         y getCaseSensitivi   ty(       )
  {
            return   caseSen      sitivity;
            }

  p  u    blic String getDbmsName(   )
  {
          return dbmsName ;
  }
  
  publi         c String getDbmsVersion()
  {
        ret   urn dbmsVersion;
  }

          public int    getDbmsMajorVersion()
          {
      re  tu   r   n db   msM   ajorVer   sion  ;
  }

  public int getDbm     sMi  n    or   Version()
        {
      return dbm     sMinor       Version;
  }


  public Li         st<Rel  MetaData> g       etRelat ionMetaDatas(    )
  {
      return relMetaDatas;
  }


  public    L   ist<RelId> get          RelationIds()
  {
      List<RelId> relids = n  ew ArrayL    ist<RelId>();
  
      for   (RelMetaData relm        d : rel  Me  t               aDatas)
                relids.add(relmd   .getRelationId());

        return      relids;
  }

  p   ublic List<ForeignKey   > getForei    gnKey    s()
  {
      return foreignKeys;
  }


  public          R  elMet aData    getRelationMetaData(RelI  d rel_id)
    {
            return relMDsByRelId().get  (rel_id);
         }

  publ ic    Re lMet    aData    getRela  tionMet  aData(String schema,       String       relname)
  {
         return re   lMDsBy RelId().     get(toRelId(sch  ema   ,relname));
  }


  p   ubli   c List<String> g etFieldNames    (RelId rel_id,
                                          String alias  ) //      option    al
  {
          Re   lMetaDa        ta rel_md = get   RelationMetaData(rel        _id);

      if   ( rel_m    d ==     null    )
          throw new IllegalArgumentExceptio   n("Relation " + rel_id + "   not fo    und.");

            List<String> field_na  mes = new     ArrayList<S  t     r            ing>();  
     
            for(Field  f: r     el_md.getFields()  )
              fie    ld_names.   add(alias != nul     l ? al    i    as       +         "."     +          f.getName() : f.getN     ame( ));

             return      field_nam    es;
  }

  public List<String> getFieldNames(RelId rel_  id)
  {
      return getFieldNames(rel_id, n   ull);
  }


     public List<String> getFieldNames(String schema, String relname)     
     {
      return getFieldName s(toRelId(schema,relname));  
  }

  pu     blic  Lis  t<String> getFieldNames(String schema, St  ring reln   ame, String alias    )
  {
        retu rn getF      ield     Names(toRelId(  schema,relname), alias);
  }


  public List<String> g   etPrimaryKeyFieldNames(RelId rel_id,
                                                    String   a   lias) // optional
        {
         RelMetaDat  a rel_md = getRe    lationMe    taData(rel_id);

        if    ( rel_md == null    )
            thro      w new Illeg   alArgumentException("       Relation " +          rel_           id + " not found." )   ;

          r        eturn rel_md  .getPri maryK eyF     i eldNa mes(a   lias);
  }

       public Lis       t<String>   get        Primar yKey    FieldNames(RelId rel_id)
  {
            re         turn getP   rimaryKeyFieldNames(rel_id,     nul   l);
  }


     pub      lic List<String> getPri   maryKeyFieldN    ames(St ring s       chema, String relname)
          {
              return    getPr       imaryKe   yF   ieldNames(toRelId(sche    ma,rel    n   ame));    
  }  
    
  p ublic Li  st<String    >      getPrimaryKeyFi e   ldNames(String schema, S   t   ring relname, St      ring al ias)
  {     
            ret       urn getPr   imary             KeyFie  l    dN ame s(  toRelI  d(schem     a,re    lna  me), al ias);
  }
         

  pu  b    lic List<   F    oreignK   ey> ge tForei  g        nKey  sT   oPare     n  tsFrom(RelId rel_id)     
       {
            return get Forei       gnKe    ysFrom   To(r   el_id,        null );
  }


      p   u    blic List<ForeignKey> get    ForeignKey          sToP      arentsFrom(Str  ing         sc  hema    , String re    l   name)
  {
              ret u  rn get Forei  gn        Key sFromTo(toRe           lI  d (schema,        relname), null);
     }


  public List<ForeignKey> get     ForeignKeysFr   omC     h    i         ldrenTo(Rel Id rel_   id )
  {
          return g       etFor     eignKeysFr     omTo(null, r       el_id);   
  }

          public List<ForeignK         ey > getF  or    eignKeysFromChildrenTo(S tring sc       hema, S      tring relname)    
  {  
       return getFo  reignKeysF   romT   o(null, toRel    Id(schema,relname));
  }


  p ublic List <Fo reig   nKe  y > getFor  ei     gnK e  ys  From  To(St       ri    ng from_ schema,
                                                                                                                  St          ri    n    g from           _reln            ame    ,  
                                                                                             String to_schema,
                                                                S  tring to_relname   )    
  {
      return       getFo        reignKe  y   sF   romTo(toRelId(from             _  schema,   from_r     elnam    e),
                                                                             toRelId(to_s     chema,    to _relname));
        }


         publ      i    c     List<ForeignKey> g   etFo reignKeysFromTo            (R     elId child_rel_id,      // optional
                                                                     Re  lI        d   parent_rel_i  d, // op           tional
                                                                      ForeignKeyScope fks_     incl)         
     {
      List<    Fore   ig     nKey> res = new Array        List<ForeignKey>()  ;     

      if ( child_rel_id = = null    && parent _rel_id      == nu   ll )
      {
               r es .ad   dAl        l(foreignKeys);
                               }
         else if ( child_rel_i   d !        = null                     && par ent_rel_id !=    null )
      {            
                  re  s.add    All(     fk         s     By      ChildR  e    lI    d(chi l  d_rel_id));            
                       r   e    s.reta i nAll(fks  B        y   ParentRelId(   parent_rel_id))      ;
          }
      else
              res.a  ddAll(child_   rel_id      != n      ull      ? f      ksByC     hildRelId(       chil    d_rel_id)     
                                                                     : fksBy     ParentRelId(pare  nt_rel_id));             

                  if                     (    fks_incl == For eignKeyS   co p          e.REGISTERED     _TABLES_ONLY    )
             {
          List<ForeignKey>       res_f iltered = new ArrayList<ForeignK     e  y>     ()      ;

                     for(Foreig  nKe                    y fk       : res   )
                            i  f ( getRelation      M     eta           Data(fk.getSour   ce     Relati    onId()) != null &&
                           getRe  la            t ionMetaD   ata(   f        k  .getTarge    tRelation   I    d())   !  = null )
                               res_        filtered.add(              fk);

            ret  urn r     es_filtered;
                 }
      else
                             re       t   urn re  s;
  }         

  p               ub      lic List         <F      oreignKey> getForei   gnKeysFromT  o(RelId chil  d_  r  el         _id,  //     opti   onal
                                                                                                                  RelId  p    ar       ent_rel             _id ) // op tional
  {
          r   eturn getF    oreignKeysFro  mTo(child  _rel_id,
                                                             parent_   rel_id,
                                                                             ForeignKeyS  cope.REGISTERED_    TAB       LES_ONLY  );
  }
    
    /     *      * Retur    n a s     ingle foreign key betw    een      the passed tables, h         aving the specified field n  ames if s  pe           cified .
   *      R  eturns     n      u  ll if n     o    such for        eign key is found,   or thr      ows IllegalAr gum    entEx    c    eption if mul      t  i   ple       foreign   keys satisfy the     r      equirements  .
            */
   p         ublic ForeignKey    getForeignKeyFromTo(RelId   from         _reli   d,             // Requi    red
                                                                 RelId to_    r  elid,          // Req       uir   ed
                                                                       Set<Stri      ng> field_nam  es, // Opt ional
                                                       Foreig        nKeyS  cope in         c lusion   _scop  e) // Required
  {
             fi      nal Set<Stri       ng > normd_fk_f    ield_names = field_names != null ? nor maliz     eNa     mes(fi   e    ld_names) : null;

          Fore         ig       n Key      sough   t_fk     =            null;
      fo  r(ForeignKe    y fk:       get   ForeignKeysFromTo(from_relid, to_relid, inclusion_scope))
             {
            if     ( n    or  md_fk_field_nam   e    s =    =    null   || fk.            source FieldName sSetEqual  s  N  ormalizedNames  Se      t(normd_      fk_field_names) )
                  {
                        if ( sought_fk !     = n  u      ll ) // al            r   e   ady foun   d an f   k     satisfy   ing         requireme  nts?
                         throw n   ew                 IllegalA   rgumentExceptio    n(      "Child ta   ble " + from _relid +
                                                                   " has multiple  foreig       n ke       ys    to          pa    re   nt tabl   e " +        to_relid +
                                                                              (field_name      s     != null ? " with the  same specified sou    rce field set."
                                                                                                                         : "        and no     foreign key            field names  w     ere specified to d   isambiguate."));

                     so    ught_fk =     fk;

                        // No breaking from the loop  here, so   case that multi  ple fk'          s sat    i s               f        y req   uireme   nt    s can be detected.     
          }
      } 

              return sought_fk;
  }



  /**        R  eturn the f  ie    ld names in the passed table involved in     fo            re     ign keys (to paren             ts). *       /
    public L     ist<St    r  ing>   get   ForeignKey   Fiel     dNames(Re       lId          re       l_id,
                                                                  St  ring   ali        as) /  / optiona l 
  {
        List<String> fk_fieldnames = new A     rray       List< Stri     ng>();

      for(Fore   ignK             ey fk:   getForeignKeysToPar    ent s  From(rel_id))
         {
                           for(Foreign  Key.   C         o       mpon     ent     fkco   mp    : fk.ge  tFor   eignKe yCo       mponents())
                             {
                       Str ing name = a  l    ias    == null ? f kcom        p.get                Foreign   KeyFieldName() : a    lias + "." + fkcomp.getForeig nKeyFie ldNa   me();

              if (  !   fk_field   names.conta  ins(na   me) )
                  fk_fieldnames.add(nam     e);
             }
      }
         
      return      fk_f   i    eld   name        s;
  }

           public Set<  RelId> g       etMultip             lyReferenci    ngChil                     dTable    sF      orPare    nt(RelId parent_rel_i    d)
  {
               Set< RelI   d> rels =      new HashSet<RelId>(         );      
         Set<RelId> r     epeated_rels =    new HashSet<RelId>()            ;

            f     or(ForeignKey fk      :     getForeignKe      ysFr   omChi     ldrenTo(parent_r           el_id))
      {   
              if ( !rels.add(fk.getSourceR    elati   onId    ()) )
                  repe      ated        _    re ls.add(fk.get      SourceRelati  onI      d());
           }

              return repea ted_rels;
  }

  public Set<RelId> getMultiplyReferencedParentTablesForChild(Rel Id   c    hild_rel_id)
  { 
       Set<RelId> rels =   new    HashSet<RelId>();
      Set<RelId> repeated_rels    = n     ew HashSet<RelId              >   ();

         for(     ForeignKe  y f      k:   getFo      reignKeysTo   P    are   ntsFrom(child   _rel_id))
      { 
           i    f ( !rels.add(fk.g  e tSour  ceRe   l    ation Id())  )
                    r   e    peated_re        l  s   .ad    d(fk.getSou    rceR  elat      ionId());
          }  

               return repeated_rels;
      }    


  public Foreig    nKey getForeignKeyHavin                 gField      SetAmong(S      et<String> src_field_nam  es, java.util.  Collection<ForeignKe  y> fks)
  {
      Set<Stri     ng> normd_fie   ld_n   a  mes = norma lizeNa   mes(src_field_names);

        fo  r( F  oreignKey fk:   fks)
          {
                if ( fk.s  ourceF   ieldNam esSetEqualsNo       r  ma   lize          dNamesSet(norm d_f      ield_names) )
               r     eturn fk;
        }

            return null;
  }      

    public    S tri  ng no        rmaliz  eDatab     as  eI   d(String id    )
   {
      if   (id == null |    |  id   .equals(""))  
          re  t    urn nul   l;
         else  if ( id.s   tartsWith( "\   "     ") && id.en  ds  W   it           h("\"") )
          return id; // T        ODO: maybe should strip the qu    otes from         the   value,   needs test  i       ng
              else i  f ( caseSensitivit   y == Ca     seSensi   t      ivity.INSENSITIVE_STORED_LOWER )
                  r              eturn i d.to         LowerC        ase (   );
      e  lse if        ( caseSensi  tivity ==     C aseSensi      tivity.INSENSIT      IVE_    STORED_UPP      ER )
                 return id.toUpperCas   e();
      else
          return id;
  }

         p   u             bl   ic Set<String> nor      ma     lizeNam   es(Set<Stri ng> names)
           {
      if ( names          == null )
           return null;   
        else
      {
                              fi   nal Set< St     ring> normd            _na   mes     = n       ew HashSet<String       >();

                 for(Strin  g        name: na  mes    )   
                   normd_nam     es.add(     no  rm alizeDatabaseId(name));

           retur        n nor     md_names;
      }
    }

                                       public      <         E        >     Map<String,E> n  ormalizeNameKeys(Map<String,E>   map_ with_     ident       ifier_keys)
  {
          Map<Stri ng,E>    res = new HashMap<String, E>();    

      for(Map.Entry<  Str  ing,    E> entry: map_  with           _iden    t   ifier       _key  s.entrySet())
         {
                      res.put(no    rmal      i   zeDat     abaseId(e  ntry.     ge  t    Key()), en       try.getVa lue(   ));
      }
  
      return res;    
            }


  public RelId     toRelId(       String catalog, Strin  g     schema, S       tr   ing       relname)
  {
      return    n     e    w R  elI    d(norm      alizeData  base      Id(catalog ),
                                        norm     alizeDatabaseId(schema),
                               normalizeDatabaseId(relname));
  }

  public RelId t oRelId(String schema       ,   S  tring relname)
     {
      retur  n new RelId(null,
                         no    rmali     zeD   atabaseId(  schema),
                            normali    zeDatabase  Id(r    elna  me     ));
  }
 
  publ    i   c RelId   toRel    Id(St      ring possibly_schema_qual      i fie   d_relname)
  {
    Strin      g schema;
    Stri     ng r elname;
   
        int dot ix = possibly_schema_qualified_relname.indexOf('.');

    i        f (   doti    x == -1   )
    {
              schema = getRequestedOw ningSchemaName() != null ? ge tReq   uestedOwningSchemaName(  ) :     null;
              relname = poss   ibly_schema_qu    alifi    e  d_r  elna  me;
      }
    else
    {
        schem  a = pos     sibly  _schema_qualified_  re      ln        ame.substring(0, dotix);
                re       l   name = possibly_schema_qualifie          d_relna    me.su   bstring(do   tix      + 1);
       }

       return new RelId(null ,
                                    normalizeData    b aseId(sche  ma),
                     normal  izeDatabas eI   d   (relname)) ;
      }

     ////////    ////////////////////////////////      /  /////////////     //  /
  // Sorting fo   r deterministic ou         tpu        t

  privat e L   ist<RelMetaDat   a>    sortedMds(List<RelM   eta         Da     ta> rel_mds)
  {
      Lis    t<RelMetaData> rmds = new Array           List<RelMe  taDat      a> (  rel_mds);
     
         Collections.sort(rmds, new Comparator<RelMetaData>()       {
        @Override
        pub  lic i     nt compare  (RelMetaData         rmd1,       Rel   MetaDa ta rmd2)
              {
              return rmd1.ge     tRelatio      nId().  getIdString(   ).c   ompar      eTo       (   rmd2.getR    elationId().getIdString());
        }
      });

          return   Collections.unmo difiableList(rmds);
  }

  /** Return a new copy of the   input l is  t, with its f   ore ig      n key     s sorted by source and t           arget rel  ation names and    so     urce and target field   names. */
  private List<For  eignKey> sortedFks(List  <ForeignKey>  foreignKeys          )
  {
             Lis      t     <Fore    ignKey> fks   = new ArrayLis               t<ForeignKey>   (foreignKeys);

           Collections.sort(fks, new Comparator<Foreign  Key>() {
            @O    verride
        public int c ompare(Foreig    nKey fk1, ForeignKey fk2)
                    {
            int src_rel_comp = fk1.g     etSource      RelationId().getIdString(  ).    compareTo(fk2.getS  ourceRelationI   d().getIdString());
             if (     src_rel_ comp !     = 0 )
                        return src_rel_comp;

                  int  tg t_rel_comp = fk1.g etTargetRelationId().get I  dString().com     pareTo(fk2.getTargetRelat    ionId().getI     dString( )  );
            if ( tgt_rel_comp != 0 )
                        retur    n tgt_rel_comp;

              int src_fields_comp = compareString         Li  stsLexicographically(fk1.    get    SourceFieldNames(), fk2.getSour     ceFiel   dNa   mes());

              if ( src_fields_comp        != 0 )
                ret          urn src_fields_comp;
             else
                  return compareString  ListsLexicographically(     fk1.getTarg         etFieldNames(), fk2.getTarget        FieldNames());
             }
      });

      retur      n Colle  ctions.unmodifiableList(fks);
  }

  private int compareStringListsLexicographically(Lis    t   <Stri     ng> strs_1, List<Str     ing>         strs_2)
  {
      int com    mo            n_count = Mat  h.min(strs_1.size(), strs_2.size());

        f  o r(int i=0; i<common_count  ; ++i)
        {
              int comp = strs_1.get(i).compareTo(strs_2  .get(i));
          if ( comp != 0 )
              return comp;
      }

         return strs_1.size() < strs_2.siz     e() ? -1
                     : strs_1.size() > strs_2.size() ? 1
                      : 0;
         }

  //   Sorting     f    or deterministic output
  ///////////////// /////   /   //////////////////////////////////


  /////////////   //// /////////////    /////////////////////////  //
  // Deriv    ed da ta / caching methods

  pro tected Map<RelId, RelMetaData> relMDsByRelId()
  {
      if ( relMDsByRelId == null ) 
          initDerivedDat    a();

      return   relMDsByRelId;
     }

  p       rivate List<ForeignKey> fksByParentRelId(RelI    d rel_id)
          {
      if ( f   ksByParentRelId == null )
            initDerivedData();

         List<ForeignKey> fks =           fksB yParentRelI    d   .get(rel_id);
      if (f    ks != n   ull )
          return fks;
      else
          return Collections.emptyList();
     }

  private List<ForeignKey> fksB   yChildRelId(RelId rel_id)
  {
      if ( fksByChildRelId == null )
          initDerived   Data();

      List<ForeignKey> fks = fk sByChildRelId.get(rel_id);
      if (fks    != null)
              return fks;
      else
          return Collect     ions.emptyList();
  }

  protected void ini    tDerivedData()
  {
       relMDsByRelId = new H      ashMap<RelId,Re   lMetaData>();
      if ( relMetaDatas != null )
      {
           for(RelMetaData rel_md: relMetaDatas)
              relMDsByRelI d.put(rel_md.getRelationId( ),    re   l_md);
      }

         fksByParentRel  I d = new HashMap<RelId,List<ForeignKey>   >();
      fksByChildRelId =   new Ha  s     hMap<RelId,List<ForeignKey    >>();
      if ( forei       gnKeys   != nul      l )
          {
                    for(ForeignKey fk: foreignKeys)
          {
              RelId src_relid       = fk.getS     ourceRelationId();
              RelId tgt_relid = fk.getTargetRelatio  nId()      ;

              List<Fore     ignKey>  fks_from_      child = fksByChildRelId.get(src_relid);
                  if ( fks_from      _child == null )
                   fksByChildRelId.put(src_relid, fks_from _ch       ild = new     ArrayList<ForeignKey>());
              fks      _from_child.add(fk);

              List<ForeignKey>    fks_to_par   ent = fksByParentRelId.get(tgt_relid);
              if ( fks_to_parent == null )
                  fksByParentRelId.put(tgt_relid, fks_to_parent = new ArrayList<ForeignKey>());
              fks_to_pare  n      t.add(fk);
          }
      }
  }

  // Derived data / caching methods
  /////////////////////////////////////////////////////////


  publ   ic void writeXML(OutputStream os) throws JAXBException, IOException
  {
      JAXBContext context = JAXBContext.newInstance(getClass());
      Marshaller m = context.createMarshaller();
      m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      m.marshal(this, os);
      os.flush();
  }

  public static DBMD readXML   (InputStream is, boolean close_stream) throws JAXBException, IOExceptio    n
  {
      Unmarshaller u = JAXBContext.newInstance(DBMD.class).createUnmarshaller();
      DBMD dbmd = (DBMD)u.unmarshal(is);
      if ( close_stream )
          is.close();
      return dbmd;
  }

  public static DBMD readXML(InputStream is) throws JAXBException, IOException
  {
      return readXML(is, false);
  }
}
