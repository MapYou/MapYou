<?php 

    interface DaoInterfaceUser{
  
  public function getUserByNickname($nickname);
  public function updateUser(User $utente);
  public function isLogin(User $utente);
  public function getIdNotificationByUser($utente);
  public function getUserByNicknameForInvite ($utente);
  public function getID($nickname);
  public function insertFriend($iduser_a, $iduser_b);
  public function deleteFried($iduser_a, $iduser_b);
  public function deleteAllFried($iduser);
  public function getAllFried($iduser);
  public function getUserLogged($nickname, $pass,$idNotification);
  public function getAllNotFriend($iduser);
  public function isRegistred(User $utente);
  public function isRegistredFacebook(User $user,$idNotification);
  public function getUserByEmailAndPassword($user);
  public function deleteAccount($nickname);
  
 }
 
 ?>
