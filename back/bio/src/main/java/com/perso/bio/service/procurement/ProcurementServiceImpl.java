package com.perso.bio.service.procurement;


import com.perso.bio.constants.MessageConstants;
import com.perso.bio.controller.StorageController;
import com.perso.bio.dto.LineProcurementDTO;
import com.perso.bio.dto.ProcurementDTO;
import com.perso.bio.dto.ProductDTO;
import com.perso.bio.enums.ProcurementState;
import com.perso.bio.model.procurement.Procurement;
import com.perso.bio.model.user_management.User;
import com.perso.bio.repository.ProcurementRepository;
import com.perso.bio.service.notification_user.NotificationUserService;
import com.perso.bio.service.product.ProductServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.MalformedURLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("jpa")
public class ProcurementServiceImpl implements ProcurementService {


    ProcurementRepository procurementRepository;

    NotificationUserService notificationUserService;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ProcurementServiceImpl.class);
    @Autowired
    public ProcurementServiceImpl(ProcurementRepository procurementRepository, NotificationUserService notificationUserService) {
        this.procurementRepository = procurementRepository;
        this.notificationUserService = notificationUserService;
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
    public ProcurementDTO getProcurement(Integer procurementId) {
        Procurement procurement = this.procurementRepository.findById(procurementId).orElseThrow(() -> new EntityNotFoundException(MessageConstants.PROCUREMENT_SERVICE_ERROR_MESSAGE + procurementId));
        return createProcurementDTO(procurement);
    }

    @Override
    public List<ProcurementDTO> getAllProcurementsForCustomer() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Procurement> procurements = this.procurementRepository.findAllProcurementByUserOrderByProcurementDateDesc(user).orElse(null);
        List<ProcurementDTO> procurementDTOS = new ArrayList<>();
        if (procurements != null) {
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
    public List<ProcurementDTO> getAllPastProcurements() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Procurement> procurements = this.procurementRepository.findAllProcurementByUserAndProcurementStateOrderByProcurementDateDesc(user, ProcurementState.COMPLETE).orElse(null);
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
    public List<ProcurementDTO> getAllValidateProcurementsForCustomer() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Procurement> procurements = this.procurementRepository.findAllProcurementByUserAndProcurementStateOrderByProcurementDateDesc(user, ProcurementState.VALIDATE).orElse(null);
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
    public List<ProcurementDTO> getAllValidateProcurements() {
        List<Procurement> procurements = this.procurementRepository.findAllProcurementByProcurementStateOrderByProcurementDateDesc(ProcurementState.VALIDATE).orElse(null);
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
    public void validateProcurement(Integer procurementId) throws MessagingException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getUserAddress() == null) {
            throw new EntityNotFoundException(MessageConstants.PROCUREMENT_SERVICE_ERROR_MESSAGE + procurementId);
        }
        Optional<Procurement> existingProcurement = this.procurementRepository.findById(procurementId);
        if (existingProcurement.isPresent() && !existingProcurement.get().getLineProcurementList().isEmpty()) {
            Procurement procurementUpdate = existingProcurement.get();
            procurementUpdate.setUser(user);
            procurementUpdate.setProcurementDate(Instant.now());
            procurementUpdate.setProcurementState(ProcurementState.VALIDATE);
            procurementUpdate.getLineProcurementList().forEach(line -> {
                if (line.getProduct().getProductStock() != 0 && line.getProduct().getProductStock() >= line.getLineQuantity() && line.getLineQuantity() > 0) {
                    line.getProduct().setProductStock(line.getProduct().getProductStock() - line.getLineQuantity());
                    if (line.getProduct().getProductStock() == 0) {
                        line.getProduct().setVisible(false);

                    }
                } else {
                    throw new RuntimeException(MessageConstants.PRODUCT_OUT_OF_STOCK);
                }

            });

            this.procurementRepository.save(procurementUpdate);
            this.notificationUserService.sendValidateProcurementNotification(procurementUpdate);
            this.notificationUserService.sendValidateProcurementForAdminNotification(procurementUpdate);
        } else {
            throw new EntityNotFoundException(MessageConstants.PROCUREMENT_SERVICE_ERROR_MESSAGE + procurementId);
        }

    }

    @Override
    public void completeProcurement(Integer procurementId) throws MessagingException {
        Optional<Procurement> existingProcurement = this.procurementRepository.findById(procurementId);
        if (existingProcurement.isPresent() && !existingProcurement.get().getLineProcurementList().isEmpty()) {
            Procurement procurementUpdate = existingProcurement.get();
            procurementUpdate.getLineProcurementList().forEach(line -> {
                if (line.getProduct().getProductStock() != 0 && line.getProduct().getProductStock() >= line.getLineQuantity() && line.getLineQuantity() > 0) {
                    line.getProduct().setProductStock(line.getProduct().getProductStock() - line.getLineQuantity());
                    if (line.getProduct().getProductStock() == 0) {
                        line.getProduct().setVisible(false);

                    }
                } else {
                    throw new RuntimeException(MessageConstants.PRODUCT_OUT_OF_STOCK);
                }

            });
            procurementUpdate.setProcurementState(ProcurementState.COMPLETE);
            this.procurementRepository.save(procurementUpdate);
            this.notificationUserService.sendCompleteProcurementNotification(procurementUpdate);
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

        if (procurement.getProcurementId() == null && procurement.getLineProcurementList().isEmpty()) {
            procurementDTO.setProcurementId(0);
            procurementDTO.setLineProcurementList(new ArrayList<>());
            procurementDTO.setUserId(new User());
            procurementDTO.setAmount(0);
            return procurementDTO;
        }
        procurement.getLineProcurementList().forEach(lineprocurement -> {
            LineProcurementDTO lineProcurementDTO = new LineProcurementDTO();
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(lineprocurement.getProduct().getProductId());
            productDTO.setProductName(lineprocurement.getProduct().getProductName());
            productDTO.setProductStock(lineprocurement.getProduct().getProductStock());
            productDTO.setProductLink(createLinkForProduct(lineprocurement.getProduct().getProductLink()));
            productDTO.setProductUnitPrice(lineprocurement.getProduct().getProductUnitPrice());
            lineProcurementDTO.setLineId(lineprocurement.getLineId());
            lineProcurementDTO.setLineQuantity(lineprocurement.getLineQuantity());
            lineProcurementDTO.setLineUnitPrice(lineprocurement.getLineUnitPrice());
            lineProcurementDTO.setProduct(productDTO);
            lineProcurementDTOS.add(lineProcurementDTO);
        });

        procurementDTO.setLineProcurementList(lineProcurementDTOS);
        procurementDTO.setUserId(procurement.getUser());
        setProcurementAmount(procurementDTO);

        return procurementDTO;
    }

    private String createLinkForProduct(String link) {
        String url = null;
        try {
            url = MvcUriComponentsBuilder
                    .fromMethodCall(MvcUriComponentsBuilder
                            .on(StorageController.class)
                            .getFile(link))
                    .build()
                    .toUriString();
        } catch (MalformedURLException e) {
            String message = String.format("[ProcurementService@%s::createLinkForProduct] Fail to create Link: %s", this.getClass().getSimpleName(), e.getMessage());
            log.error(message);
        }
        return url;
    }

    private void setProcurementAmount(ProcurementDTO procurement) {
        int total = 0;
        for (LineProcurementDTO line : procurement.getLineProcurementList()) {
            total += line.getLineUnitPrice();
        }
        procurement.setAmount(total);
    }
}
