<?php
include '../Dao/DaoFactoryCreator.php';

 
$admin = $_POST['admin'];

		  $daoMapme=DAOFactoryCreator::getFactory()->getMapMeDao();
          $daoUser=DAOFactoryCreator::getFactory()->getUserDao();
          
          $list_of_mapme = $daoMapme->getMapMeByAdministrator($daoUser->getID($admin));
        	
          $arrlength=count($list_of_mapme);
         
          if($arrlength == 0){          
          	 $res=$daoUser->deleteAccount($admin);
			 if($res==1)
             	echo "success_without_mapme";
             else
             	echo "0";
          
           }else{
          		
		  		for($i=0; $i<$arrlength; $i++){
            		$id_mapme=$list_of_mapme[$i]->getModelID();
            		$result=$daoMapme->delete($id_mapme);
                		if($result==1)
                        	continue;
                }
          }
          
          
         $res=$daoUser->deleteAccount($admin);
             if($res==1)
            	 echo "total_success";
             else
            	 echo "not_success";
           
?>