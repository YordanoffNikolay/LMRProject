package org.yordanoffnikolay.lmrproject.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.yordanoffnikolay.lmrproject.dtos.CustomTaskDto;
import org.yordanoffnikolay.lmrproject.dtos.OfficeWorkDto;
import org.yordanoffnikolay.lmrproject.dtos.VisitDto;
import org.yordanoffnikolay.lmrproject.helpers.AuthenticationHelper;
import org.yordanoffnikolay.lmrproject.models.OfficeWork;
import org.yordanoffnikolay.lmrproject.models.Task;
import org.yordanoffnikolay.lmrproject.models.Visit;
import org.yordanoffnikolay.lmrproject.repositories.ClientRepository;
import org.yordanoffnikolay.lmrproject.repositories.TaskRepository;
import org.yordanoffnikolay.lmrproject.repositories.WorkdayRepository;
import org.yordanoffnikolay.lmrproject.repositories.WorkplaceRepository;

@Service
public class TaskServiceImpl implements TaskService {

    private final AuthenticationHelper authenticationHelper;
    private final WorkdayRepository workdayRepository;
    private final TaskRepository taskRepository;
    private final ClientRepository clientRepository;
    private final WorkplaceRepository workplaceRepository;

    public TaskServiceImpl(AuthenticationHelper authenticationHelper,
                           WorkdayRepository workdayRepository,
                           TaskRepository taskRepository,
                           ClientRepository clientRepository, WorkplaceRepository workplaceRepository) {
        this.authenticationHelper = authenticationHelper;
        this.workdayRepository = workdayRepository;
        this.taskRepository = taskRepository;
        this.clientRepository = clientRepository;
        this.workplaceRepository = workplaceRepository;
    }

    @Override
    public Task createTask(VisitDto visitDto) {
        Visit visit = new Visit();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        visit.setUser(authenticationHelper.tryGetUser(authentication));
        visit.setWorkday(workdayRepository.findByDateAndUser(visitDto.getDate(), visit.getUser()));
        visit.setClient(clientRepository.findByName(visitDto.getClientName()).orElseThrow(() -> new RuntimeException("Client not found")));
        visit.setWorkplace(workplaceRepository.findByName(visitDto.getWorkplaceName()).orElseThrow(() -> new RuntimeException("Workplace not found")));
//        visit.setLocked(false);
//        visit.setCompleted(false);
        return taskRepository.save(visit);
    }

    @Override
    public Task createTask(OfficeWorkDto officeWorkDto) {
        OfficeWork officeWork = new OfficeWork();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        officeWork.setTimeSpent(officeWorkDto.getTimeSpent());
        officeWork.setUser(authenticationHelper.tryGetUser(authentication));
        officeWork.setWorkday(workdayRepository.findByDateAndUser(officeWorkDto.getDate(), officeWork.getUser()));
        return taskRepository.save(officeWork);
    }

    @Override
    public Task createTask(CustomTaskDto customTaskDto) {
        throw new RuntimeException("Not implemented yet!");
    }
}
