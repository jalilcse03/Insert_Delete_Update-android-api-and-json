<?php
$response = array();

if(isset($_POST['name'])&& isset($_POST['price'])&&isset($_POST['description'])){
    $name=$_POST['name'];
    $price=$_POST['price'];
    $description=$_POST['description'];
    
    // include db connect class
    require_once __DIR__ .'/db_connect.php';
    // connecting to db
    
    $db=new Db_connect();
    
    // mysql inserting a new row
    $result=  mysql_query("INSERT INTO products(name, price, description) VALUES('$name', '$price', '$description')");
    
    if($result){
        $response["success"]=1;
        $response["message"]="Product successfully created.";
        echo json_encode($response);
         }
     else{
         $response["success"]=0;
        $response["message"]="Oops! An error occured.";
        echo json_encode($response);
     }    
}
else{
          $response["success"]=0;
        $response["message"]="required filed(s) is missing";
        echo json_encode($response);
}


?>

