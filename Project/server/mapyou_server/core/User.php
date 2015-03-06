<?php

class User {

	private $nickname;
	private $password;
	private $email;
 	private $modelID;


	function __construct (){
		$this->nickname="";
		$this->password="";
		$this->email="";
		$this->modelID=-1;
	}

	function getNickname(){
		return $this->nickname;
	}
	function getIdUser(){
		return $this->iduser;
	}

	function getPassword(){
		return $this->password;
	}

	function getEmail(){
		return $this->email;
	}

	function setNickname($nickname){
		$this->nickname=$nickname;
	}

	function setEmail($email){
		$this->email=$email;
	}

	function setPassword($password){
		$this->password=$password;
	}
 	
 	function getModelID(){
 		return $this->modelID;
 	}
 	
 	function setModelID($id){
 		$this->modelID=$id;
 	}

}
?>