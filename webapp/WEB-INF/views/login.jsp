<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="../comp/script.jsp" %>
<title>%프로젝트이름% : 로그인</title>
</head>
<body>
	
	<div class="container-section login">
		<div class="container login">
			<div class="logo-wrapper">
				<div class="logo"><a href="/">LOGO</a></div>
			</div>
			<div class="title">로그인</div>
			<div class="e i">
				<div class='login'></div>
				<div class='login --iwp'><div class="--fwi"><i class="fa-solid fa-circle-user"></i></div><input type="text" class="mid v" name="mid" maxlength="12" placeholder="아이디"/></div>
			</div>
		
			<div class="e j" style="clear:both;">
				<div class='login'></div>
				<div class='login --iwp'><div class="--fwi"><i class="fa-solid fa-key"></i></div><input type="password" class="mpass v" name="mpass" maxlength="12" placeholder="비밀번호"/></div>
			</div>
			<div class="e alert-wrapper" style="clear:both;">
				<div class="alert fail">
					<div><i class="fa-solid fa-circle-exclamation"></i></div> <div>회원정보가 일치하지 않습니다</div>
				</div>
				<div class="alert denied">
					<div><i class="fa-solid fa-circle-exclamation"></i></div> <div>서버와의 통신에 문제가 있습니다</div>
				</div>
			</div>
	
			<div class="e k" style="clear:both;">
				<div><input class="btn_login" type="button" value="로그인" id="btnLogin"/></div>
			</div>
			<div class="e l">
				<div onclick="gaipFunc()">회원가입</div>
				<div onclick="Yaho()">뒤로가기</div>
			</div>
		</div>
	</div>

    <script>
  		$("input#btnLogin").click(function(){
  			$(".alert").removeClass("picked");
  			$.ajax({
  				url: "loginchk",
  				data: JSON.stringify({
  						memberid : $("input.mid").val(),
  						memberpass: $("input.mpass").val()
  				}),
  				type : "post", //post or get
				async : true, //true or false
				dataType : "json",
				contentType : "application/json",
  				success : function(result){
  					var str = document.referrer;
  					if(result.result=='SUC'){
  						alert("로그인에 성공하셨습니다.");
  						location.href=str;				
  					}
  					else{
						$(".alert.fail").addClass("picked");
					}
  				},
  				error: function(error){
  					console.log(error);
  				}
  				
  			})
  			
  		})
  		
    	
    	var gaipFunc = function(){
    		location.href="register";
    	}
  		var Yaho = function(){
  			location.href=document.referrer;

  		}
  	</script>
    
</body>

<style>
	.btn_login {
		border: 0px solid black;
		background: #8947cc;
		border-radius: 5px;
		width: 100%;
		height: 40px;
		font-size: 15px;
		font-weight: 700;
		color: whitesmoke;
		cursor: pointer;
	}
	.container-section.login {
		background-color: #8947CC;
		padding: 0 !important;
		display: flex;
		align-items: center;
	}
	.container.login {
		align-items: center;
		background-color: #f4f4f4;
    	width: 400px;
		height: 100%;
    	padding: 20px;
		box-shadow: 10px 15px 0px 2px rgb(0 0 255 / 20%);
		border-radius: 5px;
	}
	.container.login > div {
		width: 400px;
		display: flex;
	}
	.container.login .title {
		font-size: 33px;
		font-weight: 600;
		margin-bottom: 10px;
		justify-content: center;
	}
	.alert-wrapper {
		display: flex;
		flex-direction: column;
	}
	.alert.picked {
		display: flex;
	}
	.alert {
		display: none;
    	padding: 5px 0 5px 0;
		font-size: 14px;
    	font-weight: 500;
	}
	.alert.fail {
		color : red;
	}
	.alert.denied {
		color: #ff7700;
	}
	.alert > div {

	}
	.alert > div:nth-child(1) {
		padding: 0 10px 0 10px;
	}
	.e {
		display: flex;
		margin-bottom: 5px;
	}
	.k {
		margin-top: 40px;
	}
	.k > div {
		width: 100%;
	}
	.l {
		position: relative;
		justify-content: center;
	}
	.l > div {
		position: relative;
		padding-left: 24px;
		align-items: center;
    	justify-content: center;
    	font-size: 14px;
    	color: #636363;
		cursor: pointer;
	}
	.l > div+div::before {
		content: '';
		position: absolute;
		top: 3.5px;
		left: 12px;
		width: 1px;
		height: 14px;
		border-radius: 0.5px;
		background-color: #636363;
	}
	.v {
		font-weight: 500;
    	letter-spacing: -0.5px;
		font-family: Consolas;
	}
	.logo-wrapper {
		justify-content: center;
    	margin: 50px 0 50px 0;
	}
	.logo-wrapper > .logo {
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
    .logo-wrapper > .logo > a {
        width: 100%;
        justify-content: center;
        align-items: center;
        display: flex;
    }
    .logo-wrapper > .logo:hover {
        background-color: #a56ddd;
    } 

</style>

</html>