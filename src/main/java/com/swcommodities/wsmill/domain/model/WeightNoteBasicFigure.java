/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.model;

/**
 *
 * @author macOS
 */

public interface WeightNoteBasicFigure extends WeightBasicFigure {
    public Integer getId();
    public String getSealNo();
    public String getContainerNo();
    public Integer getNoOfBags();
    
    public default WeightNoteBasicFigure weightNoteBasicFigureAdd(WeightNoteBasicFigure other) {
        return new WeightNoteBasicFigure() {
            @Override
            public Integer getId() {
                return WeightNoteBasicFigure.this.getId();
            }

            @Override
            public String getSealNo() {
                return WeightNoteBasicFigure.this.getSealNo();
            }

            @Override
            public String getContainerNo() {
                return WeightNoteBasicFigure.this.getContainerNo();
            }

            @Override
            public Integer getNoOfBags() {
                return WeightNoteBasicFigure.this.getNoOfBags() + other.getNoOfBags();
            }

            @Override
            public Float getGrossWeight() {
                return WeightNoteBasicFigure.this.getGrossWeight() + other.getGrossWeight();
            }

            @Override
            public Float getTareWeight() {
                return WeightNoteBasicFigure.this.getTareWeight()+ other.getTareWeight();
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
    
    public default WeightNoteBasicFigure assignTareWeight(WeightNoteBasicFigure other) {
        return new WeightNoteBasicFigure() {
            @Override
            public Integer getId() {
                return WeightNoteBasicFigure.this.getId();
            }

            @Override
            public String getSealNo() {
                return WeightNoteBasicFigure.this.getSealNo();
            }

            @Override
            public String getContainerNo() {
                return WeightNoteBasicFigure.this.getContainerNo();
            }

            @Override
            public Integer getNoOfBags() {
                return WeightNoteBasicFigure.this.getNoOfBags();
            }

            @Override
            public Float getGrossWeight() {
                return WeightNoteBasicFigure.this.getGrossWeight();
            }

            @Override
            public Float getTareWeight() {
                return other.getTareWeight();
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
