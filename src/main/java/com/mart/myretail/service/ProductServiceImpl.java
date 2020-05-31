package com.mart.myretail.service;

import com.mart.myretail.entity.Product;
import com.mart.myretail.exception.BusinessServiceException;
import com.mart.myretail.exception.NotFoundException;
import com.mart.myretail.model.ProductDetail;
import com.mart.myretail.model.ProductPrice;
import com.mart.myretail.model.RetailProduct;
import com.mart.myretail.repository.RetailProductRepo;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService{

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    RetailProductRepo retailProductRepo;

    @Autowired
    RestTemplate restTemplate;

    @Value("${product.detail.external.url}")
    private String productDetailUrl;
    @Value("${product.detail.external.exclusion}")
    private String detailExclusions;


    /** fetches product details by product id **/
    @Override
    public RetailProduct getProductById(int id) throws NotFoundException,BusinessServiceException {

        RetailProduct retailProduct = new RetailProduct();

        try {
            Product product = retailProductRepo.findById(id);

            if(!Objects.isNull(product)){

                ProductPrice productPrice = new ProductPrice(product.getProductValue(), product.getCurrencyCode());

                ProductDetail productDetail = getProductName(id);

                if(Objects.isNull(productDetail))
                    throw new BusinessServiceException("Unable to fetch product name");

                mapToRetailProductVO(retailProduct,productPrice,productDetail,product.getProductCode(), product.getProductId());

            }else {
                logger.info("Product not found {}", id);
                throw new NotFoundException("Product not found " + id);
            }
        } catch (Exception e) {
            if(e instanceof NotFoundException || e instanceof BusinessServiceException)
                throw e;
            logger.error("Exception occurred while fetching product details {} ", e.getMessage());
            e.printStackTrace();
        }

        return retailProduct;
    }

    /** Update Product Details **/
    @Override
    public RetailProduct updateProductDetails(RetailProduct productDetails) throws BusinessServiceException {

        Product product = new Product(productDetails.getId(), productDetails.getProductCode(),
                productDetails.getProductPrice().getValue(),
                productDetails.getProductPrice().getCurrencyCode());
        try {
            boolean result = retailProductRepo.save(product);

            if(!result)
                throw new BusinessServiceException("Error occurred in updating product");
        }catch(Exception e) {
            if( e instanceof BusinessServiceException)
                throw e;
            logger.error("Exception occurred while updating product {} ", e.getMessage());
        }

        return productDetails;
    }


    @Cacheable(value = "productDetails", key = "#id", unless="#result == null")
    private ProductDetail getProductName(int id){
        ProductDetail productDetail = new ProductDetail();

        try{
            String response = restTemplate.getForObject(productDetailUrl+"/"+id, String.class);
            JSONObject responseObj = new JSONObject(response);
            JSONObject productNode = responseObj.getJSONObject("product");
            JSONObject itemNode = productNode.getJSONObject("item");
            JSONObject descNode = itemNode.getJSONObject("product_description");
            productDetail.setName(descNode.getString("title"));
            JSONObject brandNode = itemNode.getJSONObject("product_brand");
            productDetail.setBrand(brandNode.getString("brand"));
        } catch (RestClientException ex){
            logger.error("Rest client exception occurred {} ",ex.getMessage());
            return null;
        } catch (JSONException jex) {
            logger.error("Product Name/Brand not available {} ", jex.getMessage());
            return null;
        } catch(Exception e) {
            logger.error("Exception occurred while fetching product name {} ", e.getMessage());
            return null;
        }
        return productDetail;
    }

    private RetailProduct mapToRetailProductVO(RetailProduct retailProduct, ProductPrice price, ProductDetail details, String productCode, int id) {
        if (!Objects.isNull(retailProduct)) {
            retailProduct.setProductPrice(price);
            retailProduct.setProductCode(productCode);
            retailProduct.setProductDetail(details);
            retailProduct.setId(id);
        }

        return retailProduct;
    }


}
