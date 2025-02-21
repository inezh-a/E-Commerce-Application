package com.app.services;

import com.app.entites.Collection;
import com.app.payloads.CollectionDTO;
import com.app.payloads.CollectionResponse;

public interface CollectionService {

    CollectionDTO createCollection(Collection collection);

    CollectionResponse getCollections(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CollectionDTO updateCollection(Collection collection, Long collectionId);
    
    String deleteCollection(Long collectionId);
}
