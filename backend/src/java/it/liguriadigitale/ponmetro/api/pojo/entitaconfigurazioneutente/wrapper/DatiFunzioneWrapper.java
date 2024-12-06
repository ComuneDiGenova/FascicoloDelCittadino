package it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.wrapper;

import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatFunz;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiFunzione;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * Wrap PojoDatiFunzione (DB - CfgTCatFunz) in DatiFunzione (API)
 */
public class DatiFunzioneWrapper {
  private static Log log = LogFactory.getLog(DatiFunzioneWrapper.class);

  public static final List<DatiFunzione> wrapList(final List<CfgTCatFunz> pojos) {
    return (pojos == null || pojos.isEmpty())
        ? new ArrayList<>()
        : pojos.stream()
            .filter(Objects::nonNull)
            .map(DatiFunzioneWrapper::wrap)
            .collect(Collectors.toList());
  }

  public static final DatiFunzione wrap(final CfgTCatFunz pojo) {
    DatiFunzione toRet = new DatiFunzione();
    toRet.setDenominazioneFunz(pojo.getDenominazioneFunz());
    toRet.setDescrizioneFunz(pojo.getDescrizioneFunz());
    toRet.setFlagAbilitazione(pojo.getFlagAbilitazione());
    toRet.setIdComparto(String.valueOf(pojo.getIdComp()));
    toRet.setIdFunzione(String.valueOf(pojo.getIdFunz()));
    toRet.setClassePagina(pojo.getClassePaginaStd());
    toRet.setClassePaginaAlternative(pojo.getClassePaginaAlt());
    toRet.setWicketLabelId(pojo.getWicketLabelIdStd());
    toRet.setWicketLabelIdAlternative(pojo.getWicketLabelIdAlt());
    toRet.setWicketTitle(pojo.getWicketTitleStd());
    toRet.setWicketTitleAlternative(pojo.getWicketTitleAlt());
    toRet.setFlagResidente(pojo.getIsResidente());
    toRet.setFlagNonResidente(pojo.getIsNonResidente());

    return toRet;
  }
}
