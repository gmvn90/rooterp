#!/usr/bin/python

import MySQLdb
from MySQLdb import cursors
import json
import uuid


class MySQLCursorDict(cursors.DictCursor):
    def _row_to_python(self, rowdata, desc=None):
        row = super(MySQLCursorDict, self)._row_to_python(rowdata, desc)
        if row:
            return dict(zip(self.column_names, row))
        return None

# Open database connection
db = MySQLdb.connect("localhost","root","root","wsmill3", cursorclass=MySQLCursorDict, charset='utf8')

# prepare a cursor object using cursor() method
cursor = db.cursor()

# execute SQL query using execute() method.
cursor.execute("SELECT * from company_master")

# Fetch a single row using fetchone() method.
results = cursor.fetchall()
res = []
for row in results:
    row['master_id'] = str(uuid.uuid4())
    res.append(row)

with open('res.json', 'w') as f:
    f.write(json.dumps(res))

with open('res.json') as f:
    res = json.load(f)
print res
# disconnect from server
db.close()