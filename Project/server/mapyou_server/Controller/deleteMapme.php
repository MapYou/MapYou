<?php
include '../Dao/DaoFactoryCreator.php';

$mapmeid = $_POST['idm'];
$userid = $_POST['idu'];

        	$adm = DAOFactoryCreator::getFactory()->getMapMeDao()->getAdministratorByMapme($mapmeid);
            
           if($adm!=null && $adm->getModelID()==$userid){
	           echo DAOFactoryCreator::getFactory()->getMapMeDao()->delete($mapmeid);     
           }
           else
           		echo 3;

?>