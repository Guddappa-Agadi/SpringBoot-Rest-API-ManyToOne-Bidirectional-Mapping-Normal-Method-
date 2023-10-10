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
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryRepository cr;

	@PostMapping("/add")
	public ResponseEntity<Category> saveData(@RequestBody Category c) {
		return new ResponseEntity<>(cr.save(c), HttpStatus.CREATED);
	}

	@GetMapping("/get")
	public ResponseEntity<List<Category>> getAllData() {
		List<Category> lst = cr.findAll();
		return new ResponseEntity<>(lst, HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Category> getAllDataById(@PathVariable("id") int category_id) {
		Optional<Category> obj = cr.findById(category_id);
		if (obj.isPresent()) {
			return new ResponseEntity<>(obj.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@PutMapping("/get/{id}")
	public ResponseEntity<Category> updateAllDataById(@PathVariable("id") int category_id, @RequestBody Category c) {
		Optional<Category> obj = cr.findById(category_id);
		if (obj.isPresent()) {
			obj.get().setCategory_name(c.getCategory_name());
			obj.get().setCategory_stocks(c.getCategory_stocks());
			obj.get().setProduct(c.getProduct());
			return new ResponseEntity<>(cr.save(obj.get()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@DeleteMapping("/get/{id}")
	public ResponseEntity<Category> deleteAllDataById(@PathVariable("id") int category_id) {
		Optional<Category> obj = cr.findById(category_id);
		if (obj.isPresent()) {
			cr.deleteById(category_id);
			return new ResponseEntity<>(obj.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@GetMapping("/get/page")
	public List<Category> getPages() {
		Pageable p = PageRequest.of(0, 10, Sort.by("category_name").ascending());
		Page<Category> pa = cr.findAll(p);
		return pa.getContent();
	}

}
