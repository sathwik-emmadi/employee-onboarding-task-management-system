import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import {
    createTask,
    getAllTasks,
} from "../services/taskService";
import { getAllEmployees } from "../services/employeeService";

function HRTaskManagement() {

    const [form, setForm] = useState({
        title: "",
        description: "",
        priority: "",
        dueDate: "",
        employeeId: "",
    });

    const [tasks, setTasks] = useState([]);

    const [message, setMessage] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);
    const [employees, setEmployees] = useState([]);

    useEffect(() => {
        loadTasks();
        loadEmployees();
    }, []);

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value,
        });
    };

    const loadTasks = async () => {

        try {

            const result = await getAllTasks();

            setTasks(result.data || []);

        } catch (error) {

            setError(
                error.response?.data?.message ||
                "Unable to load tasks."
            );

        }
    };

    const handleCreateTask = async (e) => {

        e.preventDefault();

        setMessage("");
        setError("");
        setLoading(true);

        try {

            const result = await createTask({
                title: form.title,
                description: form.description,
                priority: form.priority,
                dueDate: form.dueDate,
                employeeId: Number(form.employeeId),
            });

            setMessage(
                result.message ||
                "Task assigned successfully."
            );

            setForm({
                title: "",
                description: "",
                priority: "",
                dueDate: "",
                employeeId: "",
            });

            await loadTasks();

        } catch (error) {

            setError(
                error.response?.data?.message ||
                "Unable to create task."
            );

        } finally {

            setLoading(false);

        }
    };
    const loadEmployees = async () => {

        try {

            const result = await getAllEmployees();

            setEmployees(result.data || []);

        } catch (error) {

            setError(
                error.response?.data?.message ||
                "Unable to load employees."
            );
        }
    };

    return (
        <>
            <Navbar />

            <main className="dashboard">

                <h1>Task Management</h1>

                <p>
                    Create and assign onboarding tasks
                    to employees.
                </p>

                {message && (
                    <div className="success">
                        {message}
                    </div>
                )}

                {error && (
                    <div className="error">
                        {error}
                    </div>
                )}

                <div className="dashboard-grid">

                    {/* CREATE TASK */}

                    <div className="dashboard-card">

                        <h2>Assign New Task</h2>

                        <form
                            onSubmit={handleCreateTask}
                            className="task-form"
                        >

                            <label>
                                Task Title
                            </label>

                            <input
                                type="text"
                                name="title"
                                value={form.title}
                                onChange={handleChange}
                                required
                            />

                            <label>
                                Description
                            </label>

                            <textarea
                                name="description"
                                value={form.description}
                                onChange={handleChange}
                                rows="4"
                            />

                            <label>
                                Priority
                            </label>

                            <select
                                name="priority"
                                value={form.priority}
                                onChange={handleChange}
                                required
                            >
                                <option value="">
                                    Select Priority
                                </option>

                                <option value="LOW">
                                    Low
                                </option>

                                <option value="MEDIUM">
                                    Medium
                                </option>

                                <option value="HIGH">
                                    High
                                </option>

                            </select>

                            <label>
                                Due Date
                            </label>

                            <input
                                type="date"
                                name="dueDate"
                                value={form.dueDate}
                                onChange={handleChange}
                                required
                            />

                            <label>
                                Assign To Employee
                            </label>

                            <select
                                name="employeeId"
                                value={form.employeeId}
                                onChange={handleChange}
                                required
                            >
                                <option value="">
                                    Select Employee
                                </option>

                                {employees.map((employee) => (
                                    <option
                                        key={employee.id}
                                        value={employee.id}
                                    >
                                        {employee.fullName} - {employee.email}
                                    </option>
                                ))}
                            </select>

                            <button
                                type="submit"
                                disabled={loading}
                            >
                                {loading
                                    ? "Assigning..."
                                    : "Assign Task"}
                            </button>

                        </form>

                    </div>

                    {/* ALL TASKS */}

                    <div className="dashboard-card">

                        <h2>All Tasks</h2>

                        <button
                            type="button"
                            onClick={loadTasks}
                            className="secondary-button"
                        >
                            Refresh Tasks
                        </button>

                        {tasks.length === 0 ? (

                            <p>
                                No tasks found.
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

                </div>

            </main>
        </>
    );
}

export default HRTaskManagement;