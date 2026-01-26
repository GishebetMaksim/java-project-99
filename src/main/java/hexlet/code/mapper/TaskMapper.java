package hexlet.code.mapper;

import hexlet.code.dto.task.TaskCreateDTO;
import hexlet.code.dto.task.TaskDTO;
import hexlet.code.dto.task.TaskUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        uses = { JsonNullableMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskMapper {
    @Autowired
    private TaskStatusRepository taskStatusRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LabelRepository labelRepository;

    @Mapping(target = "title", source = "name")
    @Mapping(target = "content", source = "description")
    @Mapping(target = "status", source = "taskStatus.slug")
    @Mapping(target = "assigneeId", source = "assignee.id")
    @Mapping(target = "taskLabelIds", source = "labels", qualifiedByName = "labelsToLabelIds")
    public abstract TaskDTO map(Task model);

    @Mapping(target = "name", source = "title")
    @Mapping(target = "description", source = "content")
    @Mapping(target = "taskStatus.slug", source = "status")
    @Mapping(target = "assignee.id", source = "assigneeId")
    @Mapping(target = "labels", source = "taskLabelIds", qualifiedByName = "labelIdsToLabels")
    public abstract Task map(TaskDTO data);

    @Mapping(target = "name", source = "title")
    @Mapping(target = "description", source = "content")
    @Mapping(target = "taskStatus", source = "status", qualifiedByName = "statusToTaskStatus")
    @Mapping(target = "assignee", source = "assigneeId", qualifiedByName = "assigneeIdToAssignee")
    @Mapping(target = "labels", source = "taskLabelIds", qualifiedByName = "labelIdsToLabels")
    public abstract Task map(TaskCreateDTO data);

    @Mapping(target = "name", source = "title")
    @Mapping(target = "description", source = "content")
    @Mapping(target = "taskStatus", source = "status")
    @Mapping(target = "assignee", source = "assigneeId")
    @Mapping(target = "labels", source = "taskLabelIds", qualifiedByName = "labelIdsToLabels")
    public abstract void update(TaskUpdateDTO data, @MappingTarget Task model);


    // Long assigneeId -> User assignee
    @Named("assigneeIdToAssignee")
    public User assigneeIdToAssignee(Long assigneeId) {
        if (assigneeId == null) {
            return null;
        }
        return userRepository.findById(assigneeId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id = " + assigneeId + " not found."));
    }

    // String status -> TaskStatus taskStatus
    @Named("statusToTaskStatus")
    public TaskStatus statusToTaskStatus(String status) {
        return taskStatusRepository.findBySlug(status)
                .orElseThrow(() -> new ResourceNotFoundException("Task with slug = " + status + " not found."));
    }

    // Set<Long> labelIds -> Set<Label> labels
    @Named("labelIdsToLabels")
    public Set<Label> labelIdsToLabels(Set<Long> labelIds) {
        if (labelIds == null || labelIds.isEmpty()) {
            return Set.of();
        }

        var labels = labelRepository.findAllById(labelIds);
        return new HashSet<>(labels);
    }

    // Set<Label> labels -> Set<Long> labelIds
    @Named("labelsToLabelIds")
    public Set<Long> labelsToLabelIds(Set<Label> labels) {
        if (labels == null || labels.isEmpty()) {
            return Set.of();
        }

        return labels.stream()
                .map(Label::getId)
                .collect(Collectors.toSet());
    }

}
