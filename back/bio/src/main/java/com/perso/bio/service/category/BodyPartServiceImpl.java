package com.perso.bio.service.category;


import com.perso.bio.constants.MessageConstants;
import com.perso.bio.model.category.BodyPart;
import com.perso.bio.repository.BodyPartRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("jpa")
public class BodyPartServiceImpl implements BodyPartService {

    BodyPartRepository bodyPartRepository;

    @Autowired
    public BodyPartServiceImpl(BodyPartRepository bodyPartRepository) {
        this.bodyPartRepository = bodyPartRepository;
    }

    @Override
    public void createBodyPart(BodyPart bodyPart) {
        this.bodyPartRepository.save(bodyPart);
    }

    @Override
    public BodyPart getBodyPart(Integer bodyId) {
        return this.bodyPartRepository.findById(bodyId).orElseThrow(() -> new EntityNotFoundException(MessageConstants.BODY_SERVICE_ERROR_MESSAGE + bodyId));
    }

    @Override
    public List<BodyPart> getAllBodyParts() {
        return this.bodyPartRepository.findAll();
    }

    @Override
    public void updateBodyPart(Integer bodyId, BodyPart bodyPart) {
        Optional<BodyPart> existingBodyPart = this.bodyPartRepository.findById(bodyId);
        if (existingBodyPart.isPresent()) {
            BodyPart bodyPartUpdate = existingBodyPart.get();
            bodyPartUpdate.setBodyName(Optional.ofNullable(bodyPart.getBodyName()).orElse(bodyPartUpdate.getBodyName()));
            this.bodyPartRepository.save(bodyPartUpdate);
        } else {
            throw new EntityNotFoundException(MessageConstants.BODY_SERVICE_ERROR_MESSAGE + bodyId);
        }

    }

    @Override
    public void deleteBodyPart(Integer bodyId) {
        Optional<BodyPart> existingBodyPart = this.bodyPartRepository.findById(bodyId);
        if (existingBodyPart.isPresent()) {
            this.bodyPartRepository.deleteById(bodyId);
        } else {
            throw new EntityNotFoundException(MessageConstants.BODY_SERVICE_ERROR_MESSAGE + bodyId);
        }

    }
}
