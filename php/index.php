<?php
$Servername="127.0.0.1";
$username="root";
$password="";
$database="CODERKUBE";


$conn = new mysqli($Servername,$username,$password,$database);

if($conn->connect_error){
    die("connection failed: "+ $conn->connect_error);
}
echo "conncetion successfully";

$conn->close();

?>