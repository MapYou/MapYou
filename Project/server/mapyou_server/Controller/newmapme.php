<?php include "../Dao/DaoFactoryCreator.php"; 
//include "../core/User.php";
//include "../core/Mapme.php";
//include "../core/AbstractSegment.php";
//include '../core/MapmeState.php';
//include '../core/point.php';

$idUserAdmin=$_POST['user'];
$name=$_POST['name'];
$startLat=$_POST['slat'];
$startLon=$_POST['slon'];
$endLat=$_POST['elat'];
$endLon=$_POST['elon'];
$startAdd=$_POST['sadd'];
$endAdd=$_POST['eadd'];
$maxusr=$_POST['mnu'];
$mapmeDao = DAOFactoryCreator::getFactory()->getMapMeDao();
$userDao = DAOFactoryCreator::getFactory()->getUserDao();
$user = $userDao->getUserByNickname($idUserAdmin);

if(!is_null($user) && $maxusr<=20){
$mapme = new Mapme();
$mapme->setMaxNumUsers(intval($maxusr));
$mapme->setName($name);
$mapme->setState(MapmeState::on_create_mapme());
$mapme->setAdministrator($user);

$seg = new Segment();
$sp = new Point();
$ep = new Point();
$sp->setLatitude(doubleval($startLat));
$sp->setLongitude(doubleval($startLon));
$sp->setLocation($startAdd);

$ep->setLatitude(doubleval($endLat));
$ep->setLongitude(doubleval($endLon));
$ep->setLocation($endAdd);

$seg->setEndPoint($ep);
$seg->setStartPoint($sp);

$mapme->setSegment($seg);

echo $mapmeDao->insert($mapme);
}else
	echo -1;

// $result3="0";
// $mapmeDao = DAOFactoryCreator::getFactory()->getMapMeDao();
// $mappingDao = DAOFactoryCreator::getFactory()->getMappingDao();

// $result=$mapmeDao->isRegistredMapMe($mapme);

// if($result!=null){
// 	$user=new User();
// 	$user->setNickname($idUserAdmin);
//     $resultTrim=str_replace(array("\r", "\n", "\t", "\v"), '-', $result);
//    // $result = trim(preg_replace('/\s+/g', '', $result));
//     $mapme->setIdMapme($resultTrim);
// 	$result2=$mappingDao->insertUserInMapme($mapme,$user);
//     echo $result;
// }else
//     echo $result3;


?>
