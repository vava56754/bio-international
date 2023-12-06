package com.perso.bio.dto;

import com.perso.bio.enums.ProcurementState;
import com.perso.bio.model.procurement.LineProcurement;
import com.perso.bio.model.user_management.User;

import java.time.Instant;
import java.util.List;

public class ProcurementDTO {

    private Integer procurementId;
    private Instant procurementDate;
    private ProcurementState procurementState;
    private List<LineProcurementDTO> lineProcurementList;
    private User userId;
    private Integer amount;

    public ProcurementDTO() {

    }

    public ProcurementDTO(Instant procurementDate, ProcurementState procurementState) {
        this.procurementDate = procurementDate;
        this.procurementState = procurementState;
    }

    public int getProcurementId() {
        return procurementId;
    }

    public void setProcurementId(Integer procurementId) {
        this.procurementId = procurementId;
    }

    public Instant getProcurementDate() {
        return procurementDate;
    }

    public void setProcurementDate(Instant procurementDate) {
        this.procurementDate = procurementDate;
    }

    public ProcurementState getProcurementState() {
        return procurementState;
    }


    public void setProcurementState(ProcurementState procurementState) {
        this.procurementState = procurementState;
    }

    public List<LineProcurementDTO> getLineProcurementList() {
        return lineProcurementList;
    }

    public void setLineProcurementList(List<LineProcurementDTO> lineProcurementList) {
        this.lineProcurementList = lineProcurementList;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
