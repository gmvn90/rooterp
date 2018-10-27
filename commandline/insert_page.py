#!/usr/bin/python

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
# Open database connection
db = MySQLdb.connect("localhost","root","root", "wsmill3", cursorclass=MySQLCursorDict, charset='utf8')

# prepare a cursor object using cursor() method
cursor = db.cursor()

def get_max(table):
    global cursor
    cursor.execute("SELECT id from %s ORDER BY id DESC LIMIT 1" % table)
    for row in cursor.fetchall():
        return row['id'] + 1

name = unicode(sys.argv[1])
parent = int(sys.argv[2])
try:

    max_page_id = get_max("page")
    max_menu_id = get_max("menu")
    cursor.execute("""INSERT INTO page(id, name, url, parent, access_style) VALUES (%s,%s,%s,1,1)""", (max_page_id, name, slugify(name)))
    cursor.execute("""INSERT INTO menu(id, name, parent_id, url_id, `default`) VALUES (%s,%s,%s,%s,%s)""",
                   (max_menu_id, name, 7, max_page_id, 0));
    with open("page.template") as f:
        content = f.read()
    content = content.replace("###url###", str(max_menu_id))
    content = content.replace("###page###", str(max_page_id))
    cursor.execute("""INSERT INTO authorization(user_id, page_id, permission) VALUES (%s,%s,1)""",
                   (10, max_page_id));
    with open("%s.jsp" % slugify(name), "w") as f:
        f.write(content)
    db.commit()
except Exception as e:
    print e
    db.rollback()
