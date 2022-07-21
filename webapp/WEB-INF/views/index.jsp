<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="../comp/script.jsp" %>
<title>index.jsp</title>
</head> 
<body>

    <%@ include file="../comp/header.jsp" %>
    <div class="container-section">

        <div class="container">
            <div><a href="moviegrid">영화관리테스트</a></div>
            <div><a href="test2">asasas</a></div>
            <a href="admin.kmdb">관리자 페이지</a>
        </div>
        <div class="container">
            
            <div class="carousel">
                <div class="title">캐러셀 샘플</div>
                <div>
                    <div>
                        <div class="posters"></div>
                        <div class="desc">
                            <div class="movie-title"></div>
                            <div class=""></div>
                            
                        </div>
                    </div>
                </div>
            </div>
            <div class="carousel">
                <div class="title">캐러셀 샘플</div>

            </div>
            <div class="carousel">
                <div class="title">캐러셀 샘플</div>

            </div>
        </div>
    </div>
    <%@ include file="../comp/footer.jsp" %>
</body>


<style>
    .carousel {
        height: 300px;
    }
    .carousel .title {
        font-weight: 600;
        font-size: 20px;
    }

</style>
</html>