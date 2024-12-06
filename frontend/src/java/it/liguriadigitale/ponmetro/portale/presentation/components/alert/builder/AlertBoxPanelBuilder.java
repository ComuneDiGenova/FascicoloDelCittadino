package it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Page;

public class AlertBoxPanelBuilder<P extends Page> {

  private static final Log log = LogFactory.getLog(AlertBoxPanelBuilder.class);

  private Class<P> clazz;
  private String wicketId;

  @SuppressWarnings("rawtypes")
  public static AlertBoxPanelBuilder getInstance() {
    return new AlertBoxPanelBuilder();
  }

  public AlertBoxPanelBuilder<P> addClazz(Class<P> clazz) {
    this.clazz = clazz;
    return this;
  }

  public AlertBoxPanelBuilder<P> addWicketId(String wicketId) {
    this.wicketId = wicketId;
    return this;
  }

  public AlertBoxPanel<P> build() {

    List<MessaggiInformativi> lista = getListaMessaggiDalDatabase(clazz.getSimpleName());
    AlertBoxPanel<P> boxMessaggi;

    if (StringUtils.isNotBlank(wicketId)) {
      boxMessaggi = new AlertBoxPanel<>(wicketId, lista);
    } else {
      boxMessaggi = new AlertBoxPanel<>(lista);
    }
    return boxMessaggi;
  }

  private List<MessaggiInformativi> getListaMessaggiDalDatabase(String name) {
    log.debug("ErroreGenericoPage: name:" + name);
    try {
      return ServiceLocator.getInstance().getServiziMessaggi().getMessaggiInformativi(name);
    } catch (BusinessException e) {
      log.error("Impossibile reperire il messaggio corretto");
      return new ArrayList<>();
    }
  }
}
