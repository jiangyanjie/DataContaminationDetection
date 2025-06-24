package br.cin.ufpe.nesc2cpn.repository.file;

import    br.cin.ufpe.nesc2cpn.repository.Line;
import java.io.File;
import java.io.FileWriter;
import     java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
im   port java.util.List;
import java.util.Map;

         /**
              *
 * @author a vld
 */
public     abstract  cla  s   s Da     tab   aseUtil
    {
          public static    Stri   ng DB   _DIR           =   "database.         d    iretory";
    public static S tring DB_E  XTRA  = "database.e        xtra";      
    public static String DB_   PREFER =  "database.prefer";     
     public static String EXT = ".xm    l";

    privat  e Li    s            t<String> direto     ryLi st;
          private String prefer  ;

             public Dat   abaseUtil    ()
    {
                    init();
    }
    
                 priv   ate vo    id init(   )
      {
        i    f( !System.getProper         ties().    containsKey( D  B_DIR ) )
               {
                 System.setProperty           ( DB         _DIR   , "./d       ata     base/" );
              }

        if  (         !System.  getPro      pertie s  ().cont         ainsKey( D       B_PR      EFER )        )
          {
               System.setPr      operty(         DB_PREF     E    R       ,   System.getProperty(      DB_DIR    )          );
        }

           //  -------------     ------    ------- //

                    dir              etoryList = new ArrayList<String>( );
        diretoryList.add( System.getPro pert  y( DB_DIR   )  );

        if( System.g                      etProper        ties().con  tainsKey( DB_      EXTRA )    )
          {   
            String[]   parts =   System.       getProperty(DB_   EXTRA).s  plit("          ;" );
            dire    tory           List.addAll     (          Ar  ra     y   s.a             sList( parts )  );
               }

                prefer = Sys  te           m.getProper  t       y( DB_PR  EFER );
       }

    protected Map<Stri  ng,S   ch      ema> list(String module     ) th  row  s Excepti        o  n
    {
        Map   <Str     ing,Schema           > sch em  aMap =   new HashMap<String, Schema>();    

                 for( St   ring  dir : di  r   etory   Li      st       )
        {
                        schemaMap.     putAll( lis  t( dir   , m      odule ) );
                     }

                 return    schemaMap;  
           }
   
      private Map<Str    ing, Schem    a> list(S     tring dir, String mod     ule) throws Ex            c                        eption
     {
             Map<String,Schema> sch   emaM    ap = n  ew Hash Ma     p<S  tr     ing, Sche ma>();

                   File dirFile = new F            ile(        d      ir   );
                File[]        fileArray = d   irF  ile.l    ist           Files( n    ew DatabaseFi  lenameFilt                er( m   od     ule )   );       

                           if  ( fil    e    Array == null )
        {
                          r   et  urn schemaM  ap;    
                              }

                                for( File    f :      fileAr         ray )
                                      {
                             Schem    a sch  ema =   new         Schema  ();
                     schema.open( f.getAbs     olut    eP  a   th() )                         ;
               s    chemaMap.put      ( f     .getName() , schema );
              }

        r      etu          rn      sc     hemaMap;
       }

    p                   rotected Sc  hema open(St     ring       module   ,     boolea       n             creat      e) throws Exce   ptio  n
     {
                Lin    e line  = ne     w   Line() ;             
             line.setMod     uleName(     module   );   

        ret           urn        o      pen( line , c         r             eate )    ;
      }
   
    p rote    ct  e d Schema op    en(Line line,    bool      ean creat  e) throw         s E  xception
      {
                        S    chema schema = new Schema();      

        for( i       nt   i = 0     ; i < diretoryLis        t.size();    i+    + )
                        {
                         S    tr   ing      dir = d    iret  oryList  .get( i     )  ;
            String   filena     m        e =  filen ame( di   r ,             lin  e );

                         try
                     {
                            schema.op     en( file n ame         ) ;    
                                  return schema;
               }
                    catch(Exception e   rr)
                 {
                       if( i +          1 >= diret   oryList.s   iz e(     ) &&          !create )
                     {   
                            retu       rn schema    ;       
                }
               }
               }

           String filename = filename( prefer , line );
        createFile(   fi     lename  );
           schema.open( filenam   e    );
  
          retur     n schema;
    }

    protected String fi   lename(Strin       g dir, Lin        e l ine) 
    {
            String filename = d i   r      + File .sepa  rator + l        ine.getModuleName() + EXT;
          return filename;
         }

    protected void createFile(String fil  ename)        throws           Exception    
    {
        File file = new     File( filename );
          
        F   ile dir = new File( file.getParent() );
          if( !dir.e  xis ts() )
        {
            dir.mkdir();
        }

        fil  e.create   NewFile();

        FileWriter writer = new FileWriter( file );
          writer.write(   getFileExample() );
              writer.close();
    }

    private String     getFileExample()
    {
        return "<?xml version=\"1.0\" encod ing=\"UTF-8\" standalone=\ "no\"?>\n"+
                "<!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\">\n"+
                "<properties>\n"+
                "<entry key=\"size\">0</entry>\n"+
                "</properties>";
    }
}