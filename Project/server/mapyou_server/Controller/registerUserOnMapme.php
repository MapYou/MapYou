<?php 

include '../Dao/DaoFactoryCreator.php';


$idUser=$_POST['user'];
$idMapme=$_POST['mapme'];
$nickname=$_POST['nickname'];

if(!isset($nickname))
	echo DAOFactoryCreator::getFactory()->getMapMeDao()->insertUserInMapMe($idMapme, $idUser); 
else{
	$u = DAOFactoryCreator::getFactory()->getUserDao()->getUserByNickname($nickname);
    if($u!=null)
    	echo DAOFactoryCreator::getFactory()->getMapMeDao()->insertUserInMapMe($idMapme, $u->getModelID()); 
    else
    	echo -1;
}
?>