<?php

class Root {
	public $phoneNumber = "";
	public $responses = array();
}

class UserResponse {
	public $name = "";
	public $postdate = "";
	public $message = "";
}

require 'simplehtmldom_1_5/simple_html_dom.php';

$root = new Root();
$root->phoneNumber = "1-408-449-8358";
$html = file_get_html('http://800notes.com/Phone.aspx/1-538-603-2145');
foreach($html->find('div[class=oos_p6]') as $element) {
	$response = new UserResponse();
	$response->name = "persons name";
	$response->postdate = new DateTime();
	$response->message = json_encode($element->innertext);
	array_push($root->responses, $response);
}

echo json_encode($root);

?>
