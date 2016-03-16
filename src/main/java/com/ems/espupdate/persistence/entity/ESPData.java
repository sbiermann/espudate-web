package com.ems.espupdate.persistence.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Stefan on 06.12.2014.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "KeyMapping.findAll", query = "SELECT c FROM ESPData c where c.macAddress = :mac ORDER BY c.id DESC"),
        @NamedQuery(name = "KeyMapping.findByMac", query = "SELECT c FROM ESPData c WHERE c.macAddress = :mac")
})
public class ESPData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String macAddress;

    private String apMacAddress;

    private String freeSpace;

    private String sketchSize;

    private String chipSize;

    private String sdkVersion;

    private String version;

    private String newVersion;

    private String md5NewVersion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    @PrePersist
    @PreUpdate
    void preUpdate() {
        lastUpdate = new Date();
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getApMacAddress() {
        return apMacAddress;
    }

    public void setApMacAddress(String apMacAddress) {
        this.apMacAddress = apMacAddress;
    }

    public String getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(String freeSpace) {
        this.freeSpace = freeSpace;
    }

    public String getSketchSize() {
        return sketchSize;
    }

    public void setSketchSize(String sketchSize) {
        this.sketchSize = sketchSize;
    }

    public String getChipSize() {
        return chipSize;
    }

    public void setChipSize(String chipSize) {
        this.chipSize = chipSize;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getId() {
        return id;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public String getMd5NewVersion() {
        return md5NewVersion;
    }

    public void setMd5NewVersion(String md5NewVersion) {
        this.md5NewVersion = md5NewVersion;
    }

    @Override
    public String toString() {
        return "ESPData{" +
                "id=" + id +
                ", macAddress='" + macAddress + '\'' +
                ", apMacAddress='" + apMacAddress + '\'' +
                ", freeSpace='" + freeSpace + '\'' +
                ", sketchSize='" + sketchSize + '\'' +
                ", chipSize='" + chipSize + '\'' +
                ", sdkVersion='" + sdkVersion + '\'' +
                ", version='" + version + '\'' +
                ", newVersion='" + newVersion + '\'' +
                ", md5NewVersion='" + md5NewVersion + '\'' +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
