package it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.wrapper;

import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgRFcittComp;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.builder.VCfgRFcittCompBuilder;
import it.liguriadigitale.ponmetro.api.util.BackendDateUtils;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiComparto;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiNotificaComparto;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * Wrap PojoDatiComparto (DB - VCfgRFcittComp) in DatiNotificaComparto (API)
 */
public class DatiNotificaCompartoWrapper extends DatiNotificaComparto {
  private static final long serialVersionUID = 8113002611587970977L;
  private static Log log = LogFactory.getLog(DatiVisualizzazioneSezioneWidgetWrapper.class);

  public static final List<DatiNotificaComparto> wrapList(final List<VCfgRFcittComp> pojos) {
    return (pojos == null || pojos.isEmpty())
        ? new ArrayList<>()
        : pojos.stream()
            .filter(Objects::nonNull)
            .map(DatiNotificaCompartoWrapper::wrap)
            .collect(Collectors.toList());
  }

  public static final DatiNotificaComparto wrap(final VCfgRFcittComp pojo) {
    VCfgRFcittCompBuilder builder = new VCfgRFcittCompBuilder(pojo).buildComparto().buildSezione();

    DatiNotificaComparto toRet = new DatiNotificaComparto();
    DatiSezione sezione = DatiSezioneWrapper.wrap(builder.getSezione());
    DatiComparto comparto = DatiCompartoWrapper.wrap(builder.getComparto());

    toRet.setIdNotificaComparto(String.valueOf(pojo.getIdCfgRFcittComp()));
    toRet.setSezione(sezione);
    toRet.setComparto(comparto);
    toRet.setDataRegistrazione(
        BackendDateUtils.fromLocalDateTimeToOffsetDateTime(pojo.getDataRegistrazFcittComp()));
    toRet.setDataDeregistrazione(
        BackendDateUtils.fromLocalDateTimeToOffsetDateTime(pojo.getDataDeregistrazFcittComp()));
    toRet.setFlagAbilitazione(pojo.getFlagAbilitazioneCittComp());

    return toRet;
  }
}
