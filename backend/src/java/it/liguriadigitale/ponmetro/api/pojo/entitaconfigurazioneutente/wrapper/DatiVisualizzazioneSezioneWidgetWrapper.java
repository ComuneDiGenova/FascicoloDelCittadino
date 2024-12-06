package it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.wrapper;

import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatComp;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatFunz;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatFunzSosp;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatSez;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgRFcittWidg;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.builder.VCfgRFcittWidgBuilder;
import it.liguriadigitale.ponmetro.api.util.BackendDateUtils;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiComparto;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiFunzione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSospensioneFunzione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiWidget;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * Wrap PojoDatiFunzione (DB - VCfgRFcittWidg) in
 * DatiVisualizzazioneSezioneWidget (API)
 */
public class DatiVisualizzazioneSezioneWidgetWrapper {
  private static Log log = LogFactory.getLog(DatiVisualizzazioneSezioneWidgetWrapper.class);

  public static final List<DatiVisualizzazioneSezioneWidget> wrapList(
      final List<VCfgRFcittWidg> pojos) {
    return (pojos == null || pojos.isEmpty())
        ? new ArrayList<>()
        : pojos.stream()
            .filter(Objects::nonNull)
            .map(DatiVisualizzazioneSezioneWidgetWrapper::wrap)
            .collect(Collectors.toList());
  }

  public static final DatiVisualizzazioneSezioneWidget wrap(final VCfgRFcittWidg pojo) {
    VCfgRFcittWidgBuilder builder =
        new VCfgRFcittWidgBuilder(pojo)
            .buildSezione()
            .buildComparto()
            .buildFunzione()
            .buildSospensioneFunzione()
            .buildWidget();

    DatiVisualizzazioneSezioneWidget toRet = new DatiVisualizzazioneSezioneWidget();
    DatiSezione sezione =
        DatiSezioneWrapper.wrap(
            builder.getSezione() != null ? builder.getSezione() : new CfgTCatSez());
    DatiComparto comparto =
        DatiCompartoWrapper.wrap(
            builder.getComparto() != null ? builder.getComparto() : new CfgTCatComp());
    DatiFunzione funzione =
        DatiFunzioneWrapper.wrap(
            builder.getFunzione() != null ? builder.getFunzione() : new CfgTCatFunz());
    DatiSospensioneFunzione sospensioneFunzione =
        DatiSospensioneFunzioneWrapper.wrap(
            builder.getFunzioneSospensione() != null
                ? builder.getFunzioneSospensione()
                : new CfgTCatFunzSosp());
    DatiWidget widget = DatiWidgetWrapper.wrap(builder.getWidget());

    toRet.setIdVisualizzazioneSezioneWidget(String.valueOf(pojo.getIdCfgRFcittWidg()));
    toRet.setSezione(sezione);
    toRet.setComparto(comparto);
    toRet.setFunzione(funzione);
    toRet.setSospensionefunzione(sospensioneFunzione);
    toRet.setWidget(widget);
    toRet.setDataAssociazione(
        BackendDateUtils.fromLocalDateTimeToOffsetDateTime(pojo.getDataAssociazioneFcittWidg()));
    toRet.setFlagAbilitazione(pojo.getFlagAbilitazioneFcittWidg());
    return toRet;
  }
}
