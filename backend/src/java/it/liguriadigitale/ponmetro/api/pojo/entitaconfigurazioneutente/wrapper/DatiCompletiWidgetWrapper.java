package it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.wrapper;

import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgTCatWidg;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.builder.VCfgTCatWidgBuilder;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiComparto;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiCompletiWidget;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiFunzione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiWidget;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * Wrap PojoDatiCompletiWidget (DB - VCfgTCatWidg) in DatiCompletiWidgetWrapper
 * (API)
 */
public class DatiCompletiWidgetWrapper {
  private static Log log = LogFactory.getLog(DatiCompletiWidgetWrapper.class);

  public static final List<DatiCompletiWidget> wrapList(final List<VCfgTCatWidg> pojos) {
    return (pojos == null || pojos.isEmpty())
        ? new ArrayList<>()
        : pojos.stream()
            .filter(Objects::nonNull)
            .map(DatiCompletiWidgetWrapper::wrap)
            .collect(Collectors.toList());
  }

  public static final DatiCompletiWidget wrap(final VCfgTCatWidg pojo) {
    VCfgTCatWidgBuilder builder =
        new VCfgTCatWidgBuilder(pojo).buildSezione().buildComparto().buildFunzione().buildWidget();

    DatiCompletiWidget toRet = new DatiCompletiWidget();
    DatiSezione sezione = DatiSezioneWrapper.wrap(builder.getSezione());
    DatiComparto comparto = DatiCompartoWrapper.wrap(builder.getComparto());
    DatiFunzione funzione = DatiFunzioneWrapper.wrap(builder.getFunzione());
    DatiWidget widget = DatiWidgetWrapper.wrap(builder.getWidget());

    toRet.setDatiSezione(sezione);
    toRet.setDatiComparto(comparto);
    toRet.setDatiFunzione(funzione);
    toRet.setDatiWidget(widget);

    return toRet;
  }
}
