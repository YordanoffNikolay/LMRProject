package org.yordanoffnikolay.lmrproject.mappers;

import org.springframework.stereotype.Component;
import org.yordanoffnikolay.lmrproject.dtos.WorkplaceDto;
import org.yordanoffnikolay.lmrproject.models.Workplace;

@Component
public class WorkplaceMapper {

    public Workplace fromDto(WorkplaceDto workplaceDto) {
        Workplace workplace = new Workplace();
        workplace.setName(workplaceDto.getName());
        workplace.setAddress(workplaceDto.getAddress());
        return workplace;
    }
}
