/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swcommodities.wsmill.bo.CompanyService;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.view.CompanyView;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.ServletSupporter;

/**
 *
 * @author duhc
 */
@Transactional(propagation = Propagation.REQUIRED)
@org.springframework.stereotype.Controller
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	@Autowired(required = true)
	private ServletContext context;
	@Autowired(required = true)
	private HttpServletRequest request;

	// get company List_source
	@RequestMapping(value = "company_ajax_source.htm")
	public @ResponseBody void delivery_list_source(HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String[] aColumns = { "id", "full_name", "full_name_vn", "name", "name_vn", "address1", "address1_vn",
				"representative", "representative_role", "representativRole_vn", "email", "fax", "telephone",
				"country_id", "active" };

		JSONObject result = new JSONObject();
		JSONArray aaData = new JSONArray();

		int amount = 10;
		int start = 0;
		int echo = 0;
		int col = 0;
		Byte active = 0;
		int company_type = 0;

		String dir = "asc";
		String sStart = request.getParameter("iDisplayStart");
		String sAmount = request.getParameter("iDisplayLength");
		String sEcho = request.getParameter("sEcho");
		String sCol = request.getParameter("iSortCol_0");
		String sdir = request.getParameter("sSortDir_0");
		String type_str = request.getParameter("company_type");
		// String terminalMonth = new
		// DailyBasisDao().getDailyBasisByTmCode(Integer.parseInt(sTmCode.trim().equals("A")
		// ? "0" : sTmCode));

		if (sStart != null) {
			start = Integer.parseInt(sStart);
			if (start < 0) {
				start = 0;
			}
		}
		if (sAmount != null) {
			amount = Integer.parseInt(sAmount);
		}
		if (sEcho != null) {
			echo = Integer.parseInt(sEcho);
		}
		if (sCol != null) {
			col = Integer.parseInt(sCol);
			if (col < 0 || col > 11) {
				col = 0;
			}
		}
		if (sdir != null) {
			if (!sdir.equals("asc")) {
				dir = "desc";
			}
		}

		if (type_str != null) {
			company_type = Integer.parseInt(type_str);
		}

		String colName = aColumns[col];
		long total = companyService.countRow();
		long totalAfterFilter = total;

		String searchTerm = request.getParameter("sSearch");
		if (searchTerm.equals("undefined")) {
			searchTerm = "";
		}

		ArrayList<CompanyMaster> list = companyService.searchGlobe(searchTerm, sdir, start, amount, colName, active,
				company_type);

		int count = 0;
		int showChar = 27;
		String str = "";
		for (CompanyMaster company : list) {
			int i = 0;
			JSONObject row = new JSONObject();
			row.put("DT_RowClass", "data_row masterTooltip");
			row.put("DT_RowId", "row_" + company.getId());
			row.put((i++) + "", ++count);
			row.put((i++) + "", company.getFullName() != null ? company.getFullName() : "");
			row.put((i++) + "", company.getFullNameVn() != null ? company.getFullNameVn() : "");
			row.put((i++) + "", company.getName() != null ? company.getName() : "");
			row.put((i++) + "", company.getNameVn() != null ? company.getNameVn() : "");
			row.put((i++) + "", Common.getShorterString(company.getAddress1(), showChar));

			row.put((i++) + "", Common.getShorterString(company.getAddress1Vn(), showChar));
			// row.put((i++) + "", company.getAddress2() != null ? company.getAddress2() :
			// "");
			// row.put((i++) + "", company.getAddress2Vn() != null ? company.getAddress2Vn()
			// : "");
			row.put((i++) + "", company.getRepresentative() != null ? company.getRepresentative() : "");
			row.put((i++) + "", company.getRepresentativeRole() != null ? company.getRepresentativeRole() : "");
			row.put((i++) + "", company.getRepresentativeRoleVn() != null ? company.getRepresentativeRoleVn() : "");
			// row.put((i++) + "", company.getTaxCode()!= null ? company.getTaxCode() : "");
			row.put((i++) + "", company.getEmail() != null ? company.getEmail() : "");
			row.put((i++) + "", company.getFax() != null ? company.getFax() : "");
			row.put((i++) + "", company.getTelephone() != null ? company.getTelephone() : "");
			row.put((i++) + "",
					(company.getCountryId() != null)
							? companyService.getCountryShortNameById(Integer.parseInt(company.getCountryId()))
							: "");
			row.put((i++) + "",
					"<input type=\"checkbox\" id=\"rd_" + company.getId() + "\" class=\"active\" value=\"1\" "
							+ ((company.getActive().equals(Byte.parseByte("1"))) ? "checked" : "") + " disabled />");
			aaData.put(row);
		}

		totalAfterFilter = companyService.getTotalAfterFilter();

		result.put("iTotalRecords", total);
		result.put("iTotalDisplayRecords", totalAfterFilter);
		result.put("aaData", aaData);
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		response.getWriter().print(result);
	}

	// save company master
	@RequestMapping(value = "save_company_master.htm", method = RequestMethod.POST)
	public @ResponseBody void save_company_master(HttpServletResponse response) throws Exception {
		ServletSupporter supporter = new ServletSupporter(request);

		int id = supporter.getIntValue("id");
		String fullName = supporter.getStringRequest("fullName");
		String fullNameVn = supporter.getStringRequest("fullNameVn");
		String name = supporter.getStringRequest("name");
		String nameVn = supporter.getStringRequest("nameVn");
		boolean active = supporter.getBooleanValue("active");
		String address1 = supporter.getStringRequest("address1");
		String address1Vn = supporter.getStringRequest("address1Vn");
		String address2 = supporter.getStringRequest("address2");
		String address2Vn = supporter.getStringRequest("address2Vn");
		String representative = supporter.getStringRequest("representative");
		String representativeRole = supporter.getStringRequest("representativeRole");
		String representativeRoleVn = supporter.getStringRequest("representativeRoleVn");
		String taxCode = supporter.getStringRequest("taxCode");
		String country = supporter.getStringRequest("country");
		String email = supporter.getStringRequest("email");
		String fax = supporter.getStringRequest("fax");
		String telephone = supporter.getStringRequest("telephone");

		CompanyMaster company = new CompanyMaster();
		if (id != -1) {
			company = companyService.getCompanyById(id);
		}

		company.setActive(active);
		company.setAddress1(address1);
		company.setAddress1Vn(address1Vn);
		company.setAddress2(address2);
		company.setAddress2Vn(address2Vn);
		company.setRepresentative(representative);
		company.setRepresentativeRole(representativeRole);
		company.setRepresentativeRoleVn(representativeRoleVn);
		company.setTaxCode(taxCode);
		if (!country.equals("-1")) {
			company.setCountryId(country);
		} else {
			company.setCountryId(null);
		}
		company.setEmail(email);
		company.setFax(fax);
		company.setFullName(fullName);
		company.setFullNameVn(fullNameVn);
		company.setName(name);
		company.setNameVn(nameVn);
		company.setTelephone(telephone);
		companyService.update(company);
	}

	// save company master
	@RequestMapping(value = "update_company_fromCB.htm", method = RequestMethod.POST)
	public @ResponseBody void update_company_fromCB(HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		try {
			StringBuilder jb = new StringBuilder();
			String line;
			BufferedReader reader;
			reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				jb.append(line);
			}
			JSONObject Obj = new JSONObject(jb.toString());
			JSONObject jsonData = Obj.getJSONObject("company");
			/* get value from request */
			int id = jsonData.getInt("id");
			String fullName = jsonData.getString("full_name");
			String fullNameVn = jsonData.getString("full_name_vn");
			String name = jsonData.getString("name");
			String nameVn = jsonData.getString("name_vn");

			Boolean active = Boolean.FALSE;
			if (jsonData.getInt("active") == 1) {
				active = Boolean.TRUE;
			}

			String address1 = jsonData.getString("address1");
			String address1Vn = jsonData.getString("address1_vn");
			String address2 = jsonData.getString("address2");
			String address2Vn = jsonData.getString("address2_vn");
			String representative = jsonData.getString("representative");
			String representativeRole = jsonData.getString("representative_role");
			String representativeRoleVn = jsonData.getString("representative_role_vn");
			String taxCode = jsonData.getString("tax_code");
			String country_id = jsonData.getString("country_id");
			String email = jsonData.getString("email");
			String fax = jsonData.getString("fax");
			String telephone = jsonData.getString("telephone");

			/* create new company object */
			CompanyMaster company;
			/* find in Mill for this company */
			company = companyService.getCompanyById(id);
			if (null == company) {
				/* if this company is not exist in Mill, add new */
				company = new CompanyMaster();
			}
			company.setActive(active);
			company.setAddress1(address1);
			company.setAddress1Vn(address1Vn);
			company.setAddress2(address2);
			company.setAddress2Vn(address2Vn);
			company.setRepresentative(representative);
			company.setRepresentativeRole(representativeRole);
			company.setRepresentativeRoleVn(representativeRoleVn);
			company.setTaxCode(taxCode);
			company.setCountryId(country_id);
			company.setEmail(email);
			company.setFax(fax);
			company.setFullName(fullName);
			company.setFullNameVn(fullNameVn);
			company.setName(name);
			company.setNameVn(nameVn);
			company.setTelephone(telephone);
			companyService.update(company);
			/* return value for this request */
			JSONObject jo = new JSONObject();
			jo.put("status", "success");
			jo.put("id", company.getId());
			jo.put("comany_name", company.getName());
			response.getWriter().print(jo.toString());
		} catch (IOException | JSONException | NumberFormatException e) {
			System.out.println("Error: From " + " request - " + e);
			/* report an error */
			JSONObject jo = new JSONObject();
			jo.put("status", "failed");
			jo.put("reason", e.toString());
			response.getWriter().print(jo.toString());
		}
	}

	@RequestMapping(value = "load_detail_company.json", method = RequestMethod.POST)
	public @ResponseBody CompanyView load_detail_di(HttpServletResponse response) throws Exception {
		ServletSupporter supporter = new ServletSupporter(request);
		CompanyView cm = companyService.getLazyCompanyById(supporter.getIntValue("id"));
		if (cm != null) {
			return cm;
		} else {
			return null;
		}
	}

	@RequestMapping(value = "movementPledge.htm", method = RequestMethod.POST)
	public @ResponseBody void movementPledge(HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		json.put("select", new JSONArray(companyService.getCompanyInMovement()));
		response.getWriter().print(json.toString());
	}

	@RequestMapping(value = "get_allcompany_filter.htm", method = RequestMethod.POST)
	public @ResponseBody void get_allcompany_filter(HttpServletResponse response) throws Exception {
		// response.setContentType("text/html;charset=UTF-8");
		// Template tpl = new Template(new File(context.getRealPath("/") +
		// "templates/common.html"));
		// ArrayList<CompanyMaster> list = companyService.getRefListCompanies();
		// response.getWriter().print(new
		// GenTemplate(request).generateCompanyFilterList(tpl, list, "com"));
		JSONArray jArr = new JSONArray(companyService.getCompaniesMap());
		JSONObject json = new JSONObject();
		json.put("select", jArr);
		response.getWriter().print(json.toString());
	}

	@RequestMapping("load_companytype_list.htm")
	public @ResponseBody void load_companytype_list(HttpServletResponse response) throws Exception {
		response.getWriter().print("[" + companyService.getCompanyTypeList().toString() + "]");
	}

	@RequestMapping("load_companytypes.htm")
	public @ResponseBody void load_companytypes(HttpServletResponse response) throws Exception {
		response.getWriter().print(companyService.getCompanyTypes());
	}

	@RequestMapping("load_companytype_info.htm")
	public @ResponseBody void load_companytype_info(HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		ServletSupporter supporter = new ServletSupporter(request);
		int comp_id = supporter.getIntValue("comp_id");

		JSONObject json = new JSONObject();
		JSONArray json_array = new JSONArray(companyService.getTypes(comp_id));
		json.put("roles", json_array);

		response.getWriter().print("[" + json.toString() + "]");
	}

	@RequestMapping("update_company_type.htm")
	public @ResponseBody void update_company_type(HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		ServletSupporter supporter = new ServletSupporter(request);

		String company = supporter.getStringRequest("companies");
		String types = supporter.getStringRequest("typestr");
		JSONArray types_json = new JSONArray(types);
		String[] comp_arr = company.split(",");
		for (String comp : comp_arr) {
			int company_id = Integer.parseInt(comp);
			for (int i = 0; i < types_json.length(); i++) {
				JSONObject jo = types_json.getJSONObject(i);
				if (jo.getBoolean((i + 1) + "")) {
					if (!companyService.checkRole(company_id, i + 1)) {
						companyService.UpdateCompanyRoles(company_id, i + 1);
					}
				} else {
					if (companyService.checkRole(company_id, i + 1)) {
						companyService.deleteCompanyRoles(company_id, i + 1);
					}
				}
			}
		}

	}

	// save company master
	@RequestMapping(value = "test_1.htm", method = RequestMethod.POST)
	public @ResponseBody void test_1(HttpServletResponse response) throws Exception {
		response.getWriter().print("1");
	}
}
