package com.perso.bio.service.procurement;

import com.perso.bio.dto.ProcurementDTO;
import com.perso.bio.model.procurement.Procurement;
import jakarta.mail.MessagingException;

import java.util.List;

public interface ProcurementService {
    void createProcurement();

    Procurement createProcurementForLine();

    ProcurementDTO getProcurement(Integer procurementId);

    ProcurementDTO getCurrentProcurement();

    List<ProcurementDTO> getAllProcurementsForCustomer();

    List<ProcurementDTO> getAllPastProcurements();

    List<ProcurementDTO> getAllValidateProcurementsForCustomer();

    List<ProcurementDTO> getAllValidateProcurements();

    void updateProcurement(Integer procurementId, Procurement procurement);

    void validateProcurement(Integer procurementId) throws MessagingException;

    void completeProcurement(Integer procurementId) throws MessagingException;

    void deleteProcurement(Integer procurementId);
}
