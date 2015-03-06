<?php include "../Dao/DaoFactoryCreator.php"; 
      include "../Controller_GCM/functions.php"; 
      include("utils.php");
 
$admin= $_POST['admin'];
$idmapme= $_POST['idm'];
$message=$_POST['message'];
$title=$_POST['title'];
$notif=$_POST['notif'];

$userDao = DAOFactoryCreator::getFactory()->getUserDao();
$mapmeDao = DAOFactoryCreator::getFactory()->getMapMeDao();
$parteicipationDao = DAOFactoryCreator::getFactory()->getNotificationDao();

 
$users= $mapmeDao->getUsersByMapme(intval($idmapme));
$arrlength=count($users);

$idnotifica=$parteicipationDao->insert($userDao->getID($admin), $userDao->getID($admin), $idmapme, 'CHAT');
   				 if($idnotifica >= 1){
                 
                        $ct = $parteicipationDao->insertMessageInChat($idnotifica, $message);
for($i=0; $i<$arrlength; $i++){
	$usr=$users[$i]->getNickname();
    
    if($usr==$admin)
    	continue;
        else{
        $idNotificationMobile=$userDao->getIdNotificationByUser($usr);
    					if($ct==1){
                        	 
	                        $registration_ids= array($idNotificationMobile);
    	   					$msg = array("price" => $message, "title"=>$title, "notif"=>$notif, "idnot"=>$userIsInvited,"type"=>'CHAT',
                            "idsender"=> $userDao->getID($admin), "admin"=>$admin, "idmapme"=>$idmapme,"broadcast"=>true);
							$notification= send_push_notification($registration_ids,$msg);
    						echo "send";
                          
                        }
                        else
                        	echo "Not message registed";
        }
}
						
     
   			 	}else
     				 echo "error";






?>
