<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="html" uri="/WEB-INF/tld/html.tld"%>
<html xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>

<link rel="stylesheet" type="text/css" href="../css/base.css" />
<link rel="stylesheet" type="text/css" href="../css/admin.css" />
<link rel="stylesheet" type="text/css" href="../css/search.css" />
<style>
.type02 .line{ border-bottom:2px solid #bcbcbc; }
.type02 td{background-color: #fff; }
</style>


<script type="text/javascript">

$(document).ready(function(){
	$('b').css('color','red');
});


var ControllHelper = {
	//검색	
	search : function() {
		if($('#searchStr').val() == "") {
			alert("검색어를 입력하세요");
			return;
		}else{
	        $('#fm')
	        .attr({action:'/search/SearchList2.do', method:'post', target:'_self'})
	        .submit();
		}
		
	}
};	

		
function tosearch(){
	var fm = $('#fm');
	
	if($('#searchStr').val() == "") {
		alert("검색어를 입력하세요");
		return;
	}else{
		form.action = '/search/SearchList2.do';
		from.target = "_self";
		form.submit();
	}
}


//공연 상세정보
function openViewMGT2(rcv_no,orseq,h_gubun){
	//alert(rcv_no+" / "+orseq+" / "+h_gubun);
	window.open('/view/view01/view_mgt_ajax.do?searchyn=Y&rcv_no='+rcv_no +'&orseq='+orseq +'&h_gubun='+h_gubun, '',   'width=1500,height=900,resizable=yes,scrollbars=yes,left=50,top=50,status=no');   
}

//공연 상세정보
function openViewOldMGT2(rcv_no,orseq){
	 window.open('/view/view01/view_mgt_ajax.do?searchyn=Y&rcv_no='+rcv_no +'&orseq='+orseq, '',   'width=997,height=740,resizable=yes,scrollbars=yes,left=50,top=50,status=no');   
}


function searchAllList(maingubun){
	//$('#mGubun').val(maingubun);
	$('#mainGubun').val(maingubun);
	$('#fm')//팝업에 넘길 파라미터를 가지고 있는 폼 
	.attr({action:'/search/SearchList2.do', method:'post', target:'_self'})
	.submit();
}

function selectTrR (cmn_rcvac_id){
	cw = 1000;
	ch = 900;
	sw = screen.availWidth;
	sh = screen.availHeight;
	// 열 창의 포지션
	px = (sw-cw)/2;
	py = (sh-ch)/2;
	
	$('#cmn_rcvac_id').val(cmn_rcvac_id);
	$('#rcv_no').val(cmn_rcvac_id);
	var openpop	= window.open("","신청서등록",'width='+cw+',height='+ch+',resizable=yes, scrollbars=yes,top='+py+',left='+px);
	openpop.focus();

    $('#fm')//팝업에 넘길 파라미터를 가지고 있는 폼 
    .attr({action:'/ircv/popup/ircvp055.do?searchyn=Y', method:'post', target:'신청서등록'})
    .submit();
}

//일반 첨부파일 다운로드
function fileDownload(fileSeqNo){
	$("#fileSeqNo").val(fileSeqNo);
	$('#frm')
	.attr({action:'/generalFileDown.do', method:'post', target:'downloadFrame'})
	.submit();
}

// 대용량 다운로드
function BicfileDownload(fileSeqNo){
	$("#fileSeqNo").val(fileSeqNo);
	$('#frm')
	.attr({action:'/generalBicFileDown.do', method:'post', target:''})
	.submit();
}

</script>
</head>
<body>
<form name="frm" id="frm" method="post">
	<input type="hidden" id="fileSeqNo" name="fileSeqNo"/>
</form>
<form name="fm" id="fm" method="post" >
<div class="content" style="margin: 0 0 0 0px;">
	
	
		<!-- 탭메뉴 -->
		<div class="search_result">
			<h3>통합검색</h3>	
			<div class="search_box">
				<select name="mainGubun" id="mainGubun">
					<option value="ALL" <c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL'}"> selected </c:if> >통합검색</option>
					<option value="A01" <c:if test="${paramMap.mainGubun eq 'A01'}"> selected </c:if> >영화</option>
					<option value="A02" <c:if test="${paramMap.mainGubun eq 'A02'}"> selected </c:if> >광고영화</option>
					<option value="A03" <c:if test="${paramMap.mainGubun eq 'A03'}"> selected </c:if> >영화예고편</option>
					<option value="A04" <c:if test="${paramMap.mainGubun eq 'A04'}"> selected </c:if> >영화광고선전</option>
					<option value="B01" <c:if test="${paramMap.mainGubun eq 'B01'}"> selected </c:if> >비디오</option>
					<option value="D01" <c:if test="${paramMap.mainGubun eq 'D01'}"> selected </c:if> >비디오광고선전</option>
				</select>
				<div class="search_input_bg">
					<input type="hidden" name="display" id="display" value="${paramMap.display}" />
					<input type="hidden" name="cPage" id="cPage" value="${paramMap.cPage}" />
					<input type="hidden" name="mGubun" id="mGubun" value="" />
					<input type="hidden"  name="rcv_no" id="rcv_no" />
					<input type="hidden"  name="cmn_rcvac_id" id="cmn_rcvac_id" />
					<input type="hidden"  name="searchyn" id="searchyn" value="Y"/>
					
					
				<!-- <label for="total_search" class="skip">통합검색</label> -->
				<input id="searchStr" name="searchStr"  class="tt_input" type="text" placeholder="검색어를 입력하세요." title="SEARCH" value="${paramMap.searchStr}"/>
				<input type="image" src="../images/search/btn_search.gif" onclick="javascript:ControllHelper.search();" />
				 
				</div>
                
                <div >
                <br/>
                <span style="font-weight: bold;">* 검색안내 : 영화제명(사용제명, 원제명), 신청사명, 결정등급 정보로 통합 검색을 하실 수 있습니다.</span>
                </div>
			</div>
			<ul class="tab_category">
			</ul>
			<ul class="search_list">
				<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'A01' or paramMap.mainGubun eq 'ALL' }">
					<li><span class="category">영화 ${A01_Cnt} 건</span></li>
					<c:if test="${A01_Cnt gt 0 }">
						<table class="type02 left line" summary="영화">
						<colgroup>
							<col width="9%" />
							<col />
							<col width="6%" />
							<col width="6%" />
							<col width="6%" />
							<col width="7%" />
							<col width="6%" />
							<col width="7%" />
							<col width="6%" />
							<col width="7%" />
							<col width="6%" />
							<col width="6%" />
							<col width="6%" />
							<col width="6%" />
						</colgroup>
						<c:forEach var="result" items="${A01_SCH.list }" varStatus="status">
							<tr>
								<th><span class="bold">제목(원제명)</span></th>
								<td>${result.MV_USE_TITLE}<br/>(${result.MV_ORI_TITLE})</td>
								<th><span class="bold">등급분류일자</span></th><td>${result.COMPL_DATE2_NGRAM}</td>
								<th><span class="bold">등급분류번호</span></th><td>${result.RT_NO}</td>
								<th><span class="bold">희망등급</span></th>
								<td>${result.HOPE_GRADE_NAME}</td>
								<th><span class="bold">결정등급</span></th>
								<td>${result.GRADE_CODE2_VALUE}</td>
								<th><span class="bold">결과</span></th><td>${result.PROC_STATE_CODE_VALUE}</td>
								<th><span class="bold">영화의 종</span></th><td>${result.MV_ASSO_NAME}</td>
							</tr>
							<tr>
								<th><span class="bold">신청사</span></th>
								<td>${result.APLC_NAME}</td>
								<th><span class="bold">제작년도</span></th>
								<td>${result.PROD_YEAR}</td>
								<th><span class="bold">제작국</span></th>
								<td>${result.PROD_NATI_NAME}</td>
								<th><span class="bold">감독</span></th>
								<td>${result.DIRE_NAME}</td>
								<th><span class="bold">주연</span></th>
								<td colspan = "3">${result.LEADA_NAME}</td>
								<th><span class="bold">상영시간</span></th>
								<td>${result.RT_TIME}</td>
							</tr>
							<tr>
								<th><span class="bold">줄거리</span></th>
								<td colspan = "13">${result.PLOT_CONT}</td>
							</tr>
							<tr>
								<th><span class="bold">결정의견</span></th>
								<td colspan = "13">${result.DETER_RSN}</td>
							</tr>
							<tr class="line">
								<th>
									<span class="bold">기초항목</span><br/>
									(핵심사유 : ${result.CORE_HARM_RSN})
								</th>
								<td colspan = "13">
									<span class="bold">주제 : </span>${result.RT_STD_NAME1} &nbsp;&nbsp;
									
									<span class="bold">선정성 : </span>${result.RT_STD_NAME2} &nbsp;&nbsp;
									
									<span class="bold">폭력성 : </span>${result.RT_STD_NAME3} &nbsp;&nbsp;
									
									<span class="bold">대사 : </span>${result.RT_STD_NAME4} &nbsp;&nbsp;
									
									<span class="bold">공포 : </span>${result.RT_STD_NAME5} &nbsp;&nbsp;
									
									<span class="bold">약물 : </span>${result.RT_STD_NAME6} &nbsp;&nbsp;
									
									<span class="bold">모방위험 : </span>${result.RT_STD_NAME7}
								</td>
							</tr>
						</c:forEach>
						</table>
						<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' }">
							<a class="more" onclick="searchAllList('A01');" style="line-height: 25px; padding-right: 50%; font-size: 17px; background-image:none;">+ 영화 더보기</a>
						</c:if>
					</c:if>
				</c:if>
				
				<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' or paramMap.mainGubun eq 'A02'}">
					<li><span class="category">광고영화 ${A02_Cnt} 건</span></li>
					<c:if test="${A02_Cnt gt 0 }">
					<table class="type02 left line" summary="광고영화">
						<colgroup>
							<col width="6%" />
							<col width="6%" />
							<col width="6%" />
							<col />
							<col width="6%" />
							<col width="10%" />
							<col width="6%" />
							<col width="10%" />
							<col width="6%" />
							<col width="8%" />
							<col width="6%" />
							<col width="10%" />
						</colgroup>
						<c:forEach var="result" items="${A02_SCH.list }" varStatus="status">
									<tr>
									<th><span class="bold">상영구분</span></th><td>${result.MEDI_NAME}</td>
									<th><span class="bold">제목(원제명)</span></th><td>${result.USE_TITLE}<br/>(${result.ORI_TITLE})</td>
									<th><span class="bold">등급분류일자</span></th><td>${result.COMPL_DATE2_NGRAM}</td>
									<th><span class="bold">등급분류번호</span></th><td>${result.RT_NO}</td>
									<th><span class="bold">결정등급</span></th><td>${result.DETER_OPIN_CODE2_VALUE}</td>
									<th><span class="bold">결과</span></th><td>${result.PROC_STATE_CODE_VALUE}</td>
								</tr>
 								
								<tr>
<!--								
									<th><span class="bold">첨부파일(매체)</span></th>
									<td colspan = "5">
									<c:import url="/search/SearchGetFileList2.do" charEncoding="utf-8">
										<c:param name="file_rcv_no" value="${result.RCV_NO}" />
										<c:param name="file_orseq" value="${result.ORSEQ}" />
									</c:import>
									</td>
-->									
									<th><span class="bold">신청사</span></th><td colspan = "5">${result.APLC_NAME}</td>
									<th><span class="bold">상영시간</span></th><td colspan = "5">${result.RT_TIME}</td>
								</tr>
 								
								<tr class="line">
									<th><span class="bold">결정의견</span></th><td colspan = "11">${result.DETER_RSN}</td>
								</tr>
						</c:forEach>
						</table>
						<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' }">
							<a class="more" onclick="searchAllList('A02');" style="line-height: 25px; padding-right: 50%; font-size: 17px; background-image:none;">+ 광고영화 더보기</a>
						</c:if>
					</c:if>
				</c:if>
				<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' or paramMap.mainGubun eq 'A03'}">
					<li><span class="category">영화예고편 ${A03_Cnt} 건</span></li>
					<c:if test="${A03_Cnt gt 0 }">
						<table class="type02 left line" summary="영화예고편">
						<colgroup>
							<col width="6%" />
							<col width="8%" />
							<col width="6%" />
							<col />
							<col width="6%" />
							<col width="10%" />
							<col width="6%" />
							<col width="10%" />
							<col width="6%" />
							<col width="8%" />
							<col width="6%" />
							<col width="10%" />
						</colgroup>
						<c:forEach var="result" items="${A03_SCH.list }" varStatus="status">
								<tr>
									<th><span class="bold">상영구분</span></th><td>${result.MEDI_NAME}</td>
									<th><span class="bold">제목(원제명)</span></th><td>${result.USE_TITLE}<br/>(${result.ORI_TITLE})</td>
									<th><span class="bold">등급분류일자</span></th><td>${result.COMPL_DATE2_NGRAM}</td>
									<th><span class="bold">등급분류번호</span></th><td>${result.RT_NO}</td>
									<th><span class="bold">결정등급</span></th><td>${result.DETER_OPIN_CODE2_VALUE}</td>
									<th><span class="bold">결과</span></th><td>${result.PROC_STATE_CODE_VALUE}</td>
								</tr>
								<tr>
<!--								
									<th><span class="bold">첨부파일(매체)</span></th>
									<td colspan = "5">
									<c:import url="/search/SearchGetFileList2.do" charEncoding="utf-8">
										<c:param name="file_rcv_no" value="${result.RCV_NO}" />
										<c:param name="file_orseq" value="${result.ORSEQ}" />
									</c:import>
									</td>
-->									
									<th><span class="bold">신청사</span></th><td colspan = "5">${result.APLC_NAME}</td>
									<th><span class="bold">상영시간</span></th><td colspan = "5">${result.RT_TIME}</td>
								</tr>
								<tr class="line">
									<th><span class="bold">결정의견</span></th><td colspan = "11">${result.DETER_RSN}</td>
								</tr>
						</c:forEach>
						</table>
						<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' }">
							<a class="more" onclick="searchAllList('A03');" style="line-height: 25px; padding-right: 50%; font-size: 17px; background-image:none;">+ 영화예고편 더보기</a>
						</c:if>
					</c:if>
				</c:if>
				
				<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' or paramMap.mainGubun eq 'A04' }">
					<li><span class="category">영화광고선전 ${A04_Cnt} 건</span></li>
					<c:if test="${A04_Cnt gt 0 }">
						<table class="type02 left line" summary="영화광고선전">
						<colgroup>
							<col width="6%" />
							<col width="6%" />
							<col width="6%" />
							<col />
							<col width="6%" />
							<col width="10%" />
							<col width="6%" />
							<col width="10%" />
							<col width="6%" />
							<col width="8%" />
							<col width="6%" />
							<col width="10%" />
						</colgroup>
						<c:forEach var="result" items="${A04_SCH.list }" varStatus="status">
								<tr>
									<th><span class="bold">상영구분</span></th><td>${result.MEDI_NAME}</td>
									<th><span class="bold">제목(원제명)</span></th><td>${result.USE_TITLE}<br/>(${result.ORI_TITLE})</td>
									<th><span class="bold">등급분류일자</span></th><td>${result.COMPL_DATE2_NGRAM}</td>
									<th><span class="bold">등급분류번호</span></th><td>${result.RT_NO}</td>
									<th><span class="bold">결정등급</span></th><td>${result.DETER_OPIN_CODE2_VALUE}</td>
									<th><span class="bold">결과</span></th><td>${result.PROC_STATE_CODE_VALUE}</td>
								</tr>
								<tr>
<!-- 
									<th><span class="bold">첨부파일(매체)</span></th>
									<td colspan = "5">
									<c:import url="/search/SearchGetFileList2.do" charEncoding="utf-8">
										<c:param name="file_rcv_no" value="${result.RCV_NO}" />
										<c:param name="file_orseq" value="${result.ORSEQ}" />
									</c:import>
									</td>
 -->									
									<th><span class="bold">신청사</span></th><td colspan = "5">${result.APLC_NAME}</td>
									<th><span class="bold">상영시간</span></th><td colspan = "5">${result.RT_TIME}</td>
								</tr>
								<tr class="line">
									<th><span class="bold">결정의견</span></th><td colspan = "11">${result.DETER_RSN}</td>
								</tr>						
						</c:forEach>
						</table>
						<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' }">
							<a class="more" onclick="searchAllList('A04');" style="line-height: 25px; padding-right: 50%; font-size: 17px; background-image:none;">+ 영화광고선전 더보기</a>
						</c:if>
					</c:if>
				</c:if>
				
				<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' or paramMap.mainGubun eq 'B01'}">
					<li><span class="category">비디오 ${B01_Cnt} 건</span></li>
					<c:if test="${B01_Cnt gt 0 }">
						<table class="type02 left line" summary="비디오">
						<colgroup>
							<col width="8%" />
							<col />
							<col width="6%" />
							<col width="6%" />
							<col width="6%" />
							<col width="7%" />
							<col width="6%" />
							<col width="7%" />
							<col width="6%" />
							<col width="9%" />
							<col width="5%" />
							<col width="5%" />
							<col width="6%" />
							<col width="6%" />
						</colgroup>
						<c:forEach var="result" items="${B01_SCH.list}" varStatus="status">
							<tr>
								<th><span class="bold">제목(원제명)</span></th>
								<td>${result.USE_TITLE}<br/>(${result.ORI_TITLE})</td>
								<th><span class="bold">등급분류일자</span></th><td>${result.COMPL_DATE2_NGRAM}</td>
								<th><span class="bold">등급분류번호</span></th><td>${result.RT_NO_NGRAM}</td>
								<th><span class="bold">희망등급</span></th>
								<td>${result.GRADE_CODE2_VALUE}</td>
								<th><span class="bold">결정등급</span></th>
								<td>
									<c:if test='${result.DETER_OPIN_CODE2_VALUE ne "자료제출"}'>
										${result.GRADE_CODE2_VALUE}
									</c:if> 
                                    <c:if test="${not empty result.DETER_OPIN_CODE2_VALUE}">
                                        ( <c:out value='${result.DETER_OPIN_CODE2_VALUE}' /> )
                                    </c:if>
								</td>
								<th><span class="bold">결과</span></th>
								<td>${result.DETER_OPIN_CODE2_VALUE}</td>
								<th><span class="bold">영화의 종</span></th><td>${result.MV_ASSO_NAME}</td>
							</tr>
							<tr>
								<th><span class="bold">신청사</span></th>
								<td>${result.APLC_NAME}</td>
								<th><span class="bold">제작년도</span></th>
								<td>${result.PROD_YEAR}</td>
								<th><span class="bold">제작국</span></th>
								<td>${result.PROD_NATI_NAME}</td>
								<th><span class="bold">감독</span></th>
								<td>${result.DIRE_NAME}</td>
								<th><span class="bold">주연</span></th>
								<td colspan = "3">${result.LEADA_NAME}</td>
								<th><span class="bold">상영시간</span></th>
								<td>${result.RT_TIME}</td>
							</tr>
							<tr>
								<th><span class="bold">줄거리</span></th>
								<td colspan = "13">${result.PLOT_CONT}</td>
							</tr>
							<tr>
								<th><span class="bold">결정의견</span></th>
								<td colspan = "13">${result.DETER_RSN}</td>
							</tr>
							<tr class="line">
								<th>
									<span class="bold">기초항목</span><br/>
									(핵심사유 : ${result.CORE_HARM_RSN})
								</th>
								<td colspan = "13">
									<span class="bold">주제 : </span>${result.RT_STD_NAME1} &nbsp;&nbsp;
									
									<span class="bold">선정성 : </span>${result.RT_STD_NAME2} &nbsp;&nbsp;
									
									<span class="bold">폭력성 : </span>${result.RT_STD_NAME3} &nbsp;&nbsp;
									
									<span class="bold">대사 : </span>${result.RT_STD_NAME4} &nbsp;&nbsp;
									
									<span class="bold">공포 : </span>${result.RT_STD_NAME5} &nbsp;&nbsp;
									
									<span class="bold">약물 : </span>${result.RT_STD_NAME6} &nbsp;&nbsp;
									
									<span class="bold">모방위험 : </span>${result.RT_STD_NAME7}
								</td>
							</tr>
						</c:forEach>
						</table>
						<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' }">
							<a class="more" onclick="searchAllList('B01');" style="line-height: 25px; padding-right: 50%; font-size: 17px; background-image:none;">+ 비디오 더보기</a>
						</c:if>
					</c:if>
				</c:if>
				<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' or paramMap.mainGubun eq 'D01'}">
				    <li><span class="category">비디오광고선전 ${D01_Cnt} 건</span></li>
					<c:if test="${D01_Cnt gt 0 }">
						<table class="type02 left line" summary="비디오광고선전">
						<colgroup>
							<col width="6%" />
							<col width="8%" />
							<col width="6%" />
							<col />
							<col width="6%" />
							<col width="10%" />
							<col width="6%" />
							<col width="10%" />
							<col width="6%" />
							<col width="8%" />
							<col width="6%" />
							<col width="10%" />
						</colgroup>
						<c:forEach var="result" items="${D01_SCH.list}" varStatus="status">
								<tr>
									<th><span class="bold">상영구분</span></th><td>비디오광고선전</td>
									<th><span class="bold">제목(원제명)</span></th><td>${result.USE_TITLE}<br/>(${result.ORI_TITLE})</td>
									<th><span class="bold">등급분류일자</span></th><td>${result.COMPL_DATE2_NGRAM}</td>
									<th><span class="bold">등급분류번호</span></th><td>${result.RT_NO}</td>
									<th><span class="bold">결정등급</span></th><td>${result.DETER_OPIN_CODE2_VALUE}</td>
									<th><span class="bold">결과</span></th><td>${result.PROC_STATE_CODE_VALUE}</td>
								</tr>
								<tr>
<!-- 								
									<th><span class="bold">첨부파일(매체)</span></th>
									<td colspan = "5">
									<c:import url="/search/SearchGetFileList2.do" charEncoding="utf-8">
										<c:param name="file_rcv_no" value="${result.RCV_NO}" />
										<c:param name="file_orseq" value="${result.ORSEQ}" />
									</c:import>
									</td>
 -->									
									<th><span class="bold">신청사</span></th><td colspan = "5">${result.APLC_NAME}</td>
									<th><span class="bold">상영시간</span></th><td colspan = "5">${result.RT_TIME}</td>
								</tr>
								<tr class="line">
									<th><span class="bold">결정의견</span></th><td colspan = "11">${result.DETER_RSN}</td>
								</tr>
						</c:forEach>
						</table>
						<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' }">
							<a class="more" onclick="searchAllList('D01');" style="line-height: 25px; padding-right: 50%; font-size: 17px; background-image:none;">+ 비디오광고선전 더보기</a>
						</c:if>
					</c:if>
				</c:if>
			</ul>
		</div>
	
	<c:if test="${paramMap.mainGubun eq 'A01' or paramMap.mainGubun eq 'A02' or paramMap.mainGubun eq 'A03' or paramMap.mainGubun eq 'A04' or paramMap.mainGubun eq 'B01' or paramMap.mainGubun eq 'B02' or paramMap.mainGubun eq 'B03' or paramMap.mainGubun eq 'C01' or paramMap.mainGubun eq 'C02' or paramMap.mainGubun eq 'D01'}">
		<div class="pagination mt20 mb20">
			<html:pagingEx uri="/search/SearchList2.do" parameters="${parameters}" left02Img="/images/btn/btn_first.gif" left01Img="/images/btn/btn_prev.gif" right01Img="/images/btn/btn_next.gif" right02Img="/images/btn/btn_last.gif"  />
		</div>
	</c:if>
	<!-- 탭메뉴 끝 -->
</div>

</form>				

<!-- 본문 메인 끝-->
</body>
</html>
