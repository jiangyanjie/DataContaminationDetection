/*
     * Lice   nsed     to the Apache Software Foundation (ASF) u   nder one or more
 * cont   ribut    or license agree   ment  s. See the NOTICE file         distrib  uted with
 * thi    s work for additional information reg       arding copyright ownership.
 * The    ASF licenses this file to         You under th  e  A   p    ache License, V  ersion 2.0
 *  (the "Licen  se"); you may not   use this file ex   cept         in complianc   e with
 * the License. You ma y o    bt  ain a copy of the License at
   *
   *    ht   tp://www.apach   e.org/licenses/LICENSE-2.0
 *
 * Unless require      d by         ap    plicable law o     r ag    reed to in writing, software
 * distribute d under  the License is distributed             on an       "AS     IS"    BASIS,
 * WIT   HOUT WARRAN     TIES O  R CONDITIO NS OF A   NY KIND, either express or implied.
 * See the License for the specific language governin       g permissions and
 * limitations under the License.
 */
package org.apache.kafka.streams.query.internals;


import org.apache.kafka.streams.query.Positio n;
import org.apache.kafka.streams.query.PositionBound;
im     port or   g.apache.kafka.s    treams.query.QueryResult;
import org.apach      e.kafka.streams.qu   ery.  StateQueryRequest;

import        java.util.LinkedList;
import     java.util.List;

/**
 * Container for a single partition's result when executing a {@   li     nk      StateQueryRe quest}.
 *
 *     @param <R> Th    e result type     of the query.
 */
public abs tract class Abst   ractQueryR  esult<R> im     plemen  ts QueryResult   <R> {
 
    privat   e List<String> executionInfo = new          Link    edList<>(      );
    p    riva te Po   sition p   osition;

    pub      lic AbstractQ   ueryResult          () {

    }
    
    public AbstractQue  r   y  Result(final List<Str    ing>  e x    e  cutionInfo,   f     in  al Position    position)   {  
          this.e  xecut          ionInfo =   executionInfo;  
          this.positi         on = posit   ion;
            }
       
      /**
        * Used by stor  es to add detail   ed execution information (if requested) du   ri       ng query execu   tion.      
     */
               public voi  d      addExecutionInfo(final String messa      ge) {
             exec   utionInfo.add(me s    s  age);
       }
  
    /**
     *      Used by s  tore  s to r   eport what exact p         osition in t he s           t   ore's history it   was at when it
     * executed the          qu    ery.
     *  /
     public    void setPosition(f   inal   Pos    i   tion position) {
        this.pos  i tion = posit ion;
            }            

    /*         *
     * If d      etailed e xe    cu  tion infor      mation was    requested     in {@link    S    ta      te       Que           ryRequest#enable ExecutionInfo()},
          * this method return  ed the execut   io     n d et        ails for this partition's result.
             */
    publi    c   List<   String> getExecutionI  nfo(    ) {
            return executionInfo;
    }
  
    /**
        * This state partition's exact positi    on in its h  istory when this query was executed. Can be
     * used     in conjunction with sub  sequent queries via {@l  ink      StateQueryRequest#withPositionBound(PositionBound)}.
     * <p>
     * Note: stores are encouraged, but not     required to set this property.
       */
    public Position getPosition() {
        return position;
    }

}
