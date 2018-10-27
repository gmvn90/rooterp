/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.model;

import java.util.List;

/**
 *
 * @author macOS
 */
public class WeightNoteComparingAggegrate {
    
    protected List<WeightNoteBasicFigure> weightNotes; // real wn; internal use
    protected List<WeightNoteBasicFigure> contractWeightNotes; // contract wn, use for customer
    protected WeightNoteBasicFigure totalWeightNotes;
    protected WeightNoteBasicFigure totalContractWeightNotes;
    protected WeightNoteBasicFigure diff;

    public List<WeightNoteBasicFigure> getWeightNotes() {
        return weightNotes;
    }

    public void setWeightNotes(List<WeightNoteBasicFigure> weightNotes) {
        this.weightNotes = weightNotes;
    }

    public List<WeightNoteBasicFigure> getContractWeightNotes() {
        return contractWeightNotes;
    }

    public void setContractWeightNotes(List<WeightNoteBasicFigure> contractWeightNotes) {
        this.contractWeightNotes = contractWeightNotes;
    }

    public WeightNoteBasicFigure getTotalWeightNotes() {
        return totalWeightNotes;
    }

    public void setTotalWeightNotes(WeightNoteBasicFigure totalWeightNotes) {
        this.totalWeightNotes = totalWeightNotes;
    }

    public WeightNoteBasicFigure getTotalContractWeightNotes() {
        return totalContractWeightNotes;
    }

    public void setTotalContractWeightNotes(WeightNoteBasicFigure totalContractWeightNotes) {
        this.totalContractWeightNotes = totalContractWeightNotes;
    }

    public WeightNoteBasicFigure getDiff() {
        return diff;
    }

    public void setDiff(WeightNoteBasicFigure diff) {
        this.diff = diff;
    }
    
    public static WeightNoteBasicFigure diff(WeightNoteBasicFigure wn, WeightNoteBasicFigure cwn) {
        return new WeightNoteBasicFigure() {
            @Override
            public Integer getId() {
                return wn.getId();
            }

            @Override
            public String getSealNo() {
                return wn.getSealNo();
            }

            @Override
            public String getContainerNo() {
                return wn.getContainerNo();
            }

            @Override
            public Float getGrossWeight() {
                return cwn.getGrossWeight() - wn.getGrossWeight();
            }

            @Override
            public Float getTareWeight() {
                return cwn.getTareWeight() - wn.getTareWeight();
            }

            @Override
            public Integer getNoOfBags() {
                return cwn.getNoOfBags() - wn.getNoOfBags();
            }
            
            @Override
            public Float getNetWeight() {
                return getGrossWeight() - getTareWeight();
            }

            @Override
            public Integer getWeightNoteId() {
                return getId();
            }
            
        };
    }
    
    public static WeightNoteBasicFigure total(List<WeightNoteBasicFigure> wns) {
        return new WeightNoteBasicFigure() {
            @Override
            public Integer getId() {
                return 0;
            }

            @Override
            public String getSealNo() {
                return "";
            }

            @Override
            public String getContainerNo() {
                return "";
            }

            @Override
            public Float getGrossWeight() {
                return (float) wns.stream().mapToDouble(x -> x.getGrossWeight()).sum();
            }

            @Override
            public Float getTareWeight() {
                return (float) wns.stream().mapToDouble(x -> x.getTareWeight()).sum();
            }

            @Override
            public Integer getNoOfBags() {
                return wns.stream().mapToInt(x -> x.getNoOfBags()).sum();
            }
            
            @Override
            public Float getNetWeight() {
                return getGrossWeight() - getTareWeight();
            }
            
            @Override
            public Integer getWeightNoteId() {
                return getId();
            }
        };
    }
    
    
}
