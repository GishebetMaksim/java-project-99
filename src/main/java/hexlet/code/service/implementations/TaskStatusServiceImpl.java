package hexlet.code.service.implementations;

import hexlet.code.dto.taskstatus.TaskStatusCreateDTO;
import hexlet.code.dto.taskstatus.TaskStatusDTO;
import hexlet.code.dto.taskstatus.TaskStatusUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.service.interfaces.TaskStatusService;
import hexlet.code.utils.UserUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TaskStatusServiceImpl implements TaskStatusService {
    private final TaskStatusRepository repository;
    private final TaskStatusMapper mapper;
    private final UserUtils userUtils;

    public List<TaskStatusDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::map)
                .toList();
    }

    public TaskStatusDTO create(TaskStatusCreateDTO data) {
        var taskStatus = mapper.map(data);
        repository.save(taskStatus);
        return mapper.map(taskStatus);
    }

    public TaskStatusDTO findById(Long id) {
        var taskStatus = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TaskStatus with id = " + id + " not found."));
        return mapper.map(taskStatus);
    }

    public TaskStatusDTO update(TaskStatusUpdateDTO data, Long id) {
        var taskStatus = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TaskStatus with id = " + id + " not found."));
        mapper.update(data, taskStatus);
        repository.save(taskStatus);
        return mapper.map(taskStatus);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
