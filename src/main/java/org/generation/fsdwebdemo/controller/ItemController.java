package org.generation.fsdwebdemo.controller;


import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

import org.generation.fsdwebdemo.repository.entity.Item;
import org.generation.fsdwebdemo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Value;
import org.generation.fsdwebdemo.component.FileUploadUtil;
import org.generation.fsdwebdemo.controller.dto.ItemDTO;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


//Request mapping is to provide a URL route for frontend to call the API endpoints
//controller for /item, /user, /customer, /post
@RestController
@RequestMapping("/item")
public class ItemController {

    @Value("${image.folder}")
    private String imageFolder;

    private final ItemService itemService;

    //Dependency Injection of the itemService object so that the Controller can call any methods
    //in the ItemServiceMySQL Class
    public ItemController( @Autowired ItemService itemService )
    {
        this.itemService = itemService;
    }


    //1) API endpoint - to be able to return all products to the front-end
    //Frontend will issue a GET http request
    //Not in a valid domain (e.g. tp.edu.sg, generation.org)
    //localhost is NOT a valid domain
    @CrossOrigin
    @GetMapping( "/all" )
    public Iterable<Item> getItems()
    {
        //To display images from local folder
//        for (Item image: itemService.all())
//        {
//            //productimages/t-shirt1.jpg
//            String setURL = imageFolder + "/" + image.getImageUrl();
//            image.setImageUrl(setURL);
//            System.out.println(image.getImageUrl());
//        }

        //To display images from the Server Container
        String connectStr2 = "DefaultEndpointsProtocol=https;AccountName=productimagespring;AccountKey=Ks6c/pXfzdU5+atu5N0nQIXzXLkJNwjyeA57C32FpTWrq0bU5KoB0KID5IB6UH3IrCa9yDpgfhgu+AStkpkNUA==;EndpointSuffix=core.windows.net";
        //System.out.println("Connect String: " + connectStr2);
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectStr2).buildClient();
        String containerName = "prodimage";
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

        //productimagesping
        BlobClient blobClient = containerClient.getBlobClient(itemService.all().get(0).getImageUrl());
        System.out.println(blobClient);

        //Loop through the ArrayList of itemService.all() and append the Blob url to the imageUrl
        for (Item image: itemService.all())
        {
            //path: productimagespring/prodimage/t-shirt1.jpg
            String setURL = blobClient.getAccountUrl() + "/" + containerName + "/" + image.getImageUrl();
            image.setImageUrl(setURL);
            System.out.println(setURL);
        }
        //return in the Controller represents a response to the Client
        return this.itemService.all();
    }

    @CrossOrigin
    @PostMapping("/add")
    public void save(  @RequestParam(name="name", required = true) String name,
                       @RequestParam(name="description", required = true) String description,
                       @RequestParam(name="imageUrl", required = true) String imageUrl,
                       @RequestParam(name="style", required = false) String style,
                       @RequestParam(name="price", required = false) double price,
                       @RequestParam("imagefile") MultipartFile multipartFile) throws IOException
    {

        //t-shirt_new.jpg
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        //productimages, t-shirt_new.jpg, object
        FileUploadUtil.saveFile(imageFolder, fileName, multipartFile);

        //String fullPath = imageFolder + "/" + imageUrl;

        ItemDTO itemDto = new ItemDTO(name, description, imageUrl, style, price);
        Item item = new Item(itemDto);
        itemService.save(item);
    }

    //The id value will be sent from the front-end through the API URL parameter
    @CrossOrigin
    @GetMapping( "/{id}" )
    public Item findItemById( @PathVariable Integer id )
    {
        return itemService.findById( id );
    }

    @CrossOrigin
    @DeleteMapping( "/{id}" )
    public void delete( @PathVariable Integer id )
    {
        itemService.delete( id );
    }













    @CrossOrigin
    @PostMapping("/add2")
    public void save(@RequestBody ItemDTO itemDTO)
    {
        Item item = new Item(itemDTO);
        itemService.save(item);
    }

}
