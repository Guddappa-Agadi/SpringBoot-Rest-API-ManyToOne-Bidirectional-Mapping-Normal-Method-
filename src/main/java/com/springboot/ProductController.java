package com.springboot;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductRepository cr;

	@PostMapping("/add")
	public ResponseEntity<Product> saveData(@RequestBody Product c) {
		return new ResponseEntity<>(cr.save(c), HttpStatus.CREATED);
	}

	@GetMapping("/get")
	public ResponseEntity<List<Product>> getAllData() {
		List<Product> lst = cr.findAll();
		return new ResponseEntity<>(lst, HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Product> getAllDataById(@PathVariable("id") int product_id) {
		Optional<Product> obj = cr.findById(product_id);
		if (obj.isPresent()) {
			return new ResponseEntity<>(obj.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@PutMapping("/get/{id}")
	public ResponseEntity<Product> updateAllDataById(@PathVariable("id") int product_id, @RequestBody Product c) {
		Optional<Product> obj = cr.findById(product_id);
		if (obj.isPresent()) {
			obj.get().setProduct_name(c.getProduct_name());
			obj.get().setProduct_price(c.getProduct_price());
			return new ResponseEntity<>(cr.save(obj.get()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@DeleteMapping("/get/{id}")
	public ResponseEntity<Product> deleteAllDataById(@PathVariable("id") int product_id) {
		Optional<Product> obj = cr.findById(product_id);
		if (obj.isPresent()) {
			cr.deleteById(product_id);
			return new ResponseEntity<>(obj.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@GetMapping("/get/page")
	public List<Product> getPages() {
		Pageable p = PageRequest.of(0, 10, Sort.by("product_name").ascending());
		Page<Product> pa = cr.findAll(p);
		return pa.getContent();
	}

}
