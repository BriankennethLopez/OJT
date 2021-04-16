<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Search by DATE</title>
</head>

<body>
	<center>
    <h1>search all record from Besafe</h1>
    <?php
     $connection = mysqli_connect('localhost','id16581731_brianlopez','K>eM4v!EHPn#3Jc1','id16581731_covid_management_system');
	 if(isset($_POST['id'])){
		 $searchKey = $_POST['id'];
		  $sql = "SELECT * FROM data WHERE date LIKE '%$searchKey%'";
		 }else{
			 $sql = "SELECT * FROM data";
			 $searchKey = "";
			 }
	 
	 $result = mysqli_query($connection,$sql);
	 
	
	?>
    
    <div class="container">
    	<form action="" method="POST">
        	<input type="text" name = "id" placeholder="date" value="<?php echo $searchKey;?>">
            <input type="submit" name = "search" value="search by date">
        </form>
        <table>
        <tr>
        	<th>name</th>
            <th>address</th>
            <th>contact</th>
            <th>course</th>
            <th>date</th>
            <br>
        </tr>
        <?php while($row = mysqli_fetch_object($result)) { ?>
        <tr>
        	<th><?php echo $row->name ?></th>
            <th><?php echo $row->address ?></th>
            <th><?php echo $row->contact ?></th>
            <th><?php echo $row->course ?></th>
            <th><?php echo $row->date ?></th>
            <br>
        </tr>
        <?php }?> 
        
        
        </table>
	</center>
</body>
</html>