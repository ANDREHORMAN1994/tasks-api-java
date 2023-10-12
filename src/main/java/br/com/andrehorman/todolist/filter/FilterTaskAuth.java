package br.com.andrehorman.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.andrehorman.todolist.user.IUserRepository;
import br.com.andrehorman.todolist.user.UserModel;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

  @Autowired
  private IUserRepository userRepository;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    // PEGAR A AUTENTICAÇÃO DO HEADER
    var auth = request.getHeader("Authorization");
    var token = auth.substring("Basic".length()).trim();

    System.out.println(
      String.format("Token: %s", token)
    );

    // DESCRIPTOGRAFANDO INFORMAÇÕES DO TOKEN
    byte[] decodedToken = Base64.getDecoder().decode(token);
    var infos = new String(decodedToken);
    String username = infos.split(":")[0];
    String password = infos.split(":")[1];

    System.out.println(
      "Username: " + username + "\n" +
      "Password: " + password
    );

    // VALIDAR USUÁRIO E SENHA
    UserModel user = this.userRepository.findByUsername(username);
    
    if (user == null) {
      response.sendError(401, "Usuário não existe!");
    } else {
      var result = BCrypt
        .verifyer()
        .verify(password.toCharArray(), user.getPassword());
      
      if (!result.verified) {
        response.sendError(401, "Senha inválida!");
      }
    }


    filterChain.doFilter(request, response);
  }

}
