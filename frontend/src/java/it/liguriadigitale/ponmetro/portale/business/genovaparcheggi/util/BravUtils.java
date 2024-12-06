package it.liguriadigitale.ponmetro.portale.business.genovaparcheggi.util;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravFunzioni;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravFunzioni.TipoFunzEnum;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravPermessi;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BravUtils {

  private static Log log = LogFactory.getLog(BravUtils.class);

  public static BravFunzioni getFunzioneBrav(TipoFunzEnum tipologia) {
    BravFunzioni funzione = null;

    try {
      funzione =
          ServiceLocator.getInstance()
              .getServiziGenovaParcheggiHelper()
              .getBravFunzione(tipologia.value());
    } catch (BusinessException e) {
      log.error("Errore getFunzioneBrav: " + e.getMessage());
    }

    return funzione;
  }

  public static List<BravPermessi> getBravPermessi(TipoFunzEnum tipologia) {
    List<BravPermessi> lista = new ArrayList<BravPermessi>();

    try {
      lista =
          ServiceLocator.getInstance()
              .getServiziGenovaParcheggiHelper()
              .getBravPermessi(tipologia.value());
    } catch (BusinessException e) {
      log.error("Errore getBravPermessi: " + e.getMessage());
    }

    return lista;
  }
}
