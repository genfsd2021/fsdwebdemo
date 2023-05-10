

//Global variable - to store the image object
let storeImage1 = ""

//When user clicks on 'Save Item':
//1) store all the inputs into variables
//2) do validation
//3) calls a function from the productController.js to access the API to add items to the database

//Add an 'onsubmit' event listener for productform to add a product
newItemForm.addEventListener('submit', (event) => {

   // Prevent default action of the Form submission
   event.preventDefault();

   //1) Select the inputs
   const name = document.querySelector('#newItemNameInput').value;
   const description = document.querySelector('#newItemDescription').value;

   //Browser security will not be able to track/store the actual path of where you choose your image
   // C:/Users/Desktop/t-shirt_new.jpg
   //C:\fakepath\t-shirt_new.jpg
   //console.log(document.querySelector('#newItemImageFile').value + '\n' + "Hello world");

   const imageUrl = document.querySelector('#newItemImageFile').value.replace("C:\\fakepath\\", "");
   const style = document.querySelector('#newItemStyle').value;
   const price = document.querySelector('#newItemPrice').value;

   /* 2)
       Do the Validation code here
   */

   // 3)  calls a function from the productController.js to access the API to add items to the database
   addProduct(name, description, imageUrl, style, price, storeImage);  //arguments

});


// select file input
const input = document.querySelector('#newItemImageFile');
// add event listener
input.addEventListener('change', () => {
   storeImage = input.files[0]; //array of files for us to access
});
