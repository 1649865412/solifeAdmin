package com.cartmatic.estore.system.service;

import java.util.List;

import com.cartmatic.estore.common.model.system.Wrap;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for Wrap, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface WrapManager extends GenericManager<Wrap> {
    public List<Wrap> getWrapsAllDesc();
}
