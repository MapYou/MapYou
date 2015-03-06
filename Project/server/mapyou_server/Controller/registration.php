<?php 
include '../Dao/DaoFactoryCreator.php';
      

$nickname= $_POST['nickname'];
$password= $_POST['password'];
$email= $_POST ['email'];

$user=new User();
$user->setNickname($nickname);
$user->setPassword($password);
$user->setEmail($email);

$userDao = DAOFactoryCreator::getFactory()->getUserDao();

if($nickname!="" && $password!="" && $email!=""){
	$result=$userDao->isRegistred($user);
    echo $result;
}else
	echo "-1";


 

//_GET
//_POST

?>
