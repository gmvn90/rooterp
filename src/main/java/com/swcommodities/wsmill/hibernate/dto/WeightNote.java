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
import javax.persistence.Transient;

import com.google.common.base.Objects;
import com.swcommodities.wsmill.domain.model.WeightNoteBasicFigure;

/**
 * WeightNote generated by hbm2java
 */
@Entity
@Table(name = "weight_note")
public class WeightNote implements java.io.Serializable, WeightNoteBasicFigure {

    private Integer id;
    private User user;
    private WarehouseCell warehouseCell;
    private GradeMaster gradeMaster;
    private PackingMaster packingMaster;
    private QualityReport qualityReport;
    private String refNumber;
    private String type;
    private Date createdDate;
    private Integer instId;
    private String truckNo;
    private String driver;
    private Float grossWeight;
    private Float tareWeight;
    private Float netWeight;
    private Integer noOfBags;
    private Integer wrcId;
    private Byte status;
    private String icoNo;
    private String containerNo;
    private String log;
    private String sealNo;
    private Set<WeightNoteReceipt> weightNoteReceipts = new HashSet<>(0);
    private ProcessingInstruction processingInstruction;
    private ShippingInstruction shippingInstruction;
    private DeliveryInstruction deliveryInstruction;

    public WeightNote() {
    }
    
    public WeightNote(int id) {
    		this.id = id;
    }
    
    public static WeightNote fromIdAndRefNumber(int id, String refNumber) {
    		WeightNote weightNote = new WeightNote(id);
    		weightNote.setRefNumber(refNumber);
    		return weightNote;
    }

    public WeightNote(String refNumber, float grossWeight, float tareWeight) {
        this.refNumber = refNumber;
        this.grossWeight = grossWeight;
        this.tareWeight = tareWeight;
        this.status = 0;
    }
    
    public WeightNote(float grossWeight, float tareWeight) {
        this.grossWeight = grossWeight;
        this.tareWeight = tareWeight;
        this.status = 0;
    }

    public WeightNote(User user, WarehouseCell warehouseCell, GradeMaster gradeMaster, PackingMaster packingMaster, QualityReport qualityReport, String refNumber, String type, Date createdDate, Integer instId, String truckNo, String driver, Float grossWeight, Float tareWeight, Float netWeight, Integer noOfBags, Integer wrcId, Byte status, String icoNo, String containerNo, String log, String sealNo, Set<WeightNoteReceipt> weightNoteReceipts) {
        this.user = user;
        this.warehouseCell = warehouseCell;
        this.gradeMaster = gradeMaster;
        this.packingMaster = packingMaster;
        this.qualityReport = qualityReport;
        this.refNumber = refNumber;
        this.type = type;
        this.createdDate = createdDate;
        this.instId = instId;
        this.truckNo = truckNo;
        this.driver = driver;
        this.grossWeight = grossWeight;
        this.tareWeight = tareWeight;
        this.netWeight = netWeight;
        this.noOfBags = noOfBags;
        this.wrcId = wrcId;
        this.status = status;
        this.icoNo = icoNo;
        this.containerNo = containerNo;
        this.log = log;
        this.sealNo = sealNo;
        this.weightNoteReceipts = weightNoteReceipts;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id", unique = true, nullable = false)
    @Override
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    public WarehouseCell getWarehouseCell() {
        return this.warehouseCell;
    }

    public void setWarehouseCell(WarehouseCell warehouseCell) {
        this.warehouseCell = warehouseCell;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id")
    public GradeMaster getGradeMaster() {
        return this.gradeMaster;
    }

    public void setGradeMaster(GradeMaster gradeMaster) {
        this.gradeMaster = gradeMaster;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packing_id")
    public PackingMaster getPackingMaster() {
        return this.packingMaster;
    }

    public void setPackingMaster(PackingMaster packingMaster) {
        this.packingMaster = packingMaster;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qr_id")
    public QualityReport getQualityReport() {
        return this.qualityReport;
    }

    public void setQualityReport(QualityReport qualityReport) {
        this.qualityReport = qualityReport;
    }

    @Column(name = "ref_number")
    public String getRefNumber() {
        return this.refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    @Column(name = "type", length = 50)
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", length = 19)
    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "inst_id")
    public Integer getInstId() {
        return this.instId;
    }

    public void setInstId(Integer instId) {
        this.instId = instId;
    }

    @Column(name = "truck_no")
    public String getTruckNo() {
        return this.truckNo;
    }

    public void setTruckNo(String truckNo) {
        this.truckNo = truckNo;
    }

    @Column(name = "driver")
    public String getDriver() {
        return this.driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    @Column(name = "gross_weight", precision = 12, scale = 0)
    @Override
    public Float getGrossWeight() {
        return this.grossWeight;
    }

    public void setGrossWeight(Float grossWeight) {
        this.grossWeight = grossWeight;
    }

    @Column(name = "tare_weight", precision = 12, scale = 0)
    @Override
    public Float getTareWeight() {
        return this.tareWeight;
    }

    public void setTareWeight(Float tareWeight) {
        this.tareWeight = tareWeight;
    }

    @Column(name = "net_weight", precision = 12, scale = 0)
    @Override
    public Float getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(Float netWeight) {
        this.netWeight = netWeight;
    }

    @Column(name = "no_of_bags")
    @Override
    public Integer getNoOfBags() {
        return this.noOfBags;
    }

    public void setNoOfBags(Integer noOfBags) {
        this.noOfBags = noOfBags;
    }

    @Column(name = "wrc_id")
    public Integer getWrcId() {
        return this.wrcId;
    }

    public void setWrcId(Integer wrcId) {
        this.wrcId = wrcId;
    }

    @Column(name = "status")
    public Byte getStatus() {
        return this.status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Column(name = "ico_no")
    public String getIcoNo() {
        return this.icoNo;
    }

    public void setIcoNo(String icoNo) {
        this.icoNo = icoNo;
    }

    @Column(name = "container_no")
    public String getContainerNo() {
        return this.containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    @Column(name = "log", length = 65535)
    public String getLog() {
        return this.log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @Column(name = "seal_no")
    public String getSealNo() {
        return this.sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "weightNote")
    public Set<WeightNoteReceipt> getWeightNoteReceipts() {
        return this.weightNoteReceipts;
    }

    public void setWeightNoteReceipts(Set<WeightNoteReceipt> weightNoteReceipts) {
        this.weightNoteReceipts = weightNoteReceipts;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(refNumber, type, truckNo, driver, noOfBags);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof WeightNote) {
            final WeightNote other = (WeightNote) obj;
            return Objects.equal(refNumber, other.getRefNumber())
                && Objects.equal(type, other.getType())
                && Objects.equal(truckNo, other.getTruckNo())
                && Objects.equal(driver, other.getDriver())
                && noOfBags == other.getNoOfBags();
        } else {
            return false;
        }
    }

    @ManyToOne
    @JoinColumn(name = "processing_instruction_id")
    public ProcessingInstruction getProcessingInstruction() {
        return processingInstruction;
    }

    public WeightNote setProcessingInstruction(ProcessingInstruction processingInstruction) {
        this.processingInstruction = processingInstruction;
        return this;
    }

    @ManyToOne
    @JoinColumn(name = "shipping_instruction_id")
    public ShippingInstruction getShippingInstruction() {
        return shippingInstruction;
    }

    public WeightNote setShippingInstruction(ShippingInstruction shippingInstruction) {
        this.shippingInstruction = shippingInstruction;
        return this;
    }

    @ManyToOne
    @JoinColumn(name = "delivery_instruction_id")
    public DeliveryInstruction getDeliveryInstruction() {
        return deliveryInstruction;
    }

    public WeightNote setDeliveryInstruction(DeliveryInstruction deliveryInstruction) {
        this.deliveryInstruction = deliveryInstruction;
        return this;
    }
    
    public void complete() {
    	this.status = Byte.valueOf("1");
    }
    
    @Transient
    public boolean isCompleteted() {
    	return Byte.valueOf("1").equals(this.status);
    }

    @Transient
    public boolean isDeleted() {
        return Byte.valueOf("2").equals(this.status);
    }

    @Transient
    public double getTotalGrossWeight() {
        return getWeightNoteReceipts().stream().filter((x) -> !x.getIsDeleted()).mapToDouble(x -> x.getGrossWeight()).sum();
    }

    @Transient
    public double getTotalTareWeight() {
        return getWeightNoteReceipts().stream().filter((x) -> !x.getIsDeleted()).mapToDouble(x -> x.getTareWeight()).sum();
    }

    @Transient
    public int getTotalNoOfBags() {
        return getWeightNoteReceipts().stream().filter((x) -> !x.getIsDeleted()).mapToInt(x -> x.getNoOfBags()).sum();
    }

    @Transient
    public boolean getIsDeleted() {
        return Byte.valueOf("2").equals(status);
    }
    
    @Transient
    public boolean getIsAvailable() {
        return ! getIsDeleted();
    }

    @Override
    @Transient
    public Integer getWeightNoteId() {
        return getId();
    }
}