<?php include "../Dao/DaoFactoryCreator.php"; 
      include "../Model/User.php";
      include "../Controller_GCM/functions.php";

$nickname= $_POST['nickname'];
$password= $_POST['pass'];

$user=new User();
$user->setPassword($password);
$user->setNickname($nickname);
$userDao = DAOFactoryCreator::getFactory()->getUserDao();
 
$result=$userDao->updateUser($user);
$idNot=$userDao->getIdNotification($user);

//Send Notification
$registration_ids= array($idNot);
$script="The password have benn changed";
$message=array("price" => $script);
$notification= send_push_notification($registration_ids,$message);


?>

<html>
<title>Update MapYou</title>
<head></head>
<body bgcolor="#66FF99">

<style type="text/css">

.titlecix {
	font-family: Verdana, Geneva, sans-serif;
	font-size: 20px;
	alignment-adjust: central;
	text-align:center;
}
</style>
<h1 class="titlecix"><?php echo $result; ?> </h1>
 

</body>
</html>

 