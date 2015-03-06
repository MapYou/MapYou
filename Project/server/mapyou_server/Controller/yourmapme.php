<?php 
include 'JSonOutputter.php';
include '../Dao/DaoFactoryCreator.php';



$idUser=$_POST['iduser'];
$t=$_POST['inclusion'];
 
if(isset($idUser) ){

	    $js = JSonOutputter::getFactory();
	echo
	$js->encode(
			$js->
			mapmeToJson(DAOFactoryCreator::getFactory()->getMapMeDao()->getMapMeByUser(intval($idUser), intval($t))),
			"Mapme"
	);
}else
	echo "error";

?>