package it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.wrapper;

import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgTCatFunz;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.builder.VCfgTCatFunzBuilder;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiComparto;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiCompletiFunzione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiFunzione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * Wrap PojoDatiCompletiFunzione (DB - VCfgTCatFunz) in DatiCompletiFunzione
 * (API)
 */
public class DatiCompletiFunzioneWrapper {
  private static Log log = LogFactory.getLog(DatiCompletiFunzioneWrapper.class);

  public static final List<DatiCompletiFunzione> wrapList(final List<VCfgTCatFunz> pojos) {
    return (pojos == null || pojos.isEmpty())
        ? new ArrayList<>()
        : pojos.stream()
            .filter(Objects::nonNull)
            .map(DatiCompletiFunzioneWrapper::wrap)
            .collect(Collectors.toList());
  }

  public static final DatiCompletiFunzione wrap(final VCfgTCatFunz pojo) {
    VCfgTCatFunzBuilder builder =
        new VCfgTCatFunzBuilder(pojo).buildSezione().buildComparto().buildFunzione();

    DatiCompletiFunzione toRet = new DatiCompletiFunzione();
    DatiSezione sezione = DatiSezioneWrapper.wrap(builder.getSezione());
    DatiComparto comparto = DatiCompartoWrapper.wrap(builder.getComparto());
    DatiFunzione funzione = DatiFunzioneWrapper.wrap(builder.getFunzione());

    toRet.setDatiSezione(sezione);
    toRet.setDatiComparto(comparto);
    toRet.setDatiFunzione(funzione);

    return toRet;
  }
}
