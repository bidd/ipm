html_login='''
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>IPM-MDB</title>

	<!-- Bootstrap -->
	<link href="../css/bootstrap.css" rel="stylesheet">
</head>
<body>

	<div class="container-fluid" style="padding-top: 10%">
		<div  class="form-group">
		<div class="col-sm-12 col-xs-8 col-xs-offset-2 col-md-8 col-md-offset-2 col-lg-4 col-lg-offset-4">
		<form class="form-signin" role="form" method="post" action="authentication.py">
			<h2 class="form-signin-heading">Log in</h2>
			<input hidden name="action" value="login">
			<input name="username" type="text" class="form-control" placeholder="Username" required autofocus>
			<input name="passwd" type="password" class="form-control" placeholder="Password" required>
            <span>{message}</span>
			<input class="btn btn-lg btn-primary btn-block" type="submit" value="Log in"></button>
		</form>
		</div>
	</div> <!-- /container -->		

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
</body>
</html>
'''
