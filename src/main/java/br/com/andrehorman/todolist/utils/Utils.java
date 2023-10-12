package br.com.andrehorman.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {

  public static void copyNonNullProperties(Object source, Object target) {
    BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
  }
  
  public static String[] getNullPropertyNames(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source); // Forma de acessar os atributos de um objeto
    
    PropertyDescriptor[] pds = src.getPropertyDescriptors(); // Pega todos os atributos do objeto
    
    Set<String> emptyNames = new HashSet<>(); // Cria um conjunto de strings vazias

    for (PropertyDescriptor pd: pds) {
      String attr = pd.getName(); // Pega o nome do atributo
      Object srcValue = src.getPropertyValue(attr); // Pega o valor do atributo
      if (srcValue == null) {
        emptyNames.add(attr); // Adiciona o nome do atributo no conjunto de strings vazias
      }
    }

    String[] result = new String[emptyNames.size()]; // Cria um array de strings com o tamanho do conjunto de strings vazias
    return emptyNames.toArray(result); // Converte o conjunto de strings vazias para um array de strings
  }

}
