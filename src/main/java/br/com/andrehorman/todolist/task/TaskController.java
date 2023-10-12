package br.com.andrehorman.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.andrehorman.todolist.utils.ErrorMessage;
import br.com.andrehorman.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {
  
  @Autowired
  private ITaskRepository taskRepository;

  @PostMapping("/create")
  public ResponseEntity<?> create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
    var idUser = request.getAttribute("idUser");
    var currentDate = LocalDateTime.now();
    var startDateTask = taskModel.getStartAt();
    var endDateTask = taskModel.getEndAt();

    if (currentDate.isAfter(startDateTask) || currentDate.isAfter(endDateTask)) {
      var message = new ErrorMessage("A data de início / data de término não pode ser menor que a data atual!");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    if (startDateTask.isAfter(endDateTask)) {
      var message = new ErrorMessage("A data de início não pode ser maior que a data final!");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    taskModel.setIdUser((UUID) idUser);
    var newTask = this.taskRepository.save(taskModel);

    return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
  }

  @GetMapping("/all")
  public ResponseEntity<?> getAll() {
    var tasks = this.taskRepository.findAll();
    return ResponseEntity.status(HttpStatus.OK).body(tasks);
  }

  @GetMapping("/all-user")
  public ResponseEntity<?> getByIdUser(HttpServletRequest request) {
    var idUser = request.getAttribute("idUser");
    var tasks = this.taskRepository.findByIdUser((UUID) idUser);

    return ResponseEntity.status(HttpStatus.OK).body(tasks);
  }

  @PutMapping("/{idTask}")
  public TaskModel update(@RequestBody TaskModel taskModel, @PathVariable UUID idTask, HttpServletRequest request) {

    var task = this.taskRepository.findById(idTask).orElse(null);

    Utils.copyNonNullProperties(taskModel, task);

    return this.taskRepository.save(task);
  }

}
