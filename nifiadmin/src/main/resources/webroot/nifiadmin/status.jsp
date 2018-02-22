<%@ page import="com.labs1904.nifi.NifiClient" %>
<%@ include file="../container.jsp" %>
        <div class="starter-template">
            <h1> Status </h1>
             <p>
             Available Proccessors: <%= NifiClient.getAvailableProcessors() %>
             </p>
        </div>
<%@ include file="../container_end.jsp" %>
