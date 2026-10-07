import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import TaskCard from "../components/TaskCard";
import {
  getMyTasks,
  updateTaskStatus,
} from "../services/taskService";

function EmployeeTasks() {

  const [tasks, setTasks] =
    useState([]);

  const [error, setError] =
    useState("");

  const [loading, setLoading] =
    useState(true);

  const loadTasks = async () => {

    try {

      setError("");

      const result =
        await getMyTasks();

      setTasks(result.data || []);

    } catch (error) {

      setError(
        error.response?.data?.message ||
          "Unable to load tasks."
      );

    } finally {

      setLoading(false);

    }
  };

  useEffect(() => {
    loadTasks();
  }, []);

  const handleStatusChange = async (
    taskId,
    status
  ) => {

    try {

      await updateTaskStatus(
        taskId,
        status
      );

      await loadTasks();

    } catch (error) {

      setError(
        error.response?.data?.message ||
          "Unable to update task status."
      );

    }
  };

  return (
    <>
      <Navbar />

      <main className="dashboard">

        <h1>My Tasks</h1>

        <p>
          View and manage your assigned
          onboarding tasks.
        </p>

        {error && (
          <div className="error">
            {error}
          </div>
        )}

        {loading ? (
          <p>
            Loading tasks...
          </p>
        ) : tasks.length === 0 ? (
          <p>
            No tasks assigned to you.
          </p>
        ) : (
          tasks.map((task) => (
            <TaskCard
              key={task.id}
              task={task}
              onStatusChange={
                handleStatusChange
              }
            />
          ))
        )}

      </main>
    </>
  );
}

export default EmployeeTasks;