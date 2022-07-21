<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="header-section">
    <div class="header"> 
        <div class="logo-wrapper">
            <div class="logo"><a href="/">LOGO</a></div>
        </div>
        <div class="search-wrapper">
            <div class="search-box">
                <div class="-fw-search"> <i class="fa-solid fa-magnifying-glass"></i>  </div>
                <input class="search-input" placeholder="영화 또는 출연진 이름으로 검색하기" value="${keyword}" />
                <div class="search-button-wrapper"><button class="search-button">검색</button></div>
            </div>
            <div class="auto-completaion"></div>
        </div>
        
        <div class="user-profile-wrapper">
        <%
        if(session.getAttribute("memberid")==null) {
        %>
            <div class="before-login">
        	    <div class="btn_login"> <a href="login">로그인</a> </div>
                <div class="btn_register"> <a href="register">회원가입</a> </div>
            </div>
        <%
        } else {
        %>	
	        <div class="after-login user-profile">
	        	<div><%= session.getAttribute("memberProfile")%></div>
	        	<div class="menu-wrapper">
	        		<div class="menu my-profile"><a href="profile?memberid=<%=session.getAttribute("memberid")%>">내 정보</a></div>
	        		<div class="menu logout"><a href="logout">로그아웃</a></div>
	        	</div>
	        </div>

        <%} %>
        	
        </div>
    </div>
</div>

<script>
    let timeoutObjectId = undefined;
    let acArray = 0;
    let acIndex = 0;
    let acLength = 0;
    let beforeKeyword = "";
    let currentKeyword = "";
    let quickSearch = function () {
            if (!document.querySelector(".search-input").value.trim()) {
                document.querySelector(".auto-completaion").innerHTML = "";
                return;
        	}
            console.log("QUICK SEARCH : "+document.querySelector(".search-input").value.trim());
            $.ajax({
                url : `qs?keyword=\${document.querySelector(".search-input").value}`,
                type : `get`,
                dataType : `json`,
                contentType : `application/json`,
                success : function (res) {
                    console.log(res);
                    let HTML = res.result.map( e => `<div class="ar" data-docid=\${e.DOCID}>\${e.title}</div>`).join("");
                    document.querySelector(".auto-completaion").innerHTML = HTML;
                    acArray = [ document.querySelector(".search-input"), ...document.querySelectorAll(".ar") ];
  			        acIndex = 0;
                    acLength = acArray.length;
                },
                error : function (err) {
                    console.error(err);
                }
            });
            clearTimeout(timeoutObjectId);
        }

    document.querySelector(".search-input").addEventListener("keydown",function (key) {
        let ignoreKeyList = [37, 39, 229]; // arrow-left, arrow-right, korean-ime
        if(ignoreKeyList.includes(key.keyCode)) return;
        
        else if(key.keyCode == 13) { // enter
            document.querySelector(".search-button").click();
        }
        else if(key.keyCode == 40) { // arrow-bottom
            if(acIndex == acLength-1) acIndex = 0;
            acArray.map( e => e.classList.remove("selected"));
            acArray[++acIndex].classList.add("selected");
            acArray[0].value = acArray[acIndex].innerText;
        }
        else if(key.keyCode == 38) { // arrow-top
            if(acIndex == 1) acIndex = acLength;
            acArray.map( e => e.classList.remove("selected"));
            acArray[--acIndex].classList.add("selected");
            acArray[0].value = acArray[acIndex].innerText;
            let that = this;
            setTimeout(function(){ that.selectionStart = that.selectionEnd = 10000; }, 0);
        }
        else if(key.keyCode == 8) { // backspace
            clearTimeout(timeoutObjectId);
            timeoutObjectId = setTimeout(function () { quickSearch();}, 400);
        }
    });
    document.querySelector(".search-input").addEventListener("input", function (key) {
        beforeKeyword = currentKeyword;
        currentKeyword = document.querySelector(".search-input").value;
        if(beforeKeyword.length != currentKeyword.length) { // ignore korean-ime return
            clearTimeout(timeoutObjectId);
            timeoutObjectId = setTimeout(function () { quickSearch();}, 400);
        }
    });
    document.querySelector(".search-input").addEventListener("blur", function (e) {
        setTimeout(function() {
            $('.auto-completaion').css({"display": "none", "opacity" : "0"});
        }, 150);
        
    });
    document.querySelector(".search-input").addEventListener("focus", function (e) {
        $('.auto-completaion').css({"display": "flex", "opacity" : "1"});
    });
    $(document).on("click", ".ar", function () {
        location.href = `movie.info?docid=\${this.dataset.docid}`;
    });
    //header.section
    $(document).on("click", ".header .search-button", function () {
        
        let keyword = document.querySelector(".search-input").value;
        console.log(keyword); 
        if (!keyword || keyword.length < 2) alert("키워드는 2글자 이상 입력하셔야합니다");
        else location.href = `movie.search?keyword=\${keyword}`; 
    }); 


</script> 

<style>
    .before-login > div > a {
        padding: 5px 10px 5px 10px;
        border-radius: 5px;
        border: 1px solid #d9d9d9;
        font-size: 14px;
        display: flex;
        justify-content: center;
        align-items: center;
        margin-right: 5px;
    }
    
    .btn_login {

    }
    .btn_register {

    }
    .ar {
        font-size: 14px;
        cursor: pointer;
    }
    .ar:first-child {
        border-radius: 5px 5px 0 0;
    }
    .ar:last-child {
        border-radius: 0 0 5px 5px;
    }
    .ar.selected, .ar:hover {
        background-color: #d9d9d9;
    }
    .auto-completaion {
        box-shadow: 0px 1px 11px -2px #adadad;
        display: none;
        flex-direction: column;
        position: absolute;
        float: right;
        width: 550px;
        background: #f4f4f4;
        font-size: 13px;
        top: 75px;
        border-radius: 5px;
        box-sizing: border-box;
    }
    .auto-completaion > div {
        padding : 10px 10px 10px 50px;
    }
    .-fw-search {
        justify-content: center;
        align-items: center;
        padding-left: 20px;
        position: absolute;
        height: 100%;
    }
	.header .user-profile .menu-wrapper {
		position: absolute;
    	display: none;
    	flex-direction: column;
    	width: 150px;
    	top: 40px;
    	right : 0%;
    	font-size: 14px;
    	justify-content: center;
    	align-items: center;
    	background: #8947cc;
    	border-radius: 5px;
    	padding : 5px;
    	color: white;
	}
	.menu {
		border-top : 1px solid white;
		padding: 5px 0 5px 0;
		width: 100%;
	}
	.menu:nth-child(1) {
		border-top : 0px solid;
	}
    .menu a:link, .menu a:visited { 
        color : whitesmoke;
        text-decoration: none;
    } 
    
    .header-section {
        width: 100%;
        border-bottom: 1px solid #BEBEBE;
    }
    .header {
        margin: 0 auto; 
        display: flex;
        width: 1080px;
        /* height: 100px; */
        height: 60px;
    }
    .header div { 
        display: flex;
    }
    .header > .logo-wrapper {
        justify-content: center;
        align-items: center;

    }
    .header > .logo-wrapper > .logo {
        display: flex;
        font-size: 28px;
        background-color: #8947CC;
        height: 40px;
        width: 130px;
        color: #53178F; 
        font-weight: 600;
        border-radius: 5px;
        cursor: pointer;
        transition : 0.5s color, 0.5s background-color;
    }
    .header > .logo-wrapper > .logo > a {
        width: 100%;
        justify-content: center;
        align-items: center;
        display: flex;
    }
    .header > .logo-wrapper > .logo:hover {
        background-color: #a56ddd;
    } 
    .header > .search-wrapper {
        flex-grow: 3;
        justify-content: center;
        align-items: center;
        position: relative;
    }
    .search-box {
        position: relative;
        height: 40px;
        background: #D9D9D9;
        width: 550px;
        border-radius: 5px;
    }
    .search-box .search-input {
        background: none;
        border: 0px solid;
        padding-left: 50px;
        flex-grow: 1;
        font-size: 14px;
        outline-color: #53178fb3;
    }
    
    .search-box .search-input::placeholder {
        color: #7B7B7B;
        font-size: 14px;
    }
    .search-box .search-button-wrapper {
        float: right;
        position: absolute;
        right: 1px; 
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .search-box .search-button {
        border: 0px solid;
        background: none;
        font-weight: 600;
        cursor: pointer;
        height: 28px;
        border-radius: 5px;
        width: 40px;
        height: 28px;
        margin-right: 5px;
        padding-right: 9px; /* tt */
        transition : 0.5s color, 0.5s background-color;
        font-size : 14px;
    }
    .search-box .search-button:hover {
        background-color: #8947CC;
        color : whitesmoke;
        transition : 0.5s color, 0.5s background-color;
    }      
    .header > .user-profile-wrapper {
        justify-content: center;
        align-items: center;
    }
    .user-profile {
        border-radius: 50px;
        position : relative;
        background-color: #d9d9d9;
        width: 50px;
        height: 50px;
        justify-content: center;
        align-items: center;
        cursor: pointer;
    }
    .user-profile:hover > .menu-wrapper, .menu-wrapper:hover {
    	display : flex !important;
    }
    .user-profile > div {
        font-size: 33px;
        margin-bottom: 5px;
    }
</style>