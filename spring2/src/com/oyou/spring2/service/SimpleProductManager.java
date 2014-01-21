package com.oyou.spring2.service;

import java.util.List;

import com.oyou.spring2.domain.Product;
import com.oyou.spring2.repository.ProductDao;


public class SimpleProductManager implements ProductManager {

	private static final long serialVersionUID = 1L;

    //private List<Product> products;
    private ProductDao productDao;
	
    public List<Product> getProducts() {
        //return products;
    	return productDao.getProductList();
    }

    public void increasePrice(int percentage) {
    	List<Product> products = productDao.getProductList();
        if (products != null) {
            for (Product product : products) {
                double newPrice = product.getPrice().doubleValue() * 
                                    (100 + percentage)/100;
                product.setPrice(newPrice);
                productDao.saveProduct(product);
            }
        }
    }
    
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

}
