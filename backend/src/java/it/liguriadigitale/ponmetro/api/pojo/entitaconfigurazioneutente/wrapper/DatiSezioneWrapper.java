package it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.wrapper;

import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatSez;
import it.liguriadigitale.ponmetro.api.util.BackendDateUtils;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * Wrap PojoDatiSezione (DB - CfgTCatSez) in DatiSezione (API)
 */
public class DatiSezioneWrapper {
  private static Log log = LogFactory.getLog(DatiSezioneWrapper.class);

  public static final List<DatiSezione> wrapList(final List<CfgTCatSez> pojos) {
    return (pojos == null || pojos.isEmpty())
        ? new ArrayList<>()
        : pojos.stream()
            .filter(Objects::nonNull)
            .map(DatiSezioneWrapper::wrap)
            .collect(Collectors.toList());
  }

  public static final DatiSezione wrap(final CfgTCatSez pojo) {
    DatiSezione toRet = new DatiSezione();
    if (pojo != null) {
      toRet.setDataCatalogazioneSez(
          BackendDateUtils.fromLocalDateTimeToOffsetDateTime(pojo.getDataCatalogazioneSez()));
      toRet.setDenominazioneSez(pojo.getDenominazioneSez());
      toRet.setDescrizioneSez(pojo.getDescrizioneSez());
      toRet.setFlagAbilitazione(pojo.getFlagAbilitazione());
      toRet.setIdSezione(String.valueOf(pojo.getIdSez()));
      toRet.setOrdinamento(String.valueOf(pojo.getOrdinamento()));
      toRet.setUriSez(pojo.getUriSez());
    }

    return toRet;
  }
}
