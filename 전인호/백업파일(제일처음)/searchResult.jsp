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
	        .attr({action:'/search/SearchList.do', method:'post', target:'_self'})
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
		form.action = '/search/SearchList.do';
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
	.attr({action:'/search/SearchList.do', method:'post', target:'_self'})
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
					<option value="B02" <c:if test="${paramMap.mainGubun eq 'B02'}"> selected </c:if> >비디오복제배급국내</option>
					<option value="B03" <c:if test="${paramMap.mainGubun eq 'B03'}"> selected </c:if> >비디오복제배급국외</option>
					<option value="D01" <c:if test="${paramMap.mainGubun eq 'D01'}"> selected </c:if> >비디오광고선전</option>
					<option value="C01" <c:if test="${paramMap.mainGubun eq 'C01'}"> selected </c:if> >공연신규</option>
					<option value="C02" <c:if test="${paramMap.mainGubun eq 'C02'}"> selected </c:if> >공연변경</option>
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
                <span style="font-weight: bold;">* 검색안내 : 제명, 신청사, 감독, 주연, 결정등급, 등급분류번호(ex:2014-MF00426), 등급분류일자(ex:2014-02-01) 정보로 통합 검색을 하실 수 있습니다.</span>
                </div>
			</div>
			<ul class="tab_category">
			</ul>
			<ul class="search_list">
				<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'A01' or paramMap.mainGubun eq 'ALL' }">
					<li><span class="category">영화 ${A01_Cnt} 건</span></li>
					<c:if test="${A01_Cnt gt 0 }">
						<ul class="list">
						<c:forEach var="result" items="${A01_SCH.list }" varStatus="status">
							<li>
								<c:if test="${result.H_GUBUN eq 'R'}" >
	                            	<a class="title"   onclick="selectTrR('${result.RCV_NO}')">
	                            		<!-- 원제목, 줄거리, 감독, 주연, -->
	                            		${result.MV_ORI_TITLE} &nbsp;|&nbsp;
                                        ${result.APLC_NAME}
	                            	</a>
	                            </c:if>
	                            <c:if test="${result.H_GUBUN eq 'O'}" >
	                            	<a class="title"  onclick="openViewOldMGT2('${result.RCV_NO}','${result.ORSEQ}')">
	                            	<!-- 원제목, 줄거리, 감독, 주연, -->
	                            		${result.MV_ORI_TITLE} &nbsp;|&nbsp;
                                        ${result.APLC_NAME}
									</a>            
	                            </c:if> 
	                            <c:if test="${result.H_GUBUN ne 'O' and result.H_GUBUN ne 'R'}" >
		                        	<a class="title"  onclick="openViewMGT2('${result.RCV_NO}','${result.ORSEQ}','${result.H_GUBUN}')">
		                        	<!-- 원제목, 줄거리, 감독, 주연, -->
	                            		${result.MV_ORI_TITLE} &nbsp;|&nbsp;
                                        ${result.APLC_NAME}
									</a>
	                            </c:if>            
								<ul class="info">
									<li><span class="bold">제목 : </span>${result.MV_USE_TITLE}</li>
									<li><span class="bold">접수일자 : </span>${result.RCV_DATE}</li>
									<!-- 20170412 Mod 등급분류일자, 등급분류번호의 경우 '-' 부호가 특수문자로 인지하여 정상적으로 조회가 안되는 현상이 있으므로, 화면상에서 표현을 위해 NGRAM으로 변경 -->
									<li><span class="bold">등급분류일자 : </span>${result.COMPL_DATE2_NGRAM}</li>
									<li><span class="bold">등급분류번호 : </span>${result.RT_NO_NGRAM}</li>
									<li><span class="bold">결정등급 :</span>${result.GRADE_CODE2_VALUE}</li>
									<li><span class="bold">진행상태 : </span>${result.PROC_STATE_CODE_VALUE}</li>
									<li><span class="bold">상태 : </span>${result.STATE_CODE_VALUE}</li>
								</ul>
                                <span style="display: block; margin-right: 20px;"><span style="font-weight: bold; ">줄거리 : </span>${result.PLOT_CONT}</span><br>
                                <span style="display: block; margin-right: 20px;"><span style="font-weight: bold; ">결정의견 : </span>${result.DETER_RSN}</span>
							</li>
						</c:forEach>
						</ul>
						<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' }">
							<a class="more" onclick="searchAllList('A01');" style="line-height: 25px; padding-right: 50%; font-size: 17px; background-image:none;">+ 영화 더보기</a>
						</c:if>
					</c:if>
				</c:if>
				
				<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' or paramMap.mainGubun eq 'A02'}">
					<li><span class="category">광고영화 ${A02_Cnt} 건</span></li>
					<c:if test="${A02_Cnt gt 0 }">
						<ul class="list">
						<c:forEach var="result" items="${A02_SCH.list }" varStatus="status">
							<li>
	                            <c:if test="${result.H_GUBUN eq 'O'}" >
	                            	<a class="title"  onclick="openViewOldMGT2('${result.RCV_NO}','${result.ORSEQ}')">
	                            	<!-- 원제목,감독,주연,배급사 -->
	                            	${result.MV_ORI_TITLE} &nbsp;|&nbsp;
	                            	${result.APLC_NAME}
	                            	</a>            
	                            </c:if> 
	                            <c:if test="${result.H_GUBUN ne 'O'}" >
		                        	<a class="title"  onclick="openViewMGT2('${result.RCV_NO}','${result.ORSEQ}','${result.H_GUBUN}')">
		                        	${result.MV_ORI_TITLE} &nbsp;|&nbsp;
	                            	${result.APLC_NAME}
									</a>
	                            </c:if> 
								<ul class="info">
									<li><span class="bold">제목 : </span>${result.MV_USE_TITLE}</li>
									<li><span class="bold">접수일자 : </span>${result.RCV_DATE}</li>
									<!-- 20170412 Mod 등급분류일자, 등급분류번호의 경우 '-' 부호가 특수문자로 인지하여 정상적으로 조회가 안되는 현상이 있으므로, 화면상에서 표현을 위해 NGRAM으로 변경 -->
									<li><span class="bold">등급분류일자 : </span>${result.COMPL_DATE2_NGRAM}</li>
									<li><span class="bold">등급분류번호 : </span>${result.RT_NO_NGRAM}</li>
									<li>
										<span class="bold">결정등급 :</span>
										<c:choose>
											<c:when test = "${result.MEDI_CODE == '17505' && result.DETER_OPIN_CODE2 == '10402'}">전체관람가</c:when>
												<c:when test = "${(result.MEDI_CODE == '17503' || result.MEDI_CODE == '17504') && result.GRADE_CODE2 == '10411'}"><font color="blue">자료제출</font></c:when>
												<c:otherwise><c:if test="${result.DETER_OPIN_CODE2 eq '10413' }"><c:out value='${result.DETER_OPIN_CODE2_VALUE}' /></c:if>
													<c:if test="${result.DETER_OPIN_CODE2 ne '10413' }"><font color="red"><c:out value='${result.DETER_OPIN_CODE2_VALUE}' /></font></c:if>
												</c:otherwise>
										</c:choose>
									</li>
									<li><span class="bold">진행상태 : </span>${result.PROC_STATE_CODE_VALUE}</li>
									<li><span class="bold">상태 : </span>${result.STATE_CODE_VALUE}</li>
								</ul>	
                                <span style="display: block; margin-right: 20px;"><span style="font-weight: bold; ">결정의견 : </span>${result.DETER_RSN}</span>
                                <!-- 
                                <span style="display: block; margin-right: 20px;"><span style="font-weight: bold; ">증빙자료 : </span></br>
								<c:import url="/search/SearchGetFileList.do" charEncoding="utf-8">
									<c:param name="file_rcv_no" value="${result.RCV_NO}" />
									<c:param name="file_orseq" value="${result.ORSEQ}" />
								</c:import>
                                </span>
                                 -->
							</li>
						</c:forEach>
						</ul>
						<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' }">
							<a class="more" onclick="searchAllList('A02');" style="line-height: 25px; padding-right: 50%; font-size: 17px; background-image:none;">+ 광고영화 더보기</a>
						</c:if>
					</c:if>
				</c:if>
				<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' or paramMap.mainGubun eq 'A03'}">
					<li><span class="category">영화예고편 ${A03_Cnt} 건</span></li>
					<c:if test="${A03_Cnt gt 0 }">
						<ul class="list">
						<c:forEach var="result" items="${A03_SCH.list }" varStatus="status">
							<li>
	                            <c:if test="${result.H_GUBUN eq 'O'}" >
	                            	<a class="title"  onclick="openViewOldMGT2('${result.RCV_NO}','${result.ORSEQ}')">
	                            	${result.MV_ORI_TITLE} &nbsp;|&nbsp;
	                            	${result.APLC_NAME}
									</a>            
	                            </c:if> 
	                            <c:if test="${result.H_GUBUN ne 'O'}" >
		                        	<a class="title"  onclick="openViewMGT2('${result.RCV_NO}','${result.ORSEQ}','${result.H_GUBUN}')">
									${result.MV_ORI_TITLE} &nbsp;|&nbsp;
	                            	${result.APLC_NAME}
									</a>
	                            </c:if> 
								<ul class="info">
									<li><span class="bold">제목 : </span>${result.MV_USE_TITLE}</li>
									<li><span class="bold">접수일자 : </span>${result.RCV_DATE}</li>
									<!-- 20170412 Mod 등급분류일자, 등급분류번호의 경우 '-' 부호가 특수문자로 인지하여 정상적으로 조회가 안되는 현상이 있으므로, 화면상에서 표현을 위해 NGRAM으로 변경 -->
									<li><span class="bold">등급분류일자 : </span>${result.COMPL_DATE2_NGRAM}</li>
									<li><span class="bold">등급분류번호 : </span>${result.RT_NO_NGRAM}</li>
									<li>
										<span class="bold">결정등급 :</span>
										<c:choose>
											<c:when test = "${result.MEDI_CODE == '17505' && result.DETER_OPIN_CODE2 == '10402'}">전체관람가</c:when>
												<c:when test = "${(result.MEDI_CODE == '17503' || result.MEDI_CODE == '17504') && result.GRADE_CODE2 == '10411'}"><font color="blue">자료제출</font></c:when>
												<c:otherwise><c:if test="${result.DETER_OPIN_CODE2 eq '10413' }"><c:out value='${result.DETER_OPIN_CODE2_VALUE}' /></c:if>
													<c:if test="${result.DETER_OPIN_CODE2 ne '10413' }"><font color="red"><c:out value='${result.DETER_OPIN_CODE2_VALUE}' /></font></c:if>
												</c:otherwise>
										</c:choose>
									</li>
									<li><span class="bold">진행상태 : </span>${result.PROC_STATE_CODE_VALUE}</li>
									<li><span class="bold">상태 : </span>${result.STATE_CODE_VALUE}</li>
								</ul>
                                <span style="display: block; margin-right: 20px;"><span style="font-weight: bold; ">결정의견 : </span>${result.DETER_RSN}</span>
                                <!-- 
                                <span style="display: block; margin-right: 20px;"><span style="font-weight: bold; ">증빙자료 : </span></br>
								<c:import url="/search/SearchGetFileList.do" charEncoding="utf-8">
									<c:param name="file_rcv_no" value="${result.RCV_NO}" />
									<c:param name="file_orseq" value="${result.ORSEQ}" />
								</c:import></span>
								 -->	
							</li>
						</c:forEach>
						</ul>
						<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' }">
							<a class="more" onclick="searchAllList('A03');" style="line-height: 25px; padding-right: 50%; font-size: 17px; background-image:none;">+ 영화예고편 더보기</a>
						</c:if>
					</c:if>
				</c:if>
				
				<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' or paramMap.mainGubun eq 'A04' }">
					<li><span class="category">영화광고선전 ${A04_Cnt} 건</span></li>
					<c:if test="${A04_Cnt gt 0 }">
						<ul class="list">
						<c:forEach var="result" items="${A04_SCH.list }" varStatus="status">
							<li>
	                            <c:if test="${result.H_GUBUN eq 'O'}" >
	                            	<a class="title"  onclick="openViewOldMGT2('${result.RCV_NO}','${result.ORSEQ}')">
	                            	${result.MV_ORI_TITLE} &nbsp;|&nbsp;
	                            	${result.APLC_NAME}
	                            	</a>            
	                            </c:if> 
	                            <c:if test="${result.H_GUBUN ne 'O'}" >
		                        	<a class="title"  onclick="openViewMGT2('${result.RCV_NO}','${result.ORSEQ}','${result.H_GUBUN}')">
									${result.MV_ORI_TITLE} &nbsp;|&nbsp;
	                            	${result.APLC_NAME} 
		                        	</a>
	                            </c:if> 
								<ul class="info">
									<li><span class="bold">제목 : </span>${result.MV_USE_TITLE}</li>
									<li><span class="bold">접수일자 : </span>${result.RCV_DATE}</li>
									<!-- 20170412 Mod 등급분류일자, 등급분류번호의 경우 '-' 부호가 특수문자로 인지하여 정상적으로 조회가 안되는 현상이 있으므로, 화면상에서 표현을 위해 NGRAM으로 변경 -->
									<li><span class="bold">등급분류일자 : </span>${result.COMPL_DATE2_NGRAM}</li>
									<li><span class="bold">등급분류번호 : </span>${result.RT_NO_NGRAM}</li>
									<li>
										<span class="bold">결정등급 :</span>
										<c:choose>
											<c:when test = "${result.MEDI_CODE == '17505' && result.DETER_OPIN_CODE2 == '10402'}">전체관람가</c:when>
												<c:when test = "${(result.MEDI_CODE == '17503' || result.MEDI_CODE == '17504') && result.GRADE_CODE2 == '10411'}">자료제출</c:when>
												<c:otherwise><c:if test="${result.DETER_OPIN_CODE2 eq '10413' }"><c:out value='${result.DETER_OPIN_CODE2_VALUE}' /></c:if>
													<c:if test="${result.DETER_OPIN_CODE2 ne '10413' }"><c:out value='${result.DETER_OPIN_CODE2_VALUE}' /></c:if>
												</c:otherwise>
										</c:choose>
									</li>
									<li><span class="bold">진행상태 : </span>${result.PROC_STATE_CODE_VALUE}</li>
									<li><span class="bold">상태 : </span>${result.STATE_CODE_VALUE}</li>
									<c:import url="/search/SearchGetEtcInfo.do" charEncoding="utf-8">
										<c:param name="etc_rcv_no" value="${result.RCV_NO}" />
										<c:param name="etc_orseq" value="${result.ORSEQ}" />
									</c:import>
								</ul>
                                <span style="display: block; margin-right: 20px;"><span style="font-weight: bold; ">결정의견 : </span>${result.DETER_RSN}</span>
                                <!-- 
                                <span style="display: block; margin-right: 20px;"><span style="font-weight: bold; ">증빙자료 : </span></br>
								<c:import url="/search/SearchGetFileList.do" charEncoding="utf-8">
									<c:param name="file_rcv_no" value="${result.RCV_NO}" />
									<c:param name="file_orseq" value="${result.ORSEQ}" />
								</c:import></span>
								 -->	
							</li>
						</c:forEach>
						</ul>
						<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' }">
							<a class="more" onclick="searchAllList('A04');" style="line-height: 25px; padding-right: 50%; font-size: 17px; background-image:none;">+ 영화광고선전 더보기</a>
						</c:if>
					</c:if>
				</c:if>
				
				<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' or paramMap.mainGubun eq 'B01'}">
					<li><span class="category">비디오 ${B01_Cnt} 건</span></li>
					<c:if test="${B01_Cnt gt 0 }">
						<ul class="list">
						<c:forEach var="result" items="${B01_SCH.list}" varStatus="status">
							<li>
	                            <c:if test="${result.H_GUBUN eq 'O'}" >
	                            	<a class="title"  onclick="openViewOldMGT2('${result.RCV_NO}','${result.ORSEQ}')">
	                            		<!-- 줄거리/내용, 원제목, 감독, 주연 -->
	                            		${result.ORI_TITLE} &nbsp;|&nbsp;
                                        ${result.APLC_NAME}
	                            	</a>         
	                            </c:if> 
	                            <c:if test="${result.H_GUBUN ne 'O'}" >
		                        	<a class="title"  onclick="openViewMGT2('${result.RCV_NO}','${result.ORSEQ}','${result.H_GUBUN}')">
		                        		<!-- 줄거리/내용, 원제목, 감독, 주연 -->
	                            		${result.ORI_TITLE} &nbsp;|&nbsp;
                                        ${result.APLC_NAME}
		                        	</a>
	                            </c:if> 
								<ul class="info">
									<li>
										<span class="bold">사용제목 : </span>
										${result.USE_TITLE}
										<c:if test="${not empty eo.DOMST_FORGN_DIV_CODE_VALUE}" >
                                        	( <c:out value='${eo.DOMST_FORGN_DIV_CODE_VALUE}' /> )
                                        </c:if>
									</li>
									<li><span class="bold">접수일자 : </span>${result.RCV_DATE}</li>
									<!-- 20170412 Mod 등급분류일자, 등급분류번호의 경우 '-' 부호가 특수문자로 인지하여 정상적으로 조회가 안되는 현상이 있으므로, 화면상에서 표현을 위해 NGRAM으로 변경 -->
									<li><span class="bold">등급분류일자 : </span>${result.COMPL_DATE2_NGRAM}</li>
									<li><span class="bold">등급분류번호 : </span>${result.RT_NO_NGRAM}</li>
									<li>
										<span class="bold">결정등급 :</span>
										<c:if test='${result.DETER_OPIN_CODE2_VALUE ne "자료제출"}'>
											${result.GRADE_CODE2_VALUE}
										</c:if> 
	                                    <c:if test="${not empty result.DETER_OPIN_CODE2_VALUE}">
	                                        ( <c:out value='${result.DETER_OPIN_CODE2_VALUE}' /> )
	                                    </c:if>
									</li>
									<li><span class="bold">진행상태 : ${result.PROC_STATE_CODE_VALUE}</span></li>
									<li><span class="bold">상태 : </span>${result.STATE_CODE_VALUE}</li>
								</ul>	
                                <span style="display: block; margin-right: 20px;"><span style="font-weight: bold; ">줄거리 : </span>${result.PLOT_CONT}</span><br>
                                <span style="display: block; margin-right: 20px;"><span style="font-weight: bold; ">결정의견 : </span>${result.DETER_RSN}</span> 
							</li>
						</c:forEach>
						</ul>
						<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' }">
							<a class="more" onclick="searchAllList('B01');" style="line-height: 25px; padding-right: 50%; font-size: 17px; background-image:none;">+ 비디오 더보기</a>
						</c:if>
					</c:if>
				</c:if>
				
				
				<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' or paramMap.mainGubun eq 'B02'}">
					<li><span class="category">비디오복제배급(국내) ${B02_Cnt} 건</span></li>
					<c:if test="${B02_Cnt gt 0 }">
						<ul class="list">
						<c:forEach var="result" items="${B02_SCH.list}" varStatus="status">
							<li>
	                            <c:if test="${result.H_GUBUN eq 'O'}" >
	                            	<a class="title"  onclick="openViewOldMGT2('${result.RCV_NO}','${result.ORSEQ}')">
	                            		<!-- 줄거리/내용, 원제목, 감독, 주연 -->
	                            		${result.ORI_TITLE} &nbsp;|&nbsp;
                                        ${result.APLC_NAME}
	                            	</a>            
	                            </c:if> 
	                            <c:if test="${result.H_GUBUN ne 'O'}" >
		                        	<a class="title"  onclick="openViewMGT2('${result.RCV_NO}','${result.ORSEQ}','${result.H_GUBUN}')">
		                        		<!-- 줄거리/내용, 원제목, 감독, 주연 -->
	                            		${result.ORI_TITLE} &nbsp;|&nbsp;
                                        ${result.APLC_NAME}
		                        	</a>
	                            </c:if> 
								<ul class="info">
									<li>
										<span class="bold">사용제목 : </span>
										${result.USE_TITLE}
										<c:if test="${not empty eo.DOMST_FORGN_DIV_CODE_VALUE}" >
                                        	( <c:out value='${eo.DOMST_FORGN_DIV_CODE_VALUE}' /> )
                                        </c:if>
									</li>
									<li><span class="bold">접수일자 : </span>${result.RCV_DATE}</li>
									<!-- 20170412 Mod 등급분류일자, 등급분류번호의 경우 '-' 부호가 특수문자로 인지하여 정상적으로 조회가 안되는 현상이 있으므로, 화면상에서 표현을 위해 NGRAM으로 변경 -->
									<li><span class="bold">등급분류일자 : </span>${result.COMPL_DATE2_NGRAM}</li>
									<li><span class="bold">등급분류번호 : </span>${result.RT_NO_NGRAM}</li>
									<li>
										<span class="bold">결정등급 :</span>
										<c:if test='${result.DETER_OPIN_CODE2_VALUE ne "자료제출"}'>
											${result.GRADE_CODE2_VALUE}
										</c:if> 
	                                    <c:if test="${not empty result.DETER_OPIN_CODE2_VALUE}">
	                                        ( <c:out value='${result.DETER_OPIN_CODE2_VALUE}' /> )
	                                    </c:if>
									</li>
									<li><span class="bold">진행상태 : ${result.PROC_STATE_CODE_VALUE}</span></li>
									<li><span class="bold">상태 : </span>${result.STATE_CODE_VALUE}</li>
								</ul>	
							</li>
						</c:forEach>
						</ul>
						<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' }">
							<a class="more" onclick="searchAllList('B02');" style="line-height: 25px; padding-right: 50%; font-size: 17px; background-image:none;">+ 비디오복제배급 더보기</a>
						</c:if>
					</c:if>
				</c:if>
				
				<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' or paramMap.mainGubun eq 'B03'}">
					<li><span class="category">비디오복제배급(국외) ${B03_Cnt} 건</span></li>
					<c:if test="${B03_Cnt gt 0 }">
						<ul class="list">
						<c:forEach var="result" items="${B03_SCH.list}" varStatus="status">
							<li>
	                            <c:if test="${result.H_GUBUN eq 'O'}" >
	                            	<a class="title"  onclick="openViewOldMGT2('${result.RCV_NO}','${result.ORSEQ}')">
	                            		<!-- 줄거리/내용, 원제목, 감독, 주연 -->
	                            		${result.ORI_TITLE} &nbsp;|&nbsp;
                                        ${result.APLC_NAME}
	                            	</a>            
	                            </c:if> 
	                            <c:if test="${result.H_GUBUN ne 'O'}" >
		                        	<a class="title"  onclick="openViewMGT2('${result.RCV_NO}','${result.ORSEQ}','${result.H_GUBUN}')">
		                        		<!-- 줄거리/내용, 원제목, 감독, 주연 -->
	                            		${result.ORI_TITLE} &nbsp;|&nbsp;
                                        ${result.APLC_NAME}
		                        	</a>
	                            </c:if> 
								<ul class="info">
									<li>
										<span class="bold">사용제목 : </span>
										${result.USE_TITLE}
										<c:if test="${not empty eo.DOMST_FORGN_DIV_CODE_VALUE}" >
                                        	( <c:out value='${eo.DOMST_FORGN_DIV_CODE_VALUE}' /> )
                                        </c:if>
									</li>
									<li><span class="bold">접수일자 : </span>${result.RCV_DATE}</li>
									<!-- 20170412 Mod 등급분류일자, 등급분류번호의 경우 '-' 부호가 특수문자로 인지하여 정상적으로 조회가 안되는 현상이 있으므로, 화면상에서 표현을 위해 NGRAM으로 변경 -->
									<li><span class="bold">등급분류일자 : </span>${result.COMPL_DATE2_NGRAM}</li>
									<li><span class="bold">등급분류번호 : </span>${result.RT_NO_NGRAM}</li>
									<li>
										<span class="bold">결정등급 :</span>
										<c:if test='${result.DETER_OPIN_CODE2_VALUE ne "자료제출"}'>
											${result.GRADE_CODE2_VALUE}
										</c:if> 
	                                    <c:if test="${not empty result.DETER_OPIN_CODE2_VALUE}">
	                                        ( <c:out value='${result.DETER_OPIN_CODE2_VALUE}' /> )
	                                    </c:if>
									</li>
									<li><span class="bold">진행상태 : ${result.PROC_STATE_CODE_VALUE}</span></li>
									<li><span class="bold">상태 : </span>${result.STATE_CODE_VALUE}</li>
								</ul>	
							</li>
						</c:forEach>
						</ul>
						<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' }">
							<a class="more" onclick="searchAllList('B03');" style="line-height: 25px; padding-right: 50%; font-size: 17px; background-image:none;">+ 비디오복제배급 더보기</a>
						</c:if>
					</c:if>
				</c:if>
				
				<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' or paramMap.mainGubun eq 'D01'}">
				    <li><span class="category">비디오광고선전 ${D01_Cnt} 건</span></li>
					<c:if test="${D01_Cnt gt 0 }">
						<ul class="list">
						<c:forEach var="result" items="${D01_SCH.list}" varStatus="status">
							<li>
	                            <c:if test="${result.H_GUBUN eq 'O'}" >
	                            	<a class="title"  onclick="openViewOldMGT2('${result.RCV_NO}','${result.ORSEQ}')">
	                            		<!-- 원제목, 신청사명, 대표자성명 -->
	                            		${result.ORI_TITLE} &nbsp;|&nbsp;
	                            		${result.APLC_NAME}
	                            	</a>            
	                            </c:if> 
	                            <c:if test="${result.H_GUBUN ne 'O'}" >
		                        	<a class="title"  onclick="openViewMGT2('${result.RCV_NO}','${result.ORSEQ}','${result.H_GUBUN}')">
		                        		<!-- 원제목, 신청사명, 대표자성명 -->
		                        		${result.ORI_TITLE} &nbsp;|&nbsp;
	                            		${result.APLC_NAME}
		                        	</a>
	                            </c:if> 
								<ul class="info">
									<li><span class="bold">사용제목 : </span>${result.USE_TITLE}</li>
									<li><span class="bold">접수일자 : </span>${result.RCV_DATE}</li>
									<!-- 20170412 Mod 등급분류일자, 등급분류번호의 경우 '-' 부호가 특수문자로 인지하여 정상적으로 조회가 안되는 현상이 있으므로, 화면상에서 표현을 위해 NGRAM으로 변경 -->
									<li><span class="bold">등급분류일자 : </span>${result.COMPL_DATE2_NGRAM}</li>
									<li><span class="bold">등급분류번호 : </span>${result.RT_NO_NGRAM}</li>
									<li><span class="bold">결정의견 :</span><c:out value='${result.GRADE_CODE2_VALUE}' /> ( <c:out value='${result.DETER_OPIN_CODE2_VALUE}' /> )</li>
									<li><span class="bold">진행상태 : </span>${result.PROC_STATE_CODE_VALUE}</li>
									<li><span class="bold">상태 : </span>${result.STATE_CODE_VALUE}</li>
									<c:import url="/search/SearchGetEtcInfo.do" charEncoding="utf-8">
										<c:param name="etc_rcv_no" value="${result.RCV_NO}" />
										<c:param name="etc_orseq" value="${result.ORSEQ}" />
									</c:import>
								</ul>
								<!-- 
								<span style="display: block; margin-right: 20px;"><span style="font-weight: bold; ">증빙자료 : </span></br>
								<c:import url="/search/SearchGetFileList.do" charEncoding="utf-8">
									<c:param name="file_rcv_no" value="${result.RCV_NO}" />
									<c:param name="file_orseq" value="${result.ORSEQ}" />
								</c:import>
								</span>
								 -->
							</li>
						</c:forEach>
						</ul>
						<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' }">
							<a class="more" onclick="searchAllList('D01');" style="line-height: 25px; padding-right: 50%; font-size: 17px; background-image:none;">+ 비디오광고선전 더보기</a>
						</c:if>
					</c:if>
				</c:if>
				
				<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' or paramMap.mainGubun eq 'C01'}">
					<li><span class="category">공연신규 ${C01_Cnt} 건</span></li>
					<c:if test="${C01_Cnt gt 0 }">
						<ul class="list">
						<c:forEach var="result" items="${C01_SCH.list }" varStatus="status">
							<li>
								<c:if test="${result.H_GUBUN eq 'M'}" >
	                            	<a  class="title"  onclick="openViewMGT2('${result.RCV_NO}','${result.ORSEQ}','${result.H_GUBUN}')">
	                            	 	<!-- 공연물제목, 공연장소 신청사명, 대표자성명 -->
	                            		${result.PFATC_TITLE} &nbsp;|&nbsp;
	                            		${result.PFM_PLACE_NAME} &nbsp;|&nbsp;
	                            		${result.APLC_NAME}
	                            	</a>
	                            </c:if>
	                            <c:if test="${result.H_GUBUN eq 'H'}" >
	                            	<a  class="title"  onclick="openViewMGT2('${result.RCV_NO}','${result.ORSEQ}','${result.H_GUBUN}')"> 
	                            	<!-- 공연물제목, 공연장소 신청사명, 대표자성명 -->
	                            		${result.PFATC_TITLE} &nbsp;|&nbsp;
	                            		${result.PFM_PLACE_NAME} &nbsp;|&nbsp;
	                            		${result.APLC_NAME}
	                            	</a>
	                            </c:if>
	                            <c:if test="${result.H_GUBUN eq 'O'}" >
	                            	<a  class="title" onclick="openViewOldMGT2('${result.RCV_NO}','${result.ORSEQ}','${result.H_GUBUN}')"> 
	                            	<!-- 공연물제목, 공연장소 신청사명, 대표자성명 -->
	                            		${result.PFATC_TITLE} &nbsp;|&nbsp;
	                            		${result.PFM_PLACE_NAME} &nbsp;|&nbsp;
	                            		${result.APLC_NAME}
	                            	</a>
	                            </c:if>
								<ul class="info">
									<li><span class="bold">공연물제목 : </span>${result.PFATC_TITLE}</li>
									<li><span class="bold">접수일자 : </span>${result.RCV_DATE}</li>
									<!-- 20170412 Mod 등급분류일자, 등급분류번호의 경우 '-' 부호가 특수문자로 인지하여 정상적으로 조회가 안되는 현상이 있으므로, 화면상에서 표현을 위해 NGRAM으로 변경 -->
									<li><span class="bold">추천완료일자 : </span>${result.COMPL_DATE2_NGRAM}</li>
									<li><span class="bold">추천결과 : </span><c:out value='${result.GRADE_CODE2_VALUEDETOPT}' /> ( <c:out value='${result.DETER_OPIN_CODE2_VALUE}' /> )</li>
									<!-- 20170412 Mod 등급분류일자, 등급분류번호의 경우 '-' 부호가 특수문자로 인지하여 정상적으로 조회가 안되는 현상이 있으므로, 화면상에서 표현을 위해 NGRAM으로 변경 -->
									<li><span class="bold">추천번호 : </span>${result.RT_NO_NGRAM}</li>
									<li><span class="bold">진행상태 :</span>${result.PROC_STATE_CODE_VALUE}</li>
									<li><span class="bold">상태 : </span>
										<c:if test = '${result.STATE_CODE_VALUE eq "반납"}'>
											<span class="st02"><c:out value='${result.STATE_CODE_VALUE}' /></span>
										</c:if>	
										<c:if test = '${result.STATE_CODE_VALUE ne "반납"}'>
											<c:out value='${result.STATE_CODE_VALUE}' />
										</c:if>
									</li>
								</ul>
                                <span style="display: block; margin-right: 20px;"><span style="font-weight: bold; ">결정의견 : </span>${result.DETER_RSN}</span>	
							</li>
						</c:forEach>
						</ul>
						<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' }">
							<a class="more" onclick="searchAllList('C01');" style="line-height: 25px; padding-right: 50%; font-size: 17px; background-image:none;">+ 공연신규 더보기</a>
						</c:if>
					</c:if>
				</c:if>
				
				<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' or paramMap.mainGubun eq 'C02'}">
					<li><span class="category">공연변경 ${C02_Cnt} 건</span></li>
					<c:if test="${C02_Cnt gt 0 }">
						<ul class="list">
						<c:forEach var="result" items="${C02_SCH.list }" varStatus="status">
							<li>
								<c:if test="${result.H_GUBUN eq 'M'}" >
	                            	<a  class="title"  onclick="openViewMGT2('${result.RCV_NO}','${result.ORSEQ}','${result.H_GUBUN}')">
	                            	<!-- 공연물제목, 공연장소 신청사명, 대표자성명 -->
	                            		${result.PFATC_TITLE} &nbsp;|&nbsp;
	                            		${result.PFM_PLACE_NAME} &nbsp;|&nbsp;
	                            		${result.APLC_NAME}
									</a>
	                            </c:if>
	                            <c:if test="${result.H_GUBUN eq 'H'}" >
	                            	<a  class="title"  onclick="openViewMGT2('${result.RCV_NO}','${result.ORSEQ}','${result.H_GUBUN}')"> 
	                            	<!-- 공연물제목, 공연장소 신청사명, 대표자성명 -->
	                            		${result.PFATC_TITLE} &nbsp;|&nbsp;
	                            		${result.PFM_PLACE_NAME} &nbsp;|&nbsp;
	                            		${result.APLC_NAME}
	                            	</a>
	                            </c:if>
	                            <c:if test="${result.H_GUBUN eq 'O'}" >
	                            	<a  class="title" onclick="openViewOldMGT2('${result.RCV_NO}','${result.ORSEQ}','${result.H_GUBUN}')"> 
	                            	<!-- 공연물제목, 공연장소 신청사명, 대표자성명 -->
	                            		${result.PFATC_TITLE} &nbsp;|&nbsp;
	                            		${result.PFM_PLACE_NAME} &nbsp;|&nbsp;
	                            		${result.APLC_NAME}
	                            	</a>
	                            </c:if>
	                                            
								<ul class="info">
									<li><span class="bold">공연물제목 : </span>${result.PFATC_TITLE}</li>
									<li><span class="bold">접수일자 : </span>${result.RCV_DATE}</li>
									<!-- 20170412 Mod 등급분류일자, 등급분류번호의 경우 '-' 부호가 특수문자로 인지하여 정상적으로 조회가 안되는 현상이 있으므로, 화면상에서 표현을 위해 NGRAM으로 변경 -->
									<li><span class="bold">추천완료일자 : </span>${result.COMPL_DATE2_NGRAM}</li>
									<li><span class="bold">추천결과 : </span><c:out value='${result.GRADE_CODE2_VALUEDETOPT}' /> ( <c:out value='${result.DETER_OPIN_CODE2_VALUE}' /> )</li>
									<!-- 20170412 Mod 등급분류일자, 등급분류번호의 경우 '-' 부호가 특수문자로 인지하여 정상적으로 조회가 안되는 현상이 있으므로, 화면상에서 표현을 위해 NGRAM으로 변경 -->
									<li><span class="bold">추천번호 : </span>${result.RT_NO_NGRAM}</li>
									<li><span class="bold">진행상태 :</span>${result.PROC_STATE_CODE_VALUE}</li>
									<li><span class="bold">상태 : </span>
										<c:if test = '${result.STATE_CODE_VALUE eq "반납"}'>
											<span class="st02"><c:out value='${result.STATE_CODE_VALUE}' /></span>
										</c:if>	
										<c:if test = '${result.STATE_CODE_VALUE ne "반납"}'>
											<c:out value='${result.STATE_CODE_VALUE}' />
										</c:if>
									</li>
								</ul>	
                                <span style="display: block; margin-right: 20px;"><span style="font-weight: bold; ">결정의견 : </span>${result.DETER_RSN}</span>
							</li>
						</c:forEach>
						</ul>
						<c:if test="${empty paramMap.mainGubun or paramMap.mainGubun eq 'ALL' }">
							<a class="more" onclick="searchAllList('C02');" style="line-height: 25px; padding-right: 50%; font-size: 17px; background-image:none;">+ 공연변경 더보기</a>
						</c:if>
					</c:if>
				</c:if>
			</ul>
		</div>
	
	<c:if test="${paramMap.mainGubun eq 'A01' or paramMap.mainGubun eq 'A02' or paramMap.mainGubun eq 'A03' or paramMap.mainGubun eq 'A04' or paramMap.mainGubun eq 'B01' or paramMap.mainGubun eq 'B02' or paramMap.mainGubun eq 'B03' or paramMap.mainGubun eq 'C01' or paramMap.mainGubun eq 'C02' or paramMap.mainGubun eq 'D01'}">
		<div class="pagination mt20 mb20">
			<html:pagingEx uri="/search/SearchList.do" parameters="${parameters}" left02Img="/images/btn/btn_first.gif" left01Img="/images/btn/btn_prev.gif" right01Img="/images/btn/btn_next.gif" right02Img="/images/btn/btn_last.gif"  />
		</div>
	</c:if>
	<!-- 탭메뉴 끝 -->
</div>

</form>				

<!-- 본문 메인 끝-->
</body>
</html>
