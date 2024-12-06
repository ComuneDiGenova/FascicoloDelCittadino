package it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.wrapper;

import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatFunzSosp;
import it.liguriadigitale.ponmetro.api.util.BackendDateUtils;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSospensioneFunzione;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * Wrap PojoDatiSospensioneFunzione (DB - CfgTCatFunzSosp) in
 * DatiSospensioneFunzione (API)
 */
public class DatiSospensioneFunzioneWrapper {
  private static Log log = LogFactory.getLog(DatiSospensioneFunzioneWrapper.class);

  public static final List<DatiSospensioneFunzione> wrapList(final List<CfgTCatFunzSosp> pojos) {
    return (pojos == null || pojos.isEmpty())
        ? new ArrayList<>()
        : pojos.stream()
            .filter(Objects::nonNull)
            .map(DatiSospensioneFunzioneWrapper::wrap)
            .collect(Collectors.toList());
  }

  public static final DatiSospensioneFunzione wrap(final CfgTCatFunzSosp pojo) {
    DatiSospensioneFunzione toRet = new DatiSospensioneFunzione();
    toRet.setDataInizioSospensione(
        BackendDateUtils.fromLocalDateTimeToOffsetDateTime(pojo.getDataInizioSosp()));
    toRet.setDataFineSospensione(
        BackendDateUtils.fromLocalDateTimeToOffsetDateTime(pojo.getDataFineSosp()));
    toRet.setIdFunzione(String.valueOf(pojo.getIdFunz()));
    toRet.setIdSospensioneFunzione(String.valueOf(pojo.getIdFunzSosp()));
    toRet.setFlagAbilitazione(pojo.getFlagAbilitazione());
    toRet.setTipoSospensione(String.valueOf(pojo.getTipoSosp()));
    return toRet;
  }
}
