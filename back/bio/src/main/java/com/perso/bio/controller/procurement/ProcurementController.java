package com.perso.bio.controller.procurement;

import com.perso.bio.constants.MessageConstants;
import com.perso.bio.dto.ProcurementDTO;
import com.perso.bio.model.procurement.Procurement;
import com.perso.bio.service.procurement.ProcurementService;
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
    public ResponseEntity<Procurement> getProcurement(@PathVariable Integer id) {
        Procurement procurement = this.procurementService.getProcurement(id);
        return ResponseEntity.ok(procurement);
    }

    @GetMapping(path = "/current")
    public ResponseEntity<ProcurementDTO> getCurrentProcurement() {
        ProcurementDTO procurement = this.procurementService.getCurrentProcurement();
        return ResponseEntity.ok(procurement);
    }

    @GetMapping(path = "/past")
    public ResponseEntity<List<ProcurementDTO>> getPastProcurement() {
        List<ProcurementDTO> procurements = this.procurementService.getAllPastProcurement();
        return ResponseEntity.ok(procurements);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<ProcurementDTO>> getAllProcurement() {
        List<ProcurementDTO> procurements = this.procurementService.getAllProcurementForCustomer();
        return ResponseEntity.ok(procurements);
    }

    @PutMapping(path = "update/{id}")
    public ResponseEntity<String> updateProcurement(@PathVariable Integer id, @RequestBody Procurement procurement) {
        this.procurementService.updateProcurement(id, procurement);
        return new ResponseEntity<>(MessageConstants.UPDATE, HttpStatus.NO_CONTENT);
    }


    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity<String> deleteProcurement(@PathVariable Integer id) {
        this.procurementService.deleteProcurement(id);
        return new ResponseEntity<>(MessageConstants.DELETE, HttpStatus.NO_CONTENT);

    }
}
