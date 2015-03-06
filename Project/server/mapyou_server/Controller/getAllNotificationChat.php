<?php include "../Dao/DaoFactoryCreator.php"; 
      include 'JSonOutputter.php';
     
$user= $_POST['user'];
$mapme = $_POST['idm'];
$typeQuery=$_POST['querytype']; // for request broadcast chat

$js = JSonOutputter::getFactory();

if(is_null($typeQuery)){
echo $js->encode($js->notificationToJson(
    	DAOFactoryCreator::getFactory()->getNotificationDao()->getAllChatNotification(intval($user), intval($mapme),null)
    	, "Notification"));
}

else {
echo $js->encode($js->notificationToJson(
    	DAOFactoryCreator::getFactory()->getNotificationDao()->getAllChatNotification(intval($user), intval($mapme),$typeQuery)
    	, "Notification"));
}



?>

 