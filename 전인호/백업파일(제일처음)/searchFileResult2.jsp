<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xml:lang="ko">
<%--
<c:forEach var="result1" items="${rcvFileList.list}" varStatus="status">
	<a class="file" onclick='fileDownload("${result1.FILE_SEQ}")' ><c:out value="${result1.FILE_NAME}"/></a><BR />
</c:forEach>
--%>
<c:forEach var="result2" items="${contsFileList.list}" varStatus="status">
	<a class="file" onclick='BicfileDownload("${result2.FILE_SEQ}")' ><c:out value="${result2.FILE_NAME}"/></a><BR />
</c:forEach>