<?php
class MapmeState{
	static function on_create_mapme (){
		return "on_create";
	}

	static function on_start_mapme (){
		return "on_start";
	}
	
	static function on_destroy_mapme (){
		return "on_destroy";
	}
	
}
?>