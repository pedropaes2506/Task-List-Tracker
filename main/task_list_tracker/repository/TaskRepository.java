package main.task_list_tracker.repository;

import lombok.extern.log4j.Log4j2;
import main.task_list_tracker.conn.ConnectionFactory;
import main.task_list_tracker.domain.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

@Log4j2
public class TaskRepository {
    public static void save(Task task) {
        log.info("Saving task '{}'", task);
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = createPreparedStatementSave(conn, task)) {
            ps.execute();
        } catch (SQLException e) {
            log.error("Error while trying to save task '{}'", task.getId(), e);
        }
    }

    private static PreparedStatement createPreparedStatementSave(Connection conn, Task task) throws SQLException {
        String sql = "INSERT INTO `task_list_tracker_db`.`tasks_tb` (`name`, `description`, `status`, `createdAt`, `updatedAt`) VALUES (?, ?, ?, ?, ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, task.getName());
        ps.setString(2, task.getDescription());
        ps.setString(3, task.getStatus());
        ps.setTimestamp(4, Timestamp.valueOf(task.getCreatedAt()));
        ps.setTimestamp(5, Timestamp.valueOf(task.getUpdatedAt()));
        return ps;
    }
}
