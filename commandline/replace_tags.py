import re
reg_minFractionDigits = 'minFractionDigits="(\d)"'
reg_value = 'value="\$\{(.*?)\}"'
reg_var = 'var="(.*?)"'

reg_formatNumberWithVar = re.compile("""<fmt:formatNumber.*?minFractionDigits="(\d)".*?value="\$\{(.*?)\}".*?\/>""", re.DOTALL)
reg_formatNumber = re.compile("""<fmt:formatNumber.*?minFractionDigits="(\d)".*?value="\$\{(.*?)\}".*?\/>""", re.DOTALL)
reg_formatDate = re.compile("""<fmt:formatNumber.*?minFractionDigits="(\d)".*?value="\$\{(.*?)\}".*?\/>""", re.DOTALL)


def replace_format_number(s):
	res = re.sub(reg_formatNumber, (lambda m: r"${f:formatTons(%s)}" % m.group(2) if m.group(1) == "3" else r"${f:formatMoneyForInput(%s)}" % m.group(2)) , s)
	return res

