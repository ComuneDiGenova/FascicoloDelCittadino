package it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.wrapper;

import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgTCatComp;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.builder.VCfgTCatCompBuilder;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiComparto;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiCompletiComparto;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * Wrap PojoDatiCompletiComparto (DB - VCfgTCatComp) in DatiCompletiComparto
 * (API)
 */
public class DatiCompletiCompartoWrapper {
  private static Log log = LogFactory.getLog(DatiCompletiCompartoWrapper.class);

  public static final List<DatiCompletiComparto> wrapList(final List<VCfgTCatComp> pojos) {
    return (pojos == null || pojos.isEmpty())
        ? new ArrayList<>()
        : pojos.stream()
            .filter(Objects::nonNull)
            .map(DatiCompletiCompartoWrapper::wrap)
            .collect(Collectors.toList());
  }

  public static final DatiCompletiComparto wrap(final VCfgTCatComp pojo) {
    VCfgTCatCompBuilder builder = new VCfgTCatCompBuilder(pojo).buildSezione().buildComparto();

    DatiCompletiComparto toRet = new DatiCompletiComparto();
    DatiSezione sezione = DatiSezioneWrapper.wrap(builder.getSezione());
    DatiComparto comparto = DatiCompartoWrapper.wrap(builder.getComparto());

    toRet.setDatiSezione(sezione);
    toRet.setDatiComparto(comparto);

    return toRet;
  }
}
