package com.swcommodities.wsmill.formController;

import java.text.MessageFormat;
import java.util.List;

import com.swcommodities.wsmill.hibernate.dto.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;

import com.swcommodities.wsmill.hibernate.specification.InstructionSpecification;
import com.swcommodities.wsmill.hibernate.specification.SearchCriteria;

/**
 * Created by dunguyen on 10/18/16.
 */
public class PagingController {
    private Integer margin = 3;
    private String singleLi = "<li class=\"single-item {0}\" ><a data-page=\"{1}\">{2}</a></li>";
    private String href = "href=\"#\"";
    private String script = "<script>$(document).ready(function(){$('.single-item:not(.selected):not(.disabled) a').click(   function(){var pageNo = $(this).data('page'); $('#pageNo').val(pageNo); var _f = $(this).closest('form'); _f.find('.js_date').each(function () {if($(this).val() == '') {$(this).attr('disabled', 'disabled');}}); _f.submit();}  )});</script>";
    private String hiddenInputPageNo = "<input name='pageNo' type='hidden' id='pageNo' value='1' />";

    public Integer getPageNo(Integer pageNo) {
        if(pageNo == null) {
            pageNo = 1;
        } else {

        }
        return pageNo;
    }

    public Integer getPerPage(Integer perPage) {
        if(perPage == null) {
            perPage = 10;
        }
        return perPage;
    }

    public Pageable getPageableCriteria(Integer pageNo, Integer perPage) {
        return getPageableCriteria(pageNo, perPage, "refNumber", Sort.Direction.DESC);
    }

    public Pageable getPageableCriteria(Integer pageNo, Integer perPage, String sortField, Sort.Direction sortDirection) {
        Pageable page1 = new PageRequest(
                pageNo - 1, perPage, sortDirection, sortField
        );
        return page1;
    }

    public Pageable getPageableCriteria(Integer pageNo, Integer perPage, Sort sort) {
        Pageable page1 = new PageRequest(
                pageNo - 1, perPage, sort
        );
        return page1;
    }

    public Integer getStatus(Integer status) {
        if(status == null) {
            return -1;
        }
        return status;
    }

    public int[] getPerPages() {
        return new int[]{10,20,50};
    }

    public Specifications<DeliveryInstruction> addSpecification(Specifications<DeliveryInstruction> specifications, InstructionSpecification addItem) {
        if(specifications == null) {
            specifications = Specifications.where(addItem);
        } else {
            specifications = specifications.and(addItem);
        }
        return specifications;
    }

    public Specifications<ProcessingInstruction> addSpecificationProcessing(Specifications<ProcessingInstruction> specifications, InstructionSpecification addItem) {
        if(specifications == null) {
            specifications = Specifications.where(addItem);
        } else {
            specifications = specifications.and(addItem);
        }
        return specifications;
    }

    public Specifications<ShippingInstruction> addSpecificationShipping(Specifications<ShippingInstruction> specifications, InstructionSpecification addItem) {
        if(specifications == null) {
            specifications = Specifications.where(addItem);
        } else {
            specifications = specifications.and(addItem);
        }
        return specifications;
    }

    public Specifications<SampleSent> addSpecificationSampleSent(Specifications<SampleSent> specifications, InstructionSpecification addItem) {
        if(specifications == null) {
            specifications = Specifications.where(addItem);
        } else {
            specifications = specifications.and(addItem);
        }
        return specifications;
    }

    public Specifications<CompanyMaster> addSpecificationCompanyMaster(Specifications<CompanyMaster> specifications, InstructionSpecification addItem) {
        if(specifications == null) {
            specifications = Specifications.where(addItem);
        } else {
            specifications = specifications.and(addItem);
        }
        return specifications;
    }

    public Specifications<Transaction> addSpecificationTransaction(Specifications<Transaction> specifications, InstructionSpecification addItem) {
        if(specifications == null) {
            specifications = Specifications.where(addItem);
        } else {
            specifications = specifications.and(addItem);
        }
        return specifications;
    }

    public Specifications<Invoice> addSpecificationInvoice(Specifications<Invoice> specifications, InstructionSpecification addItem) {
        if(specifications == null) {
            specifications = Specifications.where(addItem);
        } else {
            specifications = specifications.and(addItem);
        }
        return specifications;
    }

    public Specifications<LuCafeHistory> addSpecificationLuCafeHistory(Specifications<LuCafeHistory> specifications, InstructionSpecification addItem) {
        if(specifications == null) {
            specifications = Specifications.where(addItem);
        } else {
            specifications = specifications.and(addItem);
        }
        return specifications;
    }

    public Specifications<Claim> addSpecificationClaim(Specifications<Claim> specifications, InstructionSpecification addItem) {
        if(specifications == null) {
            specifications = Specifications.where(addItem);
        } else {
            specifications = specifications.and(addItem);
        }
        return specifications;
    }

    public Specifications<FinanceContract> addSpecificationFinanceContract(Specifications<FinanceContract> specifications, InstructionSpecification addItem) {
        if(specifications == null) {
            specifications = Specifications.where(addItem);
        } else {
            specifications = specifications.and(addItem);
        }
        return specifications;
    }

    public Specifications<ShippingLineCompanyMaster> addSpecificationShippingLineCompanyMaster(Specifications<ShippingLineCompanyMaster> specifications, InstructionSpecification addItem) {
        if(specifications == null) {
            specifications = Specifications.where(addItem);
        } else {
            specifications = specifications.and(addItem);
        }
        return specifications;
    }

    public Specifications<CourierMaster> addSpecificationCourierMaster(Specifications<CourierMaster> specifications, InstructionSpecification addItem) {
        if(specifications == null) {
            specifications = Specifications.where(addItem);
        } else {
            specifications = specifications.and(addItem);
        }
        return specifications;
    }

    public Specifications<GradeMaster> addSpecificationGradeMaster(Specifications<GradeMaster> specifications, InstructionSpecification addItem) {
        if(specifications == null) {
            specifications = Specifications.where(addItem);
        } else {
            specifications = specifications.and(addItem);
        }
        return specifications;
    }

    public Specifications<PackingMaster> addSpecificationPackingMaster(Specifications<PackingMaster> specifications, InstructionSpecification addItem) {
        if(specifications == null) {
            specifications = Specifications.where(addItem);
        } else {
            specifications = specifications.and(addItem);
        }
        return specifications;
    }

    public Specifications<WarehouseMaster> addSpecificationWarehouseMaster(Specifications<WarehouseMaster> specifications, InstructionSpecification addItem) {
        if(specifications == null) {
            specifications = Specifications.where(addItem);
        } else {
            specifications = specifications.and(addItem);
        }
        return specifications;
    }

    public Specifications<PortMaster> addSpecificationPortMaster(Specifications<PortMaster> specifications, InstructionSpecification addItem) {
        if(specifications == null) {
            specifications = Specifications.where(addItem);
        } else {
            specifications = specifications.and(addItem);
        }
        return specifications;
    }

    public Specifications<OriginMaster> addSpecificationOriginMaster(Specifications<OriginMaster> specifications, InstructionSpecification addItem) {
        if(specifications == null) {
            specifications = Specifications.where(addItem);
        } else {
            specifications = specifications.and(addItem);
        }
        return specifications;
    }

    public Specifications<LocationMaster> addSpecificationLocationMaster(Specifications<LocationMaster> specifications, InstructionSpecification addItem) {
        if(specifications == null) {
            specifications = Specifications.where(addItem);
        } else {
            specifications = specifications.and(addItem);
        }
        return specifications;
    }

    public Specifications<DeliveryInstruction> getSpecifications(Integer clientId, Integer status) {
        Specifications<DeliveryInstruction> specifications = null;
        if(clientId != null) {
            specifications = addSpecification(specifications, InstructionSpecification.getClientEqualSpecification(clientId));
        }
        if(status != null) {
            specifications = addSpecification(specifications, InstructionSpecification.getStatusEqualSpecification(status));
        }
        return specifications;
    }

    public Specifications<ProcessingInstruction> getSpecificationsProcessing(Integer clientId, Integer status) {
        Specifications<ProcessingInstruction> specifications = null;
        if(clientId != null) {
            specifications = addSpecificationProcessing(specifications, InstructionSpecification.getClientEqualSpecification(clientId));
        }
        if(status != null) {
            specifications = addSpecificationProcessing(specifications, InstructionSpecification.getStatusEqualSpecification(status));
        }
        return specifications;
    }

    public Specifications<DeliveryInstruction> getSpecifications(List<InstructionSpecification> instructionSpecifications) {
        Specifications<DeliveryInstruction> specifications = null;
        for(InstructionSpecification item: instructionSpecifications) {
            specifications = addSpecification(specifications, item);
        }
        return specifications;
    }

    public Specifications<DeliveryInstruction> getSpecificationsByCriterias(List<SearchCriteria> searchCriterias) {
        Specifications<DeliveryInstruction> specifications = null;
        for(SearchCriteria item: searchCriterias) {
            specifications = addSpecification(specifications, new InstructionSpecification(item));
        }
        return specifications;
    }

    public Specifications<ProcessingInstruction> getSpecificationsByCriteriasProcessing(List<SearchCriteria> searchCriterias) {
        Specifications<ProcessingInstruction> specifications = null;
        for(SearchCriteria item: searchCriterias) {
            specifications = addSpecificationProcessing(specifications, new InstructionSpecification(item));
        }
        return specifications;
    }

    public Specifications<ShippingInstruction> getSpecificationsByCriteriasShipping(List<SearchCriteria> searchCriterias) {
        Specifications<ShippingInstruction> specifications = null;
        for(SearchCriteria item: searchCriterias) {
            specifications = addSpecificationShipping(specifications, new InstructionSpecification(item));
        }
        return specifications;
    }

    public Specifications<SampleSent> getSpecificationsByCriteriasSampleSent(List<SearchCriteria> searchCriterias) {
        Specifications<SampleSent> specifications = null;
        for(SearchCriteria item: searchCriterias) {
            specifications = addSpecificationSampleSent(specifications, new InstructionSpecification(item));
        }
        return specifications;
    }

    public Specifications<CompanyMaster> getSpecificationsByCriteriasCompanyMaster(List<SearchCriteria> searchCriterias) {
        Specifications<CompanyMaster> specifications = null;
        for(SearchCriteria item: searchCriterias) {
            specifications = addSpecificationCompanyMaster(specifications, new InstructionSpecification(item));
        }
        return specifications;
    }

    public Specifications<Transaction> getSpecificationsByCriteriasTransaction(List<SearchCriteria> searchCriterias) {
        Specifications<Transaction> specifications = null;
        for(SearchCriteria item: searchCriterias) {
            specifications = addSpecificationTransaction(specifications, new InstructionSpecification(item));
        }
        return specifications;
    }

    public Specifications<Invoice> getSpecificationsByCriteriasInvoice(List<SearchCriteria> searchCriterias) {
        Specifications<Invoice> specifications = null;
        for(SearchCriteria item: searchCriterias) {
            specifications = addSpecificationInvoice(specifications, new InstructionSpecification(item));
        }
        return specifications;
    }

    public Specifications<LuCafeHistory> getSpecificationsByCriteriasLuCafeHistory(List<SearchCriteria> searchCriterias) {
        Specifications<LuCafeHistory> specifications = null;
        for(SearchCriteria item: searchCriterias) {
            specifications = addSpecificationLuCafeHistory(specifications, new InstructionSpecification(item));
        }
        return specifications;
    }

    public Specifications<Claim> getSpecificationsByCriteriasClaim(List<SearchCriteria> searchCriterias) {
        Specifications<Claim> specifications = null;
        for(SearchCriteria item: searchCriterias) {
            specifications = addSpecificationClaim(specifications, new InstructionSpecification(item));
        }
        return specifications;
    }

    public Specifications<FinanceContract> getSpecificationsByCriteriasFinanceContract(List<SearchCriteria> searchCriterias) {
        Specifications<FinanceContract> specifications = null;
        for(SearchCriteria item: searchCriterias) {
            specifications = addSpecificationFinanceContract(specifications, new InstructionSpecification(item));
        }
        return specifications;
    }

    public Specifications<ShippingLineCompanyMaster> getSpecificationsByCriteriasShippingLineCompanyMaster(List<SearchCriteria> searchCriterias) {
        Specifications<ShippingLineCompanyMaster> specifications = null;
        for(SearchCriteria item: searchCriterias) {
            specifications = addSpecificationShippingLineCompanyMaster(specifications, new InstructionSpecification(item));
        }
        return specifications;
    }

    public Specifications<CourierMaster> getSpecificationsByCriteriasCourierMaster(List<SearchCriteria> searchCriterias) {
        Specifications<CourierMaster> specifications = null;
        for(SearchCriteria item: searchCriterias) {
            specifications = addSpecificationCourierMaster(specifications, new InstructionSpecification(item));
        }
        return specifications;
    }

    public Specifications<GradeMaster> getSpecificationsByCriteriasGradeMaster(List<SearchCriteria> searchCriterias) {
        Specifications<GradeMaster> specifications = null;
        for(SearchCriteria item: searchCriterias) {
            specifications = addSpecificationGradeMaster(specifications, new InstructionSpecification(item));
        }
        return specifications;
    }

    public Specifications<PackingMaster> getSpecificationsByCriteriasPackingMaster(List<SearchCriteria> searchCriterias) {
        Specifications<PackingMaster> specifications = null;
        for(SearchCriteria item: searchCriterias) {
            specifications = addSpecificationPackingMaster(specifications, new InstructionSpecification(item));
        }
        return specifications;
    }

    public Specifications<WarehouseMaster> getSpecificationsByCriteriasWarehouseMaster(List<SearchCriteria> searchCriterias) {
        Specifications<WarehouseMaster> specifications = null;
        for(SearchCriteria item: searchCriterias) {
            specifications = addSpecificationWarehouseMaster(specifications, new InstructionSpecification(item));
        }
        return specifications;
    }

    public Specifications<PortMaster> getSpecificationsByCriteriasPortMaster(List<SearchCriteria> searchCriterias) {
        Specifications<PortMaster> specifications = null;
        for(SearchCriteria item: searchCriterias) {
            specifications = addSpecificationPortMaster(specifications, new InstructionSpecification(item));
        }
        return specifications;
    }

    public Specifications<OriginMaster> getSpecificationsByCriteriasOriginMaster(List<SearchCriteria> searchCriterias) {
        Specifications<OriginMaster> specifications = null;
        for(SearchCriteria item: searchCriterias) {
            specifications = addSpecificationOriginMaster(specifications, new InstructionSpecification(item));
        }
        return specifications;
    }

    public Specifications<LocationMaster> getSpecificationsByCriteriasLocationMaster(List<SearchCriteria> searchCriterias) {
        Specifications<LocationMaster> specifications = null;
        for(SearchCriteria item: searchCriterias) {
            specifications = addSpecificationLocationMaster(specifications, new InstructionSpecification(item));
        }
        return specifications;
    }

    private String getNormalPagingItemByPageNo(Integer pageNo, Integer currentPage) {
        //"<li class=\"{0}\" ><a data-value=\"{1}\" {2} {3}>{4}</a></li>";
        boolean isCurrentPage = pageNo == currentPage;
        return getPagingItem(isCurrentPage ? "selected": "", pageNo.toString(), pageNo.toString());
    }

    private String getPagingItem(String klass, String dataValue, String name) {
        return MessageFormat.format(singleLi, new Object[]{klass, dataValue, name});
    }

    private String getHrefLink(boolean disabled) {
        if(disabled) {
            return href;
        }
        return "";
    }

    private String getNextLink(Integer currentPage, Integer totalPage) {
        return getPagingItem(currentPage + 1 > totalPage ? "disabled": "", Integer.toString(currentPage + 1), "Next");
    }

    private String getPreviousLink(Integer currentPage, Integer totalPage) {
        return getPagingItem(currentPage == 1 ? "disabled": "", Integer.toString(currentPage - 1), "Previous");
    }

    private String getFirstLink(Integer currentPage, Integer totalPage) {
        return getPagingItem(currentPage == 1 ? "disabled": "", "1", "First");
    }

    private String getLastLink(Integer currentPage, Integer totalPage) {
        return getPagingItem(currentPage + 1 > totalPage ? "disabled": "", totalPage.toString(), "Last");
    }

    private String getPreviousDots(Integer currentPage, Integer totalPage) {
        if(currentPage - margin > 1) {
           return getPagingItem("disabled ", "", "...");
        }
        return "";
    }

    private String getNextDots(Integer currentPage, Integer totalPage) {
        if(currentPage + margin < totalPage) {
            return getPagingItem("disabled ", "", "...");
        }
        return "";
    }

    public String getPagingHtml(Integer totalPage, Integer currentPage) {
        // currentpage: 1based, however, spring calculate base on zero-based
        String paging = "<ul class='pagination'>{0}</ul>";
        String lis = "";
        lis += getFirstLink(currentPage, totalPage);
        lis += getPreviousLink(currentPage, totalPage);
        lis += getPreviousDots(currentPage, totalPage);


        for(int i = currentPage - margin; i < currentPage; i++) {
            if(i > 0) {
                lis += getNormalPagingItemByPageNo(i, currentPage);
            }
        }
        lis += getNormalPagingItemByPageNo(currentPage, currentPage);
        for(int i = currentPage + 1; i <= currentPage + margin && i <= totalPage; i++) {
            lis += getNormalPagingItemByPageNo(i, currentPage);
        }


        lis += getNextLink(currentPage, totalPage);
        lis += getLastLink(currentPage, totalPage);
        lis = MessageFormat.format(paging, new Object[]{lis});
        lis += script;
        lis += hiddenInputPageNo;

        return lis;

    }


}
