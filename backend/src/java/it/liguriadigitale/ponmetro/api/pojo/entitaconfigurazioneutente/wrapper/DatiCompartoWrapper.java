package it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.wrapper;

import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatComp;
import it.liguriadigitale.ponmetro.api.util.BackendDateUtils;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiComparto;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * Wrap PojoDatiComparto (DB - CfgTCatComp) in DatiComparto (API)
 */
public class DatiCompartoWrapper {
  private static Log log = LogFactory.getLog(DatiCompartoWrapper.class);

  public static final List<DatiComparto> wrapList(final List<CfgTCatComp> pojos) {
    return (pojos == null || pojos.isEmpty())
        ? new ArrayList<>()
        : pojos.stream()
            .filter(Objects::nonNull)
            .map(DatiCompartoWrapper::wrap)
            .collect(Collectors.toList());
  }

  public static final DatiComparto wrap(final CfgTCatComp pojo) {
    DatiComparto toRet = new DatiComparto();
    toRet.setDataCatalogazioneComp(
        BackendDateUtils.fromLocalDateTimeToOffsetDateTime(pojo.getDataCatalogazioneComp()));
    toRet.setDenominazioneComp(pojo.getDenominazioneComp());
    toRet.setDescrizioneComp(pojo.getDescrizioneComp());
    toRet.setFlagAbilitazione(pojo.getFlagAbilitazione());
    toRet.setIdComparto(String.valueOf(pojo.getIdComp()));
    toRet.setIdSezione(String.valueOf(pojo.getIdSez()));
    toRet.setOrdinamento(String.valueOf(pojo.getOrdinamento()));
    toRet.setUriComp(pojo.getUriComp());
    return toRet;
  }
}
