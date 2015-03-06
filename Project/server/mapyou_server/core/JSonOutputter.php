<?php 

class JSonOutputter {

	private static $instance;

	private function __construct(){
	}


	public static function getFactory(){

		if(!self::$instance)
			self::$instance = new JSonOutputter();
		return self::$instance;
	}


	public static function setFactory(JSon $fact){
		self::$instance= $fact;
	}

	public function userToJson($u){
		$results = array();
		if(is_array($u)){
			foreach ($u as $user){
				if($user instanceof  User){
					$results[] = array('id'=>$user->getModelID(),
					'nickname'=>$user->getNickname(),
					'email'=>$user->getEmail());
				}
			}
		}else if($u instanceof User){
			$results[] = array(
            'id'=>$u->getModelID(),
			'nickname'=>$u->getNickname(),
			'email'=>$u->getEmail()
            );
		}
	
		return $results;
	}

	public function pointToJson($p){
		$results = array();
		if(is_array($p)){
			foreach ($p as $point){
				if($point instanceof  Point){
					$results[] = array('id'=>$point->getModelID(),
					'latitude'=>$point->getLatitude(),
					'longitude'=>$point->getLongitude(),
					'location'=>$point->getLocation());
				}
			}
		}else if($p instanceof Point){
			$results[] = array('id'=>$p->getModelID(),
			'latitude'=>$p->getLatitude(),
			'longitude'=>$p->getLongitude(),
			'location'=>$p->getLocation());
		}
			
		return $results;
	}

	public function segmentToJson($s){
		$results = array();
		if(is_array($s)){
			foreach ($s as $segment){
				if($segment instanceof  Segment)
				{
					$results[] = array('id'=>$segment->getModelID(),
					'startPoint'=>$this->pointToJson($segment->getStartPoint()),
					'endPoint'=>$this->pointToJson($segment->getEndPoint()));
				}
			}
		}else if($s instanceof Segment){
			$results[] = array('id'=>$s->getModelID(),
			'startPoint'=>$this->pointToJson($s->getStartPoint()),
			'endPoint'=>$this->pointToJson($s->getEndPoint()));
		}

		return $results;
	}

	public function mapmeToJson($m){
		$results = array();
		if(is_array($m)){
			foreach ($m as $mapme){
				if($mapme instanceof  Mapme)
				{
					$results[] = array('id'=>$mapme->getModelID(),
					'administrator'=>$this->userToJson($mapme->getAdministrator()),
					'creationDate'=>$mapme->getCreationDate(),
					'numUsers'=>$mapme->getNumUsers(),
					'maxNumUsers'=>$mapme->getMaxNumUsers(),
					'state'=>$mapme->getState(),
					'name'=>$mapme->getName(),
					'segment'=>$this->segmentToJson($mapme->getSegment()));
				}
			}
		}else if($m instanceof Mapme){
			$results[] = array('id'=>$m->getModelID(),
			'administrator'=>$this->userToJson($m->getAdministrator()),
			'creationDate'=>$m->getCreationDate(),
			'numUsers'=>$m->getNumUsers(),
			'maxNumUsers'=>$m->getMaxNumUsers(),
			'state'=>$m->getState(),
			'name'=>$m->getName(),
			'segment'=>$this->segmentToJson($m->getSegment()));
		}
		return $results;
	}

	public function mappingToJson($m){
		$results = array();
		if(is_array($m)){
			foreach ($m as $mapping){
				if($mapping instanceof  MappingUser){
					$results[] = array('id'=>$mapping->getModelID(),
					'user'=>$this->userToJson($mapping->getUser()),
					'mapme'=>$this->mapmeToJson($mapping->getMapme()),
					'point'=>$this->pointToJson($mapping->getPoint()),
					'detection'=>$mapping->getDetectionDate());
				}
			}
		}else if($m instanceof MappingUser){
			$results[] = array('id'=>$m->getModelID(),
			'user'=>$this->userToJson($m->getUser()),
			'mapme'=>$this->mapmeToJson($m->getMapme()),
			'point'=>$this->pointToJson($m->getPoint()),
			'detection'=>$m->getDetectionDate());
		}

		return $results;
	}
    
    public function notificationToJson($m){
		$results = array();
		if(is_array($m)){
			foreach ($m as $notification){
				if($notification instanceof  Notification){
					$results[] = array('id'=>$notification->getModelID(),
						'notified'=>$this->userToJson($notification->getNotified()),
            			'notifier'=>$this->userToJson($notification->getNotifier()),
						'mapme'=>$this->mapmeToJson($notification->getMapme()),
						'state'=>$notification->getState(),
						'type'=>$notification->getType());
				}
			}
		}else if($m instanceof Notification){
			$results[] = array('id'=>$m->getModelID(),
			'notified'=>$this->userToJson($m->getNotified()),
            'notifier'=>$this->userToJson($m->getNotifier()),
			'mapme'=>$this->mapmeToJson($m->getMapme()),
			'state'=>$m->getState(),
			'type'=>$m->getType());
		}

		return $results;
	}

	public function encode($array, $head){
		return "{\"".$head."\":".json_encode($array)."}";
	}
	
}
?>