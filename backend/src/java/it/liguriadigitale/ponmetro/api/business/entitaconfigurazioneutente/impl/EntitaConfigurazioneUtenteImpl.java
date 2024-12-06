package it.liguriadigitale.ponmetro.api.business.entitaconfigurazioneutente.impl;

import com.sun.xml.fastinfoset.util.StringArray;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.integration.dao.AbstractTableWithoutKeysDAO;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroBusinessHelper;
import it.liguriadigitale.ponmetro.api.business.entitaconfigurazioneutente.service.EntitaConfigurazioneUtenteInterface;
import it.liguriadigitale.ponmetro.api.integration.dao.CfgTCatCompDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.CfgTCatFunzDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.CfgTCatSezDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.CfgTCatWidgDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.VCfgRFcittWidgDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.VCfgTCatCompDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.VCfgTCatFunzDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.VCfgTCatWidgDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.extend.VCfgRFcittCompDAOExtend;
import it.liguriadigitale.ponmetro.api.integration.dao.extend.VCfgRFcittWidgDAOExtend;
import it.liguriadigitale.ponmetro.api.integration.dao.update.CfgRFcittCompGestisciDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.update.CfgRFcittWidgGestisciDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.view.VHomeWidgDelegheDAO;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgRFcittWidg;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatComp;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatFunz;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatSez;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatWidg;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgRFcittComp;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgRFcittWidg;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgTCatComp;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgTCatFunz;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgTCatWidg;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.wrapper.DatiCompartoWrapper;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.wrapper.DatiCompletiCompartoWrapper;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.wrapper.DatiCompletiFunzioneWrapper;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.wrapper.DatiCompletiWidgetWrapper;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.wrapper.DatiFunzioneWrapper;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.wrapper.DatiNotificaCompartoWrapper;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.wrapper.DatiSezioneWrapper;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.wrapper.DatiVisualizzazioneSezioneWidgetWrapper;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.wrapper.DatiWidgetWrapper;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiComparto;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiCompletiComparto;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiCompletiFunzione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiCompletiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiCompletiWidget;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiFunzione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiGestioneNotificaComparto;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiGestioneVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiNotificaComparto;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiWidget;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.EnumEntitaConfigFiltroBaseGenerico;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.EnumEntitaConfigFiltroSospensioniFunzioni;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.EnumFiltroFlag;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class FlagsAttivazione {
  public boolean sezioneAttiva = true;
  public boolean compartoAttiva = true;
  public boolean funzioneAttiva = true;
  public boolean widgetAttiva = true;

  public FlagsAttivazione() {
    sezioneAttiva = true;
    compartoAttiva = true;
    funzioneAttiva = true;
    widgetAttiva = true;
  }

  public FlagsAttivazione(
      boolean sezioneAttiva, boolean compartoAttiva, boolean funzioneAttiva, boolean widgetAttiva) {
    super();
    this.sezioneAttiva = sezioneAttiva;
    this.compartoAttiva = compartoAttiva;
    this.funzioneAttiva = funzioneAttiva;
    this.widgetAttiva = widgetAttiva;
  }
}

public class EntitaConfigurazioneUtenteImpl implements EntitaConfigurazioneUtenteInterface {

  private static Log log = LogFactory.getLog(EntitaConfigurazioneUtenteImpl.class);
  private static String className = EntitaConfigurazioneUtenteImpl.class.getName();

  /* retrieve sezioni */

  private CfgTCatSez getPojoSezione(Long idSezione) throws BusinessException {
    CfgTCatSez cfgTCatSez = new CfgTCatSez();
    cfgTCatSez.setIdSez(idSezione);
    return getSingoloOggetto(cfgTCatSez, CfgTCatSezDAO.class);
  }

  private List<CfgTCatSez> getPojosSezioni() throws BusinessException {
    return getListaOggetti(new CfgTCatSez(), CfgTCatSezDAO.class);
  }

  @Override
  public List<DatiSezione> getSezioni() throws BusinessException {
    String methodName = "getSezioni";
    List<DatiSezione> toRet = DatiSezioneWrapper.wrapList(getPojosSezioni());
    return toRet;
  }

  @Override
  public DatiSezione getSezioneByKey(String sezioneKey) throws BusinessException {
    String methodName = "getSezioneByKey";
    if (StringUtils.isEmpty(sezioneKey)) {
      throw new BusinessException("sezioneKey non puo essere null o vuota");
    }
    CfgTCatSez pojo = getPojoSezione(Long.valueOf(sezioneKey));
    DatiSezione toRet = DatiSezioneWrapper.wrap(pojo);
    return toRet;
  }

  @Override
  public DatiCompletiSezione getSezioneCompletiByKey(String sezioneKey) throws BusinessException {
    String methodName = "getSezioneCompletiByKey";
    if (StringUtils.isEmpty(sezioneKey)) {
      throw new BusinessException("sezioneKey non puo essere null o vuota");
    }
    DatiCompletiSezione toRet = null;
    return toRet;
  }

  /* end retrieve sezioni */

  /* retrieve comparti */

  private CfgTCatComp getPojoComparto(Long idComparto) throws BusinessException {
    CfgTCatComp cfgTCatComp = new CfgTCatComp();
    cfgTCatComp.setIdComp(idComparto);
    return getSingoloOggetto(cfgTCatComp, CfgTCatCompDAO.class);
  }

  private List<CfgTCatComp> getPojosComparti(Long idSezione) throws BusinessException {
    CfgTCatComp cfgTCatComp = new CfgTCatComp();
    if (idSezione != null) {
      cfgTCatComp.setIdSez(idSezione);
    }
    return getListaOggetti(cfgTCatComp, CfgTCatCompDAO.class);
  }

  private List<VCfgTCatComp> getPojosCompartiCompleti(EnumFiltroFlag enumFiltroFlag)
      throws BusinessException {
    VCfgTCatComp vCfgTCatComp = new VCfgTCatComp();
    if (enumFiltroFlag != null) {
      FlagsAttivazione flags = getFlagAttivazione(enumFiltroFlag);
      vCfgTCatComp.setFlagAbilitazioneSez(flags.sezioneAttiva);
      vCfgTCatComp.setFlagAbilitazioneComp(flags.compartoAttiva);
    }
    return getListaOggetti(vCfgTCatComp, VCfgTCatCompDAO.class);
  }

  @Override
  public List<DatiComparto> getCompartiBySezioneKey(String sezioneKey) throws BusinessException {
    String methodName = "getCompartiBySezioneKey";
    if (StringUtils.isEmpty(sezioneKey)) {
      throw new BusinessException("sezioneKey non puo essere null o vuota");
    }
    List<CfgTCatComp> pojos = getPojosComparti(getRightValueParameter(sezioneKey));
    List<DatiComparto> toRet = DatiCompartoWrapper.wrapList(pojos);
    return toRet;
  }

  @Override
  public DatiComparto getCompartoByKey(String compartoKey) throws BusinessException {
    String methodName = "getCompartoByKey";
    if (StringUtils.isEmpty(compartoKey)) {
      throw new BusinessException("compartoKey non puo essere null o vuota");
    }
    CfgTCatComp pojo = getPojoComparto(Long.valueOf(compartoKey));
    DatiComparto toRet = DatiCompartoWrapper.wrap(pojo);
    return toRet;
  }

  @Override
  public DatiCompletiComparto getCompartoCompletiByKey(String compartoKey)
      throws BusinessException {
    return null;
  }

  @Override
  public List<DatiCompletiComparto> getCompartiCompletiByFilter(EnumFiltroFlag enumFiltroFlag)
      throws BusinessException {
    String methodName = "getCompartiCompletiByFilter";
    if (enumFiltroFlag == null) {
      throw new BusinessException("enumFiltroFlag non puo essere null");
    }
    List<VCfgTCatComp> pojos = getPojosCompartiCompleti(enumFiltroFlag);
    List<DatiCompletiComparto> toRet = DatiCompletiCompartoWrapper.wrapList(pojos);
    return toRet;
  }

  private List<VCfgRFcittComp> getPojosNotificheCittadinoComparti(Long idAnagrafica)
      throws BusinessException {
    VCfgRFcittComp vCfgRFcittComp = new VCfgRFcittComp();

    if (idAnagrafica != null) {
      vCfgRFcittComp.setIdFcitt(idAnagrafica);
    }
    return getListaOggetti(vCfgRFcittComp, VCfgRFcittCompDAOExtend.class);
  }

  @Override
  public List<DatiNotificaComparto> getNoticheCompartiByAnagraficaKey(String anagraficaKey)
      throws BusinessException {
    String methodName = "getNoticheCompartiByAnagraficaKey";
    if (StringUtils.isEmpty(anagraficaKey)) {
      throw new BusinessException("anagraficaKey non puo essere null o vuota");
    }
    List<VCfgRFcittComp> pojos = getPojosNotificheCittadinoComparti(Long.valueOf(anagraficaKey));
    List<DatiNotificaComparto> toRet = DatiNotificaCompartoWrapper.wrapList(pojos);
    return toRet;
  }

  /* end retrieve comparti */

  /* start set comparti */
  @Override
  public void setNoticheCompartiByAnagraficaKey(
      String anagraficaKey, List<DatiGestioneNotificaComparto> listaDati) throws BusinessException {
    String methodName = "setNoticheCompartiByAnagraficaKey";
    setNoticheCompartiBySezioneKeyCompartoKeyAnagraficaKey(
        EnumEntitaConfigFiltroBaseGenerico.MINUS.toString(),
        EnumEntitaConfigFiltroBaseGenerico.MINUS.toString(),
        anagraficaKey,
        listaDati);
  }

  @Override
  public void setNoticheCompartiBySezioneKeyCompartoKeyAnagraficaKey(
      String sezioneKey,
      String compartoKey,
      String anagraficaKey,
      List<DatiGestioneNotificaComparto> listaDati)
      throws BusinessException {
    String methodName = "setNoticheCompartiBySezioneKeyCompartoKeyAnagraficaKey";
    if (StringUtils.isEmpty(sezioneKey)) {
      throw new BusinessException("sezioneKey non puo essere null o vuota");
    }
    if (StringUtils.isEmpty(compartoKey)) {
      throw new BusinessException("compartoKey non puo essere null o vuota");
    }
    if (StringUtils.isEmpty(anagraficaKey)) {
      throw new BusinessException("anagraficaKey non puo essere null o vuota");
    }
    if (listaDati == null || listaDati.isEmpty()) {
      throw new BusinessException("listaDati non puo essere null o vuota");
    }

    StringArray sArrayCheckOnDati = new StringArray();
    Integer index = 0;
    Long lSezione = getRightValueParameter(sezioneKey);
    Long lComparto = getRightValueParameter(compartoKey);
    Long lAnagrafica = Long.valueOf(anagraficaKey);

    for (DatiGestioneNotificaComparto dato : listaDati) {
      index++;
      if ((dato.getIdComparto() == null || dato.getIdComparto().isEmpty()) && lComparto == null) {
        sArrayCheckOnDati.add(
            " comparto nel path nullo e dell'index (1base) "
                + index
                + " non valido (nullo o vuoto)");
        continue;
      }

      Long lCompartoKeyDaUsare = getRightValueParameter(dato.getIdComparto());

      if (lCompartoKeyDaUsare != null && lComparto != null && lCompartoKeyDaUsare != lComparto) {
        sArrayCheckOnDati.add(
            " comparto in lista: "
                + lCompartoKeyDaUsare
                + " "
                + " diverso dal comparto in path: "
                + lComparto
                + " per index (1base): "
                + index);
        continue;
      }
      // o (entrambi null)
      // o (uno dei due null)
      // o (entrambi valorizzati uguali)
      lCompartoKeyDaUsare = (lComparto == null) ? lCompartoKeyDaUsare : lComparto;

      // Preferisco usar ela vista invece di aggiungere parametro
      VCfgRFcittComp vcfgRFcittComp = new VCfgRFcittComp();

      // vcfgRFcittComp.setIdSez(lSezione);
      vcfgRFcittComp.setIdComp(lCompartoKeyDaUsare);
      vcfgRFcittComp.setIdFcitt(lAnagrafica);
      utilGestisciComparto(vcfgRFcittComp, !dato.getFlagRegistra());
    }
    if (sArrayCheckOnDati.getSize() > 0) {
      if (sArrayCheckOnDati.getSize() == listaDati.size()) {
        // errore totale Exception
        throw new BusinessException(
            "Tutti idComparto sono invalidi, dettagli: " + sArrayCheckOnDati);
      }
      // PartialException
      throw new BusinessException(
          "206 - alcuni o tutti idComparto sono invalidi,	dettagli: " + sArrayCheckOnDati);
    }
  }

  private void utilGestisciComparto(VCfgRFcittComp vcfgRFcittComp, boolean deregistra)
      throws BusinessException {
    CfgRFcittCompGestisciDAO cfgRFcittCompGestisciDAO =
        new CfgRFcittCompGestisciDAO(vcfgRFcittComp);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(cfgRFcittCompGestisciDAO);
    if (deregistra) {
      helper.aggiornaOggetto();
    } else {
      helper.inserisciOggetto();
    }
  }

  /* end set comparti */

  /* retrieve funzioni */

  private CfgTCatFunz getPojoFunzione(Long idFunzione) throws BusinessException {
    CfgTCatFunz cfgTCatFunz = new CfgTCatFunz();
    cfgTCatFunz.setIdFunz(idFunzione);
    return getSingoloOggetto(cfgTCatFunz, CfgTCatFunzDAO.class);
  }

  private List<CfgTCatFunz> getPojosFunzioni(Long idComparto) throws BusinessException {
    CfgTCatFunz cfgTCatFunz = new CfgTCatFunz();
    if (idComparto != null) {
      cfgTCatFunz.setIdComp(idComparto);
    }
    return getListaOggetti(cfgTCatFunz, CfgTCatFunzDAO.class);
  }

  @Override
  public List<DatiFunzione> getFunzioniByCompartoKey(String compartoKey) throws BusinessException {
    String methodName = "getFunzioniByCompartoKey";
    if (StringUtils.isEmpty(compartoKey)) {
      throw new BusinessException("compartoKey non puo essere null o vuota");
    }
    List<CfgTCatFunz> pojos = getPojosFunzioni(getRightValueParameter(compartoKey));
    List<DatiFunzione> toRet = DatiFunzioneWrapper.wrapList(pojos);
    return toRet;
  }

  @Override
  public List<DatiFunzione> getFunzioniByFiltroSospensioniSezioneKeyCompartoKey(
      EnumEntitaConfigFiltroSospensioniFunzioni filtro, String sezioneKey, String compartoKey)
      throws BusinessException {
    String methodName = "getFunzioniByFiltroSospensioniSezioneKeyCompartoKey";
    if (filtro == null) {
      throw new BusinessException("filtro non puo essere null o vuota");
    }
    if (StringUtils.isEmpty(sezioneKey)) {
      throw new BusinessException("sezioneKey non puo essere null o vuota");
    }
    if (StringUtils.isEmpty(compartoKey)) {
      throw new BusinessException("compartoKey non puo essere null o vuota");
    }

    List<CfgTCatFunz> listPojos = new ArrayList<>();
    /*
     * getPojosFunzioniFiltroSospese( filtro, StringUtils.equals(sezioneKey,
     * EnumEntitaConfigFiltroBaseGenerico.MINUS.toString()) ? null :
     * Long.valueOf(sezioneKey), StringUtils.equals(compartoKey,
     * EnumEntitaConfigFiltroBaseGenerico.MINUS.toString()) ? null :
     * Long.valueOf(compartoKey));
     */

    List<DatiFunzione> toRet = DatiFunzioneWrapper.wrapList(listPojos);
    return toRet;
  }

  @Override
  public List<DatiFunzione> getFunzioniBySezioneKeyCompartoKey(
      String sezioneKey, String compartoKey) throws BusinessException {
    return null;
  }

  @Override
  public DatiFunzione getFunzioneByKey(String funzioneKey) throws BusinessException {
    String methodName = "getFunzioneByKey";
    if (StringUtils.isEmpty(funzioneKey)) {
      throw new BusinessException("funzioneKey non puo essere null o vuota");
    }
    CfgTCatFunz pojo = getPojoFunzione(Long.valueOf(funzioneKey));
    DatiFunzione toRet = DatiFunzioneWrapper.wrap(pojo);
    return toRet;
  }

  @Override
  public DatiCompletiFunzione getFunzioneCompletiByKey(String funzioneKey)
      throws BusinessException {
    return null;
  }

  @Override
  public List<DatiCompletiFunzione> getFunzioniCompletiByFilter(EnumFiltroFlag enumFiltroFlag)
      throws BusinessException {
    String methodName = "getFunzioniCompletiByFilter";
    if (enumFiltroFlag == null) {
      throw new BusinessException("enumFiltroFlag non puo essere null");
    }
    List<VCfgTCatFunz> pojos = getPojosFunzioniCompleti(enumFiltroFlag);

    List<DatiCompletiFunzione> toRet = DatiCompletiFunzioneWrapper.wrapList(pojos);
    return toRet;
  }

  /* end retrieve funzioni */

  @SuppressWarnings("incomplete-switch")
  private FlagsAttivazione getFlagAttivazione(EnumFiltroFlag enumFiltroFlag) {
    boolean sezioneAttiva = true;
    boolean compartoAttiva = true;
    boolean funzioneAttiva = true;
    boolean widgetAttiva = true;
    if (enumFiltroFlag != null) {

      switch (enumFiltroFlag) {
        case FLAG_ABILITAZIONE_SEZIONE_ATTIVO:
          {
            sezioneAttiva = true;
          }
          break;
        case FLAG_ABILITAZIONE_SEZIONE_ATTIVO_ALTRI_DISATTIVI:
          {
            sezioneAttiva = true;
            compartoAttiva = false;
            funzioneAttiva = false;
            widgetAttiva = false;
          }
          break;
        case FLAG_ABILITAZIONE_COMPARTO_ATTIVO:
          {
            compartoAttiva = true;
          }
          break;
        case FLAG_ABILITAZIONE_COMPARTO_ATTIVO_ALTRI_DISATTIVI:
          {
            sezioneAttiva = false;
            compartoAttiva = true;
            funzioneAttiva = false;
            widgetAttiva = false;
          }
          break;
        case FLAG_ABILITAZIONE_FUNZIONE_ATTIVO:
          {
            funzioneAttiva = true;
          }
          break;
        case FLAG_ABILITAZIONE_FUNZIONE_ATTIVO_ALTRI_DISATTIVI:
          {
            sezioneAttiva = false;
            compartoAttiva = false;
            funzioneAttiva = true;
            widgetAttiva = false;
          }
          break;
        case FLAG_ABILITAZIONE_WIDGET_ATTIVO:
          {
            widgetAttiva = true;
          }
          break;
        case FLAG_ABILITAZIONE_WIDGET_ATTIVO_ALTRI_DISATTIVI:
          {
            sezioneAttiva = false;
            compartoAttiva = false;
            funzioneAttiva = false;
            widgetAttiva = true;
          }
          break;
      }
    }
    return new FlagsAttivazione(sezioneAttiva, compartoAttiva, funzioneAttiva, widgetAttiva);
  }

  /* retrieve widget */

  private List<VCfgTCatFunz> getPojosFunzioniCompleti(EnumFiltroFlag enumFiltroFlag)
      throws BusinessException {
    VCfgTCatFunz vCfgTCatFunz = new VCfgTCatFunz();
    if (enumFiltroFlag != null) {
      FlagsAttivazione flags = getFlagAttivazione(enumFiltroFlag);
      vCfgTCatFunz.setFlagAbilitazioneSez(flags.sezioneAttiva);
      vCfgTCatFunz.setFlagAbilitazioneComp(flags.compartoAttiva);
      vCfgTCatFunz.setFlagAbilitazioneFunz(flags.funzioneAttiva);
    }
    return getListaOggetti(vCfgTCatFunz, VCfgTCatFunzDAO.class);
  }

  private CfgTCatWidg getPojoWidget(Long idWidget) throws BusinessException {
    CfgTCatWidg cfgTCatWidg = new CfgTCatWidg();
    cfgTCatWidg.setIdWidg(idWidget);
    return getSingoloOggetto(cfgTCatWidg, CfgTCatWidgDAO.class);
  }

  private List<CfgTCatWidg> getPojosWidgets(Long idFunzione) throws BusinessException {
    CfgTCatWidg cfgTCatWidg = new CfgTCatWidg();
    if (idFunzione != null) {
      cfgTCatWidg.setIdFunz(idFunzione);
    }
    return getListaOggetti(cfgTCatWidg, CfgTCatWidgDAO.class);
  }

  private List<VCfgTCatWidg> getPojosWidgetsCompleti(EnumFiltroFlag enumFiltroFlag)
      throws BusinessException {
    VCfgTCatWidg vCfgTCatWidg = new VCfgTCatWidg();
    if (enumFiltroFlag != null) {
      FlagsAttivazione flags = getFlagAttivazione(enumFiltroFlag);
      vCfgTCatWidg.setFlagAbilitazioneSez(flags.sezioneAttiva);
      vCfgTCatWidg.setFlagAbilitazioneComp(flags.compartoAttiva);
      vCfgTCatWidg.setFlagAbilitazioneFunz(flags.funzioneAttiva);
      vCfgTCatWidg.setFlagAbilitazioneWidg(flags.widgetAttiva);
    }
    return getListaOggetti(vCfgTCatWidg, VCfgTCatWidgDAO.class);
  }

  private List<VCfgRFcittWidg> getPojosCittadinoWidgets(Long idSezione, Long idAnagrafica)
      throws BusinessException {
    VCfgRFcittWidg vCfgRFcittWidg = new VCfgRFcittWidg();
    if (idSezione != null) {
      vCfgRFcittWidg.setIdSez(idSezione);
    }
    if (idAnagrafica != null) {
      vCfgRFcittWidg.setIdFcitt(idAnagrafica);
    }
    return getListaOggetti(vCfgRFcittWidg, VCfgRFcittWidgDAO.class);
  }

  private List<VCfgRFcittWidg> getPojosDaVisualizzareOConfigurareCittadinoWidgets(
      Long idSezione, Long idAnagrafica, boolean boFlagAbilitato) throws BusinessException {
    VCfgRFcittWidg vCfgRFcittWidg = new VCfgRFcittWidg();
    if (idSezione != null) {
      vCfgRFcittWidg.setIdSez(idSezione);
    }
    if (idAnagrafica != null) {
      vCfgRFcittWidg.setIdFcitt(idAnagrafica);
    }
    // uso questo per discriminare se volgio i flag = 1 (visualizzare) o
    // configurare (false)
    vCfgRFcittWidg.setFlagAbilitazioneFcittWidg(boFlagAbilitato);
    return getListaOggetti(vCfgRFcittWidg, VCfgRFcittWidgDAOExtend.class);
  }

  @Override
  public List<DatiWidget> getWidgetsByFunzioneKey(String funzioneKey) throws BusinessException {
    String methodName = "getWidgetsByFunzioneKey";
    if (StringUtils.isEmpty(funzioneKey)) {
      throw new BusinessException("funzioneKey non puo essere null o vuota");
    }
    List<CfgTCatWidg> pojos = getPojosWidgets(getRightValueParameter(funzioneKey));
    List<DatiWidget> toRet = DatiWidgetWrapper.wrapList(pojos);
    return toRet;
  }

  @Override
  public List<DatiWidget> getWidgetsBySezioneKeyCompartoKeyFunzioneKey(
      String sezioneKey, String compartoKey, String funzioneKey) throws BusinessException {
    return null;
  }

  @Override
  public DatiWidget getWidgetByKey(String wicketKey) throws BusinessException {
    String methodName = "getWidgetByKey";
    if (StringUtils.isEmpty(wicketKey)) {
      throw new BusinessException("wicketKey non puo essere null o vuota");
    }
    CfgTCatWidg pojo = getPojoWidget(Long.valueOf(wicketKey));
    DatiWidget toRet = DatiWidgetWrapper.wrap(pojo);
    return toRet;
  }

  @Override
  public List<DatiVisualizzazioneSezioneWidget> getWidgetsCittadinoBySezioneKeyAnagraficaKey(
      String sezioneKey, String anagraficaKey) throws BusinessException {
    String methodName = "getWidgetsCittadinoBySezioneKeyAnagraficaKey";
    if (StringUtils.isEmpty(sezioneKey)) {
      throw new BusinessException("sezioneKey non puo essere null o vuota");
    }
    if (StringUtils.isEmpty(anagraficaKey)) {
      throw new BusinessException("anagraficaKey non puo essere null o vuota");
    }

    List<VCfgRFcittWidg> pojos =
        getPojosCittadinoWidgets(getRightValueParameter(sezioneKey), Long.valueOf(anagraficaKey));

    List<DatiVisualizzazioneSezioneWidget> toRet =
        DatiVisualizzazioneSezioneWidgetWrapper.wrapList(pojos);

    return toRet;
  }

  @Override
  public List<DatiCompletiWidget> getWidgetsCompletiByFilter(EnumFiltroFlag enumFiltroFlag)
      throws BusinessException {
    String methodName = "getWidgetsCompletiByFilter";
    if (enumFiltroFlag == null) {
      throw new BusinessException("enumFiltroFlag non puo essere null");
    }
    List<VCfgTCatWidg> pojos = getPojosWidgetsCompleti(enumFiltroFlag);
    List<DatiCompletiWidget> toRet = DatiCompletiWidgetWrapper.wrapList(pojos);
    return toRet;
  }

  @Override
  public List<DatiVisualizzazioneSezioneWidget>
      getWidgetsCittadinoDaVisualizzareOConfigurareBySezioneKeyAnagraficaKey(
          String sezioneKey, String anagraficaKey, boolean getDaVisualizzare, Boolean isResidente)
          throws BusinessException {
    String methodName = "getWidgetsCittadinoDaVisualizzareBySezioneKeyAnagraficaKey";
    if (StringUtils.isEmpty(sezioneKey)) {
      throw new BusinessException("sezioneKey non puo essere null o vuota");
    }
    if (StringUtils.isEmpty(anagraficaKey)) {
      throw new BusinessException("anagraficaKey non puo essere null o vuota");
    }

    List<VCfgRFcittWidg> pojos =
        getPojosDaVisualizzareOConfigurareCittadinoWidgets(
            getRightValueParameter(sezioneKey), Long.valueOf(anagraficaKey), getDaVisualizzare);

    List<VCfgRFcittWidg> listaFiltrata = new ArrayList();
    if (isResidente) {
      listaFiltrata = filtraWidgetNonVisibiliPerResidenti(pojos);
    } else {
      listaFiltrata = filtraWidgetNonVisibiliPerNonResidenti(pojos);
    }

    List<DatiVisualizzazioneSezioneWidget> toRet =
        DatiVisualizzazioneSezioneWidgetWrapper.wrapList(listaFiltrata);

    return toRet;
  }

  /* end retrieve widget */

  private List<VCfgRFcittWidg> filtraWidgetNonVisibiliPerNonResidenti(List<VCfgRFcittWidg> pojos) {
    log.debug("filtraWidgetNonVisibiliPerNonResidenti: lista: " + pojos.size());

    List<VCfgRFcittWidg> listaFiltrata =
        pojos.stream().filter(c -> c.getFlagNonResidente()).collect(Collectors.toList());
    log.debug("filtraWidgetNonVisibiliPerNonResidenti: listaFiltrata: " + listaFiltrata.size());

    return listaFiltrata;
  }

  private List<VCfgRFcittWidg> filtraWidgetNonVisibiliPerResidenti(List<VCfgRFcittWidg> pojos) {
    log.debug("filtraWidgetNonVisibiliPerNonResidenti: lista: " + pojos.size());
    List<VCfgRFcittWidg> listaFiltrata =
        pojos.stream().filter(c -> c.getFlagResidente()).collect(Collectors.toList());
    log.debug("filtraWidgetNonVisibiliPerNonResidenti: listaFiltrata: " + listaFiltrata.size());
    return listaFiltrata;
  }

  /* start set widget */
  @Override
  public void setWidgetsCittadinoBySezioneKeyAnagraficaKey(
      String sezioneKey,
      String anagraficaKey,
      List<DatiGestioneVisualizzazioneSezioneWidget> listaDati)
      throws BusinessException {
    String methodName = "setWidgetsCittadinoBySezioneKeyAnagraficaKey";
    if (StringUtils.isEmpty(sezioneKey)) {
      throw new BusinessException("sezioneKey non puo essere null o vuota");
    }
    if (StringUtils.isEmpty(anagraficaKey)) {
      throw new BusinessException("anagraficaKey non puo essere null o vuota");
    }
    if (listaDati == null || listaDati.isEmpty()) {
      throw new BusinessException("listaDati non puo essere null o vuota");
    }

    for (DatiGestioneVisualizzazioneSezioneWidget dato : listaDati) {
      if (StringUtils.isBlank(dato.getIdWidget())) {
        log.debug(" dato.getIdWidget: nullo o vuoto");
        continue;
      }
      CfgRFcittWidg cfgRFcittWidg = new CfgRFcittWidg();
      cfgRFcittWidg.setIdFcitt(Long.valueOf(anagraficaKey));
      cfgRFcittWidg.setIdWidg(Long.valueOf(dato.getIdWidget()));

      CfgRFcittWidgGestisciDAO cfgRFcittWidgGestisciDAO =
          new CfgRFcittWidgGestisciDAO(cfgRFcittWidg);

      PonMetroBusinessHelper helper = new PonMetroBusinessHelper(cfgRFcittWidgGestisciDAO);

      if (dato.getFlagAssocia()) {
        helper.inserisciOggetto();
      } else {
        helper.aggiornaOggetto();
      }
    }
  }

  /* end set widget */

  // T1 is pojo, T2 is DAO
  private <T1, T2 extends AbstractTableWithoutKeysDAO> PonMetroBusinessHelper getHelper(
      T1 t, Class<T2> clazz)
      throws InstantiationException,
          IllegalAccessException,
          IllegalArgumentException,
          InvocationTargetException,
          NoSuchMethodException,
          SecurityException {
    T2 tDAO = clazz.getConstructor(t.getClass()).newInstance(t);
    return new PonMetroBusinessHelper(tDAO);
  }

  // T1 is pojo, T2 is DAO
  @SuppressWarnings("unchecked")
  private <T1, T2 extends AbstractTableWithoutKeysDAO> List<T1> getListaOggetti(
      T1 t, Class<T2> clazz) throws BusinessException {
    try {
      PonMetroBusinessHelper helper = getHelper(t, clazz);

      return (helper.cercaOggetti());
    } catch (Exception e) {
      throw new BusinessException(" helper > exception on lista oggetti: " + e.getMessage());
    }
  }

  // T1 is pojo, T2 is DAO
  @SuppressWarnings("unchecked")
  private <T1, T2 extends AbstractTableWithoutKeysDAO> T1 getSingoloOggetto(T1 t, Class<T2> clazz)
      throws BusinessException {
    try {
      return (T1) (getHelper(t, clazz).cercaOggetto());
    } catch (Exception e) {
      throw new BusinessException(" helper > singolo oggetto" + e.getMessage());
    }
  }

  private Long getRightValueParameter(String valueToCheck) throws BusinessException {
    try {
      return StringUtils.equals(valueToCheck, EnumEntitaConfigFiltroBaseGenerico.MINUS.toString())
          ? null
          : Long.valueOf(valueToCheck);
    } catch (Exception e) {
      throw new BusinessException(
          " > Errore valueToCheck: " + valueToCheck + " non riconosciuto < ");
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<DatiVisualizzazioneSezioneWidget> getWidgetsCittadinoConDelega(Boolean isResidente)
      throws BusinessException {

    VHomeWidgDelegheDAO dao = new VHomeWidgDelegheDAO();
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);

    List<VCfgRFcittWidg> listaNonFiltrata = helper.cercaOggetti();
    List<VCfgRFcittWidg> listaFiltrata = new ArrayList<>();
    if (isResidente) {
      listaFiltrata = filtraWidgetNonVisibiliPerResidenti(listaNonFiltrata);
    } else {
      listaFiltrata = filtraWidgetNonVisibiliPerNonResidenti(listaNonFiltrata);
    }

    List<DatiVisualizzazioneSezioneWidget> listaRisultato =
        DatiVisualizzazioneSezioneWidgetWrapper.wrapList(listaFiltrata);
    return listaRisultato;
  }
}
