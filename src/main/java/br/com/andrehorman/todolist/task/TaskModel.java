package br.com.andrehorman.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {
  
  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;

  private UUID idUser;

  @Column(name = "título", length = 50, nullable = false)
  private String title;

  @Column(name = "descrição", nullable = false)
  private String description;

  @Column(name = "prioridade", nullable = false)
  private String priority;

  private LocalDateTime startAt;

  private LocalDateTime endAt;

  @CreationTimestamp
  private LocalDateTime createdAt;

}
