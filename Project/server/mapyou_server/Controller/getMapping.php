<?php
include 'JSonOutputter.php';
include '../Dao/DaoFactoryCreator.php';

$iduser = $_POST['user'];
$idmapme = $_POST['mapme'];

$js = JSonOutputter::getFactory();
if(isset($iduser)){
echo 
	$js->encode(
			$js->mappingToJson(
					DAOFactoryCreator::getFactory()->getMappingDao()->getMapping(intval($idmapme), intval($iduser))
					),
			"Mapping"
			);
  }
  else{
  echo 
	$js->encode(
			$js->mappingToJson(
					DAOFactoryCreator::getFactory()->getMappingDao()->getAllMappingByMapMe(intval($idmapme))
					),
			"AllMappings"
			);
  }

?>