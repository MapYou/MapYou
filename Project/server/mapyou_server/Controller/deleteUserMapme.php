<?php
include '../Dao/DaoFactoryCreator.php';
include("utils.php");

$mapmeid = $_POST['idm'];
$admin = $_POST['admin'];
$usersList= $_POST['users'];

        	$adm = DAOFactoryCreator::getFactory()->getMapMeDao()->getAdministratorByMapme($mapmeid);
            
           if($adm!=null && $adm->getModelID()==$admin){
				$users= getListOfUsers($usersList);
				$arrlength=count($users);
                $count=0;
				for($i=0; $i<$arrlength; $i++){
					$usr=$users[$i]->getNickname();
                    $count=$count+DAOFactoryCreator::getFactory()->getMapMeDao()->deleteUserFromMapMe($mapmeid, 
                    	DAOFactoryCreator::getFactory()->getUserDao()->getID($usr));
                        
	           } 
               echo $count;
           }
           else
           		echo -1;

?>