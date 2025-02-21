package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entites.Collection;

@Repository
public interface CollectionRepo extends JpaRepository<Collection, Long>{
    
    Collection findByCollectionName(String collectionName);

}
