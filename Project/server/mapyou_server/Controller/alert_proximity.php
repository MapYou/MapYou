<?php include "../Dao/DaoFactoryCreator.php"; 
      include "../Controller_GCM/functions.php"; 
      include("utils.php");
 

$admin= $_POST['admin'];
$idmapme= $_POST['idm'];
$message=$_POST['message'];
$title=$_POST['title'];
$notif=$_POST['notif'];
$distance = $_POST['distance'];

$userDao = DAOFactoryCreator::getFactory()->getUserDao();
$parteicipationDao = DAOFactoryCreator::getFactory()->getNotificationDao();

$users= DAOFactoryCreator::getFactory()->getMapMeDao()->getUsersByMapme(intval($idmapme));
$arrlength=count($users);
for($i=0; $i<$arrlength; $i++){
	$usr=$users[$i]->getNickname();
    
    if($usr==$admin)
    	continue;
   
 	if(isset($usr)){

	$idnotifica=$parteicipationDao->insert($userDao->getID($admin), $userDao->getID($usr), $idmapme, 'PROXIMITY');
   				 if($idnotifica >= 1){
						$idNotificationMobile=$userDao->getIdNotificationByUser($usr);
    					  $registration_ids= array($idNotificationMobile);
    	   					$msg = array("price" => $message, "title"=>$title, "notif"=>$notif, "idnot"=>$userIsInvited,"type"=>'PROXIMITY',"idsender"=> $userDao->getID($admin),"idmapme"=>$idmapme, "distance"=>$distance);
							$notification= send_push_notification($registration_ids,$msg);
    						echo "send";
                          
     
   			 	}else
     				 echo "error";
	
	}else
    	 echo "not found user";
}



?>
