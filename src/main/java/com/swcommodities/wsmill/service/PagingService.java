package com.swcommodities.wsmill.service;

import java.text.MessageFormat;

import org.springframework.stereotype.Service;

@Service
public class PagingService {
	
	private Integer margin = 3;
    private String singleLi = "<li class=\"single-item {0}\" ><a data-page=\"{1}\">{2}</a></li>";
    private String script = "<script>$(document).ready(function(){$('.single-item:not(.selected):not(.disabled) a').click(   function(){var pageNo = $(this).data('page'); $('#pageNo').val(pageNo); var _f = $(this).closest('form'); _f.find('.js_date').each(function () {if($(this).val() == '') {$(this).attr('disabled', 'disabled');}}); _f.submit();}  )});</script>";
    private String hiddenInputPageNo = "<input name='pageNo' type='hidden' id='pageNo' value='1' />";

	private String getNormalPagingItemByPageNo(Integer pageNo, Integer currentPage) {
        //"<li class=\"{0}\" ><a data-value=\"{1}\" {2} {3}>{4}</a></li>";
        boolean isCurrentPage = pageNo == currentPage;
        return getPagingItem(isCurrentPage ? "selected": "", pageNo.toString(), pageNo.toString());
    }

    private String getPagingItem(String klass, String dataValue, String name) {
        return MessageFormat.format(singleLi, new Object[]{klass, dataValue, name});
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

    public String getPagingHtml(Integer totalPage, Integer currentPage) {
        // currentpage: 1based, however, spring calculates based on zero
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
