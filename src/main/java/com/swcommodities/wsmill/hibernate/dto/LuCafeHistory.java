package com.swcommodities.wsmill.hibernate.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by gmvn on 10/18/18.
 */

@Entity
@Table(name = "lu_cafe_history")
@Data
public class LuCafeHistory extends AbstractTimestampEntity implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String refNumber;
    private Integer beginExpense = 0;
    @Column(columnDefinition = "TEXT")
    private String beginExpenseDetail;
    private Integer income = 0;
    private Integer indayExpense = 0;
    @Column(columnDefinition = "TEXT")
    private String indayExpenseDetail;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdUser;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User approvalUser;
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvalDate;
    private Integer approvalStatus;
    private Integer total = 0;

    public boolean getCompleted() {
        return approvalStatus == InstructionStatus.TransactionApprovalStatus.APPROVED;
    }

}
