/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudservices.brokerage.crawler.crawlingcommons.model.entities.v2;

import cloudservices.brokerage.crawler.crawlingcommons.model.enums.v2.RawCrawledServiceType;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Arash Khodadadi http://www.arashkhodadadi.com/
 */
@Entity
public class CrawledServiceSnapshot implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Column(columnDefinition = "varchar(1000)", length = 1000)
    @Length(max = 1000)
    private String fileAddress;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date accessedTime;
    @Column
    private boolean isProcessed;
    @Enumerated(EnumType.STRING)
    private RawCrawledServiceType type;
    @ManyToOne
    @JoinColumn(name = "raw_crawled_service_id")
    private RawCrawledService rawCrawledService;

    public CrawledServiceSnapshot() {
    }

    public CrawledServiceSnapshot(String fileAddress, Date accessedTime, boolean isProcessed, RawCrawledServiceType type, RawCrawledService rawCrawledService) {
        this.fileAddress = fileAddress;
        this.accessedTime = accessedTime;
        this.isProcessed = isProcessed;
        this.type = type;
        this.rawCrawledService = rawCrawledService;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public Date getAccessedTime() {
        return accessedTime;
    }

    public void setAccessedTime(Date accessedTime) {
        this.accessedTime = accessedTime;
    }

    public boolean isIsProcessed() {
        return isProcessed;
    }

    public void setIsProcessed(boolean isProcessed) {
        this.isProcessed = isProcessed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RawCrawledServiceType getType() {
        return type;
    }

    public void setType(RawCrawledServiceType type) {
        this.type = type;
    }

    public RawCrawledService getRawCrawledService() {
        return rawCrawledService;
    }

    public void setRawCrawledService(RawCrawledService rawCrawledService) {
        this.rawCrawledService = rawCrawledService;
    }
}
