<?php 

class Point{
	
	private $latitude;
	private $longitude;
	private $location;
 	private $modelID;
	
	function __construct(){
		$this->latitude=-1;
		$this->longitude=-1;
		$this->location="NO LOCATION";
		$this->modelID=-1;
	}
	
	function getLatitude(){
		return doubleval($this->latitude);	
	}
	
	function getLongitude(){
		return doubleval($this->longitude);
	}
	
	function getLocation(){
		return $this->location;
	}
	
	function setLatitude($v){
		$this->latitude=doubleval($v);
	}
	
	function setLongitude($v){
		$this->longitude=doubleval($v);
	}
	
	function setLocation($v){
		$this->location=$v;
	}
 	
 	function getModelID(){
 		return $this->modelID;
 	}
 	
 	function setModelID($id){
 		$this->modelID=$id;
 	}
}

?>