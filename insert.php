<?php
    
    $connection = mysqli_connect("localhost","id16581731_brianlopez","K>eM4v!EHPn#3Jc1","id16581731_covid_management_system");
    
    $name =$_POST["name"];
    $address =$_POST["address"];
    $contact =$_POST["contact"];
    $course =$_POST["course"];
    
    $sql = "INSERT INTO data(name,address,contact,course) VALUES('$name','$address','$contact','$course')";
    
    $result = mysqli_query($connection,$sql);
    
    if($result){
        echo "inserted";
    }else{
        echo "failed";
    }
    mysqli_close($connection);

?>