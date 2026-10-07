import api from "./api";

export const registerEmployee = async (employeeData) => {
  const response = await api.post(
    "/employees/register",
    employeeData
  );

  return response.data;
};

export const loginEmployee = async (loginData) => {
  const response = await api.post(
    "/employees/login",
    loginData
  );

  return response.data;
};

export const getProfile = async () => {
  const response = await api.get(
    "/employees/profile"
  );

  return response.data;
};
export const getAllEmployees = async () => {
  const response = await api.get("/employees");

  return response.data;
};