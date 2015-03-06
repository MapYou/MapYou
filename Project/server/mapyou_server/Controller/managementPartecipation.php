<?php include "../Dao/DaoFactoryCreator.php"; 
       
	 
$idnotification= $_POST['idnot'];
$iduser= $_POST['iduser'];
$idmapme= $_POST['idm'];
$isAcceptance= $_POST['isAccept'];

$var="true";

$mapmeDao = DAOFactoryCreator::getFactory()->getMapmeDao(); 
$parteicipationDao = DAOFactoryCreator::getFactory()->getNotificationDao();
 
 

if($isAcceptance==$var){
	
    	$parteicipationDao->deleteById($idnotification);
	$results=$mapmeDao->insertUserInMapMe($idmapme,$iduser);
	if($results==1){
     	
        echo "true";
    }else if($results==0)
   	    {
        echo "false";
        }
        else echo "error"; 
	
}else
	{
    $results2=$parteicipationDao->deleteById($idnotification);
    if($results2 ==1)
    	echo "refused";
     else echo "errorRefused";
	
 

 }

?>


 





 