<?php include "../Dao/DaoFactoryCreator.php"; 
      include "../Controller_GCM/functions.php"; 
	 
 

$notifier= $_POST['nickinvite'];
$notified= $_POST['nickinvited'];
$idmapme= $_POST['idm'];
$type=$_POST['type'];
$message=$_POST['message'];
$title=$_POST['title'];
$notif=$_POST['notif'];

$userDao = DAOFactoryCreator::getFactory()->getUserDao();
$parteicipationDao = DAOFactoryCreator::getFactory()->getNotificationDao();

 
$userInvitedExist=$userDao->getUserByNicknameForInvite($notified);


if(isset($userInvitedExist)){

	if($type=="SEND"){
   		 if(!DAOFactoryCreator::getFactory()->getMapMeDao()->isRegisteredUser($idmapme, $userDao->getID($notified))){
    		$userIsInvited=$parteicipationDao->insert($userDao->getID($notifier), $userDao->getID($notified), $idmapme, $type);
    
   				 if($userIsInvited >= 1){
						$idNotification=$userDao->getIdNotificationByUser($userInvitedExist);
    					$registration_ids= array($idNotification);
       					 $msg = array("price" => $message, "title"=>$title, "notif"=>$notif, "idnot"=>$userIsInvited,"type"=>$type);
						$notification= send_push_notification($registration_ids,$msg);
    					echo "send";
     
    			 }else
     				echo "error";
   		 }else{
   			 echo "refused";
    		}
    }else{
   			 if(DAOFactoryCreator::getFactory()->getMapMeDao()->isRegisteredUser($idmapme, $userDao->getID($notified))){
    $userIsInvited=$parteicipationDao->insert($userDao->getID($notifier), $userDao->getID($notified), $idmapme, $type);
    
    if($userIsInvited >= 1){
		$idNotification=$userDao->getIdNotificationByUser($userInvitedExist);
    	$registration_ids= array($idNotification);
        $msg = array("price" => $message, "title"=>$title, "notif"=>$notif, "idnot"=>$userIsInvited,"type"=>$type);
		$notification= send_push_notification($registration_ids,$msg);
    	echo "send";
     
     }else
     	echo "error";
    }else{
   	 echo "refused";
    }
    }
	
}else
    echo "User not found";


?>


 





 