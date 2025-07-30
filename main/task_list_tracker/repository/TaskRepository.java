package main.task_list_tracker.repository;

import lombok.extern.log4j.Log4j2;
import main.task_list_tracker.conn.ConnectionFactory;
import main.task_list_tracker.domain.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public class TaskRepository {
    public static List<Task> findAll() {
        log.info("Finding all tasks");
        List<Task> tasks = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = createPreparedStatementFindAll(conn);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Task task = Task
                        .builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .status(rs.getString("status"))
                        .createdAt(rs.getTimestamp("createdAt").toLocalDateTime())
                        .updatedAt(rs.getTimestamp("updatedAt").toLocalDateTime())
                        .build();

                tasks.add(task);
            }
        } catch (SQLException e) {
            log.error("Error while trying to find all tasks", e);
        }
        return tasks;
    }

    private static PreparedStatement createPreparedStatementFindAll(Connection conn) throws SQLException {
        String sql = "SELECT * FROM task_list_tracker_db.tasks_tb;";
        return conn.prepareStatement(sql);
    }

    public static Optional<Task> findById(Integer id) {
        log.info("Finding task where id = '{}'", id);
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = createPreparedStatementFindById(conn, id);
             ResultSet rs = ps.executeQuery()) {
            if (!rs.next()) return Optional.empty();
            Task task = Task
                    .builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .description(rs.getString("description"))
                    .status(rs.getString("status"))
                    .createdAt(rs.getTimestamp("createdAt").toLocalDateTime())
                    .updatedAt(rs.getTimestamp("updatedAt").toLocalDateTime())
                    .build();

            return Optional.of(task);
        } catch (SQLException e) {
            log.error("Error while trying to find task where id = '{}'", id, e);
        }
        return Optional.empty();
    }

    private static PreparedStatement createPreparedStatementFindById(Connection conn, Integer id) throws SQLException {
        String sql = "SELECT * FROM task_list_tracker_db.tasks_tb WHERE id = ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }

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

    public static void update(Task task) {
        log.info("Updating '{}'", task.getName());
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = createPreparedStatementUpdate(conn, task)) {
            ps.execute();
        } catch (SQLException e) {
            log.error("Error while trying to update task '{}'", task.getName(), e);
        }
    }

    private static PreparedStatement createPreparedStatementUpdate(Connection conn, Task task) throws SQLException {
        String sql = "UPDATE `task_list_tracker_db`.`tasks_tb` SET `name` = ?, `description` = ?, `status` = ?, `updatedAt` = ? WHERE (`id` = ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, task.getName());
        ps.setString(2, task.getDescription());
        ps.setString(3, task.getStatus());
        ps.setTimestamp(4, Timestamp.valueOf(task.getUpdatedAt()));
        ps.setInt(5, task.getId());
        return ps;
    }
}
