/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author macOS
 */

@Data @Builder
public class WeightNoteClaimAggegrate {
    
    protected List<WeightNoteBasicFigure> shipWeightDetail;
    protected List<WeightBasicFigure> arrivalWeightDetail;
    protected List<WeightBasicFigure> weightLossDetail;
    protected List<WeightNoteBasicFigure> internalShipWeightDetail;
    protected List<WeightBasicFigure> internalWeightLossDetail;
    
    // total
    private WeightNoteBasicFigure totalShipWeightDetail;
    private WeightBasicFigure totalArrivalWeightDetail;
    private WeightBasicFigure totalWeightLossDetail;
    private WeightNoteBasicFigure totalInternalShipWeightDetail;
    private WeightBasicFigure totalInternalWeightLossDetail;
    @Builder.Default private double diffBetweenRealAndClaim = 0;
    @Builder.Default private double theoryFranchiseWeight = 0;
    
}
