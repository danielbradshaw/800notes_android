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
$root->phoneNumber = $_GET['phonenumber'];
$html = file_get_html('http://800notes.com/Phone.aspx/' . $root->phoneNumber);

// oos_t3 -> response root
// oos_p6 -> messages : oos_pd -> user's name : oos_p4 -> date/time
foreach($html->find('li[class=oos_t3]') as $element) 
{
	$response = new UserResponse();

	$nameArray = $element->find('span[class=oos_pd]');
	$response->name = $nameArray[0]->innertext;

	$dateArray = $element->find('div[class=oos_p4]');
	$dateString = $dateArray[0]->first_child()->getAttribute("datetime");
	$response->postdate = new DateTime($dateString);

	$messageArray = $element->find('div[class=oos_p6]');
	$response->message = $messageArray[0]->innertext;

	array_push($root->responses, $response);
}

echo json_encode($root);

?>
