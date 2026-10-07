function TaskCard({
  task,
  onStatusChange,
}) {

  return (
    <div className="task-card">

      <h3>
        {task.title}
      </h3>

      <p>
        {task.description}
      </p>

      <p>
        <strong>
          Priority:
        </strong>{" "}
        {task.priority}
      </p>

      <p>
        <strong>
          Due Date:
        </strong>{" "}
        {task.dueDate}
      </p>

      <p>
        <strong>
          Current Status:
        </strong>{" "}
        {task.status}
      </p>

      <label>
        Update Status
      </label>

      <select
        value={task.status}
        onChange={(e) =>
          onStatusChange(
            task.id,
            e.target.value
          )
        }
      >

        <option value="NOT_STARTED">
          Not Started
        </option>

        <option value="IN_PROGRESS">
          In Progress
        </option>

        <option value="COMPLETED">
          Completed
        </option>

      </select>

    </div>
  );
}

export default TaskCard;