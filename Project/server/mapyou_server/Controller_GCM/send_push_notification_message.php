<?php
 
 include('config.php');
 include ('functions.php');
 

$user= $_POST['user'];
$admin= $_POST['admin'];
$idmapme= $_POST['idm'];
$message=$_POST['message'];
$title=$_POST['title'];
$notif=$_POST['notif'];
$type=$_POST['type'];

$userDao = DAOFactoryCreator::getFactory()->getUserDao();
$parteicipationDao = DAOFactoryCreator::getFactory()->getNotificationDao();

 
$usr=$userDao->getUserByNicknameForInvite($user);


if(isset($usr)){

	$userIsInvited=$parteicipationDao->insert($userDao->getID($admin), $userDao->getID($user), $idmapme, $type);
    
   				 if($userIsInvited >= 1){
						$idNotification=$userDao->getIdNotificationByUser($usr);
    					$registration_ids= array($idNotification);
       					 $msg = array("price" => $message, "title"=>$title, "notif"=>$notif, "idnot"=>$userIsInvited);
						$notification= send_push_notification($registration_ids,$msg);
    					echo "send";
     
    			 }else
     				echo "error";
	
}else
    echo "User not found";



?>
