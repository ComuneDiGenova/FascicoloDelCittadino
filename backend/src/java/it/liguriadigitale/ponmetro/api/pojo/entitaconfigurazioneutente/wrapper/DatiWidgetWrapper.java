package it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.wrapper;

import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatWidg;
import it.liguriadigitale.ponmetro.api.util.BackendDateUtils;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiWidget;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * Wrap PojoDatiWidget (DB - CfgTCatWidg) in DatiWidget (API)
 */
public class DatiWidgetWrapper {
  private static Log log = LogFactory.getLog(DatiWidgetWrapper.class);

  public static final List<DatiWidget> wrapList(final List<CfgTCatWidg> pojos) {
    return (pojos == null || pojos.isEmpty())
        ? new ArrayList<>()
        : pojos.stream()
            .filter(Objects::nonNull)
            .map(DatiWidgetWrapper::wrap)
            .collect(Collectors.toList());
  }

  public static final DatiWidget wrap(final CfgTCatWidg pojo) {
    DatiWidget toRet = new DatiWidget();
    toRet.setIdWidget(String.valueOf(pojo.getIdWidg()));
    toRet.setIdFunzione(String.valueOf(pojo.getIdFunz()));
    toRet.setDenominazioneWidg(pojo.getDenominazioneWidg());
    toRet.setDescrizioneWidg(pojo.getDescrizioneWidg());
    toRet.setUriWidg(pojo.getUriWidg());
    toRet.setDataCatalogazioneWidg(
        BackendDateUtils.fromLocalDateTimeToOffsetDateTime(pojo.getDataCatalogazioneWidg()));
    toRet.setOrdinamento(String.valueOf(pojo.getOrdinamento()));
    toRet.setFlagAbilitazione(pojo.getFlagAbilitazione());
    toRet.setFlagDefault(pojo.getFlagDefault());
    return toRet;
  }
}
