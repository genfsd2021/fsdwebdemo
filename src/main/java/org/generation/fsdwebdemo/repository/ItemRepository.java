package org.generation.fsdwebdemo.repository;

//This interface will extends from the CRUDRepository provided by
// Springboot Framework - then we can access all the methods from the CRUDRepository object

import org.springframework.data.repository.CrudRepository;
import org.generation.fsdwebdemo.repository.entity.Item;

public interface ItemRepository extends CrudRepository<Item, Integer> {

    //Nothing for now bcoz we can just use the methods from the CrudRepository object

    //For more complex system, feature, there might be other methods that I need to addon in the ItemRepository
    // interface with the relevant class
    //ArrayList<Item> getProductWithCategory(String categoryName);
}
