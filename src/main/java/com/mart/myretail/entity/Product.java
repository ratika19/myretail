package com.mart.myretail.entity;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;

import java.util.Objects;


@Entity(defaultKeyspace = "retail_product")
public class Product {

    @CqlName("product_id")
    private int productId;
    @CqlName("product_code")
    private String productCode;
    @CqlName("product_value")
    private float productValue;
    @CqlName("currency_code")
    private String currencyCode;

    public Product() {
    }

    public Product(int productId, String productCode, float productValue, String currencyCode) {
        this.productId = productId;
        this.productCode = productCode;
        this.productValue = productValue;
        this.currencyCode = currencyCode;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode; }

    public float getProductValue() {
        return productValue;
    }

    public void setProductValue(float productValue) {
        this.productValue = productValue;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return productId == product.productId &&
                Float.compare(product.productValue, productValue) == 0 &&
                Objects.equals(productCode, product.productCode) &&
                Objects.equals(currencyCode, product.currencyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productCode, productValue, currencyCode);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productCode='" + productCode + '\'' +
                ", productValue=" + productValue +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}
