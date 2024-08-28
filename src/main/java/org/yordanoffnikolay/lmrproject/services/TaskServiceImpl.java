package org.yordanoffnikolay.lmrproject.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.yordanoffnikolay.lmrproject.dtos.CustomTaskDto;
import org.yordanoffnikolay.lmrproject.dtos.OfficeWorkDto;
import org.yordanoffnikolay.lmrproject.dtos.VisitDto;
import org.yordanoffnikolay.lmrproject.helpers.AuthenticationHelper;
import org.yordanoffnikolay.lmrproject.models.*;
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
        Workday workday = workdayRepository.findWorkdayByWorkdayId(visitDto.getWorkdayId());
        User loggedUser = authenticationHelper.tryGetUser(SecurityContextHolder.getContext().getAuthentication());
        if (workday.getUser().getUserId() != loggedUser.getUserId()
                && !loggedUser.getAuthorities().contains("ADMIN")
                && !loggedUser.getAuthorities().contains("MANAGER")) {
            throw new RuntimeException("Not authorized");
        }
        visit.setWorkday(workday);
        visit.setClient(clientRepository.findByName(visitDto.getClientName())
                .orElseThrow(() -> new RuntimeException("Client not found")));
        visit.setWorkplace(workplaceRepository.findByName(visitDto.getWorkplaceName())
                .orElseThrow(() -> new RuntimeException("Workplace not found")));
        visit.setLocked(false);
        visit.setCompleted(false);
        return taskRepository.save(visit);
    }

    @Override
    public Task createTask(OfficeWorkDto officeWorkDto) {
        OfficeWork officeWork = new OfficeWork();
        officeWork.setTimeSpent(officeWorkDto.getTimeSpent());
        officeWork.setWorkday(workdayRepository.findWorkdayByWorkdayId(officeWorkDto.getWorkdayId()));
        return taskRepository.save(officeWork);
    }

    @Override
    public Task createTask(CustomTaskDto customTaskDto) {
        //todo
        throw new RuntimeException("Custom task not implemented yet!");
    }

    @Override
    public Task getTaskById(long taskId) {
        return taskRepository.findByTaskId(taskId);
    }
}
