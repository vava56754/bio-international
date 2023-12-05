package com.perso.bio.controller.procurement;

import com.perso.bio.constants.MessageConstants;
import com.perso.bio.dto.ProcurementDTO;
import com.perso.bio.model.procurement.LineProcurement;
import com.perso.bio.model.procurement.Procurement;
import com.perso.bio.service.procurement.LineProcurementService;
import com.perso.bio.service.procurement.ProcurementService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/line")
public class LineProcurementController {


    private final LineProcurementService lineProcurementService;


    private final ProcurementService procurementService;

    @Autowired
    public LineProcurementController(LineProcurementService lineProcurementService, ProcurementService procurementService) {
        this.lineProcurementService = lineProcurementService;
        this.procurementService = procurementService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<String> createLineProcurement(@RequestBody LineProcurement lineProcurement) {
        lineProcurementForCreation(lineProcurement);
        return new ResponseEntity<>(MessageConstants.CREATE, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<LineProcurement> getOneLine(@PathVariable Integer id) {
            LineProcurement lineProcurement = this.lineProcurementService.getLine(id);
            return ResponseEntity.ok(lineProcurement);
    }

    @GetMapping(path = "/all/{id}")
    public ResponseEntity<List<LineProcurement>> getAllLine(@PathVariable Integer id) {
            List<LineProcurement> lineProcurementList = this.lineProcurementService.getAllByProcurementId(id);
            return ResponseEntity.ok(lineProcurementList);
    }

    @PutMapping(path = "update/{id}")
    public ResponseEntity<String> updateLineProcurement(@PathVariable Integer id, @RequestBody LineProcurement lineProcurement) {
            this.lineProcurementService.updateLineProcurement(id, lineProcurement);
            return new ResponseEntity<>(MessageConstants.UPDATE, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity<String> deleteLineProcurement(@PathVariable Integer id) {
            this.lineProcurementService.deleteLineProcurement(id);
            return new ResponseEntity<>(MessageConstants.DELETE, HttpStatus.NO_CONTENT);
    }

    private void lineProcurementForCreation(LineProcurement lineProcurement) {
        ProcurementDTO procurementDTO = this.procurementService.getCurrentProcurement();

        if (procurementDTO.getProcurementId() == 0) {
            Procurement newProcurement = this.procurementService.createProcurementForLine();
            lineProcurement.setProcurement(newProcurement);
            this.lineProcurementService.createLineProcurement(lineProcurement);

        } else {
            Procurement procurement = new Procurement();
            procurement.setProcurementId(procurementDTO.getProcurementId());
            procurement.setProcurementDate(procurementDTO.getProcurementDate());
            procurement.setProcurementState(procurementDTO.getProcurementState());
            lineProcurement.setProcurement(procurement);
            LineProcurement lineProcurementExist = this.lineProcurementService.findExistLine(lineProcurement.getProduct().getProductId(), lineProcurement.getProcurement().getProcurementId());
            if (lineProcurementExist != null) {
                Integer quantityExist = lineProcurementExist.getLineQuantity();
                Integer quantity = lineProcurement.getLineQuantity();
                Integer result = quantity + quantityExist;
                lineProcurement.setLineQuantity(result);
                this.lineProcurementService.updateLineProcurement(lineProcurementExist.getLineId(), lineProcurement);
            } else {
                this.lineProcurementService.createLineProcurement(lineProcurement);
            }


        }

        if (lineProcurement.getProduct() == null) {
            throw new ObjectNotFoundException(MessageConstants.PRODUCT_NOT_FOUND, lineProcurement.getProduct());
        }
    }
}
