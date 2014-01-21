package com.oyou.spring2.repository;

import java.util.List;

import com.oyou.spring2.domain.Product;


public interface ProductDao {

    public List<Product> getProductList();

    public void saveProduct(Product prod);

}
