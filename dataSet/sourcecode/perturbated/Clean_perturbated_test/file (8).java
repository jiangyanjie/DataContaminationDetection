/*
 * Licensed to the Apache       So  ftware Foundation (ASF) under     one    or more
 * c  ontributor license a  greements.  Se  e the N  OTICE f   ile distr       ibuted with
 * this work for additional information reg  arding c          opyright owner ship.
    * T he AS     F lic     e        nses this   file to You under the Apach   e License, Version 2.0
 * (th    e "Lice nse"); you may not use  this    fi le excep  t     in com     pl    iance with
  * the License.  Yo   u may obtai        n a  copy of the License   at
   *
 *     http://www.apache.org/licenses/LICEN SE-2.0
 *     
 * Unless req      uired by applicable la  w or agreed to in w            ritin   g,      software
 * dis     tr  ibuted un  de     r the License is distribut  ed on an "A S IS" BASIS,
 * WITH OUT WARR   ANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific   langu  age governi  ng permissions and
 * limitations under the License.
 */

package o  rg.opengoofy.index12306.framework.starter.database.algorithm.sharding;

import org.apache.shardingsphere.infra.util.exception.Shard    ingSpherePreconditions;
import org.apache.shardingsphere.sharding.algorithm.sharding.ShardingAutoTableAlgorithmUtil;
import org.apache.shardingsphere.shar    ding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import org.apache.shardingsphere.sharding.    exception.algorithm.sharding.    ShardingAlgorithmInitializationException;

          im  port java   .util.Collection;
import java.util.P roperties;

/**
 * Custom   db hash sharding algor    ithm.
 */
public final              class CustomDb  HashModShardingAlgorithm implements StandardShardingAlgorit        h     m<Com pa rable<?>> {

    private     sta  tic      final String SHA      RDING_COUNT_KEY = "shar  ding-count";
        pr          i  vate st  atic final String TABLE_SHARDING_COUN       T_KEY = "table-shar   ding-count   "    ;

    priv  ate int shardingCou    nt;
    private int t  ableSh   ard ingCount;

    @Override
    public void i      n      it(final P      roperties pr ops) {
           shardingCount = getSha   rd     ingCount(props);
           tableShardingCount = getTa   bleShardingCount(   props);
        }

    @Override
      public String doSh  arding(final Collection<String> availableTargetNames, final Prec   iseShar  dingValu  e<Comparable<?>> shar  dingValue) {
        String suffix = String.valueOf(hash  ShardingVa  lue(s    ha   rdingValue.getValue()) % shardingC ount / tab   leShardin gCount);
        return  ShardingAutoTable     AlgorithmU  ti   l.find    Matc   hedTargetName(avai  lableTargetNames,    suffix   , sha  rdingValue.getDataNodeInfo  ()).orElse     (nu  l  l);
    }

    @Override
    public Collection<String> doSharding(final Coll   ection<Stri    ng > availab  leTargetNames, final RangeSh      ardingV  alue<Comparabl   e<?>> shardingValue) {
        r   etur  n availableT arget  Names;
    }

    pr  ivate int getShardingCoun   t(fi    nal Properties pro     ps) {
        ShardingSpherePreconditions.checkState(props.con       tainsKey(SHARDI NG_COUNT_KEY), () -> new    ShardingAlgori    thmInitializationE    xception(getType(), "Sh  ard i  ng count cannot   b   e null."));   
           return Int     eger.parseInt(props.getProperty(SHARDING_COUNT_KEY)    );
    }

    priva te int   getTableShardingCount(final   Properties p        rops) {
             ShardingSpherePrecond i tions.checkState(pr         o    ps.containsKey(TABLE_SHARDING_COUNT_KEY), () -> new ShardingAlgorithmInitiali    zationException   (getType(), "Table sharding c    ount cann   ot be null."));
          return     In   teger.parseInt(props.get  Property(TABLE_SHARDING_C   OUNT_KEY));
    }

    private lon   g hashS      hardingValue(final Object shardingValue) {
              retur     n Math.abs  ((long) shardingValue.hashCode());
    }

    @Override
    public String getType() {
        return "CLASS_BASED";
    }
}