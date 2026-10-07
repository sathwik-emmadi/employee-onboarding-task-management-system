package com.eots.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.eots.dto.request.CreateTaskRequest;
import com.eots.dto.request.UpdateTaskStatusRequest;
import com.eots.dto.response.TaskResponse;
import com.eots.entity.Employee;
import com.eots.entity.Task;
import com.eots.enums.TaskStatus;
import com.eots.exception.EmployeeNotFoundException;
import com.eots.exception.TaskNotFoundException;
import com.eots.exception.UnauthorizedTaskAccessException;
import com.eots.mapper.TaskMapper;
import com.eots.repository.EmployeeRepository;
import com.eots.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final TaskMapper taskMapper;

    public TaskService(
            TaskRepository taskRepository,
            EmployeeRepository employeeRepository,
            TaskMapper taskMapper) {

        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
        this.taskMapper = taskMapper;
    }

    public TaskResponse createTask(
            CreateTaskRequest request) {

        Employee employee =
                employeeRepository
                        .findById(request.getEmployeeId())
                        .orElseThrow(() ->
                                new EmployeeNotFoundException(
                                        "Employee not found"));

        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());

        task.setStatus(
                TaskStatus.NOT_STARTED
        );

        task.setEmployee(employee);

        Task savedTask =
                taskRepository.save(task);

        return taskMapper.toResponse(
                savedTask
        );
    }

    public List<TaskResponse> getAllTasks() {

        List<Task> tasks =
                taskRepository.findAll();

        return tasks.stream()
                .map(taskMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<TaskResponse> getTasksByEmployee(
            Long employeeId) {

        Employee employee =
                employeeRepository
                        .findById(employeeId)
                        .orElseThrow(() ->
                                new EmployeeNotFoundException(
                                        "Employee not found"));

        List<Task> tasks =
                taskRepository.findByEmployee(employee);

        return tasks.stream()
                .map(taskMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<TaskResponse> getMyTasks(
            String email) {

        Employee employee =
                employeeRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new EmployeeNotFoundException(
                                        "Employee not found"));

        List<Task> tasks =
                taskRepository.findByEmployee(employee);

        return tasks.stream()
                .map(taskMapper::toResponse)
                .collect(Collectors.toList());
    }

    public TaskResponse updateTaskStatus(
            Long taskId,
            String email,
            UpdateTaskStatusRequest request) {

        Employee employee =
                employeeRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new EmployeeNotFoundException(
                                        "Employee not found"));

        Task task =
                taskRepository
                        .findById(taskId)
                        .orElseThrow(() ->
                                new TaskNotFoundException(
                                        "Task not found"));

        if (!task.getEmployee()
                .getId()
                .equals(employee.getId())) {

            throw new UnauthorizedTaskAccessException(
                    "You are not allowed to update this task."
            );
        }

        task.setStatus(
                request.getStatus()
        );

        Task updatedTask =
                taskRepository.save(task);

        return taskMapper.toResponse(
                updatedTask
        );
    }
}