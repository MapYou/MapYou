<?php include "../Dao/DaoFactoryCreator.php"; 
      include 'JSonOutputter.php';
     
$userInvited= $_POST['idnot'];
 
 
 

$parteicipationDao = DAOFactoryCreator::getFactory()->getNotificationDao();
$result=$parteicipationDao->getNotificationByModelID($userInvited);
$js = JSonOutputter::getFactory();

if(isset($result))
	echo $js->encode($js->notificationToJson($result, "Notification"));
	 
else
 	echo "Error";


?>

 