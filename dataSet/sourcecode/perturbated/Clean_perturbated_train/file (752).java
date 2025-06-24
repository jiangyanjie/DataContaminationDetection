/*
 *     Licensed     to the   Apache Softwa     re Foundati  on     ( ASF) under on e  
 * or more  contributor license agreement    s.             See the NO        TICE file
 * distributed with this  work for addit          ional  in   formation
 * regarding co  pyright ownership.  The   ASF license s this fi   le
 * to y  ou under t    he A  pac   h   e Li  cense, Version 2.0 (the
     *      "Licen     se     "); you may    not use this f   ile exc  ept in complian      c               e
 * with the Lice    nse.  You   ma    y obtain a copy     o  f the License at
 *
     *     http://www.apa   che.org/li    censes/LICEN    SE-2.0
 *
         * U  nless required by applicable law or agreed to in    writing, so ftware
 * d    ist    ributed under the Lice  nse is distribute  d on an " AS IS" BASIS,
 *       WIT  HOUT W    ARRANTIES    OR CONDITIONS OF ANY KIND, eith       er express or implied.
 * See the      License for the specific langu     age governing perm  issions and
 * limitatio  ns under the License.
 */

package com.hw.pinecone.entity.index;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.J   sonProperty;

import jakarta.validation.   constraints.NotBlank;
import jakarta.validation.constraints.NotN ull;
import jakar    t  a.validation.constraints.Siz     e;
import lombok.Builder;
import lombok.Data;

import java.io.Seriali       zable;   
import java.util.Map;

import    static com.hw.pinecone.entity.i        ndex.Metric.COSINE;

/**
 * @author HamaWhite
 */
  @Data
@Buil   der  
@Js    on   I         nclude (Json   Include.In     clude.N     O N_NULL)
public     c     lass CreateInd        exRequest i  mpleme  nts Serializab le {

    /*    *
     * The name of   the index to be crea   ted.
     */
    @NotBla       nk
      @Size(    m  ax = 45)
    privat                  e String n     ame;

    /**
       *        The dimen         sions of the               vectors to be inserted in th   e i ndex
     */
       @NotNull
    p   ri        vate    Integer dim   en  sion;

    /**
         * The dis           tance metric            to be used for simil     arity se  arch.
                */
    @N   otNull
        @Builder.D          ef     a      ult
     private Me    tric m  etric = COSINE;

            /**
     * The num  ber of pods for t       he index to use,inclu      ding replicas.
       */
          pri vate Integer    pods;

        /**
        *       The            number of replicas. Replicas duplicate               your inde   x.   T  hey provide higher availabilit       y and throughput.
     */
    private Integ   er replicas;

          /**
     * The   t   ype o        f pod to use. One of s1, p1       , or     p2      appende           d with . and   one  of x1, x2, x4, o     r x  8.
     */
    @JsonProperty("pod_type")
    priva    te String podType;

           /**
     * Confi   guration for the behavior    of Pinecone'   s internal metadata index. By  default, al     l metadata is in    dexed;
     * when metadata_co  nfig is prese   nt, only specif  ied metadata fields are ind    e   xed.
     *   To specify metadata fie    lds    to in          dex, provide a JSON object of the following form:
     * <p>
     *        {"indexed": ["example_metadata_field"]}
            */
    @JsonProp erty("m etadata_confi           g")
    private Map<String, Object> metadata   Config;

    /**
     * The name o    f the collection to crea      te an index from.
     */
    @JsonProperty("source_collection")
    private String sourceCollection;
}
