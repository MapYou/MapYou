<?php 

  interface DaoInterfaceMapMe{
  
  public function getMapMeByAdministrator ($admin_id);
  public function insert(Mapme $mapme);
  public function getMapMeByModelID ($id);
  public function delete($mapme_id);
  public function update(Mapme $mapme);
  public function updateSegment(Segment $seg);
  public function getMapMeByUser ($user_id, $isUserInclused);
  public function insertUserInMapMe($mapme_id, $utente_id);
  public function deleteUserFromMapMe($mapme_id, $utente_id);
  public function getUsersByMapme($mapme_id);
  public function getAdministratorByMapme($mapme_id);
  public function getID($mapme_name, $user_nickname);
  public function getNumUsers($mapme_id);
  public function isRegisteredUser($mapmeid, $user_id);
 }
 
 ?>
