<?php 
include '../Dao/DaoFactoryCreator.php';
include 'JSonOutputter.php';
      

$nickname= $_POST['n'];
$password= $_POST['p'];
$email= $_POST ['e'];
$id= $_POST ['id'];
 

$user=new User();
$user->setNickname($nickname);
$user->setPassword($password);
$user->setEmail($email);

$userDao = DAOFactoryCreator::getFactory()->getUserDao();

if($nickname!="" && $password!="" && $email!=""){
$js = JSonOutputter::getFactory();
	echo 
		$js->encode($js->userToJson(DAOFactoryCreator::getFactory()->getUserDao()->isRegistredFacebook($user,$id)), "User");
	 
}else
	echo "-1";


 

//_GET
//_POST

?>
