package hexlet.code.service.interfaces;

import hexlet.code.dto.taskstatus.TaskStatusCreateDTO;
import hexlet.code.dto.taskstatus.TaskStatusDTO;
import hexlet.code.dto.taskstatus.TaskStatusUpdateDTO;

import java.util.List;

public interface TaskStatusService {
    List<TaskStatusDTO> getAll();
    TaskStatusDTO create(TaskStatusCreateDTO data);
    TaskStatusDTO findById(Long id);
    TaskStatusDTO update(TaskStatusUpdateDTO data, Long id);
    void delete(Long id);
}
