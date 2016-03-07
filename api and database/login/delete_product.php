<?php
$response=array();
if(isset($_POST['pid']))
{
    $pid=$_POST['pid'];
    
    require_once __DIR__.'/db_connect.php';
    $db=new Db_connect();
    $response=  mysql_query("delete from products where pid=$pid");
    
    if(mysql_affected_rows()>0){
        $response["success"]=1;
    $response["message"]="Product successfully delete";
    echo json_encode($response); 
    }
    else{
         $response["success"]=0;
    $response["message"]="Product not found";
    echo json_encode($response);
    }
}
else{
    $response["success"]=0;
    $response["message"]="Required field(s) is missing";
    echo json_encode($response);
}
?>

