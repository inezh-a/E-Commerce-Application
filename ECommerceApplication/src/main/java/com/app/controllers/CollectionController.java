package com.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.config.AppConstants;
import com.app.entites.Collection;
import com.app.payloads.CollectionDTO;
import com.app.payloads.CollectionResponse;
import com.app.services.CollectionService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "E-Commerce Application")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @PostMapping("/admin/collection")
	public ResponseEntity<CollectionDTO> createCollection(@Valid @RequestBody Collection collection) {
		CollectionDTO savedCollectionDTO = collectionService.createCollection(collection);

		return new ResponseEntity<CollectionDTO>(savedCollectionDTO, HttpStatus.CREATED);
	}
    
    @GetMapping("/public/collections")
	public ResponseEntity<CollectionResponse> getCollections(
			@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
		
		CollectionResponse collectionResponse = collectionService.getCollections(pageNumber, pageSize, sortBy, sortOrder);

		return new ResponseEntity<CollectionResponse>(collectionResponse, HttpStatus.FOUND);
	}

    @PutMapping("/admin/collections/{collectionId}")
	public ResponseEntity<CollectionDTO> updateCollection(@RequestBody Collection collection,
			@PathVariable Long collectionId) {
		CollectionDTO collectionDTO = collectionService.updateCollection(collection, collectionId);

		return new ResponseEntity<CollectionDTO>(collectionDTO, HttpStatus.OK);
	}

    @DeleteMapping("/admin/collections/{collectionId}")
	public ResponseEntity<String> deleteCollection(@PathVariable Long collectionId) {
		String status = collectionService.deleteCollection(collectionId);

		return new ResponseEntity<String>(status, HttpStatus.OK);
	}

}
