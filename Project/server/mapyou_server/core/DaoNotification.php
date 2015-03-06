<?php include "DaoInterfaceNotification.php";


// notification state = INSERT | READ 
class DaoNotification implements DaoInterfaceNotification{ 
 
	public function insert($notifier_id, $notified_id, $mapme_id, $notification_type){
		global $link;
		try {
			$link->beginTransaction();
			$count = $link->exec("insert into notification(notifier, notified, mapme, state, type) values(
				'".$notifier_id."', '".$notified_id."', '".$mapme_id."', 'INSERT', '".$notification_type."');");
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
    
    
    public function retrieveDataFromNotification($notified,$type){
    
    global $link;
    
    $query="SELECT MAX(idnotification),idnotification,mapme,notifier,notified,type FROM `notification` WHERE notified='".$notified."' && '".$type."';";
 
    $row=$link->query($query)->fetch();
    if($row){
    	$notification= new Notification();
        $notification->setModelID($row['idnotification']);
        $notification->setNotifier($row['notifier']);
        $notification->setNotified($row['notified']);
        $notification->setMapme($row['mapme']);
        $notification->setType($row['type']);
        
        return $notification;
					 
    /*
    	$idmapme= $row['mapme'];
        $query2="SELECT name,nickname FROM `mapme` , `user` WHERE idmapme='".$idmapme."' && administrator=iduser";
        $row2=$link->query($query2)->fetch();
        if($row2){
        
        	$results = array("idmapme"=>$row2['name'],"invited"=>$row2['nickname']);
            return $results;
     */
        }else
        	return null;
        	
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
    
    public function deleteAllByNotified($notified_id){
		global $link;
		try {
			$link->beginTransaction();
			$count = $link->exec("delete from notification
				where notified=".$notified_id.";");
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
		$q = $link->query("select * from notification where idnotification=".$id.";");
		if($q->rowCount()==1){
			$row = $q->fetch();
			if($row){
				$m  =new Notification();
				$m->setNotified(DAOFactoryCreator::getFactory()->getUserDao()->getUserByID($row['notified']));
				$m->setNotifier(DAOFactoryCreator::getFactory()->getUserDao()->getUserByID($row['notifier']));
				$m->setMapme(DAOFactoryCreator::getFactory()->getMapMeDao()->getMapMeByModelID($row['mapme']));
				$m->setState($row['state']);
				$m->setModelID($row['idnotification']);
				$m->setType($row['type']);
				
                return $m;

			}else{
				return null;
			}


		}else return null;
	}
    
}

?>
