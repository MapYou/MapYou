<?php 

  interface DaoInterfaceNotification{
  
   public function getNotificationByMapme($mapme_id, $notification_state, $notification_type);
   
   public function getNotificationByModelID ($id);
   
   public function getNotificationByNotified($notified_id, $notification_state, $notification_type);
   
   public function getNotificationByNotifier($notifier_id, $notification_state, $notification_type);
   
   public function updateState($notification_id, $notification_state);
   
   public function deleteAllByNotifier($notifier_id);
   
   public function deleteAllByNotified($notified_id);
   
   public function delete($notifier_id, $notified_id, $mapme_id);
   
   public function insert($notifier_id, $notified_id, $mapme_id, $notification_type);
   
   public function deleteById($notification_id);
   
  public function retrieveDataFromNotification($notified);
  
  public function getAllNotification($user_id, $state,$type);
  
  public function insertMessageInChat($notification, $message);
  
  public function deleteChatMessage($user, $mapme_id);
  
 public function getAllChatNotification($user_id, $mapme, $typeQuery);
  
 }
 
 ?>
