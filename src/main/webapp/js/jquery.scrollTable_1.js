/*global jQuery */
/*!
* ScrollTable.js 1.2
*
* Copyright 2012, AJ Webb http://ajwebb.me
* Released under the MIT license
* http://opensource.org/licenses/MIT
*
* @link https://github.com/webbushka/scrollTable
*
*/

(function($) {

	var scrollbarWidth = 0;

	/*! http://jdsharp.us/jQuery/minute/calculate-scrollbar-width.php */

	function getScrollbarWidth() {
		if (scrollbarWidth) return scrollbarWidth;
		var div = $('<div style="width:50px;height:50px;overflow:hidden;position:absolute;top:-200px;left:-200px;"><div style="height:100px;"></div></div>');
		$('body').append(div);
		var w1 = $('div', div).innerWidth();
		div.css('overflow-y', 'auto');
		var w2 = $('div', div).innerWidth();
		$(div).remove();
		scrollbarWidth = (w1 - w2);
		return scrollbarWidth;
	}

	$.fn.scrollTable = function(options) {

		//extend the options and defaults
		var settings = $.extend({
			headerClass: 'scrollHeader',
			headerHeight: $(this).find('thead').height() || $(this).find('tr:eq(0)').height(),
			scrollBar: true,
			scrollClass: 'scrollArea',
			scrollHeight: 300,
                        footerClass: 'scrollFooter',
			footerHeight: $(this).find('tfoot').height() || $(this).find('tr:eq(0)').height()
		}, options);

		return this.each(function() {

			//if this function was already applied to the table don't do it again.
			if ($(this).data('scrolltable')) {
				return;
			}

			//adjust the width of the table to account for scroll bar
			settings.scrollBarWidth = getScrollbarWidth();
			$(this).width($(this).width() - settings.scrollBarWidth);

			settings.tableWidth = $(this).width();

			$(this).css({
				padding: 0,
				margin: 0
			});

			var $this = $(this);
			var clone = $this.clone(true, true);
////console.log($this.attr('class'));
			//add scrolltable to the data of both the original object and its clone
			$this.data('scrolltable', true);
			clone.data('scrolltable', true);

			clone.find('tbody').css({
				visibility: 'hidden'
			});

			//strip all the ids and data out of the clone
			clone.removeAttr('id');
			clone.removeData();
			clone.find('div, span, p, ul, li, a, input, label, td, tr, th, tbody, thead, tfoot, table').removeAttr('id').removeAttr('checked').removeAttr('name').removeData();
$this.find('tfoot').css({
				visibility: 'hidden'
			});
			$this.css({
				'margin-top': -settings.headerHeight,
                                'margin-bottom': -settings.footerHeight
			}).wrap($('<div />', {
				'class': settings.scrollClass
			}).css({
				'max-height': settings.scrollHeight,
				'overflow-y': 'auto',
				'overflow-x': 'hidden',
				width: (settings.scrollBar) ? settings.tableWidth + settings.scrollBarWidth : settings.tableWidth
			}));
                        
                        var clone_footer = clone.clone(true, true);
//                        clone_footer.data('scrolltable', true);
//                        clone.find('tfoot').remove();
			$this.parent('.' + settings.scrollClass).before($('<div />', {
				'class': settings.headerClass
			}).css({
				height: settings.headerHeight,
				overflow: 'hidden',
				width: settings.tableWidth
			}).append(clone));
                        clone_footer.find('tbody, thead').remove();
                        $this.parent('.' + settings.scrollClass).after($('<div />', {
				'class': settings.footerClass
			}).css({
				height: settings.footerHeight,
				overflow: 'hidden',
				width: settings.tableWidth
			}).append(clone_footer));
		});
	};

})(jQuery);
