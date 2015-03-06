<?php
include '../Dao/DaoFactoryCreator.php';
include 'JSonOutputter.php';

$mode = $_POST['mode'];
$user = $_POST['user'];

if(intval($mode)==1)
	echo DAOFactoryCreator::getFactory()->getUserDao()->deleteAllFried($user);
else if(intval($mode)==2){
	$js = JSonOutputter::getFactory();
	echo
	$js->encode(
			$js->
			userToJson(DAOFactoryCreator::getFactory()->getUserDao()->getAllFried($user)),
			"Users"
	);
}
else if(intval($mode)==3){
	$js = JSonOutputter::getFactory();
	echo
	$js->encode(
			$js->
			userToJson(DAOFactoryCreator::getFactory()->getUserDao()->getAllNotFriend($user)),
			"Users"
	);
}
else
	echo -1;

?>