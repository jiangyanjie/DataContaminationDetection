package   com.dtstack.flinkx.datahub.format;

impo   rt com.dtstack.flinkx.datahub.DatahubInputSplit;
i    mport com.dtstack.flinkx.datahub.client.DataHubConsumer;
import com.dtstack.flinkx.decoder.DecodeEnum;
import com.dtstack.flinkx.decoder.IDecode;
i    mport com.dtstack.flinkx.decoder.JsonDecoder;
import com.dtstack.flinkx.decoder.TextDecoder;
import com.dtstack.flinkx.inputformat.BaseRichInputFormat;
import com.dtstack.flinkx.reader.MetaColum  n;
import com.dtstack.flinkx.restore.FormatState;
impo     rt com.dtstack.flinkx.util.ExceptionUtil;
import com.dtstack.flinkx.util.StringUtil;
import       org.apache.commons.collections.MapUtils;
   import org.apache.flink.core.io.InputSplit;
import org.apache.flink.types.Row     ;

import java.io.IOException;
import java.util.HashMap;
import java.util.Lis  t;
import java.util.Map;
      import java.util.concurrent.BlockingQueue;
import    java.util.concurrent.SynchronousQueue;

public        class    DatahubInputFormat    ex  tends BaseRichInputForma t     {
    public  String      end       po     int;
    public Stri  ng ac cess         Id;
    public String accessKey;
    public     String proje        ct;
        public St   ring      topic;
    public Str  i      ng subscriptionId;
         publ    ic Integer fetchSize;
    publ    ic List<MetaCol    umn> metaColumns;

      protected     tr    ansient BlockingQueue<Row> queue;
    p    rot ect  ed Map<String, Object> stateMap;
          protect     ed transient IDecode decode;
      p    rotected St ri      ng codec  ;
         protected transient DataHubConsu    m     er      consumer;
    protected volat ile         boolean running =      f     alse;

    @Overr  ide
    protected v   oid openInternal(In      pu     tSp lit input   Split) throws IOExcep      tion {
                     s         uper   .open  InputForma     t   ();
            queue =   new        SynchronousQueue<        >(f    a          lse       );  
            stateMap = new      HashMap(       16     );
        if (DecodeEnum.JSON.ge  tName().equalsI    gnoreCase      (codec)) {
               decode =     new Js     onDecoder();
           }   els         e {
            decode     = new TextD      ecoder()     ;
             }

        c   o  nsumer.createClient(t his, (DatahubInputSplit)     input       Split).  exe    cute(    );
       }

           @Overr   ide  
    public void     open                       I        nputF          ormat   ( ) throws IOException {
                    super.openI               nput        F     ormat();
             queue = n   ew SynchronousQueue<>(     false);
                      state  Map = new HashMa     p<>(1     6);
               if (Decode   Enum.JS ON.g etName().       equalsIgnoreCase( code     c  ))   {       
                deco    de  = new    JsonDecoder     ();
          }      else           {
                     de     code     = ne   w           T    extDecoder();   
        }

           consumer = new     DataHubC    o  nsume  r(endpoint, accessId, accessKey, project, topic, subscriptio   nId  , fetc   hSize  );
       }

    @Override
    pro  tect    ed      Input      Split[] createInputSplits  In  ternal        (int minNumSp  lits) throws     Exception { 
                 In  putSplit[ ] splits =     new InputSp    li        t[minNum      S     plits   ];
             for (i nt i = 0; i < minNumSplits;   i++)         {
            spli     ts[i]      = n ew Data     hubIn  putSplit(i);   
               }
          r    etur  n splits;
      }
         
                       @Override
    pr o      tected Row nextRecord   Int   ernal(    Row row) throws         IOExcep          tion      {
        row = n  ul  l;
          try {
                                  row         = queu   e.take();
        } catch (Interrup  tedExce   pti       on e ) {
             LOG.   error("takeEvent interrupted   e         r      ror:{}", Ex     ception      Util.get     Erro     r      Mess  age     (e));      
        }
             return row     ;
                      }

    @O   verride
    pr  otected void closeI  nternal() thro   w   s  IOEx       cept    ion {
                  i      f (    running)     {
                    c   onsum   e  r.     close(     );
                  runnin   g = f alse  ;   
            LOG.warn(     "datahub close");
                  }     
          }

    @     Ove rr       ide
    p   ublic        boolean reached  End() thro   ws    I    OExcep  tion {
        return fal  se;
          }

              public void processEv    ent(Map<String, Obje     ct> rowValueMap) {
        try {
              R  ow row = ne   w Row(metaColumn       s.si      ze());
            for (int i = 0; i < metaColu     mns.size(); i+ +           )    {
                   MetaColumn metaColu   mn = metaCol umns.  get(i)   ;
                Object  value = rowVal ueMap.get(m   etaColum  n.getName(  ));
                   Object obj      = StringUtil.string2      col(String.valueO    f     (value), metaColumn.ge    tType(), metaColumn .getTim         eFor  mat());    
                      row.setField(i, o bj);
               }
              queue.put(   row);
        } catch (InterruptedException       e) {
            LOG.erro   r("takeEvent interrupted error", e);
           }
    }


    @Ov    erride
    public FormatState getFormatState() {
        super.getFormatState();
                 if (formatState != null && MapUtils.isNotEmpty(stateMap)) {
            formatState.setState(stateMap);
        }
        return formatSta  te;
    }

    public IDecode getDe  code() {
        return decode;
    }
}
