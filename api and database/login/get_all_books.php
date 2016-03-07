<?php
 require_once __DIR__.'/db_connect.php';
$response=array();
$db=new Db_connect();
$sql="select * from books";

$result=  mysql_query($sql);

if(!empty($result)){
    if(mysql_num_rows($result)>0){
        
        $response['books']=array();
        while($row=  mysql_fetch_array($result)){
           $book=array(); 
        $book['id']=$row['id'];
        $book['title']=$row['title'];
        $book['author']=$row['author'];
        $book['isbn']=$row['isbn'];
        $book['category']=$row['category'];
        $book['price']=$row['price'];
        
        array_push($response['books'],$book);
        }
        $response['success']=1;
        echo json_encode($response);
    }
    else{
        
    }
}
else{
    
}
?>

