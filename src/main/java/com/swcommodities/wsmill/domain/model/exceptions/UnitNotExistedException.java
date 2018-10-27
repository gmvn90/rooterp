/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.model.exceptions;

/**
 *
 * @author trung
 */
public class UnitNotExistedException extends DomainException {
    public UnitNotExistedException(String message) {
        super(message);
    }
}
