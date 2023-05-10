package org.generation.fsdwebdemo.repository.entity;

//Repository package is part of the Model Component (MVC)
//Item is the entity class to use to map against the table from the database
//Model is the object create to use to map to the database table & field
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.generation.fsdwebdemo.controller.dto.ItemDTO;

@Entity
public class Item {

    //Properties/attributes - will be mapped to the columns of the database table
    //Need to use the Wrapper class on primitive data types - need to pass the datatype
    // as an object to CRUDRepository Class (provided by SpringBoot framework)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;             //retrieve product item by ID - has to be an object

    private String name;
    private String description;
    private String imageUrl;
    private String style;
    private  double price;

    //Item Class is used to map with the Database table
    //Any CRUD operation, JPA will make use of this Item Class to map
    //For Read or Delete operation, the JPA requires an empty constructor to
    //populate all the records from the database as the Item instances

    //Contructor Overloading
    public Item() {}

    /* - DTO : Data Transfer Object is setup in the Controller layer
    *  Create and Update operation, JPA requires the ItemDTO object to be sent
    * via the controller
    * */
    public Item(ItemDTO itemDTO)
    {
        //Transfer the object (with the data) to Entity Class for mapping with the
        // database table columns and to be able to save the data in the columns
        this.name = itemDTO.getName();
        this.description = itemDTO.getDescription();
        this.imageUrl = itemDTO.getImageUrl();
        this.style = itemDTO.getStyle();
        this.price = itemDTO.getPrice();
    }


    public Integer getId()
    {
        return id;
    }


    public void setId( Integer id )
    {
        this.id = id;
    }


    public String getName()
    {
        return name;
    }


    public void setName( String name )
    {
        this.name = name;
    }


    public String getDescription()
    {
        return description;
    }


    public void setDescription( String description )
    {
        this.description = description;
    }


    public String getImageUrl()
    {
        return imageUrl;
    }


    public void setImageUrl( String imageUrl )
    {
        this.imageUrl = imageUrl;
    }


    public String getStyle()
    {
        return style;
    }


    public void setStyle( String style )
    {
        this.style = style;
    }


    public double getPrice()
    {
        return price;
    }


    public void setPrice( double price )
    {
        this.price = price;
    }


    @Override
    public String toString()
    {
        return "Item{" + "id=" + id + ", name='" + name + '\'' + ", description='" +
                description + '\'' + ", imageUrl='"
                + imageUrl + '\'' + ",style='" + style + '\'' + ", price='" + price +
                '}';
    }
}
