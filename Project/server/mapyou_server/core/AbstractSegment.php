<?php


 class Segment{

	private $startPoint;
	private $endPoint;
	private  $modelID;
	
	function __construct(){
		$this->modelID=-1;
	}
	
	function getStartPoint(){
		return $this->startPoint;
	}
	
	function getEndPoint(){
		return $this->endPoint;
	}
	
	function setStartPoint(Point $var){
		$this->startPoint=$var;
	}
	
	function setEndPoint(Point $var){
		$this->endPoint=$var;
	}
 	
 	function getModelID(){
 		return $this->modelID;
 	}
 	
 	function setModelID($id){
 		$this->modelID=$id;
 	}
}


?>