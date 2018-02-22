<%@ include file="../container.jsp" %>
        <div class="starter-template">
            <h1>Bootstrap starter template</h1>
            <p class="lead">Hi <shiro:guest>Guest</shiro:guest><shiro:user><shiro:principal/></shiro:user>!
                ( <shiro:user><a href="<c:url value="/logout"/>">
                    <button class="btn btn-outline-success" type="submit">Log Out</button>
                </a></shiro:user>
                <shiro:guest><a href="<c:url value="login.jsp"/>">
                    <button class="btn btn-outline-success" type="submit">Log In</button>
                </a></shiro:guest> )
            </p>
            </div>
            <div>
                <h3>Roles you have</h3>
                <p>
                <ul class="list-group">
                    <shiro:hasRole name="admin">
                        <li class="list-group-item">admin</li>
                    </shiro:hasRole>
                    <shiro:hasRole name="president">
                        <li class="list-group-item">president</li>
                    </shiro:hasRole>
                    <shiro:hasRole name="darklord">
                        <li class="list-group-item">darklord</li>
                    </shiro:hasRole>
                    <shiro:hasRole name="goodguy">
                        <li class="list-group-item">goodguy</li>
                    </shiro:hasRole>
                    <shiro:hasRole name="schwartz">
                        <li class="list-group-item">schwartz</li>
                    </shiro:hasRole>
                    <shiro:hasRole name="ludicrousspeed">
                        <li class="list-group-item">ludicrousspeed</li>
                    </shiro:hasRole>
                </ul>
                </p>
            </div>
            <div>
                <h3>Roles you DO NOT have</h3>
                <p>
                <ul class="list-group">
                    <shiro:lacksRole name="admin">
                        <li class="list-group-item">admin</li>
                    </shiro:lacksRole>
                    <shiro:lacksRole name="president">
                        <li class="list-group-item">president</li>
                    </shiro:lacksRole>
                    <shiro:lacksRole name="darklord">
                        <li class="list-group-item">darklord</li>
                    </shiro:lacksRole>
                    <shiro:lacksRole name="goodguy">
                        <li class="list-group-item">goodguy</li>
                    </shiro:lacksRole>
                    <shiro:lacksRole name="schwartz">
                        <li class="list-group-item">schwartz</li>
                    </shiro:lacksRole>
                    <shiro:lacksRole name="ludicrousspeed">
                        <li class="list-group-item">ludicrousspeed</li>
                    </shiro:lacksRole>
                </ul>
                </p>
            </div>
        </div>
<%@ include file="container_end.jsp" %>
