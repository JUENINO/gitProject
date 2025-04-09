<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="html" uri="/WEB-INF/tld/html.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>

<script	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="<spring:message code="site.ssl_url" />/js/jquery.ui.datepicker-ko.js"></script>

<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport"
	content="width=device-width, initial-scale=1 ,maximum-scale=1, minimum-scale=1, target-densitydpi=medium-dpi" />
<meta name="format-detection" content="telephone=no" />

<%-- 
<!--  datepicker -->
<link rel="stylesheet" type="text/css" href="../css/jquery-ui-1.8.23.custom.css">

	<link href="/k_js/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="/k_js/styles/kendo.default.min.css" rel="stylesheet" />
	<script type="text/javascript" src="<spring:message code="site.ssl_url" />/js/jquery.ui.datepicker-ko.js"></script>
	<script type="text/javascript" src="<spring:message code="site.ssl_url" />/js/jquery-ui.kmrb.js"></script>
<script type="text/javascript" src="<spring:message code="site.ssl_url" />/js/jquery.ui.core.min.js"></script>
    <script src="/k_js/angular.min.js"></script>
    <script src="/k_js/kendo.all.min.js"></script>
<!--  datepicker -->
 --%>





<link rel="stylesheet" type="text/css" href="../css/searchEngine/css/prg.style.css">
<link rel="stylesheet" type="text/css" 	href="../css/searchEngine/css/korLayout.css">
<link rel="stylesheet" type="text/css" href="../css/searchEngine/css/common.css">
<link rel="stylesheet" type="text/css" href="../css/searchEngine/css/core.common.css">
<link rel="stylesheet" type="text/css" href="../css/searchEngine/css/core.style.css">
<link rel="stylesheet" type="text/css" href="../css/searchEngine/css/style.default.css">
<link rel="stylesheet" type="text/css" href="../css/searchEngine/css/webfont.css">
<script src="js/jquery-2.1.4.min.js"></script>

<meta name="title" content="대표(국문)" />
<meta name="format-detection" content="telephone=no" />

<!-- 자동완성(autocomplet) 을 위한 외부script -->							
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<!-- 자동완성을 위한 외부script -->	

							<style>
@import
	url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap')
	;

@font-face {
	font-family: 'Noto Sans KR';
	font-style: normal;
	font-weight: 400;
	src: url(../font/NotoSansKR-Regular.woff2) format('woff2'),
}


/* 프로그레스바 스타일 */
#loading {
 width: 100%;  
 height: 100%;  
 top: 0px;
 left: 0px;
 position: fixed;  
 display: block;  
 opacity: 0.7;  
 background-color: #fff;  
 z-index: 1000;  
 display: none;
 
 text-align: center; } 
  
#loading-image {  
 position: absolute;  
 top: 30%;  
 left: 40%; 
 z-index: 1000; }
/* 프로그레스바 스타일 */

</style>


		<script type="text/javascript">
				document.addEventListener("contextmenu", e => {
				    e.target.matches("img") && e.preventDefault()
				});
				
			$(document).ready(function() {
				if(${paramMap.documentStart eq 'documentStart'}){
					
					$("#ins_sch").on("click", function(){
						return false;
					});
				}
				
				//자동완성 켜기/끄기 유무 시작 
				if(${paramMap.autocompleteChk eq 'N'}){
						$("#dropMenu_ul").hide();
						$("#sch-AutoTxt").text("자동완성 켜기");
						$("#autocompleteChk").val('N');
				}
				//자동완성 켜기/끄기 유무 끝
				
				
				
				//사용자 메뉴얼 mouseover, mouseout 이벤트 시, 물음표 이미지 변경하는 함수 시작
				$(".menualLink").on("mouseover", function(){
					$(this).find("img").attr("src" , "../css/searchEngine/img/manualVisible_mouseover.jpg");
					//$(this).css("font-weight" , 200);
				});
				
				$(".menualLink").on("mouseout", function(){
					$(this).find("img").attr("src" , "../css/searchEngine/img/manualVisible.jpeg");
					//$(this).css("font-weight" , '');
				});
				//사용자 메뉴얼 mouseover, mouseout 이벤트 시, 물음표 이미지 변경하는 함수 끝
				
				
				
				
				
				//20230227 : 탭 클릭 시 class='on' 추가 로직 시작
				// 이 JSP에서는 통합검색 말고는 보여질 기회가 없기떄문에, 통합검색에 대해서만 체크하면됨. (일단은 searchEngineResult2.jsp에도 사용할 수 있는 로직이니 복붙하기 위해 다 체크해놓음)
				if(
					(!$("#sectionGubun01").prop("checked")) &&
					(!$("#sectionGubun02").prop("checked")) &&
					(!$("#sectionGubun03").prop("checked")) &&
					(!$("#sectionGubun04").prop("checked")) &&
					(!$("#sectionGubun05").prop("checked")) &&
					(!$("#sectionGubun06").prop("checked")) &&
					(!$("#sectionGubun07").prop("checked")) &&
					(!$("#sectionGubun08").prop("checked")) 
				  ){
					$("#totalTab").addClass("on");
				} 
				
				
				//20230227 : 탭 클릭 시 class='on' 추가 로직 끝
				
				
				if('${paramMap.detailGubun}' == 'remove'){
					detail_remove_chk();
				}
				if('${paramMap.detailGubun}' == 'movie01_video01'){
					detail_movie01_video01_chk();
				}
				if('${paramMap.detailGubun}' == 'ad01'){
					detail_ad01_chk();
				}
				if('${paramMap.detailGubun}' == 'perform01'){
					detail_perform01_chk();
				}
				if('${paramMap.detailGubun}' == 'opin01'){
					//detail_opin01_chk();
					detail_remove_chk();
				}
				if('${paramMap.detailGubun}' == 'img01'){
					//detail_img01_chk();
					detail_remove_chk();
				}
				if('${paramMap.detailGubun}' == 'file01'){
					//detail_file01_chk();
					detail_remove_chk();
				}
				if('${paramMap.detailGubun}' == 'moni01'){
					//detail_moni01_chk();
					detail_remove_chk();
				}
				
				
				
				
				
				
				
				$('b').css('color', 'red');
				
				if("${paramMap.detail_visibleChk}" == "N" || "${paramMap.detail_visibleChk}" == ""){
					$("#divDetail").hide();
					$("#detail_visibleChk").val("N");
			    
				} else{
			    	$("#divDetail").show();
					$("#detail_visibleChk").val("Y");
			    }
				
				//용도 : 첫 로딩 시, 빈값으로 넘어가면 이 로직을 쓰진 않더라도 null pointer 오류가 날 가능성이 높다.
				if("${paramMap.detail_kindChk}" == "common" || "${paramMap.detail_kindChk}" == ""){
					//common을 기본값으로 줘도 무방한 이유가 , 어차피 detail.visibleChk 변수의 값이 N이면 적용이 안되는 값이라서.
					$("#detail_kindChk").val("common");
				} 
			    
				
				
				
				//정렬기준 로직
				//기본적으로 메인페이지 접속 하면 정렬기준 [정확도순]으로 설정함 -> 페이징 컨트롤러처럼 페이지가 이동되거나, 해당 사이트에서 계속 바뀔수 있는 경우에는 ready함수에 이런세팅은 위험하지만, 메인페이지에선 항상 그러한 변경이 없다.
				$("#orderValue").val("orderRelevance");
				$("#onclickRe").on("click", function() {
					$("#orderValue").val("orderRelevance");
					modifiedReset();
					ControllHelper.search();
				});
				$("#onclickGa").on("click", function() {
					$("#orderValue").val("orderGa");
					modifiedReset();
					ControllHelper.search();
				});
				$("#onclickDate").on("click", function() {
					$("#orderValue").val("orderDate");
					modifiedReset();
					ControllHelper.search();
				});
				//정렬기준 로직
				
				if("${paramMap.isSearch}" == "N"){
					$("#ins_sch").attr("disabled", true);
				} else {
					$("#ins_sch").attr("disabled", false);
				}
				
				
				
				
				
				
				
				////////////////////////////페이지 로딩 시, 상세조건에 알맞은 값 선택해주기
				if(${not empty paramMap.detailSearch_grade_name}){
					$("input:radio[name='detailSearch_grade_name'][value='${paramMap.detailSearch_grade_name}']").prop("checked", true);
				}
				if(${not empty paramMap.detailSearch_rt_std_name}){
					$("input:radio[name='detailSearch_rt_std_name'][value='${paramMap.detailSearch_rt_std_name}']").prop("checked", true);
				}
				// 1. 영화,비디오물 상세검색 조건  || 2. 공연추천 상세검색 조건 select box
				if(${not empty paramMap.detailSearch_hope_grade_name}){
					$("select[name='detailSearch_hope_grade_name']").val('${paramMap.detailSearch_hope_grade_name}');
				}
				
				// 영화, 비디오물 (매체명 : 국내/국외)
				if(${not empty paramMap.detailSearch_medi_name_movieAndVideo}){
					$("select[name='detailSearch_medi_name_movieAndVideo']").val('${paramMap.detailSearch_medi_name_movieAndVideo}');
				}
				
				//공연추천 상세검색 조건 - 종류 select box  (detailSearch_kind_name)
				if(${not empty paramMap.detailSearch_kind_name}){
					$("select[name='detailSearch_kind_name']").val('${paramMap.detailSearch_kind_name}');
				}
				//공연추천 상세검색 조건 - 연소자 유해성 여부 select box (detailSearch_minor_malef_yn)
				if(${not empty paramMap.detailSearch_minor_malef_yn}){
					$("select[name='detailSearch_minor_malef_yn']").val('${paramMap.detailSearch_minor_malef_yn}');
				}
				////////////////////////////페이지 로딩 시, 상세조건에 알맞은 값 선택해주기

				
				
				
				
				
				
				//페이지 로딩 시, 결과내 재검색 체크하기
				if(${paramMap.result_re_search_chk eq 'Y'}){
					$("input:checkbox[id='ins_sch']").prop("checked", true);
				} else {
					if(${empty paramMap.result_re_search_chk}){
						$("#result_re_search_chk").val("N");
					}
					$("input:checkbox[id='ins_sch']").prop("checked", false);
				}
			    //페이지 로딩 시, 결과내 재검색 체크하기
			    //결과내 재검색 체크박스 클릭 시, 체크되었다면 체크 여부를 구분할 수 있는 결과내 재검색 hidden값에 Y를 부여하기. --> 이게 Y인 채로 컨트롤러에 넘어가면 컨트롤러에서는 결과내 재검색의 알고리즘을 실행한다.
				$("#ins_sch").on("click", function(){
					if($("input:checkbox[id='ins_sch']").prop("checked") == true){
					    $("#result_re_search_chk").val("Y");
					} else {
						$("#result_re_search_chk").val("N");
					}
				})
			    //결과내 재검색 체크박스 클릭 시
				
			});
	
			//결정등급 라디오 버튼을 체크박스처럼 활용가능 (매체마다 결정등급의 값이 다르지만, 그렇다고 name을 마음대로 바꿔버리면 이 함수를 사용하지 못함.)
			function grade_radio_Chk(grade){
				if($("#grade_name_radioChk").val() == grade){
					$("input:radio[name='detailSearch_grade_name'][value='" + grade + "']").prop("checked",false);
					$("#grade_name_radioChk").val("");
				} else{
					$("#grade_name_radioChk").val(grade);
				}
			}
			//내용정보표시항목 라디오 버튼을 체크박스처럼 활용가능 (매체마다 내용정보표시항목의 값이 다르지만, 그렇다고 name을 마음대로 바꿔버리면 이 함수를 사용하지 못함.)
			function rtstd_radio_Chk(num){
				if($("#rt_std_radioChk").val() == num){
					$("input:radio[name='detailSearch_rt_std_name'][value='" + num + "']").prop("checked",false);
					$("#rt_std_radioChk").val("");
				} else{
					$("#rt_std_radioChk").val(num);
				}
			}
			
			
			
			
			//상세보기 뷰 페이지
			function openViewOldMGT2(rcv_no,orseq){
				alert(11);
				 window.open('/view/view01/view_mgt_ajax.do?searchyn=Y&rcv_no='+rcv_no +'&orseq='+orseq, '종합검색',   'width=997,height=740,resizable=yes,scrollbars=yes,left=50,top=50,status=no');   
			}
			
			
			
			
			
			
			/*********정의한 function들 *********/
			
			//////////////////////////////////////////////////////////////20221228상세검색 다양화 시작
			function detail_remove_chk(){
				$("#detail_insert").empty();
				$("#detail_kindChk").val("common");
			}
			
			//영화,비디오에 관한 상세검색
			function detail_movie01_video01_chk(){
				detail_remove_chk();
				//상세검색의 종류가 무엇인지 체크하는 hidden 변수 kindChk이다.   왜 ? visibleChk가 Y일때, 상세보기 검색 조건을 조회할 것인데, visibleChk가 Y여도 ,상세보기 체크한 종류에 따라 로직이 나뉘어야 하기 떄문이다. 
				$("#detail_kindChk").val("movie01_video01");
				$("#detail_insert").append(`<div class="gr-search-box">
			                <p class="tit">결정등급</p>
			                <div id="select-ym" class="select-list">
			                  <ul class="actRow">
			                    <li><input type="radio" id="detailSearch_grade_name01" class="" name="detailSearch_grade_name" value="All"   onclick="grade_radio_Chk('All');" ><label for="detailSearch_grade_name01"> <span class="actRow_all">전체관람가</span></label></li>
			                    <li><input type="radio" id="detailSearch_grade_name02" class="" name="detailSearch_grade_name" value="12old" onclick="grade_radio_Chk('12old');"><label for="detailSearch_grade_name02"> <span class="actRow_12">12세이상관람가</span></label></li>
			                    <li><input type="radio" id="detailSearch_grade_name03" class="" name="detailSearch_grade_name" value="15old" onclick="grade_radio_Chk('15old');"><label for="detailSearch_grade_name03"> <span class="actRow_15">15세이상관람가</span></label></li>
			                    <li><input type="radio" id="detailSearch_grade_name04" class="" name="detailSearch_grade_name" value="18old" onclick="grade_radio_Chk('18old');"><label for="detailSearch_grade_name04"> <span class="actRow_18">청소년관람불가</span></label></li>
			                    <li><input type="radio" id="detailSearch_grade_name05" class="" name="detailSearch_grade_name" value="old"   onclick="grade_radio_Chk('old');"><label for="detailSearch_grade_name05"> <span class="actRow_none">제한상영가</span></label></li>
			                  </ul>
			                </div>
			             </div>
			             <div class="gr-search-box">
			             <p class="tit">내용정보표시항목</p>
			             <div id="select-ym" class="select-list">
			               <ul class="actRow">
			                 <li><input type="radio" id="detailSearch_rt_std_name01" class="" name="detailSearch_rt_std_name" value="17701" onclick="rtstd_radio_Chk('17701');" ><label for="detailSearch_rt_std_name01"> 주제</label></li>
			                 <li><input type="radio" id="detailSearch_rt_std_name02" class="" name="detailSearch_rt_std_name" value="17702" onclick="rtstd_radio_Chk('17702');" ><label for="detailSearch_rt_std_name02"> 선정성</label></li>
			                 <li><input type="radio" id="detailSearch_rt_std_name03" class="" name="detailSearch_rt_std_name" value="17703" onclick="rtstd_radio_Chk('17703');" ><label for="detailSearch_rt_std_name03"> 폭력성</label></li>
			                 <li><input type="radio" id="detailSearch_rt_std_name04" class="" name="detailSearch_rt_std_name" value="17704" onclick="rtstd_radio_Chk('17704');" ><label for="detailSearch_rt_std_name04"> 대사</label></li>
			                 <li><input type="radio" id="detailSearch_rt_std_name05" class="" name="detailSearch_rt_std_name" value="17705" onclick="rtstd_radio_Chk('17705');" ><label for="detailSearch_rt_std_name05"> 공포</label></li>
			                 <li><input type="radio" id="detailSearch_rt_std_name06" class="" name="detailSearch_rt_std_name" value="17706" onclick="rtstd_radio_Chk('17706');" ><label for="detailSearch_rt_std_name06"> 약물</label></li>
			                 <li><input type="radio" id="detailSearch_rt_std_name07" class="" name="detailSearch_rt_std_name" value="17707" onclick="rtstd_radio_Chk('17707');" ><label for="detailSearch_rt_std_name07"> 모방위험</label></li>
			               </ul>
			             </div>
			           </div>
			
			           <div class="gr-search-box cf">
			               <div class="col-4">
			               	<p class="tit">매체명</p>
			                 <div class="select-list">
			                 <!--
			                   <input type="text" name="detailSearch_medi_name_movieAndVideo" id="detailSearch_medi_name_movieAndVideo" value="${paramMap.detailSearch_medi_name_movieAndVideo}" maxlength="" placeholder="">
			                 -->  
			                   <select name="detailSearch_medi_name_movieAndVideo" id="detailSearch_medi_name_movieAndVideo" >
			                   		<option value="" selected>전체</option>
			                   		<option value="in" >국내</option>
			                   		<option value="out" >국외</option>
			                   </select>
			                 </div>
			               </div>
			               <div class="col-4">
			               	<p class="tit">제작사 국적</p>
			                 <div class="select-list">
			                   <input type="text" name="detailSearch_prodc_natnl_name_movieAndVideo" id="detailSearch_prodc_natnl_name_movieAndVideo" value="${paramMap.detailSearch_prodc_natnl_name_movieAndVideo}" maxlength="" placeholder="">
			                 </div>
			               </div>
			               <div class="col-4">
			               	<p class="tit">감독명</p>
			                 <div class="select-list">
			                   <input type="text" name="detailSearch_dire_name_movieAndVideo" id="detailSearch_dire_name_movieAndVideo" value="${paramMap.detailSearch_dire_name_movieAndVideo}" maxlength="" placeholder="">
			                 </div>
			               </div>
			               <div class="col-4">
			               	<p class="tit">영화종류</p>
			                 <div class="select-list">
			                   <input type="text" name="detailSearch_mv_asso_name_movieAndVideo" id="detailSearch_mv_asso_name_movieAndVideo" value="${paramMap.detailSearch_mv_asso_name_movieAndVideo}" maxlength="" placeholder="">
			                 </div>
			               </div>
			           </div>
			
			           
			           <div class="gr-search-box cf">
			           <div class="col-4">
			           <p class="tit">주연명</p>
			             <div class="select-list">
			               <input type="text" name="detailSearch_leada_name" id="detailSearch_leada_name" value="${paramMap.detailSearch_leada_name}" maxlength="" placeholder="">
			             </div>
			           </div>
			             <div class="col-4">
			             <p class="tit">희망등급</p>
			             <div class="select-list">
			               <select name="detailSearch_hope_grade_name" id="detailSearch_hope_grade_name">
			                   <option value="" selected>선택</option>
			                   <option value="All">전체관람가</option>
			                   <option value="12old">12세이상관람가</option>
			                   <option value="15old">15세이상관람가</option>
			                   <option value="18old">청소년관람불가</option>
			               </select>
			             </div>  
			              </div>                  
			                <div class="col-4">
				                    <p class="tit">결정의견</p>
				                    <div class="select-list">
				                      <input type="text" name="detailSearch_deter_opin_name" id="detailSearch_deter_opin_name" value="${paramMap.detailSearch_deter_opin_name}" maxlength="" placeholder="">
				                    </div>
				            </div>       
				            <div class="col-4">
			               			<p class="tit">등급분류번호</p>
					                <div class="select-list">
					                   <input type="text" name="detailSearch_rt_no_movieAndVideo" id="detailSearch_rt_no_movieAndVideo" value="${paramMap.detailSearch_rt_no_movieAndVideo}" maxlength="" placeholder="">
			                 		</div>
			                </div>
			           </div>
			           
			           <div class="gr-search-box cf">
					           <div class="col-4" style="width:75%;">
					                 <p class="tit">작품내용(줄거리)</p>
					                 <div class="select-list">
					                   <input type="text" style="max-width:810px; width:810px;" name="detailSearch_work_cont" id="detailSearch_work_cont" value="${paramMap.detailSearch_work_cont}" maxlength="" placeholder="">
					                 </div>
					           </div>     
			                 
          				</div>
          				<div class="gr-search-box cf">
		          				<div class="col-4" style="width:75%;">
					                 <p class="tit">결정사유</p>
					             <div class="select-list">
					                   <input type="text" style="max-width:810px; width:810px;" name="detailSearch_deter_rsn_movieAndVideo" id="detailSearch_deter_rsn_movieAndVideo" value="${paramMap.detailSearch_deter_rsn_movieAndVideo}" maxlength="" placeholder="">
				                 </div>
		            	</div>       
     					</div>`);
		}
			
			
			//광고물에 관한 상세검색
			function detail_ad01_chk(){
				detail_remove_chk();
				$("#detail_kindChk").val("ad01");
				$("#detail_insert").append(`<div class="gr-search-box">
                <p class="tit">결정등급</p>
                <div id="select-ym" class="select-list">
                  <ul class="actRow">
                    <li><input type="radio" id="detailSearch_grade_name01" class="" name="detailSearch_grade_name" value="harmless" onclick="grade_radio_Chk('harmless');"><label for="detailSearch_grade_name01"> <span class="actRow_all">유해성없음</span></label></li>
                    <li><input type="radio" id="detailSearch_grade_name02" class="" name="detailSearch_grade_name" value="harm" onclick="grade_radio_Chk('harm');"><label for="detailSearch_grade_name02"> <span class="actRow_12">유해성있음</span></label></li>
                    <li><input type="radio" id="detailSearch_grade_name03" class="" name="detailSearch_grade_name" value="All" onclick="grade_radio_Chk('All');"><label for="detailSearch_grade_name03"> <span class="actRow_15">전체관람가</span></label></li>
                    <li><input type="radio" id="detailSearch_grade_name04" class="" name="detailSearch_grade_name" value="18old" onclick="grade_radio_Chk('18old');"><label for="detailSearch_grade_name04"> <span class="actRow_18">청소년관람불가</span></label></li>
                  </ul>
                </div>
               </div>
      
              
              <div class="gr-search-box cf">
              <div class="col-4">
                    <p class="tit">매체명</p>
                    <div class="select-list">
                      <input type="text" name="detailSearch_medi_name_ad" id="detailSearch_medi_name_ad" value="${paramMap.detailSearch_medi_name_ad}" maxlength="" placeholder="">
                    </div>
              </div>
              <div class="col-4">  
                    <p class="tit">종류</p>
                    <div class="select-list">
                      <input type="text" name="detailSearch_kind_name_ad" id="detailSearch_kind_name_ad" value="${paramMap.detailSearch_kind_name_ad}" maxlength="" placeholder="">
                    </div>
              </div>    
               <div class="col-4"> 
                    <p class="tit">결정의견</p>
                    <div class="select-list">
                      <input type="text" name="detailSearch_deter_opin_name_ad" id="detailSearch_deter_opin_name_ad" value="${paramMap.detailSearch_deter_opin_name_ad}" maxlength="" placeholder="">
                    </div>
               </div>
               	<div class="col-4">
		      			<p class="tit">등급분류번호</p>
			               	<div class="select-list">
			                  <input type="text" name="detailSearch_rt_no_ad" id="detailSearch_rt_no_ad" value="${paramMap.detailSearch_rt_no_ad}" maxlength="" placeholder="">
			        		</div>
				</div>`);
			}
			
			//공연추천 에 관한 상세검색
			function detail_perform01_chk(){
				detail_remove_chk();
				$("#detail_kindChk").val("perform01");
				$("#detail_insert").append(` <div class="gr-search-box">
								 <p class="tit">결정등급</p>
					                <div id="select-ym" class="select-list">
					                  <ul class="actRow">
					                    <li><input type="radio" id="detailSearch_grade_name01" class="" name="detailSearch_grade_name" value="good" onclick="grade_radio_Chk('good');" ><label for="detailSearch_grade_name01"> <span class="actRow_all">추천</span></label></li>
					                    <li><input type="radio" id="detailSearch_grade_name02" class="" name="detailSearch_grade_name" value="bad" onclick="grade_radio_Chk('bad');" ><label for="detailSearch_grade_name02"> <span class="actRow_12">미추천</span></label></li>
					                  </ul>
					                </div>
					               </div>
					      
									<!-- 필요없음 
					               <div class="gr-search-box">
						             <p class="tit">내용정보표시항목</p>
						             <div id="select-ym" class="select-list">
						               <ul class="actRow">
						                 <li><input type="radio" id="detailSearch_rt_std_name01" class="" name="detailSearch_rt_std_name" value="17701" onclick="rtstd_radio_Chk('17701');" ><label for="detailSearch_rt_std_name01"> 주제</label></li>
						                 <li><input type="radio" id="detailSearch_rt_std_name02" class="" name="detailSearch_rt_std_name" value="17702" onclick="rtstd_radio_Chk('17702');" ><label for="detailSearch_rt_std_name02"> 선정성</label></li>
						                 <li><input type="radio" id="detailSearch_rt_std_name03" class="" name="detailSearch_rt_std_name" value="17703" onclick="rtstd_radio_Chk('17703');" ><label for="detailSearch_rt_std_name03"> 폭력성</label></li>
						                 <li><input type="radio" id="detailSearch_rt_std_name04" class="" name="detailSearch_rt_std_name" value="17704" onclick="rtstd_radio_Chk('17704');" ><label for="detailSearch_rt_std_name04"> 대사</label></li>
						                 <li><input type="radio" id="detailSearch_rt_std_name05" class="" name="detailSearch_rt_std_name" value="17705" onclick="rtstd_radio_Chk('17705');" ><label for="detailSearch_rt_std_name05"> 공포</label></li>
						                 <li><input type="radio" id="detailSearch_rt_std_name06" class="" name="detailSearch_rt_std_name" value="17706" onclick="rtstd_radio_Chk('17706');" ><label for="detailSearch_rt_std_name06"> 약물</label></li>
						                 <li><input type="radio" id="detailSearch_rt_std_name07" class="" name="detailSearch_rt_std_name" value="17707" onclick="rtstd_radio_Chk('17707');" ><label for="detailSearch_rt_std_name07"> 모방위험</label></li>
						               </ul>
						             </div>
						           </div>
						           -->
						           
					              <div class="gr-search-box cf">
					              <div class="col-4">
					                    <p class="tit">외국인 인원수</p>
					                    <div class="select-list">
					                      <input type="text" name="detailSearch_pfm_pnum" id="detailSearch_pfm_pnum" value="${paramMap.detailSearch_pfm_pnum}" maxlength="" placeholder="">
					                    </div>
					              </div>  
					              <div class="col-4">
					                    <p class="tit">외국인 국적</p>
					                    <div class="select-list">
					                      <input type="text" name="detailSearch_frgnr_natnl_name_perform" id="detailSearch_frgnr_natnl_name_perform" value="${paramMap.detailSearch_frgnr_natnl_name_perform}" maxlength="" placeholder="">
					                    </div>
					              </div>       
					              <div class="col-4">
					                    <p class="tit">공연장소명</p>
					                    <div class="select-list">
					                      <input type="text" name="detailSearch_pfm_place_name_perform" id="detailSearch_pfm_place_name_perform" value="${paramMap.detailSearch_pfm_place_name_perform}" maxlength="" placeholder="">
					                    </div>
					              </div>
					              <div class="col-4">
				               			<p class="tit">등급분류번호</p>
						                <div class="select-list">
						                   <input type="text" name="detailSearch_rt_no_perform" id="detailSearch_rt_no_perform" value="${paramMap.detailSearch_rt_no_perform}" maxlength="" placeholder="">
				                 		</div>
			                	  </div>
					              </div>
					              
					              <div class="gr-search-box cf">
					              <!-- 관련데이터가 없다.
					              <div class="col-4">
					                    <p class="tit">핵심유해사유</p>
					                    <div class="select-list">
					                      <input type="text" name="detailSearch_core_harm_rsn" id="detailSearch_core_harm_rsn" value="${paramMap.detailSearch_core_harm_rsn}" maxlength="" placeholder="">
					                    </div>
					              </div>
					              -->
					              <!-- 관련데이터가 없다.
					              <div class="col-4">
						             <p class="tit">희망등급</p>
						             <div class="select-list">
						               <select name="detailSearch_hope_grade_name" id="detailSearch_hope_grade_name">
						                   <option value="" selected>전체</option>
						                   <option value="a">대분류</option>
						                   <option value="b">중분류</option>
						                   <option value="c">소분류</option>
						               </select>
						             </div>  
					              </div>   
					              -->
					             <div class="col-4">
					                    <p class="tit">연소자 유해성 여부</p>
					                    <div class="select-list">
					                      <select name="detailSearch_minor_malef_yn" id="detailSearch_minor_malef_yn">
					                          <option value="" selected>전체</option>
					                          <option value="harmless" >무해</option>
					                          <option value="harm" >유해</option>
					                      </select>
					                    </div>
					              </div>     
					             <div class="col-4">      
				                    <p class="tit">종류</p>
				                    <div class="select-list">
				                      <select name="detailSearch_kind_name" id="detailSearch_kind_name">
				                          <option value="" selected>전체</option>
				                          <option value="a">가무</option>
				                          <option value="b">곡예마술</option>
				                          <option value="c">노래 및 연주</option>
				                          <option value="d">뮤지컬</option>
				                          <option value="e">발레무용</option>
				                          <option value="f">연극</option>
				                          <option value="g">연주</option>
				                          <option value="h">오페라</option>
				                          <option value="i">음악회</option>
				                          <option value="j">인형극</option>
				                          <option value="k">콘서트</option>
				                          <option value="l">합창</option>
				                          <option value="m">기타</option>
				                      </select>
				                    </div>
				              	 </div>   
					              <div class="col-4" style="width:50%">      
					                    <p class="tit">결정사유</p>
					                    <div class="select-list">
					                      <input type="text" style="max-width: 490px; width:490px;" name="detailSearch_deter_rsn_perform" id="detailSearch_deter_rsn_perform" value="${paramMap.detailSearch_deter_rsn_perform}" maxlength="" placeholder="">
					                    </div>
					              </div>      
					              </div>
					              
					              <div class="gr-search-box cf">
					                    <p class="tit">계약 시작 일자</p>
					                    <div class="select-list">
					                      <input type="date" class="calendar" name="detailSearch_contr_start_date_start" id="detailSearch_contr_start_date_start" value="${paramMap.detailSearch_contr_start_date_start}" maxlength="8" title="시작 기간 입력 (예20200101)" onkeypress="javascript:if(event.keyCode==13){goSearch(); return;}" placeholder="YYYY-MM-DD">
					                      <span><mark>~</mark></span>
					                      <input type="date" class="calendar" name="detailSearch_contr_start_date_end" id="detailSearch_contr_start_date_end" value="${paramMap.detailSearch_contr_start_date_end}" maxlength="8" title="종료 기간 입력 (예20200101)" onkeypress="javascript:if(event.keyCode==13){goSearch(); return;}" placeholder="YYYY-MM-DD">
					                    </div>     
					                    <p class="tit">계약 종료 일자</p>
					                    <div class="select-list">
					                      <input type="date" class="calendar" name="detailSearch_contr_end_date_start" id="detailSearch_contr_end_date_start" value="${paramMap.detailSearch_contr_end_date_start}" maxlength="8" title="시작 기간 입력 (예20200101)" onkeypress="javascript:if(event.keyCode==13){goSearch(); return;}" placeholder="YYYY-MM-DD">
					                      <span><mark>~</mark></span>
					                      <input type="date" class="calendar" name="detailSearch_contr_end_date_end" id="detailSearch_contr_end_date_end" value="${paramMap.detailSearch_contr_end_date_end}" maxlength="8" title="종료 기간 입력 (예20200101)" onkeypress="javascript:if(event.keyCode==13){goSearch(); return;}" placeholder="YYYY-MM-DD">
					                    </div>    
             					 </div> `);
			}
			
			///////////////////////////////////////////상세검색 다양화 끝
			
			
			
			///////////////////////////카테고리 체크 시 이벤트 시작!!
			function category_checkbox(){
				
				//영화 또는 비디오 또는 영화,비디오만 체크 될 때 발생
				if(($("#sectionGubun01").prop("checked") || $("#sectionGubun02").prop("checked")) && 
						!($("#sectionGubun03").prop("checked") || $("#sectionGubun04").prop("checked") || $("#sectionGubun05").prop("checked") || $("#sectionGubun06").prop("checked") || $("#sectionGubun07").prop("checked") || $("#sectionGubun08").prop("checked"))){
				     
					detail_remove_chk();
					detail_movie01_video01_chk();
					 
				} else if($("#sectionGubun03").prop("checked") && 
				// 광고물만 체크 될 때 발생
						!($("#sectionGubun01").prop("checked") || $("#sectionGubun02").prop("checked") || $("#sectionGubun04").prop("checked") || $("#sectionGubun05").prop("checked") || $("#sectionGubun06").prop("checked") || $("#sectionGubun07").prop("checked") || $("#sectionGubun08").prop("checked"))){
				
					detail_remove_chk();
					detail_ad01_chk();
				
				} else if($("#sectionGubun04").prop("checked") && 
				// 공연추천만 체크 될 때 발생
						!($("#sectionGubun01").prop("checked") || $("#sectionGubun02").prop("checked") || $("#sectionGubun03").prop("checked") || $("#sectionGubun05").prop("checked") || $("#sectionGubun06").prop("checked") || $("#sectionGubun07").prop("checked") || $("#sectionGubun08").prop("checked"))){
				     
					detail_remove_chk();
					detail_perform01_chk();
					
				} else{
					detail_remove_chk();
				}
			}
			///////////////////////////카테고리 체크 시 이벤트 끝!!	
			
			
			
			
			
			
			// 20221215  시작
			// 20230228 상세보기 초기화 버튼이 생기면서, 기존 '상세검색 닫기', '상세검색' 버튼에 대한 기능 재정립 --> 초기화 기능이 필요없어짐. 
 			function detailOn(e) {
				if($("#divDetail").is(':visible')){
					
					$("#divDetail").hide();
					$("#detail_visibleChk").val("N");
					
					
					
					/* 20230228 상세검색 초기화버튼이 생김으로써 기존 기능 재정립
					$("#divDetail").hide();
					//$("input:radio[name='detailSearch_radio']").prop("checked", false);
					//$("#detailSearch_grade")
					//$("input:radio[name='detailSearch_resultLine']").prop("checked", false);
					$("#detailSearch_aplc").val("");	
					$("#detailSearch_oper").prop("checked", true);
					$("#detailSearch_resultLine01").prop("checked", true);
					
					//상세보기 일자 초기화
					$("#period_start").val("");
					$("#period_end").val("");
					
					$("#detail_visibleChk").val("N");
				
					
					
					//일자 초기화
					$("select[name=detailSearch_date]").val("gradedate");
					
					//일자 - 일수 초기화
					$("input:radio[name='detailSearch_day']").prop("checked", false);
					*/
				}else{
					$("#divDetail").show();
					$("#detail_visibleChk").val("Y");
				}

			}

			//20230228 끝
			//20221215끝
			
			//20230228 상세검색 초기화 버튼 함수 정의 시작
			function detailOnReset(e){
				
				// 오로지 데이터 초기화에만 집중 
				
				
				
				
				///// 공통기능 초기화 시작 /////
				
				
					//$("input:radio[name='detailSearch_radio']").prop("checked", false);
					//$("#detailSearch_grade")
					//$("input:radio[name='detailSearch_resultLine']").prop("checked", false);
					$("#detailSearch_aplc").val("");	
					$("#detailSearch_oper").prop("checked", true);
					$("#detailSearch_resultLine01").prop("checked", true);
					
					//상세보기 일자 초기화
					$("#period_start").val("");
					$("#period_end").val("");
					
				
					
					
					//일자 초기화
					$("select[name=detailSearch_date]").val("gradedate");
					
					/*
					//일자 - 일수 초기화
					$("input:radio[name='detailSearch_day']").prop("checked", false);
					*/
					
					//일자 : '전체' 버튼 활성화
					$("input:radio[name='detailSearch_day']").prop("checked", false);
					$("#1st").prop("checked", true);
					//$("input:radio[name='detailSearch_day'][value='1st']").prop("checked", true);
					//$("input:radio[name='detailSearch_day'][value='1st']").attr("checked", "checked");
				///// 공통기능 초기화 끝  /////
				
				
				/// 매체별 조건 초기화  시작///
				
				/// 매체별 조건 초기화  끝///
				
				
				
			} 
			//20230228 상세검색 초기화 버튼 함수 정의 끝
			
			
			

			
			//20221219 setPeriod 일자 라디오버튼 클릭 시 이벤트
			function setPeriod(clickbtn){
				
				const d = new Date();
				const year = d.getFullYear(); 
				const month = d.getMonth(); 
				const day = d.getDate();
				
				
				
				let today = new Date(year, month, day).toLocaleDateString();
				let aweek = new Date(year, month, day - 7).toLocaleDateString();
				let amonth = new Date(year, month - 1, day).toLocaleDateString();
				let ayear = new Date(year - 1, month, day).toLocaleDateString();
				
				
				var todayArray = today.split(". ");
				var aweekArray = aweek.split(". ");
				var amonthArray = amonth.split(". ");
				var ayearArray = ayear.split(". ");
				
				var period_start = "";
				var period_end = "";
				if(clickbtn == 'today'){		
					period_start = todayArray[0] + "-" +  todayArray[1].padStart(2,"0") + "-" + todayArray[2].substr(0, todayArray[2].indexOf(".")).padStart(2,"0");
					$("#period_start").val(period_start);
					
					period_end = todayArray[0] + "-" +  todayArray[1].padStart(2,"0") + "-" + todayArray[2].substr(0, todayArray[2].indexOf(".")).padStart(2,"0");
					$("#period_end").val(period_end);	
				}
				if(clickbtn == 'aweek'){
					period_start = aweekArray[0] + "-" + aweekArray[1].padStart(2,"0") + "-" + aweekArray[2].substr(0, aweekArray[2].indexOf(".")).padStart(2,"0");
					$("#period_start").val(period_start);
					
					period_end = todayArray[0] + "-" +  todayArray[1].padStart(2,"0") + "-" + todayArray[2].substr(0, todayArray[2].indexOf(".")).padStart(2,"0");
					$("#period_end").val(period_end);
				}
				if(clickbtn == 'amonth'){
					period_start = amonthArray[0] + "-" + amonthArray[1].padStart(2,"0") + "-" + amonthArray[2].substr(0, amonthArray[2].indexOf(".")).padStart(2,"0");
					$("#period_start").val(period_start);
					
					period_end = todayArray[0] + "-" +  todayArray[1].padStart(2,"0") + "-" + todayArray[2].substr(0, todayArray[2].indexOf(".")).padStart(2,"0");
					$("#period_end").val(period_end);
				}
				if(clickbtn == 'ayear'){
					period_start = ayearArray[0] + "-" + ayearArray[1].padStart(2,"0") + "-" + ayearArray[2].substr(0, ayearArray[2].indexOf(".")).padStart(2,"0");
					$("#period_start").val(period_start);
					
					period_end = todayArray[0] + "-" +  todayArray[1].padStart(2,"0") + "-" + todayArray[2].substr(0, todayArray[2].indexOf(".")).padStart(2,"0");
					$("#period_end").val(period_end);
				}
				if(clickbtn == 'setDate'){
					$("#period_start").val("");
					$("#period_end").val("");
				}
				
				
			} // setPeriod
			
			//20221220 일자가 변경된다면 "전체"버튼이 클릭 되도록
			function chgDate(){
				$("#1st").prop("checked", true);
			} // chgDate
			
			
			
			
			//20221223 결과 더보기 클릭 시 페이징 처리가 되는 페이지로 이동
			function resultPaging(ResultChk){
				$("#cPage").val(1);
				$("#resultChk").val(ResultChk);
				
				
				
				//20221229 결과 더보기 클릭 시, 해당 결과의 매체의 카테고리를 체크박스에 체크해준다.
				if(ResultChk == 'movie01'){
					
  
					$("#sectionGubun02").prop("checked", false);
					$("#sectionGubun03").prop("checked", false);
					$("#sectionGubun04").prop("checked", false);
					$("#sectionGubun05").prop("checked", false);
					$("#sectionGubun06").prop("checked", false);
					$("#sectionGubun07").prop("checked", false);
					$("#sectionGubun08").prop("checked", false);
					
					
					
					
					$("#sectionGubun01").prop("checked", true);
					
				} else if(ResultChk == 'video01'){
					
					$("#sectionGubun01").prop("checked", false);
					$("#sectionGubun03").prop("checked", false);
					$("#sectionGubun04").prop("checked", false);
					$("#sectionGubun05").prop("checked", false);
					$("#sectionGubun06").prop("checked", false);
					$("#sectionGubun07").prop("checked", false);
					$("#sectionGubun08").prop("checked", false);
					
					
					$("#sectionGubun02").prop("checked", true);
				} else if(ResultChk == 'ad01'){
					
					$("#sectionGubun01").prop("checked", false);
					$("#sectionGubun02").prop("checked", false);
					$("#sectionGubun04").prop("checked", false);
					$("#sectionGubun05").prop("checked", false);
					$("#sectionGubun06").prop("checked", false);
					$("#sectionGubun07").prop("checked", false);
					$("#sectionGubun08").prop("checked", false);
					
					
					$("#sectionGubun03").prop("checked", true);
				} else if(ResultChk == 'perform01'){
					$("#sectionGubun01").prop("checked", false);
					$("#sectionGubun02").prop("checked", false);
					$("#sectionGubun03").prop("checked", false);
					$("#sectionGubun05").prop("checked", false);
					$("#sectionGubun06").prop("checked", false);
					$("#sectionGubun07").prop("checked", false);
					$("#sectionGubun08").prop("checked", false);
					
					$("#sectionGubun04").prop("checked", true);
				} else if(ResultChk == 'opin01'){
					
					$("#sectionGubun01").prop("checked", false);
					$("#sectionGubun02").prop("checked", false);
					$("#sectionGubun03").prop("checked", false);
					$("#sectionGubun04").prop("checked", false);
					$("#sectionGubun06").prop("checked", false);
					$("#sectionGubun07").prop("checked", false);
					$("#sectionGubun08").prop("checked", false);
					
					
					$("#sectionGubun05").prop("checked", true);
				} else if(ResultChk == 'img01'){
					
					$("#sectionGubun01").prop("checked", false);
					$("#sectionGubun02").prop("checked", false);
					$("#sectionGubun03").prop("checked", false);
					$("#sectionGubun04").prop("checked", false);
					$("#sectionGubun05").prop("checked", false);
					$("#sectionGubun07").prop("checked", false);
					$("#sectionGubun08").prop("checked", false);
					
					
					$("#sectionGubun06").prop("checked", true);
				} else if(ResultChk == 'file01'){
					
					$("#sectionGubun01").prop("checked", false);
					$("#sectionGubun02").prop("checked", false);
					$("#sectionGubun03").prop("checked", false);
					$("#sectionGubun04").prop("checked", false);
					$("#sectionGubun05").prop("checked", false);
					$("#sectionGubun06").prop("checked", false);
					$("#sectionGubun08").prop("checked", false);
					
					
					
					
					$("#sectionGubun07").prop("checked", true);
				} else if(ResultChk == 'moni01'){
					
					
					$("#sectionGubun01").prop("checked", false);
					$("#sectionGubun02").prop("checked", false);
					$("#sectionGubun03").prop("checked", false);
					$("#sectionGubun04").prop("checked", false);
					$("#sectionGubun05").prop("checked", false);
					$("#sectionGubun06").prop("checked", false);
					$("#sectionGubun07").prop("checked", false);
					
					
					
					
					$("#sectionGubun08").prop("checked", true);
				}
			   ///////////////////////////////////////결과 더보기 클릭 시, 해당 결과의 매체의 카테고리를 체크박스에 체크해준다. (끝)//////////////////////////////////////////////	
			
			   	// [결과 더 보기] 클릭 시, 기존의 검색데이터에 맞게 초기화세팅
				modifiedReset();
			   
			   
				
				$('#fm')
				.attr(
						{
							action : '/search/SearchEnginePaging.do',
							method : 'post',
							target : '_self'
						}).submit();
				
				$("#loading").show();
				//URL을 넘겨주고, 그것을 받아서 단독으로 페이징처리함
				// 해당 매체의 [결과 더보기] 클릭 시, 해당 매체가 체크되고, 페이징도 추가로 처리하는로직을 구성하면됨.
				
			}
			
			//20221223 결과 더보기 클릭 시 페이징 처리가 되는 페이지로 이동
			
			//20221226 사이드메뉴명 클릭 시 페이징 처리가 되는 페이지로 이동
			function resultSidePaging(ResultChk, sidetotalUrl, sideMenuNm){
				//ResultChk 는 movie01Side ad01Side..
				//sidetotalUrl 해당 메뉴 Url을 보내준다.
				//Point : resultPaging함수를 참고하자. 
				
				//2022 12 27 사이드메뉴 클릭 시 정렬순[정확도순,가나다순...]  초기화
				$("#orderValue").val("orderRelevance");
			
				$("#sideMenuNm").val(sideMenuNm);
				$("#cPage").val(1);
				$("#resultChk").val(ResultChk);
				
				//resultSidePaging은 사이드 메뉴 클릭 시 작동하는 함수이다. 
				$("#sidetotalUrl").val(sidetotalUrl);
				
				//tabClick()함수에 넣은 것과 동일하게, 사이드 메뉴 클릭을 체크함으로써, 사이드 메뉴를 클릭하여 해당 페이지에 접속하면, 그로인해 결과내 재검색에 대한 멘트가 바뀌면 안된다.
				$("#sideMenuClickChk").val("1");
				
				////////////////////////////////////////////////////////////////////////////사이드메뉴명 클릭 시, 해당 결과의 매체의 카테고리를 체크박스에 체크해준다.
				if(ResultChk == 'movie01Side'){
					

					$("#sectionGubun02").prop("checked", false);
					$("#sectionGubun03").prop("checked", false);
					$("#sectionGubun04").prop("checked", false);
					$("#sectionGubun05").prop("checked", false);
					$("#sectionGubun06").prop("checked", false);
					$("#sectionGubun07").prop("checked", false);
					$("#sectionGubun08").prop("checked", false);
					
					
					
					
					$("#sectionGubun01").prop("checked", true);
					
				} else if(ResultChk == 'video01Side'){
					
					$("#sectionGubun01").prop("checked", false);
					$("#sectionGubun03").prop("checked", false);
					$("#sectionGubun04").prop("checked", false);
					$("#sectionGubun05").prop("checked", false);
					$("#sectionGubun06").prop("checked", false);
					$("#sectionGubun07").prop("checked", false);
					$("#sectionGubun08").prop("checked", false);
					
					
					$("#sectionGubun02").prop("checked", true);
				} else if(ResultChk == 'ad01Side'){
					
					$("#sectionGubun01").prop("checked", false);
					$("#sectionGubun02").prop("checked", false);
					$("#sectionGubun04").prop("checked", false);
					$("#sectionGubun05").prop("checked", false);
					$("#sectionGubun06").prop("checked", false);
					$("#sectionGubun07").prop("checked", false);
					$("#sectionGubun08").prop("checked", false);
					
					
					$("#sectionGubun03").prop("checked", true);
				} else if(ResultChk == 'perform01Side'){
					$("#sectionGubun01").prop("checked", false);
					$("#sectionGubun02").prop("checked", false);
					$("#sectionGubun03").prop("checked", false);
					$("#sectionGubun05").prop("checked", false);
					$("#sectionGubun06").prop("checked", false);
					$("#sectionGubun07").prop("checked", false);
					$("#sectionGubun08").prop("checked", false);
					
					$("#sectionGubun04").prop("checked", true);
				} else if(ResultChk == 'opin01Side'){
					
					$("#sectionGubun01").prop("checked", false);
					$("#sectionGubun02").prop("checked", false);
					$("#sectionGubun03").prop("checked", false);
					$("#sectionGubun04").prop("checked", false);
					$("#sectionGubun06").prop("checked", false);
					$("#sectionGubun07").prop("checked", false);
					$("#sectionGubun08").prop("checked", false);
					
					
					$("#sectionGubun05").prop("checked", true);
				} else if(ResultChk == 'img01Side'){
					
					$("#sectionGubun01").prop("checked", false);
					$("#sectionGubun02").prop("checked", false);
					$("#sectionGubun03").prop("checked", false);
					$("#sectionGubun04").prop("checked", false);
					$("#sectionGubun05").prop("checked", false);
					$("#sectionGubun07").prop("checked", false);
					$("#sectionGubun08").prop("checked", false);
					
					
					$("#sectionGubun06").prop("checked", true);
				} else if(ResultChk == 'file01Side'){
					
					$("#sectionGubun01").prop("checked", false);
					$("#sectionGubun02").prop("checked", false);
					$("#sectionGubun03").prop("checked", false);
					$("#sectionGubun04").prop("checked", false);
					$("#sectionGubun05").prop("checked", false);
					$("#sectionGubun06").prop("checked", false);
					$("#sectionGubun08").prop("checked", false);
					
					
					
					
					$("#sectionGubun07").prop("checked", true);
				} else if(ResultChk == 'moni01Side'){
					
					
					$("#sectionGubun01").prop("checked", false);
					$("#sectionGubun02").prop("checked", false);
					$("#sectionGubun03").prop("checked", false);
					$("#sectionGubun04").prop("checked", false);
					$("#sectionGubun05").prop("checked", false);
					$("#sectionGubun06").prop("checked", false);
					$("#sectionGubun07").prop("checked", false);
					
					
					
					
					$("#sectionGubun08").prop("checked", true);
				}
			   ///////////////////////////////////////사이드메뉴명 클릭 시, 해당 결과의 매체의 카테고리를 체크박스에 체크해준다. (끝)//////////////////////////////////////////////
				
				//사이드 메뉴명 클릭 시, 기존 검색데이터에 맞게 초기화세팅
				modifiedReset();
				
			
				
				
				
				
				$('#fm')
				.attr(
						{
							action : '/search/SearchEnginePaging.do',
							method : 'post',
							target : '_self'
						}).submit();
				$("#loading").show();
				
				
			}
			
			
			//기존 값으로 세팅해준다.
			function modifiedReset(){
				
				//기본적으로 검색 결과에 영향을 미치는 요소들
				//1. 검색창의 기존값 전달
				$("#searchStr").val('${paramMap.searchStr}');
				//2. 상세검색창 띄웠는지 확인유무 ------------->     상세검색창 띄웠는지 확인유무도 기존값 전달 (상세보기창을 띄우지 않고 검색했는데, 사이드명 클릭 시에는 상세보기창을 띄우고 누를수도 있으니)
				$("#detail_visibleChk").val("${paramMap.detail_visibleChk}");
				//3. 검색창 아래에 있는 카테고리 체크박스의 체크유무 ----------------> 이것도 [검색]버튼을 클릭 한 값만 전달 - 검색창 밑에 존재하는 카테고리 체크박스의 체크유무이다.
				$("#detail_kindChk").val("${paramMap.detail_kindChk}");
				//4. 결과내 재검색 체크유무 (추가예정)
				if(${not empty paramMap.result_re_search_chk and paramMap.result_re_search_chk eq 'Y'}){
					$("input:checkbox[id='ins_sch']").prop("checked", true);
					$("#result_re_search_chk").val("Y");
				} else {
					$("input:checkbox[id='ins_sch']").prop("checked", false);
					$("#result_re_search_chk").val("N");
				}
				
				
				/////////////////////////////////////////페이지 변경 시 [검색을 순수하게 클릭한 데이터만] 전달하기 - 상세검색 공통조건 시작/////////////////////////////////////////
				//공통조건1 - 연산자 (radio버튼)
				    //기본 값이 있는 조건이므로 if문으로  EL표현식(★컨트롤러에서 넘어온 데이터★)이 null일때는 기본값을 체크해줘야함.
					    if(${not empty paramMap.detailSearch_radio}){
					    	$("input:radio[name='detailSearch_radio'][value='${paramMap.detailSearch_radio}']").prop("checked", true);
					    } else {
					    	$("input:radio[name='detailSearch_radio'][value='allword']").prop("checked", true);     //기존 컨트롤러에서 연산자값이 null( 즉, 선택한적이 없으면) -> 기본값 세팅
					    }
				
			    //공통조건2 - 등급분류일자, 신청일자, 접수일자 (select box, radion box, date) 혼합
				    //(1) select box 기본값이 존재하니 if문으로 처리하여 기존값이 null이면, 기본값 세팅해주기
					    if(${not empty paramMap.detailSearch_date}){
					    	$("select[name='detailSearch_date'][value='${paramMap.detailSearch_date}']").prop("selected",true);
					    } else {
					    	$("select[name='detailSearch_date'][value='gradedate']").prop("selected", true);
					    }
				    //(2) radio button [전체,오늘,1주,1달,1년] 기본값 : 오늘 1st
					    if(${not empty paramMap.detailSearch_day}){
					    	$("input:radio[name='detailSearch_day'][value='${paramMap.detailSearch_day}']").prop("checked", true);
					    } else {
					    	$("input:radio[name='detailSearch_day'][value='1st']").prop("checked", true);     //기존 컨트롤러에서 연산자값이 null( 즉, 선택한적이 없으면) -> 기본값 세팅
					    }
				    //(3) input text
						$("#period_start").val("${paramMap.period_start}");
						$("#period_end").val("${paramMap.period_end}");
				
				/*
				//공통조건3 - 결과수 (radio) 기본 값: 3줄 
				// 결과수 공통조건은 [결과 더보기] 클릭 시 또는 사이드메뉴명 클릭 시 기본값이 10줄로 변경되어야한다.						
				    if(${not empty paramMap.detailSearch_resultLine}){
				    	$("input:radio[name='detailSearch_resultLine'][value='${paramMap.detailSearch_resultLine}']").prop("checked", true);
				    } else {
				    	$("input:radio[name='detailSearch_resultLine'][value='3']").prop("checked", true);     //기존 컨트롤러에서 연산자값이 null( 즉, 선택한적이 없으면) -> 기본값 세팅
				    }
				*/
				//공통조건4 - 신청회사
					$("#detailSearch_aplc").val("${paramMap.detailSearch_aplc}");
		/////////////////////////////////////////페이지 변경 시 [검색을 순수하게 클릭한 데이터만] 전달하기 - 상세검색 공통조건 끝 /////////////////////////////////////////
		
		
		
		/////////////////////////////////////////페이지 변경 시 [검색을 순수하게 클릭한 데이터만] 전달하기 - 매체에 따른 상세검색 다양화 (조건에 따라 다름) 시작 /////////////////////////////////////////
				//visibleChk Y는 체크안해도된다 왜냐? 어차피 데이터가 넘어가도, 페이징 컨트롤러에서, visibleChk 가 Y가 아니라면, 이 데이터를 활용하지 못하기 떄문임.
				//if 영화,비디오라면 조건 추가 --> [페이지 변경 시순수하게 검색한 조건들만 보내기 위해서.]
				if($("#detail_kindChk").val() == "movie01_video01"){
					//기존 radion버튼 값이 null이면, "기존 검색"의 데이터가 수정되어도, 기존값은 null로 chcked가 되기때문에, 이후에 수정된 데이터가 영향력이 더 쎄서, 수정된 데이터가 반영되는 현상이 발생함.							
					$("input:radio[name='detailSearch_grade_name']").prop("checked",false);
					$("input:radio[name='detailSearch_grade_name'][value='${paramMap.detailSearch_grade_name}']").prop("checked", true);      // (영화비디오) 결정등급을 기존 검색결과의 값을 반영하여 컨트롤러에 넘겨준다.
					$("input:radio[name='detailSearch_rt_std_name']").prop("checked",false);
					$("input:radio[name='detailSearch_rt_std_name'][value='${paramMap.detailSearch_rt_std_name}']").prop("checked", true);    // (영화비디오) 내용정보표시항목을 기존 검색결과의 값을 반영하여 컨트롤러에 넘겨준다.
					
					// medi_name : input -> select box로 변경
					//$("#detailSearch_medi_name_movieAndVideo").val("${paramMap.detailSearch_medi_name_movieAndVideo}");						  // (영화비디오) 매체명을 기존 검색결과의 값을 반영하여 컨트롤러에 넘겨준다.
					$("select[name='detailSearch_medi_name_movieAndVideo']").val("${paramMap.detailSearch_medi_name_movieAndVideo}");			  // (영화비디오) 매체명
					
					$("#detailSearch_prodc_natnl_name_movieAndVideo").val("${paramMap.detailSearch_prodc_natnl_name_movieAndVideo}");		  // (영화비디오) 제작사 국적
					$("#detailSearch_dire_name_movieAndVideo").val("${paramMap.detailSearch_dire_name_movieAndVideo}");						  // (영화비디오) 감독명
					$("#detailSearch_mv_asso_name_movieAndVideo").val("${paramMap.detailSearch_mv_asso_name_movieAndVideo}");                 // (영화비디오) 영화종류
					$("#detailSearch_leada_name").val("${paramMap.detailSearch_leada_name}");												  // (영화비디오) 주연명
					$("select[name='detailSearch_hope_grade_name']").val('${paramMap.detailSearch_hope_grade_name}');
					$("#detailSearch_deter_opin_name").val("${paramMap.detailSearch_deter_opin_name}");
					$("#detailSearch_work_cont").val("${paramMap.detailSearch_work_cont}");
					$("#detailSearch_deter_rsn_movieAndVideo").val("${paramMap.detailSearch_deter_rsn_movieAndVideo}");
					
					//0207 등급분류번호 추가
					$("#detailSearch_rt_no_movieAndVideo").val("${paramMap.detailSearch_rt_no_movieAndVideo}");
					
					
				}
				//if 광고물이라면 조건 추가 --> 페이지 변경 시 순수하게 검색한 조건들만 보내기 위해서.]
				if($("#detail_kindChk").val() == "ad01"){
					$("input:radio[name='detailSearch_grade_name']").prop("checked",false);
					$("input:radio[name='detailSearch_grade_name'][value='${paramMap.detailSearch_grade_name}']").prop("checked", true);
					$("#detailSearch_medi_name_ad").val("${paramMap.detailSearch_medi_name_ad}");
					$("#detailSearch_kind_name_ad").val("${paramMap.detailSearch_kind_name_ad}");
					$("#detailSearch_deter_opin_name_ad").val("${paramMap.detailSearch_deter_opin_name_ad}");
					
					
					//0207 등급분류번호 추가
					$("#detailSearch_rt_no_ad").val("${paramMap.detailSearch_rt_no_ad}");
					
					
				}
				//if 공연추천이라면 조건 추가 --> 페이지 변경 시 순수하게 검색한 조건들만 보내기 위해서.]
				if($("#detail_kindChk").val() == "perform01"){
					//체크는 기존검색 시 체크되어있던 결과로 보내자(검색을 하지않고 바꾼 라디오버튼은 초기화하기 위해서)
					$("input:radio[name='detailSearch_grade_name']").prop("checked",false);
					$("input:radio[name='detailSearch_grade_name'][value='${paramMap.detailSearch_grade_name}']").prop("checked", true);
					$("#detailSearch_pfm_pnum").val("${paramMap.detailSearch_pfm_pnum}");
					$("#detailSearch_frgnr_natnl_name_perform").val("${paramMap.detailSearch_frgnr_natnl_name_perform}");
					$("#detailSearch_pfm_place_name_perform").val("${paramMap.detailSearch_pfm_place_name_perform}");
					$("select[name='detailSearch_minor_malef_yn']").val("${paramMap.detailSearch_minor_malef_yn}");
					$("select[name='detailSearch_kind_name']").val("${paramMap.detailSearch_kind_name}");
					$("#detailSearch_contr_start_date_start").val("${paramMap.detailSearch_contr_start_date_start}");
					$("#detailSearch_contr_start_date_end").val("${paramMap.detailSearch_contr_start_date_end}");
					$("#detailSearch_contr_end_date_start").val("${paramMap.detailSearch_contr_end_date_start}");
					$("#detailSearch_contr_end_date_end").val("${paramMap.detailSearch_contr_end_date_end}");
					
					//0207 등급분류번호 추가
					$("#detailSearch_rt_no_perform").val("${paramMap.detailSearch_rt_no_perform}");
				}
		/////////////////////////////////////////페이지 변경 시 [검색을 순수하게 클릭한 데이터만] 전달하기 - 매체에 따른 상세검색  다양화 (조건에 따라 다름) 끝 /////////////////////////////////////////
			}
			
			
			
			
			
			
			
			/*
			//자동완성 기능 autocomplete 시작
			$(function(){
				$("#dropMenu").hide();
				$("#searchStr").autocomplete({
					
			        source : function(request, response) {
			        	$.ajax({
	                        url: "/search/konan_autocomplete.do",
	                        data : '{ "searchStr" : "' + $("#searchStr").val() + '"}', 
			        		type : 'post',
	                        contentType : "application/json; charset=UTF-8",
	                        dataType : "json",
			        		success : function(data){
			        			//
				        		//	$(data).each(function(i){
				        		//		alert(Object.values(data[i]));
				        		//		
				        		//	});
			        			
			        			response(
		                                $.map(data, function(item) {    //json[i] 번째 에 있는게 item 임.
		                                    return {
		                                        label: Object.values(item),
		                                        value: Object.values(item)
		                               		}
		                                })
		                                );
			        			
			        		},
			        		error : function(data){
			        			console.log(data);
			        			alert("에러!");
			        		}
			        	
			        		
			        	});
			        },
			        close : function(){
			        	$("#dropMenu").hide();
			        },
			        select : function(event, ui){
			        	console.log(event);
			        	console.log(ui);
			        }
			        
				}).autocomplete( "instance" )._renderItem = function( ul, item ) {
					if($("#dropMenu").hide()){
					 	$("#dropMenu").show();
					}
					//return $("<li>").append(`<li><a href="#" onclick="javascript:alert(55);"><em>` + item.label + `</em></a></li>`).appendTo(ul);
					//return (ul).append($("<li>")).append(`<li><a href="#" onclick="javascript:alert(55);"><em>` + item.label + `</em></a></li>`).appendTo($("#sch-dropMenuList"));
					return $("<li>").append(`<li><a href="#" onclick="javascript:alert(55);"><em>` + item.label + `</em></a></li>`).appendTo(ul)
					
				};
		});
			*/
			
			
	    $(function(){
	    	$("#dropMenu").hide();
	    	$("#searchStr").on("keyup", function(){
	    		$.ajax({
                    url: "/search/konan_autocomplete.do",
                    data : '{ "searchStr" : "' + $("#searchStr").val() + '"}', 
	        		type : 'post',
                    contentType : "application/json; charset=utf-8",
                    dataType : "json",
	        		success : function(data){
	        			$("#dropMenu_ul").empty();
	        			$("#dropMenu").show();
	        			let autocompleteList = "";
	        			$(data).each(function(i){
			        		autocompleteList += "<li><a onclick='correctWords(`" +  Object.values(data[i]) + "`);' ><em id='auto" +  i  + "'>" + Object.values(data[i]) + "</em></a></li>";
			        	});
	        			$("#dropMenu_ul").append(autocompleteList);
	        		},
	        		error : function(){
	        			$("#dropMenu").hide();
	        		}
                    
	    		});
	    	});	
	    });
			
		// 자동완성 커서 이동 시작 
		/*document.addEventListener("keydown", keyDownHandler, false);
		function keyUpHandler(e) {
			console.log(e);
			if(e.key == 40 || e.key == "ArrowDown") {
				alert($("#dropMenu_ul").children().size());
				for(var i = 0 ; i<$("#dropMenu_ul").children().size(); i++){
					console.log($("#dropMenu_ul").find("#auto" + i));
					if(i==3){
						$("#searchStr").val($("#dropMenu_ul").find("#auto3").text());
					}
				}
			}
		}
		// 자동완성 커서 이동 끝  searchStr에 onkeyup="keyUpHandler(event)"; 붙여주기!
		*/
		
		//자동완성 끄기/켜기 시작!
		function autocompleteHideorShow(){
			let msg = ""
			if($("#autocompleteChk").val() == 'Y' || $("#autocompleteChk").val() == null || $("#autocompleteChk").val() == ""){
				msg = "자동완성을 끄시겠습니까?";
				if(confirm(msg)){
					$("#dropMenu_ul").hide();
					$("#autocompleteChk").val('N');
					$("#sch-AutoTxt").text("자동완성 켜기");
					$("#switch").prop("checked", true);
				}
			} else{
				msg = "자동완성을 켜시겠습니까?";
				if(confirm(msg)){
					$("#dropMenu_ul").show();
					$("#autocompleteChk").val('Y');
					$("#sch-AutoTxt").text("자동완성 끄기");
					$("#switch").prop("checked", false);
				}
			}
		}	
		//자동완성 끄기/켜기 끝!
		
		function searchStrFocus(chkValue){
			if(chkValue == 1){
				$("#dropMenu").show();
			} else if (chkValue == 2){
				$("#dropMenu").hide();
			}
		}
		
		
		
		
		
		
		
		
		
		function correctWords(words){
			$("#searchStr").val(words);
			$("#dropMenu").hide();
		}
			
			
			        	
			
			//자동완성 기능 autocomplete 끝
			
			
			
		//더보기 클릭 시
		function viewMoreClick(index1, index2, index3){
				event.preventDefault();
				
				$("#" + index1).hide();
				if(index2 != null){
					$("#" + index2).css("white-space", "normal");
					$("#" + index2).css("overflow", "");
					$("#" + index2).css("text-overflow", "");
				}
				if(index3 != null){
					$("#" + index3).css("white-space", "normal");
					$("#" + index3).css("overflow", "");
					$("#" + index3).css("text-overflow", "");
				}
				
				return false;
		} 
			
		
		//파일 미리보기 버튼 클릭 시,
		//function openFilePrev(conts_file_list_id,addr){
		function openFilePrev(conts_file_list_id){
			event.preventDefault();
			 /*
			    var img_src;
				$.ajax({
			                    url: "/generalBicFileDown.do?fileSeqNo=" + conts_file_list_id,
				        		type : 'get',
			                    contentType : "text/html; charset=utf-8",
			                    //dataType : "text/html",
				        		success : function(data){
				        			console.log(data);
				        			console.log($(data).find("img"));
				        		},
				        		error : function(){
				        			alert(12);
				        		}
				        		
				    	});
			    
			*/    
			    /*
			    0206 이 부분은 임시 데이터를 넣어서 테스트 한것이 아닌, 실제 운영서버에 반영할 목적으로 구성함 --> 개발 도중 모니터단 로직 구성 완료를 위해 임시 주석처리함.
		        var img = new Image();
		        img.src = addr;
		        var img_width = img.width;
		        var img_height = img.height;
		        
		        var win_width = img.width + 25;
		        var win_height = img.height + 25;
		        
		        var left_position = event.screenX;
		        var top_position = event.screenY - 200;
		        
		        var popup = window.open('', '_blank', 'width=' + win_width + ', height=' + win_height + ', left=' + left_position + ', top=' + top_position + ', menubars=no, scrollbars=auto');
		        popup.document.write("<img src='/generalBicFileDown.do?fileSeqNo=" + conts_file_list_id  + "' width='"+img_width +"' height='"+ img_height +"'/>");
		        
			    */
		        
		        /*
		        임시 데이터 넣어서 테스트 해보기.
		        var img = new Image();
		        img.src = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSt6UeyE9DliWP01loUihH_gxKNyO1ojiAxnQ&usqp=CAU";
		        var img_width = img.width;
		        var img_height = img.height;
		        
		        var win_width = img.width + 25;
		        var win_height = img.height + 25;
		        
		        
		        var left_position = event.screenX;
		        var top_position = event.screenY - 200;
		        
		        var popup = window.open('', '_blank', 'width=' + win_width + ', height=' + win_height + ', left=' + left_position + ', top=' + top_position + ', menubars=no, scrollbars=auto');
		        popup.document.write("<img src='https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSt6UeyE9DliWP01loUihH_gxKNyO1ojiAxnQ&usqp=CAU' width='"+img_width +"' height='"+ img_height +"'/>");
		        */
		        
		        
		        
		        /*
		                   0206 이전 기존의 운영서버 실 데이터) 	팝업창 위치 개선과, 팝업창 크기 개선으로 인한 임시 주석 처리한다.
		        */
		        var img_width = 400;
		        var win_width = 450;
		        var img_height = 400;
		        var win_height = 450;
		        
		        
		        var popup = window.open('', '_blank', 'width=' + img_width + ', height=' + img_height + ', menubars=no, scrollbars=auto');
		        popup.document.write("<img src='/generalBicFileDown.do?fileSeqNo=" + conts_file_list_id  + "' width='"+win_width +"' height='"+ win_height +"'/>");
		        
		        
		        
		        
		        
			return false;
		}
			
		
		
		//0307 '이미지 미리보기' 구현
		function imgPreView(event,conts_file_list_id){
			event.preventDefault();
		
			/*
			var imageUrl = "https://orsin.kmrb.or.kr/generalBicFileDown.do?fileSeqNo=";
		
			var previewWindow = window.open("", "ImagePreView" , "width=500, height=500");
			previewWindow.document.write("<img src='" + imageUrl + conts_file_list_id + "' style='max-width:100%; max-height:100%;'>");
			
			*/
			
			/*
			var img = new Image();
			img.src = $("#imgtag" + cnt).attr("src");
			 var img_width = img.width;     //원본 이미지 크기 
		     var img_height = img.height;   //원본 이미지 높이 
		        
		     var win_width = img.width + 25;
		     var win_height = img.height + 25;
		        
		     var left_position = event.screenX;
		     var top_position = event.screenY - 200;
			*/
			
			//var imageUrl = "https://orsin.kmrb.or.kr/generalBicFileDown.do?fileSeqNo=";
		    var imageUrl = "/generalBicFileDown.do?fileSeqNo=";
			//var previewWindow = window.open("", "ImagePreView" , "width=500, height=500");
			var previewWindow = window.open("", "ImagePreView" , "width=500, height=500");
			previewWindow.document.write("<html><head><title>Image Preview</title></head><body><img src='" + imageUrl + conts_file_list_id + "' style='max-width:100%; max-height:100%;'></body></html>");
			
		}
		
		
		
		function imageReSizing(conts_file_list_id, cnt, addr ){
			var img = new Image();
			img.src = addr;
			if(img.width < 358){
				if(img.height < 350){
					//$("#imgtag" + cnt).css("width",  206);
					//$("#imgtag" + cnt).css("height",  img.height / (img.width/206) );
				} else {
					$("#imgtag" + cnt).css("width",  img.width / (img.height/350) );
					$("#imgtag" + cnt).css("height",  350);
				}
			} else {
				if(img.height < 350){
					//문제있다
					$("#imgtag" + cnt).css("width",  358);
					$("#imgtag" + cnt).css("height",  img.height / (img.width/358));
				} else {
					if((img.height/350) < (img.width / 358)     ){
						$("#imgtag" + cnt).css("width",   358  );
						$("#imgtag" + cnt).css("height",  img.height / (img.width / 358) );
					} else {
						$("#imgtag" + cnt).css("width",   img.width / (img.height / 350)  );
						$("#imgtag" + cnt).css("height",  350 ); 
					}
				}
			}
			
		
			
			
			/*
			  0206 기존 이미지 css에서 길고, 넓직한 이미지를 처리하는 로직 구성 중 모니터단 로직 우선 완료하기 위해 임시주석 처리
			if(img.height < 294 ){
				$("#imgtag" + cnt).css("height",  img.height / (img.width/206) );
			} else {
				$("#imgtag" + cnt).css("height",  294);
				$("#imgtag" + cnt).css("width",  img.height / (img.width/206) );
			}
			*/
			
			
			
			
			
			/*
			//0203 이미지 테스트를 위한 함수 
			$.ajax({
		                    url: "/generalBicFileDown.do?fileSeqNo=" + conts_file_list_id,
			        		type : 'post',
		                    //contentType : "html/text; charset=utf-8",
		                    //dataType : "text",
			        		success : function(data){
			        			console.log(data);
			        		},
			        		error : function(){
			        			alert(12);
			        		}
			    	});
			*/
		}
		
		function tabClick(ResultChk){
			event.preventDefault();
				if ($('#searchStr').val().trim() == "" && $('#hotkeywordChk').val() != 'Y' && $("#detail_visibleChk").val() == "N"){
					alert("검색어를 입력하세요");
					return false;
				}
				
				//탭 클릭시, 체크유무 확인  : 결과내 재검색에 대해서 '~검색결과를 찾았다'는 멘트 적을 때, 사용이됨. --> 컨트롤러단에서 결과내 재검색을 처리하는 로직에서 사용하기 위해 만듬 
				$("#tabClickChk").val("1");
				
				
				
			if(ResultChk != 'total'){	
				$("#cPage").val(1);
				$("#resultChk").val(ResultChk);
				
				
				
				
				
				//20221229 결과 더보기 클릭 시, 해당 결과의 매체의 카테고리를 체크박스에 체크해준다.
				if(ResultChk == 'movie01'){
					
	
					$("#sectionGubun02").prop("checked", false);
					$("#sectionGubun03").prop("checked", false);
					$("#sectionGubun04").prop("checked", false);
					$("#sectionGubun05").prop("checked", false);
					$("#sectionGubun06").prop("checked", false);
					$("#sectionGubun07").prop("checked", false);
					$("#sectionGubun08").prop("checked", false);
					
					
					
					
					$("#sectionGubun01").prop("checked", true);
					
				} else if(ResultChk == 'video01'){
					
					$("#sectionGubun01").prop("checked", false);
					$("#sectionGubun03").prop("checked", false);
					$("#sectionGubun04").prop("checked", false);
					$("#sectionGubun05").prop("checked", false);
					$("#sectionGubun06").prop("checked", false);
					$("#sectionGubun07").prop("checked", false);
					$("#sectionGubun08").prop("checked", false);
					
					
					$("#sectionGubun02").prop("checked", true);
				} else if(ResultChk == 'ad01'){
					
					$("#sectionGubun01").prop("checked", false);
					$("#sectionGubun02").prop("checked", false);
					$("#sectionGubun04").prop("checked", false);
					$("#sectionGubun05").prop("checked", false);
					$("#sectionGubun06").prop("checked", false);
					$("#sectionGubun07").prop("checked", false);
					$("#sectionGubun08").prop("checked", false);
					
					
					$("#sectionGubun03").prop("checked", true);
				} else if(ResultChk == 'perform01'){
					$("#sectionGubun01").prop("checked", false);
					$("#sectionGubun02").prop("checked", false);
					$("#sectionGubun03").prop("checked", false);
					$("#sectionGubun05").prop("checked", false);
					$("#sectionGubun06").prop("checked", false);
					$("#sectionGubun07").prop("checked", false);
					$("#sectionGubun08").prop("checked", false);
					
					$("#sectionGubun04").prop("checked", true);
				} else if(ResultChk == 'opin01'){
					
					$("#sectionGubun01").prop("checked", false);
					$("#sectionGubun02").prop("checked", false);
					$("#sectionGubun03").prop("checked", false);
					$("#sectionGubun04").prop("checked", false);
					$("#sectionGubun06").prop("checked", false);
					$("#sectionGubun07").prop("checked", false);
					$("#sectionGubun08").prop("checked", false);
					
					
					$("#sectionGubun05").prop("checked", true);
				} else if(ResultChk == 'img01'){
					
					$("#sectionGubun01").prop("checked", false);
					$("#sectionGubun02").prop("checked", false);
					$("#sectionGubun03").prop("checked", false);
					$("#sectionGubun04").prop("checked", false);
					$("#sectionGubun05").prop("checked", false);
					$("#sectionGubun07").prop("checked", false);
					$("#sectionGubun08").prop("checked", false);
					
					
					$("#sectionGubun06").prop("checked", true);
				} else if(ResultChk == 'file01'){
					
					$("#sectionGubun01").prop("checked", false);
					$("#sectionGubun02").prop("checked", false);
					$("#sectionGubun03").prop("checked", false);
					$("#sectionGubun04").prop("checked", false);
					$("#sectionGubun05").prop("checked", false);
					$("#sectionGubun06").prop("checked", false);
					$("#sectionGubun08").prop("checked", false);
					
					
					
					
					$("#sectionGubun07").prop("checked", true);
				} else if(ResultChk == 'moni01'){
					
					
					$("#sectionGubun01").prop("checked", false);
					$("#sectionGubun02").prop("checked", false);
					$("#sectionGubun03").prop("checked", false);
					$("#sectionGubun04").prop("checked", false);
					$("#sectionGubun05").prop("checked", false);
					$("#sectionGubun06").prop("checked", false);
					$("#sectionGubun07").prop("checked", false);
					
					
					
					
					$("#sectionGubun08").prop("checked", true);
				}
			
				// [결과 더 보기] 클릭 시, 기존의 검색데이터에 맞게 초기화세팅
				modifiedReset();
			   
			   
				
				$('#fm')
				.attr(
						{
							action : '/search/SearchEnginePaging.do',
							method : 'post',
							target : '_self'
						}).submit();
				
				$("#loading").show();
				//URL을 넘겨주고, 그것을 받아서 단독으로 페이징처리함
				// 해당 매체의 [결과 더보기] 클릭 시, 해당 매체가 체크되고, 페이징도 추가로 처리하는로직을 구성하면됨.
				
			} else {
			
				//20230227 통합검색 탭 기능을 클릭하면 다 체크 풀기 				
				modifiedReset();
				$("#sectionGubun01").prop("checked", false);
				$("#sectionGubun02").prop("checked", false);
				$("#sectionGubun03").prop("checked", false);
				$("#sectionGubun04").prop("checked", false);
				$("#sectionGubun05").prop("checked", false);
				$("#sectionGubun06").prop("checked", false);
				$("#sectionGubun07").prop("checked", false);
				$("#sectionGubun08").prop("checked", false);
				ControllHelper.search()
			}	
			
		}
		
		//20230328 사용자 메뉴얼 추가에 따른 onclick 이벤트 함수 시작
		function openMenual(){
			if(confirm("상세검색 가이드로 이동하시겠습니까? ") == true){
				var windowPopup = window.open ("../common/searchEngineManual.html", "상세검색 가이드", "popup" );	
				//windowPopup.document.write("<html><head></head><body><img src='/_res/westudy/kor/img/main/abc1.jpg' /></body></html>");
				//windowPopup.document.write("<html><head></head><body><img src='/_res/westudy/kor/img/main/abc2.jpg' /></body></html>");
				//windowPopup.document.write("<html><head></head><body><img src='/_res/westudy/kor/img/main/abc3.jpg' /></body></html>");
			} else {
				return;
			}
			 
		}
		//20230328 사용자 메뉴얼 추가에 따른 onclick 이벤트 함수 끝
			
			
			
			/*********정의한 function들 끝 *********/
			
			
			
			var ControllHelper = {
				//검색	
				search : function() {
					//const regExp = /[\{\}\[\]\/?.,;:|\)*~`!^\_+<>@\#$%&\\\=\(\'\"]/g;
					const regExp = /[\{\}\[\]\/?.,;:|\)*~`!^\+<>@\#$%&\\\=\(\']/g;
					var searchStr_regExp = $('#searchStr').val().replace(regExp, "");
					$('#searchStr').val(searchStr_regExp);
					//if ($('#searchStr').val() == "" && $('#hotkeywordChk').val() != 'Y' || ($("#detail_visibleChk").val() == "Y" && ($("#detailSearch_aplc").val() != null || 상세검색의 값이 들어가 있어야함)    ) ) {
						if ($('#searchStr').val().trim() == "" && $('#hotkeywordChk').val() != 'Y' && $("#detail_visibleChk").val() == "N"){
						alert("검색어를 입력하세요");
						return false;
					} else {
						if($("#detail_visibleChk").val() == "N"){
							$("#detailSearch_oper").attr("disabled", true);
							$("#detailSearch_oper2").attr("disabled", true);
							$("#detailSearch_resultLine01").attr("disabled", true);
							$("#detailSearch_resultLine02").attr("disabled", true);
							$("#detailSearch_resultLine03").attr("disabled", true);
							$("#detailSearch_resultLine04").attr("disabled", true);
							$("#detailSearch_aplc").attr("disabled", true);
							
						}
						
						if($("#detail_visibleChk").val() == "Y"){
							if(!($("#period_start").val() == "" && $("#period_end").val() == "")){
								if($("#period_start").val() == "" || $("#period_start").val() == null ||  $("#period_end").val() == "" || $("#period_end").val() == null){
									alert("일자를 선택해주세요.");
									return false;
								}
							}	
						}
						
						
						
						//20230227 검색 클릭 시, 무조건 통합검색으로 보여주기 위해서, 체크 올해제하는 로직 추가함 -> 왜? 이제 체크방식은 없어지고, 탭 형식으로 사용하니, 체크박스 하나하나 클릭해서 전체검색을 할 수 가없다. 그래서 무조건 체크된 부분 다 풀어주면됨! 
						// '통합검색' 탭이랑 다른 점은, modifiedReset() 함수를 실행 안함으로써, 변경에 대해서 다 수용하고 검색을 하는 기능임.
						// '통합검색' 탭은, 변경을 수용하지 않아, modifiedReset()함수 후, 검색을 실행함으로써 , 사용자가 임의로 바꾼 검색조건들을 초기화하는것
						$("#sectionGubun01").prop("checked", false);
						$("#sectionGubun02").prop("checked", false);
						$("#sectionGubun03").prop("checked", false);
						$("#sectionGubun04").prop("checked", false);
						$("#sectionGubun05").prop("checked", false);
						$("#sectionGubun06").prop("checked", false);
						$("#sectionGubun07").prop("checked", false);
						$("#sectionGubun08").prop("checked", false);
						//20230227 검색 클릭 시, 무조건 통합검색으로 보여주기 위해서, 체크 올해제하는 로직 추가함
						
						$('#fm')
								.attr(
										{
											action : '/search/TotalSearchEngine.do',
											method : 'post',
											target : '_self'
										}).submit();
						
						$("#loading").show();
					}

				},
			
			//신청서 등록 페이지 가기
			selectTr : function(event,moni_no){
				event.preventDefault();
			
				// 스크린의 크기
				cw = 1150;
				ch = 900;
				sw = screen.availWidth;
				sh = screen.availHeight;
				// 열 창의 포지션
				px = (sw-cw)/2;
				py = (sh-ch)/2;
				
				document.pfrm.moni_no.value = moni_no;
				document.pfrm.type.value = 'RPT';
			
				var openpop	= window.open("","모니터링의견서개별상세",'width='+cw+',height='+ch+',resizable=yes, scrollbars=yes,top='+py+',left='+px);
				openpop.focus();

				$('#pfrm')//팝업에 넘길 파라미터를 가지고 있는 폼 
				.attr({action:'/mnt/popup/imntp033.do', method:'post', target:'모니터링의견서개별상세'})
				.submit();
				
				return false;
			}
				
		};

			function tosearch() {
				var fm = $('#fm');

				if ($('#searchStr').val() == "") {
					alert("검색어를 입력하세요");
					return;
				} else {
					form.action = '/search/TotalSearchEngine.do';
					from.target = "_self";
					form.submit();
				}
			}

			//공연 상세정보
			function openViewMGT2(rcv_no, orseq, h_gubun) {
				//alert(rcv_no+" / "+orseq+" / "+h_gubun);
				window
						.open(
								'/view/view01/view_mgt_ajax.do?searchyn=Y&rcv_no='
										+ rcv_no
										+ '&orseq=' + orseq
										+ '&h_gubun='
										+ h_gubun, '',
								'width=1500,height=900,resizable=yes,scrollbars=yes,left=50,top=50,status=no');
			}

			//공연 상세정보
			function openViewOldMGT2(rcv_no, orseq) {
				window.open(
								'/view/view01/view_mgt_ajax.do?searchyn=Y&rcv_no='
										+ rcv_no
										+ '&orseq=' + orseq,
								'',
								'width=997,height=740,resizable=yes,scrollbars=yes,left=50,top=50,status=no');
			}

			function searchAllList(sectionGubun) {
				//;

				var chkList = $('input[name=sectionGubun]');
				for (var i = 0; i < $('input[name=sectionGubun]').length; i++) {
					if (chkList.eq(i).val() != sectionGubun) {
						chkList.eq(i)
								.prop("checked", false);
					}
				}
				$('#sectionGubun').val(sectionGubun);
				$('#fm')//팝업에 넘길 파라미터를 가지고 있는 폼 
				.attr({
					action : '/search/TotalSearchEngine.do',
					method : 'post',
					target : '_self'
				}).submit();
			}

			function selectTrR(rcv_no) {
				cw = 1000;
				ch = 900;
				sw = screen.availWidth;
				sh = screen.availHeight;
				// 열 창의 포지션
				px = (sw - cw) / 2;
				py = (sh - ch) / 2;

				//$('#cmn_rcvac_id').val(cmn_rcvac_id);
				$('#rcv_no').val(rcv_no);
				var openpop = window
						.open(
								"",
								"신청서등록",
								'width='
										+ cw
										+ ',height='
										+ ch
										+ ',resizable=yes, scrollbars=yes,top='
										+ py + ',left='
										+ px);
				openpop.focus();

				$('#fm')
						//팝업에 넘길 파라미터를 가지고 있는 폼 
						.attr(
								{
									action : '/ircv/popup/ircvp055.do?searchyn=Y',
									method : 'post',
									target : '신청서등록'
								}).submit();
			}

			//일반 첨부파일 다운로드
			function fileDownload(fileSeqNo) {
				$("#fileSeqNo").val(fileSeqNo);
				$('#frm').attr({
					action : '/generalFileDown.do',
					method : 'post',
					target : 'downloadFrame'
				}).submit();
			}

			// 대용량 다운로드
			function BicfileDownload(fileSeqNo) {
				$("#fileSeqNo").val(fileSeqNo);
				$('#frm').attr({
					action : '/generalBicFileDown.do',
					method : 'post',
					target : ''
				}).submit();
			}
		</script>
</head>


<body class="popup" >
<!-- (모니터단 팝업창을 위한 데이터)신청서 등록 팝업시 POST로 넘기고 저장 한 직후 저장된 내용이 새창에서 실행되어 임시 폼을 만들어 처리함 -->
<form name="pfrm" id="pfrm" method="post">
	<input type="hidden"  name="chkMoniNoList" id="chkMoniNoList" />
	<input type="hidden"  name="moni_no" id="moni_no" />
	<input type="hidden"  name="type" id="type" />
</form>
<!-- 프로그레스바 시작 -->
<div id="loading">
 	<img src="../css/searchEngine/img/loading.gif" alt="loading" style="margin:250px;" width="600px;" />
</div>
<!-- 프로그레스바 끝 -->

	<!-- doc-wrap -->
	<form name="fm" id="fm" method="post">
		<div id="doc-wrap">
			<div id="container-wrap" onfocus="searchStrFocus(2);"  tabindex="0">
				<div id="container">
					<!-- ////////////////////////////////////////////////////////////////////////////////////////////////////// 본문 시작 -->

					<!-- contents -->
					<div id="contents" class="div-cont">

						<!-- cont-top : 페이지 타이틀 -->
						<div class="cont-top">
						
							<h2 class="cont-tit">
							<a href="/search/TotalSearchEngine.do">
								<span><img src="../css/searchEngine/img/logo_sch.png"
									alt="logo"></span>영상물등급DB 통합검색
							</a>
							</h2>
						
							<div id="cont-navi" class="cont-navi">
								<ul class="navi-menu">
									<li class="home"><a href="/kor/Main.do"><span>Home</span></a></li>
									<li class="mn_l1"><a
										href="http://www.kmrb.or.kr/kor/CMS/GradeSearch/movie/SearchList.do?mCode=MN143"
										class="mn_a1"><span>검색ㆍ자료실</span></a></li>
									<li class="mn_l1"><a
										href="http://www.kmrb.or.kr/kor/CMS/GradeSearch/movie/SearchList.do?mCode=MN143"
										class="mn_a1"><span>등급분류검색</span></a></li>
									<li class="mn_l1 over"><a
										href="http://www.kmrb.or.kr/kor/CMS/GradeSearch/movie/SearchList.do?mCode=MN143"
										class="mn_a1"><span>영화</span></a></li>
								</ul>
							</div>
						</div>
						<!-- // cont-top : 페이지 타이틀 -->



						
						

						<!-- isSearch : 타이틀을 뺀 모든 컨텐츠 -->
						<div class="isSearch">

							<div class="sch-top">
								<!-- sch-top : 상단 검색바 영역 -->
								<!-- 1. 검색바 영역 -->
								<div class="sch-box ctrl01">
									<!-- 통합검색 박스 -->
									<div class="total_sch">
										<div class="total-select">
											<select name="searchGubun" id="searchGubun">
												<!--
                          <option selected>통합검색</option>
                          <option>통합검색2</option>
                          <option>통합검색3</option>
                      -->
												<option value="All"
													<c:if test="${empty paramMap.searchGubun or paramMap.searchGubun eq 'All'}"> selected </c:if>>통합검색</option>
												<option value="title"
													<c:if test="${paramMap.searchGubun eq 'title'}"> selected </c:if>>제목</option>
												<option value="decision"
													<c:if test="${paramMap.searchGubun eq 'decision'}"> selected </c:if>>결정사유</option>
											</select>
										</div>
										<%-- 검색창 좌측에 보여지는 select box 주석처리.
										<ul>
											<li><input type="radio" id="ttl_jm" class="" name="ttl"
												value="jm"><label for="ttl_jm">제목</label></li>
											<li><input type="radio" id="ttl_gs" class="" name="ttl"
												value="gs"><label for="ttl_gs">결정사유</label></li>
											<li><input type="checkbox" id="ttl_chk1" class=""
												name=""><label for="ttl_chk1">영화</label></li>
											<li><input type="checkbox" id="ttl_chk2" class=""
												name=""><label for="ttl_chk2">비디오물</label></li>
											<li><input type="checkbox" id="ttl_chk3" class=""
												name=""><label for="ttl_chk3">광고물</label></li>
											<li><input type="checkbox" id="ttl_chk4" class=""
												name=""><label for="ttl_chk4">공연추천</label></li>
											<li><input type="checkbox" id="ttl_chk5" class=""
												name=""><label for="ttl_chk5">등급분류의견서</label></li>
											<li><input type="checkbox" id="ttl_chk6" class=""
												name=""><label for="ttl_chk6">이미지</label></li>
											<li><input type="checkbox" id="ttl_chk7" class=""
												name=""><label for="ttl_chk7">첨부파일</label></li>
											<li><input type="checkbox" id="ttl_chk8" class=""
												name=""><label for="ttl_chk8">모니터링의견서</label></li>
											<li><input type="checkbox" id="ttl_chk9" class=""
												name=""><label for="ttl_chk9">자가등급평가</label></li>
										</ul>
										--%>
									</div>
									<!-- // 통합검색 박스 -->
									<c:choose>
									<c:when test="${paramMap.searchStr eq 'NO_VALUE'}">
										<c:set value="" var="searchStrChk" />
									</c:when>
									<c:when test="${paramMap.searchStr ne 'NO_VALUE'}">
										<c:set value='${paramMap.searchStr}' var="searchStrChk" />
									</c:when>
									</c:choose>
									<div class="sch-core ">
										<span class="key-wr"> <label for="searchKeyword"
											class="blind">검색키워드 입력</label> <input type="text"
											class="search-text ui-autocomplete-input" id="searchStr" onfocus="searchStrFocus(1);"  tabindex="0"
											name="searchStr" value='${searchStrChk}'
											placeholder="검색어를 입력하세요" autocomplete="off"> 
									<!-- 오타교정 관련 변수값 (typo : 오타) -->
									<input type="hidden" id="typoSearchStr" name="typoSearchStr" value="${paramMap.typoSearchStr}" />		
									<input type="hidden" id="typoChk" name="typoChk" value=""/>			
									<!-- 오타교정 관련 변수값 -->	
									<!-- 인기검색어 관련 변수값 -->
									<input type="hidden" id="hotkeywordSearchStr" name="hotkeywordSearchStr" value="${paramMap.hotkeywordSearchStr}" />		
									<input type="hidden" id="hotkeywordChk" name="hotkeywordChk" value=""/>	
									<!-- 인기검색어 관련 변수값 -->
									<!-- 연관검색어 관련 변수값 -->
									<input type="hidden" id="suggestionSearchStr" name="suggestionSearchStr" value="${paramMap.suggestionSearchStr}" />		
									<input type="hidden" id="suggestionChk" name="suggestionChk" value=""/>	
									<!-- 연관검색어 관련 변수값 -->
									<!--  라디오 체크 해제 -->
									<input type="hidden" id="grade_name_radioChk" name="grade_name_radioChk" value="${paramMap.detailSearch_rt_std_name}" />
									<input type="hidden" id="rt_std_radioChk" name="rt_std_radioChk" value="${paramMap.detailSearch_grade_name}" />
									<!--  라디오 체크 해제 -->
									<!-- 자동완성 끄기/켜기에 관한 체크확인을 위한 변수 -->
									<input type="hidden" id="autocompleteChk" name="autocompleteChk" value="${paramMap.autocompleteChk}" />
									<!-- 자동완성 끄기/켜기에 관한 체크확인을 위한 변수 -->
													<!-- // sch-dropMenu -->
													<span id="dropMenu">
										                    <div class="sch-dropMenu" onfocus="searchStrFocus(1);"  tabindex="0">
										                      <div id="sch-dropMenuList" class="sch-dropMenuList"> <!-- list -->
										                       
										                        <ul id="dropMenu_ul">
										                        <!--
										                          <li><a href="#"><em>범죄도시2</em>는 기획 당시에는 15세 관람가 등급</a></li>
										                          <li><a href="#">스토리와 액션 수위를 조정하는 단계에서 <em>범죄도시2</em></a></li>
										                          <li><a href="#">전작과 마찬가지로 <em>범죄도시2</em></a></li>
										                          <li><a href="#">그러나 <em>범죄도시2</em> 결론적으로는 결론적으로는 결론적으로는 결론적으로는 결론적으로는</a></li>
										                         -->
										                        </ul>
										                        
										                      </div> <!-- // list -->
										                      <div class="sch-dropAuto"> <!-- auto -->
										                        <div id="sch-AutoTxt" class="sch-AutoTxt" onclick="autocompleteHideorShow();">자동완성 끄기</div>
										                        
										                        <div class="sch-AutoSwitch">
										                          <input type="checkbox" id="switch" class="switchCheck" value="off" onclick="autocompleteHideorShow();">
										                          <label for="switch" class="switchBtn"></label>
										                        </div>
										                        
										                      </div> <!-- // auto -->
										                    </div>
													</span>	
											 		<!-- // sch-dropMenu -->
											
								    </span>
										<button id="mainBtn" class="bd-btn-search" type="submit"
											onclick="javascript:ControllHelper.search(); return false;">
											<span class="blind">검색</span>
										</button>
									</div>

									<div class="sch-btn">
										<a href="#" id="grDetail" onclick="detailOn(event);"><span
											class="">상세검색</span></a>
									</div>
									<div class="sch-btn2">
										<input type="checkbox" id="ins_sch" class="" name=""><label
											for="ins_sch"> 결과내 재검색</label>
									</div>
									<a class="menualLink" id="menualLink" href="#상세검색가이드" onclick="openMenual(); return false;"><img src="../css/searchEngine/img/manualVisible.jpeg" style="width:20px;height:20px;margin-left:20px;"/> 상세검색 가이드 </a>
								</div>
								<!-- // 01. 검색바 영역 -->
								<!-- 결과내 재검색과 관련된 변수  -->
								     <!-- 결과내 재검색을 체크했는지 여부 -->
								     <input type="hidden" id="result_re_search_chk" name="result_re_search_chk" value="${paramMap.result_re_search_chk}" />
								     <!-- 결과내 재검색을 체크했는지 여부 -->
								     
								     <!-- 결과내 재검색을 위해 mediurlEncodingResult 메소드로 데이터를 보내기 위해. -->
								     
								     
								     <input type="hidden" id="movie01_sideInformation_forResearch" name="movie01_sideInformation_forResearch" value="${movie01_sideInformation.list}" />
								     <input type="hidden" id="video01_sideInformation_forResearch" name="video01_sideInformation_forResearch" value="${video01_sideInformation.list}" />
								     <input type="hidden" id="ad01_sideInformation_forResearch" name="ad01_sideInformation_forResearch" value="${ad01_sideInformation.list}" />
								     <input type="hidden" id="perform01_sideInformation_forResearch" name="perform01_sideInformation_forResearch" value="${perform01_sideInformation.list}" />
								     <input type="hidden" id="opin01_sideInformation_forResearch" name="opin01_sideInformation_forResearch" value="${opin01_sideInformation.list}" />
								     <input type="hidden" id="file01_sideInformation_forResearch" name="file01_sideInformation_forResearch" value="${file01_sideInformation.list}" />
								     <input type="hidden" id="img01_sideInformation_forResearch" name="img01_sideInformation_forResearch" value="${img01_sideInformation.list}" />
								     <input type="hidden" id="moni01_sideInformation_forResearch" name="moni01_sideInformation_forResearch" value="${moni01_sideInformation.list}" />
								     <!-- 결과내 재검색을 위해 mediurlEncodingResult 메소드로 데이터를 보내기 위해. -->
								     
								     <!-- 결과내 재검색을 하기 위한 이전쿼리를 가지고 있기 위한 hidden 값 (1번 컨트롤러) -->
									<input type="hidden" id="movie01_historySearchQuery" name="movie01_historySearchQuery" value="${paramMap.movie01_historySearchQuery}" />
									<input type="hidden" id="video01_historySearchQuery" name="video01_historySearchQuery" value="${paramMap.video01_historySearchQuery}" />
									<input type="hidden" id="ad01_historySearchQuery" name="ad01_historySearchQuery" value="${paramMap.ad01_historySearchQuery}" />
									<input type="hidden" id="perform01_historySearchQuery" name="perform01_historySearchQuery" value="${paramMap.perform01_historySearchQuery}" />
									<input type="hidden" id="img01_historySearchQuery" name="img01_historySearchQuery" value="${paramMap.img01_historySearchQuery}" />
									<input type="hidden" id="file01_historySearchQuery" name="file01_historySearchQuery" value="${paramMap.file01_historySearchQuery}" />
									<input type="hidden" id="opin01_historySearchQuery" name="opin01_historySearchQuery" value="${paramMap.opin01_historySearchQuery}" />
									<input type="hidden" id="moni01_historySearchQuery" name="moni01_historySearchQuery" value="${paramMap.moni01_historySearchQuery}" />
									<!-- 결과내 재검색을 하기 위한 이전쿼리를 가지고 있기 위한 hidden 값 (1번 컨트롤러) -->
								<!-- 결과내 재검색과 관련된 변수  -->								
								<!-- // 01(추가). 오타교정 영역 -->
								<div style="font-size:15px;">
										<c:forEach var="result" items="${paramMap.correctStrList}" varStatus="stat">
													<c:if test="${stat.first}" >
													 	<c:if test="${not empty result}">
													 		이런 단어를 찾고 계신가요?
													 	</c:if>
													 </c:if>
										
													 <c:if test="${!stat.last}" >
													 	<a  href="" onclick="javascript:$('#typoSearchStr').val('${result}'); $('#typoChk').val('Y'); ControllHelper.search(); return false;">${result},</a>
													 </c:if>
													 
													 <c:if test="${stat.last}" >
													 	<a href="" onclick="javascript:$('#typoSearchStr').val('${result}'); $('#typoChk').val('Y'); ControllHelper.search(); return false;">${result}</a>
													 </c:if>
										</c:forEach>
								</div>	
								<!-- // 01(추가). 오타교정 영역 -->
								<!-- 02. 체크박스 영역 -->
								<c:forEach var="sectionChk" items="${paramMap.sectionGubun}"
									varStatus="status">
									<c:if test="${sectionChk eq 'Total'}">
										<c:set var="TotalChk" value="Total" />
									</c:if>
									<c:if test="${sectionChk eq 'movie01'}">
										<c:set var="movie01Chk" value="movie01" />
									</c:if>
									<c:if test="${sectionChk eq 'video01'}">
										<c:set var="video01Chk" value="video01" />
									</c:if>
									<c:if test="${sectionChk eq 'ad01'}">
										<c:set var="ad01Chk" value="ad01" />
									</c:if>
									<c:if test="${sectionChk eq 'perform01'}">
										<c:set var="perform01Chk" value="perform01" />
									</c:if>
									<c:if test="${sectionChk eq 'opin01'}">
										<c:set var="opin01Chk" value="opin01" />
									</c:if>
									<c:if test="${sectionChk eq 'img01'}">
										<c:set var="img01Chk" value="img01" />
									</c:if>
									<c:if test="${sectionChk eq 'file01'}">
										<c:set var="file01Chk" value="file01" />
									</c:if>
									<c:if test="${sectionChk eq 'moni01'}">
										<c:set var="moni01Chk" value="moni01" />
									</c:if>
								</c:forEach>
								
								<c:choose>
									<c:when test="${(not empty movie01Chk or not empty video01Chk) and (empty ad01Chk and empty perform01Chk and empty opin01Chk and empty img01Chk and empty file01Chk and empty moni01Chk)}">
										       <script>
										      
										       /*
										       $("#detail_insert").append(`<div class="gr-search-box">
							                    <p class="tit">결정등급</p>
							                    <div id="select-ym" class="select-list">
							                      <!-- 
							                      <ul class="actRow">
							                        <li><input type="radio" id="grDetail03_01" class="" name="grdt" checked><label for="grDetail03_01"> <img src="../css/searchEngine/img/v_btn1.png" alt="all 등급"></label></li>
							                        <li><input type="radio" id="grDetail03_02" class="" name="grdt"><label for="grDetail03_02"> <img src="../css/searchEngine/img/v_btn2.png" alt="12세 등급"></label></li>
							                        <li><input type="radio" id="grDetail03_03" class="" name="grdt"><label for="grDetail03_03"> <img src="../css/searchEngine/img/v_btn3.png" alt="15세 등급"></label></li>
							                        <li><input type="radio" id="grDetail03_04" class="" name="grdt"><label for="grDetail03_04"> <img src="../css/searchEngine/img/v_btn4.png" alt="18세 등급"></label></li>
							                        <li><input type="radio" id="grDetail03_05" class="" name="grdt"><label for="grDetail03_05"> <img src="../css/searchEngine/img/v_btn5.png" alt="유해성 없음"></label></li>
							                      </ul>
							                      -->
							                      <ul class="actRow">
							                        <li><input type="radio" id="grDetail03_01" class="" name="grdt" checked><label for="grDetail03_01"> <span class="actRow_all">All</span></label></li>
							                        <li><input type="radio" id="grDetail03_02" class="" name="grdt"><label for="grDetail03_02"> <span class="actRow_12">12세 등급</span></label></li>
							                        <li><input type="radio" id="grDetail03_03" class="" name="grdt"><label for="grDetail03_03"> <span class="actRow_15">15세 등급</span></label></li>
							                        <li><input type="radio" id="grDetail03_04" class="" name="grdt"><label for="grDetail03_04"> <span class="actRow_18">18세 등급</span></label></li>
							                        <li><input type="radio" id="grDetail03_05" class="" name="grdt"><label for="grDetail03_05"> <span class="actRow_none">유해성 없음</span></label></li>
							                      </ul>
							                    </div>
							                 </div>`)
							                 */
							                 </script>
									</c:when>
									<c:when test="${(not empty ad01Chk) and (empty movie01Chk and empty video01Chk  and empty perform01Chk and empty opin01Chk and empty img01Chk and empty file01Chk and empty moni01Chk)}">
									</c:when>
									<c:when test="${(not empty perform01Chk) and (empty ad01Chk and empty movie01Chk and empty video01Chk and empty opin01Chk and empty img01Chk and empty file01Chk and empty moni01Chk)}">
									</c:when>
									<c:when test="${(not empty opin01Chk) and (empty ad01Chk and empty movie01Chk and empty video01Chk and empty perform01Chk and empty img01Chk and empty file01Chk and empty moni01Chk)}">
									</c:when>
									<c:when test="${(not empty img01Chk or not empty file01Chk) and (empty ad01Chk and empty movie01Chk and empty video01Chk and empty opin01Chk and perform01Chk and empty moni01Chk)}">
									</c:when>
									<c:otherwise>
									</c:otherwise>
								</c:choose>
								
								
								
								
								<!-- 20230224 탭 추가로 인한  기존 체크박스 주석 처리  -->
								 
								<div class="sch-topChk" style="display:none">
									<ul class="sch-topChk-list">
										<li class="sch-chkList ch-chkList-on"><input
											type="checkbox" id="sectionGubun01" name="sectionGubun" onclick="category_checkbox();"
											value="movie01"
											<c:if test="${not empty movie01Chk}">checked</c:if>>
												<label>영화 </label></li>
										<li class="sch-chkList ch-chkList-on"><input
											type="checkbox" id="sectionGubun02" name="sectionGubun" onclick="category_checkbox();"
											value="video01"
											<c:if test="${not empty video01Chk}">checked</c:if>>
												<label>비디오물</label></li>
										<li class="sch-chkList ch-chkList-on"><input
											type="checkbox" id="sectionGubun03" name="sectionGubun" onclick="category_checkbox();"
											value="ad01" <c:if test="${not empty ad01Chk}">checked</c:if>>
												<label>광고물</label></li>
										<li class="sch-chkList ch-chkList-on"><input
											type="checkbox" id="sectionGubun04" name="sectionGubun" onclick="category_checkbox();"
											value="perform01"
											<c:if test="${not empty perform01Chk}">checked</c:if>>
												<label>공연추천</label></li>
										<li class="sch-chkList ch-chkList-on"><input
											type="checkbox" id="sectionGubun05" name="sectionGubun" onclick="category_checkbox();"
											value="opin01"
											<c:if test="${not empty opin01Chk}">checked</c:if>> <label>등급분류의견서</label></li>
										<c:if test="${(paramMap.user_div_code ne '1') and (paramMap.user_div_code eq '9' or paramMap.user_div_code eq '5' or paramMap.user_div_code eq '5A' or paramMap.user_div_code eq '2' or paramMap.user_div_code eq '3' or paramMap.user_div_code eq '4' or paramMap.user_div_code eq '23' or paramMap.user_div_code eq '34' or paramMap.user_div_code eq '234')}">	
											<li class="sch-chkList ch-chkList-on"><input
												type="checkbox" id="sectionGubun06" name="sectionGubun" onclick="category_checkbox();"
												value="img01"
												<c:if test="${not empty img01Chk}">checked</c:if>> <label>이미지</label></li>
										</c:if>
										 
									    <c:if test="${((paramMap.user_div_code ne '1' and paramMap.user_div_code ne '5' and paramMap.user_div_code ne '5A') and (paramMap.user_div_code eq '9' or paramMap.user_div_code eq '2' or paramMap.user_div_code eq '3' or paramMap.user_div_code eq '4' or paramMap.user_div_code eq '23' or paramMap.user_div_code eq '34' or paramMap.user_div_code eq '234'))}">
											<li class="sch-chkList ch-chk List-on"><input
												type="checkbox" id="sectionGubun07" name="sectionGubun" onclick="category_checkbox();"
												value="file01"
												<c:if test="${not empty file01Chk}">checked</c:if>> <label>첨부파일</label></li>
								        </c:if>
									    <c:if test="${((paramMap.user_div_code ne '1' and paramMap.user_div_code ne '2' and paramMap.user_div_code ne '3' and paramMap.user_div_code ne '4' and paramMap.user_div_code ne '23' and paramMap.user_div_code ne '24' and paramMap.user_div_code ne '34' and paramMap.user_div_code ne '234') and (paramMap.user_div_code eq '9' or paramMap.user_div_code eq '5' or paramMap.user_div_code eq '5A'))}">
											<li class="sch-chkList ch-chkList-on"><input
												type="checkbox" id="sectionGubun08" name="sectionGubun" onclick="category_checkbox();"
												value="moni01"
												<c:if test="${not empty moni01Chk}">checked</c:if>> <label>모니터링의견서</label></li>
										</c:if>	
									</ul>
								</div>
								
								<!-- 20230224 탭 추가로 인한  기존 체크박스 주석 처리  -->
								<!-- // 02. 체크박스 영역 -->
							</div>
							<!-- // sch-top : 상단 검색바 영역 -->

							<input type="hidden" id="detail_visibleChk" name="detail_visibleChk" value="" />
							<input type="hidden" id="detail_kindChk"	name="detail_kindChk"    value="${paramMap.detail_kindChk}" />
							<!-- gr-search : 상세검색 펼침 영역 1 -->
							<!-- 펼침메뉴 -->
							<!-- -->
							<span id="divDetail">
							<div class="gr-search gr-ctrl">
								<div class="gr-search-wr">
									<div class="search_box1">

										<%-- 
                  <div class="gr-search-box">
                    <p class="tit">검색대상</p>
                    <div id="select-ym" class="select-list">
                      <ul class="actRow">
                        <li><input type="radio" id="grDetail01_01" class="" name="grdt"><label for="grDetail01_01"> 전체</label></li>
                        <li><input type="radio" id="grDetail01_02" class="" name="grdt"><label for="grDetail01_02"> 제목</label></li>
                        <li><input type="radio" id="grDetail01_03" class="" name="grdt"><label for="grDetail01_03"> 결정사유</label></li>
                      </ul>
                    </div>
                  </div>
				--%>


										<div class="gr-search-box">
											<p class="tit">연산자</p>
											<div id="select-ym" class="select-list">
												<ul class="actRow">
												
												<c:choose>
												<c:when test="${paramMap.detailSearch_radio eq 'allword'}">
														<c:set var="chk01" value="checked"/> 
												</c:when>
												<c:when test="${paramMap.detailSearch_radio eq 'anyword'}">
														<c:set var="chk02" value="checked"/> 
												</c:when>
												<c:when test="${paramMap.detailSearch_radio ne 'allword' or paramMap.detailSearch_radio ne 'anyword'}">
														<c:set var="chk01" value="checked"/>  
												</c:when>
												</c:choose>
												
												
													<li><input type="radio" id="detailSearch_oper" class=""
														name="detailSearch_radio" ${chk01} value="allword"><label for="detailSearch_oper">
																모든 단어 포함(AND)</label></li>
													<li><input type="radio" id="detailSearch_oper2" class="" ${chk02}
														name="detailSearch_radio"  value="anyword"><label for="detailSearch_oper2"> 한
																단어만 포함(OR)</label></li>
												</ul>
											</div>
										</div>

										<div class="gr-search-box">
											<p class="tit">
											<select name="detailSearch_date" id="detailSearch_date" style="width:25px; height:40px; text-align:center;">
													<option value="gradedate" <c:if test="${paramMap.detailSearch_date eq 'gradedate' or (paramMap.detailSearch_date ne 'jubsudate' and paramMap.detailSearch_date ne 'regdate' and paramMap.detailSearch_date ne 'gradedate')}">selected</c:if> >등급분류일자</option>
													<option value="regdate" <c:if test="${paramMap.detailSearch_date eq 'regdate' }">selected</c:if> >신청일자</option>
													<option value="jubsudate" <c:if test="${paramMap.detailSearch_date eq  'jubsudate' }">selected</c:if> >접수일자</option>
										     </select>
											</p>
							
							
											<!--  전체, 오늘, 1주, 1달, 1년 라디오버튼 선택 체크저장 시작-->
											<c:choose>
												<c:when test="${paramMap.detailSearch_day eq 'setDate'}">
														<c:set var="setDate" value="checked"/> 
												</c:when>
												<c:when test="${paramMap.detailSearch_day eq 'today'}">
														<c:set var="today" value="checked"/> 
												</c:when>
												<c:when test="${paramMap.detailSearch_day eq 'aweek'}">
														<c:set var="aweek" value="checked"/> 
												</c:when>
												<c:when test="${paramMap.detailSearch_day eq 'amonth'}">
														<c:set var="amonth" value="checked"/> 
												</c:when>
												<c:when test="${paramMap.detailSearch_day eq 'ayear'}">
														<c:set var="ayear" value="checked"/> 
												</c:when>
												<c:when test="${paramMap.detailSearch_day ne 'setDate' and paramMap.detailSearch_day ne 'today' and paramMap.detailSearch_day ne 'aweek' and paramMap.detailSearch_day ne 'amonth' and paramMap.detailSearch_day ne 'ayear'}">
														<c:set var="setDate" value="checked"/>  
												</c:when>
											</c:choose>
											<!--  전체, 오늘, 1주, 1달, 1년 라디오버튼 선택 체크저장 끝-->
											
											
											
											<div class="radio-btn-list gr-day-search">
															<input type="radio" value="setDate" name="detailSearch_day" id="1st" ${setDate} value="1st">
																<label for="1st" class="radio-btn" onclick="setPeriod('setDate');">전체</label>
															<input type="radio" value="today" name="detailSearch_day" id="2st" ${today} value="2st">
																<label for="2st" class="radio-btn"	onclick="javascript:setPeriod('today');">오늘</label> 
															<input type="radio" value="aweek" name="detailSearch_day" id="3st" ${aweek} value="3st">
																<label for="3st" class="radio-btn" onclick="javascript:setPeriod('aweek');">1주</label> 
														    <input type="radio" value="amonth" name="detailSearch_day" id="4st" ${amonth} value="4st">
																<label for="4st" class="radio-btn" onclick="javascript:setPeriod('amonth');">1달</label> 
															<input type="radio" value="ayear" name="detailSearch_day" id="5st" ${ayear} value="5st">
																<label for=5st class="radio-btn" onclick="javascript:setPeriod('ayear');">1년</label>
											</div>
											<div class="select-list">
												<input type="date" name="period_start" id="period_start" onchange="chgDate();"
													value="${paramMap.period_start}" maxlength="8" title="시작 기간 입력 (예20200101)"
													onkeypress="javascript:if(event.keyCode==13){goSearch(); return;}"
													placeholder="YYYY-MM-DD" class="calendar"> <span>~</span>
													<input type="date" name="period_end" id="period_end" onchange="chgDate();"
													value="${paramMap.period_end}" maxlength="8" title="종료 기간 입력 (예20200101)"
													onkeypress="javascript:if(event.keyCode==13){goSearch(); return;}"
													placeholder="YYYY-MM-DD" class="calendar">
											</div>
											<%-- 20221216 다른 페이지에서 달력 가져온것
											<td>
												<input type="text" id="start_sub_date" name="start_sub_date" class="inputText w60 datepicker" value="${paramMap.start_sub_date}" onkeyup="format_YYYYMMDD(this);" maxlength="8" />
												<input type="text" id="end_sub_date" name="end_sub_date" class="inputText w60 datepicker" value="${paramMap.end_sub_date}" onkeyup="format_YYYYMMDD(this);" maxlength="8" />
											</td>
											
											<input type="text" id="start_sub_date" name="start_sub_date" class="inputText w60 datepicker" value="${paramMap.start_sub_date}" onkeyup="format_YYYYMMDD(this);" maxlength="8" />
											<input type="text" id="end_sub_date" name="end_sub_date" class="inputText w60 datepicker" value="${paramMap.end_sub_date}" onkeyup="format_YYYYMMDD(this);" maxlength="8" />
											--%>
										</div>
										
										<c:choose>
												<c:when test="${paramMap.detailSearch_resultLine eq '3'}">
														<c:set var="chk001" value="checked"/> 
												</c:when>
												<c:when test="${paramMap.detailSearch_resultLine eq '5'}">
														<c:set var="chk002" value="checked"/> 
												</c:when>
												<c:when test="${paramMap.detailSearch_resultLine eq '10'}">
														<c:set var="chk003" value="checked"/> 
												</c:when>
												<c:when test="${paramMap.detailSearch_resultLine eq '20'}">
														<c:set var="chk004" value="checked"/> 
												</c:when>
												<c:when test="${paramMap.detailSearch_resultLine ne '3' or paramMap.detailSearch_resultLine ne '5' or paramMap.detailSearch_resultLine ne '10' or paramMap.detailSearch_resultLine ne '20'}">
														<c:set var="chk001" value="checked"/>  
												</c:when>
												</c:choose>

										<div class="gr-search-box">
											<p class="tit">결과수</p>
											<div id="select-ym" class="select-list">
												<ul class="actRow">
													<li><input type="radio" id="detailSearch_resultLine01" class="" ${chk001} 
														name="detailSearch_resultLine" checked  value="3"><label for="detailSearch_resultLine01" >
																3줄</label></li>
													<li><input type="radio" id="detailSearch_resultLine02" class="" value="5" ${chk002}
														name="detailSearch_resultLine"><label for="detailSearch_resultLine02"> 5줄</label></li>
													<li><input type="radio" id="detailSearch_resultLine03" class="" value="10" ${chk003}
														name="detailSearch_resultLine"><label for="detailSearch_resultLine03">
																10줄</label></li>
													<li><input type="radio" id="detailSearch_resultLine04" class="" value="20" ${chk004}
														name="detailSearch_resultLine"><label for="detailSearch_resultLine04">
																20줄</label></li>
												</ul>
											</div>
										</div>
   <%-- 
										<div class="gr-search-box">
											<p class="tit">결정등급</p>
											<div id="detailSearch_grade" class="detailSearch_grade">
												<!-- 
                      <ul class="actRow">
                        <li><input type="radio" id="grDetail03_01" class="" name="grdt" checked><label for="grDetail03_01"> <img src="../css/searchEngine/img/v_btn1.png" alt="all 등급"></label></li>
                        <li><input type="radio" id="grDetail03_02" class="" name="grdt"><label for="grDetail03_02"> <img src="../css/searchEngine/img/v_btn2.png" alt="12세 등급"></label></li>
                        <li><input type="radio" id="grDetail03_03" class="" name="grdt"><label for="grDetail03_03"> <img src="../css/searchEngine/img/v_btn3.png" alt="15세 등급"></label></li>
                        <li><input type="radio" id="grDetail03_04" class="" name="grdt"><label for="grDetail03_04"> <img src="../css/searchEngine/img/v_btn4.png" alt="18세 등급"></label></li>
                        <li><input type="radio" id="grDetail03_05" class="" name="grdt"><label for="grDetail03_05"> <img src="../css/searchEngine/img/v_btn5.png" alt="유해성 없음"></label></li>
                      </ul>
                      -->
												<ul class="actRow">
													<li><input type="radio" id="grDetail03_01" class=""
														name="grdt" checked><label for="grDetail03_01">
																<span class="actRow_all">All</span>
														</label></li>
													<li><input type="radio" id="grDetail03_02" class=""
														name="grdt"><label for="grDetail03_02"> <span
																class="actRow_12">12세 등급</span></label></li>
													<li><input type="radio" id="grDetail03_03" class=""
														name="grdt"><label for="grDetail03_03"> <span
																class="actRow_15">15세 등급</span></label></li>
													<li><input type="radio" id="grDetail03_04" class=""
														name="grdt"><label for="grDetail03_04"> <span
																class="actRow_18">18세 등급</span></label></li>
													<li><input type="radio" id="grDetail03_05" class=""
														name="grdt"><label for="grDetail03_05"> <span
																class="actRow_none">유해성 없음</span></label></li>
												</ul>
											</div>
										</div>
--%>
										<div class="gr-search-box">
											<p class="tit">신청회사</p>
											<div class="select-list">
												<input type="text" name="detailSearch_aplc" id="detailSearch_aplc"
													value="${paramMap.detailSearch_aplc}" maxlength="" placeholder="">
											</div>
										</div>
										
										
										
										<!--  카테고리별 삽입 해주는 곳 -->
										<div id="detail_insert" name="detail_insert">
						                 </div>
										<!--  카테고리별 삽입 해주는 곳 -->
										
										
										
										
										
										
										
										
										
										
										
										
										
										
										
									</div>

									<div class="c">
									    <a href="#" onclick="detailOnReset(event);" style="float:left;padding:10px;margin:15px 0px 0px 0px;"><img src="../css/searchEngine/img/reset.png" width=15px; height="15px"/>&nbsp;상세검색 초기화 </a>
										<button type="button" class="cp-btn"
											onclick="javascript:ControllHelper.search();">
											<span class="normal"><img
												src="../css/searchEngine/img/search_btn.png" alt="">검색</span>
										</button>
										<button type="button" class="cg-btn cancel" onclick="detailOn(event);">
											<span class="normal">상세검색 닫기</span>
										</button>
									</div>
								</div>
							</div>
						</span>
							<!-- // 펼침메뉴 -->
							<!-- // gr-search : 상세검색 펼침 영역 1 -->










<!-- 20221227 상세검색 다양화 시작  -->
							<%-- 20221219 상세검색 --%>
 
            <!-- gr-search : 상세검색 펼침 영역 2 -->
            <!-- 영화,비디오 상세검색 -->
<%--             
            <div class="gr-search gr-ctrl">
              <div class="gr-search-wr">
                <div class="search_box1">

                  <div class="gr-search-box">
                    <p class="tit">검색대상</p>
                    <div id="select-ym" class="select-list">
                      <ul class="actRow">
                        <li><input type="radio" id="grDetail01_01" class="" name="grdt"><label for="grDetail01_01"> 전체</label></li>
                        <li><input type="radio" id="grDetail01_02" class="" name="grdt"><label for="grDetail01_02"> 제목</label></li>
                        <li><input type="radio" id="grDetail01_03" class="" name="grdt"><label for="grDetail01_03"> 결정사유</label></li>
                      </ul>
                    </div>
                  </div>

                  <div class="gr-search-box">
                    <p class="tit">연산자</p>
                    <div id="select-ym" class="select-list">
                      <ul class="actRow">
                        <li><input type="radio" id="grDetail02_01" class="" name="grdt2" ><label for="grDetail02_01"> 모든 단어 포함(AND)</label></li>
                        <li><input type="radio" id="grDetail02_02" class="" name="grdt2"><label for="grDetail02_02"> 한 단어만 포함(OR)</label></li>
                      </ul>
                    </div>
                  </div>
                  
                  <div class="gr-search-box">
                    <p class="tit">등급분류일자</p>
                    <div class="select-list">
                      <select name="" id="">
                          <option selected>접수일자</option>
                          <option>일자1</option>
                          <option>일자2</option>
                          <option>일자3</option>
                      </select>
                    </div>

                    <div class="radio-btn-list gr-day-search">
                      <input type="radio" value="1st" name="setPeriod" id="1st">
                      <label for="1st" class="radio-btn" onclick="setPeriod('');">전체</label>
                      <input type="radio" value="2st" name="setPeriod" id="2st">
                      <label for="2st" class="radio-btn" onclick="javascript:setPeriod('');">오늘</label>
                      <input type="radio" value="3st" name="setPeriod" id="3st">
                      <label for="3st" class="radio-btn" onclick="javascript:setPeriod('');">1주</label>
                      <input type="radio" value="4st" name="setPeriod" id="4st">
                      <label for="4st" class="radio-btn" onclick="javascript:setPeriod('');">1달</label>
                      <input type="radio" value="5st" name="setPeriod" id="5st">
                      <label for="5st" class="radio-btn" onclick="javascript:setPeriod('');">1년</label>
                    </div>
                    <div class="select-list">
                      <input type="text" name="period_start" id="period_start" value="" maxlength="8" title="시작 기간 입력 (예20200101)" onkeypress="javascript:if(event.keyCode==13){goSearch(); return;}" placeholder="YYYY-MM-DD" class="calendar">
                      <span>~</span>
                      <input type="text" name="period_end" id="period_end" value="" maxlength="8" title="종료 기간 입력 (예20200101)" onkeypress="javascript:if(event.keyCode==13){goSearch(); return;}" placeholder="YYYY-MM-DD" class="calendar">
                    </div>
                  </div> 

                  <div class="gr-search-box">
                    <p class="tit">결과수</p>
                    <div id="select-ym" class="select-list">
                      <ul class="actRow">
                        <li><input type="radio" id="grDetail02_01" class="" name="grdt" checked><label for="grDetail02_01"> 10줄</label></li>
                        <li><input type="radio" id="grDetail02_02" class="" name="grdt"><label for="grDetail2_02"> 20줄</label></li>
                        <li><input type="radio" id="grDetail02_03" class="" name="grdt"><label for="grDetail02_03"> 50줄</label></li>
                        <li><input type="radio" id="grDetail02_04" class="" name="grdt"><label for="grDetail02_04"> 100줄</label></li>
                      </ul>
                    </div>
                  </div>
                  

                  <div class="gr-search-box">
                    <p class="tit">결정등급</p>
                    <div id="select-ym" class="select-list">
                      <!-- 
                      <ul class="actRow">
                        <li><input type="radio" id="grDetail03_01" class="" name="grdt" checked><label for="grDetail03_01"> <img src="../css/searchEngine/img/v_btn1.png" alt="all 등급"></label></li>
                        <li><input type="radio" id="grDetail03_02" class="" name="grdt"><label for="grDetail03_02"> <img src="../css/searchEngine/img/v_btn2.png" alt="12세 등급"></label></li>
                        <li><input type="radio" id="grDetail03_03" class="" name="grdt"><label for="grDetail03_03"> <img src="../css/searchEngine/img/v_btn3.png" alt="15세 등급"></label></li>
                        <li><input type="radio" id="grDetail03_04" class="" name="grdt"><label for="grDetail03_04"> <img src="../css/searchEngine/img/v_btn4.png" alt="18세 등급"></label></li>
                        <li><input type="radio" id="grDetail03_05" class="" name="grdt"><label for="grDetail03_05"> <img src="../css/searchEngine/img/v_btn5.png" alt="유해성 없음"></label></li>
                      </ul>
                      -->
                      <ul class="actRow">
                        <li><input type="radio" id="grDetail03_01" class="" name="grdt" checked><label for="grDetail03_01"> <span class="actRow_all">All</span></label></li>
                        <li><input type="radio" id="grDetail03_02" class="" name="grdt"><label for="grDetail03_02"> <span class="actRow_12">12세 등급</span></label></li>
                        <li><input type="radio" id="grDetail03_03" class="" name="grdt"><label for="grDetail03_03"> <span class="actRow_15">15세 등급</span></label></li>
                        <li><input type="radio" id="grDetail03_04" class="" name="grdt"><label for="grDetail03_04"> <span class="actRow_18">18세 등급</span></label></li>
                        <li><input type="radio" id="grDetail03_05" class="" name="grdt"><label for="grDetail03_05"> <span class="actRow_none">유해성 없음</span></label></li>
                      </ul>
                    </div>
                  </div>
          

                  <div class="gr-search-box">
                    <p class="tit">내용정보표시항목</p>
                    <div id="select-ym" class="select-list">
                      <ul class="actRow">
                        <li><input type="radio" id="grDetail04_01" class="" name="grdt"><label for="grDetail04_01"> 주제</label></li>
                        <li><input type="radio" id="grDetail04_02" class="" name="grdt"><label for="grDetail04_02"> 선정성</label></li>
                        <li><input type="radio" id="grDetail04_03" class="" name="grdt"><label for="grDetail04_03"> 폭력성</label></li>
                        <li><input type="radio" id="grDetail04_04" class="" name="grdt"><label for="grDetail04_04"> 대사</label></li>
                        <li><input type="radio" id="grDetail04_05" class="" name="grdt"><label for="grDetail04_05"> 공포</label></li>
                        <li><input type="radio" id="grDetail04_06" class="" name="grdt"><label for="grDetail04_06"> 약물</label></li>
                        <li><input type="radio" id="grDetail04_07" class="" name="grdt"><label for="grDetail04_07"> 모방위험</label></li>
                      </ul>
                    </div>
                  </div>

                  <div class="gr-search-box cf">
	                  <div class="col-4">
	                  	<p class="tit">매체명</p>
	                    <div class="select-list">
	                      <input type="text" name="period_start" id="period_start" value="" maxlength="" placeholder="">
	                    </div>
	                  </div>
	                  <div class="col-4">
	                  	<p class="tit">제작사 국적</p>
	                    <div class="select-list">
	                      <input type="text" name="period_start" id="period_start" value="" maxlength="" placeholder="">
	                    </div>
	                  </div>
	                  <div class="col-4">
	                  	<p class="tit">감독명</p>
	                    <div class="select-list">
	                      <input type="text" name="period_start" id="period_start" value="" maxlength="" placeholder="">
	                    </div>
	                  </div>
	                  <div class="col-4">
	                  	<p class="tit">영화종류</p>
	                    <div class="select-list">
	                      <input type="text" name="period_start" id="period_start" value="" maxlength="" placeholder="">
	                    </div>
	                  </div>
                  </div>

                  
                  <div class="gr-search-box cf">
                  <div class="col-4">
                  <p class="tit">주연명</p>
                    <div class="select-list">
                      <input type="text" name="period_start" id="period_start" value="" maxlength="" placeholder="">
                    </div>
                  </div>
                    <div class="col-4">
                    <p class="tit">희망등급</p>
                    <div class="select-list">
                      <select name="" id="">
                          <option selected>전체</option>
                          <option>대분류</option>
                          <option>중분류</option>
                          <option>소분류</option>
                      </select>
                    </div>  
                     </div>                  
                    <div class="col-4">
		                    <p class="tit">신청회사</p>
		                    <div class="select-list">
		                      <input type="text" name="period_start" id="period_start" value="" maxlength="" placeholder="">
		                    </div>
                    </div> 
                    <div class="col-4">
		                    <p class="tit">결정의견</p>
		                    <div class="select-list">
		                      <input type="text" name="period_start" id="period_start" value="" maxlength="" placeholder="">
		                    </div>
		            </div>       
                  </div>
                  
                  <div class="gr-search-box cf">
                  <div class="col-4">
	                    <p class="tit">작품내용(줄거리)</p>
	                    <div class="select-list">
	                      <input type="text" name="period_start" id="period_start" value="" maxlength="" placeholder="">
	                    </div>
	              </div>     
	              <div class="col-4">
	                    <p class="tit">결정사유</p>
	                    <div class="select-list">
	                      <input type="text" name="period_start" id="period_start" value="" maxlength="" placeholder="">
	                    </div>
	               </div>       
                  </div>
                  
                </div>

                <div class="c">
                  <button type="button" class="cp-btn" onclick="javascript:goSearch(); return false;"><span class="normal"><img src="../css/searchEngine/img/search_btn.png" alt="">검색</span></button>
                  <button type="button" class="cg-btn cancel"><span class="normal">상세검색 닫기</span></button>
                </div>

              </div>
            </div> 
 
 
            <!-- // 영화,비디오 상세검색 -->
            <!-- // gr-search : 상세검색 펼침 영역 2 -->
              
              
<!--  20221227 상세검색 다양화 -->              
              
              
              

 <%--         
            <!-- gr-search : 상세검색 펼침 영역 3 -->
            <!-- 광고 상세검색 -->
            
            <div class="gr-search gr-ctrl">
              <div class="gr-search-wr">
                <div class="search_box1">
                  
                  <div class="gr-search-box">
                    <p class="tit">검색대상</p>
                    <div id="select-ym" class="select-list">
                      <ul class="actRow">
                        <li><input type="radio" id="grDetail01_01" class="" name="grdt"><label for="grDetail01_01"> 전체</label></li>
                        <li><input type="radio" id="grDetail01_02" class="" name="grdt"><label for="grDetail01_02"> 제목</label></li>
                        <li><input type="radio" id="grDetail01_03" class="" name="grdt"><label for="grDetail01_03"> 결정사유</label></li>
                      </ul>
                    </div>
                  </div>

                  <div class="gr-search-box">
                    <p class="tit">연산자</p>
                    <div id="select-ym" class="select-list">
                      <ul class="actRow">
                        <li><input type="radio" id="grDetail02_01" class="" name="grdt2"><label for="grDetail02_01"> 모든 단어 포함(AND)</label></li>
                        <li><input type="radio" id="grDetail02_02" class="" name="grdt2"><label for="grDetail02_02"> 한 단어만 포함(OR)</label></li>
                      </ul>
                    </div>
                  </div>
              
                  <div class="gr-search-box">
                    <p class="tit">등급분류일자</p>
                    <div class="select-list">
                      <select name="" id="">
                          <option selected>접수일자</option>
                          <option>일자1</option>
                          <option>일자2</option>
                          <option>일자3</option>
                      </select>
                    </div>

                    <div class="radio-btn-list gr-day-search">
                      <input type="radio" value="1st" name="setPeriod" id="1st">
                      <label for="1st" class="radio-btn" onclick="setPeriod('');">전체</label>
                      <input type="radio" value="2st" name="setPeriod" id="2st">
                      <label for="2st" class="radio-btn" onclick="javascript:setPeriod('');">오늘</label>
                      <input type="radio" value="3st" name="setPeriod" id="3st">
                      <label for="3st" class="radio-btn" onclick="javascript:setPeriod('');">1주</label>
                      <input type="radio" value="4st" name="setPeriod" id="4st">
                      <label for="4st" class="radio-btn" onclick="javascript:setPeriod('');">1달</label>
                      <input type="radio" value="5st" name="setPeriod" id="5st">
                      <label for="5st" class="radio-btn" onclick="javascript:setPeriod('');">1년</label>
                    </div>
                    <div class="select-list">
                      <input type="text" name="period_start" id="period_start" value="" maxlength="8" title="시작 기간 입력 (예20200101)" onkeypress="javascript:if(event.keyCode==13){goSearch(); return;}" placeholder="YYYY-MM-DD" class="calendar">
                      <span>~</span>
                      <input type="text" name="period_end" id="period_end" value="" maxlength="8" title="종료 기간 입력 (예20200101)" onkeypress="javascript:if(event.keyCode==13){goSearch(); return;}" placeholder="YYYY-MM-DD" class="calendar">
                    </div>
                  </div> 

                  <div class="gr-search-box">
                    <p class="tit">결과수</p>
                    <div id="select-ym" class="select-list">
                      <ul class="actRow">
                        <li><input type="radio" id="grDetail02_01" class="" name="grdt" checked><label for="grDetail02_01"> 10줄</label></li>
                        <li><input type="radio" id="grDetail02_02" class="" name="grdt"><label for="grDetail2_02"> 20줄</label></li>
                        <li><input type="radio" id="grDetail02_03" class="" name="grdt"><label for="grDetail02_03"> 50줄</label></li>
                        <li><input type="radio" id="grDetail02_04" class="" name="grdt"><label for="grDetail02_04"> 100줄</label></li>
                      </ul>
                    </div>
                  </div>
                  

                  <div class="gr-search-box">
                    <p class="tit">결정등급</p>
                    <div id="select-ym" class="select-list">
                      <!-- 
                      <ul class="actRow">
                        <li><input type="radio" id="grDetail03_01" class="" name="grdt" checked><label for="grDetail03_01"> <img src="../css/searchEngine/img/v_btn1.png" alt="all 등급"></label></li>
                        <li><input type="radio" id="grDetail03_02" class="" name="grdt"><label for="grDetail03_02"> <img src="../css/searchEngine/img/v_btn2.png" alt="12세 등급"></label></li>
                        <li><input type="radio" id="grDetail03_03" class="" name="grdt"><label for="grDetail03_03"> <img src="../css/searchEngine/img/v_btn3.png" alt="15세 등급"></label></li>
                        <li><input type="radio" id="grDetail03_04" class="" name="grdt"><label for="grDetail03_04"> <img src="../css/searchEngine/img/v_btn4.png" alt="18세 등급"></label></li>
                        <li><input type="radio" id="grDetail03_05" class="" name="grdt"><label for="grDetail03_05"> <img src="../css/searchEngine/img/v_btn5.png" alt="유해성 없음"></label></li>
                      </ul>
                      -->
                      <ul class="actRow">
                        <li><input type="radio" id="grDetail03_01" class="" name="grdt" checked><label for="grDetail03_01"> <span class="actRow_all">All</span></label></li>
                        <li><input type="radio" id="grDetail03_02" class="" name="grdt"><label for="grDetail03_02"> <span class="actRow_12">12세 등급</span></label></li>
                        <li><input type="radio" id="grDetail03_03" class="" name="grdt"><label for="grDetail03_03"> <span class="actRow_15">15세 등급</span></label></li>
                        <li><input type="radio" id="grDetail03_04" class="" name="grdt"><label for="grDetail03_04"> <span class="actRow_18">18세 등급</span></label></li>
                        <li><input type="radio" id="grDetail03_05" class="" name="grdt"><label for="grDetail03_05"> <span class="actRow_none">유해성 없음</span></label></li>
                      </ul>
                    </div>
                  </div>
          
                  
                  <div class="gr-search-box cf">
                  <div class="col-4">
	                    <p class="tit">매체명</p>
	                    <div class="select-list">
	                      <input type="text" name="period_start" id="period_start" value="" maxlength="" placeholder="">
	                    </div>
                  </div>
                  <div class="col-4">  
	                    <p class="tit">종류</p>
	                    <div class="select-list">
	                      <input type="text" name="period_start" id="period_start" value="" maxlength="" placeholder="">
	                    </div>
	              </div>   
	              <div class="col-4">   
	                    <p class="tit">신청회사</p>
	                    <div class="select-list">
	                      <input type="text" name="period_start" id="period_start" value="" maxlength="" placeholder="">
	                    </div>
                  </div> 
                  <div class="col-4"> 
	                    <p class="tit">결정의견</p>
	                    <div class="select-list">
	                      <input type="text" name="period_start" id="period_start" value="" maxlength="" placeholder="">
	                    </div>
	               </div>     
                  </div> 
                </div> 

                <div class="c">
                  <button type="button" class="cp-btn" onclick="javascript:goSearch(); return false;"><span class="normal"><img src="../css/searchEngine/img/search_btn.png" alt="">검색</span></button>
                  <button type="button" class="cg-btn cancel"><span class="normal">상세검색 닫기</span></button>
                </div>    
              </div>
            </div>
            
            <!-- // 광고 상세검색 -->
            <!-- // gr-search : 상세검색 펼침 영역 3 -->
              
            
            <!-- gr-search : 상세검색 펼침 영역 4 -->
            <!-- 공연추천 상세검색 -->
             
            <div class="gr-search gr-ctrl">
              <div class="gr-search-wr">
                <div class="search_box1">
                  
                  <div class="gr-search-box">
                    <p class="tit">검색대상</p>
                    <div id="select-ym" class="select-list">
                      <ul class="actRow">
                        <li><input type="radio" id="grDetail01_01" class="" name="grdt"><label for="grDetail01_01"> 전체</label></li>
                        <li><input type="radio" id="grDetail01_02" class="" name="grdt"><label for="grDetail01_02"> 제목</label></li>
                        <li><input type="radio" id="grDetail01_03" class="" name="grdt"><label for="grDetail01_03"> 결정사유</label></li>
                      </ul>
                    </div>
                  </div>

                  <div class="gr-search-box">
                    <p class="tit">연산자</p>
                    <div id="select-ym" class="select-list">
                      <ul class="actRow">
                        <li><input type="radio" id="grDetail02_01" class="" name="grdt2" ><label for="grDetail02_01"> 모든 단어 포함(AND)</label></li>
                        <li><input type="radio" id="grDetail02_02" class="" name="grdt2"><label for="grDetail02_02"> 한 단어만 포함(OR)</label></li>
                      </ul>
                    </div>
                  </div>
            
                  <div class="gr-search-box">
                    <p class="tit">등급분류일자</p>
                    <div class="select-list">
                      <select name="" id="">
                          <option selected>접수일자</option>
                          <option>일자1</option>
                          <option>일자2</option>
                          <option>일자3</option>
                      </select>
                    </div>

                    <div class="radio-btn-list gr-day-search">
                      <input type="radio" value="1st" name="setPeriod" id="1st">
                      <label for="1st" class="radio-btn" onclick="setPeriod('');">전체</label>
                      <input type="radio" value="2st" name="setPeriod" id="2st">
                      <label for="2st" class="radio-btn" onclick="javascript:setPeriod('');">오늘</label>
                      <input type="radio" value="3st" name="setPeriod" id="3st">
                      <label for="3st" class="radio-btn" onclick="javascript:setPeriod('');">1주</label>
                      <input type="radio" value="4st" name="setPeriod" id="4st">
                      <label for="4st" class="radio-btn" onclick="javascript:setPeriod('');">1달</label>
                      <input type="radio" value="5st" name="setPeriod" id="5st">
                      <label for="5st" class="radio-btn" onclick="javascript:setPeriod('');">1년</label>
                    </div>
                    <div class="select-list">
                      <input type="text" name="period_start" id="period_start" value="" maxlength="8" title="시작 기간 입력 (예20200101)" onkeypress="javascript:if(event.keyCode==13){goSearch(); return;}" placeholder="YYYY-MM-DD" class="calendar">
                      <span>~</span>
                      <input type="text" name="period_end" id="period_end" value="" maxlength="8" title="종료 기간 입력 (예20200101)" onkeypress="javascript:if(event.keyCode==13){goSearch(); return;}" placeholder="YYYY-MM-DD" class="calendar">
                    </div>
                  </div>

                  <div class="gr-search-box">
                    <p class="tit">결과수</p>
                    <div id="select-ym" class="select-list">
                      <ul class="actRow">
                        <li><input type="radio" id="grDetail02_01" class="" name="grdt" checked><label for="grDetail02_01"> 10줄</label></li>
                        <li><input type="radio" id="grDetail02_02" class="" name="grdt"><label for="grDetail2_02"> 20줄</label></li>
                        <li><input type="radio" id="grDetail02_03" class="" name="grdt"><label for="grDetail02_03"> 50줄</label></li>
                        <li><input type="radio" id="grDetail02_04" class="" name="grdt"><label for="grDetail02_04"> 100줄</label></li>
                      </ul>
                    </div>
                  </div>
                    

                  <div class="gr-search-box">
                    <p class="tit">결정등급</p>
                    <div id="select-ym" class="select-list">
                      <!-- 
                      <ul class="actRow">
                        <li><input type="radio" id="grDetail03_01" class="" name="grdt" checked><label for="grDetail03_01"> <img src="../css/searchEngine/img/v_btn1.png" alt="all 등급"></label></li>
                        <li><input type="radio" id="grDetail03_02" class="" name="grdt"><label for="grDetail03_02"> <img src="../css/searchEngine/img/v_btn2.png" alt="12세 등급"></label></li>
                        <li><input type="radio" id="grDetail03_03" class="" name="grdt"><label for="grDetail03_03"> <img src="../css/searchEngine/img/v_btn3.png" alt="15세 등급"></label></li>
                        <li><input type="radio" id="grDetail03_04" class="" name="grdt"><label for="grDetail03_04"> <img src="../css/searchEngine/img/v_btn4.png" alt="18세 등급"></label></li>
                        <li><input type="radio" id="grDetail03_05" class="" name="grdt"><label for="grDetail03_05"> <img src="../css/searchEngine/img/v_btn5.png" alt="유해성 없음"></label></li>
                      </ul>
                      -->
                      <ul class="actRow">
                        <li><input type="radio" id="grDetail03_01" class="" name="grdt" checked><label for="grDetail03_01"> <span class="actRow_all">All</span></label></li>
                        <li><input type="radio" id="grDetail03_02" class="" name="grdt"><label for="grDetail03_02"> <span class="actRow_12">12세 등급</span></label></li>
                        <li><input type="radio" id="grDetail03_03" class="" name="grdt"><label for="grDetail03_03"> <span class="actRow_15">15세 등급</span></label></li>
                        <li><input type="radio" id="grDetail03_04" class="" name="grdt"><label for="grDetail03_04"> <span class="actRow_18">18세 등급</span></label></li>
                        <li><input type="radio" id="grDetail03_05" class="" name="grdt"><label for="grDetail03_05"> <span class="actRow_none">유해성 없음</span></label></li>
                      </ul>
                    </div>
                  </div>
          

                  <div class="gr-search-box">
                    <p class="tit">내용정보표시항목</p>
                    <div id="select-ym" class="select-list">
                      <ul class="actRow">
                        <li><input type="radio" id="grDetail04_01" class="" name="grdt"><label for="grDetail04_01"> 주제</label></li>
                        <li><input type="radio" id="grDetail04_02" class="" name="grdt"><label for="grDetail04_02"> 선정성</label></li>
                        <li><input type="radio" id="grDetail04_03" class="" name="grdt"><label for="grDetail04_03"> 폭력성</label></li>
                        <li><input type="radio" id="grDetail04_04" class="" name="grdt"><label for="grDetail04_04"> 대사</label></li>
                        <li><input type="radio" id="grDetail04_05" class="" name="grdt"><label for="grDetail04_05"> 공포</label></li>
                        <li><input type="radio" id="grDetail04_06" class="" name="grdt"><label for="grDetail04_06"> 약물</label></li>
                        <li><input type="radio" id="grDetail04_07" class="" name="grdt"><label for="grDetail04_07"> 모방위험</label></li>
                      </ul>
                    </div>
                  </div>

                  <div class="gr-search-box cf">
                  <div class="col-4">
	                    <p class="tit">외국인 인원수</p>
	                    <div class="select-list">
	                      <input type="text" name="period_start" id="period_start" value="" maxlength="" placeholder="">
	                    </div>
                  </div>  
                  <div class="col-4">
	                    <p class="tit">외국인 국적</p>
	                    <div class="select-list">
	                      <input type="text" name="period_start" id="period_start" value="" maxlength="" placeholder="">
	                    </div>
	              </div>       
                  <div class="col-4">
	                    <p class="tit">공연장소명</p>
	                    <div class="select-list">
	                      <input type="text" name="period_start" id="period_start" value="" maxlength="" placeholder="">
	                    </div>
	              </div>       
	              <div class="col-4">      
	                    <p class="tit">종류</p>
	                    <div class="select-list">
	                      <select name="" id="">
	                          <option selected>전체</option>
	                          <option>대분류</option>
	                          <option>중분류</option>
	                          <option>소분류</option>
	                      </select>
	                    </div>
	               </div>    
                  </div>
                  
                  <div class="gr-search-box cf">
                  <div class="col-4">
	                    <p class="tit">핵심유해사유</p>
	                    <div class="select-list">
	                      <input type="text" name="period_start" id="period_start" value="" maxlength="" placeholder="">
	                    </div>
                  </div>
                  <div class="col-4">  
	                    <p class="tit">희망등급</p>
	                    <div class="select-list">
	                      <select name="" id="">
	                          <option selected>전체</option>
	                          <option>대분류</option>
	                          <option>중분류</option>
	                          <option>소분류</option>
	                      </select>
	                    </div>
	            </div>       
                <div class="col-4">
	                    <p class="tit">연소자 유해성 여부</p>
	                    <div class="select-list">
	                      <select name="" id="">
	                          <option selected>전체</option>
	                          <option>대분류</option>
	                          <option>중분류</option>
	                          <option>소분류</option>
	                      </select>
	                    </div>
	              </div>     
	              <div class="col-4">      
	                    <p class="tit">결정의견</p>
	                    <div class="select-list">
	                      <input type="text" name="period_start" id="period_start" value="" maxlength="" placeholder="">
	                    </div>
	              </div>      
                  </div>
                  
                  <div class="gr-search-box cf">
	                    <p class="tit">계약 시작 일자</p>
	                    <div class="select-list">
	                      <input type="text" name="period_start" id="period_start" value="" maxlength="8" title="시작 기간 입력 (예20200101)" onkeypress="javascript:if(event.keyCode==13){goSearch(); return;}" placeholder="YYYY-MM-DD">
	                      <span>~</span>
	                      <input type="text" name="period_end" id="period_end" value="" maxlength="8" title="종료 기간 입력 (예20200101)" onkeypress="javascript:if(event.keyCode==13){goSearch(); return;}" placeholder="YYYY-MM-DD">
	                    </div>     
	                    <p class="tit">계약 종료 일자</p>
	                    <div class="select-list">
	                      <input type="text" name="period_start" id="period_start" value="" maxlength="8" title="시작 기간 입력 (예20200101)" onkeypress="javascript:if(event.keyCode==13){goSearch(); return;}" placeholder="YYYY-MM-DD">
	                      <span>~</span>
	                      <input type="text" name="period_end" id="period_end" value="" maxlength="8" title="종료 기간 입력 (예20200101)" onkeypress="javascript:if(event.keyCode==13){goSearch(); return;}" placeholder="YYYY-MM-DD">
	                    </div>    
                  </div> 
                </div>
                <div class="c">
                  <button type="button" class="cp-btn" onclick="javascript:goSearch(); return false;"><span class="normal"><img src="../css/searchEngine/img/search_btn.png" alt="">검색</span></button>
                  <button type="button" class="cg-btn cancel"><span class="normal">상세검색 닫기</span></button>
                </div>    
              </div>
            </div>
            
            <!-- // 공연추천 상세검색 -->
            <!-- // gr-search : 상세검색 펼침 영역 4 -->
            --%>
<%-- 20221219 상세검색 --%>


							<div class="ssgap"></div>



							<!-- 검색결과 상단 -->
							<div class="searchrst-listbox" id="boardSearchResult0">
							
							
								<!-- 20230224 css 추가 - 체크박스를 탭 형식으로 변경  -->
						            <div class="tab">
						              <ul>
						              	
						                  <li><a href="" id="totalTab" onclick="tabClick('total');">통합검색</a></li>
						                  <li><a href="" id="movie01Tab" onclick="tabClick('movie01');">영화</a></li>
						                  <li><a href="" id="video01Tab" onclick="tabClick('video01');">비디오물</a></li>
						                  <li><a href="" id="ad01Tab" onclick="tabClick('ad01');">광고물</a></li>
						                  <li><a href="" id="perform01Tab" onclick="tabClick('perform01');">공연추천</a></li>
						                  <c:if test="${paramMap.user_div_code eq '9'}">
						                  <li><a href="" id="opin01Tab" onclick="tabClick('opin01');">등급분류의견서</a></li>
						                  </c:if>
						                  <%--<c:if test="${(paramMap.user_div_code ne '1') and (paramMap.user_div_code eq '9' or paramMap.user_div_code eq '5' or paramMap.user_div_code eq '5A' or paramMap.user_div_code eq '2' or paramMap.user_div_code eq '3' or paramMap.user_div_code eq '4' or paramMap.user_div_code eq '23' or paramMap.user_div_code eq '34' or paramMap.user_div_code eq '234')}"> --%>
						                  	<li><a href="" id="img01Tab" onclick="tabClick('img01');">이미지</a></li>
						                  <%-- </c:if>--%>
						                  <%--<c:if test="${((paramMap.user_div_code ne '1' and paramMap.user_div_code ne '5' and paramMap.user_div_code ne '5A') and (paramMap.user_div_code eq '9' or paramMap.user_div_code eq '2' or paramMap.user_div_code eq '3' or paramMap.user_div_code eq '4' or paramMap.user_div_code eq '23' or paramMap.user_div_code eq '34' or paramMap.user_div_code eq '234'))}"> --%>
						                  <c:if test="${paramMap.user_div_code eq '9'}">
						                    <li><a href="" id="file01Tab" onclick="tabClick('file01');">첨부파일</a></li>
						                  </c:if>
						                  <%--<c:if test="${((paramMap.user_div_code ne '1' and paramMap.user_div_code ne '2' and paramMap.user_div_code ne '3' and paramMap.user_div_code ne '4' and paramMap.user_div_code ne '23' and paramMap.user_div_code ne '24' and paramMap.user_div_code ne '34' and paramMap.user_div_code ne '234') and (paramMap.user_div_code eq '9' or paramMap.user_div_code eq '5' or paramMap.user_div_code eq '5A'))}"> --%>
						                  <c:if test="${paramMap.user_div_code eq '9' }">
						                  	<li><a href="" id="moni01Tab" onclick="tabClick('moni01');">모니터링의견서</a></li>
						                  </c:if>
						                </ul>
						            </div>
								<!-- 20230224 css 추가 - 체크박스를 탭 형식으로 변경  -->

								<div class="searchrst-listboxIn">
									<!-- 회색박스 영역-->
								
									<fmt:formatNumber var="total_count_formating" value="${movie01.total_count + video01.total_count + ad01.total_count + perform01.total_count + file01.total_count + img01.total_count  + moni01.total_count + opin01.total_count}" type="number"/>
									<%-- 테스트를 위해서 임시 주석 
									<c:choose>
										<c:when test="${empty searchStrChk}">
														<span class="rtxt"> 총 <strong class="rnum"
															id="rnumM" data-cnt="0"><i class="text-green">${total_count_formating}</i>건</strong>의
															검색결과를 찾았습니다.
														</span>
										</c:when>
										<c:when test="${not empty searchStrChk}" >
												<h3 class="c-tit01">
													<i class="text-green">'${searchStrChk}'</i>
												</h3>
												<span class="rtxt"> 에 대한 검색결과 총 <strong class="rnum"
													id="rnumM" data-cnt="0"><i class="text-green">${total_count_formating}</i>건</strong>의
													검색결과를 찾았습니다.
												</span>
												<input type="hidden" name="searchStrHistory" id="searchStrHistory" value="${searchStrChk}"/>
										</c:when>
									</c:choose>
									--%>
									<c:choose>
									
										<c:when test="${empty searchStrChk and paramMap.result_re_search_chk ne 'Y'}">
														<span class="rtxt"> 총 <strong class="rnum"
															id="rnumM" data-cnt="0"><i class="text-green">${total_count_formating}</i>건</strong>의
															검색결과를 찾았습니다.
														</span>
										</c:when>
										<c:when test="${not empty searchStrChk and paramMap.result_re_search_chk ne 'Y'}" >
												<h3 class="c-tit01">
													<i class="text-green">'${searchStrChk}'</i>
												</h3>
												<span class="rtxt"> 에 대한 검색결과 총 <strong class="rnum"
													id="rnumM" data-cnt="0"><i class="text-green">${total_count_formating}</i>건</strong>의
													검색결과를 찾았습니다.
												</span>
												<input type="hidden" name="searchStrHistory" id="searchStrHistory" value='${searchStrChk}'/>
												<input type="hidden" name="searchStrHistoryPrev" id="searchStrHistoryPrev" value='${searchStrChk}'  />
										</c:when>
										
										
										<c:when test="${paramMap.result_re_search_chk eq 'Y' and not empty searchStrChk and empty paramMap.tabClickChkOk and empty paramMap.sideMenuClickChkOk and empty paramMap.re_search_WordExist}" >
												<h3 class="c-tit01">
													<i class="text-green">'${paramMap.searchStrHistory}&nbsp;&nbsp;>&nbsp;&nbsp;${searchStrChk}'</i>
												</h3>
												<span class="rtxt"> 에 대한 검색결과 총 <strong class="rnum"
													id="rnumM" data-cnt="0"><i class="text-green">${total_count_formating}</i>건</strong>의
													검색결과를 찾았습니다.
												</span>
												<input type="hidden" name="searchStrHistory" id="searchStrHistory" value='${paramMap.searchStrHistory}'  />
												<input type="hidden" name="searchStrHistoryPrev" id="searchStrHistoryPrev" value='${searchStrChk}'  />
												<input type="hidden" name="searchStrPlusWhiteSpace" id="searchStrPlusWhiteSpace" value="searchStrPlusWhiteSpace"  />
										</c:when>
										<c:when test="${(paramMap.result_re_search_chk eq 'Y' and empty searchStrChk) or not empty paramMap.tabClickChkOk or not empty paramMap.sideMenuClickChkOk or not empty paramMap.re_search_WordExist}" >
												<h3 class="c-tit01">
													<i class="text-green">'${paramMap.searchStrHistory}'</i>
												</h3>
												<span class="rtxt"> 에 대한 검색결과 총 <strong class="rnum"
													id="rnumM" data-cnt="0"><i class="text-green">${total_count_formating}</i>건</strong>의
													검색결과를 찾았습니다.
												</span>
												<input type="hidden" name="searchStrHistory" id="searchStrHistory" value='${paramMap.searchStrHistory}'  />
												<input type="hidden" name="searchStrHistoryPrev" id="searchStrHistoryPrev" value='${searchStrChk}'  />
										</c:when>
									  </c:choose>
									
								</div>
								<!-- // searchrst-listboxIn -->
								
								<!-- tabClick,sideMenuClickChk 클릭 유무  (결과내 재검색과 관련하여 사용되는 변수 . 탭이 클릭되면 , 결과내 재검색 멘트를  searchStrHistory만 보여주기 위해서..  탭 클릭을 했는지 안했는지 확인 필요 -->
								<input type="hidden" name="tabClickChk" id="tabClickChk" value="0"  />
								<input type="hidden" name="sideMenuClickChk" id="sideMenuClickChk" value="0"  />
								<!-- tabClick,sideMenuClickChk 클릭 유무  (결과내 재검색과 관련하여 사용되는 변수.  탭이 클릭되면 , 결과내 재검색 멘트를  searchStrHistory만 보여주기 위해서.. -->
								<div class="listboxIn">
									<div class="search-nav">
											<%-- 20221219 우측 사이드 카테고리를 클릭하면 활성화되게 하기
										<ul>
											<li>Home</li>
											<li>영화</li>
											<li>감독명</li>
										</ul>
											--%>
									</div>
									<ul class="ttl_desc">
										<c:choose>
											<c:when test="${paramMap.orderValue eq 'orderRelevance'}">
												<c:set var="order01" value="cwn-btn-on" />
											</c:when>
											<c:when test="${paramMap.orderValue eq 'orderGa'}">
												<c:set var="order02" value="cwn-btn-on" />
											</c:when>
											<c:when test="${paramMap.orderValue eq 'orderDate'}">
												<c:set var="order03" value="cwn-btn-on" />
											</c:when>
											<c:when test="${paramMap.orderValue ne 'orderRelevance' and paramMap.orderValue ne 'orderGa' and paramMap.orderValue ne 'orderDate'}">
												<c:set var="order01" value="cwn-btn-on" />
											</c:when>
										</c:choose>

										<li><button type="button" id="onclickRe"
												class="cwn-btn ${order01}" onclick="">
												<span class="normal">정확도순</span>
											</button></li>
										<li><button type="button" id="onclickGa"
												class="cwn-btn ${order02}" onclick="">
												<span class="normal">가나다순</span>
											</button></li>
										<li><button type="button" id="onclickDate"
												class="cwn-btn ${order03}" onclick="">
												<span class="normal">등급분류일순</span>
											</button></li>
									</ul>
								</div>
								
								
								
								
								
								
							</div>
							<!-- // 검색결과 상단 -->



							<!-- sch_rst_wrap : 검색결과 목록 -->
							<div class="sch_rst_wrap">
								<div class="sch_rst_lf">
									
									<!-- [결과 더보기] 클릭 시 데이터 넘겨주기 위해서  -->
									 <input type="hidden" id="resultChk" name="resultChk" value="${paramMap.resultChk}" />
 									<!-- [결과 더보기] 클릭 시  -->
 									
 									<!--  [결과 더보기]와 유사한 로직..  // 사이드메뉴명 클릭 시  -->
 									<input type="hidden" id="sidetotalUrl" name="sidetotalUrl" value="${paramMap.sidetotalUrl}" />
 									<input type="hidden" id="sideMenuNm" name="sideMenuNm" value="${paramMap.sideMenuNm}" />
									<!--  [결과 더보기]와 유사한 로직..  // 사이드메뉴명 클릭 시  -->
									
									<input type="hidden" name="limit" id="limit"
										value="${paramMap.limit}" /> <input type="hidden"
										name="cPage" id="cPage" value="${paramMap.cPage}" /> <input
										type="hidden" name="orderValue" id="orderValue"
										value="${paramMap.orderValue}" />

									<!-- 카테고리1  -->

									<c:if
										test="${movie01Chk eq 'movie01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'file01' or paramMap.sectionGubun eq 'moni01'))}">
										<!-- 영화 검색결과 -->
										<div class="searchrst-listbox" id="boardSearchResult0">
											<div class="searchrst-listbox" id="boardSearchResult0">
												<h3 class="c-tit01">
													<span>영화</span>
												</h3>
												<!-- 건수에 콤마 넣어주기 시작 -->
												<fmt:formatNumber var="movie01_count_formating" value="${movie01.total_count}" type="number"/>
												<!-- 건수에 콤마 넣어주기  끝 -->
												<span class="rtxt"> <strong class="rnum" id="rnumM"
													data-cnt="0"><i class="text-green">${movie01_count_formating}
													</i>건</strong>
												</span>
												<span style="color:red;float:right;font-size:15px;" >
												    ※ 검색결과는 등급분류 완료된 자료만 표시됩니다.
												</span>
											</div>
											<ul class="searchrst-list" id="searchrst-list-B">

												<c:set var="movie01ResultCount" value="${movie01.total_count}"/>
													
												<!-- forEach로 반복할 구간 - (영화)-->
												<c:forEach var="result"
													items="${movie01_resultMapList.list}" varStatus="status">

													<li>
														<div class="data-in-wrap">
															<div class="ptit_prevw cf">
																<!--2022-12-09 ul li 신규추가-->
																<div class="ptit">
																	<a class="ptit_a"  onclick="openViewOldMGT2(${result.rcv_no},${result.orseq})"
																		target="_blank" title="새창열림">
																		<p><span class="item">${result.medi_name}</span><mark>${result.use_title}</mark>&nbsp<mark>${result.ori_title}</mark></p>
																		<p>${result.aplc_name}</p>
																		</a>
																</div>
																<!--  
																<div class="prevw">
																	<a href="onclick="openViewOldMGT2('${result.rcv_no}','${result.orseq}')"" target="_blank" onclick="openViewOldMGT2('${result.rcv_no}','${result.orseq}')">
																	 <span>첨부파일
																			미리보기</span>
																	</a>
																</div>
																-->
															</div>

															<!-- 접수일자 포맷변경 -->
															<c:set var="receipt_date"
																value="${fn:substring(result.rcv_date,0,8)}" />
															<p></p>
															<fmt:parseDate value="${receipt_date}" var="dateValue"
																pattern="yyyyMMdd" />
															<fmt:formatDate value="${dateValue}"
																var="receipt_dateValue" pattern="yyyy-MM-dd" />
															<!-- 등록분류일자 포맷변경 -->
															<c:set var="regi_date"
																value="${fn:substring(result.rt_date,0,8)}" />
															<p></p>
															<fmt:parseDate value="${regi_date}" var="dateValue"
																pattern="yyyyMMdd" />
															<fmt:formatDate value="${dateValue}" var="regi_dateValue"
																pattern="yyyy-MM-dd" />

															<ul class="data-class">
																<li><i>접수일자</i><span>${receipt_dateValue}</span></li>
																<li><i>등록분류일자</i><span>${regi_dateValue}</span></li>
																<li><i>등급분류번호</i><span>${result.rt_no}</span></li>
																<li><i>결정등급</i><span>${result.grade_name}</span></li>
																<c:choose>
																	<c:when test="${result.proc_state_code eq '10101'}">
																		<c:set var='proc_nm' value="신청서작성" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10102'}">
																		<c:set var='proc_nm' value="접수반려" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10103'}">
																		<c:set var='proc_nm' value="접수대기" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10104'}">
																		<c:set var='proc_nm' value="접수신청" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10105'}">
																		<c:set var='proc_nm' value="신청완료" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10106'}">
																		<c:set var='proc_nm' value="접수확인" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10107'}">
																		<c:set var='proc_nm' value="전문위원예정" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10108'}">
																		<c:set var='proc_nm' value="추천예정" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10109'}">
																		<c:set var='proc_nm' value="전문위원완료" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10110'}">
																		<c:set var='proc_nm' value="추천완료" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10111'}">
																		<c:set var='proc_nm' value="등급분류예정(대기)" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10112'}">
																		<c:set var='proc_nm' value="등급분류완료" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10113'}">
																		<c:set var='proc_nm' value="담당자확인" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10114'}">
																		<c:set var='proc_nm' value="등급분류번호생성" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10115'}">
																		<c:set var='proc_nm' value="결과통보" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10116'}">
																		<c:set var='proc_nm' value="임시저장" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10117'}">
																		<c:set var='proc_nm' value="가접수" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10118'}">
																		<c:set var='proc_nm' value="회의록작성" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10119'}">
																		<c:set var='proc_nm' value="재작성" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '18102'}">
																		<c:set var='proc_nm' value="결재상신" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '18103'}">
																		<c:set var='proc_nm' value="결재완료" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '18104'}">
																		<c:set var='proc_nm' value="결재회수" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '18105'}">
																		<c:set var='proc_nm' value="결재반려" />
																	</c:when>
																</c:choose>
																<li><i>진행상태</i><span>${proc_nm}</span></li>



																<c:choose>
																	<c:when test="${result.state_code eq '13801'}">
																		<c:set var='code_nm' value="삭제" />
																	</c:when>
																	<c:when test="${result.state_code eq '13802'}">
																		<c:set var='code_nm' value="활성화" />
																	</c:when>
																	<c:when test="${result.state_code eq '13803'}">
																		<c:set var='code_nm' value="취소" />
																	</c:when>
																	<c:when test="${result.state_code eq '13804'}">
																		<c:set var='code_nm' value="취하" />
																	</c:when>
																	<c:when test="${result.state_code eq '13805'}">
																		<c:set var='code_nm' value="담당자취소" />
																	</c:when>
																	<c:when test="${result.state_code eq '13806'}">
																		<c:set var='code_nm' value="시간초과취소" />
																	</c:when>
																	<c:when test="${result.state_code eq '13807'}">
																		<c:set var='code_nm' value="취소요청" />
																	</c:when>
																	<c:when test="${result.state_code eq '13808'}">
																		<c:set var='code_nm' value="사용자취소" />
																	</c:when>
																	<c:when test="${result.state_code eq '13809'}">
																		<c:set var='code_nm' value="반납" />
																	</c:when>
																</c:choose>
																<li><i>상태</i><span>${code_nm}</span></li>
															</ul>
															<p class="pcont_ex" id="viewMoreMovie1${status.index}">
																<span>줄거리 :</span> ${result.work_cont}
															</p>
															<p class="pcont_ex" id="viewMoreMovie2${status.index}"> 
																<span>결정사유 :</span> ${result.deter_rsn}
															</p>
															
															 <c:if test="${fn:length(result.deter_rsn) > 100}" >
																<div class="view-more" id="viewMoreMovie${status.index}">
																		<a href="" onclick="viewMoreClick('viewMoreMovie${status.index}', 'viewMoreMovie1${status.index}' , 'viewMoreMovie2${status.index}' ); return false;"><span>더보기</span></a>
																</div>
															 </c:if>
														   
														</div>
													</li>

												</c:forEach>


											</ul>
											<div class="more_btn cf">
												<!--2022-12-09 더보기버튼추가-->
												
												<c:choose>
													<c:when test="${empty paramMap.detailSearch_resultLine and movie01ResultCount > 3}">
														<button id="resultPaging_movie01" onclick="resultPaging('movie01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '3' and  movie01ResultCount > 3}">
														<button id="resultPaging_movie01" onclick="resultPaging('movie01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '5' and  movie01ResultCount > 5}">
														<button id="resultPaging_movie01" onclick="resultPaging('movie01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '10' and  movie01ResultCount > 10}">
														<button id="resultPaging_movie01" onclick="resultPaging('movie01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '20' and  movie01ResultCount > 20}">
														<button id="resultPaging_movie01" onclick="resultPaging('movie01');">결과 더보기</button>
													</c:when>
												</c:choose>
											</div>
										</div>
										<!-- 영화 검색결과 끝  -->
									</c:if>




									<!-- 카테고리2: 비디오물 시작-->
									<c:if
										test="${video01Chk eq 'video01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'file01' or paramMap.sectionGubun eq 'moni01'))}">
										<div class="searchrst-listbox" id="boardSearchResult0">
											<div class="searchrst-listbox" id="boardSearchResult0">
												<h3 class="c-tit01">
													<span>비디오물</span>
												</h3>
												<!-- 건수에 콤마 넣어주기 시작 -->
												<fmt:formatNumber var="video01_count_formating" value="${video01.total_count}" type="number"/>
												<!-- 건수에 콤마 넣어주기  끝 -->
												<span class="rtxt"> <strong class="rnum" id="rnumM"
													data-cnt="0"><i class="text-green">${video01_count_formating}</i>건</strong>
												</span>
											</div>


											<ul class="searchrst-list" id="searchrst-list-B">


												<c:set var="video01ResultCount" value="${video01.total_count}"/>

												<!-- forEach 개별적으로 수정해줘야하는 부분 -->
												<c:forEach var="result"
													items="${video01_resultMapList.list}" varStatus="status">
													<li>
														<div class="data-in-wrap">
															<div class="ptit_prevw cf">
																<!--2022-12-09 ul li 신규추가-->
																<div class="ptit">
																	<a class="ptit_a"  onclick="openViewOldMGT2(${result.rcv_no},${result.orseq})"
																		target="_blank" title="새창열림">
																		<p><span class="item">${result.medi_name}</span><mark>${result.use_title}</mark>&nbsp<mark>${result.ori_title}</mark></p>
																		<p>${result.aplc_name}</p></a>
																</div>
																<!-- 
																<div class="prevw">
																	<a href="javascript:void(0);" target="_blank" onclick="openViewOldMGT2('${result.rcv_no}','${result.orseq}')">
																	 <span>첨부파일
																			미리보기</span>
																	</a>
																</div>
																-->
															</div>
															<!-- 접수일자 포맷변경 시작 -->
															<c:set var="receipt_date"
																value="${fn:substring(result.rcv_date,0,8)}" />
															<p></p>
															<fmt:parseDate value="${receipt_date}" var="dateValue"
																pattern="yyyyMMdd" />
															<fmt:formatDate value="${dateValue}"
																var="receipt_dateValue" pattern="yyyy-MM-dd" />
															<!-- 접수일자 포맷변경  끝 -->	
																
															<!-- 등록분류일자 포맷변경 시작 -->
															<c:set var="regi_date"
																value="${fn:substring(result.rt_date,0,8)}" />
															<p></p>
															<fmt:parseDate value="${regi_date}" var="dateValue"
																pattern="yyyyMMdd" />
															<fmt:formatDate value="${dateValue}" var="regi_dateValue"
																pattern="yyyy-MM-dd" />
															<!-- 등록분류일자 포맷변경 끝 -->



															<ul class="data-class">
																<li><i>접수일자</i><span>${receipt_dateValue}</span></li>
																<li><i>등록분류일자</i><span>${regi_dateValue}</span></li>
																<li><i>등급분류번호</i><span>${result.rt_no}</span></li>
																<li><i>결정등급</i><span>${result.grade_name}</span></li>
																<c:choose>
																	<c:when test="${result.proc_state_code eq '10101'}">
																		<c:set var='proc_nm' value="신청서작성" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10102'}">
																		<c:set var='proc_nm' value="접수반려" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10103'}">
																		<c:set var='proc_nm' value="접수대기" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10104'}">
																		<c:set var='proc_nm' value="접수신청" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10105'}">
																		<c:set var='proc_nm' value="신청완료" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10106'}">
																		<c:set var='proc_nm' value="접수확인" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10107'}">
																		<c:set var='proc_nm' value="전문위원예정" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10108'}">
																		<c:set var='proc_nm' value="추천예정" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10109'}">
																		<c:set var='proc_nm' value="전문위원완료" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10110'}">
																		<c:set var='proc_nm' value="추천완료" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10111'}">
																		<c:set var='proc_nm' value="등급분류예정(대기)" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10112'}">
																		<c:set var='proc_nm' value="등급분류완료" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10113'}">
																		<c:set var='proc_nm' value="담당자확인" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10114'}">
																		<c:set var='proc_nm' value="등급분류번호생성" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10115'}">
																		<c:set var='proc_nm' value="결과통보" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10116'}">
																		<c:set var='proc_nm' value="임시저장" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10117'}">
																		<c:set var='proc_nm' value="가접수" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10118'}">
																		<c:set var='proc_nm' value="회의록작성" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10119'}">
																		<c:set var='proc_nm' value="재작성" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '18102'}">
																		<c:set var='proc_nm' value="결재상신" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '18103'}">
																		<c:set var='proc_nm' value="결재완료" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '18104'}">
																		<c:set var='proc_nm' value="결재회수" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '18105'}">
																		<c:set var='proc_nm' value="결재반려" />
																	</c:when>
																</c:choose>
																<li><i>진행상태</i><span>${proc_nm}</span></li>



																<c:choose>
																	<c:when test="${result.state_code eq '13801'}">
																		<c:set var='code_nm' value="삭제" />
																	</c:when>
																	<c:when test="${result.state_code eq '13802'}">
																		<c:set var='code_nm' value="활성화" />
																	</c:when>
																	<c:when test="${result.state_code eq '13803'}">
																		<c:set var='code_nm' value="취소" />
																	</c:when>
																	<c:when test="${result.state_code eq '13804'}">
																		<c:set var='code_nm' value="취하" />
																	</c:when>
																	<c:when test="${result.state_code eq '13805'}">
																		<c:set var='code_nm' value="담당자취소" />
																	</c:when>
																	<c:when test="${result.state_code eq '13806'}">
																		<c:set var='code_nm' value="시간초과취소" />
																	</c:when>
																	<c:when test="${result.state_code eq '13807'}">
																		<c:set var='code_nm' value="취소요청" />
																	</c:when>
																	<c:when test="${result.state_code eq '13808'}">
																		<c:set var='code_nm' value="사용자취소" />
																	</c:when>
																	<c:when test="${result.state_code eq '13809'}">
																		<c:set var='code_nm' value="반납" />
																	</c:when>
																</c:choose>
																<li><i>상태</i><span>${code_nm}</span></li>
															</ul>
															<p class="pcont_ex" id="viewMoreVideo1${status.index}">
																<span>줄거리 :</span> ${result.work_cont}
															</p>
															<p class="pcont_ex" id="viewMoreVideo2${status.index}">
																<span>결정사유 :</span> ${result.deter_rsn}
															</p>
															<c:if test="${fn:length(result.deter_rsn) > 100}" >
																<div class="view-more" id="viewMoreVideo${status.index}">
																		<a href="#" onclick="viewMoreClick('viewMoreVideo${status.index}', 'viewMoreVideo1${status.index}' , 'viewMoreVideo2${status.index}' ); return false;"><span>더보기</span></a>
																</div>
															</c:if>
														</div>
													</li>
												</c:forEach>

											</ul>



											<div class="more_btn cf">
												<!--2022-12-09 더보기버튼추가-->
												<c:choose>									
													<c:when test="${empty paramMap.detailSearch_resultLine and video01ResultCount > 3}">
														<button onclick="resultPaging('video01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '3' and  video01ResultCount > 3}">
														<button onclick="resultPaging('video01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '5' and  video01ResultCount > 5}">
														<button onclick="resultPaging('video01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '10' and  video01ResultCount > 10}">
														<button onclick="resultPaging('video01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '20' and  video01ResultCount > 20}">
														<button onclick="resultPaging('video01');">결과 더보기</button>
													</c:when>
												</c:choose>											
											</div>
										</div>
									</c:if>
									<!-- 카테고리2: 비디오물 끝 -->


									<!-- 카테고리3: 광고물 시작-->
									<c:if
										test="${ad01Chk eq 'ad01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'file01' or paramMap.sectionGubun eq 'moni01'))}">
										<div class="searchrst-listbox" id="boardSearchResult0">
											<div class="searchrst-listbox" id="boardSearchResult0">
												<h3 class="c-tit01">
													<span>광고물</span>
												</h3>
												<!-- 건수에 콤마 넣어주기 시작 -->
												<fmt:formatNumber var="ad01_count_formating" value="${ad01.total_count}" type="number"/>
												<!-- 건수에 콤마 넣어주기  끝 -->
												<span class="rtxt"> <strong class="rnum" id="rnumM"
													data-cnt="0"><i class="text-green">${ad01_count_formating}</i>건</strong>
												</span>
											</div>


											<ul class="searchrst-list" id="searchrst-list-B">
											
											<c:set var="ad01ResultCount" value="${ad01.total_count}"/>

												<!-- forEach 개별적으로 수정해줘야하는 부분 -->
												<c:forEach var="result" items="${ad01_resultMapList.list}"
													varStatus="status">
													<li>
														<div class="data-in-wrap">
															<div class="ptit_prevw cf">
																<!--2022-12-09 ul li 신규추가-->
																<div class="ptit">
																	<a class="ptit_a"  onclick="openViewOldMGT2(${result.rcv_no},${result.orseq})"
																		target="_blank" title="새창열림"><span>
																		<p><span class="item">${result.medi_name}</span><mark>${result.use_title}</mark>&nbsp<mark>${result.ori_title}</mark></p>
																		<p>${result.aplc_name}</p></a>
																</div>
																<!-- 
																<div class="prevw">
																	<a href="javascript:void(0);" target="_blank" onclick="openViewOldMGT2('${result.rcv_no}','${result.orseq}')"> 
																	<span>첨부파일
																			미리보기</span>
																	</a>
																</div>
																-->
															</div>

															<!-- 접수일자 포맷변경 -->
															<c:set var="receipt_date"
																value="${fn:substring(result.rcv_date,0,8)}" />
															<p></p>
															<fmt:parseDate value="${receipt_date}" var="dateValue"
																pattern="yyyyMMdd" />
															<fmt:formatDate value="${dateValue}"
																var="receipt_dateValue" pattern="yyyy-MM-dd" />
															<!-- 등록분류일자 포맷변경 -->
															<c:set var="regi_date"
																value="${fn:substring(result.rt_date,0,8)}" />
															<p></p>
															<fmt:parseDate value="${regi_date}" var="dateValue"
																pattern="yyyyMMdd" />
															<fmt:formatDate value="${dateValue}" var="regi_dateValue"
																pattern="yyyy-MM-dd" />

															<ul class="data-class">
																<li><i>접수일자</i><span>${receipt_dateValue}</span></li>
																<li><i>등록분류일자</i><span>${regi_dateValue}</span></li>
																<li><i>등급분류번호</i><span>${result.rt_no}</span></li>
																<li><i>결정등급</i><span>${result.grade_name}</span></li>
																<c:choose>
																	<c:when test="${result.proc_state_code eq '10101'}">
																		<c:set var='proc_nm' value="신청서작성" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10102'}">
																		<c:set var='proc_nm' value="접수반려" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10103'}">
																		<c:set var='proc_nm' value="접수대기" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10104'}">
																		<c:set var='proc_nm' value="접수신청" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10105'}">
																		<c:set var='proc_nm' value="신청완료" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10106'}">
																		<c:set var='proc_nm' value="접수확인" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10107'}">
																		<c:set var='proc_nm' value="전문위원예정" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10108'}">
																		<c:set var='proc_nm' value="추천예정" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10109'}">
																		<c:set var='proc_nm' value="전문위원완료" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10110'}">
																		<c:set var='proc_nm' value="추천완료" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10111'}">
																		<c:set var='proc_nm' value="등급분류예정(대기)" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10112'}">
																		<c:set var='proc_nm' value="등급분류완료" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10113'}">
																		<c:set var='proc_nm' value="담당자확인" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10114'}">
																		<c:set var='proc_nm' value="등급분류번호생성" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10115'}">
																		<c:set var='proc_nm' value="결과통보" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10116'}">
																		<c:set var='proc_nm' value="임시저장" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10117'}">
																		<c:set var='proc_nm' value="가접수" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10118'}">
																		<c:set var='proc_nm' value="회의록작성" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10119'}">
																		<c:set var='proc_nm' value="재작성" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '18102'}">
																		<c:set var='proc_nm' value="결재상신" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '18103'}">
																		<c:set var='proc_nm' value="결재완료" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '18104'}">
																		<c:set var='proc_nm' value="결재회수" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '18105'}">
																		<c:set var='proc_nm' value="결재반려" />
																	</c:when>
																</c:choose>
																<li><i>진행상태</i><span>${proc_nm}</span></li>



																<c:choose>
																	<c:when test="${result.state_code eq '13801'}">
																		<c:set var='code_nm' value="삭제" />
																	</c:when>
																	<c:when test="${result.state_code eq '13802'}">
																		<c:set var='code_nm' value="활성화" />
																	</c:when>
																	<c:when test="${result.state_code eq '13803'}">
																		<c:set var='code_nm' value="취소" />
																	</c:when>
																	<c:when test="${result.state_code eq '13804'}">
																		<c:set var='code_nm' value="취하" />
																	</c:when>
																	<c:when test="${result.state_code eq '13805'}">
																		<c:set var='code_nm' value="담당자취소" />
																	</c:when>
																	<c:when test="${result.state_code eq '13806'}">
																		<c:set var='code_nm' value="시간초과취소" />
																	</c:when>
																	<c:when test="${result.state_code eq '13807'}">
																		<c:set var='code_nm' value="취소요청" />
																	</c:when>
																	<c:when test="${result.state_code eq '13808'}">
																		<c:set var='code_nm' value="사용자취소" />
																	</c:when>
																	<c:when test="${result.state_code eq '13809'}">
																		<c:set var='code_nm' value="반납" />
																	</c:when>
																</c:choose>
																<li><i>상태</i><span>${code_nm}</span></li>
															</ul>
															<p class="pcont_ex" id="viewMoreAd1${status.index}">
																<c:choose>
																	<c:when test="${result.medi_code eq '17506' or result.medi_code eq '17514'}" >
																		<span>종류 :</span> ${result.kind_name}
																	</c:when>
																	<c:when test="${result.medi_code eq '17503' or result.medi_code eq '17504' or result.medi_code eq '17505'}" >
																		<span>종류 :</span> ${result.medi_name}
																	</c:when>
																</c:choose>
															</p>
															<p class="pcont_ex" id="viewMoreAd2${status.index}">
																<span>결정사유 :</span> ${result.deter_rsn}
															</p>
															<c:if test="${fn:length(result.deter_rsn) > 100}" >
															<div class="view-more" id="viewMoreAd${status.index}">
																	<a href="#" onclick="viewMoreClick('viewMoreAd${status.index}', 'viewMoreAd1${status.index}' , 'viewMoreAd2${status.index}' ); return false;"><span>더보기</span></a>
															</div>
															</c:if>
														</div>
													</li>
												</c:forEach>

											</ul>



											<div class="more_btn cf">
												<!--2022-12-09 더보기버튼추가-->
												<c:choose>									
													<c:when test="${empty paramMap.detailSearch_resultLine and ad01ResultCount > 3}">
														<button onclick="resultPaging('ad01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '3' and  ad01ResultCount > 3}">
														<button onclick="resultPaging('ad01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '5' and  ad01ResultCount > 5}">
														<button onclick="resultPaging('ad01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '10' and  ad01ResultCount > 10}">
														<button onclick="resultPaging('ad01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '20' and  ad01ResultCount > 20}">
														<button onclick="resultPaging('ad01');">결과 더보기</button>
													</c:when>
												</c:choose>	
											</div>
										</div>
									</c:if>
									<!-- 카테고리3: 광고물 끝-->




									<!-- 카테고리4: 공연추천 시작-->
									<c:if
										test="${ perform01Chk eq 'perform01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'file01' or paramMap.sectionGubun eq 'moni01'))}">
										<div class="searchrst-listbox" id="boardSearchResult0">
											<div class="searchrst-listbox" id="boardSearchResult0">
												<h3 class="c-tit01">
													<span>공연추천</span>
												</h3>
												<!-- 건수에 콤마 넣어주기 시작 -->
												<fmt:formatNumber var="perform01_count_formating" value="${perform01.total_count}" type="number"/>
												<!-- 건수에 콤마 넣어주기  끝 -->
												<span class="rtxt"> <strong class="rnum" id="rnumM"
													data-cnt="0"><i class="text-green">${perform01_count_formating}</i>건</strong>
												</span>
											</div>


											<ul class="searchrst-list" id="searchrst-list-B">

												<c:set var="perform01ResultCount" value="${perform01.total_count}"/>

												<!-- forEach 개별적으로 수정해줘야하는 부분 -->
												<c:forEach var="result"
													items="${perform01_resultMapList.list}" varStatus="status">
													<li>
														<div class="data-in-wrap">
															<div class="ptit_prevw cf">
																<!--2022-12-09 ul li 신규추가-->
																<div class="ptit">
																	<a class="ptit_a"  onclick="openViewOldMGT2(${result.rcv_no},${result.orseq})"
																		target="_blank" title="새창열림">
																		<p><span class="item">${result.medi_name}</span>&nbsp<mark>${result.ori_title}</mark></p>
																		<p>${result.aplc_name}</p></a>
																</div>
																<!-- 
																<div class="prevw">
																	<a href="javascript:void(0);" target="_blank" onclick="openViewOldMGT2('${result.rcv_no}','${result.orseq}')"> 
																	<span>첨부파일
																			미리보기</span>
																	</a>
																</div>
																-->
															</div>

															<!-- 접수일자 포맷변경 -->
															<c:set var="receipt_date"
																value="${fn:substring(result.rcv_date,0,8)}" />
															<p></p>
															<fmt:parseDate value="${receipt_date}" var="dateValue"
																pattern="yyyyMMdd" />
															<fmt:formatDate value="${dateValue}"
																var="receipt_dateValue" pattern="yyyy-MM-dd" />
															<!-- 등록분류일자 포맷변경 -->
															<c:set var="regi_date"
																value="${fn:substring(result.rt_date,0,8)}" />
															<p></p>
															<fmt:parseDate value="${regi_date}" var="dateValue"
																pattern="yyyyMMdd" />
															<fmt:formatDate value="${dateValue}" var="regi_dateValue"
																pattern="yyyy-MM-dd" />

															<ul class="data-class">
																<li><i>접수일자</i><span>${receipt_dateValue}</span></li>
																<li><i>등록분류일자</i><span>${regi_dateValue}</span></li>
																<li><i>등급분류번호</i><span>${result.rt_no}</span></li>
																<li><i>결정등급</i><span>${result.grade_name}</span></li>
																<c:choose>
																	<c:when test="${result.proc_state_code eq '10101'}">
																		<c:set var='proc_nm' value="신청서작성" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10102'}">
																		<c:set var='proc_nm' value="접수반려" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10103'}">
																		<c:set var='proc_nm' value="접수대기" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10104'}">
																		<c:set var='proc_nm' value="접수신청" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10105'}">
																		<c:set var='proc_nm' value="신청완료" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10106'}">
																		<c:set var='proc_nm' value="접수확인" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10107'}">
																		<c:set var='proc_nm' value="전문위원예정" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10108'}">
																		<c:set var='proc_nm' value="추천예정" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10109'}">
																		<c:set var='proc_nm' value="전문위원완료" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10110'}">
																		<c:set var='proc_nm' value="추천완료" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10111'}">
																		<c:set var='proc_nm' value="등급분류예정(대기)" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10112'}">
																		<c:set var='proc_nm' value="등급분류완료" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10113'}">
																		<c:set var='proc_nm' value="담당자확인" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10114'}">
																		<c:set var='proc_nm' value="등급분류번호생성" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10115'}">
																		<c:set var='proc_nm' value="결과통보" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10116'}">
																		<c:set var='proc_nm' value="임시저장" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10117'}">
																		<c:set var='proc_nm' value="가접수" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10118'}">
																		<c:set var='proc_nm' value="회의록작성" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '10119'}">
																		<c:set var='proc_nm' value="재작성" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '18102'}">
																		<c:set var='proc_nm' value="결재상신" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '18103'}">
																		<c:set var='proc_nm' value="결재완료" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '18104'}">
																		<c:set var='proc_nm' value="결재회수" />
																	</c:when>
																	<c:when test="${result.proc_state_code eq '18105'}">
																		<c:set var='proc_nm' value="결재반려" />
																	</c:when>
																</c:choose>
																<li><i>진행상태</i><span>${proc_nm}</span></li>



																<c:choose>
																	<c:when test="${result.state_code eq '13801'}">
																		<c:set var='code_nm' value="삭제" />
																	</c:when>
																	<c:when test="${result.state_code eq '13802'}">
																		<c:set var='code_nm' value="활성화" />
																	</c:when>
																	<c:when test="${result.state_code eq '13803'}">
																		<c:set var='code_nm' value="취소" />
																	</c:when>
																	<c:when test="${result.state_code eq '13804'}">
																		<c:set var='code_nm' value="취하" />
																	</c:when>
																	<c:when test="${result.state_code eq '13805'}">
																		<c:set var='code_nm' value="담당자취소" />
																	</c:when>
																	<c:when test="${result.state_code eq '13806'}">
																		<c:set var='code_nm' value="시간초과취소" />
																	</c:when>
																	<c:when test="${result.state_code eq '13807'}">
																		<c:set var='code_nm' value="취소요청" />
																	</c:when>
																	<c:when test="${result.state_code eq '13808'}">
																		<c:set var='code_nm' value="사용자취소" />
																	</c:when>
																	<c:when test="${result.state_code eq '13809'}">
																		<c:set var='code_nm' value="반납" />
																	</c:when>
																</c:choose>
																<li><i>상태</i><span>${code_nm}</span></li>
															</ul>
															<p class="pcont_ex" id="viewMorePerform1${status.index}">
																<span>줄거리 :</span> ${result.work_cont}
															</p>
															<p class="pcont_ex" id="viewMorePerform2${status.index}">
																<span>결정사유 :</span> ${result.deter_rsn}
															</p>
															<c:if test="${fn:length(result.deter_rsn) > 100}" >
															<div class="view-more" id="viewMorePerform${status.index}">
																	<a href="#" onclick="viewMoreClick('viewMorePerform${status.index}', 'viewMorePerform1${status.index}' , 'viewMorePerform2${status.index}' ); return false;"><span>더보기</span></a>
															</div>
															</c:if>
														</div>
													</li>
												</c:forEach>

											</ul>



											<div class="more_btn cf">
												<!--2022-12-09 더보기버튼추가-->
												<c:choose>									
													<c:when test="${empty paramMap.detailSearch_resultLine and perform01ResultCount > 3}">
														<button onclick="resultPaging('perform01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '3' and  perform01ResultCount > 3}">
														<button onclick="resultPaging('perform01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '5' and  perform01ResultCount > 5}">
														<button onclick="resultPaging('perform01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '10' and  perform01ResultCount > 10}">
														<button onclick="resultPaging('perform01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '20' and  perform01ResultCount > 20}">
														<button onclick="resultPaging('perform01');">결과 더보기</button>
													</c:when>
												</c:choose>	
											</div>
										</div>
									</c:if>
									<!-- 카테고리4: 공연추천 끝-->


									<!-- 카테고리5: 등급분류의견서 시작-->
									<c:if
										test="${ ((paramMap.user_div_code eq '9')) and (opin01Chk eq 'opin01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'file01' or paramMap.sectionGubun eq 'moni01')))}">
										<div class="searchrst-listbox" id="boardSearchResult0">
											<div class="searchrst-listbox" id="boardSearchResult0">
												<h3 class="c-tit01">
													<span>등급분류의견서</span>
												</h3>
												<!-- 건수에 콤마 넣어주기 시작 -->
												<fmt:formatNumber var="opin01_count_formating" value="${opin01.total_count}" type="number"/>
												<!-- 건수에 콤마 넣어주기  끝 -->
												<span class="rtxt"> <strong class="rnum" id="rnumM"
													data-cnt="0"><i class="text-green">${opin01_count_formating}</i>건</strong>
												</span>
											</div>


											<ul class="searchrst-list" id="searchrst-list-B">

												<c:set var="opin01ResultCount" value="${opin01.total_count}"/>
					
						
												<!-- forEach 개별적으로 수정해줘야하는 부분 -->
												<c:forEach var="result" items="${opin01_resultMapList.list}"
													varStatus="status">
													<li>
														<div class="data-in-wrap">
															<div class="ptit_prevw cf">
																<!--2022-12-09 ul li 신규추가-->
																<div class="ptit">
																	<a class="ptit_a"  onclick="openViewOldMGT2('${result.rcv_no}','${result.rt_parr_orseq}')"
																		target="_blank" title="새창열림">
																		<p><span class="item">${result.medi_name}</span><mark>${result.use_title}</mark>&nbsp<mark>${result.ori_title}</mark></p><p>${result.aplc_name}</p>
																		</a>
																</div>
																<!-- 
																<div class="prevw">
																	<a href="javascript:void(0);" target="_blank" onclick="openViewOldMGT2('${result.rcv_no}','${result.orseq}')">
																	 <span>첨부파일
																			미리보기</span>
																	</a>
																</div>
																-->
															</div>

															<!-- 접수일자 포맷변경 -->
															<c:set var="receipt_date"
																value="${fn:substring(result.rcv_date,0,8)}" />
															<p></p>
															<fmt:parseDate value="${receipt_date}" var="dateValue"
																pattern="yyyyMMdd" />
															<fmt:formatDate value="${dateValue}"
																var="receipt_dateValue" pattern="yyyy-MM-dd" />
															<!-- 등록분류일자 포맷변경 -->
															<c:set var="regi_date"
																value="${fn:substring(result.rt_date,0,8)}" />
															<p></p>
															<fmt:parseDate value="${regi_date}" var="dateValue"
																pattern="yyyyMMdd" />
															<fmt:formatDate value="${dateValue}" var="regi_dateValue"
																pattern="yyyy-MM-dd" />

															<ul class="data-class">
																<li><i>종합/개별 구분</i><span>${result.synt_indvdl_div}</span></li>
																<li><i>등록분류일자</i><span>${regi_dateValue}</span></li>
																<li><i>등급분류번호</i><span>${result.rt_no}</span></li>
																<li><i>위원명</i><span>${result.comb_nm}</span></li>
																<li><i>진행상태</i><span>${result.proc_state}</span></li>
																<li><i>전문위원 조 구분</i><span>${result.exprt_part_div}</span></li>
																<li><i>주제 등급</i><span>${result.opin_grade_nm1}</span></li>
																<li><i>선정성 등급</i><span>${result.opin_grade_nm2}</span></li>
																<li><i>폭력성 등급</i><span>${result.opin_grade_nm3}</span></li>
																<li><i>대사 등급</i><span>${result.opin_grade_nm4}</span></li>
																<li><i>공포 등급</i><span>${result.opin_grade_nm5}</span></li>
																<li><i>약물 등급</i><span>${result.opin_grade_nm6}</span></li>
																<li><i>모방위험 등급</i><span>${result.opin_grade_nm7}</span></li>
																	<c:if test="${result.rt_core_harm_rsn_code1 eq '17701'}">
																		<c:set var="rt_core_harm_rsn_code_nm1" value="주제"/>
																	</c:if>
																	<c:if test="${result.rt_core_harm_rsn_code1 eq '17702'}">
																		<c:set var="rt_core_harm_rsn_code_nm1" value="선정성"/>
																	</c:if>
																	<c:if test="${result.rt_core_harm_rsn_code1 eq '17703'}">
																		<c:set var="rt_core_harm_rsn_code_nm1" value="폭력성"/>
																	</c:if>
																	<c:if test="${result.rt_core_harm_rsn_code1 eq '17704'}">
																		<c:set var="rt_core_harm_rsn_code_nm1" value="대사"/>
																	</c:if>
																	<c:if test="${result.rt_core_harm_rsn_code1 eq '17705'}">
																		<c:set var="rt_core_harm_rsn_code_nm1" value="공포"/>
																	</c:if>
																	<c:if test="${result.rt_core_harm_rsn_code1 eq '17706'}">
																		<c:set var="rt_core_harm_rsn_code_nm1" value="약물"/>
																	</c:if>
																	<c:if test="${result.rt_core_harm_rsn_code1 eq '17707'}">
																		<c:set var="rt_core_harm_rsn_code_nm1" value="모방위험"/>
																	</c:if>
																	<c:if test="${result.rt_core_harm_rsn_code2 eq '17701'}">
																		<c:set var="rt_core_harm_rsn_code_nm2" value=", 주제"/>
																	</c:if>
																	<c:if test="${result.rt_core_harm_rsn_code2 eq '17702'}">
																		<c:set var="rt_core_harm_rsn_code_nm2" value=", 선정성"/>
																	</c:if>
																	<c:if test="${result.rt_core_harm_rsn_code2 eq '17703'}">
																		<c:set var="rt_core_harm_rsn_code_nm2" value=", 폭력성"/>
																	</c:if>
																	<c:if test="${result.rt_core_harm_rsn_code2 eq '17704'}">
																		<c:set var="rt_core_harm_rsn_code_nm2" value=", 대사"/>
																	</c:if>
																	<c:if test="${result.rt_core_harm_rsn_code2 eq '17705'}">
																		<c:set var="rt_core_harm_rsn_code_nm2" value=", 공포"/>
																	</c:if>
																	<c:if test="${result.rt_core_harm_rsn_code2 eq '17706'}">
																		<c:set var="rt_core_harm_rsn_code_nm2" value=", 약물"/>
																	</c:if>
																	<c:if test="${result.rt_core_harm_rsn_code2 eq '17707'}">
																		<c:set var="rt_core_harm_rsn_code_nm2" value=", 모방위험"/>
																	</c:if>
																	<c:if test="${result.rt_core_harm_rsn_code3 eq '17701'}">
																		<c:set var="rt_core_harm_rsn_code_nm3" value=", 주제"/>
																	</c:if>
																	<c:if test="${result.rt_core_harm_rsn_code3 eq '17702'}">
																		<c:set var="rt_core_harm_rsn_code_nm3" value=", 선정성"/>
																	</c:if>
																	<c:if test="${result.rt_core_harm_rsn_code3 eq '17703'}">
																		<c:set var="rt_core_harm_rsn_code_nm3" value=", 폭력성"/>
																	</c:if>
																	<c:if test="${result.rt_core_harm_rsn_code3 eq '17704'}">
																		<c:set var="rt_core_harm_rsn_code_nm3" value=", 대사"/>
																	</c:if>
																	<c:if test="${result.rt_core_harm_rsn_code3 eq '17705'}">
																		<c:set var="rt_core_harm_rsn_code_nm3" value=", 공포"/>
																	</c:if>
																	<c:if test="${result.rt_core_harm_rsn_code3 eq '17706'}">
																		<c:set var="rt_core_harm_rsn_code_nm3" value=", 약물"/>
																	</c:if>
																	<c:if test="${result.rt_core_harm_rsn_code3 eq '17707'}">
																		<c:set var="rt_core_harm_rsn_code_nm3" value=", 모방위험"/>
																	</c:if>
																<li class="data-class-li"><i>내용정보 표시항목</i><span><span>${rt_core_harm_rsn_code_nm1}</span><span>${rt_core_harm_rsn_code_nm2}</span><span>${rt_core_harm_rsn_code_nm3}</span></span></li>
															</ul>
															<p class="pcont_ex" id="viewMoreOpin1${status.index}">
																<span>줄거리 :</span> ${result.work_cont}
															</p>
															<p class="pcont_ex" id="viewMoreOpin2${status.index}">
																<span>결정사유 :</span> ${result.deter_rsn}
															</p>
															<c:if test="${fn:length(result.deter_rsn) > 100}" >
															<div class="view-more" id="viewMoreOpin${status.index}">
																	<a href="#" onclick="viewMoreClick('viewMoreOpin${status.index}', 'viewMoreOpin1${status.index}' , 'viewMoreOpin2${status.index}' ); return false;"><span>더보기</span></a>
															</div>
															</c:if>
														</div>
													</li>
												</c:forEach>

											</ul>



											<div class="more_btn cf">
												<!--2022-12-09 더보기버튼추가-->
												<c:choose>									
													<c:when test="${empty paramMap.detailSearch_resultLine and opin01ResultCount > 3}">
														<button onclick="resultPaging('opin01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '3' and  opin01ResultCount > 3}">
														<button onclick="resultPaging('opin01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '5' and  opin01ResultCount > 5}">
														<button onclick="resultPaging('opin01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '10' and  opin01ResultCount > 10}">
														<button onclick="resultPaging('opin01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '20' and  opin01ResultCount > 20}">
														<button onclick="resultPaging('opin01');">결과 더보기</button>
													</c:when>
												</c:choose>	
											</div>
										</div>
									</c:if>
									<!-- 카테고리5: 등급분류의견서 끝-->




									<!-- 카테고리6: 이미지 시작-->
									<%--<c:if test="${ ((paramMap.user_div_code ne '1') and (paramMap.user_div_code eq '9' or paramMap.user_div_code eq '5' or paramMap.user_div_code eq '5A' or paramMap.user_div_code eq '2' or paramMap.user_div_code eq '3' or paramMap.user_div_code eq '4' or paramMap.user_div_code eq '23' or paramMap.user_div_code eq '34' or paramMap.user_div_code eq '234')) and (img01Chk eq 'img01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'file01' or paramMap.sectionGubun eq 'moni01')))}"> --%>
									<c:if test="${ (img01Chk eq 'img01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'file01' or paramMap.sectionGubun eq 'moni01')))}">
										<div class="searchrst-listbox" id="boardSearchResult0">
											<div class="searchrst-listbox" id="boardSearchResult0">
												<h3 class="c-tit01">
													<span>이미지</span>
												</h3>
												<!-- 건수에 콤마 넣어주기 시작 -->
												<fmt:formatNumber var="img01_count_formating" value="${img01.total_count}" type="number"/>
												<!-- 건수에 콤마 넣어주기  끝 -->
												<span class="rtxt"> <strong class="rnum" id="rnumM"
													data-cnt="0"><i class="text-green">${img01_count_formating}</i>건</strong>
												</span>
											</div>


											<ul class="searchrst-list" id="searchrst-list-B">

												<c:set var="img01ResultCount" value="${img01.total_count}"/>	
                                    
													
													<div class="schres-wrap">
									                  <ul class="img-sch-list clm5">
									                  <c:forEach var="result" items="${img01_resultMapList.list}"	varStatus="status">
									                  
									                  
									                  <!--  
									                  <c:if test="${empty result.nas_id}">
									                    	<c:set var="prevWay"  value="/kmros_file/upload${result.file_path}" />
									                  </c:if>
									                  <c:if test="${result.nas_id eq '1'}">
									                  		<c:set var="prevWay"  value="/kmros_file1/upload${result.file_path}" />
									                  </c:if>
									                  <c:if test="${result.nas_id eq '2'}">
									                  		<c:set var="prevWay"  value="/kmros_file2/upload${result.file_path}" />
									                  </c:if>
									                  -->
									                  <li> 
									                  	<!--  
									                    	<button style="margin-bottom: 0px; width:100px;" onclick="javascript:imgPreView(${result.conts_file_list_id}, ${status.count});">이미지 미리보기</button>
									                    -->
									                    <button style="margin-bottom: 0px; width:100px;" onclick="javascript:imgPreView(event , ${result.conts_file_list_id});">이미지 미리보기</button>
									                  	<a href="#tab" onClick="openViewOldMGT2('${result.rcv_no}','${result.orseq}')"> 
									                  		<span class="img" > 
									                  			<span class="rank-ico rank-ico04">
									                  				<span class="blind">${result.grade_name}
									                  				</span>
									                  			</span> 
									                  			
									                  			
									                  		
									                  		<!-- 
									                  		<img src="/generalBicFileDown.do?fileSeqNo=${result.conts_file_list_id}" alt="이미지 없음" />
									                  		-->
									                  		
									                  		<img id="imgtag${status.count}" onload="javascript:imageReSizing(${result.conts_file_list_id}, ${status.count}, this.src  );" src="/generalBicFileDown.do?fileSeqNo=${result.conts_file_list_id}" alt="이미지 없음"  />	
									                  		
									                  		<!--  
															<c:if test="${status.count eq 1}">
									                  		<img id="imgtag${status.count}" onload="javascript:imageReSizing(${result.conts_file_list_id}, ${status.count}, this.src  );" src="https://orsin.kmrb.or.kr/generalBicFileDown.do?fileSeqNo=1266279" alt="이미지 없음"  />
									                  		</c:if>
									                  		<c:if test="${status.count eq 2}">
									                  		<img id="imgtag${status.count}" onload="javascript:imageReSizing(${result.conts_file_list_id}, ${status.count}, this.src  );" src="https://orsin.kmrb.or.kr/generalBicFileDown.do?fileSeqNo=1262103" alt="이미지 없음"  />
									                  		</c:if>
									                  		<c:if test="${status.count eq 3}">
									                  		<img id="imgtag${status.count}" onload="javascript:imageReSizing(${result.conts_file_list_id}, ${status.count}, this.src  );" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTVadDUdcI7CK_1pnU4FrZgcYLHV9vcxc93qg&usqp=CAU" alt="이미지 없음" />
									                  		</c:if>
									                  		<c:if test="${status.count eq 4}">
									                  		<img id="imgtag${status.count}" onload="javascript:imageReSizing(${result.conts_file_list_id}, ${status.count}, this.src  );" src="data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAoHCBUVFBcVFRQYGBcaGiQdGhsaHCEcIB0eHhsbHSQeIBsgIiwkIiApIR4bJTgoKS4wMzM0GyI5PjkxPSwyNDABCwsLEA4QGxISHTIpIikyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjAyMjIyMjIyMjIyMv/AABEIAZAAWgMBIgACEQEDEQH/xAAbAAABBQEBAAAAAAAAAAAAAAAFAQIDBAYAB//EAEAQAAIBAwIEAwUGBAQGAgMAAAECEQADIRIxBAVBURMiYQYycYGRQqGxwdHwFCNSYnKC4fEHFVOSotJDshYzVP/EABcBAQEBAQAAAAAAAAAAAAAAAAABAgP/xAAdEQEBAQEAAwADAAAAAAAAAAAAARECEiExAxNB/9oADAMBAAIRAxEAPwD06ummTSg1EOmnA0yaWaB9NIrppKymEIrqWurSm1xFOptA0im1JNJFXWcNrq6uqBZpdVNpKCSaWajrpoupJrppk0k0NSTSTTJrqJpxpKSkoOmlqMGl1UD6Sk1UmqgfXUk100C0lJNJNA6ups0k1Q6a6aZNdNQIhkA+lMd9/T8c96qpfj3jAGZJ75yO89O0d6bDEsSYiImRpMD5ZnrnfNVcWX4kAT6Y+sfv51JYBiW3O/6fAVQ4JtaqxBYKIEj4Z7dv2Yq+k9YA7D9aFPmumkpKIdNLTSaSgdNJXE1Gt0GgkrqbrG9OmiaDcPcCquxC4JO0xkmfWIHeT2qThs+ZgdJJ0hjBOwB09Oh0nuMDaoeF4bxAT7q/0kbxEE5wdyAJG0zAi+qYj3W7gSD6+vXHxo2Thrvl1RAJO/SDH5VYDjb6np8Pj+FDBc/ltkQQSI+Z+f79KslwxCqJAwesEQY7dpzQxcRpz9PhSzTRTdXXptP5/v0oydqzHbP1/Z+6nE1FYJiTuTP7+UU5m2oHFoqhxSwdQHfUOxP2vh0PxqXiLudJ6jH0mPjiRQ5+K1HRqjAZW6jaVPfcCjSfxswDjc+m5/AH6/CrH8Qv9RoFdOliSfeEEHoRJAjoN/3tJ/zT0H0H60MErN4qonDdysEjrqB7EkTtP0pfHn3gNUCTI8oxkbZz8qH27Okzp0AZUGAVyZYr9mc5k/LJp/DusBS0kTHTHyIM5P1oqW44W0ZEs6mD66JOOggH9nFuwzR0UAYzO2SCfxI+vYK16EcjV7jKP8TdZOANWkepJ+c78xQCCw3jMAbbHJJnt8qA0tzYd/qfhP41FxLknRjPvD+0RP1kD5ms/wATzi/rHhebUY0FPNBGCcdcYxEr3wO5lzi6Xd1YLpYKxH9ury5wMyflTExulOBiovEgkk46fmf32rBI7rrNy47Q+wf3myQSCYAkdTt0IIqhf45lZWZnDSCAxkgDt9223zNXDxbrj2dhgEMNhsd9/vkb/lQfig+r3CpEHIIBAJBz6j7x9AKc2usVlyQMr1wQQRPU5Gw60vDcxaCCzATIKvt9ST33PwimLB+4ysonbEHG2SI9fLv06ULNq5/V/wDWorfHFtKwCJyYIPz8+k7kznvThzS11YT1w2//AH0UVbh2MamZlmcmB6ENJziZ9aXxAFhWAJzqSSIBBYggaSIO8j3xUDEAHSgZ2bSADHSRLbQIMtkAZ6gFpRRHiPJnUdIeEgQCsA7YySSd57A25f1eGFBAzGkKQTA0nMKYZphSR5DntwChZd2YCR5vKST9m2S7GNz94O08tlRdJe6gQjGrys2d/NpJg9P1NWXCFgG8PSp8pYoSdRzBDjzHv2jeJIOfmFvUYl2G/nRSgI3IaQCV2MnBO0zQS9xroEW3ZUkvANxg9wyGOtQhAG7efp3FEuMsB97ghSfLoVM4hgTjCnderTXcPwq2iLiHzER37g5IliZiWxj0oofx76EDElicJIAJY+9cIAG4HUSRGxBoO6zkmSZn75oncti5cLNcVvhMLuYJP2vh6nFQcTw0gwIgCZ2jJJJ+vQzVgi4W2mkMWdliJVRhieqzg7CMhu9KttJIF1Z7OrA4HaDPwk/dUdsBOuRMRPu5zAgkb7dqu2WtkGQjMcKYIkkQPKIn5nagbZ4dSSyPbwJg68xBx5Z3n1AHY1L/AAh/6Cfh9xEj51Pc4pgDp0JgS6qp7R5iCVB2zBgdqq/xNz/pofWBmgLIAzEE6ThdSkgLkEfaMTtJMyATNSIhYwwUgGNJQwIyWEL0xg9tzIjrTgaiM6pBkxkgSQZ3/H1pUXV78sCAykZkABcGcnA3I269IiB7esgEAxIUssbdSAQIkBdjt8YaT5xpVvDyfeA9DqUgzMfhHrdsaQBoc4G2nO2nzZk9Bk5xXOv8wLJEmSTJnqB6A7yM7YigrW+DUAE5YjUxmAYBnJO2T2Gdj165aDmdIjEAAkqDEZMdI2HTO1TmDrAdtIPbf0B7dcYmO1Drh8zAkmBqiJI2xidzG5zOaB1+2uCNQgqAM5MjJPf3TP8AbuKRjABHeS0YjfLdu9VbDge63vGVJGe/edusbEVbt2ZGR5eufrj0x9NqKg/hyYko8bhiAZGcY2/QbU5rBUAEE/AzAn/FqG/rv9JymnAldzggYMZ3n51UdXGdeqO5kgyMie9DTCkk5IByTswPmiAR2ER2kQKr3tWpsjc/ZQ9e8Zq2jvlTkZmREGR67dR8ADUkt0W2R0Pf76ot27LaiYRc7ypB2Ak9CfUTJ32pHtx5fMVO8biPtRMmMGJzp270UunDaHAcBAN4YFjpnrue23fAs2eNDamZToQ6fMYJbed/dx1PpEVET2WOgMWB/wAMBcAEkAH4HPYdxDuGYeG2qAWyxECQ0wpYGQ2nJM43gYofxHFfyfOfeJVRAIzIEaTvB2JGOgiKucIjCBqDrmB29CYBLZ32gdBigs+Fj+lekRP0xH1jHyqG9bRQDAAJiMe9icDc4zmq98OCsufMYjAXYkDbAx0B9aq3rdxU1v7syUDaiADMwd+sfHM7AhLJUrBWAgwfKZOFjttOd/wq3b4hycQImAPLMZ6CIzExMUM4Th7jKjIY8q5bPmAg47wfqe9SWbd2YZwxJIk4iAojA6z9Qaqry3GGPrnbGDtjr+yaciQCQcHOZifjPx2qonCOpEsDqgZPaTO3YGY7DvXBLgOh5375MDeCPj+tEcRAc4IUBlkRmJ97viMHE9ZpispAOjfPTr/kqHh776HKkNpkgMJMAsAR2xAoYObXF8qsYGBvsMDrRWl4W8hteHqGtl1QOjEE4IxIO0fnNRcs5xbayRcMa3dn1DEMzYgYMCBGdq3XC8usAgi2kjqF+A/fwoT7G8PbnirVxLZe1xLqupRPhkDRmNo/HvWdJ8YOzd1E2ixKW2Y6tQGtA0omdzqGBB32xjU8tsPdCv5FxCDWA0DBMTkziT/ScVQ5fwdu4/NWe2CEF024HulHuAFI2IAG1abhuWWm4eywvaQbSaQ2VBKgllGLgmTgMBnbelW+g21yd2QG7qYy0MgJxJifDBkxE46b0NtcD4V4M9k6TOp/NBAUmMhSNuo/KtF/y+2FUXeJLadRm2Ch90AAFSAIP9QafrIvmvFW7PD3UtcQ9xnBGm6zEHUVU4BCxAIn1Mz0TWfrL8Bw9x+GU2zpIBfSYAK62woidWQcTMYyDS8P4l1XVbgRwZJhpk6cSqYH+najFniB4SW7al9BCylpLhChYBkqTJbsO9IvBcWtx3t8Ox8QiVKBQsagNMhY6Y+ta1dVn4G4Ut6nGudEhpkm3v5ojIP7NOXl/EsbjNctkkzbA6TOCVE9syavXOB4+JNoSH1CTbGykQM9sZ+dS8NwvGGFZUXuwNot8YBj7qamgvDcDcTVMGQ6qdRgaCVYEbESSf8ALQj/AJBxpytkkHIMjI6fao7xSXUABBz4pGlZYyxmQQcHvnBrScPxZCKP4A4Ufh8KasuAyjiZnXcOd1uaB0jfp1x8OlUHW5bNxg7q7up1MQSdKaQSPjAz8K2RKxsJMQJHTbG3SqzcFdvKVW35ZADFVEwMMJ36fsVNJWM4G94bXHuCDcZpE9GYk7HYkjr0+NHuUcvv3Ai21i0FABY4AjG+TsMfCj3KvYtU0PfbxHXoD5Zz0Iz8+1aYqVGJPwqeRaF8HyG2B/MY3DEESQN52FTvyjhF87WbShcyUUAR1mMVObrf0H8fyoDzzjLl1lsp5Fk+IdQzgEW++xBIx09ajMq23NFKjwrYGrCyIxEzA90TAk96jtpccBnuMJ6A4GT/AE4qldu/ywLbe6B7unGY2zAienSnniHAAEwD6/nWpGaNJyu0vvDWe5/SrNu2i4CgdoAqDlt7WkH3lwaskRUFccuUXLThj5FcAbz4hBJn0g4jrQXiH5hrbTZGnUdPnXacfa7UeZe4lfvFP0D9zQ1Q5dyNEhnZmMABSIVYn0nr3oyHjaoNdKDUXUm9KKjFU+bceLNp7hiQIUMYDOcKJ9TH30IXmvH+GAFVmd8LAkDaWY7ADfJzFAAgnxLsBpkiQq5kmX91j1gkiZ3xQ0sFZbt50N24wOrLKjHSNFsqNQTOwkbGak4/ibistu47IwxNtn0NJwGIQBTj+s9flqRV7iHZ7i6EMbSB5WnYCczEnERgnsTKcKGWWUD0B/Gh/CmTojUROkoyXBAhoMtr1ZiTA+FHFwPX76iVW4EKjECIO/XPaRRBrYrP3eLQXDqgXNOqAs9cAvpwd8TjHej9t5UGlQmmk8Idqea751BGBSgV1LRpX47i0tW3uuYRFLE/D9xXm9xr3H3BdusgsrJS2GFzSNjqVWHmg7+9nAFS+1fH3uMuvYthVsW2jUw1a2ECQdJaZJAC/XNW+G5a1q2ptabdwkAhghUweoDSx+JFakW+oIcDwy3rfh62a1MOE1IRGNElCSPXxOtDb/FWLV25ai3aCEOIfUNfYW3GgEjc+X49aOcT4iDUQXEQypcSwmcCF1Fp6e8N6D8ab1si5ctvaVZBbx7TvoJHRlAn1kse9VI0HJODXV4jIJCBUfywynOoabjDrE/SivGToOldZ6rgavQlsR9apcpuBlLh9WqABrVxjY+UmCRuJNELgMTpk9prKVn7/iJcD+ILVogjSNLoG2CldA0n+6WmNqPctuTbAnIEGP8AYfgKp3WXxCWgPAVfdOxmAd5M+lKjMrggBS2WXqx7lhidsffVTRU1001WkA0mod6yEWgntNze3atm21w27lxSqFRqIJxqiR33OKMXHCqzmIAJJJgQM79K8u5vx9viuI8QWxcGxUn7GdIlAGBk/wBXferJrUVeWW/COLj6Ax0guVIOxaFkD5E9TW34a5Cfy/CvYnU1wsZ2yDLfKMUL5byi54im5YRU0gKo8sx6L+Z+lGuG4BFm2X0x5jbloE7A5hgexMVpm3VbSmpnu8NoKAAXAniS0Y84SFG3XrmgvMuKvWgz3m4XxSo0oBcU3ATnUoOn649aI3Dxl241vxbdm0syQokD/CTmevSmWLZsWyxe3xao5YeGEkSeuJBM9DFGov8AsrwLWrZJtWwWOoi1cLb9SrMEEDHlnAFaFGkEgafWPy/WhlrjzC3Dau2w0Ez4YCziGltQPw+farlvi7bEMLiycQrBp9PU/CpWarcaxAK+JptwdRGCNoCgyCYJznaobdweUi48RCknoehUeUt6kd6v8Tj3UBk+YQBIxJ3E1QY3iNKW9KgbEAEdoOof6VUXOAuMpKklhvJA2/ygD5UQ1D9ihYdzBKMCP8oPST8O1XP4g1LDWC537S2uIdFZi1oMQbS+9c1DSCSx0ACZkiR6GnWuT2UK+dLYZp0I5uETGWYsw6RjFCuX8otIGe4txw5Hh3MeVe5UmNTep2+NaLl78LbEBC39zMpn5Ax9BVa6v8iS9atMBbtm+zA5uMWYx1ycDPYUT4TgbQXwyGbrq1H6lgRneuQ27aeKrG47YA1BlUnMDoAPyqhd4y1dcrxFpJIhGXUzE9gFUlT60ZW+OJIW3bt+IgOm5puFWjpkRjvJHzqwnCsLi2wLS2UWQiSGBnEKMRv06VQ5Jbu20KJw3hrqxqdy7Gfe8yADHp8hRLjmuIviBhCSzqiamaI/vUmPjUVWXmNx7/hoQE/puJJI9No7570YDmYFshSPQZ7afvoDwHG8VdTxxattbIOldRD4O+kgpPpq+tW+Q8zZ0i4CG1EAuyS+egWIjb9aAlxKGBLMOnl7z1qHw7inDLHUMCJ+BB3+INTXndk/lkA92Un7pH1qtxBdUlAC/YkEE/UR9aIkAIU+b7MyTMY+Qob4x/8A6rX/AGj/AN6dZ/iPMbum2SAQFAZZjOdYzM9NqX+I4z/pWv8AuaqBnDcQvFsAOGdQk+aVCgkQRDbmMVGvJ7HS2MmA2MT6z+Har9ziGS2icPbE7lcgfIkRv2qkj3ROq3LMMrbJx8Onz3qwWP8AkVi7/IDuqosSjQZO5IO8/A072ea9bu3bBfxbS+7ckSoiACywCcGRuD2ruC5fZuE6rd2QMi4jQM7EkyTI2FOTnllFItAkq3hkQbaiJkDVAxBxOfvqLFlXXgrbNcum5lmUNcyRgwodskenegR9p2v3B4Si3ZQg3CwXPWInqZ9TV9+fW7UWrhuC4QfM5A07yTc93aMLPQZrMcV7TX14lEQnwlcAaIc3BPva4MswOwxNJGpNbXknOrfEahbTSFOZ6+iqIj996jbmvh3iHsuLZaBca3pAOwzkmT1xV+01sEXHhbjgAB9Af4eXE5zVlkRyG0gsDhoyCPXtUZdcCuPtRIP2lnr8YofzHl1q4R52Rl2CvCzMjUhwTI3iaJlSQQZ+R/Oo+IcKIldsgkD69P8AeiK9i0QsXH1HrGx6QAzGPkaZ/Bj+qpLFy2gCLoAA6QAJzAjFTR/efoP1qjM8Fz1bYVEtgkiC23/iMgVffiRpKcNblmOSpA+ed/nis3y7lkCf5kMfLggHr7x6VpeG4G640ghF/tA/EjNUV+a8la+Aou3rUMNYDJBHUiM/UxmgF7heD4G+Na8RdcCbZ8o82cjSRrOYkg1sLNl7R0zcuk5kAeUT1IAA+cmsxxHEfxPG22Ksnh4BXzMWkxLRpHTuM1GpRvl3FniQXu2ns7KoZnDPIkQD+IzWc557P8TaueLavW4tmbYDEOOmEIMmiPtPzziOH8K4loJrBDFnV5IgiArSMTPQz6A1W9mPae7xF8273hBSpI0gqxOIUGTO5OfWiyX67lo5pduI7ybaHJDKgmI2O5E9omtSvC3Nm4lwxxGhI+Xl3/SgfP8AxbT2ltW0RBqKsrQ06TOoEDGR8/lXcl49+KWW4h1uK3urbm2B0Mgfie9Ga1CWm06XGrpJIlvUgCKHnly6tTJbU5zbBWVzAMbmKnscYFYJcBE7OT5WjqD0ntXcTcCt0IGcZjtMfpRFThLhZri+GoCxA1eY9zGYG0ZpDzIjEW8f4f8A2q0bBJ1iBOCe4Gwg07WP7aomuWwwlyCu4x1/GoODdSTo1QMDtTGARfMXfSudgGPx/KaZw3FNclQDbxgECT9JAFQQc94u1bKi4brEmPDRpmdiUmYx0FNdrHF22tm54Z66GXWBiDMCPhFVuI4C1wiXOIur5iTLFtTNqPuqSMD0Gcb0GHt5aCQnDQ3SSI9Pj0piyW/GQ5pwiWrr27dwXVUwHA3/ANqNcX7G8Tbt+KptugXVKt5h12Pb0NBON49rlw3YCOTqlPLnuI2+Nek+yvNfEW3bFm6rBJYsp0sceYOSJkmYjc1a6dWyPMrlx3PmZmO0kk/ea2fsv7P8VaYXDd8JWElfeYj1Sfxo5Nq1xpU20DPmTEwewIEdf3ij9y0pzA1RgxNNZvWq78L4qfzGJ+g29B1pq8CF0aBMYIkZHcjvVskCJiY3P6VSW4usvHm7YHTpO8xUYTqyqSCI/wA09PWmfxY7N+/nXLJw1vpOr19R+lQlD/0//Af+1BduER5RqJ6Tj55pkKgLsoXGWwap815wtm2SAC32RO/qewq1wN7xLauwBJGY2+VQZ72oS7xlsW+HQMoMlmImR0UdPjWD4jkXE28NaYESe+3f616dw1i/bvvoCi0TMTt8BFFk4dB5mHmO5P6Vdxrnqx5jyP2Te5Dm+lq4IZVjUy7kFlxHQ/OrXHcdzIXDa8cOU6oVGr7hnO1aL2h4VryOtq2UIxrggkYkY3xO5rH3uW3rBAd9L7qDPmG4gAnPSPSqvlq0nIOPfVduBWZl/wDkYFvToY9Ij5Ue9nOGvK48S4wOS9sliBG0BgTHrq9KJ8r5iLltNY/mHEAjMCcSdqKPxAW2bnhsfQRP+1TWbdR37s4DHJg6RJHxxio1tlT7xIjYgZ+JipbHEh0DaGBPSmJcEDX73Y9PoKInW6vQiew70n8R6D6j9aq+EqFmIAX3wY2IBk6h6ChbcdwhJPirnPXrTBdTlVsr/MXX6v27fKhPMebjh2VLZEBfKjDp6HfNaW8+lS0DAkiJJ/1rzH2l5kLlxrgEKTAXEiNyfjRZNHOG/wCIC/8AyWyPVTP3Gn3f+ICM6qlolZyWIB/QV57cUTIjOw/eK5LrLsY3q46+Eep8n5vf4py1lES0CQ+t9TgwIhQNvjWd57c4i1xWviH1AToYeVYOMAiO0/nQ32Nt8Sb0WLhTbXABGnvnEzH1NHPa9OIGg32VrYxMTBPXAnp0+lGLMuCfsxxKv/8ArIb7Thz5gT2IERR7iXukqtvT73nnYLvuK8YXiHtuGRirDquOv3zW09iecXHuFbl0t5SdJ+0cfgKWJeMmt6jN9sDOMHeq78MpmTqkxkbfA1W5rzK3ati47DJ8q9W+FD+C5rcvMFt28HJJiAOu2/TNRkYHCnSApgbEdx6Heap6U7H6CiVy4AY1QTsPh2Bqt4f9/wBy/pQDL6cRekF7dtIyY1H47gVn+P8AZ+yLbXdYIU+YxpBHUL3JNWuG9nrlsw924VAkorkBvSQe/p0rO+0PMb9wm2yaFQwLY6fOTqMdfWqvM9+lN7agSAYbbAP309eVuUnwwce8ZP1Oyj1mpeUiQxGosmkgCAsGJDZk/Ada9a5fdVragKFAAGkRAxt2pbjVtjyT2e5jc4e5qmEPvqMyBsMbHtV3m3MzxNyVkKRCqZ1DHpjP31Pzqwo4m4WthQXMAiBHfTt6zWq9mOBtLbVgo1HcwPu9KJbvtmuD9gzcTW9zw+ygaj85NHOG9k+GtAAoWIEFycnadtvyrS3gDsJjIAO/7xQu+90FSAFBxBjHr/pU1L1QDieV27FzWuQ22uW0gbgEyQvWiXJXhnnSojzd4mimtiP5iC4CMYicdDtFAjwrKWDB7YJ6Zwf7u8VWaOM75MpP2MdPj8Kl/hrf9H31HbsWgigZ6CcfOk/h27t9B+tRQD2RseKHuu+ss3Wek/rXe2vLLQtm5pAfEEAz8MdKN8pt27Si3btFQMZH51a47iVt2zccDV9kRJJ32zQ1gvZ7kNwFbpD2wGypUgkRMg7xPevQRxNsJq1Kq9ZIEH51k+I9tWKMnhgXI6HYHM5jIFYnjbmpBksVyZIOTO0DbI3z0q/Wstrae0/NLd5kS06syTLQCPgD8qK+xqlrTlmkzA9B6V5dwPFhA6+GNTRBgysTt2nEn0rQ+y/MXt3VBYKpidU7THz70xeuMenvb07H9/Gh/MeBS8MsyEbFT+RxU13iNJ8xIziASPme9QoXZjKwo26hvWowl4e5gIFIULAk/KhHtDeBuW0JkRJEbT6zvg0TRWgeVV7CZJzjP31L/DhjLZ6QQD98TQULvCsbY8NipGxyZB/Onrw92B5vu/1oh/CAIVBYD47elU/EXsfvqousF05bTG5/KqlnmSHUWIAQeYnAA9Z2MZivHH5xxDJoN5yvYn9z86dx3P7122LTXIQR5VAWY2mMnv8AOni7frrSe1HN7D3C9vSQVyRBnMAj1H4UE4fmcWzZnDmSQskmBC/OPvoKonrW+9lvZy34dq68M7gticCcD44Oaq9cziMy3BOmRbu2/NGpwY22JjfFaT2U5O9xxdPuISQOjGfw9fStunDIyeGw1DrOd/jVDkPEWreq1qAhiFkgAqDGP3vU1zvWrd3iXtkalJk9I+op3D8XLAeYCIEj6Gah9oOY2rdlwzqWIhVGTPeKGch5hbYQzlnXYtMEHsNqjLRNxFsDzMv169I9afZ0xI2oJzPlitb8SdJU6tS4PrB9dh8qJ8v4pLlsFQwXpqHYUETXrnjFUwvcwcfvvUn8G/dfpV2zaALENM9+npTtJ7rTRj+I/wCHtpmhWKITLYBIPZSdhHedhRThuQcMnCsnhqF0mWdQT18xPoMj5V3tD7QGxbVlUQTEn8qFcdz9LvDrqMG6wCqTGqGAjH2Zx8zT21trDcHyR7i3bq23NpDgJu0sBiewkn5VtvYjj0a2bbIUdIEEEYORg9K1dkWrdpUGkIigdAPj+JrAcw9qbVvi7r25NtlVQyjDFREzHqc+gq/Wrb29A4lGNtgm5FeS82NxbwVgVYHYAYjqCPSK2HA+2lsrlYgYzvQnlnKL3HPcu3dVu2W8gG5H6ATSMz19Tezvskbuq7eMgnyAkgnOZrYcNya1aTwws/HJOZoI5exet2rYueGMFjJAJ2zt8a0lwMQNJg+tRLdRNy9CoXTC9R0MZ2+MVLw1kIioNhinM5VSWliOi9arpf8AETVlB64JFEWix0mDkU7xP7fuqjrCrCz3x2pf40+v1FMGU5lziy99rdx9Vu2IVAA+txjy/P8AOq/NOV8a6hxYWF91THlSNonff6ms/wCyvNLVriFe8AFAwYmG716J/wDl/BvbYm4QpJTIIJ6TG8etVu83m/GTv8h465w+tX028sLYgGN5x6d6xDOZ3PbevUOb+2nCpw7W7Ll2KFFAnGIktXlnSrHb8e57OZidzNew+xnFqeEQW1PlEdpIGTjpNeOCjfJvaC9w4YIwgjZhP0FLD8nNs9PTfaHmNqxbNxz/ADB7i/1NtMdQKj5NzU3LaF/fbpt9xrzTieLv8Uz3XJbSAsBcZJgQNqPey102b4S6rK5geZiRDdh8IxUxx64yPSEWPn1qK9bJIKxjEHrTwyrOYAknP4VV5k7lQUBOo/Z3HrUYVeO45rastuWuAdpArFNxl2TKrPX3v1rccBaCnTOSD6GcfWrngD1+g/Sqj//Z" alt="이미지 없음" style="height:this.height/(this.width/206);" />
									                  		</c:if>
									                  		<c:if test="${status.count eq 5}">
									                  		<img id="imgtag${status.count}" onload="javascript:imageReSizing(${result.conts_file_list_id}, ${status.count}, this.src  );" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ-Gz9eZgWBbu-nw7WuWZ4sDh9l3jXsVzXeRQ&usqp=CAU" alt="이미지 없음"  />
									                  		</c:if>
															-->
									                  		</span> 
									                  		<span class="mv-tit"><em> ${result.use_title} - ${result.aplc_name} </em></span> 
									                  	</a> 
									                  </li>
									                  </c:forEach>
									                  <!--  참고할 HTML 형식 
									                  <li> <a href="#tab" onClick="gradeView('ORS','AV', '','${result.grade_name}','2331226', '기타','' );"> <span class="img"> <span class="rank-ico rank-ico03"><span class="blind">${result.grade_name}</span></span> <img src="../css/searchEngine/img/movie_noimg.jpg" alt="이미지 없음"> </span> <span class="mv-tit"><em> ${result.use_title} - ${result.aplc_name} </em></span> </a> </li>
									                  <li> <a href="#tab" onClick="gradeView('ORS','AV', '','${result.grade_name}','2331226', '기타','' );"> <span class="img"> <span class="rank-ico rank-ico03"><span class="blind">${result.grade_name}</span></span> <img src="../css/searchEngine/img/movie_noimg.jpg" alt="이미지 없음"> </span> <span class="mv-tit"><em> ${result.use_title} - ${result.aplc_name} </em></span> </a> </li>
									                  -->
									                  </ul>
									                </div> 
											</ul>



											<div class="more_btn cf">
												<!--2022-12-09 더보기버튼추가-->
												<c:choose>									
													<c:when test="${empty paramMap.detailSearch_resultLine and img01ResultCount > 3}">
														<button onclick="resultPaging('img01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '3' and  img01ResultCount > 3}">
														<button onclick="resultPaging('img01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '5' and  img01ResultCount > 5}">
														<button onclick="resultPaging('img01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '10' and  img01ResultCount > 10}">
														<button onclick="resultPaging('img01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '20' and  img01ResultCount > 20}">
														<button onclick="resultPaging('img01');">결과 더보기</button>
													</c:when>
											    </c:choose>	
											</div>
										</div>
									</c:if>
									<!-- 카테고리6: 이미지 끝-->




									<!-- 카테고리7: 파일 시작-->
									<%-- <c:if test="${ ((paramMap.user_div_code ne '1' and paramMap.user_div_code ne '5' and paramMap.user_div_code ne '5A') and (paramMap.user_div_code eq '9' or paramMap.user_div_code eq '2' or paramMap.user_div_code eq '3' or paramMap.user_div_code eq '4' or paramMap.user_div_code eq '23' or paramMap.user_div_code eq '34' or paramMap.user_div_code eq '234')) and (file01Chk eq 'file01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'moni01')))}"> --%>
									<c:if test="${ ((paramMap.user_div_code eq '9')) and (file01Chk eq 'file01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'moni01')))}">
										<div class="searchrst-listbox" id="boardSearchResult0">
											<div class="searchrst-listbox" id="boardSearchResult0">
												<h3 class="c-tit01">
													<span>첨부파일</span>
												</h3>
												<!-- 건수에 콤마 넣어주기 시작 -->
												<fmt:formatNumber var="file01_count_formating" value="${file01.total_count}" type="number"/>
												<!-- 건수에 콤마 넣어주기  끝 -->
												<span class="rtxt"> <strong class="rnum" id="rnumM"
													data-cnt="0"><i class="text-green">${file01_count_formating}</i>건</strong>
												</span>
											</div>


											<ul class="searchrst-list" id="searchrst-list-B">

												<c:set var="file01ResultCount" value="${file01.total_count}"/>	

												<!-- forEach 개별적으로 수정해줘야하는 부분 -->
												<c:forEach var="result" items="${file01_resultMapList.list}"
													varStatus="status">
													<li>
														<div class="data-in-wrap">
															<div class="ptit_prevw cf">
																<!--2022-12-09 ul li 신규추가-->
																<div class="ptit">
																	<a class="ptit_a"  onclick="openViewOldMGT2('${result.rcv_no}','${result.orseq}')"
																		target="_blank" title="새창열림">
																		<p><span class="item">${result.medi_name}</span><mark>${result.use_title}</mark>&nbsp<mark>${result.ori_title}</mark></p><p>${result.aplc_name}</p>
																		</a>
																</div>
																
																
																<!-- 첨부파일 미리보기 시작 -->
																<c:if test="${result.file_ext eq 'jpg' or result.file_ext eq 'png'}" >
																	<div class="prevw">
																		<a href="/generalBicFileDown.do?fileSeqNo=${result.conts_file_list_id}"   onclick="openFilePrev('${result.conts_file_list_id}', this.href); return false;"> 
																		<span>첨부파일
																				미리보기</span>
																		</a>
																	</div>
																</c:if>
																<!-- 첨부파일 미리보기 끝-->
																
																
																
															</div>
															<!-- 레이블 구분 코드 변환하기 시작 -->
															<c:choose>
																<c:when test="${result.label_div_id eq 'CONTS'}" >
																	<c:set var="label_div_id_nm" value="대용량 첨부 파일" />
																</c:when>
																<c:when test="${result.label_div_id eq 'RCV'}" >
																	<c:set var="label_div_id_nm" value="접수 파일" />
																</c:when>
																<c:when test="${result.label_div_id eq 'FILE'} " >
																	<c:set var="label_div_id_nm" value="기타 파일" />
																</c:when>
															</c:choose>
															<!-- 레이블 구분 코드 변환하기 끝 -->
															<ul class="data-class">
																<li><i>등급분류번호</i><span>${result.rt_no}</span></li>
																<li><i>레이블 구분</i><span>${label_div_id_nm}</span></li>
																<li><i>파일 확장자명</i><span>${result.file_ext}</span></li>
																<li><i>파일크기</i><span>${result.file_size}</span></li>
																<!-- 등록일자 포맷변경 -->
																<c:set var="register_date"
																	value="${fn:substring(result.reg_date,0,8)}" />
																<p></p>
																<fmt:parseDate value="${register_date}" var="dateValue"
																	pattern="yyyyMMdd" />
																<fmt:formatDate value="${dateValue}"
																	var="register_dateValue" pattern="yyyy-MM-dd" />
																<li><i>등록일자</i><span>${register_dateValue}</span></li>
															</ul>
															<p class="pcont_ex" id="viewMoreFile1${status.index}">
																<span>파일명 :</span> ${result.file_name}
															</p>
															<c:if test="${fn:length(result.file_name) > 100}" >
																<div class="view-more" id="viewMoreFile${status.index}">
																		<a href="#" onclick="viewMoreClick('viewMoreFile${status.index}', 'viewMoreFile1${status.index}'); return false;"><span>더보기</span></a>
																</div>
															</c:if>
														</div>
													</li>
												</c:forEach>

											</ul>



											<div class="more_btn cf">
												<!--2022-12-09 더보기버튼추가-->
												<c:choose>									
													<c:when test="${empty paramMap.detailSearch_resultLine and file01ResultCount > 3}">
														<button onclick="resultPaging('file01');">결과 더보기</button>
													</c:when> 
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '3' and  file01ResultCount > 3}">
														<button onclick="resultPaging('file01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '5' and  file01ResultCount > 5}">
														<button onclick="resultPaging('file01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '10' and  file01ResultCount > 10}">
														<button onclick="resultPaging('file01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '20' and  file01ResultCount > 20}">
														<button onclick="resultPaging('file01');">결과 더보기</button>
													</c:when>
												</c:choose>	
											</div>
										</div>
									</c:if>
									<!-- 카테고리7: 파일 끝-->



									<!-- 카테고리8: 모니터링의견서 시작-->
									<%-- <c:if test="${ ((paramMap.user_div_code ne '1' and paramMap.user_div_code ne '2' and paramMap.user_div_code ne '3' and paramMap.user_div_code ne '4' and paramMap.user_div_code ne '23' and paramMap.user_div_code ne '24' and paramMap.user_div_code ne '34' and paramMap.user_div_code ne '234') and (paramMap.user_div_code eq '9' or paramMap.user_div_code eq '5' or paramMap.user_div_code eq '5A')) and (moni01Chk eq 'moni01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'file01')))}"> --%>
									<c:if test="${ ((paramMap.user_div_code eq '9')) and (moni01Chk eq 'moni01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'file01')))}">
										<div class="searchrst-listbox" id="boardSearchResult0">
											<div class="searchrst-listbox" id="boardSearchResult0">
												<h3 class="c-tit01">
													<span>모니터링의견서</span>
												</h3>
												<!-- 건수에 콤마 넣어주기 시작 -->
												<fmt:formatNumber var="moni01_count_formating" value="${moni01.total_count}" type="number"/>
												<!-- 건수에 콤마 넣어주기  끝 -->
												<span class="rtxt"> <strong class="rnum" id="rnumM"
													data-cnt="0"><i class="text-green">${moni01_count_formating}</i>건</strong>
												</span>
											</div>


											<ul class="searchrst-list" id="searchrst-list-B">

												<c:set var="moni01ResultCount" value="${moni01.total_count}"/>	

												<!-- forEach 개별적으로 수정해줘야하는 부분 -->
												<c:forEach var="result" items="${moni01_resultMapList.list}"
													varStatus="status">
													<li>
														<div class="data-in-wrap">
															<div class="ptit_prevw cf">
																<!--2022-12-09 ul li 신규추가-->
																<div class="ptit">
																	<a class="ptit_a"  onclick="javascript:ControllHelper.selectTr(event,'${result.moni_no}');">
																		<p><span class="item">${result.medi_name}</span><mark>${result.use_title}</mark>&nbsp<mark>${result.ori_title}</mark></p><p>${result.aplc_name}</p>
																		</a>
																</div>
																<!-- 
																<div class="prevw">
																	<a href="javascript:void(0);" target="_blank" onclick="openViewOldMGT2('${result.rcv_no}','${result.orseq}')"> 
																	<span>첨부파일
																			미리보기</span>
																	</a>
																</div>
																-->
															</div>

															<!-- 모니터 일자 포맷변경 시작 -->
															<c:set var="moni_date"
																value="${fn:substring(result.moni_date,0,8)}" />
															<p></p>
															<fmt:parseDate value="${moni_date}" var="dateValue"
																pattern="yyyyMMdd" />
															<fmt:formatDate value="${dateValue}"
																var="moni_dateValue" pattern="yyyy-MM-dd" />
															<!-- 모니터 일자 포맷변경 끝 -->
															<!-- 보고서 구분 코드 값을 코드명으로 변환 시작-->
															<c:choose>
																<c:when test="${result.rpt_div eq '50701'}" >
																	<c:set var="rpt_div_nm" value="등급분류" />
																</c:when>
																<c:when test="${result.rpt_div eq '50702'}" >
																	<c:set var="rpt_div_nm" value="등급미필" />
																</c:when>
																<c:when test="${result.rpt_div eq '50703'}" >
																	<c:set var="rpt_div_nm" value="뮤직비디오" />
																</c:when>
																<c:when test="${result.rpt_div eq '50704'}" >
																	<c:set var="rpt_div_nm" value="기타" />
																</c:when>
															</c:choose>	
															<!-- 보고서 구분 코드 값을 코드명으로 변환 끝-->
															<!-- proc_state(진행상태코드)값을 진행상태 명으로 변환 // proc_state_nm이라는 컬럼이 있지만, 다 null값이다. -->
															<c:choose>
																<c:when test="${result.proc_state eq '50301'}" >
																	<c:set var="proc_state_nm_moni" value="작성중(기본정보)" />
																</c:when>
																<c:when test="${result.proc_state eq '50302'}" >
																	<c:set var="proc_state_nm_moni" value="작성중(등급적절성)" />
																</c:when>
																<c:when test="${result.proc_state eq '50303'}" >
																	<c:set var="proc_state_nm_moni" value="작성중(문제점)" />
																</c:when>
																<c:when test="${result.proc_state eq '50304'}" >
																	<c:set var="proc_state_nm_moni" value="작성중(총평)" />
																</c:when>
																<c:when test="${result.proc_state eq '50305'}" >
																	<c:set var="proc_state_nm_moni" value="작성중(첨부파일)" />
																</c:when>
																<c:when test="${result.proc_state eq '50306'}" >
																	<c:set var="proc_state_nm_moni" value="제출" />
																</c:when>
																<c:when test="${result.proc_state eq '50307'}" >
																	<c:set var="proc_state_nm_moni" value="재작성요망" />
																</c:when>
																<c:when test="${result.proc_state eq '50308'}" >
																	<c:set var="proc_state_nm_moni" value="제출(재작성)" />
																</c:when>
																<c:when test="${result.proc_state eq '50309'}" >
																	<c:set var="proc_state_nm_moni" value="마감" />
																</c:when>
															</c:choose>	
															<ul class="data-class">
																<li><i>등급분류번호</i><span>${result.rt_no}</span></li>
																<li><i>사용자명</i><span>${result.user_nm}</span></li>
																<li><i>보고서 구분</i><span>${rpt_div_nm}</span></li>
																<li><i>모니터 일자</i><span>${moni_dateValue}</span></li>
																<li><i>진행 상태</i><span>${proc_state_nm_moni}</span></li>
																<li><i>상영 시간</i><span>${result.rt_time}</span></li>
															</ul>
															<p class="pcont_ex" id="viewMoreMoni1${status.index}">
																<span>서비스 경로 :</span> ${result.srvc_path}
															</p>
															<p class="pcont_ex" id="viewMoreMoni2${status.index}">
																<span>결정사유 :</span> ${result.deter_rsn}
															</p>
															<c:if test="${fn:length(result.deter_rsn) > 100}" >
															<div class="view-more" id="viewMoreMoni${status.index}">
																	<a href="#" onclick="viewMoreClick('viewMoreMoni${status.index}', 'viewMoreMoni1${status.index}' , 'viewMoreMoni2${status.index}' ); return false;"><span>더보기</span></a>
															</div>
															</c:if>
														</div>
													</li>
												</c:forEach>

											</ul>
											



											<div class="more_btn cf">
												<!--2022-12-09 더보기버튼추가-->
												<c:choose>									
													<c:when test="${empty paramMap.detailSearch_resultLine and moni01ResultCount > 3}">
														<button onclick="resultPaging('moni01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '3' and  moni01ResultCount > 3}">
														<button onclick="resultPaging('moni01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '5' and  moni01ResultCount > 5}">
														<button onclick="resultPaging('moni01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '10' and  moni01ResultCount > 10}">
														<button onclick="resultPaging('moni01');">결과 더보기</button>
													</c:when>
													<c:when test="${not empty paramMap.detailSearch_resultLine and paramMap.detailSearch_resultLine eq '20' and  moni01ResultCount > 20}">
														<button onclick="resultPaging('moni01');">결과 더보기</button>
													</c:when>
												</c:choose>
											</div>
										</div>
									</c:if>
									<!-- 카테고리8: 모니터링의견서 끝-->



								<%-- 20221219 더보기 클릭 시 페이징처리
									<c:if test="${movie01Chk eq 'movie01'}">
										<div class="pagination mt20 mb20">
											<html:pagingEx uri="/search/TotalSearchEngine.do"
												parameters="${parameters}"
												left02Img="/images/btn/btn_first.gif"
												left01Img="/images/btn/btn_prev.gif"
												right01Img="/images/btn/btn_next.gif"
												right02Img="/images/btn/btn_last.gif" />
										</div>
									</c:if>
							--%>



									 

                
                
                
                <%-- 
                 20221219 이미지, 첨부파일 예시 목록리스트
                <!-- 비디오물 검색결과 -->
                <div class="searchrst-listbox" id="boardSearchResult0">
                  <h3 class="c-tit01">
                    <span>example 1</span>
                  </h3>
                  <span class="rtxt">
                    <strong class="rnum" id="rnumM" data-cnt="0"><i class="text-green">8</i>건</strong>
                  </span>
                 
                </div>
                <!-- // 비디오물 검색결과 -->

                <!-- 이미지 검색결과 -->
                <div class="searchrst-listbox" id="boardSearchResult0">
               
                  <h3 class="c-tit01">
                    <span>example 2 이미지</span>
                  </h3>
                  <span class="rtxt">
                    <strong class="rnum" id="rnumM" data-cnt="0"><i class="text-green">8</i>건</strong>
                  </span>
                 
                </div>
                <!-- // 이미지 검색결과 -->

                <div class="schres-wrap">
             
                  <ul class="img-sch-list clm5">
                  <li> <a href="#tab" onClick="gradeView('ORS','AV', '','청소년관람불가','2349230', '기타','' );"> <span class="img"> <span class="rank-ico rank-ico04"><span class="blind">청소년관람불가</span></span> <img src="../css/searchEngine/img/movie_noimg.jpg" alt="이미지 없음"> </span> <span class="mv-tit"><em> 도쿄 범죄 수사대 - 루시 블랙맨 사건 </em></span> </a> </li>
                  <li> <a href="#tab" onClick="gradeView('ORS','AV', '','15세이상관람가','2345689', '극영화','' );"> <span class="img"> <span class="rank-ico rank-ico03"><span class="blind">15세이상관람가</span></span> <img src="../css/searchEngine/img/movie_noimg.jpg" alt="이미지 없음"> </span> <span class="mv-tit"><em> 에밀리:범죄의유혹 </em></span> </a> </li>
                  <li> <a href="#tab" onClick="gradeView('ORS','AV', '','15세이상관람가','2334066', '기타','' );"> <span class="img"> <span class="rank-ico rank-ico03"><span class="blind">15세이상관람가</span></span> <img src="../css/searchEngine/img/movie_noimg.jpg" alt="이미지 없음"> </span> <span class="mv-tit"><em> 빅 보스 - 21세기 범죄자 (시즌1) </em></span> </a> </li>
                  <li> <a href="#tab" onClick="gradeView('ORS','AV', '','15세이상관람가','2331226', '기타','' );"> <span class="img"> <span class="rank-ico rank-ico03"><span class="blind">15세이상관람가</span></span> <img src="../css/searchEngine/img/movie_noimg.jpg" alt="이미지 없음"> </span> <span class="mv-tit"><em> 로앤오더성범죄전담반시즌23-22회:포를리니바의마지막방문 </em></span> </a> </li>
                  <li> <a href="#tab" onClick="gradeView('ORS','AV', '','15세이상관람가','2331225', '기타','' );"> <span class="img"> <span class="rank-ico rank-ico03"><span class="blind">15세이상관람가</span></span> <img src="../css/searchEngine/img/movie_noimg.jpg" alt="이미지 없음"> </span> <span class="mv-tit"><em> 로앤오더성범죄전담반시즌23-21회:네죄를고하라 </em></span> </a> </li>
                  </ul>
                </div> 
                 --%>
                 
                 
                 
                 
                 
                 
                 
                 
              </div> <!-- // ch_rst_lf -->



              <!-- 결과 내 통합검색 side 시작-->
              <div class="sch_rst_rt">
                <div class="total_sch_side">
              <!-- 만약 검색어가 빈값이 아니라면 사이드 메뉴 각각 다 보여주기. -->
              <c:choose>
              	<c:when test="${not empty searchStrChk}" >   
                  <ul>
                    <li class="bTtl">통합검색</li>
                    
	                    <!-- 우측메뉴 영화 총합 구하기 -->
	                    <c:set var="side_movietotal" value="0"/>
	                      <c:forEach var="result" items="${movie01_sideInformation.list}" >
	                      	<c:set var="side_movietotal" value="${side_movietotal + result.total_side_page_count}"/>
	                      </c:forEach>
                    	<!-- 우측메뉴 영화 총합 구하기 -->
                 <c:if test="${movie01Chk eq 'movie01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'file01' or paramMap.sectionGubun eq 'moni01'))}">
                    <li>영화 (<em class="sch_category_num">${side_movietotal}</em>)
                      <ul class="sch_category">
                      
                      <c:forEach var="result" items="${movie01_sideInformation.list}" >
                          <c:if test="${result.total_side_page_count ne 0}" >
                      		 <li>
                         		 <a href="" onclick="resultSidePaging('movie01Side', `${result.totalSideUrl}`, '${result.menuName}'); return false;" > ${result.menuName} (<em class="sch_category_num">${result.total_side_page_count}</em>)</a>
                       		 </li>
                       	  </c:if> 
                      </c:forEach>
                      </ul>
                    </li>
                 </c:if>
                    
                    	<!-- 우측메뉴 비디오 총합 구하기 -->
		                    <c:set var="side_videototal" value="0"/>
		                      <c:forEach var="result" items="${video01_sideInformation.list}" >
		                      	<c:set var="side_videototal" value="${side_videototal + result.total_side_page_count}"/>
		                      </c:forEach>
                    	<!-- 우측메뉴 비디오 총합 구하기 -->
				<c:if test="${video01Chk eq 'video01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'file01' or paramMap.sectionGubun eq 'moni01'))}">
                    <li>비디오물 (<em class="sch_category_num">${side_videototal}</em>)
                      <ul class="sch_category">
                      
                        <c:forEach var="result" items="${video01_sideInformation.list}" >
                          <c:if test="${result.total_side_page_count ne 0}" >
                      		 <li>
                         		 <a href="" onclick="resultSidePaging('video01Side', `${result.totalSideUrl}`, '${result.menuName}'); return false;" >${result.menuName} (<em class="sch_category_num">${result.total_side_page_count}</em>)</a>
                       		 </li>
                       	  </c:if> 
                      </c:forEach>    
                      
                      </ul>  
                    </li>
               </c:if>     
                    	<!-- 우측메뉴 광고물 총합 구하기 -->
		                    <c:set var="side_adtotal" value="0"/>
		                      <c:forEach var="result" items="${ad01_sideInformation.list}" >
		                      	<c:set var="side_adtotal" value="${side_adtotal + result.total_side_page_count}"/>
		                      </c:forEach>
                    	<!-- 우측메뉴 광고물 총합 구하기 -->
		       <c:if test="${ad01Chk eq 'ad01' or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'file01' or paramMap.sectionGubun eq 'moni01'))}">
                    <li>광고물 (<em class="sch_category_num">${side_adtotal}</em>)
	                     <ul class="sch_category">
	                      
		                        <c:forEach var="result" items="${ad01_sideInformation.list}" >
		                          <c:if test="${result.total_side_page_count ne 0}" >
		                      		 <li>
		                         		 <a href="" onclick="resultSidePaging('ad01Side', `${result.totalSideUrl}`, '${result.menuName}'); return false;" >${result.menuName} (<em class="sch_category_num">${result.total_side_page_count}</em>)</a>
		                       		 </li>
		                       	  </c:if> 
		                       </c:forEach>    
	               
	                      </ul> 
                    </li>
               </c:if>
                
                    
                    
                    	<!-- 우측메뉴 광고물 총합 구하기 -->
		                    <c:set var="side_performtotal" value="0"/>
		                      <c:forEach var="result" items="${perform01_sideInformation.list}" >
		                      	<c:set var="side_performtotal" value="${side_performtotal + result.total_side_page_count}"/>
		                      </c:forEach>
                    	<!-- 우측메뉴 광고물 총합 구하기 -->
			<c:if test="${ perform01Chk eq 'perform01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'file01' or paramMap.sectionGubun eq 'moni01'))}">
                    <li>공연추천 (<em class="sch_category_num">${side_performtotal}</em>)
                    	  <ul class="sch_category">
	                      
		                        <c:forEach var="result" items="${perform01_sideInformation.list}" >
		                          <c:if test="${result.total_side_page_count ne 0}" >
		                      		 <li>
		                         		 <a href="" onclick="resultSidePaging('perform01Side', `${result.totalSideUrl}`, '${result.menuName}'); return false;" >${result.menuName} (<em class="sch_category_num">${result.total_side_page_count}</em>)</a>
		                       		 </li>
		                       	  </c:if> 
		                       </c:forEach>    
	                      </ul> 
                    </li>
            </c:if>       
                    	 <!-- 우측메뉴 등급분류의견서 총합 구하기 -->
		                    <c:set var="side_opintotal" value="0"/>
		                      <c:forEach var="result" items="${opin01_sideInformation.list}" >
		                      	<c:set var="side_opintotal" value="${side_opintotal + result.total_side_page_count}"/>
		                      </c:forEach>
                    	<!-- 우측메뉴 등급분류의견서 총합 구하기 -->
				 <c:if test="${ (paramMap.user_div_code eq '9') and (opin01Chk eq 'opin01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'file01' or paramMap.sectionGubun eq 'moni01')))}">     	
                    <li>등급분류의견서 (<em class="sch_category_num">${side_opintotal}</em>)
						  <ul class="sch_category">
		                        <c:forEach var="result" items="${opin01_sideInformation.list}" >
		                          <c:if test="${result.total_side_page_count ne 0}" >
		                      		 <li>
		                         		 <a href="" onclick="resultSidePaging('opin01Side', `${result.totalSideUrl}`, '${result.menuName}'); return false;" >${result.menuName} (<em class="sch_category_num">${result.total_side_page_count}</em>)</a>
		                       		 </li>
		                       	  </c:if> 
		                       </c:forEach>    
	                      
	                      </ul>                     
                    </li>
                 </c:if>
                    	<!-- 우측메뉴 이미지 총합 구하기 -->
		                    <c:set var="side_imgtotal" value="0"/>
		                      <c:forEach var="result" items="${img01_sideInformation.list}" >
		                      	<c:set var="side_imgtotal" value="${side_imgtotal + result.total_side_page_count}"/>
		                      </c:forEach>
                    	<!-- 우측메뉴 이미지 총합 구하기 -->
                <%-- <c:if test="${ ((paramMap.user_div_code ne '1') and (paramMap.user_div_code eq '9' or paramMap.user_div_code eq '5' or paramMap.user_div_code eq '5A' or paramMap.user_div_code eq '2' or paramMap.user_div_code eq '3' or paramMap.user_div_code eq '4' or paramMap.user_div_code eq '23' or paramMap.user_div_code eq '34' or paramMap.user_div_code eq '234')) and (img01Chk eq 'img01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'file01' or paramMap.sectionGubun eq 'moni01')))}"> --%>
                <c:if test="${ (img01Chk eq 'img01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'file01' or paramMap.sectionGubun eq 'moni01')))}">
                    <li>이미지 (<em class="sch_category_num">${side_imgtotal}</em>)
                   		 <ul class="sch_category">
		                        <c:forEach var="result" items="${img01_sideInformation.list}" >
		                          <c:if test="${result.total_side_page_count ne 0}" >
		                      		 <li>
		                         		 <a href="" onclick="resultSidePaging('img01Side', `${result.totalSideUrl}`, '${result.menuName}'); return false;" >${result.menuName} (<em class="sch_category_num">${result.total_side_page_count}</em>)</a>
		                       		 </li>
		                       	  </c:if> 
		                       </c:forEach>    
	                      </ul> 
	                </li>
	             </c:if>       
                    <!-- 우측메뉴 첨부파일 총합 구하기 -->
		                    <c:set var="side_filetotal" value="0"/>
		                      <c:forEach var="result" items="${file01_sideInformation.list}" >
		                      	<c:set var="side_filetotal" value="${side_filetotal + result.total_side_page_count}"/>
		                      </c:forEach>
                    	<!-- 우측메뉴 첨부파일 총합 구하기 -->      
                  <%-- <c:if test="${ ((paramMap.user_div_code ne '1' and paramMap.user_div_code ne '5' and paramMap.user_div_code ne '5A') and (paramMap.user_div_code eq '9' or paramMap.user_div_code eq '2' or paramMap.user_div_code eq '3' or paramMap.user_div_code eq '4' or paramMap.user_div_code eq '23' or paramMap.user_div_code eq '34' or paramMap.user_div_code eq '234')) and (file01Chk eq 'file01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'moni01')))}"> --%> 
                  <c:if test="${ ((paramMap.user_div_code eq '9' )) and (file01Chk eq 'file01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'moni01')))}">
	                    <li>첨부파일 (<em class="sch_category_num">${side_filetotal}</em>)
	                     	  <ul class="sch_category">
			                        <c:forEach var="result" items="${file01_sideInformation.list}" >
			                          <c:if test="${result.total_side_page_count ne 0}" >
			                      		 <li>
			                         		 <a href="" onclick="resultSidePaging('file01Side', `${result.totalSideUrl}`, '${result.menuName}'); return false;" >${result.menuName} (<em class="sch_category_num">${result.total_side_page_count}</em>)</a>
			                       		 </li>
			                       	  </c:if> 
			                       </c:forEach>    
		                      </ul> 
	                    </li>
                 </c:if>
                    
                    <!-- 우측메뉴 모니터링의견서 총합 구하기 -->
		                    <c:set var="side_monitotal" value="0"/>
		                      <c:forEach var="result" items="${moni01_sideInformation.list}" >
		                      	<c:set var="side_monitotal" value="${side_monitotal + result.total_side_page_count}"/>
		                      </c:forEach>
                    <!-- 우측메뉴 모니터링의견서 총합 구하기 -->
                 <%-- <c:if test="${ ((paramMap.user_div_code ne '1' and paramMap.user_div_code ne '2' and paramMap.user_div_code ne '3' and paramMap.user_div_code ne '4' and paramMap.user_div_code ne '23' and paramMap.user_div_code ne '24' and paramMap.user_div_code ne '34' and paramMap.user_div_code ne '234') and (paramMap.user_div_code eq '9' or paramMap.user_div_code eq '5' or paramMap.user_div_code eq '5A')) and (moni01Chk eq 'moni01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'file01')))}"> --%>
                 <c:if test="${ ((paramMap.user_div_code eq '9' )) and (moni01Chk eq 'moni01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'file01')))}">
                    <li>모니터링의견서 (<em class="sch_category_num">${side_monitotal}</em>)
                   		 <ul class="sch_category">
		                        <c:forEach var="result" items="${moni01_sideInformation.list}" >
		                          <c:if test="${result.total_side_page_count ne 0}" >
		                      		 <li>
		                         		 <a href="" onclick="resultSidePaging('moni01Side', `${result.totalSideUrl}`, '${result.menuName}'); return false;" >${result.menuName} (<em class="sch_category_num">${result.total_side_page_count}</em>)</a>
		                       		 </li>
		                       	  </c:if> 
		                       </c:forEach>    
	                      </ul>   
	                </li>
	             </c:if>
                       <!-- <li>자가등급평가 (<em class="sch_category_num">0</em>)</li> -->
                  </ul>
                </c:when>
                <c:when test="${empty searchStrChk}" >
				                		<ul>
				                    <li class="bTtl">통합검색</li>
				                    
					                    <!-- 우측메뉴 영화 총합 구하기 -->
					                    <c:set var="side_movietotal" value="0"/>
					                      <c:forEach var="result" items="${movie01_sideInformation.list}" >
					                      	<c:set var="side_movietotal" value="${side_movietotal + result.total_side_page_count}"/>
					                      </c:forEach>
				                    	<!-- 우측메뉴 영화 총합 구하기 -->
				                 <c:if test="${movie01Chk eq 'movie01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'file01' or paramMap.sectionGubun eq 'moni01'))}">
				                    <li>
				                    	<a href="javascript:resultPaging('movie01');" style="color:black;">영화 (<em class="sch_category_num">${movie01.total_count}</em>)</a>
				                    </li>
				                 </c:if>
				                    
				                    	<!-- 우측메뉴 비디오 총합 구하기 -->
						                    <c:set var="side_videototal" value="0"/>
						                      <c:forEach var="result" items="${video01_sideInformation.list}" >
						                      	<c:set var="side_videototal" value="${side_videototal + result.total_side_page_count}"/>
						                      </c:forEach>
				                    	<!-- 우측메뉴 비디오 총합 구하기 -->
								<c:if test="${video01Chk eq 'video01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'file01' or paramMap.sectionGubun eq 'moni01'))}">
				                    <li>
				                    	<a href="javascript:resultPaging('video01');" style="color:black;">비디오물 (<em class="sch_category_num">${video01.total_count}</em>)</a>
				                    </li>
				                </c:if>     
				                    	<!-- 우측메뉴 광고물 총합 구하기 -->
						                    <c:set var="side_adtotal" value="0"/>
						                      <c:forEach var="result" items="${ad01_sideInformation.list}" >
						                      	<c:set var="side_adtotal" value="${side_adtotal + result.total_side_page_count}"/>
						                      </c:forEach>
				                    	<!-- 우측메뉴 광고물 총합 구하기 -->
						       <c:if test="${ad01Chk eq 'ad01' or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'file01' or paramMap.sectionGubun eq 'moni01'))}">
				                    <li>
				                    	<a href="javascript:resultPaging('ad01');" style="color:black;">광고물 (<em class="sch_category_num">${ad01.total_count}</em>)</a>
				                    </li>
				               </c:if>
				                
				                    
				                    
				                    	<!-- 우측메뉴 광고물 총합 구하기 -->
						                    <c:set var="side_performtotal" value="0"/>
						                      <c:forEach var="result" items="${perform01_sideInformation.list}" >
						                      	<c:set var="side_performtotal" value="${side_performtotal + result.total_side_page_count}"/>
						                      </c:forEach>
				                    	<!-- 우측메뉴 광고물 총합 구하기 -->
							<c:if test="${ perform01Chk eq 'perform01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'file01' or paramMap.sectionGubun eq 'moni01'))}">
				                    <li>
				                    	<a href="javascript:resultPaging('perform01');" style="color:black;">공연추천 (<em class="sch_category_num">${perform01.total_count}</em>)</a>
				                    </li>
				            </c:if>       
				                    	 <!-- 우측메뉴 등급분류의견서 총합 구하기 -->
						                    <c:set var="side_opintotal" value="0"/>
						                      <c:forEach var="result" items="${opin01_sideInformation.list}" >
						                      	<c:set var="side_opintotal" value="${side_opintotal + result.total_side_page_count}"/>
						                      </c:forEach>
				                    	<!-- 우측메뉴 등급분류의견서 총합 구하기 -->
								 <%-- <c:if test="${ opin01Chk eq 'opin01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'file01' or paramMap.sectionGubun eq 'moni01'))}"> --%>     	
								 <c:if test="${ (paramMap.user_div_code eq '9') and (opin01Chk eq 'opin01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'file01' or paramMap.sectionGubun eq 'moni01')))}">
				                    <li>
				                    	<a href="javascript:resultPaging('opin01');" style="color:black;">등급분류의견서 (<em class="sch_category_num">${opin01.total_count}</em>)</a>
				                    </li>
				                 </c:if>
				                    	<!-- 우측메뉴 이미지 총합 구하기 -->
						                    <c:set var="side_imgtotal" value="0"/>
						                      <c:forEach var="result" items="${img01_sideInformation.list}" >
						                      	<c:set var="side_imgtotal" value="${side_imgtotal + result.total_side_page_count}"/>
						                      </c:forEach>
				                    	<!-- 우측메뉴 이미지 총합 구하기 -->
				                 <%-- <c:if test="${ ((paramMap.user_div_code ne '1') and (paramMap.user_div_code eq '9' or paramMap.user_div_code eq '5' or paramMap.user_div_code eq '5A' or paramMap.user_div_code eq '2' or paramMap.user_div_code eq '3' or paramMap.user_div_code eq '4' or paramMap.user_div_code eq '23' or paramMap.user_div_code eq '34' or paramMap.user_div_code eq '234')) and (img01Chk eq 'img01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'file01' or paramMap.sectionGubun eq 'moni01')))}"> --%>
				                 <c:if test="${  (img01Chk eq 'img01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'file01' or paramMap.sectionGubun eq 'moni01')))}">
				                    <li>
				                    	<a href="javascript:resultPaging('img01');" style="color:black;">이미지 (<em class="sch_category_num">${img01.total_count}</em>)</a>
					                </li>
					             </c:if>       
				                    <!-- 우측메뉴 파일 총합 구하기 -->
						                    <c:set var="side_filetotal" value="0"/>
						                      <c:forEach var="result" items="${file01_sideInformation.list}" >
						                      	<c:set var="side_filetotal" value="${side_filetotal + result.total_side_page_count}"/>
						                      </c:forEach>
				                    	<!-- 우측메뉴 파일 총합 구하기 -->      
				                  <%-- <c:if test="${ ((paramMap.user_div_code ne '1' and paramMap.user_div_code ne '5' and paramMap.user_div_code ne '5A') and (paramMap.user_div_code eq '9' or paramMap.user_div_code eq '2' or paramMap.user_div_code eq '3' or paramMap.user_div_code eq '4' or paramMap.user_div_code eq '23' or paramMap.user_div_code eq '34' or paramMap.user_div_code eq '234')) and (file01Chk eq 'file01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'moni01')))}"> --%>
				                  <c:if test="${ ((paramMap.user_div_code eq '9' )) and (file01Chk eq 'file01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'moni01')))}"> 
					                    <li>
					                    	<a href="javascript:resultPaging('file01');" style="color:black;">첨부파일 (<em class="sch_category_num">${file01.total_count}</em>)</a>
					                    </li>
				                 </c:if>
				                    
				                    <!-- 우측메뉴 모니터링의견서 총합 구하기 -->
						                    <c:set var="side_monitotal" value="0"/>
						                      <c:forEach var="result" items="${moni01_sideInformation.list}" >
						                      	<c:set var="side_monitotal" value="${side_monitotal + result.total_side_page_count}"/>
						                      </c:forEach>
				                    <!-- 우측메뉴 모니터링의견서 총합 구하기 -->
				                 <%-- <c:if test="${ ((paramMap.user_div_code ne '1' and paramMap.user_div_code ne '2' and paramMap.user_div_code ne '3' and paramMap.user_div_code ne '4' and paramMap.user_div_code ne '23' and paramMap.user_div_code ne '24' and paramMap.user_div_code ne '34' and paramMap.user_div_code ne '234') and (paramMap.user_div_code eq '9' or paramMap.user_div_code eq '5' or paramMap.user_div_code eq '5A')) and (moni01Chk eq 'moni01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'file01')))}"> --%>
				                 <c:if test="${ ((paramMap.user_div_code eq '9' )) and (moni01Chk eq 'moni01'  or empty paramMap.sectionGubun or (empty paramMap.sectionGubun and !(paramMap.sectionGubun eq 'movie01' or paramMap.sectionGubun eq 'video01' or paramMap.sectionGubun eq 'ad01' or paramMap.sectionGubun eq 'opin01' or paramMap.sectionGubun eq 'perform01' or paramMap.sectionGubun eq 'img01' or paramMap.sectionGubun eq 'file01')))}">
				                    <li>
				                    	<a href="javascript:resultPaging('moni01');" style="color:black;">모니터링의견서 (<em class="sch_category_num">${moni01.total_count}</em>)</a>
					                </li>
					             </c:if>
				                       <!-- <li>자가등급평가 (<em class="sch_category_num">0</em>)</li> -->
				                  </ul>
                </c:when>
                
              </c:choose>  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                </div>
                <!-- 결과 내 통합검색 side -->
                
                
                <c:if test="${not empty paramMap.suggestionStrList}" >
                <div class="total_sch_side mt-20">
                 
                  <ul>
                    <li class="bTtl">연관검색어</li>
                    <c:forEach var="result" items="${paramMap.suggestionStrList}" varStatus="stat" >
                    	<c:set value="${fn:indexOf(result,'\\\"')}"  var="suggestionInvalid" />
                    	<c:if test="${suggestionInvalid eq -1}">
                   		<li><a  href="" onclick="javascript:$('#suggestionSearchStr').val('${result}'); $('#suggestionChk').val('Y'); ControllHelper.search(); return false;">${result}</a></li>
                   		</c:if>
					</c:forEach>
					<!-- 
                    <li>범죄도시1</li> 
                    <li>12세관람</li> 
                    <li>등급분류의견서</li> 
                    <li>제작사</li> 
                    <li>폭력성</li>
                     --> 
                  </ul>
                  
                </div>
             </c:if>
                
               <c:if test="${not empty paramMap.hotKeywordResultList}" > 		 
                <div class="total_sch_side mt-20">
                  <ol>
                    <li class="bTtl">인기 검색어</li>
                <c:forEach var="result" items="${paramMap.hotKeywordResultList}" varStatus="stat" begin="0" end="2">
	                  <c:set value="${fn:indexOf(result,'\\\\')}"  var="hotkeywordInvalid" />
	                  <c:if test="${hotkeywordInvalid eq -1}" >
	                   <li><a  href="" onclick="javascript:$('#hotkeywordSearchStr').val('${result}'); $('#hotkeywordChk').val('Y'); ControllHelper.search(); return false;"><span>${stat.count}</span>${result}</a></li>
	                  </c:if>
				</c:forEach>
                  </ol>
                </div>
      			<!-- 
      			 	<li><span></span>범죄도시</li>
                    <li><span>1</span>범죄도시</li>
                    <li><span>2</span>12세관람</li>
                    <li><span>3</span>등급분류의견서</li>
                    <li><span>4</span>제작사</li>
                    <li><span>5</span>감독명</li>
                    <li><span>6</span>제작년도</li>
                    <li><span>7</span>선정성</li>
                    <li><span>8</span>폭력성</li>
                    <li><span>9</span>매체기타내용</li>
                    <li><span>10</span>희망등급</li>
      			-->
      		</c:if>
      
            
              </div>
              
              <!-- // 결과 내 통합검색 side menu 끝-->
              
              <!--  
              <c:if test="${not empty paramMap.suggestionStrList}" >
                <div class="total_sch_side mt-20">
                 
                  <ul>
                    <li class="bTtl">연관검색어</li>
                    <c:forEach var="result" items="${paramMap.suggestionStrList}" varStatus="stat" >
                   		<li><a  href="" onclick="javascript:$('#suggestionSearchStr').val('${result}'); $('#suggestionChk').val('Y'); ControllHelper.search(); return false;">${result}</a></li>
					</c:forEach>
					 
                    <li>범죄도시1</li> 
                    <li>12세관람</li> 
                    <li>등급분류의견서</li> 
                    <li>제작사</li> 
                    <li>폭력성</li>
                      
                  </ul>
                  
                </div>
             </c:if>
             -->
              
              
              
            </div> 
            <!-- // sch_rst_wrap : 검색결과 목록 -->


            <div class="gap"></div>


<%-- 
            <div class="schres-wrap"> 
              <!-- s : 검색결과 없을때 -->
              <div class="searchrst-listbox">
                <p class="searchrst-msg"><strong>""</strong>에 대한 검색결과가 없습니다.</p>
                <div class="info-box01">
                  <ul class="c-list01">
                    <li>단어의 철자가 정확한지 확인해 주시기 바랍니다.</li>
                    <li>검색어의 단어 수를 줄이거나, 다른 검색어(유사어)로 검색해 보시기 바랍니다.</li>
                    <li>일반적으로 많이 사용하는 검색어로 다시 검색해 주시기 바랍니다.</li>
                  </ul>
                </div>
                <div class="sgap"></div>
              </div>
              <!-- // e : 검색결과 없을때 --> 
            </div>
 
              <!-- 페이지 버튼 -->
              <div class="search-paging cf">
                <div class="pagelist">
                  <span class="firstpage" title="처음 페이지">
                    <span>처음페이지</span>
                  </span>
                  <span class="prevblock" title="1 페이지">
                    <span>이전페이지</span>
                  </span>
                  <strong>
                    <span>1</span>
                  </strong>
                  <a href="#" title="2 페이지">
                    <span>2</span>
                  </a>
                  <a href="#" title="3 페이지">
                    <span>3</span>
                  </a>
                  <a href="#" title="4 페이지">
                    <span>4</span>
                  </a>
                  <a href="#" title="5 페이지">
                    <span>5</span>
                  </a>
                  <a href="#" title="6 페이지">
                    <span>6</span>
                  </a>
                  <a href="#" title="7 페이지">
                    <span>7</span>
                  </a>
                  <a href="#" title="8 페이지">
                    <span>8</span>
                  </a>
                  <a href="#" title="9 페이지">
                    <span>9</span>
                  </a>
                  <a href="#" title="10 페이지">
                    <span>10</span>
                  </a>
                  <a class="nextblock" title="11 페이지">
                    <span>다음 페이지</span>
                  </a>
                  <a class="lastpage" title="마지막 페이지">
                    <span>마지막 페이지</span>
                  </a>
                </div>
              </div>
              <!-- // 페이지 버튼 -->
--%>              
              

								</div>
								<!-- // isSearch : 타이틀을 뺀 모든 컨텐츠 -->

							</div>
							<!-- // contents -->

						</div>
						<!-- // container-->
					</div>
					<!-- // container-wrap -->
				</div>
				<!-- // doc-wrap //-->
	</form>

	<script>
		$(function() {
			var dtBtn = $(".sch-btn");
			var closeBtn = $(".cg-btn");
			var ttlBtn = $(".total_sch");
			dtBtn.on("click", function() {
				$(".gr-ctrl").css("display", "block");
			});
			closeBtn.on("click", function() {
				$(".gr-ctrl").css("display", "none");
				$(".search-text").focus();
			});
			ttlBtn.on("mouseenter", function() {
				$(this).find("ul").css("display", "block");
				$(this).find("a").addClass("on");
			});
			ttlBtn.on("mouseleave", function() {
				$(this).find("ul").css("display", "none");
				$(this).find("a").removeClass("on");
			});
		});
	</script>
</body>
</html>
