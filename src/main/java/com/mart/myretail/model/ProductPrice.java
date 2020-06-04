package com.mart.myretail.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class ProductPrice implements Serializable {

    private static final long serialVersionUID = 6470090944414208596L;


    @JsonProperty("value")
    private float value;
    @JsonProperty("currency_code")
    private String currencyCode;

    public ProductPrice() {
    }

    public ProductPrice(float productValue, String currencyCode) {
        super();
        this.value = productValue;
        this.currencyCode = currencyCode;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductPrice)) return false;
        ProductPrice that = (ProductPrice) o;
        return Float.compare(that.value, value) == 0 &&
                Objects.equals(currencyCode, that.currencyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currencyCode);
    }

    @Override
    public String toString() {
        return "ProductPrice{" +
                "value=" + value +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}
