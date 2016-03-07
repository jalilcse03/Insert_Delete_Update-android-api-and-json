<?php
    $response=array();
    
    if(isset($_POST['pid']) && isset($_POST['name'])&& isset($_POST['price'])&& isset($_POST['description']))
    {
        $pid = $_POST['pid'];
        $name = $_POST['name'];
        $price = $_POST['price'];
        $description = $_POST['description'];
        
        include_once __DIR__.'/db_connect.php';
        $db=new Db_connect();
        $result=  mysql_query("Update products set name='$name',price='$price',description='$description' where pid=$pid");

        if($result){
            $response["success"]=1;
            $response["message"]="Product successfully updated.";
            
            echo json_encode($response);
            }
        else{
            $response["success"]=0;
            $response["message"]="Error";
            
            echo json_encode($response);
        }
        
    }
 else {
     $response["success"]=1;
            $response["message"]="Required field(s) is missing";
            
            echo json_encode($response);
}
?>
