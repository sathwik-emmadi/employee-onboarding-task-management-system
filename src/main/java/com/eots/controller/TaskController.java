package com.eots.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.eots.dto.request.CreateTaskRequest;
import com.eots.dto.response.ApiResponse;
import com.eots.dto.response.TaskResponse;
import com.eots.service.TaskService;
import org.springframework.security.core.Authentication;
import com.eots.dto.request.UpdateTaskStatusRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PreAuthorize("hasRole('HR')")
    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponse>> createTask(
            @Valid @RequestBody CreateTaskRequest request) {

        TaskResponse taskResponse = taskService.createTask(request);

        ApiResponse<TaskResponse> response = new ApiResponse<>();

        response.setSuccess(true);
        response.setMessage("Task assigned successfully.");
        response.setData(taskResponse);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PreAuthorize("hasRole('HR')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getAllTasks() {

        List<TaskResponse> tasks = taskService.getAllTasks();

        ApiResponse<List<TaskResponse>> response = new ApiResponse<>();

        response.setSuccess(true);
        response.setMessage("Tasks fetched successfully.");
        response.setData(tasks);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('HR')")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getTasksByEmployee(
            @PathVariable Long employeeId) {

        List<TaskResponse> tasks = taskService.getTasksByEmployee(employeeId);

        ApiResponse<List<TaskResponse>> response = new ApiResponse<>();

        response.setSuccess(true);
        response.setMessage("Employee tasks fetched successfully.");
        response.setData(tasks);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/my-tasks")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getMyTasks(
            Authentication authentication) {

        String email = authentication.getName();

        List<TaskResponse> tasks = taskService.getMyTasks(email);

        ApiResponse<List<TaskResponse>> response = new ApiResponse<>();

        response.setSuccess(true);
        response.setMessage("Tasks fetched successfully.");
        response.setData(tasks);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PutMapping("/{taskId}/status")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTaskStatus(
            @PathVariable Long taskId,
            @Valid @RequestBody UpdateTaskStatusRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        TaskResponse taskResponse = taskService.updateTaskStatus(
                taskId,
                email,
                request);

        ApiResponse<TaskResponse> response = new ApiResponse<>();

        response.setSuccess(true);
        response.setMessage("Task status updated successfully.");
        response.setData(taskResponse);

        return ResponseEntity.ok(response);
    }
}