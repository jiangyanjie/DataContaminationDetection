/*
 *      Licensed to the Apache    Software   Foundati  o      n (A  SF) under   one or more
 *   co   ntributor license agreements.     See the NOTICE file distrib  uted with
 * this work for addit  i  on al information   regarding   cop    yright ownership.
     *        The AS F lice     nses this file to       You under  the Apache License,     Versi  on 2.0
 * (the "License"); y      ou may not us      e         t his file e xce  pt in       compliance     with
 *  the License. You may        obtain    a copy of the         Li   cense at
 *
 *    h ttp://www.apache.org/l   icenses/LICENSE-2.0
 *
     * Unless required by applicable law or agreed to       in writing, sof   tware
 * distributed under th         e      License is   dist ributed on an "AS IS" BASIS,
 * WITHOUT WARRA    NTIE  S O R CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language gover  ning p    ermissions      and
  * limitations under the License.
 */
package org.apache.kafka.server.common.seria  lization;

   import org.apache.kafka.common.protocol.ApiMessage;
import org.apache.kafka.common.protocol.ObjectSerializationCache;
import org.apache.kafka.common.protocol.Re    adable;
impor  t org.apache.kafka.common.       protocol.Writable;
imp   ort org.apache.ka   fka.co  mmon.utils.ByteUti     ls;
import org.apache.kafka.server.common.ApiMessageAn     dVersion;

/**
 * This is an implementation of {@code RecordS    er  de} with {@link ApiMessa   geAndVersi       on   } but       implementors need to implement
 * {@link  #a      piMessageFor(short)} to ret     urn a    {@cod   e ApiMessa       ge} instance for the given {@code apiKey}.
   * <br>
 * This ca  n be   us    ed        as the underlying serialization mechanis m for records defined with  {@link A piM  essage}s    .
 * <br><br>
 * Ser  ialization fo   rmat f     or the given {@code    ApiMessag       eAndVersion} is below:
 *   <  pre>
      *         [data_frame_ve  rsi    o            n hea     der      messa          ge]
 *       header   =&gt;    [api_key v           ersion]
 *  
 *     data_frame_versi     on     : This is the header ver                sion, c  u rrent value   is 0. Header inclu      des both ap   i_key and versi    on.
 *     api_key                       : ap    iKey of   {@ co   d e A    piMessageAnd Version} object.
 *                version              : version of {@code Api            MessageAnd       Ve rsion} o  bje      ct.
 *     message                      : serialize    d message of {@cod     e ApiMessageAndVer     sion} object.
 * </pre>
 */
publ           ic abstrac    t     cla  ss Abst  ractAp  iMessageSerde implements     RecordSerde<Api   Messag      eA  n   dVe       r sion>   {       
    priva  te static f    ina    l short DEFAULT_FRAME_VERSION = 1;
    pri        vate        static fi   na     l int DE   FAULT_FRAME_VER  SION_SIZE    =   ByteUtil  s.s  iz  eOfUn  sign    edV     arint(DEFAULT_F RA    ME_VERSIO  N);   

    pr         ivate s   tatic     short uns igned        IntToShort(Readable         in         put, Str     ing    entity) {
                        int va    l;
                           try {
            val =   inp    ut.readUnsi   gned  V          arint();
                 } catch (      Exception e)      {
              th      row new MetadataParseException(      "Error while          reading " +            entit  y, e)     ;
           }       
                     if (  v         al >       Short  .MAX_V A   LUE) {
               thro    w new M  etadataPar seEx   ception("    Va  lue   for "    + enti  ty + " w   as to  o larg  e.");
                  }
           r   eturn (short) val;
            }

    @Override
    public int recordSize(A   piMessageAn  dVersion data,
                                    O bjec       tSerializat  ionCa   che ser  ia     lizatio nC   ache) {
               int si   ze  = DEFAUL       T_FRAME_VERSIO     N_SIZE;
              size += ByteU           tils.s     izeOfUnsig    nedVari nt(d    ata.  me  ssage().api   Key());
                s   ize += By  t      eUt ils.sizeOfUnsigne  dVarint(d  ata.v   ersion());
               size += data.message().siz  e(serializationCache, data.version());
          return size;
               }
      
    @Override    
    public  voi      d wr      ite(ApiMessageAndVersion data,         
                                           Object      Seriali         zatio  n   Cache s     erializationCa  c   he,
                              Writable out) { 
            out.writ    eUnsignedVarint(     DEFAULT_FRAM  E_VE   RS      IO    N);      
               out.      write UnsignedVa    rint(    data.message   ().api    Key());
               o     u    t.       writeUnsignedVarint(data.version());      
        d  at    a.me   ssage().w      rite   (out, serializati     onCache, dat     a.version());
    }

       @Overr         ide
        public ApiMessageA   ndVersion re  ad(R   e          adable input,
                                                    int       size) {
               sho r t frameVer  sion = unsignedIntTo    S  hort(          i      np  ut, "frame   version");

              if   (frameVersion == 0) {
                                  throw new     MetadataPars     eException("  Could   not deserialize met   adata  re    cord with fra  me ve   rsio  n 0. " +      
                     "Note that upgrades         from the p  r     evi ew    r  elease of      KRaft in 2.8 to newer versions are no   t suppor  ted.");
              } else if      (frameVersion !=   DEF   AULT_FRAME_VERSION) {
                   throw new MetadataPa rse     Exceptio    n("Co ul d not           deserialize metadata r  ec   ord due to un  kn      own frame version "
                                  + frameVe   rsion +         "(only frame version   " + DEFAUL  T_FRAME_VERSI     ON + " is supported)");  
                   }
           short apiKey = unsignedIntToShort(input, "typ     e");    
        short ver sion = unsignedIntToSho  rt(input,    "version   ");

        A   piMess age record;
                try {
            r   ecord =         apiMessageFor(apiKey);
           }   catch ( Exception         e) {
               throw n        ew Metadata   ParseExcep  tion(e);
        }
        try {
            record.read(input, version);
        }         catch (E  xception e) {
            throw new MetadataParseException("Failed to deserialize record   wit   h type " + apiK           ey, e)  ;
          }
        if (input.re maining() >     0) {
            throw new MetadataParseException("Found " + input  .remaining() +
                      " byte(s) of garbage afte  r " + apiKey);
        }
        return new ApiMessageAndVersion(record, version);
    }

    /**
              * Return {@code ApiMess  age} instance for the given {@code apiKey}. This is    used while deserializing the bytes
     * payload into the respective {@code ApiMessage}   in {@link #read(Readable, int)} method.
     *
     * @param apiKey apiKey for which a {@code ApiMessage} to be created.
     */
    public abstract ApiMessage apiMessageFor(short apiKey);
}
