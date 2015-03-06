<?php include "../Dao/DaoFactoryCreator.php"; 
      include 'JSonOutputter.php';
     
$user= $_POST['user'];
$state = $_POST['state'];
$type = $_POST['type'];
$st=isset($state)?$state:'INSERT';

$js = JSonOutputter::getFactory();



if(isset($user)){
	echo $js->encode($js->notificationToJson(
    	DAOFactoryCreator::getFactory()->getNotificationDao()->getAllNotification($user, $st,$type)
    	, "Notification"));
}else
	echo "error";


?>