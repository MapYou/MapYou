<?php include "../Dao/DaoFactoryCreator.php"; 
      include "../Controller_GCM/functions.php"; 
 

$user= $_POST['user'];
$admin= $_POST['admin'];
$idmapme= $_POST['idm'];
$message=$_POST['message'];
$title=$_POST['title'];
$notif=$_POST['notif'];

$userDao = DAOFactoryCreator::getFactory()->getUserDao();
$parteicipationDao = DAOFactoryCreator::getFactory()->getNotificationDao();


if(isset($user)){

	$userIsInvited=$parteicipationDao->insert($userDao->getID($admin), $userDao->getID($user), $idmapme, 'CHAT');
   				 if($userIsInvited >= 1){
						$idNotification=$userDao->getIdNotificationByUser($user);
                        $ct = $parteicipationDao->insertMessageInChat($userIsInvited, $message);
    					if($ct==1){
	                        $registration_ids= array($idNotification);
                            $dt=new DateTime();
    	   					 $msg = array("price" => $message, "title"=>$title, "notif"=>$notif,
                             "idnot"=>$userIsInvited,"type"=>'CHAT',"idsender"=>$userDao->getID($admin),
                             "idmapme"=>$idmapme, "admin"=>$admin,"broadcast"=>false,"date"=>$dt->format('Y-m-d H:i:s'));
							$notification= send_push_notification($registration_ids,$msg);
    						echo "send";
                        }
                        else
                        	echo "Not message registed";
     
    			 }else
     				echo "error";
	
}else
    echo "User not found";



?>
