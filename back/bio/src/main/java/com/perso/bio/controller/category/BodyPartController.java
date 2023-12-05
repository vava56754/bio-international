package com.perso.bio.controller.category;

import com.perso.bio.constants.MessageConstants;
import com.perso.bio.model.category.BodyPart;
import com.perso.bio.service.category.BodyPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/body")
public class BodyPartController {


    private final BodyPartService bodyPartService;

    @Autowired
    public BodyPartController(BodyPartService bodyPartService) {
        this.bodyPartService = bodyPartService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<String> createBodyPart(@RequestBody BodyPart bodyPart) {
        this.bodyPartService.createBodyPart(bodyPart);
        return new ResponseEntity<>(MessageConstants.CREATE, HttpStatus.CREATED);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<String> updateBodyPart(@PathVariable Integer id, @RequestBody BodyPart bodyPart) {
        this.bodyPartService.updateBodyPart(id, bodyPart);
        return new ResponseEntity<>(MessageConstants.UPDATE, HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteBodyPart(@PathVariable Integer id) {
        this.bodyPartService.deleteBodyPart(id);
        return new ResponseEntity<>(MessageConstants.DELETE, HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<BodyPart>> getAllBodyPart() {
        List<BodyPart> bodyParts = this.bodyPartService.getAllBodyParts();
        return ResponseEntity.ok(bodyParts);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BodyPart> getBodyPart(@PathVariable Integer id) {
        BodyPart bodyPart = this.bodyPartService.getBodyPart(id);
        return ResponseEntity.ok(bodyPart);
    }
}
