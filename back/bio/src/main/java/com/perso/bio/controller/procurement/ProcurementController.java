package com.perso.bio.controller.procurement;

import com.perso.bio.constants.MessageConstants;
import com.perso.bio.dto.ProcurementDTO;
import com.perso.bio.model.procurement.Procurement;
import com.perso.bio.service.procurement.ProcurementService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/procurement")
public class ProcurementController {

    private final ProcurementService procurementService;

    @Autowired
    public ProcurementController(ProcurementService procurementService) {
        this.procurementService = procurementService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<String> createProcurement() {
        this.procurementService.createProcurement();
        return new ResponseEntity<>(MessageConstants.CREATE, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProcurementDTO> getProcurement(@PathVariable Integer id) {
        ProcurementDTO procurement = this.procurementService.getProcurement(id);
        return ResponseEntity.ok(procurement);
    }

    @GetMapping(path = "/current")
    public ResponseEntity<ProcurementDTO> getCurrentProcurement() {
        ProcurementDTO procurement = this.procurementService.getCurrentProcurement();
        return ResponseEntity.ok(procurement);
    }

    @GetMapping(path = "/past")
    public ResponseEntity<List<ProcurementDTO>> getAllPastProcurements() {
        List<ProcurementDTO> procurements = this.procurementService.getAllPastProcurements();
        return ResponseEntity.ok(procurements);
    }

    @GetMapping(path = "/validate")
    public ResponseEntity<List<ProcurementDTO>> getAllValidateProcurementsForCustomer() {
        List<ProcurementDTO> procurements = this.procurementService.getAllValidateProcurementsForCustomer();
        return ResponseEntity.ok(procurements);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<ProcurementDTO>> getAllProcurements() {
        List<ProcurementDTO> procurements = this.procurementService.getAllProcurementsForCustomer();
        return ResponseEntity.ok(procurements);
    }

    @GetMapping(path = "/validate/all")
    public ResponseEntity<List<ProcurementDTO>> getAllValidateProcurements() {
        List<ProcurementDTO> procurements = this.procurementService.getAllValidateProcurements();
        return ResponseEntity.ok(procurements);
    }

    @PutMapping(path = "update/{id}")
    public ResponseEntity<String> updateProcurement(@PathVariable Integer id, @RequestBody Procurement procurement) {
        this.procurementService.updateProcurement(id, procurement);
        return new ResponseEntity<>(MessageConstants.UPDATE, HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "validate/{id}")
    public ResponseEntity<String> validateProcurement(@PathVariable Integer id) throws MessagingException {
        this.procurementService.validateProcurement(id);
        return new ResponseEntity<>(MessageConstants.UPDATE, HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "complete/{id}")
    public ResponseEntity<String> completeProcurement(@PathVariable Integer id) throws MessagingException {
        this.procurementService.completeProcurement(id);
        return new ResponseEntity<>(MessageConstants.UPDATE, HttpStatus.NO_CONTENT);
    }


    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity<String> deleteProcurement(@PathVariable Integer id) {
        this.procurementService.deleteProcurement(id);
        return new ResponseEntity<>(MessageConstants.DELETE, HttpStatus.NO_CONTENT);

    }
}
