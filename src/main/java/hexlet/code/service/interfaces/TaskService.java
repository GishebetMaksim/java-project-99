package hexlet.code.service.interfaces;

import hexlet.code.dto.task.TaskCreateDTO;
import hexlet.code.dto.task.TaskDTO;
import hexlet.code.dto.task.TaskParamsDTO;
import hexlet.code.dto.task.TaskUpdateDTO;

import java.util.List;

public interface TaskService {
    List<TaskDTO> findAll(TaskParamsDTO params, int page);
    TaskDTO findById(Long id);
    TaskDTO create(TaskCreateDTO data);
    TaskDTO update(Long id, TaskUpdateDTO data);
    void delete(Long id);
}
