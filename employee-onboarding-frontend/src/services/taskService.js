import api from "./api";

export const createTask = async (taskData) => {
  const response = await api.post(
    "/tasks",
    taskData
  );

  return response.data;
};

export const getAllTasks = async () => {
  const response = await api.get("/tasks");

  return response.data;
};

export const getTasksByEmployee = async (employeeId) => {
  const response = await api.get(
    `/tasks/employee/${employeeId}`
  );

  return response.data;
};

export const getMyTasks = async () => {
  const response = await api.get(
    "/tasks/my-tasks"
  );

  return response.data;
};

export const updateTaskStatus = async (
  taskId,
  status
) => {
  const response = await api.put(
    `/tasks/${taskId}/status`,
    { status }
  );

  return response.data;
};