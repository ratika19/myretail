package com.mart.myretail.repository;

/** Repository template with template methods **/

public interface RetailBaseRepo<T>{

    <T> T findById(int id);

    boolean save(T saveObj);
}
