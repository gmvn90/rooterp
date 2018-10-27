from replace_tags import replace_format_number
import unittest
import re

class TestReg(unittest.TestCase):

	def test_tags(self):
		s = """aaa<fmt:formatNumber type="number" 
		minFractionDigits="3" 
		maxFractionDigits="3" value="${item.balance}"/>abbb<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${item.total}"/>"""
		res = replace_format_number(s)
		self.assertEqual(res, "aaa${f:formatTons(item.balance)}abbb${f:formatMoneyForInput(item.total)}")


if __name__ == "__main__":
	unittest.main()
