package com.product.project_rebuild.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.project_rebuild.model.Product;
import com.product.project_rebuild.repository.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepo productRepo;
	
	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}

	public Product addProduct(Product p) {
		return productRepo.save(p);
	}

	public List<Product> addProducts(List<Product> products) {
		return productRepo.saveAll(products);
	}

	public Product getProduct(Integer id) {
		
		return productRepo.findById(id).orElse(null);
	}

	public Product updateProduct(Product p, Integer id) {
		Product proToUp = getProduct(id);
		if (proToUp == null) {
			return null;
		} else {
			return productRepo.save(p);
		}
	}

	public void deleteProduct(Integer id) {
		productRepo.deleteById(id);;
	}

}
