package com.mart.myretail.service;


import com.mart.myretail.entity.Product;
import com.mart.myretail.model.ProductDetail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class RestServiceTest {


    @Mock
    RestTemplate restTemplateMock;

    @InjectMocks
    RestService restService;

    public int productId;
    public String productDetailJsonResponse;
    public ProductDetail productDetailInput;
    public String detailUrl;

    @Before
    public void setUp() {

        productId = 1234;
        productDetailJsonResponse = "{'product':{'item':{'product_brand':{'brand':'Sony'},'product_description':{'title':'Music System'}}}}";
        productDetailInput = new ProductDetail("Music System", "Sony");
        detailUrl = "https://redsky.target.com/v2/pdp/tcin";
        ReflectionTestUtils.setField(restService, "productDetailUrl", detailUrl);
    }

    @After
    public void tearDown() {
    }


    @Test
    public void testGetProductName(){
        doReturn(productDetailJsonResponse).when(restTemplateMock).getForObject(detailUrl+"/"+productId, String.class);
        ProductDetail productDetail = restService.getProductName(productId);
        assertEquals(productDetail.getName(), productDetailInput.getName());
    }



}
