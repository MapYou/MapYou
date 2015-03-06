<?php include "DaoInterfaceNotification.php";
	  include "../core/Notification.php";


// notification state = INSERT | READ 
class DaoNotification implements DaoInterfaceNotification{ 
 
	public function insert($notifier_id, $notified_id, $mapme_id, $notification_type){
		global $link;
		try {
			$link->beginTransaction();
			$count = $link->exec("insert into notification(notifier, notified, mapme, state, type) values(
				'".$notifier_id."', '".$notified_id."', '".$mapme_id."', 'INSERT', '".$notification_type."');");
            if($count==1){
            $id = $link->query("select MAX(idnotification) from notification where notifier='".$notifier_id."' and
            		 notified='".$notified_id."' and mapme='".$mapme_id."' and type='".$notification_type."';")->fetch();
			$link->commit();
            if($id)
            	return $id['MAX(idnotification)'];
            else return -1;
            }
			else
            	{
                $link->commit();
                return $count;
                }
		} catch (Exception $e) {
			$link->rollBack();
			return -1;
		}
	}
    
    public function insertMessageInChat($notification, $message){
		global $link;
		try {
			$link->beginTransaction();
			$count = $link->exec("insert into chat(idnot, message) values(
				'".$notification."', '".$message."');");
            $link->commit();
           return $count;
		} catch (Exception $e) {
			$link->rollBack();
			return -1;
		}
	}
	
	public function deleteById($notification_id){
		global $link;
		try {
			$link->beginTransaction();
			$count = $link->exec("delete from notification
				where idnotification=".$notification_id.";");
			$link->commit();
			return $count;
		} catch (Exception $e) {
			$link->rollBack();
			return -1;
		}
	}
    
    
    public function retrieveDataFromNotification($notified){
    
    global $link;
    
    $query="SELECT MAX(idnotification) FROM `notification` WHERE notified='".$notified."';";
 
    $row=$link->query($query)->fetch();
    if($row){
    	$idmapme= $row['MAX(idnotification)'];
        return $this->getNotificationByModelID($idmapme);
    }
    else
    	return -1;
        	
    }
    
    public function delete($notifier_id, $notified_id, $mapme_id){
		global $link;
		try {
			$link->beginTransaction();
			$count = $link->exec("delete from notification
				where notifier=".$notifier_id." and notified=".$notified_id." and mapme=".$mapme_id.";");
			$link->commit();
			return $count;
		} catch (Exception $e) {
			$link->rollBack();
			return -1;
		}
	}
    
     public function deleteChatMessage($user, $mapme_id){
		global $link;
		try {
			$link->beginTransaction();
			$count = $link->exec("delete from notification
				where (notifier=".$user." or notified=".$user.") and mapme=".$mapme_id.";");
			$link->commit();
			return $count;
		} catch (Exception $e) {
			$link->rollBack();
			return -1;
		}
	}
    
    public function deleteAllByNotified($notified_id){
		global $link;
		try {
			$link->beginTransaction();
			$count = $link->exec("delete from notification where notified=".$notified_id.";");
			$link->commit();
			return $count;
		} catch (Exception $e) {
			$link->rollBack();
			return -1;
		}
	}
    
    public function deleteAllByNotifier($notifier_id){
		global $link;
		try {
			$link->beginTransaction();
			$count = $link->exec("delete from notification
				where notifier=".$notifier_id.";");
			$link->commit();
			return $count;
		} catch (Exception $e) {
			$link->rollBack();
			return -1;
		}
	}
    
    public function updateState($notification_id, $notification_state){
		global $link;
		try {
			$link->beginTransaction();
			$count = $link->exec("update notification set state='".$notification_state."' where idnotification='".$notification_id."';");
			$link->commit();
			return 1;
		} catch (Exception $e) {
			$link->rollBack();
			return 0;
		}
	}
    
    public function getNotificationByNotifier($notifier_id, $notification_state, $notification_type){
   		global $link;
		$not="false";
		$query="SELECT * FROM notification WHERE notifier=".$notifier_id." and state='".$notification_state."' and type ='".$notification_type."';";
		$qRes=$link->query($query);
		if($qRes->rowCount()>0){
			$results = array();
			$row = $qRes->fetch();
			while($row){
				$m  =$this->getNotificationByModelID($row['idnotification']);
				if(!is_null($m)){
					$results[] = $m;
				}else;
				$row = $qRes->fetch();
			}
			return $results;
		}
		else return null;
    
    }
    
     public function getNotificationByNotified($notified_id, $notification_state, $notification_type){
   		global $link;
		$not="false";
		$query="SELECT * FROM notification WHERE notified=".$notified_id." and state='".$notification_state."' and type ='".$notification_type."';";
		$qRes=$link->query($query);
		if($qRes->rowCount()>0){
			$results = array();
			$row = $qRes->fetch();
			while($row){
				$m  =$this->getNotificationByModelID($row['idnotification']);
				if(!is_null($m)){
					$results[] = $m;
				}else;
				$row = $qRes->fetch();
			}
			return $results;
		}
		else return null;
    
    }
    
        public function getNotificationByMapme($mapme_id, $notification_state, $notification_type){
   		global $link;
		$not="false";
		$query="SELECT * FROM notification WHERE mapme=".$mapme_id." and state='".$notification_state."' and type ='".$notification_type."';";
		$qRes=$link->query($query);
		if($qRes->rowCount()>0){
			$results = array();
			$row = $qRes->fetch();
			while($row){
				$m  =$this->getNotificationByModelID($row['idnotification']);
				if(!is_null($m)){
					$results[] = $m;
				}else;
				$row = $qRes->fetch();
			}
			return $results;
		}
		else return null;
    
    }
    
    public function getNotificationByModelID ($id){
		global $link;
		$q = $link->query("select * from notification where idnotification='".$id."';");
		if($q->rowCount()==1){
			$row = $q->fetch();
			if($row){
				$m  = new Notification();
				$m->setNotified(DAOFactoryCreator::getFactory()->getUserDao()->getUserByID($row['notified']));
				$m->setNotifier(DAOFactoryCreator::getFactory()->getUserDao()->getUserByID($row['notifier']));
				$m->setMapme(DAOFactoryCreator::getFactory()->getMapMeDao()->getMapMeByModelID($row['mapme']));
				$m->setState($row['state']);
				$m->setModelID($row['idnotification']);
				$m->setType($row['type']);
                $m->setDate($row['date']);
				
                return $m;

			}else{
				return null;
			}

		}else return null;
	}
         public function getAllNotification($user_id, $state,$type){
   		global $link;
        
        if($type=="CHAT")
       		 $typeC="type='CHAT'";
        else
           $typeC="type!='CHAT'";
		$query="SELECT * FROM notification WHERE state='".$state."' and ".$typeC." and notified='".$user_id."' order by date desc;";
		$qRes=$link->query($query);
		if($qRes->rowCount()>0){
			$results = array();
			$row = $qRes->fetch();
			while($row){
				$m  =$this->getNotificationByModelID($row['idnotification']);
				if(!is_null($m)){
					$results[] = $m;
                    if($type=='CHAT'){
                    	$q = $link->query("SELECT message FROM chat WHERE idnot='".$row['idnotification']."';")->fetchColumn();
                		$m->setState($q);
                    }
				}else;
				$row = $qRes->fetch();
			}
			return $results;
		}
		else return null;
    
    }
    
     public function getAllChatNotification($user_id, $mapme, $typeQuery){
   		global $link;
        $queryBroadcast="SELECT * FROM notification join chat WHERE idnotification=idnot  and type='CHAT' AND mapme='".$mapme."' and notified=notifier order by date desc LIMIT 0,15;";
        
		$query="SELECT * FROM notification join chat WHERE idnotification=idnot and type='CHAT' and mapme='".$mapme."' and notified!=notifier order by date desc LIMIT 0,15;";
		
        if(isset($typeQuery))
       		 $qRes=$link->query($queryBroadcast);
        else
        	$qRes=$link->query($query);
		if($qRes->rowCount()>0){
			$results = array();
			$row = $qRes->fetch();
			while($row){
				$m  =$this->getNotificationByModelID($row['idnotification']);
				if(!is_null($m)){
                	$m->setState($row['message']);
					$results[] = $m;
				}else;
				$row = $qRes->fetch();
			}
			return $results;
		}
		else return null;
    
    }
    
}

?>
