package com.cartmatic.estore.system.service;

import java.util.List;

import com.cartmatic.estore.common.model.system.Carrier;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for Carrier, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface CarrierManager extends GenericManager<Carrier> {
    public List<Carrier> findActiveCarriers();
}
