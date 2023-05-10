/*
    ProductController perform action to the products to be displayed

    (1) Display all products to be retrieved from the back-end
    (2) Add product to the product list (send the new project to the back-end)
    --- edit an existing product detail
    -- remove an existing product from the product list
*/

//development APIs
//const addAPI= 'http://localhost:8080/item/add';
//const displayAPI = 'http://localhost:8080/item/all';

//production APIs
const addAPI = 'https://jeanwebdemo.azurewebsites.net/item/add';
const displayAPI = 'https://jeanwebdemo.azurewebsites.net/item/all';

let productController = [];

function displayItem()
{
       //fetch data from database using the REST API endpoint from Spring Boot
       //GET http method is a default
       fetch(displayAPI)
           .then((resp) => resp.json())
           .then(function(data) {
               console.log("2. receive data")
              // console.log(data);

               data.forEach(function (item) {
                   const itemObj = {
                       id: item.id,
                       name: item.name,
                       description: item.description,
                       imageUrl: item.imageUrl,
                       style: item.style,
                       price: item.price
                  };

                    //This array consist of 12 objects
                   productController.push(itemObj);
             });

            //Display all the 12 objects from the productController array
             renderProductPage();
           })
           .catch(function(error) {
               console.log(error);
           });
}

//(3)  Display all products when user launch the product.html page
function renderProductPage() {

    let display = "";

    for (let i = 0; i < productController.length; i++ ) {

        display += `
            <div  class="col-lg-4">
            <div class="card" style="width: 18rem;">
                <img src=${productController[i].imageUrl} class="card-img-top"
                    alt="image">
                <div class="card-body">
                    <h5 class="card-title">${productController[i].name}</h5>
                    <p class="card-text">${productController[i].style}</p>
                    <a id="item${i+1}" href="#" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#productModal" onClick="displayDetails(${i})">More</a>
                </div>
            </div>
        </div>
        `
    }

    document.querySelector("#row").innerHTML= display;

} //End of renderProductPage function

function displayDetails(index) {
   //When user clicks on any "More" button, the details of the selected product will be displayed
   console.log("testing " + productController[index].imageUrl);

    document.querySelector("#modalName").innerHTML =  productController[index].name;
    document.querySelector("#modalStyle").innerHTML =  productController[index].style;
    document.querySelector("#modalPrice").innerHTML =  productController[index].price;
    document.querySelector("#modalImg").src =  productController[index].imageUrl;
}


function addProduct(name, description, imageUrl, style, price, imageObject)
{
   const formData = new FormData();
   formData.append('name', name);
   formData.append('description', description);
   formData.append('imageUrl', imageUrl);
   formData.append('style', style);
   formData.append('price', price);
   formData.append('imagefile',imageObject);

    fetch(addAPI, {
           method: 'POST',
           body: formData
           })
           .then(function(response) {
               console.log(response.status); // Will show you the status
               if (response.ok) {
                   alert("Successfully Added Product!")
               }
               else {
                  alert("Something went wrong. Please try again")
               }
           })
           .catch((error) => {
               console.error('Error:', error);
               alert("Error adding item to Product")
           });


//   const item = {
//          "name": "abc",
//          "description": "abc",
//          "imageUrl": "t-shirt1.jpg",
//          "price": 10,
//          "style": "abc"
//        }

//  fetch(addAPI, {
//        method: 'POST',
//         headers: {
//              "Content-Type": "application/json",
//              // 'Content-Type': 'application/x-www-form-urlencoded',
//            },
//        body: JSON.stringify(item)
//        })
//        .then(function(response) {
//            console.log(response.status); // Will show you the status - 200 ok, 500, 404
//            if (response.ok) {
//                alert("Successfully Added Product!")
//            }
//            else {
//               alert("Something went wrong. Please try again")
//            }
//        })
//        .catch((error) => {
//            console.error('Error:', error);
//            alert("Error adding item to Product")
//        });
}





