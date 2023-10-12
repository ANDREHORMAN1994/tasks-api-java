package br.com.andrehorman.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

// @Setter  Adiciona apenas os métodos set
// @Getter  Adiciona apenas os métodos get
@Data // Adiciona os métodos set e get
@Entity(name = "tb_users")
public class UserModel {
  
  @Id // Define o campo como chave primária
  @GeneratedValue(generator = "UUID")
  private UUID id;

  @Column(name = "usuário", unique = true)
  private String username;

  @Column(name = "nome")
  private String name;

  @Column(name = "senha")
  private String password;

  @CreationTimestamp
  private LocalDateTime createdAt;

}
