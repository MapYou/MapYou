<?php

class Notification{
	
	private $notifier;
	private $notified;
	private $mapme;
	private $type;
	private $state;
 	private $modelID;
    private $data;
	
 	function __construct(){
 		$this->modelID=-1;
 	}
    
    function getDate(){
		return $this->data;
	}
    
    function setDate($d){
		$this->data=$d;
	}
 	
	function getNotifier(){
		return $this->notifier;
	}
	
	function getNotified(){
		return $this->notified;
	}
	
	function getMapme(){
		return $this->mapme;
	}
	
	function getType(){
		return $this->type;
	}
	
	function getState(){
		return $this->state;
	}
	
	function setNotifier(User $n){
		$this->notifier = $n;
	}
	
	function setNotified(User $n){
		$this->notified = $n;
	}
		
	function setMapme(Mapme $n){
		$this->mapme = $n;
	}
	
	function setType($n){
		$this->type = $n;
	}
	
	function setState($n){
		$this->state = $n;
	}
 	
 	function getModelID(){
 		return $this->modelID;
 	}
 	
 	function setModelID($id){
 		$this->modelID=$id;
 	}
	
}

?>