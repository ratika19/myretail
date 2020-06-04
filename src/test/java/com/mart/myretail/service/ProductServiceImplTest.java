package com.mart.myretail.service;

import com.mart.myretail.entity.Product;
import com.mart.myretail.exception.BusinessServiceException;
import com.mart.myretail.exception.NotFoundException;
import com.mart.myretail.model.ProductDetail;
import com.mart.myretail.model.ProductPrice;
import com.mart.myretail.model.RetailProduct;
import com.mart.myretail.repository.RetailProductRepo;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import  org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @Mock
    RetailProductRepo retailProductRepoMock;

    @Mock
    RestService restServiceMock;

    @InjectMocks
    ProductServiceImpl service;




    public int productId;
    public String productDetailJsonResponse;
    public ProductDetail productDetail;
    public Product productObj;
    public String detailUrl;

    @Before
    public void setUp() {

        productId = 1234;
        productObj = new Product(1234,"35467",(float) 133.0, "USD");
        productDetail = new ProductDetail("Music System", "Sony");
    }

    @After
    public void tearDown() {
    }


    @Test
    public void testGetProductById(){
        doReturn(productObj).when(retailProductRepoMock).findById(productId);
        doReturn(productDetail).when(restServiceMock).getProductName(productId);

        RetailProduct retailProduct = null;
        try {
            retailProduct = service.getProductById(productId);
        }catch (Exception e){

        }
        //check if Retail product found with same id
        assertEquals(retailProduct.getId(), productId);
    }


    @Test
    public void testNotFoundExceptionToGetProductById(){

        doReturn(null).when(retailProductRepoMock).findById(productId);

        //Check if NotFoundException is thrown when Product is not available
        Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
            service.getProductById(productId);
        });
    }

    @Test
    public void testNotBusinessServiceException(){
        doReturn(productObj).when(retailProductRepoMock).findById(productId);
        doReturn(null).when(restServiceMock).getProductName(productId);

        //Check if BusinessServiceException is thrown when not able to fetch details
        Exception exception = Assertions.assertThrows(BusinessServiceException.class, () -> {
            service.getProductById(productId);
        });
    }


    @Test
    public void testUpdateProductDetails(){
        doReturn(productObj).when(retailProductRepoMock).findById(productId);
        doReturn(productDetail).when(restServiceMock).getProductName(productId);

        RetailProduct retailProduct = null;
        try {
            retailProduct = service.getProductById(productId);
        }catch (Exception e){

        }
        //Change the price
        float newPriceValue = (float)110.0;
        retailProduct.setProductPrice(new ProductPrice(newPriceValue, "USD"));
        productObj.setProductValue(newPriceValue);
        doReturn(true).when(retailProductRepoMock).save(productObj);

        RetailProduct updatedRetailProduct = null;
        try {
            updatedRetailProduct = service.updateProductDetails(retailProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //check if Retail product price is updated
        assertEquals(updatedRetailProduct.getProductPrice().getValue(), newPriceValue, 0.001);
    }



    @Test
    public void testBusinessServiceExceptionIfUpdateProductDetailsFails(){
        doReturn(productObj).when(retailProductRepoMock).findById(productId);
        doReturn(productDetail).when(restServiceMock).getProductName(productId);

        RetailProduct retailProduct = null;
        try {
            retailProduct = service.getProductById(productId);
        }catch (Exception e){

        }
        //Change the price
        float newPriceValue = (float)110.0;
        retailProduct.setProductPrice(new ProductPrice(newPriceValue, "USD"));
        productObj.setProductValue(newPriceValue);
        doReturn(false).when(retailProductRepoMock).save(productObj);

        RetailProduct changedRetailProduct = retailProduct;

        //Check if BusinessServiceException is thrown when not able to update details
        Exception exception = Assertions.assertThrows(BusinessServiceException.class, () -> {
            service.updateProductDetails(changedRetailProduct);
        });


    }

}