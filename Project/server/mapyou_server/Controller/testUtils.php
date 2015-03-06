<?php include("utils.php");


$dt=new DateTime();
echo $dt->format('Y-m-d H:i:s');
$tex="ciao;peppe;ciao;peppe;ciao;peppe;ciao;peppe";
$users= getListOfUsers($tex);
$arrlength=count($users);
for($i=0; $i<$arrlength; $i++){
	echo $users[$i]->getNickname();
    echo "<BR>"; 
}
   

?>