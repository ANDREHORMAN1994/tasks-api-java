package br.com.andrehorman.todolist.utils;

import lombok.Data;

@Data
public class ErrorMessage {
  
  private String message;

  public ErrorMessage(String newMessage) {
    this.message = newMessage;
  }

}
