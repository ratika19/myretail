package com.mart.myretail.controller;

import com.mart.myretail.Application;
import com.mart.myretail.exception.BusinessServiceException;
import com.mart.myretail.exception.NotFoundException;
import com.mart.myretail.model.RetailProduct;
import com.mart.myretail.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public RetailProduct getProductById(@PathVariable int id, HttpServletResponse response){

        logger.info("GetProductById Request : {}", id);

        RetailProduct retailProduct = new RetailProduct();
        try {
                retailProduct = productService.getProductById(id);

        }catch( NotFoundException ex) {
            logger.error("Product Details Not Found {} ", id);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;

        }catch( BusinessServiceException bex){
            logger.error("Business Service exception while fetching product details {}", bex.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        } catch(Exception e){
            logger.error("Exception while fetching Product Details for Id {} error {} ",id,e.getMessage());
        }

        return retailProduct;
    }


    @PutMapping("/{id}")
    public RetailProduct updateProductPriceDetails(@PathVariable int id, @RequestBody RetailProduct product, HttpServletResponse response){

        logger.info("UpdateProductDetails Request : {} and {}", id, product);

        RetailProduct retailProduct = new RetailProduct();

        try {
            retailProduct = productService.updateProductDetails(product);
        }catch(BusinessServiceException ex){
            logger.error("Business Service exception while updating product details {}", ex.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        } catch(Exception e){
            logger.error("Exception while updating product details {} ", e.getMessage());
            return null;
        }

        return retailProduct;
    }
}
