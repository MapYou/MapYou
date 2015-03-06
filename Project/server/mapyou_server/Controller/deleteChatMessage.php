<?php include "../Dao/DaoFactoryCreator.php"; 
      include "../Controller_GCM/functions.php"; 
 

$admin= $_POST['admin'];
$idmapme= $_POST['idm'];

echo DAOFactoryCreator::getFactory()->getNotificationDao()->deleteChatMessage(
	DAOFactoryCreator::getFactory()->getUserDao()->getID($admin), $idmapme);



?>
