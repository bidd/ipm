html_detail='''<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>IPM-MDB</title>

	<!-- Bootstrap -->
	<link href="../css/bootstrap.css" rel="stylesheet">
	
</head>
<body class="body">
	
	
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">IPM-MDB</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li ><a href="authentication.py?action=movies&page=1">Movies</a></li>
            <li ><a href="authentication.py?action=delete&ids={ids}">Delete movie</a></li>
           	<li>
          		<form action="authentication.py" method="post" onsubmit="fav_unfav()">
          			<input hidden name="action" value="fav">	
          			<input hidden name="ids" value="{ids}">
          			<input id="fav-navbar" type="image" src="{fav}">
          		</form>
          	</li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li><a href="authentication.py?action=logout">Log out</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
	
	<div class=container style="padding-top: 10%">
		<p class="lead">{message}</p>
		<div class="col-sm-12 col-xs-12 col-md-4 col-lg-5">
			<img class="img-responsive movie-cover" src={image} alt="Cover">
		</div>
		<div class="col-sm-12 col-xs-12 col-md-5 col-md-offset-1 col-lg-6 movie-details">
			<p class="text-left"> <span class="lead">{title} </span> <span class="text-right">{year}</span></p>
		</div>
		<div class="col-sm-12 col-xs-12 col-md-5 col-md-offset-1 col-lg-6 movie-details">
			<p class="text-left"> <span class="lead"> {category} </span></p>
		</div>
		<div class="col-sm-12 col-xs-12 col-md-5 col-md-offset-1 col-lg-6 movie-details">
			<p class="text-left"> <span class="lead"></span> {synopsis} </p>
		</div>
	</div>
	
	<script>
		function fav_unfav(){{
			if (document.getElementById("fav-navbar").src == "../i/heart_empty.png"){{
				document.getElementById("fav-navbar").src= "../i/heart_empty.png";
			}} else {{
				document.getElementById("fav-navbar").src= "../i/heart.png";				
			}}
		}}
	</script>
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="../js/bootstrap.js"></script>
</body>
</html>
'''