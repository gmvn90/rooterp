package com.swcommodities.wsmill.domain.model;

public interface WeightBasicFigure {
	public Float getGrossWeight();
    public Float getTareWeight();
    public Integer getWeightNoteId();
    
    default public Float getNetWeight() {
        return getGrossWeight() - getTareWeight();
    }
    
    default public WeightBasicFigure basicFigureDiff(WeightBasicFigure other) {
        return new WeightBasicFigure() {
            @Override
            public Float getGrossWeight() {
                return WeightBasicFigure.this.getGrossWeight() - other.getGrossWeight();
            }

            @Override
            public Float getTareWeight() {
                return WeightBasicFigure.this.getTareWeight()- other.getTareWeight();
            }
            
            @Override
            public Float getNetWeight() {
                return getGrossWeight() - getTareWeight();
            }
            
            @Override
            public Integer getWeightNoteId() {
                return WeightBasicFigure.this.getWeightNoteId();
            }
            
        };
    }
    
    default public WeightBasicFigure basicFigureAdd(WeightBasicFigure other) {
        return new WeightBasicFigure() {
            @Override
            public Float getGrossWeight() {
                return WeightBasicFigure.this.getGrossWeight() + other.getGrossWeight();
            }

            @Override
            public Float getTareWeight() {
                return WeightBasicFigure.this.getTareWeight() + other.getTareWeight();
            }
            
            @Override
            public Float getNetWeight() {
                return getGrossWeight() - getTareWeight();
            }
            
            @Override
            public Integer getWeightNoteId() {
                return WeightBasicFigure.this.getWeightNoteId();
            }
        };
    }
    
}
