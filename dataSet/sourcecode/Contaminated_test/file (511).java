package com.sale.db.dao.object;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: shalabh.kulshreshtha
 * Date: 7/29/13
 * Time: 11:04 PM
 */
@Entity
@Table(name = "LOCATION")
public class DBLocation implements Serializable {
    private static final long serialVersionUID = 1207577463247332586L;

    private Integer locationId_;
    private String locationName_;
    private Double latitude_;
    private Double longitude_;
    private Integer locationType_;
    private DBLocation parentLocation_;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCATION_ID", unique = true, nullable = false)
    public Integer getLocationId() {
        return locationId_;
    }

    public void setLocationId(Integer locationId) {
        locationId_ = locationId;
    }

    @Column(name = "LOCATION_NAME", unique = true, nullable = false, length = 128)
    public String getLocationName() {
        return locationName_;
    }

    public void setLocationName(String locationName) {
        locationName_ = locationName;
    }

    @Column(name = "LATITUDE", nullable = false, precision = 13, scale = 8)
    public Double getLatitude() {
        return latitude_;
    }

    public void setLatitude(Double latitude) {
        latitude_ = latitude;
    }

    @Column(name = "LONGITUDE", nullable = false, precision = 13, scale = 8)
    public Double getLongitude() {
        return longitude_;
    }

    public void setLongitude(Double longitude) {
        longitude_ = longitude;
    }

    @Column(name = "LOCATION_TYPE", nullable = false)
    public Integer getLocationType() {
        return locationType_;
    }

    public void setLocationType(Integer locationType) {
        locationType_ = locationType;
    }

    @ManyToOne
    @JoinColumn(name = "PARENT_LOCATION_ID", referencedColumnName = "LOCATION_ID")
    public DBLocation getParentLocation() {
        return parentLocation_;
    }

    public void setParentLocation(DBLocation parentLocation) {
        parentLocation_ = parentLocation;
    }
}
