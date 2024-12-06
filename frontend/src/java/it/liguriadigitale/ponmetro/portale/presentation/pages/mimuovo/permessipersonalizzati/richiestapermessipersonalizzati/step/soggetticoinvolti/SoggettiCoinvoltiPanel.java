package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.soggetticoinvolti;

import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.Disabile;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.RichiestaPermessoPersonalizzato;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.SoggettiCoinvolti;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.Tutore;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.components.util.EnumTipoDomandaPermessoPersonalizzato;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.model.CompoundPropertyModel;

public class SoggettiCoinvoltiPanel extends BasePanel {

  private static final long serialVersionUID = -3021289773196275267L;

  private WebMarkupContainer wmkTutoreCoincideConAccompagnatore;
  private CheckBox tutoreCoincideConAccompagnatore;
  private boolean checkTutoreCoincideConAccompagnatoreVisible;

  // private WebMarkupContainer wmkTipoDomanda;
  // Label tipoDomandaSelezionata;

  TutoreGenitorePanel tutoreGenitorePanel;
  DisabilePanel disabilePanel;
  AccompagnatorePanel accompagnatorePanel;

  CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel;

  private boolean attivo;

  public SoggettiCoinvoltiPanel(
      String id,
      CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel,
      boolean attivo) {
    super(id);
    this.richiestaPermessoPersonalizzatoModel = richiestaPermessoPersonalizzatoModel;
    this.attivo = attivo;
    fillDati(richiestaPermessoPersonalizzatoModel);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
  }

  @Override
  public void fillDati(Object dati) {

    // createFeedBackStep3Panel();

    addOrReplace(
        wmkTutoreCoincideConAccompagnatore =
            new WebMarkupContainer("wmkTutoreCoincideConAccompagnatore"));

    wmkTutoreCoincideConAccompagnatore.addOrReplace(
        tutoreCoincideConAccompagnatore = creaToggleTutoreCoincideConAccompagnatore());

    if (richiestaPermessoPersonalizzatoModel.getObject().getSoggettiCoinvolti() == null)
      richiestaPermessoPersonalizzatoModel
          .getObject()
          .setSoggettiCoinvolti(new SoggettiCoinvolti());

    disabilePanel =
        new DisabilePanel("disabilePanel", richiestaPermessoPersonalizzatoModel, attivo);
    tutoreGenitorePanel =
        new TutoreGenitorePanel("tutoreGenitorePanel", richiestaPermessoPersonalizzatoModel);
    accompagnatorePanel =
        new AccompagnatorePanel(
            "accompagnatorePanel", richiestaPermessoPersonalizzatoModel, attivo);

    Utente utente = getUtente();

    if (richiestaPermessoPersonalizzatoModel.getObject().getTipoDomanda() != null) {

      switch (EnumTipoDomandaPermessoPersonalizzato.getById(
          richiestaPermessoPersonalizzatoModel.getObject().getTipoDomanda().getCodice())) {
        case DISABILE_GUIDATORE_RESIDENZA:
        case DISABILE_GUIDATORE_LAVORO:
          disabilePanel.setVisible(true);
          tutoreGenitorePanel.setVisible(false);
          accompagnatorePanel.setVisible(false);
          checkTutoreCoincideConAccompagnatoreVisible = false;

          impostaDatiDisabile(utente);

          break;
        case DISABILE_ACCOMPAGNATO:
          disabilePanel.setVisible(true);

          tutoreGenitorePanel.setVisible(false);
          accompagnatorePanel.setVisible(!tutoreCoincideConAccompagnatore.getModelObject());
          accompagnatorePanel.setEnabled(true);

          checkTutoreCoincideConAccompagnatoreVisible = false;

          impostaDatiDisabile(utente);

          break;
        case DISABILE_MINORE:
        case DISABILE_TUTORE:
          disabilePanel.setVisible(true);
          tutoreCoincideConAccompagnatore.setVisible(true);
          tutoreGenitorePanel.setVisible(true);
          accompagnatorePanel.setVisible(!tutoreCoincideConAccompagnatore.getModelObject());
          accompagnatorePanel.setEnabled(true);

          checkTutoreCoincideConAccompagnatoreVisible = getUtente().isResidente();

          impostaDatiTutore(utente);

          break;
        case UNKNOWN:
          log.error("SoggettiCoinvoltiPanel: E' stata selezionata un'opzione non prevista");
      }
    }

    //		log.error("richiestaPermessoPersonalizzato.getTipoDomanda()"
    //				+ richiestaPermessoPersonalizzatoModel.getObject().getTipoDomanda());

    wmkTutoreCoincideConAccompagnatore.setVisible(checkTutoreCoincideConAccompagnatoreVisible);

    //		wmkTipoDomanda = new WebMarkupContainer("wmkTipoDomanda");
    //		wmkTipoDomanda.setOutputMarkupId(true);
    //		wmkTipoDomanda.setOutputMarkupPlaceholderTag(true);
    //		wmkTipoDomanda.setVisible(richiestaPermessoPersonalizzatoModel.getObject().getTipoDomanda()
    // != null
    //				&& richiestaPermessoPersonalizzatoModel.getObject().getTipoDomanda().getDescrizione() !=
    // null);
    //
    //		wmkTipoDomanda.addOrReplace(tipoDomandaSelezionata = new Label("tipoDomanda",
    //				new PropertyModel<>(richiestaPermessoPersonalizzatoModel.getObject().getTipoDomanda(),
    // "codice")));
    //
    //		addOrReplace(wmkTipoDomanda);

    addOrReplace(disabilePanel);
    addOrReplace(tutoreGenitorePanel);
    addOrReplace(accompagnatorePanel);
  }

  private void impostaDatiDisabile(Utente utente) {

    Disabile disabile =
        richiestaPermessoPersonalizzatoModel.getObject().getSoggettiCoinvolti().getDisabile();
    FeaturesGeoserver featuresGeoserver = new FeaturesGeoserver();
    disabile.setCodiceFiscaleDisabile(utente.getCodiceFiscaleOperatore());
    disabile.setCodiceIndividuoDisabile(utente.getIdAnagrafica());
    disabile.setCognomeDisabile(utente.getCognome());
    disabile.setNomeDisabile(utente.getNome());
    disabile.setDataNascitaDisabile(utente.getDataDiNascita());

    if (utente.getMobile() != null && !utente.getMobile().equalsIgnoreCase(""))
      disabile.setTelefonoDisabile(utente.getMobile());

    if (utente.getMail() != null && !utente.getMail().equalsIgnoreCase(""))
      disabile.setEmailDisabile(utente.getMail());

    if (utente.isResidente()) {
      disabile.setLuogoNascitaDisabile(utente.getLuogoNascita());
      disabile.setProvinciaNascitaDisabile(
          utente.getDatiCittadinoResidente().getCpvHasBirthPlace().getClvProvince());
      disabile.setIndirizzoResidenzaDisabile(
          utente.getDatiCittadinoResidente().getCpvHasAddress().getClvFullAddress());
      disabile.setCapResidenzaDisabile(
          utente.getDatiCittadinoResidente().getCpvHasAddress().getClvPostCode());
      disabile.setCittaResidenzaDisabile(
          utente.getDatiCittadinoResidente().getCpvHasAddress().getClvCity());
      disabile.setProvinciaResidenzaDisabile("GE");

      try {
        disabile.setCodCivicoResidenzaDisabile(
            Integer.parseInt(
                utente.getDatiCittadinoResidente().getCpvHasAddress().getClvStreetNumber()));
      } catch (Exception e) {
        log.error(
            "Richiesta PermessoPersonalizzatoConverter:errore nel parsing del codice civico residenza disabile");
      }

      try {
        disabile.setCodInternoResidenzaDisabile(
            Integer.parseInt(
                utente.getDatiCittadinoResidente().getCpvHasAddress().getClvFlatNumber()));
      } catch (Exception e) {
        log.error("CodInterno valorizzato con un valore non numerico.");
      }
      disabile.setLuogoResidenzaDisabile(
          utente.getDatiCittadinoResidente().getCpvHasAddress().getClvCity());

      featuresGeoserver = new FeaturesGeoserver();
      featuresGeoserver.setMACHINE_LAST_UPD(
          utente.getDatiCittadinoResidente().getCpvHasAddress().getClvFullAddress());
      featuresGeoserver.setCODICE_INDIRIZZO(
          Integer.parseInt(
              utente
                  .getDatiCittadinoResidente()
                  .getCpvHasAddress()
                  .getGenovaOntoStreetNumberCode()));

      if (!(richiestaPermessoPersonalizzatoModel.getObject().getTipoDomanda() != null
          && EnumTipoDomandaPermessoPersonalizzato.getById(
                  richiestaPermessoPersonalizzatoModel.getObject().getTipoDomanda().getCodice())
              .equals(EnumTipoDomandaPermessoPersonalizzato.DISABILE_GUIDATORE_LAVORO)))
        richiestaPermessoPersonalizzatoModel
            .getObject()
            .setGeoserverUbicazioneParcheggio(featuresGeoserver);
    }

    richiestaPermessoPersonalizzatoModel.getObject().getSoggettiCoinvolti().setDisabile(disabile);
  }

  private void impostaDatiTutore(Utente utente) {

    Tutore tutore =
        richiestaPermessoPersonalizzatoModel.getObject().getSoggettiCoinvolti().getTutore();

    tutore.setCodiceFiscaleTutore(utente.getCodiceFiscaleOperatore());
    tutore.setCodiceIndividuoTutore(utente.getIdAnagrafica());
    tutore.setCognomeTutore(utente.getCognome());
    tutore.setNomeTutore(utente.getNome());
    tutore.setDataNascitaTutore(utente.getDataDiNascita());

    if (utente.getMobile() != null && !utente.getMobile().equalsIgnoreCase(""))
      tutore.setTelefonoTutore(utente.getMobile());

    if (utente.getMail() != null && !utente.getMail().equalsIgnoreCase(""))
      tutore.setEmailTutore(utente.getMail());

    if (utente.isResidente()) {
      tutore.setLuogoNascitaTutore(utente.getLuogoNascita());
      tutore.setProvinciaNascitaTutore(
          utente.getDatiCittadinoResidente().getCpvHasBirthPlace().getClvProvince());
      tutore.setIndirizzoResidenzaTutore(
          utente.getDatiCittadinoResidente().getCpvHasAddress().getClvFullAddress());
      tutore.setCapResidenzaTutore(
          utente.getDatiCittadinoResidente().getCpvHasAddress().getClvPostCode());
      tutore.setCittaResidenzaTutore(
          utente.getDatiCittadinoResidente().getCpvHasAddress().getClvCity());
      tutore.setProvinciaResidenzaTutore("GE");

      try {
        tutore.setCodCivicoResidenzaTutore(
            Integer.parseInt(
                utente.getDatiCittadinoResidente().getCpvHasAddress().getClvStreetNumber()));
      } catch (Exception e) {
        log.error(
            "Richiesta PermessoPersonalizzatoConverter:errore nel parsing del codice civico residenza tutore");
      }

      try {
        tutore.setCodInternoResidenzaTutore(
            Integer.parseInt(
                utente.getDatiCittadinoResidente().getCpvHasAddress().getClvFlatNumber()));
      } catch (Exception e) {
        log.error("CodInterno valorizzato con un valore non numerico.");
      }

      tutore.setLuogoResidenzaTutore(
          utente.getDatiCittadinoResidente().getCpvHasAddress().getClvCity());
    }
    richiestaPermessoPersonalizzatoModel.getObject().getSoggettiCoinvolti().setTutore(tutore);
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();

    fillDati(richiestaPermessoPersonalizzatoModel);
  }

  private CheckBox creaToggleTutoreCoincideConAccompagnatore() {
    tutoreCoincideConAccompagnatore =
        new CheckBox(
            "tutoreCoincideConAccompagnatore",
            richiestaPermessoPersonalizzatoModel.bind(
                "soggettiCoinvolti.tutoreCoincideConAccompagnatore"));
    tutoreCoincideConAccompagnatore.setRequired(true);
    tutoreCoincideConAccompagnatore.setOutputMarkupId(true);
    tutoreCoincideConAccompagnatore.setOutputMarkupPlaceholderTag(true);
    tutoreCoincideConAccompagnatore.setVisible(checkTutoreCoincideConAccompagnatoreVisible);

    tutoreCoincideConAccompagnatore.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = 1L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            accompagnatorePanel.setVisible(!tutoreCoincideConAccompagnatore.getModelObject());
            // accompagnatorePanel.setVisible(false);
            target.add(tutoreGenitorePanel, accompagnatorePanel);
          }
        });

    return tutoreCoincideConAccompagnatore;
  }

  public TutoreGenitorePanel getTutoreGenitorePanel() {
    return tutoreGenitorePanel;
  }

  public DisabilePanel getDisabilePanel() {
    return disabilePanel;
  }

  public AccompagnatorePanel getAccompagnatorePanel() {
    return accompagnatorePanel;
  }

  /*
   * protected FeedbackPanel createFeedBackStep3Panel() {
   *
   * NotificationPanel feedback = new NotificationPanel("feedback3") {
   *
   * private static final long serialVersionUID = -8510097378330901001L;
   *
   * @Override protected boolean isCloseButtonVisible() { return false; } };
   * feedback.setOutputMarkupId(true); this.addOrReplace(feedback); return
   * feedback; }
   */

}
