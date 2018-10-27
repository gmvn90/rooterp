from data_connection import cursorMill, cursorTrade
import json

result_filename = "grades.json"

def get_all_mill_grades():
    cursor = cursorMill
    cursor.execute("SELECT * from grade_master")
    results = cursor.fetchall()
    res = []
    for row in results:
        item = {}
        item['id'] = row['id']
        item['name'] = row['name']
        res.append(item)
        
    return res

def get_all_trade_grades():
    cursor = cursorTrade
    cursor.execute("SELECT * from grade_master")
    results = cursor.fetchall()
    res = []
    for row in results:
        item = {}
        item['id'] = row['id']
        item['name'] = row['name']
        res.append(item)
        
    return res


if __name__ == '__main__':
    with open(result_filename, 'w') as f:
        f.write(json.dumps({'mill': get_all_mill_grades(), 'trade': get_all_trade_grades()}))