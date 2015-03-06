<?php  include ("DaoInterfaceUser.php");

class DaoUser implements DaoInterfaceUser {

	public function getID($nickname){
    
		global $link;
		$row = $link->query("select iduser from user where nickname='".$nickname."';")->fetch();
		if($row)
			return $row['iduser'];
		else
			return -1;
	}
	
	
	public function getUserLogged($nickname, $pass, $idNotification){
		global $link;
		
        $link->beginTransaction();
		try{
        
        $link->exec("update user set idNotification='".$idNotification."' where nickname='".$nickname."';");
         
         $row=$link->query("select * from user where nickname=\"".$nickname."\" and password=\"".$pass."\";")->fetch();
         if($row){
         $u = new User();
				$u->setEmail($row['email']);
				$u->setModelID($row['iduser']);
				$u->setNickname($row['nickname']);
				$u->setPassword($row['password']);
            $link->commit();
                return $u;
         }else
         	return null;
        }catch (Exception $e) {
			$link->rollBack();
			return null;
         }
       
	}
    
   public function getUserByNickname($nickname){
		global $link;
		$row=$link->query(
				"select * from user where nickname='".$nickname."';")
				->fetch();
		if($row){
			$u = new User();
			$u->setEmail($row['email']);
			$u->setModelID($row['iduser']);
			$u->setNickname($row['nickname']);
			$u->setPassword($row['password']);
			return $u;
		}else return null;
	}

	//Check Login
	public function isLogin(User $user){
		global $link;

		return $link->query(
				"select * from user where nickname='".$user->getNickname().
				"' && password='".$user->getPassword()."';")->rowCount();
	}

	
	//Check Rgistration
	public function isRegistred(User $user){
		 
		global $link;
        
		try {
			$link->beginTransaction();
			$queryV= "SELECT * FROM user WHERE nickname='".$user->getNickname()."';";
			$queryReg="INSERT INTO user(nickname, password, email) VALUES ('".$user->getNickname()."','".$user->getPassword()."','".$user->getEmail()."');";
			
            $row=$link->query($queryV)->rowCount();
          
        	if($row==1){
            	$link->rollBack();
            	return -1;
            }
            else{
				if($link->exec($queryReg)==1){
					$id = $this->getID($user->getNickname());
					$link->commit();
					return $id;
				}else{
					$link->rollBack();
					return -1;
				}
			} 
            	
		} catch (Exception $e) {
			$link->rollBack();
			return -1;
		}
	}


	// Send email for Forgot password
	public function forgotPassword(User $user){

		global $link;

		$query="SELECT nickname FROM user where email='".$user->getEmail()."';";

		$error=0;

		$row=$link->query($query)->fetch();
		$us="";
		if($row) {
			$us=$row['nickname'];
		}
	 	else
	 		$error=1;
	  

	 	if($error==0){
	 	 

	 	$header= "From: MapYou <mapyouu@gmail.com> \n";
	 	$header .= "Content-Type: text/html; charset=\"iso-8859-1\"\n";
	 	$header .= "Content-Transfer-Encoding: 7bit\n\n";

	 	$subject= "Change password";

	 	$mess_invio="<html><body>";
	 	$mess_invio.="<h2><font color='#0000FF'>Benvenuto nella sezione di recupero password di MapYou </font> </h2><br>";
	 	$mess_invio.="<img src=\"http://mapyou.altervista.org/myMapYou/logo2.png\" align='center' ><br><br>";
	 	$mess_invio.="<h3><font color='#0000FF'>Per creare una nuova password per il tuo account, accedi al link sottostante! </font></h3> ";
	 	$mess_invio.="<a href=\"http://www.mapyou.altervista.org/myMapYou/RecMapYou.php?nickname=".$us."\">Forgot Password MapYou</a><br><br>";
	 	$mess_invio.="'</body><html>'";

	 	mail($user->getEmail(), $subject, $mess_invio, $header);
	 	echo"send";
	 }
	 else
	 	echo "not_send";
	}

	// Update password User
	public function updateUser(User $user){

		global $link;

		$update="The password can't update! Try again!";
		$query="update user set password='".$user->getPassword()."' where nickname='".$user->getNickname()."';";
		$link->query($query);
		$update="The password have been update!";

		return $update;
	}
	
	public function getIdNotificationByUser($user){

	global $link;

		$query= "select * from user where nickname='".$user."';";
  
		$r = $link->query($query);
		
		if($r->rowCount()==1){

			$ids="";
			$row = $r->fetch();
			while($row){
				$ids.=$row['idNotification'];
				$row = $r->fetch();
			}
			return $ids;
		}
		else
			return null;
	}

	public function getUserByNicknameForInvite($user){

		global $link;
        $query= "select nickname from user where nickname='".$user."';";

		
		$qRes=$link->query($query);

		if($qRes->rowCount()==1){
			$row = $qRes->fetch();
			$nick.=$row['nickname'];
		}
		else
			$nick=null;

		return $nick;
	}
	
	public function getUserByID($id){
		global $link;
		$row=$link->query(
				"select * from user where iduser=".$id.";")
				->fetch();
		if($row){
			$u = new User();
			$u->setEmail($row['email']);
			$u->setModelID($row['iduser']);
			$u->setNickname($row['nickname']);
			$u->setPassword($row['password']);
			return $u;
            
		}else 
        	return null;
	}

	public function insertFriend($iduser_a, $iduser_b){
		global $link;
		try {
			$link->beginTransaction();
			$count = $link->exec("insert into friend_relationship(user_a, user_b) values(".$iduser_a.", ".$iduser_b.");");
			$link->commit();
			return $count;
		} catch (Exception $e) {
			$link->rollBack();
			return -1;
		}
	}
	
	public function deleteFried($iduser_a, $iduser_b){
		global $link;
		try {
			$link->beginTransaction();
			$count = $link->exec("delete from friend_relationship where user_a=
				".$iduser_a." and user_b=".$iduser_b.";");
			$link->commit();
			return $count;
		} catch (Exception $e) {
			$link->rollBack();
			return -1;
		}
	}
	
	public function deleteAllFried($iduser){
		global $link;
		try {
			$link->beginTransaction();
			$count =  $link->exec("delete from friend_relationship where user_a=
				".$iduser." or user_b=".$iduser.";");
			$link->commit();
			return $count;
		} catch (Exception $e) {
			$link->rollBack();
			return -1;
		}
	}
	
	public function getAllFried($iduser){
		global $link;
		$q = $link->query("select * from friend_relationship where user_a=
				".$iduser." or user_b=".$iduser.";");
		$row = $q->fetch();
		$res = array();
		while($row){
			$u = null;
			if($row['user_a']==$iduser){
				$u = $this->getUserByID($row['user_b']);
			}else{
				$u = $this->getUserByID($row['user_a']);
			}
			if(!is_null($u))
				$res[] = $u;
			else;
			$row = $q->fetch();
		}
		return $res;
	}
	
	public function getAllNotFriend($iduser){
		global $link;
		$q = $link->query("select * from friend_relationship where user_a!=
				".$iduser." and user_b!=".$iduser.";");
		$row = $q->fetch();
		$res = array();
		while($row){
			$u = null;
			if($row['user_a']==$iduser){
				$u = $this->getUserByID($row['user_b']);
			}else{
				$u = $this->getUserByID($row['user_a']);
			}
			if(!is_null($u))
				$res[] = $u;
			else;
			$row = $q->fetch();
		}
		return $res;
	}
    
    //Check Facebook registration
	public function isAccessFacebook(User $user){
		 
		global $link;
        
		try {
        
			$link->beginTransaction();
            
            $idNotification=getIdNotificationByUser($user->getNickname());
            $count=$link->exec("update user set idNotification='".$idNotification."' where nickname='".$user->getNickname()."';");
            
            if($count==0){
            	 $queryReg="INSERT INTO user(nickname, password, email) VALUES ('".$user->getNickname()."','".$user->getPassword()."','".$user->getEmail()."');";
                 $count2=$link->exec($queryReg);
                 
                 if($count==1){
                 	$id = $this->getID($user->getNickname());
                    $link->commit();
                    return $this->getUserByID($id);
                 
                 }else{
                 	$link->rollBack();
					return -1;
                 }
            }else{
             
            $queryV= "SELECT * FROM user WHERE email='".$user->getEmail()."' and password='".$user->getPassword()."';";
            $row=$link->query($queryV)->rowCount();
          
        	if($row==1){
            		$u = new User();
					$u->setEmail($row['email']);
					$u->setModelID($row['iduser']);
					$u->setNickname($row['nickname']);
					$u->setPassword($row['password']);
                    $link->commit();
					return $u;
            }else{
            
            		$link->rollBack();
					return -1;
            }
          }
         
            	
		} catch (Exception $e) {
			$link->rollBack();
			return -1;
		}
	}
    
}

?>
