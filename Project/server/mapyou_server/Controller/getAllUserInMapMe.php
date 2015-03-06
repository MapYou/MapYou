<?php 


include 'JSonOutputter.php';
include '../Dao/DaoFactoryCreator.php';

$idmapme= $_POST['idm'];


$js = JSonOutputter::getFactory();
echo
$js->encode(
		$js->
		userToJson(DAOFactoryCreator::getFactory()->getMapMeDao()->getUsersByMapme(intval($idmapme))),
		"Users"
);

?>


