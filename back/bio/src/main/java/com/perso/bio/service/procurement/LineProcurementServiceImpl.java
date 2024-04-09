package com.perso.bio.service.procurement;


import com.perso.bio.constants.MessageConstants;
import com.perso.bio.model.procurement.LineProcurement;
import com.perso.bio.repository.LineProcurementRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("jpa")
public class LineProcurementServiceImpl implements LineProcurementService {

    LineProcurementRepository lineProcurementRepository;

    @Autowired
    public LineProcurementServiceImpl(LineProcurementRepository lineProcurementRepository) {
        this.lineProcurementRepository = lineProcurementRepository;
    }

    @Override
    public void createLineProcurement(LineProcurement lineProcurement) {
        if (lineProcurement.getProduct().getProductStock() >= 1) {
            lineProcurement.setLineUnitPrice(setLinePrice(lineProcurement));
            this.lineProcurementRepository.save(lineProcurement);
        } else {
            throw new RuntimeException(MessageConstants.PRODUCT_OUT_OF_STOCK);
        }

    }

    @Override
    public LineProcurement getLine(Integer lineId) {
        return this.lineProcurementRepository.findById(lineId).orElseThrow(() -> new EntityNotFoundException(MessageConstants.LINE_PROCUREMENT_SERVICE_ERROR_MESSAGE + lineId));
    }

    @Override
    public LineProcurement findExistLine(Integer productId, Integer procurementId) {
        return this.lineProcurementRepository.findByProductProductIdAndProcurementProcurementId(productId, procurementId).orElse(null);

    }

    @Override
    public List<LineProcurement> getAllByProcurementId(Integer lineId) {
        return this.lineProcurementRepository.findAllByProcurementProcurementId(lineId).orElseThrow();
    }

    @Override
    public void updateLineProcurement(Integer lineId, LineProcurement lineProcurement) {
        Optional<LineProcurement> existingLineProcurement = this.lineProcurementRepository.findById(lineId);
        if (existingLineProcurement.isPresent() && lineProcurement.getLineQuantity() <= lineProcurement.getProduct().getProductStock()) {
            LineProcurement lineProcurementUpdate = existingLineProcurement.get();
            lineProcurementUpdate.setLineQuantity(Optional.ofNullable(lineProcurement.getLineQuantity()).orElse(lineProcurementUpdate.getLineQuantity()));
            lineProcurementUpdate.setProcurement(Optional.ofNullable(lineProcurement.getProcurement()).orElse(lineProcurementUpdate.getProcurement()));
            lineProcurementUpdate.setProduct(Optional.ofNullable(lineProcurement.getProduct()).orElse(lineProcurementUpdate.getProduct()));
            lineProcurementUpdate.setLineUnitPrice(setLinePrice(lineProcurement));
            this.lineProcurementRepository.save(lineProcurementUpdate);
        } else {
            throw new EntityNotFoundException(MessageConstants.LINE_PROCUREMENT_SERVICE_ERROR_MESSAGE + lineId);
        }

    }

    @Override
    public void deleteLineProcurement(Integer lineId) {
        Optional<LineProcurement> existingLineProcurement = this.lineProcurementRepository.findById(lineId);
        if (existingLineProcurement.isPresent()) {
            this.lineProcurementRepository.deleteById(lineId);
        } else {
            throw new EntityNotFoundException(MessageConstants.LINE_PROCUREMENT_SERVICE_ERROR_MESSAGE + lineId);
        }

    }

    private Integer setLinePrice(LineProcurement lineProcurement) {
        if (lineProcurement.getProduct().getProductUnitPrice() != null) {
            int productPrice = lineProcurement.getProduct().getProductUnitPrice();
            int quantity = lineProcurement.getLineQuantity();
            return quantity * productPrice;
        } else {
            throw new NullPointerException();
        }

    }

}
