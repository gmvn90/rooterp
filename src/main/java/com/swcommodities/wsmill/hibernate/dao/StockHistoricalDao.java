/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.type.FloatType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.StockHistorical;
import com.swcommodities.wsmill.hibernate.dto.StockHistories;
import com.swcommodities.wsmill.utils.Common;

/**
 *
 * @author duhc
 */
public class StockHistoricalDao {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public StockHistoricalDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    @Transactional
    public void saveStockHistoricalReport() {

        String sql = "CALL saveStockHistoricalDaily()";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString())
                .addScalar("wn_id") //0
                .addScalar("wn_ref")
                .addScalar("inst_ref") //2
                .addScalar("tons")
                .addScalar("num") //4
                .addScalar("packing")
                .addScalar("date") //6
                .addScalar("map_name")
                .addScalar("ordinate_x") //8
                .addScalar("ordinate_y")
                .addScalar("wall_vertical") //10
                .addScalar("wall_horizontal")
                .addScalar("width") //12
                .addScalar("height")
                .addScalar("black") //14
                .addScalar("brown")
                .addScalar("fm") //16
                .addScalar("broken")
                .addScalar("moist") //18
                .addScalar("ocrop")
                .addScalar("moldy") //20
                .addScalar("cup")
                .addScalar("number") //22
                .addScalar("asc20")
                .addScalar("sc20") //24
                .addScalar("sc19")
                .addScalar("sc18") //26
                .addScalar("sc17")
                .addScalar("sc16") //28
                .addScalar("sc15")
                .addScalar("sc14") //30
                .addScalar("sc13")
                .addScalar("sc12") //32
                .addScalar("bsc12")
                .addScalar("area") //34
                .addScalar("client")
                .addScalar("grade") //36
                .addScalar("type", StringType.INSTANCE)
                .addScalar("map") //38  
                .addScalar("pi_id")
                .addScalar("wna_id") //40
                .addScalar("wna_stt")
                .addScalar("wna_type"); //42
        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();
        if (obj_list != null || !obj_list.isEmpty()) {
            ArrayList<StockHistorical> shs = new ArrayList<>();
            for (Object[] obj : obj_list) {
                StockHistorical sh = new StockHistorical();
                sh.setWnRef(obj[1].toString());
                sh.setAllocationRef(obj[2].toString());
                sh.setTons(Float.parseFloat(obj[3].toString()));
                sh.setNum(Integer.parseInt(obj[4].toString()));
                sh.setPackingId(Integer.parseInt(obj[5].toString()));
                sh.setInDate((Date) obj[6]);
                sh.setArea((!obj[34].toString().equals("0")) ? Common.convertIdIntoCode(obj[7].toString(), Integer.parseInt(obj[8].toString()), Integer.parseInt(obj[9].toString()), obj[10].toString(), obj[11].toString(), Integer.parseInt(obj[12].toString()), Integer.parseInt(obj[13].toString())) : "");
                sh.setBlacks(Float.parseFloat(obj[14].toString()));
                sh.setBrown(Float.parseFloat(obj[15].toString()));
                sh.setFm(Float.parseFloat(obj[16].toString()));
                sh.setBroken(Float.parseFloat(obj[17].toString()));
                sh.setMoist(Float.parseFloat(obj[18].toString()));
                sh.setOcrop(Float.parseFloat(obj[19].toString()));
                sh.setMoldy(Float.parseFloat(obj[20].toString()));
                sh.setCup(obj[21].toString() + "/" + obj[22].toString());
                sh.setAsc20(Float.parseFloat(obj[23].toString()));
                sh.setSc20(Float.parseFloat(obj[24].toString()));
                sh.setSc19(Float.parseFloat(obj[25].toString()));
                sh.setSc18(Float.parseFloat(obj[26].toString()));
                sh.setSc17(Float.parseFloat(obj[27].toString()));
                sh.setSc16(Float.parseFloat(obj[28].toString()));
                sh.setSc15(Float.parseFloat(obj[29].toString()));
                sh.setSc14(Float.parseFloat(obj[30].toString()));
                sh.setSc13(Float.parseFloat(obj[31].toString()));
                sh.setSc12(Float.parseFloat(obj[32].toString()));
                sh.setBsc12(Float.parseFloat(obj[33].toString()));
                sh.setClientId(Integer.parseInt(obj[35].toString()));
                sh.setGradeId(Integer.parseInt(obj[36].toString()));
//                Date TodayDate = new Date();
//
//                Calendar calendar = Calendar.getInstance();
//                calendar.add(Calendar.DAY_OF_YEAR, -1);// 1 is used for tomorrow date, and -1 is used for yesterday date.
//
//                Date yesDate = calendar.getTime();

                sh.setCreatedDate(new Date());
                sh.setType(obj[37].toString());
                sh.setMapId(Integer.parseInt(obj[38].toString()));
                sh.setPiId(Integer.parseInt(obj[39].toString()));
                sh.setWnaId(Integer.parseInt(obj[40].toString()));
                sh.setWnaStt(Integer.parseInt(obj[41].toString()));
                sh.setWnaType(obj[42].toString());
                shs.add(sh);
            }
            sessionFactory.getCurrentSession().saveOrUpdate(shs);
            //getHibernateTemplate().saveOrUpdateAll(shs);
        }
    }

    public ArrayList<HashMap> getStockInfo(int grade_id, int client_id, int pledge_id, int warehouse_id) {
        ArrayList<HashMap> result = new ArrayList<>();
        String sql = "call getStockInfo(:client,:pledge,:grade,:warehouse)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .addScalar("client", StringType.INSTANCE) //0
                .addScalar("pledge", StringType.INSTANCE) //1
                .addScalar("allocation_ref", StringType.INSTANCE) //2
                .addScalar("ref_number", StringType.INSTANCE) //3
                .addScalar("tons", FloatType.INSTANCE) //4
                .addScalar("num", IntegerType.INSTANCE) //5
                .addScalar("map_name", StringType.INSTANCE) //6
                .addScalar("ordinate_x", IntegerType.INSTANCE) //7
                .addScalar("ordinate_y", IntegerType.INSTANCE) //8
                .addScalar("wall_vertical", StringType.INSTANCE) //9
                .addScalar("wall_horizontal", StringType.INSTANCE) //10
                .addScalar("width", IntegerType.INSTANCE) //11
                .addScalar("height", IntegerType.INSTANCE) //12
                .addScalar("packing", StringType.INSTANCE) //13
                .addScalar("in_date", StringType.INSTANCE) //14
                .addScalar("warehouse", IntegerType.INSTANCE) //15
                .addScalar("black", FloatType.INSTANCE) //16
                .addScalar("brown", FloatType.INSTANCE) //17
                .addScalar("fm", FloatType.INSTANCE) //18
                .addScalar("broken", FloatType.INSTANCE) //19
                .addScalar("moist", FloatType.INSTANCE) //20
                .addScalar("ocrop", FloatType.INSTANCE) //21
                .addScalar("moldy", FloatType.INSTANCE) //22
                .addScalar("cup", IntegerType.INSTANCE)
                .addScalar("number", IntegerType.INSTANCE)
                .addScalar("asc20", FloatType.INSTANCE) //25
                .addScalar("sc20", FloatType.INSTANCE) //26
                .addScalar("sc19", FloatType.INSTANCE) //27
                .addScalar("sc18", FloatType.INSTANCE) //28
                .addScalar("sc17", FloatType.INSTANCE) //29
                .addScalar("sc16", FloatType.INSTANCE) //30
                .addScalar("sc15", FloatType.INSTANCE) //31
                .addScalar("sc14", FloatType.INSTANCE) //32
                .addScalar("sc13", FloatType.INSTANCE) //33
                .addScalar("sc12", FloatType.INSTANCE) //34
                .addScalar("bsc12", FloatType.INSTANCE) //35
                .addScalar("grade", StringType.INSTANCE) //36
                .addScalar("warehouse", IntegerType.INSTANCE) //37
                .addScalar("client_id", IntegerType.INSTANCE) //38
                .addScalar("pledge_id", IntegerType.INSTANCE) //39
                .addScalar("wn_id", IntegerType.INSTANCE) //40
                .addScalar("obean", FloatType.INSTANCE); //41
        query.setParameter("client", client_id);
        query.setParameter("pledge", pledge_id);
        query.setParameter("grade", grade_id);
        query.setParameter("warehouse", warehouse_id);

        ArrayList<Object[]> objs = (ArrayList<Object[]>) query.list();
        int count = 1;
        if (objs != null && !objs.isEmpty()) {
            for (Object[] obj : objs) {
                String map_name = Common.getStringValue(obj[6]);
                int ordinateX = Common.getIntegerValue(obj[7]);
                int ordinateY = Common.getIntegerValue(obj[8]);
                String wallver = Common.getStringValue(obj[9]);
                String wallhor = Common.getStringValue(obj[10]);
                int width = Common.getIntegerValue(obj[11]);
                int height = Common.getIntegerValue(obj[12]);
                float tons = Common.getFloatValue(obj[4]);
                float black = Common.getFloatValue(obj[16]);
                float brown = Common.getFloatValue(obj[17]);
                float fm = Common.getFloatValue(obj[18]);
                float broken = Common.getFloatValue(obj[19]);
                float moist = Common.getFloatValue(obj[20]);
                float ocrop = Common.getFloatValue(obj[21]);
                float obean = Common.getFloatValue(obj[41]);
                float moldy = Common.getFloatValue(obj[22]);
                float asc20 = Common.getFloatValue(obj[25]);
                float sc20 = Common.getFloatValue(obj[26]);
                float sc19 = Common.getFloatValue(obj[27]);
                float sc18 = Common.getFloatValue(obj[28]);
                float sc17 = Common.getFloatValue(obj[29]);
                float sc16 = Common.getFloatValue(obj[30]);
                float sc15 = Common.getFloatValue(obj[31]);
                float sc14 = Common.getFloatValue(obj[32]);
                float sc13 = Common.getFloatValue(obj[33]);
                float sc12 = Common.getFloatValue(obj[34]);
                float bsc12 = Common.getFloatValue(obj[35]);
                int warehouse = Common.getIntegerValue(obj[37]);
                int client = Common.getIntegerValue(obj[38]);
                int pledge = Common.getIntegerValue(obj[39]);
                int wn_id = Common.getIntegerValue(obj[40]);
                HashMap map = new HashMap();
                map.put("client", Common.getStringValue(obj[0]));
                map.put("pledge", Common.getStringValue(obj[1]));
                map.put("allocation_ref", Common.getStringValue(obj[2]));
                map.put("ref_number", Common.getStringValue(obj[3]));
                map.put("tons", tons);
                map.put("num", Common.getStringValue(obj[5]));
                map.put("packing", Common.getStringValue(obj[13]));
                map.put("in_date", Common.getStringValue(obj[14]));
                map.put("warehouse", Common.getStringValue(obj[15]));
                map.put("area", (!map_name.equals("")) ? Common.convertIdIntoCode(map_name, ordinateX, ordinateY, wallver, wallhor, width, height) : "");
                map.put("black", black);
                map.put("black_tons", black * tons);
                map.put("brown", brown);
                map.put("brown_tons", brown * tons);
                map.put("fm", fm);
                map.put("fm_tons", fm * tons);
                map.put("broken", broken);
                map.put("broken_tons", broken * tons);
                map.put("moist", moist);
                map.put("moist_tons", moist * tons);
                map.put("ocrop", ocrop);
                map.put("ocrop_tons", ocrop * tons);
                map.put("obean", obean);
                map.put("obean_tons", obean * tons);
                map.put("moldy", moldy);
                map.put("moldy_tons", moldy * tons);
                map.put("cup", Common.getStringValue(obj[23]) + "/" + Common.getStringValue(obj[24]));
                map.put("asc20", asc20);
                map.put("asc20_tons", asc20 * tons);
                map.put("sc20", sc20);
                map.put("sc20_tons", sc20 * tons);
                map.put("sc19", sc19);
                map.put("sc19_tons", sc19 * tons);
                map.put("sc18", sc18);
                map.put("sc18_tons", sc18 * tons);
                map.put("sc17", sc17);
                map.put("sc17_tons", sc17 * tons);
                map.put("sc16", sc16);
                map.put("sc16_tons", sc16 * tons);
                map.put("sc15", sc15);
                map.put("sc15_tons", sc15 * tons);
                map.put("sc14", sc14);
                map.put("sc14_tons", sc14 * tons);
                map.put("sc13", sc13);
                map.put("sc13_tons", sc13 * tons);
                map.put("sc12", sc12);
                map.put("sc12_tons", sc12 * tons);
                map.put("bsc12", bsc12);
                map.put("bsc12_tons", bsc12 * tons);
                map.put("count", count);
                map.put("grade_id", grade_id);
                map.put("client_id", client);
                map.put("pledge_id", pledge);
                map.put("warehouse_id", warehouse);
                map.put("wn_id", wn_id);
                count++;
                result.add(map);
            }
        }
        return result;
    }

    public StockHistories getHistoryByCode(long dateCode) {
        String sql = "from StockHistories s where s.dateCode =:dateCode";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("dateCode", dateCode);
        Object res = query.uniqueResult();
        return (res != null) ? (StockHistories) res : null;
    }

    public StockHistories updateHistory(StockHistories st) {
        StockHistories stock = this.getHistoryByCode(st.getDateCode());
        if (stock != null) {
            stock.setContent(st.getContent());
            sessionFactory.getCurrentSession().update(stock);
            return stock;
        } else {
            sessionFactory.getCurrentSession().save(st);
            return st;
        }
    }
}
