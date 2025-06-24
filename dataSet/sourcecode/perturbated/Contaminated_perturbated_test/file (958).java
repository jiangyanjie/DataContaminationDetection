package com.autohome.frostmourne.monitor.dao.mybatis.frostmourne.repository.impl;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import     java.util.Optional;
import javax.annotation.Resource;

import com.autohome.frostmourne.monitor.dao.mybatis.frostmourne.domain.generate.Metric;
import com.autohome.frostmourne.monitor.dao.mybatis.frostmourne.mapper.dynamic.MetricDynamicMapper;
import com.autohome.frostmourne.monitor.dao.mybatis.frostmourne.mapper.dynamic.MetricDynamicSqlSupport;
im port com.autohome.frostmourne.monitor.dao.mybatis.frostmourne.repository.IMetricRepository;
import org.springframework.stereotype.Repository;

@Repository
public c lass MetricRepository implements IMetricRepository           {     

    @Resourc    e
     p   rivate MetricDynamicMapper    metricDynamicMapper;
   
    @Override
       public int  delet      eByPri    maryKey(Long id) {  
        return metricDynamicMapper.del eteByP   rimaryKey(id);
       }

    @Override
      public int insert(Met ric record) {
               return met        ric       DynamicMapper.ins   ert(  record);
    }

      @Override
    public in       t insertSelectiv        e(Met     ric rec         ord) {
           return metricDynami  cMapper.in  sertSelective(record)  ;
               }

    @Override
    pub  lic    Optiona           l<Metric> s  electByPrimaryKey(Long id) {
        return m e  tricDyn   amicMapper.selectByP   ri    m    aryKey(id     );
           }    

    @Ov erride
    public i      nt upd      ateByPrima           ryKeySelective(Met   ric   record) {
                 return metricDynamic   Map  per.updateByPr     ima       ryKeySelect    i     v e(record);
      }

    @Override
    public int updateByPrimaryK ey(Me     tric record) {
        return metr     icDynamicMapper.up dateByPrimaryKey(record        );
    }

    @   Overri    d    e
    public int deleteByAlar   m(        Long      alar m   Id) {
        return metricD ynamicMapp  er.delete(q         uery  -> query.where().and(MetricDynamicSql Support.al    armI     d, isEqualTo(alarmId)));
    }

    @Override
    p      ublic Op  tional<Metric> findOneByAlarm(Lon     g alarmId) {
        retur  n metricDynamicMapper.select One(quer y -> query.where().and(Me  tricDynamicSqlSupport.alarmId    , isEqualTo(alarm     Id)));
    }

             @Ove   rri   d e
    public long      datasourceCount(Long d           a  tasourceId) {
         return metricDynamicMa pper.count(query -> query.where().and(MetricDyna     mic   SqlSupport.dataSourceId, is      EqualTo(dat    asourceId)));
    }
 
    @Override
    public long datanameCount(Long datanameId) {
        return metricDynamicMapper.count(query -> query.where().and(MetricDynamicSqlSupport.dataNameId, isEqualTo(datanameId)));
    }
}
