package com.perso.bio.service.procurement;

import com.perso.bio.dto.ProcurementDTO;
import com.perso.bio.model.procurement.Procurement;

import java.util.List;

public interface ProcurementService {
    void createProcurement();

    Procurement createProcurementForLine();

    Procurement getProcurement(Integer procurementId);

    ProcurementDTO getCurrentProcurement();

    List<ProcurementDTO> getAllProcurementForCustomer();

    List<ProcurementDTO> getAllPastProcurement();

    void updateProcurement(Integer procurementId, Procurement procurement);

    void deleteProcurement(Integer procurementId);
}
