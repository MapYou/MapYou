<?php include "DaoInterfaceMapMe.php";
include 'conn.php';
 include '../core/Mapme.php';
include '../core/User.php';
include '../core/AbstractSegment.php';
include '../core/point.php';
include '../core/MappingUser.php';
include '../core/MapmeState.php';


class DaoMapMe implements DaoInterfaceMapMe{

	public function getID($mapme_name, $user_nickname){
		global $link;
		$row = $link->query("select idmapme from mapme where name='".$mapme_name."', and
				user=(select iduser from user where nickname='".$user_nickname."');")->fetch();
		if($row)
			return $row['idmapme'];
		else
			return null;
	}

	public function isRegistredMapMe($mapme_name, $admin_nickname){
		global $link;
		$queryAdmin= "SELECT * FROM user WHERE nickname='".$admin_nickname."';";
		$idu = $link->query($queryAdmin)->fetchColumn();
		$q = $link->query("select * from mapme where name='".$mapme_name.
				"' and administrator=".$idu.";");
		return $q->rowCount()==1;
	}

	public function getMapMeByAdministrator ($admin_id){
		global $link;
		$query="SELECT * FROM mapme WHERE administrator='".$admin_id."';";
		$qRes=$link->query($query);
		if($qRes->rowCount()>0){
			$results = array();
			$row = $qRes->fetch();
			while($row){
				$m  =$this->getMapMeByModelID($row['idmapme']);
				if(!is_null($m)){
					$results[] = $m;
				}else;

				$row = $qRes->fetch();
			}
			return $results;
		}
		else
			return null;
	}

	public function insert(Mapme $mapme){
		global $link;

		$segment = $mapme->getSegment();
		$admin = $mapme->getAdministrator();
		if(is_null($segment)){
			return -1;
		}
		else if(is_null($admin)){
			return -1;
		}else{

			$link->beginTransaction();
			try {
				$queryAdmin= "SELECT * FROM user WHERE nickname='".$admin->getNickname()."';";

				$idu = $link->query($queryAdmin)->fetchColumn();
				$q = "select * from mapme where name='".$mapme->getName().
				"' and administrator=".$idu.";";

				$queryseg = "insert into segment (first_latitude,first_longitude,first_location,
						second_latitude,second_longitude,second_location) values(
						".doubleval($segment->getStartPoint()->getLatitude()).",
								".doubleval($segment->getStartPoint()->getLongitude()).",
										'".$segment->getStartPoint()->getLocation()."',
												".doubleval($segment->getEndPoint()->getLatitude()).",
														".doubleval($segment->getEndPoint()->getLongitude()).",
																'".$segment->getEndPoint()->getLocation()."');";


				if($link->query($q)->rowCount()==0 && $link->exec($queryseg)==1){

					$id = $link->query("select max(idsegment) from segment;")->fetchColumn();


					$queryReg="INSERT INTO mapme(name, administrator, maxNumUsers,state, segment) VALUES
							('".$mapme->getName()."',".$idu.",
									".$mapme->getMaxNumUsers().",'".$mapme->getState()."',".$id.");";
					if($link->exec($queryReg)==1){
						$link->commit();
						$mapme->setModelID(
								$link->query("select max(idmapme) from mapme;")->fetchColumn()
						);
						if($this->insertUserInMapMe($mapme->getModelID(), $admin->getModelID())==1)
							return $mapme->getModelID();
						{
							$link->rollBack();
							return -1;
						}
					}else
					{
						$link->rollBack();
						return -1;
					}

				}
				else
				{
					$link->rollBack();
					return -1;
				}
			} catch (Exception $e) {
				$link->rollBack();
				return -1;
			}

		}
	}
	
	public function getNumUsers($mapme_id){
		global $link;
		try {
        	return $link->query("select count(*) from registration_mapme where mapme='".$mapme_id."';")->fetchColumn();
			
		} catch (Exception $e) {
			return -1;
		}
	}

	public function getMapMeByModelID ($id){
		global $link;
		$q = $link->query("select * from mapme where idmapme='".$id."';");
		if($q->rowCount()==1){
			$row = $q->fetch();
			if($row){
				$m  =new Mapme();
				$m->setName($row['name']);
				$m->setCreationDate(new DateTime($row['creationDate']));
				$m->setMaxNumUsers($row['maxNumUsers']);
				$m->setState($row['state']);
				$m->setModelID($row['idmapme']);
				$m->setNumUsers($this->getNumUsers($row['idmapme']));

				$q1 = $link->query("select * from user where iduser='".$row['administrator']."';");
				$row1 = $q1->fetch();

				$q2 = $link->query("select * from segment where idsegment='".$row['segment']."';");
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

	public function delete($mapme_id){
		global $link;
		try {
			$link->beginTransaction();
			$idseg = $link->query("select segment from mapme where idmapme=".$mapme_id.";")->fetchColumn();
			$link->exec("delete from segment where idsegment=".$idseg.";");
			$count = $link->exec("delete from mapme where idmapme=".$mapme_id.";");
			$link->commit();
			return $count;
		} catch (Exception $e) {
			$link->rollBack();
			return -1;
		}
	}

	public function update(Mapme $mapme){
		global $link;
		try {
			$link->beginTransaction();
			$count = $link->exec("update mapme set name='".$mapme->getName()."',
					set name='".$mapme->getName()."', 
					maxNumUsers=".$mapme->getMaxNumUsers().", 
					state='".$mapme->getState()."'
					where idmapme=".$mapme->getModelID().";");
			$link->commit();
			return $count;
		} catch (Exception $e) {
			$link->rollBack();
			return -1;
		}
	}

	public function updateSegment(Segment $seg){
		global $link;
		try {
			$link->beginTransaction();
			$count = $link->exec("update segment
					set first_latitude=".$seg->getStartPoint()->getLatitude().",
					 first_longitude=".$seg->getStartPoint()->getLongitude().",
					 first_location='".$seg->getStartPoint()->getLocation()."',
					 second_latitude=".$seg->getEndPoint()->getLatitude().",
					 second_longitude=".$seg->getEndPoint()->getLongitude().",
					 second_location='".$seg->getEndPoint()->getLocation()."'
					where idsegment=".$seg->getModelID().";");
			$link->commit();
			return $count;
		} catch (Exception $e) {
			$link->rollBack();
			return -1;
		}
	}

	public function getMapMeByUser ($user_id, $isUserInclused){
		global $link;
		$query=$isUserInclused==1?
        	"SELECT * FROM registration_mapme WHERE user='".intval($user_id)."';":
            "SELECT distinct(mapme) FROM `registration_mapme` as rmp where  rmp.mapme<>all(
            		select mapme from registration_mapme as r where r.user='".intval($user_id)."');";
		
        $qRes=$link->query($query);
		if($qRes->rowCount()>0){
			$results = array();
			$row = $qRes->fetch();
			while($row){
				$m  =$this->getMapMeByModelID($row['mapme']);
				if(!is_null($m)){
					$results[] = $m;
				}else;
				$row = $qRes->fetch();
			}
			return $results;
		}
		else return null;
	}

	public function insertUserInMapMe($mapme_id, $utente_id){
		global $link;
		try {
			$link->beginTransaction();
			$num = $this->getNumUsers($mapme_id);
            $maxUs=$link->query("select maxNumUsers from mapme where idmapme='".$mapme_id."';");
			if($num>=0 && $num<$maxUs->fetchColumn())
			{
				$count = $link->exec("insert into registration_mapme(user, mapme) values('".$utente_id."','".$mapme_id."');");
				$link->commit();
				return $count;
			}
			else{
				$link->rollBack();
				return -1;
			}
		} catch (Exception $e) {
			$link->rollBack();
			return -1;
		}

	}

	public function deleteUserFromMapMe($mapme_id, $utente_id){
		global $link;
		try {
				$link->beginTransaction();
				$count = $link->exec("delete from registration_mapme where mapme=".$mapme_id." and user=".$utente_id.";");
				$link->commit();
				return $count;
		} catch (Exception $e) {
			$link->rollBack();
			return -1;
		}
	}

	public function getUsersByMapme($mapme_id){
		global $link;
		$res = $link->query("select * from registration_mapme join user where iduser=user and 
				mapme=".$mapme_id.";");
		$results = array();
		$row = $res->fetch();
		while($row){
			$u = new User();
			$u->setEmail($row['email']);
			$u->setModelID($row['iduser']);
			$u->setNickname($row['nickname']);
			$u->setPassword($row['password']);
			$results[] = $u;
			$row = $res->fetch();
		}
		return $results;
	}
    
    public function isRegisteredUser($mapme_id, $user_id){
		global $link;
		return $link->query("select * from registration_mapme where user='".$user_id."' and 
				mapme=".$mapme_id.";")->fetch();
	}

	public function getAdministratorByMapme($mapme_id){
		global $link;
		$row = $link->query("select * from user where iduser=
				(select administrator from mapme where idmapme='".$mapme_id."');")->fetch();
		if($row){
			$u = new User();
			$u->setEmail($row['email']);
			$u->setModelID($row['iduser']);
			$u->setNickname($row['nickname']);
			$u->setPassword($row['password']);
			return $u;
		}else return null;
	}

}

?>
