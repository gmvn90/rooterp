import MySQLdb
from MySQLdb import cursors
import json
import uuid
import sys
from slugify import slugify



class MySQLCursorDict(cursors.DictCursor):
    def _row_to_python(self, rowdata, desc=None):
        row = super(MySQLCursorDict, self)._row_to_python(rowdata, desc)
        if row:
            return dict(zip(self.column_names, row))
        return None

dbMill = MySQLdb.connect("localhost","root","root", "wsmill3", cursorclass=MySQLCursorDict, charset='utf8')
cursorMill = dbMill.cursor()

dbTrade = MySQLdb.connect("localhost","root","root", "wstrader2", cursorclass=MySQLCursorDict, charset='utf8')
cursorTrade = dbTrade.cursor()

__all__ = [cursorMill, cursorTrade, dbMill, dbTrade]