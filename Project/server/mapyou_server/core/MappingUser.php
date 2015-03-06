<?php

class MappingUser{
	
	private $user;
	private $mapme;
	private $point;
	private $detectionDate;
 	private $modelID;

 	function __construct(){
 		$this->modelID=-1;
 	}
	
	function getUser(){
		return $this->user;
	}
	
	function getMapme(){
		return $this->mapme;
	}
	
	function getPoint(){
		return $this->point;
	}
	
	function getDetectionDate(){
		return $this->detectionDate;
	}
	
	function setUser(User $u){
		$this->user=$u;
	}
	
	function setMapme(Mapme $m){
		$this->mapme=$m;
	}
	
	function setPoint(Point $s){
		$this->point=$s;
	}
	
	function setDetectionDate(DateTime $date){
		$this->detectionDate=$date;
	}
 	
 	function getModelID(){
 		return $this->modelID;
 	}
 	
 	function setModelID($id){
 		$this->modelID=$id;
 	}
	
}

?>