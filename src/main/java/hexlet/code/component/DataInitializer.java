package hexlet.code.component;

import hexlet.code.dto.label.LabelCreateDTO;
import hexlet.code.dto.taskstatus.TaskStatusCreateDTO;
import hexlet.code.dto.user.UserCreateDTO;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.service.implementations.LabelServiceImpl;
import hexlet.code.service.implementations.TaskStatusServiceImpl;
import hexlet.code.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {
    private final UserService userService;
    private final TaskStatusRepository taskStatusRepository;
    private final TaskStatusServiceImpl taskStatusService;
    private final LabelServiceImpl labelService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var userData = new UserCreateDTO();
        userData.setEmail("hexlet@example.com");
        userData.setPassword("qwerty");
        userService.create(userData);

        String[][] statuses = {
                {"Draft", "draft"},
                {"ToReview", "to_review"},
                {"ToBeFixed", "to_be_fixed"},
                {"ToPublish", "to_publish"},
                {"Published", "published"}
        };

        for (String[] status : statuses) {
            String name = status[0];
            String slug = status[1];

            if (!taskStatusRepository.existsBySlug(slug)) {
                var taskData = new TaskStatusCreateDTO();
                taskData.setName(name);
                taskData.setSlug(slug);
                taskStatusService.create(taskData);
            }
        }

        var featureLabel = new LabelCreateDTO();
        featureLabel.setName("feature");
        labelService.create(featureLabel);

        var bugLabel = new LabelCreateDTO();
        bugLabel.setName("bug");
        labelService.create(bugLabel);
    }
}
