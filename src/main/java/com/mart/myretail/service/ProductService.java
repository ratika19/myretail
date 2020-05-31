package com.mart.myretail.service;

import com.mart.myretail.exception.BusinessServiceException;
import com.mart.myretail.exception.NotFoundException;
import com.mart.myretail.model.RetailProduct;

public interface ProductService {

    RetailProduct getProductById(int id) throws NotFoundException, BusinessServiceException;

    RetailProduct updateProductDetails(RetailProduct changeRetailProduct) throws BusinessServiceException;
}
