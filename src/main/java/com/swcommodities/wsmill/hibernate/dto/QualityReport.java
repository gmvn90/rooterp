package com.swcommodities.wsmill.hibernate.dto;
// Generated Feb 18, 2014 3:38:53 PM by Hibernate Tools 3.2.1.GA

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * QualityReport generated by hbm2java
 */
@Entity
@Table(name = "quality_report"
)
public class QualityReport implements java.io.Serializable {

    private Integer id;
    private User user;
    private String type;
    private String refNumber;
    private Date date;
    private Byte status;
    
    private Float aboveSc20;
    private Float sc20;
    private Float sc19;
    private Float sc18;
    private Float sc17;
    private Float sc16;
    private Float sc15;
    private Float sc14;
    private Float sc13;
    private Float sc12;
    private Float belowSc12;
    private Float black;
    private Float broken;
    private Float blackBroken;
    private Float brown;
    private Float moisture;
    private Float oldCrop;
    private Float excelsa;
    private Float foreignMatter;
    private Float worm;
    private Float moldy;
    private Float otherBean;
    private Float cherry;
    private Float defect;
    private Integer rejectedCup;
    
    private String remark;
    private Date updatedDate;
    private String log;
    private Set<CupTest> cupTests = new HashSet<CupTest>(0);
    private Set<WeightNote> weightNotes = new HashSet<WeightNote>(0);
    private Set<WarehouseReceipt> warehouseReceipts = new HashSet<WarehouseReceipt>(0);

    public QualityReport() {
    }

    public QualityReport(Integer id) {
        this.id = id;
    }

    public QualityReport(String type, String refNumber) {
        this.type = type;
        this.refNumber = refNumber;
    }

    public QualityReport(User user, String type, String refNumber, Date date, Byte status, Float aboveSc20, Float sc20,
        Float sc19, Float sc18, Float sc17, Float sc16, Float sc15, Float sc14, Float sc13, Float sc12,
        Float belowSc12, Float black, Float broken, Float blackBroken, Float brown, Float moisture, Float oldCrop,
        Float excelsa, Float foreignMatter, Float worm, Float moldy, Float otherBean, Float cherry, Float defect,
        Integer rejectedCup, String remark, Date updatedDate, String log, Set<CupTest> cupTests,
        Set<WeightNote> weightNotes, Set<WarehouseReceipt> warehouseReceipts) {
        this.user = user;
        this.type = type;
        this.refNumber = refNumber;
        this.date = date;
        this.status = status;
        this.aboveSc20 = aboveSc20;
        this.sc20 = sc20;
        this.sc19 = sc19;
        this.sc18 = sc18;
        this.sc17 = sc17;
        this.sc16 = sc16;
        this.sc15 = sc15;
        this.sc14 = sc14;
        this.sc13 = sc13;
        this.sc12 = sc12;
        this.belowSc12 = belowSc12;
        this.black = black;
        this.broken = broken;
        this.blackBroken = blackBroken;
        this.brown = brown;
        this.moisture = moisture;
        this.oldCrop = oldCrop;
        this.excelsa = excelsa;
        this.foreignMatter = foreignMatter;
        this.worm = worm;
        this.moldy = moldy;
        this.otherBean = otherBean;
        this.cherry = cherry;
        this.defect = defect;
        this.rejectedCup = rejectedCup;
        this.remark = remark;
        this.updatedDate = updatedDate;
        this.log = log;
        this.cupTests = cupTests;
        this.weightNotes = weightNotes;
        this.warehouseReceipts = warehouseReceipts;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "type", nullable = false, length = 50)
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "ref_number", nullable = false)
    public String getRefNumber() {
        return this.refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", length = 19)
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "status")
    public Byte getStatus() {
        return this.status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Column(name = "above_sc20", precision = 12, scale = 0)
    public Float getAboveSc20() {
        return this.aboveSc20;
    }

    public void setAboveSc20(Float aboveSc20) {
        this.aboveSc20 = aboveSc20;
    }

    @Column(name = "sc20", precision = 12, scale = 0)
    public Float getSc20() {
        return this.sc20;
    }

    public void setSc20(Float sc20) {
        this.sc20 = sc20;
    }

    @Column(name = "sc19", precision = 12, scale = 0)
    public Float getSc19() {
        return this.sc19;
    }

    public void setSc19(Float sc19) {
        this.sc19 = sc19;
    }

    @Column(name = "sc18", precision = 12, scale = 0)
    public Float getSc18() {
        return this.sc18;
    }

    public void setSc18(Float sc18) {
        this.sc18 = sc18;
    }

    @Column(name = "sc17", precision = 12, scale = 0)
    public Float getSc17() {
        return this.sc17;
    }

    public void setSc17(Float sc17) {
        this.sc17 = sc17;
    }

    @Column(name = "sc16", precision = 12, scale = 0)
    public Float getSc16() {
        return this.sc16;
    }

    public void setSc16(Float sc16) {
        this.sc16 = sc16;
    }

    @Column(name = "sc15", precision = 12, scale = 0)
    public Float getSc15() {
        return this.sc15;
    }

    public void setSc15(Float sc15) {
        this.sc15 = sc15;
    }

    @Column(name = "sc14", precision = 12, scale = 0)
    public Float getSc14() {
        return this.sc14;
    }

    public void setSc14(Float sc14) {
        this.sc14 = sc14;
    }

    @Column(name = "sc13", precision = 12, scale = 0)
    public Float getSc13() {
        return this.sc13;
    }

    public void setSc13(Float sc13) {
        this.sc13 = sc13;
    }

    @Column(name = "sc12", precision = 12, scale = 0)
    public Float getSc12() {
        return this.sc12;
    }

    public void setSc12(Float sc12) {
        this.sc12 = sc12;
    }

    @Column(name = "below_sc12", precision = 12, scale = 0)
    public Float getBelowSc12() {
        return this.belowSc12;
    }

    public void setBelowSc12(Float belowSc12) {
        this.belowSc12 = belowSc12;
    }

    @Column(name = "black", precision = 12, scale = 0)
    public Float getBlack() {
        return this.black;
    }

    public void setBlack(Float black) {
        this.black = black;
    }

    @Column(name = "broken", precision = 12, scale = 0)
    public Float getBroken() {
        return this.broken;
    }

    public void setBroken(Float broken) {
        this.broken = broken;
    }

    @Column(name = "black_broken", precision = 12, scale = 0)
    public Float getBlackBroken() {
        return this.blackBroken;
    }

    public void setBlackBroken(Float blackBroken) {
        this.blackBroken = blackBroken;
    }

    @Column(name = "brown", precision = 12, scale = 0)
    public Float getBrown() {
        return this.brown;
    }

    public void setBrown(Float brown) {
        this.brown = brown;
    }

    @Column(name = "moisture", precision = 12, scale = 0)
    public Float getMoisture() {
        return this.moisture;
    }

    public void setMoisture(Float moisture) {
        this.moisture = moisture;
    }

    @Column(name = "old_crop", precision = 12, scale = 0)
    public Float getOldCrop() {
        return this.oldCrop;
    }

    public void setOldCrop(Float oldCrop) {
        this.oldCrop = oldCrop;
    }

    @Column(name = "excelsa", precision = 12, scale = 0)
    public Float getExcelsa() {
        return this.excelsa;
    }

    public void setExcelsa(Float excelsa) {
        this.excelsa = excelsa;
    }

    @Column(name = "foreign_matter", precision = 12, scale = 0)
    public Float getForeignMatter() {
        return this.foreignMatter;
    }

    public void setForeignMatter(Float foreignMatter) {
        this.foreignMatter = foreignMatter;
    }

    @Column(name = "worm", precision = 12, scale = 0)
    public Float getWorm() {
        return this.worm;
    }

    public void setWorm(Float worm) {
        this.worm = worm;
    }

    @Column(name = "moldy", precision = 12, scale = 0)
    public Float getMoldy() {
        return this.moldy;
    }

    public void setMoldy(Float moldy) {
        this.moldy = moldy;
    }

    @Column(name = "other_bean", precision = 12, scale = 0)
    public Float getOtherBean() {
        return this.otherBean;
    }

    public void setOtherBean(Float otherBean) {
        this.otherBean = otherBean;
    }

    @Column(name = "cherry", precision = 12, scale = 0)
    public Float getCherry() {
        return this.cherry;
    }

    public void setCherry(Float cherry) {
        this.cherry = cherry;
    }

    @Column(name = "defect", precision = 12, scale = 0)
    public Float getDefect() {
        return this.defect;
    }

    public void setDefect(Float defect) {
        this.defect = defect;
    }

    @Column(name = "rejected_cup")
    public Integer getRejectedCup() {
        return this.rejectedCup;
    }

    public void setRejectedCup(Integer rejectedCup) {
        this.rejectedCup = rejectedCup;
    }

    @Column(name = "remark", length = 65535)
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date", length = 19)
    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Column(name = "log", length = 65535)
    public String getLog() {
        return this.log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "qualityReport")
    public Set<CupTest> getCupTests() {
        return this.cupTests;
    }

    public void setCupTests(Set<CupTest> cupTests) {
        this.cupTests = cupTests;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "qualityReport")
    public Set<WeightNote> getWeightNotes() {
        return this.weightNotes;
    }

    public void setWeightNotes(Set<WeightNote> weightNotes) {
        this.weightNotes = weightNotes;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "qualityReport")
    public Set<WarehouseReceipt> getWarehouseReceipts() {
        return this.warehouseReceipts;
    }

    public void setWarehouseReceipts(Set<WarehouseReceipt> warehouseReceipts) {
        this.warehouseReceipts = warehouseReceipts;
    }

}