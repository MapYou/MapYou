<?php 
include 'JSonOutputter.php';
include '../Dao/DaoFactoryCreator.php';


$userM=$_POST['user'];
$lat=$_POST['lat'];
$lon=$_POST['lon'];
$mapme = $_POST['mapme'];
$loc = $_POST['loc'];

$dlat = doubleval($lat);
$dlon = doubleval($lon);

if($dlat>0 && $dlon>0 && isset($loc)){
	$point = new Point();
	$point->setLatitude($dlat);
	$point->setLongitude($dlon);
    $point->setLocation($loc);
    $daouser=DAOFactoryCreator::getFactory()->getUserDao();
	
    echo DAOFactoryCreator::getFactory()->getMappingDao()->addMappingForMapMe(intval( $daouser->getID($userM)), $point, intval($mapme));
	
    // if $res==1 update else insertMapping
    
}else
	echo "error coordinates";
    
?>