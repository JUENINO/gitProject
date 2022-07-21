<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="header-section">
    <div class="header admin-menu">
        <div class="logo-wrapper">
            <div class="logo"><a href="/">LOGO</a></div>
        </div>
        <div class="admin-menu">
            <div class="admin-menu-dagger"><a href="admin.movie">영화 관리</a></div>
            <div class="admin-menu-dagger"><a href="admin.member">회원 관리</a></div>
            <div class="admin-menu-dagger"><a href="admin.article">게시글 관리</a></div>
            <div class="admin-menu-dagger"><a href="admin.reply">댓글 관리</a></div>
            <div class="admin-menu-dagger"><a href="admin.auth">권한 관리</a></div>
            
            <div class="admin-menu-dagger"><a href="admin.ajaxTester">AJAX 테스트툴</a></div>
            <div class="admin-menu-dagger"><a href="admin.kmdb">KMDb API</a></div>
        </div>

    </div>
</div>

<style>
    .logo-wrapper {
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .header-section {
        border-bottom: 1px solid #d9d9d9;
    }
    .header {
        display: flex;
        height: 60px;
    }
    .header > .admin-menu {
        padding-left: 50px;
        display: flex;
    }
    .admin-menu > .admin-menu-dagger {
        padding : 20px;
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
    .admin-menu-dagger {
        display: flex;
        justify-content: center;
        align-items: center;
    }
</style>