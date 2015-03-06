<?php 

 //include("../core/User.php");


// return listOfUsers by queryString for BroadcastChat
function getListOfUsers($text){
 
	$token = strtok($text, ";");
	$vec[]=null;
  
	while ($token !== false)
    	{
    	$vec[]=$token;
    	$token = strtok(";");
    }

	$arrlength=count($vec);

	for($x=1;$x<$arrlength;$x++) {
	 $u = new User();
	 $u->setNickname($vec[$x]);
     $users[]=$u;	
	}
    
    return $users;
}
 

?>