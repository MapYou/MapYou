<pre>
<?php 
include 'Controller/JSonOutputter.php';
include 'Dao/DaoFactoryCreator.php';

$js = JSonOutputter::getFactory();
echo 
		$js->encode(
				$js->
				mapmeToJson(DAOFactoryCreator::getFactory()->getMapMeDao()->getMapMeByUser(1)),
				//  		mapmeToJson(DAOFactoryCreator::getFactory()->getMapMeDao()->getMapMeByModelID(55)),
				"Mapme"
);
 
// foreach (DAOFactoryCreator::getFactory()->getMapMeDao()->getMapMeByAdministrator(1) 
// 		as $r){
// 	echo $r->getName();
// }
?>
</pre>