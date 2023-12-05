package com.perso.bio.service.category;

import com.perso.bio.model.category.BodyPart;

import java.util.List;

public interface BodyPartService {

    void createBodyPart(BodyPart bodyPart);

    BodyPart getBodyPart(Integer bodyId);

    List<BodyPart> getAllBodyParts();

    void updateBodyPart(Integer bodyId, BodyPart bodyPart);

    void deleteBodyPart(Integer bodyId);
}
