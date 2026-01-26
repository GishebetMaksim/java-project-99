package hexlet.code.service;

import hexlet.code.dto.task.TaskCreateDTO;
import hexlet.code.dto.task.TaskDTO;
import hexlet.code.dto.task.TaskParamsDTO;
import hexlet.code.dto.task.TaskUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.repository.TaskRepository;
import hexlet.code.specification.TaskSpecification;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository repository;
    private final TaskMapper mapper;
    private final TaskSpecification specBuilder;

    public List<TaskDTO> findAll(TaskParamsDTO params, int page) {
//        return repository.findAll().stream()
//                .map(mapper::map)
//                .toList();

        var spec = specBuilder.build(params);
        var tasks = repository.findAll(spec, PageRequest.of(page - 1, 10));
        return tasks.map(mapper::map).toList();
    }

    public TaskDTO findById(Long id) {
        var task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id = " + id + " not found."));
        return mapper.map(task);
    }

    public TaskDTO create(TaskCreateDTO data) {
        var task = mapper.map(data);
        repository.save(task);
        return mapper.map(task);
    }

    public TaskDTO update(Long id, TaskUpdateDTO data) {
        var task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id = " + id + " not found."));

        mapper.update(data, task);
        repository.save(task);
        return mapper.map(task);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
