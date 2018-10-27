(function($) {

	var reset_data = {
		text : "",
		radio : true, // uncheck all if true
		checkbox : true, // uncheck all if true
		textarea : "",
		select : -1,
		hidden : "",
		label : {
			reset : false,
			value : ""
		},
		param : {
			reset : false,
			value : ""
		}
	};

	var confirm_dialog = {
		reason : true,
		language : "en",
		turnOffLayout : false
	};

	var dialog = {
		status : "new",
		yes : "",
		no : ""
	};

	$.resetForm = function(opts) {
		$.extend(reset_data, opts);
		var ext = reset_data.ext;

		$("input[type=text]").val(reset_data.text);

		if (reset_data.radio) {
			$("input[type=radio]").prop("checked", false);
		}

		if (reset_data.checkbox) {
			$("input[type=checkbox]").prop("checked", false);
		}

		$("select").val(reset_data.select);
		$("input[type=hidden]").val(reset_data.hidden);

		if (reset_data.label.reset) {
			$("label").text(reset_data.label.value);
		}

		if (reset_data.param.reset) {
			$("p").text(reset_data.label.value);
		}

		if (ext != undefined) {
			if (ext.length != undefined) {
				for (var i = 0; i < ext.length; i++) {
					var type = ext[i].type;
					var el = ext[i].element;
					var val = ext[i].value;
					if (type == "text") {
						$(el).text(val);
					}
					if (type == "input" || type == "val") {
						$(el).val(val);
					}
				}
			} else {
				var type = ext.type;
				var el = ext.element;
				var val = ext.value;
				if (type == "text") {
					$(el).text(val);
				}
				if (type == "input" || type == "val") {
					$(el).val(val);
				}
			}
		}
	};
	$.fn.callMessageDialog = function(options) {
		if ($('#message_box').length !== 0) {
			_removeElementId("message_box");
		}
		var template = "<div id='message_box' class='" + options.type
				+ " box center'>" + options.message + "</div>";
		if ($("#overlay").length == 0) {
			$('body').append("<div id='overlay'></div>");
		}
		$('#overlay').css('opacity', 0.6).fadeIn(400);

		$('body').append(template);

		$("#overlay").click(function() {
			$('#message_box').slideUp('fast', function() {
				$('#message_box').remove();
			});
			$('#overlay').fadeOut(800, function() {
				$('#overlay').remove();
			});
		});

		$('#message_box').slideDown(400);
		$("input[type=button]").button();
		if (window['turnOffLayout'] != undefined) {
			turnOffLayout = false;
		}
	};

	$.fn.callDialog = function(body_data_name, options) {

		var autoClose = (options != undefined && options.autoCloseOverlay != undefined) ? options.autoCloseOverlay
				: true;
		var isAppendBody = (options != undefined && options.isAppendBody != undefined) ? options.isAppendBody
				: true;
		var data = body_data_name;
		var template_body = "    {{:title}}\n" + "<ul class='list_error'>\n"
				+ "    {{for msg}}\n" + "    <li>\n" + "        {{:a_row}}\n"
				+ "    </li>\n" + "    {{/for}}\n" + "</ul>";
		if (options != undefined && options.template != undefined) {
			template_body = options.template;
		}

		var type = (options != undefined && options.type != undefined) ? options.type
				: "error";

		var template = "<div id='message_box' class='" + type
				+ " box center'>\n" + template_body + "</div>";

		if (isAppendBody) {
			data = $.data(document.body, body_data_name);
		}

		if ($('#message_box').length !== 0) {
			_removeElementId("message_box");
		}

		if ($("#overlay").length === 0) {
			$('body').append("<div id='overlay'></div>");
		}
		$('#overlay').css('opacity', 0.6).fadeIn(400);

		$("body").append($.templates(template).render(data));

		if (autoClose) {
			$("#overlay").click(function() {
				$('#message_box').slideUp('fast', function() {
					$('#message_box').remove();
				});
				$('#overlay').fadeOut('slow', function() {
					$('#overlay').remove();
				});
			});
		} else {
			$("#message_box")
					.append(
							"<input type='button' id='closeMsgBox' value='Close' style='float: right'/>");
			$("#closeMsgBox").click(function() {
				$('#message_box').slideUp('fast', function() {
					$('#message_box').remove();
				});
				$('#overlay').fadeOut('slow', function() {
					$('#overlay').remove();
				});
			});
		}

		$('#message_box').slideDown('fast');
		$("input[type=button]").button();
		if (window['turnOffLayout'] != undefined) {
			turnOffLayout = false;
		}
	};

	$.closeDialog = function(el) {
		$(el).slideUp('fast', function() {
			$(el).remove();
		});
		$('#overlay').fadeOut('slow', function() {
			$('#overlay').remove();
		});
	};

	$.error = function(message) {
		$.fn.callMessageDialog({
			type : "error",
			message : message
		});
	};

	$.info = function(message) {
		$.fn.callMessageDialog({
			type : "info",
			message : message
		});
	};

	$.success = function(message) {
		$.fn.callMessageDialog({
			type : "success",
			message : message
		});
	};

	$.warning = function(message) {
		$.fn.callMessageDialog({
			type : "warning",
			message : message
		});
	};
	//
	// $.fn.callDialog = function(body_data_name, options) {
	// if ($('#message_box').length !== 0) {
	// _removeElementId("message_box");
	// }
	// var template = "<div id='message_box' class='" + options.type + " box
	// center'>\n" +
	// " {{:title}}\n" +
	// "<ul class='list_error'>\n" +
	// " {{for msg}}\n" +
	// " <li>\n" +
	// " {{:a_row}}\n" +
	// " </li>\n" +
	// " {{/for}}\n" +
	// "</ul></div>";
	//
	// if ($("#overlay").length === 0) {
	// $('body').append('<div id="overlay"></div>');
	// }
	// $('#overlay').css('opacity', 0.6).fadeIn(400);
	//
	// $("body").append($.templates(template).render($.data(document.body,
	// body_data_name)));
	// if (options.autoCloseOverlay !== undefined) {
	// if (options.autoCloseOverlay) {
	// $("#overlay").click(function() {
	// $('#message_box').slideUp('fast', function() {
	// $('#message_box').remove();
	// });
	// $('#overlay').fadeOut('slow', function() {
	// $('#overlay').remove();
	// });
	// });
	// }
	// }
	//
	// $('#message_box').slideDown('fast');
	// $("input[type=button]").button();
	// };
	// action, json, status, functions, asynchronous, optional
	$.sendRequest = function(options, callbackfunc) {
		var target = options.target != undefined ? options.target : "body";
		var action = options.action;
		var json = options.data;
		var status = options.status;
		var functions = options.functions;
		var asynchronous = options.asynchronous;
		var optional = options.optional;
		var formatDecimal = 2;
		var func = options.func;
		var opac = 0.6;
                var headers = options.headers || {};
		var turnOffLayout = (options != undefined && options.turnOffLayout != undefined) ? options.turnOffLayout
				: true;
		if (optional != undefined) {
			if (optional.opac != undefined) {
				opac = optional.opac;
			}
		}
		url_type = ".htm";
		if (optional != undefined && optional.url_type == 'json') {
			url_type = ".json";
		} else if (optional != undefined && optional.url_type == 'html') {
			url_type = ".html";
		}
		// url_type = (optional != undefined && optional.url_type == 'json') ?
		// ".json" : ".htm";
		var as = true;
		if (asynchronous != undefined) {
			as = asynchronous;
		}
		if ($("#overlay").length == 0) {
			$('body')
					.append(
							'<div id="overlay"></div><div id="preloader">Loading..</div>');
		}
		$('#overlay').css('opacity', opac).fadeIn(400);
		$('#preloader').fadeIn(600);
		request = $
				.ajax(
						{
							type : 'POST',
                                                        headers: headers,
							async : as,
							data : json,
							url : getAbsolutePath() + "/" + action + url_type,
							success : function(msg) {
								switch (status) {
								case "replace_html":
									$(target).html(msg);
									break;
								case "append_html":
									// console.log(target + ": " +
									// $(target).text());
									$(target).append(msg);
									break;
								case "replace_text":
									$(target).text(msg);
									break;
								case "text_value":
									$(target).val(msg);
									break;
								case "open_dialog":
									console.log(msg);
									if (optional != undefined) {
										if (optional.action == undefined) {
											if ($('#detailBox').length == 0) {
												$(target)
														.append(
																"<div id='detailBox'></div>");
											}
											$("#detailBox").html(msg);
											var dialog_styles = optional.styles;
											if (dialog_styles !== undefined
													&& dialog_styles !== null
													&& dialog_styles.length > 0) {
												for (var i = 0; i < dialog_styles.length; i++) {
													$("#detailBox")
															.css(
																	dialog_styles[i].name,
																	dialog_styles[i].style);
												}
											}
											$('#preloader').fadeOut(300);
											$('#detailBox').fadeIn(400);
										}
										if (optional.action == "apply_template") {

											var template = optional.template;
											var data = msg;
											if (typeof (msg) == "string") {
												data = JSON.parse(msg);
											}

											var type = optional.type != undefined ? optional.type
													: "info";
											var options = {
												template : template,
												type : type,
												isAppendBody : false
											};
											if (data.length != undefined) {
												for (var i = 0; i < data.length; i++) {
													console.log(data[i]);
													console.log(options);
													$.fn.callDialog(data[i],
															options);
												}
											} else {
												$.fn.callDialog(data, options);
											}
										}
									}
									var autoClose = optional.autoClose;
									if (autoClose) {
										$("#overlay").click(function() {
											_closeOpacity();
										});
									}
									break;
								case "append_data":
									var msg_array = JSON.parse(msg);
									// console.log("JSON : " + msg);
									if (optional != undefined
											&& optional.length == msg_array.length) {
										for (var i = 0; i < msg_array.length; i++) {
											$.data(document.body, optional[i],
													msg_array[i]);
										}
									}
									break;
								case "simple_dialog":
									switch (optional) {
									case "error":
										$.error(msg);
										break;
									case "info":
										$.info(msg);
										break;
									case "warning":
										$.warning(msg);
										break;
									}
									break;
								case "apply_template":
									var msg_data = JSON.parse(msg);
									// an element (id, class, etc.)
									var tmpl = $.templates(optional.tmpl);
									var opt = (optional.opt != undefined) ? optional.opt
											: "replace";
									if (opt == "replace") {
										$(target).html(tmpl.render(msg_data));
									}
									if (opt == "append") {
										$(target).append(tmpl.render(msg_data));
									}
									if (opt == "prepend") {
										$(target).append(tmpl.render(msg_data));
									}
									if (optional.dataBody != undefined) {
										$.data(document.body,
												optional.dataBody, msg_data);
									}
									break;

								case "export":
									console.log(msg);
									window.open(msg);

									break;
								case "handle_response":
									// $.data(document.body,"response_data",msg);

									if (func != undefined) {
										if (typeof (func) == "function") {
											eval('(' + func + '())');
										} else if (typeof (func) == "object") {
											_applyFunction(func);
										}
									}
									break;
								}
								if (callbackfunc != undefined) {
									var res = callbackfunc(msg);
									turnOffLayout = res != undefined ? res
											: false;
								}

							},
							error : function(msg) {
								// $.error(msg);
								$.fn.callMessageDialog({
									type : "error",
									message : msg.responseText
								});
								$("#message_box").toggleClass("center");
								$("#message_box style").remove();
								$("#message_box").toggleClass("internalerror");
								// $("#message_box").css("width","auto");
								// $("#message_box").css("font-size","11px");
								// $("#message_box").css("left","7%");
								// $("#message_box").css("height","500px");
								// $("#message_box").css("overflow","auto");
							}
						}).done(function() {

					if (optional != undefined) {
						if (optional.decimal != undefined) {
							formatDecimal = optional.decimal;
						}
					}
					$.formatNumber(formatDecimal);
					if (status == "simple_dialog" || status == "open_dialog") {
						turnOffLayout = false;
					}
					if (turnOffLayout) {
						$('#overlay').fadeOut(400);
						$('#overlay').remove();
					}
					$('#preloader').fadeOut(300);
					$("input[type=button]").button();
					$("input[type=submit]").button();
					$('#preloader').remove();
					$(".date_picker_text").datepicker({
						dateFormat : "dd-M-y",
						changeMonth : true,
						changeYear : true
					});
					if (functions != undefined) {
						if (typeof (functions) == "function") {
							eval('(' + functions + '())');
						} else if (typeof (functions) == "object") {
							_applyFunction(functions);
						}
					}
					// _applyFunction(functions);
					$(".timePicker").timepicker();
				});
	};

	$.ConfirmDialog = function(message, opts) {
		var language = (opts != undefined && opts.language != undefined) ? opts.language
				: "en";
		if ($('#confirm_box').length !== 0) {
			_removeElementId("confirm_box");
		}
		var template = (language == "en") ? "<div id='confirm_box' class='confirm_box'><h1 style='font-size: 13px'>"
				+ message
				+ "</h1>\n\
                                    <div style=''><label style='display: none; color: red; height:20px; position: absolute; top: 5px' id='reason_stt'></label><textarea id='reason' style='width: 100%; height: 50px' placeholder='Input reason here...'></textarea>\n\
                                    <input type='button' id='confirm_no' value='No' style='float: right'/>\n\
                                    <input type='button' id='confirm_yes' value='Yes' style='float: right'/></div>\n\
                        </div>"
				: "<div id='confirm_box' class='confirm_box'><h1 style='font-size: 13px'>"
						+ message
						+ "</h1>\n\
                                    <div style=''><label style='display: none; color: red; height:20px; position: absolute; top: 5px' id='reason_stt'></label><textarea id='reason' style='width: 100%; height: 50px' placeholder='nguyên nhân...'></textarea>\n\
                                    <input type='button' id='confirm_no' value='Không' style='float: right'/>\n\
                                    <input type='button' id='confirm_yes' value='Đồng Ý' style='float: right'/></div>\n\
                        </div>";
		if ($("#overlay").length === 0) {
			$('body').append("<div id='overlay'></div>");
		}
		$('#overlay').css('opacity', 0.6).fadeIn(400);

		$('body').append(template);
		$('#confirm_box').slideDown(800);
		$("input[type=button]").button();

	};

	$.callConfirm = function(message, resultCallback, opts) {
		$.extend(confirm_dialog, opts);
		$.ConfirmDialog(message, opts);
		$(document).on(
				"click",
				"#confirm_yes",
				function() {

					var reason = $("#reason").val();
					if (reason === "") {
						if (confirm_dialog.reason) {
							if (confirm_dialog.language == "en") {
								$("#reason_stt").html(
										"*The reason is required!").show();
							} else {
								$("#reason_stt").html(
										"*Vui lòng nhập nguyên nhân!").show();
							}
						} else {
							resultCallback(true, reason);
							$('#confirm_box').remove();
							_removeElementId("overlay");
						}

					} else {
						resultCallback(true, reason);
						$('#confirm_box').remove();
						_removeElementId("overlay");
					}

				});
		$(document).on("click", "#confirm_no", function() {
			resultCallback(false);
			$('#confirm_box').remove();
			_removeElementId("overlay");
		});

		if (confirm_dialog.turnOffLayout) {
			$(document).on("click", "#overlay", function() {
				$('#confirm_box').remove();
				_removeElementId("overlay");
			});
		}

	};

	$.validation = function(opts, callbackfunc) {
		var validator = true;
		var data = {};
		var a_row = [];
		if (opts != undefined && $.isArray(opts)) {
			for (var i = 0; i < opts.length; i++) {
				var msg = opts[i].msg;
				var el = opts[i].element;
				var more_opts = (opts[i].opts != undefined) ? opts[i].opts
						: undefined;
				if (!_checkElEmpty(el, more_opts)) {
					validator = false;
					a_row.push({
						a_row : msg
					});
				}
			}
			if (a_row.length > 0) {
				data["title"] = "Some elements don't match requirement";
				data["msg"] = a_row;
				$.fn.callDialog(data);
			}
			if (callbackfunc != undefined && validator == true) {
				callbackfunc(validator);
			}
			return validator;
		}
		return validator;
	};

	function _checkElEmpty(el, opts) {
		if ($(el).length > 0) {
			if (el[0] == "#" || el[0] == ".") { /* id(class) */
				if ($(el).is("input[type=text]") || $(el).is("select")
						|| $(el).is("input[type=password]")) {
					if ($(el).val() != "") {
						if (opts != undefined) {
							var type = opts.type;
							var value = opts.value != undefined ? opts.value
									: "";
							var cur = $(el).val();
							switch (type) {
							case "==":
								return (cur == value) ? true : false;
							case ">":
								return ($.isNumeric(cur) && $.isNumeric(value) && cur > value) ? true
										: false;
							case ">=":
								return ($.isNumeric(cur) && $.isNumeric(value) && cur >= value) ? true
										: false;
							case "<":
								return ($.isNumeric(cur) && $.isNumeric(value) && cur < value) ? true
										: false;
							case "<=":
								return ($.isNumeric(cur) && $.isNumeric(value) && cur <= value) ? true
										: false;
							case "!=":
								return (cur != value) ? true : false;
							case "%%":
								return (cur.indexOf(value) > 0) ? true : false;
							case "len": {
								if (opts.limit != undefined) {
									var min = opts.limit.min != undefined ? opts.limit.min
											: 0;
									var max = opts.limit.max != undefined ? opts.limit.max
											: 1000;
									return (cur.length > min && cur.length < max) ? true
											: false;
								}
							}
							default:
								return false;
							}
						}
						return true;
					} else {
						return false;
					}
				} else if ($(el).is("input[type=radio]")
						|| $(el).is("input[type=checkbox]")) {
					return $(el).is("checked") ? true : false;
				} else {
					return $(el).text() != "" ? true : false;
				}
			} else if (el.indexOf("name") > 0) {
				if ($(el).is("input[type=text]") || $(el).is("select")) {
					return ($(el).val() != "") ? true : false;
				} else {
					return $(el).text() != "" ? true : false;
				}
			}
		} else {
			return true;
		}
	}

	$.getParameter = function(options) {
		var parameterName = options.parameter + "=";
		var queryString = window.top.location.search.substring(1);
		if (queryString.length > 0) {
			// Find the beginning of the string
			begin = queryString.indexOf(parameterName);
			// If the parameter name is not found, skip it, otherwise return the
			// value
			if (begin != -1) {
				// Add the length (integer) to the beginning
				begin += parameterName.length;
				// Multiple parameters are separated by the "&" sign
				end = queryString.indexOf("&", begin);
				if (end == -1) {
					end = queryString.length;
				}
				// Return the string
				return unescape(queryString.substring(begin, end));
			}
			// Return "null" if no parameter has been found
			return "null";
		}
	};

	$.getData = function(opt) {
		// if (opt != undefined) {
		// var name = opt.name;
		// var data_container = (opt.container != undefined) ? opt.container :
		// document.body;
		var data = {};
		var text_arr = $("[rq_type=text]");
		var paragraph_arr = $("input[rq_type=html],p[rq_type=html]");
		for (var i = 0; i < text_arr.length; i++) {
			if ($(text_arr[i]).is("input[type=text],input[type=hidden]")
					|| $(text_arr[i]).is("select")) {
				if ($(text_arr[i]).attr("id") != undefined
						&& $(text_arr[i]).attr("id") != "") {
					var key = $(text_arr[i]).attr("id");
					var value = $(text_arr[i]).val();
					data[key] = value;
				}
			} else if ($(text_arr[i]).is(
					"input[type=radio],input[type=checkbox]")) {
				if ($(text_arr[i]).attr("id") != undefined
						&& $(text_arr[i]).attr("id") != "") {
					var key = $(text_arr[i]).attr("id");
					var value = $(text_arr[i]).prop("checked") ? 1 : 0;
					data[key] = value;
				}
			} else {
				if ($(text_arr[i]).attr("id") != undefined
						&& $(text_arr[i]).attr("id") != "") {
					var key = $(text_arr[i]).attr("id");
					var value = $.trim($(text_arr[i]).text());
					data[key] = value;
				}
			}

		}
		for (var i = 0; i < paragraph_arr.length; i++) {
			if ($(paragraph_arr[i]).attr("id") != undefined
					&& $(paragraph_arr[i]).attr("id") != "") {
				var key = $(paragraph_arr[i]).attr("id");
				var value = $(paragraph_arr[i]).text();
				data[key] = value;
			}
		}
		if (opt != undefined) {
			var extend_data = opt.extend;
			if (extend_data != undefined) {
				for (var i = 0; i < extend_data.length; i++) {
					data[extend_data[i].key] = extend_data[i].value;
				}
			}
		}
		return data;
		// } else {
		// $.error("Data Must Content A Name");
		// }
	};

	$.parseData = function(opt) {
		var data = opt.data;
		if (data != undefined) {
			if ($.isArray(data)) {
				for (var i = 0; i < data.length; i++) {
					parseJson(data[i]);
				}
			} else {
				parseJson(data);
			}
		}
	};

	$.formatNumber = function(formatDecimal) {
		var dec = formatDecimal != undefined ? formatDecimal : 2;
		$(".formatNumber").each(
				function() {
					// console.log($(this));
					if ($(this).is("input")) {
						if ($.isNumeric($(this).val())) {
							var vl = accounting.toFixed(parseFloat($(this)
									.val()), dec);
							$(this).val(accounting.formatMoney(vl, "", dec));
						}
					} else {
						if ($.isNumeric($(this).text())) {
							var vl = accounting.toFixed(parseFloat($(this)
									.text()), dec);
							$(this).text(accounting.formatMoney(vl, "", dec));
						}
					}
				});
	};

	$.fn.formatNumber = function(formatDecimal) {
		var dec = formatDecimal != undefined ? formatDecimal : 2;
		var selector = $(this).selector;
		$(selector).each(
				function() {
					// console.log($(this));
					if ($(this).is("input")) {
						if ($.isNumeric($(this).val())) {
							var vl = accounting.toFixed(parseFloat($(this)
									.val()), dec);
							$(this).val(accounting.formatMoney(vl, "", dec));
						}
					} else {
						if ($.isNumeric($(this).text())) {
							var vl = accounting.toFixed(parseFloat($(this)
									.text()), dec);
							$(this).text(accounting.formatMoney(vl, "", dec));
						}
					}
				});
	};
	$.getEmptyData = function(opt) {
		var data = {};
		var text_arr = $("input:text[rq_type=text]");
		var paragraph_arr = $("input:text[rq_type=html]");
		for (var i = 0; i < text_arr.length; i++) {
			if ($(text_arr[i]).attr("id") != "") {
				var key = $(text_arr[i]).attr("id");
				data[key] = "";
			}
		}
		for (var i = 0; i < paragraph_arr.length; i++) {
			if ($(paragraph_arr[i]).attr("id") != "") {
				var key = $(paragraph_arr[i]).attr("id");
				data[key] = "";
			}
		}
		if (opt != undefined) {
			var extend_data = opt.extend;
			if (extend_data != undefined) {
				for (var i = 0; i < extend_data.length; i++) {
					data[extend_data[i].key] = extend_data[i].value;
				}
			}
		}
		return data;
	};

	// $.emptyForm = function(opt){
	// var rq_text = $("input[rq_type=text][type=text]").val("");
	// var rq_html = $("input[rq_type=html]");
	//        
	// };

	function parseJson(data) {
		if (data != undefined) {
			$
					.each(
							data,
							function(key, value) {
								if ($("#" + key).length > 0) {
									if ($("#" + key)
											.is(
													"input[type=text],input[type=hidden]")) {
										if ($("#" + key).attr("auto-fill") == undefined
												|| $("#" + key).attr(
														"auto-fill") == "true") {
											$("#" + key).val(value);
										}
									} else if ($("#" + key).is(
											"input[type=radio]")
											|| $("#" + key).is(
													"input[type=checkbox]")) {
										$("#" + key).val(value);
										if (value == 1) {
											$("#" + key).prop("checked", true);
										} else {
											$("#" + key).prop("checked", false);
										}
									} else if ($("#" + key).is("select")) {
										$("#" + key + " option")
												.each(
														function() {
															if ($(this).val() == value) {
																$(this)
																		.prop(
																				"selected",
																				true);
															}
														});
									} else {
										if ($("#" + key).attr("auto-fill") == undefined
												|| $("#" + key).attr(
														"auto-fill") == "true") {
											$("#" + key).text(value);
										}
									}
								}
							});
		}
	}

	function _applyFunction(f) {
		if (f != null) {
			for (var i = 0; i < f.length; i++) {
				window[f[i].name].apply(this, f[i].params);
			}
		}
	}

	// remove an element id
	function _removeElementId(id) {
		$("#" + id).remove();
	}

	function _closeOpacity() {
		$('#detailBox').fadeOut(200);
		$('#overlay').fadeOut(400);
		$('#overlay').remove();
		$('#preloader').remove();
		$('#detailBox').remove();

	}

	function _showLogin() {
		if ($("#overlay") !== true) {
			$('body')
					.append(
							'<div id="overlay"></div><div id="preloader">Loading..</div>');
		}
		$('#overlay').css('opacity', 0.6).fadeIn(400, function() {
			$('#preloader').fadeIn(600);
			$.ajax({
				type : 'POST',
				cache : false,
				url : getAbsolutePath() + "/show_login.htm",
				success : function(msg) {

					if ($('#detailBox') !== true) {
						$("form").append("<div id='detailBox'></div>");
					}
					$("#detailBox").html(msg);

					$('#preloader').fadeOut(300);
					$('#detailBox').fadeIn(400);
				}
			}).done(function() {
				$("#overlay").click(function() {
					closeOpacity();
				});
				$("input[type=button]").button();
				$(".close_opacity").click(function() {
					closeOpacity();
				});
				$('#preloader').remove();
			});
		});
	}

})(jQuery);