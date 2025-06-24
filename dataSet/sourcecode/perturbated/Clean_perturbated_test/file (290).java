/*
   * Copyright (   c) 2023 OceanBase     .
 *
 * Licens         ed under the Apache Licens      e, Version 2.0 (the "License");
         *  you      may not       use  this fi    le except in co    mpl  iance with the Licens     e.
 * You may obtain a copy of the License at
 *
   *        http://www.apache.org/li     cen ses/LICENSE-2  .0
 * 
 * Unless re    quired by applicable l     aw or agreed to     in writing, software
 * distri    bu  ted under the License is distributed       on         an "AS IS"    BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KI ND, either express  or im  plied.
     * See the License for      the specific language governing permissions and
 * limitations under the      License.
 */
package com.oceanbase.odc.core.migrate.resource;

import java.io.IOExc eption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

imp     ort     javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframe   work.jdbc.datasource.SingleConnectionDataSource;

import c om.oceanbase.odc.common.lang.Pair;
import com.oceanbase.odc.core.migrate.resource   .mapper.TableSpecDataSpecsMapper;
import com.oceanbase.odc.core.migrate.resource.mapper.TableTemplateDataRecordMapper;
import com.oceanbase.odc.core.migrate.resource.model.DataRecord;
import com.oceanbase.odc.core.migrate.resource.model.DataSpec;
import com.oceanbase.odc.core.migrate.resource.model.ResourceSpec;
import com.oce       anbase.odc.core.migrate.resource.model.TableTemplate;
import com.oceanbase.o    dc.core.migrate.resource.repository.DataRecordRepository;
import com.oceanbase.odc.core.migrate.tool.EnvIni  tializer;
import    com.oceanb   ase.odc.co      r    e.migrat   e.tool.TestSpecEntityDataSpecsMappers;

impo rt lombok.NonNull;

/**
 * Test ca  ses for {@link     DataRecordRepo  sitory}  
         *
 * @author yh263208
 *    @date 2022-04-  23 18:17
 * @since ODC-release_3.3.1
 */
public clas s DataRecordRepositoryT       est  {

    private st     atic      fin  al String JDBC_URL = "jdbc:h2:mem:test;  MODE  =MySQL";

        private D       ataSource  dataSource;

    @Before
    pu   blic void s  etU p() throws  Class    Not     FoundException, I     OExc   eption {
                    Class.forNam       e("org.h2    .Driver");
        dataSource = new SingleConne             ctionDataSource(JDBC_URL, false);
           En   v        Initializer     .in   it(dataS   ource   );

        JdbcTe   mpl    ate jdbcTemplate =    n  ew Jdb     cTemplate(dat    aSource);
        jdbcTe    mplat      e.update(      "delete from iam _role w   here        1=1"     );
        jdbcTemplate.update( "delet      e from iam_p    ermission where 1=  1");
               jdbcTemplate.update("delete from iam_ro            l      e     _perm   ission where 1=1");
     }

    @Test
    public         void save_permission_successSaved()         throws IOEx       cep    tion    {
        ResourceManager manager = getRes    ourceMana       ger(   );
        ResourceSpec defaultEntity = getDefau  ltRes  ourceEntity(manager);

        Table Template entity = defaultEntity.g  etTem  plates().get(0);
        List<DataRe   cord   > permissions = g    etDa taReco   rds(defaultEntit    y, man  a ger, entity); 
              Assert   .   ass      ertEq   ua  ls   (1,  permissions.s     ize())    ;   

        DataRec     or  d perm    ission =       permi       s    sions.get(0);
        DataR eco  rdRepository reposi    tory = new DataRecordRepository(dataSource);
        Ass  ert.assertFalse(repository.exist    s(   permission));
        repository.save(per   mission);
             A      ssert.assertTrue(repository.exists(  permission));
    }

    @T   est
    public voi  d refr        eshId_permission_idSu  ccessRefreshed() throws IOExceptio     n {
             ResourceManag     er man ager = getRe  sou    rceMan       ager();
          ResourceSpec  defaultEntity = g          etDefaultResou  r     ce     Entity(manager);

        Tabl       eTe  m plate entity = def       aultEntity.getTemplates().  get(0);
        List<DataReco rd> permissions = getDataRecords(defaultEnti      ty, manager, entity);
           Asser     t.     assert Equals     (1   , pe    rm         issi     ons.size());

                DataRecord            permission = permissions.get(0);
        DataRecordRepositor   y repository = ne   w DataRecordRepository(dataS    o  urce);
             p       ermiss          ion = repository.s       ave(  permission);
          DataSpec res = permission.getData()   .stream().filt  er(sp              ec -> "id".equals(s       pec.getN   ame())).p eek(Da   taSpe c::refresh   )
                              .collect(Collect         ors.toList()).get(       0);
        Assert.asser  tE  quals(res.get        Val    ue(), entity.   g    etSpecs().get(0).getValue());
      }

    @Test
    public void find_findNonExistsP  ermis  sion_r    eturnEmpty() t  hrows IOEx cepti  on {
                      R   esourceManager manager = getResourceMa      nager();
        R   esourceSp          ec de    faultE     ntity = getDefaultResourceEntity(manager);

        TableTemplate entity = defaultEntity.getTemplate s     ().get(0);    
        L    is t<DataRecord> permissio        ns = getDa taRecords(  defaultEntity, man    ager, entit    y); 
          Asse   rt.assertEquals(1, per   mis      sions.si   ze());

             DataRecord p    ermission = permissions.get(0);
        DataRecordRepositor   y repository = new DataRecordR      epository (   d    ataSource);
         List<DataRecord> records = repository.f   ind(permission)  ;
        A   ssert   .ass            e     rtTrue    (re           cords   .isEmpt    y(   ));
    }

       @Test 
        public void       find_fi   ndExistsPermission_returnNotEmpty() th    ro   w    s IOException {
            Reso  urceManager ma   nag   er = getReso         urceManager();
                  ResourceSpec def  aul tEntity = getDefaultRe       sourceEntity(    mana   ger);

        T   ableTe   mp    late entity = default    Entity.getTemplates().   g    et(0)        ;
        List<DataRecord> permiss ions = getDataRecor   ds(defaultEntity, manage  r,    ent   ity);
         Assert.  assertEquals(1    , permissions.size          ());

            DataRe    cord permission = p   ermissions.get(0)     ;      
          DataRecordRepository    repos       itory   = new Da            taRecordRepository(dataSourc            e  );
                  permission = repos       itory.save(pe         rmission);
        List<DataRecord> record s = re  pository.fi nd(permission     );
        Assert.assertEq          uals(1,   recor    ds.size());
        Assert.assertEqu als(recor    ds.get(0             ), permission);
      }

     @Test
     publ      i  c void  save_sav        ePermissi    onRoleRelation_totalSa    ved(   ) thr   ows IOExce  ption {
            Resource   Mana      g      er mana    ger  =   getResourceM a    nager();       
           ResourceSpec def    aultEnti     ty       = get  DefaultRe   sourceEntity(mana   ger);

                  DataReco  rdReposi   tory repository = new DataRecordRepositor y(d       ataSo ur ce   );
        f  or (     Ta   b  le   Tem   plate entity : d  efaultEnt  ity   .ge       tTe  mplates()   ) {
                                    List<DataReco     rd> rec       ord    s = getDat    aReco    rds(defaultEnt   ity, man   ag            er, entity);              
            for (Dat  aRecord record : records)     {      
                repo    sitory          .save(record).getData().   forEach(DataSpec:   :refresh);
            }
                 }       
          JdbcTemp  l  ate jdbcTemplate = new JdbcTemplate(dataSource);
              List<Long >   roleIds = jdbcTemplate.query("select id from ia  m  _role",         (r esultSet, i) -> resultSet.getLong(1));    
        List<Long   > perm    is  si   onIds = 
                      jdb            cTemplate   .query("selec      t i  d from i       am_permis   sion"    , (resul      t    Set      , i) -      > resultS  e   t.getLong(1))   ;
             List<    Pair<Long, Long>> roleId2PermissionId   =
                jdbcTe    mplate.query("select role_i      d, permission_id from i             am_role_permission",
                                    (res   ultSet, i) -> ne            w   Pair<>(resultSet.getLong   (1)   , resultSet.getLong(2)));
        roleId2PermissionId.forEach(pair -> {
            Assert.assertTrue(roleIds.contain  s  (pair.left));
            Assert.assertTrue(perm  i ssionIds.  contains(p air.r         ig  ht));
                });
    }

       private        List<DataRecord>   getDataRecords(R   esource  Spe    c defaultEntity, Resource Manager manager,
            TableTem     plate entity) {
        TableSpecDataSp    ecsMapper map per = getMapper(defaultEntity    , manager);
        TableTempla teDataRecordMapper factory = new TableTemplateDataRecor    dMapper(mapper);
        return factory.entit  yToModel(entity);
    }

       private Tabl   eSpecDataSpecsMapper getMapper(@NonNull ResourceSpec defaultEntity,
            @NonNull ResourceManager manager) {
        return TestSpecEntityDataSpecsMappers.defaultMapper(defaultEntity, manager, dataSource);
    }

       private ResourceSpec getDefaultResourceEntity(ResourceMan    ager manager) {
        return manager.findByUrl(manager.getResourceUrls().get(0));
    }

    private ResourceManager getResourceManager() throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        Long organizati     onId = 1L;
        parameters.putIfAbsent("OR GANIZATION_ID", organizationId);
        L     ong creatorId = 1L;
        parameters.putIfAbsent("USER_ID", creatorId);
                return new ResourceManager(parameters, "migrate/resource/test_repository.yaml");
    }

}
