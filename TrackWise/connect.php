<?php
$host = "127.0.0.1";
$user = "root";
$password = "";
$database = "trackwise";   // make sure this DB exists
$port = 3307;              // IMPORTANT: your actual port

$conn = mysqli_connect($host, $user, $password, $database, $port);

if (!$conn) {
    die("Database Connection Failed: " . mysqli_connect_error());
}
?>