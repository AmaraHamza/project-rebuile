package com.product.project_rebuild.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.product.project_rebuild.dto.ApiResponse;
import com.product.project_rebuild.model.Product;
import com.product.project_rebuild.service.ProductService;


@RestController
public class ProductController {
	
	@Autowired
	private ProductService prodService;
	
	@GetMapping("/products")
	public List<Product> getAllProducts(){
		List<Product> products = prodService.getAllProducts();
		return products;
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable Integer id){
		Product p = prodService.getProduct(id);
		if (p != null) {
			return new ResponseEntity<Product>(p, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<Product>(p, HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/add_product")
	public ResponseEntity<ApiResponse<Product>> addProduct(@RequestBody Product p){
		Product addedP = prodService.addProduct(p);
		if (addedP != null) {
			return ResponseEntity.ok(new ApiResponse<>( "Product "+ addedP.getName() +" Added", addedP)) ;
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Product Cannot be Created", p));
		}
	}
	
	@PostMapping("/add_products")
	public ResponseEntity<List<Product>> addProducts(@RequestBody List<Product> products){
		List<Product> ps = prodService.addProducts(products);
		return new ResponseEntity<List<Product>>(ps, HttpStatus.CREATED);
	}
	
	@PutMapping("/update_product/{id}")
	public ResponseEntity<ApiResponse<Product>> updateProduct(@RequestBody Product p, @PathVariable Integer id){
		Product upProduct = prodService.updateProduct(p,id);
		if (upProduct != null) {
			return ResponseEntity.ok(new ApiResponse<>("Product Updated Successfully", upProduct));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("Product to Update Not Found", p));
		}
	}
	
	@DeleteMapping("/delete_product/{id}")
	public ResponseEntity<ApiResponse<Product>> deleteProduct(@PathVariable Integer id){
		prodService.deleteProduct(id);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	
	
	
}
