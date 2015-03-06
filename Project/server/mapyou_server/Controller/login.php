<?php 
include 'JSonOutputter.php';
include '../Dao/DaoFactoryCreator.php';

$nickname=$_POST['nickname'];
$pass=$_POST['password'];
$idNot=$_POST['idnot'];

$js = JSonOutputter::getFactory();
echo 
	$js->encode($js->userToJson(DAOFactoryCreator::getFactory()->getUserDao()->getUserLogged($nickname, $pass,$idNot)), "User");
         
//_GET
//_POST
?>
