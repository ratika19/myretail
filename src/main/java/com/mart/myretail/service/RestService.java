package com.mart.myretail.service;

import com.mart.myretail.model.ProductDetail;
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

@Service
public class RestService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${product.detail.external.url}")
    private String productDetailUrl;
    @Value("${product.detail.external.exclusion}")
    private String detailExclusions;

    private static final Logger logger = LoggerFactory.getLogger(RestService.class);


    @Cacheable(value = "productDetails", key = "#id", unless="#result == null")
    public ProductDetail getProductName(int id){
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



}
