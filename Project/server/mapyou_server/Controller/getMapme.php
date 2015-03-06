<?php 


include 'JSonOutputter.php';
include '../Dao/DaoFactoryCreator.php';

$idmapme= $_POST['idm'];


$js = JSonOutputter::getFactory();
echo
$js->encode(
		$js->
		mapmeToJson(DAOFactoryCreator::getFactory()->getMapMeDao()->getMapMeByModelID(intval($idmapme))),"Mapme");

?>


