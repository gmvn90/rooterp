package com.swcommodities.wsmill.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.StockListClientViewHistory;

/**
 * Created by dunguyen on 11/11/16.
 */
@Component
@Transactional
public class WeightNoteReceiptRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Integer> getWNInStock() {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT distinct wnr.weightNote.id from WeightNoteReceipt wnr where wnr.status=1";
        Query q = session.createQuery(queryString);
        return q.list();
    }

    public Double getTonForClient(int clientId, int gradeId) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT COALESCE(sum(wnr.grossWeight - wnr.tareWeight)/1000, 0) from WeightNoteReceipt as wnr left join wnr.wnrAllocationsForWnrId as wna where wnr.status != 2 and ((wnr.status = 3 and wna.status != 1) or wna.status is null) and (wnr.refNumber like '%IM%' or wnr.refNumber like '%XP%')\n" +
                "and (-1 = :clientId or wnr.companyMasterByClientId.id = :clientId) and (-1 = :gradeId or wnr.gradeMaster.id = :gradeId)";
        Query q = session.createQuery(queryString);
        q.setParameter("clientId", clientId);
        q.setParameter("gradeId", gradeId);
        return (Double) q.uniqueResult();
    }

    public Map<String, Double> getCurrentTonByClientAndGrade(int clientId) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT COALESCE(wnr.gradeMaster.name,'') as gradeName ,COALESCE(sum(wnr.grossWeight - wnr.tareWeight)/1000, 0) as tons from WeightNoteReceipt as wnr left join wnr.wnrAllocationsForWnrId as wna where wnr.status != 2 and ((wnr.status = 3 and wna.status != 1) or wna.status is null) and (wnr.refNumber like '%IM%' or wnr.refNumber like '%XP%')\n" +
                "and (-1 = :clientId or wnr.companyMasterByClientId.id = :clientId) group by wnr.gradeMaster";
        Query q = session.createQuery(queryString);
        q.setParameter("clientId", clientId);
        ArrayList<Object[]> objs = (ArrayList<Object[]>) q.list();
        Map<String, Double> result = new HashMap<String, Double>();
        for (Object[] obj : objs) {
            result.put(obj[0].toString(), Double.parseDouble(obj[1].toString()));
        }
        return result;
    }

    public List<StockListClientViewHistory> getCurrentStockListClientView(int clientId, int gradeId, int warehouseId) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT\n" +
                "\tcommon.gradeId,\n" +
                "\tcommon.gradeName,\n" +
                "\tcommon.pledgeTons,\n" +
                "\tcommon.unpledgeTons,\n" +
                "\tyield.asc20,\n" +
                "\tyield.sc20,\n" +
                "\tyield.sc19,\n" +
                "\tyield.sc18,\n" +
                "\tyield.sc17,\n" +
                "\tyield.sc16,\n" +
                "\tyield.sc15,\n" +
                "\tyield.sc14,\n" +
                "\tyield.sc13,\n" +
                "\tyield.sc12,\n" +
                "\tyield.bsc12,\n" +
                "\tyield.black,\n" +
                "\tyield.broken,\n" +
                "\tyield.brown,\n" +
                "\tyield.moist,\n" +
                "\tyield.ocrop,\n" +
                "\tyield.fm,\n" +
                "\tyield.moldy,\n" +
                "\tyield.obean\n" +
                "FROM\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tgm.id AS gradeId,\n" +
                "\t\t\tgm.`name` AS gradeName,\n" +
                "\t\t\tsum(\n" +
                "\n" +
                "\t\t\t\tIF (\n" +
                "\t\t\t\t\twnr.pledge_id IS NULL,\n" +
                "\t\t\t\t\t0,\n" +
                "\t\t\t\t\twnr.gross_weight - wnr.tare_weight\n" +
                "\t\t\t\t)\n" +
                "\t\t\t) / 1000 AS pledgeTons,\n" +
                "\t\t\tsum(\n" +
                "\n" +
                "\t\t\t\tIF (\n" +
                "\t\t\t\t\twnr.pledge_id IS NULL,\n" +
                "\t\t\t\t\twnr.gross_weight - wnr.tare_weight,\n" +
                "\t\t\t\t\t0\n" +
                "\t\t\t\t)\n" +
                "\t\t\t) / 1000 AS unpledgeTons\n" +
                "\t\tFROM\n" +
                "\t\t\tweight_note_receipt wnr\n" +
                "\t\tLEFT JOIN wnr_allocation wna ON wnr.id = wna.wnr_id\n" +
                "\t\tLEFT JOIN grade_master gm ON wnr.grade_id = gm.id\n" +
                "\t\tLEFT JOIN warehouse_cell wc ON wnr.area_id = wc.id\n" +
                "\t\tLEFT JOIN warehouse_map wm ON wc.warehouse_map_id = wm.id\n" +
                "\t\tWHERE (wnr.client_id = :clientId or :clientId = -1) and (wnr.grade_id = :gradeId or :gradeId = -1) and (wm.id = :warehouseId or :warehouseId = -1)\n" +
                "\t\t\tAND wnr.`status` != 2\n" +
                "\t\tAND (\n" +
                "\t\t\t(wnr. STATUS = 3 AND wna. STATUS != 1)\n" +
                "\t\t\tOR wna. STATUS IS NULL\n" +
                "\t\t)\n" +
                "\t\tAND wnr.grade_id IS NOT NULL\n" +
                "\t\tAND (\n" +
                "\t\t\twnr.ref_number LIKE '%IM%'\n" +
                "\t\t\tOR wnr.ref_number LIKE '%XP%'\n" +
                "\t\t) \n" +
                "\t\tGROUP BY\n" +
                "\t\t\twnr.grade_id\n" +
                "\t\tORDER BY\n" +
                "\t\t\tgm.`name`\n" +
                "\t) common\n" +
                "LEFT JOIN (\n" +
                "\tSELECT\n" +
                "\t\traw.gradeId AS gradeId,\n" +
                "\t\tsum(raw.asc20) / sum(raw.tons) AS asc20,\n" +
                "\t\tsum(raw.sc20) / sum(raw.tons) AS sc20,\n" +
                "\t\tsum(raw.sc19) / sum(raw.tons) AS sc19,\n" +
                "\t\tsum(raw.sc18) / sum(raw.tons) AS sc18,\n" +
                "\t\tsum(raw.sc17) / sum(raw.tons) AS sc17,\n" +
                "\t\tsum(raw.sc16) / sum(raw.tons) AS sc16,\n" +
                "\t\tsum(raw.sc15) / sum(raw.tons) AS sc15,\n" +
                "\t\tsum(raw.sc14) / sum(raw.tons) AS sc14,\n" +
                "\t\tsum(raw.sc13) / sum(raw.tons) AS sc13,\n" +
                "\t\tsum(raw.sc12) / sum(raw.tons) AS sc12,\n" +
                "\t\tsum(raw.bsc12) / sum(raw.tons) AS bsc12,\n" +
                "\t\tsum(raw.black) / sum(raw.tons) AS black,\n" +
                "\t\tsum(raw.broken) / sum(raw.tons) AS broken,\n" +
                "\t\tsum(raw.brown) / sum(raw.tons) AS brown,\n" +
                "\t\tsum(raw.moist) / sum(raw.tons) AS moist,\n" +
                "\t\tsum(raw.ocrop) / sum(raw.tons) AS ocrop,\n" +
                "\t\tsum(raw.fm) / sum(raw.tons) AS fm,\n" +
                "\t\tsum(raw.moldy) / sum(raw.tons) AS moldy,\n" +
                "\t\tsum(raw.obean) / sum(raw.tons) AS obean\n" +
                "\tFROM\n" +
                "\t\t(\n" +
                "\t\t\tSELECT\n" +
                "\t\t\t\tgm.id AS gradeId,\n" +
                "\t\t\t\tsum(\n" +
                "\t\t\t\t\t(\n" +
                "\t\t\t\t\t\twnr.gross_weight - wnr.tare_weight\n" +
                "\t\t\t\t\t) / 1000\n" +
                "\t\t\t\t) AS tons,\n" +
                "\t\t\t\tIFNULL(qr.black, 0) * (\n" +
                "\t\t\t\t\tsum(\n" +
                "\t\t\t\t\t\twnr.gross_weight - wnr.tare_weight\n" +
                "\t\t\t\t\t) / 1000\n" +
                "\t\t\t\t) AS black,\n" +
                "\t\t\t\tIFNULL(qr.brown, 0) * (\n" +
                "\t\t\t\t\tsum(\n" +
                "\t\t\t\t\t\twnr.gross_weight - wnr.tare_weight\n" +
                "\t\t\t\t\t) / 1000\n" +
                "\t\t\t\t) AS brown,\n" +
                "\t\t\t\tIFNULL(qr.foreign_matter, 0) * (\n" +
                "\t\t\t\t\tsum(\n" +
                "\t\t\t\t\t\twnr.gross_weight - wnr.tare_weight\n" +
                "\t\t\t\t\t) / 1000\n" +
                "\t\t\t\t) AS fm,\n" +
                "\t\t\t\tIFNULL(qr.broken, 0) * (\n" +
                "\t\t\t\t\tsum(\n" +
                "\t\t\t\t\t\twnr.gross_weight - wnr.tare_weight\n" +
                "\t\t\t\t\t) / 1000\n" +
                "\t\t\t\t) AS broken,\n" +
                "\t\t\t\tIFNULL(qr.moisture, 0) * (\n" +
                "\t\t\t\t\tsum(\n" +
                "\t\t\t\t\t\twnr.gross_weight - wnr.tare_weight\n" +
                "\t\t\t\t\t) / 1000\n" +
                "\t\t\t\t) AS moist,\n" +
                "\t\t\t\tIFNULL(qr.old_crop, 0) * (\n" +
                "\t\t\t\t\tsum(\n" +
                "\t\t\t\t\t\twnr.gross_weight - wnr.tare_weight\n" +
                "\t\t\t\t\t) / 1000\n" +
                "\t\t\t\t) AS ocrop,\n" +
                "\t\t\t\tIFNULL(qr.moldy, 0) * (\n" +
                "\t\t\t\t\tsum(\n" +
                "\t\t\t\t\t\twnr.gross_weight - wnr.tare_weight\n" +
                "\t\t\t\t\t) / 1000\n" +
                "\t\t\t\t) AS moldy,\n" +
                "\t\t\t\tIFNULL(qr.above_sc20, 0) * (\n" +
                "\t\t\t\t\tsum(\n" +
                "\t\t\t\t\t\twnr.gross_weight - wnr.tare_weight\n" +
                "\t\t\t\t\t) / 1000\n" +
                "\t\t\t\t) AS asc20,\n" +
                "\t\t\t\tIFNULL(qr.sc20, 0) * (\n" +
                "\t\t\t\t\tsum(\n" +
                "\t\t\t\t\t\twnr.gross_weight - wnr.tare_weight\n" +
                "\t\t\t\t\t) / 1000\n" +
                "\t\t\t\t) AS sc20,\n" +
                "\t\t\t\tIFNULL(qr.sc19, 0) * (\n" +
                "\t\t\t\t\tsum(\n" +
                "\t\t\t\t\t\twnr.gross_weight - wnr.tare_weight\n" +
                "\t\t\t\t\t) / 1000\n" +
                "\t\t\t\t) AS sc19,\n" +
                "\t\t\t\tIFNULL(qr.sc18, 0) * (\n" +
                "\t\t\t\t\tsum(\n" +
                "\t\t\t\t\t\twnr.gross_weight - wnr.tare_weight\n" +
                "\t\t\t\t\t) / 1000\n" +
                "\t\t\t\t) AS sc18,\n" +
                "\t\t\t\tIFNULL(qr.sc17, 0) * (\n" +
                "\t\t\t\t\tsum(\n" +
                "\t\t\t\t\t\twnr.gross_weight - wnr.tare_weight\n" +
                "\t\t\t\t\t) / 1000\n" +
                "\t\t\t\t) AS sc17,\n" +
                "\t\t\t\tIFNULL(qr.sc16, 0) * (\n" +
                "\t\t\t\t\tsum(\n" +
                "\t\t\t\t\t\twnr.gross_weight - wnr.tare_weight\n" +
                "\t\t\t\t\t) / 1000\n" +
                "\t\t\t\t) AS sc16,\n" +
                "\t\t\t\tIFNULL(qr.sc15, 0) * (\n" +
                "\t\t\t\t\tsum(\n" +
                "\t\t\t\t\t\twnr.gross_weight - wnr.tare_weight\n" +
                "\t\t\t\t\t) / 1000\n" +
                "\t\t\t\t) AS sc15,\n" +
                "\t\t\t\tIFNULL(qr.sc14, 0) * (\n" +
                "\t\t\t\t\tsum(\n" +
                "\t\t\t\t\t\twnr.gross_weight - wnr.tare_weight\n" +
                "\t\t\t\t\t) / 1000\n" +
                "\t\t\t\t) AS sc14,\n" +
                "\t\t\t\tIFNULL(qr.sc13, 0) * (\n" +
                "\t\t\t\t\tsum(\n" +
                "\t\t\t\t\t\twnr.gross_weight - wnr.tare_weight\n" +
                "\t\t\t\t\t) / 1000\n" +
                "\t\t\t\t) AS sc13,\n" +
                "\t\t\t\tIFNULL(qr.sc12, 0) * (\n" +
                "\t\t\t\t\tsum(\n" +
                "\t\t\t\t\t\twnr.gross_weight - wnr.tare_weight\n" +
                "\t\t\t\t\t) / 1000\n" +
                "\t\t\t\t) AS sc12,\n" +
                "\t\t\t\tIFNULL(qr.below_sc12, 0) * (\n" +
                "\t\t\t\t\tsum(\n" +
                "\t\t\t\t\t\twnr.gross_weight - wnr.tare_weight\n" +
                "\t\t\t\t\t) / 1000\n" +
                "\t\t\t\t) AS bsc12,\n" +
                "\t\t\t\tIFNULL(qr.other_bean, 0) * (\n" +
                "\t\t\t\t\tsum(\n" +
                "\t\t\t\t\t\twnr.gross_weight - wnr.tare_weight\n" +
                "\t\t\t\t\t) / 1000\n" +
                "\t\t\t\t) AS obean\n" +
                "\t\t\tFROM\n" +
                "\t\t\t\tweight_note_receipt wnr\n" +
                "\t\t\tLEFT JOIN weight_note wn ON wn.id = wnr.wn_id\n" +
                "\t\t\tLEFT JOIN quality_report qr ON wn.qr_id = qr.id\n" +
                "\t\t\tLEFT JOIN grade_master gm ON wn.grade_id = gm.id\n" +
                "\t\t\tLEFT JOIN wnr_allocation wna ON wna.wnr_id = wnr.id\n" +
                "\t\t\tWHERE\n" +
                "\t\t\t\twn.type <> 'EX'\n" +
                "\t\t\tAND wn.type <> 'IP'\n" +
                "\t\t\tAND wnr. STATUS <> 2\n" +
                "\t\t\tAND (\n" +
                "\t\t\t\t(wnr. STATUS = 3 AND wna. STATUS <> 1)\n" +
                "\t\t\t\tOR wna. STATUS IS NULL\n" +
                "\t\t\t)\n" +
                "\t\t\tGROUP BY\n" +
                "\t\t\t\twnr.wn_id,\n" +
                "\t\t\t\twnr.area_id,\n" +
                "\t\t\t\twnr. STATUS,\n" +
                "\t\t\t\twna. STATUS\n" +
                "\t\t) raw\n" +
                "\tGROUP BY\n" +
                "\t\traw.gradeId\n" +
                ") yield ON common.gradeId = yield.gradeId";
        Query q = session.createSQLQuery(queryString).setParameter("clientId", clientId).setParameter("gradeId", gradeId).setParameter("warehouseId", warehouseId);

        List<Object[]> obj_list = (List<Object[]>) q.list();
        List<StockListClientViewHistory> grades = new ArrayList<>();
        if (obj_list != null || !obj_list.isEmpty()) {
            for (Object[] obj : obj_list) {
                StockListClientViewHistory grade = new StockListClientViewHistory();

                grade.setGradeId(Integer.valueOf(obj[0].toString()));
                grade.setGradeName(obj[1].toString());
                grade.setPledgedTons(Double.valueOf(obj[2].toString()));
                grade.setUnpledgedTons(Double.valueOf(obj[3].toString()));
                grade.setAboveSc20(Double.valueOf(obj[4].toString()));
                grade.setSc20(Double.valueOf(obj[5].toString()));
                grade.setSc19(Double.valueOf(obj[6].toString()));
                grade.setSc18(Double.valueOf(obj[7].toString()));
                grade.setSc17(Double.valueOf(obj[8].toString()));
                grade.setSc16(Double.valueOf(obj[9].toString()));
                grade.setSc15(Double.valueOf(obj[10].toString()));
                grade.setSc14(Double.valueOf(obj[11].toString()));
                grade.setSc13(Double.valueOf(obj[12].toString()));
                grade.setSc12(Double.valueOf(obj[13].toString()));
                grade.setBelowSc12(Double.valueOf(obj[14].toString()));
                grade.setBlack(Double.valueOf(obj[15].toString()));
                grade.setBroken(Double.valueOf(obj[16].toString()));
                grade.setBrown(Double.valueOf(obj[17].toString()));
                grade.setMoisture(Double.valueOf(obj[18].toString()));
                grade.setOldCrop(Double.valueOf(obj[19].toString()));
                grade.setForeignMatter(Double.valueOf(obj[20].toString()));
                grade.setMoldy(Double.valueOf(obj[21].toString()));
                grade.setOtherBean(Double.valueOf(obj[22].toString()));

                grades.add(grade);
            }
        }
        return grades;
    }
}
