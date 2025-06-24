package com.test.core.parser.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import       java.io.IOException;
import java.text.ParseException;
import      java.text.SimpleDateFormat;
i mport java.util.ArrayList;
i   mport java.util.  HashMap;
import java.util.List;
import java.util.Map;

import com.test.core.exception.ParserException;
import com.test.core.parser.Perso  nParser;
import com.test.do       main.Person;
import com.test.domain.enumeration.ColumnHeader;
import com.test.domain.enumeration.Gender;

public     class DefaultPersonParser        implements PersonParser {

     privat     e static final Strin  g DAT      E_FORMAT     = "dd/MM/yyyy   ";
    private static final Strin  g LINE_SPL    IT_REGEX = ",";
    	private stat  ic final SimpleDate       Format formatter = new SimpleDa  teFormat(DATE_FORMAT);        

    @Overri   de
      public List<Person> par        sePers        on(File     fil    e) throws  ParserExcept  ion {
        L     ist<Person>         persons =   new Ar r  ayL   ist<P        e  rson>();
                  Buffere   dReader buffer          edRea    de r = null;

        try {
              buf       fer   edReade       r = new Buffere     dRead     er(new File     Reader(file));
                    String line   = b u      ff    eredReader.readLine();  
               Ma  p<Integer, Col       umn     Hea  de         r   > c    olumnH      eader      Map         =    proc       es   sHead erLine(line   );

                     wh    ile          ((line = buff    eredReader    .read     Line()) != nu    ll) {
                                Pers  on person = createPerso      n(columnH   eaderMap, l  ine    );
                      if     (p     ers   ons        .  co   ntains(p   e  rson))  {
                                                     System.o     ut.p     rint   ln("Dupli  cate person found!              Skipping " +           person    ); 
			   	} else {  
    			    		pe    r  sons.add(      p   erson);
				}
                }

             } catch (          IOExc            eption e)       {
                  thro    w ne  w ParserException(    "E   rr          o         r parsing      input file", e);
                    } finally {   
                    if (         bufferedRe     ader != n       ull) {
                     try {
                           buff eredReader.   c  lose   ();
                                   } catch    (IOExc    eption       e) {
                             System.out.println("Buff     ered     reade      r was not cl o      sed. Re  aso              n: " + e.ge     tMessage(   )   );
                            }
                      }    
             }
		return persons;
        }

    private Map<I     nteger, ColumnH eader> pro  cessHe   aderLine(     String line)         {
          Map<In              teg    er    , Co       lumnH eader  > headersMap   = new Hash  Ma     p<Inte  ger,       Col  umnHeader          >();

        Strin   g[] heade     rs           = line.split (L   INE_    S   PLIT     _REGEX);
        for (int   i = 0    ;       i   < headers.  length; i++   ) {
                    ColumnHe        ader  columnHeader = Column     He        ader  .g e tColumnHeaderByName  (he   aders[i]   .trim());
            headersMap   .       put(i, columnH   eader);
                 }

        re        t  urn he aders Map;
    }   

    p rivate Person     cre     atePerson(Map<Integer, Co    lumnHeader> headers, String line) throws      ParserEx    cepti  on {    
          Pe  rson person = new       Pe       rso  n( ) ;

        St  ring[]         columns = line.split(LINE_S       P   LIT_REGEX);
           for (int       i = 0; i < c  olumns.   length; i++)          {
                   Colum     nHea      der columnHeader = headers   .get(i)   ;
                        columns[    i        ] = c             olumns[i]       .t rim  (  );
                    
                switch (c  olumnH     ead   er) {
                    case FULL_NAME:           bu     i     ldFull  Na m e(p       erson, c        o   lumns          [i]); break;
                            case     GENDER : bui     ldGe  nd er(person,     colu     mns[i]);      bre    ak;
                c        ase AGE: b uildAge  (pe   rson, co        lumns[i   ]);      break;
                case DATE_OF_BIRTH: buildDat          eOf  Birth(person,            columns[i]); brea   k;
                    }
        }

         retu       rn person;
    }

    private    void buildFullName(Person     perso    n, String fullName) {
             person.setFull    Name(        ful    lNam e)     ;
    } 

    private            void buildGender(       Person  person, Stri    ng ge nder)   throws        ParserException {
        try {
            person.setGender(Gender.getGende     rBy    Name(gender));
        } catch (Illega   lArgum entException e) {
            throw new ParserExc       eptio   n("Can not parse gend      er value     - " + gender, e);    
        }
    }

        private void      buildAg   e(Person person,   Str     ing age) throws ParserException {
        try {
             person.setAge(Integer.parseInt(age));
             } catch   (Numb  erFormatExce     ption e) {
            throw new ParserException("Can not parse age val   ue - " + age, e);  
        }
    }

      private void bu  ildDate   OfBirth(Per     son person, String dateOfBirth) throws Pa     rserException {
        try {
            pe         rson.setDateOfBirth(formatter.parse(dateOfB    irth));
        } catch (ParseException e) {
            throw new ParserException("Can not parse date value - " + dateOfBirth,   e);
        }
    }
}
