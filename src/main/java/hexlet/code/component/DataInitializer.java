package hexlet.code.component;

import hexlet.code.dto.label.LabelCreateDTO;
import hexlet.code.dto.taskstatus.TaskStatusCreateDTO;
import hexlet.code.model.User;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.service.CustomUserDetailsService;
import hexlet.code.service.LabelService;
import hexlet.code.service.TaskStatusService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {
    @Autowired
    CustomUserDetailsService userService;

    @Autowired
    TaskStatusRepository taskStatusRepository;
    @Autowired
    TaskStatusService taskStatusService;
    @Autowired
    LabelService labelService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var user = new User();
        user.setEmail("hexlet@example.com");
        user.setPasswordDigest("qwerty");
        userService.createUser(user);


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
