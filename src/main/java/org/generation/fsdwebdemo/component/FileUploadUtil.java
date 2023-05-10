package org.generation.fsdwebdemo.component;

import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.*;

import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;


public class FileUploadUtil {
    public static void saveFile(String uploadDir1, String fileName,
                                MultipartFile multipartFile) throws IOException
    {
        // test upload for local folder
//        //C:\Desktop\fsdwebdemo\productimages\
//        Path uploadPath1 = Paths.get(uploadDir1);
//
//        try (InputStream inputStream = multipartFile.getInputStream()) {
//            //C:\Desktop\fsdwebdemo\productimages\t-shirt_new.jpg
//            Path filePath1 = uploadPath1.resolve(fileName);
//
//            Files.copy(inputStream, filePath1, StandardCopyOption.REPLACE_EXISTING);
//
//        } catch (IOException ioe) {
//            throw new IOException("Could not save image file: " + fileName, ioe);
//        }

        //actual upload of images to Azure storage
        /* This is the setup using Azure storage */
        String connectStr2 = "DefaultEndpointsProtocol=https;AccountName=productimagespring;AccountKey=Ks6c/pXfzdU5+atu5N0nQIXzXLkJNwjyeA57C32FpTWrq0bU5KoB0KID5IB6UH3IrCa9yDpgfhgu+AStkpkNUA==;EndpointSuffix=core.windows.net";

        //Create a connection between this web application with the storage container that we created in our azure
        // server
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectStr2).buildClient();

        //Specify which container we want to get the images from.
        String containerName = "prodimage";

        //To get the container with the images
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

        //fileName refers to which image filename that we want to upload (e.g. t-shirt_new.jpg)
        BlobClient blobClient = containerClient.getBlobClient(fileName);
        InputStream inputStream = multipartFile.getInputStream();
        blobClient.upload(inputStream);

    }


}
