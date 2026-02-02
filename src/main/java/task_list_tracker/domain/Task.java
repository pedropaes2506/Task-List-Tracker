package task_list_tracker.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class Task {
    Integer id;
    String name;
    String description;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
