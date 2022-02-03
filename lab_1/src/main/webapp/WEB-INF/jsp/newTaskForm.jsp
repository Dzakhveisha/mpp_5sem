<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <style>
        <%@include file="/WEB-INF/css/main.css" %>
    </style>
</head>
<body>

<div class="formContainer container ">

    <h2 id="newCertificateTitle">Add New Task</h2>

    <%--@elvariable id="task" type="com.bsuir.spp.tasklist.dao.model.Task"--%>
    <form:form id="newCertificateForm" class="justify-content-center align-items-cent m-6"
          method="POST"
          action ="/taskList/new" modelAttribute="task"
               enctype="multipart/form-data">

    <div class=" row g-3 p-2">
            <div class="col-md-6">
                <form:label for="name" path="name"> Task Name </form:label>
                <form:input path="name" class="form-control mx-auto mb-2 mt-1" type="text" required="required"/>
            </div>
            <div class="col-md-6">
                <form:label path="deadline" for="deadline"> Deadline </form:label>
                <form:input path="deadline" id="deadline" class="form-control mx-auto mb-2 mt-1" type="datetime-local" required="required"/>

            </div>
        </div>
        <div class="row justify-content-end g-2 p-2">
            <form:label path="file" for="file"> File </form:label>
            <input id="file" type="file" name="file" path="file" >
        </div>

        <div class=" row justify-content-end g-2 p-2">
            <form:label path="description" for="description"> Description </form:label>
            <form:textarea path="description" rows="6" class="form-control mx-auto mb-2 mt-1" id="description"></form:textarea>
        </div>

        <div class=" row justify-content-end g-2 p-2">
            <div class="col-md-3">
                <button id="registerCancel" class="form-control  mx-auto mb-2 mt-1" type="button"
                        onclick="window.location.href = '${pageContext.request.contextPath}/taskList'">
                Cancel</button>
            </div>
            <div class="col-md-3">
                <input id="saveSubmit" class="form-control mx-auto mb-2 mt-1" type="submit"
                       value="Save">
            </div>
        </div>

    </form:form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"
        integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13"
        crossorigin="anonymous"></script>
</body>
</html>
