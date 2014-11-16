html_movies='''<!DOCTYPE html>
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
            <li><a onclick="add_movie()">Add Movie</a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li><a href="authentication.py?action=logout">Log out</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
	
	
	
	
	<div id="movies-table" class="container-fluid movies-list">
		<div class="col-sm-12 col-xs-12 col-md-12 col-lg-4 col-lg-offset-4">
			<form class="form-signin" role="form" method="post" action="authentication.py">
			<div class="input-group">
				<form class="form" role="form" method="get" action"authentication.py">
					<input hidden type="text" name="action" value="search">
					<input type="text" name="query" class="form-control" placeholder="Movie title...">
					<span class="input-group-btn">
						<button class="btn btn-default" type="submit">Search</button>
					</span>
				</form>
			</div><!-- /input-group -->
			<table class="table" style="background-color: white" >
				<caption></caption>
				<thead>
					<tr>
						<th><h4>Title</h4></th>
						<th><h4>Year</h4></th>
					</tr>
				</thead>
				<tbody>
					{movies}
				</tbody>
			</table>
			<div class="button-container">
				<form class = action="authentication.py"  method="get">
						<input hidden type="text" name="action" value="movies">
						<input hidden type="text" name="page" value="{previous_page}">
						<button class="btn btn-default" style="width:49%">Previous Page</button>
				</form>
				<form action="authentication.py" method="get">
					<input hidden type="text" name="action" value="movies">
					<input hidden type="text" name="page" value="{next_page}">
					<button class="btn btn-default" style="width:49%">Next Page</button>
				</form>
			</div>
		</div>
		
		
		
		
	</div> <!-- /container -->		
	
	
	<div id="add-movie" class="col-sm-12 col-xs-12 col-md-12 col-lg-12" >
		<div id="add-movie-form" class="col-sm-12 col-xs-10 col-xs-offset-1 col-md-8 col-md-offset-2 col-lg-4 col-lg-offset-4">
			<form id="add-movie-form" class="form" role="form" method="post" action="authentication.py" on_submit="movie_added()">
				<h2 class="form-heading">Add movie</h2>
				<input hidden name="action" value="add_movie">
				<input name="title" type="text" class="form-control" placeholder="Title" required autofocus>
				<input name="synopsis" type="text" class="form-control" placeholder="Synopsis" required>
				<input name="image" type="text" class="form-control" placeholder="Image" required>
				<input name="year" type="text" class="form-control" placeholder="Year" required>
				<input name="category" type="text" class="form-control" placeholder="Category" required>
				<input class="btn btn-lg btn-primary btn-block" type="submit" value="Add movie"></button>
				<input class="btn btn-lg btn-primary btn-block" value="Cancel" onclick="movie_added()"></button>
			</form>
		</div>
	</div>
	

	
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script>
		function add_movie(){{
			document.getElementById("movies-table").style.display= "none";
			document.getElementById("add-movie").style.display= "block";
		}}

		function movie_added(){{
			document.getElementById("movies-table").style.display= "block";
			document.getElementById("add-movie").style.display= "none";
		}}
	</script>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="../js/bootstrap.js"></script>
</body>
</html>
'''
