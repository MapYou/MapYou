<?php include "../Dao/DaoFactoryCreator.php"; 
    

$idNot=$_POST['idNot'];
$state = $_POST['state'];
 
echo DAOFactoryCreator::getFactory()->getNotificationDao()->updateState($idNot, $state);



?>