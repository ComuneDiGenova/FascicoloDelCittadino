package it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb;

import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.portale.presentation.application.CONTEXT_PATH;
import it.liguriadigitale.ponmetro.portale.presentation.application.FUNZIONI_PATH;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BreadcrumbFdCUtil {

  private Log log = LogFactory.getLog(getClass());

  public static BreadcrumbFdCUtil getInstance() {
    return new BreadcrumbFdCUtil();
  }

  public void creaBreadcrumb(
      FunzioniDisponibili funzione, CONTEXT_PATH contextPath, boolean isDelegabile) {
    String mountedPath = contextPath + ricavaMountedPath(funzione, contextPath, isDelegabile);
    this.creaBreadcrumb(funzione, mountedPath, contextPath);
  }

  public void creaBreadcrumb(
      FunzioniDisponibili funzione, String mountedPath, CONTEXT_PATH contextPath) {
    BreadcrumbFdC nuovoBreadcrumb = new BreadcrumbFdC(mountedPath, funzione.getDescrizioneFunz());
    nuovoBreadcrumb.setNomeClassePaginaPadre(funzione.getClassePaginaStdPadre());
    nuovoBreadcrumb.setContextPath(contextPath.getPath());
    nuovoBreadcrumb.setFunzione(funzione);
    MapBreadcrumbsFdc.addBreadcrumb(funzione.getClassePaginaStd(), nuovoBreadcrumb);
  }

  public String ricavaMountedPath(
      FunzioniDisponibili funzione, CONTEXT_PATH contextPath, boolean isDelegabile) {

    String pathDelegabili = "";
    String pathNonDelegabili = "";
    String mountedPath = "";

    if (CONTEXT_PATH.NORMALE.equals(contextPath)) {
      pathDelegabili = FUNZIONI_PATH.DELEGABILI.getPath();
      pathNonDelegabili = FUNZIONI_PATH.NON_DELEGABILI.getPath();
    }
    if (isDelegabile) {
      mountedPath = pathDelegabili;
    } else {
      mountedPath = pathNonDelegabili;
    }

    if (isDelegabile && isFunzioneDelegabilePadre(funzione)) {
      mountedPath = urlPathSenzaNull(mountedPath) + urlPathSenzaNull(funzione.getPath());
      log.debug("funzione.getIdDelega()=" + funzione.getIdDelega());
      log.debug("funzione.getPathPadre()=" + funzione.getPath());
      log.debug("mountedPath=" + mountedPath);
    } else {
      String idPagina = usaNomePaginaSeIdPaginaNonPresente(funzione);
      mountedPath =
          urlPathSenzaNull(mountedPath)
              + urlPathSenzaNull(funzione.getPathPadre())
              + urlPathSenzaNull(idPagina);
      log.debug("funzione.getIdDelega()=" + funzione.getIdDelega());
      log.debug("mountedPath=" + mountedPath);
    }
    return mountedPath;
  }

  private String usaNomePaginaSeIdPaginaNonPresente(FunzioniDisponibili funzione) {
    String idPagina = funzione.getIdPagina();
    if (StringUtils.isNotBlank(idPagina)) {
      return idPagina;
    } else {
      return funzione.getClassePaginaStd();
    }
  }

  private boolean isFunzioneDelegabilePadre(FunzioniDisponibili funzione) {
    return funzione.getIdDelega() > 0L;
  }

  private boolean isFunzioneDelegabileFiglio(FunzioniDisponibili funzione) {
    log.debug("funzione.getIdFunzPadre()=" + funzione.getIdFunzPadre());
    return StringUtils.isNotBlank(funzione.getIdFunzPadre());
  }

  public List<String> creaListaPageClassDelegabili(List<FunzioniDisponibili> listaFunzioni) {

    List<String> listaFunzioniPadriDelegabili = new ArrayList<>();
    for (FunzioniDisponibili funzione : listaFunzioni) {
      if (isFunzioneDelegabilePadre(funzione)) {
        log.debug("classePadre=" + funzione.getClassePaginaStd());
        listaFunzioniPadriDelegabili.add(funzione.getClassePaginaStd());
      }
    }
    return listaFunzioniPadriDelegabili;
  }

  public List<String> creaListaPageClassFiglioDelegabili(List<FunzioniDisponibili> listaFunzioni) {

    List<String> listaListaPageClassDelegabili = new ArrayList<>();

    for (FunzioniDisponibili funzione : listaFunzioni) {
      if (isFunzioneDelegabileFiglio(funzione)) {
        log.debug("classeFiglio=" + funzione.getClassePaginaStd());
        listaListaPageClassDelegabili.add(funzione.getClassePaginaStd());
      }
    }
    log.debug("listaListaPageClassDelegabili.size=" + listaListaPageClassDelegabili.size());
    return listaListaPageClassDelegabili;
  }

  private String urlPathSenzaNull(String path) {
    String separatore = "/";
    path = escludePathWeb(path);
    if (StringUtils.isNotBlank(path)) {
      return separatore + path;
    } else {
      return "";
    }
  }

  private String escludePathWeb(String path) {
    if ("WEB".equalsIgnoreCase(path)) {
      return "";
    } else {
      return path;
    }
  }
}
