package com.perso.bio.service.procurement;

import com.perso.bio.model.procurement.LineProcurement;

import java.util.List;

public interface LineProcurementService {

    void createLineProcurement(LineProcurement lineProcurement);

    LineProcurement getLine(Integer lineId);

    LineProcurement findExistLine(Integer productId, Integer procurementId);

    List<LineProcurement> getAllByProcurementId(Integer lineId);

    void updateLineProcurement(Integer lineId, LineProcurement lineProcurement);

    void deleteLineProcurement(Integer lineId);


}
