package com.zosh.service;

import com.zosh.model.Task;
import com.zosh.model.TaskStatus;
import com.zosh.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImplementation implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createTask(Task task, String requestRole) throws Exception {
        if(!requestRole.equals("ROLE_ADMIN")){
            throw new Exception("only admin can create task");
        }

        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) throws Exception {
        return taskRepository.findById(id).orElseThrow(()->new Exception("task not found with id "+id));
    }

    @Override
    public List<Task> getAllTask(TaskStatus status) throws Exception {

        List<Task> allTask = taskRepository.findAll();

        List<Task> filteredTasks = allTask.stream().filter(
                task -> status==null || task.getStatus().name().equalsIgnoreCase(status.toString())
        ).toList();

        return filteredTasks;
    }

    @Override
    public Task updateTask(Long id, Task updatedTask, Long userId) throws Exception {

        Task existingTask = getTaskById(id);

        // Title update
        if(updatedTask.getTitle() != null){
            existingTask.setTitle(updatedTask.getTitle());
        }
        // Image update
        if(updatedTask.getImage() != null){
            existingTask.setImage(updatedTask.getImage());
        }
        // Description update
        if(updatedTask.getDescription() != null){
            existingTask.setDescription(updatedTask.getDescription());
        }
        // Status update
        if(updatedTask.getStatus() != null){
            existingTask.setStatus(updatedTask.getStatus());
        }
        // Deadline update
        if(updatedTask.getDeadline() != null){
            existingTask.setDeadline(updatedTask.getDeadline());
        }

        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id) throws Exception {

        getTaskById(id);
        taskRepository.deleteById(id);
    }

    @Override
    public Task assignedToUser(Long userId, Long taskId) throws Exception {
        Task task = getTaskById(taskId);
        task.setAssignedUserId(userId);
        task.setStatus(TaskStatus.DONE);

        return taskRepository.save(task);
    }

    @Override
    public List<Task> assignedUsersTask(Long userId, TaskStatus status) {
        List<Task> allTask = taskRepository.findByAssignedUserId(userId);

        List<Task> filteredTasks = allTask.stream().filter(
                task -> status==null || task.getStatus().name().equalsIgnoreCase(status.toString())
        ).toList();

        return filteredTasks;
    }

    @Override
    public Task completeTask(Long taskId) throws Exception {

        Task task = getTaskById(taskId);
        task.setStatus(TaskStatus.DONE);

        return taskRepository.save(task);
    }
}
