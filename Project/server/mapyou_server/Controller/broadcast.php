<?php include "../Dao/DaoFactoryCreator.php"; 
      include "../Controller_GCM/functions.php"; 
      include("utils.php");
 

$usersList= $_POST['users'];
$admin= $_POST['admin'];
$idmapme= $_POST['idm'];
$message=$_POST['message'];
$title=$_POST['title'];
$notif=$_POST['notif'];

$userDao = DAOFactoryCreator::getFactory()->getUserDao();
$parteicipationDao = DAOFactoryCreator::getFactory()->getNotificationDao();

 
$users= getListOfUsers($usersList);
$arrlength=count($users);
for($i=0; $i<$arrlength; $i++){
	$usr=$users[$i]->getNickname();
    
    if($usr==$admin)
    	continue;
   
 	if(isset($usr)){

	$idnotifica=$parteicipationDao->insert($userDao->getID($usr), $userDao->getID($usr), $idmapme, 'CHAT');
   				 if($idnotifica >= 1){
						$idNotificationMobile=$userDao->getIdNotificationByUser($usr);
                        $ct = $parteicipationDao->insertMessageInChat($idnotifica, $message);
    					if($ct==1){
                        	 
	                        $registration_ids= array($idNotificationMobile);
    	   					$msg = array("price" => $message, "title"=>$title, "notif"=>$notif, "idnot"=>$userIsInvited,"type"=>'CHAT',"idsender"=> $userDao->getID($admin),"idmapme"=>$idmapme,"broadcast"=>true);
							$notification= send_push_notification($registration_ids,$msg);
    						echo "send";
                          
                        }
                        else
                        	echo "Not message registed";
     
   			 	}else
     				 echo "error";
	
	}else
    	 echo "not found user";
}



?>
