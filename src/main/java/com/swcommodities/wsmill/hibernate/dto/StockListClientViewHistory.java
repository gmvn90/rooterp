package com.swcommodities.wsmill.hibernate.dto;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by macOS on 3/3/17.
 */
@Entity
@Table(name = "stock_list_client_view_history")
public class StockListClientViewHistory {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private Integer clientId;
    private Integer gradeId;
    private String gradeName;
    private Double unpledgedTons;
    private Double pledgedTons;
    private Date historyDate;

    private Double aboveSc20;
    private Double sc20;
    private Double sc19;
    private Double sc18;
    private Double sc17;
    private Double sc16;
    private Double sc15;
    private Double sc14;
    private Double sc13;
    private Double sc12;
    private Double belowSc12;
    private Double black;
    private Double broken;
    private Double blackBroken;
    private Double brown;
    private Double moisture;
    private Double oldCrop;
    private Double excelsa;
    private Double foreignMatter;
    private Double worm;
    private Double moldy;
    private Double otherBean;
    private Double cherry;
    private Double defect;

    public StockListClientViewHistory() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Double getUnpledgedTons() {
        return unpledgedTons;
    }

    public void setUnpledgedTons(Double unpledgedTons) {
        this.unpledgedTons = unpledgedTons;
    }

    public Double getPledgedTons() {
        return pledgedTons;
    }

    public void setPledgedTons(Double pledgedTons) {
        this.pledgedTons = pledgedTons;
    }

    public Date getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(Date historyDate) {
        this.historyDate = historyDate;
    }

    public Double getAboveSc20() {
        return aboveSc20;
    }

    public void setAboveSc20(Double aboveSc20) {
        this.aboveSc20 = aboveSc20;
    }

    public Double getSc20() {
        return sc20;
    }

    public void setSc20(Double sc20) {
        this.sc20 = sc20;
    }

    public Double getSc19() {
        return sc19;
    }

    public void setSc19(Double sc19) {
        this.sc19 = sc19;
    }

    public Double getSc18() {
        return sc18;
    }

    public void setSc18(Double sc18) {
        this.sc18 = sc18;
    }

    public Double getSc17() {
        return sc17;
    }

    public void setSc17(Double sc17) {
        this.sc17 = sc17;
    }

    public Double getSc16() {
        return sc16;
    }

    public void setSc16(Double sc16) {
        this.sc16 = sc16;
    }

    public Double getSc15() {
        return sc15;
    }

    public void setSc15(Double sc15) {
        this.sc15 = sc15;
    }

    public Double getSc14() {
        return sc14;
    }

    public void setSc14(Double sc14) {
        this.sc14 = sc14;
    }

    public Double getSc13() {
        return sc13;
    }

    public void setSc13(Double sc13) {
        this.sc13 = sc13;
    }

    public Double getSc12() {
        return sc12;
    }

    public void setSc12(Double sc12) {
        this.sc12 = sc12;
    }

    public Double getBelowSc12() {
        return belowSc12;
    }

    public void setBelowSc12(Double belowSc12) {
        this.belowSc12 = belowSc12;
    }

    public Double getBlack() {
        return black;
    }

    public void setBlack(Double black) {
        this.black = black;
    }

    public Double getBroken() {
        return broken;
    }

    public void setBroken(Double broken) {
        this.broken = broken;
    }

    public Double getBlackBroken() {
        return blackBroken;
    }

    public void setBlackBroken(Double blackBroken) {
        this.blackBroken = blackBroken;
    }

    public Double getBrown() {
        return brown;
    }

    public void setBrown(Double brown) {
        this.brown = brown;
    }

    public Double getMoisture() {
        return moisture;
    }

    public void setMoisture(Double moisture) {
        this.moisture = moisture;
    }

    public Double getOldCrop() {
        return oldCrop;
    }

    public void setOldCrop(Double oldCrop) {
        this.oldCrop = oldCrop;
    }

    public Double getExcelsa() {
        return excelsa;
    }

    public void setExcelsa(Double excelsa) {
        this.excelsa = excelsa;
    }

    public Double getForeignMatter() {
        return foreignMatter;
    }

    public void setForeignMatter(Double foreignMatter) {
        this.foreignMatter = foreignMatter;
    }

    public Double getWorm() {
        return worm;
    }

    public void setWorm(Double worm) {
        this.worm = worm;
    }

    public Double getMoldy() {
        return moldy;
    }

    public void setMoldy(Double moldy) {
        this.moldy = moldy;
    }

    public Double getOtherBean() {
        return otherBean;
    }

    public void setOtherBean(Double otherBean) {
        this.otherBean = otherBean;
    }

    public Double getCherry() {
        return cherry;
    }

    public void setCherry(Double cherry) {
        this.cherry = cherry;
    }

    public Double getDefect() {
        return defect;
    }

    public void setDefect(Double defect) {
        this.defect = defect;
    }
}
