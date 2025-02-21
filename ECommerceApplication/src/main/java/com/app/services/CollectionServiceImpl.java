package com.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.app.entites.Collection;
import com.app.entites.Product;
import com.app.exceptions.APIException;
import com.app.exceptions.ResourceNotFoundException;
import com.app.payloads.CollectionDTO;
import com.app.payloads.CollectionResponse;
import com.app.repositories.CollectionRepo;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
	private CollectionRepo collectionRepo;
	
	@Autowired
	private ProductService productService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CollectionDTO createCollection(Collection collection) {
		Collection savedCollection = collectionRepo.findByCollectionName(collection.getCollectionName());

		if (savedCollection != null) {
			throw new APIException("Collection with the name '" + collection.getCollectionName() + "' already exists !!!");
		}

		savedCollection = collectionRepo.save(collection);

		return modelMapper.map(savedCollection, CollectionDTO.class);
	}

	@Override
	public CollectionResponse getCollections(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
		Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
		
		Page<Collection> pageCollections = collectionRepo.findAll(pageDetails);

		List<Collection> collections = pageCollections.getContent();

		if (collections.size() == 0) {
			throw new APIException("No collection is created till now");
		}

		List<CollectionDTO> collectionDTOs = collections.stream()
				.map(collection -> modelMapper.map(collection, CollectionDTO.class)).collect(Collectors.toList());

		CollectionResponse collectionResponse = new CollectionResponse();
		
		collectionResponse.setContent(collectionDTOs);
		collectionResponse.setPageNumber(pageCollections.getNumber());
		collectionResponse.setPageSize(pageCollections.getSize());
		collectionResponse.setTotalElements(pageCollections.getTotalElements());
		collectionResponse.setTotalPages(pageCollections.getTotalPages());
		collectionResponse.setLastPage(pageCollections.isLast());
		
		return collectionResponse;
	}

	@Override
	public CollectionDTO updateCollection(Collection collection, Long collectionId) {
		Collection savedCollection = collectionRepo.findById(collectionId)
				.orElseThrow(() -> new ResourceNotFoundException("Collection", "collectionId", collectionId));

		collection.setCollectionId(collectionId);

		savedCollection = collectionRepo.save(collection);

		return modelMapper.map(savedCollection, CollectionDTO.class);
	}

	@Override
	public String deleteCollection(Long collectionId) {
		Collection collection = collectionRepo.findById(collectionId)
				.orElseThrow(() -> new ResourceNotFoundException("Collection", "collectionId", collectionId));
		
		List<Product> products = collection.getProducts();

		products.forEach(product -> {
			productService.deleteProduct(product.getProductId());
		});
		
		collectionRepo.delete(collection);

		return "Collection with collectionId: " + collectionId + " deleted successfully !!!";
	}
    
}
