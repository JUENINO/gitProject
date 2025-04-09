package egovframework.search.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.print.attribute.HashAttributeSet;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.taglibs.standard.tag.common.fmt.RequestEncodingSupport;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;

import com.pcn.common.util.ListWrapper;
import com.pcn.common.util.TypeConvertUtil;

import egovframework.kmrb.common.service.CommonService;
import egovframework.pcn.base.BaseWalker;
import egovframework.pcn.base.Walker;
import egovframework.pcn.common.util.CommonUtil;
import egovframework.pcn.common.util.StringUtil;
import egovframework.pcn.http.wrapper.RequestWrapper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import egovframework.search.service.SearchEngineService;

@Controller  
public class SearchController {
	@Resource(name = "searchServiceEngine")
	private SearchEngineService searchServiceEngine;

	@Resource(name = "commonService")
	private CommonService commonService;

	/** baseWalker */
	@Inject
	Provider<BaseWalker> baseWalker;

	@RequestMapping(value = "/search/consult.do")
	public String consult(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap, ModelMap model) throws Exception {
		BaseWalker walker = this.baseWalker.get();
		String initResult = walker.init(request, response);
		if (!walker.isNull(initResult))
			return initResult;
		Map memberMap = (HashMap) walker.session.getAttribute("member");
		walker.setValue("auth", (String) memberMap.get("USER_AUTY_DIV_CODE"));
		return "common/consult";
	}

	@RequestMapping(value = "/search/SearchList.do")
	public String searchTotalList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap, ModelMap model) throws Exception {
		BaseWalker walker = null;

		try {
			walker = this.baseWalker.get();
			String initResult = walker.init(request, response);
			if (!walker.isNull(initResult))
				return initResult;

			paramMap = walker.getParamToMap(walker.request, false);

			String searchStr = paramMap.get("searchStr") == null ? "" : paramMap.get("searchStr").toString();
			String searchSq = paramMap.get("searchSq") == null ? "" : paramMap.get("searchSq").toString().trim();
			String mainGubun = paramMap.get("mainGubun") == null ? "ALL" : paramMap.get("mainGubun").toString().trim();
			int cPage = 1;

			int display = 10;
			if ("ALL".equals(mainGubun)) {
				display = 5;
			}

			// 검색 범위 설정

			List<Map<String, Object>> C01_SCH = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> C02_SCH = new ArrayList<Map<String, Object>>();

			List<Map<String, Object>> A01_SCH = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> A0201_SCH = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> A0202_SCH = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> A0203_SCH = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> A0204_SCH = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> A02_SCH = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> A03_SCH = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> A04_SCH = new ArrayList<Map<String, Object>>();

			List<Map<String, Object>> B0101_SCH = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> B0102_SCH = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> B0103_SCH = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> B0104_SCH = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> B0105_SCH = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> B01_SCH = new ArrayList<Map<String, Object>>();

			List<Map<String, Object>> B0201_SCH = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> B0202_SCH = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> B0203_SCH = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> B0204_SCH = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> B02_SCH = new ArrayList<Map<String, Object>>();

			List<Map<String, Object>> B0301_SCH = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> B03_SCH = new ArrayList<Map<String, Object>>();

			List<Map<String, Object>> D0101_SCH = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> D0102_SCH = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> D01_SCH = new ArrayList<Map<String, Object>>();

			int C01_Cnt = 0;
			int C02_Cnt = 0;
			int A01_Cnt = 0;
			int A02_Cnt = 0;
			int A03_Cnt = 0;
			int A04_Cnt = 0;
			int B01_Cnt = 0;
			int B02_Cnt = 0;
			int B03_Cnt = 0;
			int D01_Cnt = 0;

			if (StringUtil.isNotNull(searchStr)) {

				// 영화
				if (mainGubun.equals("ALL")) {

					A01_SCH = searchServiceEngine.list("A01", searchStr, searchSq, "A01_SCH", display, cPage);
					if (A01_SCH.size() > 0) {
						A01_Cnt = searchServiceEngine.getTotalCount();
					} else {
						A01_Cnt = 0;
					}

					A03_SCH = searchServiceEngine.list("A03", searchStr, searchSq, "A03_SCH", display, cPage);
					if (A03_SCH.size() > 0) {
						A03_Cnt = searchServiceEngine.getTotalCount();
					} else {
						A03_Cnt = 0;
					}

					A04_SCH = searchServiceEngine.list("A04", searchStr, searchSq, "A04_SCH", display, cPage);
					if (A04_SCH.size() > 0) {
						A04_Cnt = searchServiceEngine.getTotalCount();
					} else {
						A04_Cnt = 0;
					}

					A0201_SCH = searchServiceEngine.list("A02", searchStr, searchSq, "A0201_SCH", display, cPage);
					if (A0201_SCH.size() > 0) {
						A02_Cnt += searchServiceEngine.getTotalCount();
					} else {
						A02_Cnt += 0;
					}

					A0202_SCH = searchServiceEngine.list("A02", searchStr, searchSq, "A0202_SCH", display, cPage);
					if (A0202_SCH.size() > 0) {
						A02_Cnt += searchServiceEngine.getTotalCount();
					} else {
						A02_Cnt += 0;
					}

					/*
					 * 미사용 A0203_SCH = searchServiceEngine.list("A02",
					 * searchStr, searchSq, "A0203_SCH", display, cPage);
					 * if(A0203_SCH.size() > 0){ A02_Cnt +=
					 * searchServiceEngine.getTotalCount(); }else{ A02_Cnt += 0;
					 * }
					 */

					/*
					 * 미사용 A0204_SCH = searchServiceEngine.list("A02",
					 * searchStr, searchSq, "A0204_SCH", display, cPage);
					 * if(A0204_SCH.size() > 0){ A02_Cnt +=
					 * searchServiceEngine.getTotalCount(); }else{ A02_Cnt += 0;
					 * }
					 */

					List<Map<String, Object>> aList = new ArrayList<Map<String, Object>>();

					aList.addAll(A0201_SCH);
					aList.addAll(A0202_SCH);
					/*
					 * 미사용 aList.addAll(A0203_SCH); aList.addAll(A0204_SCH);
					 */

					int endi = 5;

					if (A02_Cnt < endi) {
						endi = A02_Cnt;
					}

					if (aList.size() > 0) {

						for (int i = 0; i < endi; i++) {
							Map<String, Object> miniMap = new HashMap<String, Object>();
							miniMap = (Map) aList.get(i);
							A02_SCH.add(miniMap);
						}

					}

					B0101_SCH = searchServiceEngine.list("B01", searchStr, searchSq, "B0101_SCH", display, cPage);
					if (B0101_SCH.size() > 0) {
						B01_Cnt += searchServiceEngine.getTotalCount();
					} else {
						B01_Cnt += 0;
					}

					/*
					 * 미사용 B0102_SCH = searchServiceEngine.list("B01",
					 * searchStr, searchSq, "B0102_SCH", display, cPage);
					 * if(B0102_SCH.size() > 0){ B01_Cnt +=
					 * searchServiceEngine.getTotalCount(); }else{ B01_Cnt += 0;
					 * }
					 */

					/*
					 * 미사용 B0103_SCH = searchServiceEngine.list("B01",
					 * searchStr, searchSq, "B0103_SCH", display, cPage);
					 * if(B0103_SCH.size() > 0){ B01_Cnt +=
					 * searchServiceEngine.getTotalCount(); }else{ B01_Cnt += 0;
					 * }
					 */

					/*
					 * 미사용 B0104_SCH = searchServiceEngine.list("B01",
					 * searchStr, searchSq, "B0104_SCH", display, cPage);
					 * if(B0104_SCH.size() > 0){ B01_Cnt +=
					 * searchServiceEngine.getTotalCount(); }else{ B01_Cnt += 0;
					 * }
					 */

					B0105_SCH = searchServiceEngine.list("B01", searchStr, searchSq, "B0105_SCH", display, cPage);
					if (B0105_SCH.size() > 0) {
						B01_Cnt += searchServiceEngine.getTotalCount();
					} else {
						B01_Cnt += 0;
					}

					List<Map<String, Object>> aList2 = new ArrayList<Map<String, Object>>();

					aList2.addAll(B0101_SCH);
					/*
					 * 미사용 aList2.addAll(B0102_SCH); aList2.addAll(B0103_SCH);
					 * aList2.addAll(B0104_SCH);
					 */
					aList2.addAll(B0105_SCH);

					int endi2 = 5;

					if (B01_Cnt < endi2) {
						endi2 = B01_Cnt;
					}

					if (aList2.size() > 0) {

						for (int i = 0; i < endi2; i++) {
							Map<String, Object> miniMap = new HashMap<String, Object>();
							miniMap = (Map) aList2.get(i);
							B01_SCH.add(miniMap);
						}

					}

					B0201_SCH = searchServiceEngine.list("B02", searchStr, searchSq, "B0201_SCH", display, cPage);

					if (B0201_SCH.size() > 0) {
						B02_Cnt += searchServiceEngine.getTotalCount();
					} else {
						B02_Cnt += 0;
					}

					B0202_SCH = searchServiceEngine.list("B02", searchStr, searchSq, "B0202_SCH", display, cPage);
					if (B0202_SCH.size() > 0) {
						B02_Cnt += searchServiceEngine.getTotalCount();
					} else {
						B02_Cnt += 0;
					}

					B0203_SCH = searchServiceEngine.list("B02", searchStr, searchSq, "B0203_SCH", display, cPage);
					if (B0203_SCH.size() > 0) {
						B02_Cnt += searchServiceEngine.getTotalCount();
					} else {
						B02_Cnt += 0;
					}

					B0204_SCH = searchServiceEngine.list("B02", searchStr, searchSq, "B0204_SCH", display, cPage);
					if (B0204_SCH.size() > 0) {
						B02_Cnt += searchServiceEngine.getTotalCount();
					} else {
						B02_Cnt += 0;
					}

					List<Map<String, Object>> aList3 = new ArrayList<Map<String, Object>>();

					aList3.addAll(B0201_SCH);
					aList3.addAll(B0202_SCH);
					aList3.addAll(B0203_SCH);
					aList3.addAll(B0204_SCH);

					int endi3 = 5;

					if (B02_Cnt < endi3) {
						endi3 = B02_Cnt;
					}

					if (aList3.size() > 0) {

						for (int i = 0; i < endi3; i++) {
							Map<String, Object> miniMap = new HashMap<String, Object>();
							miniMap = (Map) aList3.get(i);
							B02_SCH.add(miniMap);
						}

					}

					// 복제배급 구분으로 인한 추가 20171018
					B0301_SCH = searchServiceEngine.list("B03", searchStr, searchSq, "B0301_SCH", display, cPage);
					if (B0301_SCH.size() > 0) {
						B03_Cnt += searchServiceEngine.getTotalCount();
					} else {
						B03_Cnt += 0;
					}

					List<Map<String, Object>> aList5 = new ArrayList<Map<String, Object>>();

					aList5.addAll(B0301_SCH);

					int endi5 = 5;

					if (B03_Cnt < endi5) {
						endi5 = B03_Cnt;
					}

					if (aList5.size() > 0) {

						for (int i = 0; i < endi5; i++) {
							Map<String, Object> miniMap = new HashMap<String, Object>();
							miniMap = (Map) aList5.get(i);
							B03_SCH.add(miniMap);
						}

					}

					D0101_SCH = searchServiceEngine.list("D01", searchStr, searchSq, "D0101_SCH", display, cPage);
					if (D0101_SCH.size() > 0) {
						D01_Cnt += searchServiceEngine.getTotalCount();
					} else {
						D01_Cnt += 0;
					}

					D0102_SCH = searchServiceEngine.list("D01", searchStr, searchSq, "D0102_SCH", display, cPage);
					if (D0102_SCH.size() > 0) {
						D01_Cnt += searchServiceEngine.getTotalCount();
					} else {
						D01_Cnt += 0;
					}

					List<Map<String, Object>> aList4 = new ArrayList<Map<String, Object>>();

					aList4.addAll(D0101_SCH);
					aList4.addAll(D0102_SCH);

					int endi4 = 5;

					if (D01_Cnt < endi4) {
						endi4 = D01_Cnt;
					}

					if (aList4.size() > 0) {

						for (int i = 0; i < endi4; i++) {
							Map<String, Object> miniMap = new HashMap<String, Object>();
							miniMap = (Map) aList4.get(i);
							D01_SCH.add(miniMap);
						}

					}

					C01_SCH = searchServiceEngine.list("C01", searchStr, searchSq, "C01_SCH", display, cPage);
					if (C01_SCH.size() > 0) {
						C01_Cnt = searchServiceEngine.getTotalCount();
					} else {
						C01_Cnt = 0;
					}

					C02_SCH = searchServiceEngine.list("C02", searchStr, searchSq, "C02_SCH", display, cPage);
					if (C02_SCH.size() > 0) {
						C02_Cnt = searchServiceEngine.getTotalCount();
					} else {
						C02_Cnt = 0;
					}

				} else {

					if (mainGubun.equals("A01")) {
						A01_SCH = searchServiceEngine.list("A01", searchStr, searchSq, "A01_SCH", display, cPage);
						if (A01_SCH.size() > 0) {
							A01_Cnt = searchServiceEngine.getTotalCount();
							int[] rowRange = walker.setPageInfo(walker.request, A01_Cnt, display);
							cPage = String.valueOf(rowRange[0]) != null ? Integer.parseInt(String.valueOf(rowRange[0]))
									: 1;
							A01_SCH = searchServiceEngine.list("A01", searchStr, searchSq, "A01_SCH", display,
									(cPage / display) + 1);
							A01_Cnt = searchServiceEngine.getTotalCount();

						} else {
							A01_Cnt = 0;
						}
					}

					// 영화예고
					if (mainGubun.equals("A03")) {
						A03_SCH = searchServiceEngine.list("A03", searchStr, searchSq, "A03_SCH", display, cPage);
						if (A03_SCH.size() > 0) {
							A03_Cnt = searchServiceEngine.getTotalCount();
							int[] rowRange = walker.setPageInfo(walker.request, A03_Cnt, display);
							cPage = String.valueOf(rowRange[0]) != null ? Integer.parseInt(String.valueOf(rowRange[0]))
									: 1;
							A03_SCH = searchServiceEngine.list("A03", searchStr, searchSq, "A03_SCH", display,
									(cPage / display) + 1);
							A03_Cnt = searchServiceEngine.getTotalCount();
						} else {
							A03_Cnt = 0;
						}
					}

					// 영화광고선전
					if (mainGubun.equals("A04")) {
						A04_SCH = searchServiceEngine.list("A04", searchStr, searchSq, "A04_SCH", display, cPage);
						if (A04_SCH.size() > 0) {
							A04_Cnt = searchServiceEngine.getTotalCount();
							int[] rowRange = walker.setPageInfo(walker.request, A04_Cnt, display);
							cPage = String.valueOf(rowRange[0]) != null ? Integer.parseInt(String.valueOf(rowRange[0]))
									: 1;
							A04_SCH = searchServiceEngine.list("A04", searchStr, searchSq, "A04_SCH", display,
									(cPage / display) + 1);
							A04_Cnt = searchServiceEngine.getTotalCount();
						} else {
							A04_Cnt = 0;
						}
					}

					// 영화광고
					if (mainGubun.equals("A02")) {

						int A0201 = 0;
						int A0202 = 0;
						int A0203 = 0;
						int A0204 = 0;

						int A0201M = 0;
						int A0202M = 0;
						int A0203M = 0;
						int A0204M = 0;

						A0201_SCH = searchServiceEngine.list("A02", searchStr, searchSq, "A0201_SCH", display, cPage);
						if (A0201_SCH.size() > 0) {
							A0201 = searchServiceEngine.getTotalCount();
							A02_Cnt += A0201;
						} else {
							A02_Cnt += 0;
						}

						A0202_SCH = searchServiceEngine.list("A02", searchStr, searchSq, "A0202_SCH", display, cPage);
						if (A0202_SCH.size() > 0) {
							A0202 = searchServiceEngine.getTotalCount();
							A02_Cnt += A0202;
						} else {
							A02_Cnt += 0;
						}

						/*
						 * 미사용 A0203_SCH = searchServiceEngine.list("A02",
						 * searchStr, searchSq, "A0203_SCH", display, cPage);
						 * if(A0203_SCH.size() > 0){ A0203 =
						 * searchServiceEngine.getTotalCount(); A02_Cnt +=
						 * A0203; }else{ A02_Cnt += 0; }
						 */

						/*
						 * A0204_SCH = searchServiceEngine.list("A02",
						 * searchStr, searchSq, "A0204_SCH", display, cPage);
						 * if(A0204_SCH.size() > 0){ A0204 =
						 * searchServiceEngine.getTotalCount(); A02_Cnt +=
						 * A0204; }else{ A02_Cnt += 0; }
						 */

						int[] rowRange = walker.setPageInfo(walker.request, A02_Cnt, display);
						cPage = String.valueOf(rowRange[0]) != null ? Integer.parseInt(String.valueOf(rowRange[0])) : 1;

						A0201_SCH = searchServiceEngine.list("A02", searchStr, searchSq, "A0201_SCH", A0201, 1);
						A0202_SCH = searchServiceEngine.list("A02", searchStr, searchSq, "A0202_SCH", A0202, 1);
						/*
						 * 미사용 A0203_SCH = searchServiceEngine.list("A02",
						 * searchStr, searchSq, "A0203_SCH", A0203, 1);
						 * A0204_SCH = searchServiceEngine.list("A02",
						 * searchStr, searchSq, "A0204_SCH", A0204, 1);
						 */

						List<Map<String, Object>> aList = new ArrayList<Map<String, Object>>();

						aList.addAll(A0201_SCH);
						aList.addAll(A0202_SCH);
						/*
						 * 미사용 aList.addAll(A0203_SCH); aList.addAll(A0204_SCH);
						 */

						int i = 0;
						int endi = cPage + display;

						if (A02_Cnt < endi) {
							endi = A02_Cnt;
						}

						if (aList.size() > 0) {

							for (i = (cPage - 1); i < endi; i++) {
								Map<String, Object> miniMap = new HashMap<String, Object>();
								miniMap = (Map) aList.get(i);
								A02_SCH.add(miniMap);
							}

						}

					}

					// 비디오
					if (mainGubun.equals("B01")) {

						int B0101 = 0;
						int B0102 = 0;
						int B0103 = 0;
						int B0104 = 0;
						int B0105 = 0;

						int B0101M = 0;
						int B0102M = 0;
						int B0103M = 0;
						int B0104M = 0;
						int B0105M = 0;

						B0101_SCH = searchServiceEngine.list("B01", searchStr, searchSq, "B0101_SCH", display, cPage);
						if (B0101_SCH.size() > 0) {
							B0101 = searchServiceEngine.getTotalCount();
							B01_Cnt += B0101;
						} else {
							B01_Cnt += 0;
						}

						/*
						 * 미사용 B0102_SCH = searchServiceEngine.list("B01",
						 * searchStr, searchSq, "B0102_SCH", display, cPage);
						 * if(B0102_SCH.size() > 0){ B0102 =
						 * searchServiceEngine.getTotalCount(); B01_Cnt +=
						 * B0102; }else{ B01_Cnt += 0; }
						 */

						/*
						 * 미사용 B0103_SCH = searchServiceEngine.list("B01",
						 * searchStr, searchSq, "B0103_SCH", display, cPage);
						 * if(B0103_SCH.size() > 0){ B0103 =
						 * searchServiceEngine.getTotalCount(); B01_Cnt +=
						 * B0103; }else{ B01_Cnt += 0; }
						 */

						/*
						 * 미사용 B0104_SCH = searchServiceEngine.list("B01",
						 * searchStr, searchSq, "B0104_SCH", display, cPage);
						 * if(B0104_SCH.size() > 0){ B0104 =
						 * searchServiceEngine.getTotalCount(); B01_Cnt +=
						 * B0104; }else{ B01_Cnt += 0; }
						 */

						B0105_SCH = searchServiceEngine.list("B01", searchStr, searchSq, "B0105_SCH", display, cPage);
						if (B0105_SCH.size() > 0) {
							B0105 = searchServiceEngine.getTotalCount();
							B01_Cnt += B0105;
						} else {
							B01_Cnt += 0;
						}

						int[] rowRange = walker.setPageInfo(walker.request, B01_Cnt, display);
						cPage = String.valueOf(rowRange[0]) != null ? Integer.parseInt(String.valueOf(rowRange[0])) : 1;

						B0101_SCH = searchServiceEngine.list("B01", searchStr, searchSq, "B0101_SCH", B0101, 1);
						/*
						 * 미사용 B0102_SCH = searchServiceEngine.list("B01",
						 * searchStr, searchSq, "B0102_SCH", B0102, 1);
						 * B0103_SCH = searchServiceEngine.list("B01",
						 * searchStr, searchSq, "B0103_SCH", B0103, 1);
						 * B0104_SCH = searchServiceEngine.list("B01",
						 * searchStr, searchSq, "B0104_SCH", B0104, 1);
						 */
						B0105_SCH = searchServiceEngine.list("B01", searchStr, searchSq, "B0105_SCH", B0105, 1);

						List<Map<String, Object>> aList = new ArrayList<Map<String, Object>>();

						aList.addAll(B0101_SCH);
						/*
						 * 미사용 aList.addAll(B0102_SCH); aList.addAll(B0103_SCH);
						 * aList.addAll(B0104_SCH);
						 */
						aList.addAll(B0105_SCH);

						int i = 0;
						int endi = cPage + display;

						if (B01_Cnt < endi) {
							endi = B01_Cnt;
						}

						if (aList.size() > 0) {

							for (i = (cPage - 1); i < endi; i++) {
								Map<String, Object> miniMap = new HashMap<String, Object>();
								miniMap = (Map) aList.get(i);
								B01_SCH.add(miniMap);
							}

						}

					}

					// 비디오복제배급
					if (mainGubun.equals("B02")) {

						int B0201 = 0;
						int B0202 = 0;
						int B0203 = 0;
						int B0204 = 0;

						int B0201M = 0;
						int B0202M = 0;
						int B0203M = 0;
						int B0204M = 0;

						B0201_SCH = searchServiceEngine.list("B02", searchStr, searchSq, "B0201_SCH", display, cPage);

						if (B0201_SCH.size() > 0) {
							B0201 = searchServiceEngine.getTotalCount();
							B02_Cnt += B0201;
						} else {
							B02_Cnt += 0;
						}

						B0202_SCH = searchServiceEngine.list("B02", searchStr, searchSq, "B0202_SCH", display, cPage);
						if (B0202_SCH.size() > 0) {
							B0202 = searchServiceEngine.getTotalCount();
							B02_Cnt += B0202;
						} else {
							B02_Cnt += 0;
						}

						B0203_SCH = searchServiceEngine.list("B02", searchStr, searchSq, "B0203_SCH", display, cPage);
						if (B0203_SCH.size() > 0) {
							B0203 = searchServiceEngine.getTotalCount();
							B02_Cnt += B0203;
						} else {
							B02_Cnt += 0;
						}

						B0204_SCH = searchServiceEngine.list("B02", searchStr, searchSq, "B0204_SCH", display, cPage);
						if (B0204_SCH.size() > 0) {
							B0204 = searchServiceEngine.getTotalCount();
							B02_Cnt += B0204;
						} else {
							B02_Cnt += 0;
						}

						int[] rowRange = walker.setPageInfo(walker.request, B02_Cnt, display);
						cPage = String.valueOf(rowRange[0]) != null ? Integer.parseInt(String.valueOf(rowRange[0])) : 1;

						B0201_SCH = searchServiceEngine.list("B02", searchStr, searchSq, "B0201_SCH", B0201, 1);
						B0202_SCH = searchServiceEngine.list("B02", searchStr, searchSq, "B0202_SCH", B0202, 1);
						B0203_SCH = searchServiceEngine.list("B02", searchStr, searchSq, "B0203_SCH", B0203, 1);
						B0204_SCH = searchServiceEngine.list("B02", searchStr, searchSq, "B0204_SCH", B0204, 1);

						List<Map<String, Object>> aList = new ArrayList<Map<String, Object>>();

						aList.addAll(B0201_SCH);
						aList.addAll(B0202_SCH);
						aList.addAll(B0203_SCH);
						aList.addAll(B0204_SCH);

						int i = 0;
						int endi = cPage + display;

						if (B02_Cnt < endi) {
							endi = B02_Cnt;
						}

						if (aList.size() > 0) {

							for (i = (cPage - 1); i < endi; i++) {
								Map<String, Object> miniMap = new HashMap<String, Object>();
								miniMap = (Map) aList.get(i);
								B02_SCH.add(miniMap);
							}

						}

					}

					// 비디오복제배급국외
					if (mainGubun.equals("B03")) {

						int B0301 = 0;

						int B0301M = 0;

						B0301_SCH = searchServiceEngine.list("B03", searchStr, searchSq, "B0301_SCH", display, cPage);

						if (B0301_SCH.size() > 0) {
							B0301 = searchServiceEngine.getTotalCount();
							B03_Cnt += B0301;
						} else {
							B03_Cnt += 0;
						}

						int[] rowRange = walker.setPageInfo(walker.request, B03_Cnt, display);
						cPage = String.valueOf(rowRange[0]) != null ? Integer.parseInt(String.valueOf(rowRange[0])) : 1;

						B0301_SCH = searchServiceEngine.list("B03", searchStr, searchSq, "B0301_SCH", B0301, 1);

						List<Map<String, Object>> aList = new ArrayList<Map<String, Object>>();

						aList.addAll(B0301_SCH);

						int i = 0;
						int endi = cPage + display;

						if (B03_Cnt < endi) {
							endi = B03_Cnt;
						}

						if (aList.size() > 0) {

							for (i = (cPage - 1); i < endi; i++) {
								Map<String, Object> miniMap = new HashMap<String, Object>();
								miniMap = (Map) aList.get(i);
								B03_SCH.add(miniMap);
							}

						}

					}

					// 비디오광고선전
					if (mainGubun.equals("D01")) {

						int D0101 = 0;
						int D0102 = 0;

						int D0101M = 0;
						int D0102M = 0;

						D0101_SCH = searchServiceEngine.list("D01", searchStr, searchSq, "D0101_SCH", display, cPage);
						if (D0101_SCH.size() > 0) {
							D0101 = searchServiceEngine.getTotalCount();
							D01_Cnt += D0101;
						} else {
							D01_Cnt += 0;
						}

						D0102_SCH = searchServiceEngine.list("D01", searchStr, searchSq, "D0102_SCH", display, cPage);
						if (D0102_SCH.size() > 0) {
							D0102 = searchServiceEngine.getTotalCount();
							D01_Cnt += D0102;
						} else {
							D01_Cnt += 0;
						}

						int[] rowRange = walker.setPageInfo(walker.request, D01_Cnt, display);
						cPage = String.valueOf(rowRange[0]) != null ? Integer.parseInt(String.valueOf(rowRange[0])) : 1;

						D0101_SCH = searchServiceEngine.list("D01", searchStr, searchSq, "D0101_SCH", D0101, 1);
						D0102_SCH = searchServiceEngine.list("D01", searchStr, searchSq, "D0102_SCH", D0102, 1);

						List<Map<String, Object>> aList = new ArrayList<Map<String, Object>>();

						aList.addAll(D0101_SCH);
						aList.addAll(D0102_SCH);

						int i = 0;
						int endi = cPage + display;

						if (D01_Cnt < endi) {
							endi = D01_Cnt;
						}

						if (aList.size() > 0) {

							for (i = (cPage - 1); i < endi; i++) {
								Map<String, Object> miniMap = new HashMap<String, Object>();
								miniMap = (Map) aList.get(i);
								D01_SCH.add(miniMap);
							}

						}

					}

					// 공연신규
					if (mainGubun.equals("C01")) {
						C01_SCH = searchServiceEngine.list("C01", searchStr, searchSq, "C01_SCH", display, cPage);
						if (C01_SCH.size() > 0) {
							C01_Cnt = searchServiceEngine.getTotalCount();

							int[] rowRange = walker.setPageInfo(walker.request, C01_Cnt, display);
							cPage = String.valueOf(rowRange[0]) != null ? Integer.parseInt(String.valueOf(rowRange[0]))
									: 1;
							C01_SCH = searchServiceEngine.list("C01", searchStr, searchSq, "C01_SCH", display,
									(cPage / display) + 1);
							C01_Cnt = searchServiceEngine.getTotalCount();

						} else {
							C01_Cnt = 0;
						}

					}
					// 공연변경
					if (mainGubun.equals("C02")) {
						C02_SCH = searchServiceEngine.list("C02", searchStr, searchSq, "C02_SCH", display, cPage);
						if (C02_SCH.size() > 0) {
							C02_Cnt = searchServiceEngine.getTotalCount();

							int[] rowRange = walker.setPageInfo(walker.request, C02_Cnt, display);
							cPage = String.valueOf(rowRange[0]) != null ? Integer.parseInt(String.valueOf(rowRange[0]))
									: 1;
							C02_SCH = searchServiceEngine.list("C02", searchStr, searchSq, "C02_SCH", display,
									(cPage / display) + 1);
							C02_Cnt = searchServiceEngine.getTotalCount();

						} else {
							C02_Cnt = 0;
						}
					}

				}

				System.out.println("C01_Cnt = " + C01_Cnt);
				System.out.println("C02_Cnt = " + C02_Cnt);
				System.out.println("A01_Cnt =  " + A01_Cnt);
				System.out.println("A02_Cnt =  " + A02_Cnt);
				System.out.println("A03_Cnt =  " + A03_Cnt);
				System.out.println("A04_Cnt =  " + A04_Cnt);
				System.out.println("B01_Cnt =  " + B01_Cnt);
				System.out.println("B02_Cnt =  " + B02_Cnt);
				System.out.println("B03_Cnt =  " + B03_Cnt);
				System.out.println("D01_Cnt =  " + D01_Cnt);

				System.out.println("A01_SCH =  " + A01_SCH.toString());
				System.out.println("B0101_SCH =  " + B0101_SCH.toString());
				System.out.println("B0102_SCH =  " + B0102_SCH.toString());
				System.out.println("B0103_SCH =  " + B0103_SCH.toString());
				System.out.println("B0104_SCH =  " + B0104_SCH.toString());

				int endCnt = Integer
						.parseInt((((String) paramMap.get("endCnt") == null) ? "10" : (String) paramMap.get("endCnt")));// 조회될
																														// 레코드
																														// 수

				walker.setValue("paramMap", paramMap);
				walker.setValue("parameters", walker.makeParameterQuery());

				walker.setValue("A01_SCH", A01_SCH);
				walker.setValue("A01_Cnt", A01_Cnt);

				walker.setValue("A02_SCH", A02_SCH);
				walker.setValue("A02_Cnt", A02_Cnt);

				walker.setValue("A03_SCH", A03_SCH);
				walker.setValue("A03_Cnt", A03_Cnt);

				walker.setValue("A04_SCH", A04_SCH);
				walker.setValue("A04_Cnt", A04_Cnt);

				walker.setValue("B01_SCH", B01_SCH);
				walker.setValue("B01_Cnt", B01_Cnt);

				walker.setValue("B02_SCH", B02_SCH);
				walker.setValue("B02_Cnt", B02_Cnt);

				walker.setValue("B03_SCH", B03_SCH);
				walker.setValue("B03_Cnt", B03_Cnt);

				walker.setValue("D01_SCH", D01_SCH);
				walker.setValue("D01_Cnt", D01_Cnt);

				walker.setValue("C01_SCH", C01_SCH);
				walker.setValue("C01_Cnt", C01_Cnt);

				walker.setValue("C02_SCH", C02_SCH);
				walker.setValue("C02_Cnt", C02_Cnt);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "common/searchResult";
	}

	@RequestMapping(value = "/search/SearchList2.do")
	public String searchTotalList2(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap, ModelMap model) throws Exception {
		BaseWalker walker = null;

		try {
			walker = this.baseWalker.get();
			String initResult = walker.init(request, response);
			if (!walker.isNull(initResult))
				return initResult;

			paramMap = walker.getParamToMap(walker.request, false);

			String searchStr = paramMap.get("searchStr") == null ? "" : paramMap.get("searchStr").toString();
			String searchSq = paramMap.get("searchSq") == null ? "" : paramMap.get("searchSq").toString().trim();
			String mainGubun = paramMap.get("mainGubun") == null ? "ALL" : paramMap.get("mainGubun").toString().trim();
			int cPage = 1;

			int display = 10;
			if ("ALL".equals(mainGubun)) {
				display = 5;
			}

			// 검색 범위 설정

			List<Map<String, Object>> A01_SCH = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> A02_SCH = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> A03_SCH = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> A04_SCH = new ArrayList<Map<String, Object>>();

			List<Map<String, Object>> B01_SCH = new ArrayList<Map<String, Object>>();

			List<Map<String, Object>> D01_SCH = new ArrayList<Map<String, Object>>();

			// int C01_Cnt = 0;
			// int C02_Cnt = 0;
			int A01_Cnt = 0;
			int A02_Cnt = 0;
			int A03_Cnt = 0;
			int A04_Cnt = 0;
			int B01_Cnt = 0;
			int D01_Cnt = 0;

			if (StringUtil.isNotNull(searchStr)) {

				// 영화
				if (mainGubun.equals("ALL")) {

					// 영화
					A01_SCH = searchServiceEngine.list("M01", searchStr, searchSq, "M01_SCH", display, cPage);
					if (A01_SCH.size() > 0) {
						A01_Cnt = searchServiceEngine.getTotalCount();
					} else {
						A01_Cnt = 0;
					}

					// 영화 예고편
					A03_SCH = searchServiceEngine.list("E01", searchStr, searchSq, "E01_SCH", display, cPage);
					if (A03_SCH.size() > 0) {
						A03_Cnt = searchServiceEngine.getTotalCount();
					} else {
						A03_Cnt = 0;
					}

					// 영화 광고선전
					A04_SCH = searchServiceEngine.list("E03", searchStr, searchSq, "E03_SCH", display, cPage);
					if (A04_SCH.size() > 0) {
						A04_Cnt = searchServiceEngine.getTotalCount();
					} else {
						A04_Cnt = 0;
					}

					// 광고영화
					A02_SCH = searchServiceEngine.list("E02", searchStr, searchSq, "E02_SCH", display, cPage);
					if (A02_SCH.size() > 0) {
						A02_Cnt += searchServiceEngine.getTotalCount();
					} else {
						A02_Cnt += 0;
					}

					// 비디오
					B01_SCH = searchServiceEngine.list("V01", searchStr, searchSq, "V01_SCH", display, cPage);
					if (B01_SCH.size() > 0) {
						B01_Cnt += searchServiceEngine.getTotalCount();
					} else {
						B01_Cnt += 0;
					}

					// 비디오 광고선전
					D01_SCH = searchServiceEngine.list("E04", searchStr, searchSq, "E04_SCH", display, cPage);
					if (D01_SCH.size() > 0) {
						D01_Cnt += searchServiceEngine.getTotalCount();
					} else {
						D01_Cnt += 0;
					}

				} else {

					// 영화
					if (mainGubun.equals("A01")) {
						A01_SCH = searchServiceEngine.list("M01", searchStr, searchSq, "M01_SCH", display, cPage);
						if (A01_SCH.size() > 0) {
							A01_Cnt = searchServiceEngine.getTotalCount();
							int[] rowRange = walker.setPageInfo(walker.request, A01_Cnt, display);
							cPage = String.valueOf(rowRange[0]) != null ? Integer.parseInt(String.valueOf(rowRange[0]))
									: 1;
							A01_SCH = searchServiceEngine.list("M01", searchStr, searchSq, "M01_SCH", display,
									(cPage / display) + 1);
							A01_Cnt = searchServiceEngine.getTotalCount();

						} else {
							A01_Cnt = 0;
						}
					}
					// 영화예고
					if (mainGubun.equals("A03")) {
						A03_SCH = searchServiceEngine.list("E01", searchStr, searchSq, "E01_SCH", display, cPage);
						if (A03_SCH.size() > 0) {
							A03_Cnt = searchServiceEngine.getTotalCount();
							int[] rowRange = walker.setPageInfo(walker.request, A03_Cnt, display);
							cPage = String.valueOf(rowRange[0]) != null ? Integer.parseInt(String.valueOf(rowRange[0]))
									: 1;
							A03_SCH = searchServiceEngine.list("E01", searchStr, searchSq, "E01_SCH", display,
									(cPage / display) + 1);
							A03_Cnt = searchServiceEngine.getTotalCount();
						} else {
							A03_Cnt = 0;
						}
					}

					// 영화광고선전
					if (mainGubun.equals("A04")) {
						A04_SCH = searchServiceEngine.list("E03", searchStr, searchSq, "E03_SCH", display, cPage);
						if (A04_SCH.size() > 0) {
							A04_Cnt = searchServiceEngine.getTotalCount();
							int[] rowRange = walker.setPageInfo(walker.request, A04_Cnt, display);
							cPage = String.valueOf(rowRange[0]) != null ? Integer.parseInt(String.valueOf(rowRange[0]))
									: 1;
							A04_SCH = searchServiceEngine.list("E03", searchStr, searchSq, "E03_SCH", display,
									(cPage / display) + 1);
							A04_Cnt = searchServiceEngine.getTotalCount();
						} else {
							A04_Cnt = 0;
						}
					}

					// 영화광고
					if (mainGubun.equals("A02")) {
						A02_SCH = searchServiceEngine.list("E02", searchStr, searchSq, "E02_SCH", display, cPage);
						if (A02_SCH.size() > 0) {
							A02_Cnt = searchServiceEngine.getTotalCount();
							int[] rowRange = walker.setPageInfo(walker.request, A02_Cnt, display);
							cPage = String.valueOf(rowRange[0]) != null ? Integer.parseInt(String.valueOf(rowRange[0]))
									: 1;
							A02_SCH = searchServiceEngine.list("E02", searchStr, searchSq, "E02_SCH", display,
									(cPage / display) + 1);
							A02_Cnt = searchServiceEngine.getTotalCount();
						} else {
							A02_Cnt = 0;
						}
					}

					// 비디오
					if (mainGubun.equals("B01")) {
						B01_SCH = searchServiceEngine.list("V01", searchStr, searchSq, "V01_SCH", display, cPage);
						if (B01_SCH.size() > 0) {
							B01_Cnt = searchServiceEngine.getTotalCount();
							int[] rowRange = walker.setPageInfo(walker.request, B01_Cnt, display);
							cPage = String.valueOf(rowRange[0]) != null ? Integer.parseInt(String.valueOf(rowRange[0]))
									: 1;
							B01_SCH = searchServiceEngine.list("V01", searchStr, searchSq, "V01_SCH", display,
									(cPage / display) + 1);
							B01_Cnt = searchServiceEngine.getTotalCount();
						} else {
							B01_Cnt = 0;
						}
					}

					// 비디오광고선전
					if (mainGubun.equals("D01")) {

						D01_SCH = searchServiceEngine.list("E04", searchStr, searchSq, "E04_SCH", display, cPage);
						if (D01_SCH.size() > 0) {
							D01_Cnt = searchServiceEngine.getTotalCount();
							int[] rowRange = walker.setPageInfo(walker.request, D01_Cnt, display);
							cPage = String.valueOf(rowRange[0]) != null ? Integer.parseInt(String.valueOf(rowRange[0]))
									: 1;
							D01_SCH = searchServiceEngine.list("E04", searchStr, searchSq, "E04_SCH", display,
									(cPage / display) + 1);
							D01_Cnt = searchServiceEngine.getTotalCount();
						} else {
							D01_Cnt = 0;
						}
					}
				}

				/*
				 * System.out.println("C01_Cnt = "+C01_Cnt); System.out.println(
				 * "C02_Cnt = "+C02_Cnt);
				 */
				System.out.println("A01_Cnt =  " + A01_Cnt);
				System.out.println("A02_Cnt =  " + A02_Cnt);
				System.out.println("A03_Cnt =  " + A03_Cnt);
				System.out.println("A04_Cnt =  " + A04_Cnt);
				System.out.println("B01_Cnt =  " + B01_Cnt);
				System.out.println("D01_Cnt =  " + D01_Cnt);

				System.out.println("A01_SCH =  " + A01_SCH.toString());
				System.out.println("A02_SCH =  " + A02_SCH.toString());
				System.out.println("A03_SCH =  " + A03_SCH.toString());
				System.out.println("A04_SCH =  " + A04_SCH.toString());
				System.out.println("B01_SCH =  " + B01_SCH.toString());
				System.out.println("D01_SCH =  " + D01_SCH.toString());

				int endCnt = Integer
						.parseInt((((String) paramMap.get("endCnt") == null) ? "10" : (String) paramMap.get("endCnt")));// 조회될
																														// 레코드
																														// 수

				walker.setValue("paramMap", paramMap);
				walker.setValue("parameters", walker.makeParameterQuery());

				walker.setValue("A01_SCH", A01_SCH);
				walker.setValue("A01_Cnt", A01_Cnt);

				walker.setValue("A02_SCH", A02_SCH);
				walker.setValue("A02_Cnt", A02_Cnt);

				walker.setValue("A03_SCH", A03_SCH);
				walker.setValue("A03_Cnt", A03_Cnt);

				walker.setValue("A04_SCH", A04_SCH);
				walker.setValue("A04_Cnt", A04_Cnt);

				walker.setValue("B01_SCH", B01_SCH);
				walker.setValue("B01_Cnt", B01_Cnt);

				walker.setValue("D01_SCH", D01_SCH);
				walker.setValue("D01_Cnt", D01_Cnt);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "common/searchResult2";
	}

	/**
	 * 20170412 Add 검색엔진 결과에 첨부파일을 붙여서 나오도록 추가
	 * 
	 * @param request
	 * @param response
	 * @param commandMap
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search/SearchGetFileList.do")
	public String searchGetFileList(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> commandMap, ModelMap model) throws Exception {

		BaseWalker walker = null;

		try {
			walker = this.baseWalker.get();
			String initResult = walker.init(request, response);

			if (!walker.isNull(initResult))
				return initResult;

			commonService.searchGetFileList(walker);

		} catch (Exception e) {
			return walker.returnErrorPage("ERR-C012", new Object[] { "검색엔진 > 증빙자료가져오기 에러 " }, e);
		}

		return "common/searchFileResult";
	}

	/**
	 * 20170412 Add 검색엔진 결과에 첨부파일을 붙여서 나오도록 추가 : 위원 검색엔진 파일 찾기
	 * 
	 * @param request
	 * @param response
	 * @param commandMap
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search/SearchGetFileList2.do")
	public String searchGetFileList2(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> commandMap, ModelMap model) throws Exception {

		BaseWalker walker = null;

		try {
			walker = this.baseWalker.get();
			String initResult = walker.init(request, response);

			if (!walker.isNull(initResult))
				return initResult;

			commonService.searchGetFileList(walker);

		} catch (Exception e) {
			return walker.returnErrorPage("ERR-C012", new Object[] { "검색엔진 > 증빙자료가져오기 에러 " }, e);
		}

		return "common/searchFileResult2";
	}

	/**
	 * 20170412 Add 검색엔진 결과에 TMP_RESULT_NOTICE 에서 필요한 항목 추출
	 * 
	 * @param request
	 * @param response
	 * @param commandMap
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search/SearchGetEtcInfo.do")
	public String searchGetEtcInfo(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> commandMap, ModelMap model) throws Exception {

		BaseWalker walker = null;

		try {
			walker = this.baseWalker.get();
			String initResult = walker.init(request, response);

			if (!walker.isNull(initResult))
				return initResult;

			commonService.searchGetEtcInfo(walker);

		} catch (Exception e) {
			return walker.returnErrorPage("ERR-C012", new Object[] { "검색엔진 > TMP_RESULT_NOTICE 데이터 추출 에러 " }, e);
		}

		return "common/searchEtcInfo";
	}

	/*********************************
	 * ******************************** 2022 12 19 실질적인
	 * 컨트롤러 @@*********************************
	 * **************************************************************
	 **/

	@RequestMapping(value = "/search/TotalSearchEngine.do")
	public String searchAPItest2Prco(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap, ModelMap model) throws Exception {
		BaseWalker walker = null;

		try {
			walker = this.baseWalker.get();
			String initResult = walker.init(request, response);
			if (!walker.isNull(initResult))
				return initResult;
			paramMap = walker.getParamToMap(walker.request, false);
			paramMap.put("isPaging", "N");
			paramMap.put("isSearch", "Y");
			// 접속한 사용자 권한 가져오기
			paramMap.put("user_div_code", walker.session.getAttribute("user_div_code"));
			
			
			 System.out.println("@@@@@@@@@@@@param@@@@@@@@@@@@@@@@@");
			 System.out.println(paramMap);
			 System.out.println(walker.session.getAttribute("user_div_code"));
			 System.out.println("@@@@@@@@@@@@param@@@@@@@@@@@@@@@@@");
			 

			/***** 인기검색어 처리하는 곳 ********/
			// domain_no=2 : 주간
			String hotKeywordUrl = "http://192.168.11.242:7614/ksf/api/rankings?domain_no=2&max_count=10";
			Map<String, List<String>> hotKeywordReusult = hotKeywordFunc(hotKeywordUrl);
			List<String> hotkeywordResultListOrigin = (List<String>) hotKeywordReusult.get("resultList");
			List<String> hotKeywordResultStateList = (List<String>) hotKeywordReusult.get("resultStateList");
			
			List<String> hotkeywordResultList = new ArrayList<String>();
			//인기검색어 중에 따옴표 검색으로 인한 \범죄\ 같은 데이터들이 있다면, 필터해주기.
			for(String str : hotkeywordResultListOrigin){
				if(str.indexOf("\\") == -1){
					hotkeywordResultList.add(str);
				}
			}
			
			
			paramMap.put("hotKeywordResultList", hotkeywordResultList);
			paramMap.put("hotKeywordResultStateList", hotKeywordResultStateList);
			
			// 찬우 추가
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println(hotkeywordResultListOrigin);
			System.out.println(hotKeywordResultStateList);
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			
			// System.out.println(hotKeywordReusult.get("resultList"));
			/***** 인기검색어 처리하는 곳 ********/

			// null처리는 필수!
			// 파라미터 목록 가져오기. * 검색어, 검색구분, 섹션구분
			String searchStr = (String) paramMap.get("searchStr");

			// 20230202 추가 / 검색어는 빈 값이어도, 상세검색 시에는 검색이 되게끔 하기 위해서.. 아래의
			// searchStr이 빈 값일때 return하는 로직을 피하고자 만듬
			String searchStrNullChk_detail_visibleChk = (String) paramMap.get("detail_visibleChk");
			if (searchStrNullChk_detail_visibleChk == null) {
				// 상세검색 체크 변수값의 기본값으로 N을 준다. (널 포인터 예외 방지)
				paramMap.put("detail_visibleChk", "N");
			}

			//검색어도 없고, 키워드도없고 ( 키워드 클릭 시, 검색어는 빈값으로 오기떄문에 이것도 체크) , 상세조건도 N이라면? -->  기본적인 paramMap만 보내고 메인페이지로 보낸다.
			//(이 로직마저 없으면 페이지가 이상하게 꺠짐 (예외처리하는 셈 ))
			if ((searchStr == null || searchStr.equals("")) && paramMap.get("hotkeywordChk") == null && paramMap.get("detail_visibleChk").equals("N")) {
				paramMap.put("documentStart", "documentStart");
				walker.setValue("paramMap", paramMap);
				walker.setValue("parameters", walker.makeParameterQuery());
				return "common/searchEngineResult";
			}

			// mainPage로 return 보내는 로직을 통과했는데도, searchStr이 null이면 상세조건때문에 통과했다는
			// 의미임.
			// 아래 로직에서 NO_VALUE라면, 상세검색의 조건으로만 검색하도록 로직을 구성함.
			if (searchStr == null || searchStr.equals("")) {
				searchStr = "NO_VALUE";
				paramMap.put("searchStr", "NO_VALUE");
			}
			
			
			
			//추가 결과내 재검색 체크유무 ( ""에 대한 검색결과 총 ~건의 검색결과를 찾았습니다. 멘트에 재검색 관련하여 정보를 보여주기 위한 로직)
			// ex)  범죄 > 도시 > 재검색3 > 재검색4 이런 식으로..
			if(paramMap.get("result_re_search_chk").equals("Y")){
				
				
			  if(paramMap.get("searchStrHistory") != null){
				if(paramMap.get("searchStrPlusWhiteSpace") != null){
					paramMap.put("searchStrHistory",(String)paramMap.get("searchStrHistory") + "  >  "  + (String)paramMap.get("searchStrHistoryPrev"));
				}
				
				
				//결과내 재검색 시, 중복된 단어들도 생략하기 위해서. 앞에서 결과내 재검색으로 검색한 단어가 있는지 체크한 후, 검색한 단어가 있다면, jsp에서 공백 검색 시, 이전 검색어 목록을 보여준 그 로직에 조건문을 추가하여 그 해당 로직을 사용하면된다.
				String searchHistory = (String)paramMap.get("searchStrHistory");
				String searchHistoryNoWhiteSpace = searchHistory.replaceAll(" ", "");
				String[] searchHistorySplit = searchHistoryNoWhiteSpace.split(">");				
				List<String> searchHistorySplitList = Arrays.asList(searchHistorySplit);
				
				if(searchHistorySplitList.contains(searchStr)){
					paramMap.put("re_search_WordExist", "re_search_WordExist");
				}
				//결과내 재검색 시, 중복된 단어들 생략로직 끝
				
				
				
				
				
				//체크가 되어있다면
				
				// re_search_chk : 현재 입력한 검색어               만약에 null 이라면 ""로 처리하기   . JSP 에서 활용하고 있음
				// searchStrHistody : 이전에 입력한 검색어    만약에 null 이라면  ""로 처리하기.  JSP 에서 활용하고 있음
				
				
				//* (매모)re_search_chk 변수는 현재 사용하지 않고있음  --  result_re_search_chk 변수명이랑 착오가 없어야 함.. result_re_search_chk변수는 많이 쓰임.
				if(searchStr.equals("NO_VALUE")){
					paramMap.put("re_search_chk", "no_value");
				} else {
					paramMap.put("re_search_chk", searchStr);
				}
				
				//탭 클릭 유무 --> 탭 클릭 후 화면 전환 시에는 검색결과 멘트에 영향을 주면 안되기 때문에..
				if(paramMap.get("tabClickChk").equals("1")){
					paramMap.put("tabClickChkOk", "checking" );
				}
				//사이드메뉴 클릭 유무 --> 사이드메뉴 클릭 후 화면 전환 시에는 검색결과 멘트에 영향을 주면 안되기 때문에..
				if(paramMap.get("sideMenuClickChk").equals("1")){
					paramMap.put("sideMenuClickChkOk", "checking" );
				}
			  }
				
				
				if(paramMap.get("searchStrHistory") == null){
					paramMap.put("searchStrHistory", "");
				} else {
					paramMap.put("searchStrHistory", paramMap.get("searchStrHistory"));
				}
			} else {
				//체크가 되어있지 않다면
				paramMap.put("re_search_chk", "");
			}
			//결과내 재검색 체크유무 끝
			

			String searchGubun = (String) paramMap.get("searchGubun");
			String[] sectionGubun = null;
			if (request.getParameterValues("sectionGubun") == null) {
				sectionGubun = new String[] { "Total" };
			} else {
				sectionGubun = request.getParameterValues("sectionGubun");
			}
			List<String> sectionGubunList = new ArrayList<String>(Arrays.asList(sectionGubun));

			
			//자동완성 켜기/끄기 변숫값 저장
			if(paramMap.get("autocompleteChk") == null || paramMap.get("autocompleteChk").equals("")){
				paramMap.put("autocompleteChk", "");
			} else{
				paramMap.put("autocompleteChk", ((String)paramMap.get("autocompleteChk")).trim());
			}
			
			
			
			
			/*************** 검색어에 숫자가 들어올떄 *******************************/
			long searchStrInt = 0;
			if (paramMap.get("searchStr") != null
					&& ((String) paramMap.get("searchStr")).matches("[-+]?\\d*\\.?\\d+")) {
				searchStrInt = Long.valueOf((String) paramMap.get("searchStr"));
			}
			/*************** 검색어에 숫자가 들어올떄 *******************************/

			// 만약 오타교정으로 인한 검색이라면? jsp에서 오타교정에 관한 변수를 넘겨받아서 처리한다. 시작
			if (paramMap.get("typoChk") != null) {
				if (paramMap.get("typoChk").equals("Y")) {
					searchStr = (String) paramMap.get("typoSearchStr");
					paramMap.put("searchStr", searchStr);
				}
			}
			// 만약 오타교정으로 인한 검색이라면? jsp에서 오타교정에 관한 변수를 넘겨받아서 처리한다. 끝
			// 만약 인기 검색어의 링크로 인한 검색이라면? jsp에서 인기 검색어에 관한 변수를 넘겨받아서 처리한다. 시작
			if (paramMap.get("hotkeywordChk") != null) {
				if (paramMap.get("hotkeywordChk").equals("Y")) {
					searchStr = (String) paramMap.get("hotkeywordSearchStr");
					paramMap.put("searchStr", searchStr);
				}
			}
			// 만약 인기 검색어의 링크로 인한 검색이라면? jsp에서 인기 검색어에 관한 변수를 넘겨받아서 처리한다. 끝
			// 만약 연관 검색어의 링크로 인한 검색이라면? jsp에서 인기 검색어에 관한 변수를 넘겨받아서 처리한다. 시작
			if (paramMap.get("suggestionChk") != null) {
				if (paramMap.get("suggestionChk").equals("Y")) {
					searchStr = (String) paramMap.get("suggestionSearchStr");
					paramMap.put("searchStr", searchStr);
				}
			}
			System.out.println(searchStr);
			// 만약 연관 검색어의 링크로 인한 검색이라면? jsp에서 인기 검색어에 관한 변수를 넘겨받아서 처리한다. 끝

			// 최종 URL
			String totalUrl = "";
			// 공통 URL
			String result_noticeUrl = "http://192.168.11.242:7577/search5?select=*&from=result_notice.result_notice&where=";
			String result_opinUrl = "http://192.168.11.242:7577/search5?select=*&from=result_opin.result_opin&where=";
			String result_fileUrl = "http://192.168.11.242:7577/search5?select=*&from=result_file.result_file&where=";
			String result_imageUrl = "http://192.168.11.242:7577/search5?select=*&from=result_image.result_image&where=";
			String result_moniUrl = "http://192.168.11.242:7577/search5?select=*&from=result_moni.result_moni&where=";
			String result_surveyUrl = "http://192.168.11.242:7577/search5?select=*&from=result_survey.result_survey&where=";
			// 공통 인코딩 목록
			String encode = "UTF-8";
			String encodeSearchStr = URLEncoder.encode(searchStr, encode); // 검색어
			String encodeSearchGubun = URLEncoder.encode(searchGubun, encode); // 검색구분(통합검색,
																				// 제목,
																				// 내용,
																				// 결정사유
																				// 등등)
			String encodeAnd = URLEncoder.encode(" and ", encode);
			String encodeOr = URLEncoder.encode(" or ", encode);
			String encodeAns = URLEncoder.encode("=", encode);
			String encodeInOpen = URLEncoder.encode(" in{", encode);
			String encodeInClose = URLEncoder.encode("}", encode);
			String encodeLGual = "(";
			String encodeRGual = ")";
			String encodeLike = URLEncoder.encode(" like ", encode);
			String strMark = URLEncoder.encode("'", encode);
			String between = URLEncoder.encode(" between ", encode);

			// where절의 text타입에만 사용가능.
			String allword = URLEncoder.encode(" allword ", encode);
			String anyword = URLEncoder.encode(" anyword ", encode);
			String natural = URLEncoder.encode(" natural ", encode);

			// 동의어 검색
			String synonym = URLEncoder.encode(" synonym ", encode);

			// 카테고리 정렬 ex) ORDER BY $CATEGORYFIELD(field_name(domain), keyword)
			String categoryOrder = URLEncoder.encode(" $categoryfield(use_title(), 검색어넣기 ", encode);

			/**
			 * 17501 : 국내영화 17502 : 국외영화 17505 : 광고영화 17511 : 국내비디오 17512 :
			 * 국외비디오
			 */

			// medi_code 인코딩 목록
			String medi_mv = URLEncoder.encode("'17501','17502'", encode);
			String medi_ad = URLEncoder.encode("'17503','17504','17505','17506','17514'", encode);
			String medi_video = URLEncoder.encode("'17511','17512'", encode);
			String medi_perform = URLEncoder.encode("'17521','17522','17523','17524'", encode);

			/*
			 * //첫 로딩 시 데이터를 표시해주는 역할을 함. if(searchGubun == null ){ searchGubun
			 * = "All"; } else if(searchGubun.trim().isEmpty()){ searchGubun =
			 * "All"; }
			 */

			String orderValue = (String) request.getParameter("orderValue");
			if (orderValue == null || orderValue.equals("")) {
				orderValue = URLEncoder.encode(" order by $matchfield(use_title,use1) desc, $relevance desc ", encode);
			}
			if (orderValue.equals("orderRelevance")) {
				orderValue = URLEncoder.encode(" order by $matchfield(use_title,use1) desc, $relevance desc ", encode);
			}
			if (orderValue.equals("orderGa")) {
				orderValue = "%20order%20by%20use_title_sort%20asc%20";
			}
			if (orderValue.equals("orderDate")) {
				orderValue = "%20order%20by%20rcv_date%20desc%20";
			}

			//
			String groupByTest = URLEncoder.encode(" order by $relevance desc ", encode);

			// 2022 12 15 페이징 처리 시작

			// 1. sectionGubun을 하나만 선택하거나, -더보기 클릭 시, sectionGubun에는 딱 하나의 데이터가
			// 들어있고, 페이지는 1페이지로 이동한다. -->sectionGubun이 딱 하나라면 (Total)이 아닐떄 ,
			// 페이징목록이 보이게끔 유도하기.
			// 페이지는 페이지 목록 버튼 시 마다 2페이지 3페이지 이동한다... 그러다가 다시 검색버튼 같은것을 누르면 1페이지로
			// 초기화!

			// 기존 display가 limit로 대체

			int cPage = 1;
			int offset = 0;
			int pagelength = 10;
			int limit = 5;

			offset = (cPage - 1) * pagelength;

			if (!sectionGubunList.contains("Total") && sectionGubunList.size() == 1) {
				limit = 10;
				// System.out.println("검색창 아래의 체크박스 카테고리가 딱 한개만 선택했을떄 실행되는
				// 부분@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			}
			// 페이징 처리 끝
			// 페이징 테스트

			/**************** 상세보기 시작 ********************************/
			// 20221216 상세보기에 관련된 것들 맨 위로 빼기 (기본값이 존재함)
			// 하지만 상세보기를 하지 않는다면 이 값이 null이라서 오류가뜸 ~~~~~~~~~~~~~~~~~~~~~~~~~~~

			// 상세보기를 클릭했는지 여부
			String detail_visibleChk = (String) paramMap.get("detail_visibleChk");

			// 연산자 가져오기
			String searchOperation = "";

			// 결과 수
			int detailresultLine = 3;
			try {
				if ((String) paramMap.get("detailSearch_resultLine") != null) {
					detailresultLine = Integer.valueOf((String) paramMap.get("detailSearch_resultLine"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (detailresultLine != 3 && (String) paramMap.get("detailSearch_resultLine") != null) {
					detailresultLine = detailresultLine;
				} else {
					detailresultLine = 3;
				}
			}

			try {
				if (detail_visibleChk.equals("Y")) {
					searchOperation = (String) paramMap.get("detailSearch_radio");
				} else {
					searchOperation = "natural";
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if ((String) paramMap.get("detailSearch_radio") == null) {
					searchOperation = "natural";
				}
			}

			if (searchOperation.equals("allword")) {
				searchOperation = allword;
			} else if (searchOperation.equals("anyword")) {
				searchOperation = anyword;
			} else if (searchOperation.equals("natural")) {
				searchOperation = natural;
			} else {
				searchOperation = natural;
			}

			// 신청회사 null 처리
			String detailAplc = "";
			try {
				if ((String) paramMap.get("detailSearch_aplc") != null) {
					detailAplc = (String) paramMap.get("detailSearch_aplc");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if ((String) paramMap.get("detailSearch_aplc") == null) {
					detailAplc = "";
				}
			}

			// 상세보기-일자 데이터 가져오기 및 null 처리 시작

			// 시작날짜
			String period_start = "";
			try {
				if ((String) paramMap.get("period_start") != null) {
					period_start = (String) paramMap.get("period_start");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if ((String) paramMap.get("period_start") == null) {
					period_start = "";
				}
			}

			// 종료날짜
			String period_end = "";
			try {
				if ((String) paramMap.get("period_end") != null) {
					period_end = (String) paramMap.get("period_end");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if ((String) paramMap.get("period_end") == null) {
					period_end = "";
				}
			}

			// 등급분류일자, 신청일자 , 접수일자 구분
			String detail_dateGubun = "";
			try {
				if ((String) paramMap.get("detailSearch_date") != null) {
					detail_dateGubun = (String) paramMap.get("detailSearch_date");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if ((String) paramMap.get("detailSearch_date") == null) {
					detail_dateGubun = "gradedate";
				}
			}

			if (!period_start.equals("")) {
				period_start = period_start.substring(0, 4) + period_start.substring(5, 7)
						+ period_start.substring(8, 10) + "000000";
			}
			if (!period_end.equals("")) {
				period_end = period_end.substring(0, 4) + period_end.substring(5, 7) + period_end.substring(8, 10)
						+ "000000";
			}

			// 듈 중에 하나를 선택안하고 상세검색을 하면 --> 전체날짜로 인식하여 데이터검색하기
			if ((period_start.equals("") || period_end.equals(""))
					&& !(period_start.equals("") && period_end.equals(""))) {
				period_start = "";
				period_end = "";

			}
			// 상세보기-일자 데이터 가져오기 및 null 처리 끝

			/**************** 상세보기 끝 ********************************/

			/***** 오타교정, 추천검색어 등등 처리하는 곳 ********/

			String typo_correction = "http://192.168.11.242:7614/ksf/api/suggest?target=spell&term=";
			List<String> correctStrList = typocorrectionFunc(typo_correction, encodeSearchStr);
			paramMap.put("correctStrList", correctStrList);

			
			//20230310 연관검색어 관련 URL 
			Date now = Calendar.getInstance().getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String formatedNow = formatter.format(now);
			String suggestionUrl = "http://192.168.11.242:7581/ksm/kla/api/data.jsp?output=json&fromDate=2019-03-03&site=&category=&target=subword&format=recommend&duration=3&count=100&hasHeadWord=true&toDate=" + formatedNow  + "&relatedWordCount=5&keyword=";
			
			List<String> suggestionStrList = suggestionFunc(suggestionUrl, encodeSearchStr);
			paramMap.put("suggestionStrList", suggestionStrList);
			
			/*
			 * 기존 추천검색어를 쓰던 로직임. --> 연관검색어로 변경됨
				String suggestionUrl = "http://192.168.11.242:7614/ksf/api/suggest?target=related&term=";
				List<String> suggestionStrList = suggestionFunc(suggestionUrl, encodeSearchStr);
				paramMap.put("suggestionStrList", suggestionStrList);
            */  

			/***** 오타교정, 추천검색어 등등 처리하는 곳 ********/

			/********************************
			 * 로그 처리하는 곳
			 ********************************/
			// 20220103

			// encodeSite는 해당 컨트롤러가 작동하는 시점의 페이지를 알면된다. 여기서는
			// 사이트명
			String encodeSite = URLEncoder.encode("MainPage", encode); // movie01()
																		// 등의
																		// 메소드에
																		// 넘겨주어야함.
			// 카테고리(Total, movie, video ...)
			String encodeCategory = ""; // movie01()메소드 등의 안에서 카테고리별로 movie01처럼
										// 삽입해줘야함
			/*
			 * if(sectionGubunList.contains("Total")){ encodeCategory =
			 * URLEncoder.encode( "Total" , encode); } else if { encodeCategory
			 * = URLEncoder.encode( "Total" , encode); } 일단 생략
			 */

			// 유저아이디
			String encodeUserid = URLEncoder.encode((String) paramMap.get("user_id"), encode);

			// 성별
			String encodeUsergender = "";

			// String 정렬방법? relevance , 가나다순, 날짜순
			// 검색어 던져주고, 이전검색어

			// sectionGubunList.contains 카테고리 체크 // Total은 Total로, 영화면 영화로..
			// 체크된것이 있는지.

			if(!encodeSearchStr.equals("NO_VALUE")){
				if(paramMap.get("searchStrHistoryPrev") != null){
					String searchStrHistoryPrev = URLEncoder.encode((String)  paramMap.get("searchStrHistoryPrev") , encode);
					String customLogURL = "http://192.168.11.242:7577/search5?select=*&from=result_notice.result_notice&pagelength=3&offset=0&limit=3&custom="
							+ encodeSite + "@" + "|" + encodeUserid + "||||^" + encodeSearchStr + "$" + searchStrHistoryPrev;
					logURLFunc(customLogURL);
				} else {
					String customLogURL = "http://192.168.11.242:7577/search5?select=*&from=result_notice.result_notice&pagelength=3&offset=0&limit=3&custom="
							+ encodeSite + "@" + "|" + encodeUserid + "||||^" + encodeSearchStr + "$";
					logURLFunc(customLogURL);
				}
			}

			/********************************
			 * 로그 처리하는 곳
			 ********************************/

			// 하지만 상세보기를 하지 않는다면 이 값이 null이라서 오류가뜸 ~~~~~~~~~~~~~~~~~~~~~~~~~~~
			///////////////////////////////////////////// 영화 시작 (17501,
			// 17502)///////////////////////////////////////////////////////////////////////////////////////////////////
			if (sectionGubunList.contains("movie01") || sectionGubunList.contains("Total")) {
				movie01(walker, paramMap, request, response);
			}
			///////////////////////////////////////////// 영화 끝 (17501,
			///////////////////////////////////////////// 17502)///////////////////////////////////////////////////////////////////////////////////////////////////

			///////////////////////////////////////////// 비디오 시작 (17511,
			///////////////////////////////////////////// 17512)///////////////////////////////////////////////////////////////////////////////////////////////////
			if (sectionGubunList.contains("video01") || sectionGubunList.contains("Total")) {
				video01(walker, paramMap, request, response);
			}
			///////////////////////////////////////////// 비디오 끝 (17511,
			///////////////////////////////////////////// 17512)///////////////////////////////////////////////////////////////////////////////////////////////////

			///////////////////////////////////////////// 광고물 시작 (17503, 17504,
			///////////////////////////////////////////// 17505, 17506,
			///////////////////////////////////////////// 17514)///////////////////////////////////////////////////////////////////////////////////////////////////
			if (sectionGubunList.contains("ad01") || sectionGubunList.contains("Total")) {
				ad01(walker, paramMap, request, response);
			}
			///////////////////////////////////////////// 광고물 끝 (17503, 17504,
			///////////////////////////////////////////// 17505, 17506,
			///////////////////////////////////////////// 17514)///////////////////////////////////////////////////////////////////////////////////////////////////

			///////////////////////////////////////////// 공연추천 시작 (17521, 17522,
			///////////////////////////////////////////// 17523,
			///////////////////////////////////////////// 17524)///////////////////////////////////////////////////////////////////////////////////////////////////
			if (sectionGubunList.contains("perform01") || sectionGubunList.contains("Total")) {
				perform01(walker, paramMap, request, response);
			}
			///////////////////////////////////////////// 공연추천 끝 (17521, 17522,
			///////////////////////////////////////////// 17523,
			///////////////////////////////////////////// 17524)///////////////////////////////////////////////////////////////////////////////////////////////////

			// 첨부파일 : file01
			// 매체파일에서 바뀌는점 : 검색구분에 따라 컬럼명을 바꿔줘야함.... 예를들어 기존에는 [검색구분 : 제목]으로
			// 선택을하면 use_title에 조건을 줬다면, 이제는 다른 컬럼이다.
			// medi_code도 의미없다. --> 테이블 전체임.

			// ★특이사항 : 이미지와 99% 유사함 .
			// label_div_id가 이미지에는 없지만, 파일에는 있음
			// ori_title이 파일은 text 타입이고, 이미지에서는 string 타입임.
			///////////////////////////////////////////// 첨부파일 시작
			// ()///////////////////////////////////////////////////////////////////////////////////////////////////
			file01(walker, paramMap, request, response);
			///////////////////////////////////////////// 첨부파일 끝
			///////////////////////////////////////////// ()///////////////////////////////////////////////////////////////////////////////////////////////////

			/////////////////////////////////////////// 이미지 시작
			/////////////////////////////////////////// ()///////////////////////////////////////////////////////////////////////////////////////////////////
			img01(walker, paramMap, request, response);
			/////////////////////////////////////////// 이미지 끝
			/////////////////////////////////////////// ()///////////////////////////////////////////////////////////////////////////////////////////////////

			/////////////////////////////////////////// 등급분류의견서 시작
			/////////////////////////////////////////// ()///////////////////////////////////////////////////////////////////////////////////////////////////
			opin01(walker, paramMap, request, response);
			/////////////////////////////////////////// 등급분류의견서 끝
			/////////////////////////////////////////// ()///////////////////////////////////////////////////////////////////////////////////////////////////

			// ★특이사항 : ori_title이 string타입이다.
			/////////////////////////////////////////// 모니터링의견서 시작
			// ()///////////////////////////////////////////////////////////////////////////////////////////////////
			moni01(walker, paramMap, request, response);
			///////////////////////////////////////// 모니터링의견서 끝
			///////////////////////////////////////// ()///////////////////////////////////////////////////////////////////////////////////////////////////

			/**************
			 * ■■■■■■■■■■■■■*************************************우측사이드에 관한
			 * information 시작
			 * *************************************************■■■■■■■■■
			 **************************************************/

			/////////////////////////////////////////// 자가등급의견서 시작
			/////////////////////////////////////////// ()///////////////////////////////////////////////////////////////////////////////////////////////////
			if (sectionGubunList.contains("01") || sectionGubunList.contains("Total")) {
			}

			walker.setValue("paramMap", paramMap);
			walker.setValue("parameters", walker.makeParameterQuery());

			// 상세보기 검색 다양화를 위한 데이터 전
			if ((sectionGubunList.contains("movie01") || sectionGubunList.contains("video01"))
					&& !(sectionGubunList.contains("ad01") || sectionGubunList.contains("perform01")
							|| sectionGubunList.contains("opin01") || sectionGubunList.contains("img01")
							|| sectionGubunList.contains("file01") || sectionGubunList.contains("moni01"))) {
				//System.out.println("영화,비디오");
				paramMap.put("detailGubun", "movie01_video01");
			} else if (sectionGubunList.contains("ad01") && !(sectionGubunList.contains("movie01")
					|| sectionGubunList.contains("video01") || sectionGubunList.contains("perform01")
					|| sectionGubunList.contains("opin01") || sectionGubunList.contains("img01")
					|| sectionGubunList.contains("file01") || sectionGubunList.contains("moni01"))) {
				//System.out.println("광고물");
				paramMap.put("detailGubun", "ad01");
			} else if (sectionGubunList.contains("perform01") && !(sectionGubunList.contains("movie01")
					|| sectionGubunList.contains("video01") || sectionGubunList.contains("ad01")
					|| sectionGubunList.contains("opin01") || sectionGubunList.contains("img01")
					|| sectionGubunList.contains("file01") || sectionGubunList.contains("moni01"))) {
				//System.out.println("공연추천");
				paramMap.put("detailGubun", "perform01");
			} else if (sectionGubunList.contains("opin01") && !(sectionGubunList.contains("movie01")
					|| sectionGubunList.contains("video01") || sectionGubunList.contains("ad01")
					|| sectionGubunList.contains("perform01") || sectionGubunList.contains("img01")
					|| sectionGubunList.contains("file01") || sectionGubunList.contains("moni01"))) {
				//System.out.println("등급분류의견서");
				paramMap.put("detailGubun", "opin01");
			} else if (sectionGubunList.contains("img01") && !(sectionGubunList.contains("movie01")
					|| sectionGubunList.contains("video01") || sectionGubunList.contains("ad01")
					|| sectionGubunList.contains("perform01") || sectionGubunList.contains("opin01")
					|| sectionGubunList.contains("file01") || sectionGubunList.contains("moni01"))) {
				//System.out.println("이미지");
				paramMap.put("detailGubun", "img01");
			} else if (sectionGubunList.contains("file01") && !(sectionGubunList.contains("movie01")
					|| sectionGubunList.contains("video01") || sectionGubunList.contains("ad01")
					|| sectionGubunList.contains("perform01") || sectionGubunList.contains("opin01")
					|| sectionGubunList.contains("img01") || sectionGubunList.contains("moni01"))) {
				//System.out.println("파일첨부");
				paramMap.put("detailGubun", "file01");
			} else if (sectionGubunList.contains("moni01") && !(sectionGubunList.contains("movie01")
					|| sectionGubunList.contains("video01") || sectionGubunList.contains("ad01")
					|| sectionGubunList.contains("perform01") || sectionGubunList.contains("opin01")
					|| sectionGubunList.contains("img01") || sectionGubunList.contains("file01"))) {
				///System.out.println("모니터링의견서");
				paramMap.put("detailGubun", "moni01");
			} else {
				paramMap.put("detailGubun", "remove");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "common/searchEngineResult";
	}

	// 더보기 또는 우측사이드 메뉴 클릭 시 URL을 넘겨받아, 상세보기 페이지를 제공한다.

	@RequestMapping(value = "/search/SearchEnginePaging.do")
	public String searchEngineAPIPaging(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap, ModelMap model) throws Exception {

		BaseWalker walker = null;

		try {
			walker = this.baseWalker.get();
			String initResult = walker.init(request, response);
			if (!walker.isNull(initResult))
				return initResult;
			paramMap = walker.getParamToMap(walker.request, false);
			paramMap.put("isPaging", "Y");
			paramMap.put("isSearch", "Y");
			// 권한 데이터 보내주기.
			paramMap.put("user_div_code", walker.session.getAttribute("user_div_code"));
			/*
			 * System.out.println("@@@@@@@@@@@@param@@@@@@@@@@@@@@@@@");
			 * System.out.println(paramMap);
			 * System.out.println("@@@@@@@@@@@@param@@@@@@@@@@@@@@@@@");
			 */
			// null처리는 필수!
			// 파라미터 목록 가져오기. * 검색어, 검색구분, 섹션구분
			String searchStr = (String) paramMap.get("searchStr");
			String searchGubun = (String) paramMap.get("searchGubun");
			String[] sectionGubun = null;
			if (request.getParameterValues("sectionGubun") == null) {
				sectionGubun = new String[] { "Total" };
			} else {
				sectionGubun = request.getParameterValues("sectionGubun");
			}
			List<String> sectionGubunList = new ArrayList<String>(Arrays.asList(sectionGubun));

			/***** 인기검색어 처리하는 곳 ********/
			// domain_no=2 : 주간
			String hotKeywordUrl = "http://192.168.11.242:7614/ksf/api/rankings?domain_no=2&max_count=10";
			Map<String, List<String>> hotKeywordReusult = hotKeywordFunc(hotKeywordUrl);
			List<String> hotkeywordResultListOrigin = (List<String>) hotKeywordReusult.get("resultList");
			List<String> hotKeywordResultStateList = (List<String>) hotKeywordReusult.get("resultStateList");
			
			List<String> hotkeywordResultList = new ArrayList<String>();
			//인기검색어 중에 따옴표 검색으로 인한 \범죄\ 같은 데이터들이 있다면, 필터해주기.
			for(String str : hotkeywordResultListOrigin){
				if(str.indexOf("\\") == -1){
					hotkeywordResultList.add(str);
				}
			}
			paramMap.put("hotKeywordResultList", hotkeywordResultList);
			paramMap.put("hotKeywordResultStateList", hotKeywordResultStateList);
			// System.out.println(hotKeywordReusult.get("resultList"));
			/***** 인기검색어 처리하는 곳 ********/

						// 20230202 추가 / 검색어는 빈 값이어도, 상세검색 시에는 검색이 되게끔 하기 위해서.. 아래의
						// searchStr이 빈 값일때 return하는 로직을 피하고자 만듬
						String searchStrNullChk_detail_visibleChk = (String) paramMap.get("detail_visibleChk");
						if (searchStrNullChk_detail_visibleChk == null) {
							// 상세검색 체크 변수값의 기본값으로 N을 준다. (널 포인터 예외 방지)
							paramMap.put("detail_visibleChk", "N");
						}

						//검색어도 없고, 키워드도없고 ( 키워드 클릭 시, 검색어는 빈값으로 오기떄문에 이것도 체크) , 상세조건도 N이라면? -->  기본적인 paramMap만 보내고 메인페이지로 보낸다.
						//(이 로직마저 없으면 페이지가 이상하게 꺠짐 (예외처리하는 셈 ))
						if ((searchStr == null || searchStr.equals("")) && paramMap.get("hotkeywordChk") == null && paramMap.get("detail_visibleChk").equals("N")) {
							walker.setValue("paramMap", paramMap);
							walker.setValue("parameters", walker.makeParameterQuery());
							return "common/searchEngineResult";
						}

						// mainPage로 return 보내는 로직을 통과했는데도, searchStr이 null이면 상세조건때문에 통과했다는
						// 의미임.
						// 아래 로직에서 NO_VALUE라면, 상세검색의 조건으로만 검색하도록 로직을 구성함.
						if (searchStr == null || searchStr.equals("")) {
							searchStr = "NO_VALUE";
							paramMap.put("searchStr", "NO_VALUE");
						}
						
						
						//20230303 추가 결과내 재검색 체크유무 ( ""에 대한 검색결과 총 ~건의 검색결과를 찾았습니다. 멘트에 재검색 관련하여 정보를 보여주기 위한 로직)
						if(paramMap.get("result_re_search_chk").equals("Y")){
							
							
						 if(paramMap.get("searchStrHistory") != null){
							if(paramMap.get("searchStrPlusWhiteSpace") != null){
								paramMap.put("searchStrHistory",(String)paramMap.get("searchStrHistory") + "  >  "  + (String)paramMap.get("searchStrHistoryPrev"));
							}
							
							
							//결과내 재검색 시, 중복된 단어들도 생략하기 위해서. 앞에서 결과내 재검색으로 검색한 단어가 있는지 체크한 후, 검색한 단어가 있다면, jsp에서 공백 검색 시, 이전 검색어 목록을 보여준 그 로직에 조건문을 추가하여 그 해당 로직을 사용하면된다.
							String searchHistory = (String)paramMap.get("searchStrHistory");
							String searchHistoryNoWhiteSpace = searchHistory.replaceAll(" ", "");
							String[] searchHistorySplit = searchHistoryNoWhiteSpace.split(">");				
							List<String> searchHistorySplitList = Arrays.asList(searchHistorySplit);
							
							if(searchHistorySplitList.contains(searchStr)){
								paramMap.put("re_search_WordExist", "re_search_WordExist");
							}
							//결과내 재검색 시, 중복된 단어들 생략로직 끝
							
							
							//체크가 되어있다면
							
							// re_search_chk : 현재 입력한 검색어               만약에 null 이라면 ""로 처리하기
							// searchStrHistody : 이전에 입력한 검색어    만약에 null 이라면  ""로 처리하기.
							
							//* (매모)re_search_chk 변수는 현재 사용하지 않고있음  --  result_re_search_chk 변수명이랑 착오가 없어야 함.. result_re_search_chk변수는 많이 쓰임.
							if(searchStr.equals("NO_VALUE")){
								paramMap.put("re_search_chk", "no_value");
							} else {
								paramMap.put("re_search_chk", searchStr);
							}
							
							//탭 클릭 유무 --> 탭 클릭 후 화면 전환 시에는 검색결과 멘트에 영향을 주면 안되기 때문에..
							if(paramMap.get("tabClickChk").equals("1")){
								paramMap.put("tabClickChkOk", "checking" );
							}
							//사이드메뉴 클릭 유무 --> 사이드메뉴 클릭 후 화면 전환 시에는 검색결과 멘트에 영향을 주면 안되기 때문에..
							if(paramMap.get("sideMenuClickChk").equals("1")){
								paramMap.put("sideMenuClickChkOk", "checking" );
							}
						 }
							
							
							if(paramMap.get("searchStrHistory") == null){
								paramMap.put("searchStrHistory", "");
							} else {
								paramMap.put("searchStrHistory", paramMap.get("searchStrHistory"));
							}
						} else {
							//체크가 되어있지 않다면
							paramMap.put("re_search_chk", "");
						}
						//결과내 재검색 체크유무 끝
						
						//자동완성 켜기/끄기 변숫값 저장
						if(paramMap.get("autocompleteChk") == null || paramMap.get("autocompleteChk").equals("")){
							paramMap.put("autocompleteChk", "");
						} else{
							paramMap.put("autocompleteChk", ((String)paramMap.get("autocompleteChk")).trim());
						}
						
						
						
			/*************** 검색어에 숫자가 들어올떄 *******************************/
			long searchStrInt = 0;
			if (paramMap.get("searchStr") != null
					&& ((String) paramMap.get("searchStr")).matches("[-+]?\\d*\\.?\\d+")) {
				searchStrInt = Long.valueOf((String) paramMap.get("searchStr"));
			}
			/*************** 검색어에 숫자가 들어올떄 *******************************/
			// 만약 오타교정으로 인한 검색이라면? jsp에서 오타교정에 관한 변수를 넘겨받아서 처리한다. 시작
			if (paramMap.get("typoChk") != null) {
				if (paramMap.get("typoChk").equals("Y")) {
					searchStr = (String) paramMap.get("typoSearchStr");
					paramMap.put("searchStr", searchStr);
				}
			}
			// 만약 오타교정으로 인한 검색이라면? jsp에서 오타교정에 관한 변수를 넘겨받아서 처리한다. 끝
			// 만약 인기 검색어의 링크로 인한 검색이라면? jsp에서 인기 검색어에 관한 변수를 넘겨받아서 처리한다. 시작
			if (paramMap.get("hotkeywordChk") != null) {
				if (paramMap.get("hotkeywordChk").equals("Y")) {
					searchStr = (String) paramMap.get("hotkeywordSearchStr");
					paramMap.put("searchStr", searchStr);
				}
			}
			// 만약 인기 검색어의 링크로 인한 검색이라면? jsp에서 인기 검색어에 관한 변수를 넘겨받아서 처리한다. 끝
			// 만약 연관 검색어의 링크로 인한 검색이라면? jsp에서 인기 검색어에 관한 변수를 넘겨받아서 처리한다. 시작
			if (paramMap.get("suggestionChk") != null) {
				if (paramMap.get("suggestionChk").equals("Y")) {
					searchStr = (String) paramMap.get("suggestionSearchStr");
					paramMap.put("searchStr", searchStr);
				}
			}
			// 만약 연관 검색어의 링크로 인한 검색이라면? jsp에서 인기 검색어에 관한 변수를 넘겨받아서 처리한다. 끝

			// 최종 URL
			String totalUrl = "";
			// 공통 URL
			String result_noticeUrl = "http://192.168.11.242:7577/search5?select=*&from=result_notice.result_notice&where=";
			String result_opinUrl = "http://192.168.11.242:7577/search5?select=*&from=result_opin.result_opin&where=";
			String result_fileUrl = "http://192.168.11.242:7577/search5?select=*&from=result_file.result_file&where=";
			String result_imageUrl = "http://192.168.11.242:7577/search5?select=*&from=result_image.result_image&where=";
			String result_moniUrl = "http://192.168.11.242:7577/search5?select=*&from=result_moni.result_moni&where=";
			String result_surveyUrl = "http://192.168.11.242:7577/search5?select=*&from=result_survey.result_survey&where=";
			// 공통 인코딩 목록
			String encode = "UTF-8";
			String encodeSearchStr = URLEncoder.encode(searchStr, encode); // 검색어
			String encodeSearchGubun = URLEncoder.encode(searchGubun, encode); // 검색구분(통합검색,
																				// 제목,
																				// 내용,
																				// 결정사유
																				// 등등)
			String encodeAnd = URLEncoder.encode(" and ", encode);
			String encodeOr = URLEncoder.encode(" or ", encode);
			String encodeAns = URLEncoder.encode("=", encode);
			String encodeInOpen = URLEncoder.encode(" in{", encode);
			String encodeInClose = URLEncoder.encode("}", encode);
			String encodeLGual = "(";
			String encodeRGual = ")";
			String encodeLike = URLEncoder.encode(" like ", encode);
			String strMark = URLEncoder.encode("'", encode);
			String between = URLEncoder.encode(" between ", encode);

			// where절의 text타입에만 사용가능.
			String allword = URLEncoder.encode(" allword ", encode);
			String anyword = URLEncoder.encode(" anyword ", encode);
			String natural = URLEncoder.encode(" natural ", encode);

			// 동의어 검색
			String synonym = URLEncoder.encode(" synonym ", encode);

			// 카테고리 정렬 ex) ORDER BY $CATEGORYFIELD(field_name(domain), keyword)
			String categoryOrder = URLEncoder.encode(" $categoryfield(use_title(), 검색어넣기 ", encode);

			/**
			 * 17501 : 국내영화 17502 : 국외영화 17505 : 광고영화 17511 : 국내비디오 17512 :
			 * 국외비디오
			 */

			// medi_code 인코딩 목록
			String medi_mv = URLEncoder.encode("'17501','17502'", encode);
			String medi_ad = URLEncoder.encode("'17503','17504','17505','17506','17514'", encode);
			String medi_video = URLEncoder.encode("'17511','17512'", encode);
			String medi_perform = URLEncoder.encode("'17521','17522','17523','17524'", encode);

			/*
			 * //첫 로딩 시 데이터를 표시해주는 역할을 함. if(searchGubun == null ){ searchGubun
			 * = "All"; } else if(searchGubun.trim().isEmpty()){ searchGubun =
			 * "All"; }
			 */

			String orderValue = (String) request.getParameter("orderValue");
			if (orderValue == null || orderValue.equals("")) {
				orderValue = URLEncoder.encode(" order by $matchfield(use_title,use1) desc, $relevance desc ", encode);
			}
			if (orderValue.equals("orderRelevance")) {
				orderValue = URLEncoder.encode(" order by $matchfield(use_title,use1) desc, $relevance desc ", encode);
			}
			if (orderValue.equals("orderGa")) {
				orderValue = "%20order%20by%20use_title_sort%20asc%20";
			}
			if (orderValue.equals("orderDate")) {
				orderValue = "%20order%20by%20rcv_date%20desc%20";
			}

			//
			String groupByTest = URLEncoder.encode(" order by $relevance desc ", encode);

			/**************** 상세보기 시작 ********************************/
			// 20221216 상세보기에 관련된 것들 맨 위로 빼기 (기본값이 존재함)
			// 하지만 상세보기를 하지 않는다면 이 값이 null이라서 오류가뜸 ~~~~~~~~~~~~~~~~~~~~~~~~~~~

			// 상세보기를 클릭했는지 여부
			String detail_visibleChk = (String) paramMap.get("detail_visibleChk");

			// 연산자 가져오기
			String searchOperation = "";

			// 결과 수
			int detailresultLine = 3;
			try {
				if ((String) paramMap.get("detailSearch_resultLine") != null) {
					detailresultLine = Integer.valueOf((String) paramMap.get("detailSearch_resultLine"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (detailresultLine != 3 && (String) paramMap.get("detailSearch_resultLine") != null) {
					detailresultLine = detailresultLine;
				} else {
					detailresultLine = 3;
				}
			}

			try {
				if (detail_visibleChk.equals("Y")) {
					searchOperation = (String) paramMap.get("detailSearch_radio");
				} else {
					searchOperation = "natural";
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if ((String) paramMap.get("detailSearch_radio") == null) {
					searchOperation = "natural";
				}
			}

			if (searchOperation.equals("allword")) {
				searchOperation = allword;
			} else if (searchOperation.equals("anyword")) {
				searchOperation = anyword;
			} else if (searchOperation.equals("natural")) {
				searchOperation = natural;
			} else {
				searchOperation = natural;
			}

			// 신청회사 null 처리
			String detailAplc = "";
			try {
				if ((String) paramMap.get("detailSearch_aplc") != null) {
					detailAplc = (String) paramMap.get("detailSearch_aplc");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if ((String) paramMap.get("detailSearch_aplc") == null) {
					detailAplc = "";
				}
			}

			// 상세보기-일자 데이터 가져오기 및 null 처리 시작

			// 시작날짜
			String period_start = "";
			try {
				if ((String) paramMap.get("period_start") != null) {
					period_start = (String) paramMap.get("period_start");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if ((String) paramMap.get("period_start") == null) {
					period_start = "";
				}
			}

			// 종료날짜
			String period_end = "";
			try {
				if ((String) paramMap.get("period_end") != null) {
					period_end = (String) paramMap.get("period_end");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if ((String) paramMap.get("period_end") == null) {
					period_end = "";
				}
			}

			// 등급분류일자, 신청일자 , 접수일자 구분
			String detail_dateGubun = "";
			try {
				if ((String) paramMap.get("detailSearch_date") != null) {
					detail_dateGubun = (String) paramMap.get("detailSearch_date");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if ((String) paramMap.get("detailSearch_date") == null) {
					detail_dateGubun = "gradedate";
				}
			}

			if (!period_start.equals("")) {
				period_start = period_start.substring(0, 4) + period_start.substring(5, 7)
						+ period_start.substring(8, 10) + "000000";
			}
			if (!period_end.equals("")) {
				period_end = period_end.substring(0, 4) + period_end.substring(5, 7) + period_end.substring(8, 10)
						+ "000000";
			}

			// 듈 중에 하나를 선택안하고 상세검색을 하면 --> 전체날짜로 인식하여 데이터검색하기
			if ((period_start.equals("") || period_end.equals(""))
					&& !(period_start.equals("") && period_end.equals(""))) {
				period_start = "";
				period_end = "";

			}
			// 상세보기-일자 데이터 가져오기 및 null 처리 끝

			// 페이징 갯수 제한
			if (detailresultLine < 10) {
				paramMap.put("detailSearch_resultLine", "10");
			}
			;

			// System.out.println("페이징 컨트롤러 detailresultLine : " +
			// detailresultLine );

			/**************** 상세보기 끝 ********************************/

			/***** 오타교정, 추천검색어 등등 처리하는 곳 ********/

			String typo_correction = "http://192.168.11.242:7614/ksf/api/suggest?target=spell&term=";
			List<String> correctStrList = typocorrectionFunc(typo_correction, encodeSearchStr);
			paramMap.put("correctStrList", correctStrList);

			
			//20230310 연관검색어 관련 URL 
			Date now = Calendar.getInstance().getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String formatedNow = formatter.format(now);
			String suggestionUrl = "http://192.168.11.242:7581/ksm/kla/api/data.jsp?output=json&fromDate=2019-03-03&site=&category=&target=subword&format=recommend&duration=3&count=100&hasHeadWord=true&toDate=" + formatedNow  + "&relatedWordCount=5&keyword=";
			
			List<String> suggestionStrList = suggestionFunc(suggestionUrl, encodeSearchStr);
			paramMap.put("suggestionStrList", suggestionStrList);
			
			/* 기존 추천검색어를 쓰던 로직임. --> 연관검색어로 변경됨
				String suggestionUrl = "http://192.168.11.242:7614/ksf/api/suggest?target=related&term=";
				List<String> suggestionStrList = suggestionFunc(suggestionUrl, encodeSearchStr);
				paramMap.put("suggestionStrList", suggestionStrList);
			*/
			/***** 오타교정, 추천검색어 등등 처리하는 곳 ********/

			// 페이징처리111111111 / 넘겨받아야할 값 : cPage, total, limit

			int cPage = 0;
			try {
				cPage = Integer.valueOf((String) paramMap.get("cPage"));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if ((String) paramMap.get("cPage") == null) {
					cPage = 1;
				}
			}

			// 2022 12 23 이 현재 페이지를 전달할 건데, 문제는.. movie01()이 페이징처리 로직하고, 일반 검색
			// 로직하고 둘 다 사용을 한다는 점이다. (충돌)
			/*********** 일단 movie01()에서 써야할 데이터들 *********/
			paramMap.put("cPage", cPage);
			int offset = 0;
			int pagelength = Integer.valueOf((String) paramMap.get("detailSearch_resultLine")); // pagelength
																								// :
																								// 한
																								// 페이지당
																								// 보여줄
																								// [글
																								// 갯수]를
																								// 의마함.
			offset = (cPage - 1) * pagelength;
			paramMap.put("offset", offset);
			paramMap.put("pagelength", pagelength);
			/*********** 일단 movie01()에서 써야할 데이터들 *********/

			// cntPage : 1 2 3 4 5 6 .. 10 ( 페이징 [목록리스트] - 보여지는 부분)
			int cntPage = 10;
			int startPage = 1;
			int endPage = 10;
			boolean prev, next;
			int total = 0;

			// 페이징처리1111111111 // 페이징처리// 페이징처리// 페이징처리// 페이징처리// 페이징처리//
			// 페이징처리// 페이징처리// 페이징처리// 페이징처리// 페이징처리// 페이징처리

			/********************************
			 * 로그 처리하는 곳
			 ********************************/
			// 20220103

			// encodeSite는 해당 컨트롤러가 작동하는 시점의 페이지를 알면된다. 여기서는
			// 사이트명
			String encodeSite = URLEncoder.encode("SideMainPage", encode); // movie01()
																			// 등의
																			// 메소드에
																			// 넘겨주어야함.
			// 카테고리(Total, movie, video ...)
			String encodeCategory = ""; // movie01()메소드 등의 안에서 카테고리별로 movie01처럼
										// 삽입해줘야함
			/*
			 * if(sectionGubunList.contains("Total")){ encodeCategory =
			 * URLEncoder.encode( "Total" , encode); } else if { encodeCategory
			 * = URLEncoder.encode( "Total" , encode); } 일단 생략
			 */

			// 유저아이디
			String encodeUserid = URLEncoder.encode((String) paramMap.get("user_id"), encode);

			// 성별
			String encodeUsergender = "";

			// String 정렬방법? relevance , 가나다순, 날짜순
			// 검색어 던져주고, 이전검색어

			// sectionGubunList.contains 카테고리 체크 // Total은 Total로, 영화면 영화로..
			// 체크된것이 있는지.
			/*
			if(!encodeSearchStr.equals("NO_VALUE")){
				String customLogURL = "http://192.168.11.242:7577/search5?select=*&from=result_notice.result_notice&pagelength=3&offset=0&limit=3&custom="
						+ encodeSite + "@" + "|" + encodeUserid + "||||^" + encodeSearchStr + "$";
				logURLFunc(customLogURL);
			}
			*/
			if(!encodeSearchStr.equals("NO_VALUE")){
				if(paramMap.get("searchStrHistoryPrev") != null){
					String searchStrHistoryPrev = URLEncoder.encode((String)  paramMap.get("searchStrHistoryPrev") , encode);
					String customLogURL = "http://192.168.11.242:7577/search5?select=*&from=result_notice.result_notice&pagelength=3&offset=0&limit=3&custom="
							+ encodeSite + "@" + "|" + encodeUserid + "||||^" + encodeSearchStr + "$" + searchStrHistoryPrev;
					logURLFunc(customLogURL);
				} else {
					String customLogURL = "http://192.168.11.242:7577/search5?select=*&from=result_notice.result_notice&pagelength=3&offset=0&limit=3&custom="
							+ encodeSite + "@" + "|" + encodeUserid + "||||^" + encodeSearchStr + "$";
					logURLFunc(customLogURL);
				}
			}

			/********************************
			 * 로그 처리하는 곳
			 ********************************/

			/************************
			 * 결과 더보기를 클릭한 매체에 따라 코드 싫행 시작
			 **********************************/
			if (((String) paramMap.get("resultChk")).equals("movie01")) {
				movie01(walker, paramMap, request, response);
				total = (Integer) ((Map<String, Object>) walker.getValue("movie01")).get("total_count");
			}
			if (((String) paramMap.get("resultChk")).equals("video01")) {
				video01(walker, paramMap, request, response);
				total = (Integer) ((Map<String, Object>) walker.getValue("video01")).get("total_count");
			}
			if (((String) paramMap.get("resultChk")).equals("ad01")) {
				ad01(walker, paramMap, request, response);
				total = (Integer) ((Map<String, Object>) walker.getValue("ad01")).get("total_count");
			}
			if (((String) paramMap.get("resultChk")).equals("perform01")) {
				perform01(walker, paramMap, request, response);
				total = (Integer) ((Map<String, Object>) walker.getValue("perform01")).get("total_count");
			}
			if (((String) paramMap.get("resultChk")).equals("img01")) {
				img01(walker, paramMap, request, response);
				total = (Integer) ((Map<String, Object>) walker.getValue("img01")).get("total_count");
			}
			if (((String) paramMap.get("resultChk")).equals("file01")) {
				file01(walker, paramMap, request, response);
				total = (Integer) ((Map<String, Object>) walker.getValue("file01")).get("total_count");
			}
			if (((String) paramMap.get("resultChk")).equals("opin01")) {
				opin01(walker, paramMap, request, response);
				total = (Integer) ((Map<String, Object>) walker.getValue("opin01")).get("total_count");
			}
			if (((String) paramMap.get("resultChk")).equals("moni01")) {
				moni01(walker, paramMap, request, response);
				total = (Integer) ((Map<String, Object>) walker.getValue("moni01")).get("total_count");
			}

			/************************
			 * 결과 더보기를 클릭한 매체에 따라 코드 싫행 끝
			 **********************************/

			/************************
			 * 사이드 메뉴명을 직접 클릭하면 시작
			 **********************************/

			if (((String) paramMap.get("resultChk")).equals("movie01Side")) {

				// 형식상 돌리는 movie01()메소드 - movie01side에 관해서..
				movie01(walker, paramMap, request, response);

				// 위 함수에서 기본적으로 기존의 영화페이징을 처리한 다음~ (왜? 일단 데이터는 가져와야하기 떄문임..
				// movie01()메소드에서는 사이드메뉴 갯수 등을 가져온다.)
				sideMenuURLEncodingFunc(request, response, walker, paramMap, (String) paramMap.get("sidetotalUrl"));

				/****************************
				 * 2022 12 27상세검색을 끄든 뭘하든 마지막의 검색조건으로 사이드메뉴의 내용을 볼 수 있도록 하는 로직
				 * 시작
				 *******************************************************/
				/***
				 * 설명 : paramMap.get("sideMenuNm") : 상세보기([결과 더 보기]) 페이지에서 클릭한
				 * 사이드 메뉴명 사이드 메뉴명에는, 속성에 검색결과에 맞는 URL를 지니고 있다. 그래서 검색결과가 나온뒤에,
				 * 상세검색 조건을 바꾼다거나, 상세검색을 꺼버려도 검색을 다시 하지않는 이상 기존의 URL을 지니고 있다.
				 * 그래서 사이드메뉴명 클릭 시, 가령 기존의URL로 movie01()메소드를 먼저 위에서 돌려주고, 거기서 나온
				 * 사이드메뉴명과, 지금 클릭한 사이드 메뉴명이 일치한다면, 해당 사이드URL을 가지고 한번 더 URL 인코딩을
				 * 진행하게 된다 --> 여러 테스트 결과 지금 주석된 부분은 이젠 필요없는 로직인거같다.
				 * 
				 * String realSideUrl = ""; ListWrapper recentSearchResult =
				 * (ListWrapper)walker.getValue("movie01_sideInformation"); //
				 * 위에서 movie01()메소드를 통해 가져온, 사이드메뉴 정보들의 ListWrapper를 가져온다.
				 * for(int i = 0; i<recentSearchResult.getList().size(); i++){
				 * System.out.println(((Map
				 * <String,Object>)recentSearchResult.getList().get(i)).get(
				 * "menuName"));
				 * 
				 * 
				 * if(((Map
				 * <String,Object>)recentSearchResult.getList().get(i)).get(
				 * "menuName").equals((String)paramMap.get("sideMenuNm"))){
				 * realSideUrl = (String)((Map
				 * <String,Object>)recentSearchResult.getList().get(i)).get(
				 * "totalSideUrl"); System.out.println(realSideUrl); } }
				 * sideMenuURLEncodingFunc(request, response, walker, paramMap,
				 * realSideUrl );
				 */

				/****************************
				 * 2022 12 27상세검색을 끄든 뭘하든 마지막의 검색조건으로 사이드메뉴의 내용을 볼 수 있도록 하는 로직 끝
				 *******************************************************/

				// ■total : 페이징 할떄 중요한 요소 (전체 데이터 갯수)■
				total = (Integer) paramMap.get("sidePagingResultCnt");
				paramMap.put("sideMenuNm", (String) paramMap.get("sideMenuNm"));
				// ★★★★★★★사이드메뉴 URL은 진짜 기본적인 전달받은 url로 정보만 보여주면 되기때문에 . 기존
				// URLEncoding 메소드를 사용하면된다. 왜?-> 사이드메뉴 갯수를 세거나 하는건 위에
				// movie01()에서 처리해주기 떄문
			}
			if (((String) paramMap.get("resultChk")).equals("video01Side")) {
				video01(walker, paramMap, request, response);
				sideMenuURLEncodingFunc(request, response, walker, paramMap, (String) paramMap.get("sidetotalUrl"));
				/****************************
				 * 상세검색을 끄든 뭘하든 마지막의 검색조건으로 사이드메뉴의 내용을 볼 수 있도록 하는 로직 시작
				 *******************************************************/
				String realSideUrl = "";
				ListWrapper recentSearchResult = (ListWrapper) walker.getValue("video01_sideInformation");
				for (int i = 0; i < recentSearchResult.getList().size(); i++) {

					if (((Map<String, Object>) recentSearchResult.getList().get(i)).get("menuName")
							.equals((String) paramMap.get("sideMenuNm"))) {
						realSideUrl = (String) ((Map<String, Object>) recentSearchResult.getList().get(i))
								.get("totalSideUrl");
					}
				}
				sideMenuURLEncodingFunc(request, response, walker, paramMap, realSideUrl);
				/****************************
				 * 상세검색을 끄든 뭘하든 마지막의 검색조건으로 사이드메뉴의 내용을 볼 수 있도록 하는 로직 끝
				 *******************************************************/
				total = (Integer) paramMap.get("sidePagingResultCnt");
				paramMap.put("sideMenuNm", (String) paramMap.get("sideMenuNm"));
			}
			if (((String) paramMap.get("resultChk")).equals("ad01Side")) {
				ad01(walker, paramMap, request, response);
				sideMenuURLEncodingFunc(request, response, walker, paramMap, (String) paramMap.get("sidetotalUrl"));
				/****************************
				 * 상세검색을 끄든 뭘하든 마지막의 검색조건으로 사이드메뉴의 내용을 볼 수 있도록 하는 로직 시작
				 *******************************************************/
				String realSideUrl = "";
				ListWrapper recentSearchResult = (ListWrapper) walker.getValue("ad01_sideInformation");
				for (int i = 0; i < recentSearchResult.getList().size(); i++) {

					if (((Map<String, Object>) recentSearchResult.getList().get(i)).get("menuName")
							.equals((String) paramMap.get("sideMenuNm"))) {
						realSideUrl = (String) ((Map<String, Object>) recentSearchResult.getList().get(i))
								.get("totalSideUrl");
					}
				}
				sideMenuURLEncodingFunc(request, response, walker, paramMap, realSideUrl);
				/****************************
				 * 상세검색을 끄든 뭘하든 마지막의 검색조건으로 사이드메뉴의 내용을 볼 수 있도록 하는 로직 끝
				 *******************************************************/
				total = (Integer) paramMap.get("sidePagingResultCnt");
				paramMap.put("sideMenuNm", (String) paramMap.get("sideMenuNm"));
			}
			if (((String) paramMap.get("resultChk")).equals("perform01Side")) {
				perform01(walker, paramMap, request, response);
				sideMenuURLEncodingFunc(request, response, walker, paramMap, (String) paramMap.get("sidetotalUrl"));
				/****************************
				 * 상세검색을 끄든 뭘하든 마지막의 검색조건으로 사이드메뉴의 내용을 볼 수 있도록 하는 로직 시작
				 *******************************************************/
				String realSideUrl = "";
				ListWrapper recentSearchResult = (ListWrapper) walker.getValue("perform01_sideInformation");
				for (int i = 0; i < recentSearchResult.getList().size(); i++) {

					if (((Map<String, Object>) recentSearchResult.getList().get(i)).get("menuName")
							.equals((String) paramMap.get("sideMenuNm"))) {
						realSideUrl = (String) ((Map<String, Object>) recentSearchResult.getList().get(i))
								.get("totalSideUrl");
					}
				}
				sideMenuURLEncodingFunc(request, response, walker, paramMap, realSideUrl);
				/****************************
				 * 상세검색을 끄든 뭘하든 마지막의 검색조건으로 사이드메뉴의 내용을 볼 수 있도록 하는 로직 끝
				 *******************************************************/
				total = (Integer) paramMap.get("sidePagingResultCnt");
				paramMap.put("sideMenuNm", (String) paramMap.get("sideMenuNm"));
			}
			if (((String) paramMap.get("resultChk")).equals("opin01Side")) {
				opin01(walker, paramMap, request, response);
				sideMenuURLEncodingFunc(request, response, walker, paramMap, (String) paramMap.get("sidetotalUrl"));
				/****************************
				 * 상세검색을 끄든 뭘하든 마지막의 검색조건으로 사이드메뉴의 내용을 볼 수 있도록 하는 로직 시작
				 *******************************************************/
				String realSideUrl = "";
				ListWrapper recentSearchResult = (ListWrapper) walker.getValue("opin01_sideInformation");
				for (int i = 0; i < recentSearchResult.getList().size(); i++) {

					if (((Map<String, Object>) recentSearchResult.getList().get(i)).get("menuName")
							.equals((String) paramMap.get("sideMenuNm"))) {
						realSideUrl = (String) ((Map<String, Object>) recentSearchResult.getList().get(i))
								.get("totalSideUrl");
					}
				}
				sideMenuURLEncodingFunc(request, response, walker, paramMap, realSideUrl);
				/****************************
				 * 상세검색을 끄든 뭘하든 마지막의 검색조건으로 사이드메뉴의 내용을 볼 수 있도록 하는 로직 끝
				 *******************************************************/
				total = (Integer) paramMap.get("sidePagingResultCnt");
				paramMap.put("sideMenuNm", (String) paramMap.get("sideMenuNm"));
			}
			if (((String) paramMap.get("resultChk")).equals("img01Side")) {
				img01(walker, paramMap, request, response);
				sideMenuURLEncodingFunc(request, response, walker, paramMap, (String) paramMap.get("sidetotalUrl"));
				/****************************
				 * 상세검색을 끄든 뭘하든 마지막의 검색조건으로 사이드메뉴의 내용을 볼 수 있도록 하는 로직 시작
				 *******************************************************/
				String realSideUrl = "";
				ListWrapper recentSearchResult = (ListWrapper) walker.getValue("img01_sideInformation");
				for (int i = 0; i < recentSearchResult.getList().size(); i++) {

					if (((Map<String, Object>) recentSearchResult.getList().get(i)).get("menuName")
							.equals((String) paramMap.get("sideMenuNm"))) {
						realSideUrl = (String) ((Map<String, Object>) recentSearchResult.getList().get(i))
								.get("totalSideUrl");
					}
				}
				sideMenuURLEncodingFunc(request, response, walker, paramMap, realSideUrl);
				/****************************
				 * 상세검색을 끄든 뭘하든 마지막의 검색조건으로 사이드메뉴의 내용을 볼 수 있도록 하는 로직 끝
				 *******************************************************/
				total = (Integer) paramMap.get("sidePagingResultCnt");
				paramMap.put("sideMenuNm", (String) paramMap.get("sideMenuNm"));
			}
			if (((String) paramMap.get("resultChk")).equals("file01Side")) {
				file01(walker, paramMap, request, response);
				sideMenuURLEncodingFunc(request, response, walker, paramMap, (String) paramMap.get("sidetotalUrl"));
				/****************************
				 * 상세검색을 끄든 뭘하든 마지막의 검색조건으로 사이드메뉴의 내용을 볼 수 있도록 하는 로직 시작
				 *******************************************************/
				String realSideUrl = "";
				ListWrapper recentSearchResult = (ListWrapper) walker.getValue("file01_sideInformation");
				for (int i = 0; i < recentSearchResult.getList().size(); i++) {

					if (((Map<String, Object>) recentSearchResult.getList().get(i)).get("menuName")
							.equals((String) paramMap.get("sideMenuNm"))) {
						realSideUrl = (String) ((Map<String, Object>) recentSearchResult.getList().get(i))
								.get("totalSideUrl");
					}
				}
				sideMenuURLEncodingFunc(request, response, walker, paramMap, realSideUrl);
				/****************************
				 * 상세검색을 끄든 뭘하든 마지막의 검색조건으로 사이드메뉴의 내용을 볼 수 있도록 하는 로직 끝
				 *******************************************************/
				total = (Integer) paramMap.get("sidePagingResultCnt");
				paramMap.put("sideMenuNm", (String) paramMap.get("sideMenuNm"));
			}
			if (((String) paramMap.get("resultChk")).equals("moni01Side")) {
				moni01(walker, paramMap, request, response);
				sideMenuURLEncodingFunc(request, response, walker, paramMap, (String) paramMap.get("sidetotalUrl"));
				/****************************
				 * 상세검색을 끄든 뭘하든 마지막의 검색조건으로 사이드메뉴의 내용을 볼 수 있도록 하는 로직 시작
				 *******************************************************/
				String realSideUrl = "";
				ListWrapper recentSearchResult = (ListWrapper) walker.getValue("moni01_sideInformation");
				for (int i = 0; i < recentSearchResult.getList().size(); i++) {

					if (((Map<String, Object>) recentSearchResult.getList().get(i)).get("menuName")
							.equals((String) paramMap.get("sideMenuNm"))) {
						realSideUrl = (String) ((Map<String, Object>) recentSearchResult.getList().get(i))
								.get("totalSideUrl");
					}
				}
				sideMenuURLEncodingFunc(request, response, walker, paramMap, realSideUrl);
				/****************************
				 * 상세검색을 끄든 뭘하든 마지막의 검색조건으로 사이드메뉴의 내용을 볼 수 있도록 하는 로직 끝
				 *******************************************************/
				total = (Integer) paramMap.get("sidePagingResultCnt");
				paramMap.put("sideMenuNm", (String) paramMap.get("sideMenuNm"));
			}

			/************************
			 * 사이드 메뉴명을 직접 클릭하면 끝
			 **********************************/


			/*******************
			 * 페이징 처리에 필요한 변수2222222222222222
			 ******************************/

			// 1 총 갯수 total을 받아서 총합페이지를 구한다.
			int totalPage = 1;
			if (total != 0) {
				totalPage = (int) Math.ceil((double) total / (double) pagelength);
				// ★★★ 유효성체크 1. 총 갯수가 10개([페이징 버튼 목록])로 딱 떨어지는 갯수면, 페이지 -1 해준다.
				// ★★★
				/*
				 * 2022 12 26 ?? 필요없는 로직임 if(total % pagelength == 0){
				 * totalPage--; }
				 */
			}

			// ★★★유효성체크2 해야함 ★★★ if(total < 0 ) 만약 총합이 0이하라면 어떻게 처리할 것인가?

			// 3 endPage 단, 페이지는 1......2.... 10 으로 가정한다
			endPage = ((int) Math.ceil((double) cPage / (double) cntPage)) * cntPage;

			// ★★★ 유효성체크3 총~ 끝 페이지가 , 기본값인 endPage(10페이지)보다 적은 갯수라면?
			if (totalPage < endPage) {
				endPage = totalPage;
			}
			// 4. startPage : endPage를 구했으니, 거기다가 cntPage(보여줄 페이지목록)을 빼고 +1을
			// 해주면됨... ex) endPage가 40페이지면, cntPage(목록 10개씩)을 뺴주고 + 1을 해준다. ->
			// 31페이지 .. 32.. 40

			startPage = endPage - cntPage + 1;
			// ★★★ 유효성체크4 : 이렇게 계산했는데 startPage가 1보다 작은 숫자가 나온다면?

			if (startPage < 1) {
				startPage = 1;
			}

			// 위 로직에서 totalPage가 37인데, endPage가 40이면 (endPage = totalPage)로 로직을
			// 구성함
			// startPage는 항상 endPage(10페이지단위) 에서 cntPage(10)을 빼고 +1 을 해줌으로써,계산을
			// 해왔는데, 만약 바로 윗줄의 주석에 의하여 endPage가 37페이지거나, 38페이지가 된다면 계산을 달리
			// 해줘야한다.
			// ★★(다만, 이 경우에도 endPage = totalPage지만, 10단위로 끝낼 경우는 제외해야함/ 예를들어
			// endPage = totalPage가 100페이지인데, 해당 아래의 로직을 실행하면 startPage는 101페이지가
			// 나오는 오류가 발생)
			if (endPage == totalPage) {
				if (endPage % 10 != 0) {
					startPage = (endPage / cntPage) * cntPage + 1;
				}
			}

			paramMap.put("totalPage", totalPage); // 총합 페이지 넘겨주기
			// paramMap.put("cPage", cPage); --> 현재 페이지는 movie01() 같은 메소드 내에서도
			// 쓰이는 변수값이다. offset을 계산할떄쓰임. 그래서 위에서 선언을 해주어야한다.
			paramMap.put("startPage", startPage);
			paramMap.put("endPage", endPage);

			// lasPages : <마지막 페이지로 이동> 버튼 클릭 시, 해당 글 목록의 1페이지 클릭 되도록 유도.
			int lastPages = (totalPage / cntPage) * cntPage + 1;
			if (totalPage % cntPage == 0) {
				lastPages = totalPage - cntPage + 1;
			}
			paramMap.put("lastPages", lastPages);

			/*******************
			 * 페이징 처리에 필요한 변수2222222222222222
			 ******************************/

			// 상세보기 검색 다양화를 위해서, 기존 Controller의 복붙형태. (페이징을 처리하는 페이지에서도 상세검색
			// 다양화를 위해서..)
			if ((sectionGubunList.contains("movie01") || sectionGubunList.contains("video01"))
					&& !(sectionGubunList.contains("ad01") || sectionGubunList.contains("perform01")
							|| sectionGubunList.contains("opin01") || sectionGubunList.contains("img01")
							|| sectionGubunList.contains("file01") || sectionGubunList.contains("moni01"))) {
				// System.out.println("페이징 컨트롤러 ) 영화,비디오");
				paramMap.put("detailGubun", "movie01_video01");
			} else if (sectionGubunList.contains("ad01") && !(sectionGubunList.contains("movie01")
					|| sectionGubunList.contains("video01") || sectionGubunList.contains("perform01")
					|| sectionGubunList.contains("opin01") || sectionGubunList.contains("img01")
					|| sectionGubunList.contains("file01") || sectionGubunList.contains("moni01"))) {
				// System.out.println("페이징 컨트롤러 ) 광고물");
				paramMap.put("detailGubun", "ad01");
			} else if (sectionGubunList.contains("perform01") && !(sectionGubunList.contains("movie01")
					|| sectionGubunList.contains("video01") || sectionGubunList.contains("ad01")
					|| sectionGubunList.contains("opin01") || sectionGubunList.contains("img01")
					|| sectionGubunList.contains("file01") || sectionGubunList.contains("moni01"))) {
				paramMap.put("detailGubun", "perform01");
			} else if (sectionGubunList.contains("opin01") && !(sectionGubunList.contains("movie01")
					|| sectionGubunList.contains("video01") || sectionGubunList.contains("ad01")
					|| sectionGubunList.contains("perform01") || sectionGubunList.contains("img01")
					|| sectionGubunList.contains("file01") || sectionGubunList.contains("moni01"))) {
				paramMap.put("detailGubun", "opin01");
			} else if (sectionGubunList.contains("img01") && !(sectionGubunList.contains("movie01")
					|| sectionGubunList.contains("video01") || sectionGubunList.contains("ad01")
					|| sectionGubunList.contains("perform01") || sectionGubunList.contains("opin01")
					|| sectionGubunList.contains("file01") || sectionGubunList.contains("moni01"))) {
				paramMap.put("detailGubun", "img01");
			} else if (sectionGubunList.contains("file01") && !(sectionGubunList.contains("movie01")
					|| sectionGubunList.contains("video01") || sectionGubunList.contains("ad01")
					|| sectionGubunList.contains("perform01") || sectionGubunList.contains("opin01")
					|| sectionGubunList.contains("img01") || sectionGubunList.contains("moni01"))) {
				paramMap.put("detailGubun", "file01");
			} else if (sectionGubunList.contains("moni01") && !(sectionGubunList.contains("movie01")
					|| sectionGubunList.contains("video01") || sectionGubunList.contains("ad01")
					|| sectionGubunList.contains("perform01") || sectionGubunList.contains("opin01")
					|| sectionGubunList.contains("img01") || sectionGubunList.contains("file01"))) {
				paramMap.put("detailGubun", "file01");
			} else {
				paramMap.put("detailGubun", "remove");
			}

			walker.setValue("paramMap", paramMap);
			walker.setValue("parameters", walker.makeParameterQuery());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "common/searchEngineResult2";
	}

	/*
	 * 단순 기본적인 URL을 입력받아 로그쌓는것을 위해서 만든 메소드
	 */
	public void logURLFunc(String logUrl) {

		//System.out.println("logUrlfunc 실행 : " + logUrl);
		
		
		InputStream is = null;
		BufferedReader br = null;
		try {
			URL url = new URL(logUrl);
			URLConnection conn = url.openConnection();
			is = conn.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			char[] buff = new char[512];
			int len = -1;
			String apidata = "";
			while ((len = br.read(buff)) != -1) {
				apidata += new String(buff, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception occur: SearchController.java) logURLFunc ------ { try~catch } ");
		} finally {
			try {
				is.close();
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception occur: SearchController.java) logURLFunc ------ finally { try~catch } ");
			}
		}
	}

	/*
	 * URL을 입력받아 사이드 메뉴명에 관한 데이터들을 추출하는 메소드
	 */
	public Integer totalURLCntFunc(String totalUrl) {

		InputStream is = null;
		BufferedReader br = null;
		Integer total_count = 0;

		try {
			URL url = new URL(totalUrl);
			URLConnection conn = url.openConnection();
			is = conn.getInputStream();
			// BufferedReader br = new BufferedReader(new
			// InputStreamReader(is));
			br = new BufferedReader(new InputStreamReader(is, "UTF-8")); // ,
																			// "KSC5601"

			String apidata = "";

			String output = null;
			StringBuffer sb = new StringBuffer();

			while ((output = br.readLine()) != null) {
				sb.append(output + "\r\n");
			}

			apidata = sb.toString();

			// System.out.println("1번 컨트롤러의 총합갯수만 세는 역할을 하는 [totalURLCntFunc]메소드
			// 이다. 데이터는 ?? -->(총합을 구하는 데이터는 쓸모가없지만 혹시몰라서 찍어둠) -----> : " +
			// apidata);
			// System.out.println("현재 이 메소드에 들어온 매체는? url 첫부분으로 판단하기. --> : " +
			// totalUrl);

			JSONParser parser = new JSONParser();
			JSONObject originJsonObject = (JSONObject) parser.parse(apidata);
			Map<String, Object> fieldnameResult = (Map<String, Object>) originJsonObject.get("result");
			List<Map<String, Map<String, String>>> fieldnameRows = (List<Map<String, Map<String, String>>>) fieldnameResult
					.get("rows");
			List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>();
			for (int i = 0; i < fieldnameRows.size(); i++) {
				resultMapList.add((Map<String, String>) fieldnameRows.get(i).get("fields"));
			}

			total_count = ((Long) fieldnameResult.get("total_count")).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception occur: SearchController.java) totalURLCntFunc ------ { try~catch } ");
		} finally {
			try {
				is.close();
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(
						"Exception occur: SearchController.java) totalURLCntFunc ------ finally { try~catch } ");
			}
		}

		return total_count;

	}

	/*
	 * 핵심메소드! -- URL을 입력받아 사이드 메뉴명에 관한 데이터들을 추출하는 메소드 컨트롤러1에서 사이드에 관한 모든 데이터를 이
	 * 인코딩해석하는 메소드를 지나간다. mediurlEncodingResult, urlEncodingResult 메소드도 이 메소드를
	 * 사용하고 있다.
	 */
	public Map<String, Object> URLEncodingFunc(String totalSideUrl) {

		String apidata = "";
		BufferedReader br = null;
		InputStream is = null;
		Map<String, Object> resultMap = null;
		try {
			URL url = new URL(totalSideUrl);
			URLConnection conn = url.openConnection();
			is = conn.getInputStream();
			br = new BufferedReader(new InputStreamReader(is, "UTF-8")); // ,
																			// "KSC5601"

			String output = null;
			StringBuffer sb = new StringBuffer();

			char[] buff = new char[3000];
			int len = -1;
			String apidata01 = "";
			while ((output = br.readLine()) != null) {
				sb.append(output + "\r\n");
			}
			apidata = sb.toString();

			JSONParser parser = new JSONParser();

			JSONObject originJsonObject = (JSONObject) parser.parse(apidata);
			Map<String, Object> fieldnameResult = (Map<String, Object>) originJsonObject.get("result");
			List<Map<String, Map<String, String>>> fieldnameRows = (List<Map<String, Map<String, String>>>) fieldnameResult
					.get("rows");

			List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>();
			for (int i = 0; i < fieldnameRows.size(); i++) {
				resultMapList.add((Map<String, String>) fieldnameRows.get(i).get("fields"));
			}

			// 페이징처리 시 나온 데이터의 총 갯수를 형변환 하는중.
			Integer total_side_page_count = ((Long) fieldnameResult.get("total_count")).intValue();

			// 페이징처리시 발생한 데이터 갯수는 한 화면에 보여지는 갯수와 동일해야한다.
			/*
			 * System.out.println(
			 * "URLEncodingFunc 함수안에서 나오는 메시지임. 사이드에 표시되는 갯수 total_side_page_count :"
			 * + total_side_page_count); System.out.println(
			 * "URLEncodingFunc 함수안에서 나오는 메시지임.  totalSideUrl : " + totalSideUrl
			 * );
			 */

			// 실질적으로 쓰이는 데이터임
			resultMap = new HashMap<String, Object>();
			resultMap.put("total_side_page_count", total_side_page_count);
			resultMap.put("totalSideUrl", totalSideUrl);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception occur: SearchController.java) URLEncodingFunc ------ { try~catch } ");
		} finally {
			try {
				is.close();
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(
						"Exception occur: SearchController.java) URLEncodingFunc ------ finally { try~catch }  ");
			}
		}

		return resultMap;
	}

	/*
	 * 사이드메뉴 URL을 입력받아 사이드 메뉴명에 관한 데이터들을 추출하는 메소드 (페이징되는 화면에 보여줄 데이터를 추출함)
	 */
	public void sideMenuURLEncodingFunc(HttpServletRequest request, HttpServletResponse response, Walker walker,
			Map<String, Object> paramMap, String totalSideUrl) throws Exception {

		String sidehighlight = "";
		if (((String) paramMap.get("sideMenuNm")).equals("원제명")) {
			sidehighlight = "ori_title";
		} else if (((String) paramMap.get("sideMenuNm")).equals("사용제명")) {
			sidehighlight = "use_title";
		} else if (((String) paramMap.get("sideMenuNm")).equals("신청회사")) {
			sidehighlight = "aplc_name";
		} else if (((String) paramMap.get("sideMenuNm")).equals("등급분류번호")) {
			sidehighlight = "rt_no";
		} else if (((String) paramMap.get("sideMenuNm")).equals("작품내용(줄거리)")) {
			sidehighlight = "work_cont";
		} else if (((String) paramMap.get("sideMenuNm")).equals("결정사유")) {
			sidehighlight = "deter_rsn";
		} else if (((String) paramMap.get("sideMenuNm")).equals("결정의견")) {
			sidehighlight = "deter_opin_name";
		}

		String orderValue = "";
		// file과 img는 가나다순 정렬때, ori_title을 사용할 수 가 없어서 따로 분류함. (20230117 추가 모니터링
		// 의견서도 마찬가지)
		if (((String) paramMap.get("resultChk")).equals("file01Side")
				|| ((String) paramMap.get("resultChk")).equals("img01Side")
				|| ((String) paramMap.get("resultChk")).equals("moni01Side")) {
			orderValue = (String) request.getParameter("orderValue");
			if (orderValue == null || orderValue.equals("")) {
				orderValue = URLEncoder.encode(" order by $relevance desc ", "UTF-8");
			}
			if (orderValue.equals("orderRelevance")) {
				orderValue = URLEncoder.encode(" order by $relevance desc ", "UTF-8");
			}
			if (orderValue.equals("orderGa")) {
				orderValue = "%20order%20by%20use_title_sort%20asc%20";
			}
			if (orderValue.equals("orderDate")) {
				orderValue = "%20order%20by%20rcv_date%20desc%20";
			}
		} else {
			orderValue = (String) request.getParameter("orderValue");
			if (orderValue == null || orderValue.equals("")) {
				orderValue = URLEncoder.encode(" order by $relevance desc ", "UTF-8");
			}
			if (orderValue.equals("orderRelevance")) {
				orderValue = URLEncoder.encode(" order by $relevance desc ", "UTF-8");
			}
			if (orderValue.equals("orderGa")) {
				orderValue = "%20order%20by%20use_title_sort%20asc%20%2c%20ori_title_sort%20asc%20";
			}
			if (orderValue.equals("orderDate")) {
				orderValue = "%20order%20by%20rcv_date%20desc%20";
			}
		}

		// 2022 12 27 사이드메뉴명에 정렬 적용하기.
		totalSideUrl = totalSideUrl + orderValue;

		String apidata = "";

		int cPage = 0;
		int offset = 0;
		int pagelength = 0;
		/**************************
		 * 20221223 페이징처리를 위한 새로운 로직 (영화만 반영했음)
		 *********************************************/
		if (((String) paramMap.get("isPaging")).equals("Y")) {
			cPage = (Integer) paramMap.get("cPage");
			offset = (Integer) paramMap.get("offset");
			pagelength = (Integer) paramMap.get("pagelength");
			offset = (cPage - 1) * pagelength;
		} else {
			cPage = 1;
			offset = 0;
			pagelength = 10;
			offset = (cPage - 1) * pagelength;
		}

		/*******************************************************************/
		// 결과 수
		int detailresultLine = 3;
		try {
			if ((String) paramMap.get("detailSearch_resultLine") != null) {
				detailresultLine = Integer.valueOf((String) paramMap.get("detailSearch_resultLine"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (detailresultLine != 3 && (String) paramMap.get("detailSearch_resultLine") != null) {
				detailresultLine = detailresultLine;
				
				//20230207 이미지 형식을 1줄에 5개에서 3개씩 보여줌에 따라서, [사이드 메뉴]명 클릭 시 ,10개를 기본적으로 보여주던 <이미지> 형식을 9개를 기본으로 보여준다. (1줄에 3개씩 보여주기 때문에)
				if(((String) paramMap.get("resultChk")).equals("img01Side") && detailresultLine == 10){
					detailresultLine = 9;
				}
				
				
				
			} else {
				detailresultLine = 3;
			}
		}

		URL url = new URL(totalSideUrl);
		URLConnection conn = url.openConnection();
		InputStream is = conn.getInputStream();
		/*
		 * BufferedReader br = new BufferedReader(new InputStreamReader(is));
		 * 
		 * char[] buff = new char[512]; int len = -1;
		 * 
		 * while( (len = br.read(buff)) != -1) { apidata += new String(buff, 0,
		 * len); }
		 */

		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8")); // ,
																					// "KSC5601"

		/*
		 * char[] buff = new char[512]; int len = -1;
		 * 
		 * String apidata = ""; while( (len = br.read(buff)) != -1) { apidata +=
		 * new String(buff, 0, len); }
		 */

		String output = null;
		StringBuffer sb = new StringBuffer();

		while ((output = br.readLine()) != null) {
			sb.append(output + "\r\n");
		}

		apidata = sb.toString();

		JSONParser parser = new JSONParser();
		JSONObject originJsonObject = (JSONObject) parser.parse(apidata);
		Map<String, Object> fieldnameResult = (Map<String, Object>) originJsonObject.get("result");
		List<Map<String, Map<String, String>>> fieldnameRows = (List<Map<String, Map<String, String>>>) fieldnameResult
				.get("rows");

		List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < fieldnameRows.size(); i++) {
			resultMapList.add((Map<String, String>) fieldnameRows.get(i).get("fields"));
		}

		// 20230119 주석 System.out.println("sideMenuURLEncodingFunc 실행 -
		// totalSideUrl : " + totalSideUrl);

		// 페이징처리 시 나온 데이터의 총 갯수를 형변환 하는중.
		Integer total_side_page_count = ((Long) fieldnameResult.get("total_count")).intValue();

		// 페이징처리시 발생한 데이터 갯수는 한 화면에 보여지는 갯수와 동일해야한다.
		// 20230119 주석System.out.println("sideMenuURLEncodingFunc 함수안에서 나오는
		// 메시지임. 사이드에 표시되는 갯수 total_side_page_count :" + total_side_page_count);
		// 20230119 주석System.out.println("URLEncodingFunc 함수안에서 나오는 메시지임.
		// totalSideUrl : " + totalSideUrl );

		// 지금 이 로직에서는 총합만 가져오면 됨.
		// paramMap.put("sidePagingResultMapList", resultMapList);
		paramMap.put("sidePagingResultCnt", total_side_page_count);

		/*******************************************
		 * 사이드메뉴 실질적 데이터 추출
		 ************************************************************************************/
		String totalSideUrlData = totalSideUrl + "&pagelength=" + detailresultLine + "&offset=" + offset + "&limit="
				+ detailresultLine + "&hilite-fields=" + "%7B%22" + sidehighlight
				+ "%22%3A%7B%22length%22%3A3000%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D";
		URL url2 = new URL(totalSideUrlData);
		URLConnection conn2 = url2.openConnection();
		InputStream is2 = conn2.getInputStream();
		BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));

		char[] buff2 = new char[512];
		int len2 = -1;

		String apidata01 = "";
		while ((len2 = br2.read(buff2)) != -1) {
			apidata01 += new String(buff2, 0, len2);
		}
		JSONParser parser2 = new JSONParser();
		JSONObject originJsonObject2 = (JSONObject) parser2.parse(apidata01);
		Map<String, Object> fieldnameResult2 = (Map<String, Object>) originJsonObject2.get("result");
		List<Map<String, Map<String, String>>> fieldnameRows2 = (List<Map<String, Map<String, String>>>) fieldnameResult2
				.get("rows");

		List<Map<String, String>> resultMapList2 = new ArrayList<Map<String, String>>();
		for (int i = 0; i < fieldnameRows2.size(); i++) {
			resultMapList2.add((Map<String, String>) fieldnameRows2.get(i).get("fields"));
		}

		// 20230119 주석 System.out.println("sideMenuURLEncodingFunc 실질적 데이터 실행 -
		// totalSideUrlData : " + totalSideUrlData);

		// 지금 이 로직에서는 실질적 데이터만 가져오면 됨.
		paramMap.put("sidePagingResultMapList", resultMapList2);
		/*******************************************
		 * 사이드메뉴 실질적 데이터 추출
		 ************************************************************************************/

		is.close();
		is2.close();
		br2.close();
		br.close();
	}

	// 페이징 totalUrl을 처리해주는 메소드 정의
	// 실질적으로 보여지는 데이터는 다 이 로직에 있음 : 핵심로직
	public List<Object> pagingURLEncodingFunc(String paging_totalUrl) {

		InputStream is01 = null;
		BufferedReader br01 = null;
		List<Object> dataList = null;
		try {
			// 20230119 주석 System.out.println("[pagingURLEncodingFunc] 시작 (실제로
			// 1번 컨트롤러 페이지의 보여지는 부분은 이 컨트롤러에서 책임진다)| 메소드가 실행되는 순간 현재 매체 --> url
			// 앞 부분 분석하기(result_~) " + paging_totalUrl);
			URL url01 = new URL(paging_totalUrl);
			URLConnection conn01 = url01.openConnection();
			is01 = conn01.getInputStream();
			/*
			 * BufferedReader br01 = new BufferedReader(new
			 * InputStreamReader(is01));
			 * 
			 * char[] buff01 = new char[512]; int len01 = -1;
			 * 
			 * String apidata01 = ""; while( (len01 = br01.read(buff01)) != -1)
			 * { apidata01 += new String(buff01, 0, len01); }
			 */
			br01 = new BufferedReader(new InputStreamReader(is01, "UTF-8")); // ,
																				// "KSC5601"

			String apidata01 = "";

			/*
			 * char[] buff = new char[512]; int len = -1;
			 * 
			 * String apidata = ""; while( (len = br.read(buff)) != -1) {
			 * apidata += new String(buff, 0, len); }
			 */

			String output = null;
			StringBuffer sb = new StringBuffer();

			while ((output = br01.readLine()) != null) {
				sb.append(output + "\r\n");
			}

			apidata01 = sb.toString();

			JSONParser parser01 = new JSONParser();
			JSONObject originJsonObject01 = (JSONObject) parser01.parse(apidata01);
			Map<String, Object> fieldnameResult01 = (Map<String, Object>) originJsonObject01.get("result");
			List<Map<String, Map<String, String>>> fieldnameRows01 = (List<Map<String, Map<String, String>>>) fieldnameResult01
					.get("rows");

			List<Map<String, String>> resultMapList01 = new ArrayList<Map<String, String>>();
			for (int i = 0; i < fieldnameRows01.size(); i++) {
				//20230227 개행문자 처리해보기
				if(((Map<String, String>) fieldnameRows01.get(i).get("fields")).get("rcv_date").equals("\n")){
					((Map<String, String>) fieldnameRows01.get(i).get("fields")).put("rcv_date", "");
					
				}
				
				if(((Map<String, String>) fieldnameRows01.get(i).get("fields")).get("rt_date").equals("\n")){
					((Map<String, String>) fieldnameRows01.get(i).get("fields")).put("rt_date", "");
				}
				
				
				
					resultMapList01.add((Map<String, String>) fieldnameRows01.get(i).get("fields"));
				
			}
			
			
			// 페이지에 보여줄 데이터 갯수를 형변환
			Integer total_count_page = ((Long) fieldnameResult01.get("total_count")).intValue();
			// 한 화면에 보여줄 데이터 갯수이므로, 한 화면에 3개면 3개. 5개면 5개를 들고오는게 맞음.

			// 산출물 total_count_page : 페이징 카운트를 세줌
			// resultMapList01 : 실질적으로 보여지는 데이터임

			dataList = new ArrayList<Object>();
			dataList.add(total_count_page);
			dataList.add(resultMapList01);
			// 20230119 주석 System.out.println("[pagingURLEncodingFunc] 끝");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception occur: SearchController.java) typocorrectionFunc ------ try~catch ");
		} finally {
			try {
				is01.close();
				br01.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(
						"Exception occur: SearchController.java) typocorrectionFunc ------ finally { try~catch } ");
			}
		}

		return dataList;

	}

	// 메소드1 정의 medi_code가 없는 것들
	// 사용법 : 매체코드가 없다.
	// 매체코드가 있는 것과 달리, Url을 항상 바꿔줘야함. (매체코드 대신 Url로 구분을 하기 떄문)
	public Map<String, Object> urlEncodingResult(Walker walker, Map<String, Object> paramMap,
			HttpServletRequest request, HttpServletResponse response, String result_Url, String orderValue,
			String searchCol, String str) throws Exception {
		// 이것만 던져주면?
		String total_url = result_Url + searchCol;
		String encodeAnd = URLEncoder.encode(" and ", "UTF-8");

		/***********************************************************************************************************************/
		// return 해줄 Map생성.
		Map<String, Object> total_url_information = new HashMap<String, Object>();
		total_url_information.put("menuName", str);

		// 이전 쿼리 저장하기
		String historySideSearchQuery = "";
		historySideSearchQuery = result_Url + "(" + searchCol + ")";

		// 만약 결과내 재검색이 체크되어 있다면??
		if (paramMap.get("result_re_search_chk").equals("Y")) {
			try {
				if (result_Url
						.equals("http://192.168.11.242:7577/search5?select=*&from=result_opin.result_opin&where=")) {
					String originalHistoryQuery = (String) paramMap.get("opin01_sideInformation_forResearch");
					String firstparsing = originalHistoryQuery.replaceAll("\\[|\\]", "");
					String new_totalUrl = "";
					String[] secondparsing = firstparsing.split("},");
					for (String secondparsingStr : secondparsing) {
						String thirdparsing = secondparsingStr.replaceAll("\\{|\\}", "");
						String[] thirdparsingArrays = thirdparsing.split("\\, ");
						List<String> finalparsingHistoryQuery = Arrays.asList(thirdparsingArrays);
						// 이전쿼리와 메뉴명의 인덱스번호를 찾는 로직 시작!
						int i = 0;
						int historyIndexNum = 0;
						int menuNameIndexNum = 0;
						for (String preciseStr : finalparsingHistoryQuery) {
							// System.out.println("이것이 바로 속성명이 뭐있는지 확인 가능 한 반복문
							// : " + preciseStr.substring(0,
							// preciseStr.indexOf("=")));
							if ((preciseStr.substring(0, preciseStr.indexOf("=")).trim())
									.equals("historySideSearchQuery")) {
								historyIndexNum = i;
							}
							if ((preciseStr.substring(0, preciseStr.indexOf("=")).trim()).equals("menuName")) {
								menuNameIndexNum = i;
							}
							i++;
						}
						// 이전쿼리와 메뉴명의 인덱스번호를 찾는 로직 끝!
						if (finalparsingHistoryQuery.get(menuNameIndexNum)
								.substring(finalparsingHistoryQuery.get(menuNameIndexNum).indexOf("=") + 1)
								.equals(str)) {
							total_url = finalparsingHistoryQuery.get(historyIndexNum)
									.substring(finalparsingHistoryQuery.get(historyIndexNum).indexOf("=") + 1)
									+ encodeAnd + "(" + searchCol + ")";
							historySideSearchQuery = finalparsingHistoryQuery.get(historyIndexNum)
									.substring(finalparsingHistoryQuery.get(historyIndexNum).indexOf("=") + 1)
									+ encodeAnd + "(" + searchCol + ")";
						}
					}
				} else if (result_Url
						.equals("http://192.168.11.242:7577/search5?select=*&from=result_file.result_file&where=")) {
					String originalHistoryQuery = (String) paramMap.get("file01_sideInformation_forResearch");
					String firstparsing = originalHistoryQuery.replaceAll("\\[|\\]", "");
					String new_totalUrl = "";
					String[] secondparsing = firstparsing.split("},");
					for (String secondparsingStr : secondparsing) {
						String thirdparsing = secondparsingStr.replaceAll("\\{|\\}", "");
						String[] thirdparsingArrays = thirdparsing.split("\\, ");
						List<String> finalparsingHistoryQuery = Arrays.asList(thirdparsingArrays);
						// 이전쿼리와 메뉴명의 인덱스번호를 찾는 로직 시작!
						int i = 0;
						int historyIndexNum = 0;
						int menuNameIndexNum = 0;
						for (String preciseStr : finalparsingHistoryQuery) {
							// System.out.println("이것이 바로 속성명이 뭐있는지 확인 가능 한 반복문
							// : " + preciseStr.substring(0,
							// preciseStr.indexOf("=")));
							if ((preciseStr.substring(0, preciseStr.indexOf("=")).trim())
									.equals("historySideSearchQuery")) {
								historyIndexNum = i;
							}
							if ((preciseStr.substring(0, preciseStr.indexOf("=")).trim()).equals("menuName")) {
								menuNameIndexNum = i;
							}
							i++;
						}
						// 이전쿼리와 메뉴명의 인덱스번호를 찾는 로직 끝!
						if (finalparsingHistoryQuery.get(menuNameIndexNum)
								.substring(finalparsingHistoryQuery.get(menuNameIndexNum).indexOf("=") + 1)
								.equals(str)) {
							total_url = finalparsingHistoryQuery.get(historyIndexNum)
									.substring(finalparsingHistoryQuery.get(historyIndexNum).indexOf("=") + 1)
									+ encodeAnd + "(" + searchCol + ")";
							historySideSearchQuery = finalparsingHistoryQuery.get(historyIndexNum)
									.substring(finalparsingHistoryQuery.get(historyIndexNum).indexOf("=") + 1)
									+ encodeAnd + "(" + searchCol + ")";
						}
					}
				} else if (result_Url
						.equals("http://192.168.11.242:7577/search5?select=*&from=result_image.result_image&where=")) {
					String originalHistoryQuery = (String) paramMap.get("img01_sideInformation_forResearch");
					String firstparsing = originalHistoryQuery.replaceAll("\\[|\\]", "");
					String new_totalUrl = "";
					String[] secondparsing = firstparsing.split("},");
					for (String secondparsingStr : secondparsing) {
						String thirdparsing = secondparsingStr.replaceAll("\\{|\\}", "");
						String[] thirdparsingArrays = thirdparsing.split("\\, ");
						List<String> finalparsingHistoryQuery = Arrays.asList(thirdparsingArrays);
						// 이전쿼리와 메뉴명의 인덱스번호를 찾는 로직 시작!
						int i = 0;
						int historyIndexNum = 0;
						int menuNameIndexNum = 0;
						for (String preciseStr : finalparsingHistoryQuery) {
							// System.out.println("이것이 바로 속성명이 뭐있는지 확인 가능 한 반복문
							// : " + preciseStr.substring(0,
							// preciseStr.indexOf("=")));
							if ((preciseStr.substring(0, preciseStr.indexOf("=")).trim())
									.equals("historySideSearchQuery")) {
								historyIndexNum = i;
							}
							if ((preciseStr.substring(0, preciseStr.indexOf("=")).trim()).equals("menuName")) {
								menuNameIndexNum = i;
							}
							i++;
						}
						// 이전쿼리와 메뉴명의 인덱스번호를 찾는 로직 끝!
						if (finalparsingHistoryQuery.get(menuNameIndexNum)
								.substring(finalparsingHistoryQuery.get(menuNameIndexNum).indexOf("=") + 1)
								.equals(str)) {
							total_url = finalparsingHistoryQuery.get(historyIndexNum)
									.substring(finalparsingHistoryQuery.get(historyIndexNum).indexOf("=") + 1)
									+ encodeAnd + "(" + searchCol + ")";
							historySideSearchQuery = finalparsingHistoryQuery.get(historyIndexNum)
									.substring(finalparsingHistoryQuery.get(historyIndexNum).indexOf("=") + 1)
									+ encodeAnd + "(" + searchCol + ")";
						}
					}
				} else if (result_Url
						.equals("http://192.168.11.242:7577/search5?select=*&from=result_moni.result_moni&where=")) {
					String originalHistoryQuery = (String) paramMap.get("moni01_sideInformation_forResearch");
					String firstparsing = originalHistoryQuery.replaceAll("\\[|\\]", "");
					String new_totalUrl = "";
					String[] secondparsing = firstparsing.split("},");
					for (String secondparsingStr : secondparsing) {
						String thirdparsing = secondparsingStr.replaceAll("\\{|\\}", "");
						String[] thirdparsingArrays = thirdparsing.split("\\, ");
						List<String> finalparsingHistoryQuery = Arrays.asList(thirdparsingArrays);
						// 이전쿼리와 메뉴명의 인덱스번호를 찾는 로직 시작!
						int i = 0;
						int historyIndexNum = 0;
						int menuNameIndexNum = 0;
						for (String preciseStr : finalparsingHistoryQuery) {
							// System.out.println("이것이 바로 속성명이 뭐있는지 확인 가능 한 반복문
							// : " + preciseStr.substring(0,
							// preciseStr.indexOf("=")));
							if ((preciseStr.substring(0, preciseStr.indexOf("=")).trim())
									.equals("historySideSearchQuery")) {
								historyIndexNum = i;
							}
							if ((preciseStr.substring(0, preciseStr.indexOf("=")).trim()).equals("menuName")) {
								menuNameIndexNum = i;
							}
							i++;
						}
						// 이전쿼리와 메뉴명의 인덱스번호를 찾는 로직 끝!
						if (finalparsingHistoryQuery.get(menuNameIndexNum)
								.substring(finalparsingHistoryQuery.get(menuNameIndexNum).indexOf("=") + 1)
								.equals(str)) {
							total_url = finalparsingHistoryQuery.get(historyIndexNum)
									.substring(finalparsingHistoryQuery.get(historyIndexNum).indexOf("=") + 1)
									+ encodeAnd + "(" + searchCol + ")";
							historySideSearchQuery = finalparsingHistoryQuery.get(historyIndexNum)
									.substring(finalparsingHistoryQuery.get(historyIndexNum).indexOf("=") + 1)
									+ encodeAnd + "(" + searchCol + ")";
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		Map<String, Object> urlEncodingFunc_result = URLEncodingFunc(total_url);
		total_url_information.put("total_side_page_count", urlEncodingFunc_result.get("total_side_page_count"));
		total_url_information.put("totalSideUrl", urlEncodingFunc_result.get("totalSideUrl"));
		total_url_information.put("historySideSearchQuery", historySideSearchQuery);
		// 20230119 주석System.out.println("20230118 urlEncodingResult(1번 컨트롤러에서
		// 사이드 갯수 정보 구하는 메소드)에서 결과내 재검색을 체크하였는지 확인(N/Y) : " +
		// paramMap.get("result_re_search_chk"));
		// 20230119 주석System.out.println(result_Url);
		// 20230119 주석System.out.println("20230116 urlEncodingResult " + str + "
		// 그리고 " + result_Url + " 의 이전쿼리 내역 " + historySideSearchQuery);
		/***********************************************************************************************************************/

		return total_url_information;
	}

	// 메소드2 정의 medi_code가 존재한 것들 (영화,비디오 등등)
	// --> movie01() 이나, video01() 메소드 실행 시, 큰틀에서 사이드 메뉴의 갯수들을 가져올떄 사용하는 메소드임.
	public Map<String, Object> mediurlEncodingResult(Walker walker, Map<String, Object> paramMap,
			HttpServletRequest request, HttpServletResponse response, String result_noticeUrl, String encodeAnd,
			String orderValue, String medi_code, String searchCol, String str) throws Exception {

		String total_url = result_noticeUrl + medi_code + encodeAnd + searchCol;

		
		
		// total_url(일반검색/결과내 재검색)을 바탕으로 total_url_information 작성 시작!
		Map<String, Object> total_url_information = new HashMap<String, Object>();
		total_url_information.put("menuName", str);
		// medi_code있는것들만 취급한다 (영화,비디오,광고,공연추천)
		// 이전쿼리로 사용할 URL을 추출하고, JSP에 보내서 저장해준다. 시작
		String historySideSearchQuery = "";
		historySideSearchQuery = result_noticeUrl + "(" + medi_code + encodeAnd + searchCol + ")";

		// 만약 결과내재검색을 통해 한번 더 왔다면? , 이전쿼리는 = 현재쿼리에다가 괄호를 덧붙여주는 형태가 아닌, 이전쿼리는 =
		// (이전쿼리) + encodeAnd + (현재쿼리) 라는 느낌으로 이전쿼리를 정의해줘야할 것이다.
		// --> 20230116 이 로직은 아래에
		// paramMap.get("result_re_search_chk").equals("Y") 조건 안에 위치한다.
		// 이전쿼리로 사용할 URL을 추출하고, JSP에 보내서 저장해준다. 끝

		/******************************************************************
		 * 결과내 재검색이 체크 된 경우
		 ****************************************************************************************************/
		if (paramMap.get("result_re_search_chk").equals("Y")) {
			try {
				// if()(medi_code.equals("medi_code+in%7B%2717501%27%2C%2717502%27%7D"))
				// 이건 perform01임
				// System.out.println(medi_code.equals("medi_code+in%7B%2717521%27%2C%2717522%27%2C%2717523%27%2C%2717524%27%7D"));
				// 만약 매체코드가 영화라면
				if (medi_code.equals("medi_code+in%7B%2717501%27%2C%2717502%27%7D")) {
					// 이전쿼리를 전부 다~ 가져옴 , 일단 movie01의 사이드명 전부를 가져와보자.
					String originalHistoryQuery = (String) paramMap.get("movie01_sideInformation_forResearch");
					String firstparsing = originalHistoryQuery.replaceAll("\\[|\\]", "");
					String new_totalUrl = "";
					String[] secondparsing = firstparsing.split("},");
					for (String secondparsingStr : secondparsing) {
						// System.out.println(secondparsingStr);
						String thirdparsing = secondparsingStr.replaceAll("\\{|\\}", "");
						// System.out.println(thirdparsing);
						String[] thirdparsingArrays = thirdparsing.split("\\, ");
						List<String> finalparsingHistoryQuery = Arrays.asList(thirdparsingArrays);

						// 이전쿼리와 메뉴명의 인덱스번호를 찾는 로직 시작!
						int i = 0;
						int historyIndexNum = 0;
						int menuNameIndexNum = 0;
						for (String preciseStr : finalparsingHistoryQuery) {
							// System.out.println("이것이 바로 속성명이 뭐있는지 확인 가능 한 반복문
							// : " + preciseStr.substring(0,
							// preciseStr.indexOf("=")));
							if ((preciseStr.substring(0, preciseStr.indexOf("=")).trim())
									.equals("historySideSearchQuery")) {
								historyIndexNum = i;
							}
							if ((preciseStr.substring(0, preciseStr.indexOf("=")).trim()).equals("menuName")) {
								menuNameIndexNum = i;
							}
							i++;
						}
						// 이전쿼리와 메뉴명의 인덱스번호를 찾는 로직 끝!

						/*
						 * System.out.println(
						 * "ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd"
						 * ); System.out.println(historyIndexNum + "  " +
						 * menuNameIndexNum);
						 * System.out.println(finalparsingHistoryQuery.get(
						 * historyIndexNum).substring(finalparsingHistoryQuery.
						 * get(historyIndexNum).indexOf("=") + 1 )); //이전쿼리
						 * System.out.println(str); //현재 돌고 있는 메뉴명
						 * System.out.println(finalparsingHistoryQuery.get(
						 * menuNameIndexNum).substring(finalparsingHistoryQuery.
						 * get(menuNameIndexNum).indexOf("=") + 1 )); // 이전쿼리를
						 * 담고있는 메뉴명
						 */
						// tip: 이상한 검색어를 검색해서, 카운트가 없더라도, 사이드 메뉴명 로직을 계속 돌고있다.
						// 즉, null pointer 걱정 X
						// 만약 JSP에서 가져온 이전쿼리 뭉터기와 현재 이 메소드로 접속한 어떠한 매체의 사이드메뉴명이
						// 같다면?
						if (finalparsingHistoryQuery.get(menuNameIndexNum)
								.substring(finalparsingHistoryQuery.get(menuNameIndexNum).indexOf("=") + 1)
								.equals(str)) {
							// total_url은 " (이전쿼리) + and + 현재쿼리 "로 값을 덮어버린다.
							total_url = finalparsingHistoryQuery.get(historyIndexNum)
									.substring(finalparsingHistoryQuery.get(historyIndexNum).indexOf("=") + 1)
									+ encodeAnd + "(" + medi_code + encodeAnd + searchCol + ")";
							// ★이 조건문에서 이전쿼리를 그대로 또 저장해서 JSP로 넘겨줄 준비를 하면됨
							historySideSearchQuery = finalparsingHistoryQuery.get(historyIndexNum)
									.substring(finalparsingHistoryQuery.get(historyIndexNum).indexOf("=") + 1)
									+ encodeAnd + "(" + medi_code + encodeAnd + searchCol + ")";
						}

					}
				} else if (medi_code.equals("medi_code+in%7B%2717511%27%2C%2717512%27%7D")) {
					// 비디오매체
					String originalHistoryQuery = (String) paramMap.get("video01_sideInformation_forResearch");
					String firstparsing = originalHistoryQuery.replaceAll("\\[|\\]", "");
					String new_totalUrl = "";
					String[] secondparsing = firstparsing.split("},");
					for (String secondparsingStr : secondparsing) {
						String thirdparsing = secondparsingStr.replaceAll("\\{|\\}", "");
						String[] thirdparsingArrays = thirdparsing.split("\\, ");
						List<String> finalparsingHistoryQuery = Arrays.asList(thirdparsingArrays);
						// 이전쿼리와 메뉴명의 인덱스번호를 찾는 로직 시작!
						int i = 0;
						int historyIndexNum = 0;
						int menuNameIndexNum = 0;
						for (String preciseStr : finalparsingHistoryQuery) {
							// System.out.println("이것이 바로 속성명이 뭐있는지 확인 가능 한 반복문
							// : " + preciseStr.substring(0,
							// preciseStr.indexOf("=")));
							if ((preciseStr.substring(0, preciseStr.indexOf("=")).trim())
									.equals("historySideSearchQuery")) {
								historyIndexNum = i;
							}
							if ((preciseStr.substring(0, preciseStr.indexOf("=")).trim()).equals("menuName")) {
								menuNameIndexNum = i;
							}
							i++;
						}
						// 이전쿼리와 메뉴명의 인덱스번호를 찾는 로직 끝!
						if (finalparsingHistoryQuery.get(menuNameIndexNum)
								.substring(finalparsingHistoryQuery.get(menuNameIndexNum).indexOf("=") + 1)
								.equals(str)) {
							total_url = finalparsingHistoryQuery.get(historyIndexNum)
									.substring(finalparsingHistoryQuery.get(historyIndexNum).indexOf("=") + 1)
									+ encodeAnd + "(" + medi_code + encodeAnd + searchCol + ")";
							historySideSearchQuery = finalparsingHistoryQuery.get(historyIndexNum)
									.substring(finalparsingHistoryQuery.get(historyIndexNum).indexOf("=") + 1)
									+ encodeAnd + "(" + medi_code + encodeAnd + searchCol + ")";
						}
					}
				} else if (medi_code.equals(
						"medi_code+in%7B%2717503%27%2C%2717504%27%2C%2717505%27%2C%2717506%27%2C%2717514%27%7D")) {
					// 광고물매체
					String originalHistoryQuery = (String) paramMap.get("ad01_sideInformation_forResearch");
					String firstparsing = originalHistoryQuery.replaceAll("\\[|\\]", "");
					String new_totalUrl = "";
					String[] secondparsing = firstparsing.split("},");
					for (String secondparsingStr : secondparsing) {
						String thirdparsing = secondparsingStr.replaceAll("\\{|\\}", "");
						String[] thirdparsingArrays = thirdparsing.split("\\, ");
						List<String> finalparsingHistoryQuery = Arrays.asList(thirdparsingArrays);
						// 이전쿼리와 메뉴명의 인덱스번호를 찾는 로직 시작!
						int i = 0;
						int historyIndexNum = 0;
						int menuNameIndexNum = 0;
						for (String preciseStr : finalparsingHistoryQuery) {
							// System.out.println("이것이 바로 속성명이 뭐있는지 확인 가능 한 반복문
							// : " + preciseStr.substring(0,
							// preciseStr.indexOf("=")));
							if ((preciseStr.substring(0, preciseStr.indexOf("=")).trim())
									.equals("historySideSearchQuery")) {
								historyIndexNum = i;
							}
							if ((preciseStr.substring(0, preciseStr.indexOf("=")).trim()).equals("menuName")) {
								menuNameIndexNum = i;
							}
							i++;
						}
						// 이전쿼리와 메뉴명의 인덱스번호를 찾는 로직 끝!
						if (finalparsingHistoryQuery.get(menuNameIndexNum)
								.substring(finalparsingHistoryQuery.get(menuNameIndexNum).indexOf("=") + 1)
								.equals(str)) {
							total_url = finalparsingHistoryQuery.get(historyIndexNum)
									.substring(finalparsingHistoryQuery.get(historyIndexNum).indexOf("=") + 1)
									+ encodeAnd + "(" + medi_code + encodeAnd + searchCol + ")";
							historySideSearchQuery = finalparsingHistoryQuery.get(historyIndexNum)
									.substring(finalparsingHistoryQuery.get(historyIndexNum).indexOf("=") + 1)
									+ encodeAnd + "(" + medi_code + encodeAnd + searchCol + ")";
						}
					}
				} else if (medi_code
						.equals("medi_code+in%7B%2717521%27%2C%2717522%27%2C%2717523%27%2C%2717524%27%7D")) {
					// 공연추천매체
					String originalHistoryQuery = (String) paramMap.get("perform01_sideInformation_forResearch");
					String firstparsing = originalHistoryQuery.replaceAll("\\[|\\]", "");
					String new_totalUrl = "";
					String[] secondparsing = firstparsing.split("},");
					for (String secondparsingStr : secondparsing) {
						String thirdparsing = secondparsingStr.replaceAll("\\{|\\}", "");
						String[] thirdparsingArrays = thirdparsing.split("\\, ");
						List<String> finalparsingHistoryQuery = Arrays.asList(thirdparsingArrays);
						// 이전쿼리와 메뉴명의 인덱스번호를 찾는 로직 시작!
						int i = 0;
						int historyIndexNum = 0;
						int menuNameIndexNum = 0;
						for (String preciseStr : finalparsingHistoryQuery) {
							// System.out.println("이것이 바로 속성명이 뭐있는지 확인 가능 한 반복문
							// : " + preciseStr.substring(0,
							// preciseStr.indexOf("=")));
							if ((preciseStr.substring(0, preciseStr.indexOf("=")).trim())
									.equals("historySideSearchQuery")) {
								historyIndexNum = i;
							}
							if ((preciseStr.substring(0, preciseStr.indexOf("=")).trim()).equals("menuName")) {
								menuNameIndexNum = i;
							}
							i++;
						}
						// 이전쿼리와 메뉴명의 인덱스번호를 찾는 로직 끝!
						if (finalparsingHistoryQuery.get(menuNameIndexNum)
								.substring(finalparsingHistoryQuery.get(menuNameIndexNum).indexOf("=") + 1)
								.equals(str)) {
							total_url = finalparsingHistoryQuery.get(historyIndexNum)
									.substring(finalparsingHistoryQuery.get(historyIndexNum).indexOf("=") + 1)
									+ encodeAnd + "(" + medi_code + encodeAnd + searchCol + ")";
							historySideSearchQuery = finalparsingHistoryQuery.get(historyIndexNum)
									.substring(finalparsingHistoryQuery.get(historyIndexNum).indexOf("=") + 1)
									+ encodeAnd + "(" + medi_code + encodeAnd + searchCol + ")";
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // 결과내 재검색이 체크되어있는지 확인하는 if문 끝--> 결과내 재검색 버튼이 체크가 되어있으면 이 로직을 실행하고,
			// 아니면 그냥 넘어가면됨.

		/***********************************************************************
		 * 결과내 재검색이 체크 된 경우
		 ***********************************************************************************************/
		// System.out.println(total_url);
		Map<String, Object> urlEncodingFunc_result = URLEncodingFunc(total_url);

		total_url_information.put("total_side_page_count", urlEncodingFunc_result.get("total_side_page_count"));
		total_url_information.put("totalSideUrl", urlEncodingFunc_result.get("totalSideUrl"));
		total_url_information.put("historySideSearchQuery", historySideSearchQuery);
		// 20230119 주석System.out.println("20230118 urlEncodingResult(1번 컨트롤러에서
		// 사이드 갯수 정보 구하는 메소드)에서 결과내 재검색을 체크하였는지 확인(N/Y) : " +
		// paramMap.get("result_re_search_chk"));
		// 20230119 주석System.out.println(medi_code);
		// 20230119 주석System.out.println("20230116 mediurlEncodingResult " + str
		// + "의 이전쿼리 내역 " + historySideSearchQuery);
		// 20230112 결과내재검색 mediurlEncodingResult(컨트롤러1에서 사이드별로 가져올떄 쓰는 메소드)
		// medi_code있는것들만 취급한다 (영화,비디오,광고,공연추천)
		// total_url_informatin에는 해당 URL과 그 결과 갯수 정보, 해당 사이드 메뉴명을 가지고있다.
		// 현재 이 메소드는 movie01(), video01(), ad01(), perform01()에서만 호출된다. 즉
		// medi_code가 있는것들이 호출되는 메소드
		return total_url_information;
	}

	public void movie01(Walker walker, Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/*
		 * System.out.println(
		 * "@@@@@@@@@@@@movie01() 시작 -- param@@@@@@@@@@@@@@@@@");
		 * System.out.println(paramMap); System.out.println(
		 * "@@@@@@@@@@@@movie01() param@@@@@@@@@@@@@@@@@");
		 */

		// null처리는 필수!
		// 파라미터 목록 가져오기. * 검색어, 검색구분, 섹션구분
		String searchStr = (String) paramMap.get("searchStr");
		String searchGubun = (String) paramMap.get("searchGubun");
		String[] sectionGubun = null;
		if (request.getParameterValues("sectionGubun") == null) {
			sectionGubun = new String[] { "Total" };
		} else {
			sectionGubun = request.getParameterValues("sectionGubun");
		}
		List<String> sectionGubunList = new ArrayList<String>(Arrays.asList(sectionGubun));

		/*************** 검색어에 숫자가 들어올떄 *******************************/
		long searchStrInt = 0;
		if (paramMap.get("searchStr") != null && ((String) paramMap.get("searchStr")).matches("[-+]?\\d*\\.?\\d+")) {
			searchStrInt = Long.valueOf((String) paramMap.get("searchStr"));
			// System.out.println(searchStrInt);
		}
		/*************** 검색어에 숫자가 들어올떄 *******************************/
		// 최종 URL 담을 변수
		String totalUrl = "";
		// 공통 URL
		String result_noticeUrl = "http://192.168.11.242:7577/search5?select=*&from=result_notice.result_notice&where=";
		String result_opinUrl = "http://192.168.11.242:7577/search5?select=*&from=result_opin.result_opin&where=";
		String result_fileUrl = "http://192.168.11.242:7577/search5?select=*&from=result_file.result_file&where=";
		String result_imageUrl = "http://192.168.11.242:7577/search5?select=*&from=result_image.result_image&where=";
		String result_moniUrl = "http://192.168.11.242:7577/search5?select=*&from=result_moni.result_moni&where=";
		String result_surveyUrl = "http://192.168.11.242:7577/search5?select=*&from=result_survey.result_survey&where=";
		// 공통 인코딩 목록
		String encode = "UTF-8";
		String encodeSearchStr = URLEncoder.encode(searchStr, encode); // 검색어
		String encodeSearchGubun = URLEncoder.encode(searchGubun, encode); // 검색구분(통합검색,
																			// 제목,
																			// 내용,
																			// 결정사유
																			// 등등)
		String encodeAnd = URLEncoder.encode(" and ", encode);
		String encodeOr = URLEncoder.encode(" or ", encode);
		String encodeAns = URLEncoder.encode("=", encode);
		String encodeInOpen = URLEncoder.encode(" in{", encode);
		String encodeInClose = URLEncoder.encode("}", encode);
		String encodeLGual = "(";
		String encodeRGual = ")";
		String encodeLike = URLEncoder.encode(" like ", encode);
		String strMark = URLEncoder.encode("'", encode);
		String between = URLEncoder.encode(" between ", encode);

		// where절의 text타입에만 사용가능.
		String allword = URLEncoder.encode(" allword ", encode);
		String anyword = URLEncoder.encode(" anyword ", encode);
		String natural = URLEncoder.encode(" natural ", encode);

		// 동의어 검색
		String synonym = URLEncoder.encode(" synonym ", encode);

		// 카테고리 정렬 ex) ORDER BY $CATEGORYFIELD(field_name(domain), keyword)
		String categoryOrder = URLEncoder.encode(" $categoryfield(use_title(), 검색어넣기 ", encode);

		/**
		 * 17501 : 국내영화 17502 : 국외영화 17505 : 광고영화 17511 : 국내비디오 17512 : 국외비디오
		 */

		// medi_code 인코딩 목록
		String medi_mv = URLEncoder.encode("'17501','17502'", encode);
		String medi_ad = URLEncoder.encode("'17503','17504','17505','17506','17514'", encode);
		String medi_video = URLEncoder.encode("'17511','17522'", encode);
		String medi_perform = URLEncoder.encode("'17521','17522','17523','17524'", encode);

		/*
		 * //첫 로딩 시 데이터를 표시해주는 역할을 함. if(searchGubun == null ){ searchGubun =
		 * "All"; } else if(searchGubun.trim().isEmpty()){ searchGubun = "All";
		 * }
		 */

		String orderValue = (String) request.getParameter("orderValue");
		if (orderValue == null || orderValue.equals("")) {
			orderValue = URLEncoder.encode(" order by $match(rt_no) desc, $relevance desc, $rowid desc ", encode);
		}
		if (orderValue.equals("orderRelevance")) {
			orderValue = URLEncoder.encode("  order by $match(rt_no) desc, $relevance desc, $rowid desc ", encode);
		}
		if (orderValue.equals("orderGa")) {
			orderValue = "%20order%20by%20use_title_sort%20asc%20%2c%20ori_title_sort%20asc%20";
		}
		if (orderValue.equals("orderDate")) {
			orderValue = "%20order%20by%20rcv_date%20desc%20";
		}

		//(메모)groupByTest 사용 안하는 변수임.
		String groupByTest = URLEncoder.encode(" order by $relevance desc ", encode);

		// 2022 12 15 페이징 처리 시작

		// 1. sectionGubun을 하나만 선택하거나, -더보기 클릭 시, sectionGubun에는 딱 하나의 데이터가
		// 들어있고, 페이지는 1페이지로 이동한다. -->sectionGubun이 딱 하나라면 (Total)이 아닐떄 , 페이징목록이
		// 보이게끔 유도하기.
		// 페이지는 페이지 목록 버튼 시 마다 2페이지 3페이지 이동한다... 그러다가 다시 검색버튼 같은것을 누르면 1페이지로
		// 초기화!

		// 기존 display가 limit로 대체

		int cPage = 0;
		int offset = 0;
		int pagelength = 0;

		/**************************
		 * 20221223 페이징처리를 위한 새로운 로직 (영화만 반영했음)
		 *********************************************/
		if (((String) paramMap.get("isPaging")).equals("Y")) {
			cPage = (Integer) paramMap.get("cPage");
			offset = (Integer) paramMap.get("offset");
			pagelength = (Integer) paramMap.get("pagelength");
			offset = (cPage - 1) * pagelength;
		} else {
			cPage = 1;
			offset = 0;
			pagelength = 10;
			offset = (cPage - 1) * pagelength;
		}

		/*******************************************************************/

		/**************** 상세보기 시작 ********************************/
		String detail_Kind = (String) paramMap.get("detail_Kind");

		// 20221216 상세보기에 관련된 것들 맨 위로 빼기 (기본값이 존재함)
		// 하지만 상세보기를 하지 않는다면 이 값이 null이라서 오류가뜸 ~~~~~~~~~~~~~~~~~~~~~~~~~~~

		// 상세보기를 클릭했는지 여부
		String detail_visibleChk = (String) paramMap.get("detail_visibleChk");

		// 연산자 가져오기
		String searchOperation = "";

		// 결과 수
		int detailresultLine = 3;
		try {
			if ((String) paramMap.get("detailSearch_resultLine") != null) {
				detailresultLine = Integer.valueOf((String) paramMap.get("detailSearch_resultLine"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (detailresultLine != 3 && (String) paramMap.get("detailSearch_resultLine") != null) {
				detailresultLine = detailresultLine;
			} else {
				detailresultLine = 3;
			}
		}

		try {
			if (detail_visibleChk.equals("Y")) {
				searchOperation = (String) paramMap.get("detailSearch_radio");
			} else {
				searchOperation = "natural";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_radio") == null) {
				searchOperation = "natural";
			}
		}

		if (searchOperation.equals("allword")) {
			searchOperation = natural;
		} else if (searchOperation.equals("anyword")) {
			searchOperation = natural;
		} else if (searchOperation.equals("natural")) {
			searchOperation = natural;
		} else {
			searchOperation = natural;
		}
		
		if(searchStr.indexOf("-") != -1){
			searchOperation = allword;
		}

		// 신청회사 null 처리
		String detailAplc = "";
		try {
			if ((String) paramMap.get("detailSearch_aplc") != null) {
				detailAplc = (String) paramMap.get("detailSearch_aplc");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_aplc") == null) {
				detailAplc = "";
			}
		}

		// 상세보기-일자 데이터 가져오기 및 null 처리 시작

		// 시작날짜
		String period_start = "";
		try {
			if ((String) paramMap.get("period_start") != null) {
				period_start = (String) paramMap.get("period_start");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("period_start") == null) {
				period_start = "";
			}
		}

		// 종료날짜
		String period_end = "";
		try {
			if ((String) paramMap.get("period_end") != null) {
				period_end = (String) paramMap.get("period_end");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("period_end") == null) {
				period_end = "";
			}
		}

		// 등급분류일자, 신청일자 , 접수일자 구분
		String detail_dateGubun = "";
		try {
			if ((String) paramMap.get("detailSearch_date") != null) {
				detail_dateGubun = (String) paramMap.get("detailSearch_date");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_date") == null) {
				detail_dateGubun = "gradedate";
			}
		}

		if (!period_start.equals("")) {
			period_start = period_start.substring(0, 4) + period_start.substring(5, 7) + period_start.substring(8, 10)
					+ "000000";
		}
		if (!period_end.equals("")) {
			period_end = period_end.substring(0, 4) + period_end.substring(5, 7) + period_end.substring(8, 10)
					+ "000000";
		}

		// 듈 중에 하나를 선택안하고 상세검색을 하면 --> 전체날짜로 인식하여 데이터검색하기
		if ((period_start.equals("") || period_end.equals("")) && !(period_start.equals("") && period_end.equals(""))) {
			period_start = "";
			period_end = "";
		}

		// 상세보기-일자 데이터 가져오기 및 null 처리 끝

		/**************** 상세보기 끝 ********************************/

		// 하지만 상세보기를 하지 않는다면 이 값이 null이라서 오류가뜸 ~~~~~~~~~~~~~~~~~~~~~~~~~~~

		
		
		
		
		// 20230328 따옴표 기능 추가 시작
		//먼저 JSP의 정규식에서 따옴표를 받을 수 있겍끔 허용해준다.
		
		String quote_searchStr_origin = ""; //반드시 포함해야 하는 따옴표 안의 단어.
		String quote_searchStr = ""; //반드시 포함해야 하는 따옴표 안의 단어 (인코딩완료).
		
		String word = "\""; // 찾고자하는 단어
		int searchStrIndex = searchStr.indexOf(word);
		
		List<Integer> searchStrIndexList = new ArrayList<Integer>();
		 while(searchStrIndex != -1) {
			 searchStrIndexList.add(searchStrIndex);
			 searchStrIndex = searchStr.indexOf(word, searchStrIndex+word.length()); 
		 }
		
		 
		 if(searchStrIndexList.size() >= 2 ) {
			 int firstIndex = searchStrIndexList.get(0);
			 int lastIndex = searchStrIndexList.get(1);
			 
			 //subString 메소드로 firstLindex는 searchStr의  "를 가리키고, lastLindex는 searchStr의 " 앞에는 가리킴/ 그래서 아래와 같이 로직을 구성해야한다
			 quote_searchStr_origin = searchStr.substring(firstIndex + 1, lastIndex);  //  ~~ "실질적으로 찾고자하는 단어" ~~     -> 실
			 quote_searchStr = URLEncoder.encode(quote_searchStr_origin, encode);
		 }
		
		 
		//  20230329 추가
		 if(searchStrIndexList.size() >= 1 ){
			 //따옴표가 하나 이상있으면, encodeSearchStr(기본 검색어)에서 따옴표 제거할 것.  --> 왜? --> 따옴표 기능으로 검색 시, "범죄"를 다룬 영화 + "범죄" 를 하면 "범죄"에 관한 영화만 검색되므로,
			   //--> 범죄를 다룬 영화 + "범죄" 이런식으로, 기본 검색어에서는 따옴표를 지워줘야함
			 
			String searchStr2 = searchStr.replaceAll("\"", "");
			String searchStr3 = searchStr2.replaceAll( quote_searchStr_origin , "").trim(); 
			if(!searchStr3.equals("") && searchStr3 != null){
				encodeSearchStr = URLEncoder.encode(searchStr3, encode); // 검색어
			}
		 }
		 
		 
		 
		 
		    String quote_searchCol = "";
			String quote_searchCol_ori_title = "";
			String quote_searchCol_use_title = "";
			String quote_searchCol_dire_name = "";
			String quote_searchCol_prodc_name = "";
			String quote_searchCol_rcv_no_view = "";
			String quote_searchCol_sub_no = "";
			String quote_searchCol_onlin_offlin = "";
			String quote_searchCol_medi_name = "";
			String quote_searchCol_prodc_natnl_name = "";
			String quote_searchCol_mv_asso_name = "";
			String quote_searchCol_leada_name = "";
			String quote_searchCol_frgnr_natnl_name = "";
			String quote_searchCol_kind_name = "";
			String quote_searchCol_hope_grade_name = "";
			String quote_searchCol_prod_year = "";
			String quote_searchCol_pfm_place_name = "";
			String quote_searchCol_repr_nm = "";
			String quote_searchCol_aplc_name = "";
			String quote_searchCol_rt_no = "";
			String quote_searchCol_grade_name = "";
			String quote_searchCol_deter_opin_name = "";
			String quote_searchCol_rt_std_name1 = "";
			String quote_searchCol_rt_std_name2 = "";
			String quote_searchCol_rt_std_name3 = "";
			String quote_searchCol_rt_std_name4 = "";
			String quote_searchCol_rt_std_name5 = "";
			String quote_searchCol_rt_std_name6 = "";
			String quote_searchCol_rt_std_name7 = "";
			String quote_searchCol_minor_malef_yn = "";
			String quote_searchCol_wdra_yn = "";
			String quote_searchCol_return_name = "";
			String quote_searchCol_proc_state_code = "";
			String quote_searchCol_state_code = "";
			String quote_searchCol_non_com_yn = "";
			String quote_searchCol_rt_core_harm_rsn_code1 = "";
			String quote_searchCol_rt_core_harm_rsn_code2 = "";
			String quote_searchCol_rt_core_harm_rsn_code3 = "";
			String quote_searchCol_work_cont = "";
			String quote_searchCol_deter_rsn = "";
			String quote_searchCol_major_opin_narrn_cont = "";
			String quote_searchCol_core_harm_rsn = "";
			String quote_searchCol_sub_rcv_date = "";
			String quote_searchCol_rcv_date = "";
			String quote_searchCol_rt_date = "";
			String quote_searchCol_pfm_pnum = "";
			String quote_searchCol_contr_start_date = "";
			String quote_searchCol_contr_end_date = "";
			String quote_searchCol_ori_rt_no = "";
		 if(!quote_searchStr.equals("")) {
			 //반드시 포함해야 하는 단어가 공백이 아니라면 movie01()
			 
    /***********************************************movie01() quote_searchStr start*************************************************************************/	 
			
		quote_searchCol += encodeAnd + "(" + "use_title" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" +  "synonym('d0')" 
						+ encodeOr + "ori_title" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" +  "synonym('d0')" 
					    + encodeOr + "dire_name" + encodeAns + strMark + quote_searchStr + strMark // 감독명
						+ encodeOr + "prodc_name" + encodeAns + strMark + quote_searchStr + strMark // 제작사명
						+ encodeOr + "rcv_no_view" + encodeAns + strMark + quote_searchStr + strMark + // no3
																										// 접수번호
																										// rcv_no_view
																										// |string
																										// like
																										// X
						encodeOr + "sub_no" + encodeAns + strMark + quote_searchStr + strMark + // no4
																								// 신청번호
																								// sub_no
																								// |string
																								// like
																								// X
						encodeOr + "onlin_offlin" + encodeAns + strMark + quote_searchStr + strMark +  // no7
																													// 온라인/오프라인
																													// |string
						encodeOr + "medi_name" + encodeAns + strMark + quote_searchStr + strMark +  // no9
																												// 매체명
																												// |string
						encodeOr + "prodc_natnl_name" + encodeAns + strMark + quote_searchStr + strMark
						+ // no15 제작자 국적 |string
						encodeOr + "mv_asso_name" + encodeAns + strMark + quote_searchStr + strMark +  // no23
																													// 영화
																													// 종류
																													// |string
						encodeOr + "leada_name" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" +   // no25
																													// 주연명
																													// |string
						encodeOr + "frgnr_natnl_name" + encodeAns + strMark + quote_searchStr + strMark
						+ // no29 외국인 국적 |string
						encodeOr + "kind_name" + encodeAns + strMark + quote_searchStr + strMark + // no35
																												// 종류
																												// |string
						encodeOr + "hope_grade_name" + encodeAns + strMark + quote_searchStr + strMark
						+ // no41 희망등급 |string
						encodeOr + "prod_year" + encodeAns + strMark + quote_searchStr + strMark + // no42
																									// 제작년도
																									// |string
																									// like
																									// X
						encodeOr + "pfm_place_name" + encodeAns + strMark + quote_searchStr + strMark + // no46
																														// 공연
																														// 장소명
																														// |string
						encodeOr + "repr_nm" + encodeAns + strMark + quote_searchStr + strMark + // no61
																												// 대표자
																												// 성명
																												// |string
						encodeOr + "aplc_name" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + // no63
																												// 신청회사
																												// |string
						encodeOr + "rt_no" + encodeAns + strMark + quote_searchStr + strMark + // no69
																								// 등급분류번호
																								// |string
																								// like
																								// X
						encodeOr + "grade_name" + encodeAns + strMark + quote_searchStr + strMark + // no71
																													// 결정등급
																													// |string
						encodeOr + "deter_opin_name" + encodeAns + strMark + quote_searchStr + strMark
						+ // no72 결정의견 |string
						encodeOr + "rt_std_name1" + encodeAns + strMark + quote_searchStr + strMark + // no73
																													// 주제
																													// |string
						encodeOr + "rt_std_name2" + encodeAns + strMark + quote_searchStr + strMark + // no74
																													// 선정성
																													// |string
						encodeOr + "rt_std_name3" + encodeAns + strMark + quote_searchStr + strMark + // no75
																													// 폭력성
																													// |string
						encodeOr + "rt_std_name4" + encodeAns + strMark + quote_searchStr + strMark + // no76
																													// 대사
																													// |string
						encodeOr + "rt_std_name5" + encodeAns + strMark + quote_searchStr + strMark + // no77
																													// 공포
																													// |string
						encodeOr + "rt_std_name6" + encodeAns + strMark + quote_searchStr + strMark + // no78
																													// 약물
																													// |string
						encodeOr + "rt_std_name7" + encodeAns + strMark + quote_searchStr + strMark + // no79
																													// 모방위험
																													// |string
						encodeOr + "minor_malef_yn" + encodeAns + strMark + quote_searchStr + strMark + // no80
																										// 연소자
																										// 유해성
																										// 여부
																										// |string
																										// like
																										// X
						encodeOr + "wdra_yn" + encodeAns + strMark + quote_searchStr + strMark + // no86
																									// 취하여부
																									// |string
																									// like
																									// X
						encodeOr + "return_name" + encodeAns + strMark + quote_searchStr + strMark + // no90
																										// 취하,
																										// 반납
																										// |string
																										// like
																										// X
						encodeOr + "proc_state_code" + encodeAns + strMark + quote_searchStr + strMark + // no97
																											// 진행
																											// 상태
																											// 코드
																											// |string
																											// like
																											// X
						encodeOr + "state_code" + encodeAns + strMark + quote_searchStr + strMark + // no98
																									// 상태코드
																									// |string
																									// like
																									// X
						encodeOr + "non_com_yn" + encodeAns + strMark + quote_searchStr + strMark + // no109
																													// 불완전데이터
																													// 플래그
																													// |string
						encodeOr + "rt_core_harm_rsn_code1" + encodeAns + strMark + quote_searchStr + strMark + // no114 내용정보표시항목1 |string
						encodeOr + "rt_core_harm_rsn_code2" + encodeAns + strMark + quote_searchStr + strMark + // no114 내용정보표시항목2 |string
						encodeOr + "rt_core_harm_rsn_code3" + encodeAns + strMark + quote_searchStr + strMark + // no114 내용정보표시항목3 |string
						encodeOr + "work_cont"             + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + // no81// 작품내용(줄거리) // |text
						encodeOr + "deter_rsn"             + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" +  // no83 // 결정사유 // |text
						encodeOr + "major_opin_narrn_cont" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + // no84 주요의견 |text
						encodeOr + "core_harm_rsn"         + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+"; // no85 핵심유해사유 |text

				// int는 encode를 따로 안해줘도 무방함.
				if (searchStrInt != 0) {
					quote_searchCol += encodeOr + "sub_rcv_date" + encodeAns + searchStrInt + "000000" + // no5
																									// 신청일자
																									// |
																									// int
							encodeOr + "rcv_date" + encodeAns + searchStrInt + "000000" + // no6
																							// 접수일자
																							// |
																							// int
							encodeOr + "rt_date" + encodeAns + searchStrInt + "000000" + // no70
																							// 등급분류일
																							// |
																							// int
							encodeOr + "pfm_pnum" + encodeAns + searchStrInt + // no28
																				// 외국인
																				// 인원수
																				// |
																				// int
							encodeOr + "contr_start_date" + encodeAns + searchStrInt + "000000" + // no44
																									// 계약
																									// 시작
																									// 일자
																									// |
																									// int
							encodeOr + "contr_end_date" + encodeAns + searchStrInt + "000000" + // no45
																								// 계약
																								// 종료
																								// 일자
																								// |
																								// int
							encodeOr + "ori_rt_no" + encodeAns + searchStrInt; // no59
																				// 신규추천번호
																				// |
																				// int
				}
				// 통합검색의 마무리 괄호
				quote_searchCol += ")";
				
				/*
				 * 20230329  이 로직을 쓰면, 사이드메뉴에서  [기본 검색어] + [따옴표 검색어]를 다 만족하는 것들을 보여줘야 하기 때문에, 웬만하면 사이드에서는 0건으로 통계가 잡힌다.(나중에 쓸수도있어서 주석처리)
				 */
				  // --> 그래서 [기본 검색어] + [따옴표 검색어] 가 아닌, 그냥 기존에 썼던 [기본 검색어]로 사이드를 보여주는 코드를 아래에 새로 생성할 예정 / 
				  //기존 사이드메뉴 로직에서  ★★[따옴표]가 들어오면  제거하는 로직이 원래 있어야하지만, 따옴표가 들어오면 따옴표가 제거되도록, encodeSearchStr 변수 값을 구하는 로직이 위에서 구성됐으므로, 기존 사이드 메뉴를 구하는 로직을 써도 무방함!
				
				/*
				quote_searchCol_ori_title =  encodeAnd + "(" + "ori_title" + encodeAns + strMark + "%22" +  quote_searchStr + "%22" + strMark + "+allword+"
						 + "synonym('d0')" + ")";
				quote_searchCol_use_title = encodeAnd + "(" + "use_title" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+"
						 + "synonym('d0')" + ")";
				quote_searchCol_dire_name = encodeAnd + "(" + "dire_name" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_prodc_name = encodeAnd + "(" + "prodc_name" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_rcv_no_view = encodeAnd + "(" + "rcv_no_view" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_sub_no = encodeAnd + "(" + "sub_no" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_onlin_offlin = encodeAnd + "(" + "onlin_offlin" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_medi_name = encodeAnd + "(" + "medi_name" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_prodc_natnl_name = encodeAnd + "(" + "prodc_natnl_name" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_mv_asso_name = encodeAnd + "(" + "mv_asso_name" + encodeAns + strMark + quote_searchStr + strMark +  ")";
				quote_searchCol_leada_name = encodeAnd + "(" + "leada_name" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + ")";
				quote_searchCol_frgnr_natnl_name = encodeAnd + "(" + "frgnr_natnl_name" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_kind_name = encodeAnd + "(" + "kind_name" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_hope_grade_name = encodeAnd + "(" + "hope_grade_name" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_prod_year = encodeAnd + "(" + "prod_year" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_pfm_place_name = encodeAnd + "(" + "pfm_place_name" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_repr_nm = encodeAnd + "(" + "repr_nm" + encodeAns + strMark + quote_searchStr + strMark
						+ ")";
				quote_searchCol_aplc_name = encodeAnd + "(" + "aplc_name" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + ")";
				quote_searchCol_rt_no = encodeAnd + "(" + "rt_no" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_grade_name = encodeAnd + "(" + "grade_name" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_deter_opin_name = encodeAnd + "(" + "deter_opin_name" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_rt_std_name1 = encodeAnd + "(" + "rt_std_name1" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_rt_std_name2 = encodeAnd + "(" + "rt_std_name2" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_rt_std_name3 = encodeAnd + "(" + "rt_std_name3" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_rt_std_name4 = encodeAnd + "(" + "rt_std_name4" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_rt_std_name5 = encodeAnd + "(" + "rt_std_name5" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_rt_std_name6 = encodeAnd + "(" + "rt_std_name6" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_rt_std_name7 = encodeAnd + "(" + "rt_std_name7" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_minor_malef_yn = encodeAnd + "(" + "minor_malef_yn" + encodeAns + strMark + quote_searchStr + strMark
						+ ")";
				quote_searchCol_wdra_yn = encodeAnd + "(" + "wdra_yn" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_return_name = encodeAnd + "(" + "return_name" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_proc_state_code = encodeAnd + "(" + "proc_state_code" + encodeAns + strMark + quote_searchStr
						+ strMark + ")";
				quote_searchCol_state_code = encodeAnd + "(" + "state_code" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_non_com_yn = encodeAnd + "(" + "non_com_yn" + encodeAns + strMark + quote_searchStr + strMark + ")";
				quote_searchCol_rt_core_harm_rsn_code1 = encodeAnd + "(" + "rt_core_harm_rsn_code1" + encodeAns + strMark  + strMark + ")";
				quote_searchCol_rt_core_harm_rsn_code2 = encodeAnd + "(" + "rt_core_harm_rsn_code2" + encodeAns + strMark  + strMark + ")";
				quote_searchCol_rt_core_harm_rsn_code3 = encodeAnd + "(" + "rt_core_harm_rsn_code3" + encodeAns + strMark  + strMark + ")";
				// 텍스트 타입 시작!
				quote_searchCol_work_cont = encodeAnd + "(" + "work_cont" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+"
						 + ")";
				quote_searchCol_deter_rsn = encodeAnd + "(" + "deter_rsn" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+"
						 + ")";
				quote_searchCol_major_opin_narrn_cont = encodeAnd + "(" + "major_opin_narrn_cont" + encodeAns + strMark	+ "%22" + quote_searchStr + "%22" + strMark + "+allword+" + ")";
				quote_searchCol_core_harm_rsn = encodeAnd + "(" + "core_harm_rsn" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+"
					    + ")";
				// 검색어가 숫자인 경우 추가되는 항목!! 
				if (searchStrInt != 0) {
					quote_searchCol_sub_rcv_date = encodeAnd + "(" + "sub_rcv_date" + encodeAns + searchStrInt + "000000" + ")";
					quote_searchCol_rcv_date = encodeAnd + "(" + "rcv_date" + encodeAns + searchStrInt + "000000" + ")";
					quote_searchCol_rt_date = encodeAnd + "(" + "rt_date" + encodeAns + searchStrInt + "000000" + ")";
					quote_searchCol_pfm_pnum = encodeAnd + "(" + "pfm_pnum" + encodeAns + searchStrInt + ")";
					quote_searchCol_contr_start_date = encodeAnd + "(" + "contr_start_date" + encodeAns + searchStrInt + "000000"
							+ ")";
					quote_searchCol_contr_end_date = encodeAnd + "(" + "contr_end_date" + encodeAns + searchStrInt + "000000" + ")";
					quote_searchCol_ori_rt_no = encodeAnd + "(" + "ori_rt_no" + encodeAns + searchStrInt + ")";
				}
				
				*/
				
		 }
		
		 /***********************************************movie01() quote_searchStr start*************************************************************************/
		
		 // 따옴표 기능 추가 끝
		
		
		
		///////////////////////////////////////////// 영화 시작 (17501,
		///////////////////////////////////////////// 17502)///////////////////////////////////////////////////////////////////////////////////////////////////

		if (sectionGubunList.contains("movie01") || sectionGubunList.contains("Total")) {
			// 20221216 17시 카운트가 2개라서 맨 위로 올림 -- 매체마다 다른 map이라서 바깥으로 뺴도 무방함
			Map<String, Object> movie01 = new HashMap<String, Object>();

			String medi_code = "medi_code" + encodeInOpen + medi_mv + encodeInClose;
			// ex) medi_code in {'17501' , '17502'} 를 인코딩 한 url로 준비하는 과정임.

			if (searchStr != null) {

				/*****************
				 * 검색구분 처리 시작! : 통합검색이면 모든 조건에 검색어 처리.
				 ************************************/
				// 검색구분은 if절로서 쓰이고, 검색어는 where절 안에 쓰임으로써, 직접적인 쿼리에 영향을 줌.
				String searchCol = ""; // where절을 담을 변수
				String searchCol_ori_title = "";
				String searchCol_use_title = "";
				String searchCol_dire_name = "";
				String searchCol_prodc_name = "";
				String searchCol_rcv_no_view = "";
				String searchCol_sub_no = "";
				String searchCol_onlin_offlin = "";
				String searchCol_medi_name = "";
				String searchCol_prodc_natnl_name = "";
				String searchCol_mv_asso_name = "";
				String searchCol_leada_name = "";
				String searchCol_frgnr_natnl_name = "";
				String searchCol_kind_name = "";
				String searchCol_hope_grade_name = "";
				String searchCol_prod_year = "";
				String searchCol_pfm_place_name = "";
				String searchCol_repr_nm = "";
				String searchCol_aplc_name = "";
				String searchCol_rt_no = "";
				String searchCol_grade_name = "";
				String searchCol_deter_opin_name = "";
				String searchCol_rt_std_name1 = "";
				String searchCol_rt_std_name2 = "";
				String searchCol_rt_std_name3 = "";
				String searchCol_rt_std_name4 = "";
				String searchCol_rt_std_name5 = "";
				String searchCol_rt_std_name6 = "";
				String searchCol_rt_std_name7 = "";
				String searchCol_minor_malef_yn = "";
				String searchCol_wdra_yn = "";
				String searchCol_return_name = "";
				String searchCol_proc_state_code = "";
				String searchCol_state_code = "";
				String searchCol_non_com_yn = "";
				String searchCol_rt_core_harm_rsn_code1 = "";
				String searchCol_rt_core_harm_rsn_code2 = "";
				String searchCol_rt_core_harm_rsn_code3 = "";
				String searchCol_work_cont = "";
				String searchCol_deter_rsn = "";
				String searchCol_major_opin_narrn_cont = "";
				String searchCol_core_harm_rsn = "";
				String searchCol_sub_rcv_date = "";
				String searchCol_rcv_date = "";
				String searchCol_rt_date = "";
				String searchCol_pfm_pnum = "";
				String searchCol_contr_start_date = "";
				String searchCol_contr_end_date = "";
				String searchCol_ori_rt_no = "";

				// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
				if (searchGubun.equals("All")) {
					searchCol += "(" + "use_title" + encodeAns + strMark + encodeSearchStr + strMark + "+allword+" + "synonym('d0')" 
				            + encodeOr + "use_title" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + "synonym('d0')" 
							+ encodeOr + "ori_title" + encodeAns + strMark
							+ encodeSearchStr + strMark + searchOperation + "synonym('d0')" + encodeOr + "dire_name"
							+ encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // 감독명
							encodeOr + "prodc_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // 제작사명
							encodeOr + "rcv_no_view" + encodeAns + strMark + encodeSearchStr + strMark + // no3
																											// 접수번호
																											// rcv_no_view
																											// |string
																											// like
																											// X
							encodeOr + "sub_no" + encodeAns + strMark + encodeSearchStr + strMark + // no4
																									// 신청번호
																									// sub_no
																									// |string
																									// like
																									// X
							encodeOr + "onlin_offlin" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no7
																														// 온라인/오프라인
																														// |string
							encodeOr + "medi_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no9
																													// 매체명
																													// |string
							encodeOr + "prodc_natnl_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no15 제작자 국적 |string
							encodeOr + "mv_asso_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no23
																														// 영화
																														// 종류
																														// |string
							encodeOr + "leada_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation +   // no25
																														// 주연명
																														// |string
							encodeOr + "frgnr_natnl_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no29 외국인 국적 |string
							encodeOr + "kind_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no35
																													// 종류
																													// |string
							encodeOr + "hope_grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no41 희망등급 |string
							encodeOr + "prod_year" + encodeAns + strMark + encodeSearchStr + strMark + // no42
																										// 제작년도
																										// |string
																										// like
																										// X
							encodeOr + "pfm_place_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no46
																															// 공연
																															// 장소명
																															// |string
							encodeOr + "repr_nm" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no61
																													// 대표자
																													// 성명
																													// |string
							encodeOr + "aplc_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + // no63
																													// 신청회사
																													// |string
							encodeOr + "rt_no" + encodeAns + strMark + encodeSearchStr + strMark + // no69
																									// 등급분류번호
																									// |string
																									// like
																									// X
							encodeOr + "grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no71
																														// 결정등급
																														// |string
							encodeOr + "deter_opin_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no72 결정의견 |string
							encodeOr + "rt_std_name1" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no73
																														// 주제
																														// |string
							encodeOr + "rt_std_name2" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no74
																														// 선정성
																														// |string
							encodeOr + "rt_std_name3" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no75
																														// 폭력성
																														// |string
							encodeOr + "rt_std_name4" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no76
																														// 대사
																														// |string
							encodeOr + "rt_std_name5" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no77
																														// 공포
																														// |string
							encodeOr + "rt_std_name6" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no78
																														// 약물
																														// |string
							encodeOr + "rt_std_name7" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no79
																														// 모방위험
																														// |string
							encodeOr + "minor_malef_yn" + encodeAns + strMark + encodeSearchStr + strMark + // no80
																											// 연소자
																											// 유해성
																											// 여부
																											// |string
																											// like
																											// X
							encodeOr + "wdra_yn" + encodeAns + strMark + encodeSearchStr + strMark + // no86
																										// 취하여부
																										// |string
																										// like
																										// X
							encodeOr + "return_name" + encodeAns + strMark + encodeSearchStr + strMark + // no90
																											// 취하,
																											// 반납
																											// |string
																											// like
																											// X
							encodeOr + "proc_state_code" + encodeAns + strMark + encodeSearchStr + strMark + // no97
																												// 진행
																												// 상태
																												// 코드
																												// |string
																												// like
																												// X
							encodeOr + "state_code" + encodeAns + strMark + encodeSearchStr + strMark + // no98
																										// 상태코드
																										// |string
																										// like
																										// X
							encodeOr + "non_com_yn" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no109
																														// 불완전데이터
																														// 플래그
																														// |string
							encodeOr + "rt_core_harm_rsn_code1" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + // no114 내용정보표시항목1 |string
							encodeOr + "rt_core_harm_rsn_code2" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + // no114 내용정보표시항목2 |string
							encodeOr + "rt_core_harm_rsn_code3" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + // no114 내용정보표시항목3 |string
							encodeOr + "work_cont"             + encodeAns + strMark + encodeSearchStr + strMark + "+someword+" + // no81// 작품내용(줄거리) // |text
							encodeOr + "deter_rsn"             + encodeAns + strMark + encodeSearchStr + strMark + "+someword+" + // no83 // 결정사유 // |text
							encodeOr + "major_opin_narrn_cont" + encodeAns + strMark + encodeSearchStr + strMark + "+someword+" + // no84 주요의견 |text
							encodeOr + "core_harm_rsn"         + encodeAns + strMark + encodeSearchStr + strMark + "+someword+"; // no85 핵심유해사유 |text

					// int는 encode를 따로 안해줘도 무방함.
					if (searchStrInt != 0) {
						searchCol += encodeOr + "sub_rcv_date" + encodeAns + searchStrInt + "000000" + // no5
																										// 신청일자
																										// |
																										// int
								encodeOr + "rcv_date" + encodeAns + searchStrInt + "000000" + // no6
																								// 접수일자
																								// |
																								// int
								encodeOr + "rt_date" + encodeAns + searchStrInt + "000000" + // no70
																								// 등급분류일
																								// |
																								// int
								encodeOr + "pfm_pnum" + encodeAns + searchStrInt + // no28
																					// 외국인
																					// 인원수
																					// |
																					// int
								encodeOr + "contr_start_date" + encodeAns + searchStrInt + "000000" + // no44
																										// 계약
																										// 시작
																										// 일자
																										// |
																										// int
								encodeOr + "contr_end_date" + encodeAns + searchStrInt + "000000" + // no45
																									// 계약
																									// 종료
																									// 일자
																									// |
																									// int
								encodeOr + "ori_rt_no" + encodeAns + searchStrInt; // no59
																					// 신규추천번호
																					// |
																					// int
					}

					// 통합검색의 마무리 괄호
					searchCol += ")";

					// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가

					// 0 원제명Url (텍스트지만, 중요도에 따라 맨위에 위치)
					searchCol_ori_title = "(" + "ori_title" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + "synonym('d0')" + ")";

					// 0 사용제명Url (텍스트지만, 중요도에 따라 맨위에 위치)
					searchCol_use_title = "(" + "use_title" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + "synonym('d0')" + ")";

					// 1. 감독명Url
					searchCol_dire_name = "(" + "dire_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 2. 제작사명Url
					searchCol_prodc_name = "(" + "prodc_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";

					// 3. 접수번호Url
					searchCol_rcv_no_view = "(" + "rcv_no_view" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 4. 신청번호Url
					searchCol_sub_no = "(" + "sub_no" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 5. 온라인/오프라인 Url
					searchCol_onlin_offlin = "(" + "onlin_offlin" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 6. 매체명
					searchCol_medi_name = "(" + "medi_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 7. 제작자 국적
					searchCol_prodc_natnl_name = "(" + "prodc_natnl_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 8. 영화 종류
					searchCol_mv_asso_name = "(" + "mv_asso_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 9. 주연명
					searchCol_leada_name = "(" + "leada_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + ")";
					// 10. 외국인 국적
					searchCol_frgnr_natnl_name = "(" + "frgnr_natnl_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 11. 종류
					searchCol_kind_name = "(" + "kind_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 12. 희망등급
					searchCol_hope_grade_name = "(" + "hope_grade_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 13. 제작년도
					searchCol_prod_year = "(" + "prod_year" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 14. 공연 장소명
					searchCol_pfm_place_name = "(" + "pfm_place_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 15. 대표자 성명
					searchCol_repr_nm = "(" + "repr_nm" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ ")";
					// 16. 신청회사
					searchCol_aplc_name = "(" + "aplc_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + ")";
					// 17. 등급분류번호
					searchCol_rt_no = "(" + "rt_no" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 18. 결정등급
					searchCol_grade_name = "(" + "grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 19. 결정의견
					searchCol_deter_opin_name = "(" + "deter_opin_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 20. 주제
					searchCol_rt_std_name1 = "(" + "rt_std_name1" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 21. 선정성
					searchCol_rt_std_name2 = "(" + "rt_std_name2" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 22. 폭력성
					searchCol_rt_std_name3 = "(" + "rt_std_name3" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 23. 대사
					searchCol_rt_std_name4 = "(" + "rt_std_name4" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 24. 공포
					searchCol_rt_std_name5 = "(" + "rt_std_name5" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 25. 약물
					searchCol_rt_std_name6 = "(" + "rt_std_name6" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 26. 모방위험
					searchCol_rt_std_name7 = "(" + "rt_std_name7" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 27. 연소자 유해성 여부
					searchCol_minor_malef_yn = "(" + "minor_malef_yn" + encodeAns + strMark + encodeSearchStr + strMark
							+ ")";
					// 28. 취하여부
					searchCol_wdra_yn = "(" + "wdra_yn" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 29. 취하,반납
					searchCol_return_name = "(" + "return_name" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 30. 진행 상태 코드
					searchCol_proc_state_code = "(" + "proc_state_code" + encodeAns + strMark + encodeSearchStr
							+ strMark + ")";
					// 31. 상태코드
					searchCol_state_code = "(" + "state_code" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 32. 불완전데이터 플래그
					searchCol_non_com_yn = "(" + "non_com_yn" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 33. 내용정보표시항목1
					searchCol_rt_core_harm_rsn_code1 = "(" + "rt_core_harm_rsn_code1" + encodeLike + strMark + "*"
							+ encodeSearchStr + "*" + strMark + ")";
					// 34. 내용정보표시항목2
					searchCol_rt_core_harm_rsn_code2 = "(" + "rt_core_harm_rsn_code2" + encodeLike + strMark + "*"
							+ encodeSearchStr + "*" + strMark + ")";
					// 35. 내용정보표시항목3
					searchCol_rt_core_harm_rsn_code3 = "(" + "rt_core_harm_rsn_code3" + encodeLike + strMark + "*"
							+ encodeSearchStr + "*" + strMark + ")";

					/********* 텍스트 타입 시작! **********/
					// 36. 작품내용(줄거리)
					searchCol_work_cont = "(" + "work_cont" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+" + ")";
					// 37. 결정사유
					searchCol_deter_rsn = "(" + "deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+" + ")";
					// 38. 주요의견
					searchCol_major_opin_narrn_cont = "(" + "major_opin_narrn_cont" + encodeAns + strMark
							+ encodeSearchStr + strMark + "+someword+" + ")";
					// 39. 핵심유해사유
					searchCol_core_harm_rsn = "(" + "core_harm_rsn" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+" + ")";

					/*** 검색어가 숫자인 경우 추가되는 항목!! ***/
					if (searchStrInt != 0) {

						// 40. 신청일자
						searchCol_sub_rcv_date = "(" + "sub_rcv_date" + encodeAns + searchStrInt + "000000" + ")";
						// 41. 접수일자
						searchCol_rcv_date = "(" + "rcv_date" + encodeAns + searchStrInt + "000000" + ")";
						// 42. 등급분류일
						searchCol_rt_date = "(" + "rt_date" + encodeAns + searchStrInt + "000000" + ")";
						// 43. 외국인 인원수
						searchCol_pfm_pnum = "(" + "pfm_pnum" + encodeAns + searchStrInt + ")";
						// 44. 계약 시작 일자
						searchCol_contr_start_date = "(" + "contr_start_date" + encodeAns + searchStrInt + "000000"
								+ ")";
						// 45. 계약 종료 일자
						searchCol_contr_end_date = "(" + "contr_end_date" + encodeAns + searchStrInt + "000000" + ")";
						// 46. 신규추천번호
						searchCol_ori_rt_no = "(" + "ori_rt_no" + encodeAns + searchStrInt + ")";
					}

				}
				if (searchGubun.equals("title")) {
					searchCol += "(use_title" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ "synonym('d0')" + encodeOr + "ori_title" + encodeAns + strMark + encodeSearchStr
							+ strMark + searchOperation + "synonym('d0')" + ")";
				}
				if (searchGubun.equals("decision")) {
					searchCol += "(deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ "synonym('d0')" + encodeOr + "deter_basis" + encodeAns + strMark + encodeSearchStr
							+ strMark + searchOperation + "synonym('d0')" + ")";
				}

				// 20230202 if 검색어가 공백이지만, 상세조건에 값이 있다면? ex) 검색어는 빈값, 주연명 "마동석"
				// searchCol부터.. 전부 다~ medi_code값을 줌 --> 검색어가 빈값일떄 이 방법이 최선.
				// 일부러 의미가 없는 medi_code를  부여 함으로써, 빈값일때 검색이 되도록 하는 로직임. medi_code를 줬다고 해서 당황 X 그냥 오류를 방지하기 위한 빈값이라고 생각하면됨
				if (searchStr.equals("NO_VALUE")) {
					searchCol = medi_code;
					searchCol_dire_name = medi_code;
					searchCol_prodc_name = medi_code;
					searchCol_rcv_no_view = medi_code;
					searchCol_sub_no = medi_code;
					searchCol_onlin_offlin = medi_code;
					searchCol_medi_name = medi_code;
					searchCol_prodc_natnl_name = medi_code;
					searchCol_mv_asso_name = medi_code;
					searchCol_leada_name = medi_code;
					searchCol_frgnr_natnl_name = medi_code;
					searchCol_kind_name = medi_code;
					searchCol_hope_grade_name = medi_code;
					searchCol_prod_year = medi_code;
					searchCol_pfm_place_name = medi_code;
					searchCol_repr_nm = medi_code;
					searchCol_aplc_name = medi_code;
					searchCol_rt_no = medi_code;
					searchCol_grade_name = medi_code;
					searchCol_deter_opin_name = medi_code;
					searchCol_rt_std_name1 = medi_code;
					searchCol_rt_std_name2 = medi_code;
					searchCol_rt_std_name3 = medi_code;
					searchCol_rt_std_name4 = medi_code;
					searchCol_rt_std_name5 = medi_code;
					searchCol_rt_std_name6 = medi_code;
					searchCol_rt_std_name7 = medi_code;
					searchCol_minor_malef_yn = medi_code;
					searchCol_wdra_yn = medi_code;
					searchCol_return_name = medi_code;
					searchCol_proc_state_code = medi_code;
					searchCol_state_code = medi_code;
					searchCol_non_com_yn = medi_code;
					searchCol_rt_core_harm_rsn_code1 = medi_code;
					searchCol_rt_core_harm_rsn_code2 = medi_code;
					searchCol_rt_core_harm_rsn_code3 = medi_code;

					// 텍스트타입
					searchCol_ori_title = medi_code;
					searchCol_use_title = medi_code;
					searchCol_work_cont = medi_code;
					searchCol_deter_rsn = medi_code;
					searchCol_major_opin_narrn_cont = medi_code;
					searchCol_core_harm_rsn = medi_code;

					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_sub_rcv_date = medi_code;
						searchCol_rcv_date = medi_code;
						searchCol_rt_date = medi_code;
						searchCol_pfm_pnum = medi_code;
						searchCol_contr_start_date = medi_code;
						searchCol_contr_end_date = medi_code;
						searchCol_ori_rt_no = medi_code;
					}
				} // if(searchStr == null) 끝.

				// 상세검색 일때만 뒤에 이걸 붙여주자!
				/****************
				 * 20221220 상세검색의 일자 통합작업중
				 ***********************/
				if (detail_visibleChk.equals("Y")) {
					
					 //0220 한번 더 searchCol를 괄호로 묶어줄 준비를 한다. 그전에 And연산자를 덧붙여서, 기존 통합검색의 (기존검색어) 와 And로 연결해줘야한다 (무조건 And연산자임!!)
 					 // 1. 상세조건 추가하기전 첫 부분에는 And가 오류나지 않게 , 더미 스텁을 넣어줘야한다 그 역할을 하는것이 medi_code(여기서 현재 아무의미없이 쓰임) 
   					 //searchCol += encodeAnd + "(" + medi_code;
					 // 1-1 이렇게 된다면, 만약 상세조건을 Y하고, 아무것도 검색을 하지않아도  (기존 검색) AND (medi_code)라는 그림이 나올것이다.
					 //                           반대로 무엇인가를 검색한다면 (기존 검색 ) AND (medi_code <AND/OR> 상세조건1 <AND/OR> 상세조건2 ); --> 이것이 문제임. medi_code OR 이면 뭐든지 나올 확률이 있어서 검색오류.
					 // 14시 추가! 문제 될게 없음. 왜냐면, "(기존 검색) AND (상세검색)" 이기때문에  -->  "(기존 검색)" 안에 medi_code가 무조건 존재하니깐, 이 medi_code에 의해서 걸러진 기존검색을!! -->  상세조건에서 한번더  or medi_code 라든지 and medi_code라든지 하는거기 때문에 의미가없어짐. 
					
					
					
           ////////////////////medi_code: 영화 AND/OR 연산자 관련 변수 초기화 시작//////////////////////////////////////////////////////////////////
					//1. 상세검색 조건내에 선택한  연산자에 따라서 and를 할지 or을 할지 정하는 로직. 
					String encodeTotal = "";
					String encodeTotalChk = (String) paramMap.get("detailSearch_radio");
					
					if(encodeTotalChk != null){
						if(encodeTotalChk.equals("allword")){
							encodeTotal = encodeAnd;
						} else if(encodeTotalChk.equals("anyword")){
							encodeTotal = encodeOr;
						} 
					} else {
						encodeTotal = encodeAnd;
					}
					
					//2. 상세검색 조건문에 들어오면 AND연산자 해주고,  괄호도  열어주기. (상세검색 조건문 끝날떄쯤 괄호 닫아줄 것)
					if(encodeTotal.equals(encodeAnd)){
						searchCol += encodeAnd + encodeLGual + medi_code;
						searchCol_dire_name += encodeAnd + encodeLGual + medi_code;
						searchCol_prodc_name += encodeAnd + encodeLGual + medi_code;
						searchCol_rcv_no_view += encodeAnd + encodeLGual + medi_code;
						searchCol_sub_no += encodeAnd + encodeLGual + medi_code;
						searchCol_onlin_offlin += encodeAnd + encodeLGual + medi_code;
						searchCol_medi_name += encodeAnd + encodeLGual + medi_code;
						searchCol_prodc_natnl_name += encodeAnd + encodeLGual + medi_code;
						searchCol_mv_asso_name += encodeAnd + encodeLGual + medi_code;
						searchCol_leada_name += encodeAnd + encodeLGual + medi_code;
						searchCol_frgnr_natnl_name += encodeAnd + encodeLGual + medi_code;
						searchCol_kind_name += encodeAnd + encodeLGual + medi_code;
						searchCol_hope_grade_name += encodeAnd + encodeLGual + medi_code;
						searchCol_prod_year += encodeAnd + encodeLGual + medi_code;
						searchCol_pfm_place_name += encodeAnd + encodeLGual + medi_code;
						searchCol_repr_nm += encodeAnd + encodeLGual + medi_code;
						searchCol_aplc_name += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_no += encodeAnd + encodeLGual + medi_code;
						searchCol_grade_name += encodeAnd + encodeLGual + medi_code;
						searchCol_deter_opin_name += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name1 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name2 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name3 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name4 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name5 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name6 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name7 += encodeAnd + encodeLGual + medi_code;
						searchCol_minor_malef_yn += encodeAnd + encodeLGual + medi_code;
						searchCol_wdra_yn += encodeAnd + encodeLGual + medi_code;
						searchCol_return_name += encodeAnd + encodeLGual + medi_code;
						searchCol_proc_state_code += encodeAnd + encodeLGual + medi_code;
						searchCol_state_code += encodeAnd + encodeLGual + medi_code;
						searchCol_non_com_yn += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_core_harm_rsn_code1 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_core_harm_rsn_code2 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_core_harm_rsn_code3 += encodeAnd + encodeLGual + medi_code;
 
						// 텍스트타입
						searchCol_ori_title += encodeAnd + encodeLGual + medi_code;
						searchCol_use_title += encodeAnd + encodeLGual + medi_code;
						searchCol_work_cont += encodeAnd + encodeLGual + medi_code;
						searchCol_deter_rsn += encodeAnd + encodeLGual + medi_code;
						searchCol_major_opin_narrn_cont += encodeAnd + encodeLGual + medi_code;
						searchCol_core_harm_rsn += encodeAnd + encodeLGual + medi_code;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_sub_rcv_date += encodeAnd + encodeLGual + medi_code;
							searchCol_rcv_date += encodeAnd + encodeLGual + medi_code;
							searchCol_rt_date += encodeAnd + encodeLGual + medi_code;
							searchCol_pfm_pnum += encodeAnd + encodeLGual + medi_code;
							searchCol_contr_start_date += encodeAnd + encodeLGual + medi_code;
							searchCol_contr_end_date += encodeAnd + encodeLGual + medi_code;
							searchCol_ori_rt_no += encodeAnd + encodeLGual + medi_code;
						}

					} else if(encodeTotal.equals(encodeOr)){
						//만약 OR이라면 medi_code가 영향을 주니, 더미데이터를 넣어준다. 해당 구문은 medi_code가 존재하지 않는 더미데이터를 넣는것이다.
						searchCol += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_dire_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_prodc_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rcv_no_view += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_sub_no += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_onlin_offlin += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_medi_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_prodc_natnl_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_mv_asso_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_leada_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_frgnr_natnl_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_kind_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_hope_grade_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_prod_year += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_pfm_place_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_repr_nm += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_aplc_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_no += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_grade_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_deter_opin_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name1 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name2 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name3 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name4 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name5 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name6 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name7 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_minor_malef_yn += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_wdra_yn += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_return_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_proc_state_code += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_state_code += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_non_com_yn += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_core_harm_rsn_code1 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_core_harm_rsn_code2 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_core_harm_rsn_code3 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
 
						// 텍스트타입
						searchCol_ori_title += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_use_title += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_work_cont += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_deter_rsn += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_major_opin_narrn_cont += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_core_harm_rsn += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_sub_rcv_date += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
							searchCol_rcv_date += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
							searchCol_rt_date += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
							searchCol_pfm_pnum += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
							searchCol_contr_start_date += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
							searchCol_contr_end_date += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
							searchCol_ori_rt_no += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						}
					}
		   ////////////////////medi_code: 영화 AND/OR 연산자 관련 변수 초기화 끝//////////////////////////////////////////////////////////////////		
					
					// 일자보다 신청회사 먼저 붙여준다.
					if (!(detailAplc.equals("") || detailAplc == null)) {

						detailAplc = URLEncoder.encode(detailAplc, "UTF-8");
						//encodeAns + strMark + encodeSearchStr + strMark + searchOperation
						// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
						searchCol += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_dire_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_prodc_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rcv_no_view += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_sub_no += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_onlin_offlin += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_medi_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_prodc_natnl_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_mv_asso_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_leada_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_frgnr_natnl_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_kind_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_hope_grade_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_prod_year += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_pfm_place_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_repr_nm += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_aplc_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_no += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_grade_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_deter_opin_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name1 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name2 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name3 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name4 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name5 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name6 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name7 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_minor_malef_yn += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_wdra_yn += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_return_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_proc_state_code += encodeTotal + encodeLGual + "aplc_name" +encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_state_code += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_non_com_yn += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_core_harm_rsn_code1 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_core_harm_rsn_code2 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_core_harm_rsn_code3 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
 
						// 텍스트타입
						searchCol_ori_title += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_use_title += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_work_cont += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_deter_rsn += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_major_opin_narrn_cont += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_core_harm_rsn += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_sub_rcv_date += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_rcv_date += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_rt_date += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_pfm_pnum += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_contr_start_date += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_contr_end_date += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_ori_rt_no += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
						}

					}

					String detail_date = "";
					if (!period_start.equals("") && !period_end.equals("")) {
						if (detail_dateGubun.equals("regdate")) {
							detail_date = encodeTotal + encodeLGual + "sub_rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;// 신청일자
						} else if (detail_dateGubun.equals("jubsudate")) {
							detail_date = encodeTotal + encodeLGual + "rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 접수일자
						} else {
							detail_date = encodeTotal + encodeLGual + "rt_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 등급분류일자
						}

						// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
						searchCol += detail_date;
						searchCol_dire_name += detail_date;
						searchCol_prodc_name += detail_date;
						searchCol_rcv_no_view += detail_date;
						searchCol_sub_no += detail_date;
						searchCol_onlin_offlin += detail_date;
						searchCol_medi_name += detail_date;
						searchCol_prodc_natnl_name += detail_date;
						searchCol_mv_asso_name += detail_date;
						searchCol_leada_name += detail_date;
						searchCol_frgnr_natnl_name += detail_date;
						searchCol_kind_name += detail_date;
						searchCol_hope_grade_name += detail_date;
						searchCol_prod_year += detail_date;
						searchCol_pfm_place_name += detail_date;
						searchCol_repr_nm += detail_date;
						searchCol_aplc_name += detail_date;
						searchCol_rt_no += detail_date;
						searchCol_grade_name += detail_date;
						searchCol_deter_opin_name += detail_date;
						searchCol_rt_std_name1 += detail_date;
						searchCol_rt_std_name2 += detail_date;
						searchCol_rt_std_name3 += detail_date;
						searchCol_rt_std_name4 += detail_date;
						searchCol_rt_std_name5 += detail_date;
						searchCol_rt_std_name6 += detail_date;
						searchCol_rt_std_name7 += detail_date;
						searchCol_minor_malef_yn += detail_date;
						searchCol_wdra_yn += detail_date;
						searchCol_return_name += detail_date;
						searchCol_proc_state_code += detail_date;
						searchCol_state_code += detail_date;
						searchCol_non_com_yn += detail_date;
						searchCol_rt_core_harm_rsn_code1 += detail_date;
						searchCol_rt_core_harm_rsn_code2 += detail_date;
						searchCol_rt_core_harm_rsn_code3 += detail_date;

						// 텍스트타입
						searchCol_ori_title += detail_date;
						searchCol_use_title += detail_date;
						searchCol_work_cont += detail_date;
						searchCol_deter_rsn += detail_date;
						searchCol_major_opin_narrn_cont += detail_date;
						searchCol_core_harm_rsn += detail_date;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_sub_rcv_date += detail_date;
							searchCol_rcv_date += detail_date;
							searchCol_rt_date += detail_date;
							searchCol_pfm_pnum += detail_date;
							searchCol_contr_start_date += detail_date;
							searchCol_contr_end_date += detail_date;
							searchCol_ori_rt_no += detail_date;
						}
					} else {
						
						//날짜가 '전체'라면 빈값으로 오기때문에 더미데이터로 medi_code를 넣어준다. 
						// 연산자가 AND라면 medi_code가 날짜의 '전체' 기능을 할 것이고,
						// 연산자가 OR이더라도 medi_code가 날짜의 '전체' 기능을 할것이다. --> 정보 : 처음에 무조건 더미데이터(medi_code~) 가 존재하기때문에 encodeTotal로 시작해도 무방함.
						searchCol += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_dire_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_prodc_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rcv_no_view += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_sub_no += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_onlin_offlin += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_medi_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_prodc_natnl_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_mv_asso_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_leada_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_frgnr_natnl_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_kind_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_hope_grade_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_prod_year += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_pfm_place_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_repr_nm += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_aplc_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_no += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_grade_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_deter_opin_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name1 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name2 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name3 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name4 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name5 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name6 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name7 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_minor_malef_yn += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_wdra_yn += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_return_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_proc_state_code += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_state_code += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_non_com_yn += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_core_harm_rsn_code1 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_core_harm_rsn_code2 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_core_harm_rsn_code3 += encodeTotal + encodeLGual + medi_code + encodeRGual;
 
						// 텍스트타입
						searchCol_ori_title += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_use_title += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_work_cont += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_deter_rsn += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_major_opin_narrn_cont += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_core_harm_rsn += encodeTotal + encodeLGual + medi_code + encodeRGual;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_sub_rcv_date += encodeTotal + encodeLGual + medi_code + encodeRGual;
							searchCol_rcv_date += encodeTotal + encodeLGual + medi_code + encodeRGual;
							searchCol_rt_date += encodeTotal + encodeLGual + medi_code + encodeRGual;
							searchCol_pfm_pnum += encodeTotal + encodeLGual + medi_code + encodeRGual;
							searchCol_contr_start_date += encodeTotal + encodeLGual + medi_code + encodeRGual;
							searchCol_contr_end_date += encodeTotal + encodeLGual + medi_code + encodeRGual;
							searchCol_ori_rt_no += encodeTotal + encodeLGual + medi_code + encodeRGual;
						}
					}

					if (((String) paramMap.get("detail_kindChk")).equals("movie01_video01")) {

						String totalDetailChk = "";
						String detailMovieVideo = "";
						String encodeMovieVideo = "";
						String tmp_movie_video = "";

						////////////////////////////// 결정등급(grade_name)////////////////////////////////////////////
						if (paramMap.get("detailSearch_grade_name") != null) {
							/*
							 * String detailGradeName = ""; String
							 * encodeGradeName = ""; String tmp_grade_name = "";
							 */
							if (paramMap.get("detailSearch_grade_name").equals("12old")) {
								tmp_movie_video = "12세이상관람가";
							} else if (paramMap.get("detailSearch_grade_name").equals("15old")) {
								tmp_movie_video = "15세이상관람가";
							} else if (paramMap.get("detailSearch_grade_name").equals("18old")) {
								tmp_movie_video = "청소년관람불가";
							} else if (paramMap.get("detailSearch_grade_name").equals("old")) {
								tmp_movie_video = "제한상영가";
							} else {
								tmp_movie_video = "전체관람가";
							}
							encodeMovieVideo = URLEncoder.encode(tmp_movie_video, "UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "grade_name" + encodeAns + strMark
									+ encodeMovieVideo + strMark + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						//////////////////////////// 결정등급////////////////////////////////////////////

						//////////////////////////// 내용정보표시항목
						//////////////////////////// (rt_core_harm_rsn_code1)////////////////////////////
						// code : 17701 주제 17702 선정성 17703 폭력성 17704 대사 17705 공포
						//////////////////////////// 17706 악몽 17707 모방위험

						if (paramMap.get("detailSearch_rt_std_name") != null) {
							/*
							 * String detailRtStd = ""; String encodeRtStd = "";
							 * String tmp_rt_std = "";
							 */
							// 주제
							if (paramMap.get("detailSearch_rt_std_name").equals("17701")) {
								tmp_movie_video = "17701";
							} else if (paramMap.get("detailSearch_rt_std_name").equals("17702")) {
								tmp_movie_video = "17702";
							} else if (paramMap.get("detailSearch_rt_std_name").equals("17703")) {
								tmp_movie_video = "17703";
							} else if (paramMap.get("detailSearch_rt_std_name").equals("17704")) {
								tmp_movie_video = "17704";
							} else if (paramMap.get("detailSearch_rt_std_name").equals("17705")) {
								tmp_movie_video = "17705";
							} else if (paramMap.get("detailSearch_rt_std_name").equals("17706")) {
								tmp_movie_video = "17706";
							} else if (paramMap.get("detailSearch_rt_std_name").equals("17707")) {
								tmp_movie_video = "17707";
							}
							encodeMovieVideo = URLEncoder.encode(tmp_movie_video, "UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "rt_core_harm_rsn_code1" + encodeAns + strMark
									+ encodeMovieVideo + strMark + encodeOr + "rt_core_harm_rsn_code2" + encodeAns
									+ strMark + encodeMovieVideo + strMark + encodeOr + "rt_core_harm_rsn_code3"
									+ encodeAns + strMark + encodeMovieVideo + strMark + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						//////////////////////////// 내용정보표시항목
						//////////////////////////// (rt_core_harm_rsn_code1)////////////////////////////

						//////////////////////////// 매체명////////////////////////////
						/* 매체명이 input type이 text였을때 로직 --> 아래 처럼 select box로 변경됨
						if (paramMap.get("detailSearch_medi_name_movieAndVideo") != null) {
							encodeMovieVideo = URLEncoder
									.encode((String) paramMap.get("detailSearch_medi_name_movieAndVideo"), "UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "medi_name" + encodeLike + strMark + "*"
									+ encodeMovieVideo + "*" + strMark + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						*/
						//영화 매체
						if (paramMap.get("detailSearch_medi_name_movieAndVideo") != null
								&& !((String) paramMap.get("detailSearch_medi_name_movieAndVideo")).equals("")) {
							if (paramMap.get("detailSearch_medi_name_movieAndVideo").equals("in")) {
								tmp_movie_video = "국내";
							} else if (paramMap.get("detailSearch_medi_name_movieAndVideo").equals("out")) {
								tmp_movie_video = "국외";
							}
							encodeMovieVideo = URLEncoder.encode(tmp_movie_video, "UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "medi_name" + encodeLike + strMark + "*"
									+ encodeMovieVideo + "*" + strMark + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						
						//////////////////////////// 매체명////////////////////////////

						//////////////////////////// 제작사
						//////////////////////////// 국적////////////////////////////
						
						/*
						 * 기존 input type="text"형태에서 select box로 바뀜에따라 , 로직 변경
						if (paramMap.get("detailSearch_prodc_natnl_name_movieAndVideo") != null) {
							encodeMovieVideo = URLEncoder.encode(
									(String) paramMap.get("detailSearch_prodc_natnl_name_movieAndVideo"), "UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "prodc_natnl_name" + encodeLike + strMark + "*"
									+ encodeMovieVideo + "*" + strMark + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						*/
						
						
						//영화 매체
						if (paramMap.get("detailSearch_prodc_natnl_name_movieAndVideo") != null
								&& !((String) paramMap.get("detailSearch_prodc_natnl_name_movieAndVideo")).equals("")) {
							tmp_movie_video = (String) paramMap.get("detailSearch_prodc_natnl_name_movieAndVideo");
							encodeMovieVideo = URLEncoder.encode(tmp_movie_video, "UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "prodc_natnl_name" + encodeAns + strMark
									+ encodeMovieVideo + strMark + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						
						
						
						//////////////////////////// 제작사
						//////////////////////////// 국적////////////////////////////

						//////////////////////////// 감독명////////////////////////////
						if (paramMap.get("detailSearch_dire_name_movieAndVideo") != null) {
							encodeMovieVideo = URLEncoder
									.encode((String) paramMap.get("detailSearch_dire_name_movieAndVideo"), "UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "dire_name" + encodeLike + strMark + "*"
									+ encodeMovieVideo + "*" + strMark + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						//////////////////////////// 감독명////////////////////////////

						//////////////////////////// 영화종류////////////////////////////
						/*
						if (paramMap.get("detailSearch_mv_asso_name_movieAndVideo") != null) {
							encodeMovieVideo = URLEncoder
									.encode((String) paramMap.get("detailSearch_mv_asso_name_movieAndVideo"), "UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "mv_asso_name" + encodeLike + strMark + "*"
									+ encodeMovieVideo + "*" + strMark + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						*/
						if (paramMap.get("detailSearch_mv_asso_name_movieAndVideo") != null
								&& !((String) paramMap.get("detailSearch_mv_asso_name_movieAndVideo")).equals("")) {
							tmp_movie_video = (String) paramMap.get("detailSearch_mv_asso_name_movieAndVideo");
							encodeMovieVideo = URLEncoder.encode(tmp_movie_video, "UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "mv_asso_name" + encodeAns + strMark
									+ encodeMovieVideo + strMark + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						//////////////////////////// 영화종류////////////////////////////

						//////////////////////////// 주연명////////////////////////////
						if (paramMap.get("detailSearch_leada_name") != null) {
							encodeMovieVideo = URLEncoder.encode((String) paramMap.get("detailSearch_leada_name"),
									"UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "leada_name" + encodeAns + strMark + encodeMovieVideo + strMark + searchOperation +  encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						//////////////////////////// 주연명////////////////////////////

						//////////////////////////// 희망등급////////////////////////////
						if (paramMap.get("detailSearch_hope_grade_name") != null
								&& !((String) paramMap.get("detailSearch_hope_grade_name")).equals("")) {
							if (paramMap.get("detailSearch_hope_grade_name").equals("12old")) {
								tmp_movie_video = "12세이상관람가";
							} else if (paramMap.get("detailSearch_hope_grade_name").equals("15old")) {
								tmp_movie_video = "15세이상관람가";
							} else if (paramMap.get("detailSearch_hope_grade_name").equals("18old")) {
								tmp_movie_video = "청소년관람불가";
							} else {
								tmp_movie_video = "전체관람가";
							}
							encodeMovieVideo = URLEncoder.encode(tmp_movie_video, "UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "hope_grade_name" + encodeAns + strMark
									+ encodeMovieVideo + strMark + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						//////////////////////////// 희망등급////////////////////////////

						//////////////////////////// 결정의견////////////////////////////
						if (paramMap.get("detailSearch_deter_opin_name") != null) {
							encodeMovieVideo = URLEncoder.encode((String) paramMap.get("detailSearch_deter_opin_name"),
									"UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "deter_opin_name" + encodeAns + strMark
									+ encodeMovieVideo + strMark + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						//////////////////////////// 결정의견////////////////////////////

						//////////////////////////// 작품내용(줄거리)////////////////////////////
						if (paramMap.get("detailSearch_work_cont") != null) {
							encodeMovieVideo = URLEncoder.encode((String) paramMap.get("detailSearch_work_cont"),
									"UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "work_cont" + encodeAns + strMark
									+ encodeMovieVideo + strMark + searchOperation + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						//////////////////////////// 작품내용(줄거리)////////////////////////////

						//////////////////////////// 결정사유////////////////////////////
						if (paramMap.get("detailSearch_deter_rsn_movieAndVideo") != null) {
							encodeMovieVideo = URLEncoder
									.encode((String) paramMap.get("detailSearch_deter_rsn_movieAndVideo"), "UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "deter_rsn" + encodeAns + strMark
									+ encodeMovieVideo + strMark + searchOperation + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						//////////////////////////// 결정사유////////////////////////////

						////////////////////////////등급분류번호////////////////////////////
						if (paramMap.get("detailSearch_rt_no_movieAndVideo") != null) {
							encodeMovieVideo = URLEncoder.encode((String) paramMap.get("detailSearch_rt_no_movieAndVideo"), "UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "rt_no" + encodeAns + strMark 
									+ encodeMovieVideo + strMark + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						/////////////////////////////등급분류번호////////////////////////////
						
						
						
						
						
						if (!totalDetailChk.equals("")) {
							// 이것이 빈값이 아닐떄만 뒤에 조건들을 붙여주자!
							searchCol += totalDetailChk;
							searchCol_dire_name += totalDetailChk;
							searchCol_prodc_name += totalDetailChk;
							searchCol_rcv_no_view += totalDetailChk;
							searchCol_sub_no += totalDetailChk;
							searchCol_onlin_offlin += totalDetailChk;
							searchCol_medi_name += totalDetailChk;
							searchCol_prodc_natnl_name += totalDetailChk;
							searchCol_mv_asso_name += totalDetailChk;
							searchCol_leada_name += totalDetailChk;
							searchCol_frgnr_natnl_name += totalDetailChk;
							searchCol_kind_name += totalDetailChk;
							searchCol_hope_grade_name += totalDetailChk;
							searchCol_prod_year += totalDetailChk;
							searchCol_pfm_place_name += totalDetailChk;
							searchCol_repr_nm += totalDetailChk;
							searchCol_aplc_name += totalDetailChk;
							searchCol_rt_no += totalDetailChk;
							searchCol_grade_name += totalDetailChk;
							searchCol_deter_opin_name += totalDetailChk;
							searchCol_rt_std_name1 += totalDetailChk;
							searchCol_rt_std_name2 += totalDetailChk;
							searchCol_rt_std_name3 += totalDetailChk;
							searchCol_rt_std_name4 += totalDetailChk;
							searchCol_rt_std_name5 += totalDetailChk;
							searchCol_rt_std_name6 += totalDetailChk;
							searchCol_rt_std_name7 += totalDetailChk;
							searchCol_minor_malef_yn += totalDetailChk;
							searchCol_wdra_yn += totalDetailChk;
							searchCol_return_name += totalDetailChk;
							searchCol_proc_state_code += totalDetailChk;
							searchCol_state_code += totalDetailChk;
							searchCol_non_com_yn += totalDetailChk;
							searchCol_rt_core_harm_rsn_code1 += totalDetailChk;
							searchCol_rt_core_harm_rsn_code2 += totalDetailChk;
							searchCol_rt_core_harm_rsn_code3 += totalDetailChk;

							// 텍스트타입
							searchCol_ori_title += totalDetailChk;
							searchCol_use_title += totalDetailChk;
							searchCol_work_cont += totalDetailChk;
							searchCol_deter_rsn += totalDetailChk;
							searchCol_major_opin_narrn_cont += totalDetailChk;
							searchCol_core_harm_rsn += totalDetailChk;

							// 숫자타입
							if (searchStrInt != 0) {
								searchCol_sub_rcv_date += totalDetailChk;
								searchCol_rcv_date += totalDetailChk;
								searchCol_rt_date += totalDetailChk;
								searchCol_pfm_pnum += totalDetailChk;
								searchCol_contr_start_date += totalDetailChk;
								searchCol_contr_end_date += totalDetailChk;
								searchCol_ori_rt_no += totalDetailChk;
							}
						}

					}

					
					// 상세검색 조건절 마무리 괄호
					searchCol += encodeRGual;
					searchCol_dire_name += encodeRGual;
					searchCol_prodc_name += encodeRGual;
					searchCol_rcv_no_view += encodeRGual;
					searchCol_sub_no += encodeRGual;
					searchCol_onlin_offlin += encodeRGual;
					searchCol_medi_name += encodeRGual;
					searchCol_prodc_natnl_name += encodeRGual;
					searchCol_mv_asso_name += encodeRGual;
					searchCol_leada_name += encodeRGual;
					searchCol_frgnr_natnl_name += encodeRGual;
					searchCol_kind_name += encodeRGual;
					searchCol_hope_grade_name += encodeRGual;
					searchCol_prod_year += encodeRGual;
					searchCol_pfm_place_name += encodeRGual;
					searchCol_repr_nm += encodeRGual;
					searchCol_aplc_name += encodeRGual;
					searchCol_rt_no += encodeRGual;
					searchCol_grade_name += encodeRGual;
					searchCol_deter_opin_name += encodeRGual;
					searchCol_rt_std_name1 += encodeRGual;
					searchCol_rt_std_name2 += encodeRGual;
					searchCol_rt_std_name3 += encodeRGual;
					searchCol_rt_std_name4 += encodeRGual;
					searchCol_rt_std_name5 += encodeRGual;
					searchCol_rt_std_name6 += encodeRGual;
					searchCol_rt_std_name7 += encodeRGual;
					searchCol_minor_malef_yn += encodeRGual;
					searchCol_wdra_yn += encodeRGual;
					searchCol_return_name += encodeRGual;
					searchCol_proc_state_code += encodeRGual;
					searchCol_state_code += encodeRGual;
					searchCol_non_com_yn += encodeRGual;
					searchCol_rt_core_harm_rsn_code1 += encodeRGual;
					searchCol_rt_core_harm_rsn_code2 += encodeRGual;
					searchCol_rt_core_harm_rsn_code3 += encodeRGual;

					// 텍스트타입
					searchCol_ori_title += encodeRGual;
					searchCol_use_title += encodeRGual;
					searchCol_work_cont += encodeRGual;
					searchCol_deter_rsn += encodeRGual;
					searchCol_major_opin_narrn_cont += encodeRGual;
					searchCol_core_harm_rsn += encodeRGual;

					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_sub_rcv_date += encodeRGual;
						searchCol_rcv_date += encodeRGual;
						searchCol_rt_date += encodeRGual;
						searchCol_pfm_pnum += encodeRGual;
						searchCol_contr_start_date += encodeRGual;
						searchCol_contr_end_date += encodeRGual;
						searchCol_ori_rt_no += encodeRGual;
					}
				}
				//detailVisibleChk 조건 끝
				
				
				//20230328 만약 따옴표로 검색이 들어온다면 ? 시작!
				if(!quote_searchStr.equals("")) {
				  /*
					searchCol += quote_searchCol;
					searchCol_dire_name += quote_searchCol_dire_name;
					searchCol_prodc_name += quote_searchCol_prodc_name;
					searchCol_rcv_no_view += quote_searchCol_rcv_no_view;
					searchCol_sub_no += quote_searchCol_sub_no;
					searchCol_onlin_offlin += quote_searchCol_onlin_offlin;
					searchCol_medi_name += quote_searchCol_medi_name;
					searchCol_prodc_natnl_name += quote_searchCol_prodc_natnl_name;
					searchCol_mv_asso_name += quote_searchCol_mv_asso_name;
					searchCol_leada_name += quote_searchCol_leada_name;
					searchCol_frgnr_natnl_name += quote_searchCol_frgnr_natnl_name;
					searchCol_kind_name += quote_searchCol_kind_name;
					searchCol_hope_grade_name += quote_searchCol_hope_grade_name;
					searchCol_prod_year += quote_searchCol_prod_year;
					searchCol_pfm_place_name += quote_searchCol_pfm_place_name;
					searchCol_repr_nm += quote_searchCol_repr_nm;
					searchCol_aplc_name += quote_searchCol_aplc_name;
					searchCol_rt_no += quote_searchCol_rt_no;
					searchCol_grade_name += quote_searchCol_grade_name;
					searchCol_deter_opin_name += quote_searchCol_deter_opin_name;
					searchCol_rt_std_name1 += quote_searchCol_rt_std_name1;
					searchCol_rt_std_name2 += quote_searchCol_rt_std_name2;
					searchCol_rt_std_name3 += quote_searchCol_rt_std_name3;
					searchCol_rt_std_name4 += quote_searchCol_rt_std_name4;
					searchCol_rt_std_name5 += quote_searchCol_rt_std_name5;
					searchCol_rt_std_name6 += quote_searchCol_rt_std_name6;
					searchCol_rt_std_name7 += quote_searchCol_rt_std_name7;
					searchCol_minor_malef_yn += quote_searchCol_minor_malef_yn;
					searchCol_wdra_yn += quote_searchCol_wdra_yn;
					searchCol_return_name += quote_searchCol_return_name;
					searchCol_proc_state_code += quote_searchCol_proc_state_code;
					searchCol_state_code += quote_searchCol_state_code;
					searchCol_non_com_yn += quote_searchCol_non_com_yn;
					searchCol_rt_core_harm_rsn_code1 += quote_searchCol_rt_core_harm_rsn_code1;
					searchCol_rt_core_harm_rsn_code2 += quote_searchCol_rt_core_harm_rsn_code2;
					searchCol_rt_core_harm_rsn_code3 += quote_searchCol_rt_core_harm_rsn_code3;

					// 텍스트타입
					searchCol_ori_title += quote_searchCol_ori_title;
					searchCol_use_title += quote_searchCol_use_title;
					searchCol_work_cont += quote_searchCol_work_cont;
					searchCol_deter_rsn += quote_searchCol_deter_rsn;
					searchCol_major_opin_narrn_cont += quote_searchCol_major_opin_narrn_cont;
					searchCol_core_harm_rsn += quote_searchCol_core_harm_rsn;

					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_sub_rcv_date += quote_searchCol_sub_rcv_date;
						searchCol_rcv_date += quote_searchCol_sub_rcv_date;
						searchCol_rt_date += quote_searchCol_rt_date;
						searchCol_pfm_pnum += quote_searchCol_pfm_pnum;
						searchCol_contr_start_date += quote_searchCol_contr_start_date;
						searchCol_contr_end_date += quote_searchCol_contr_end_date;
						searchCol_ori_rt_no += quote_searchCol_ori_rt_no;
					}
				*/
					
					//20230329 메뉴명에도 그냥 동일하게 quote_searchCol 붙여주기 
					searchCol += quote_searchCol;
					searchCol_dire_name += quote_searchCol;
					searchCol_prodc_name += quote_searchCol;
					searchCol_rcv_no_view += quote_searchCol;
					searchCol_sub_no += quote_searchCol;
					searchCol_onlin_offlin += quote_searchCol;
					searchCol_medi_name += quote_searchCol;
					searchCol_prodc_natnl_name += quote_searchCol;
					searchCol_mv_asso_name += quote_searchCol;
					searchCol_leada_name += quote_searchCol;
					searchCol_frgnr_natnl_name += quote_searchCol;
					searchCol_kind_name += quote_searchCol;
					searchCol_hope_grade_name += quote_searchCol;
					searchCol_prod_year += quote_searchCol;
					searchCol_pfm_place_name += quote_searchCol;
					searchCol_repr_nm += quote_searchCol;
					searchCol_aplc_name += quote_searchCol;
					searchCol_rt_no += quote_searchCol;
					searchCol_grade_name += quote_searchCol;
					searchCol_deter_opin_name += quote_searchCol;
					searchCol_rt_std_name1 += quote_searchCol;
					searchCol_rt_std_name2 += quote_searchCol;
					searchCol_rt_std_name3 += quote_searchCol;
					searchCol_rt_std_name4 += quote_searchCol;
					searchCol_rt_std_name5 += quote_searchCol;
					searchCol_rt_std_name6 += quote_searchCol;
					searchCol_rt_std_name7 += quote_searchCol;
					searchCol_minor_malef_yn += quote_searchCol;
					searchCol_wdra_yn += quote_searchCol;
					searchCol_return_name += quote_searchCol;
					searchCol_proc_state_code += quote_searchCol;
					searchCol_state_code += quote_searchCol;
					searchCol_non_com_yn += quote_searchCol;
					searchCol_rt_core_harm_rsn_code1 += quote_searchCol;
					searchCol_rt_core_harm_rsn_code2 += quote_searchCol;
					searchCol_rt_core_harm_rsn_code3 += quote_searchCol;

					// 텍스트타입
					searchCol_ori_title += quote_searchCol;
					searchCol_use_title += quote_searchCol;
					searchCol_work_cont += quote_searchCol;
					searchCol_deter_rsn += quote_searchCol;
					searchCol_major_opin_narrn_cont += quote_searchCol;
					searchCol_core_harm_rsn += quote_searchCol;

					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_sub_rcv_date += quote_searchCol;
						searchCol_rcv_date += quote_searchCol;
						searchCol_rt_date += quote_searchCol;
						searchCol_pfm_pnum += quote_searchCol;
						searchCol_contr_start_date += quote_searchCol;
						searchCol_contr_end_date += quote_searchCol;
						searchCol_ori_rt_no += quote_searchCol;
					}
					
				
				}
				//20230328 만약 따옴표로 검색이 들어온다면 ? 끝!
				

				/****************
				 * 20221220 상세검색의 일자, 신청회사 통합작업중
				 ***********************/

				/**************
				 * ■■■■■■■■■■■■■*************************************우측사이드에 관한
				 * information 시작
				 * *************************************************■■■■■■■■■
				 **************************************************/
				List<Map<String, Object>> movie01_sideInformation = new ArrayList<Map<String, Object>>();

				if (searchGubun.equals("All")) {
					// 0. (조건에 맞는)원제명 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_ori_title, "원제명"));

					// 0. (조건에 맞는)사용제명 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_use_title, "사용제명"));

					// 1. (조건에 맞는)감독명에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_dire_name, "감독명"));

					// 2. (조건에 맞는)제작사명에 대한 건수 및 데이터 조회 // walker에 정보 담기
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_prodc_name, "제작사명"));

					// 3. (조건에 맞는)접수번호에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rcv_no_view, "접수번호"));

					// 4. (조건에 맞는)신청번호에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_sub_no, "신청번호"));

					// 5. (조건에 맞는)온라인/오프라인에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_onlin_offlin, "온라인/오프라인"));

					// 6. (조건에 맞는)메체명에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_medi_name, "매체명"));

					// 7. (조건에 맞는)제작자 국적에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_prodc_natnl_name, "제작자 국적"));

					// 8. (조건에 맞는)영화 종류에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_mv_asso_name, "영화 종류"));

					// 9. (조건에 맞는)주연명 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_leada_name, "주연명"));

					// 10. (조건에 맞는)외국인 국적에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_frgnr_natnl_name, "외국인 국적"));

					// 11. (조건에 맞는)종류에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_kind_name, "종류"));

					// 12. (조건에 맞는)희망등급에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_hope_grade_name, "희망등급"));

					// 13. (조건에 맞는)제작년도에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_prod_year, "제작년도"));

					// 14. (조건에 맞는)공연 장소명에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_pfm_place_name, "공연 장소명"));

					// 15. (조건에 맞는)대표자 성명에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_repr_nm, "대표자 성명"));

					// 16. (조건에 맞는)신청회사에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_aplc_name, "신청회사"));

					// 17. (조건에 맞는)등급분류번호에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_no, "등급분류번호"));

					// 18. (조건에 맞는)결정등급에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_grade_name, "결정등급"));

					// 19. (조건에 맞는)결정의견에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_deter_opin_name, "결정의견"));

					// 20. (조건에 맞는)주제에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name1, "주제"));

					// 21. (조건에 맞는)선정성에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name2, "선정성"));

					// 22. (조건에 맞는)폭력성에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name3, "폭력성"));

					// 23. (조건에 맞는)대사에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name4, "대사"));

					// 24. (조건에 맞는)공포에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name5, "공포"));

					// 25. (조건에 맞는)약물에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name6, "약물"));

					// 26. (조건에 맞는)모방위험에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name7, "모방위험"));

					// 27. (조건에 맞는)연소자 유해성 여부에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_minor_malef_yn, "연소자 유해성"));

					// 28. (조건에 맞는)취하여부에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_wdra_yn, "취하여부"));

					// 29. (조건에 맞는)취하,반납에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_return_name, "취하,반납"));

					// 30. (조건에 맞는)진행 상태 코드에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_proc_state_code, "진행 상태 코드"));

					// 31. (조건에 맞는)상태코드에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_state_code, "상태코드"));

					// 32. (조건에 맞는)불완전데이터 플래그에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_non_com_yn, "불완전데이터 플래그"));

					// 33. (조건에 맞는)내용정보표시항목1에 대한 건수 및 데이터 조회
					movie01_sideInformation
							.add(mediurlEncodingResult(walker, paramMap, request, response, result_noticeUrl, encodeAnd,
									orderValue, medi_code, searchCol_rt_core_harm_rsn_code1, "내용정보표시항목1"));

					// 34. (조건에 맞는)내용정보표시항목2에 대한 건수 및 데이터 조회
					movie01_sideInformation
							.add(mediurlEncodingResult(walker, paramMap, request, response, result_noticeUrl, encodeAnd,
									orderValue, medi_code, searchCol_rt_core_harm_rsn_code2, "내용정보표시항목2"));

					// 35. (조건에 맞는)내용정보표시항목3에 대한 건수 및 데이터 조회
					movie01_sideInformation
							.add(mediurlEncodingResult(walker, paramMap, request, response, result_noticeUrl, encodeAnd,
									orderValue, medi_code, searchCol_rt_core_harm_rsn_code3, "내용정보표시항목3"));

					/**************** 텍스트 데이터 시작!!! ******************/
					// 36. (조건에 맞는)작품내용(줄거리) 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_work_cont, "작품내용(줄거리)"));

					// 37. (조건에 맞는)결정사유에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_deter_rsn, "결정사유"));

					// 38. (조건에 맞는)주요의견에 대한 건수 및 데이터 조회
					movie01_sideInformation
							.add(mediurlEncodingResult(walker, paramMap, request, response, result_noticeUrl, encodeAnd,
									orderValue, medi_code, searchCol_major_opin_narrn_cont, "주요의견"));

					// 39. (조건에 맞는)핵심유해사유에 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_core_harm_rsn, "핵심유해사유"));
					/**************** 텍스트 데이터 끝!!! ******************/

					/**************** 숫자 데이터 시작 (null처리)!!! ******************/
					if (searchStrInt != 0) {
						// 40. (조건에 맞는)신청일자에 대한 건수 및 데이터 조회
						movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
								result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_sub_rcv_date, "신청일자"));

						// 41. (조건에 맞는)접수일자에 대한 건수 및 데이터 조회
						movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
								result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rcv_date, "접수일자"));

						// 42. (조건에 맞는)등급분류일에 대한 건수 및 데이터 조회
						movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
								result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_date, "등급분류일"));

						// 43. (조건에 맞는)외국인 인원수에 대한 건수 및 데이터 조회
						movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
								result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_pfm_pnum, "외국인 인원수"));

						// 44. (조건에 맞는)계약 시작 일자에 대한 건수 및 데이터 조회
						movie01_sideInformation
								.add(mediurlEncodingResult(walker, paramMap, request, response, result_noticeUrl,
										encodeAnd, orderValue, medi_code, searchCol_contr_start_date, "계약 시작 일자"));

						// 45. (조건에 맞는)계약 종료 일자에 대한 건수 및 데이터 조회
						movie01_sideInformation
								.add(mediurlEncodingResult(walker, paramMap, request, response, result_noticeUrl,
										encodeAnd, orderValue, medi_code, searchCol_contr_end_date, "계약 종료 일자"));

						// 46. (조건에 맞는)신규추천번호에 대한 건수 및 데이터 조회
						movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
								result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_ori_rt_no, "신규추천번호"));
					}
					/**************** 숫자 데이터 끝 (null처리)!!! ******************/

					// 2022 12 27 리스트정렬(사용자정의)
					Collections.sort(movie01_sideInformation, new sideMenuCntComparator());
					// 2022 12 27 리스트정렬(사용자정의)
				} // if(searchGubun.equals("All")) 끝
				if (searchGubun.equals("title")) {
					// (조건에 맞는)원제명 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol, "원제명/사용제명"));
				}
				if (searchGubun.equals("decision")) {
					// (조건에 맞는)결정사유 대한 건수 및 데이터 조회
					movie01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol, "결정사유"));
				}

				walker.setValue("movie01_sideInformation", movie01_sideInformation);

				/**************
				 * ■■■■■■■■■■■■■***************************************우측사이드에 관한
				 * information
				 * 끝***********************************************■■■■■■■■■
				 **************************************************/

				/****************
				 * 검색어 처리 끝
				 **********************************************************************/

				totalUrl = result_noticeUrl + medi_code + encodeAnd + searchCol
						+ orderValue;

				// 20230112 결과내재검색 movie01()메소드 안 시작
				if (((String) paramMap.get("result_re_search_chk")).equals("Y")
						&& (String) paramMap.get("movie01_historySearchQuery") != null) {
					totalUrl = (String) paramMap.get("movie01_historySearchQuery") + encodeAnd + "(" + medi_code
							+ encodeAnd + searchCol + ")" + orderValue;
				}
				// 20230112 결과내재검색 movie01()메소드 안 끝

				// 이전쿼리 저장 시작 -> paramMap.put을 이용하기 떄문에, paramMap.get을 이용해서
				// 이전쿼리를 가져오는 위 로직보다는 뒤에 위치하여야한다.
				String historySearchQuery = "";
				historySearchQuery = result_noticeUrl + "(" + medi_code + encodeAnd + searchCol + ")";
				// 만약 결과내재검색을 통한 검색이었다면, historySearchQuery(이전쿼리)는 , 이전쿼리 +
				// encodeAnd + 현재쿼리 일 것이다.
				if (((String) paramMap.get("result_re_search_chk")).equals("Y")
						&& (String) paramMap.get("movie01_historySearchQuery") != null) {
					historySearchQuery = (String) paramMap.get("movie01_historySearchQuery") + encodeAnd + "("
							+ medi_code + encodeAnd + searchCol + ")";
				}
				paramMap.put("movie01_historySearchQuery", historySearchQuery);

				// 20230119 주석System.out.println("20230112
				// 1번컨트롤러___결과내재검색___movie01()__이전쿼리 담아놓기 : " +
				// historySearchQuery);
				// 20230119 주석System.out.println("20230112
				// 1번컨트롤러___결과내재검색___movie01()__최종 totalUrl은? : " + totalUrl);
				// 이전쿼리 저장 끝

			} else {
				// 검색어가 공백이라면? // 통합검색이든, 제목이든 내용이든 // 영화매체에 관한 모든 데이터가 등장해줘야함

				totalUrl = result_noticeUrl + medi_code + orderValue;

			}

			// ---------------------------------------------모든 조건을 받아내는 곳이라서 여기에
			// 카운트 로직을 위치시킴. // 2022 12 16 페이징 처리 시작 // 페이징 int 위에 정의
			// 페이징을 먼저 처리한다. --> 카운트는 전체결과수랑 다르게 나올것이다 (total_count가 아님)
			// 1216 18시 이후 메모: 페이징 처리후 핵심은 데이터를 무엇을 가져오냐? 이것이 핵심 --> 페이징 연산 처리를
			// 잘해줘야한다는 의미. --> 일단 10개, 20개 보여주기는 완성된 상태니, pagelength는 건들지말자.
			// 실질적인 데이터를 가져오는 페이징로직임 (여기에 집중해야함)
			/*****************************************************
			 * 페이징 처리 시작
			 ***************************************************************************************/
			String paging_totalUrl = totalUrl + "&pagelength=" + detailresultLine + "&offset=" + offset + "&limit="
					+ detailresultLine + "&hilite-fields="
					+ "%7B%22use_title%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22ori_title%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22aplc_name%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22rt_no%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22grade_name%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22rt_no%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22work_cont%22%3A%7B%22length%22%3A3000%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22deter_rsn%22%3A%7B%22length%22%3A3000%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D";
			List<Object> dataList = pagingURLEncodingFunc(paging_totalUrl);
			movie01.put("total_count_page", (Integer) dataList.get(0));
			walker.setValue("movie01_resultMapList", (List<Map<String, String>>) dataList.get(1));
			// 20230119 주석System.out.println("영화에서 들고온 페이징처리 시 얻어낸
			// paging_totalUrl : " + (Integer)dataList.get(0));
			// 20230119 주석System.out.println("영화에서 들고온 페이징처리 시 데이터 갯수
			// total_count_page :" + (List<Map<String,String>>)dataList.get(1));
			/*****************************************************
			 * 페이징 처리 끝
			 ***************************************************************************************/

			/*********************
			 * ********************* ********************* *********************
			 * ********************* ********************* *********************
			 * ********************* / //1216 18시 퇴근이후 코멘트(읽어보기) : 기존에는
			 * 빨리빨리하다보니깐, 이 페이징처리 로직을 아래로 못뻈었는데(검색어(searchStr)이 존재할떄만) 사용 가능
			 * 했었는데, 상세보기 변수들을 맨 위로 뺴니 이렇게 분리가 가능함 // 1219 월요일에 와서 맨 위에 상세보기 변수
			 * null처리 제대로해줄것! (모든 매체가 공유해도 되는부분임) 다만 신청회사처럼 조건절에 들어가는건 안에서 따로
			 * 나눠줄수밖에없다. /********************* *********************
			 * ********************* ********************* *********************
			 * ********************* ********************* *********************
			 */

			
			
			// totalURLCntFunc로 총합 갯수만 가져오고있다. -> 데이터가 필요하다면 해당 메소드를 안에
			// resultMap을 확인하면 됨.
			Integer total_count = totalURLCntFunc(totalUrl);
			movie01.put("total_count", total_count);
			walker.setValue("movie01", movie01);
			
			// 찬우 확인2
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println(searchStr);
			System.out.println(dataList);
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@");
		}

		///////////////////////////////////////////// 영화 끝 (17501,
		///////////////////////////////////////////// 17502)///////////////////////////////////////////////////////////////////////////////////////////////////
	}

	public void video01(Walker walker, Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// null처리는 필수!
		// 파라미터 목록 가져오기. * 검색어, 검색구분, 섹션구분
		String searchStr = (String) paramMap.get("searchStr");
		String searchGubun = (String) paramMap.get("searchGubun");
		String[] sectionGubun = null;
		if (request.getParameterValues("sectionGubun") == null) {
			sectionGubun = new String[] { "Total" };
		} else {
			sectionGubun = request.getParameterValues("sectionGubun");
		}
		List<String> sectionGubunList = new ArrayList<String>(Arrays.asList(sectionGubun));

		/*************** 검색어에 숫자가 들어올떄 *******************************/
		long searchStrInt = 0;
		if (paramMap.get("searchStr") != null && ((String) paramMap.get("searchStr")).matches("[-+]?\\d*\\.?\\d+")) {
			searchStrInt = Long.valueOf((String) paramMap.get("searchStr"));
		}
		/*************** 검색어에 숫자가 들어올떄 *******************************/
		// 최종 URL
		String totalUrl = "";
		// 공통 URL
		String result_noticeUrl = "http://192.168.11.242:7577/search5?select=*&from=result_notice.result_notice&where=";
		String result_opinUrl = "http://192.168.11.242:7577/search5?select=*&from=result_opin.result_opin&where=";
		String result_fileUrl = "http://192.168.11.242:7577/search5?select=*&from=result_file.result_file&where=";
		String result_imageUrl = "http://192.168.11.242:7577/search5?select=*&from=result_image.result_image&where=";
		String result_moniUrl = "http://192.168.11.242:7577/search5?select=*&from=result_moni.result_moni&where=";
		String result_surveyUrl = "http://192.168.11.242:7577/search5?select=*&from=result_survey.result_survey&where=";
		// 공통 인코딩 목록
		String encode = "UTF-8";
		String encodeSearchStr = URLEncoder.encode(searchStr, encode); // 검색어
		String encodeSearchGubun = URLEncoder.encode(searchGubun, encode); // 검색구분(통합검색,
																			// 제목,
																			// 내용,
																			// 결정사유
																			// 등등)
		String encodeAnd = URLEncoder.encode(" and ", encode);
		String encodeOr = URLEncoder.encode(" or ", encode);
		String encodeAns = URLEncoder.encode("=", encode);
		String encodeInOpen = URLEncoder.encode(" in{", encode);
		String encodeInClose = URLEncoder.encode("}", encode);
		String encodeLGual = "(";
		String encodeRGual = ")";
		String encodeLike = URLEncoder.encode(" like ", encode);
		String strMark = URLEncoder.encode("'", encode);
		String between = URLEncoder.encode(" between ", encode);

		// where절의 text타입에만 사용가능.
		String allword = URLEncoder.encode(" allword ", encode);
		String anyword = URLEncoder.encode(" anyword ", encode);
		String natural = URLEncoder.encode(" natural ", encode);

		// 동의어 검색
		String synonym = URLEncoder.encode(" synonym ", encode);

		// 카테고리 정렬 ex) ORDER BY $CATEGORYFIELD(field_name(domain), keyword)
		String categoryOrder = URLEncoder.encode(" $categoryfield(use_title(), 검색어넣기 ", encode);

		/**
		 * 17501 : 국내영화 17502 : 국외영화 17505 : 광고영화 17511 : 국내비디오 17512 : 국외비디오
		 */

		// medi_code 인코딩 목록
		String medi_mv = URLEncoder.encode("'17501','17502'", encode);
		String medi_ad = URLEncoder.encode("'17503','17504','17505','17506','17514'", encode);
		String medi_video = URLEncoder.encode("'17511','17512'", encode);
		String medi_perform = URLEncoder.encode("'17521','17522','17523','17524'", encode);

		/*
		 * //첫 로딩 시 데이터를 표시해주는 역할을 함. if(searchGubun == null ){ searchGubun =
		 * "All"; } else if(searchGubun.trim().isEmpty()){ searchGubun = "All";
		 * }
		 */

		// 부가설명 : 처음 orderValue로 JSP에서 넘어온 정렬값을 받아서, 넘어온 정렬값을 기준으로 url인코딩을 실시한다.
		String orderValue = (String) request.getParameter("orderValue");
		if (orderValue == null || orderValue.equals("")) {
			orderValue = URLEncoder.encode(" order by $match(rt_no) desc, $relevance desc, $rowid desc ", encode);
		}
		if (orderValue.equals("orderRelevance")) {
			orderValue = URLEncoder.encode(" order by $match(rt_no) desc, $relevance desc, $rowid desc ", encode);
		}
		if (orderValue.equals("orderGa")) {
			orderValue = "%20order%20by%20use_title_sort%20asc%20%2c%20ori_title_sort%20asc%20";
		}
		if (orderValue.equals("orderDate")) {
			orderValue = "%20order%20by%20rcv_date%20desc%20";
		}

		//
		String groupByTest = URLEncoder.encode(" order by $relevance desc ", encode);

		// 2022 12 15 페이징 처리 시작

		// 1. sectionGubun을 하나만 선택하거나, -더보기 클릭 시, sectionGubun에는 딱 하나의 데이터가
		// 들어있고, 페이지는 1페이지로 이동한다. -->sectionGubun이 딱 하나라면 (Total)이 아닐떄 , 페이징목록이
		// 보이게끔 유도하기.
		// 페이지는 페이지 목록 버튼 시 마다 2페이지 3페이지 이동한다... 그러다가 다시 검색버튼 같은것을 누르면 1페이지로
		// 초기화!

		// 기존 display가 limit로 대체

		int cPage = 0;
		int offset = 0;
		int pagelength = 0;

		/**************************
		 * 20221223 페이징처리를 위한 새로운 로직 (영화, 비디오 반영완료)
		 *********************************************/
		if (((String) paramMap.get("isPaging")).equals("Y")) {
			cPage = (Integer) paramMap.get("cPage");
			offset = (Integer) paramMap.get("offset");
			pagelength = (Integer) paramMap.get("pagelength");
			offset = (cPage - 1) * pagelength;
		} else {
			cPage = 1;
			offset = 0;
			pagelength = 10;
			offset = (cPage - 1) * pagelength;
		}

		/*******************************************************************/

		/**************** 상세보기 시작 ********************************/
		// 20221216 상세보기에 관련된 것들 맨 위로 빼기 (기본값이 존재함)
		// 하지만 상세보기를 하지 않는다면 이 값이 null이라서 오류가뜸 ~~~~~~~~~~~~~~~~~~~~~~~~~~~

		// 상세보기를 클릭했는지 여부
		String detail_visibleChk = (String) paramMap.get("detail_visibleChk");

		// 연산자 가져오기
		String searchOperation = "";

		// 결과 수
		int detailresultLine = 3;
		try {
			if ((String) paramMap.get("detailSearch_resultLine") != null) {
				detailresultLine = Integer.valueOf((String) paramMap.get("detailSearch_resultLine"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (detailresultLine != 3 && (String) paramMap.get("detailSearch_resultLine") != null) {
				detailresultLine = detailresultLine;
			} else {
				detailresultLine = 3;
			}
		}

		try {
			if (detail_visibleChk.equals("Y")) {
				searchOperation = (String) paramMap.get("detailSearch_radio");
			} else {
				searchOperation = "natural";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_radio") == null) {
				searchOperation = "natural";
			}
		}

		if (searchOperation.equals("allword")) {
			searchOperation = natural;
		} else if (searchOperation.equals("anyword")) {
			searchOperation = natural;
		} else if (searchOperation.equals("natural")) {
			searchOperation = natural;
		} else {
			searchOperation = natural;
		}
		
		if(searchStr.indexOf("-") != -1){
			searchOperation = allword;
		}

		// 신청회사 null 처리
		String detailAplc = "";
		try {
			if ((String) paramMap.get("detailSearch_aplc") != null) {
				detailAplc = (String) paramMap.get("detailSearch_aplc");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_aplc") == null) {
				detailAplc = "";
			}
		}

		// 상세보기-일자 데이터 가져오기 및 null 처리 시작

		// 시작날짜
		String period_start = "";
		try {
			if ((String) paramMap.get("period_start") != null) {
				period_start = (String) paramMap.get("period_start");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("period_start") == null) {
				period_start = "";
			}
		}

		// 종료날짜
		String period_end = "";
		try {
			if ((String) paramMap.get("period_end") != null) {
				period_end = (String) paramMap.get("period_end");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("period_end") == null) {
				period_end = "";
			}
		}

		// 등급분류일자, 신청일자 , 접수일자 구분
		String detail_dateGubun = "";
		try {
			if ((String) paramMap.get("detailSearch_date") != null) {
				detail_dateGubun = (String) paramMap.get("detailSearch_date");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_date") == null) {
				detail_dateGubun = "gradedate";
			}
		}

		if (!period_start.equals("")) {
			period_start = period_start.substring(0, 4) + period_start.substring(5, 7) + period_start.substring(8, 10)
					+ "000000";
		}
		if (!period_end.equals("")) {
			period_end = period_end.substring(0, 4) + period_end.substring(5, 7) + period_end.substring(8, 10)
					+ "000000";
		}

		// 듈 중에 하나를 선택안하고 상세검색을 하면 --> 전체날짜로 인식하여 데이터검색하기
		if ((period_start.equals("") || period_end.equals("")) && !(period_start.equals("") && period_end.equals(""))) {
			period_start = "";
			period_end = "";

		}
		// 상세보기-일자 데이터 가져오기 및 null 처리 끝

		/**************** 상세보기 끝 ********************************/

		// 하지만 상세보기를 하지 않는다면 이 값이 null이라서 오류가뜸 ~~~~~~~~~~~~~~~~~~~~~~~~~~~

		
		
		//따옴표 기능 추가 시작

				//따옴표기능 변수1 : 반드시 포함해야 하는 따옴표 안의 단어.
				String quote_searchStr_origin = "";
				//따옴표기능 변수2 : 반드시 포함해야 하는 따옴표 안의 단어 (인코딩 받아줄 변수).
				String quote_searchStr = ""; 
				
				

				//따옴표기능 로직1 : 따옴표의 인덱스 위치를 추출한다.
				String word = "\""; 
				int searchStrIndex = searchStr.indexOf(word);
				List<Integer> searchStrIndexList = new ArrayList<Integer>();
				 while(searchStrIndex != -1) {
					 searchStrIndexList.add(searchStrIndex);
					 searchStrIndex = searchStr.indexOf(word, searchStrIndex+word.length()); 
				 }
				
				 
				 
				//따옴표기능 로직2 : 따옴표의 인덱스가 2개이상 나왔다면, 따옴표가 4개있든 7개 있든, 제일 첫번째로 나오는 따옴표 2개의 쌍을 구하고, 그 따옴표로 깜사져 있는 단어를 추출한다.
				if(searchStrIndexList.size() >= 2 ) {
					 int firstIndex = searchStrIndexList.get(0);
					 int lastIndex = searchStrIndexList.get(1);
					 quote_searchStr_origin = searchStr.substring(firstIndex + 1, lastIndex);  
					 quote_searchStr = URLEncoder.encode(quote_searchStr_origin, encode);
				 }
				
				 
				 
				 
				 //따옴표기능 로직3 : 따옴표의 인덱스가 일단 하나 이상 등장한다면, 한개 이상일 경우는 따옴표를 제거해주고, 따옴표가 2개이상인 경우에는 위에서 얻어온 '따옴표 안'의 단어를 따로 추출하여 인코딩한다.
				             // --> 차후에 따옴표 검색이 이뤄질때, 헤어질 결심 "수사관" 검색 시, --> (헤어질 결심 ) AND ("수사관") 이런식으로 검색 하게끔 해줌.
				 if(searchStrIndexList.size() >= 1 ){
					 
					String searchStr2 = searchStr.replaceAll("\"", "");
					String searchStr3 = searchStr2.replaceAll( quote_searchStr_origin , "").trim(); 
					if(!searchStr3.equals("") && searchStr3 != null){
						encodeSearchStr = URLEncoder.encode(searchStr3, encode); // 검색어
					}
				 }
				 
				 
				 
				 // 따옴표에 들어간 단어에 맞춰서 조건을 추가해주기 위해서 셋팅하는 작업 
				 String quote_searchCol = "";
				 if(!quote_searchStr.equals("")) {
		    /***********************************************video0101() quote_searchStr start*************************************************************************/	 
				 quote_searchCol += encodeAnd + "(" + "use_title" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" +  "synonym('d0')" 
								+ encodeOr + "ori_title" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" +  "synonym('d0')" 
							    + encodeOr + "dire_name" + encodeAns + strMark + quote_searchStr + strMark // 감독명
								+ encodeOr + "prodc_name" + encodeAns + strMark + quote_searchStr + strMark // 제작사명
								+ encodeOr + "rcv_no_view" + encodeAns + strMark + quote_searchStr + strMark + // no3
																												// 접수번호
																												// rcv_no_view
																												// |string
																												// like
																												// X
								encodeOr + "sub_no" + encodeAns + strMark + quote_searchStr + strMark + // no4
																										// 신청번호
																										// sub_no
																										// |string
																										// like
																										// X
								encodeOr + "onlin_offlin" + encodeAns + strMark + quote_searchStr + strMark +  // no7
																															// 온라인/오프라인
																															// |string
								encodeOr + "medi_name" + encodeAns + strMark + quote_searchStr + strMark +  // no9
																														// 매체명
																														// |string
								encodeOr + "prodc_natnl_name" + encodeAns + strMark + quote_searchStr + strMark
								+ // no15 제작자 국적 |string
								encodeOr + "mv_asso_name" + encodeAns + strMark + quote_searchStr + strMark +  // no23
																															// 영화
																															// 종류
																															// |string
								encodeOr + "leada_name" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" +   // no25
																															// 주연명
																															// |string
								encodeOr + "frgnr_natnl_name" + encodeAns + strMark + quote_searchStr + strMark
								+ // no29 외국인 국적 |string
								encodeOr + "kind_name" + encodeAns + strMark + quote_searchStr + strMark + // no35
																														// 종류
																														// |string
								encodeOr + "hope_grade_name" + encodeAns + strMark + quote_searchStr + strMark
								+ // no41 희망등급 |string
								encodeOr + "prod_year" + encodeAns + strMark + quote_searchStr + strMark + // no42
																											// 제작년도
																											// |string
																											// like
																											// X
								encodeOr + "pfm_place_name" + encodeAns + strMark + quote_searchStr + strMark + // no46
																																// 공연
																																// 장소명
																																// |string
								encodeOr + "repr_nm" + encodeAns + strMark + quote_searchStr + strMark + // no61
																														// 대표자
																														// 성명
																														// |string
								encodeOr + "aplc_name" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + // no63
																														// 신청회사
																														// |string
								encodeOr + "rt_no" + encodeAns + strMark + quote_searchStr + strMark + // no69
																										// 등급분류번호
																										// |string
																										// like
																										// X
								encodeOr + "grade_name" + encodeAns + strMark + quote_searchStr + strMark + // no71
																															// 결정등급
																															// |string
								encodeOr + "deter_opin_name" + encodeAns + strMark + quote_searchStr + strMark
								+ // no72 결정의견 |string
								encodeOr + "rt_std_name1" + encodeAns + strMark + quote_searchStr + strMark + // no73
																															// 주제
																															// |string
								encodeOr + "rt_std_name2" + encodeAns + strMark + quote_searchStr + strMark + // no74
																															// 선정성
																															// |string
								encodeOr + "rt_std_name3" + encodeAns + strMark + quote_searchStr + strMark + // no75
																															// 폭력성
																															// |string
								encodeOr + "rt_std_name4" + encodeAns + strMark + quote_searchStr + strMark + // no76
																															// 대사
																															// |string
								encodeOr + "rt_std_name5" + encodeAns + strMark + quote_searchStr + strMark + // no77
																															// 공포
																															// |string
								encodeOr + "rt_std_name6" + encodeAns + strMark + quote_searchStr + strMark + // no78
																															// 약물
																															// |string
								encodeOr + "rt_std_name7" + encodeAns + strMark + quote_searchStr + strMark + // no79
																															// 모방위험
																															// |string
								encodeOr + "minor_malef_yn" + encodeAns + strMark + quote_searchStr + strMark + // no80
																												// 연소자
																												// 유해성
																												// 여부
																												// |string
																												// like
																												// X
								encodeOr + "wdra_yn" + encodeAns + strMark + quote_searchStr + strMark + // no86
																											// 취하여부
																											// |string
																											// like
																											// X
								encodeOr + "return_name" + encodeAns + strMark + quote_searchStr + strMark + // no90
																												// 취하,
																												// 반납
																												// |string
																												// like
																												// X
								encodeOr + "proc_state_code" + encodeAns + strMark + quote_searchStr + strMark + // no97
																													// 진행
																													// 상태
																													// 코드
																													// |string
																													// like
																													// X
								encodeOr + "state_code" + encodeAns + strMark + quote_searchStr + strMark + // no98
																											// 상태코드
																											// |string
																											// like
																											// X
								encodeOr + "non_com_yn" + encodeAns + strMark + quote_searchStr + strMark + // no109
																															// 불완전데이터
																															// 플래그
																															// |string
								encodeOr + "rt_core_harm_rsn_code1" + encodeAns + strMark + quote_searchStr + strMark + // no114 내용정보표시항목1 |string
								encodeOr + "rt_core_harm_rsn_code2" + encodeAns + strMark + quote_searchStr + strMark + // no114 내용정보표시항목2 |string
								encodeOr + "rt_core_harm_rsn_code3" + encodeAns + strMark + quote_searchStr + strMark + // no114 내용정보표시항목3 |string
								encodeOr + "work_cont"             + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + // no81// 작품내용(줄거리) // |text
								encodeOr + "deter_rsn"             + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" +  // no83 // 결정사유 // |text
								encodeOr + "major_opin_narrn_cont" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + // no84 주요의견 |text
								encodeOr + "core_harm_rsn"         + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+"; // no85 핵심유해사유 |text
						// int는 encode를 따로 안해줘도 무방함.
						if (searchStrInt != 0) {
							quote_searchCol += encodeOr + "sub_rcv_date" + encodeAns + searchStrInt + "000000" + // no5
																											// 신청일자
																											// |
																											// int
									encodeOr + "rcv_date" + encodeAns + searchStrInt + "000000" + // no6
																									// 접수일자
																									// |
																									// int
									encodeOr + "rt_date" + encodeAns + searchStrInt + "000000" + // no70
																									// 등급분류일
																									// |
																									// int
									encodeOr + "pfm_pnum" + encodeAns + searchStrInt + // no28
																						// 외국인
																						// 인원수
																						// |
																						// int
									encodeOr + "contr_start_date" + encodeAns + searchStrInt + "000000" + // no44
																											// 계약
																											// 시작
																											// 일자
																											// |
																											// int
									encodeOr + "contr_end_date" + encodeAns + searchStrInt + "000000" + // no45
																										// 계약
																										// 종료
																										// 일자
																										// |
																										// int
									encodeOr + "ori_rt_no" + encodeAns + searchStrInt; // no59
																						// 신규추천번호
																						// |
																						// int
						}
						// 통합검색의 마무리 괄호
						quote_searchCol += ")";
						//quote_searchCol만 셋팅 하고, 왜 다른 사이드는 셋팅 하지 않는지?
						//위에 보다시피 quote_searchCol 이 모든 컬럼에 "따옴표에 들어간 단어"를 매칭시켜서 가져오고있다 
						// 나중에 제일 밑에 detail_visibleChk를 쓰는 로직 밑에 보면, 통합검색이든 사이드메뉴든 뒤에 quote_searchCol를 붙여주는 작업을 하고 있다.
				 }
				 /***********************************************video01() quote_searchStr start*************************************************************************/
				 // 따옴표 기능 추가 끝
		
		
		
		///////////////////////////////////////////// 비디오 시작 (17511,
		///////////////////////////////////////////// 17512)///////////////////////////////////////////////////////////////////////////////////////////////////
		if (sectionGubunList.contains("video01") || sectionGubunList.contains("Total")) {
			Map<String, Object> video01 = new HashMap<String, Object>();
			String medi_code = "medi_code" + encodeInOpen + medi_video + encodeInClose;

			if (searchStr != null) {
				/*************************************************************
				 * 검색어 처리 시작
				 *************************************************************************************************/

				// 검색구분은 if절로서 쓰이고, 검색어는 where절 안에 쓰임으로써, 직접적인 쿼리에 영향을 줌.
				String searchCol = ""; // where절을 담을 변수
				String searchCol_ori_title = "";
				String searchCol_use_title = "";
				String searchCol_dire_name = "";
				String searchCol_prodc_name = "";
				String searchCol_rcv_no_view = "";
				String searchCol_sub_no = "";
				String searchCol_onlin_offlin = "";
				String searchCol_medi_name = "";
				String searchCol_prodc_natnl_name = "";
				String searchCol_mv_asso_name = "";
				String searchCol_leada_name = "";
				String searchCol_frgnr_natnl_name = "";
				String searchCol_kind_name = "";
				String searchCol_hope_grade_name = "";
				String searchCol_prod_year = "";
				String searchCol_pfm_place_name = "";
				String searchCol_repr_nm = "";
				String searchCol_aplc_name = "";
				String searchCol_rt_no = "";
				String searchCol_grade_name = "";
				String searchCol_deter_opin_name = "";
				String searchCol_rt_std_name1 = "";
				String searchCol_rt_std_name2 = "";
				String searchCol_rt_std_name3 = "";
				String searchCol_rt_std_name4 = "";
				String searchCol_rt_std_name5 = "";
				String searchCol_rt_std_name6 = "";
				String searchCol_rt_std_name7 = "";
				String searchCol_minor_malef_yn = "";
				String searchCol_wdra_yn = "";
				String searchCol_return_name = "";
				String searchCol_proc_state_code = "";
				String searchCol_state_code = "";
				String searchCol_non_com_yn = "";
				String searchCol_rt_core_harm_rsn_code1 = "";
				String searchCol_rt_core_harm_rsn_code2 = "";
				String searchCol_rt_core_harm_rsn_code3 = "";
				String searchCol_work_cont = "";
				String searchCol_deter_rsn = "";
				String searchCol_major_opin_narrn_cont = "";
				String searchCol_core_harm_rsn = "";
				String searchCol_sub_rcv_date = "";
				String searchCol_rcv_date = "";
				String searchCol_rt_date = "";
				String searchCol_pfm_pnum = "";
				String searchCol_contr_start_date = "";
				String searchCol_contr_end_date = "";
				String searchCol_ori_rt_no = "";

				// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
				if (searchGubun.equals("All")) {
					searchCol += "(" + "use_title" + encodeAns + strMark + encodeSearchStr + strMark + "+allword+"	+ "synonym('d0')" + 
						    encodeOr + "use_title" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + "synonym('d0')" + 
							encodeOr + "ori_title" + encodeAns + strMark
							+ encodeSearchStr + strMark + searchOperation + "synonym('d0')" + encodeOr + "dire_name"
							+ encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // 감독명
							encodeOr + "prodc_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // 제작사명
							encodeOr + "rcv_no_view" + encodeAns + strMark + encodeSearchStr + strMark + // no3
																											// 접수번호
																											// rcv_no_view
																											// |string
																											// like
																											// X
							encodeOr + "sub_no" + encodeAns + strMark + encodeSearchStr + strMark + // no4
																									// 신청번호
																									// sub_no
																									// |string
																									// like
																									// X
							encodeOr + "onlin_offlin" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no7
																														// 온라인/오프라인
																														// |string
							encodeOr + "medi_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no9
																													// 매체명
																													// |string
							encodeOr + "prodc_natnl_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no15 제작자 국적 |string
							encodeOr + "mv_asso_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no23
																														// 영화
																														// 종류
																														// |string
							encodeOr + "leada_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + // no25
																														// 주연명
																														// |string
							encodeOr + "frgnr_natnl_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no29 외국인 국적 |string
							encodeOr + "kind_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no35
																													// 종류
																													// |string
							encodeOr + "hope_grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no41 희망등급 |string
							encodeOr + "prod_year" + encodeAns + strMark + encodeSearchStr + strMark + // no42
																										// 제작년도
																										// |string
																										// like
																										// X
							encodeOr + "pfm_place_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no46
																															// 공연
																															// 장소명
																															// |string
							encodeOr + "repr_nm" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no61
																													// 대표자
																													// 성명
																													// |string
							encodeOr + "aplc_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + // no63
																													// 신청회사
																													// |string
							encodeOr + "rt_no" + encodeAns + strMark + encodeSearchStr + strMark + // no69
																									// 등급분류번호
																									// |string
																									// like
																									// X
							encodeOr + "grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no71
																														// 결정등급
																														// |string
							encodeOr + "deter_opin_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no72 결정의견 |string
							encodeOr + "rt_std_name1" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no73
																														// 주제
																														// |string
							encodeOr + "rt_std_name2" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no74
																														// 선정성
																														// |string
							encodeOr + "rt_std_name3" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no75
																														// 폭력성
																														// |string
							encodeOr + "rt_std_name4" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no76
																														// 대사
																														// |string
							encodeOr + "rt_std_name5" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no77
																														// 공포
																														// |string
							encodeOr + "rt_std_name6" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no78
																														// 약물
																														// |string
							encodeOr + "rt_std_name7" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no79
																														// 모방위험
																														// |string
							encodeOr + "minor_malef_yn" + encodeAns + strMark + encodeSearchStr + strMark + // no80
																											// 연소자
																											// 유해성
																											// 여부
																											// |string
																											// like
																											// X
							encodeOr + "wdra_yn" + encodeAns + strMark + encodeSearchStr + strMark + // no86
																										// 취하여부
																										// |string
																										// like
																										// X
							encodeOr + "return_name" + encodeAns + strMark + encodeSearchStr + strMark + // no90
																											// 취하,
																											// 반납
																											// |string
																											// like
																											// X
							encodeOr + "proc_state_code" + encodeAns + strMark + encodeSearchStr + strMark + // no97
																												// 진행
																												// 상태
																												// 코드
																												// |string
																												// like
																												// X
							encodeOr + "state_code" + encodeAns + strMark + encodeSearchStr + strMark + // no98
																										// 상태코드
																										// |string
																										// like
																										// X
							encodeOr + "non_com_yn" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no109
																														// 불완전데이터
																														// 플래그
																														// |string
							encodeOr + "rt_core_harm_rsn_code1" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + // no114 내용정보표시항목1 |string
							encodeOr + "rt_core_harm_rsn_code2" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + // no114 내용정보표시항목2 |string
							encodeOr + "rt_core_harm_rsn_code3" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + // no114 내용정보표시항목3 |string
							encodeOr + "work_cont" + encodeAns + strMark + encodeSearchStr + strMark + "+someword+" +// no81
																															// 작품내용(줄거리)
																															// |text
							encodeOr + "deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark + "+someword+" + // no83
																															// 결정사유
																															// |text
							encodeOr + "major_opin_narrn_cont" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+" + // no84 주요의견 |text
							encodeOr + "core_harm_rsn" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+"; // no85 핵심유해사유 |text

					// int는 encode를 따로 안해줘도 무방함.
					if (searchStrInt != 0) {
						searchCol += encodeOr + "sub_rcv_date" + encodeAns + searchStrInt + "000000" + // no5
																										// 신청일자
																										// |
																										// int
								encodeOr + "rcv_date" + encodeAns + searchStrInt + "000000" + // no6
																								// 접수일자
																								// |
																								// int
								encodeOr + "rt_date" + encodeAns + searchStrInt + "000000" + // no70
																								// 등급분류일
																								// |
																								// int
								encodeOr + "pfm_pnum" + encodeAns + searchStrInt + // no28
																					// 외국인
																					// 인원수
																					// |
																					// int
								encodeOr + "contr_start_date" + encodeAns + searchStrInt + "000000" + // no44
																										// 계약
																										// 시작
																										// 일자
																										// |
																										// int
								encodeOr + "contr_end_date" + encodeAns + searchStrInt + "000000" + // no45
																									// 계약
																									// 종료
																									// 일자
																									// |
																									// int
								encodeOr + "ori_rt_no" + encodeAns + searchStrInt; // no59
																					// 신규추천번호
																					// |
																					// int
					}

					// 통합검색의 마무리 괄호
					searchCol += ")";

					// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
					// 0 원제명Url (텍스트지만, 중요도에 따라 맨위에 위치)
					searchCol_ori_title = "(" + "ori_title" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + "synonym('d0')" + ")";

					// 0 사용제명Url (텍스트지만, 중요도에 따라 맨위에 위치)
					searchCol_use_title = "(" + "use_title" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + "synonym('d0')" + ")";
					// 1. 감독명Url
					searchCol_dire_name = "(" + "dire_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 2. 제작사명Url
					searchCol_prodc_name = "(" + "prodc_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";

					// 3. 접수번호Url
					searchCol_rcv_no_view = "(" + "rcv_no_view" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 4. 신청번호Url
					searchCol_sub_no = "(" + "sub_no" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 5. 온라인/오프라인 Url
					searchCol_onlin_offlin = "(" + "onlin_offlin" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 6. 매체명
					searchCol_medi_name = "(" + "medi_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 7. 제작자 국적
					searchCol_prodc_natnl_name = "(" + "prodc_natnl_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 8. 영화 종류
					searchCol_mv_asso_name = "(" + "mv_asso_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 9. 주연명
					searchCol_leada_name = "(" + "leada_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + ")";
					// 10. 외국인 국적
					searchCol_frgnr_natnl_name = "(" + "frgnr_natnl_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 11. 종류
					searchCol_kind_name = "(" + "kind_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 12. 희망등급
					searchCol_hope_grade_name = "(" + "hope_grade_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 13. 제작년도
					searchCol_prod_year = "(" + "prod_year" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 14. 공연 장소명
					searchCol_pfm_place_name = "(" + "pfm_place_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 15. 대표자 성명
					searchCol_repr_nm = "(" + "repr_nm" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ ")";
					// 16. 신청회사
					searchCol_aplc_name = "(" + "aplc_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + ")";
					// 17. 등급분류번호
					searchCol_rt_no = "(" + "rt_no" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 18. 결정등급
					searchCol_grade_name = "(" + "grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 19. 결정의견
					searchCol_deter_opin_name = "(" + "deter_opin_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 20. 주제
					searchCol_rt_std_name1 = "(" + "rt_std_name1" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 21. 선정성
					searchCol_rt_std_name2 = "(" + "rt_std_name2" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 22. 폭력성
					searchCol_rt_std_name3 = "(" + "rt_std_name3" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 23. 대사
					searchCol_rt_std_name4 = "(" + "rt_std_name4" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 24. 공포
					searchCol_rt_std_name5 = "(" + "rt_std_name5" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 25. 약물
					searchCol_rt_std_name6 = "(" + "rt_std_name6" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 26. 모방위험
					searchCol_rt_std_name7 = "(" + "rt_std_name7" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 27. 연소자 유해성 여부
					searchCol_minor_malef_yn = "(" + "minor_malef_yn" + encodeAns + strMark + encodeSearchStr + strMark
							+ ")";
					// 28. 취하여부
					searchCol_wdra_yn = "(" + "wdra_yn" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 29. 취하,반납
					searchCol_return_name = "(" + "return_name" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 30. 진행 상태 코드
					searchCol_proc_state_code = "(" + "proc_state_code" + encodeAns + strMark + encodeSearchStr
							+ strMark + ")";
					// 31. 상태코드
					searchCol_state_code = "(" + "state_code" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 32. 불완전데이터 플래그
					searchCol_non_com_yn = "(" + "non_com_yn" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 33. 내용정보표시항목1
					searchCol_rt_core_harm_rsn_code1 = "(" + "rt_core_harm_rsn_code1" + encodeLike + strMark + "*"
							+ encodeSearchStr + "*" + strMark + ")";
					// 34. 내용정보표시항목2
					searchCol_rt_core_harm_rsn_code2 = "(" + "rt_core_harm_rsn_code2" + encodeLike + strMark + "*"
							+ encodeSearchStr + "*" + strMark + ")";
					// 35. 내용정보표시항목3
					searchCol_rt_core_harm_rsn_code3 = "(" + "rt_core_harm_rsn_code3" + encodeLike + strMark + "*"
							+ encodeSearchStr + "*" + strMark + ")";

					/********* 텍스트 타입 시작! **********/
					// 36. 작품내용(줄거리)
					searchCol_work_cont = "(" + "work_cont" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+" + ")";
					// 37. 결정사유
					searchCol_deter_rsn = "(" + "deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+" + ")";
					// 38. 주요의견
					searchCol_major_opin_narrn_cont = "(" + "major_opin_narrn_cont" + encodeAns + strMark
							+ "+someword+" + strMark + searchOperation + ")";
					// 39. 핵심유해사유
					searchCol_core_harm_rsn = "(" + "core_harm_rsn" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+" + ")";

					/*** 검색어가 숫자인 경우 추가되는 항목!! ***/
					if (searchStrInt != 0) {

						// 40. 신청일자
						searchCol_sub_rcv_date = "(" + "sub_rcv_date" + encodeAns + searchStrInt + "000000" + ")";
						// 41. 접수일자
						searchCol_rcv_date = "(" + "rcv_date" + encodeAns + searchStrInt + "000000" + ")";
						// 42. 등급분류일
						searchCol_rt_date = "(" + "rt_date" + encodeAns + searchStrInt + "000000" + ")";
						// 43. 외국인 인원수
						searchCol_pfm_pnum = "(" + "pfm_pnum" + encodeAns + searchStrInt + ")";
						// 44. 계약 시작 일자
						searchCol_contr_start_date = "(" + "contr_start_date" + encodeAns + searchStrInt + "000000"
								+ ")";
						// 45. 계약 종료 일자
						searchCol_contr_end_date = "(" + "contr_end_date" + encodeAns + searchStrInt + "000000" + ")";
						// 46. 신규추천번호
						searchCol_ori_rt_no = "(" + "ori_rt_no" + encodeAns + searchStrInt + ")";

					}

				}
				if (searchGubun.equals("title")) {
					searchCol += "(use_title" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ "synonym('d0')" + encodeOr + "ori_title" + encodeAns + strMark + encodeSearchStr
							+ strMark + searchOperation + "synonym('d0')" + ")";
				}
				if (searchGubun.equals("decision")) {
					searchCol += "(deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ "synonym('d0')" + encodeOr + "deter_basis" + encodeAns + strMark + encodeSearchStr
							+ strMark + searchOperation + "synonym('d0')" + ")";
				}

				// 20230202 if 검색어가 공백이지만, 상세조건에 값이 있다면? ex) 검색어는 빈값, 주연명 "마동석"
				// searchCol부터.. 전부 다~ medi_code값을 줌 --> 검색어가 빈값일떄 이 방법이 최선.
				if (searchStr.equals("NO_VALUE")) {
					searchCol = medi_code;
					searchCol_dire_name = medi_code;
					searchCol_prodc_name = medi_code;
					searchCol_rcv_no_view = medi_code;
					searchCol_sub_no = medi_code;
					searchCol_onlin_offlin = medi_code;
					searchCol_medi_name = medi_code;
					searchCol_prodc_natnl_name = medi_code;
					searchCol_mv_asso_name = medi_code;
					searchCol_leada_name = medi_code;
					searchCol_frgnr_natnl_name = medi_code;
					searchCol_kind_name = medi_code;
					searchCol_hope_grade_name = medi_code;
					searchCol_prod_year = medi_code;
					searchCol_pfm_place_name = medi_code;
					searchCol_repr_nm = medi_code;
					searchCol_aplc_name = medi_code;
					searchCol_rt_no = medi_code;
					searchCol_grade_name = medi_code;
					searchCol_deter_opin_name = medi_code;
					searchCol_rt_std_name1 = medi_code;
					searchCol_rt_std_name2 = medi_code;
					searchCol_rt_std_name3 = medi_code;
					searchCol_rt_std_name4 = medi_code;
					searchCol_rt_std_name5 = medi_code;
					searchCol_rt_std_name6 = medi_code;
					searchCol_rt_std_name7 = medi_code;
					searchCol_minor_malef_yn = medi_code;
					searchCol_wdra_yn = medi_code;
					searchCol_return_name = medi_code;
					searchCol_proc_state_code = medi_code;
					searchCol_state_code = medi_code;
					searchCol_non_com_yn = medi_code;
					searchCol_rt_core_harm_rsn_code1 = medi_code;
					searchCol_rt_core_harm_rsn_code2 = medi_code;
					searchCol_rt_core_harm_rsn_code3 = medi_code;

					// 텍스트타입
					searchCol_ori_title = medi_code;
					searchCol_use_title = medi_code;
					searchCol_work_cont = medi_code;
					searchCol_deter_rsn = medi_code;
					searchCol_major_opin_narrn_cont = medi_code;
					searchCol_core_harm_rsn = medi_code;

					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_sub_rcv_date = medi_code;
						searchCol_rcv_date = medi_code;
						searchCol_rt_date = medi_code;
						searchCol_pfm_pnum = medi_code;
						searchCol_contr_start_date = medi_code;
						searchCol_contr_end_date = medi_code;
						searchCol_ori_rt_no = medi_code;
					}
				} // if(searchStr == null) 끝.

				// 상세검색 일때만 뒤에 이걸 붙여주자!
				/**************** 일자 통합작업중 ***********************/
				if (detail_visibleChk.equals("Y")) {
					
					

			//////////////////medi_code: 비디오 AND/OR 연산자 관련 변수 초기화 시작//////////////////////////////////////////////////////////////////
					//1. 상세검색 조건내에 선택한  연산자에 따라서 and를 할지 or을 할지 정하는 로직. 
					String encodeTotal = "";
					String encodeTotalChk = (String) paramMap.get("detailSearch_radio");
					
					if(encodeTotalChk != null){
						if(encodeTotalChk.equals("allword")){
							encodeTotal = encodeAnd;
						} else if(encodeTotalChk.equals("anyword")){
							encodeTotal = encodeOr;
						} 
					} else {
						encodeTotal = encodeAnd;
					}
					
					//2. 상세검색 조건문에 들어오면 AND연산자 해주고,  괄호도  열어주기. (상세검색 조건문 끝날떄쯤 괄호 닫아줄 것)
					if(encodeTotal.equals(encodeAnd)){
						searchCol += encodeAnd + encodeLGual + medi_code;
						searchCol_dire_name += encodeAnd + encodeLGual + medi_code;
						searchCol_prodc_name += encodeAnd + encodeLGual + medi_code;
						searchCol_rcv_no_view += encodeAnd + encodeLGual + medi_code;
						searchCol_sub_no += encodeAnd + encodeLGual + medi_code;
						searchCol_onlin_offlin += encodeAnd + encodeLGual + medi_code;
						searchCol_medi_name += encodeAnd + encodeLGual + medi_code;
						searchCol_prodc_natnl_name += encodeAnd + encodeLGual + medi_code;
						searchCol_mv_asso_name += encodeAnd + encodeLGual + medi_code;
						searchCol_leada_name += encodeAnd + encodeLGual + medi_code;
						searchCol_frgnr_natnl_name += encodeAnd + encodeLGual + medi_code;
						searchCol_kind_name += encodeAnd + encodeLGual + medi_code;
						searchCol_hope_grade_name += encodeAnd + encodeLGual + medi_code;
						searchCol_prod_year += encodeAnd + encodeLGual + medi_code;
						searchCol_pfm_place_name += encodeAnd + encodeLGual + medi_code;
						searchCol_repr_nm += encodeAnd + encodeLGual + medi_code;
						searchCol_aplc_name += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_no += encodeAnd + encodeLGual + medi_code;
						searchCol_grade_name += encodeAnd + encodeLGual + medi_code;
						searchCol_deter_opin_name += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name1 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name2 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name3 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name4 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name5 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name6 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name7 += encodeAnd + encodeLGual + medi_code;
						searchCol_minor_malef_yn += encodeAnd + encodeLGual + medi_code;
						searchCol_wdra_yn += encodeAnd + encodeLGual + medi_code;
						searchCol_return_name += encodeAnd + encodeLGual + medi_code;
						searchCol_proc_state_code += encodeAnd + encodeLGual + medi_code;
						searchCol_state_code += encodeAnd + encodeLGual + medi_code;
						searchCol_non_com_yn += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_core_harm_rsn_code1 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_core_harm_rsn_code2 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_core_harm_rsn_code3 += encodeAnd + encodeLGual + medi_code;
 
						// 텍스트타입
						searchCol_ori_title += encodeAnd + encodeLGual + medi_code;
						searchCol_use_title += encodeAnd + encodeLGual + medi_code;
						searchCol_work_cont += encodeAnd + encodeLGual + medi_code;
						searchCol_deter_rsn += encodeAnd + encodeLGual + medi_code;
						searchCol_major_opin_narrn_cont += encodeAnd + encodeLGual + medi_code;
						searchCol_core_harm_rsn += encodeAnd + encodeLGual + medi_code;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_sub_rcv_date += encodeAnd + encodeLGual + medi_code;
							searchCol_rcv_date += encodeAnd + encodeLGual + medi_code;
							searchCol_rt_date += encodeAnd + encodeLGual + medi_code;
							searchCol_pfm_pnum += encodeAnd + encodeLGual + medi_code;
							searchCol_contr_start_date += encodeAnd + encodeLGual + medi_code;
							searchCol_contr_end_date += encodeAnd + encodeLGual + medi_code;
							searchCol_ori_rt_no += encodeAnd + encodeLGual + medi_code;
						}

					} else if(encodeTotal.equals(encodeOr)){
						//만약 OR이라면 medi_code가 영향을 주니, 더미데이터를 넣어준다. 해당 구문은 medi_code가 존재하지 않는 더미데이터를 넣는것이다.
						searchCol += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_dire_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_prodc_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rcv_no_view += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_sub_no += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_onlin_offlin += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_medi_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_prodc_natnl_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_mv_asso_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_leada_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_frgnr_natnl_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_kind_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_hope_grade_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_prod_year += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_pfm_place_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_repr_nm += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_aplc_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_no += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_grade_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_deter_opin_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name1 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name2 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name3 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name4 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name5 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name6 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name7 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_minor_malef_yn += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_wdra_yn += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_return_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_proc_state_code += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_state_code += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_non_com_yn += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_core_harm_rsn_code1 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_core_harm_rsn_code2 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_core_harm_rsn_code3 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
 
						// 텍스트타입
						searchCol_ori_title += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_use_title += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_work_cont += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_deter_rsn += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_major_opin_narrn_cont += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_core_harm_rsn += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_sub_rcv_date += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
							searchCol_rcv_date += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
							searchCol_rt_date += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
							searchCol_pfm_pnum += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
							searchCol_contr_start_date += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
							searchCol_contr_end_date += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
							searchCol_ori_rt_no += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						}
					}
			//////////////////medi_code: 비디오 AND/OR 연산자 관련 변수 초기화 끝//////////////////////////////////////////////////////////////////		
					
					
					// 일자보다 신청회사 먼저 붙여준다.
					if (!(detailAplc.equals("") || detailAplc == null)) {
						detailAplc = URLEncoder.encode(detailAplc, "UTF-8");
						// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
						searchCol += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_dire_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_prodc_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rcv_no_view += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_sub_no += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_onlin_offlin += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_medi_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_prodc_natnl_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_mv_asso_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_leada_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_frgnr_natnl_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_kind_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_hope_grade_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_prod_year += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_pfm_place_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_repr_nm += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_aplc_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_no += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_grade_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_deter_opin_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name1 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name2 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name3 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name4 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name5 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name6 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name7 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_minor_malef_yn += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_wdra_yn += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_return_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_proc_state_code += encodeTotal + encodeLGual + "aplc_name" +encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_state_code += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_non_com_yn += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_core_harm_rsn_code1 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_core_harm_rsn_code2 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_core_harm_rsn_code3 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
 
						// 텍스트타입
						searchCol_ori_title += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_use_title += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_work_cont += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_deter_rsn += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_major_opin_narrn_cont += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_core_harm_rsn += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_sub_rcv_date += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_rcv_date += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_rt_date += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_pfm_pnum += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_contr_start_date += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_contr_end_date += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_ori_rt_no += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
						}

					}

					String detail_date = "";
					if (!period_start.equals("") && !period_end.equals("")) {
						if (detail_dateGubun.equals("regdate")) {
							searchCol += encodeTotal + encodeLGual + "sub_rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 신청일자
							detail_date = encodeTotal + encodeLGual + "sub_rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;
						} else if (detail_dateGubun.equals("jubsudate")) {
							searchCol += encodeTotal + encodeLGual + "rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 접수일자
							detail_date = encodeTotal + encodeLGual + "rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;
						} else {
							searchCol += encodeTotal + encodeLGual + "rt_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 등급분류일자
							detail_date = encodeTotal + encodeLGual + "rt_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;
						}

						// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
						searchCol_dire_name += detail_date;
						searchCol_prodc_name += detail_date;
						searchCol_rcv_no_view += detail_date;
						searchCol_sub_no += detail_date;
						searchCol_onlin_offlin += detail_date;
						searchCol_medi_name += detail_date;
						searchCol_prodc_natnl_name += detail_date;
						searchCol_mv_asso_name += detail_date;
						searchCol_leada_name += detail_date;
						searchCol_frgnr_natnl_name += detail_date;
						searchCol_kind_name += detail_date;
						searchCol_hope_grade_name += detail_date;
						searchCol_prod_year += detail_date;
						searchCol_pfm_place_name += detail_date;
						searchCol_repr_nm += detail_date;
						searchCol_aplc_name += detail_date;
						searchCol_rt_no += detail_date;
						searchCol_grade_name += detail_date;
						searchCol_deter_opin_name += detail_date;
						searchCol_rt_std_name1 += detail_date;
						searchCol_rt_std_name2 += detail_date;
						searchCol_rt_std_name3 += detail_date;
						searchCol_rt_std_name4 += detail_date;
						searchCol_rt_std_name5 += detail_date;
						searchCol_rt_std_name6 += detail_date;
						searchCol_rt_std_name7 += detail_date;
						searchCol_minor_malef_yn += detail_date;
						searchCol_wdra_yn += detail_date;
						searchCol_return_name += detail_date;
						searchCol_proc_state_code += detail_date;
						searchCol_state_code += detail_date;
						searchCol_non_com_yn += detail_date;
						searchCol_rt_core_harm_rsn_code1 += detail_date;
						searchCol_rt_core_harm_rsn_code2 += detail_date;
						searchCol_rt_core_harm_rsn_code3 += detail_date;

						// 텍스트타입
						searchCol_ori_title += detail_date;
						searchCol_use_title += detail_date;
						searchCol_work_cont += detail_date;
						searchCol_deter_rsn += detail_date;
						searchCol_major_opin_narrn_cont += detail_date;
						searchCol_core_harm_rsn += detail_date;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_sub_rcv_date += detail_date;
							searchCol_rcv_date += detail_date;
							searchCol_rt_date += detail_date;
							searchCol_pfm_pnum += detail_date;
							searchCol_contr_start_date += detail_date;
							searchCol_contr_end_date += detail_date;
							searchCol_ori_rt_no += detail_date;
						}
					} else {
						
						//날짜가 '전체'라면 빈값으로 오기때문에 더미데이터로 medi_code를 넣어준다. 
						// 연산자가 AND라면 medi_code가 날짜의 '전체' 기능을 할 것이고,
						// 연산자가 OR이더라도 medi_code가 날짜의 '전체' 기능을 할것이다. --> 정보 : 처음에 무조건 더미데이터(medi_code~) 가 존재하기때문에 encodeTotal로 시작해도 무방함.
						searchCol += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_dire_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_prodc_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rcv_no_view += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_sub_no += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_onlin_offlin += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_medi_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_prodc_natnl_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_mv_asso_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_leada_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_frgnr_natnl_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_kind_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_hope_grade_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_prod_year += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_pfm_place_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_repr_nm += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_aplc_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_no += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_grade_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_deter_opin_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name1 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name2 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name3 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name4 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name5 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name6 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name7 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_minor_malef_yn += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_wdra_yn += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_return_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_proc_state_code += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_state_code += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_non_com_yn += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_core_harm_rsn_code1 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_core_harm_rsn_code2 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_core_harm_rsn_code3 += encodeTotal + encodeLGual + medi_code + encodeRGual;
 
						// 텍스트타입
						searchCol_ori_title += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_use_title += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_work_cont += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_deter_rsn += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_major_opin_narrn_cont += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_core_harm_rsn += encodeTotal + encodeLGual + medi_code + encodeRGual;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_sub_rcv_date += encodeTotal + encodeLGual + medi_code + encodeRGual;
							searchCol_rcv_date += encodeTotal + encodeLGual + medi_code + encodeRGual;
							searchCol_rt_date += encodeTotal + encodeLGual + medi_code + encodeRGual;
							searchCol_pfm_pnum += encodeTotal + encodeLGual + medi_code + encodeRGual;
							searchCol_contr_start_date += encodeTotal + encodeLGual + medi_code + encodeRGual;
							searchCol_contr_end_date += encodeTotal + encodeLGual + medi_code + encodeRGual;
							searchCol_ori_rt_no += encodeTotal + encodeLGual + medi_code + encodeRGual;
						}
					}

					if (((String) paramMap.get("detail_kindChk")).equals("movie01_video01")) {
						// System.out.println("값이 movie01_video01이라서 video내부로 진입
						// 성공");

						String totalDetailChk = "";
						String detailMovieVideo = "";
						String encodeMovieVideo = "";
						String tmp_movie_video = "";

						////////////////////////////// 결정등급(grade_name)////////////////////////////////////////////
						if (paramMap.get("detailSearch_grade_name") != null) {
							if (paramMap.get("detailSearch_grade_name").equals("12old")) {
								tmp_movie_video = "12세";
							} else if (paramMap.get("detailSearch_grade_name").equals("15old")) {
								tmp_movie_video = "15세";
							} else if (paramMap.get("detailSearch_grade_name").equals("18old")) {
								tmp_movie_video = "청소년관람불가";
							} else if (paramMap.get("detailSearch_grade_name").equals("old")) {
								tmp_movie_video = "제한상영가";
							} else {
								tmp_movie_video = "전체관람가";
							}
							encodeMovieVideo = URLEncoder.encode(tmp_movie_video, "UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "grade_name" + encodeLike + strMark + "*"
									+ encodeMovieVideo + "*" + strMark + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						//////////////////////////// 결정등급////////////////////////////////////////////

						//////////////////////////// 내용정보표시항목
						//////////////////////////// (rt_core_harm_rsn_code1)////////////////////////////
						// code : 17701 주제 17702 선정성 17703 폭력성 17704 대사 17705 공포
						//////////////////////////// 17706 악몽 17707 모방위험

						if (paramMap.get("detailSearch_rt_std_name") != null) {
							/*
							 * String detailRtStd = ""; String encodeRtStd = "";
							 * String tmp_rt_std = "";
							 */
							// 주제
							if (paramMap.get("detailSearch_rt_std_name").equals("17701")) {
								tmp_movie_video = "17701";
							} else if (paramMap.get("detailSearch_rt_std_name").equals("17702")) {
								tmp_movie_video = "17702";
							} else if (paramMap.get("detailSearch_rt_std_name").equals("17703")) {
								tmp_movie_video = "17703";
							} else if (paramMap.get("detailSearch_rt_std_name").equals("17704")) {
								tmp_movie_video = "17704";
							} else if (paramMap.get("detailSearch_rt_std_name").equals("17705")) {
								tmp_movie_video = "17705";
							} else if (paramMap.get("detailSearch_rt_std_name").equals("17706")) {
								tmp_movie_video = "17706";
							} else if (paramMap.get("detailSearch_rt_std_name").equals("17707")) {
								tmp_movie_video = "17707";
							}
							encodeMovieVideo = URLEncoder.encode(tmp_movie_video, "UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "rt_core_harm_rsn_code1" + encodeAns + strMark
									+ encodeMovieVideo + strMark + encodeOr + "rt_core_harm_rsn_code2" + encodeAns
									+ strMark + encodeMovieVideo + strMark + encodeOr + "rt_core_harm_rsn_code3"
									+ encodeAns + strMark + encodeMovieVideo + strMark + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						//////////////////////////// 내용정보표시항목
						//////////////////////////// (rt_core_harm_rsn_code1)////////////////////////////

						//////////////////////////// 매체명////////////////////////////
						if (paramMap.get("detailSearch_medi_name_movieAndVideo") != null) {
							encodeMovieVideo = URLEncoder
									.encode((String) paramMap.get("detailSearch_medi_name_movieAndVideo"), "UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "medi_name" + encodeLike + strMark + "*"
									+ encodeMovieVideo + "*" + strMark + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						
						
						
						//비디오 매체
						if (paramMap.get("detailSearch_medi_name_movieAndVideo") != null
								&& !((String) paramMap.get("detailSearch_medi_name_movieAndVideo")).equals("")) {
							if (paramMap.get("detailSearch_medi_name_movieAndVideo").equals("in")) {
								tmp_movie_video = "국내";
							} else if (paramMap.get("detailSearch_medi_name_movieAndVideo").equals("out")) {
								tmp_movie_video = "국외";
							}
							encodeMovieVideo = URLEncoder.encode(tmp_movie_video, "UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "medi_name" + encodeLike + strMark + "*"
									+ encodeMovieVideo + "*" + strMark + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						
						
						
						
						
						//////////////////////////// 매체명////////////////////////////

						//////////////////////////// 제작사
						//////////////////////////// 국적////////////////////////////
						/*
						 * 기존 input type="text"형태에서 select box로 바뀜에따라 , 로직 변경
						if (paramMap.get("detailSearch_prodc_natnl_name_movieAndVideo") != null) {
							encodeMovieVideo = URLEncoder.encode(
									(String) paramMap.get("detailSearch_prodc_natnl_name_movieAndVideo"), "UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "prodc_natnl_name" + encodeLike + strMark + "*"
									+ encodeMovieVideo + "*" + strMark + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						*/
						if (paramMap.get("detailSearch_prodc_natnl_name_movieAndVideo") != null
								&& !((String) paramMap.get("detailSearch_prodc_natnl_name_movieAndVideo")).equals("")) {
							tmp_movie_video = (String) paramMap.get("detailSearch_prodc_natnl_name_movieAndVideo");
							encodeMovieVideo = URLEncoder.encode(tmp_movie_video, "UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "prodc_natnl_name" + encodeAns + strMark
									+ encodeMovieVideo + strMark + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						//////////////////////////// 제작사
						//////////////////////////// 국적////////////////////////////

						//////////////////////////// 감독명////////////////////////////
						if (paramMap.get("detailSearch_dire_name_movieAndVideo") != null) {
							encodeMovieVideo = URLEncoder
									.encode((String) paramMap.get("detailSearch_dire_name_movieAndVideo"), "UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "dire_name" + encodeLike + strMark + "*"
									+ encodeMovieVideo + "*" + strMark + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						//////////////////////////// 감독명////////////////////////////

						//////////////////////////// 비디오종류////////////////////////////
						/*
						if (paramMap.get("detailSearch_mv_asso_name_movieAndVideo") != null) {
							encodeMovieVideo = URLEncoder
									.encode((String) paramMap.get("detailSearch_mv_asso_name_movieAndVideo"), "UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "mv_asso_name" + encodeLike + strMark + "*"
									+ encodeMovieVideo + "*" + strMark + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						SELECT BOX로 변경!
						*/
						
						if (paramMap.get("detailSearch_mv_asso_name_movieAndVideo") != null
								&& !((String) paramMap.get("detailSearch_mv_asso_name_movieAndVideo")).equals("")) {
							tmp_movie_video = (String) paramMap.get("detailSearch_mv_asso_name_movieAndVideo");
							encodeMovieVideo = URLEncoder.encode(tmp_movie_video, "UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "mv_asso_name" + encodeAns + strMark
									+ encodeMovieVideo + strMark + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						
						
						//////////////////////////// 비디오종류////////////////////////////

						//////////////////////////// 주연명////////////////////////////
						if (paramMap.get("detailSearch_leada_name") != null) {
							encodeMovieVideo = URLEncoder.encode((String) paramMap.get("detailSearch_leada_name"),
									"UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "leada_name" + encodeAns + strMark + encodeMovieVideo + strMark + searchOperation +  encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						//////////////////////////// 주연명////////////////////////////

						//////////////////////////// 희망등급////////////////////////////
						if (paramMap.get("detailSearch_hope_grade_name") != null
								&& !((String) paramMap.get("detailSearch_hope_grade_name")).equals("")) {
							if (paramMap.get("detailSearch_hope_grade_name").equals("12old")) {
								tmp_movie_video = "12세";
							} else if (paramMap.get("detailSearch_hope_grade_name").equals("15old")) {
								tmp_movie_video = "15세";
							} else if (paramMap.get("detailSearch_hope_grade_name").equals("18old")) {
								tmp_movie_video = "청소년관람불가";
							} else {
								tmp_movie_video = "전체관람가";
							}
							encodeMovieVideo = URLEncoder.encode(tmp_movie_video, "UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "hope_grade_name" + encodeLike + strMark + "*"
									+ encodeMovieVideo + "*" + strMark + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						//////////////////////////// 희망등급////////////////////////////

						//////////////////////////// 결정의견////////////////////////////
						if (paramMap.get("detailSearch_deter_opin_name") != null) {
							encodeMovieVideo = URLEncoder.encode((String) paramMap.get("detailSearch_deter_opin_name"),
									"UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "deter_opin_name" + encodeAns + strMark
									+ encodeMovieVideo + strMark + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						//////////////////////////// 결정의견////////////////////////////

						//////////////////////////// 작품내용(줄거리)////////////////////////////
						if (paramMap.get("detailSearch_work_cont") != null) {
							encodeMovieVideo = URLEncoder.encode((String) paramMap.get("detailSearch_work_cont"),
									"UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "work_cont" + encodeAns + strMark
									+ encodeMovieVideo + strMark + searchOperation + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						//////////////////////////// 작품내용(줄거리)////////////////////////////

						//////////////////////////// 결정사유////////////////////////////
						if (paramMap.get("detailSearch_deter_rsn_movieAndVideo") != null) {
							encodeMovieVideo = URLEncoder
									.encode((String) paramMap.get("detailSearch_deter_rsn_movieAndVideo"), "UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "deter_rsn" + encodeAns + strMark
									+ encodeMovieVideo + strMark + searchOperation + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						//////////////////////////// 결정사유////////////////////////////
						
						////////////////////////////등급분류번호////////////////////////////
						if (paramMap.get("detailSearch_rt_no_movieAndVideo") != null) {
							encodeMovieVideo = URLEncoder.encode((String) paramMap.get("detailSearch_rt_no_movieAndVideo"), "UTF-8");
							detailMovieVideo = encodeTotal + encodeLGual + "rt_no" + encodeAns + strMark
									+ encodeMovieVideo + strMark + encodeRGual;
							totalDetailChk += detailMovieVideo;
						}
						////////////////////////////등급분류번호////////////////////////////
						
						
						

						if (!totalDetailChk.equals("")) {
							// 이것이 빈값이 아닐떄만 뒤에 조건들을 붙여주자!
							searchCol += totalDetailChk;
							searchCol_dire_name += totalDetailChk;
							searchCol_prodc_name += totalDetailChk;
							searchCol_rcv_no_view += totalDetailChk;
							searchCol_sub_no += totalDetailChk;
							searchCol_onlin_offlin += totalDetailChk;
							searchCol_medi_name += totalDetailChk;
							searchCol_prodc_natnl_name += totalDetailChk;
							searchCol_mv_asso_name += totalDetailChk;
							searchCol_leada_name += totalDetailChk;
							searchCol_frgnr_natnl_name += totalDetailChk;
							searchCol_kind_name += totalDetailChk;
							searchCol_hope_grade_name += totalDetailChk;
							searchCol_prod_year += totalDetailChk;
							searchCol_pfm_place_name += totalDetailChk;
							searchCol_repr_nm += totalDetailChk;
							searchCol_aplc_name += totalDetailChk;
							searchCol_rt_no += totalDetailChk;
							searchCol_grade_name += totalDetailChk;
							searchCol_deter_opin_name += totalDetailChk;
							searchCol_rt_std_name1 += totalDetailChk;
							searchCol_rt_std_name2 += totalDetailChk;
							searchCol_rt_std_name3 += totalDetailChk;
							searchCol_rt_std_name4 += totalDetailChk;
							searchCol_rt_std_name5 += totalDetailChk;
							searchCol_rt_std_name6 += totalDetailChk;
							searchCol_rt_std_name7 += totalDetailChk;
							searchCol_minor_malef_yn += totalDetailChk;
							searchCol_wdra_yn += totalDetailChk;
							searchCol_return_name += totalDetailChk;
							searchCol_proc_state_code += totalDetailChk;
							searchCol_state_code += totalDetailChk;
							searchCol_non_com_yn += totalDetailChk;
							searchCol_rt_core_harm_rsn_code1 += totalDetailChk;
							searchCol_rt_core_harm_rsn_code2 += totalDetailChk;
							searchCol_rt_core_harm_rsn_code3 += totalDetailChk;

							// 텍스트타입
							searchCol_ori_title += totalDetailChk;
							searchCol_use_title += totalDetailChk;
							searchCol_work_cont += totalDetailChk;
							searchCol_deter_rsn += totalDetailChk;
							searchCol_major_opin_narrn_cont += totalDetailChk;
							searchCol_core_harm_rsn += totalDetailChk;

							// 숫자타입
							if (searchStrInt != 0) {
								searchCol_sub_rcv_date += totalDetailChk;
								searchCol_rcv_date += totalDetailChk;
								searchCol_rt_date += totalDetailChk;
								searchCol_pfm_pnum += totalDetailChk;
								searchCol_contr_start_date += totalDetailChk;
								searchCol_contr_end_date += totalDetailChk;
								searchCol_ori_rt_no += totalDetailChk;
							}
						}

					}
					
					
					// 상세검색 조건절 마무리 괄호
					searchCol += encodeRGual;
					searchCol_dire_name += encodeRGual;
					searchCol_prodc_name += encodeRGual;
					searchCol_rcv_no_view += encodeRGual;
					searchCol_sub_no += encodeRGual;
					searchCol_onlin_offlin += encodeRGual;
					searchCol_medi_name += encodeRGual;
					searchCol_prodc_natnl_name += encodeRGual;
					searchCol_mv_asso_name += encodeRGual;
					searchCol_leada_name += encodeRGual;
					searchCol_frgnr_natnl_name += encodeRGual;
					searchCol_kind_name += encodeRGual;
					searchCol_hope_grade_name += encodeRGual;
					searchCol_prod_year += encodeRGual;
					searchCol_pfm_place_name += encodeRGual;
					searchCol_repr_nm += encodeRGual;
					searchCol_aplc_name += encodeRGual;
					searchCol_rt_no += encodeRGual;
					searchCol_grade_name += encodeRGual;
					searchCol_deter_opin_name += encodeRGual;
					searchCol_rt_std_name1 += encodeRGual;
					searchCol_rt_std_name2 += encodeRGual;
					searchCol_rt_std_name3 += encodeRGual;
					searchCol_rt_std_name4 += encodeRGual;
					searchCol_rt_std_name5 += encodeRGual;
					searchCol_rt_std_name6 += encodeRGual;
					searchCol_rt_std_name7 += encodeRGual;
					searchCol_minor_malef_yn += encodeRGual;
					searchCol_wdra_yn += encodeRGual;
					searchCol_return_name += encodeRGual;
					searchCol_proc_state_code += encodeRGual;
					searchCol_state_code += encodeRGual;
					searchCol_non_com_yn += encodeRGual;
					searchCol_rt_core_harm_rsn_code1 += encodeRGual;
					searchCol_rt_core_harm_rsn_code2 += encodeRGual;
					searchCol_rt_core_harm_rsn_code3 += encodeRGual;

					// 텍스트타입
					searchCol_ori_title += encodeRGual;
					searchCol_use_title += encodeRGual;
					searchCol_work_cont += encodeRGual;
					searchCol_deter_rsn += encodeRGual;
					searchCol_major_opin_narrn_cont += encodeRGual;
					searchCol_core_harm_rsn += encodeRGual;

					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_sub_rcv_date += encodeRGual;
						searchCol_rcv_date += encodeRGual;
						searchCol_rt_date += encodeRGual;
						searchCol_pfm_pnum += encodeRGual;
						searchCol_contr_start_date += encodeRGual;
						searchCol_contr_end_date += encodeRGual;
						searchCol_ori_rt_no += encodeRGual;
					}
					
					
				}
				//detail_visibleChk 조건 끝
				
				//만약 따옴표 안에 단어가 인식이 된다면 이 로직 실행.
				if(!quote_searchStr.equals("")) {
					//통합검색이든, 메뉴명이든 뒤에 quote_searchCol 붙여주기 시작! 
					searchCol += quote_searchCol;
					searchCol_dire_name += quote_searchCol;
					searchCol_prodc_name += quote_searchCol;
					searchCol_rcv_no_view += quote_searchCol;
					searchCol_sub_no += quote_searchCol;
					searchCol_onlin_offlin += quote_searchCol;
					searchCol_medi_name += quote_searchCol;
					searchCol_prodc_natnl_name += quote_searchCol;
					searchCol_mv_asso_name += quote_searchCol;
					searchCol_leada_name += quote_searchCol;
					searchCol_frgnr_natnl_name += quote_searchCol;
					searchCol_kind_name += quote_searchCol;
					searchCol_hope_grade_name += quote_searchCol;
					searchCol_prod_year += quote_searchCol;
					searchCol_pfm_place_name += quote_searchCol;
					searchCol_repr_nm += quote_searchCol;
					searchCol_aplc_name += quote_searchCol;
					searchCol_rt_no += quote_searchCol;
					searchCol_grade_name += quote_searchCol;
					searchCol_deter_opin_name += quote_searchCol;
					searchCol_rt_std_name1 += quote_searchCol;
					searchCol_rt_std_name2 += quote_searchCol;
					searchCol_rt_std_name3 += quote_searchCol;
					searchCol_rt_std_name4 += quote_searchCol;
					searchCol_rt_std_name5 += quote_searchCol;
					searchCol_rt_std_name6 += quote_searchCol;
					searchCol_rt_std_name7 += quote_searchCol;
					searchCol_minor_malef_yn += quote_searchCol;
					searchCol_wdra_yn += quote_searchCol;
					searchCol_return_name += quote_searchCol;
					searchCol_proc_state_code += quote_searchCol;
					searchCol_state_code += quote_searchCol;
					searchCol_non_com_yn += quote_searchCol;
					searchCol_rt_core_harm_rsn_code1 += quote_searchCol;
					searchCol_rt_core_harm_rsn_code2 += quote_searchCol;
					searchCol_rt_core_harm_rsn_code3 += quote_searchCol;
	
					// 텍스트타입
					searchCol_ori_title += quote_searchCol;
					searchCol_use_title += quote_searchCol;
					searchCol_work_cont += quote_searchCol;
					searchCol_deter_rsn += quote_searchCol;
					searchCol_major_opin_narrn_cont += quote_searchCol;
					searchCol_core_harm_rsn += quote_searchCol;
	
					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_sub_rcv_date += quote_searchCol;
						searchCol_rcv_date += quote_searchCol;
						searchCol_rt_date += quote_searchCol;
						searchCol_pfm_pnum += quote_searchCol;
						searchCol_contr_start_date += quote_searchCol;
						searchCol_contr_end_date += quote_searchCol;
						searchCol_ori_rt_no += quote_searchCol;
					}
				}
				//통합검색이든, 메뉴명이든 뒤에 quote_searchCol 붙여주기 끝!
				
				
				/**************** 상세검색의 일자, 신청회사 통합작업중 ***********************/

				/**************
				 * ■■■■■■■■■■■■■*************************************우측사이드에 관한
				 * information 시작
				 * *************************************************■■■■■■■■■
				 **************************************************/
				List<Map<String, Object>> video01_sideInformation = new ArrayList<Map<String, Object>>();

				if (searchGubun.equals("All")) {
					// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가

					// 0. (조건에 맞는)원제명 대한 건수 및 데이터 조회
					/*
					 * String total_ori_title = result_noticeUrl + medi_code +
					 * encodeAnd + searchCol_ori_title + orderValue;
					 * Map<String,Object> ori_title_information =
					 * URLEncodingFunc(total_ori_title);
					 * ori_title_information.put("menuName", "원제명" );
					 * video01_sideInformation.add(ori_title_information);
					 */
					// 0. (조건에 맞는)원제명 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_ori_title, "원제명"));

					// 0. (조건에 맞는)사용제명 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_use_title, "사용제명"));

					// 1. (조건에 맞는)감독명에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_dire_name, "감독명"));

					// 2. (조건에 맞는)제작사명에 대한 건수 및 데이터 조회 // walker에 정보 담기
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_prodc_name, "제작사명"));

					// 3. (조건에 맞는)접수번호에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rcv_no_view, "접수번호"));

					// 4. (조건에 맞는)신청번호에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_sub_no, "신청번호"));

					// 5. (조건에 맞는)온라인/오프라인에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_onlin_offlin, "온라인/오프라인"));

					// 6. (조건에 맞는)메체명에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_medi_name, "매체명"));

					// 7. (조건에 맞는)제작자 국적에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_prodc_natnl_name, "제작자 국적"));

					// 8. (조건에 맞는)영화 종류에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_mv_asso_name, "영화 종류"));

					// 9. (조건에 맞는)주연명 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_leada_name, "주연명"));

					// 10. (조건에 맞는)외국인 국적에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_frgnr_natnl_name, "외국인 국적"));

					// 11. (조건에 맞는)종류에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_kind_name, "종류"));

					// 12. (조건에 맞는)희망등급에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_hope_grade_name, "희망등급"));

					// 13. (조건에 맞는)제작년도에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_prod_year, "제작년도"));

					// 14. (조건에 맞는)공연 장소명에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_pfm_place_name, "공연 장소명"));

					// 15. (조건에 맞는)대표자 성명에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_repr_nm, "대표자 성명"));

					// 16. (조건에 맞는)신청회사에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_aplc_name, "신청회사"));

					// 17. (조건에 맞는)등급분류번호에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_no, "등급분류번호"));

					// 18. (조건에 맞는)결정등급에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_grade_name, "결정등급"));

					// 19. (조건에 맞는)결정의견에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_deter_opin_name, "결정의견"));

					// 20. (조건에 맞는)주제에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name1, "주제"));

					// 21. (조건에 맞는)선정성에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name2, "선정성"));

					// 22. (조건에 맞는)폭력성에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name3, "폭력성"));

					// 23. (조건에 맞는)대사에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name4, "대사"));

					// 24. (조건에 맞는)공포에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name5, "공포"));

					// 25. (조건에 맞는)약물에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name6, "약물"));

					// 26. (조건에 맞는)모방위험에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name7, "모방위험"));

					// 27. (조건에 맞는)연소자 유해성 여부에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_minor_malef_yn, "연소자 유해성"));

					// 28. (조건에 맞는)취하여부에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_wdra_yn, "취하여부"));

					// 29. (조건에 맞는)취하,반납에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_return_name, "취하,반납"));

					// 30. (조건에 맞는)진행 상태 코드에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_proc_state_code, "진행 상태 코드"));

					// 31. (조건에 맞는)상태코드에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_state_code, "상태코드"));

					// 32. (조건에 맞는)불완전데이터 플래그에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_non_com_yn, "불완전데이터 플래그"));

					// 33. (조건에 맞는)내용정보표시항목1에 대한 건수 및 데이터 조회
					video01_sideInformation
							.add(mediurlEncodingResult(walker, paramMap, request, response, result_noticeUrl, encodeAnd,
									orderValue, medi_code, searchCol_rt_core_harm_rsn_code1, "내용정보표시항목1"));

					// 34. (조건에 맞는)내용정보표시항목2에 대한 건수 및 데이터 조회
					video01_sideInformation
							.add(mediurlEncodingResult(walker, paramMap, request, response, result_noticeUrl, encodeAnd,
									orderValue, medi_code, searchCol_rt_core_harm_rsn_code2, "내용정보표시항목2"));

					// 35. (조건에 맞는)내용정보표시항목3에 대한 건수 및 데이터 조회
					video01_sideInformation
							.add(mediurlEncodingResult(walker, paramMap, request, response, result_noticeUrl, encodeAnd,
									orderValue, medi_code, searchCol_rt_core_harm_rsn_code3, "내용정보표시항목3"));

					/**************** 텍스트 데이터 시작!!! ******************/
					// 36. (조건에 맞는)작품내용(줄거리) 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_work_cont, "작품내용(줄거리)"));

					// 37. (조건에 맞는)결정사유에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_deter_rsn, "결정사유"));

					// 38. (조건에 맞는)주요의견에 대한 건수 및 데이터 조회
					video01_sideInformation
							.add(mediurlEncodingResult(walker, paramMap, request, response, result_noticeUrl, encodeAnd,
									orderValue, medi_code, searchCol_major_opin_narrn_cont, "주요의견"));

					// 39. (조건에 맞는)핵심유해사유에 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_core_harm_rsn, "핵심유해사유"));
					/**************** 텍스트 데이터 끝!!! ******************/

					/**************** 숫자 데이터 시작 (null처리)!!! ******************/
					if (searchStrInt != 0) {
						// 40. (조건에 맞는)신청일자에 대한 건수 및 데이터 조회
						video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
								result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_sub_rcv_date, "신청일자"));

						// 41. (조건에 맞는)접수일자에 대한 건수 및 데이터 조회
						video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
								result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rcv_date, "접수일자"));

						// 42. (조건에 맞는)등급분류일에 대한 건수 및 데이터 조회
						video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
								result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_date, "등급분류일"));

						// 43. (조건에 맞는)외국인 인원수에 대한 건수 및 데이터 조회
						video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
								result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_pfm_pnum, "외국인 인원수"));

						// 44. (조건에 맞는)계약 시작 일자에 대한 건수 및 데이터 조회
						video01_sideInformation
								.add(mediurlEncodingResult(walker, paramMap, request, response, result_noticeUrl,
										encodeAnd, orderValue, medi_code, searchCol_contr_start_date, "계약 시작 일자"));

						// 45. (조건에 맞는)계약 종료 일자에 대한 건수 및 데이터 조회
						video01_sideInformation
								.add(mediurlEncodingResult(walker, paramMap, request, response, result_noticeUrl,
										encodeAnd, orderValue, medi_code, searchCol_contr_end_date, "계약 종료 일자"));

						// 46. (조건에 맞는)신규추천번호에 대한 건수 및 데이터 조회
						video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
								result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_ori_rt_no, "신규추천번호"));
					}
					/**************** 숫자 데이터 끝 (null처리)!!! ******************/

					Collections.sort(video01_sideInformation, new sideMenuCntComparator());
				} // if(searchGubun.equals("All")) 끝
				if (searchGubun.equals("title")) {
					// (조건에 맞는)원제명 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol, "원제명/사용제명"));
				}
				if (searchGubun.equals("decision")) {
					// (조건에 맞는)결정사유 대한 건수 및 데이터 조회
					video01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol, "결정사유"));
				}
				// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
				walker.setValue("video01_sideInformation", video01_sideInformation);

				/**************
				 * ■■■■■■■■■■■■■***************************************우측사이드에 관한
				 * information
				 * 끝***********************************************■■■■■■■■■
				 **************************************************/

				/*************************************************************
				 * 검색어 처리 끝
				 *************************************************************************************************/

				totalUrl = result_noticeUrl  + medi_code + encodeAnd + searchCol
						+ orderValue;
				// 결과내 재검색이라면 ? 시작
				if (((String) paramMap.get("result_re_search_chk")).equals("Y")
						&& (String) paramMap.get("video01_historySearchQuery") != null) {
					totalUrl = (String) paramMap.get("video01_historySearchQuery") + encodeAnd + "(" + medi_code
							+ encodeAnd + searchCol + ")" + orderValue;
				}
				// 결과내 재검색이라면 ? 끝

				// 이전쿼리 저장 시작
				String historySearchQuery = "";
				historySearchQuery = result_noticeUrl + "(" + medi_code + encodeAnd + searchCol + ")";
				// 만약 결과내재검색을 통한 검색이었다면, historySearchQuery(이전쿼리)는 , 이전쿼리 +
				// encodeAnd + 현재쿼리 일 것이다.
				if (((String) paramMap.get("result_re_search_chk")).equals("Y")
						&& (String) paramMap.get("video01_historySearchQuery") != null) {
					historySearchQuery = (String) paramMap.get("video01_historySearchQuery") + encodeAnd + "("
							+ medi_code + encodeAnd + searchCol + ")";
				}
				paramMap.put("video01_historySearchQuery", historySearchQuery);
				// 이전쿼리 저장 끝

			} else {
				// 검색어가 공백이라면? // 통합검색이든, 제목이든 내용이든 // 영화매체에 관한 모든 데이터가 등장해줘야함
				totalUrl = result_noticeUrl + medi_code + orderValue;
			}

			/*************************************************************
			 * 얻어낸 Url 결과물로 추가적인 작업시작 / 페이징 처리 시작
			 *************************************************************************************************/
			String paging_totalUrl = totalUrl + "&pagelength=" + detailresultLine + "&offset=" + offset + "&limit="
					+ detailresultLine + "&hilite-fields="
					+ "%7B%22use_title%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22ori_title%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22aplc_name%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22rt_no%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22grade_name%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22rt_no%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22work_cont%22%3A%7B%22length%22%3A3000%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22deter_rsn%22%3A%7B%22length%22%3A3000%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D";

			List<Object> dataList = pagingURLEncodingFunc(paging_totalUrl);
			video01.put("total_count_page", (Integer) dataList.get(0));
			walker.setValue("video01_resultMapList", (List<Map<String, String>>) dataList.get(1));
			/*************************************************************
			 * 얻어낸 Url 결과물로 추가적인 작업시작 / 페이징 처리 시작
			 *************************************************************************************************/
			// totalURLCntFunc로 총합 갯수만 가져오고있다. -> 데이터가 필요하다면 해당 메소드를 안에
			// resultMap을 확인하면 됨.
			Integer total_count = totalURLCntFunc(totalUrl);
			video01.put("total_count", total_count);
			walker.setValue("video01", video01);

		}
		///////////////////////////////////////////// 비디오 끝 (17511,
		///////////////////////////////////////////// 17512)///////////////////////////////////////////////////////////////////////////////////////////////////
	}

	/**
	 * 
	 * @param walker
	 * @param paramMap
	 * @param request
	 * @param response
	 * @throws Exception
	 *             광고매체에 관한 데이터를 추출하는 메소드
	 */
	public void ad01(Walker walker, Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// null처리는 필수!
		// 파라미터 목록 가져오기. * 검색어, 검색구분, 섹션구분
		String searchStr = (String) paramMap.get("searchStr");
		String searchGubun = (String) paramMap.get("searchGubun");
		String[] sectionGubun = null;
		if (request.getParameterValues("sectionGubun") == null) {
			sectionGubun = new String[] { "Total" };
		} else {
			sectionGubun = request.getParameterValues("sectionGubun");
		}
		List<String> sectionGubunList = new ArrayList<String>(Arrays.asList(sectionGubun));

		/*************** 검색어에 숫자가 들어올떄 *******************************/
		long searchStrInt = 0;
		if (paramMap.get("searchStr") != null && ((String) paramMap.get("searchStr")).matches("[-+]?\\d*\\.?\\d+")) {
			searchStrInt = Long.valueOf((String) paramMap.get("searchStr"));
		}
		/*************** 검색어에 숫자가 들어올떄 *******************************/
		// 최종 URL
		String totalUrl = "";
		// 공통 URL
		String result_noticeUrl = "http://192.168.11.242:7577/search5?select=*&from=result_notice.result_notice&where=";
		String result_opinUrl = "http://192.168.11.242:7577/search5?select=*&from=result_opin.result_opin&where=";
		String result_fileUrl = "http://192.168.11.242:7577/search5?select=*&from=result_file.result_file&where=";
		String result_imageUrl = "http://192.168.11.242:7577/search5?select=*&from=result_image.result_image&where=";
		String result_moniUrl = "http://192.168.11.242:7577/search5?select=*&from=result_moni.result_moni&where=";
		String result_surveyUrl = "http://192.168.11.242:7577/search5?select=*&from=result_survey.result_survey&where=";
		// 공통 인코딩 목록
		String encode = "UTF-8";
		String encodeSearchStr = URLEncoder.encode(searchStr, encode); // 검색어
		String encodeSearchGubun = URLEncoder.encode(searchGubun, encode); // 검색구분(통합검색,
																			// 제목,
																			// 내용,
																			// 결정사유
																			// 등등)
		String encodeAnd = URLEncoder.encode(" and ", encode);
		String encodeOr = URLEncoder.encode(" or ", encode);
		String encodeAns = URLEncoder.encode("=", encode);
		String encodeInOpen = URLEncoder.encode(" in{", encode);
		String encodeInClose = URLEncoder.encode("}", encode);
		String encodeLGual = "(";
		String encodeRGual = ")";
		String encodeLike = URLEncoder.encode(" like ", encode);
		String strMark = URLEncoder.encode("'", encode);
		String between = URLEncoder.encode(" between ", encode);

		// where절의 text타입에만 사용가능.
		String allword = URLEncoder.encode(" allword ", encode);
		String anyword = URLEncoder.encode(" anyword ", encode);
		String natural = URLEncoder.encode(" natural ", encode);

		// 동의어 검색
		String synonym = URLEncoder.encode(" synonym ", encode);

		// 카테고리 정렬 ex) ORDER BY $CATEGORYFIELD(field_name(domain), keyword)
		String categoryOrder = URLEncoder.encode(" $categoryfield(use_title(), 검색어넣기 ", encode);

		/**
		 * 17501 : 국내영화 17502 : 국외영화 17505 : 광고영화 17511 : 국내비디오 17512 : 국외비디오
		 */

		// medi_code 인코딩 목록
		String medi_mv = URLEncoder.encode("'17501','17502'", encode);
		String medi_ad = URLEncoder.encode("'17503','17504','17505','17506','17514'", encode);
		String medi_video = URLEncoder.encode("'17511','17512'", encode);
		String medi_perform = URLEncoder.encode("'17521','17522','17523','17524'", encode);

		/*
		 * //첫 로딩 시 데이터를 표시해주는 역할을 함. if(searchGubun == null ){ searchGubun =
		 * "All"; } else if(searchGubun.trim().isEmpty()){ searchGubun = "All";
		 * }
		 */

		// 부가설명 : 처음 orderValue로 JSP에서 넘어온 정렬값을 받아서, 넘어온 정렬값을 기준으로 url인코딩을 실시한다.
		String orderValue = (String) request.getParameter("orderValue");
		if (orderValue == null || orderValue.equals("")) {
			orderValue = URLEncoder.encode(" order by $match(rt_no) desc, $relevance desc, $rowid desc ", encode);
		}
		if (orderValue.equals("orderRelevance")) {
			orderValue = URLEncoder.encode(" order by $match(rt_no) desc, $relevance desc, $rowid desc ", encode);
		}
		if (orderValue.equals("orderGa")) {
			orderValue = "%20order%20by%20use_title_sort%20asc%20%2c%20ori_title_sort%20asc%20";
		}
		if (orderValue.equals("orderDate")) {
			orderValue = "%20order%20by%20rcv_date%20desc%20";
		}

		//
		String groupByTest = URLEncoder.encode(" order by $relevance desc ", encode);

		// 2022 12 15 페이징 처리 시작

		// 1. sectionGubun을 하나만 선택하거나, -더보기 클릭 시, sectionGubun에는 딱 하나의 데이터가
		// 들어있고, 페이지는 1페이지로 이동한다. -->sectionGubun이 딱 하나라면 (Total)이 아닐떄 , 페이징목록이
		// 보이게끔 유도하기.
		// 페이지는 페이지 목록 버튼 시 마다 2페이지 3페이지 이동한다... 그러다가 다시 검색버튼 같은것을 누르면 1페이지로
		// 초기화!

		// 기존 display가 limit로 대체

		int cPage = 0;
		int offset = 0;
		int pagelength = 0;

		/**************************
		 * 20221223 페이징처리를 위한 새로운 로직 (영화, 비디오 반영완료)
		 *********************************************/
		if (((String) paramMap.get("isPaging")).equals("Y")) {
			cPage = (Integer) paramMap.get("cPage");
			offset = (Integer) paramMap.get("offset");
			pagelength = (Integer) paramMap.get("pagelength");
			offset = (cPage - 1) * pagelength;
		} else {
			cPage = 1;
			offset = 0;
			pagelength = 10;
			offset = (cPage - 1) * pagelength;
		}

		/*******************************************************************/

		/**************** 상세보기 시작 ********************************/
		// 20221216 상세보기에 관련된 것들 맨 위로 빼기 (기본값이 존재함)
		// 하지만 상세보기를 하지 않는다면 이 값이 null이라서 오류가뜸 ~~~~~~~~~~~~~~~~~~~~~~~~~~~

		// 상세보기를 클릭했는지 여부
		String detail_visibleChk = (String) paramMap.get("detail_visibleChk");

		// 연산자 가져오기
		String searchOperation = "";

		// 결과 수
		int detailresultLine = 3;
		try {
			if ((String) paramMap.get("detailSearch_resultLine") != null) {
				detailresultLine = Integer.valueOf((String) paramMap.get("detailSearch_resultLine"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (detailresultLine != 3 && (String) paramMap.get("detailSearch_resultLine") != null) {
				detailresultLine = detailresultLine;
			} else {
				detailresultLine = 3;
			}
		}

		try {
			if (detail_visibleChk.equals("Y")) {
				searchOperation = (String) paramMap.get("detailSearch_radio");
			} else {
				searchOperation = "natural";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_radio") == null) {
				searchOperation = "natural";
			}
		}

		if (searchOperation.equals("allword")) {
			searchOperation = natural;
		} else if (searchOperation.equals("anyword")) {
			searchOperation = natural;
		} else if (searchOperation.equals("natural")) {
			searchOperation = natural;
		} else {
			searchOperation = natural;
		}
		
		if(searchStr.indexOf("-") != -1){
			searchOperation = allword;
		}

		// 신청회사 null 처리
		String detailAplc = "";
		try {
			if ((String) paramMap.get("detailSearch_aplc") != null) {
				detailAplc = (String) paramMap.get("detailSearch_aplc");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_aplc") == null) {
				detailAplc = "";
			}
		}

		// 상세보기-일자 데이터 가져오기 및 null 처리 시작

		// 시작날짜
		String period_start = "";
		try {
			if ((String) paramMap.get("period_start") != null) {
				period_start = (String) paramMap.get("period_start");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("period_start") == null) {
				period_start = "";
			}
		}

		// 종료날짜
		String period_end = "";
		try {
			if ((String) paramMap.get("period_end") != null) {
				period_end = (String) paramMap.get("period_end");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("period_end") == null) {
				period_end = "";
			}
		}

		// 등급분류일자, 신청일자 , 접수일자 구분
		String detail_dateGubun = "";
		try {
			if ((String) paramMap.get("detailSearch_date") != null) {
				detail_dateGubun = (String) paramMap.get("detailSearch_date");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_date") == null) {
				detail_dateGubun = "gradedate";
			}
		}

		if (!period_start.equals("")) {
			period_start = period_start.substring(0, 4) + period_start.substring(5, 7) + period_start.substring(8, 10)
					+ "000000";
		}
		if (!period_end.equals("")) {
			period_end = period_end.substring(0, 4) + period_end.substring(5, 7) + period_end.substring(8, 10)
					+ "000000";
		}

		// 듈 중에 하나를 선택안하고 상세검색을 하면 --> 전체날짜로 인식하여 데이터검색하기
		if ((period_start.equals("") || period_end.equals("")) && !(period_start.equals("") && period_end.equals(""))) {
			period_start = "";
			period_end = "";

		}
		// 상세보기-일자 데이터 가져오기 및 null 처리 끝

		/**************** 상세보기 끝 ********************************/

		// 하지만 상세보기를 하지 않는다면 이 값이 null이라서 오류가뜸 ~~~~~~~~~~~~~~~~~~~~~~~~~~~

		
		//따옴표 기능 추가 시작

		//따옴표기능 변수1 : 반드시 포함해야 하는 따옴표 안의 단어.
		String quote_searchStr_origin = "";
		//따옴표기능 변수2 : 반드시 포함해야 하는 따옴표 안의 단어 (인코딩 받아줄 변수).
		String quote_searchStr = ""; 
		
		

		//따옴표기능 로직1 : 따옴표의 인덱스 위치를 추출한다.
		String word = "\""; 
		int searchStrIndex = searchStr.indexOf(word);
		List<Integer> searchStrIndexList = new ArrayList<Integer>();
		 while(searchStrIndex != -1) {
			 searchStrIndexList.add(searchStrIndex);
			 searchStrIndex = searchStr.indexOf(word, searchStrIndex+word.length()); 
		 }
		
		 
		 
		//따옴표기능 로직2 : 따옴표의 인덱스가 2개이상 나왔다면, 따옴표가 4개있든 7개 있든, 제일 첫번째로 나오는 따옴표 2개의 쌍을 구하고, 그 따옴표로 깜사져 있는 단어를 추출한다.
		if(searchStrIndexList.size() >= 2 ) {
			 int firstIndex = searchStrIndexList.get(0);
			 int lastIndex = searchStrIndexList.get(1);
			 quote_searchStr_origin = searchStr.substring(firstIndex + 1, lastIndex);  
			 quote_searchStr = URLEncoder.encode(quote_searchStr_origin, encode);
		 }
		
		 
		 
		 
		 //따옴표기능 로직3 : 따옴표의 인덱스가 일단 하나 이상 등장한다면, 한개 이상일 경우는 따옴표를 제거해주고, 따옴표가 2개이상인 경우에는 위에서 얻어온 '따옴표 안'의 단어를 따로 추출하여 인코딩한다.
		             // --> 차후에 따옴표 검색이 이뤄질때, 헤어질 결심 "수사관" 검색 시, --> (헤어질 결심 ) AND ("수사관") 이런식으로 검색 하게끔 해줌.
		 if(searchStrIndexList.size() >= 1 ){
			 
			String searchStr2 = searchStr.replaceAll("\"", "");
			String searchStr3 = searchStr2.replaceAll( quote_searchStr_origin , "").trim(); 
			if(!searchStr3.equals("") && searchStr3 != null){
				encodeSearchStr = URLEncoder.encode(searchStr3, encode); // 검색어
			}
		 }
		 
		 
		 
		 // 따옴표에 들어간 단어에 맞춰서 조건을 추가해주기 위해서 셋팅하는 작업 
		 String quote_searchCol = "";
		 if(!quote_searchStr.equals("")) {
    /***********************************************ad01() quote_searchStr start*************************************************************************/	 
		 quote_searchCol += encodeAnd + "(" + "use_title" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" +  "synonym('d0')" 
						+ encodeOr + "ori_title" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" +  "synonym('d0')" 
					    + encodeOr + "dire_name" + encodeAns + strMark + quote_searchStr + strMark // 감독명
						+ encodeOr + "prodc_name" + encodeAns + strMark + quote_searchStr + strMark // 제작사명
						+ encodeOr + "rcv_no_view" + encodeAns + strMark + quote_searchStr + strMark + // no3
																										// 접수번호
																										// rcv_no_view
																										// |string
																										// like
																										// X
						encodeOr + "sub_no" + encodeAns + strMark + quote_searchStr + strMark + // no4
																								// 신청번호
																								// sub_no
																								// |string
																								// like
																								// X
						encodeOr + "onlin_offlin" + encodeAns + strMark + quote_searchStr + strMark +  // no7
																													// 온라인/오프라인
																													// |string
						encodeOr + "medi_name" + encodeAns + strMark + quote_searchStr + strMark +  // no9
																												// 매체명
																												// |string
						encodeOr + "prodc_natnl_name" + encodeAns + strMark + quote_searchStr + strMark
						+ // no15 제작자 국적 |string
						encodeOr + "mv_asso_name" + encodeAns + strMark + quote_searchStr + strMark +  // no23
																													// 영화
																													// 종류
																													// |string
						encodeOr + "leada_name" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" +   // no25
																													// 주연명
																													// |string
						encodeOr + "frgnr_natnl_name" + encodeAns + strMark + quote_searchStr + strMark
						+ // no29 외국인 국적 |string
						encodeOr + "kind_name" + encodeAns + strMark + quote_searchStr + strMark + // no35
																												// 종류
																												// |string
						encodeOr + "hope_grade_name" + encodeAns + strMark + quote_searchStr + strMark
						+ // no41 희망등급 |string
						encodeOr + "prod_year" + encodeAns + strMark + quote_searchStr + strMark + // no42
																									// 제작년도
																									// |string
																									// like
																									// X
						encodeOr + "pfm_place_name" + encodeAns + strMark + quote_searchStr + strMark + // no46
																														// 공연
																														// 장소명
																														// |string
						encodeOr + "repr_nm" + encodeAns + strMark + quote_searchStr + strMark + // no61
																												// 대표자
																												// 성명
																												// |string
						encodeOr + "aplc_name" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + // no63
																												// 신청회사
																												// |string
						encodeOr + "rt_no" + encodeAns + strMark + quote_searchStr + strMark + // no69
																								// 등급분류번호
																								// |string
																								// like
																								// X
						encodeOr + "grade_name" + encodeAns + strMark + quote_searchStr + strMark + // no71
																													// 결정등급
																													// |string
						encodeOr + "deter_opin_name" + encodeAns + strMark + quote_searchStr + strMark
						+ // no72 결정의견 |string
						encodeOr + "rt_std_name1" + encodeAns + strMark + quote_searchStr + strMark + // no73
																													// 주제
																													// |string
						encodeOr + "rt_std_name2" + encodeAns + strMark + quote_searchStr + strMark + // no74
																													// 선정성
																													// |string
						encodeOr + "rt_std_name3" + encodeAns + strMark + quote_searchStr + strMark + // no75
																													// 폭력성
																													// |string
						encodeOr + "rt_std_name4" + encodeAns + strMark + quote_searchStr + strMark + // no76
																													// 대사
																													// |string
						encodeOr + "rt_std_name5" + encodeAns + strMark + quote_searchStr + strMark + // no77
																													// 공포
																													// |string
						encodeOr + "rt_std_name6" + encodeAns + strMark + quote_searchStr + strMark + // no78
																													// 약물
																													// |string
						encodeOr + "rt_std_name7" + encodeAns + strMark + quote_searchStr + strMark + // no79
																													// 모방위험
																													// |string
						encodeOr + "minor_malef_yn" + encodeAns + strMark + quote_searchStr + strMark + // no80
																										// 연소자
																										// 유해성
																										// 여부
																										// |string
																										// like
																										// X
						encodeOr + "wdra_yn" + encodeAns + strMark + quote_searchStr + strMark + // no86
																									// 취하여부
																									// |string
																									// like
																									// X
						encodeOr + "return_name" + encodeAns + strMark + quote_searchStr + strMark + // no90
																										// 취하,
																										// 반납
																										// |string
																										// like
																										// X
						encodeOr + "proc_state_code" + encodeAns + strMark + quote_searchStr + strMark + // no97
																											// 진행
																											// 상태
																											// 코드
																											// |string
																											// like
																											// X
						encodeOr + "state_code" + encodeAns + strMark + quote_searchStr + strMark + // no98
																									// 상태코드
																									// |string
																									// like
																									// X
						encodeOr + "non_com_yn" + encodeAns + strMark + quote_searchStr + strMark + // no109
																													// 불완전데이터
																													// 플래그
																													// |string
						encodeOr + "rt_core_harm_rsn_code1" + encodeAns + strMark + quote_searchStr + strMark + // no114 내용정보표시항목1 |string
						encodeOr + "rt_core_harm_rsn_code2" + encodeAns + strMark + quote_searchStr + strMark + // no114 내용정보표시항목2 |string
						encodeOr + "rt_core_harm_rsn_code3" + encodeAns + strMark + quote_searchStr + strMark + // no114 내용정보표시항목3 |string
						encodeOr + "work_cont"             + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + // no81// 작품내용(줄거리) // |text
						encodeOr + "deter_rsn"             + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" +  // no83 // 결정사유 // |text
						encodeOr + "major_opin_narrn_cont" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + // no84 주요의견 |text
						encodeOr + "core_harm_rsn"         + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+"; // no85 핵심유해사유 |text
				// int는 encode를 따로 안해줘도 무방함.
				if (searchStrInt != 0) {
					quote_searchCol += encodeOr + "sub_rcv_date" + encodeAns + searchStrInt + "000000" + // no5
																									// 신청일자
																									// |
																									// int
							encodeOr + "rcv_date" + encodeAns + searchStrInt + "000000" + // no6
																							// 접수일자
																							// |
																							// int
							encodeOr + "rt_date" + encodeAns + searchStrInt + "000000" + // no70
																							// 등급분류일
																							// |
																							// int
							encodeOr + "pfm_pnum" + encodeAns + searchStrInt + // no28
																				// 외국인
																				// 인원수
																				// |
																				// int
							encodeOr + "contr_start_date" + encodeAns + searchStrInt + "000000" + // no44
																									// 계약
																									// 시작
																									// 일자
																									// |
																									// int
							encodeOr + "contr_end_date" + encodeAns + searchStrInt + "000000" + // no45
																								// 계약
																								// 종료
																								// 일자
																								// |
																								// int
							encodeOr + "ori_rt_no" + encodeAns + searchStrInt; // no59
																				// 신규추천번호
																				// |
																				// int
				}
				// 통합검색의 마무리 괄호
				quote_searchCol += ")";
				//quote_searchCol만 셋팅 하고, 왜 다른 사이드는 셋팅 하지 않는지?
				//위에 보다시피 quote_searchCol 이 모든 컬럼에 "따옴표에 들어간 단어"를 매칭시켜서 가져오고있다 
				// 나중에 제일 밑에 detail_visibleChk를 쓰는 로직 밑에 보면, 통합검색이든 사이드메뉴든 뒤에 quote_searchCol를 붙여주는 작업을 하고 있다.
		 }
		 /***********************************************ad01() quote_searchStr start*************************************************************************/
		 // 따옴표 기능 추가 끝
		
		
		///////////////////////////////////////////// 광고물 시작 (17503, 17504,
		///////////////////////////////////////////// 17505, 17506,
		///////////////////////////////////////////// 17514)///////////////////////////////////////////////////////////////////////////////////////////////////
		if (sectionGubunList.contains("ad01") || sectionGubunList.contains("Total")) {
			Map<String, Object> ad01 = new HashMap<String, Object>();
			String medi_code = "medi_code" + encodeInOpen + medi_ad + encodeInClose;

			if (searchStr != null) {
				/*************************************************************
				 * 검색어 처리 시작
				 *************************************************************************************************/

				// 검색구분은 if절로서 쓰이고, 검색어는 where절 안에 쓰임으로써, 직접적인 쿼리에 영향을 줌.
				String searchCol = ""; // where절을 담을 변수
				String searchCol_ori_title = "";
				String searchCol_use_title = "";
				String searchCol_dire_name = "";
				String searchCol_prodc_name = "";
				String searchCol_rcv_no_view = "";
				String searchCol_sub_no = "";
				String searchCol_onlin_offlin = "";
				String searchCol_medi_name = "";
				String searchCol_prodc_natnl_name = "";
				String searchCol_mv_asso_name = "";
				String searchCol_leada_name = "";
				String searchCol_frgnr_natnl_name = "";
				String searchCol_kind_name = "";
				String searchCol_hope_grade_name = "";
				String searchCol_prod_year = "";
				String searchCol_pfm_place_name = "";
				String searchCol_repr_nm = "";
				String searchCol_aplc_name = "";
				String searchCol_rt_no = "";
				String searchCol_grade_name = "";
				String searchCol_deter_opin_name = "";
				String searchCol_rt_std_name1 = "";
				String searchCol_rt_std_name2 = "";
				String searchCol_rt_std_name3 = "";
				String searchCol_rt_std_name4 = "";
				String searchCol_rt_std_name5 = "";
				String searchCol_rt_std_name6 = "";
				String searchCol_rt_std_name7 = "";
				String searchCol_minor_malef_yn = "";
				String searchCol_wdra_yn = "";
				String searchCol_return_name = "";
				String searchCol_proc_state_code = "";
				String searchCol_state_code = "";
				String searchCol_non_com_yn = "";
				String searchCol_rt_core_harm_rsn_code1 = "";
				String searchCol_rt_core_harm_rsn_code2 = "";
				String searchCol_rt_core_harm_rsn_code3 = "";
				String searchCol_work_cont = "";
				String searchCol_deter_rsn = "";
				String searchCol_major_opin_narrn_cont = "";
				String searchCol_core_harm_rsn = "";
				String searchCol_sub_rcv_date = "";
				String searchCol_rcv_date = "";
				String searchCol_rt_date = "";
				String searchCol_pfm_pnum = "";
				String searchCol_contr_start_date = "";
				String searchCol_contr_end_date = "";
				String searchCol_ori_rt_no = "";

				// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
				if (searchGubun.equals("All")) {
					searchCol += "(" + "use_title" + encodeAns + strMark + encodeSearchStr + strMark + "+allword+" + "synonym('d0')" +
				             encodeOr + "use_title" + encodeAns + strMark + encodeSearchStr + strMark+ searchOperation + "synonym('d0')" + 
							encodeOr + "ori_title" + encodeAns + strMark
							+ encodeSearchStr + strMark + searchOperation + "synonym('d0')" + encodeOr + "dire_name"
							+ encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // 감독명
							encodeOr + "prodc_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // 제작사명
							encodeOr + "rcv_no_view" + encodeAns + strMark + encodeSearchStr + strMark + // no3
																											// 접수번호
																											// rcv_no_view
																											// |string
																											// like
																											// X
							encodeOr + "sub_no" + encodeAns + strMark + encodeSearchStr + strMark + // no4
																									// 신청번호
																									// sub_no
																									// |string
																									// like
																									// X
							encodeOr + "onlin_offlin" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no7
																														// 온라인/오프라인
																														// |string
							encodeOr + "medi_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no9
																													// 매체명
																													// |string
							encodeOr + "prodc_natnl_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no15 제작자 국적 |string
							encodeOr + "mv_asso_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no23
																														// 영화
																														// 종류
																														// |string
							encodeOr + "leada_name" + encodeAns + strMark + encodeSearchStr + strMark+ searchOperation + // no25
																														// 주연명
																														// |string
							encodeOr + "frgnr_natnl_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no29 외국인 국적 |string
							encodeOr + "kind_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no35
																													// 종류
																													// |string
							encodeOr + "hope_grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no41 희망등급 |string
							encodeOr + "prod_year" + encodeAns + strMark + encodeSearchStr + strMark + // no42
																										// 제작년도
																										// |string
																										// like
																										// X
							encodeOr + "pfm_place_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no46
																															// 공연
																															// 장소명
																															// |string
							encodeOr + "repr_nm" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no61
																													// 대표자
																													// 성명
																													// |string
							encodeOr + "aplc_name" + encodeAns + strMark + encodeSearchStr + strMark+ searchOperation + // no63
																													// 신청회사
																													// |string
							encodeOr + "rt_no" + encodeAns + strMark + encodeSearchStr + strMark + // no69
																									// 등급분류번호
																									// |string
																									// like
																									// X
							encodeOr + "grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no71
																														// 결정등급
																														// |string
							encodeOr + "deter_opin_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no72 결정의견 |string
							encodeOr + "rt_std_name1" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no73
																														// 주제
																														// |string
							encodeOr + "rt_std_name2" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no74
																														// 선정성
																														// |string
							encodeOr + "rt_std_name3" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no75
																														// 폭력성
																														// |string
							encodeOr + "rt_std_name4" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no76
																														// 대사
																														// |string
							encodeOr + "rt_std_name5" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no77
																														// 공포
																														// |string
							encodeOr + "rt_std_name6" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no78
																														// 약물
																														// |string
							encodeOr + "rt_std_name7" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no79
																														// 모방위험
																														// |string
							encodeOr + "minor_malef_yn" + encodeAns + strMark + encodeSearchStr + strMark + // no80
																											// 연소자
																											// 유해성
																											// 여부
																											// |string
																											// like
																											// X
							encodeOr + "wdra_yn" + encodeAns + strMark + encodeSearchStr + strMark + // no86
																										// 취하여부
																										// |string
																										// like
																										// X
							encodeOr + "return_name" + encodeAns + strMark + encodeSearchStr + strMark + // no90
																											// 취하,
																											// 반납
																											// |string
																											// like
																											// X
							encodeOr + "proc_state_code" + encodeAns + strMark + encodeSearchStr + strMark + // no97
																												// 진행
																												// 상태
																												// 코드
																												// |string
																												// like
																												// X
							encodeOr + "state_code" + encodeAns + strMark + encodeSearchStr + strMark + // no98
																										// 상태코드
																										// |string
																										// like
																										// X
							encodeOr + "non_com_yn" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no109
																														// 불완전데이터
																														// 플래그
																														// |string
							encodeOr + "rt_core_harm_rsn_code1" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + // no114 내용정보표시항목1 |string
							encodeOr + "rt_core_harm_rsn_code2" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + // no114 내용정보표시항목2 |string
							encodeOr + "rt_core_harm_rsn_code3" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + // no114 내용정보표시항목3 |string
							encodeOr + "work_cont" + encodeAns + strMark + encodeSearchStr + strMark + "+someword+"  + // no81
																															// 작품내용(줄거리)
																															// |text
							encodeOr + "deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark + "+someword+"  + // no83
																															// 결정사유
																															// |text
							encodeOr + "major_opin_narrn_cont" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+"  + // no84 주요의견 |text
							encodeOr + "core_harm_rsn" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+" ; // no85 핵심유해사유 |text

					// int는 encode를 따로 안해줘도 무방함.
					if (searchStrInt != 0) {
						searchCol += encodeOr + "sub_rcv_date" + encodeAns + searchStrInt + "000000" + // no5
																										// 신청일자
																										// |
																										// int
								encodeOr + "rcv_date" + encodeAns + searchStrInt + "000000" + // no6
																								// 접수일자
																								// |
																								// int
								encodeOr + "rt_date" + encodeAns + searchStrInt + "000000" + // no70
																								// 등급분류일
																								// |
																								// int
								encodeOr + "pfm_pnum" + encodeAns + searchStrInt + // no28
																					// 외국인
																					// 인원수
																					// |
																					// int
								encodeOr + "contr_start_date" + encodeAns + searchStrInt + "000000" + // no44
																										// 계약
																										// 시작
																										// 일자
																										// |
																										// int
								encodeOr + "contr_end_date" + encodeAns + searchStrInt + "000000" + // no45
																									// 계약
																									// 종료
																									// 일자
																									// |
																									// int
								encodeOr + "ori_rt_no" + encodeAns + searchStrInt; // no59
																					// 신규추천번호
																					// |
																					// int
					}

					// 통합검색의 마무리 괄호
					searchCol += ")";

					// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
					// 0 원제명Url (텍스트지만, 중요도에 따라 맨위에 위치)
					searchCol_ori_title = "(" + "ori_title" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + "synonym('d0')" + ")";

					// 0 사용제명Url (텍스트지만, 중요도에 따라 맨위에 위치)
					searchCol_use_title = "(" + "use_title" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + "synonym('d0')" + ")";
					// 1. 감독명Url
					searchCol_dire_name = "(" + "dire_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 2. 제작사명Url
					searchCol_prodc_name = "(" + "prodc_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";

					// 3. 접수번호Url
					searchCol_rcv_no_view = "(" + "rcv_no_view" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 4. 신청번호Url
					searchCol_sub_no = "(" + "sub_no" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 5. 온라인/오프라인 Url
					searchCol_onlin_offlin = "(" + "onlin_offlin" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 6. 매체명
					searchCol_medi_name = "(" + "medi_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 7. 제작자 국적
					searchCol_prodc_natnl_name = "(" + "prodc_natnl_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 8. 영화 종류
					searchCol_mv_asso_name = "(" + "mv_asso_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 9. 주연명
					searchCol_leada_name = "(" + "leada_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + ")";
					// 10. 외국인 국적
					searchCol_frgnr_natnl_name = "(" + "frgnr_natnl_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 11. 종류
					searchCol_kind_name = "(" + "kind_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 12. 희망등급
					searchCol_hope_grade_name = "(" + "hope_grade_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 13. 제작년도
					searchCol_prod_year = "(" + "prod_year" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 14. 공연 장소명
					searchCol_pfm_place_name = "(" + "pfm_place_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 15. 대표자 성명
					searchCol_repr_nm = "(" + "repr_nm" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ ")";
					// 16. 신청회사
					searchCol_aplc_name = "(" + "aplc_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + ")";
					// 17. 등급분류번호
					searchCol_rt_no = "(" + "rt_no" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 18. 결정등급
					searchCol_grade_name = "(" + "grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 19. 결정의견
					searchCol_deter_opin_name = "(" + "deter_opin_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 20. 주제
					searchCol_rt_std_name1 = "(" + "rt_std_name1" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 21. 선정성
					searchCol_rt_std_name2 = "(" + "rt_std_name2" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 22. 폭력성
					searchCol_rt_std_name3 = "(" + "rt_std_name3" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 23. 대사
					searchCol_rt_std_name4 = "(" + "rt_std_name4" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 24. 공포
					searchCol_rt_std_name5 = "(" + "rt_std_name5" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 25. 약물
					searchCol_rt_std_name6 = "(" + "rt_std_name6" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 26. 모방위험
					searchCol_rt_std_name7 = "(" + "rt_std_name7" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 27. 연소자 유해성 여부
					searchCol_minor_malef_yn = "(" + "minor_malef_yn" + encodeAns + strMark + encodeSearchStr + strMark
							+ ")";
					// 28. 취하여부
					searchCol_wdra_yn = "(" + "wdra_yn" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 29. 취하,반납
					searchCol_return_name = "(" + "return_name" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 30. 진행 상태 코드
					searchCol_proc_state_code = "(" + "proc_state_code" + encodeAns + strMark + encodeSearchStr
							+ strMark + ")";
					// 31. 상태코드
					searchCol_state_code = "(" + "state_code" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 32. 불완전데이터 플래그
					searchCol_non_com_yn = "(" + "non_com_yn" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 33. 내용정보표시항목1
					searchCol_rt_core_harm_rsn_code1 = "(" + "rt_core_harm_rsn_code1" + encodeLike + strMark + "*"
							+ encodeSearchStr + "*" + strMark + ")";
					// 34. 내용정보표시항목2
					searchCol_rt_core_harm_rsn_code2 = "(" + "rt_core_harm_rsn_code2" + encodeLike + strMark + "*"
							+ encodeSearchStr + "*" + strMark + ")";
					// 35. 내용정보표시항목3
					searchCol_rt_core_harm_rsn_code3 = "(" + "rt_core_harm_rsn_code3" + encodeLike + strMark + "*"
							+ encodeSearchStr + "*" + strMark + ")";

					/********* 텍스트 타입 시작! **********/
					// 36. 작품내용(줄거리)
					searchCol_work_cont = "(" + "work_cont" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+" + ")";
					// 37. 결정사유
					searchCol_deter_rsn = "(" + "deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+" + ")";
					// 38. 주요의견
					searchCol_major_opin_narrn_cont = "(" + "major_opin_narrn_cont" + encodeAns + strMark
							+ "+someword+" + strMark + searchOperation + ")";
					// 39. 핵심유해사유
					searchCol_core_harm_rsn = "(" + "core_harm_rsn" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+" + ")";

					/*** 검색어가 숫자인 경우 추가되는 항목!! ***/
					if (searchStrInt != 0) {

						// 40. 신청일자
						searchCol_sub_rcv_date = "(" + "sub_rcv_date" + encodeAns + searchStrInt + "000000" + ")";
						// 41. 접수일자
						searchCol_rcv_date = "(" + "rcv_date" + encodeAns + searchStrInt + "000000" + ")";
						// 42. 등급분류일
						searchCol_rt_date = "(" + "rt_date" + encodeAns + searchStrInt + "000000" + ")";
						// 43. 외국인 인원수
						searchCol_pfm_pnum = "(" + "pfm_pnum" + encodeAns + searchStrInt + ")";
						// 44. 계약 시작 일자
						searchCol_contr_start_date = "(" + "contr_start_date" + encodeAns + searchStrInt + "000000"
								+ ")";
						// 45. 계약 종료 일자
						searchCol_contr_end_date = "(" + "contr_end_date" + encodeAns + searchStrInt + "000000" + ")";
						// 46. 신규추천번호
						searchCol_ori_rt_no = "(" + "ori_rt_no" + encodeAns + searchStrInt + ")";

					}

				}
				if (searchGubun.equals("title")) {
					searchCol += "(use_title" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ "synonym('d0')" + encodeOr + "ori_title" + encodeAns + strMark + encodeSearchStr
							+ strMark + searchOperation + "synonym('d0')" + ")";
				}
				if (searchGubun.equals("decision")) {
					searchCol += "(deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ "synonym('d0')" + encodeOr + "deter_basis" + encodeAns + strMark + encodeSearchStr
							+ strMark + searchOperation + "synonym('d0')" + ")";
				}

				// 20230202 if 검색어가 공백이지만, 상세조건에 값이 있다면? ex) 검색어는 빈값, 주연명 "마동석"
				// searchCol부터.. 전부 다~ medi_code값을 줌 --> 검색어가 빈값일떄 이 방법이 최선.
				if (searchStr.equals("NO_VALUE")) {
					searchCol = medi_code;
					searchCol_dire_name = medi_code;
					searchCol_prodc_name = medi_code;
					searchCol_rcv_no_view = medi_code;
					searchCol_sub_no = medi_code;
					searchCol_onlin_offlin = medi_code;
					searchCol_medi_name = medi_code;
					searchCol_prodc_natnl_name = medi_code;
					searchCol_mv_asso_name = medi_code;
					searchCol_leada_name = medi_code;
					searchCol_frgnr_natnl_name = medi_code;
					searchCol_kind_name = medi_code;
					searchCol_hope_grade_name = medi_code;
					searchCol_prod_year = medi_code;
					searchCol_pfm_place_name = medi_code;
					searchCol_repr_nm = medi_code;
					searchCol_aplc_name = medi_code;
					searchCol_rt_no = medi_code;
					searchCol_grade_name = medi_code;
					searchCol_deter_opin_name = medi_code;
					searchCol_rt_std_name1 = medi_code;
					searchCol_rt_std_name2 = medi_code;
					searchCol_rt_std_name3 = medi_code;
					searchCol_rt_std_name4 = medi_code;
					searchCol_rt_std_name5 = medi_code;
					searchCol_rt_std_name6 = medi_code;
					searchCol_rt_std_name7 = medi_code;
					searchCol_minor_malef_yn = medi_code;
					searchCol_wdra_yn = medi_code;
					searchCol_return_name = medi_code;
					searchCol_proc_state_code = medi_code;
					searchCol_state_code = medi_code;
					searchCol_non_com_yn = medi_code;
					searchCol_rt_core_harm_rsn_code1 = medi_code;
					searchCol_rt_core_harm_rsn_code2 = medi_code;
					searchCol_rt_core_harm_rsn_code3 = medi_code;

					// 텍스트타입
					searchCol_ori_title = medi_code;
					searchCol_use_title = medi_code;
					searchCol_work_cont = medi_code;
					searchCol_deter_rsn = medi_code;
					searchCol_major_opin_narrn_cont = medi_code;
					searchCol_core_harm_rsn = medi_code;

					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_sub_rcv_date = medi_code;
						searchCol_rcv_date = medi_code;
						searchCol_rt_date = medi_code;
						searchCol_pfm_pnum = medi_code;
						searchCol_contr_start_date = medi_code;
						searchCol_contr_end_date = medi_code;
						searchCol_ori_rt_no = medi_code;
					}
				} // if(searchStr == null) 끝.

				// 상세검색 일때만 뒤에 이걸 붙여주자!
				/**************** 일자 통합작업중 ***********************/
				if (detail_visibleChk.equals("Y")) {
					
					
	      //////////////////medi_code: 광고물 AND/OR 연산자 관련 변수 초기화 시작//////////////////////////////////////////////////////////////////
					//1. 상세검색 조건내에 선택한  연산자에 따라서 and를 할지 or을 할지 정하는 로직. 
					String encodeTotal = "";
					String encodeTotalChk = (String) paramMap.get("detailSearch_radio");
					
					if(encodeTotalChk != null){
						if(encodeTotalChk.equals("allword")){
							encodeTotal = encodeAnd;
						} else if(encodeTotalChk.equals("anyword")){
							encodeTotal = encodeOr;
						} 
					} else {
						encodeTotal = encodeAnd;
					}
					
					//2. 상세검색 조건문에 들어오면 AND연산자 해주고,  괄호도  열어주기. (상세검색 조건문 끝날떄쯤 괄호 닫아줄 것)
					if(encodeTotal.equals(encodeAnd)){
						searchCol += encodeAnd + encodeLGual + medi_code;
						searchCol_dire_name += encodeAnd + encodeLGual + medi_code;
						searchCol_prodc_name += encodeAnd + encodeLGual + medi_code;
						searchCol_rcv_no_view += encodeAnd + encodeLGual + medi_code;
						searchCol_sub_no += encodeAnd + encodeLGual + medi_code;
						searchCol_onlin_offlin += encodeAnd + encodeLGual + medi_code;
						searchCol_medi_name += encodeAnd + encodeLGual + medi_code;
						searchCol_prodc_natnl_name += encodeAnd + encodeLGual + medi_code;
						searchCol_mv_asso_name += encodeAnd + encodeLGual + medi_code;
						searchCol_leada_name += encodeAnd + encodeLGual + medi_code;
						searchCol_frgnr_natnl_name += encodeAnd + encodeLGual + medi_code;
						searchCol_kind_name += encodeAnd + encodeLGual + medi_code;
						searchCol_hope_grade_name += encodeAnd + encodeLGual + medi_code;
						searchCol_prod_year += encodeAnd + encodeLGual + medi_code;
						searchCol_pfm_place_name += encodeAnd + encodeLGual + medi_code;
						searchCol_repr_nm += encodeAnd + encodeLGual + medi_code;
						searchCol_aplc_name += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_no += encodeAnd + encodeLGual + medi_code;
						searchCol_grade_name += encodeAnd + encodeLGual + medi_code;
						searchCol_deter_opin_name += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name1 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name2 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name3 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name4 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name5 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name6 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name7 += encodeAnd + encodeLGual + medi_code;
						searchCol_minor_malef_yn += encodeAnd + encodeLGual + medi_code;
						searchCol_wdra_yn += encodeAnd + encodeLGual + medi_code;
						searchCol_return_name += encodeAnd + encodeLGual + medi_code;
						searchCol_proc_state_code += encodeAnd + encodeLGual + medi_code;
						searchCol_state_code += encodeAnd + encodeLGual + medi_code;
						searchCol_non_com_yn += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_core_harm_rsn_code1 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_core_harm_rsn_code2 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_core_harm_rsn_code3 += encodeAnd + encodeLGual + medi_code;
 
						// 텍스트타입
						searchCol_ori_title += encodeAnd + encodeLGual + medi_code;
						searchCol_use_title += encodeAnd + encodeLGual + medi_code;
						searchCol_work_cont += encodeAnd + encodeLGual + medi_code;
						searchCol_deter_rsn += encodeAnd + encodeLGual + medi_code;
						searchCol_major_opin_narrn_cont += encodeAnd + encodeLGual + medi_code;
						searchCol_core_harm_rsn += encodeAnd + encodeLGual + medi_code;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_sub_rcv_date += encodeAnd + encodeLGual + medi_code;
							searchCol_rcv_date += encodeAnd + encodeLGual + medi_code;
							searchCol_rt_date += encodeAnd + encodeLGual + medi_code;
							searchCol_pfm_pnum += encodeAnd + encodeLGual + medi_code;
							searchCol_contr_start_date += encodeAnd + encodeLGual + medi_code;
							searchCol_contr_end_date += encodeAnd + encodeLGual + medi_code;
							searchCol_ori_rt_no += encodeAnd + encodeLGual + medi_code;
						}

					} else if(encodeTotal.equals(encodeOr)){
						//만약 OR이라면 medi_code가 영향을 주니, 더미데이터를 넣어준다. 해당 구문은 medi_code가 존재하지 않는 더미데이터를 넣는것이다.
						searchCol += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_dire_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_prodc_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rcv_no_view += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_sub_no += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_onlin_offlin += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_medi_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_prodc_natnl_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_mv_asso_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_leada_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_frgnr_natnl_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_kind_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_hope_grade_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_prod_year += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_pfm_place_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_repr_nm += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_aplc_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_no += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_grade_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_deter_opin_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name1 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name2 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name3 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name4 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name5 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name6 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name7 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_minor_malef_yn += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_wdra_yn += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_return_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_proc_state_code += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_state_code += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_non_com_yn += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_core_harm_rsn_code1 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_core_harm_rsn_code2 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_core_harm_rsn_code3 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
 
						// 텍스트타입
						searchCol_ori_title += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_use_title += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_work_cont += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_deter_rsn += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_major_opin_narrn_cont += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_core_harm_rsn += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_sub_rcv_date += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
							searchCol_rcv_date += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
							searchCol_rt_date += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
							searchCol_pfm_pnum += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
							searchCol_contr_start_date += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
							searchCol_contr_end_date += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
							searchCol_ori_rt_no += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						}
					}
	     //////////////////medi_code: 광고물 AND/OR 연산자 관련 변수 초기화 끝//////////////////////////////////////////////////////////////////			
					
					// 일자보다 신청회사 먼저 붙여준다.
					if (!(detailAplc.equals("") || detailAplc == null)) {
						detailAplc = URLEncoder.encode(detailAplc, "UTF-8");
						// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
						searchCol += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_dire_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_prodc_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rcv_no_view += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_sub_no += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_onlin_offlin += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_medi_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_prodc_natnl_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_mv_asso_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_leada_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_frgnr_natnl_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_kind_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_hope_grade_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_prod_year += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_pfm_place_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_repr_nm += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_aplc_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_no += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_grade_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_deter_opin_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name1 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name2 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name3 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name4 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name5 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name6 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name7 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_minor_malef_yn += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_wdra_yn += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_return_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_proc_state_code += encodeTotal + encodeLGual + "aplc_name" +encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_state_code += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_non_com_yn += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_core_harm_rsn_code1 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_core_harm_rsn_code2 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_core_harm_rsn_code3 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
 
						// 텍스트타입
						searchCol_ori_title += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_use_title += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_work_cont += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_deter_rsn += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_major_opin_narrn_cont += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_core_harm_rsn += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_sub_rcv_date += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_rcv_date += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_rt_date += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_pfm_pnum += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_contr_start_date += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_contr_end_date += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_ori_rt_no += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
						}
					}

					String detail_date = "";
					if (!period_start.equals("") && !period_end.equals("")) {
						if (detail_dateGubun.equals("regdate")) {
							searchCol += encodeTotal + encodeLGual + "sub_rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 신청일자
							detail_date = encodeTotal + encodeLGual + "sub_rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;
						} else if (detail_dateGubun.equals("jubsudate")) {
							searchCol += encodeTotal + encodeLGual + "rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 접수일자
							detail_date = encodeTotal + encodeLGual + "rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;
						} else {
							searchCol += encodeTotal + encodeLGual + "rt_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 등급분류일자
							detail_date = encodeTotal + encodeLGual + "rt_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;
						}

						// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
						searchCol_dire_name += detail_date;
						searchCol_prodc_name += detail_date;
						searchCol_rcv_no_view += detail_date;
						searchCol_sub_no += detail_date;
						searchCol_onlin_offlin += detail_date;
						searchCol_medi_name += detail_date;
						searchCol_prodc_natnl_name += detail_date;
						searchCol_mv_asso_name += detail_date;
						searchCol_leada_name += detail_date;
						searchCol_frgnr_natnl_name += detail_date;
						searchCol_kind_name += detail_date;
						searchCol_hope_grade_name += detail_date;
						searchCol_prod_year += detail_date;
						searchCol_pfm_place_name += detail_date;
						searchCol_repr_nm += detail_date;
						searchCol_aplc_name += detail_date;
						searchCol_rt_no += detail_date;
						searchCol_grade_name += detail_date;
						searchCol_deter_opin_name += detail_date;
						searchCol_rt_std_name1 += detail_date;
						searchCol_rt_std_name2 += detail_date;
						searchCol_rt_std_name3 += detail_date;
						searchCol_rt_std_name4 += detail_date;
						searchCol_rt_std_name5 += detail_date;
						searchCol_rt_std_name6 += detail_date;
						searchCol_rt_std_name7 += detail_date;
						searchCol_minor_malef_yn += detail_date;
						searchCol_wdra_yn += detail_date;
						searchCol_return_name += detail_date;
						searchCol_proc_state_code += detail_date;
						searchCol_state_code += detail_date;
						searchCol_non_com_yn += detail_date;
						searchCol_rt_core_harm_rsn_code1 += detail_date;
						searchCol_rt_core_harm_rsn_code2 += detail_date;
						searchCol_rt_core_harm_rsn_code3 += detail_date;

						// 텍스트타입
						searchCol_ori_title += detail_date;
						searchCol_use_title += detail_date;
						searchCol_work_cont += detail_date;
						searchCol_deter_rsn += detail_date;
						searchCol_major_opin_narrn_cont += detail_date;
						searchCol_core_harm_rsn += detail_date;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_sub_rcv_date += detail_date;
							searchCol_rcv_date += detail_date;
							searchCol_rt_date += detail_date;
							searchCol_pfm_pnum += detail_date;
							searchCol_contr_start_date += detail_date;
							searchCol_contr_end_date += detail_date;
							searchCol_ori_rt_no += detail_date;
						}
					} else {
						//날짜가 '전체'라면 빈값으로 오기때문에 더미데이터로 medi_code를 넣어준다. 
						// 연산자가 AND라면 medi_code가 날짜의 '전체' 기능을 할 것이고,
						// 연산자가 OR이더라도 medi_code가 날짜의 '전체' 기능을 할것이다. --> 정보 : 처음에 무조건 더미데이터(medi_code~) 가 존재하기때문에 encodeTotal로 시작해도 무방함.
						searchCol += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_dire_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_prodc_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rcv_no_view += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_sub_no += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_onlin_offlin += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_medi_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_prodc_natnl_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_mv_asso_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_leada_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_frgnr_natnl_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_kind_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_hope_grade_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_prod_year += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_pfm_place_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_repr_nm += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_aplc_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_no += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_grade_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_deter_opin_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name1 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name2 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name3 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name4 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name5 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name6 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name7 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_minor_malef_yn += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_wdra_yn += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_return_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_proc_state_code += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_state_code += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_non_com_yn += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_core_harm_rsn_code1 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_core_harm_rsn_code2 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_core_harm_rsn_code3 += encodeTotal + encodeLGual + medi_code + encodeRGual;
 
						// 텍스트타입
						searchCol_ori_title += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_use_title += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_work_cont += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_deter_rsn += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_major_opin_narrn_cont += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_core_harm_rsn += encodeTotal + encodeLGual + medi_code + encodeRGual;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_sub_rcv_date += encodeTotal + encodeLGual + medi_code + encodeRGual;
							searchCol_rcv_date += encodeTotal + encodeLGual + medi_code + encodeRGual;
							searchCol_rt_date += encodeTotal + encodeLGual + medi_code + encodeRGual;
							searchCol_pfm_pnum += encodeTotal + encodeLGual + medi_code + encodeRGual;
							searchCol_contr_start_date += encodeTotal + encodeLGual + medi_code + encodeRGual;
							searchCol_contr_end_date += encodeTotal + encodeLGual + medi_code + encodeRGual;
							searchCol_ori_rt_no += encodeTotal + encodeLGual + medi_code + encodeRGual;
						}
					}
					
					
					
					if (((String) paramMap.get("detail_kindChk")).equals("ad01")) {
						String totalDetailChk = "";
						String detailAd = "";
						String encodeAd = "";
						String tmp_Ad = "";
						/////////////////////////////// 결정등급//////////////////////////////////
						if (paramMap.get("detailSearch_grade_name") != null) {
							if (paramMap.get("detailSearch_grade_name").equals("harmless")) {
								tmp_Ad = "유해성없음";
							} else if (paramMap.get("detailSearch_grade_name").equals("harm")) {
								tmp_Ad = "유해성있음";
							} else if (paramMap.get("detailSearch_grade_name").equals("All")) {
								tmp_Ad = "전체관람가";
							} else if (paramMap.get("detailSearch_grade_name").equals("18old")) {
								tmp_Ad = "청소년관람불가";
							}
							encodeAd = URLEncoder.encode(tmp_Ad, "UTF-8");
							detailAd = encodeTotal + encodeLGual + "grade_name" + encodeAns + strMark + encodeAd + strMark
									+ encodeRGual;
							totalDetailChk += detailAd;
						}
						///////////////////////////// 결정등급////////////////////////////////////
						///////////////////////////// 매체명////////////////////////////////////
						/*
						 * 광고물의 매체명 input box - > select box로 교체함에 따른 로직 변경 
						if (paramMap.get("detailSearch_medi_name_ad") != null) {
							encodeAd = URLEncoder.encode((String) paramMap.get("detailSearch_medi_name_ad"), "UTF-8");
							detailAd = encodeTotal + encodeLGual + "medi_name" + encodeLike + strMark + "*" + encodeAd
									+ "*" + strMark + encodeRGual;
							totalDetailChk += detailAd;
						}
						*/
						
						//select box가 기존에 없어서, tmp_movie_ad 변수를 새로 만듬 (select box가 많은 영화나 비디오 매체에서는 어떻게 쓰이는지 보면 참고가 될듯)
						if (paramMap.get("detailSearch_medi_name_ad") != null
								&& !((String) paramMap.get("detailSearch_medi_name_ad")).equals("")) {
							String tmp_movie_ad = (String) paramMap.get("detailSearch_medi_name_ad");
						    encodeAd = URLEncoder.encode(tmp_movie_ad, "UTF-8");
						    detailAd = encodeTotal + encodeLGual + "medi_name" + encodeAns + strMark
									+ encodeAd + strMark + encodeRGual;
							totalDetailChk += detailAd;
						}
						
						
						
						///////////////////////////// 매체명////////////////////////////////////
						///////////////////////////// 종류(신문,포스터
						///////////////////////////// 등등)////////////////////////////////////
						/*
						if (paramMap.get("detailSearch_kind_name_ad") != null) {
							encodeAd = URLEncoder.encode((String) paramMap.get("detailSearch_kind_name_ad"), "UTF-8");
							detailAd = encodeTotal + encodeLGual + "kind_name" + encodeLike + strMark + "*" + encodeAd
									+ "*" + strMark + encodeRGual;
							totalDetailChk += detailAd;
						}
						*/
						//select box가 기존에 없어서, tmp_movie_ad 변수를 새로 만듬 (select box가 많은 영화나 비디오 매체에서는 어떻게 쓰이는지 보면 참고가 될듯)
						if (paramMap.get("detailSearch_kind_name_ad") != null
								&& !((String) paramMap.get("detailSearch_kind_name_ad")).equals("")) {
							String tmp_movie_ad = (String) paramMap.get("detailSearch_kind_name_ad");
						    encodeAd = URLEncoder.encode(tmp_movie_ad, "UTF-8");
						    detailAd = encodeTotal + encodeLGual + "kind_name" + encodeAns + strMark
									+ encodeAd + strMark + encodeRGual;
							totalDetailChk += detailAd;
						}
						///////////////////////////// 종류(신문,포스터
						///////////////////////////// 등등)////////////////////////////////////
						///////////////////////////// 결정의견////////////////////////////////////
						if (paramMap.get("detailSearch_deter_opin_name_ad") != null) {
							encodeAd = URLEncoder.encode((String) paramMap.get("detailSearch_deter_opin_name_ad"),
									"UTF-8");
							detailAd = encodeTotal + encodeLGual + "deter_opin_name" + encodeLike + strMark + "*"
									+ encodeAd + "*" + strMark + encodeRGual;
							totalDetailChk += detailAd;
						}
						///////////////////////////// 결정의견////////////////////////////////////
						
					////////////////////////////등급분류번호////////////////////////////
					if (paramMap.get("detailSearch_rt_no_ad") != null) {
						encodeAd = URLEncoder.encode((String) paramMap.get("detailSearch_rt_no_ad"), "UTF-8");
						detailAd = encodeTotal + encodeLGual + "rt_no" + encodeAns + strMark
								+ encodeAd + strMark + encodeRGual;
						totalDetailChk += detailAd;
					}
					////////////////////////////등급분류번호////////////////////////////
						
						
						
						
						if (!totalDetailChk.equals("")) {
							// 이것이 빈값이 아닐떄만 뒤에 조건들을 붙여주자! , 즉 ad(광고물) 상세보기 전용조건이
							// 하나라도 있다면 붙여주고, 아예없다면 생략.
							searchCol += totalDetailChk;
							searchCol_dire_name += totalDetailChk;
							searchCol_prodc_name += totalDetailChk;
							searchCol_rcv_no_view += totalDetailChk;
							searchCol_sub_no += totalDetailChk;
							searchCol_onlin_offlin += totalDetailChk;
							searchCol_medi_name += totalDetailChk;
							searchCol_prodc_natnl_name += totalDetailChk;
							searchCol_mv_asso_name += totalDetailChk;
							searchCol_leada_name += totalDetailChk;
							searchCol_frgnr_natnl_name += totalDetailChk;
							searchCol_kind_name += totalDetailChk;
							searchCol_hope_grade_name += totalDetailChk;
							searchCol_prod_year += totalDetailChk;
							searchCol_pfm_place_name += totalDetailChk;
							searchCol_repr_nm += totalDetailChk;
							searchCol_aplc_name += totalDetailChk;
							searchCol_rt_no += totalDetailChk;
							searchCol_grade_name += totalDetailChk;
							searchCol_deter_opin_name += totalDetailChk;
							searchCol_rt_std_name1 += totalDetailChk;
							searchCol_rt_std_name2 += totalDetailChk;
							searchCol_rt_std_name3 += totalDetailChk;
							searchCol_rt_std_name4 += totalDetailChk;
							searchCol_rt_std_name5 += totalDetailChk;
							searchCol_rt_std_name6 += totalDetailChk;
							searchCol_rt_std_name7 += totalDetailChk;
							searchCol_minor_malef_yn += totalDetailChk;
							searchCol_wdra_yn += totalDetailChk;
							searchCol_return_name += totalDetailChk;
							searchCol_proc_state_code += totalDetailChk;
							searchCol_state_code += totalDetailChk;
							searchCol_non_com_yn += totalDetailChk;
							searchCol_rt_core_harm_rsn_code1 += totalDetailChk;
							searchCol_rt_core_harm_rsn_code2 += totalDetailChk;
							searchCol_rt_core_harm_rsn_code3 += totalDetailChk;

							// 텍스트타입
							searchCol_ori_title += totalDetailChk;
							searchCol_use_title += totalDetailChk;
							searchCol_work_cont += totalDetailChk;
							searchCol_deter_rsn += totalDetailChk;
							searchCol_major_opin_narrn_cont += totalDetailChk;
							searchCol_core_harm_rsn += totalDetailChk;

							// 숫자타입
							if (searchStrInt != 0) {
								searchCol_sub_rcv_date += totalDetailChk;
								searchCol_rcv_date += totalDetailChk;
								searchCol_rt_date += totalDetailChk;
								searchCol_pfm_pnum += totalDetailChk;
								searchCol_contr_start_date += totalDetailChk;
								searchCol_contr_end_date += totalDetailChk;
								searchCol_ori_rt_no += totalDetailChk;
							}
						}
					}
					
					// 상세검색 조건절 마무리 괄호
					searchCol += encodeRGual;
					searchCol_dire_name += encodeRGual;
					searchCol_prodc_name += encodeRGual;
					searchCol_rcv_no_view += encodeRGual;
					searchCol_sub_no += encodeRGual;
					searchCol_onlin_offlin += encodeRGual;
					searchCol_medi_name += encodeRGual;
					searchCol_prodc_natnl_name += encodeRGual;
					searchCol_mv_asso_name += encodeRGual;
					searchCol_leada_name += encodeRGual;
					searchCol_frgnr_natnl_name += encodeRGual;
					searchCol_kind_name += encodeRGual;
					searchCol_hope_grade_name += encodeRGual;
					searchCol_prod_year += encodeRGual;
					searchCol_pfm_place_name += encodeRGual;
					searchCol_repr_nm += encodeRGual;
					searchCol_aplc_name += encodeRGual;
					searchCol_rt_no += encodeRGual;
					searchCol_grade_name += encodeRGual;
					searchCol_deter_opin_name += encodeRGual;
					searchCol_rt_std_name1 += encodeRGual;
					searchCol_rt_std_name2 += encodeRGual;
					searchCol_rt_std_name3 += encodeRGual;
					searchCol_rt_std_name4 += encodeRGual;
					searchCol_rt_std_name5 += encodeRGual;
					searchCol_rt_std_name6 += encodeRGual;
					searchCol_rt_std_name7 += encodeRGual;
					searchCol_minor_malef_yn += encodeRGual;
					searchCol_wdra_yn += encodeRGual;
					searchCol_return_name += encodeRGual;
					searchCol_proc_state_code += encodeRGual;
					searchCol_state_code += encodeRGual;
					searchCol_non_com_yn += encodeRGual;
					searchCol_rt_core_harm_rsn_code1 += encodeRGual;
					searchCol_rt_core_harm_rsn_code2 += encodeRGual;
					searchCol_rt_core_harm_rsn_code3 += encodeRGual;

					// 텍스트타입
					searchCol_ori_title += encodeRGual;
					searchCol_use_title += encodeRGual;
					searchCol_work_cont += encodeRGual;
					searchCol_deter_rsn += encodeRGual;
					searchCol_major_opin_narrn_cont += encodeRGual;
					searchCol_core_harm_rsn += encodeRGual;

					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_sub_rcv_date += encodeRGual;
						searchCol_rcv_date += encodeRGual;
						searchCol_rt_date += encodeRGual;
						searchCol_pfm_pnum += encodeRGual;
						searchCol_contr_start_date += encodeRGual;
						searchCol_contr_end_date += encodeRGual;
						searchCol_ori_rt_no += encodeRGual;
					}
					
				}
				//detail_visibleChk 조건 끝
				
				//만약 따옴표 안에 단어가 인식이 된다면 이 로직 실행.
				if(!quote_searchStr.equals("")) {
					//통합검색이든, 메뉴명이든 뒤에 quote_searchCol 붙여주기 시작! 
					searchCol += quote_searchCol;
					searchCol_dire_name += quote_searchCol;
					searchCol_prodc_name += quote_searchCol;
					searchCol_rcv_no_view += quote_searchCol;
					searchCol_sub_no += quote_searchCol;
					searchCol_onlin_offlin += quote_searchCol;
					searchCol_medi_name += quote_searchCol;
					searchCol_prodc_natnl_name += quote_searchCol;
					searchCol_mv_asso_name += quote_searchCol;
					searchCol_leada_name += quote_searchCol;
					searchCol_frgnr_natnl_name += quote_searchCol;
					searchCol_kind_name += quote_searchCol;
					searchCol_hope_grade_name += quote_searchCol;
					searchCol_prod_year += quote_searchCol;
					searchCol_pfm_place_name += quote_searchCol;
					searchCol_repr_nm += quote_searchCol;
					searchCol_aplc_name += quote_searchCol;
					searchCol_rt_no += quote_searchCol;
					searchCol_grade_name += quote_searchCol;
					searchCol_deter_opin_name += quote_searchCol;
					searchCol_rt_std_name1 += quote_searchCol;
					searchCol_rt_std_name2 += quote_searchCol;
					searchCol_rt_std_name3 += quote_searchCol;
					searchCol_rt_std_name4 += quote_searchCol;
					searchCol_rt_std_name5 += quote_searchCol;
					searchCol_rt_std_name6 += quote_searchCol;
					searchCol_rt_std_name7 += quote_searchCol;
					searchCol_minor_malef_yn += quote_searchCol;
					searchCol_wdra_yn += quote_searchCol;
					searchCol_return_name += quote_searchCol;
					searchCol_proc_state_code += quote_searchCol;
					searchCol_state_code += quote_searchCol;
					searchCol_non_com_yn += quote_searchCol;
					searchCol_rt_core_harm_rsn_code1 += quote_searchCol;
					searchCol_rt_core_harm_rsn_code2 += quote_searchCol;
					searchCol_rt_core_harm_rsn_code3 += quote_searchCol;
	
					// 텍스트타입
					searchCol_ori_title += quote_searchCol;
					searchCol_use_title += quote_searchCol;
					searchCol_work_cont += quote_searchCol;
					searchCol_deter_rsn += quote_searchCol;
					searchCol_major_opin_narrn_cont += quote_searchCol;
					searchCol_core_harm_rsn += quote_searchCol;
	
					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_sub_rcv_date += quote_searchCol;
						searchCol_rcv_date += quote_searchCol;
						searchCol_rt_date += quote_searchCol;
						searchCol_pfm_pnum += quote_searchCol;
						searchCol_contr_start_date += quote_searchCol;
						searchCol_contr_end_date += quote_searchCol;
						searchCol_ori_rt_no += quote_searchCol;
					}
				}
				//통합검색이든, 메뉴명이든 뒤에 quote_searchCol 붙여주기 끝!
				
				/**************** 상세검색의 일자, 신청회사 통합작업중 ***********************/

				/**************
				 * ■■■■■■■■■■■■■*************************************우측사이드에 관한
				 * information 시작
				 * *************************************************■■■■■■■■■
				 **************************************************/
				List<Map<String, Object>> ad01_sideInformation = new ArrayList<Map<String, Object>>();
				if (searchGubun.equals("All")) {
					// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가

					// 0. (조건에 맞는)원제명 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_ori_title, "원제명"));

					// 0. (조건에 맞는)사용제명 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_use_title, "사용제명"));

					// 1. (조건에 맞는)감독명에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_dire_name, "감독명"));

					// 2. (조건에 맞는)제작사명에 대한 건수 및 데이터 조회 // walker에 정보 담기
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_prodc_name, "제작사명"));

					// 3. (조건에 맞는)접수번호에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rcv_no_view, "접수번호"));

					// 4. (조건에 맞는)신청번호에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_sub_no, "신청번호"));

					// 5. (조건에 맞는)온라인/오프라인에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_onlin_offlin, "온라인/오프라인"));

					// 6. (조건에 맞는)메체명에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_medi_name, "매체명"));

					// 7. (조건에 맞는)제작자 국적에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_prodc_natnl_name, "제작자 국적"));

					// 8. (조건에 맞는)영화 종류에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_mv_asso_name, "영화 종류"));

					// 9. (조건에 맞는)주연명 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_leada_name, "주연명"));

					// 10. (조건에 맞는)외국인 국적에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_frgnr_natnl_name, "외국인 국적"));

					// 11. (조건에 맞는)종류에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_kind_name, "종류"));

					// 12. (조건에 맞는)희망등급에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_hope_grade_name, "희망등급"));

					// 13. (조건에 맞는)제작년도에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_prod_year, "제작년도"));

					// 14. (조건에 맞는)공연 장소명에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_pfm_place_name, "공연 장소명"));

					// 15. (조건에 맞는)대표자 성명에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_repr_nm, "대표자 성명"));

					// 16. (조건에 맞는)신청회사에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_aplc_name, "신청회사"));

					// 17. (조건에 맞는)등급분류번호에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_no, "등급분류번호"));

					// 18. (조건에 맞는)결정등급에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_grade_name, "결정등급"));

					// 19. (조건에 맞는)결정의견에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_deter_opin_name, "결정의견"));

					// 20. (조건에 맞는)주제에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name1, "주제"));

					// 21. (조건에 맞는)선정성에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name2, "선정성"));

					// 22. (조건에 맞는)폭력성에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name3, "폭력성"));

					// 23. (조건에 맞는)대사에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name4, "대사"));

					// 24. (조건에 맞는)공포에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name5, "공포"));

					// 25. (조건에 맞는)약물에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name6, "약물"));

					// 26. (조건에 맞는)모방위험에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name7, "모방위험"));

					// 27. (조건에 맞는)연소자 유해성 여부에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_minor_malef_yn, "연소자 유해성"));

					// 28. (조건에 맞는)취하여부에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_wdra_yn, "취하여부"));

					// 29. (조건에 맞는)취하,반납에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_return_name, "취하,반납"));

					// 30. (조건에 맞는)진행 상태 코드에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_proc_state_code, "진행 상태 코드"));

					// 31. (조건에 맞는)상태코드에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_state_code, "상태코드"));

					// 32. (조건에 맞는)불완전데이터 플래그에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_non_com_yn, "불완전데이터 플래그"));

					// 33. (조건에 맞는)내용정보표시항목1에 대한 건수 및 데이터 조회
					ad01_sideInformation
							.add(mediurlEncodingResult(walker, paramMap, request, response, result_noticeUrl, encodeAnd,
									orderValue, medi_code, searchCol_rt_core_harm_rsn_code1, "내용정보표시항목1"));

					// 34. (조건에 맞는)내용정보표시항목2에 대한 건수 및 데이터 조회
					ad01_sideInformation
							.add(mediurlEncodingResult(walker, paramMap, request, response, result_noticeUrl, encodeAnd,
									orderValue, medi_code, searchCol_rt_core_harm_rsn_code2, "내용정보표시항목2"));

					// 35. (조건에 맞는)내용정보표시항목3에 대한 건수 및 데이터 조회
					ad01_sideInformation
							.add(mediurlEncodingResult(walker, paramMap, request, response, result_noticeUrl, encodeAnd,
									orderValue, medi_code, searchCol_rt_core_harm_rsn_code3, "내용정보표시항목3"));

					/**************** 텍스트 데이터 시작!!! ******************/
					// 36. (조건에 맞는)작품내용(줄거리) 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_work_cont, "작품내용(줄거리)"));

					// 37. (조건에 맞는)결정사유에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_deter_rsn, "결정사유"));

					// 38. (조건에 맞는)주요의견에 대한 건수 및 데이터 조회
					ad01_sideInformation
							.add(mediurlEncodingResult(walker, paramMap, request, response, result_noticeUrl, encodeAnd,
									orderValue, medi_code, searchCol_major_opin_narrn_cont, "주요의견"));

					// 39. (조건에 맞는)핵심유해사유에 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_core_harm_rsn, "핵심유해사유"));
					/**************** 텍스트 데이터 끝!!! ******************/

					/**************** 숫자 데이터 시작 (null처리)!!! ******************/
					if (searchStrInt != 0) {
						// 40. (조건에 맞는)신청일자에 대한 건수 및 데이터 조회
						ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
								result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_sub_rcv_date, "신청일자"));

						// 41. (조건에 맞는)접수일자에 대한 건수 및 데이터 조회
						ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
								result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rcv_date, "접수일자"));

						// 42. (조건에 맞는)등급분류일에 대한 건수 및 데이터 조회
						ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
								result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_date, "등급분류일"));

						// 43. (조건에 맞는)외국인 인원수에 대한 건수 및 데이터 조회
						ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
								result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_pfm_pnum, "외국인 인원수"));

						// 44. (조건에 맞는)계약 시작 일자에 대한 건수 및 데이터 조회
						ad01_sideInformation
								.add(mediurlEncodingResult(walker, paramMap, request, response, result_noticeUrl,
										encodeAnd, orderValue, medi_code, searchCol_contr_start_date, "계약 시작 일자"));

						// 45. (조건에 맞는)계약 종료 일자에 대한 건수 및 데이터 조회
						ad01_sideInformation
								.add(mediurlEncodingResult(walker, paramMap, request, response, result_noticeUrl,
										encodeAnd, orderValue, medi_code, searchCol_contr_end_date, "계약 종료 일자"));

						// 46. (조건에 맞는)신규추천번호에 대한 건수 및 데이터 조회
						ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
								result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_ori_rt_no, "신규추천번호"));
					}
					/**************** 숫자 데이터 끝 (null처리)!!! ******************/

					Collections.sort(ad01_sideInformation, new sideMenuCntComparator());
				} // if(searchGubun.equals("All")) 끝
				if (searchGubun.equals("title")) {
					// (조건에 맞는)원제명 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol, "원제명/사용제명"));
				}
				if (searchGubun.equals("decision")) {
					// (조건에 맞는)결정사유 대한 건수 및 데이터 조회
					ad01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol, "결정사유"));
				}
				// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
				walker.setValue("ad01_sideInformation", ad01_sideInformation);

				/**************
				 * ■■■■■■■■■■■■■***************************************우측사이드에 관한
				 * information
				 * 끝***********************************************■■■■■■■■■
				 **************************************************/

				/*************************************************************
				 * 검색어 처리 끝
				 *************************************************************************************************/

				totalUrl = result_noticeUrl + medi_code + encodeAnd + searchCol
						+ orderValue;
				// 결과내 재검색이라면 ? 시작
				if (((String) paramMap.get("result_re_search_chk")).equals("Y")
						&& (String) paramMap.get("ad01_historySearchQuery") != null) {
					totalUrl = (String) paramMap.get("ad01_historySearchQuery") + encodeAnd + "(" + medi_code
							+ encodeAnd + searchCol + ")" + orderValue;
				}
				// 결과내 재검색이라면 ? 끝

				// 이전쿼리 저장 시작
				String historySearchQuery = "";
				historySearchQuery = result_noticeUrl + "(" + medi_code + encodeAnd + searchCol + ")";
				// 만약 결과내재검색을 통한 검색이었다면, historySearchQuery(이전쿼리)는 , 이전쿼리 +
				// encodeAnd + 현재쿼리 일 것이다.
				if (((String) paramMap.get("result_re_search_chk")).equals("Y")
						&& (String) paramMap.get("ad01_historySearchQuery") != null) {
					historySearchQuery = (String) paramMap.get("ad01_historySearchQuery") + encodeAnd + "(" + medi_code
							+ encodeAnd + searchCol + ")";
				}
				paramMap.put("ad01_historySearchQuery", historySearchQuery);
				// 이전쿼리 저장 끝

			} else {
				// 검색어가 공백이라면? // 통합검색이든, 제목이든 내용이든 // 영화매체에 관한 모든 데이터가 등장해줘야함
				totalUrl = result_noticeUrl + medi_code + orderValue;
			}

			/*************************************************************
			 * 얻어낸 Url 결과물로 추가적인 작업시작 / 페이징 처리 시작
			 *************************************************************************************************/
			String paging_totalUrl = totalUrl + "&pagelength=" + detailresultLine + "&offset=" + offset + "&limit="
					+ detailresultLine + "&hilite-fields="
					+ "%7B%22use_title%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22ori_title%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22aplc_name%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22rt_no%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22grade_name%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22rt_no%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22work_cont%22%3A%7B%22length%22%3A3000%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22deter_rsn%22%3A%7B%22length%22%3A3000%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D";

				
			
			List<Object> dataList = pagingURLEncodingFunc(paging_totalUrl);
			ad01.put("total_count_page", (Integer) dataList.get(0));
			walker.setValue("ad01_resultMapList", (List<Map<String, String>>) dataList.get(1));
			/*************************************************************
			 * 얻어낸 Url 결과물로 추가적인 작업시작 / 페이징 처리 시작
			 *************************************************************************************************/
			// totalURLCntFunc로 총합 갯수만 가져오고있다. -> 데이터가 필요하다면 해당 메소드를 안에
			// resultMap을 확인하면 됨.
			Integer total_count = totalURLCntFunc(totalUrl);
			ad01.put("total_count", total_count);
			walker.setValue("ad01", ad01);
		}
		///////////////////////////////////////////// 광고물 끝 (17503, 17504,
		///////////////////////////////////////////// 17505, 17506,
		///////////////////////////////////////////// 17514)///////////////////////////////////////////////////////////////////////////////////////////////////

	}

	/**
	 * 
	 * @param walker
	 * @param paramMap
	 * @param request
	 * @param response
	 * @throws Exception
	 *             공연 매체에 대한 데이터를 추출
	 */

	public void perform01(Walker walker, Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// null처리는 필수!
		// 파라미터 목록 가져오기. * 검색어, 검색구분, 섹션구분
		String searchStr = (String) paramMap.get("searchStr");
		String searchGubun = (String) paramMap.get("searchGubun");
		String[] sectionGubun = null;
		if (request.getParameterValues("sectionGubun") == null) {
			sectionGubun = new String[] { "Total" };
		} else {
			sectionGubun = request.getParameterValues("sectionGubun");
		}
		List<String> sectionGubunList = new ArrayList<String>(Arrays.asList(sectionGubun));

		/*************** 검색어에 숫자가 들어올떄 *******************************/
		long searchStrInt = 0;
		if (paramMap.get("searchStr") != null && ((String) paramMap.get("searchStr")).matches("[-+]?\\d*\\.?\\d+")) {
			searchStrInt = Long.valueOf((String) paramMap.get("searchStr"));
		}
		/*************** 검색어에 숫자가 들어올떄 *******************************/
		// 최종 URL
		String totalUrl = "";
		// 공통 URL
		String result_noticeUrl = "http://192.168.11.242:7577/search5?select=*&from=result_notice.result_notice&where=";
		String result_opinUrl = "http://192.168.11.242:7577/search5?select=*&from=result_opin.result_opin&where=";
		String result_fileUrl = "http://192.168.11.242:7577/search5?select=*&from=result_file.result_file&where=";
		String result_imageUrl = "http://192.168.11.242:7577/search5?select=*&from=result_image.result_image&where=";
		String result_moniUrl = "http://192.168.11.242:7577/search5?select=*&from=result_moni.result_moni&where=";
		String result_surveyUrl = "http://192.168.11.242:7577/search5?select=*&from=result_survey.result_survey&where=";
		// 공통 인코딩 목록
		String encode = "UTF-8";
		String encodeSearchStr = URLEncoder.encode(searchStr, encode); // 검색어
		String encodeSearchGubun = URLEncoder.encode(searchGubun, encode); // 검색구분(통합검색,
																			// 제목,
																			// 내용,
																			// 결정사유
																			// 등등)
		String encodeAnd = URLEncoder.encode(" and ", encode);
		String encodeOr = URLEncoder.encode(" or ", encode);
		String encodeAns = URLEncoder.encode("=", encode);
		String encodeInOpen = URLEncoder.encode(" in{", encode);
		String encodeInClose = URLEncoder.encode("}", encode);
		String encodeLGual = "(";
		String encodeRGual = ")";
		String encodeLike = URLEncoder.encode(" like ", encode);
		String strMark = URLEncoder.encode("'", encode);
		String between = URLEncoder.encode(" between ", encode);

		// where절의 text타입에만 사용가능.
		String allword = URLEncoder.encode(" allword ", encode);
		String anyword = URLEncoder.encode(" anyword ", encode);
		String natural = URLEncoder.encode(" natural ", encode);

		// 동의어 검색
		String synonym = URLEncoder.encode(" synonym ", encode);

		// 카테고리 정렬 ex) ORDER BY $CATEGORYFIELD(field_name(domain), keyword)
		String categoryOrder = URLEncoder.encode(" $categoryfield(use_title(), 검색어넣기 ", encode);

		/**
		 * 17501 : 국내영화 17502 : 국외영화 17505 : 광고영화 17511 : 국내비디오 17512 : 국외비디오
		 */

		// medi_code 인코딩 목록
		String medi_mv = URLEncoder.encode("'17501','17502'", encode);
		String medi_ad = URLEncoder.encode("'17503','17504','17505','17506','17514'", encode);
		String medi_video = URLEncoder.encode("'17511','17512'", encode);
		String medi_perform = URLEncoder.encode("'17521','17522','17523','17524'", encode);

		/*
		 * //첫 로딩 시 데이터를 표시해주는 역할을 함. if(searchGubun == null ){ searchGubun =
		 * "All"; } else if(searchGubun.trim().isEmpty()){ searchGubun = "All";
		 * }
		 */

		// 부가설명 : 처음 orderValue로 JSP에서 넘어온 정렬값을 받아서, 넘어온 정렬값을 기준으로 url인코딩을 실시한다.
		String orderValue = (String) request.getParameter("orderValue");
		if (orderValue == null || orderValue.equals("")) {
			orderValue = URLEncoder.encode(" order by $match(rt_no) desc, $relevance desc, $rowid desc ", encode);
		}
		if (orderValue.equals("orderRelevance")) {
			orderValue = URLEncoder.encode(" order by $match(rt_no) desc, $relevance desc, $rowid desc ", encode);
		}
		if (orderValue.equals("orderGa")) {
			orderValue = "%20order%20by%20use_title_sort%20asc%20%2c%20ori_title_sort%20asc%20";
		}
		if (orderValue.equals("orderDate")) {
			orderValue = "%20order%20by%20rcv_date%20desc%20";
		}

		//
		String groupByTest = URLEncoder.encode(" order by $relevance desc ", encode);

		// 2022 12 15 페이징 처리 시작

		// 1. sectionGubun을 하나만 선택하거나, -더보기 클릭 시, sectionGubun에는 딱 하나의 데이터가
		// 들어있고, 페이지는 1페이지로 이동한다. -->sectionGubun이 딱 하나라면 (Total)이 아닐떄 , 페이징목록이
		// 보이게끔 유도하기.
		// 페이지는 페이지 목록 버튼 시 마다 2페이지 3페이지 이동한다... 그러다가 다시 검색버튼 같은것을 누르면 1페이지로
		// 초기화!

		// 기존 display가 limit로 대체

		int cPage = 0;
		int offset = 0;
		int pagelength = 0;

		/**************************
		 * 20221223 페이징처리를 위한 새로운 로직 (영화, 비디오 반영완료)
		 *********************************************/
		if (((String) paramMap.get("isPaging")).equals("Y")) {
			cPage = (Integer) paramMap.get("cPage");
			offset = (Integer) paramMap.get("offset");
			pagelength = (Integer) paramMap.get("pagelength");
			offset = (cPage - 1) * pagelength;
		} else {
			cPage = 1;
			offset = 0;
			pagelength = 10;
			offset = (cPage - 1) * pagelength;
		}

		/*******************************************************************/

		/**************** 상세보기 시작 ********************************/
		// 20221216 상세보기에 관련된 것들 맨 위로 빼기 (기본값이 존재함)
		// 하지만 상세보기를 하지 않는다면 이 값이 null이라서 오류가뜸 ~~~~~~~~~~~~~~~~~~~~~~~~~~~

		// 상세보기를 클릭했는지 여부
		String detail_visibleChk = (String) paramMap.get("detail_visibleChk");

		// 연산자 가져오기
		String searchOperation = "";

		// 결과 수
		int detailresultLine = 3;
		try {
			if ((String) paramMap.get("detailSearch_resultLine") != null) {
				detailresultLine = Integer.valueOf((String) paramMap.get("detailSearch_resultLine"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (detailresultLine != 3 && (String) paramMap.get("detailSearch_resultLine") != null) {
				detailresultLine = detailresultLine;
			} else {
				detailresultLine = 3;
			}
		}

		try {
			if (detail_visibleChk.equals("Y")) {
				searchOperation = (String) paramMap.get("detailSearch_radio");
			} else {
				searchOperation = "natural";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_radio") == null) {
				searchOperation = "natural";
			}
		}

		if (searchOperation.equals("allword")) {
			searchOperation = natural;
		} else if (searchOperation.equals("anyword")) {
			searchOperation = natural;
		} else if (searchOperation.equals("natural")) {
			searchOperation = natural;
		} else {
			searchOperation = natural;
		}
		
		if(searchStr.indexOf("-") != -1){
			searchOperation = allword;
		}

		// 신청회사 null 처리
		String detailAplc = "";
		try {
			if ((String) paramMap.get("detailSearch_aplc") != null) {
				detailAplc = (String) paramMap.get("detailSearch_aplc");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_aplc") == null) {
				detailAplc = "";
			}
		}

		// 상세보기-일자 데이터 가져오기 및 null 처리 시작

		// 시작날짜
		String period_start = "";
		try {
			if ((String) paramMap.get("period_start") != null) {
				period_start = (String) paramMap.get("period_start");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("period_start") == null) {
				period_start = "";
			}
		}

		// 종료날짜
		String period_end = "";
		try {
			if ((String) paramMap.get("period_end") != null) {
				period_end = (String) paramMap.get("period_end");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("period_end") == null) {
				period_end = "";
			}
		}

		// 등급분류일자, 신청일자 , 접수일자 구분
		String detail_dateGubun = "";
		try {
			if ((String) paramMap.get("detailSearch_date") != null) {
				detail_dateGubun = (String) paramMap.get("detailSearch_date");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_date") == null) {
				detail_dateGubun = "gradedate";
			}
		}

		if (!period_start.equals("")) {
			period_start = period_start.substring(0, 4) + period_start.substring(5, 7) + period_start.substring(8, 10)
					+ "000000";
		}
		if (!period_end.equals("")) {
			period_end = period_end.substring(0, 4) + period_end.substring(5, 7) + period_end.substring(8, 10)
					+ "000000";
		}

		// 듈 중에 하나를 선택안하고 상세검색을 하면 --> 전체날짜로 인식하여 데이터검색하기
		if ((period_start.equals("") || period_end.equals("")) && !(period_start.equals("") && period_end.equals(""))) {
			period_start = "";
			period_end = "";

		}
		// 상세보기-일자 데이터 가져오기 및 null 처리 끝

		/**************** 상세보기 끝 ********************************/

		// 하지만 상세보기를 하지 않는다면 이 값이 null이라서 오류가뜸 ~~~~~~~~~~~~~~~~~~~~~~~~~~~

		
		
		//따옴표 기능 추가 시작

		//따옴표기능 변수1 : 반드시 포함해야 하는 따옴표 안의 단어.
		String quote_searchStr_origin = "";
		//따옴표기능 변수2 : 반드시 포함해야 하는 따옴표 안의 단어 (인코딩 받아줄 변수).
		String quote_searchStr = ""; 
		
		

		//따옴표기능 로직1 : 따옴표의 인덱스 위치를 추출한다.
		String word = "\""; 
		int searchStrIndex = searchStr.indexOf(word);
		List<Integer> searchStrIndexList = new ArrayList<Integer>();
		 while(searchStrIndex != -1) {
			 searchStrIndexList.add(searchStrIndex);
			 searchStrIndex = searchStr.indexOf(word, searchStrIndex+word.length()); 
		 }
		
		 
		 
		//따옴표기능 로직2 : 따옴표의 인덱스가 2개이상 나왔다면, 따옴표가 4개있든 7개 있든, 제일 첫번째로 나오는 따옴표 2개의 쌍을 구하고, 그 따옴표로 깜사져 있는 단어를 추출한다.
		if(searchStrIndexList.size() >= 2 ) {
			 int firstIndex = searchStrIndexList.get(0);
			 int lastIndex = searchStrIndexList.get(1);
			 quote_searchStr_origin = searchStr.substring(firstIndex + 1, lastIndex);  
			 quote_searchStr = URLEncoder.encode(quote_searchStr_origin, encode);
		 }
		
		 
		 
		 
		 //따옴표기능 로직3 : 따옴표의 인덱스가 일단 하나 이상 등장한다면, 한개 이상일 경우는 따옴표를 제거해주고, 따옴표가 2개이상인 경우에는 위에서 얻어온 '따옴표 안'의 단어를 따로 추출하여 인코딩한다.
		             // --> 차후에 따옴표 검색이 이뤄질때, 헤어질 결심 "수사관" 검색 시, --> (헤어질 결심 ) AND ("수사관") 이런식으로 검색 하게끔 해줌.
		 if(searchStrIndexList.size() >= 1 ){
			 
			String searchStr2 = searchStr.replaceAll("\"", "");
			String searchStr3 = searchStr2.replaceAll( quote_searchStr_origin , "").trim(); 
			if(!searchStr3.equals("") && searchStr3 != null){
				encodeSearchStr = URLEncoder.encode(searchStr3, encode); // 검색어
			}
		 }
		 
		 
		 
		 // 따옴표에 들어간 단어에 맞춰서 조건을 추가해주기 위해서 셋팅하는 작업 
		 String quote_searchCol = "";
		 if(!quote_searchStr.equals("")) {
    /***********************************************perform01() quote_searchStr start*************************************************************************/	 
		 quote_searchCol += encodeAnd + "(" + "use_title" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" +  "synonym('d0')" 
						+ encodeOr + "ori_title" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" +  "synonym('d0')" 
					    + encodeOr + "dire_name" + encodeAns + strMark + quote_searchStr + strMark // 감독명
						+ encodeOr + "prodc_name" + encodeAns + strMark + quote_searchStr + strMark // 제작사명
						+ encodeOr + "rcv_no_view" + encodeAns + strMark + quote_searchStr + strMark + // no3
																										// 접수번호
																										// rcv_no_view
																										// |string
																										// like
																										// X
						encodeOr + "sub_no" + encodeAns + strMark + quote_searchStr + strMark + // no4
																								// 신청번호
																								// sub_no
																								// |string
																								// like
																								// X
						encodeOr + "onlin_offlin" + encodeAns + strMark + quote_searchStr + strMark +  // no7
																													// 온라인/오프라인
																													// |string
						encodeOr + "medi_name" + encodeAns + strMark + quote_searchStr + strMark +  // no9
																												// 매체명
																												// |string
						encodeOr + "prodc_natnl_name" + encodeAns + strMark + quote_searchStr + strMark
						+ // no15 제작자 국적 |string
						encodeOr + "mv_asso_name" + encodeAns + strMark + quote_searchStr + strMark +  // no23
																													// 영화
																													// 종류
																													// |string
						encodeOr + "leada_name" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" +   // no25
																													// 주연명
																													// |string
						encodeOr + "frgnr_natnl_name" + encodeAns + strMark + quote_searchStr + strMark
						+ // no29 외국인 국적 |string
						encodeOr + "kind_name" + encodeAns + strMark + quote_searchStr + strMark + // no35
																												// 종류
																												// |string
						encodeOr + "hope_grade_name" + encodeAns + strMark + quote_searchStr + strMark
						+ // no41 희망등급 |string
						encodeOr + "prod_year" + encodeAns + strMark + quote_searchStr + strMark + // no42
																									// 제작년도
																									// |string
																									// like
																									// X
						encodeOr + "pfm_place_name" + encodeAns + strMark + quote_searchStr + strMark + // no46
																														// 공연
																														// 장소명
																														// |string
						encodeOr + "repr_nm" + encodeAns + strMark + quote_searchStr + strMark + // no61
																												// 대표자
																												// 성명
																												// |string
						encodeOr + "aplc_name" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + // no63
																												// 신청회사
																												// |string
						encodeOr + "rt_no" + encodeAns + strMark + quote_searchStr + strMark + // no69
																								// 등급분류번호
																								// |string
																								// like
																								// X
						encodeOr + "grade_name" + encodeAns + strMark + quote_searchStr + strMark + // no71
																													// 결정등급
																													// |string
						encodeOr + "deter_opin_name" + encodeAns + strMark + quote_searchStr + strMark
						+ // no72 결정의견 |string
						encodeOr + "rt_std_name1" + encodeAns + strMark + quote_searchStr + strMark + // no73
																													// 주제
																													// |string
						encodeOr + "rt_std_name2" + encodeAns + strMark + quote_searchStr + strMark + // no74
																													// 선정성
																													// |string
						encodeOr + "rt_std_name3" + encodeAns + strMark + quote_searchStr + strMark + // no75
																													// 폭력성
																													// |string
						encodeOr + "rt_std_name4" + encodeAns + strMark + quote_searchStr + strMark + // no76
																													// 대사
																													// |string
						encodeOr + "rt_std_name5" + encodeAns + strMark + quote_searchStr + strMark + // no77
																													// 공포
																													// |string
						encodeOr + "rt_std_name6" + encodeAns + strMark + quote_searchStr + strMark + // no78
																													// 약물
																													// |string
						encodeOr + "rt_std_name7" + encodeAns + strMark + quote_searchStr + strMark + // no79
																													// 모방위험
																													// |string
						encodeOr + "minor_malef_yn" + encodeAns + strMark + quote_searchStr + strMark + // no80
																										// 연소자
																										// 유해성
																										// 여부
																										// |string
																										// like
																										// X
						encodeOr + "wdra_yn" + encodeAns + strMark + quote_searchStr + strMark + // no86
																									// 취하여부
																									// |string
																									// like
																									// X
						encodeOr + "return_name" + encodeAns + strMark + quote_searchStr + strMark + // no90
																										// 취하,
																										// 반납
																										// |string
																										// like
																										// X
						encodeOr + "proc_state_code" + encodeAns + strMark + quote_searchStr + strMark + // no97
																											// 진행
																											// 상태
																											// 코드
																											// |string
																											// like
																											// X
						encodeOr + "state_code" + encodeAns + strMark + quote_searchStr + strMark + // no98
																									// 상태코드
																									// |string
																									// like
																									// X
						encodeOr + "non_com_yn" + encodeAns + strMark + quote_searchStr + strMark + // no109
																													// 불완전데이터
																													// 플래그
																													// |string
						encodeOr + "rt_core_harm_rsn_code1" + encodeAns + strMark + quote_searchStr + strMark + // no114 내용정보표시항목1 |string
						encodeOr + "rt_core_harm_rsn_code2" + encodeAns + strMark + quote_searchStr + strMark + // no114 내용정보표시항목2 |string
						encodeOr + "rt_core_harm_rsn_code3" + encodeAns + strMark + quote_searchStr + strMark + // no114 내용정보표시항목3 |string
						encodeOr + "work_cont"             + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + // no81// 작품내용(줄거리) // |text
						encodeOr + "deter_rsn"             + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" +  // no83 // 결정사유 // |text
						encodeOr + "major_opin_narrn_cont" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + // no84 주요의견 |text
						encodeOr + "core_harm_rsn"         + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+"; // no85 핵심유해사유 |text
				// int는 encode를 따로 안해줘도 무방함.
				if (searchStrInt != 0) {
					quote_searchCol += encodeOr + "sub_rcv_date" + encodeAns + searchStrInt + "000000" + // no5
																									// 신청일자
																									// |
																									// int
							encodeOr + "rcv_date" + encodeAns + searchStrInt + "000000" + // no6
																							// 접수일자
																							// |
																							// int
							encodeOr + "rt_date" + encodeAns + searchStrInt + "000000" + // no70
																							// 등급분류일
																							// |
																							// int
							encodeOr + "pfm_pnum" + encodeAns + searchStrInt + // no28
																				// 외국인
																				// 인원수
																				// |
																				// int
							encodeOr + "contr_start_date" + encodeAns + searchStrInt + "000000" + // no44
																									// 계약
																									// 시작
																									// 일자
																									// |
																									// int
							encodeOr + "contr_end_date" + encodeAns + searchStrInt + "000000" + // no45
																								// 계약
																								// 종료
																								// 일자
																								// |
																								// int
							encodeOr + "ori_rt_no" + encodeAns + searchStrInt; // no59
																				// 신규추천번호
																				// |
																				// int
				}
				// 통합검색의 마무리 괄호
				quote_searchCol += ")";
				//quote_searchCol만 셋팅 하고, 왜 다른 사이드는 셋팅 하지 않는지?
				//위에 보다시피 quote_searchCol 이 모든 컬럼에 "따옴표에 들어간 단어"를 매칭시켜서 가져오고있다 
				// 나중에 제일 밑에 detail_visibleChk를 쓰는 로직 밑에 보면, 통합검색이든 사이드메뉴든 뒤에 quote_searchCol를 붙여주는 작업을 하고 있다.
		 }
		 /***********************************************perform01() quote_searchStr start*************************************************************************/
		 // 따옴표 기능 추가 끝
		
		
		///////////////////////////////////////////// 공연추천 시작 (17521, 17522,
		///////////////////////////////////////////// 17523,
		///////////////////////////////////////////// 17524)///////////////////////////////////////////////////////////////////////////////////////////////////
		if (sectionGubunList.contains("perform01") || sectionGubunList.contains("Total")) {
			Map<String, Object> perform01 = new HashMap<String, Object>();
			String medi_code = "medi_code" + encodeInOpen + medi_perform + encodeInClose;

			if (searchStr != null) {
				/*************************************************************
				 * 검색어 처리 시작
				 *************************************************************************************************/

				// 검색구분은 if절로서 쓰이고, 검색어는 where절 안에 쓰임으로써, 직접적인 쿼리에 영향을 줌.
				String searchCol = ""; // where절을 담을 변수
				String searchCol_ori_title = "";
				String searchCol_use_title = "";
				String searchCol_dire_name = "";
				String searchCol_prodc_name = "";
				String searchCol_rcv_no_view = "";
				String searchCol_sub_no = "";
				String searchCol_onlin_offlin = "";
				String searchCol_medi_name = "";
				String searchCol_prodc_natnl_name = "";
				String searchCol_mv_asso_name = "";
				String searchCol_leada_name = "";
				String searchCol_frgnr_natnl_name = "";
				String searchCol_kind_name = "";
				String searchCol_hope_grade_name = "";
				String searchCol_prod_year = "";
				String searchCol_pfm_place_name = "";
				String searchCol_repr_nm = "";
				String searchCol_aplc_name = "";
				String searchCol_rt_no = "";
				String searchCol_grade_name = "";
				String searchCol_deter_opin_name = "";
				String searchCol_rt_std_name1 = "";
				String searchCol_rt_std_name2 = "";
				String searchCol_rt_std_name3 = "";
				String searchCol_rt_std_name4 = "";
				String searchCol_rt_std_name5 = "";
				String searchCol_rt_std_name6 = "";
				String searchCol_rt_std_name7 = "";
				String searchCol_minor_malef_yn = "";
				String searchCol_wdra_yn = "";
				String searchCol_return_name = "";
				String searchCol_proc_state_code = "";
				String searchCol_state_code = "";
				String searchCol_non_com_yn = "";
				String searchCol_rt_core_harm_rsn_code1 = "";
				String searchCol_rt_core_harm_rsn_code2 = "";
				String searchCol_rt_core_harm_rsn_code3 = "";
				String searchCol_work_cont = "";
				String searchCol_deter_rsn = "";
				String searchCol_major_opin_narrn_cont = "";
				String searchCol_core_harm_rsn = "";
				String searchCol_sub_rcv_date = "";
				String searchCol_rcv_date = "";
				String searchCol_rt_date = "";
				String searchCol_pfm_pnum = "";
				String searchCol_contr_start_date = "";
				String searchCol_contr_end_date = "";
				String searchCol_ori_rt_no = "";

				// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
				if (searchGubun.equals("All")) {
					searchCol += "(" + "use_title" + encodeAns + strMark + encodeSearchStr + strMark + "+allword+" + "synonym('d0')" + 
								encodeOr + "use_title" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + "synonym('d0')" + 
							    encodeOr + "ori_title" + encodeAns + strMark
							+ encodeSearchStr + strMark + searchOperation + "synonym('d0')" + encodeOr + "dire_name"
							+ encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // 감독명
							encodeOr + "prodc_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // 제작사명
							encodeOr + "rcv_no_view" + encodeAns + strMark + encodeSearchStr + strMark + // no3
																											// 접수번호
																											// rcv_no_view
																											// |string
																											// like
																											// X
							encodeOr + "sub_no" + encodeAns + strMark + encodeSearchStr + strMark + // no4
																									// 신청번호
																									// sub_no
																									// |string
																									// like
																									// X
							encodeOr + "onlin_offlin" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no7
																														// 온라인/오프라인
																														// |string
							encodeOr + "medi_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no9
																													// 매체명
																													// |string
							encodeOr + "prodc_natnl_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no15 제작자 국적 |string
							encodeOr + "mv_asso_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no23
																														// 영화
																														// 종류
																														// |string
							encodeOr + "leada_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + // no25
																														// 주연명
																														// |string
							encodeOr + "frgnr_natnl_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no29 외국인 국적 |string
							encodeOr + "kind_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no35
																													// 종류
																													// |string
							encodeOr + "hope_grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no41 희망등급 |string
							encodeOr + "prod_year" + encodeAns + strMark + encodeSearchStr + strMark + // no42
																										// 제작년도
																										// |string
																										// like
																										// X
							encodeOr + "pfm_place_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no46
																															// 공연
																															// 장소명
																															// |string
							encodeOr + "repr_nm" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no61
																													// 대표자
																													// 성명
																													// |string
							encodeOr + "aplc_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + // no63
																													// 신청회사
																													// |string
							encodeOr + "rt_no" + encodeAns + strMark + encodeSearchStr + strMark + // no69
																									// 등급분류번호
																									// |string
																									// like
																									// X
							encodeOr + "grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no71
																														// 결정등급
																														// |string
							encodeOr + "deter_opin_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no72 결정의견 |string
							encodeOr + "rt_std_name1" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no73
																														// 주제
																														// |string
							encodeOr + "rt_std_name2" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no74
																														// 선정성
																														// |string
							encodeOr + "rt_std_name3" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no75
																														// 폭력성
																														// |string
							encodeOr + "rt_std_name4" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no76
																														// 대사
																														// |string
							encodeOr + "rt_std_name5" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no77
																														// 공포
																														// |string
							encodeOr + "rt_std_name6" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no78
																														// 약물
																														// |string
							encodeOr + "rt_std_name7" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no79
																														// 모방위험
																														// |string
							encodeOr + "minor_malef_yn" + encodeAns + strMark + encodeSearchStr + strMark + // no80
																											// 연소자
																											// 유해성
																											// 여부
																											// |string
																											// like
																											// X
							encodeOr + "wdra_yn" + encodeAns + strMark + encodeSearchStr + strMark + // no86
																										// 취하여부
																										// |string
																										// like
																										// X
							encodeOr + "return_name" + encodeAns + strMark + encodeSearchStr + strMark + // no90
																											// 취하,
																											// 반납
																											// |string
																											// like
																											// X
							encodeOr + "proc_state_code" + encodeAns + strMark + encodeSearchStr + strMark + // no97
																												// 진행
																												// 상태
																												// 코드
																												// |string
																												// like
																												// X
							encodeOr + "state_code" + encodeAns + strMark + encodeSearchStr + strMark + // no98
																										// 상태코드
																										// |string
																										// like
																										// X
							encodeOr + "non_com_yn" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no109
																														// 불완전데이터
																														// 플래그
																														// |string
							encodeOr + "rt_core_harm_rsn_code1" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + // no114 내용정보표시항목1 |string
							encodeOr + "rt_core_harm_rsn_code2" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + // no114 내용정보표시항목2 |string
							encodeOr + "rt_core_harm_rsn_code3" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + // no114 내용정보표시항목3 |string
							encodeOr + "work_cont" + encodeAns + strMark + encodeSearchStr + strMark + "+someword+" + // no81
																															// 작품내용(줄거리)
																															// |text
							encodeOr + "deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark + "+someword+" + // no83
																															// 결정사유
																															// |text
							encodeOr + "major_opin_narrn_cont" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+" + // no84 주요의견 |text
							encodeOr + "core_harm_rsn" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+"; // no85 핵심유해사유 |text

					// int는 encode를 따로 안해줘도 무방함.
					if (searchStrInt != 0) {
						searchCol += encodeOr + "sub_rcv_date" + encodeAns + searchStrInt + "000000" + // no5
																										// 신청일자
																										// |
																										// int
								encodeOr + "rcv_date" + encodeAns + searchStrInt + "000000" + // no6
																								// 접수일자
																								// |
																								// int
								encodeOr + "rt_date" + encodeAns + searchStrInt + "000000" + // no70
																								// 등급분류일
																								// |
																								// int
								encodeOr + "pfm_pnum" + encodeAns + searchStrInt + // no28
																					// 외국인
																					// 인원수
																					// |
																					// int
								encodeOr + "contr_start_date" + encodeAns + searchStrInt + "000000" + // no44
																										// 계약
																										// 시작
																										// 일자
																										// |
																										// int
								encodeOr + "contr_end_date" + encodeAns + searchStrInt + "000000" + // no45
																									// 계약
																									// 종료
																									// 일자
																									// |
																									// int
								encodeOr + "ori_rt_no" + encodeAns + searchStrInt; // no59
																					// 신규추천번호
																					// |
																					// int
					}

					// 통합검색의 마무리 괄호
					searchCol += ")";

					// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
					// 0 원제명Url (텍스트지만, 중요도에 따라 맨위에 위치)
					searchCol_ori_title = "(" + "ori_title" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + "synonym('d0')" + ")";

					// 0 사용제명Url (텍스트지만, 중요도에 따라 맨위에 위치)
					searchCol_use_title = "(" + "use_title" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + "synonym('d0')" + ")";
					// 1. 감독명Url
					searchCol_dire_name = "(" + "dire_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 2. 제작사명Url
					searchCol_prodc_name = "(" + "prodc_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";

					// 3. 접수번호Url
					searchCol_rcv_no_view = "(" + "rcv_no_view" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 4. 신청번호Url
					searchCol_sub_no = "(" + "sub_no" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 5. 온라인/오프라인 Url
					searchCol_onlin_offlin = "(" + "onlin_offlin" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 6. 매체명
					searchCol_medi_name = "(" + "medi_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 7. 제작자 국적
					searchCol_prodc_natnl_name = "(" + "prodc_natnl_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 8. 영화 종류
					searchCol_mv_asso_name = "(" + "mv_asso_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 9. 주연명
					searchCol_leada_name = "(" + "leada_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + ")";
					// 10. 외국인 국적
					searchCol_frgnr_natnl_name = "(" + "frgnr_natnl_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 11. 종류
					searchCol_kind_name = "(" + "kind_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 12. 희망등급
					searchCol_hope_grade_name = "(" + "hope_grade_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 13. 제작년도
					searchCol_prod_year = "(" + "prod_year" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 14. 공연 장소명
					searchCol_pfm_place_name = "(" + "pfm_place_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 15. 대표자 성명
					searchCol_repr_nm = "(" + "repr_nm" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ ")";
					// 16. 신청회사
					searchCol_aplc_name = "(" + "aplc_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + ")";
					// 17. 등급분류번호
					searchCol_rt_no = "(" + "rt_no" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 18. 결정등급
					searchCol_grade_name = "(" + "grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 19. 결정의견
					searchCol_deter_opin_name = "(" + "deter_opin_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 20. 주제
					searchCol_rt_std_name1 = "(" + "rt_std_name1" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 21. 선정성
					searchCol_rt_std_name2 = "(" + "rt_std_name2" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 22. 폭력성
					searchCol_rt_std_name3 = "(" + "rt_std_name3" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 23. 대사
					searchCol_rt_std_name4 = "(" + "rt_std_name4" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 24. 공포
					searchCol_rt_std_name5 = "(" + "rt_std_name5" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 25. 약물
					searchCol_rt_std_name6 = "(" + "rt_std_name6" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 26. 모방위험
					searchCol_rt_std_name7 = "(" + "rt_std_name7" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 27. 연소자 유해성 여부
					searchCol_minor_malef_yn = "(" + "minor_malef_yn" + encodeAns + strMark + encodeSearchStr + strMark
							+ ")";
					// 28. 취하여부
					searchCol_wdra_yn = "(" + "wdra_yn" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 29. 취하,반납
					searchCol_return_name = "(" + "return_name" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 30. 진행 상태 코드
					searchCol_proc_state_code = "(" + "proc_state_code" + encodeAns + strMark + encodeSearchStr
							+ strMark + ")";
					// 31. 상태코드
					searchCol_state_code = "(" + "state_code" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 32. 불완전데이터 플래그
					searchCol_non_com_yn = "(" + "non_com_yn" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 33. 내용정보표시항목1
					searchCol_rt_core_harm_rsn_code1 = "(" + "rt_core_harm_rsn_code1" + encodeLike + strMark + "*"
							+ encodeSearchStr + "*" + strMark + ")";
					// 34. 내용정보표시항목2
					searchCol_rt_core_harm_rsn_code2 = "(" + "rt_core_harm_rsn_code2" + encodeLike + strMark + "*"
							+ encodeSearchStr + "*" + strMark + ")";
					// 35. 내용정보표시항목3
					searchCol_rt_core_harm_rsn_code3 = "(" + "rt_core_harm_rsn_code3" + encodeLike + strMark + "*"
							+ encodeSearchStr + "*" + strMark + ")";

					/********* 텍스트 타입 시작! **********/
					// 36. 작품내용(줄거리)
					searchCol_work_cont = "(" + "work_cont" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+" + ")";
					// 37. 결정사유
					searchCol_deter_rsn = "(" + "deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+" + ")";
					// 38. 주요의견
					searchCol_major_opin_narrn_cont = "(" + "major_opin_narrn_cont" + encodeAns + strMark
							+ "+someword+" + strMark + searchOperation + ")";
					// 39. 핵심유해사유
					searchCol_core_harm_rsn = "(" + "core_harm_rsn" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+" + ")";

					/*** 검색어가 숫자인 경우 추가되는 항목!! ***/
					if (searchStrInt != 0) {

						// 40. 신청일자
						searchCol_sub_rcv_date = "(" + "sub_rcv_date" + encodeAns + searchStrInt + "000000" + ")";
						// 41. 접수일자
						searchCol_rcv_date = "(" + "rcv_date" + encodeAns + searchStrInt + "000000" + ")";
						// 42. 등급분류일
						searchCol_rt_date = "(" + "rt_date" + encodeAns + searchStrInt + "000000" + ")";
						// 43. 외국인 인원수
						searchCol_pfm_pnum = "(" + "pfm_pnum" + encodeAns + searchStrInt + ")";
						// 44. 계약 시작 일자
						searchCol_contr_start_date = "(" + "contr_start_date" + encodeAns + searchStrInt + "000000"
								+ ")";
						// 45. 계약 종료 일자
						searchCol_contr_end_date = "(" + "contr_end_date" + encodeAns + searchStrInt + "000000" + ")";
						// 46. 신규추천번호
						searchCol_ori_rt_no = "(" + "ori_rt_no" + encodeAns + searchStrInt + ")";

					}

				}
				if (searchGubun.equals("title")) {
					searchCol += "(use_title" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ "synonym('d0')" + encodeOr + "ori_title" + encodeAns + strMark + encodeSearchStr
							+ strMark + searchOperation + "synonym('d0')" + ")";
				}
				if (searchGubun.equals("decision")) {
					searchCol += "(deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ "synonym('d0')" + encodeOr + "deter_basis" + encodeAns + strMark + encodeSearchStr
							+ strMark + searchOperation + "synonym('d0')" + ")";
				}

				// 20230202 if 검색어가 공백이지만, 상세조건에 값이 있다면? ex) 검색어는 빈값, 주연명 "마동석"
				// searchCol부터.. 전부 다~ medi_code값을 줌 --> 검색어가 빈값일떄 이 방법이 최선.
				if (searchStr.equals("NO_VALUE")) {
					searchCol = medi_code;
					searchCol_dire_name = medi_code;
					searchCol_prodc_name = medi_code;
					searchCol_rcv_no_view = medi_code;
					searchCol_sub_no = medi_code;
					searchCol_onlin_offlin = medi_code;
					searchCol_medi_name = medi_code;
					searchCol_prodc_natnl_name = medi_code;
					searchCol_mv_asso_name = medi_code;
					searchCol_leada_name = medi_code;
					searchCol_frgnr_natnl_name = medi_code;
					searchCol_kind_name = medi_code;
					searchCol_hope_grade_name = medi_code;
					searchCol_prod_year = medi_code;
					searchCol_pfm_place_name = medi_code;
					searchCol_repr_nm = medi_code;
					searchCol_aplc_name = medi_code;
					searchCol_rt_no = medi_code;
					searchCol_grade_name = medi_code;
					searchCol_deter_opin_name = medi_code;
					searchCol_rt_std_name1 = medi_code;
					searchCol_rt_std_name2 = medi_code;
					searchCol_rt_std_name3 = medi_code;
					searchCol_rt_std_name4 = medi_code;
					searchCol_rt_std_name5 = medi_code;
					searchCol_rt_std_name6 = medi_code;
					searchCol_rt_std_name7 = medi_code;
					searchCol_minor_malef_yn = medi_code;
					searchCol_wdra_yn = medi_code;
					searchCol_return_name = medi_code;
					searchCol_proc_state_code = medi_code;
					searchCol_state_code = medi_code;
					searchCol_non_com_yn = medi_code;
					searchCol_rt_core_harm_rsn_code1 = medi_code;
					searchCol_rt_core_harm_rsn_code2 = medi_code;
					searchCol_rt_core_harm_rsn_code3 = medi_code;

					// 텍스트타입
					searchCol_ori_title = medi_code;
					searchCol_use_title = medi_code;
					searchCol_work_cont = medi_code;
					searchCol_deter_rsn = medi_code;
					searchCol_major_opin_narrn_cont = medi_code;
					searchCol_core_harm_rsn = medi_code;

					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_sub_rcv_date = medi_code;
						searchCol_rcv_date = medi_code;
						searchCol_rt_date = medi_code;
						searchCol_pfm_pnum = medi_code;
						searchCol_contr_start_date = medi_code;
						searchCol_contr_end_date = medi_code;
						searchCol_ori_rt_no = medi_code;
					}
				} // if(searchStr == null) 끝.

				// 상세검색 일때만 뒤에 이걸 붙여주자!
				/**************** 일자 통합작업중 ***********************/
				if (detail_visibleChk.equals("Y")) {
					
					
			//////////////////medi_code: 공연추천 AND/OR 연산자 관련 변수 초기화 시작//////////////////////////////////////////////////////////////////
					//1. 상세검색 조건내에 선택한  연산자에 따라서 and를 할지 or을 할지 정하는 로직. 
					String encodeTotal = "";
					String encodeTotalChk = (String) paramMap.get("detailSearch_radio");
					
					if(encodeTotalChk != null){
						if(encodeTotalChk.equals("allword")){
							encodeTotal = encodeAnd;
						} else if(encodeTotalChk.equals("anyword")){
							encodeTotal = encodeOr;
						} 
					} else {
						encodeTotal = encodeAnd;
					}
					
					//2. 상세검색 조건문에 들어오면 AND연산자 해주고,  괄호도  열어주기. (상세검색 조건문 끝날떄쯤 괄호 닫아줄 것)
					if(encodeTotal.equals(encodeAnd)){
						searchCol += encodeAnd + encodeLGual + medi_code;
						searchCol_dire_name += encodeAnd + encodeLGual + medi_code;
						searchCol_prodc_name += encodeAnd + encodeLGual + medi_code;
						searchCol_rcv_no_view += encodeAnd + encodeLGual + medi_code;
						searchCol_sub_no += encodeAnd + encodeLGual + medi_code;
						searchCol_onlin_offlin += encodeAnd + encodeLGual + medi_code;
						searchCol_medi_name += encodeAnd + encodeLGual + medi_code;
						searchCol_prodc_natnl_name += encodeAnd + encodeLGual + medi_code;
						searchCol_mv_asso_name += encodeAnd + encodeLGual + medi_code;
						searchCol_leada_name += encodeAnd + encodeLGual + medi_code;
						searchCol_frgnr_natnl_name += encodeAnd + encodeLGual + medi_code;
						searchCol_kind_name += encodeAnd + encodeLGual + medi_code;
						searchCol_hope_grade_name += encodeAnd + encodeLGual + medi_code;
						searchCol_prod_year += encodeAnd + encodeLGual + medi_code;
						searchCol_pfm_place_name += encodeAnd + encodeLGual + medi_code;
						searchCol_repr_nm += encodeAnd + encodeLGual + medi_code;
						searchCol_aplc_name += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_no += encodeAnd + encodeLGual + medi_code;
						searchCol_grade_name += encodeAnd + encodeLGual + medi_code;
						searchCol_deter_opin_name += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name1 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name2 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name3 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name4 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name5 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name6 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_std_name7 += encodeAnd + encodeLGual + medi_code;
						searchCol_minor_malef_yn += encodeAnd + encodeLGual + medi_code;
						searchCol_wdra_yn += encodeAnd + encodeLGual + medi_code;
						searchCol_return_name += encodeAnd + encodeLGual + medi_code;
						searchCol_proc_state_code += encodeAnd + encodeLGual + medi_code;
						searchCol_state_code += encodeAnd + encodeLGual + medi_code;
						searchCol_non_com_yn += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_core_harm_rsn_code1 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_core_harm_rsn_code2 += encodeAnd + encodeLGual + medi_code;
						searchCol_rt_core_harm_rsn_code3 += encodeAnd + encodeLGual + medi_code;
 
						// 텍스트타입
						searchCol_ori_title += encodeAnd + encodeLGual + medi_code;
						searchCol_use_title += encodeAnd + encodeLGual + medi_code;
						searchCol_work_cont += encodeAnd + encodeLGual + medi_code;
						searchCol_deter_rsn += encodeAnd + encodeLGual + medi_code;
						searchCol_major_opin_narrn_cont += encodeAnd + encodeLGual + medi_code;
						searchCol_core_harm_rsn += encodeAnd + encodeLGual + medi_code;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_sub_rcv_date += encodeAnd + encodeLGual + medi_code;
							searchCol_rcv_date += encodeAnd + encodeLGual + medi_code;
							searchCol_rt_date += encodeAnd + encodeLGual + medi_code;
							searchCol_pfm_pnum += encodeAnd + encodeLGual + medi_code;
							searchCol_contr_start_date += encodeAnd + encodeLGual + medi_code;
							searchCol_contr_end_date += encodeAnd + encodeLGual + medi_code;
							searchCol_ori_rt_no += encodeAnd + encodeLGual + medi_code;
						}

					} else if(encodeTotal.equals(encodeOr)){
						//만약 OR이라면 medi_code가 영향을 주니, 더미데이터를 넣어준다. 해당 구문은 medi_code가 존재하지 않는 더미데이터를 넣는것이다.
						searchCol += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_dire_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_prodc_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rcv_no_view += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_sub_no += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_onlin_offlin += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_medi_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_prodc_natnl_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_mv_asso_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_leada_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_frgnr_natnl_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_kind_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_hope_grade_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_prod_year += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_pfm_place_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_repr_nm += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_aplc_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_no += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_grade_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_deter_opin_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name1 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name2 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name3 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name4 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name5 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name6 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_std_name7 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_minor_malef_yn += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_wdra_yn += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_return_name += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_proc_state_code += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_state_code += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_non_com_yn += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_core_harm_rsn_code1 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_core_harm_rsn_code2 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_rt_core_harm_rsn_code3 += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
 
						// 텍스트타입
						searchCol_ori_title += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_use_title += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_work_cont += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_deter_rsn += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_major_opin_narrn_cont += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						searchCol_core_harm_rsn += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_sub_rcv_date += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
							searchCol_rcv_date += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
							searchCol_rt_date += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
							searchCol_pfm_pnum += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
							searchCol_contr_start_date += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
							searchCol_contr_end_date += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
							searchCol_ori_rt_no += encodeAnd + encodeLGual + "medi_code+in%7B%2717551%27%2C%2717562%27%7D+";
						}
					}
	   //////////////////medi_code: 공연추천 AND/OR 연산자 관련 변수 초기화  끝//////////////////////////////////////////////////////////////////		
					
					
					// 일자보다 신청회사 먼저 붙여준다.
					if (!(detailAplc.equals("") || detailAplc == null)) {
						detailAplc = URLEncoder.encode(detailAplc, "UTF-8");
						// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
						searchCol += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_dire_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_prodc_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rcv_no_view += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_sub_no += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_onlin_offlin += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_medi_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_prodc_natnl_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_mv_asso_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_leada_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_frgnr_natnl_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_kind_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_hope_grade_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_prod_year += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_pfm_place_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_repr_nm += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_aplc_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_no += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_grade_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_deter_opin_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name1 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name2 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name3 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name4 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name5 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name6 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_std_name7 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_minor_malef_yn += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_wdra_yn += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_return_name += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_proc_state_code += encodeTotal + encodeLGual + "aplc_name" +encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_state_code += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_non_com_yn += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_core_harm_rsn_code1 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_core_harm_rsn_code2 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_core_harm_rsn_code3 += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
 
						// 텍스트타입
						searchCol_ori_title += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_use_title += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_work_cont += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_deter_rsn += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_major_opin_narrn_cont += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_core_harm_rsn += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_sub_rcv_date += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_rcv_date += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_rt_date += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_pfm_pnum += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_contr_start_date += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_contr_end_date += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_ori_rt_no += encodeTotal + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
						}

					}

					String detail_date = "";
					if (!period_start.equals("") && !period_end.equals("")) {
						if (detail_dateGubun.equals("regdate")) {
							searchCol += encodeTotal + encodeLGual + "sub_rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 신청일자
							detail_date = encodeTotal + encodeLGual + "sub_rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;
						} else if (detail_dateGubun.equals("jubsudate")) {
							searchCol += encodeTotal + encodeLGual + "rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 접수일자
							detail_date = encodeTotal + encodeLGual + "rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;
						} else {
							searchCol += encodeTotal + encodeLGual + "rt_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 등급분류일자
							detail_date = encodeTotal + encodeLGual + "rt_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;
						}

						// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
						searchCol_dire_name += detail_date;
						searchCol_prodc_name += detail_date;
						searchCol_rcv_no_view += detail_date;
						searchCol_sub_no += detail_date;
						searchCol_onlin_offlin += detail_date;
						searchCol_medi_name += detail_date;
						searchCol_prodc_natnl_name += detail_date;
						searchCol_mv_asso_name += detail_date;
						searchCol_leada_name += detail_date;
						searchCol_frgnr_natnl_name += detail_date;
						searchCol_kind_name += detail_date;
						searchCol_hope_grade_name += detail_date;
						searchCol_prod_year += detail_date;
						searchCol_pfm_place_name += detail_date;
						searchCol_repr_nm += detail_date;
						searchCol_aplc_name += detail_date;
						searchCol_rt_no += detail_date;
						searchCol_grade_name += detail_date;
						searchCol_deter_opin_name += detail_date;
						searchCol_rt_std_name1 += detail_date;
						searchCol_rt_std_name2 += detail_date;
						searchCol_rt_std_name3 += detail_date;
						searchCol_rt_std_name4 += detail_date;
						searchCol_rt_std_name5 += detail_date;
						searchCol_rt_std_name6 += detail_date;
						searchCol_rt_std_name7 += detail_date;
						searchCol_minor_malef_yn += detail_date;
						searchCol_wdra_yn += detail_date;
						searchCol_return_name += detail_date;
						searchCol_proc_state_code += detail_date;
						searchCol_state_code += detail_date;
						searchCol_non_com_yn += detail_date;
						searchCol_rt_core_harm_rsn_code1 += detail_date;
						searchCol_rt_core_harm_rsn_code2 += detail_date;
						searchCol_rt_core_harm_rsn_code3 += detail_date;

						// 텍스트타입
						searchCol_ori_title += detail_date;
						searchCol_use_title += detail_date;
						searchCol_work_cont += detail_date;
						searchCol_deter_rsn += detail_date;
						searchCol_major_opin_narrn_cont += detail_date;
						searchCol_core_harm_rsn += detail_date;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_sub_rcv_date += detail_date;
							searchCol_rcv_date += detail_date;
							searchCol_rt_date += detail_date;
							searchCol_pfm_pnum += detail_date;
							searchCol_contr_start_date += detail_date;
							searchCol_contr_end_date += detail_date;
							searchCol_ori_rt_no += detail_date;
						}
					} else {
						//날짜가 '전체'라면 빈값으로 오기때문에 더미데이터로 medi_code를 넣어준다. 
						// 연산자가 AND라면 medi_code가 날짜의 '전체' 기능을 할 것이고,
						// 연산자가 OR이더라도 medi_code가 날짜의 '전체' 기능을 할것이다. --> 정보 : 처음에 무조건 더미데이터(medi_code~) 가 존재하기때문에 encodeTotal로 시작해도 무방함.
						searchCol += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_dire_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_prodc_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rcv_no_view += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_sub_no += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_onlin_offlin += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_medi_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_prodc_natnl_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_mv_asso_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_leada_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_frgnr_natnl_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_kind_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_hope_grade_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_prod_year += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_pfm_place_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_repr_nm += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_aplc_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_no += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_grade_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_deter_opin_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name1 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name2 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name3 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name4 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name5 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name6 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_std_name7 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_minor_malef_yn += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_wdra_yn += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_return_name += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_proc_state_code += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_state_code += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_non_com_yn += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_core_harm_rsn_code1 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_core_harm_rsn_code2 += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_rt_core_harm_rsn_code3 += encodeTotal + encodeLGual + medi_code + encodeRGual;
 
						// 텍스트타입
						searchCol_ori_title += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_use_title += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_work_cont += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_deter_rsn += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_major_opin_narrn_cont += encodeTotal + encodeLGual + medi_code + encodeRGual;
						searchCol_core_harm_rsn += encodeTotal + encodeLGual + medi_code + encodeRGual;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_sub_rcv_date += encodeTotal + encodeLGual + medi_code + encodeRGual;
							searchCol_rcv_date += encodeTotal + encodeLGual + medi_code + encodeRGual;
							searchCol_rt_date += encodeTotal + encodeLGual + medi_code + encodeRGual;
							searchCol_pfm_pnum += encodeTotal + encodeLGual + medi_code + encodeRGual;
							searchCol_contr_start_date += encodeTotal + encodeLGual + medi_code + encodeRGual;
							searchCol_contr_end_date += encodeTotal + encodeLGual + medi_code + encodeRGual;
							searchCol_ori_rt_no += encodeTotal + encodeLGual + medi_code + encodeRGual;
						}
					} 

					// System.out.println(paramMap.get("detail_kindChk"));
					// 상세검색 visibleChk가 Y이고, kindChk가 perform일떄 실행.
					if (((String) paramMap.get("detail_kindChk")).equals("perform01")) {
						String totalDetailChk = "";
						String detailPerform = "";
						String encodePerform = "";
						String tmp_Perform = "";
						/////////////////////////////// 결정등급//////////////////////////////////
						if (paramMap.get("detailSearch_grade_name") != null) {
							if (paramMap.get("detailSearch_grade_name").equals("good")) {
								tmp_Perform = "추천";
							} else if (paramMap.get("detailSearch_grade_name").equals("bad")) {
								tmp_Perform = "미추천";
							}
							encodePerform = URLEncoder.encode(tmp_Perform, "UTF-8");
							detailPerform = encodeTotal + encodeLGual + "deter_opin_name" + encodeAns + strMark
									+ encodePerform + strMark + encodeRGual;
							totalDetailChk += detailPerform;
						}
						///////////////////////////// 결정등급////////////////////////////////////
						/*
						 * /////////////////////////////내용정보표시항목////////////////
						 * ////////////////////
						 * if(paramMap.get("detailSearch_rt_std_name") != null){
						 * if(paramMap.get("detailSearch_rt_std_name").equals(
						 * "17701")){ tmp_Perform = "17701"; } else
						 * if(paramMap.get("detailSearch_rt_std_name").equals(
						 * "17702")){ tmp_Perform = "17702"; } else
						 * if(paramMap.get("detailSearch_rt_std_name").equals(
						 * "17703")){ tmp_Perform = "17703"; } else
						 * if(paramMap.get("detailSearch_rt_std_name").equals(
						 * "17704")){ tmp_Perform = "17704"; } else
						 * if(paramMap.get("detailSearch_rt_std_name").equals(
						 * "17705")){ tmp_Perform = "17705"; } else
						 * if(paramMap.get("detailSearch_rt_std_name").equals(
						 * "17706")){ tmp_Perform = "17706"; } else
						 * if(paramMap.get("detailSearch_rt_std_name").equals(
						 * "17707")){ tmp_Perform = "17707"; } encodePerform =
						 * URLEncoder.encode(tmp_Perform,"UTF-8"); detailPerform
						 * = encodeTotal + encodeLGual + "rt_core_harm_rsn_code1"
						 * + encodeAns + strMark + encodePerform + strMark +
						 * encodeOr + "rt_core_harm_rsn_code2" + encodeAns +
						 * strMark + encodePerform + strMark + encodeOr +
						 * "rt_core_harm_rsn_code3" + encodeAns + strMark +
						 * encodePerform + strMark+ encodeRGual; totalDetailChk
						 * += detailPerform; }
						 * /////////////////////////////내용정보표시항목////////////////
						 * ////////////////////
						 */

						//////////////////////////// 외국인
						//////////////////////////// 인원수////////////////////////////////////
						if (paramMap.get("detailSearch_pfm_pnum") != null) {
							int tmp_perform_int = 0;
							try {
								tmp_perform_int = Integer.parseInt(((String) paramMap.get("detailSearch_pfm_pnum")));
							} catch (Exception e) {
								e.printStackTrace();
								// System.out.println("상세검색 -> 공연추천 상세검색 -> 상세검색
								// 조건에 [외국인인원수]에 숫자가 아닌 문자열이 들어왔음.");
								tmp_perform_int = -1;
							}
							if (tmp_perform_int > 0) {
								detailPerform = encodeTotal + encodeLGual + "pfm_pnum" + encodeAns + tmp_perform_int
										+ encodeRGual;
								totalDetailChk += detailPerform;
							}
						}
						///////////////////////////// 외국인
						///////////////////////////// 인원수////////////////////////////////////

						//////////////////////////// 외국인
						//////////////////////////// 국적////////////////////////////////////
						if (paramMap.get("detailSearch_frgnr_natnl_name_perform") != null) {
							encodePerform = URLEncoder
									.encode((String) paramMap.get("detailSearch_frgnr_natnl_name_perform"), "UTF-8");
							detailPerform = encodeTotal + encodeLGual + "frgnr_natnl_name" + encodeAns + strMark
									+ encodePerform + strMark + encodeRGual;
							totalDetailChk += detailPerform;
						}
						///////////////////////////// 외국인
						///////////////////////////// 국적////////////////////////////////////

						//////////////////////////// 공연장소명////////////////////////////////////
						if (paramMap.get("detailSearch_pfm_place_name_perform") != null) {
							encodePerform = URLEncoder
									.encode((String) paramMap.get("detailSearch_pfm_place_name_perform"), "UTF-8");
							detailPerform = encodeTotal + encodeLGual + "pfm_place_name" + encodeLike + strMark + "*"
									+ encodePerform + "*" + strMark + encodeRGual;
							totalDetailChk += detailPerform;
						}
						///////////////////////////// 공연장소명////////////////////////////////////

						//////////////////////////// 종류 (콘서트, 음악회..)
						//////////////////////////// ////////////////////////////////////
						if (paramMap.get("detailSearch_kind_name") != null
								&& !((String) paramMap.get("detailSearch_kind_name")).equals("")) {
							if (paramMap.get("detailSearch_kind_name").equals("a")) {
								tmp_Perform = "가무";
							} else if (paramMap.get("detailSearch_kind_name").equals("b")) {
								tmp_Perform = "곡예마술";
							} else if (paramMap.get("detailSearch_kind_name").equals("c")) {
								tmp_Perform = "노래 및 연주";
							} else if (paramMap.get("detailSearch_kind_name").equals("d")) {
								tmp_Perform = "뮤지컬";
							} else if (paramMap.get("detailSearch_kind_name").equals("e")) {
								tmp_Perform = "발레무용";
							} else if (paramMap.get("detailSearch_kind_name").equals("f")) {
								tmp_Perform = "연극";
							} else if (paramMap.get("detailSearch_kind_name").equals("g")) {
								tmp_Perform = "연주";
							} else if (paramMap.get("detailSearch_kind_name").equals("h")) {
								tmp_Perform = "오페라";
							} else if (paramMap.get("detailSearch_kind_name").equals("i")) {
								tmp_Perform = "음악회";
							} else if (paramMap.get("detailSearch_kind_name").equals("j")) {
								tmp_Perform = "인형극";
							} else if (paramMap.get("detailSearch_kind_name").equals("k")) {
								tmp_Perform = "콘서트";
							} else if (paramMap.get("detailSearch_kind_name").equals("l")) {
								tmp_Perform = "합창";
							} else if (paramMap.get("detailSearch_kind_name").equals("m")) {
								tmp_Perform = "기타";
							}

							encodePerform = URLEncoder.encode(tmp_Perform, "UTF-8");
							detailPerform = encodeTotal + encodeLGual + "kind_name" + encodeAns + strMark + encodePerform
									+ strMark + encodeRGual;
							totalDetailChk += detailPerform;
						}
						///////////////////////////// 종류////////////////////////////////////

						//////////////////////////// 연소자 유해성
						//////////////////////////// 여부////////////////////////////////////
						if (paramMap.get("detailSearch_minor_malef_yn") != null
								&& !((String) paramMap.get("detailSearch_minor_malef_yn")).equals("")) {
							if (paramMap.get("detailSearch_minor_malef_yn").equals("harmless")) {
								tmp_Perform = "무해";
							} else if (paramMap.get("detailSearch_minor_malef_yn").equals("harm")) {
								tmp_Perform = "유해";
							}
							encodePerform = URLEncoder.encode(tmp_Perform, "UTF-8");
							detailPerform = encodeTotal + encodeLGual + "minor_malef_yn" + encodeAns + strMark
									+ encodePerform + strMark + encodeRGual;
							totalDetailChk += detailPerform;
						}
						///////////////////////////// 연소자 유해성
						///////////////////////////// 여부////////////////////////////////////

						/*
						 * 결정의견은 잠시 보류 ( 공연추천에는 속행,추천 2가지가 있다. 99%는 추천임.)
						 * /////////////////////////////결정의견////////////////////
						 * ////////////////
						 * if(paramMap.get("detailSearch_deter_opin_name") !=
						 * null){ encodePerform =
						 * URLEncoder.encode((String)paramMap.get(
						 * "detailSearch_deter_opin_name"),"UTF-8");
						 * detailPerform = encodeTotal + encodeLGual +
						 * "deter_opin_name" + encodeLike + strMark + "*" +
						 * encodePerform + "*" + strMark + encodeRGual;
						 * totalDetailChk += detailPerform; }
						 * /////////////////////////////결정의견////////////////////
						 * ////////////////
						 */

						//////////////////////////// 결정사유////////////////////////////////////
						if (paramMap.get("detailSearch_deter_rsn_perform") != null) {
							encodePerform = URLEncoder.encode((String) paramMap.get("detailSearch_deter_rsn_perform"),
									"UTF-8");
							detailPerform = encodeTotal + encodeLGual + "deter_rsn" + encodeAns + strMark + encodePerform
									+ strMark + searchOperation + encodeRGual;
							totalDetailChk += detailPerform;
						}
						///////////////////////////// 결정사유////////////////////////////////////

						String dataValidChk = "";
						//////////////////////////// 계약 시작
						//////////////////////////// 일자////////////////////////////////////
						if (paramMap.get("detailSearch_contr_start_date_start") != null
								&& paramMap.get("detailSearch_contr_start_date_end") != null) {
							// substring(0,4)
							String startDate = ((String) paramMap.get("detailSearch_contr_start_date_start"))
									.substring(0, 4)
									+ ((String) paramMap.get("detailSearch_contr_start_date_start")).substring(5, 7)
									+ ((String) paramMap.get("detailSearch_contr_start_date_start")).substring(8, 10)
									+ "000000";
							String endDate = ((String) paramMap.get("detailSearch_contr_start_date_end")).substring(0,
									4) + ((String) paramMap.get("detailSearch_contr_start_date_end")).substring(5, 7)
									+ ((String) paramMap.get("detailSearch_contr_start_date_end")).substring(8, 10)
									+ "000000";

							detailPerform = encodeTotal + encodeLGual + "contr_start_date" + between + startDate
									+ encodeAnd + endDate + encodeRGual;
							totalDetailChk += detailPerform;

						}
						///////////////////////////// 계약 시작
						///////////////////////////// 일자////////////////////////////////////
						//////////////////////////// 계약 종료
						///////////////////////////// 일자////////////////////////////////////
						if (paramMap.get("detailSearch_contr_end_date_start") != null
								&& paramMap.get("detailSearch_contr_end_date_end") != null) {
							// substring(0,4)
							String startDate = ((String) paramMap.get("detailSearch_contr_end_date_start")).substring(0,
									4) + ((String) paramMap.get("detailSearch_contr_end_date_start")).substring(5, 7)
									+ ((String) paramMap.get("detailSearch_contr_end_date_start")).substring(8, 10)
									+ "000000";
							String endDate = ((String) paramMap.get("detailSearch_contr_end_date_end")).substring(0, 4)
									+ ((String) paramMap.get("detailSearch_contr_end_date_end")).substring(5, 7)
									+ ((String) paramMap.get("detailSearch_contr_end_date_end")).substring(8, 10)
									+ "000000";

							detailPerform = encodeTotal + encodeLGual + "contr_end_date" + between + startDate + encodeAnd
									+ endDate + encodeRGual;
							totalDetailChk += detailPerform;

						}
						///////////////////////////// 계약 종료
						///////////////////////////// 일자////////////////////////////////////
						
						
						////////////////////////////등급분류번호////////////////////////////
						if (paramMap.get("detailSearch_rt_no_perform") != null) {
							encodePerform = URLEncoder.encode((String) paramMap.get("detailSearch_rt_no_perform"), "UTF-8");
							detailPerform = encodeTotal + encodeLGual + "rt_no" + encodeAns + strMark
									+ encodePerform + strMark + encodeRGual;
							totalDetailChk += detailPerform;
						}
						////////////////////////////등급분류번호////////////////////////////
						
						
						
						

						if (!totalDetailChk.equals("")) {
							// 이것이 빈값이 아닐떄만 뒤에 조건들을 붙여주자! , 즉 ad(광고물) 상세보기 전용조건이
							// 하나라도 있다면 붙여주고, 아예없다면 생략.
							searchCol += totalDetailChk;
							searchCol_dire_name += totalDetailChk;
							searchCol_prodc_name += totalDetailChk;
							searchCol_rcv_no_view += totalDetailChk;
							searchCol_sub_no += totalDetailChk;
							searchCol_onlin_offlin += totalDetailChk;
							searchCol_medi_name += totalDetailChk;
							searchCol_prodc_natnl_name += totalDetailChk;
							searchCol_mv_asso_name += totalDetailChk;
							searchCol_leada_name += totalDetailChk;
							searchCol_frgnr_natnl_name += totalDetailChk;
							searchCol_kind_name += totalDetailChk;
							searchCol_hope_grade_name += totalDetailChk;
							searchCol_prod_year += totalDetailChk;
							searchCol_pfm_place_name += totalDetailChk;
							searchCol_repr_nm += totalDetailChk;
							searchCol_aplc_name += totalDetailChk;
							searchCol_rt_no += totalDetailChk;
							searchCol_grade_name += totalDetailChk;
							searchCol_deter_opin_name += totalDetailChk;
							searchCol_rt_std_name1 += totalDetailChk;
							searchCol_rt_std_name2 += totalDetailChk;
							searchCol_rt_std_name3 += totalDetailChk;
							searchCol_rt_std_name4 += totalDetailChk;
							searchCol_rt_std_name5 += totalDetailChk;
							searchCol_rt_std_name6 += totalDetailChk;
							searchCol_rt_std_name7 += totalDetailChk;
							searchCol_minor_malef_yn += totalDetailChk;
							searchCol_wdra_yn += totalDetailChk;
							searchCol_return_name += totalDetailChk;
							searchCol_proc_state_code += totalDetailChk;
							searchCol_state_code += totalDetailChk;
							searchCol_non_com_yn += totalDetailChk;
							searchCol_rt_core_harm_rsn_code1 += totalDetailChk;
							searchCol_rt_core_harm_rsn_code2 += totalDetailChk;
							searchCol_rt_core_harm_rsn_code3 += totalDetailChk;

							// 텍스트타입
							searchCol_ori_title += totalDetailChk;
							searchCol_use_title += totalDetailChk;
							searchCol_work_cont += totalDetailChk;
							searchCol_deter_rsn += totalDetailChk;
							searchCol_major_opin_narrn_cont += totalDetailChk;
							searchCol_core_harm_rsn += totalDetailChk;

							// 숫자타입
							if (searchStrInt != 0) {
								searchCol_sub_rcv_date += totalDetailChk;
								searchCol_rcv_date += totalDetailChk;
								searchCol_rt_date += totalDetailChk;
								searchCol_pfm_pnum += totalDetailChk;
								searchCol_contr_start_date += totalDetailChk;
								searchCol_contr_end_date += totalDetailChk;
								searchCol_ori_rt_no += totalDetailChk;
							}
						}
					}
					// 상세검색 조건절 마무리 괄호
					searchCol += encodeRGual;
					searchCol_dire_name += encodeRGual;
					searchCol_prodc_name += encodeRGual;
					searchCol_rcv_no_view += encodeRGual;
					searchCol_sub_no += encodeRGual;
					searchCol_onlin_offlin += encodeRGual;
					searchCol_medi_name += encodeRGual;
					searchCol_prodc_natnl_name += encodeRGual;
					searchCol_mv_asso_name += encodeRGual;
					searchCol_leada_name += encodeRGual;
					searchCol_frgnr_natnl_name += encodeRGual;
					searchCol_kind_name += encodeRGual;
					searchCol_hope_grade_name += encodeRGual;
					searchCol_prod_year += encodeRGual;
					searchCol_pfm_place_name += encodeRGual;
					searchCol_repr_nm += encodeRGual;
					searchCol_aplc_name += encodeRGual;
					searchCol_rt_no += encodeRGual;
					searchCol_grade_name += encodeRGual;
					searchCol_deter_opin_name += encodeRGual;
					searchCol_rt_std_name1 += encodeRGual;
					searchCol_rt_std_name2 += encodeRGual;
					searchCol_rt_std_name3 += encodeRGual;
					searchCol_rt_std_name4 += encodeRGual;
					searchCol_rt_std_name5 += encodeRGual;
					searchCol_rt_std_name6 += encodeRGual;
					searchCol_rt_std_name7 += encodeRGual;
					searchCol_minor_malef_yn += encodeRGual;
					searchCol_wdra_yn += encodeRGual;
					searchCol_return_name += encodeRGual;
					searchCol_proc_state_code += encodeRGual;
					searchCol_state_code += encodeRGual;
					searchCol_non_com_yn += encodeRGual;
					searchCol_rt_core_harm_rsn_code1 += encodeRGual;
					searchCol_rt_core_harm_rsn_code2 += encodeRGual;
					searchCol_rt_core_harm_rsn_code3 += encodeRGual;

					// 텍스트타입
					searchCol_ori_title += encodeRGual;
					searchCol_use_title += encodeRGual;
					searchCol_work_cont += encodeRGual;
					searchCol_deter_rsn += encodeRGual;
					searchCol_major_opin_narrn_cont += encodeRGual;
					searchCol_core_harm_rsn += encodeRGual;

					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_sub_rcv_date += encodeRGual;
						searchCol_rcv_date += encodeRGual;
						searchCol_rt_date += encodeRGual;
						searchCol_pfm_pnum += encodeRGual;
						searchCol_contr_start_date += encodeRGual;
						searchCol_contr_end_date += encodeRGual;
						searchCol_ori_rt_no += encodeRGual;
					}
				}
				//detail_visibleChk 조건 끝
				
				//만약 따옴표 안에 단어가 인식이 된다면 이 로직 실행.
				if(!quote_searchStr.equals("")) {
					//통합검색이든, 메뉴명이든 뒤에 quote_searchCol 붙여주기 시작! 
					searchCol += quote_searchCol;
					searchCol_dire_name += quote_searchCol;
					searchCol_prodc_name += quote_searchCol;
					searchCol_rcv_no_view += quote_searchCol;
					searchCol_sub_no += quote_searchCol;
					searchCol_onlin_offlin += quote_searchCol;
					searchCol_medi_name += quote_searchCol;
					searchCol_prodc_natnl_name += quote_searchCol;
					searchCol_mv_asso_name += quote_searchCol;
					searchCol_leada_name += quote_searchCol;
					searchCol_frgnr_natnl_name += quote_searchCol;
					searchCol_kind_name += quote_searchCol;
					searchCol_hope_grade_name += quote_searchCol;
					searchCol_prod_year += quote_searchCol;
					searchCol_pfm_place_name += quote_searchCol;
					searchCol_repr_nm += quote_searchCol;
					searchCol_aplc_name += quote_searchCol;
					searchCol_rt_no += quote_searchCol;
					searchCol_grade_name += quote_searchCol;
					searchCol_deter_opin_name += quote_searchCol;
					searchCol_rt_std_name1 += quote_searchCol;
					searchCol_rt_std_name2 += quote_searchCol;
					searchCol_rt_std_name3 += quote_searchCol;
					searchCol_rt_std_name4 += quote_searchCol;
					searchCol_rt_std_name5 += quote_searchCol;
					searchCol_rt_std_name6 += quote_searchCol;
					searchCol_rt_std_name7 += quote_searchCol;
					searchCol_minor_malef_yn += quote_searchCol;
					searchCol_wdra_yn += quote_searchCol;
					searchCol_return_name += quote_searchCol;
					searchCol_proc_state_code += quote_searchCol;
					searchCol_state_code += quote_searchCol;
					searchCol_non_com_yn += quote_searchCol;
					searchCol_rt_core_harm_rsn_code1 += quote_searchCol;
					searchCol_rt_core_harm_rsn_code2 += quote_searchCol;
					searchCol_rt_core_harm_rsn_code3 += quote_searchCol;
	
					// 텍스트타입
					searchCol_ori_title += quote_searchCol;
					searchCol_use_title += quote_searchCol;
					searchCol_work_cont += quote_searchCol;
					searchCol_deter_rsn += quote_searchCol;
					searchCol_major_opin_narrn_cont += quote_searchCol;
					searchCol_core_harm_rsn += quote_searchCol;
	
					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_sub_rcv_date += quote_searchCol;
						searchCol_rcv_date += quote_searchCol;
						searchCol_rt_date += quote_searchCol;
						searchCol_pfm_pnum += quote_searchCol;
						searchCol_contr_start_date += quote_searchCol;
						searchCol_contr_end_date += quote_searchCol;
						searchCol_ori_rt_no += quote_searchCol;
					}
				}
				//통합검색이든, 메뉴명이든 뒤에 quote_searchCol 붙여주기 끝!
				
				/**************** 상세검색의 일자, 신청회사 통합작업중 ***********************/

				// 20230127 속도 개선을 위해, 공연추천은 개별적으로 totalUrl 위치를 여기로 옮겨서 결과가 0건이면
				// 사이드명 데이터를 가져오는 함수를 실행하지 않는다.
				totalUrl = result_noticeUrl + medi_code + encodeAnd + searchCol
						+ orderValue;
				// 결과내 재검색이라면 ? 시작
				if (((String) paramMap.get("result_re_search_chk")).equals("Y")
						&& (String) paramMap.get("perform01_historySearchQuery") != null) {
					totalUrl = (String) paramMap.get("perform01_historySearchQuery") + encodeAnd + "(" + medi_code
							+ encodeAnd + searchCol + ")" + orderValue;
				}
				// 결과내 재검색이라면 ? 끝

				// 이전쿼리 저장 시작
				String historySearchQuery = "";
				historySearchQuery = result_noticeUrl + "(" + medi_code + encodeAnd + searchCol + ")";
				// 만약 결과내재검색을 통한 검색이었다면, historySearchQuery(이전쿼리)는 , 이전쿼리 +
				// encodeAnd + 현재쿼리 일 것이다.
				if (((String) paramMap.get("result_re_search_chk")).equals("Y")
						&& (String) paramMap.get("perform01_historySearchQuery") != null) {
					historySearchQuery = (String) paramMap.get("perform01_historySearchQuery") + encodeAnd + "("
							+ medi_code + encodeAnd + searchCol + ")";
				}
				paramMap.put("perform01_historySearchQuery", historySearchQuery);
				// 이전쿼리 저장 끝

				// 임시로 사이드명 로직 돌기 전에 카운트0인지 체크.
				String paging_totalUrl = totalUrl + "&pagelength=" + detailresultLine + "&offset=" + offset + "&limit="
						+ detailresultLine;
				List<Object> dataList = pagingURLEncodingFunc(paging_totalUrl);
				// if(searchGubun.equals("All") 부분에 카운트 0이 아닐떄 사이드로직 돌도록 조건 추가함.

				/**************
				 * ■■■■■■■■■■■■■*************************************우측사이드에 관한
				 * information 시작
				 * *************************************************■■■■■■■■■
				 **************************************************/
				List<Map<String, Object>> perform01_sideInformation = new ArrayList<Map<String, Object>>();
				if (searchGubun.equals("All") && (Integer) dataList.get(0) != 0) {
					// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가

					// 0. (조건에 맞는)원제명 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_ori_title, "원제명"));

					// 0. (조건에 맞는)사용제명 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_use_title, "사용제명"));

					// 1. (조건에 맞는)감독명에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_dire_name, "감독명"));

					// 2. (조건에 맞는)제작사명에 대한 건수 및 데이터 조회 // walker에 정보 담기
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_prodc_name, "제작사명"));

					// 3. (조건에 맞는)접수번호에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rcv_no_view, "접수번호"));

					// 4. (조건에 맞는)신청번호에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_sub_no, "신청번호"));

					// 5. (조건에 맞는)온라인/오프라인에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_onlin_offlin, "온라인/오프라인"));

					// 6. (조건에 맞는)메체명에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_medi_name, "매체명"));

					// 7. (조건에 맞는)제작자 국적에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_prodc_natnl_name, "제작자 국적"));

					// 8. (조건에 맞는)영화 종류에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_mv_asso_name, "영화 종류"));

					// 9. (조건에 맞는)주연명 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_leada_name, "주연명"));

					// 10. (조건에 맞는)외국인 국적에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_frgnr_natnl_name, "외국인 국적"));

					// 11. (조건에 맞는)종류에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_kind_name, "종류"));

					// 12. (조건에 맞는)희망등급에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_hope_grade_name, "희망등급"));

					// 13. (조건에 맞는)제작년도에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_prod_year, "제작년도"));

					// 14. (조건에 맞는)공연 장소명에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_pfm_place_name, "공연 장소명"));

					// 15. (조건에 맞는)대표자 성명에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_repr_nm, "대표자 성명"));

					// 16. (조건에 맞는)신청회사에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_aplc_name, "신청회사"));

					// 17. (조건에 맞는)등급분류번호에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_no, "등급분류번호"));

					// 18. (조건에 맞는)결정등급에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_grade_name, "결정등급"));

					// 19. (조건에 맞는)결정의견에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_deter_opin_name, "결정의견"));

					// 20. (조건에 맞는)주제에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name1, "주제"));

					// 21. (조건에 맞는)선정성에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name2, "선정성"));

					// 22. (조건에 맞는)폭력성에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name3, "폭력성"));

					// 23. (조건에 맞는)대사에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name4, "대사"));

					// 24. (조건에 맞는)공포에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name5, "공포"));

					// 25. (조건에 맞는)약물에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name6, "약물"));

					// 26. (조건에 맞는)모방위험에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_std_name7, "모방위험"));

					// 27. (조건에 맞는)연소자 유해성 여부에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_minor_malef_yn, "연소자 유해성"));

					// 28. (조건에 맞는)취하여부에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_wdra_yn, "취하여부"));

					// 29. (조건에 맞는)취하,반납에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_return_name, "취하,반납"));

					// 30. (조건에 맞는)진행 상태 코드에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_proc_state_code, "진행 상태 코드"));

					// 31. (조건에 맞는)상태코드에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_state_code, "상태코드"));

					// 32. (조건에 맞는)불완전데이터 플래그에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_non_com_yn, "불완전데이터 플래그"));

					// 33. (조건에 맞는)내용정보표시항목1에 대한 건수 및 데이터 조회
					perform01_sideInformation
							.add(mediurlEncodingResult(walker, paramMap, request, response, result_noticeUrl, encodeAnd,
									orderValue, medi_code, searchCol_rt_core_harm_rsn_code1, "내용정보표시항목1"));

					// 34. (조건에 맞는)내용정보표시항목2에 대한 건수 및 데이터 조회
					perform01_sideInformation
							.add(mediurlEncodingResult(walker, paramMap, request, response, result_noticeUrl, encodeAnd,
									orderValue, medi_code, searchCol_rt_core_harm_rsn_code2, "내용정보표시항목2"));

					// 35. (조건에 맞는)내용정보표시항목3에 대한 건수 및 데이터 조회
					perform01_sideInformation
							.add(mediurlEncodingResult(walker, paramMap, request, response, result_noticeUrl, encodeAnd,
									orderValue, medi_code, searchCol_rt_core_harm_rsn_code3, "내용정보표시항목3"));

					/**************** 텍스트 데이터 시작!!! ******************/
					// 36. (조건에 맞는)작품내용(줄거리) 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_work_cont, "작품내용(줄거리)"));

					// 37. (조건에 맞는)결정사유에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_deter_rsn, "결정사유"));

					// 38. (조건에 맞는)주요의견에 대한 건수 및 데이터 조회
					perform01_sideInformation
							.add(mediurlEncodingResult(walker, paramMap, request, response, result_noticeUrl, encodeAnd,
									orderValue, medi_code, searchCol_major_opin_narrn_cont, "주요의견"));

					// 39. (조건에 맞는)핵심유해사유에 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_core_harm_rsn, "핵심유해사유"));
					/**************** 텍스트 데이터 끝!!! ******************/

					/**************** 숫자 데이터 시작 (null처리)!!! ******************/
					if (searchStrInt != 0) {
						// 40. (조건에 맞는)신청일자에 대한 건수 및 데이터 조회
						perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
								result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_sub_rcv_date, "신청일자"));

						// 41. (조건에 맞는)접수일자에 대한 건수 및 데이터 조회
						perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
								result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rcv_date, "접수일자"));

						// 42. (조건에 맞는)등급분류일에 대한 건수 및 데이터 조회
						perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
								result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_rt_date, "등급분류일"));

						// 43. (조건에 맞는)외국인 인원수에 대한 건수 및 데이터 조회
						perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
								result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_pfm_pnum, "외국인 인원수"));

						// 44. (조건에 맞는)계약 시작 일자에 대한 건수 및 데이터 조회
						perform01_sideInformation
								.add(mediurlEncodingResult(walker, paramMap, request, response, result_noticeUrl,
										encodeAnd, orderValue, medi_code, searchCol_contr_start_date, "계약 시작 일자"));

						// 45. (조건에 맞는)계약 종료 일자에 대한 건수 및 데이터 조회
						perform01_sideInformation
								.add(mediurlEncodingResult(walker, paramMap, request, response, result_noticeUrl,
										encodeAnd, orderValue, medi_code, searchCol_contr_end_date, "계약 종료 일자"));

						// 46. (조건에 맞는)신규추천번호에 대한 건수 및 데이터 조회
						perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
								result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol_ori_rt_no, "신규추천번호"));
					}
					/**************** 숫자 데이터 끝 (null처리)!!! ******************/

					Collections.sort(perform01_sideInformation, new sideMenuCntComparator());
				} // if(searchGubun.equals("All")) 끝
				if (searchGubun.equals("title")) {
					// (조건에 맞는)원제명 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol, "원제명/사용제명"));
				}
				if (searchGubun.equals("decision")) {
					// (조건에 맞는)결정사유 대한 건수 및 데이터 조회
					perform01_sideInformation.add(mediurlEncodingResult(walker, paramMap, request, response,
							result_noticeUrl, encodeAnd, orderValue, medi_code, searchCol, "결정사유"));
				}
				// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
				walker.setValue("perform01_sideInformation", perform01_sideInformation);

				/**************
				 * ■■■■■■■■■■■■■***************************************우측사이드에 관한
				 * information
				 * 끝***********************************************■■■■■■■■■
				 **************************************************/

				/*************************************************************
				 * 검색어 처리 끝
				 *************************************************************************************************/

			} else {
				// 검색어가 공백이라면? // 통합검색이든, 제목이든 내용이든 // 영화매체에 관한 모든 데이터가 등장해줘야함
				totalUrl = result_noticeUrl + medi_code + orderValue;
			}

			/*************************************************************
			 * 얻어낸 Url 결과물로 추가적인 작업시작 / 페이징 처리 시작
			 *************************************************************************************************/
			String paging_totalUrl = totalUrl + "&pagelength=" + detailresultLine + "&offset=" + offset + "&limit="
					+ detailresultLine + "&hilite-fields="
					+ "%7B%22ori_title%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22aplc_name%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22rt_no%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22grade_name%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22rt_no%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22work_cont%22%3A%7B%22length%22%3A3000%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22deter_rsn%22%3A%7B%22length%22%3A3000%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D";

			List<Object> dataList = pagingURLEncodingFunc(paging_totalUrl);
			perform01.put("total_count_page", (Integer) dataList.get(0));
			walker.setValue("perform01_resultMapList", (List<Map<String, String>>) dataList.get(1));
			/*************************************************************
			 * 얻어낸 Url 결과물로 추가적인 작업시작 / 페이징 처리 시작
			 *************************************************************************************************/

			// totalURLCntFunc로 총합 갯수만 가져오고있다. -> 데이터가 필요하다면 해당 메소드를 안에
			// resultMap을 확인하면 됨.
			Integer total_count = totalURLCntFunc(totalUrl);
			perform01.put("total_count", total_count);
			walker.setValue("perform01", perform01);
		}
		///////////////////////////////////////////// 공연추천 끝 (17521, 17522,
		///////////////////////////////////////////// 17523,
		///////////////////////////////////////////// 17524)///////////////////////////////////////////////////////////////////////////////////////////////////

	}

	/**
	 * 
	 * @param walker
	 * @param paramMap
	 * @param request
	 * @param response
	 * @throws Exception
	 *             파일 매체에 대한 데이터를 추출
	 */
	public void file01(Walker walker, Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// null처리는 필수!
		// 파라미터 목록 가져오기. * 검색어, 검색구분, 섹션구분
		String searchStr = (String) paramMap.get("searchStr");
		String searchGubun = (String) paramMap.get("searchGubun");
		String[] sectionGubun = null;
		if (request.getParameterValues("sectionGubun") == null) {
			sectionGubun = new String[] { "Total" };
		} else {
			sectionGubun = request.getParameterValues("sectionGubun");
		}
		List<String> sectionGubunList = new ArrayList<String>(Arrays.asList(sectionGubun));

		/*************** 검색어에 숫자가 들어올떄 *******************************/
		long searchStrInt = 0;
		if (paramMap.get("searchStr") != null && ((String) paramMap.get("searchStr")).matches("[-+]?\\d*\\.?\\d+")) {
			searchStrInt = Long.valueOf((String) paramMap.get("searchStr"));
		}
		/*************** 검색어에 숫자가 들어올떄 *******************************/
		// 최종 URL
		String totalUrl = "";
		// 공통 URL
		String result_noticeUrl = "http://192.168.11.242:7577/search5?select=*&from=result_notice.result_notice&where=";
		String result_opinUrl = "http://192.168.11.242:7577/search5?select=*&from=result_opin.result_opin&where=";
		String result_fileUrl = "http://192.168.11.242:7577/search5?select=*&from=result_file.result_file&where=";
		String result_imageUrl = "http://192.168.11.242:7577/search5?select=*&from=result_image.result_image&where=";
		String result_moniUrl = "http://192.168.11.242:7577/search5?select=*&from=result_moni.result_moni&where=";
		String result_surveyUrl = "http://192.168.11.242:7577/search5?select=*&from=result_survey.result_survey&where=";
		// 공통 인코딩 목록
		String encode = "UTF-8";
		String encodeSearchStr = URLEncoder.encode(searchStr, encode); // 검색어
		String encodeSearchGubun = URLEncoder.encode(searchGubun, encode); // 검색구분(통합검색,
																			// 제목,
																			// 내용,
																			// 결정사유
																			// 등등)
		String encodeAnd = URLEncoder.encode(" and ", encode);
		String encodeOr = URLEncoder.encode(" or ", encode);
		String encodeAns = URLEncoder.encode("=", encode);
		String encodeInOpen = URLEncoder.encode(" in{", encode);
		String encodeInClose = URLEncoder.encode("}", encode);
		String encodeLGual = "(";
		String encodeRGual = ")";
		String encodeLike = URLEncoder.encode(" like ", encode);
		String strMark = URLEncoder.encode("'", encode);
		String between = URLEncoder.encode(" between ", encode);

		// where절의 text타입에만 사용가능.
		String allword = URLEncoder.encode(" allword ", encode);
		String anyword = URLEncoder.encode(" anyword ", encode);
		String natural = URLEncoder.encode(" natural ", encode);

		// 동의어 검색
		String synonym = URLEncoder.encode(" synonym ", encode);

		// 카테고리 정렬 ex) ORDER BY $CATEGORYFIELD(field_name(domain), keyword)
		String categoryOrder = URLEncoder.encode(" $categoryfield(use_title(), 검색어넣기 ", encode);

		/**
		 * 17501 : 국내영화 17502 : 국외영화 17505 : 광고영화 17511 : 국내비디오 17512 : 국외비디오
		 */

		// medi_code 인코딩 목록
		String medi_mv = URLEncoder.encode("'17501','17502'", encode);
		String medi_ad = URLEncoder.encode("'17503','17504','17505','17506','17514'", encode);
		String medi_video = URLEncoder.encode("'17511','17512'", encode);
		String medi_perform = URLEncoder.encode("'17521','17522','17523','17524'", encode);

		/*
		 * //첫 로딩 시 데이터를 표시해주는 역할을 함. if(searchGubun == null ){ searchGubun =
		 * "All"; } else if(searchGubun.trim().isEmpty()){ searchGubun = "All";
		 * }
		 */

		// 부가설명 : 처음 orderValue로 JSP에서 넘어온 정렬값을 받아서, 넘어온 정렬값을 기준으로 url인코딩을 실시한다.
		String orderValue = (String) request.getParameter("orderValue");
		if (orderValue == null || orderValue.equals("")) {
			orderValue = URLEncoder.encode(" order by $match(rt_no) desc, $relevance desc, $rowid desc ", encode);
		}
		if (orderValue.equals("orderRelevance")) {
			orderValue = URLEncoder.encode(" order by $match(rt_no) desc, $relevance desc, $rowid desc ", encode);
		}
		if (orderValue.equals("orderGa")) {
			orderValue = "%20order%20by%20use_title_sort%20asc%20";
		}
		if (orderValue.equals("orderDate")) {
			orderValue = "%20order%20by%20rcv_date%20desc%20";
		}

		//
		String groupByTest = URLEncoder.encode(" order by $relevance desc ", encode);

		// 2022 12 15 페이징 처리 시작

		// 1. sectionGubun을 하나만 선택하거나, -더보기 클릭 시, sectionGubun에는 딱 하나의 데이터가
		// 들어있고, 페이지는 1페이지로 이동한다. -->sectionGubun이 딱 하나라면 (Total)이 아닐떄 , 페이징목록이
		// 보이게끔 유도하기.
		// 페이지는 페이지 목록 버튼 시 마다 2페이지 3페이지 이동한다... 그러다가 다시 검색버튼 같은것을 누르면 1페이지로
		// 초기화!

		// 기존 display가 limit로 대체

		int cPage = 0;
		int offset = 0;
		int pagelength = 0;

		/**************************
		 * 20221223 페이징처리를 위한 새로운 로직 (영화, 비디오 반영완료)
		 *********************************************/
		if (((String) paramMap.get("isPaging")).equals("Y")) {
			cPage = (Integer) paramMap.get("cPage");
			offset = (Integer) paramMap.get("offset");
			pagelength = (Integer) paramMap.get("pagelength");
			offset = (cPage - 1) * pagelength;
		} else {
			cPage = 1;
			offset = 0;
			pagelength = 10;
			offset = (cPage - 1) * pagelength;
		}

		/*******************************************************************/

		/**************** 상세보기 시작 ********************************/
		// 20221216 상세보기에 관련된 것들 맨 위로 빼기 (기본값이 존재함)
		// 하지만 상세보기를 하지 않는다면 이 값이 null이라서 오류가뜸 ~~~~~~~~~~~~~~~~~~~~~~~~~~~

		// 상세보기를 클릭했는지 여부
		String detail_visibleChk = (String) paramMap.get("detail_visibleChk");

		// 연산자 가져오기
		String searchOperation = "";

		// 결과 수
		int detailresultLine = 3;
		try {
			if ((String) paramMap.get("detailSearch_resultLine") != null) {
				detailresultLine = Integer.valueOf((String) paramMap.get("detailSearch_resultLine"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (detailresultLine != 3 && (String) paramMap.get("detailSearch_resultLine") != null) {
				detailresultLine = detailresultLine;
			} else {
				detailresultLine = 3;
			}
		}

		try {
			if (detail_visibleChk.equals("Y")) {
				searchOperation = (String) paramMap.get("detailSearch_radio");
			} else {
				searchOperation = "natural";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_radio") == null) {
				searchOperation = "natural";
			}
		}

		if (searchOperation.equals("allword")) {
			searchOperation = natural;
		} else if (searchOperation.equals("anyword")) {
			searchOperation = natural;
		} else if (searchOperation.equals("natural")) {
			searchOperation = natural;
		} else {
			searchOperation = natural;
		}
		
		if(searchStr.indexOf("-") != -1){
			searchOperation = allword;
		}

		// 신청회사 null 처리
		String detailAplc = "";
		try {
			if ((String) paramMap.get("detailSearch_aplc") != null) {
				detailAplc = (String) paramMap.get("detailSearch_aplc");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_aplc") == null) {
				detailAplc = "";
			}
		}

		// 상세보기-일자 데이터 가져오기 및 null 처리 시작

		// 시작날짜
		String period_start = "";
		try {
			if ((String) paramMap.get("period_start") != null) {
				period_start = (String) paramMap.get("period_start");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("period_start") == null) {
				period_start = "";
			}
		}

		// 종료날짜
		String period_end = "";
		try {
			if ((String) paramMap.get("period_end") != null) {
				period_end = (String) paramMap.get("period_end");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("period_end") == null) {
				period_end = "";
			}
		}

		// 등급분류일자, 신청일자 , 접수일자 구분
		String detail_dateGubun = "";
		try {
			if ((String) paramMap.get("detailSearch_date") != null) {
				detail_dateGubun = (String) paramMap.get("detailSearch_date");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_date") == null) {
				detail_dateGubun = "gradedate";
			}
		}

		if (!period_start.equals("")) {
			period_start = period_start.substring(0, 4) + period_start.substring(5, 7) + period_start.substring(8, 10)
					+ "000000";
		}
		if (!period_end.equals("")) {
			period_end = period_end.substring(0, 4) + period_end.substring(5, 7) + period_end.substring(8, 10)
					+ "000000";
		}

		// 듈 중에 하나를 선택안하고 상세검색을 하면 --> 전체날짜로 인식하여 데이터검색하기
		if ((period_start.equals("") || period_end.equals("")) && !(period_start.equals("") && period_end.equals(""))) {
			period_start = "";
			period_end = "";

		}
		// 상세보기-일자 데이터 가져오기 및 null 처리 끝

		
		
		//따옴표 기능 추가 시작

				//따옴표기능 변수1 : 반드시 포함해야 하는 따옴표 안의 단어.
				String quote_searchStr_origin = "";
				//따옴표기능 변수2 : 반드시 포함해야 하는 따옴표 안의 단어 (인코딩 받아줄 변수).
				String quote_searchStr = ""; 
				
				

				//따옴표기능 로직1 : 따옴표의 인덱스 위치를 추출한다.
				String word = "\""; 
				int searchStrIndex = searchStr.indexOf(word);
				List<Integer> searchStrIndexList = new ArrayList<Integer>();
				 while(searchStrIndex != -1) {
					 searchStrIndexList.add(searchStrIndex);
					 searchStrIndex = searchStr.indexOf(word, searchStrIndex+word.length()); 
				 }
				
				 
				 
				//따옴표기능 로직2 : 따옴표의 인덱스가 2개이상 나왔다면, 따옴표가 4개있든 7개 있든, 제일 첫번째로 나오는 따옴표 2개의 쌍을 구하고, 그 따옴표로 깜사져 있는 단어를 추출한다.
				if(searchStrIndexList.size() >= 2 ) {
					 int firstIndex = searchStrIndexList.get(0);
					 int lastIndex = searchStrIndexList.get(1);
					 quote_searchStr_origin = searchStr.substring(firstIndex + 1, lastIndex);  
					 quote_searchStr = URLEncoder.encode(quote_searchStr_origin, encode);
				 }
				
				 
				 
				 
				 //따옴표기능 로직3 : 따옴표의 인덱스가 일단 하나 이상 등장한다면, 한개 이상일 경우는 따옴표를 제거해주고, 따옴표가 2개이상인 경우에는 위에서 얻어온 '따옴표 안'의 단어를 따로 추출하여 인코딩한다.
				             // --> 차후에 따옴표 검색이 이뤄질때, 헤어질 결심 "수사관" 검색 시, --> (헤어질 결심 ) AND ("수사관") 이런식으로 검색 하게끔 해줌.
				 if(searchStrIndexList.size() >= 1 ){
					 
					String searchStr2 = searchStr.replaceAll("\"", "");
					String searchStr3 = searchStr2.replaceAll( quote_searchStr_origin , "").trim(); 
					if(!searchStr3.equals("") && searchStr3 != null){
						encodeSearchStr = URLEncoder.encode(searchStr3, encode); // 검색어
					}
				 }
				 
				 
				 
				 // 따옴표에 들어간 단어에 맞춰서 조건을 추가해주기 위해서 셋팅하는 작업 
				 String quote_searchCol = "";
				 if(!quote_searchStr.equals("")) {
		    /**********************************************file01() quote_searchStr start*************************************************************************/	 
				 quote_searchCol += encodeAnd + "(" + "use_title" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + "synonym('d0')"  
						 + encodeOr + "ori_title" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + "synonym('d0')"  
						 + encodeOr + "rcv_no_view"	+ encodeAns + strMark + quote_searchStr + strMark 
						 + encodeOr + "medi_name" + encodeAns + strMark + quote_searchStr + strMark 
						 + encodeOr + "prodc_name" + encodeAns + strMark + quote_searchStr + strMark 
						 + encodeOr + "prodc_natnl_name" + encodeAns + strMark + quote_searchStr + strMark 
						 + encodeOr + "dire_name" + encodeAns + strMark + quote_searchStr + strMark 
						 + encodeOr + "mv_asso_name" + encodeAns + strMark + quote_searchStr + strMark 
						 + encodeOr + "leada_name" + encodeAns + strMark + quote_searchStr + strMark 
						 + encodeOr + "kind_name" + encodeAns + strMark + quote_searchStr + strMark 
						 + encodeOr + "hope_grade_name" + encodeAns + strMark + quote_searchStr + strMark 
						 + encodeOr + "prod_year" + encodeAns + strMark + quote_searchStr + strMark 
						 + encodeOr + "aplc_name" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + "synonym('d0')"
						 + encodeOr + "rt_no" + encodeAns + strMark + quote_searchStr + strMark 
						 + encodeOr + "grade_name" + encodeAns + strMark + quote_searchStr + strMark 
						// 기본매체에 존재한 텍스트타입
						 + encodeOr + "label_div_id" + encodeAns + strMark + quote_searchStr + strMark 
						 + encodeOr + "file_ext" + encodeAns + strMark + quote_searchStr + strMark 

						// 텍스트타입
						 + encodeOr + "deter_rsn" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + "synonym('d0')"
						 + encodeOr + "file_name" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + "synonym('d0')";

				// int는 encode를 따로 안해줘도 무방함.
				if (searchStrInt != 0) {
					quote_searchCol += encodeOr + "rcv_date" + encodeAns + searchStrInt + "000000" + // no3
							encodeOr + "rt_date" + encodeAns + searchStrInt + "000000"; // no17
				}
				// 통합검색의 마무리 괄호
				quote_searchCol += ")";
		}
		/**********************************************file01() quote_searchStr end*************************************************************************/
		/**************** 상세보기 끝 ********************************/

		// 하지만 상세보기를 하지 않는다면 이 값이 null이라서 오류가뜸 ~~~~~~~~~~~~~~~~~~~~~~~~~~~

		// 첨부파일 : file01
		// 매체파일에서 바뀌는점 : 검색구분에 따라 컬럼명을 바꿔줘야함.... 예를들어 기존에는 [검색구분 : 제목]으로 선택을하면
		// use_title에 조건을 줬다면, 이제는 다른 컬럼이다.
		// medi_code도 의미없다. --> 테이블 전체임.

		// ★특이사항 : 이미지와 99% 유사함 .
		// label_div_id가 이미지에는 없지만, 파일에는 있음
		// ori_title이 파일은 text 타입이고, 이미지에서는 string 타입임.
		///////////////////////////////////////////// 첨부파일 시작
		// ()///////////////////////////////////////////////////////////////////////////////////////////////////

		if (sectionGubunList.contains("file01") || sectionGubunList.contains("Total")) {

			Map<String, Object> file01 = new HashMap<String, Object>();

			if (searchStr != null) {
				/*************************************************************
				 * 검색어 처리 시작
				 *************************************************************************************************/

				// 검색구분은 if절로서 쓰이고, 검색어는 where절 안에 쓰임으로써, 직접적인 쿼리에 영향을 줌.
				String searchCol = ""; // where절을 담을 변수
				String searchCol_ori_title = "";
				String searchCol_use_title = "";
				String searchCol_rcv_no_view = "";
				String searchCol_medi_name = "";
				String searchCol_prodc_name = "";
				String searchCol_prodc_natnl_name = "";
				String searchCol_dire_name = "";
				String searchCol_mv_asso_name = "";
				String searchCol_leada_name = "";
				String searchCol_kind_name = "";
				String searchCol_hope_grade_name = "";
				String searchCol_prod_year = "";
				String searchCol_aplc_name = "";
				String searchCol_rt_no = "";
				String searchCol_grade_name = "";
				String searchCol_label_div_id = "";
				String searchCol_file_ext = "";

				String searchCol_deter_rsn = "";
				String searchCol_file_name = "";

				String searchCol_rcv_date = "";
				String searchCol_rt_date = "";

				// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
				if (searchGubun.equals("All")) {
					searchCol += "(" + "use_title" + encodeAns + strMark + encodeSearchStr + strMark + "+allword+" + "synonym('d0')" + 
								encodeOr + "use_title" + encodeAns + strMark + encodeSearchStr + strMark	+ searchOperation + "synonym('d0')" + 
							encodeOr + "ori_title" + encodeAns + strMark
							+ encodeSearchStr + strMark + searchOperation + "synonym('d0')" + encodeOr + "rcv_no_view"
							+ encodeAns + strMark + encodeSearchStr + strMark + // no2
																				// 접수번호
																				// rcv_no_view
																				// |string
																				// like
																				// X
							encodeOr + "medi_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no5
																													// 매체명
																													// |string
							encodeOr + "prodc_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no8
																														// 제작사명
																														// |string
							encodeOr + "prodc_natnl_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no9 제작사 국적 |string
							encodeOr + "dire_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no10
																													// 감독명
																													// |string
							encodeOr + "mv_asso_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no11
																														// 영화
																														// 종류
																														// |string
							encodeOr + "leada_name" + encodeAns + strMark + encodeSearchStr + strMark	+ searchOperation + // no12
																														// 주연명
																														// |string
							encodeOr + "kind_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no13
																													// 종류
																													// |string
							encodeOr + "hope_grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no14 희망등급 |string
							encodeOr + "prod_year" + encodeAns + strMark + encodeSearchStr + strMark + // no15
																										// 제작년도
																										// |string
																										// like
																										// X
							encodeOr + "aplc_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation +  // no16
																													// 신청회사
																													// |string
							encodeOr + "rt_no" + encodeAns + strMark + encodeSearchStr + strMark + // no17
																									// 등급분류번호
																									// |string
																									// like
																									// X
							encodeOr + "grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no19
																														// 결정등급
																														// |string

							// 기본매체에 존재한 텍스트타입
							encodeOr + "label_div_id" + encodeAns + strMark + encodeSearchStr + strMark + // no21
																											// 레이블
																											// 구분
																											// 아이디
																											// |string
																											// like
																											// X
							encodeOr + "file_ext" + encodeAns + strMark + encodeSearchStr + strMark + // no33
																										// 파일
																										// 확장자명
																										// |string
																										// like
																										// X

							// 텍스트타입
							encodeOr + "deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark + "+someword+" + // no19
																															// 결정사유
																															// |text
							encodeOr + "file_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation; // no25
																														// 서비스
																														// 경로
																														// |text

					// int는 encode를 따로 안해줘도 무방함.
					if (searchStrInt != 0) {
						searchCol += encodeOr + "rcv_date" + encodeAns + searchStrInt + "000000" + // no3
																									// 접수일자
																									// |
																									// int(date)
								encodeOr + "rt_date" + encodeAns + searchStrInt + "000000"; // no17
																							// 등급분류일
																							// |
																							// int(date)
					}

					// 통합검색의 마무리 괄호
					searchCol += ")";

					// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
					// 1. 원제명Url (ori_title이 string타입이다)
					searchCol_ori_title = "(" + "ori_title" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + "synonym('d0')" + ")";

					// 2. 사용제명Url (텍스트지만, 중요도에 따라 맨위에 위치)
					searchCol_use_title = "(" + "use_title" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + "synonym('d0')" + ")";

					// 3. 접수번호Url
					searchCol_rcv_no_view = "(" + "rcv_no_view" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 4. 매체명Url
					searchCol_medi_name = "(" + "medi_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 5. 제작사 국적Url
					searchCol_prodc_natnl_name = "(" + "prodc_natnl_name" + encodeAns + strMark + encodeSearchStr
							+ strMark + ")";
					// 6. 제작사명Url
					searchCol_prodc_name = "(" + "prodc_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";

					// 7. 감독명Url
					searchCol_dire_name = "(" + "dire_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 8. 영화 종류
					searchCol_mv_asso_name = "(" + "mv_asso_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 9. 주연명
					searchCol_leada_name = "(" + "leada_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + ")";
					// 10. 종류
					searchCol_kind_name = "(" + "kind_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 11. 희망등급
					searchCol_hope_grade_name = "(" + "hope_grade_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";

					// 12. 제작년도
					searchCol_prod_year = "(" + "prod_year" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 13. 신청회사
					searchCol_aplc_name = "(" + "aplc_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + ")";
					// 14. 등급분류번호
					searchCol_rt_no = "(" + "rt_no" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 15. 결정등급
					searchCol_grade_name = "(" + "grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";

					// 16 레이블 구분 아이디
					searchCol_label_div_id = "(" + "label_div_id" + encodeAns + strMark + encodeSearchStr + strMark
							+ ")";
					// 17 파일 확장자명
					searchCol_file_ext = "(" + "file_ext" + encodeAns + strMark + encodeSearchStr + strMark + ")";

					/******* 텍스트 *********/
					// 19. 결정사유
					searchCol_deter_rsn = "(" + "deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+" + ")";
					// 20. 파일 명
					searchCol_file_name = "(" + "file_name" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + ")";

					/*** 검색어가 숫자인 경우 추가되는 항목!! ***/
					if (searchStrInt != 0) {

						// 21. 접수일자
						searchCol_rcv_date = "(" + "rcv_date" + encodeAns + searchStrInt + "000000" + ")";
						// 22. 등급분류일
						searchCol_rt_date = "(" + "rt_date" + encodeAns + searchStrInt + "000000" + ")";

					}

				}
				if (searchGubun.equals("title")) {
					searchCol += "(use_title" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ "synonym('d0')" + encodeOr + "ori_title" + encodeAns + strMark + encodeSearchStr
							+ strMark + searchOperation + "synonym('d0')" + ")";
				}
				if (searchGubun.equals("decision")) {
					searchCol += "(deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ "synonym('d0')" + ")";
				}

				if (searchStr.equals("NO_VALUE")) {
					searchCol = "ori_title+not+in%7B%7D+";
					searchCol_rcv_no_view = "ori_title+not+in%7B%7D+";
					searchCol_medi_name = "ori_title+not+in%7B%7D+";
					searchCol_prodc_name = "ori_title+not+in%7B%7D+";
					searchCol_prodc_natnl_name = "ori_title+not+in%7B%7D+";
					searchCol_dire_name = "ori_title+not+in%7B%7D+";
					searchCol_mv_asso_name = "ori_title+not+in%7B%7D+";
					searchCol_leada_name = "ori_title+not+in%7B%7D+";
					searchCol_kind_name = "ori_title+not+in%7B%7D+";
					searchCol_hope_grade_name = "ori_title+not+in%7B%7D+";
					searchCol_prod_year = "ori_title+not+in%7B%7D+";
					searchCol_aplc_name = "ori_title+not+in%7B%7D+";
					searchCol_rt_no = "ori_title+not+in%7B%7D+";
					searchCol_grade_name = "ori_title+not+in%7B%7D+";
					searchCol_file_ext = "ori_title+not+in%7B%7D+";
					searchCol_label_div_id = "ori_title+not+in%7B%7D+";

					// 텍스트타입
					searchCol_ori_title = "ori_title+not+in%7B%7D+";
					searchCol_use_title = "ori_title+not+in%7B%7D+";
					searchCol_deter_rsn = "ori_title+not+in%7B%7D+";
					searchCol_file_name = "ori_title+not+in%7B%7D+";

					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_rcv_date = "ori_title+not+in%7B%7D+";
						searchCol_rt_date = "ori_title+not+in%7B%7D+";
					}
				}
				
				
				
				
				
				
				
				
				
				

				// 상세검색 일때만 뒤에 이걸 붙여주자!
				/**************** 일자 통합작업중 ***********************/
				if (detail_visibleChk.equals("Y")) {
       ///////////////////////////////AND/OR 연산조건 관련 변수 초기화  시작///////////////////////////////////////////////////////////		
			//1. 상세검색 조건내에 선택한  연산자에 따라서 and를 할지 or을 할지 정하는 로직. 
			// encodeTotalChk : 상세조건 내 AND/OR 연산자 무엇을 선택했는지에 대한 값을 지니고 있는 변수 (JSP에서 넘어오는 데이터)
			String encodeTotalChk = (String) paramMap.get("detailSearch_radio");
			// encodeTotal : 상세조건 내 AND/OR 연산자 선택에 따라 And나 Or 값을 지니고 있는 변수
			String encodeTotal = ""; 
			
			if(encodeTotalChk != null){
				if(encodeTotalChk.equals("allword")){
					encodeTotal = encodeAnd;
				} else if(encodeTotalChk.equals("anyword")){
					encodeTotal = encodeOr;
				} 
			} else {
				encodeTotal = encodeAnd;
			}
			
			//2. 상세검색 조건문에 들어오면 AND연산자 해주고,  괄호도  열어주기. (상세검색 조건문 끝날떄쯤 괄호 닫아줄 것)
			// 상세검색 시 이 구문이 기본적으로 실행되며, 구조는 "(기존검색) AND (" 인데, // 여기서 "AND (" 부분을 셋팅해주는 구문이다. 		
				searchCol +=  encodeAnd + encodeLGual;
				searchCol_rcv_no_view +=  encodeAnd + encodeLGual;
				searchCol_medi_name +=  encodeAnd + encodeLGual;
				searchCol_prodc_name +=  encodeAnd + encodeLGual;
				searchCol_prodc_natnl_name +=  encodeAnd + encodeLGual;
				searchCol_dire_name +=  encodeAnd + encodeLGual;
				searchCol_mv_asso_name +=  encodeAnd + encodeLGual;
				searchCol_leada_name +=  encodeAnd + encodeLGual;
				searchCol_kind_name +=  encodeAnd + encodeLGual;
				searchCol_hope_grade_name +=  encodeAnd + encodeLGual;
				searchCol_prod_year +=  encodeAnd + encodeLGual;
				searchCol_aplc_name +=  encodeAnd + encodeLGual;
				searchCol_rt_no +=  encodeAnd + encodeLGual;
				searchCol_grade_name +=  encodeAnd + encodeLGual;
				searchCol_file_ext +=  encodeAnd + encodeLGual;
				searchCol_label_div_id +=  encodeAnd + encodeLGual;
				// 텍스트타입
				searchCol_ori_title +=  encodeAnd + encodeLGual;
				searchCol_use_title +=  encodeAnd + encodeLGual;
				searchCol_deter_rsn +=  encodeAnd + encodeLGual;
				searchCol_file_name +=  encodeAnd + encodeLGual;
				// 숫자타입
				if (searchStrInt != 0) {
					searchCol_rcv_date +=  encodeAnd + encodeLGual;
					searchCol_rt_date +=  encodeAnd + encodeLGual;
				}
			
			
			
			
			
			
			
			//첫번째 조건인지 아닌지 확인하는 변수
			int firstConditionChk = 0;
	 ///////////////////////////////AND/OR 연산조건 관련 변수 초기화  끝///////////////////////////////////////////////////////////
					
					
					
					
					//file01
					// 일자보다 신청회사 먼저 붙여준다.
					if (!(detailAplc.equals("") || detailAplc == null)) {
						detailAplc = URLEncoder.encode(detailAplc, "UTF-8");
						
						// 첫번쨰 조건 체크 ------------- 시작 
						String chkResult = "";
						if(firstConditionChk == 0){
							firstConditionChk++;
						} else {
							chkResult = encodeTotal;
						}
						// 첫번쨰 조건 체크 ------------- 끝
						
						
						// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
						searchCol += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rcv_no_view += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_medi_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_prodc_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_prodc_natnl_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						;
						searchCol_dire_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_mv_asso_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_leada_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_kind_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_hope_grade_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_prod_year += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_aplc_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_rt_no += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_grade_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_file_ext += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_label_div_id += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;

						// 텍스트타입
						searchCol_ori_title += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_use_title += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_deter_rsn += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;
						searchCol_file_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
								+ strMark + searchOperation +  encodeRGual;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_rcv_date += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
							searchCol_rt_date += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc
									+ strMark + searchOperation +  encodeRGual;
						}

					}

					String detail_date = "";
					if (!period_start.equals("") && !period_end.equals("")) {
						
						// 첫번쨰 조건 체크 ------------- 시작 
						String chkResult = "";
						if(firstConditionChk == 0){
							firstConditionChk++;
						} else {
							chkResult = encodeTotal;
						}
						// 첫번쨰 조건 체크 ------------- 끝
						
						
						
						if (detail_dateGubun.equals("regdate")) {
							searchCol += chkResult + encodeLGual + "sub_rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 신청일자
							detail_date = chkResult + encodeLGual + "sub_rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;
						} else if (detail_dateGubun.equals("jubsudate")) {
							searchCol += chkResult + encodeLGual + "rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 접수일자
							detail_date = chkResult + encodeLGual + "rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;
						} else {
							searchCol += chkResult + encodeLGual + "rt_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 등급분류일자
							detail_date = chkResult + encodeLGual + "rt_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;
						}

						// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
						searchCol_dire_name += detail_date;
						searchCol_rcv_no_view += detail_date;
						searchCol_medi_name += detail_date;
						searchCol_prodc_name += detail_date;
						searchCol_prodc_natnl_name += detail_date;
						searchCol_mv_asso_name += detail_date;
						searchCol_leada_name += detail_date;
						searchCol_kind_name += detail_date;
						searchCol_hope_grade_name += detail_date;
						searchCol_prod_year += detail_date;
						searchCol_aplc_name += detail_date;
						searchCol_rt_no += detail_date;
						searchCol_grade_name += detail_date;
						searchCol_label_div_id += detail_date;
						searchCol_file_ext += detail_date;

						// 텍스트타입
						searchCol_ori_title += detail_date;
						searchCol_use_title += detail_date;
						searchCol_deter_rsn += detail_date;
						searchCol_file_name += detail_date;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_rcv_date += detail_date;
							searchCol_rt_date += detail_date;
						}
					} else {
						// 상세검색내 날짜조건이 "전체"라면 null이기 때문에 이 구문이 실행된다. 날짜가 전체 or 신청회사가 디즈니일때, 신청회사가 디즈니인것만 조건에 들어가는 오류가 발생함. 즉 날짜가 전체라는 조건이 빠져서 데이터가 디즈니인것만 나옴.
						// 첫번쨰 조건 체크 ------------- 시작 
						String chkResult = "";
						if(firstConditionChk == 0){
							firstConditionChk++;
						} else {
							chkResult = encodeTotal;
						}
						// 첫번쨰 조건 체크 ------------- 끝
						searchCol +=  chkResult + "ori_title+not+in%7B%7D+";
						searchCol_rcv_no_view +=  chkResult + "ori_title+not+in%7B%7D+";
						searchCol_medi_name +=  chkResult + "ori_title+not+in%7B%7D+";
						searchCol_prodc_name +=  chkResult + "ori_title+not+in%7B%7D+";
						searchCol_prodc_natnl_name +=  chkResult + "ori_title+not+in%7B%7D+";
						searchCol_dire_name +=  chkResult + "ori_title+not+in%7B%7D+";
						searchCol_mv_asso_name +=  chkResult + "ori_title+not+in%7B%7D+";
						searchCol_leada_name +=  chkResult + "ori_title+not+in%7B%7D+";
						searchCol_kind_name +=  chkResult + "ori_title+not+in%7B%7D+";
						searchCol_hope_grade_name +=  chkResult + "ori_title+not+in%7B%7D+";
						searchCol_prod_year +=  chkResult + "ori_title+not+in%7B%7D+";
						searchCol_aplc_name +=  chkResult + "ori_title+not+in%7B%7D+";
						searchCol_rt_no +=  chkResult + "ori_title+not+in%7B%7D+";
						searchCol_grade_name +=  chkResult + "ori_title+not+in%7B%7D+";
						searchCol_file_ext +=  chkResult + "ori_title+not+in%7B%7D+";
						searchCol_label_div_id +=  chkResult + "ori_title+not+in%7B%7D+";
						// 텍스트타입
						searchCol_ori_title +=  chkResult + "ori_title+not+in%7B%7D+";
						searchCol_use_title +=  chkResult + "ori_title+not+in%7B%7D+";
						searchCol_deter_rsn +=  chkResult + "ori_title+not+in%7B%7D+";
						searchCol_file_name +=  chkResult + "ori_title+not+in%7B%7D+";
						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_rcv_date +=  chkResult + "ori_title+not+in%7B%7D+";
							searchCol_rt_date +=  chkResult + "ori_title+not+in%7B%7D+";
						}
					}
					
					//만약에 상세조건에 아무것도 입력하지 않는다면, 더미데이터룰 넣어준다 --> 데이터가 없으면 오류가 나기 떄문이다.
					if(searchCol.substring(searchCol.length()-1).equals("(")){
						searchCol += "ori_title+not+in%7B%7D+";
						searchCol_rcv_no_view += "ori_title+not+in%7B%7D+";
						searchCol_medi_name += "ori_title+not+in%7B%7D+";
						searchCol_prodc_name += "ori_title+not+in%7B%7D+";
						searchCol_prodc_natnl_name += "ori_title+not+in%7B%7D+";
						searchCol_dire_name += "ori_title+not+in%7B%7D+";
						searchCol_mv_asso_name += "ori_title+not+in%7B%7D+";
						searchCol_leada_name += "ori_title+not+in%7B%7D+";
						searchCol_kind_name += "ori_title+not+in%7B%7D+";
						searchCol_hope_grade_name += "ori_title+not+in%7B%7D+";
						searchCol_prod_year += "ori_title+not+in%7B%7D+";
						searchCol_aplc_name += "ori_title+not+in%7B%7D+";
						searchCol_rt_no += "ori_title+not+in%7B%7D+";
						searchCol_grade_name += "ori_title+not+in%7B%7D+";
						searchCol_file_ext += "ori_title+not+in%7B%7D+";
						searchCol_label_div_id += "ori_title+not+in%7B%7D+";
						// 텍스트타입
						searchCol_ori_title += "ori_title+not+in%7B%7D+";
						searchCol_use_title += "ori_title+not+in%7B%7D+";
						searchCol_deter_rsn += "ori_title+not+in%7B%7D+";
						searchCol_file_name += "ori_title+not+in%7B%7D+";
						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_rcv_date += "ori_title+not+in%7B%7D+";
							searchCol_rt_date += "ori_title+not+in%7B%7D+";
						}
					}
					
					searchCol += encodeRGual;
					searchCol_rcv_no_view += encodeRGual;
					searchCol_medi_name += encodeRGual;
					searchCol_prodc_name += encodeRGual;
					searchCol_prodc_natnl_name += encodeRGual;
					searchCol_dire_name += encodeRGual;
					searchCol_mv_asso_name += encodeRGual;
					searchCol_leada_name += encodeRGual;
					searchCol_kind_name += encodeRGual;
					searchCol_hope_grade_name += encodeRGual;
					searchCol_prod_year += encodeRGual;
					searchCol_aplc_name += encodeRGual;
					searchCol_rt_no += encodeRGual;
					searchCol_grade_name += encodeRGual;
					searchCol_file_ext += encodeRGual;
					searchCol_label_div_id += encodeRGual;
					// 텍스트타입
					searchCol_ori_title += encodeRGual;
					searchCol_use_title += encodeRGual;
					searchCol_deter_rsn += encodeRGual;
					searchCol_file_name += encodeRGual;
					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_rcv_date += encodeRGual;
						searchCol_rt_date += encodeRGual;
					}
					
				}
				//detail_visibleChk 조건 끝
				
				//만약 따옴표 안에 단어가 인식이 된다면 이 로직 실행.
				if(!quote_searchStr.equals("")) {
					//통합검색이든, 메뉴명이든 뒤에 quote_searchCol 붙여주기 시작! 
					searchCol += quote_searchCol;
					searchCol_rcv_no_view += quote_searchCol;
					searchCol_medi_name += quote_searchCol;
					searchCol_prodc_name += quote_searchCol;
					searchCol_prodc_natnl_name += quote_searchCol;
					searchCol_dire_name += quote_searchCol;
					searchCol_mv_asso_name += quote_searchCol;
					searchCol_leada_name += quote_searchCol;
					searchCol_kind_name += quote_searchCol;
					searchCol_hope_grade_name += quote_searchCol;
					searchCol_prod_year += quote_searchCol;
					searchCol_aplc_name += quote_searchCol;
					searchCol_rt_no += quote_searchCol;
					searchCol_grade_name += quote_searchCol;
					searchCol_file_ext += quote_searchCol;
					searchCol_label_div_id += quote_searchCol;
					// 텍스트타입
					searchCol_ori_title += quote_searchCol;
					searchCol_use_title += quote_searchCol;
					searchCol_deter_rsn += quote_searchCol;
					searchCol_file_name += quote_searchCol;
					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_rcv_date += quote_searchCol;
						searchCol_rt_date += quote_searchCol;
					}
				}
				//통합검색이든, 메뉴명이든 뒤에 quote_searchCol 붙여주기 끝!
				/**************** 상세검색의 일자, 신청회사 통합작업중 ***********************/

				/**************
				 * ■■■■■■■■■■■■■*************************************우측사이드에 관한
				 * information 시작
				 * *************************************************■■■■■■■■■
				 **************************************************/
				List<Map<String, Object>> file01_sideInformation = new ArrayList<Map<String, Object>>();
				if (searchGubun.equals("All")) {
					// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
					// 1. (조건에 맞는)원제명에 대한 건수 및 데이터 조회
					file01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_fileUrl,
							orderValue, searchCol_ori_title, "원제명"));

					// 2. (조건에 맞는)사용제명에 대한 건수 및 데이터 조회
					file01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_fileUrl,
							orderValue, searchCol_use_title, "사용제명"));

					// 3. (조건에 맞는)접수번호에 대한 건수 및 데이터 조회
					file01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_fileUrl,
							orderValue, searchCol_rcv_no_view, "접수번호"));

					// 4. (조건에 맞는)매체명에 대한 건수 및 데이터 조회
					file01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_fileUrl,
							orderValue, searchCol_medi_name, "매체명"));

					// 5. (조건에 맞는)제작사명에 대한 건수 및 데이터 조회
					file01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_fileUrl,
							orderValue, searchCol_prodc_name, "제작사명"));

					// 6. (조건에 맞는)제작사 국적에 대한 건수 및 데이터 조회
					file01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_fileUrl,
							orderValue, searchCol_prodc_natnl_name, "제작사 국적"));

					// 7. (조건에 맞는)감독명에 대한 건수 및 데이터 조회
					file01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_fileUrl,
							orderValue, searchCol_dire_name, "감독명"));

					// 8. (조건에 맞는)영화 종류에 대한 건수 및 데이터 조회
					file01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_fileUrl,
							orderValue, searchCol_mv_asso_name, "영화 종류"));

					// 9. (조건에 맞는)주연명에 대한 건수 및 데이터 조회
					file01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_fileUrl,
							orderValue, searchCol_leada_name, "주연명"));

					// 10. (조건에 맞는)종류에 대한 건수 및 데이터 조회
					file01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_fileUrl,
							orderValue, searchCol_kind_name, "종류"));

					// 11. (조건에 맞는)희망등급에 대한 건수 및 데이터 조회
					file01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_fileUrl,
							orderValue, searchCol_hope_grade_name, "희망등급"));

					// 12. (조건에 맞는)제작년도에 대한 건수 및 데이터 조회
					file01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_fileUrl,
							orderValue, searchCol_prod_year, "제작년도"));

					// 13. (조건에 맞는)신청회사에 대한 건수 및 데이터 조회
					file01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_fileUrl,
							orderValue, searchCol_aplc_name, "신청회사"));

					// 14. (조건에 맞는)등급분류번호 에 대한 건수 및 데이터 조회
					file01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_fileUrl,
							orderValue, searchCol_rt_no, "등급분류번호"));

					// 15. (조건에 맞는)결정등급에 대한 건수 및 데이터 조회
					file01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_fileUrl,
							orderValue, searchCol_grade_name, "결정등급"));

					// 16. (조건에 맞는)레이블 구분 아이디에 대한 건수 및 데이터 조회
					file01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_fileUrl,
							orderValue, searchCol_label_div_id, "레이블 구분 아이디"));

					// 17. (조건에 맞는)파일 확장자명에 대한 건수 및 데이터 조회
					file01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_fileUrl,
							orderValue, searchCol_file_ext, "파일 확장자명"));

					// 텍스트 타입 시작
					// 18. (조건에 맞는)결정사유에 대한 건수 및 데이터 조회
					file01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_fileUrl,
							orderValue, searchCol_deter_rsn, "결정사유"));

					// 19. (조건에 맞는)파일 명 에 대한 건수 및 데이터 조회
					file01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_fileUrl,
							orderValue, searchCol_file_name, "파일 명"));

					/**************** 텍스트 데이터 끝!!! ******************/

					/**************** 숫자 데이터 시작 (null처리)!!! ******************/

					if (searchStrInt != 0) {
						// 20 (조건에 맞는)접수일자에 대한 건수 및 데이터 조회
						file01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response,
								result_fileUrl, orderValue, searchCol_rcv_date, "접수일자"));
						// 21 (조건에 맞는)등급분류일에 대한 건수 및 데이터 조회
						file01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response,
								result_fileUrl, orderValue, searchCol_rt_date, "등급분류일"));
					}
					/**************** 숫자 데이터 끝 (null처리)!!! ******************/

					Collections.sort(file01_sideInformation, new sideMenuCntComparator());
				} // if(searchGubun.equals("All")) 끝
				if (searchGubun.equals("title")) {
					// (조건에 맞는)원제명 대한 건수 및 데이터 조회
					file01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_fileUrl,
							orderValue, searchCol, "원제명/사용제명"));
				}
				if (searchGubun.equals("decision")) {
					// (조건에 맞는)결정사유 대한 건수 및 데이터 조회
					file01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_fileUrl,
							orderValue, searchCol, "결정사유"));
				}
				// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
				walker.setValue("file01_sideInformation", file01_sideInformation);

				/**************
				 * ■■■■■■■■■■■■■***************************************우측사이드에 관한
				 * information
				 * 끝***********************************************■■■■■■■■■
				 **************************************************/

				/*************************************************************
				 * 검색어 처리 끝
				 *************************************************************************************************/

				totalUrl = result_fileUrl + searchCol + orderValue;
				// 결과내 재검색이라면 ? 시작
				if (((String) paramMap.get("result_re_search_chk")).equals("Y")
						&& (String) paramMap.get("file01_historySearchQuery") != null) {
					totalUrl = (String) paramMap.get("file01_historySearchQuery") + encodeAnd + "(" + searchCol + ")"
							+ orderValue;
				}
				// 결과내 재검색이라면 ? 끝

				// 이전쿼리 저장 시작
				String historySearchQuery = "";
				historySearchQuery = result_fileUrl + "(" + searchCol + ")";
				// 만약 결과내재검색을 통한 검색이었다면, historySearchQuery(이전쿼리)는 , 이전쿼리 +
				// encodeAnd + 현재쿼리 일 것이다.
				if (((String) paramMap.get("result_re_search_chk")).equals("Y")
						&& (String) paramMap.get("file01_historySearchQuery") != null) {
					historySearchQuery = (String) paramMap.get("file01_historySearchQuery") + encodeAnd + "("
							+ searchCol + ")";
				}
				paramMap.put("file01_historySearchQuery", historySearchQuery);
				// 이전쿼리 저장 끝
			} else {
				// 검색어가 공백이라면? // 통합검색이든, 제목이든 내용이든 // 영화매체에 관한 모든 데이터가 등장해줘야함
				totalUrl = result_fileUrl + orderValue;
			}

			/*************************************************************
			 * 얻어낸 Url 결과물로 추가적인 작업시작 / 페이징 처리 시작
			 *************************************************************************************************/
			String paging_totalUrl = totalUrl + "&pagelength=" + detailresultLine + "&offset=" + offset + "&limit="
					+ detailresultLine + "&hilite-fields="
					+ "%7B%22use_title%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22ori_title%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22aplc_name%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22rt_no%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22file_ext%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22file_size%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22regt_id%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22file_name%22%3A%7B%22length%22%3A3000%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D";
			List<Object> dataList = pagingURLEncodingFunc(paging_totalUrl);
			file01.put("total_count_page", (Integer) dataList.get(0));
			walker.setValue("file01_resultMapList", (List<Map<String, String>>) dataList.get(1));
			/*************************************************************
			 * 얻어낸 Url 결과물로 추가적인 작업시작 / 페이징 처리 시작
			 *************************************************************************************************/

			// totalURLCntFunc로 총합 갯수만 가져오고있다. -> 데이터가 필요하다면 해당 메소드를 안에
			// resultMap을 확인하면 됨.
			Integer total_count = totalURLCntFunc(totalUrl);
			file01.put("total_count", total_count);
			walker.setValue("file01", file01);
		}
		///////////////////////////////////////////// 첨부파일 끝
		///////////////////////////////////////////// ()///////////////////////////////////////////////////////////////////////////////////////////////////
	}

	/**
	 * 
	 * @param walker
	 * @param paramMap
	 * @param request
	 * @param response
	 * @throws Exception
	 *             이미지 매체에 대한 데이터를 추출하는 메소드
	 */
	public void img01(Walker walker, Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// null처리는 필수!
		// 파라미터 목록 가져오기. * 검색어, 검색구분, 섹션구분
		String searchStr = (String) paramMap.get("searchStr");
		String searchGubun = (String) paramMap.get("searchGubun");
		String[] sectionGubun = null;
		if (request.getParameterValues("sectionGubun") == null) {
			sectionGubun = new String[] { "Total" };
		} else {
			sectionGubun = request.getParameterValues("sectionGubun");
		}
		List<String> sectionGubunList = new ArrayList<String>(Arrays.asList(sectionGubun));

		/*************** 검색어에 숫자가 들어올떄 *******************************/
		long searchStrInt = 0;
		if (paramMap.get("searchStr") != null && ((String) paramMap.get("searchStr")).matches("[-+]?\\d*\\.?\\d+")) {
			searchStrInt = Long.valueOf((String) paramMap.get("searchStr"));
		}
		/*************** 검색어에 숫자가 들어올떄 *******************************/
		// 최종 URL
		String totalUrl = "";
		// 공통 URL
		String result_noticeUrl = "http://192.168.11.242:7577/search5?select=*&from=result_notice.result_notice&where=";
		String result_opinUrl = "http://192.168.11.242:7577/search5?select=*&from=result_opin.result_opin&where=";
		String result_fileUrl = "http://192.168.11.242:7577/search5?select=*&from=result_file.result_file&where=";
		String result_imageUrl = "http://192.168.11.242:7577/search5?select=*&from=result_image.result_image&where=";
		String result_moniUrl = "http://192.168.11.242:7577/search5?select=*&from=result_moni.result_moni&where=";
		String result_surveyUrl = "http://192.168.11.242:7577/search5?select=*&from=result_survey.result_survey&where=";
		// 공통 인코딩 목록
		String encode = "UTF-8";
		String encodeSearchStr = URLEncoder.encode(searchStr, encode); // 검색어
		String encodeSearchGubun = URLEncoder.encode(searchGubun, encode); // 검색구분(통합검색,
																			// 제목,
																			// 내용,
																			// 결정사유
																			// 등등)
		String encodeAnd = URLEncoder.encode(" and ", encode);
		String encodeOr = URLEncoder.encode(" or ", encode);
		String encodeAns = URLEncoder.encode("=", encode);
		String encodeInOpen = URLEncoder.encode(" in{", encode);
		String encodeInClose = URLEncoder.encode("}", encode);
		String encodeLGual = "(";
		String encodeRGual = ")";
		String encodeLike = URLEncoder.encode(" like ", encode);
		String strMark = URLEncoder.encode("'", encode);
		String between = URLEncoder.encode(" between ", encode);

		// where절의 text타입에만 사용가능.
		String allword = URLEncoder.encode(" allword ", encode);
		String anyword = URLEncoder.encode(" anyword ", encode);
		String natural = URLEncoder.encode(" natural ", encode);

		// 동의어 검색
		String synonym = URLEncoder.encode(" synonym ", encode);

		// 카테고리 정렬 ex) ORDER BY $CATEGORYFIELD(field_name(domain), keyword)
		String categoryOrder = URLEncoder.encode(" $categoryfield(use_title(), 검색어넣기 ", encode);

		/**
		 * 17501 : 국내영화 17502 : 국외영화 17505 : 광고영화 17511 : 국내비디오 17512 : 국외비디오
		 */

		// medi_code 인코딩 목록
		String medi_mv = URLEncoder.encode("'17501','17502'", encode);
		String medi_ad = URLEncoder.encode("'17503','17504','17505','17506','17514'", encode);
		String medi_video = URLEncoder.encode("'17511','17522'", encode);
		String medi_perform = URLEncoder.encode("'17521','17522','17523','17524'", encode);

		/*
		 * //첫 로딩 시 데이터를 표시해주는 역할을 함. if(searchGubun == null ){ searchGubun =
		 * "All"; } else if(searchGubun.trim().isEmpty()){ searchGubun = "All";
		 * }
		 */

		// 부가설명 : 처음 orderValue로 JSP에서 넘어온 정렬값을 받아서, 넘어온 정렬값을 기준으로 url인코딩을 실시한다.
		String orderValue = (String) request.getParameter("orderValue");
		if (orderValue == null || orderValue.equals("")) {
			orderValue = URLEncoder.encode(" order by $match(rt_no) desc, $relevance desc, $rowid desc ", encode);
		}
		if (orderValue.equals("orderRelevance")) {
			orderValue = URLEncoder.encode(" order by $match(rt_no) desc, $relevance desc, $rowid desc ", encode);
		}
		if (orderValue.equals("orderGa")) {
			orderValue = "%20order%20by%20use_title_sort%20asc%20";
		}
		if (orderValue.equals("orderDate")) {
			orderValue = "%20order%20by%20rcv_date%20desc%20";
		}

		//
		String groupByTest = URLEncoder.encode(" order by $relevance desc ", encode);

		// 2022 12 15 페이징 처리 시작

		// 1. sectionGubun을 하나만 선택하거나, -더보기 클릭 시, sectionGubun에는 딱 하나의 데이터가
		// 들어있고, 페이지는 1페이지로 이동한다. -->sectionGubun이 딱 하나라면 (Total)이 아닐떄 , 페이징목록이
		// 보이게끔 유도하기.
		// 페이지는 페이지 목록 버튼 시 마다 2페이지 3페이지 이동한다... 그러다가 다시 검색버튼 같은것을 누르면 1페이지로
		// 초기화!

		// 기존 display가 limit로 대체

		int cPage = 0;
		int offset = 0;
		int pagelength = 0;

		/**************************
		 * 20221223 페이징처리를 위한 새로운 로직 (영화, 비디오 반영완료)
		 *********************************************/
		if (((String) paramMap.get("isPaging")).equals("Y")) {
			cPage = (Integer) paramMap.get("cPage");
			offset = (Integer) paramMap.get("offset");
			pagelength = (Integer) paramMap.get("pagelength");
			offset = (cPage - 1) * pagelength;
		} else {
			cPage = 1;
			offset = 0;
			pagelength = 10;
			offset = (cPage - 1) * pagelength;
		}

		/*******************************************************************/

		/**************** 상세보기 시작 ********************************/
		// 20221216 상세보기에 관련된 것들 맨 위로 빼기 (기본값이 존재함)
		// 하지만 상세보기를 하지 않는다면 이 값이 null이라서 오류가뜸 ~~~~~~~~~~~~~~~~~~~~~~~~~~~

		// 상세보기를 클릭했는지 여부
		String detail_visibleChk = (String) paramMap.get("detail_visibleChk");

		// 연산자 가져오기
		String searchOperation = "";

		// 결과 수
		int detailresultLine = 3;
		try {
			if ((String) paramMap.get("detailSearch_resultLine") != null) {
				detailresultLine = Integer.valueOf((String) paramMap.get("detailSearch_resultLine"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (detailresultLine != 3 && (String) paramMap.get("detailSearch_resultLine") != null) {
				//20230207 이미지 형식을 1줄에 5개에서 3개씩 보여줌에 따라서, 이미지 매체의 [결과 더보기] 클릭 시 ,10개를 기본적으로 보여주던 <이미지> 형식을 9개를 기본으로 보여준다. (1줄에 3개씩 보여주기 때문에)
				if (detailresultLine != 10){
					detailresultLine = detailresultLine;
				} else{
					detailresultLine = 9;
				}
			} else {
				detailresultLine = 3;
			}
		}

		try {
			if (detail_visibleChk.equals("Y")) {
				searchOperation = (String) paramMap.get("detailSearch_radio");
			} else {
				searchOperation = "natural";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_radio") == null) {
				searchOperation = "natural";
			}
		}

		if (searchOperation.equals("allword")) {
			searchOperation = natural;
		} else if (searchOperation.equals("anyword")) {
			searchOperation = natural;
		} else if (searchOperation.equals("natural")) {
			searchOperation = natural;
		} else {
			searchOperation = natural;
		}
		
		if(searchStr.indexOf("-") != -1){
			searchOperation = allword;
		}

		// 신청회사 null 처리
		String detailAplc = "";
		try {
			if ((String) paramMap.get("detailSearch_aplc") != null) {
				detailAplc = (String) paramMap.get("detailSearch_aplc");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_aplc") == null) {
				detailAplc = "";
			}
		}

		// 상세보기-일자 데이터 가져오기 및 null 처리 시작

		// 시작날짜
		String period_start = "";
		try {
			if ((String) paramMap.get("period_start") != null) {
				period_start = (String) paramMap.get("period_start");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("period_start") == null) {
				period_start = "";
			}
		}

		// 종료날짜
		String period_end = "";
		try {
			if ((String) paramMap.get("period_end") != null) {
				period_end = (String) paramMap.get("period_end");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("period_end") == null) {
				period_end = "";
			}
		}

		// 등급분류일자, 신청일자 , 접수일자 구분
		String detail_dateGubun = "";
		try {
			if ((String) paramMap.get("detailSearch_date") != null) {
				detail_dateGubun = (String) paramMap.get("detailSearch_date");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_date") == null) {
				detail_dateGubun = "gradedate";
			}
		}

		if (!period_start.equals("")) {
			period_start = period_start.substring(0, 4) + period_start.substring(5, 7) + period_start.substring(8, 10)
					+ "000000";
		}
		if (!period_end.equals("")) {
			period_end = period_end.substring(0, 4) + period_end.substring(5, 7) + period_end.substring(8, 10)
					+ "000000";
		}

		// 듈 중에 하나를 선택안하고 상세검색을 하면 --> 전체날짜로 인식하여 데이터검색하기
		if ((period_start.equals("") || period_end.equals("")) && !(period_start.equals("") && period_end.equals(""))) {
			period_start = "";
			period_end = "";

		}
		// 상세보기-일자 데이터 가져오기 및 null 처리 끝

		
		//따옴표 기능 추가 시작

		//따옴표기능 변수1 : 반드시 포함해야 하는 따옴표 안의 단어.
		String quote_searchStr_origin = "";
		//따옴표기능 변수2 : 반드시 포함해야 하는 따옴표 안의 단어 (인코딩 받아줄 변수).
		String quote_searchStr = ""; 
		
		

		//따옴표기능 로직1 : 따옴표의 인덱스 위치를 추출한다.
		String word = "\""; 
		int searchStrIndex = searchStr.indexOf(word);
		List<Integer> searchStrIndexList = new ArrayList<Integer>();
		 while(searchStrIndex != -1) {
			 searchStrIndexList.add(searchStrIndex);
			 searchStrIndex = searchStr.indexOf(word, searchStrIndex+word.length()); 
		 }
		
		 
		 
		//따옴표기능 로직2 : 따옴표의 인덱스가 2개이상 나왔다면, 따옴표가 4개있든 7개 있든, 제일 첫번째로 나오는 따옴표 2개의 쌍을 구하고, 그 따옴표로 깜사져 있는 단어를 추출한다.
		if(searchStrIndexList.size() >= 2 ) {
			 int firstIndex = searchStrIndexList.get(0);
			 int lastIndex = searchStrIndexList.get(1);
			 quote_searchStr_origin = searchStr.substring(firstIndex + 1, lastIndex);  
			 quote_searchStr = URLEncoder.encode(quote_searchStr_origin, encode);
		 }
		
		 
		 
		 
		 //따옴표기능 로직3 : 따옴표의 인덱스가 일단 하나 이상 등장한다면, 한개 이상일 경우는 따옴표를 제거해주고, 따옴표가 2개이상인 경우에는 위에서 얻어온 '따옴표 안'의 단어를 따로 추출하여 인코딩한다.
		             // --> 차후에 따옴표 검색이 이뤄질때, 헤어질 결심 "수사관" 검색 시, --> (헤어질 결심 ) AND ("수사관") 이런식으로 검색 하게끔 해줌.
		 if(searchStrIndexList.size() >= 1 ){
			 
			String searchStr2 = searchStr.replaceAll("\"", "");
			String searchStr3 = searchStr2.replaceAll( quote_searchStr_origin , "").trim(); 
			if(!searchStr3.equals("") && searchStr3 != null){
				encodeSearchStr = URLEncoder.encode(searchStr3, encode); // 검색어
			}
		 }
		 
		 
		 
		 // 따옴표에 들어간 단어에 맞춰서 조건을 추가해주기 위해서 셋팅하는 작업 
		 String quote_searchCol = "";
		 if(!quote_searchStr.equals("")) {
    /***********************************************img01() quote_searchStr start*************************************************************************/	 
		 quote_searchCol += encodeAnd + "(" + "use_title" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + "synonym('d0')" + 
					encodeOr + "ori_title" + encodeAns + strMark + quote_searchStr + strMark +
					encodeOr + "rcv_no_view" + encodeAns + strMark + quote_searchStr + strMark + // no2 접수번호	// rcv_no_view // |string like X
					encodeOr + "medi_name" + encodeAns + strMark + quote_searchStr + strMark + // no4
					encodeOr + "prodc_name" + encodeAns + strMark + quote_searchStr + strMark + // no7
					encodeOr + "prodc_natnl_name" + encodeAns + strMark + quote_searchStr + strMark +
					encodeOr + "dire_name" + encodeAns + strMark + quote_searchStr + strMark +
					encodeOr + "mv_asso_name" + encodeAns + strMark + quote_searchStr + strMark +
					encodeOr + "leada_name" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + "synonym('d0')" +
					encodeOr + "kind_name" + encodeAns + strMark + quote_searchStr + strMark +
					encodeOr + "hope_grade_name" + encodeAns + strMark + quote_searchStr + strMark +
					encodeOr + "prod_year" + encodeAns + strMark + quote_searchStr + strMark +
					encodeOr + "aplc_name" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + "synonym('d0')" +
					encodeOr + "rt_no" + encodeAns + strMark + quote_searchStr + strMark +
					encodeOr + "grade_name" + encodeAns + strMark + quote_searchStr + strMark +
					// 기본매체에 존재한 텍스트타입
					encodeOr + "file_ext" + encodeAns + strMark + quote_searchStr + strMark +

					// 텍스트타입
					encodeOr + "deter_rsn" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + "synonym('d0')" +
					encodeOr + "file_name" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + "synonym('d0')";

			// int는 encode를 따로 안해줘도 무방함.
			if (searchStrInt != 0) {
				quote_searchCol += encodeOr + "rcv_date" + encodeAns + searchStrInt + "000000" + // no3
																							// 접수일자
																							// |
																							// int(date)
						encodeOr + "rt_date" + encodeAns + searchStrInt + "000000"; // no17
																					// 등급분류일
																					// |
																					// int(date)
			}

			// 통합검색의 마무리 괄호
			quote_searchCol += ")";
				
						//quote_searchCol만 셋팅 하고, 왜 다른 사이드는 셋팅 하지 않는지?
						//위에 보다시피 quote_searchCol 이 모든 컬럼에 "따옴표에 들어간 단어"를 매칭시켜서 가져오고있다 
						// 나중에 제일 밑에 detail_visibleChk를 쓰는 로직 밑에 보면, 통합검색이든 사이드메뉴든 뒤에 quote_searchCol를 붙여주는 작업을 하고 있다.
				 }
				 /***********************************************img01() quote_searchStr end*************************************************************************/
				 // 따옴표 기능 추가 끝
			
		
		
		/////////////////////////////////////////// 이미지 시작
		/////////////////////////////////////////// ()///////////////////////////////////////////////////////////////////////////////////////////////////
		if (sectionGubunList.contains("img01") || sectionGubunList.contains("Total")) {

			Map<String, Object> img01 = new HashMap<String, Object>();

			if (searchStr != null) {
				/*************************************************************
				 * 검색어 처리 시작
				 *************************************************************************************************/

				// 검색구분은 if절로서 쓰이고, 검색어는 where절 안에 쓰임으로써, 직접적인 쿼리에 영향을 줌.
				String searchCol = ""; // where절을 담을 변수
				String searchCol_ori_title = "";
				String searchCol_use_title = "";
				String searchCol_rcv_no_view = "";
				String searchCol_medi_name = "";
				String searchCol_prodc_name = "";
				String searchCol_prodc_natnl_name = "";
				String searchCol_dire_name = "";
				String searchCol_mv_asso_name = "";
				String searchCol_leada_name = "";
				String searchCol_kind_name = "";
				String searchCol_hope_grade_name = "";
				String searchCol_prod_year = "";
				String searchCol_aplc_name = "";
				String searchCol_rt_no = "";
				String searchCol_grade_name = "";
				String searchCol_file_ext = "";

				String searchCol_deter_rsn = "";
				String searchCol_file_name = "";

				String searchCol_rcv_date = "";
				String searchCol_rt_date = "";

				// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
				if (searchGubun.equals("All")) {
					searchCol += "(" + "use_title" + encodeAns + strMark + encodeSearchStr + strMark + "+allword+" + "synonym('d0')" + 
				            encodeOr + "use_title" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + "synonym('d0')" + 
							encodeOr + "ori_title" + encodeLike + strMark + "*"	+ encodeSearchStr + "*" + strMark +
							encodeOr + "rcv_no_view" + encodeAns + strMark + encodeSearchStr + strMark + // no2 접수번호	// rcv_no_view // |string like X
							encodeOr + "medi_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no4
																													// 매체명
																													// |string
							encodeOr + "prodc_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no7
																														// 제작사명
																														// |string
							encodeOr + "prodc_natnl_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no8 제작사 국적 |string
							encodeOr + "dire_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no9
																													// 감독명
																													// |string
							encodeOr + "mv_asso_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no10
																														// 영화
																														// 종류
																														// |string
							encodeOr + "leada_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + // no11
																														// 주연명
																														// |string
							encodeOr + "kind_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no12
																													// 종류
																													// |string
							encodeOr + "hope_grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no13 희망등급 |string
							encodeOr + "prod_year" + encodeAns + strMark + encodeSearchStr + strMark + // no14
																										// 제작년도
																										// |string
																										// like
																										// X
							encodeOr + "aplc_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation +  // no15
																													// 신청회사
																													// |string
							encodeOr + "rt_no" + encodeAns + strMark + encodeSearchStr + strMark + // no16
																									// 등급분류번호
																									// |string
																									// like
																									// X
							encodeOr + "grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no18
																														// 결정등급
																														// |string

							// 기본매체에 존재한 텍스트타입
							encodeOr + "file_ext" + encodeAns + strMark + encodeSearchStr + strMark + // no19
																										// 파일
																										// 확장자명
																										// |string
																										// like
																										// X

							// 텍스트타입
							encodeOr + "deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark + "+someword+" + // no19
																															// 결정사유
																															// |text
							encodeOr + "file_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation; // no25
																														// 서비스
																														// 경로
																														// |text

					// int는 encode를 따로 안해줘도 무방함.
					if (searchStrInt != 0) {
						searchCol += encodeOr + "rcv_date" + encodeAns + searchStrInt + "000000" + // no3
																									// 접수일자
																									// |
																									// int(date)
								encodeOr + "rt_date" + encodeAns + searchStrInt + "000000"; // no17
																							// 등급분류일
																							// |
																							// int(date)
					}

					// 통합검색의 마무리 괄호
					searchCol += ")";

					// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
					// 1. 원제명Url (ori_title이 string타입이다)
					searchCol_ori_title = "(" + "ori_title" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";

					// 2. 사용제명Url (텍스트지만, 중요도에 따라 맨위에 위치)
					searchCol_use_title = "(" + "use_title" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + "synonym('d0')" + ")";

					// 3. 접수번호Url
					searchCol_rcv_no_view = "(" + "rcv_no_view" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 4. 매체명Url
					searchCol_medi_name = "(" + "medi_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 5. 제작사 국적Url
					searchCol_prodc_natnl_name = "(" + "prodc_natnl_name" + encodeAns + strMark + encodeSearchStr
							+ strMark + ")";
					// 6. 제작사명Url
					searchCol_prodc_name = "(" + "prodc_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";

					// 7. 감독명Url
					searchCol_dire_name = "(" + "dire_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 10. 영화 종류
					searchCol_mv_asso_name = "(" + "mv_asso_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 11. 주연명
					searchCol_leada_name = "(" + "leada_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + ")";
					// 12. 종류
					searchCol_kind_name = "(" + "kind_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 13. 희망등급
					searchCol_hope_grade_name = "(" + "hope_grade_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";

					// 14. 제작년도
					searchCol_prod_year = "(" + "prod_year" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 15. 신청회사
					searchCol_aplc_name = "(" + "aplc_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + ")";
					// 16. 등급분류번호
					searchCol_rt_no = "(" + "rt_no" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 17. 결정등급
					searchCol_grade_name = "(" + "grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";

					// 18 파일 확장자명
					searchCol_file_ext = "(" + "file_ext" + encodeAns + strMark + encodeSearchStr + strMark + ")";

					/******* 텍스트 *********/
					// 19. 결정사유
					searchCol_deter_rsn = "(" + "deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+" + ")";
					// 20. 파일 명
					searchCol_file_name = "(" + "file_name" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + ")";

					/*** 검색어가 숫자인 경우 추가되는 항목!! ***/
					if (searchStrInt != 0) {

						// 21. 접수일자
						searchCol_rcv_date = "(" + "rcv_date" + encodeAns + searchStrInt + "000000" + ")";
						// 22. 등급분류일
						searchCol_rt_date = "(" + "rt_date" + encodeAns + searchStrInt + "000000" + ")";

					}

				}
				if (searchGubun.equals("title")) {
					searchCol += "(use_title" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ "synonym('d0')" + encodeOr + "ori_title" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
				}
				if (searchGubun.equals("decision")) {
					searchCol += "(deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ "synonym('d0')" + ")";
				}

				// 검색어가 빈값일떄 (이미지매체)
				if (searchStr.equals("NO_VALUE")) {
					searchCol = "ori_title+not+in%7B%7D+";
					searchCol_ori_title = "ori_title+not+in%7B%7D+";
					searchCol_rcv_no_view = "ori_title+not+in%7B%7D+";
					searchCol_medi_name = "ori_title+not+in%7B%7D+";
					searchCol_prodc_name = "ori_title+not+in%7B%7D+";
					searchCol_prodc_natnl_name = "ori_title+not+in%7B%7D+";
					;
					searchCol_dire_name = "ori_title+not+in%7B%7D+";
					searchCol_mv_asso_name = "ori_title+not+in%7B%7D+";
					searchCol_leada_name = "ori_title+not+in%7B%7D+";
					searchCol_kind_name = "ori_title+not+in%7B%7D+";
					searchCol_hope_grade_name = "ori_title+not+in%7B%7D+";
					searchCol_prod_year = "ori_title+not+in%7B%7D+";
					searchCol_aplc_name = "ori_title+not+in%7B%7D+";
					searchCol_rt_no = "ori_title+not+in%7B%7D+";
					searchCol_grade_name = "ori_title+not+in%7B%7D+";
					searchCol_file_ext = "ori_title+not+in%7B%7D+";

					// 텍스트타입
					searchCol_use_title = "ori_title+not+in%7B%7D+";
					searchCol_deter_rsn = "ori_title+not+in%7B%7D+";
					searchCol_file_name = "ori_title+not+in%7B%7D+";

					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_rcv_date = "ori_title+not+in%7B%7D+";
						searchCol_rt_date = "ori_title+not+in%7B%7D+";
					}
				}

				// 상세검색 일때만 뒤에 이걸 붙여주자!
				/**************** 일자 통합작업중 ***********************/
				if (detail_visibleChk.equals("Y")) {
					
					
			///////////////////////////////AND/OR 연산조건 관련 변수 초기화  시작///////////////////////////////////////////////////////////		
					//1. 상세검색 조건내에 선택한  연산자에 따라서 and를 할지 or을 할지 정하는 로직. 
					// encodeTotalChk : 상세조건 내 AND/OR 연산자 무엇을 선택했는지에 대한 값을 지니고 있는 변수 (JSP에서 넘어오는 데이터)
					String encodeTotalChk = (String) paramMap.get("detailSearch_radio");
					// encodeTotal : 상세조건 내 AND/OR 연산자 선택에 따라 And나 Or 값을 지니고 있는 변수
					String encodeTotal = ""; 
					
					if(encodeTotalChk != null){
						if(encodeTotalChk.equals("allword")){
							encodeTotal = encodeAnd;
						} else if(encodeTotalChk.equals("anyword")){
							encodeTotal = encodeOr;
						} 
					} else {
						encodeTotal = encodeAnd;
					}
					
					//2. 상세검색 조건문에 들어오면 AND연산자 해주고,  괄호도  열어주기. (상세검색 조건문 끝날떄쯤 괄호 닫아줄 것)
					// 상세검색 시 이 구문이 기본적으로 실행되며, 구조는 "(기존검색) AND (" 인데, // 여기서 "AND (" 부분을 셋팅해주는 구문이다. 
					searchCol += encodeAnd + encodeLGual;
					searchCol_ori_title += encodeAnd + encodeLGual;
					searchCol_rcv_no_view += encodeAnd + encodeLGual;
					searchCol_medi_name += encodeAnd + encodeLGual;
					searchCol_prodc_name += encodeAnd + encodeLGual;
					searchCol_prodc_natnl_name += encodeAnd + encodeLGual;
					searchCol_dire_name += encodeAnd + encodeLGual;
					searchCol_mv_asso_name += encodeAnd + encodeLGual;
					searchCol_leada_name += encodeAnd + encodeLGual;
					searchCol_kind_name += encodeAnd + encodeLGual;
					searchCol_hope_grade_name += encodeAnd + encodeLGual;
					searchCol_prod_year += encodeAnd + encodeLGual;
					searchCol_aplc_name += encodeAnd + encodeLGual;
					searchCol_rt_no += encodeAnd + encodeLGual;
					searchCol_grade_name += encodeAnd + encodeLGual;
					searchCol_file_ext += encodeAnd + encodeLGual;

					// 텍스트타입
					searchCol_use_title += encodeAnd + encodeLGual;
					searchCol_deter_rsn += encodeAnd + encodeLGual;
					searchCol_file_name += encodeAnd + encodeLGual;

					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_rcv_date += encodeAnd + encodeLGual;
						searchCol_rt_date += encodeAnd + encodeLGual;
					}
					
					//첫번째 조건인지 아닌지 확인하는 변수
					int firstConditionChk = 0;
			///////////////////////////////AND/OR 연산조건 관련 변수 초기화  끝///////////////////////////////////////////////////////////			
					
					
					// img01
					// 일자보다 신청회사 먼저 붙여준다.
					
					if (!(detailAplc.equals("") || detailAplc == null)) {
						detailAplc = URLEncoder.encode(detailAplc, "UTF-8");
						// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
						
						// 첫번쨰 조건 체크 ------------- 시작 
						String chkResult = "";
						if(firstConditionChk == 0){
							firstConditionChk++;
						} else {
							chkResult = encodeTotal;
						}
						// 첫번쨰 조건 체크 ------------- 끝
						
						
						searchCol += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_ori_title += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_rcv_no_view += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_medi_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_prodc_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_prodc_natnl_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_dire_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_mv_asso_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_leada_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_kind_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_hope_grade_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_prod_year += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_aplc_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_rt_no += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_grade_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_file_ext += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;

						// 텍스트타입
						searchCol_use_title += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_deter_rsn += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_file_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_rcv_date += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
							searchCol_rt_date += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						}

					}

					String detail_date = "";
					if (!period_start.equals("") && !period_end.equals("")) {
						
						
						
						// 첫번쨰 조건 체크 ------------- 시작 
						String chkResult = "";
						if(firstConditionChk == 0){
							firstConditionChk++;
						} else {
							chkResult = encodeTotal;
						}
						// 첫번쨰 조건 체크 ------------- 끝
						
						
						if (detail_dateGubun.equals("regdate")) {
							searchCol += chkResult + encodeLGual + "sub_rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 신청일자
							detail_date = chkResult + encodeLGual + "sub_rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;
						} else if (detail_dateGubun.equals("jubsudate")) {
							searchCol += chkResult + encodeLGual + "rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 접수일자
							detail_date = chkResult + encodeLGual + "rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;
						} else {
							searchCol += chkResult + encodeLGual + "rt_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 등급분류일자
							detail_date = chkResult + encodeLGual + "rt_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;
						}

						// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
						searchCol_ori_title += detail_date;
						searchCol_dire_name += detail_date;
						searchCol_rcv_no_view += detail_date;
						searchCol_medi_name += detail_date;
						searchCol_prodc_name += detail_date;
						searchCol_prodc_natnl_name += detail_date;
						searchCol_mv_asso_name += detail_date;
						searchCol_leada_name += detail_date;
						searchCol_kind_name += detail_date;
						searchCol_hope_grade_name += detail_date;
						searchCol_prod_year += detail_date;
						searchCol_aplc_name += detail_date;
						searchCol_rt_no += detail_date;
						searchCol_grade_name += detail_date;
						searchCol_file_ext += detail_date;

						// 텍스트타입
						searchCol_use_title += detail_date;
						searchCol_deter_rsn += detail_date;
						searchCol_file_name += detail_date;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_rcv_date += detail_date;
							searchCol_rt_date += detail_date;
						}
					} else {
						//20230221 추가
						// 상세검색내 날짜조건이 "전체"라면 null이기 때문에 이 구문이 실행된다. 날짜가 전체 or 신청회사가 디즈니일때, 신청회사가 디즈니인것만 조건에 들어가는 오류가 발생함. 즉 날짜가 전체라는 조건이 빠져서 데이터가 디즈니인것만 나옴.
						
						// 첫번쨰 조건 체크 ------------- 시작 
						String chkResult = "";
						if(firstConditionChk == 0){
							firstConditionChk++;
						} else {
							chkResult = encodeTotal;
						}
						// 첫번쨰 조건 체크 ------------- 끝
						
						searchCol += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_ori_title += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_rcv_no_view += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_medi_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_prodc_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_prodc_natnl_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_dire_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_mv_asso_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_leada_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_kind_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_hope_grade_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_prod_year += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_aplc_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_rt_no += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_grade_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_file_ext += chkResult + "ori_title+not+in%7B%7D+";

						// 텍스트타입
						searchCol_use_title += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_deter_rsn += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_file_name += chkResult + "ori_title+not+in%7B%7D+";

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_rcv_date += chkResult + "ori_title+not+in%7B%7D+";
							searchCol_rt_date += chkResult + "ori_title+not+in%7B%7D+";
						}
					}

				
					
					//만약에 상세조건에 아무것도 입력하지 않는다면, 더미데이터룰 넣어준다 --> 데이터가 없으면 오류가 나기 떄문이다.
					if(searchCol.substring(searchCol.length()-1).equals("(")){
						searchCol += "ori_title+not+in%7B%7D+";
						searchCol_ori_title += "ori_title+not+in%7B%7D+";
						searchCol_rcv_no_view += "ori_title+not+in%7B%7D+";
						searchCol_medi_name += "ori_title+not+in%7B%7D+";
						searchCol_prodc_name += "ori_title+not+in%7B%7D+";
						searchCol_prodc_natnl_name += "ori_title+not+in%7B%7D+";
						searchCol_dire_name += "ori_title+not+in%7B%7D+";
						searchCol_mv_asso_name += "ori_title+not+in%7B%7D+";
						searchCol_leada_name += "ori_title+not+in%7B%7D+";
						searchCol_kind_name += "ori_title+not+in%7B%7D+";
						searchCol_hope_grade_name += "ori_title+not+in%7B%7D+";
						searchCol_prod_year += "ori_title+not+in%7B%7D+";
						searchCol_aplc_name += "ori_title+not+in%7B%7D+";
						searchCol_rt_no += "ori_title+not+in%7B%7D+";
						searchCol_grade_name += "ori_title+not+in%7B%7D+";
						searchCol_file_ext += "ori_title+not+in%7B%7D+";

						// 텍스트타입
						searchCol_use_title += "ori_title+not+in%7B%7D+";
						searchCol_deter_rsn += "ori_title+not+in%7B%7D+";
						searchCol_file_name += "ori_title+not+in%7B%7D+";

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_rcv_date += "ori_title+not+in%7B%7D+";
							searchCol_rt_date += "ori_title+not+in%7B%7D+";
						}
					}
					
					searchCol += encodeRGual;
					searchCol_ori_title += encodeRGual;
					searchCol_rcv_no_view += encodeRGual;
					searchCol_medi_name += encodeRGual;
					searchCol_prodc_name += encodeRGual;
					searchCol_prodc_natnl_name += encodeRGual;
					searchCol_dire_name += encodeRGual;
					searchCol_mv_asso_name += encodeRGual;
					searchCol_leada_name += encodeRGual;
					searchCol_kind_name += encodeRGual;
					searchCol_hope_grade_name += encodeRGual;
					searchCol_prod_year += encodeRGual;
					searchCol_aplc_name += encodeRGual;
					searchCol_rt_no += encodeRGual;
					searchCol_grade_name += encodeRGual;
					searchCol_file_ext += encodeRGual;

					// 텍스트타입
					searchCol_use_title += encodeRGual;
					searchCol_deter_rsn += encodeRGual;
					searchCol_file_name += encodeRGual;

					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_rcv_date += encodeRGual;
						searchCol_rt_date += encodeRGual;
					}
				}
				//detail_visibleChk 조건 끝
				//만약 따옴표 안에 단어가 인식이 된다면 이 로직 실행.
				if(!quote_searchStr.equals("")) {
					//통합검색이든, 메뉴명이든 뒤에 quote_searchCol 붙여주기 시작! 
					searchCol += quote_searchCol;
					searchCol_ori_title += quote_searchCol;
					searchCol_rcv_no_view += quote_searchCol;
					searchCol_medi_name += quote_searchCol;
					searchCol_prodc_name += quote_searchCol;
					searchCol_prodc_natnl_name += quote_searchCol;
					searchCol_dire_name += quote_searchCol;
					searchCol_mv_asso_name += quote_searchCol;
					searchCol_leada_name += quote_searchCol;
					searchCol_kind_name += quote_searchCol;
					searchCol_hope_grade_name += quote_searchCol;
					searchCol_prod_year += quote_searchCol;
					searchCol_aplc_name += quote_searchCol;
					searchCol_rt_no += quote_searchCol;
					searchCol_grade_name += quote_searchCol;
					searchCol_file_ext += quote_searchCol;

					// 텍스트타입
					searchCol_use_title += quote_searchCol;
					searchCol_deter_rsn += quote_searchCol;
					searchCol_file_name += quote_searchCol;

					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_rcv_date += quote_searchCol;
						searchCol_rt_date += quote_searchCol;
					}
				}
				//통합검색이든, 메뉴명이든 뒤에 quote_searchCol 붙여주기 끝!
				/**************** 상세검색의 일자, 신청회사 통합작업중 ***********************/

				/**************
				 * ■■■■■■■■■■■■■*************************************우측사이드에 관한
				 * information 시작
				 * *************************************************■■■■■■■■■
				 **************************************************/
				List<Map<String, Object>> img01_sideInformation = new ArrayList<Map<String, Object>>();
				if (searchGubun.equals("All")) {
					// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
					// 1. (조건에 맞는)원제명에 대한 건수 및 데이터 조회
					img01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_imageUrl,
							orderValue, searchCol_ori_title, "원제명"));

					// 2. (조건에 맞는)사용제명에 대한 건수 및 데이터 조회
					img01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_imageUrl,
							orderValue, searchCol_use_title, "사용제명"));

					// 3. (조건에 맞는)접수번호에 대한 건수 및 데이터 조회
					img01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_imageUrl,
							orderValue, searchCol_rcv_no_view, "접수번호"));

					// 4. (조건에 맞는)매체명에 대한 건수 및 데이터 조회
					img01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_imageUrl,
							orderValue, searchCol_medi_name, "매체명"));

					// 5. (조건에 맞는)제작사명에 대한 건수 및 데이터 조회
					img01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_imageUrl,
							orderValue, searchCol_prodc_name, "제작사명"));

					// 6. (조건에 맞는)제작사 국적에 대한 건수 및 데이터 조회
					img01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_imageUrl,
							orderValue, searchCol_prodc_natnl_name, "제작사 국적"));

					// 7. (조건에 맞는)감독명에 대한 건수 및 데이터 조회
					img01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_imageUrl,
							orderValue, searchCol_dire_name, "감독명"));

					// 8. (조건에 맞는)영화 종류에 대한 건수 및 데이터 조회
					img01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_imageUrl,
							orderValue, searchCol_mv_asso_name, "영화 종류"));

					// 9. (조건에 맞는)주연명에 대한 건수 및 데이터 조회
					img01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_imageUrl,
							orderValue, searchCol_leada_name, "주연명"));

					// 10. (조건에 맞는)종류에 대한 건수 및 데이터 조회
					img01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_imageUrl,
							orderValue, searchCol_kind_name, "종류"));

					// 11. (조건에 맞는)희망등급에 대한 건수 및 데이터 조회
					img01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_imageUrl,
							orderValue, searchCol_hope_grade_name, "희망등급"));

					// 12. (조건에 맞는)제작년도에 대한 건수 및 데이터 조회
					img01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_imageUrl,
							orderValue, searchCol_prod_year, "제작년도"));

					// 13. (조건에 맞는)신청회사에 대한 건수 및 데이터 조회
					img01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_imageUrl,
							orderValue, searchCol_aplc_name, "신청회사"));

					// 14. (조건에 맞는)등급분류번호 에 대한 건수 및 데이터 조회
					img01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_imageUrl,
							orderValue, searchCol_rt_no, "등급분류번호"));

					// 15. (조건에 맞는)결정등급에 대한 건수 및 데이터 조회
					img01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_imageUrl,
							orderValue, searchCol_grade_name, "결정등급"));

					// 16. (조건에 맞는)파일 확장자명에 대한 건수 및 데이터 조회
					img01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_imageUrl,
							orderValue, searchCol_file_ext, "파일 확장자명"));

					// 텍스트 타입 시작
					// 20. (조건에 맞는)결정사유에 대한 건수 및 데이터 조회
					img01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_imageUrl,
							orderValue, searchCol_deter_rsn, "결정사유"));

					// 27. (조건에 맞는)파일 명 에 대한 건수 및 데이터 조회
					img01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_imageUrl,
							orderValue, searchCol_file_name, "파일 명"));

					/**************** 텍스트 데이터 끝!!! ******************/

					/**************** 숫자 데이터 시작 (null처리)!!! ******************/

					if (searchStrInt != 0) {
						// 29 (조건에 맞는)접수일자에 대한 건수 및 데이터 조회
						img01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response,
								result_imageUrl, orderValue, searchCol_rcv_date, "접수일자"));
						// 30 (조건에 맞는)등급분류일에 대한 건수 및 데이터 조회
						img01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response,
								result_imageUrl, orderValue, searchCol_rt_date, "등급분류일"));
					}
					/**************** 숫자 데이터 끝 (null처리)!!! ******************/

					Collections.sort(img01_sideInformation, new sideMenuCntComparator());
				} // if(searchGubun.equals("All")) 끝
				if (searchGubun.equals("title")) {
					// (조건에 맞는)원제명 대한 건수 및 데이터 조회
					img01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_imageUrl,
							orderValue, searchCol, "원제명/사용제명"));
				}
				if (searchGubun.equals("decision")) {
					// (조건에 맞는)결정사유 대한 건수 및 데이터 조회
					img01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_imageUrl,
							orderValue, searchCol, "결정사유"));
				}
				// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
				walker.setValue("img01_sideInformation", img01_sideInformation);

				/**************
				 * ■■■■■■■■■■■■■***************************************우측사이드에 관한
				 * information
				 * 끝***********************************************■■■■■■■■■
				 **************************************************/

				/*************************************************************
				 * 검색어 처리 끝
				 *************************************************************************************************/

				totalUrl = result_imageUrl  + searchCol + orderValue;
				// 결과내 재검색이라면 ? 시작
				if (((String) paramMap.get("result_re_search_chk")).equals("Y")
						&& (String) paramMap.get("img01_historySearchQuery") != null) {
					totalUrl = (String) paramMap.get("img01_historySearchQuery") + encodeAnd + "(" + searchCol + ")"
							+ orderValue;
				}
				// 결과내 재검색이라면 ? 끝

				// 이전쿼리 저장 시작
				String historySearchQuery = "";
				historySearchQuery = result_imageUrl  + "(" + searchCol + ")";
				// 만약 결과내재검색을 통한 검색이었다면, historySearchQuery(이전쿼리)는 , 이전쿼리 +
				// encodeAnd + 현재쿼리 일 것이다.
				if (((String) paramMap.get("result_re_search_chk")).equals("Y")
						&& (String) paramMap.get("img01_historySearchQuery") != null) {
					historySearchQuery = (String) paramMap.get("img01_historySearchQuery") + encodeAnd + "(" + searchCol
							+ ")";
				}
				paramMap.put("img01_historySearchQuery", historySearchQuery);
				// 이전쿼리 저장 끝
			} else {
				// 검색어가 공백이라면? // 통합검색이든, 제목이든 내용이든 // 영화매체에 관한 모든 데이터가 등장해줘야함
				totalUrl = result_imageUrl + orderValue;
			}

			/*************************************************************
			 * 얻어낸 Url 결과물로 추가적인 작업시작 / 페이징 처리 시작
			 *************************************************************************************************/
			String paging_totalUrl = totalUrl + "&pagelength=" + detailresultLine + "&offset=" + offset + "&limit="
					+ detailresultLine + "&hilite-fields="
					+ "%7B%22use_title%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C";
			List<Object> dataList = pagingURLEncodingFunc(paging_totalUrl);
			img01.put("total_count_page", (Integer) dataList.get(0));
			walker.setValue("img01_resultMapList", (List<Map<String, String>>) dataList.get(1));
			/*************************************************************
			 * 얻어낸 Url 결과물로 추가적인 작업시작 / 페이징 처리 시작
			 *************************************************************************************************/
			// totalURLCntFunc로 총합 갯수만 가져오고있다. -> 데이터가 필요하다면 해당 메소드를 안에
			// resultMap을 확인하면 됨.
			Integer total_count = totalURLCntFunc(totalUrl);
			img01.put("total_count", total_count);
			walker.setValue("img01", img01);
		}

		/////////////////////////////////////////// 이미지 끝
		/////////////////////////////////////////// ()///////////////////////////////////////////////////////////////////////////////////////////////////
	}

	/**
	 * 
	 * @param walker
	 * @param paramMap
	 * @param request
	 * @param response
	 * @throws Exception
	 *             등급분류의견서에 대한 데이터 추출하는 메소드
	 */
	public void opin01(Walker walker, Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// null처리는 필수!
		// 파라미터 목록 가져오기. * 검색어, 검색구분, 섹션구분
		String searchStr = (String) paramMap.get("searchStr");
		String searchGubun = (String) paramMap.get("searchGubun");
		String[] sectionGubun = null;
		if (request.getParameterValues("sectionGubun") == null) {
			sectionGubun = new String[] { "Total" };
		} else {
			sectionGubun = request.getParameterValues("sectionGubun");
		}
		List<String> sectionGubunList = new ArrayList<String>(Arrays.asList(sectionGubun));

		/*************** 검색어에 숫자가 들어올떄 *******************************/
		long searchStrInt = 0;
		if (paramMap.get("searchStr") != null && ((String) paramMap.get("searchStr")).matches("[-+]?\\d*\\.?\\d+")) {
			searchStrInt = Long.valueOf((String) paramMap.get("searchStr"));
		}
		/*************** 검색어에 숫자가 들어올떄 *******************************/
		// 최종 URL
		String totalUrl = "";
		// 공통 URL
		String result_noticeUrl = "http://192.168.11.242:7577/search5?select=*&from=result_notice.result_notice&where=";
		String result_opinUrl = "http://192.168.11.242:7577/search5?select=*&from=result_opin.result_opin&where=";
		String result_fileUrl = "http://192.168.11.242:7577/search5?select=*&from=result_file.result_file&where=";
		String result_imageUrl = "http://192.168.11.242:7577/search5?select=*&from=result_image.result_image&where=";
		String result_moniUrl = "http://192.168.11.242:7577/search5?select=*&from=result_moni.result_moni&where=";
		String result_surveyUrl = "http://192.168.11.242:7577/search5?select=*&from=result_survey.result_survey&where=";
		// 공통 인코딩 목록
		String encode = "UTF-8";
		String encodeSearchStr = URLEncoder.encode(searchStr, encode); // 검색어
		String encodeSearchGubun = URLEncoder.encode(searchGubun, encode); // 검색구분(통합검색,
																			// 제목,
																			// 내용,
																			// 결정사유
																			// 등등)
		String encodeAnd = URLEncoder.encode(" and ", encode);
		String encodeOr = URLEncoder.encode(" or ", encode);
		String encodeAns = URLEncoder.encode("=", encode);
		String encodeInOpen = URLEncoder.encode(" in{", encode);
		String encodeInClose = URLEncoder.encode("}", encode);
		String encodeLGual = "(";
		String encodeRGual = ")";
		String encodeLike = URLEncoder.encode(" like ", encode);
		String strMark = URLEncoder.encode("'", encode);
		String between = URLEncoder.encode(" between ", encode);

		// where절의 text타입에만 사용가능.
		String allword = URLEncoder.encode(" allword ", encode);
		String anyword = URLEncoder.encode(" anyword ", encode);
		String natural = URLEncoder.encode(" natural ", encode);

		// 동의어 검색
		String synonym = URLEncoder.encode(" synonym ", encode);

		// 카테고리 정렬 ex) ORDER BY $CATEGORYFIELD(field_name(domain), keyword)
		String categoryOrder = URLEncoder.encode(" $categoryfield(use_title(), 검색어넣기 ", encode);

		/**
		 * 17501 : 국내영화 17502 : 국외영화 17505 : 광고영화 17511 : 국내비디오 17512 : 국외비디오
		 */

		// medi_code 인코딩 목록
		String medi_mv = URLEncoder.encode("'17501','17502'", encode);
		String medi_ad = URLEncoder.encode("'17503','17504','17505','17506','17514'", encode);
		String medi_video = URLEncoder.encode("'17511','17522'", encode);
		String medi_perform = URLEncoder.encode("'17521','17522','17523','17524'", encode);

		/*
		 * //첫 로딩 시 데이터를 표시해주는 역할을 함. if(searchGubun == null ){ searchGubun =
		 * "All"; } else if(searchGubun.trim().isEmpty()){ searchGubun = "All";
		 * }
		 */

		// 부가설명 : 처음 orderValue로 JSP에서 넘어온 정렬값을 받아서, 넘어온 정렬값을 기준으로 url인코딩을 실시한다.
		String orderValue = (String) request.getParameter("orderValue");
		if (orderValue == null || orderValue.equals("")) {
			orderValue = URLEncoder.encode(" order by $match(rt_no) desc, $relevance desc, $rowid desc ", encode);
		}
		if (orderValue.equals("orderRelevance")) {
			orderValue = URLEncoder.encode(" order by $match(rt_no) desc, $relevance desc, $rowid desc ", encode);
		}
		if (orderValue.equals("orderGa")) {
			orderValue = "%20order%20by%20use_title_sort%20asc%20%2c%20ori_title_sort%20asc%20";
		}
		if (orderValue.equals("orderDate")) {
			orderValue = "%20order%20by%20rcv_date%20desc%20";
		}

		//
		String groupByTest = URLEncoder.encode(" order by $relevance desc ", encode);

		// 2022 12 15 페이징 처리 시작

		// 1. sectionGubun을 하나만 선택하거나, -더보기 클릭 시, sectionGubun에는 딱 하나의 데이터가
		// 들어있고, 페이지는 1페이지로 이동한다. -->sectionGubun이 딱 하나라면 (Total)이 아닐떄 , 페이징목록이
		// 보이게끔 유도하기.
		// 페이지는 페이지 목록 버튼 시 마다 2페이지 3페이지 이동한다... 그러다가 다시 검색버튼 같은것을 누르면 1페이지로
		// 초기화!

		// 기존 display가 limit로 대체

		int cPage = 0;
		int offset = 0;
		int pagelength = 0;

		/**************************
		 * 20221223 페이징처리를 위한 새로운 로직 (영화, 비디오 반영완료)
		 *********************************************/
		if (((String) paramMap.get("isPaging")).equals("Y")) {
			cPage = (Integer) paramMap.get("cPage");
			offset = (Integer) paramMap.get("offset");
			pagelength = (Integer) paramMap.get("pagelength");
			offset = (cPage - 1) * pagelength;
		} else {
			cPage = 1;
			offset = 0;
			pagelength = 10;
			offset = (cPage - 1) * pagelength;
		}

		/*******************************************************************/

		/**************** 상세보기 시작 ********************************/
		// 20221216 상세보기에 관련된 것들 맨 위로 빼기 (기본값이 존재함)
		// 하지만 상세보기를 하지 않는다면 이 값이 null이라서 오류가뜸 ~~~~~~~~~~~~~~~~~~~~~~~~~~~

		// 상세보기를 클릭했는지 여부
		String detail_visibleChk = (String) paramMap.get("detail_visibleChk");

		// 연산자 가져오기
		String searchOperation = "";

		// 결과 수
		int detailresultLine = 3;
		try {
			if ((String) paramMap.get("detailSearch_resultLine") != null) {
				detailresultLine = Integer.valueOf((String) paramMap.get("detailSearch_resultLine"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (detailresultLine != 3 && (String) paramMap.get("detailSearch_resultLine") != null) {
				detailresultLine = detailresultLine;
			} else {
				detailresultLine = 3;
			}
		}

		try {
			if (detail_visibleChk.equals("Y")) {
				searchOperation = (String) paramMap.get("detailSearch_radio");
			} else {
				searchOperation = "natural";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_radio") == null) {
				searchOperation = "natural";
			}
		}

		if (searchOperation.equals("allword")) {
			searchOperation = natural;
		} else if (searchOperation.equals("anyword")) {
			searchOperation = natural;
		} else if (searchOperation.equals("natural")) {
			searchOperation = natural;
		} else {
			searchOperation = natural;
		}
		
		if(searchStr.indexOf("-") != -1){
			searchOperation = allword;
		}

		// 신청회사 null 처리
		String detailAplc = "";
		try {
			if ((String) paramMap.get("detailSearch_aplc") != null) {
				detailAplc = (String) paramMap.get("detailSearch_aplc");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_aplc") == null) {
				detailAplc = "";
			}
		}

		// 상세보기-일자 데이터 가져오기 및 null 처리 시작

		// 시작날짜
		String period_start = "";
		try {
			if ((String) paramMap.get("period_start") != null) {
				period_start = (String) paramMap.get("period_start");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("period_start") == null) {
				period_start = "";
			}
		}

		// 종료날짜
		String period_end = "";
		try {
			if ((String) paramMap.get("period_end") != null) {
				period_end = (String) paramMap.get("period_end");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("period_end") == null) {
				period_end = "";
			}
		}

		// 등급분류일자, 신청일자 , 접수일자 구분
		String detail_dateGubun = "";
		try {
			if ((String) paramMap.get("detailSearch_date") != null) {
				detail_dateGubun = (String) paramMap.get("detailSearch_date");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_date") == null) {
				detail_dateGubun = "gradedate";
			}
		}

		if (!period_start.equals("")) {
			period_start = period_start.substring(0, 4) + period_start.substring(5, 7) + period_start.substring(8, 10)
					+ "000000";
		}
		if (!period_end.equals("")) {
			period_end = period_end.substring(0, 4) + period_end.substring(5, 7) + period_end.substring(8, 10)
					+ "000000";
		}

		// 듈 중에 하나를 선택안하고 상세검색을 하면 --> 전체날짜로 인식하여 데이터검색하기
		if ((period_start.equals("") || period_end.equals("")) && !(period_start.equals("") && period_end.equals(""))) {
			period_start = "";
			period_end = "";

		}
		// 상세보기-일자 데이터 가져오기 및 null 처리 끝
		
		
		//따옴표 기능 추가 시작

		//따옴표기능 변수1 : 반드시 포함해야 하는 따옴표 안의 단어.
		String quote_searchStr_origin = "";
		//따옴표기능 변수2 : 반드시 포함해야 하는 따옴표 안의 단어 (인코딩 받아줄 변수).
		String quote_searchStr = ""; 
		
		

		//따옴표기능 로직1 : 따옴표의 인덱스 위치를 추출한다.
		String word = "\""; 
		int searchStrIndex = searchStr.indexOf(word);
		List<Integer> searchStrIndexList = new ArrayList<Integer>();
		 while(searchStrIndex != -1) {
			 searchStrIndexList.add(searchStrIndex);
			 searchStrIndex = searchStr.indexOf(word, searchStrIndex+word.length()); 
		 }
		
		 
		 
		//따옴표기능 로직2 : 따옴표의 인덱스가 2개이상 나왔다면, 따옴표가 4개있든 7개 있든, 제일 첫번째로 나오는 따옴표 2개의 쌍을 구하고, 그 따옴표로 깜사져 있는 단어를 추출한다.
		if(searchStrIndexList.size() >= 2 ) {
			 int firstIndex = searchStrIndexList.get(0);
			 int lastIndex = searchStrIndexList.get(1);
			 quote_searchStr_origin = searchStr.substring(firstIndex + 1, lastIndex);  
			 quote_searchStr = URLEncoder.encode(quote_searchStr_origin, encode);
		 }
		
		 
		 
		 
		 //따옴표기능 로직3 : 따옴표의 인덱스가 일단 하나 이상 등장한다면, 한개 이상일 경우는 따옴표를 제거해주고, 따옴표가 2개이상인 경우에는 위에서 얻어온 '따옴표 안'의 단어를 따로 추출하여 인코딩한다.
		             // --> 차후에 따옴표 검색이 이뤄질때, 헤어질 결심 "수사관" 검색 시, --> (헤어질 결심 ) AND ("수사관") 이런식으로 검색 하게끔 해줌.
		 if(searchStrIndexList.size() >= 1 ){
			 
			String searchStr2 = searchStr.replaceAll("\"", "");
			String searchStr3 = searchStr2.replaceAll( quote_searchStr_origin , "").trim(); 
			if(!searchStr3.equals("") && searchStr3 != null){
				encodeSearchStr = URLEncoder.encode(searchStr3, encode); // 검색어
			}
		 }
		 
		 
		 
		 // 따옴표에 들어간 단어에 맞춰서 조건을 추가해주기 위해서 셋팅하는 작업 
		 String quote_searchCol = "";
		 if(!quote_searchStr.equals("")) {
    /***********************************************opin01() quote_searchStr start*************************************************************************/	 
		 quote_searchCol += encodeAnd + "(" + "use_title" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + "synonym('d0')" 
					+ encodeOr + "ori_title" + encodeAns + strMark  + "%22" + quote_searchStr + "%22"  + strMark + "+allword+" + "synonym('d0')" 
					+ encodeOr + "rcv_no_view" + encodeAns + strMark + quote_searchStr + strMark + // no2
																		// 접수번호
																		// rcv_no_view
																		// |string
																		// like
																		// X
					encodeOr + "medi_name" + encodeAns + strMark + quote_searchStr + strMark + // no4
																											// 매체명
																											// |string
					encodeOr + "prodc_name" + encodeAns + strMark + quote_searchStr + strMark + // no7
																												// 제작사명
																												// |string
					encodeOr + "prodc_natnl_name" + encodeAns + strMark + quote_searchStr + strMark 
					+ // no8 제작사 국적 |string
					encodeOr + "dire_name" + encodeAns + strMark + quote_searchStr + strMark + // no9
																											// 감독명
																											// |string
					encodeOr + "mv_asso_name" +  encodeAns + strMark + quote_searchStr + strMark + // no10
																												// 영화
																												// 종류
																												// |string
					encodeOr + "leada_name" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + // no11
																												// 주연명
																												// |string
					encodeOr + "kind_name" + encodeAns + strMark + quote_searchStr + strMark + // no12
																											// 종류
																											// |string
					encodeOr + "hope_grade_name"  + encodeAns + strMark + quote_searchStr + strMark 
					+ // no13 희망등급 |string
					encodeOr + "prod_year" + encodeAns + strMark + quote_searchStr + strMark + // no14
																								// 제작년도
																								// |string
																								// like
																								// X
					encodeOr + "aplc_name" + encodeAns + strMark + quote_searchStr + strMark +  // no15
																											// 신청회사
																											// |string
					encodeOr + "rt_no" + encodeAns + strMark + quote_searchStr + strMark + // no69
																							// 등급분류번호
																							// |string
																							// like
																							// X
					encodeOr + "grade_name" + encodeAns + strMark + quote_searchStr + strMark + // no71
																												// 결정등급
																												// |string
					// 기본매체에 존재한 텍스트타입
					encodeOr + "major_opin_narrn_cont" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + // no44 주요 의견 서술 내용 |text
					encodeOr + "core_harm_rsn" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + // no46 핵심유해사유 |text
					encodeOr + "work_cont" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" + // no47
					encodeOr + "deter_rsn" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" +

					/************ 새로 만들어야하는 컬럼 **************/
					encodeOr + "comb_nm" + encodeAns + strMark + quote_searchStr + strMark + // no23
																											// 위원명
																											// |string

					encodeOr + "synt_indvdl_div" + encodeAns + strMark + quote_searchStr + strMark + // no25
																										// 종합
																										// 개별
																										// 구분
																										// |string
																										// like
																										// X

					encodeOr + "proc_state" + encodeAns + strMark + quote_searchStr + strMark + // no30
																								// 진행
																								// 상태
																								// |string
																								// like
																								// X

					encodeOr + "exprt_part_div" + encodeAns + strMark + quote_searchStr + strMark + // no32
																									// 전문위원
																									// 조
																									// 구분
																									// |string
																									// like
																									// X

					encodeOr + "opin_grade_nm1" + encodeAns + strMark + quote_searchStr + strMark + // no91
																													// 주제
																													// 등급
																													// |string
					encodeOr + "opin_grade_nm2" + encodeAns + strMark + quote_searchStr + strMark + // no94
																													// 선정성
																													// 등급
																													// |string
					encodeOr + "opin_grade_nm3" + encodeAns + strMark + quote_searchStr + strMark + // no97
																													// 폭력성
																													// 등급
																													// |string
					encodeOr + "opin_grade_nm4" + encodeAns + strMark + quote_searchStr + strMark + // no100
																													// 대사
																													// 등급
																													// |string
					encodeOr + "opin_grade_nm5" + encodeAns + strMark + quote_searchStr + strMark + // no103
																													// 공포
																													// 등급
																													// |string
					encodeOr + "opin_grade_nm6" + encodeAns + strMark + quote_searchStr + strMark + // no106
																													// 약물
																													// 등급
																													// |string
					encodeOr + "opin_grade_nm7" + encodeAns + strMark + quote_searchStr + strMark + // no109
																													// 모방위험
																													// 등급
																													// |string

					// 텍스트타입
					encodeOr + "exprt_synt_opin" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" +
					encodeOr + "deter_basis" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" +
					encodeOr + "deter_opin_nm" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+" +
					encodeOr + "reslt_noti_deter_rsn" + encodeAns + strMark + "%22" + quote_searchStr + "%22" + strMark + "+allword+"
					; // no58 결과 통보 결정 사유 |text
			/************ 새로 만들어야하는 컬럼 **************/

			// int는 encode를 따로 안해줘도 무방함.
			if (searchStrInt != 0) {
				quote_searchCol += encodeOr + "rcv_date" + encodeAns + searchStrInt + "000000" + // no6
																							// 접수일자
																							// |
																							// int(date)
						encodeOr + "rt_date" + encodeAns + searchStrInt + "000000" + // no70
																						// 등급분류일
																						// |
																						// int(date)
						encodeOr + "reg_dt" + encodeAns + searchStrInt + "000000"; // no87
																					// 등록
																					// 일시
																					// |
																					// int(date)
			}

			// 통합검색의 마무리 괄호
			quote_searchCol += ")";
				
		
				//quote_searchCol만 셋팅 하고, 왜 다른 사이드는 셋팅 하지 않는지?
				//위에 보다시피 quote_searchCol 이 모든 컬럼에 "따옴표에 들어간 단어"를 매칭시켜서 가져오고있다 
				// 나중에 제일 밑에 detail_visibleChk를 쓰는 로직 밑에 보면, 통합검색이든 사이드메뉴든 뒤에 quote_searchCol를 붙여주는 작업을 하고 있다.
		 }
		 /***********************************************opin01() quote_searchStr end*************************************************************************/
		 // 따옴표 기능 추가 끝
		
		
		/////////////////////////////////////////// 등급분류의견서 시작
		// ()///////////////////////////////////////////////////////////////////////////////////////////////////
		if (sectionGubunList.contains("opin01") || sectionGubunList.contains("Total")) {
			Map<String, Object> opin01 = new HashMap<String, Object>();

			if (searchStr != null) {
				/*************************************************************
				 * 검색어 처리 시작
				 *************************************************************************************************/

				// 검색구분은 if절로서 쓰이고, 검색어는 where절 안에 쓰임으로써, 직접적인 쿼리에 영향을 줌.
				String searchCol = ""; // where절을 담을 변수
				String searchCol_ori_title = "";
				String searchCol_use_title = "";
				String searchCol_rcv_no_view = "";
				String searchCol_medi_name = "";
				String searchCol_prodc_name = "";
				String searchCol_prodc_natnl_name = "";
				String searchCol_dire_name = "";
				String searchCol_mv_asso_name = "";
				String searchCol_leada_name = "";
				String searchCol_kind_name = "";
				String searchCol_hope_grade_name = "";
				String searchCol_prod_year = "";
				String searchCol_aplc_name = "";
				String searchCol_rt_no = "";
				String searchCol_grade_name = "";
				String searchCol_comb_nm = "";
				String searchCol_synt_indvdl_div = "";
				String searchCol_proc_state = "";
				String searchCol_exprt_part_div = "";
				String searchCol_opin_grade_nm1 = "";
				String searchCol_opin_grade_nm2 = "";
				String searchCol_opin_grade_nm3 = "";
				String searchCol_opin_grade_nm4 = "";
				String searchCol_opin_grade_nm5 = "";
				String searchCol_opin_grade_nm6 = "";
				String searchCol_opin_grade_nm7 = "";
				String searchCol_major_opin_narrn_cont = "";
				String searchCol_core_harm_rsn = "";
				String searchCol_work_cont = "";
				String searchCol_deter_rsn = "";
				String searchCol_exprt_synt_opin = "";
				String searchCol_deter_basis = "";
				String searchCol_deter_opin_nm = "";
				String searchCol_reslt_noti_deter_rsn = "";
				String searchCol_rcv_date = "";
				String searchCol_rt_date = "";
				String searchCol_reg_dt = "";

				// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
				if (searchGubun.equals("All")) {
					searchCol += "(" + "use_title" + encodeAns + strMark + encodeSearchStr + strMark + "+allword+" + "synonym('d0')" + 
				               encodeOr + "use_title" + encodeAns + strMark + encodeSearchStr + strMark	+ searchOperation + "synonym('d0')" +
							   encodeOr + "ori_title" + encodeAns + strMark
							+ encodeSearchStr + strMark + searchOperation + "synonym('d0')" + encodeOr + "rcv_no_view"
							+ encodeAns + strMark + encodeSearchStr + strMark + // no2
																				// 접수번호
																				// rcv_no_view
																				// |string
																				// like
																				// X
							encodeOr + "medi_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no4
																													// 매체명
																													// |string
							encodeOr + "prodc_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no7
																														// 제작사명
																														// |string
							encodeOr + "prodc_natnl_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no8 제작사 국적 |string
							encodeOr + "dire_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no9
																													// 감독명
																													// |string
							encodeOr + "mv_asso_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no10
																														// 영화
																														// 종류
																														// |string
							encodeOr + "leada_name" + encodeAns + strMark + encodeSearchStr + strMark	+ searchOperation + // no11
																														// 주연명
																														// |string
							encodeOr + "kind_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no12
																													// 종류
																													// |string
							encodeOr + "hope_grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no13 희망등급 |string
							encodeOr + "prod_year" + encodeAns + strMark + encodeSearchStr + strMark + // no14
																										// 제작년도
																										// |string
																										// like
																										// X
							encodeOr + "aplc_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark +  // no15
																													// 신청회사
																													// |string
							encodeOr + "rt_no" + encodeAns + strMark + encodeSearchStr + strMark + // no69
																									// 등급분류번호
																									// |string
																									// like
																									// X
							encodeOr + "grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no71
																														// 결정등급
																														// |string
							// 기본매체에 존재한 텍스트타입
							encodeOr + "major_opin_narrn_cont" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+" + // no44 주요 의견 서술 내용 |text
							encodeOr + "core_harm_rsn" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+" + // no46 핵심유해사유 |text
							encodeOr + "work_cont" + encodeAns + strMark + encodeSearchStr + strMark + "+someword+" + // no47
																															// 작품내용
																															// |text
							encodeOr + "deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark + "+someword+" + // no54
																															// 결정사유
																															// |text

							/************ 새로 만들어야하는 컬럼 **************/
							encodeOr + "comb_nm" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no23
																													// 위원명
																													// |string

							encodeOr + "synt_indvdl_div" + encodeAns + strMark + encodeSearchStr + strMark + // no25
																												// 종합
																												// 개별
																												// 구분
																												// |string
																												// like
																												// X

							encodeOr + "proc_state" + encodeAns + strMark + encodeSearchStr + strMark + // no30
																										// 진행
																										// 상태
																										// |string
																										// like
																										// X

							encodeOr + "exprt_part_div" + encodeAns + strMark + encodeSearchStr + strMark + // no32
																											// 전문위원
																											// 조
																											// 구분
																											// |string
																											// like
																											// X

							encodeOr + "opin_grade_nm1" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no91
																															// 주제
																															// 등급
																															// |string
							encodeOr + "opin_grade_nm2" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no94
																															// 선정성
																															// 등급
																															// |string
							encodeOr + "opin_grade_nm3" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no97
																															// 폭력성
																															// 등급
																															// |string
							encodeOr + "opin_grade_nm4" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no100
																															// 대사
																															// 등급
																															// |string
							encodeOr + "opin_grade_nm5" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no103
																															// 공포
																															// 등급
																															// |string
							encodeOr + "opin_grade_nm6" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no106
																															// 약물
																															// 등급
																															// |string
							encodeOr + "opin_grade_nm7" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no109
																															// 모방위험
																															// 등급
																															// |string

							// 텍스트타입
							encodeOr + "exprt_synt_opin" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + // no41 전문위원 종합 의견 |text
							encodeOr + "deter_basis" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ // no48 결정근거 |text
							encodeOr + "deter_opin_nm" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + // no52 결정의견 |text
							encodeOr + "reslt_noti_deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation; // no58 결과 통보 결정 사유 |text
					/************ 새로 만들어야하는 컬럼 **************/

					// int는 encode를 따로 안해줘도 무방함.
					if (searchStrInt != 0) {
						searchCol += encodeOr + "rcv_date" + encodeAns + searchStrInt + "000000" + // no6
																									// 접수일자
																									// |
																									// int(date)
								encodeOr + "rt_date" + encodeAns + searchStrInt + "000000" + // no70
																								// 등급분류일
																								// |
																								// int(date)
								encodeOr + "reg_dt" + encodeAns + searchStrInt + "000000"; // no87
																							// 등록
																							// 일시
																							// |
																							// int(date)
					}

					// 통합검색의 마무리 괄호
					searchCol += ")";

					// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
					// 1. 원제명Url (텍스트지만, 중요도에 따라 맨위에 위치)
					searchCol_ori_title = "(" + "ori_title" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + "synonym('d0')" + ")";

					// 2. 사용제명Url (텍스트지만, 중요도에 따라 맨위에 위치)
					searchCol_use_title = "(" + "use_title" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + "synonym('d0')" + ")";

					searchCol_prodc_natnl_name = "(" + "prodc_natnl_name" + encodeAns + strMark + encodeSearchStr
							+ strMark + ")";

					// 3. 접수번호Url
					searchCol_rcv_no_view = "(" + "rcv_no_view" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 4. 매체명Url
					searchCol_medi_name = "(" + "medi_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";

					// 5. 제작사명Url
					searchCol_prodc_name = "(" + "prodc_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";

					// 6. 감독명Url
					searchCol_dire_name = "(" + "dire_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 10. 영화 종류
					searchCol_mv_asso_name = "(" + "mv_asso_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 11. 주연명
					searchCol_leada_name = "(" + "leada_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + ")";
					// 12. 종류
					searchCol_kind_name = "(" + "kind_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 13. 희망등급
					searchCol_hope_grade_name = "(" + "hope_grade_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 14. 제작년도
					searchCol_prod_year = "(" + "prod_year" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 15. 신청회사
					searchCol_aplc_name = "(" + "aplc_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + ")";
					// 16. 등급분류번호
					searchCol_rt_no = "(" + "rt_no" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 17. 결정등급
					searchCol_grade_name = "(" + "grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";

					/**************** 매체에서 추가한것들 ****************************/
					// 18. 위원명
					searchCol_comb_nm = "(" + "comb_nm" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ ")";
					// 19. 종합 개별 구분
					searchCol_synt_indvdl_div = "(" + "synt_indvdl_div" + encodeAns + strMark + encodeSearchStr
							+ strMark + ")";
					// 20. 진행 상태
					searchCol_proc_state = "(" + "proc_state" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 21. 전문위원 조 구분
					searchCol_exprt_part_div = "(" + "exprt_part_div" + encodeAns + strMark + encodeSearchStr + strMark
							+ ")";
					// 22. 주제 등급
					searchCol_opin_grade_nm1 = "(" + "opin_grade_nm1" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 23. 선정성 등급
					searchCol_opin_grade_nm2 = "(" + "opin_grade_nm2" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 24. 폭력성 등급
					searchCol_opin_grade_nm3 = "(" + "opin_grade_nm3" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 25. 대사 등급
					searchCol_opin_grade_nm4 = "(" + "opin_grade_nm4" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 26. 공포 등급
					searchCol_opin_grade_nm5 = "(" + "opin_grade_nm5" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 27. 약물 등급
					searchCol_opin_grade_nm6 = "(" + "opin_grade_nm6" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 28. 모방위험 등급
					searchCol_opin_grade_nm7 = "(" + "opin_grade_nm7" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";

					/******* 텍스트 *********/
					// 29. 주요 의견 서술 내용
					searchCol_major_opin_narrn_cont = "(" + "major_opin_narrn_cont" + encodeAns + strMark
							+ "+someword+" + strMark + searchOperation + ")";
					// 30. 핵심유해사유
					searchCol_core_harm_rsn = "(" + "core_harm_rsn" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+" + ")";
					// 31. 작품내용
					searchCol_work_cont = "(" + "work_cont" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+" + ")";
					// 32. 결정사유
					searchCol_deter_rsn = "(" + "deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+" + ")";
					// 33. 전문위원 종합 의견
					searchCol_exprt_synt_opin = "(" + "exprt_synt_opin" + encodeAns + strMark + encodeSearchStr
							+ strMark + searchOperation + ")";
					// 34. 결정근거
					searchCol_deter_basis = "(" + "deter_basis" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + ")";
					// 35. 결정의견
					searchCol_deter_opin_nm = "(" + "deter_opin_nm" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + ")";
					// 36. 결과 통보 결정 사유
					searchCol_reslt_noti_deter_rsn = "(" + "reslt_noti_deter_rsn" + encodeAns + strMark
							+ encodeSearchStr + strMark + searchOperation + ")";

					/*** 검색어가 숫자인 경우 추가되는 항목!! ***/
					if (searchStrInt != 0) {

						// 37. 접수일자
						searchCol_rcv_date = "(" + "rcv_date" + encodeAns + searchStrInt + "000000" + ")";
						// 38. 등급분류일
						searchCol_rt_date = "(" + "rt_date" + encodeAns + searchStrInt + "000000" + ")";
						// 39. 등록 일시
						searchCol_reg_dt = "(" + "reg_dt" + encodeAns + searchStrInt + "000000" + ")";

					}

				}
				if (searchGubun.equals("title")) {
					searchCol += "(use_title" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ "synonym('d0')" + encodeOr + "ori_title" + encodeAns + strMark + encodeSearchStr
							+ strMark + searchOperation + "synonym('d0')" + ")";
				}
				if (searchGubun.equals("decision")) {
					searchCol += "(deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ "synonym('d0')" + encodeOr + "deter_basis" + encodeAns + strMark + encodeSearchStr
							+ strMark + searchOperation + "synonym('d0')" + ")";
				}

				// 검색어가 빈값일때 (등급분류의견서)
				if (searchStr.equals("NO_VALUE")) {
					searchCol = "ori_title+not+in%7B%7D+";
					searchCol_rcv_no_view = "ori_title+not+in%7B%7D+";
					searchCol_medi_name = "ori_title+not+in%7B%7D+";
					searchCol_prodc_name = "ori_title+not+in%7B%7D+";
					searchCol_prodc_natnl_name = "ori_title+not+in%7B%7D+";
					;
					searchCol_dire_name = "ori_title+not+in%7B%7D+";
					searchCol_mv_asso_name = "ori_title+not+in%7B%7D+";
					searchCol_leada_name = "ori_title+not+in%7B%7D+";
					searchCol_kind_name = "ori_title+not+in%7B%7D+";
					searchCol_hope_grade_name = "ori_title+not+in%7B%7D+";
					searchCol_prod_year = "ori_title+not+in%7B%7D+";
					searchCol_aplc_name = "ori_title+not+in%7B%7D+";
					searchCol_rt_no = "ori_title+not+in%7B%7D+";
					searchCol_grade_name = "ori_title+not+in%7B%7D+";
					searchCol_comb_nm = "ori_title+not+in%7B%7D+";
					searchCol_synt_indvdl_div = "ori_title+not+in%7B%7D+";
					searchCol_proc_state = "ori_title+not+in%7B%7D+";
					searchCol_exprt_part_div = "ori_title+not+in%7B%7D+";
					searchCol_opin_grade_nm1 = "ori_title+not+in%7B%7D+";
					searchCol_opin_grade_nm2 = "ori_title+not+in%7B%7D+";
					searchCol_opin_grade_nm3 = "ori_title+not+in%7B%7D+";
					searchCol_opin_grade_nm4 = "ori_title+not+in%7B%7D+";
					searchCol_opin_grade_nm5 = "ori_title+not+in%7B%7D+";
					searchCol_opin_grade_nm6 = "ori_title+not+in%7B%7D+";
					searchCol_opin_grade_nm7 = "ori_title+not+in%7B%7D+";
					searchCol_major_opin_narrn_cont = "ori_title+not+in%7B%7D+";
					searchCol_core_harm_rsn = "ori_title+not+in%7B%7D+";

					// 텍스트타입
					searchCol_ori_title = "ori_title+not+in%7B%7D+";
					searchCol_use_title = "ori_title+not+in%7B%7D+";
					searchCol_major_opin_narrn_cont = "ori_title+not+in%7B%7D+";
					searchCol_core_harm_rsn = "ori_title+not+in%7B%7D+";
					searchCol_work_cont = "ori_title+not+in%7B%7D+";
					searchCol_deter_rsn = "ori_title+not+in%7B%7D+";
					searchCol_exprt_synt_opin = "ori_title+not+in%7B%7D+";
					searchCol_deter_basis = "ori_title+not+in%7B%7D+";
					searchCol_deter_opin_nm = "ori_title+not+in%7B%7D+";
					searchCol_reslt_noti_deter_rsn = "ori_title+not+in%7B%7D+";

					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_rcv_date = "ori_title+not+in%7B%7D+";
						searchCol_rt_date = "ori_title+not+in%7B%7D+";
						searchCol_reg_dt = "ori_title+not+in%7B%7D+";
					}
				}

				// 상세검색 일때만 뒤에 이걸 붙여주자!
				/**************** 일자 통합작업중 ***********************/
				if (detail_visibleChk.equals("Y")) {
					
				///////////////////////////////AND/OR 연산조건 관련 변수 초기화  시작///////////////////////////////////////////////////////////		
					//1. 상세검색 조건내에 선택한  연산자에 따라서 and를 할지 or을 할지 정하는 로직. 
					// encodeTotalChk : 상세조건 내 AND/OR 연산자 무엇을 선택했는지에 대한 값을 지니고 있는 변수 (JSP에서 넘어오는 데이터)
					String encodeTotalChk = (String) paramMap.get("detailSearch_radio");
					// encodeTotal : 상세조건 내 AND/OR 연산자 선택에 따라 And나 Or 값을 지니고 있는 변수
					String encodeTotal = ""; 
					
					if(encodeTotalChk != null){
						if(encodeTotalChk.equals("allword")){
							encodeTotal = encodeAnd;
						} else if(encodeTotalChk.equals("anyword")){
							encodeTotal = encodeOr;
						} 
					} else {
						encodeTotal = encodeAnd;
					}
					
					//2. 상세검색 조건문에 들어오면 AND연산자 해주고,  괄호도  열어주기. (상세검색 조건문 끝날떄쯤 괄호 닫아줄 것)
					// 상세검색 시 이 구문이 기본적으로 실행되며, 구조는 "(기존검색) AND (" 인데, // 여기서 "AND (" 부분을 셋팅해주는 구문이다. 		
						
						searchCol += encodeAnd + encodeLGual;
						searchCol_rcv_no_view += encodeAnd + encodeLGual;
						searchCol_medi_name += encodeAnd + encodeLGual;
						searchCol_prodc_name += encodeAnd + encodeLGual;
						searchCol_prodc_natnl_name += encodeAnd + encodeLGual;
						searchCol_dire_name += encodeAnd + encodeLGual;
						searchCol_leada_name += encodeAnd + encodeLGual;
						searchCol_mv_asso_name += encodeAnd + encodeLGual;
						searchCol_kind_name += encodeAnd + encodeLGual;
						searchCol_hope_grade_name += encodeAnd + encodeLGual;
						searchCol_prod_year += encodeAnd + encodeLGual;
						searchCol_aplc_name += encodeAnd + encodeLGual;
						searchCol_rt_no += encodeAnd + encodeLGual;
						searchCol_grade_name += encodeAnd + encodeLGual;
						searchCol_comb_nm += encodeAnd + encodeLGual;
						searchCol_synt_indvdl_div += encodeAnd + encodeLGual;
						searchCol_proc_state += encodeAnd + encodeLGual;
						searchCol_exprt_part_div += encodeAnd + encodeLGual;
						searchCol_opin_grade_nm1 += encodeAnd + encodeLGual;
						searchCol_opin_grade_nm2 += encodeAnd + encodeLGual;
						searchCol_opin_grade_nm3 += encodeAnd + encodeLGual;
						searchCol_opin_grade_nm4 += encodeAnd + encodeLGual;
						searchCol_opin_grade_nm5 += encodeAnd + encodeLGual;
						searchCol_opin_grade_nm6 += encodeAnd + encodeLGual;
						searchCol_opin_grade_nm7 += encodeAnd + encodeLGual;
						searchCol_major_opin_narrn_cont += encodeAnd + encodeLGual;
						searchCol_core_harm_rsn += encodeAnd + encodeLGual;

						// 텍스트타입
						searchCol_ori_title += encodeAnd + encodeLGual;
						searchCol_use_title += encodeAnd + encodeLGual;
						searchCol_work_cont += encodeAnd + encodeLGual;
						searchCol_deter_rsn += encodeAnd + encodeLGual;
						searchCol_exprt_synt_opin += encodeAnd + encodeLGual;
						searchCol_deter_basis += encodeAnd + encodeLGual;
						searchCol_deter_opin_nm += encodeAnd + encodeLGual;
						searchCol_reslt_noti_deter_rsn += encodeAnd + encodeLGual;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_rcv_date += encodeAnd + encodeLGual;
							searchCol_rt_date += encodeAnd + encodeLGual;
							searchCol_reg_dt += encodeAnd + encodeLGual;
						}
					
					
					
					
					
					
					//첫번째 조건인지 아닌지 확인하는 변수
					int firstConditionChk = 0;
///////////////////////////////AND/OR 연산조건 관련 변수 초기화  끝///////////////////////////////////////////////////////////
					
					//opin01
					
					// 일자보다 신청회사 먼저 붙여준다.
					if (!(detailAplc.equals("") || detailAplc == null)) {
						detailAplc = URLEncoder.encode(detailAplc, "UTF-8");
						// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
						// 첫번쨰 조건 체크 ------------- 시작 
						String chkResult = "";
						if(firstConditionChk == 0){
							firstConditionChk++;
						} else {
							chkResult = encodeTotal;
						}
						// 첫번쨰 조건 체크 ------------- 끝
						
						
						searchCol += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*" + detailAplc
								+ "*" + strMark + encodeRGual;
						searchCol_rcv_no_view += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_medi_name += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_prodc_name += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_prodc_natnl_name += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						;
						searchCol_dire_name += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_mv_asso_name += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_leada_name += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_kind_name += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_hope_grade_name += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_prod_year += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_aplc_name += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_rt_no += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_grade_name += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_comb_nm += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_synt_indvdl_div += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_proc_state += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_exprt_part_div += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_opin_grade_nm1 += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_opin_grade_nm2 += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_opin_grade_nm3 += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_opin_grade_nm4 += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_opin_grade_nm5 += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_opin_grade_nm6 += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_opin_grade_nm7 += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_major_opin_narrn_cont += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark
								+ "*" + detailAplc + "*" + strMark + encodeRGual;
						searchCol_core_harm_rsn += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;

						// 텍스트타입
						searchCol_ori_title += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_use_title += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_work_cont += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_deter_rsn += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_exprt_synt_opin += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_deter_basis += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_deter_opin_nm += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_reslt_noti_deter_rsn += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark
								+ "*" + detailAplc + "*" + strMark + encodeRGual;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_rcv_date += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
									+ detailAplc + "*" + strMark + encodeRGual;
							searchCol_rt_date += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
									+ detailAplc + "*" + strMark + encodeRGual;
							searchCol_reg_dt += chkResult + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
									+ detailAplc + "*" + strMark + encodeRGual;
						}

					}

					String detail_date = "";
					if (!period_start.equals("") && !period_end.equals("")) {
						// 첫번쨰 조건 체크 ------------- 시작 
						String chkResult = "";
						if(firstConditionChk == 0){
							firstConditionChk++;
						} else {
							chkResult = encodeTotal;
						}
						// 첫번쨰 조건 체크 ------------- 끝
						
						if (detail_dateGubun.equals("regdate")) {
							searchCol += chkResult + encodeLGual + "sub_rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 신청일자
							detail_date = chkResult + encodeLGual + "sub_rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;
						} else if (detail_dateGubun.equals("jubsudate")) {
							searchCol += chkResult + encodeLGual + "rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 접수일자
							detail_date = chkResult + encodeLGual + "rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;
						} else {
							searchCol += chkResult + encodeLGual + "rt_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 등급분류일자
							detail_date = chkResult + encodeLGual + "rt_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;
						}

						// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
						searchCol_dire_name += detail_date;
						searchCol_rcv_no_view += detail_date;
						searchCol_medi_name += detail_date;
						searchCol_prodc_name += detail_date;
						searchCol_prodc_natnl_name += detail_date;
						searchCol_mv_asso_name += detail_date;
						searchCol_leada_name += detail_date;
						searchCol_kind_name += detail_date;
						searchCol_hope_grade_name += detail_date;
						searchCol_prod_year += detail_date;
						searchCol_aplc_name += detail_date;
						searchCol_rt_no += detail_date;
						searchCol_grade_name += detail_date;
						searchCol_comb_nm += detail_date;
						searchCol_synt_indvdl_div += detail_date;
						searchCol_proc_state += detail_date;
						searchCol_exprt_part_div += detail_date;
						searchCol_opin_grade_nm1 += detail_date;
						searchCol_opin_grade_nm2 += detail_date;
						searchCol_opin_grade_nm3 += detail_date;
						searchCol_opin_grade_nm4 += detail_date;
						searchCol_opin_grade_nm5 += detail_date;
						searchCol_opin_grade_nm6 += detail_date;
						searchCol_opin_grade_nm7 += detail_date;
						searchCol_major_opin_narrn_cont += detail_date;
						searchCol_core_harm_rsn += detail_date;

						// 텍스트타입
						searchCol_ori_title += detail_date;
						searchCol_use_title += detail_date;
						searchCol_work_cont += detail_date;
						searchCol_deter_rsn += detail_date;
						searchCol_exprt_synt_opin += detail_date;
						searchCol_deter_basis += detail_date;
						searchCol_deter_opin_nm += detail_date;
						searchCol_reslt_noti_deter_rsn += detail_date;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_rcv_date += detail_date;
							searchCol_rt_date += detail_date;
							searchCol_reg_dt += detail_date;
						}
					} else {
						//만약 날짜가 빈값이라면? 더미데이터를 넣어주기 위한 로직이다.
						
						
						// 첫번쨰 조건 체크 ------------- 시작 
						String chkResult = "";
						if(firstConditionChk == 0){
							firstConditionChk++;
						} else {
							chkResult = encodeTotal;
						}
						// 첫번쨰 조건 체크 ------------- 끝
						
						searchCol += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_rcv_no_view += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_medi_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_prodc_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_prodc_natnl_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_dire_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_leada_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_mv_asso_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_kind_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_hope_grade_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_prod_year += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_aplc_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_rt_no += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_grade_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_comb_nm += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_synt_indvdl_div += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_proc_state += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_exprt_part_div += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_opin_grade_nm1 += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_opin_grade_nm2 += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_opin_grade_nm3 += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_opin_grade_nm4 += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_opin_grade_nm5 += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_opin_grade_nm6 += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_opin_grade_nm7 += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_major_opin_narrn_cont += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_core_harm_rsn += chkResult + "ori_title+not+in%7B%7D+";

						// 텍스트타입
						searchCol_ori_title += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_use_title += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_work_cont += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_deter_rsn += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_exprt_synt_opin += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_deter_basis += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_deter_opin_nm += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_reslt_noti_deter_rsn += chkResult + "ori_title+not+in%7B%7D+";

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_rcv_date += chkResult + "ori_title+not+in%7B%7D+";
							searchCol_rt_date += chkResult + "ori_title+not+in%7B%7D+";
							searchCol_reg_dt += chkResult + "ori_title+not+in%7B%7D+";
						}
					}

					//만약에 상세조건에 아무것도 입력하지 않는다면, 더미데이터룰 넣어준다 --> 데이터가 없으면 오류가 나기 떄문이다.
					if(searchCol.substring(searchCol.length()-1).equals("(")){
						searchCol += "ori_title+not+in%7B%7D+";
						searchCol_rcv_no_view += "ori_title+not+in%7B%7D+";
						searchCol_medi_name += "ori_title+not+in%7B%7D+";
						searchCol_prodc_name += "ori_title+not+in%7B%7D+";
						searchCol_prodc_natnl_name += "ori_title+not+in%7B%7D+";
						searchCol_dire_name += "ori_title+not+in%7B%7D+";
						searchCol_leada_name += "ori_title+not+in%7B%7D+";
						searchCol_mv_asso_name += "ori_title+not+in%7B%7D+";
						searchCol_kind_name += "ori_title+not+in%7B%7D+";
						searchCol_hope_grade_name += "ori_title+not+in%7B%7D+";
						searchCol_prod_year += "ori_title+not+in%7B%7D+";
						searchCol_aplc_name += "ori_title+not+in%7B%7D+";
						searchCol_rt_no += "ori_title+not+in%7B%7D+";
						searchCol_grade_name += "ori_title+not+in%7B%7D+";
						searchCol_comb_nm += "ori_title+not+in%7B%7D+";
						searchCol_synt_indvdl_div += "ori_title+not+in%7B%7D+";
						searchCol_proc_state += "ori_title+not+in%7B%7D+";
						searchCol_exprt_part_div += "ori_title+not+in%7B%7D+";
						searchCol_opin_grade_nm1 += "ori_title+not+in%7B%7D+";
						searchCol_opin_grade_nm2 += "ori_title+not+in%7B%7D+";
						searchCol_opin_grade_nm3 += "ori_title+not+in%7B%7D+";
						searchCol_opin_grade_nm4 += "ori_title+not+in%7B%7D+";
						searchCol_opin_grade_nm5 += "ori_title+not+in%7B%7D+";
						searchCol_opin_grade_nm6 += "ori_title+not+in%7B%7D+";
						searchCol_opin_grade_nm7 += "ori_title+not+in%7B%7D+";
						searchCol_major_opin_narrn_cont += "ori_title+not+in%7B%7D+";
						searchCol_core_harm_rsn += "ori_title+not+in%7B%7D+";

						// 텍스트타입
						searchCol_ori_title += "ori_title+not+in%7B%7D+";
						searchCol_use_title += "ori_title+not+in%7B%7D+";
						searchCol_work_cont += "ori_title+not+in%7B%7D+";
						searchCol_deter_rsn += "ori_title+not+in%7B%7D+";
						searchCol_exprt_synt_opin += "ori_title+not+in%7B%7D+";
						searchCol_deter_basis += "ori_title+not+in%7B%7D+";
						searchCol_deter_opin_nm += "ori_title+not+in%7B%7D+";
						searchCol_reslt_noti_deter_rsn += "ori_title+not+in%7B%7D+";

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_rcv_date += "ori_title+not+in%7B%7D+";
							searchCol_rt_date += "ori_title+not+in%7B%7D+";
							searchCol_reg_dt += "ori_title+not+in%7B%7D+";
						}
					}
					
					searchCol += encodeRGual;
					searchCol_rcv_no_view += encodeRGual;
					searchCol_medi_name += encodeRGual;
					searchCol_prodc_name += encodeRGual;
					searchCol_prodc_natnl_name += encodeRGual;
					searchCol_dire_name += encodeRGual;
					searchCol_leada_name += encodeRGual;
					searchCol_mv_asso_name += encodeRGual;
					searchCol_kind_name += encodeRGual;
					searchCol_hope_grade_name += encodeRGual;
					searchCol_prod_year += encodeRGual;
					searchCol_aplc_name += encodeRGual;
					searchCol_rt_no += encodeRGual;
					searchCol_grade_name += encodeRGual;
					searchCol_comb_nm += encodeRGual;
					searchCol_synt_indvdl_div += encodeRGual;
					searchCol_proc_state += encodeRGual;
					searchCol_exprt_part_div += encodeRGual;
					searchCol_opin_grade_nm1 += encodeRGual;
					searchCol_opin_grade_nm2 += encodeRGual;
					searchCol_opin_grade_nm3 += encodeRGual;
					searchCol_opin_grade_nm4 += encodeRGual;
					searchCol_opin_grade_nm5 += encodeRGual;
					searchCol_opin_grade_nm6 += encodeRGual;
					searchCol_opin_grade_nm7 += encodeRGual;
					searchCol_major_opin_narrn_cont += encodeRGual;
					searchCol_core_harm_rsn += encodeRGual;

					// 텍스트타입
					searchCol_ori_title += encodeRGual;
					searchCol_use_title += encodeRGual;
					searchCol_work_cont += encodeRGual;
					searchCol_deter_rsn += encodeRGual;
					searchCol_exprt_synt_opin += encodeRGual;
					searchCol_deter_basis += encodeRGual;
					searchCol_deter_opin_nm += encodeRGual;
					searchCol_reslt_noti_deter_rsn += encodeRGual;

					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_rcv_date += encodeRGual;
						searchCol_rt_date += encodeRGual;
						searchCol_reg_dt += encodeRGual;
					}
				}
				//detail_visibleChk 조건 끝
				
				//만약 따옴표 안에 단어가 인식이 된다면 이 로직 실행.
				//통합검색이든, 메뉴명이든 뒤에 quote_searchCol 붙여주기 시작! 
				if(!quote_searchStr.equals("")) {
					searchCol += quote_searchCol;
					searchCol_rcv_no_view += quote_searchCol;
					searchCol_medi_name += quote_searchCol;
					searchCol_prodc_name += quote_searchCol;
					searchCol_prodc_natnl_name += quote_searchCol;
					searchCol_dire_name += quote_searchCol;
					searchCol_leada_name += quote_searchCol;
					searchCol_mv_asso_name += quote_searchCol;
					searchCol_kind_name += quote_searchCol;
					searchCol_hope_grade_name += quote_searchCol;
					searchCol_prod_year += quote_searchCol;
					searchCol_aplc_name += quote_searchCol;
					searchCol_rt_no += quote_searchCol;
					searchCol_grade_name += quote_searchCol;
					searchCol_comb_nm += quote_searchCol;
					searchCol_synt_indvdl_div += quote_searchCol;
					searchCol_proc_state += quote_searchCol;
					searchCol_exprt_part_div += quote_searchCol;
					searchCol_opin_grade_nm1 += quote_searchCol;
					searchCol_opin_grade_nm2 += quote_searchCol;
					searchCol_opin_grade_nm3 += quote_searchCol;
					searchCol_opin_grade_nm4 += quote_searchCol;
					searchCol_opin_grade_nm5 += quote_searchCol;
					searchCol_opin_grade_nm6 += quote_searchCol;
					searchCol_opin_grade_nm7 += quote_searchCol;
					searchCol_major_opin_narrn_cont += quote_searchCol;
					searchCol_core_harm_rsn += quote_searchCol;

					// 텍스트타입
					searchCol_ori_title += quote_searchCol;
					searchCol_use_title += quote_searchCol;
					searchCol_work_cont += quote_searchCol;
					searchCol_deter_rsn += quote_searchCol;
					searchCol_exprt_synt_opin += quote_searchCol;
					searchCol_deter_basis += quote_searchCol;
					searchCol_deter_opin_nm += quote_searchCol;
					searchCol_reslt_noti_deter_rsn += quote_searchCol;

					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_rcv_date += quote_searchCol;
						searchCol_rt_date += quote_searchCol;
						searchCol_reg_dt += quote_searchCol;
					}
					//통합검색이든, 메뉴명이든 뒤에 quote_searchCol 붙여주기 끝! 	
				}
				/**************** 상세검색의 일자, 신청회사 통합작업중 ***********************/

				/**************
				 * ■■■■■■■■■■■■■*************************************우측사이드에 관한
				 * information 시작
				 * *************************************************■■■■■■■■■
				 **************************************************/
				List<Map<String, Object>> opin01_sideInformation = new ArrayList<Map<String, Object>>();
				if (searchGubun.equals("All")) {
					// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
					// 1. (조건에 맞는)원제명에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_ori_title, "원제명"));

					// 2. (조건에 맞는)사용제명에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_use_title, "사용제명"));

					// 3. (조건에 맞는)접수번호에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_rcv_no_view, "접수번호"));

					// 4. (조건에 맞는)매체명에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_medi_name, "매체명"));

					// 5. (조건에 맞는)제작사명에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_prodc_name, "제작사명"));

					// 6. (조건에 맞는)제작사 국적에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_prodc_natnl_name, "제작사 국적"));

					// 7. (조건에 맞는)감독명에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_dire_name, "감독명"));

					// 8. (조건에 맞는)영화 종류에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_mv_asso_name, "영화 종류"));

					// 9. (조건에 맞는)주연명에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_leada_name, "주연명"));

					// 10. (조건에 맞는)종류에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_kind_name, "종류"));

					// 11. (조건에 맞는)희망등급에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_hope_grade_name, "희망등급"));

					// 12. (조건에 맞는)제작년도에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_prod_year, "제작년도"));

					// 13. (조건에 맞는)신청회사에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_aplc_name, "신청회사"));

					// 14. (조건에 맞는)등급분류번호 에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_rt_no, "등급분류번호"));

					// 15. (조건에 맞는)결정등급에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_grade_name, "결정등급"));

					// 16. (조건에 맞는)위원명에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_comb_nm, "위원명"));

					// 17 (조건에 맞는)종합 개별 구분에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_synt_indvdl_div, "종합 개별 구분"));

					// 18 (조건에 맞는)진행 상태에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_proc_state, "진행 상태"));

					// 19 (조건에 맞는)전문위원 조 구분에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_exprt_part_div, "전문위원 조 구분"));

					// 20 (조건에 맞는)주제 등급에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_opin_grade_nm1, "주제 등급"));

					// 21 (조건에 맞는)선정성 등급에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_opin_grade_nm2, "선정성 등급"));

					// 22 (조건에 맞는)폭력성 등급에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_opin_grade_nm3, "폭력성 등급"));

					// 23 (조건에 맞는)대사 등급에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_opin_grade_nm4, "대사 등급"));

					// 24 (조건에 맞는)공포 등급에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_opin_grade_nm5, "공포 등급"));

					// 25 (조건에 맞는)약물 등급에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_opin_grade_nm6, "약물 등급"));

					// 26 (조건에 맞는)모방위험 등급에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_opin_grade_nm7, "모방위험 등급"));

					// 텍스트 타입 시작
					// 27. (조건에 맞는)주요 의견 서술 내용 에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_major_opin_narrn_cont, "주요 의견 서술 내용"));

					// 28. (조건에 맞는)핵심유해사유에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_core_harm_rsn, "핵심유해사유"));

					// 29. (조건에 맞는)작품내용에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_work_cont, "작품내용"));

					// 30. (조건에 맞는)결정사유에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_deter_rsn, "결정사유"));

					// 31 (조건에 맞는)전문위원 종합 의견에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_exprt_synt_opin, "전문위원 종합 의견"));

					// 32 (조건에 맞는)결정근거에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_deter_basis, "결정근거"));

					// 33 (조건에 맞는)결정의견에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_deter_opin_nm, "결정의견"));

					// 34 (조건에 맞는)결과 통보 결정 사유에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_reslt_noti_deter_rsn, "결과 통보 결정 사유"));
					/**************** 텍스트 데이터 끝!!! ******************/

					/**************** 숫자 데이터 시작 (null처리)!!! ******************/

					if (searchStrInt != 0) {
						// 35 (조건에 맞는)접수일자에 대한 건수 및 데이터 조회
						opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response,
								result_opinUrl, orderValue, searchCol_rcv_date, "접수일자"));
						// 36 (조건에 맞는)등급분류일에 대한 건수 및 데이터 조회
						opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response,
								result_opinUrl, orderValue, searchCol_rt_date, "등급분류일"));
						// 37 (조건에 맞는)등록 일시에 대한 건수 및 데이터 조회
						opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response,
								result_opinUrl, orderValue, searchCol_reg_dt, "등록 일시"));
					}
					/**************** 숫자 데이터 끝 (null처리)!!! ******************/

					Collections.sort(opin01_sideInformation, new sideMenuCntComparator());
				} // if(searchGubun.equals("All")) 끝
				if (searchGubun.equals("title")) {
					// (조건에 맞는)원제명 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol, "원제명/사용제명"));
				}
				if (searchGubun.equals("decision")) {
					// (조건에 맞는)결정사유 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol, "결정사유"));
				}
				// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
				walker.setValue("opin01_sideInformation", opin01_sideInformation);

				/**************
				 * ■■■■■■■■■■■■■***************************************우측사이드에 관한
				 * information
				 * 끝***********************************************■■■■■■■■■
				 **************************************************/

				/*************************************************************
				 * 검색어 처리 끝
				 *************************************************************************************************/

				totalUrl = result_opinUrl  + searchCol + orderValue;
				// 결과내 재검색이라면 ? 시작
				if (((String) paramMap.get("result_re_search_chk")).equals("Y")
						&& (String) paramMap.get("opin01_historySearchQuery") != null) {
					totalUrl = (String) paramMap.get("opin01_historySearchQuery") + encodeAnd + "(" + searchCol + ")"
							+ orderValue;
				}
				// 결과내 재검색이라면 ? 끝

				// 이전쿼리 저장 시작
				String historySearchQuery = "";
				historySearchQuery = result_opinUrl  + "(" + searchCol + ")";
				// 만약 결과내재검색을 통한 검색이었다면, historySearchQuery(이전쿼리)는 , 이전쿼리 +
				// encodeAnd + 현재쿼리 일 것이다.
				if (((String) paramMap.get("result_re_search_chk")).equals("Y")
						&& (String) paramMap.get("opin01_historySearchQuery") != null) {
					historySearchQuery = (String) paramMap.get("opin01_historySearchQuery") + encodeAnd + "("
							+ searchCol + ")";
				}
				paramMap.put("opin01_historySearchQuery", historySearchQuery);
				// 이전쿼리 저장 끝
			} else {
				// 검색어가 공백이라면? result_opin에 관한 모든 데이터 조회!
				totalUrl = result_opinUrl + orderValue;
			}

			/*************************************************************
			 * 얻어낸 Url 결과물로 추가적인 작업시작 / 페이징 처리 시작
			 *************************************************************************************************/
			String paging_totalUrl = totalUrl + "&pagelength=" + detailresultLine + "&offset=" + offset + "&limit="
					+ detailresultLine + "&hilite-fields="
					+ "%7B%22use_title%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22ori_title%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22aplc_name%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22synt_indvdl_div%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22comb_nm%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22rt_no%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22exprt_part_div%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22opin_grade_nm1%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22opin_grade_nm2%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22opin_grade_nm3%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22opin_grade_nm4%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22opin_grade_nm5%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22opin_grade_nm6%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22opin_grade_nm7%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22work_cont%22%3A%7B%22length%22%3A3000%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22deter_rsn%22%3A%7B%22length%22%3A3000%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22proc_state%22%3A%7B%22length%22%3A1000%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D";
			List<Object> dataList = pagingURLEncodingFunc(paging_totalUrl);
			opin01.put("total_count_page", (Integer) dataList.get(0));
			walker.setValue("opin01_resultMapList", (List<Map<String, String>>) dataList.get(1));
			/*************************************************************
			 * 얻어낸 Url 결과물로 추가적인 작업시작 / 페이징 처리 시작
			 *************************************************************************************************/

			
			
			// totalURLCntFunc로 총합 갯수만 가져오고있다. -> 데이터가 필요하다면 해당 메소드를 안에
			// resultMap을 확인하면 됨.
			Integer total_count = totalURLCntFunc(totalUrl);
			opin01.put("total_count", total_count);
			walker.setValue("opin01", opin01);

		}
		/////////////////////////////////////////// 등급분류의견서 끝
		/////////////////////////////////////////// ()///////////////////////////////////////////////////////////////////////////////////////////////////
	}

	/**
	 * 
	 * @param walker
	 * @param paramMap
	 * @param request
	 * @param response
	 * @throws Exception
	 *             등급분류의견서에 대한 데이터 추출하는 메소드
	 */
	
	public void newopin01(Walker walker, Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// null처리는 필수!
		// 파라미터 목록 가져오기. * 검색어, 검색구분, 섹션구분
		String searchStr = (String) paramMap.get("searchStr");
		String searchGubun = (String) paramMap.get("searchGubun");
		String[] sectionGubun = null;
		if (request.getParameterValues("sectionGubun") == null) {
			sectionGubun = new String[] { "Total" };
		} else {
			sectionGubun = request.getParameterValues("sectionGubun");
		}
		List<String> sectionGubunList = new ArrayList<String>(Arrays.asList(sectionGubun));

		/*************** 검색어에 숫자가 들어올떄 *******************************/
		long searchStrInt = 0;
		if (paramMap.get("searchStr") != null && ((String) paramMap.get("searchStr")).matches("[-+]?\\d*\\.?\\d+")) {
			// System.out.println("이것은 숫자");
			searchStrInt = Long.valueOf((String) paramMap.get("searchStr"));
			// System.out.println(searchStrInt);
		}
		/*************** 검색어에 숫자가 들어올떄 *******************************/
		// 최종 URL
		String totalUrl = "";
		// 공통 URL
		String result_noticeUrl = "http://192.168.11.242:7577/search5?select=*&from=result_notice.result_notice&where=";
		String result_opinUrl = "http://192.168.11.242:7577/search5?select=*&from=result_opin.result_opin&where=";
		String result_fileUrl = "http://192.168.11.242:7577/search5?select=*&from=result_file.result_file&where=";
		String result_imageUrl = "http://192.168.11.242:7577/search5?select=*&from=result_image.result_image&where=";
		String result_moniUrl = "http://192.168.11.242:7577/search5?select=*&from=result_moni.result_moni&where=";
		String result_surveyUrl = "http://192.168.11.242:7577/search5?select=*&from=result_survey.result_survey&where=";
		// 공통 인코딩 목록
		String encode = "UTF-8";
		String encodeSearchStr = URLEncoder.encode(searchStr, encode); // 검색어
		String encodeSearchGubun = URLEncoder.encode(searchGubun, encode); // 검색구분(통합검색,
																			// 제목,
																			// 내용,
																			// 결정사유
																			// 등등)
		String encodeAnd = URLEncoder.encode(" and ", encode);
		String encodeOr = URLEncoder.encode(" or ", encode);
		String encodeAns = URLEncoder.encode("=", encode);
		String encodeInOpen = URLEncoder.encode(" in{", encode);
		String encodeInClose = URLEncoder.encode("}", encode);
		String encodeLGual = "(";
		String encodeRGual = ")";
		String encodeLike = URLEncoder.encode(" like ", encode);
		String strMark = URLEncoder.encode("'", encode);
		String between = URLEncoder.encode(" between ", encode);

		// where절의 text타입에만 사용가능.
		String allword = URLEncoder.encode(" allword ", encode);
		String anyword = URLEncoder.encode(" anyword ", encode);
		String natural = URLEncoder.encode(" natural ", encode);

		// 동의어 검색
		String synonym = URLEncoder.encode(" synonym ", encode);

		// 카테고리 정렬 ex) ORDER BY $CATEGORYFIELD(field_name(domain), keyword)
		String categoryOrder = URLEncoder.encode(" $categoryfield(use_title(), 검색어넣기 ", encode);

		/**
		 * 17501 : 국내영화 17502 : 국외영화 17505 : 광고영화 17511 : 국내비디오 17512 : 국외비디오
		 */

		// medi_code 인코딩 목록
		String medi_mv = URLEncoder.encode("'17501','17502'", encode);
		String medi_ad = URLEncoder.encode("'17503','17504','17505','17506','17514'", encode);
		String medi_video = URLEncoder.encode("'17511','17522'", encode);
		String medi_perform = URLEncoder.encode("'17521','17522','17523','17524'", encode);

		/*
		 * //첫 로딩 시 데이터를 표시해주는 역할을 함. if(searchGubun == null ){ searchGubun =
		 * "All"; } else if(searchGubun.trim().isEmpty()){ searchGubun = "All";
		 * }
		 */

		// 부가설명 : 처음 orderValue로 JSP에서 넘어온 정렬값을 받아서, 넘어온 정렬값을 기준으로 url인코딩을 실시한다.
		String orderValue = (String) request.getParameter("orderValue");
		if (orderValue == null || orderValue.equals("")) {
			orderValue = URLEncoder.encode(" order by $matchfield(use_title,use1) desc, $relevance desc ", encode);
		}
		if (orderValue.equals("orderRelevance")) {
			orderValue = URLEncoder.encode(" order by $matchfield(use_title,use1) desc, $relevance desc ", encode);
		}
		if (orderValue.equals("orderGa")) {
			orderValue = "%20order%20by%20use_title_sort%20asc%20%2c%20ori_title_sort%20asc%20";
		}
		if (orderValue.equals("orderDate")) {
			orderValue = "%20order%20by%20rcv_date%20desc%20";
		}

		//
		String groupByTest = URLEncoder.encode(" order by $relevance desc ", encode);

		// 2022 12 15 페이징 처리 시작

		// 1. sectionGubun을 하나만 선택하거나, -더보기 클릭 시, sectionGubun에는 딱 하나의 데이터가
		// 들어있고, 페이지는 1페이지로 이동한다. -->sectionGubun이 딱 하나라면 (Total)이 아닐떄 , 페이징목록이
		// 보이게끔 유도하기.
		// 페이지는 페이지 목록 버튼 시 마다 2페이지 3페이지 이동한다... 그러다가 다시 검색버튼 같은것을 누르면 1페이지로
		// 초기화!

		// 기존 display가 limit로 대체

		int cPage = 0;
		int offset = 0;
		int pagelength = 0;

		/**************************
		 * 20221223 페이징처리를 위한 새로운 로직 (영화, 비디오 반영완료)
		 *********************************************/
		if (((String) paramMap.get("isPaging")).equals("Y")) {
			cPage = (Integer) paramMap.get("cPage");
			offset = (Integer) paramMap.get("offset");
			pagelength = (Integer) paramMap.get("pagelength");
			offset = (cPage - 1) * pagelength;
		} else {
			cPage = 1;
			offset = 0;
			pagelength = 10;
			offset = (cPage - 1) * pagelength;
		}

		/*******************************************************************/

		/**************** 상세보기 시작 ********************************/
		// 20221216 상세보기에 관련된 것들 맨 위로 빼기 (기본값이 존재함)
		// 하지만 상세보기를 하지 않는다면 이 값이 null이라서 오류가뜸 ~~~~~~~~~~~~~~~~~~~~~~~~~~~

		// 상세보기를 클릭했는지 여부
		String detail_visibleChk = (String) paramMap.get("detail_visibleChk");

		// 연산자 가져오기
		String searchOperation = "";

		// 결과 수
		int detailresultLine = 3;
		try {
			if ((String) paramMap.get("detailSearch_resultLine") != null) {
				detailresultLine = Integer.valueOf((String) paramMap.get("detailSearch_resultLine"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (detailresultLine != 3 && (String) paramMap.get("detailSearch_resultLine") != null) {
				detailresultLine = detailresultLine;
			} else {
				detailresultLine = 3;
			}
		}

		try {
			if (detail_visibleChk.equals("Y")) {
				searchOperation = (String) paramMap.get("detailSearch_radio");
			} else {
				searchOperation = "natural";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_radio") == null) {
				searchOperation = "natural";
			}
		}

		if (searchOperation.equals("allword")) {
			searchOperation = allword;
		} else if (searchOperation.equals("anyword")) {
			searchOperation = anyword;
		} else if (searchOperation.equals("natural")) {
			searchOperation = natural;
		} else {
			searchOperation = natural;
		}

		// 신청회사 null 처리
		String detailAplc = "";
		try {
			if ((String) paramMap.get("detailSearch_aplc") != null) {
				detailAplc = (String) paramMap.get("detailSearch_aplc");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_aplc") == null) {
				detailAplc = "";
			}
		}

		// 상세보기-일자 데이터 가져오기 및 null 처리 시작

		// 시작날짜
		String period_start = "";
		try {
			if ((String) paramMap.get("period_start") != null) {
				period_start = (String) paramMap.get("period_start");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("period_start") == null) {
				period_start = "";
			}
		}

		// 종료날짜
		String period_end = "";
		try {
			if ((String) paramMap.get("period_end") != null) {
				period_end = (String) paramMap.get("period_end");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("period_end") == null) {
				period_end = "";
			}
		}

		// 등급분류일자, 신청일자 , 접수일자 구분
		String detail_dateGubun = "";
		try {
			if ((String) paramMap.get("detailSearch_date") != null) {
				detail_dateGubun = (String) paramMap.get("detailSearch_date");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_date") == null) {
				detail_dateGubun = "gradedate";
			}
		}

		if (!period_start.equals("")) {
			period_start = period_start.substring(0, 4) + period_start.substring(5, 7) + period_start.substring(8, 10)
					+ "000000";
		}
		if (!period_end.equals("")) {
			period_end = period_end.substring(0, 4) + period_end.substring(5, 7) + period_end.substring(8, 10)
					+ "000000";
		}

		// 듈 중에 하나를 선택안하고 상세검색을 하면 --> 전체날짜로 인식하여 데이터검색하기
		if ((period_start.equals("") || period_end.equals("")) && !(period_start.equals("") && period_end.equals(""))) {
			period_start = "";
			period_end = "";

		}
		// 상세보기-일자 데이터 가져오기 및 null 처리 끝
		/////////////////////////////////////////// 등급분류의견서 시작
		// ()///////////////////////////////////////////////////////////////////////////////////////////////////
		if (sectionGubunList.contains("opin01") || sectionGubunList.contains("Total")) {
			Map<String, Object> opin01 = new HashMap<String, Object>();

			if (searchStr != null) {
				/*************************************************************
				 * 검색어 처리 시작
				 *************************************************************************************************/

				// 검색구분은 if절로서 쓰이고, 검색어는 where절 안에 쓰임으로써, 직접적인 쿼리에 영향을 줌.
				String searchCol = ""; // where절을 담을 변수
				// text타입
				String searchCol_ori_title = "";
				String searchCol_use_title = "";
				String searchCol_exprt_synt_opin = "";
				String searchCol_major_opin_narrn_cont = "";
				String searchCol_core_harm_rsn = "";
				String searchCol_work_cont = "";
				String searchCol_deter_basis = "";
				String searchCol_deter_opin_nm = "";
				String searchCol_deter_rsn = "";
				String searchCol_reslt_noti_deter_rsn = "";

				// String 타입
				String searchCol_rcv_no_view = "";
				String searchCol_medi_name = "";
				String searchCol_prodc_name = "";
				String searchCol_prodc_natnl_name = "";
				String searchCol_dire_name = "";
				String searchCol_mv_asso_name = "";
				String searchCol_leada_name = "";
				String searchCol_kind_name = "";
				String searchCol_hope_grade_name = "";
				String searchCol_prod_year = "";
				String searchCol_aplc_name = "";
				String searchCol_rt_no = "";
				String searchCol_grade_name = "";
				String searchCol_comb_nm = "";
				String searchCol_synt_indvdl_div = "";
				String searchCol_proc_state = "";
				String searchCol_exprt_part_div = "";

				// text, String 타입 혼합. (비슷한 내용 묶음)
				String searchCol_opin1 = "";
				String searchCol_opin_grade_nm1 = "";
				String searchCol_opin2 = "";
				String searchCol_opin_grade_nm2 = "";
				String searchCol_opin3 = "";
				String searchCol_opin_grade_nm3 = "";
				String searchCol_opin4 = "";
				String searchCol_opin_grade_nm4 = "";
				String searchCol_opin5 = "";
				String searchCol_opin_grade_nm5 = "";
				String searchCol_opin6 = "";
				String searchCol_opin_grade_nm6 = "";
				String searchCol_opin7 = "";
				String searchCol_opin_grade_nm7 = "";
				String searchCol_rt_core_harm_rsn_code1 = "";
				String searchCol_rt_core_harm_rsn_code2 = "";
				String searchCol_rt_core_harm_rsn_code3 = "";

				// number 타입
				String searchCol_rcv_date = "";
				String searchCol_rt_date = "";
				String searchCol_reg_dt = "";

				// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
				if (searchGubun.equals("All")) {
					searchCol += "(" + "use_title" + encodeAns + strMark + encodeSearchStr + strMark + "+allword+"
							+ "synonym('d0')" + encodeOr + "use1" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + "synonym('d0')" + encodeOr + "ori_title" + encodeAns + strMark
							+ encodeSearchStr + strMark + searchOperation + "synonym('d0')" + encodeOr + "rcv_no_view"
							+ encodeAns + strMark + encodeSearchStr + strMark + // no2
																				// 접수번호
																				// rcv_no_view
																				// |string
																				// like
																				// X
							encodeOr + "medi_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no4
																													// 매체명
																													// |string
							encodeOr + "prodc_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no7
																														// 제작사명
																														// |string
							encodeOr + "prodc_natnl_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no8 제작사 국적 |string
							encodeOr + "dire_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no9
																													// 감독명
																													// |string
							encodeOr + "mv_asso_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no10
																														// 영화
																														// 종류
																														// |string
							encodeOr + "leada_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no11
																														// 주연명
																														// |string
							encodeOr + "kind_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no12
																													// 종류
																													// |string
							encodeOr + "hope_grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no13 희망등급 |string
							encodeOr + "prod_year" + encodeAns + strMark + encodeSearchStr + strMark + // no14
																										// 제작년도
																										// |string
																										// like
																										// X
							encodeOr + "aplc_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no15
																													// 신청회사
																													// |string
							encodeOr + "rt_no" + encodeAns + strMark + encodeSearchStr + strMark + // no69
																									// 등급분류번호
																									// |string
																									// like
																									// X
							encodeOr + "grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no71
																														// 결정등급
																														// |string
							encodeOr + "comb_nm" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no23
																													// 위원명
																													// |string
							encodeOr + "synt_indvdl_div" + encodeAns + strMark + encodeSearchStr + strMark + // no25
																												// 종합
																												// 개별
																												// 구분
																												// |string
																												// like
																												// X
							encodeOr + "proc_state" + encodeAns + strMark + encodeSearchStr + strMark + // no30
																										// 진행
																										// 상태
																										// |string
																										// like
																										// X
							encodeOr + "exprt_part_div" + encodeAns + strMark + encodeSearchStr + strMark + // no32
																											// 전문위원
																											// 조
																											// 구분
																											// |string
																											// like
																											// X
							encodeOr + "opin_grade_nm1" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no91
																															// 주제
																															// 등급
																															// |string
							encodeOr + "opin_grade_nm2" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no94
																															// 선정성
																															// 등급
																															// |string
							encodeOr + "opin_grade_nm3" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no97
																															// 폭력성
																															// 등급
																															// |string
							encodeOr + "opin_grade_nm4" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no100
																															// 대사
																															// 등급
																															// |string
							encodeOr + "opin_grade_nm5" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no103
																															// 공포
																															// 등급
																															// |string
							encodeOr + "opin_grade_nm6" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no106
																															// 약물
																															// 등급
																															// |string
							encodeOr + "opin_grade_nm7" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no109
																															// 모방위험
																															// 등급
																															// |string
							encodeOr + "rt_core_harm_rsn_code1" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + encodeOr + "rt_core_harm_rsn_code2" + encodeLike + strMark + "*"
							+ encodeSearchStr + "*" + strMark + encodeOr + "rt_core_harm_rsn_code3" + encodeLike
							+ strMark + "*" + encodeSearchStr + "*" + strMark +

							// 텍스트타입
							encodeOr + "exprt_synt_opin" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + // no41 전문위원 종합 의견 |text
							encodeOr + "major_opin_narrn_cont" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + // no44 주요 의견 서술 내용 |text
							encodeOr + "core_harm_rsn" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + // no46 핵심유해사유 |text
							encodeOr + "work_cont" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + // no47
																															// 작품내용
																															// |text
							encodeOr + "deter_basis" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ // no48 결정근거 |text
							encodeOr + "deter_opin_nm" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + // no52 결정의견 |text
							encodeOr + "deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + // no54
																															// 결정사유
																															// |text
							encodeOr + "reslt_noti_deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + // no58 결과 통보 결정 사유 |text
							encodeOr + "opin1" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ encodeOr + "opin2" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ encodeOr + "opin3" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ encodeOr + "opin4" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ encodeOr + "opin5" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ encodeOr + "opin6" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ encodeOr + "opin7" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation;

					/************ 새로 만들어야하는 컬럼 **************/

					// int는 encode를 따로 안해줘도 무방함.
					if (searchStrInt != 0) {
						searchCol += encodeOr + "rcv_date" + encodeAns + searchStrInt + "000000" + // no6
																									// 접수일자
																									// |
																									// int(date)
								encodeOr + "rt_date" + encodeAns + searchStrInt + "000000" + // no70
																								// 등급분류일
																								// |
																								// int(date)
								encodeOr + "reg_dt" + encodeAns + searchStrInt + "000000"; // no87
																							// 등록
																							// 일시
																							// |
																							// int(date)
					}

					// 통합검색의 마무리 괄호
					searchCol += ")";

					// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
					// 1. 원제명Url (텍스트지만, 중요도에 따라 맨위에 위치)
					searchCol_ori_title = "(" + "ori_title" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + "synonym('d0')" + ")";

					// 2. 사용제명Url (텍스트지만, 중요도에 따라 맨위에 위치)
					searchCol_use_title = "(" + "use_title" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + "synonym('d0')" + ")";

					searchCol_prodc_natnl_name = "(" + "prodc_natnl_name" + encodeAns + strMark + encodeSearchStr
							+ strMark + ")";

					// 3. 접수번호Url
					searchCol_rcv_no_view = "(" + "rcv_no_view" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 4. 매체명Url
					searchCol_medi_name = "(" + "medi_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";

					// 5. 제작사명Url
					searchCol_prodc_name = "(" + "prodc_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";

					// 6. 감독명Url
					searchCol_dire_name = "(" + "dire_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 10. 영화 종류
					searchCol_mv_asso_name = "(" + "mv_asso_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 11. 주연명
					searchCol_leada_name = "(" + "leada_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 12. 종류
					searchCol_kind_name = "(" + "kind_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 13. 희망등급
					searchCol_hope_grade_name = "(" + "hope_grade_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 14. 제작년도
					searchCol_prod_year = "(" + "prod_year" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 15. 신청회사
					searchCol_aplc_name = "(" + "aplc_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 16. 등급분류번호
					searchCol_rt_no = "(" + "rt_no" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 17. 결정등급
					searchCol_grade_name = "(" + "grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";

					/**************** 매체에서 추가한것들 ****************************/
					// 18. 위원명
					searchCol_comb_nm = "(" + "comb_nm" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ ")";
					// 19. 종합 개별 구분
					searchCol_synt_indvdl_div = "(" + "synt_indvdl_div" + encodeAns + strMark + encodeSearchStr
							+ strMark + ")";
					// 20. 진행 상태
					searchCol_proc_state = "(" + "proc_state" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 21. 전문위원 조 구분
					searchCol_exprt_part_div = "(" + "exprt_part_div" + encodeAns + strMark + encodeSearchStr + strMark
							+ ")";
					// 22. 주제 등급
					searchCol_opin_grade_nm1 = "(" + "opin_grade_nm1" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 23. 선정성 등급
					searchCol_opin_grade_nm2 = "(" + "opin_grade_nm2" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 24. 폭력성 등급
					searchCol_opin_grade_nm3 = "(" + "opin_grade_nm3" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 25. 대사 등급
					searchCol_opin_grade_nm4 = "(" + "opin_grade_nm4" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 26. 공포 등급
					searchCol_opin_grade_nm5 = "(" + "opin_grade_nm5" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 27. 약물 등급
					searchCol_opin_grade_nm6 = "(" + "opin_grade_nm6" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 28. 모방위험 등급
					searchCol_opin_grade_nm7 = "(" + "opin_grade_nm7" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";

					/******* 텍스트 *********/
					// 29. 주요 의견 서술 내용
					searchCol_major_opin_narrn_cont = "(" + "major_opin_narrn_cont" + encodeAns + strMark
							+ encodeSearchStr + strMark + searchOperation + ")";
					// 30. 핵심유해사유
					searchCol_core_harm_rsn = "(" + "core_harm_rsn" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + ")";
					// 31. 작품내용
					searchCol_work_cont = "(" + "work_cont" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + ")";
					// 32. 결정사유
					searchCol_deter_rsn = "(" + "deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + ")";
					// 33. 전문위원 종합 의견
					searchCol_exprt_synt_opin = "(" + "exprt_synt_opin" + encodeAns + strMark + encodeSearchStr
							+ strMark + searchOperation + ")";
					// 34. 결정근거
					searchCol_deter_basis = "(" + "deter_basis" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + ")";
					// 35. 결정의견
					searchCol_deter_opin_nm = "(" + "deter_opin_nm" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + ")";
					// 36. 결과 통보 결정 사유
					searchCol_reslt_noti_deter_rsn = "(" + "reslt_noti_deter_rsn" + encodeAns + strMark
							+ encodeSearchStr + strMark + searchOperation + ")";

					/*** 검색어가 숫자인 경우 추가되는 항목!! ***/
					if (searchStrInt != 0) {

						// 37. 접수일자
						searchCol_rcv_date = "(" + "rcv_date" + encodeAns + searchStrInt + "000000" + ")";
						// 38. 등급분류일
						searchCol_rt_date = "(" + "rt_date" + encodeAns + searchStrInt + "000000" + ")";
						// 39. 등록 일시
						searchCol_reg_dt = "(" + "reg_dt" + encodeAns + searchStrInt + "000000" + ")";

					}

				}
				if (searchGubun.equals("title")) {
					searchCol += "(use_title" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ "synonym('d0')" + encodeOr + "ori_title" + encodeAns + strMark + encodeSearchStr
							+ strMark + searchOperation + "synonym('d0')" + ")";
				}
				if (searchGubun.equals("decision")) {
					searchCol += "(deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ "synonym('d0')" + encodeOr + "deter_basis" + encodeAns + strMark + encodeSearchStr
							+ strMark + searchOperation + "synonym('d0')" + ")";
				}
				// 검색어가 빈값일때 (new opin이라 생략)
				// if(searchStr.equals("NO_VALUE")){

				
				//new opin
				// 상세검색 일때만 뒤에 이걸 붙여주자!
				/**************** 일자 통합작업중 ***********************/
				if (detail_visibleChk.equals("Y")) {
					// 일자보다 신청회사 먼저 붙여준다.
					if (!(detailAplc.equals("") || detailAplc == null)) {
						detailAplc = URLEncoder.encode(detailAplc, "UTF-8");
						// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
						searchCol += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*" + detailAplc
								+ "*" + strMark + encodeRGual;
						searchCol_rcv_no_view += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_medi_name += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_prodc_name += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_prodc_natnl_name += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						;
						searchCol_dire_name += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_mv_asso_name += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_leada_name += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_kind_name += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_hope_grade_name += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_prod_year += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_aplc_name += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_rt_no += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_grade_name += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_comb_nm += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_synt_indvdl_div += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_proc_state += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_exprt_part_div += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_opin_grade_nm1 += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_opin_grade_nm2 += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_opin_grade_nm3 += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_opin_grade_nm4 += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_opin_grade_nm5 += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_opin_grade_nm6 += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_opin_grade_nm7 += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_major_opin_narrn_cont += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark
								+ "*" + detailAplc + "*" + strMark + encodeRGual;
						searchCol_core_harm_rsn += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;

						// 텍스트타입
						searchCol_ori_title += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_use_title += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_major_opin_narrn_cont += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark
								+ "*" + detailAplc + "*" + strMark + encodeRGual;
						searchCol_core_harm_rsn += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_work_cont += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_deter_rsn += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_exprt_synt_opin += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_deter_basis += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_deter_opin_nm += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
								+ detailAplc + "*" + strMark + encodeRGual;
						searchCol_reslt_noti_deter_rsn += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark
								+ "*" + detailAplc + "*" + strMark + encodeRGual;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_rcv_date += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
									+ detailAplc + "*" + strMark + encodeRGual;
							searchCol_rt_date += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
									+ detailAplc + "*" + strMark + encodeRGual;
							searchCol_reg_dt += encodeAnd + encodeLGual + "aplc_name" + encodeLike + strMark + "*"
									+ detailAplc + "*" + strMark + encodeRGual;
						}

					}

					String detail_date = "";
					if (!period_start.equals("") && !period_end.equals("")) {
						if (detail_dateGubun.equals("regdate")) {
							searchCol += encodeAnd + encodeLGual + "sub_rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 신청일자
							detail_date = encodeAnd + encodeLGual + "sub_rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;
						} else if (detail_dateGubun.equals("jubsudate")) {
							searchCol += encodeAnd + encodeLGual + "rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 접수일자
							detail_date = encodeAnd + encodeLGual + "rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;
						} else {
							searchCol += encodeAnd + encodeLGual + "rt_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 등급분류일자
							detail_date = encodeAnd + encodeLGual + "rt_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;
						}

						// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
						searchCol_dire_name += detail_date;
						searchCol_rcv_no_view += detail_date;
						searchCol_medi_name += detail_date;
						searchCol_prodc_name += detail_date;
						searchCol_prodc_natnl_name += detail_date;
						searchCol_mv_asso_name += detail_date;
						searchCol_leada_name += detail_date;
						searchCol_kind_name += detail_date;
						searchCol_hope_grade_name += detail_date;
						searchCol_prod_year += detail_date;
						searchCol_aplc_name += detail_date;
						searchCol_rt_no += detail_date;
						searchCol_grade_name += detail_date;
						searchCol_comb_nm += detail_date;
						searchCol_synt_indvdl_div += detail_date;
						searchCol_proc_state += detail_date;
						searchCol_exprt_part_div += detail_date;
						searchCol_opin_grade_nm1 += detail_date;
						searchCol_opin_grade_nm2 += detail_date;
						searchCol_opin_grade_nm3 += detail_date;
						searchCol_opin_grade_nm4 += detail_date;
						searchCol_opin_grade_nm5 += detail_date;
						searchCol_opin_grade_nm6 += detail_date;
						searchCol_opin_grade_nm7 += detail_date;
						searchCol_major_opin_narrn_cont += detail_date;
						searchCol_core_harm_rsn += detail_date;

						// 텍스트타입
						searchCol_ori_title += detail_date;
						searchCol_use_title += detail_date;
						searchCol_major_opin_narrn_cont += detail_date;
						searchCol_core_harm_rsn += detail_date;
						searchCol_work_cont += detail_date;
						searchCol_deter_rsn += detail_date;
						searchCol_exprt_synt_opin += detail_date;
						searchCol_deter_basis += detail_date;
						searchCol_deter_opin_nm += detail_date;
						searchCol_reslt_noti_deter_rsn += detail_date;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_rcv_date += detail_date;
							searchCol_rt_date += detail_date;
							searchCol_reg_dt += detail_date;
						}
					}

				}
				/**************** 상세검색의 일자, 신청회사 통합작업중 ***********************/

				/**************
				 * ■■■■■■■■■■■■■*************************************우측사이드에 관한
				 * information 시작
				 * *************************************************■■■■■■■■■
				 **************************************************/
				List<Map<String, Object>> opin01_sideInformation = new ArrayList<Map<String, Object>>();
				if (searchGubun.equals("All")) {
					// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
					// 1. (조건에 맞는)원제명에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_ori_title, "원제명"));

					// 2. (조건에 맞는)사용제명에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_use_title, "사용제명"));

					// 3. (조건에 맞는)접수번호에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_rcv_no_view, "접수번호"));

					// 4. (조건에 맞는)매체명에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_medi_name, "매체명"));

					// 5. (조건에 맞는)제작사명에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_prodc_name, "제작사명"));

					// 6. (조건에 맞는)제작사 국적에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_prodc_natnl_name, "제작사 국적"));

					// 7. (조건에 맞는)감독명에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_dire_name, "감독명"));

					// 8. (조건에 맞는)영화 종류에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_mv_asso_name, "영화 종류"));

					// 9. (조건에 맞는)주연명에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_leada_name, "주연명"));

					// 10. (조건에 맞는)종류에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_kind_name, "종류"));

					// 11. (조건에 맞는)희망등급에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_hope_grade_name, "희망등급"));

					// 12. (조건에 맞는)제작년도에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_prod_year, "제작년도"));

					// 13. (조건에 맞는)신청회사에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_aplc_name, "신청회사"));

					// 14. (조건에 맞는)등급분류번호 에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_rt_no, "등급분류번호"));

					// 15. (조건에 맞는)결정등급에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_grade_name, "결정등급"));

					// 16. (조건에 맞는)위원명에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_comb_nm, "위원명"));

					// 17 (조건에 맞는)종합 개별 구분에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_synt_indvdl_div, "종합 개별 구분"));

					// 18 (조건에 맞는)진행 상태에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_proc_state, "진행 상태"));

					// 19 (조건에 맞는)전문위원 조 구분에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_exprt_part_div, "전문위원 조 구분"));

					// 20 (조건에 맞는)주제 등급에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_opin_grade_nm1, "주제 등급"));

					// 21 (조건에 맞는)선정성 등급에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_opin_grade_nm2, "선정성 등급"));

					// 22 (조건에 맞는)폭력성 등급에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_opin_grade_nm3, "폭력성 등급"));

					// 23 (조건에 맞는)대사 등급에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_opin_grade_nm4, "대사 등급"));

					// 24 (조건에 맞는)공포 등급에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_opin_grade_nm5, "공포 등급"));

					// 25 (조건에 맞는)약물 등급에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_opin_grade_nm6, "약물 등급"));

					// 26 (조건에 맞는)모방위험 등급에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_opin_grade_nm7, "모방위험 등급"));

					// 텍스트 타입 시작
					// 27. (조건에 맞는)주요 의견 서술 내용 에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_major_opin_narrn_cont, "주요 의견 서술 내용"));

					// 28. (조건에 맞는)핵심유해사유에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_core_harm_rsn, "핵심유해사유"));

					// 29. (조건에 맞는)작품내용에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_work_cont, "작품내용"));

					// 30. (조건에 맞는)결정사유에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_deter_rsn, "결정사유"));

					// 31 (조건에 맞는)전문위원 종합 의견에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_exprt_synt_opin, "전문위원 종합 의견"));

					// 32 (조건에 맞는)결정근거에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_deter_basis, "결정근거"));

					// 33 (조건에 맞는)결정의견에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_deter_opin_nm, "결정의견"));

					// 34 (조건에 맞는)결과 통보 결정 사유에 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol_reslt_noti_deter_rsn, "결과 통보 결정 사유"));
					/**************** 텍스트 데이터 끝!!! ******************/

					/**************** 숫자 데이터 시작 (null처리)!!! ******************/

					if (searchStrInt != 0) {
						// 35 (조건에 맞는)접수일자에 대한 건수 및 데이터 조회
						opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response,
								result_opinUrl, orderValue, searchCol_rcv_date, "접수일자"));
						// 36 (조건에 맞는)등급분류일에 대한 건수 및 데이터 조회
						opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response,
								result_opinUrl, orderValue, searchCol_rt_date, "등급분류일"));
						// 37 (조건에 맞는)등록 일시에 대한 건수 및 데이터 조회
						opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response,
								result_opinUrl, orderValue, searchCol_reg_dt, "등록 일시"));
					}
					/**************** 숫자 데이터 끝 (null처리)!!! ******************/

					Collections.sort(opin01_sideInformation, new sideMenuCntComparator());
				} // if(searchGubun.equals("All")) 끝
				if (searchGubun.equals("title")) {
					// (조건에 맞는)원제명 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol, "원제명/사용제명"));
				}
				if (searchGubun.equals("decision")) {
					// (조건에 맞는)결정사유 대한 건수 및 데이터 조회
					opin01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_opinUrl,
							orderValue, searchCol, "결정사유"));
				}
				// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
				walker.setValue("opin01_sideInformation", opin01_sideInformation);

				/**************
				 * ■■■■■■■■■■■■■***************************************우측사이드에 관한
				 * information
				 * 끝***********************************************■■■■■■■■■
				 **************************************************/

				/*************************************************************
				 * 검색어 처리 끝
				 *************************************************************************************************/

				totalUrl = result_opinUrl + "+aliasing+use_title+as+use1+" + searchCol + orderValue;
				// 결과내 재검색이라면 ? 시작
				if (((String) paramMap.get("result_re_search_chk")).equals("Y")
						&& (String) paramMap.get("opin01_historySearchQuery") != null) {
					totalUrl = (String) paramMap.get("opin01_historySearchQuery") + encodeAnd + "(" + searchCol + ")"
							+ orderValue;
				}
				// 결과내 재검색이라면 ? 끝

				// 이전쿼리 저장 시작
				String historySearchQuery = "";
				historySearchQuery = result_opinUrl + "+aliasing+use_title+as+use1+" + "(" + searchCol + ")";
				// 만약 결과내재검색을 통한 검색이었다면, historySearchQuery(이전쿼리)는 , 이전쿼리 +
				// encodeAnd + 현재쿼리 일 것이다.
				if (((String) paramMap.get("result_re_search_chk")).equals("Y")
						&& (String) paramMap.get("opin01_historySearchQuery") != null) {
					historySearchQuery = (String) paramMap.get("opin01_historySearchQuery") + encodeAnd + "("
							+ searchCol + ")";
				}
				paramMap.put("opin01_historySearchQuery", historySearchQuery);
				// 이전쿼리 저장 끝
			} else {
				// 검색어가 공백이라면? result_opin에 관한 모든 데이터 조회!
				totalUrl = result_opinUrl + orderValue;
			}

			/*************************************************************
			 * 얻어낸 Url 결과물로 추가적인 작업시작 / 페이징 처리 시작
			 *************************************************************************************************/
			String paging_totalUrl = totalUrl + "&pagelength=" + detailresultLine + "&offset=" + offset + "&limit="
					+ detailresultLine;

			List<Object> dataList = pagingURLEncodingFunc(paging_totalUrl);
			opin01.put("total_count_page", (Integer) dataList.get(0));
			walker.setValue("opin01_resultMapList", (List<Map<String, String>>) dataList.get(1));
			/*************************************************************
			 * 얻어낸 Url 결과물로 추가적인 작업시작 / 페이징 처리 시작
			 *************************************************************************************************/

			// totalURLCntFunc로 총합 갯수만 가져오고있다. -> 데이터가 필요하다면 해당 메소드를 안에
			// resultMap을 확인하면 됨.
			Integer total_count = totalURLCntFunc(totalUrl);
			opin01.put("total_count", total_count);
			walker.setValue("opin01", opin01);

		}
		/////////////////////////////////////////// new등급분류의견서 끝
		/////////////////////////////////////////// ()///////////////////////////////////////////////////////////////////////////////////////////////////
	}

	/**
	 * 
	 * @param walker
	 * @param paramMap
	 * @param request
	 * @param response
	 * @throws Exception
	 *             모니터링의견서에 대한 데이터 추출하는 메소드
	 */
	public void moni01(Walker walker, Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// null처리는 필수!
		// 파라미터 목록 가져오기. * 검색어, 검색구분, 섹션구분
		String searchStr = (String) paramMap.get("searchStr");
		String searchGubun = (String) paramMap.get("searchGubun");
		String[] sectionGubun = null;
		if (request.getParameterValues("sectionGubun") == null) {
			sectionGubun = new String[] { "Total" };
		} else {
			sectionGubun = request.getParameterValues("sectionGubun");
		}
		List<String> sectionGubunList = new ArrayList<String>(Arrays.asList(sectionGubun));

		/*************** 검색어에 숫자가 들어올떄 *******************************/
		long searchStrInt = 0;
		if (paramMap.get("searchStr") != null && ((String) paramMap.get("searchStr")).matches("[-+]?\\d*\\.?\\d+")) {
			searchStrInt = Long.valueOf((String) paramMap.get("searchStr"));
		}
		/*************** 검색어에 숫자가 들어올떄 *******************************/
		// 최종 URL
		String totalUrl = "";
		// 공통 URL
		String result_noticeUrl = "http://192.168.11.242:7577/search5?select=*&from=result_notice.result_notice&where=";
		String result_opinUrl = "http://192.168.11.242:7577/search5?select=*&from=result_opin.result_opin&where=";
		String result_fileUrl = "http://192.168.11.242:7577/search5?select=*&from=result_file.result_file&where=";
		String result_imageUrl = "http://192.168.11.242:7577/search5?select=*&from=result_image.result_image&where=";
		String result_moniUrl = "http://192.168.11.242:7577/search5?select=*&from=result_moni.result_moni&where=";
		String result_surveyUrl = "http://192.168.11.242:7577/search5?select=*&from=result_survey.result_survey&where=";
		// 공통 인코딩 목록
		String encode = "UTF-8";
		String encodeSearchStr = URLEncoder.encode(searchStr, encode); // 검색어
		String encodeSearchGubun = URLEncoder.encode(searchGubun, encode); // 검색구분(통합검색,
																			// 제목,
																			// 내용,
																			// 결정사유
																			// 등등)
		String encodeAnd = URLEncoder.encode(" and ", encode);
		String encodeOr = URLEncoder.encode(" or ", encode);
		String encodeAns = URLEncoder.encode("=", encode);
		String encodeInOpen = URLEncoder.encode(" in{", encode);
		String encodeInClose = URLEncoder.encode("}", encode);
		String encodeLGual = "(";
		String encodeRGual = ")";
		String encodeLike = URLEncoder.encode(" like ", encode);
		String strMark = URLEncoder.encode("'", encode);
		String between = URLEncoder.encode(" between ", encode);

		// where절의 text타입에만 사용가능.
		String allword = URLEncoder.encode(" allword ", encode);
		String anyword = URLEncoder.encode(" anyword ", encode);
		String natural = URLEncoder.encode(" natural ", encode);

		// 동의어 검색
		String synonym = URLEncoder.encode(" synonym ", encode);

		// 카테고리 정렬 ex) ORDER BY $CATEGORYFIELD(field_name(domain), keyword)
		String categoryOrder = URLEncoder.encode(" $categoryfield(use_title(), 검색어넣기 ", encode);

		/**
		 * 17501 : 국내영화 17502 : 국외영화 17505 : 광고영화 17511 : 국내비디오 17512 : 국외비디오
		 */

		// medi_code 인코딩 목록
		String medi_mv = URLEncoder.encode("'17501','17502'", encode);
		String medi_ad = URLEncoder.encode("'17503','17504','17505','17506','17514'", encode);
		String medi_video = URLEncoder.encode("'17511','17522'", encode);
		String medi_perform = URLEncoder.encode("'17521','17522','17523','17524'", encode);

		/*
		 * //첫 로딩 시 데이터를 표시해주는 역할을 함. if(searchGubun == null ){ searchGubun =
		 * "All"; } else if(searchGubun.trim().isEmpty()){ searchGubun = "All";
		 * }
		 */

		// 부가설명 : 처음 orderValue로 JSP에서 넘어온 정렬값을 받아서, 넘어온 정렬값을 기준으로 url인코딩을 실시한다.
		// 특이사항: 이 매체는 orderGa 조건일때 use_title_sort만 있는 이유는 use_title만 text타입이라서
		// 정렬에 필요한 use_title_sort이라는 string타입이 필요했던것. 즉, 다른 매체와 다르게 ori_title이
		// text타입이 아니라는 의미.
		String orderValue = (String) request.getParameter("orderValue");
		if (orderValue == null || orderValue.equals("")) {
			orderValue = URLEncoder.encode(" order by $match(rt_no) desc, $relevance desc, $rowid desc ", encode);
		}
		if (orderValue.equals("orderRelevance")) {
			orderValue = URLEncoder.encode(" order by $match(rt_no) desc, $relevance desc, $rowid desc ", encode);
		}
		if (orderValue.equals("orderGa")) {
			orderValue = "%20order%20by%20use_title_sort%20asc%20";
		}
		if (orderValue.equals("orderDate")) {
			orderValue = "%20order%20by%20rcv_date%20desc%20";
		}

		//
		String groupByTest = URLEncoder.encode(" order by $relevance desc ", encode);

		// 2022 12 15 페이징 처리 시작

		// 1. sectionGubun을 하나만 선택하거나, -더보기 클릭 시, sectionGubun에는 딱 하나의 데이터가
		// 들어있고, 페이지는 1페이지로 이동한다. -->sectionGubun이 딱 하나라면 (Total)이 아닐떄 , 페이징목록이
		// 보이게끔 유도하기.
		// 페이지는 페이지 목록 버튼 시 마다 2페이지 3페이지 이동한다... 그러다가 다시 검색버튼 같은것을 누르면 1페이지로
		// 초기화!

		// 기존 display가 limit로 대체

		int cPage = 0;
		int offset = 0;
		int pagelength = 0;

		/**************************
		 * 20221223 페이징처리를 위한 새로운 로직 (영화, 비디오 반영완료)
		 *********************************************/
		if (((String) paramMap.get("isPaging")).equals("Y")) {
			cPage = (Integer) paramMap.get("cPage");
			offset = (Integer) paramMap.get("offset");
			pagelength = (Integer) paramMap.get("pagelength");
			offset = (cPage - 1) * pagelength;
		} else {
			cPage = 1;
			offset = 0;
			pagelength = 10;
			offset = (cPage - 1) * pagelength;
		}

		/*******************************************************************/

		/**************** 상세보기 시작 ********************************/
		// 20221216 상세보기에 관련된 것들 맨 위로 빼기 (기본값이 존재함)
		// 하지만 상세보기를 하지 않는다면 이 값이 null이라서 오류가뜸 ~~~~~~~~~~~~~~~~~~~~~~~~~~~

		// 상세보기를 클릭했는지 여부
		String detail_visibleChk = (String) paramMap.get("detail_visibleChk");

		// 연산자 가져오기
		String searchOperation = "";

		// 결과 수
		int detailresultLine = 3;
		try {
			if ((String) paramMap.get("detailSearch_resultLine") != null) {
				detailresultLine = Integer.valueOf((String) paramMap.get("detailSearch_resultLine"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (detailresultLine != 3 && (String) paramMap.get("detailSearch_resultLine") != null) {
				detailresultLine = detailresultLine;
			} else {
				detailresultLine = 3;
			}
		}

		try {
			if (detail_visibleChk.equals("Y")) {
				searchOperation = (String) paramMap.get("detailSearch_radio");
			} else {
				searchOperation = "natural";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_radio") == null) {
				searchOperation = "natural";
			}
		}

		if (searchOperation.equals("allword")) {
			searchOperation = natural;
		} else if (searchOperation.equals("anyword")) {
			searchOperation = natural;
		} else if (searchOperation.equals("natural")) {
			searchOperation = natural;
		} else {
			searchOperation = natural;
		}
		
		if(searchStr.indexOf("-") != -1){
			searchOperation = allword;
		}

		// 신청회사 null 처리
		String detailAplc = "";
		try {
			if ((String) paramMap.get("detailSearch_aplc") != null) {
				detailAplc = (String) paramMap.get("detailSearch_aplc");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_aplc") == null) {
				detailAplc = "";
			}
		}

		// 상세보기-일자 데이터 가져오기 및 null 처리 시작

		// 시작날짜
		String period_start = "";
		try {
			if ((String) paramMap.get("period_start") != null) {
				period_start = (String) paramMap.get("period_start");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("period_start") == null) {
				period_start = "";
			}
		}

		// 종료날짜
		String period_end = "";
		try {
			if ((String) paramMap.get("period_end") != null) {
				period_end = (String) paramMap.get("period_end");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("period_end") == null) {
				period_end = "";
			}
		}

		// 등급분류일자, 신청일자 , 접수일자 구분
		String detail_dateGubun = "";
		try {
			if ((String) paramMap.get("detailSearch_date") != null) {
				detail_dateGubun = (String) paramMap.get("detailSearch_date");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ((String) paramMap.get("detailSearch_date") == null) {
				detail_dateGubun = "gradedate";
			}
		}

		if (!period_start.equals("")) {
			period_start = period_start.substring(0, 4) + period_start.substring(5, 7) + period_start.substring(8, 10)
					+ "000000";
		}
		if (!period_end.equals("")) {
			period_end = period_end.substring(0, 4) + period_end.substring(5, 7) + period_end.substring(8, 10)
					+ "000000";
		}

		// 듈 중에 하나를 선택안하고 상세검색을 하면 --> 전체날짜로 인식하여 데이터검색하기
		if ((period_start.equals("") || period_end.equals("")) && !(period_start.equals("") && period_end.equals(""))) {
			period_start = "";
			period_end = "";

		}
		// 상세보기-일자 데이터 가져오기 및 null 처리 끝
		
		
		//따옴표 기능 추가 시작

				//따옴표기능 변수1 : 반드시 포함해야 하는 따옴표 안의 단어.
				String quote_searchStr_origin = "";
				//따옴표기능 변수2 : 반드시 포함해야 하는 따옴표 안의 단어 (인코딩 받아줄 변수).
				String quote_searchStr = ""; 
				
				

				//따옴표기능 로직1 : 따옴표의 인덱스 위치를 추출한다.
				String word = "\""; 
				int searchStrIndex = searchStr.indexOf(word);
				List<Integer> searchStrIndexList = new ArrayList<Integer>();
				 while(searchStrIndex != -1) {
					 searchStrIndexList.add(searchStrIndex);
					 searchStrIndex = searchStr.indexOf(word, searchStrIndex+word.length()); 
				 }
				
				 
				 
				//따옴표기능 로직2 : 따옴표의 인덱스가 2개이상 나왔다면, 따옴표가 4개있든 7개 있든, 제일 첫번째로 나오는 따옴표 2개의 쌍을 구하고, 그 따옴표로 깜사져 있는 단어를 추출한다.
				if(searchStrIndexList.size() >= 2 ) {
					 int firstIndex = searchStrIndexList.get(0);
					 int lastIndex = searchStrIndexList.get(1);
					 quote_searchStr_origin = searchStr.substring(firstIndex + 1, lastIndex);  
					 quote_searchStr = URLEncoder.encode(quote_searchStr_origin, encode);
				 }
				
				 
				 
				 
				 //따옴표기능 로직3 : 따옴표의 인덱스가 일단 하나 이상 등장한다면, 한개 이상일 경우는 따옴표를 제거해주고, 따옴표가 2개이상인 경우에는 위에서 얻어온 '따옴표 안'의 단어를 따로 추출하여 인코딩한다.
				             // --> 차후에 따옴표 검색이 이뤄질때, 헤어질 결심 "수사관" 검색 시, --> (헤어질 결심 ) AND ("수사관") 이런식으로 검색 하게끔 해줌.
				 if(searchStrIndexList.size() >= 1 ){
					 
					String searchStr2 = searchStr.replaceAll("\"", "");
					String searchStr3 = searchStr2.replaceAll( quote_searchStr_origin , "").trim(); 
					if(!searchStr3.equals("") && searchStr3 != null){
						encodeSearchStr = URLEncoder.encode(searchStr3, encode); // 검색어
					}
				 }
				 
				 
				 
				 // 따옴표에 들어간 단어에 맞춰서 조건을 추가해주기 위해서 셋팅하는 작업 
				 String quote_searchCol = "";
				 if(!quote_searchStr.equals("")) {
		    /***********************************************img01() quote_searchStr start*************************************************************************/	 
				 quote_searchCol += encodeAnd + "(" + "use_title" + encodeAns + strMark + "%22" +  quote_searchStr  + "%22" + strMark + "+allword+" + "synonym('d0')" 
						 + encodeOr + "rcv_no_view" + encodeAns + strMark + quote_searchStr + strMark  // no2 접수번호
						 + encodeOr + "medi_name" + encodeAns + strMark + quote_searchStr + strMark 
						 + encodeOr + "ori_title" + encodeAns + strMark + quote_searchStr + strMark 
						 + encodeOr + "prodc_natnl_name" + encodeAns + strMark + quote_searchStr + strMark 
						 + encodeOr + "prodc_name" + encodeAns + strMark + quote_searchStr + strMark 
						 + encodeOr + "dire_name" + encodeAns + strMark + quote_searchStr + strMark 
						+ encodeOr + "mv_asso_name" + encodeAns + strMark + quote_searchStr + strMark  
						+ encodeOr + "leada_name" + encodeAns + strMark + "%22" +  quote_searchStr  + "%22" + strMark + "+allword+" + "synonym('d0')" 
						+ encodeOr + "kind_name" + encodeAns + strMark + quote_searchStr + strMark 
						+ encodeOr + "hope_grade_name" + encodeAns + strMark + quote_searchStr + strMark 
						+ encodeOr + "prod_year" + encodeAns + strMark + quote_searchStr + strMark 
						+ encodeOr + "aplc_name" + encodeAns + strMark + "%22" +  quote_searchStr  + "%22" + strMark + "+allword+" + "synonym('d0')"  
						+ encodeOr + "rt_no" + encodeAns + strMark + quote_searchStr + strMark 
						+ encodeOr + "grade_name" + encodeAns + strMark + quote_searchStr + strMark 
						+ encodeOr + "user_id"+ encodeAns + strMark + quote_searchStr + strMark 
						+ encodeOr + "user_nm" + encodeAns + strMark + quote_searchStr + strMark 
						+ encodeOr + "proc_state_nm" + encodeAns + strMark + quote_searchStr + strMark 
						+ encodeOr + "probl_state"+ encodeAns + strMark + quote_searchStr + strMark 
						
						// 텍스트타입
						+ encodeOr + "deter_rsn" + encodeAns + strMark + "%22" +  quote_searchStr  + "%22" + strMark + "+allword+" + "synonym('d0')" 
						+ encodeOr + "srvc_path" + encodeAns + strMark + "%22" +  quote_searchStr  + "%22" + strMark + "+allword+" + "synonym('d0')" 
						+ encodeOr + "probl_rsn" + encodeAns + strMark + "%22" +  quote_searchStr  + "%22" + strMark + "+allword+" + "synonym('d0')";

				// int는 encode를 따로 안해줘도 무방함.
				if (searchStrInt != 0) {
					quote_searchCol += encodeOr + "rcv_date" + encodeAns + searchStrInt + "000000" + // no6
																								// 접수일자
																								// |
																								// int(date)
							encodeOr + "rt_date" + encodeAns + searchStrInt + "000000" + // no70
																							// 등급분류일
																							// |
																							// int(date)
							encodeOr + "submt_dt" + encodeAns + searchStrInt + "000000" + encodeOr + "return_dt"
							+ encodeAns + searchStrInt + "000000";
				}
				// 통합검색의 마무리 괄호
				quote_searchCol += ")";
								//quote_searchCol만 셋팅 하고, 왜 다른 사이드는 셋팅 하지 않는지?
								//위에 보다시피 quote_searchCol 이 모든 컬럼에 "따옴표에 들어간 단어"를 매칭시켜서 가져오고있다 
								// 나중에 제일 밑에 detail_visibleChk를 쓰는 로직 밑에 보면, 통합검색이든 사이드메뉴든 뒤에 quote_searchCol를 붙여주는 작업을 하고 있다.
						 }
						 /***********************************************img01() quote_searchStr end*************************************************************************/
						 // 따옴표 기능 추가 끝
		/////////////////////////////////////////// 등급분류의견서 시작
		// ()///////////////////////////////////////////////////////////////////////////////////////////////////
		if (sectionGubunList.contains("moni01") || sectionGubunList.contains("Total")) {
			Map<String, Object> moni01 = new HashMap<String, Object>();

			if (searchStr != null) {
				/*************************************************************
				 * 검색어 처리 시작
				 *************************************************************************************************/

				// 검색구분은 if절로서 쓰이고, 검색어는 where절 안에 쓰임으로써, 직접적인 쿼리에 영향을 줌.
				String searchCol = ""; // where절을 담을 변수
				// text타입
				String searchCol_use_title = "";
				String searchCol_deter_rsn = "";
				String searchCol_srvc_path = "";
				String searchCol_probl_rsn = "";

				// String 타입
				String searchCol_rcv_no_view = "";
				String searchCol_medi_name = "";
				String searchCol_ori_title = "";
				String searchCol_prodc_natnl_name = "";
				String searchCol_prodc_name = "";
				String searchCol_dire_name = "";
				String searchCol_mv_asso_name = "";
				String searchCol_leada_name = "";
				String searchCol_kind_name = "";
				String searchCol_hope_grade_name = "";
				String searchCol_prod_year = "";
				String searchCol_aplc_name = "";
				String searchCol_rt_no = "";
				String searchCol_grade_name = "";
				String searchCol_user_id = "";
				String searchCol_user_nm = "";
				String searchCol_proc_state_nm = "";
				String searchCol_probl_state = "";

				// number 타입
				String searchCol_rcv_date = "";
				String searchCol_rt_date = "";
				String searchCol_submt_dt = "";
				String searchCol_return_dt = "";

				// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
				if (searchGubun.equals("All")) {
					searchCol += "(" + "use_title" + encodeAns + strMark + encodeSearchStr + strMark + "+allword+" + "synonym('d0')" + 
				             encodeOr + "use_title" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + "synonym('d0')" + 
							encodeOr + "rcv_no_view" + encodeAns + strMark
							+ encodeSearchStr + strMark + // no2 접수번호
															// rcv_no_view
															// |string like X
							encodeOr + "medi_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no4
																													// 매체명
																													// |string
							encodeOr + "ori_title" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ encodeOr + "prodc_natnl_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + // no8 제작사 국적 |string
							encodeOr + "prodc_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no7
																														// 제작사명
																														// |string
							encodeOr + "dire_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no9
																													// 감독명
																													// |string
							encodeOr + "mv_asso_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no10
																														// 영화
																														// 종류
																														// |string
							encodeOr + "leada_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + // no11
																														// 주연명
																														// |string
							encodeOr + "kind_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no12
																													// 종류
																													// |string
							encodeOr + "hope_grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ // no13 희망등급 |string
							encodeOr + "prod_year" + encodeAns + strMark + encodeSearchStr + strMark + // no14
																										// 제작년도
																										// |string
																										// like
																										// X
							encodeOr + "aplc_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation +  // no15
																													// 신청회사
																													// |string
							encodeOr + "rt_no" + encodeAns + strMark + encodeSearchStr + strMark + // no69
																									// 등급분류번호
																									// |string
																									// like
																									// X
							encodeOr + "grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no71
																														// 결정등급
																														// |string
							encodeOr + "user_id" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark + // no23
																													// 위원명
																													// |string
							encodeOr + "user_nm" + encodeAns + strMark + encodeSearchStr + strMark + // no25
																										// 종합
																										// 개별
																										// 구분
																										// |string
																										// like
																										// X
							encodeOr + "proc_state_nm" + encodeAns + strMark + encodeSearchStr + strMark + // no30
																											// 진행
																											// 상태
																											// |string
																											// like
																											// X
							encodeOr + "probl_state" + encodeAns + strMark + encodeSearchStr + strMark + // no32
																											// 전문위원
																											// 조
																											// 구분
																											// |string
																											// like
																											// X
							// 텍스트타입
							encodeOr + "deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark + "+someword+" + // no54
																															// 결정사유
																															// |text
							encodeOr + "srvc_path" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ encodeOr + "probl_rsn" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation;

					// int는 encode를 따로 안해줘도 무방함.
					if (searchStrInt != 0) {
						searchCol += encodeOr + "rcv_date" + encodeAns + searchStrInt + "000000" + // no6
																									// 접수일자
																									// |
																									// int(date)
								encodeOr + "rt_date" + encodeAns + searchStrInt + "000000" + // no70
																								// 등급분류일
																								// |
																								// int(date)
								encodeOr + "submt_dt" + encodeAns + searchStrInt + "000000" + encodeOr + "return_dt"
								+ encodeAns + searchStrInt + "000000";
					}

					// 통합검색의 마무리 괄호
					searchCol += ")";

					// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
					// 1. 사용제명Url (텍스트지만, 중요도에 따라 맨위에 위치)
					searchCol_use_title = "(" + "use_title" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + "synonym('d0')" + ")";

					// 2. 원제명Url (String타입)
					searchCol_ori_title = "(" + "ori_title" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 3. 접수번호Url
					searchCol_rcv_no_view = "(" + "rcv_no_view" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 4. 매체명Url
					searchCol_medi_name = "(" + "medi_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";

					// 제작사 국적
					searchCol_prodc_natnl_name = "(" + "prodc_natnl_name" + encodeAns + strMark + encodeSearchStr
							+ strMark + ")";

					// 5. 제작사명Url
					searchCol_prodc_name = "(" + "prodc_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";

					// 6. 감독명Url
					searchCol_dire_name = "(" + "dire_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 10. 영화 종류
					searchCol_mv_asso_name = "(" + "mv_asso_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 11. 주연명
					searchCol_leada_name = "(" + "leada_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + ")";
					// 12. 종류
					searchCol_kind_name = "(" + "kind_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 13. 희망등급
					searchCol_hope_grade_name = "(" + "hope_grade_name" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
					// 14. 제작년도
					searchCol_prod_year = "(" + "prod_year" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 15. 신청회사
					searchCol_aplc_name = "(" + "aplc_name" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation + ")";
					// 16. 등급분류번호
					searchCol_rt_no = "(" + "rt_no" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 17. 결정등급
					searchCol_grade_name = "(" + "grade_name" + encodeLike + strMark + "*" + encodeSearchStr + "*"
							+ strMark + ")";
					// 18. 유저아이디
					searchCol_user_id = "(" + "user_id" + encodeLike + strMark + "*" + encodeSearchStr + "*" + strMark
							+ ")";
					// 19. 유저이름
					searchCol_user_nm = "(" + "user_nm" + encodeAns + strMark + encodeSearchStr + strMark + ")";
					// 20. 진행 상태명
					searchCol_proc_state_nm = "(" + "proc_state_nm" + encodeAns + strMark + encodeSearchStr + strMark
							+ ")";
					// 21. 문제점 상태
					searchCol_probl_state = "(" + "probl_state" + encodeAns + strMark + encodeSearchStr + strMark + ")";

					// 텍스트 타입
					// 32. 결정사유
					searchCol_deter_rsn = "(" + "deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark
							+ "+someword+" + ")";
					// 33. 서비스 경로
					searchCol_srvc_path = "(" + "srvc_path" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + ")";
					// 34. 문제점 사유
					searchCol_probl_rsn = "(" + "probl_rsn" + encodeAns + strMark + encodeSearchStr + strMark
							+ searchOperation + ")";
					/*** 검색어가 숫자인 경우 추가되는 항목!! ***/
					if (searchStrInt != 0) {

						// 37. 접수일자
						searchCol_rcv_date = "(" + "rcv_date" + encodeAns + searchStrInt + "000000" + ")";
						// 38. 등급분류일
						searchCol_rt_date = "(" + "rt_date" + encodeAns + searchStrInt + "000000" + ")";
						// 39. 제출 일시
						searchCol_submt_dt = "(" + "submt_dt" + encodeAns + searchStrInt + "000000" + ")";
						// 39. 반려 일시
						searchCol_return_dt = "(" + "return_dt" + encodeAns + searchStrInt + "000000" + ")";

					}

				}
				if (searchGubun.equals("title")) {
					searchCol += "(use_title" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ "synonym('d0')" + encodeOr + "ori_title" + encodeLike + strMark + "*" + encodeSearchStr
							+ "*" + strMark + ")";
				}
				if (searchGubun.equals("decision")) {
					searchCol += "(deter_rsn" + encodeAns + strMark + encodeSearchStr + strMark + searchOperation
							+ "synonym('d0')" + ")";
				}

				// 검색어가 빈값일 때 (모니터단의견서 매체)
				if (searchStr.equals("NO_VALUE")) {
					searchCol = "ori_title+not+in%7B%7D+";
					searchCol_rcv_no_view = "ori_title+not+in%7B%7D+";
					searchCol_medi_name = "ori_title+not+in%7B%7D+";
					searchCol_ori_title = "ori_title+not+in%7B%7D+";
					searchCol_prodc_name = "ori_title+not+in%7B%7D+";
					searchCol_prodc_natnl_name = "ori_title+not+in%7B%7D+";
					;
					searchCol_dire_name = "ori_title+not+in%7B%7D+";
					searchCol_mv_asso_name = "ori_title+not+in%7B%7D+";
					searchCol_leada_name = "ori_title+not+in%7B%7D+";
					searchCol_kind_name = "ori_title+not+in%7B%7D+";
					searchCol_hope_grade_name = "ori_title+not+in%7B%7D+";
					searchCol_prod_year = "ori_title+not+in%7B%7D+";
					searchCol_aplc_name = "ori_title+not+in%7B%7D+";
					searchCol_rt_no = "ori_title+not+in%7B%7D+";
					searchCol_grade_name = "ori_title+not+in%7B%7D+";
					searchCol_user_id = "ori_title+not+in%7B%7D+";
					searchCol_user_nm = "ori_title+not+in%7B%7D+";
					searchCol_proc_state_nm = "ori_title+not+in%7B%7D+";
					searchCol_probl_state = "ori_title+not+in%7B%7D+";
					// 텍스트타입
					searchCol_use_title = "ori_title+not+in%7B%7D+";
					searchCol_deter_rsn = "ori_title+not+in%7B%7D+";
					searchCol_srvc_path = "ori_title+not+in%7B%7D+";
					searchCol_probl_rsn = "ori_title+not+in%7B%7D+";

					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_rcv_date = "ori_title+not+in%7B%7D+";
						searchCol_rt_date = "ori_title+not+in%7B%7D+";
						searchCol_submt_dt = "ori_title+not+in%7B%7D+";
						searchCol_return_dt = "ori_title+not+in%7B%7D+";
					}
				}

				// 상세검색 일때만 뒤에 이걸 붙여주자!
				/**************** 일자 통합작업중 ***********************/
				if (detail_visibleChk.equals("Y")) {
				///////////////////////////////AND/OR 연산조건 관련 변수 초기화  시작///////////////////////////////////////////////////////////		
					//1. 상세검색 조건내에 선택한  연산자에 따라서 and를 할지 or을 할지 정하는 로직. 
					// encodeTotalChk : 상세조건 내 AND/OR 연산자 무엇을 선택했는지에 대한 값을 지니고 있는 변수 (JSP에서 넘어오는 데이터)
					String encodeTotalChk = (String) paramMap.get("detailSearch_radio");
					// encodeTotal : 상세조건 내 AND/OR 연산자 선택에 따라 And나 Or 값을 지니고 있는 변수
					String encodeTotal = ""; 
					
					if(encodeTotalChk != null){
						if(encodeTotalChk.equals("allword")){
							encodeTotal = encodeAnd;
						} else if(encodeTotalChk.equals("anyword")){
							encodeTotal = encodeOr;
						} 
					} else {
						encodeTotal = encodeAnd;
					}
					
					//2. 상세검색 조건문에 들어오면 AND연산자 해주고,  괄호도  열어주기. (상세검색 조건문 끝날떄쯤 괄호 닫아줄 것)
					// 상세검색 시 이 구문이 기본적으로 실행되며, 구조는 "(기존검색) AND (" 인데, // 여기서 "AND (" 부분을 셋팅해주는 구문이다. 
					searchCol += encodeAnd + encodeLGual;
					searchCol_rcv_no_view += encodeAnd + encodeLGual;
					searchCol_medi_name += encodeAnd + encodeLGual;
					searchCol_ori_title += encodeAnd + encodeLGual;
					searchCol_prodc_name += encodeAnd + encodeLGual;
					searchCol_prodc_natnl_name += encodeAnd + encodeLGual;
					searchCol_dire_name += encodeAnd + encodeLGual;
					searchCol_mv_asso_name += encodeAnd + encodeLGual;
					searchCol_leada_name += encodeAnd + encodeLGual;
					searchCol_kind_name += encodeAnd + encodeLGual;
					searchCol_hope_grade_name += encodeAnd + encodeLGual;
					searchCol_prod_year += encodeAnd + encodeLGual;
					searchCol_aplc_name += encodeAnd + encodeLGual;
					searchCol_rt_no += encodeAnd + encodeLGual;
					searchCol_grade_name += encodeAnd + encodeLGual;
					searchCol_user_id += encodeAnd + encodeLGual;
					searchCol_user_nm += encodeAnd + encodeLGual;
					searchCol_proc_state_nm += encodeAnd + encodeLGual;
					searchCol_probl_state += encodeAnd + encodeLGual;
					// 텍스트타입
					searchCol_use_title += encodeAnd + encodeLGual;
					searchCol_deter_rsn += encodeAnd + encodeLGual;
					searchCol_srvc_path += encodeAnd + encodeLGual;
					searchCol_probl_rsn += encodeAnd + encodeLGual;

					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_rcv_date += encodeAnd + encodeLGual;
						searchCol_rt_date += encodeAnd + encodeLGual;
						searchCol_submt_dt += encodeAnd + encodeLGual;
						searchCol_return_dt += encodeAnd + encodeLGual;
					}
					
					
					
					
					
					
					//첫번째 조건인지 아닌지 확인하는 변수
					int firstConditionChk = 0;
			///////////////////////////////AND/OR 연산조건 관련 변수 초기화  끝///////////////////////////////////////////////////////////
					
					
					//moni01
					// 일자보다 신청회사 먼저 붙여준다.
					if (!(detailAplc.equals("") || detailAplc == null)) {
						// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
						detailAplc = URLEncoder.encode(detailAplc, "UTF-8");
						// 첫번쨰 조건 체크 ------------- 시작 
						String chkResult = "";
						if(firstConditionChk == 0){
							firstConditionChk++;
						} else {
							chkResult = encodeTotal;
						}
						// 첫번쨰 조건 체크 ------------- 끝
						
						
						
						
						searchCol += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_rcv_no_view += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_medi_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_ori_title += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_prodc_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_prodc_natnl_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_dire_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_mv_asso_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_leada_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_kind_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_hope_grade_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_prod_year += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_aplc_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_rt_no += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_grade_name += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_user_id += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_user_nm += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_proc_state_nm += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_probl_state += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						// 텍스트타입
						searchCol_use_title += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_deter_rsn += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_srvc_path += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						searchCol_probl_rsn += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_rcv_date += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
							searchCol_rt_date += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
							searchCol_submt_dt += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
							searchCol_return_dt += chkResult + encodeLGual + "aplc_name" + encodeAns + strMark + detailAplc + strMark + searchOperation +  encodeRGual;
						}

					}

					String detail_date = "";
					if (!period_start.equals("") && !period_end.equals("")) {
						
						// 첫번쨰 조건 체크 ------------- 시작 
						String chkResult = "";
						if(firstConditionChk == 0){
							firstConditionChk++;
						} else {
							chkResult = encodeTotal;
						}
						// 첫번쨰 조건 체크 ------------- 끝
						
						
						if (detail_dateGubun.equals("regdate")) {
							searchCol += chkResult + encodeLGual + "sub_rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 신청일자
							detail_date = chkResult + encodeLGual + "sub_rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;
						} else if (detail_dateGubun.equals("jubsudate")) {
							searchCol += chkResult + encodeLGual + "rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 접수일자
							detail_date = chkResult + encodeLGual + "rcv_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;
						} else {
							searchCol += chkResult + encodeLGual + "rt_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual; // 등급분류일자
							detail_date = chkResult + encodeLGual + "rt_date" + between + period_start + encodeAnd
									+ period_end + encodeRGual;
						}

						// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
						searchCol_rcv_no_view += detail_date;
						searchCol_medi_name += detail_date;
						searchCol_ori_title += detail_date;
						searchCol_prodc_natnl_name += detail_date;
						searchCol_prodc_name += detail_date;
						searchCol_dire_name += detail_date;
						searchCol_mv_asso_name += detail_date;
						searchCol_leada_name += detail_date;
						searchCol_kind_name += detail_date;
						searchCol_hope_grade_name += detail_date;
						searchCol_prod_year += detail_date;
						searchCol_aplc_name += detail_date;
						searchCol_rt_no += detail_date;
						searchCol_grade_name += detail_date;
						searchCol_user_id += detail_date;
						searchCol_user_nm += detail_date;
						searchCol_proc_state_nm += detail_date;
						searchCol_probl_state += detail_date;

						// 텍스트타입
						searchCol_use_title += detail_date;
						searchCol_deter_rsn += detail_date;
						searchCol_srvc_path += detail_date;
						searchCol_probl_rsn += detail_date;
						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_rcv_date += detail_date;
							searchCol_rt_date += detail_date;
							searchCol_submt_dt += detail_date;
							searchCol_return_dt += detail_date;
						}
					} else{
						//만약 날짜가 빈값이라면? 더미데이터를 넣어줘야하기 때문에 else문에 데이터 삽입!
						
						// 첫번쨰 조건 체크 ------------- 시작 
						String chkResult = "";
						if(firstConditionChk == 0){
							firstConditionChk++;
						} else {
							chkResult = encodeTotal;
						}
						// 첫번쨰 조건 체크 ------------- 끝
						searchCol += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_rcv_no_view += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_medi_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_ori_title += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_prodc_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_prodc_natnl_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_dire_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_mv_asso_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_leada_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_kind_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_hope_grade_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_prod_year += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_aplc_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_rt_no += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_grade_name += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_user_id += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_user_nm += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_proc_state_nm += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_probl_state += chkResult + "ori_title+not+in%7B%7D+";
						// 텍스트타입
						searchCol_use_title += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_deter_rsn += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_srvc_path += chkResult + "ori_title+not+in%7B%7D+";
						searchCol_probl_rsn += chkResult + "ori_title+not+in%7B%7D+";

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_rcv_date += chkResult + "ori_title+not+in%7B%7D+";
							searchCol_rt_date += chkResult + "ori_title+not+in%7B%7D+";
							searchCol_submt_dt += chkResult + "ori_title+not+in%7B%7D+";
							searchCol_return_dt += chkResult + "ori_title+not+in%7B%7D+";
						}
					}
					
					//만약에 상세조건에 아무것도 입력하지 않는다면, 더미데이터룰 넣어준다 --> 데이터가 없으면 오류가 나기 떄문이다.
					if(searchCol.substring(searchCol.length()-1).equals("(")){
						searchCol += "ori_title+not+in%7B%7D+";
						searchCol_rcv_no_view += "ori_title+not+in%7B%7D+";
						searchCol_medi_name += "ori_title+not+in%7B%7D+";
						searchCol_ori_title += "ori_title+not+in%7B%7D+";
						searchCol_prodc_name += "ori_title+not+in%7B%7D+";
						searchCol_prodc_natnl_name += "ori_title+not+in%7B%7D+";
						searchCol_dire_name += "ori_title+not+in%7B%7D+";
						searchCol_mv_asso_name += "ori_title+not+in%7B%7D+";
						searchCol_leada_name += "ori_title+not+in%7B%7D+";
						searchCol_kind_name += "ori_title+not+in%7B%7D+";
						searchCol_hope_grade_name += "ori_title+not+in%7B%7D+";
						searchCol_prod_year += "ori_title+not+in%7B%7D+";
						searchCol_aplc_name += "ori_title+not+in%7B%7D+";
						searchCol_rt_no += "ori_title+not+in%7B%7D+";
						searchCol_grade_name += "ori_title+not+in%7B%7D+";
						searchCol_user_id += "ori_title+not+in%7B%7D+";
						searchCol_user_nm += "ori_title+not+in%7B%7D+";
						searchCol_proc_state_nm += "ori_title+not+in%7B%7D+";
						searchCol_probl_state += "ori_title+not+in%7B%7D+";
						// 텍스트타입
						searchCol_use_title += "ori_title+not+in%7B%7D+";
						searchCol_deter_rsn += "ori_title+not+in%7B%7D+";
						searchCol_srvc_path += "ori_title+not+in%7B%7D+";
						searchCol_probl_rsn += "ori_title+not+in%7B%7D+";

						// 숫자타입
						if (searchStrInt != 0) {
							searchCol_rcv_date += "ori_title+not+in%7B%7D+";
							searchCol_rt_date += "ori_title+not+in%7B%7D+";
							searchCol_submt_dt += "ori_title+not+in%7B%7D+";
							searchCol_return_dt += "ori_title+not+in%7B%7D+";
						}
					}
					
					searchCol += encodeRGual;
					searchCol_rcv_no_view += encodeRGual;
					searchCol_medi_name += encodeRGual;
					searchCol_ori_title += encodeRGual;
					searchCol_prodc_name += encodeRGual;
					searchCol_prodc_natnl_name += encodeRGual;
					searchCol_dire_name += encodeRGual;
					searchCol_mv_asso_name += encodeRGual;
					searchCol_leada_name += encodeRGual;
					searchCol_kind_name += encodeRGual;
					searchCol_hope_grade_name += encodeRGual;
					searchCol_prod_year += encodeRGual;
					searchCol_aplc_name += encodeRGual;
					searchCol_rt_no += encodeRGual;
					searchCol_grade_name += encodeRGual;
					searchCol_user_id += encodeRGual;
					searchCol_user_nm += encodeRGual;
					searchCol_proc_state_nm += encodeRGual;
					searchCol_probl_state += encodeRGual;
					// 텍스트타입
					searchCol_use_title += encodeRGual;
					searchCol_deter_rsn += encodeRGual;
					searchCol_srvc_path += encodeRGual;
					searchCol_probl_rsn += encodeRGual;

					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_rcv_date += encodeRGual;
						searchCol_rt_date += encodeRGual;
						searchCol_submt_dt += encodeRGual;
						searchCol_return_dt += encodeRGual;
					}
				}
				//detail_visibleChk 조건 끝
				
				
				
				//만약 따옴표 안에 단어가 인식이 된다면 이 로직 실행.
				if(!quote_searchStr.equals("")) {
					//통합검색이든, 메뉴명이든 뒤에 quote_searchCol 붙여주기 시작! 
					searchCol += quote_searchCol;
					searchCol_rcv_no_view += quote_searchCol;
					searchCol_medi_name += quote_searchCol;
					searchCol_ori_title += quote_searchCol;
					searchCol_prodc_name += quote_searchCol;
					searchCol_prodc_natnl_name += quote_searchCol;
					searchCol_dire_name += quote_searchCol;
					searchCol_mv_asso_name += quote_searchCol;
					searchCol_leada_name += quote_searchCol;
					searchCol_kind_name += quote_searchCol;
					searchCol_hope_grade_name += quote_searchCol;
					searchCol_prod_year += quote_searchCol;
					searchCol_aplc_name += quote_searchCol;
					searchCol_rt_no += quote_searchCol;
					searchCol_grade_name += quote_searchCol;
					searchCol_user_id += quote_searchCol;
					searchCol_user_nm += quote_searchCol;
					searchCol_proc_state_nm += quote_searchCol;
					searchCol_probl_state += quote_searchCol;
					// 텍스트타입
					searchCol_use_title += quote_searchCol;
					searchCol_deter_rsn += quote_searchCol;
					searchCol_srvc_path += quote_searchCol;
					searchCol_probl_rsn += quote_searchCol;

					// 숫자타입
					if (searchStrInt != 0) {
						searchCol_rcv_date += quote_searchCol;
						searchCol_rt_date += quote_searchCol;
						searchCol_submt_dt += quote_searchCol;
						searchCol_return_dt += quote_searchCol;
					}
				}
				//통합검색이든, 메뉴명이든 뒤에 quote_searchCol 붙여주기 끝!
				
				
				
				
				
				/**************** 상세검색의 일자, 신청회사 통합작업중 ***********************/

				/**************
				 * ■■■■■■■■■■■■■*************************************우측사이드에 관한
				 * information 시작
				 * *************************************************■■■■■■■■■
				 **************************************************/
				List<Map<String, Object>> moni01_sideInformation = new ArrayList<Map<String, Object>>();
				if (searchGubun.equals("All")) {
					// ■■■■■■■■■■■■ 통합검색 조건컬럼이 추가되면 여기도 추가
					// 1. (조건에 맞는)원제명에 대한 건수 및 데이터 조회
					moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_moniUrl,
							orderValue, searchCol_ori_title, "원제명"));

					// 2. (조건에 맞는)사용제명에 대한 건수 및 데이터 조회
					moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_moniUrl,
							orderValue, searchCol_use_title, "사용제명"));

					// 3. (조건에 맞는)접수번호에 대한 건수 및 데이터 조회
					moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_moniUrl,
							orderValue, searchCol_rcv_no_view, "접수번호"));

					// 4. (조건에 맞는)매체명에 대한 건수 및 데이터 조회
					moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_moniUrl,
							orderValue, searchCol_medi_name, "매체명"));

					// 5. (조건에 맞는)제작사명에 대한 건수 및 데이터 조회
					moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_moniUrl,
							orderValue, searchCol_prodc_name, "제작사명"));

					// 6. (조건에 맞는)제작사 국적에 대한 건수 및 데이터 조회
					moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_moniUrl,
							orderValue, searchCol_prodc_natnl_name, "제작사 국적"));

					// 7. (조건에 맞는)감독명에 대한 건수 및 데이터 조회
					moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_moniUrl,
							orderValue, searchCol_dire_name, "감독명"));

					// 8. (조건에 맞는)영화 종류에 대한 건수 및 데이터 조회
					moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_moniUrl,
							orderValue, searchCol_mv_asso_name, "영화 종류"));

					// 9. (조건에 맞는)주연명에 대한 건수 및 데이터 조회
					moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_moniUrl,
							orderValue, searchCol_leada_name, "주연명"));

					// 10. (조건에 맞는)종류에 대한 건수 및 데이터 조회
					moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_moniUrl,
							orderValue, searchCol_kind_name, "종류"));

					// 11. (조건에 맞는)희망등급에 대한 건수 및 데이터 조회
					moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_moniUrl,
							orderValue, searchCol_hope_grade_name, "희망등급"));

					// 12. (조건에 맞는)제작년도에 대한 건수 및 데이터 조회
					moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_moniUrl,
							orderValue, searchCol_prod_year, "제작년도"));

					// 13. (조건에 맞는)신청회사에 대한 건수 및 데이터 조회
					moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_moniUrl,
							orderValue, searchCol_aplc_name, "신청회사"));

					// 14. (조건에 맞는)등급분류번호 에 대한 건수 및 데이터 조회
					moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_moniUrl,
							orderValue, searchCol_rt_no, "등급분류번호"));

					// 15. (조건에 맞는)결정등급에 대한 건수 및 데이터 조회
					moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_moniUrl,
							orderValue, searchCol_grade_name, "결정등급"));

					// 16. (조건에 맞는)위원명에 대한 건수 및 데이터 조회
					moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_moniUrl,
							orderValue, searchCol_user_id, "사용자 아이디"));

					// 17 (조건에 맞는)종합 개별 구분에 대한 건수 및 데이터 조회
					moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_moniUrl,
							orderValue, searchCol_user_nm, "사용자명"));

					// 18 (조건에 맞는)진행 상태에 대한 건수 및 데이터 조회
					moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_moniUrl,
							orderValue, searchCol_proc_state_nm, "진행 상태명"));

					// 19 (조건에 맞는)전문위원 조 구분에 대한 건수 및 데이터 조회
					moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_moniUrl,
							orderValue, searchCol_probl_state, "문제점 상태"));

					// 20 (조건에 맞는)주제 등급에 대한 건수 및 데이터 조회
					moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_moniUrl,
							orderValue, searchCol_deter_rsn, "결정사유"));

					// 21 (조건에 맞는)선정성 등급에 대한 건수 및 데이터 조회
					moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_moniUrl,
							orderValue, searchCol_srvc_path, "서비스 경로"));

					// 22 (조건에 맞는)폭력성 등급에 대한 건수 및 데이터 조회
					moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_moniUrl,
							orderValue, searchCol_probl_rsn, "문제점 사유"));

					/**************** 텍스트 데이터 끝!!! ******************/

					/**************** 숫자 데이터 시작 (null처리)!!! ******************/

					if (searchStrInt != 0) {
						// 35 (조건에 맞는)접수일자에 대한 건수 및 데이터 조회
						moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response,
								result_moniUrl, orderValue, searchCol_rcv_date, "접수일자"));
						// 36 (조건에 맞는)등급분류일에 대한 건수 및 데이터 조회
						moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response,
								result_moniUrl, orderValue, searchCol_rt_date, "등급분류일"));
						// 제출 일시에 대한 건수 및 데이터 조회
						moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response,
								result_moniUrl, orderValue, searchCol_submt_dt, "제출 일시"));
						// 반려 일시에 대한 건수 및 데이터 조회
						moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response,
								result_moniUrl, orderValue, searchCol_return_dt, "반려 일시"));
					}
					/**************** 숫자 데이터 끝 (null처리)!!! ******************/

					Collections.sort(moni01_sideInformation, new sideMenuCntComparator());
				} // if(searchGubun.equals("All")) 끝
				if (searchGubun.equals("title")) {
					// (조건에 맞는)원제명 대한 건수 및 데이터 조회
					moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_moniUrl,
							orderValue, searchCol, "원제명/사용제명"));
				}
				if (searchGubun.equals("decision")) {
					// (조건에 맞는)결정사유 대한 건수 및 데이터 조회
					moni01_sideInformation.add(urlEncodingResult(walker, paramMap, request, response, result_moniUrl,
							orderValue, searchCol, "결정사유"));
				}
				// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
				walker.setValue("moni01_sideInformation", moni01_sideInformation);

				/**************
				 * ■■■■■■■■■■■■■***************************************우측사이드에 관한
				 * information
				 * 끝***********************************************■■■■■■■■■
				 **************************************************/

				/*************************************************************
				 * 검색어 처리 끝
				 *************************************************************************************************/

				totalUrl = result_moniUrl  + searchCol + orderValue;
				// 결과내 재검색이라면 ? 시작
				if (((String) paramMap.get("result_re_search_chk")).equals("Y")
						&& (String) paramMap.get("moni01_historySearchQuery") != null) {
					totalUrl = (String) paramMap.get("moni01_historySearchQuery") + encodeAnd + "(" + searchCol + ")"
							+ orderValue;
				}
				// 결과내 재검색이라면 ? 끝

				// 이전쿼리 저장 시작
				String historySearchQuery = "";
				historySearchQuery = result_moniUrl  + "(" + searchCol + ")";
				// 만약 결과내재검색을 통한 검색이었다면, historySearchQuery(이전쿼리)는 , 이전쿼리 +
				// encodeAnd + 현재쿼리 일 것이다.
				if (((String) paramMap.get("result_re_search_chk")).equals("Y")
						&& (String) paramMap.get("moni01_historySearchQuery") != null) {
					historySearchQuery = (String) paramMap.get("moni01_historySearchQuery") + encodeAnd + "("
							+ searchCol + ")";
				}
				paramMap.put("moni01_historySearchQuery", historySearchQuery);
				// 이전쿼리 저장 끝
			} else {
				// 검색어가 공백이라면? result_opin에 관한 모든 데이터 조회!
				totalUrl = result_moniUrl + orderValue;
			}

			/*************************************************************
			 * 얻어낸 Url 결과물로 추가적인 작업시작 / 페이징 처리 시작
			 *************************************************************************************************/
			String paging_totalUrl = totalUrl + "&pagelength=" + detailresultLine + "&offset=" + offset + "&limit="
					+ detailresultLine + "&hilite-fields="
					+ "%7B%22use_title%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22ori_title%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22aplc_name%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22rt_no%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22rt_time%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22user_nm%22%3A%7B%22length%22%3A512%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22srvc_path%22%3A%7B%22length%22%3A1000%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D"
					+ "%2C"
					+ "%7B%22deter_rsn%22%3A%7B%22length%22%3A3000%2C%22begin%22%3A%22%3Cb%20style%3D'%20background%3A%20yellow%3B%20'%3E%22%2C%20%22end%22%3A%22%3C%2Fb%3E%22%7D%7D";

			List<Object> dataList = pagingURLEncodingFunc(paging_totalUrl);
			moni01.put("total_count_page", (Integer) dataList.get(0));
			walker.setValue("moni01_resultMapList", (List<Map<String, String>>) dataList.get(1));
			/*************************************************************
			 * 얻어낸 Url 결과물로 추가적인 작업시작 / 페이징 처리 시작
			 *************************************************************************************************/

			// totalURLCntFunc로 총합 갯수만 가져오고있다. -> 데이터가 필요하다면 해당 메소드를 안에
			// resultMap을 확인하면 됨.
			Integer total_count = totalURLCntFunc(totalUrl);
			moni01.put("total_count", total_count);
			walker.setValue("moni01", moni01);

		}
		/////////////////////////////////////////// 모니터링의견서 끝
		/////////////////////////////////////////// ()///////////////////////////////////////////////////////////////////////////////////////////////////
	}

	/**** 사이드 메뉴 별 정렬 클래스 ****/
	class sideMenuCntComparator implements Comparator<Map<String, Object>> {
		@Override
		public int compare(Map<String, Object> o1, Map<String, Object> o2) {

			if ((Integer) o1.get("total_side_page_count") > (Integer) o2.get("total_side_page_count")) {
				return -1;
			} else if ((Integer) o1.get("total_side_page_count") < (Integer) o2.get("total_side_page_count")) {
				return 1;
			}
			return 0;
		}

	}

	/**** 사이드 메뉴 별 정렬 클래스 ****/

	/**** 오타 교정 URL 해석 메소드 ****/
	/*
	 * 입력받은 검색얼르 이용하여, 오타교정을 해준다. movie01(), video01()이 아닌, 기본 컨트롤러의 마지막 로직 부분에서
	 * 연계하여 사용한다.
	 */
	public List<String> typocorrectionFunc(String typocorrectionUrl, String encodeSearchStr) {

		BufferedReader br = null;
		InputStream is = null;
		List<String> correctStrList = null;
		try {
			typocorrectionUrl = typocorrectionUrl + encodeSearchStr;

			URL url = new URL(typocorrectionUrl);
			URLConnection conn = url.openConnection();
			is = conn.getInputStream();
			// br = new BufferedReader(new InputStreamReader(is));
			br = new BufferedReader(new InputStreamReader(is, "UTF-8")); // ,
																			// "KSC5601"

			String apidata = "";

			String output = null;
			StringBuffer sb = new StringBuffer();

			while ((output = br.readLine()) != null) {
				sb.append(output + "\r\n");
			}

			apidata = sb.toString();

			correctStrList = new ArrayList<String>();

			/*
			 * System.out.println("오타교정 입력받은 URL : " + typocorrectionUrl);
			 * System.out.println("오타교정 검색결과 : " + apidata);
			 * 
			 * System.out.println(apidata.substring(0));
			 * System.out.println(apidata.substring(1,apidata.lastIndexOf("]")))
			 * ;
			 */
			apidata = apidata.substring(1, apidata.lastIndexOf("]"));

			String[] correctStr = apidata.split(",");

			for (String str : correctStr) {
				str = str.replaceAll("\"", "");
				correctStrList.add(str);
			}

			// System.out.println("오타교정 리스트 correctStrList : " + correctStrList
			// );
			/*
			 * String[] correctStr = apidata.split("[\"|\"\\,\"|\"]");
			 * System.out.println(correctStr.length);
			 * 
			 * String[] correctRealStr = null; int i = 0; if(correctStr.length >
			 * 1){ for(String str : correctStr){ if(str != null &&
			 * !str.isEmpty()){ System.out.println(str); correctRealStr[i] =
			 * str; i++; } }
			 * 
			 * //paramMap.put("correctRealStr", correctRealStr);
			 * System.out.println("correctRealStr: " + correctRealStr);
			 * 
			 * 
			 * } else{ System.out.println("저장X"); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception occur: SearchController.java) typocorrectionFunc ------ try~catch ");
		} finally {
			try {
				is.close();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(
						"Exception occur: SearchController.java) typocorrectionFunc ------ finally { try~catch } ");
			}
		}
		return correctStrList;
	}

	/**** 오타 교정 URL 해석 메소드 ****/
	/**** 추천(연관)검색어 URL 해석 메소드 ****/
	/*
	 * 입력받은 검색얼르 이용하여, 오타교정을 해준다. movie01(), video01()이 아닌, 기본 컨트롤러의 마지막 로직 부분에서
	 * 연계하여 사용한다.
	 */
	public List<String> suggestionFunc(String suggestionUrl, String encodeSearchStr) {

		// System.out.println("[suggestFunc 연관검색어] 시작 ");

		InputStream is = null;
		BufferedReader br = null;
		List<String> suggestionStrList = null;
		
		JSONArray jsonArray = new JSONArray();
		JSONParser parser = new JSONParser();
		try {
			suggestionUrl = suggestionUrl + encodeSearchStr;

			URL url = new URL(suggestionUrl);
			
			URLConnection conn = url.openConnection();
			is = conn.getInputStream();
			// br = new BufferedReader(new InputStreamReader(is));
			br = new BufferedReader(new InputStreamReader(is, "UTF-8")); // ,
																			// "KSC5601"

			String apidata = "";

			String output = null;
			StringBuffer sb = new StringBuffer();

			while ((output = br.readLine()) != null) {
				sb.append(output + "\r\n");
			}

			apidata = sb.toString();


			// System.out.println("연관검색어로 입력받은 URL : " + suggestionUrl);
			// System.out.println("연관검색어 검색결과 : " + apidata);

			
			
			
			// 큰 따옴표도 replace해줘야함!
			/*
			 * 0310 임시주석 처리 --> 원래는 추천검색어였고, 추천검색어의 파싱은 아래의 로직과 같다. 나중에 추천검색어를 쓰게 된다면 이 로직을 사용하자 . (현재 연관검색어를 jsp에서 받는 것까지 복붙해서 사용하면됨) List를 받아서 쓰는 등등.. 
			suggestionStrList = new ArrayList<String>();
			String suggestionStr = apidata.replaceAll("\\[|\\]|\"", "");
			String[] suggestionStrArray = suggestionStr.split(",");
			suggestionStrList = Arrays.asList(suggestionStrArray);
			*/
			
			
			
			JSONObject suggestResult = (JSONObject) parser.parse(apidata);
			//suggestResult(연관검색어)를 받아오는  형식 {"result":{"list_count":1, "list":[{"recommend":["도시"], "keyword":"범죄"}]}}
			// 한줄로 쓰면 ((ArrayList<JSONObject>)((JSONObject)suggestResult.get("result")).get("list")).get(0).get("recommend") 가독성을 위해서 나눈다.
			
			
			// 20230328 list_count -> 가져오는 리스트의 갯수가 0이라면 아래의 연관검색어 가져오는로직 실행 X 
			Long list_count = (Long)(((JSONObject)suggestResult.get("result")).get("list_count"));
			if(list_count != 0 ){
				   ArrayList<JSONObject> suggestlist =  (ArrayList<JSONObject>)((JSONObject)suggestResult.get("result")).get("list");
				   suggestionStrList = (List<String>)(suggestlist.get(0).get("recommend"));
			   }
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception occur: SearchController.java) suggestionFunc ------ try~catch ");
		} finally {
			try {
				is.close();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(
						"Exception occur: SearchController.java) suggestionFunc ------ finally { try~catch } ");
			}
		}
		// System.out.println("[suggestFunc 연관검색어] 종료 ");

		return suggestionStrList;
	}

	/**** 추천(연관)검색어 URL 해석 메소드 ****/

	/**** 인기검색어 URL 해석 메소드 ****/
	/*
	 * 입력받은 검색얼르 이용하여, 오타교정을 해준다. movie01(), video01()이 아닌, 기본 컨트롤러의 마지막 로직 부분에서
	 * 연계하여 사용한다.
	 */
	public Map<String, List<String>> hotKeywordFunc(String hotKeywordUrl) {

		// System.out.println("[인기검색어] 시작 ");
		BufferedReader br = null;
		InputStream is = null;
		Map<String, List<String>> resultMapList = null;
		try {
			URL url = new URL(hotKeywordUrl);
			URLConnection conn = url.openConnection();
			is = conn.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));

			char[] buff = new char[512];
			int len = -1;

			String apidata = "";
			while ((len = br.read(buff)) != -1) {
				apidata += new String(buff, 0, len);
			}

			List<String> resultList = new ArrayList<String>();
			List<String> resultStateList = new ArrayList<String>();
			List<String> correctStrList = new ArrayList<String>();

			// System.out.println("인기검색어 입력받은 URL : " + hotKeywordUrl);
			// System.out.println("인기검색어 검색결과 : " + apidata);
			// System.out.println(apidata.replaceAll("[", "a"));
			String result = "";
			for (int i = 0; i < apidata.length(); i++) {
				if (String.valueOf(apidata.charAt(i)).matches("[a-zA-Z0-9 ㄱ-ㅎㅏ-ㅣ가-힣]")) {
					result += apidata.charAt(i);
				}
			}

			// System.out.println(result);
			String aa = apidata.replaceAll("\\[|\\]|\\]\\,|\"", "");
			String[] abc = aa.split(",");
			List<String> abcList = Arrays.asList(abc);
			// System.out.println("abcList : " + abcList);

			if (abcList.size() > 0) {
				for (int i = 0; i < abcList.size(); i++) {
					if (i % 2 == 0) {
						resultList.add(abcList.get(i));
					} else {
						resultStateList.add(abcList.get(i));
					}
				}
			}

			resultMapList = new HashMap<String, List<String>>();
			/*
			 * System.out.println(resultList); // 인기키워드 API데이터가 빈값이면 사이즈1
			 * System.out.println(resultStateList); // 인기키워드 API데이터가 빈값이면 사이즈0
			 * System.out.println(resultList.size());
			 * System.out.println(resultStateList.size());
			 */
			resultMapList.put("resultList", resultList);
			resultMapList.put("resultStateList", resultStateList);

			// System.out.println("인기검색어 리스트 correctStrList : " + correctStrList
			// );

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception occur: SearchController.java) hotKeywordFunc ------ try~catch ");
		} finally {
			try {
				is.close();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(
						"Exception occur: SearchController.java) hotKeywordFunc ------ finally { try~catch } ");
			}
		}
		// System.out.println("[인기검색어] 종료 ");

		return resultMapList;
	}

	/**** 인기검색어 URL 해석 메소드 ****/

	/**
	 * 자동완성을 위한 메소드 (ajax 통신으로 데이터를 전달한다.)
	 */
	@ResponseBody
	@RequestMapping(value = "/search/konan_autocomplete.do", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public JSONArray autocompleteAjax(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap, ModelMap model, @RequestBody String searchStr)
			throws Exception {
		// try~catch문 밖에서 URLEncoder 메소드를 사용중이라서, try~catch문을 적용중이지만, 따로 throws를
		// 해준다.

		// response.setCharacterEncoding("UTF-8");

		// 검색어 실시간 입력받기
		// System.out.println(searchStr);

		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(searchStr);
		if ((String) jsonObject.get("searchStr") == null || ((String) jsonObject.get("searchStr")).equals("")) {
			return null;
		}
		String autocompleteData = (String) jsonObject.get("searchStr");

		// System.out.println(autocompleteData);
		// 검색어 실시간 입력받기

		String autocompleteDataEncode = URLEncoder.encode(autocompleteData, "UTF-8");

		String autocompleteURL = "http://192.168.11.242:7614/ksf/api/suggest?target=complete&domain_no=0&term="
				+ autocompleteDataEncode + "&mode=s&max_count=10&use_spc=false";
		/*
		 * System.out.println(autocompleteDataEncode);
		 * System.out.println(autocompleteURL);
		 */

		BufferedReader br = null;
		InputStream is = null;
		JSONArray jsonArray = new JSONArray();
		try {
			URL url = new URL(autocompleteURL);

			// 여기서 오류
			URLConnection conn = url.openConnection();
			is = conn.getInputStream();
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			char[] buff = new char[512];
			int len = -1;

			String apidata = "";
			while ((len = br.read(buff)) != -1) {
				apidata += new String(buff, 0, len);
			}

			/*
			 * System.out.println("~~autocompleteURL~~시작");
			 * System.out.println(apidata);
			 * System.out.println("~~autocompleteURL~~끝");
			 */
			// 자동완성 URL 해석 후, 파싱과정
			JSONObject autocompleteResult = (JSONObject) parser.parse(apidata);
			// System.out.println("suggestions 리스트");
			// System.out.println(autocompleteResult.get("suggestions"));

			List<List<List<String>>> autocompleteResultList = (List<List<List<String>>>) autocompleteResult
					.get("suggestions");
			/*
			 * System.out.println("suggestions 리스트 -1 ");
			 * System.out.println(autocompleteResultList.get(0));
			 * System.out.println("suggestions 리스트 -2 ");
			 * System.out.println(autocompleteResultList.get(0).get(0));
			 * System.out.println("suggestions 리스트 -3 ");
			 */
			for (int i = 0; i < autocompleteResultList.get(0).size(); i++) {
				// System.out.println(autocompleteResultList.get(0).get(i).get(0));
				JSONObject autocompleteUrlResult = new JSONObject();
				autocompleteUrlResult.put(i, autocompleteResultList.get(0).get(i).get(0));
				jsonArray.add(autocompleteUrlResult);

				// System.out.println("반복문");
				// System.out.println(autocompleteUrlResult);
				// System.out.println(jsonArray);
			}
			// System.out.println("마지막단계");
			// System.out.println(jsonArray);
			// String result = autocompleteUrlResult.toJSONString();
			String result = jsonArray.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception occur: SearchController.java) autocompleteAjax ------  { try~catch } ");
		} finally {
			try {
				is.close();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(
						"Exception occur: SearchController.java) autocompleteAjax ------ finally { try~catch } ");
			}
		}

		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(jsonArray);

		return null;
	}

}
