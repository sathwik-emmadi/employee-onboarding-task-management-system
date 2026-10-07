package com.eots.mapper;

import org.springframework.stereotype.Component;

import com.eots.dto.response.TaskResponse;
import com.eots.entity.Task;

@Component
public class TaskMapper {

    public TaskResponse toResponse(Task task) {

        TaskResponse response = new TaskResponse();

        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setPriority(task.getPriority());
        response.setStatus(task.getStatus());
        response.setDueDate(task.getDueDate());

        response.setEmployeeId(task.getEmployee().getId());
        response.setEmployeeName(task.getEmployee().getFullName());

        return response;
    }

}