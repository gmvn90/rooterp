from read_grade import result_filename
import json
from data_connection import cursorMill, cursorTrade, dbMill, dbTrade


def get_one_trade_item(relations, id):
    res = {}
    for item in relations['trade']:
        if item['id'] == id:
            res = item
    return res


def insert_grade_connection():
    with open(result_filename) as f:
        relations = json.loads(f.read())
        trades_inserted = []
        for item in relations['mill']:
            trade = get_one_trade_item(relations, item['id'])
            cursorMill.execute("""INSERT INTO grade_converter(id, tradeId, tradeName, gradeMaster_id) VALUES (null, %s, %s, %s)""", 
                (trade.get('id'), trade.get('name'), item.get('id')))
            trades_inserted.append(item['id'])
        for item in relations['trade']:
            if not item['id'] in trades_inserted:
                cursorMill.execute("""INSERT INTO grade_converter(id, tradeId, tradeName, gradeMaster_id) VALUES (null, %s, %s, null)""", 
                (item.get('id'), item.get('name')))
        dbMill.commit()


if __name__ == '__main__':
    insert_grade_connection()
            
