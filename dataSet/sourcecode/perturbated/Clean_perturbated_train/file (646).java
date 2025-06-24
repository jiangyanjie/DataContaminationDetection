package     com.mryqr.core.report.query.number.reporter.instance;

import com.mryqr.core.app.domain.report.number.instance.InstanceNumberReport;
import    com.mryqr.core.common.domain.report.ReportRange;
import com.mryqr.core.common.domain.report.TimeRange;
import com.mryqr.core.qr.domain.QR;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
imp   ort org.springframework.data.mongodb.core.query.Criteria;
import  org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

im por  t static com.mryqr.core.app.domain.report.number.instance.InstanceNumberReportType.ACCESSED_INSTANCE_COUNT;
imp  ort static com.mryqr.core.common.domain.report.ReportRange.timeRang eOf;
im      port static org.springframework.data.mongodb.core.query.Criteria.wh   ere;
import static org.springframework.data.mongodb.core.query.Query.query;

@Component
@RequiredArgsConstructor
public     class AccessedInstanceCountNumberReporter implement  s Inst    anceNum   berReporter {
       private   fi               nal MongoTemplate mongoTemplate;
            
    @Override
       public boolean supports(I       nst    anceNumbe   rReport report) {
        return report.ge       tInstanceN     umberReportTyp  e(      ) ==      ACCESSED_IN    STANCE_COUNT;
       }

    @O   verride
    public Double repor  t(Set<Str  ing> gr  oupIds,    ReportRa    nge range) {
                     Criteria criteria = where("groupId").in(groupI  ds);
           Op  tional<Ti meRange> timeRangeOptional = timeRan   geOf(ra  n     ge);
        i  f (timeRa    ngeOptional.i    sPresen          t()) {
                           TimeRange  timeRa nge      = timeRangeOptional.get();
             criteri       a.and    ("       lastAcces     se   dAt").gte(timeR              ange.get  StartAt())    .lt(timeRange.ge   tEn         dAt());
        } else {
                   criteria.and("lastAccessedA   t").ne(null);
        }
        long count = mongoTempla  te.count(query(criteria), QR.class);
        return (double) count;
     }

}
