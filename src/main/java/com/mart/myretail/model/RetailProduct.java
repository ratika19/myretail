package com.mart.myretail.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class RetailProduct implements Serializable {

    private static final long serialVersionUID = 6470090944414208496L;

    private int id;

    private String productCode;

    @JsonProperty("product_detail")
    private ProductDetail productDetail;

    @JsonProperty("current_price")
    private ProductPrice productPrice;

    public RetailProduct() {
    }

    public RetailProduct(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public ProductPrice getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(ProductPrice productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RetailProduct)) return false;
        RetailProduct that = (RetailProduct) o;
        return id == that.id &&
                Objects.equals(productCode, that.productCode) &&
                Objects.equals(productDetail, that.productDetail) &&
                Objects.equals(productPrice, that.productPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productCode, productDetail, productPrice);
    }

    @Override
    public String toString() {
        return "RetailProduct{" +
                "id=" + id +
                ", productCode='" + productCode + '\'' +
                ", productDetail=" + productDetail +
                ", productPrice=" + productPrice +
                '}';
    }
}
