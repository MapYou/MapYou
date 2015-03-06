<?php

class Mapme {
	
	private $administrator;
	private $creationDate;
	private $numUsers;
	private $maxNumUsers;
	private $state;
	private $name;
	private $segment;
 	private $modelID;
	
	function __construct(){
		$this->segment = null;
		$this->modelID=-1;
	}
	
	function getAdministrator(){
		return $this->administrator;
	}
	
	function getCreationDate(){
		return $this->creationDate;
	}
	
	function getNumUsers(){
		return $this->numUsers;
	}
	
	function getMaxNumUsers(){
		return $this->maxNumUsers;
	}
	
	function getState(){
		return $this->state;
	}
	
	function getName(){
		return $this->name;
	}
	
	function getSegment(){
		return $this->segment;
	}
	
	function setAdministrator(User $var){
		$this->administrator=$var;
	}
	
	function setCreationDate(DateTime $var){
		$this->creationDate=$var;
	}
	
	function setNumUsers($var){
		$this->numUsers=$var;
	}
	
	function setMaxNumUsers($var){
		$this->maxNumUsers=$var;
	}
	
	function setState($var){
		$this->state=$var;
	}
	
	function setName($var){
		$this->name=$var;
	}
	
	function setSegment(Segment $var){
		$this->segment=$var;
	}
 	
 	function getModelID(){
 		return $this->modelID;
 	}
 	
 	function setModelID($id){
 		$this->modelID=$id;
 	}
}

?>