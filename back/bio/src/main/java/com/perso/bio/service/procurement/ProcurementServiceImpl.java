package com.perso.bio.service.procurement;


import com.perso.bio.constants.MessageConstants;
import com.perso.bio.dto.LineProcurementDTO;
import com.perso.bio.dto.ProcurementDTO;
import com.perso.bio.dto.ProductDTO;
import com.perso.bio.enums.ProcurementState;
import com.perso.bio.model.procurement.Procurement;
import com.perso.bio.model.user_management.User;
import com.perso.bio.repository.ProcurementRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("jpa")
public class ProcurementServiceImpl implements ProcurementService {


    ProcurementRepository procurementRepository;

    @Autowired
    public ProcurementServiceImpl(ProcurementRepository procurementRepository) {
        this.procurementRepository = procurementRepository;
    }

    @Override
    public void createProcurement() {
        Instant creation = Instant.now();
        Procurement test = new Procurement();
        test.setProcurementDate(creation);
        test.setProcurementState(ProcurementState.CREATED);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        test.setUser(user);
        this.procurementRepository.save(test);
    }

    @Override
    public Procurement createProcurementForLine() {
        Instant creation = Instant.now();
        Procurement test = new Procurement();
        test.setProcurementDate(creation);
        test.setProcurementState(ProcurementState.CREATED);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        test.setUser(user);
        this.procurementRepository.save(test);
        return test;
    }

    @Override
    public Procurement getProcurement(Integer procurementId) {
        return this.procurementRepository.findById(procurementId).orElseThrow(() -> new EntityNotFoundException(MessageConstants.PROCUREMENT_SERVICE_ERROR_MESSAGE + procurementId));
    }

    @Override
    public List<ProcurementDTO> getAllProcurementForCustomer() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Procurement> procurements = this.procurementRepository.findAllProcurementByUser(user).orElse(null);
        List<ProcurementDTO> procurementDTOS = new ArrayList<>();
        if (!procurements.isEmpty()) {
            for (Procurement procurement : procurements) {
                procurementDTOS.add(createProcurementDTO(procurement));
            }
        }
        return procurementDTOS;
    }

    @Override
    public ProcurementDTO getCurrentProcurement() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Procurement procurement = this.procurementRepository.findProcurementByUserAndProcurementState(user, ProcurementState.CREATED).orElse(new Procurement());
        return createProcurementDTO(procurement);

    }

    @Override
    public List<ProcurementDTO> getAllPastProcurement() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Procurement> procurements = this.procurementRepository.findAllProcurementByUserAndProcurementState(user, ProcurementState.COMPLETE).orElse(null);
        List<ProcurementDTO> procurementDTOS = new ArrayList<>();
        assert procurements != null;
        if (!procurements.isEmpty()) {
            for (Procurement procurement : procurements) {
                procurementDTOS.add(createProcurementDTO(procurement));
            }
        }
        return procurementDTOS;
    }

    @Override
    public void updateProcurement(Integer procurementId, Procurement procurement) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Procurement> existingProcurement = this.procurementRepository.findById(procurementId);
        if (existingProcurement.isPresent()) {
            Procurement procurementUpdate = existingProcurement.get();
            procurementUpdate.setUser(user);
            procurementUpdate.setProcurementDate(Optional.ofNullable(procurement.getProcurementDate()).orElse(procurementUpdate.getProcurementDate()));
            procurementUpdate.setProcurementState(Optional.ofNullable(procurement.getProcurementState()).orElse(procurementUpdate.getProcurementState()));
            if (procurement.getLineProcurementList() != null) {
                procurement.getLineProcurementList().forEach(procurementUpdate::addLineProcurement);
            }
            this.procurementRepository.save(procurementUpdate);
        } else {
            throw new EntityNotFoundException(MessageConstants.PROCUREMENT_SERVICE_ERROR_MESSAGE + procurementId);
        }

    }

    @Override
    public void deleteProcurement(Integer procurementId) {
        Optional<Procurement> existingProcurement = this.procurementRepository.findById(procurementId);
        if (existingProcurement.isPresent()) {
            this.procurementRepository.deleteById(procurementId);
        } else {
            throw new EntityNotFoundException(MessageConstants.PROCUREMENT_SERVICE_ERROR_MESSAGE + procurementId);
        }
    }

    private ProcurementDTO createProcurementDTO(Procurement procurement) {
        List<LineProcurementDTO> lineProcurementDTOS = new ArrayList<>();
        ProcurementDTO procurementDTO = new ProcurementDTO();
        procurementDTO.setProcurementId(procurement.getProcurementId());
        procurementDTO.setProcurementDate(procurement.getProcurementDate());
        procurementDTO.setProcurementState(procurement.getProcurementState());

        if(procurement.getProcurementId() == null && procurement.getLineProcurementList().isEmpty()){
            procurementDTO.setProcurementId(0);
            procurementDTO.setLineProcurementList(new ArrayList<>());
            procurementDTO.setUserId(0);
            procurementDTO.setAmount(0);
            return procurementDTO;
        }
        procurement.getLineProcurementList().forEach(lineprocurement -> {
            LineProcurementDTO lineProcurementDTO = new LineProcurementDTO();
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(lineprocurement.getProduct().getProductId());
            productDTO.setProductName(lineprocurement.getProduct().getProductName());
            productDTO.setProductStock(lineprocurement.getProduct().getProductStock());
            productDTO.setProductLink(lineprocurement.getProduct().getProductLink());
            lineProcurementDTO.setLineId(lineprocurement.getLineId());
            lineProcurementDTO.setLineQuantity(lineprocurement.getLineQuantity());
            lineProcurementDTO.setLineUnitPrice(lineprocurement.getLineUnitPrice());
            lineProcurementDTO.setProduct(productDTO);
            lineProcurementDTOS.add(lineProcurementDTO);
        });

        procurementDTO.setLineProcurementList(lineProcurementDTOS);
        procurementDTO.setUserId(procurement.getUser().getUserId());
        setProcurementAmount(procurementDTO);

        return procurementDTO;
    }


    private void setProcurementAmount(ProcurementDTO procurement) {
        int total = 0;
        for (LineProcurementDTO line : procurement.getLineProcurementList()) {
            total += line.getLineUnitPrice();
        }
        procurement.setAmount(total);
    }
}
