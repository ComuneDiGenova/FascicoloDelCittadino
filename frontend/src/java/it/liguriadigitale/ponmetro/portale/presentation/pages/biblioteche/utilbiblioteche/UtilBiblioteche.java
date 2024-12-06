package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.utilbiblioteche;

import java.util.List;
import java.util.Optional;

public class UtilBiblioteche {

  public static boolean checkIfNull(Object obj) {
    return Optional.ofNullable(obj).isPresent();
  }

  public static <T> boolean checkListIsEmpty(List<T> lista) {
    return lista.isEmpty();
  }
}
