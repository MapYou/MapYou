<?php 

  interface DaoInterfaceMapping{
  
  public function getAllMappingByMapMe($mapme_id);
  public function getMapping($mapme_id, $user_id);
  public function insertMapping($user_id, Point $point);
  public function updateMapping($user_id, Point $point);
  public function delete($user_id);
  public function getAllMappingByUser($user_id);
  public function deleteForMapme($user_id, $mapme);
  public function updateMappingForMapme($user_id, Point $point, $mapme);
  public function insertMappingForMapme($user_id, Point $point, $mapme);
  public function addMappingForMapMe ($user_id, Point $point, $mapme);
  
  
 }
 
 ?>
