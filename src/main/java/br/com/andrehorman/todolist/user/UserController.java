package br.com.andrehorman.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.andrehorman.todolist.utils.ErrorMessage;

/*
 *  Modificadores de acesso:
 *  public: acessível por qualquer classe
 *  private: acessível apenas pela classe que o declarou
 *  protected: acessível pela classe que o declarou e pelas classes que a estendem
 */

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired // Gerencia o ciclo de vida da interface
  private IUserRepository userRepository;

  @PostMapping("/create")
  public ResponseEntity<?> create(@RequestBody UserModel userModel) {
    var userName = userModel.getUsername();
    var password = userModel.getPassword();
    var userExists = this.userRepository.findByUsername(userName);

    if (userExists != null) {
      var message = new ErrorMessage("Usuário já existe");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    var passwordHash = BCrypt.withDefaults().hashToString(12, password.toCharArray());
    userModel.setPassword(passwordHash);

    var userCreated = this.userRepository.save(userModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
  }

}
