import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import { useAuth } from "../context/AuthContext";
import { getAllTasks } from "../services/taskService";

function HRDashboard() {

  const { user } = useAuth();

  const [tasks, setTasks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    loadTasks();
  }, []);

  const loadTasks = async () => {

    try {

      setError("");

      const result = await getAllTasks();

      setTasks(result.data || []);

    } catch (error) {

      setError(
        error.response?.data?.message ||
        "Unable to load HR dashboard."
      );

    } finally {

      setLoading(false);

    }
  };

  const notStartedCount = tasks.filter(
    (task) => task.status === "NOT_STARTED"
  ).length;

  const inProgressCount = tasks.filter(
    (task) => task.status === "IN_PROGRESS"
  ).length;

  const completedCount = tasks.filter(
    (task) => task.status === "COMPLETED"
  ).length;

  return (
    <>
      <Navbar />

      <main className="dashboard">

        <h1>
          Welcome, {user?.fullName}
        </h1>

        <p>
          HR Management Dashboard
        </p>

        {error && (
          <div className="error">
            {error}
          </div>
        )}

        {/* HR PROFILE */}

        <div className="dashboard-card">

          <h2>HR Profile</h2>

          <p>
            <strong>Name:</strong>{" "}
            {user?.fullName}
          </p>

          <p>
            <strong>Email:</strong>{" "}
            {user?.email}
          </p>

          <p>
            <strong>Role:</strong>{" "}
            {user?.role}
          </p>

        </div>

        {/* TASK STATISTICS */}

        <h2>Task Overview</h2>

        <div className="dashboard-grid">

          <div className="dashboard-card">

            <h3>Total Tasks</h3>

            <p className="stat-number">
              {loading ? "..." : tasks.length}
            </p>

          </div>

          <div className="dashboard-card">

            <h3>Not Started</h3>

            <p className="stat-number">
              {loading ? "..." : notStartedCount}
            </p>

          </div>

          <div className="dashboard-card">

            <h3>In Progress</h3>

            <p className="stat-number">
              {loading ? "..." : inProgressCount}
            </p>

          </div>

          <div className="dashboard-card">

            <h3>Completed</h3>

            <p className="stat-number">
              {loading ? "..." : completedCount}
            </p>

          </div>

        </div>

        {/* ALL TASKS */}

        <div className="dashboard-card">

          <h2>Assigned Tasks</h2>

          <button
            type="button"
            onClick={loadTasks}
            className="secondary-button"
          >
            Refresh
          </button>

          {loading ? (

            <p>
              Loading tasks...
            </p>

          ) : tasks.length === 0 ? (

            <p>
              No tasks have been assigned yet.
            </p>

          ) : (

            tasks.map((task) => (

              <div
                className="task-card"
                key={task.id}
              >

                <h3>
                  {task.title}
                </h3>

                <p>
                  {task.description}
                </p>

                <p>
                  <strong>
                    Employee:
                  </strong>{" "}
                  {task.employeeName}
                </p>

                <p>
                  <strong>
                    Priority:
                  </strong>{" "}
                  {task.priority}
                </p>

                <p>
                  <strong>
                    Status:
                  </strong>{" "}
                  {task.status}
                </p>

                <p>
                  <strong>
                    Due Date:
                  </strong>{" "}
                  {task.dueDate}
                </p>

              </div>

            ))

          )}

        </div>

      </main>
    </>
  );
}

export default HRDashboard;