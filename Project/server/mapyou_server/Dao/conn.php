<?php

$ipDB="localhost";
$userDB="mapyou";
$pwDB="";
$db="my_mapyou";

$link = new PDO(
		"mysql:host=localhost;dbname=my_mapyou;charset=utf8","mapyou", "");
        
if (mysqli_connect_errno()) {
            
        echo "Error connession DBMS: ".mysqli_connect_error();
        exit();
 
}

?>
