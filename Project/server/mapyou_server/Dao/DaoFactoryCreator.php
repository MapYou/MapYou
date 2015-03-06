<?php include("conn.php");
	  include("DaoManager.php");
      include("DaoUser.php");
      include("DaoMapMe.php");
      include("DaoMapping.php");
      include("DaoNotification.php");
      
 
 class DAOFactoryCreator implements DaoManager{
 
 	private static $instance;
 	private $daoUser;
 	private $daoMapme;
 	private $daoNotification;
 	private $daoMapping;
 
 	private function __construct(){
 		$this->daoMapme = new DaoMapMe();
 		$this->daoMapping = new DaoMapping();
 		$this->daoNotification = new DaoNotification();
 		$this->daoUser = new DaoUser();
 	}
 
 
	public static function getFactory(){
	
    	if(!self::$instance)
			self::$instance = new DAOFactoryCreator();
 
		return self::$instance;
	}
 
  
 	 public static function setFactory(DAOFactoryCreator $fact){
		self::$instance= $fact;
  	}
  
 	 public function getConnection(){
		return $link;
 	 }
     
     public function closeConnection(){
     	return $link->close();
     }
  
  	 // Access Object Dao
  	public function getUserDao(){
		return $this->daoUser;
 	}
    public function getMapMeDao(){
		return $this->daoMapme;
 	}
    public function getMappingDao(){
		return $this->daoMapping;
 	}
    public function getNotificationDao(){
		return $this->daoNotification;
 	}
 }
 ?>
