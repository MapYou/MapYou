<?php
include '../Dao/DaoFactoryCreator.php';

$mode = $_POST['mode'];
$user_a = $_POST['user_a'];
$user_b = $_POST['user_b'];

if(intval($mode)==1)
	echo DAOFactoryCreator::getFactory()->getUserDao()->insertFried($user_a, $user_b);
else if(intval($mode)==2)
	echo DAOFactoryCreator::getFactory()->getUserDao()->deleteFried($user_a, $user_b);
else
	echo -1;
?>