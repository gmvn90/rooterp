package com.swcommodities.wsmill.hibernate.dto;
// Generated Feb 18, 2014 3:38:53 PM by Hibernate Tools 3.2.1.GA

import static javax.persistence.GenerationType.IDENTITY;

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

/**
 * WarehouseCell generated by hbm2java
 */
@Entity
@Table(name = "warehouse_cell"
)
public class WarehouseCell implements java.io.Serializable {

    private Integer id;
    private CellType cellType;
    private WarehouseMap warehouseMap;
    private Integer ordinateX;
    private Integer ordinateY;
    private Set<WeightNoteReceipt> weightNoteReceipts = new HashSet<WeightNoteReceipt>(0);
    private Set<WeightNote> weightNotes = new HashSet<WeightNote>(0);
    private Set<Movement> movements = new HashSet<Movement>(0);

    public WarehouseCell() {
    }

    public WarehouseCell(CellType cellType, WarehouseMap warehouseMap, Integer ordinateX, Integer ordinateY, Set<WeightNoteReceipt> weightNoteReceipts, Set<WeightNote> weightNotes, Set<Movement> movements) {
        this.cellType = cellType;
        this.warehouseMap = warehouseMap;
        this.ordinateX = ordinateX;
        this.ordinateY = ordinateY;
        this.weightNoteReceipts = weightNoteReceipts;
        this.weightNotes = weightNotes;
        this.movements = movements;
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
    @JoinColumn(name = "type_id")
    public CellType getCellType() {
        return this.cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_map_id")
    public WarehouseMap getWarehouseMap() {
        return this.warehouseMap;
    }

    public void setWarehouseMap(WarehouseMap warehouseMap) {
        this.warehouseMap = warehouseMap;
    }

    @Column(name = "ordinate_x")
    public Integer getOrdinateX() {
        return this.ordinateX;
    }

    public void setOrdinateX(Integer ordinateX) {
        this.ordinateX = ordinateX;
    }

    @Column(name = "ordinate_y")
    public Integer getOrdinateY() {
        return this.ordinateY;
    }

    public void setOrdinateY(Integer ordinateY) {
        this.ordinateY = ordinateY;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "warehouseCell")
    public Set<WeightNoteReceipt> getWeightNoteReceipts() {
        return this.weightNoteReceipts;
    }

    public void setWeightNoteReceipts(Set<WeightNoteReceipt> weightNoteReceipts) {
        this.weightNoteReceipts = weightNoteReceipts;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "warehouseCell")
    public Set<WeightNote> getWeightNotes() {
        return this.weightNotes;
    }

    public void setWeightNotes(Set<WeightNote> weightNotes) {
        this.weightNotes = weightNotes;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "warehouseCell")
    public Set<Movement> getMovements() {
        return this.movements;
    }

    public void setMovements(Set<Movement> movements) {
        this.movements = movements;
    }

}