<%@ include file="include.jsp" %>
<!doctype html>
<html lang="en">
<head>
    <%@ include file="head.jsp" %>
    <title>Sample Login Page</title>
    <link href="<c:url value="static/signin.css"/>" rel="stylesheet">
</head>
<body>
 <div class="container">
        <form class="form-signin" name="loginform" action="" method="post">
            <div class="text-center mb-4">
                <img class="mb-4" src="https://getbootstrap.com/assets/brand/bootstrap-solid.svg" alt="" width="72" height="72">
                <h1 class="h3 mb-3 font-weight-normal">Fake Login</h1>
                <p>Username root, password secret</p>
            </div>

            <div class="form-label-group">
                <input type="text" id="username" name="username" maxlength="30" class="form-control" placeholder="username" required autofocus>
                <label for="username">username</label>
            </div>

            <div class="form-label-group">
                <input type="password" id="password" name="password" maxlength="30" class="form-control" placeholder="Password" required>
                <label for="password">Password</label>
            </div>

            <div class="checkbox mb-3">
                <label>
                    <input type="checkbox" name="rememberMe"> Remember me
                </label>
            </div>

            <button class="btn btn-lg btn-primary btn-block" name="submit" type="submit">Sign in</button>
            <p class="mt-5 mb-3 text-muted text-center">&copy; 2017-2018</p>
        </form>
    </div> <!-- /container -->
</body>
<%@ include file="scripts.jsp"%>
</html>