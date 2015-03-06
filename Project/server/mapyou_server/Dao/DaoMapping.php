<?php include "DaoInterfaceMapping.php";


class DaoMapping implements DaoInterfaceMapping{

	private function getUserById($iduser){
		global $link;
		$row = $link->query("select * from user where iduser=".$iduser.";")->fetch();
		if($row){
			$u = new User();
			$u->setEmail($row['email']);
			$u->setModelID($row['iduser']);
			$u->setNickname($row['nickname']);
			$u->setPassword($row['password']);
			return $u;
		}else return null;
	}

	private function getMapMeByModelID ($id){
		global $link;
		$q = $link->query("select * from mapme where idmapme=".$id.";");
		if($q->rowCount()==1){
			$results = array();
			$row = $q->fetch();
			if($row){
				$m  =new Mapme();
				$m->setName($row['name']);
				$m->setCreationDate(new DateTime($row['creationDate']));
				$m->setMaxNumUsers($row['maxNumUsers']);
				$m->setState($row['state']);
				$m->setModelID($row['idmapme']);

				$q1 = $link->query("select * from user where iduser=".$row['administrator'].";");
				$row1 = $q1->fetch();

				$q2 = $link->query("select * from segment where idsegment=".$row['segment'].";");
				$row2 = $q2->fetch();
				if($row2 && $row1){
					$u = new User();
					$u->setEmail($row1['email']);
					$u->setModelID($row1['iduser']);
					$u->setNickname($row1['nickname']);
					$u->setPassword($row1['password']);

					$s = new Segment();

					$sp = new Point();
					$sp->setLatitude($row2['first_latitude']);
					$sp->setLongitude($row2['first_longitude']);
					$sp->setLocation($row2['first_location']);

					$ep = new Point();
					$ep->setLatitude($row2['second_latitude']);
					$ep->setLongitude($row2['second_longitude']);
					$ep->setLocation($row2['second_location']);

					$s->setEndPoint($ep);
					$s->setModelID($row2['idsegment']);
					$s->setStartPoint($sp);

					$m->setAdministrator($u);
					$m->setSegment($s);

					return $m;

				}else{
					return null;
				}

			}else{
				return null;
			}


		}else return null;
	}

	public function getAllMappingByMapMe($mapme_id){
		global $link;
		$res = $link->query("select * from mapping where mapme='".$mapme_id."';");
		$row = $res->fetch();
		$results = array();
		while($row){
			$mapping = $this->getMapping($row['mapme'], $row['user']);
			$results[] = $mapping;
           $row = $res->fetch();
		}
		return $results;
	}
	
	public function getAllMappingByUser($user_id){
		global $link;
		$res = $link->query("select * from mapping where user='".$user_id."';");
		$row = $res->fetch();
		$results = array();
		while($row){
			$mapme = $this->getMapMeByModelID($row['mapme']);
			$user = $this->getUserById($row['user']);
			if(!is_null($mapme) && !is_null($user)){
				$mapping = new MappingUser();
				$mapping->setModelID($row['idmapping']);
				$mapping->setDetectionDate(new DateTime($row['detectionDate']));
					
				$point = new Point();
				$point->setLatitude($row['latitude']);
				$point->setLongitude($row['longitude']);
				$point->setLocation($row['location']);
					
				$mapping->setPoint($point);
				$mapping->setMapme($mapme);
				$mapping->setUser($user);
				$results[] = $mapping;
			}else;
	
			$row = $res->fetch();
		}
		return results;
	}

	public function getMapping($mapme_id, $user_id){
		global $link;
		$res = $link->query("select * from mapping where mapme=".$mapme_id." and user=".$user_id.";");
		$row = $res->fetch();
		if($row){
			$mapme = $this->getMapMeByModelID($row['mapme']);
			$user = $this->getUserById($row['user']);
			if(!is_null($mapme) && !is_null($user)){
				$mapping = new MappingUser();
				$mapping->setModelID($row['idmapping']);
				$mapping->setDetectionDate(new DateTime($row['detectionDate']));
					
				$point = new Point();
				$point->setLatitude($row['latitude']);
				$point->setLongitude($row['longitude']);
				$point->setLocation($row['location']);
					
				$mapping->setPoint($point);
				$mapping->setMapme($mapme);
				$mapping->setUser($user);

				return $mapping;
			}else return null;
		}else return null;
	}

	public function insertMapping($user_id, Point $point){
		global $link;
		try {
			$link->beginTransaction();
			$res = $link->query("select mapme from registration_mapme where user=".$user_id.";");
			$row = $res->fetch();
			while($row){
				$link->exec("insert into mapping(user, mapme, latitude, longitude, location)
				 values(".$user_id.", ".$row['mapme'].", ".doubleval($point->getLatitude()).",
				".doubleval($point->getLongitude()).",'".$point->getLocation()."');");
				$row = $res->fetch();
			}
			$link->commit();
			return 1;
		} catch (Exception $e) {
			$link->rollBack();
			return -1;
		}
	}
    
    public function insertMappingForMapme($user_id, Point $point, $mapme){
		global $link;
		try {
			$link->beginTransaction();
			$count = $link->exec("insert into mapping(user, mapme, latitude, longitude, location) values(".$user_id.", ".$mapme.", ".doubleval($point->getLatitude()).",".doubleval($point->getLongitude()).",'".$point->getLocation()."');");
			$link->commit();
			return $count;
		} catch (Exception $e) {
			$link->rollBack();
			return -1;
		}
	}

	public function updateMappingForMapme($user_id, Point $point, $mapme){
		global $link;
		try {
			$link->beginTransaction();
			$count = $link->exec("update mapping set latitude=".doubleval($point->getLatitude()).", longitude=".doubleval($point->getLongitude()).", location='".$point->getLocation()."' where user=".$user_id." and mapme=".$mapme.";");
			$link->commit();
			return $count;
		} catch (Exception $e) {
			$link->rollBack();
			return -1;
		}
	}
    
    
    public function addMappingForMapMe ($user_id, Point $point, $mapme){
    
    	 $result= $this->insertMappingForMapme($user_id,$point, $mapme);
         if($result==0 && $result!=-1)
         		return $resultUpdate=$this->updateMappingForMapme($user_id,$point, $mapme);       
         else
         	return null; //insertMapping
        
    }

	public function deleteForMapme($user_id, $mapme){
		global $link;
		try {
			$link->beginTransaction();
			$count = $link->exec("delete from mapping where user=".$user_id." and mapme=".$mapme.";");
			$link->commit();
			return $count;
		} catch (Exception $e) {
			$link->rollBack();
			return -1;
		}
	}
    
    public function updateMapping($user_id, Point $point){
		global $link;
		try {
			$link->beginTransaction();
			$count = $link->exec("update mapping
				set latitude=".doubleval($point->getLatitude()).",
				longitude=".doubleval($point->getLongitude()).",
				location='".$point->getLocation()."'
				where user=".$user_id.";");
			$link->commit();
			return $count;
		} catch (Exception $e) {
			$link->rollBack();
			return -1;
		}
	}

	public function delete($user_id){
		global $link;
		try {
			$link->beginTransaction();
			$count = $link->exec("delete from mapping where user=".$user_id.";");
			$link->commit();
			return $count;
		} catch (Exception $e) {
			$link->rollBack();
			return -1;
		}
	}

}

?>
