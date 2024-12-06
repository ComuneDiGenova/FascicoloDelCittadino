package it.liguriadigitale.ponmetro.portale.presentation.pages.mieipagamenti.utilmieipagamenti;

import it.liguriadigitale.ponmetro.portale.pojo.mipDebito.TentativoDiPagamento;
import java.util.Optional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UtilMieiPagamenti {

  protected static Log log = LogFactory.getLog(UtilMieiPagamenti.class);

  public static boolean checkVisibilitaRata(TentativoDiPagamento tentativoDiPagamento) {
    boolean visibile = true;

    if (checkINotNull(tentativoDiPagamento)) {
      if (auxVisibilitaRata(tentativoDiPagamento)
          && auxVisibilitaRataDettaglio(tentativoDiPagamento)) {
        visibile = false;
      } else if (auxVisibilitaRataDettaglio(tentativoDiPagamento)) {
        visibile = false;
      }
    }

    return visibile;
  }

  protected static boolean auxVisibilitaRata(TentativoDiPagamento tentativoDiPagamento) {
    boolean auxVisibile = false;

    if (checkINotNull(tentativoDiPagamento)) {
      if (!checkINotNull(tentativoDiPagamento.getServizio())
          && !checkINotNull(tentativoDiPagamento.getNumeroDocumento())
          && !checkINotNull(tentativoDiPagamento.getRata())) {
        auxVisibile = true;
      }
    }

    return auxVisibile;
  }

  protected static boolean auxVisibilitaRataDettaglio(TentativoDiPagamento tentativoDiPagamento) {
    boolean auxVisibile = false;

    if (checkINotNull(tentativoDiPagamento)) {
      if (!checkINotNull(tentativoDiPagamento.getIuv())
          && !checkINotNull(tentativoDiPagamento.getCodiceAvviso())
          && !checkINotNull(tentativoDiPagamento.getImportoDaPagare())
          && !checkINotNull(tentativoDiPagamento.getImportoPagato())
          && !checkINotNull(tentativoDiPagamento.getDataCreazione())
          && !checkINotNull(tentativoDiPagamento.getDataPagamento())
          && !checkINotNull(tentativoDiPagamento.getAttualizzato())
          && !checkINotNull(tentativoDiPagamento.getNomeRicevuta())
          && !checkINotNull(tentativoDiPagamento.getPdfRicevuta())) {

        auxVisibile = true;
      }
    }

    return auxVisibile;
  }

  public static boolean checkINotNull(Object obj) {
    return Optional.ofNullable(obj).isPresent();
  }

  public static boolean checkIfNull(Object obj) {
    return !Optional.ofNullable(obj).isPresent();
  }
}
