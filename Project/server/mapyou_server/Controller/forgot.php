<?php include "../Dao/DaoFactoryCreator.php"; 
      include "../core/User.php";
       

$email=$_POST['email'];
 
$user=new User();
$user->setEmail($email);
$userDao = DAOFactoryCreator::getFactory()->getUserDao();
 
$result=$userDao->forgotPassword($user);

echo $result;

?>