package com.perso.bio.model.procurement;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.perso.bio.constants.Field;
import com.perso.bio.enums.ProcurementState;
import com.perso.bio.model.user_management.User;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = Field.PROCUREMENT)
public class Procurement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer procurementId;
    private Instant procurementDate;

    @Enumerated(EnumType.STRING)
    private ProcurementState procurementState;

    @ManyToOne
    @JoinColumn(name = Field.USER_ID)
    private User user;


    @JsonManagedReference
    @OneToMany(mappedBy = Field.PROCUREMENT, cascade = CascadeType.ALL)
    private List<LineProcurement> lineProcurementList;

    public Procurement() {

    }

    public Procurement(Instant procurementDate, ProcurementState procurementState) {
        this.procurementDate = procurementDate;
        this.procurementState = procurementState;
        lineProcurementList = new ArrayList<>();
    }

    public Integer getProcurementId() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<LineProcurement> getLineProcurementList() {
        if(lineProcurementList != null){
            return lineProcurementList;
        } else {
            return new ArrayList<>();
        }

    }

    public void addLineProcurement(LineProcurement lineProcurement) {
        this.lineProcurementList.add(lineProcurement);
    }

}

