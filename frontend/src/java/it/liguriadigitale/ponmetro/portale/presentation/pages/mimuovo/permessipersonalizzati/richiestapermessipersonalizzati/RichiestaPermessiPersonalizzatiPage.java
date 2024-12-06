package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.DomandaBody;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.IdDomandaProtocolloBody;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.PostResult;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.Protocollazione;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.AllegatoPermessiPersonalizzati;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.DichiarazionePermessiPersonalizzati;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.InserimentoDomanda;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.RichiestaPermessoPersonalizzato;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.SoggettiCoinvolti;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.util.EnumTipoDomandaPermessoPersonalizzato;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.PermessiPersonalizzatiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.converter.RichiestaPermessoPersonalizzatoConverter;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.panel.MessagePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.dichiarazioni.DichiarazioniPermessiPersonalizzatiPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.documentiallegati.DocumentiAllegatiPermessiPersonalizzatiPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.fine.FinePermessiPersonalizzatiPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.inserimentodomanda.InserimentoDomandaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.riepilogo.RiepilogoPermessiPersonalizzatiPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.soggetticoinvolti.SoggettiCoinvoltiPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.ubicazioneparcheggio.UbicazioneParcheggioPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.util.Select2Reference;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.validator.ValidatorUtil;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.builder.StepFdCBuilder;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

public class RichiestaPermessiPersonalizzatiPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 7336477405169521487L;

  protected Integer indice = 1;

  boolean isResidente;

  MessagePanel messagePanel;
  FdCBreadcrumbPanel breadcrumbPanel;
  StepPanel stepPanel;
  Form<RichiestaPermessoPersonalizzato> form;

  InserimentoDomandaPanel inserimentoDomandaPanel;
  SoggettiCoinvoltiPanel soggettiCoinvoltiPanel;
  UbicazioneParcheggioPanel ubicazioneParcheggioPanel;
  DichiarazioniPermessiPersonalizzatiPanel dichiarazioniPermessiPersonalizzatiPanel;
  DocumentiAllegatiPermessiPersonalizzatiPanel documentiAllegatiPermessiPersonalizzatiPanel;
  RiepilogoPermessiPersonalizzatiPanel riepilogoPermessiPersonalizzatiPanel;
  FinePermessiPersonalizzatiPanel finePermessiPersonalizzatiPanel;

  private RichiestaPermessoPersonalizzato richiestaPermessoPersonalizzato;

  private InserimentoDomanda inserimentoDomanda;

  private LaddaAjaxButton btnAvanti;
  private LaddaAjaxLink<Object> btnIndietro;
  private LaddaAjaxLink<Object> btnAnnulla;
  private LaddaAjaxLink<Object> btnVaiAllaListaDeiPermessiPersonalizzati;
  private List<String> listaMessaggi;

  CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel;

  public RichiestaPermessiPersonalizzatiPage() {
    super();
    getSession().removeAttribute("listaPermessiDisabili");
    indice = 1;
    inizializzaPannelli();
    creaForm();
    createFeedBackPanel();

    this.setRichiestaPermessoPersonalizzato(new RichiestaPermessoPersonalizzato());
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private void inizializzaPannelli() {

    isResidente = isResidente();

    listaMessaggi = new ArrayList<String>();
    messagePanel = new MessagePanel("messagePanel", listaMessaggi);
    addOrReplace(messagePanel);

    breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    List<StepFdC> lista =
        RichiestaPermessiPersonalizzatiPage.inizializzaStepPermessiPersonalizzati();
    stepPanel = new StepPanel("stepPanel", indice, lista);
    addOrReplace(stepPanel);
  }

  private boolean isResidente() {
    boolean isR = false;

    Utente utente = getUtente();

    isR = utente.isResidente();
    return isR;
  }

  public RichiestaPermessoPersonalizzato getRichiestaPermessoPersonalizzato() {
    return richiestaPermessoPersonalizzato;
  }

  public void setRichiestaPermessoPersonalizzato(
      RichiestaPermessoPersonalizzato richiestaPermessoPersonalizzato) {
    this.richiestaPermessoPersonalizzato = richiestaPermessoPersonalizzato;
  }

  public List<BreadcrumbFdC> getListaBreadcrumb() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioMiMuovo", "io Mi Muovo"));
    listaBreadcrumb.add(new BreadcrumbFdC("permessiPersonalizzati", "Permessi personalizzati"));
    listaBreadcrumb.add(
        new BreadcrumbFdC("richiestaPermessiPersonalizzati", "Richiesta permessi personalizzati"));

    return listaBreadcrumb;
  }

  public static List<StepFdC> inizializzaStepPermessiPersonalizzati() {
    List<StepFdC> lista = new ArrayList<>();
    StepFdC step1 =
        StepFdCBuilder.getInstance().addDescrizione("Tipo domanda").addIndice(1).build();
    StepFdC step2 =
        StepFdCBuilder.getInstance().addDescrizione("Soggetti coinvolti").addIndice(2).build();
    StepFdC step3 =
        StepFdCBuilder.getInstance().addDescrizione("Ubicazione parcheggio").addIndice(3).build();
    StepFdC step4 =
        StepFdCBuilder.getInstance().addDescrizione("Dichiarazioni").addIndice(4).build();
    StepFdC step5 =
        StepFdCBuilder.getInstance().addDescrizione("Documenti allegati").addIndice(5).build();
    StepFdC step6 = StepFdCBuilder.getInstance().addDescrizione("Riepilogo").addIndice(6).build();
    StepFdC step7 = StepFdCBuilder.getInstance().addDescrizione("Fine").addIndice(7).build();
    lista.add(step1);
    lista.add(step2);
    lista.add(step3);
    lista.add(step4);
    lista.add(step5);
    lista.add(step6);
    lista.add(step7);
    return lista;
  }

  private void creaForm() {

    richiestaPermessoPersonalizzato = new RichiestaPermessoPersonalizzato();

    boolean attivo = true;

    CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel =
        new CompoundPropertyModel<>(richiestaPermessoPersonalizzato);
    form =
        new Form("form", richiestaPermessoPersonalizzatoModel) {

          @Override
          protected void onError() {
            getFeedbackMessages();
            super.onError();
          }
        };

    form.addOrReplace(
        inserimentoDomandaPanel =
            new InserimentoDomandaPanel("inserimentoDomandaPanel", isResidente));
    form.addOrReplace(
        soggettiCoinvoltiPanel =
            new SoggettiCoinvoltiPanel(
                "soggettiCoinvoltiPanel", richiestaPermessoPersonalizzatoModel, attivo));
    form.addOrReplace(
        ubicazioneParcheggioPanel =
            new UbicazioneParcheggioPanel(
                "ubicazioneParcheggioPanel", richiestaPermessoPersonalizzatoModel, attivo));

    form.addOrReplace(
        dichiarazioniPermessiPersonalizzatiPanel =
            new DichiarazioniPermessiPersonalizzatiPanel(
                "dichiarazioniPermessiPersonalizzatiPanel", richiestaPermessoPersonalizzatoModel));
    form.addOrReplace(
        documentiAllegatiPermessiPersonalizzatiPanel =
            new DocumentiAllegatiPermessiPersonalizzatiPanel(
                "documentiAllegatiPermessiPersonalizzatiPanel",
                richiestaPermessoPersonalizzatoModel,
                0,
                attivo));
    form.addOrReplace(
        riepilogoPermessiPersonalizzatiPanel =
            new RiepilogoPermessiPersonalizzatiPanel(
                "riepilogoPermessiPersonalizzatiPanel", richiestaPermessoPersonalizzatoModel, 0));
    form.addOrReplace(
        finePermessiPersonalizzatiPanel =
            new FinePermessiPersonalizzatiPanel(
                "finePermessiPersonalizzatiPanel", richiestaPermessoPersonalizzatoModel));

    form.add(btnAvanti = creaBottoneAvanti());
    form.add(btnIndietro = creaBottoneIndietro());
    form.add(
        btnVaiAllaListaDeiPermessiPersonalizzati =
            creaBottoneVaiAllaListaDeiPermessiPersonalizzati());
    btnVaiAllaListaDeiPermessiPersonalizzati.setVisible(false);
    btnAnnulla = creaBottoneAnnulla();
    btnAnnulla.setVisible(false);
    form.addOrReplace(btnAnnulla);

    setStatePanelAndButton();

    addOrReplace(form);
  }

  private LaddaAjaxButton creaBottoneAvanti() {
    LaddaAjaxButton avanti =
        new LaddaAjaxButton("avanti", Type.Primary) {

          private static final long serialVersionUID = 6018267445510360466L;
          private boolean datiValidati;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            inserimentoDomandaPanel.hasErrorMessage();

            if (checkValidation()) {
              messagePanel.setVisible(false);
              listaMessaggi = new ArrayList<>();
              indice = indice + 1;
              stepPanel.setIndexStep(indice);

              setStatePanelAndButton();

              if (indice == 7) {
                inviaRichiesta();
              }

              target.add(form);
              target.add(soggettiCoinvoltiPanel);
              target.add(stepPanel);

            } else {
              messagePanel.setVisible(true);
            }

            target.add(messagePanel);

            getTargetPanel(target);
          }

          private void inviaRichiesta() {

            RichiestaPermessoPersonalizzatoConverter richiestaPermessoPersonalizzatoConverter =
                new RichiestaPermessoPersonalizzatoConverter(form.getModelObject());
            DomandaBody domandaBody = richiestaPermessoPersonalizzatoConverter.toDomandaBody();

            PostResult lista = null;
            try {

              /*
               * ObjectMapper mapper = new ObjectMapper(); try { String json =
               * mapper.writeValueAsString(domandaBody);
               * System.out.println("ResultingJSONstring = " + json); log.error("aaa:" +
               * json); } catch (JsonProcessingException e) { e.printStackTrace(); }
               */

              lista =
                  ServiceLocator.getInstance()
                      .getServiziPermessiPersonalizzati()
                      .putDomanda(domandaBody, isResidente);

              setOutputMarkupId(true);

            } catch (BusinessException | ApiException | IOException e) {
              //					throw new RestartResponseAtInterceptPageException(
              //							new ErroreServiziPage(" permessi personalizzati"));
              List<String> lm = messagePanel.getListaMessaggi();
              lm.clear();

              lm.add("Errore di sistema");

              messagePanel.setVisible(true);
              finePermessiPersonalizzatiPanel.setVisible(false);
              btnIndietro.setVisible(true);
              btnIndietro.setEnabled(true);
            }

            if (lista != null) {
              try {
                if (lista.getEsito().equalsIgnoreCase("OK")) {

                  IdDomandaProtocolloBody idDomandaProtocolloBody = new IdDomandaProtocolloBody();
                  idDomandaProtocolloBody.setAllegati(domandaBody.getAllegati());
                  idDomandaProtocolloBody.setCodiceFiscale(getUtente().getCodiceFiscaleOperatore());
                  idDomandaProtocolloBody.setCognome(getUtente().getCognome());
                  idDomandaProtocolloBody.setNome(getUtente().getNome());
                  idDomandaProtocolloBody.setProcedimento(domandaBody.getProcedimento());

                  Protocollazione protocollazione =
                      ServiceLocator.getInstance()
                          .getServiziPermessiPersonalizzati()
                          .putDomanda(
                              Integer.parseInt(lista.getIdDomanda()), idDomandaProtocolloBody);

                  form.getModelObject().setProtocollazione(protocollazione);
                }

              } catch (Exception e) {

                List<String> lm = messagePanel.getListaMessaggi();
                lm.clear();

                lm.add("Si &egrave; verificato un problema nella protocollazione");

                messagePanel.setVisible(true);
                finePermessiPersonalizzatiPanel.setVisible(false);
                btnIndietro.setVisible(true);
                btnIndietro.setEnabled(true);
              }
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            /*
             * List<String> lm = messagePanel.getListaMessaggi();
             *
             * lm.add("Errore di formattazione dei dati."); messagePanel.setVisible(true);
             * target.add(messagePanel); log.error("CP errore step 2 cambio indirizzo: " +
             * getFeedbackMessages());
             */
            // log.error("errore di validazione");
            getTargetPanel(target);
          }
        };

    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaPermessiPersonalizzatiPage.avanti",
                    RichiestaPermessiPersonalizzatiPage.this)));

    return avanti;
  }

  private AjaxRequestTarget getTargetPanel(AjaxRequestTarget target) {

    if (indice == 1) {
      target.add(inserimentoDomandaPanel);
    } else if (indice == 2) {
      target.add(soggettiCoinvoltiPanel.getDisabilePanel());
      target.add(soggettiCoinvoltiPanel.getTutoreGenitorePanel());
      target.add(soggettiCoinvoltiPanel.getAccompagnatorePanel());
    }

    return target;
  }

  private LaddaAjaxLink<Object> creaBottoneIndietro() {
    LaddaAjaxLink<Object> linkImg =
        new LaddaAjaxLink<Object>("indietro", Type.Primary) {

          private static final long serialVersionUID = -2716814672722651649L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            indice = indice - 1;
            stepPanel.setIndexStep(indice);

            setStatePanelAndButton();

            messagePanel.setVisible(false);

            // form.getModelObject()

            target.add(form);
            target.add(stepPanel);
            target.add(messagePanel);
            //				target.prependJavaScript("window.addEventListener('load', function () {\r\n"
            //						+ "  alert(\"It's loaded!\")\r\n"
            //						+ "})");
            // target.appendJavaScript("$(document).ready(function() {window.scrollTo(0,
            // 300);});");
          }
        };

    linkImg.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaPermessiPersonalizzatiPage.indietro",
                    RichiestaPermessiPersonalizzatiPage.this)));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }

  private LaddaAjaxLink<Object> creaBottoneVaiAllaListaDeiPermessiPersonalizzati() {
    LaddaAjaxLink<Object> btnVaiAllaListaDeiPermessiPersonalizzati =
        new LaddaAjaxLink<Object>("btnVaiAllaListaDeiPermessiPersonalizzati", Type.Primary) {

          private static final long serialVersionUID = -5269371962796000761L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaPermessiPersonalizzatiPage.this);
            setResponsePage(new PermessiPersonalizzatiPage());
          }
        };
    btnVaiAllaListaDeiPermessiPersonalizzati.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaPermessiPersonalizzatiPage.btnVaiAllaListaDeiPermessiPersonalizzati",
                    RichiestaPermessiPersonalizzatiPage.this)));
    return btnVaiAllaListaDeiPermessiPersonalizzati;
  }

  private LaddaAjaxLink<Object> creaBottoneAnnulla() {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = -5269371962796000761L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaPermessiPersonalizzatiPage.this);
            setResponsePage(new RichiestaPermessiPersonalizzatiPage());
          }
        };
    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaPermessiPersonalizzatiPage.annulla",
                    RichiestaPermessiPersonalizzatiPage.this)));
    return annulla;
  }

  private void setStatePanelAndButton() {

    switch (indice) {
      case 1:
        inserimentoDomandaPanel.setVisible(true);
        soggettiCoinvoltiPanel.setVisible(false);
        ubicazioneParcheggioPanel.setVisible(false);

        dichiarazioniPermessiPersonalizzatiPanel.setVisible(false);
        documentiAllegatiPermessiPersonalizzatiPanel.setVisible(false);
        riepilogoPermessiPersonalizzatiPanel.setVisible(false);
        finePermessiPersonalizzatiPanel.setVisible(false);

        btnAvanti.setEnabled(true);
        btnAvanti.setVisible(true);
        btnIndietro.setEnabled(false);
        // btnVaiAllaListaDeiPermessiPersonalizzati.setVisible(true);
        break;
      case 2:
        inserimentoDomandaPanel.setVisible(false);
        soggettiCoinvoltiPanel.setVisible(true);
        ubicazioneParcheggioPanel.setVisible(false);

        dichiarazioniPermessiPersonalizzatiPanel.setVisible(false);
        documentiAllegatiPermessiPersonalizzatiPanel.setVisible(false);
        riepilogoPermessiPersonalizzatiPanel.setVisible(false);
        finePermessiPersonalizzatiPanel.setVisible(false);

        btnAvanti.setEnabled(true);
        btnAvanti.setVisible(true);
        btnIndietro.setEnabled(true);
        // btnVaiAllaListaDeiPermessiPersonalizzati.setVisible(true);
        break;

      case 3:
        inserimentoDomandaPanel.setVisible(false);
        soggettiCoinvoltiPanel.setVisible(false);
        ubicazioneParcheggioPanel.setVisible(true);

        dichiarazioniPermessiPersonalizzatiPanel.setVisible(false);
        documentiAllegatiPermessiPersonalizzatiPanel.setVisible(false);
        riepilogoPermessiPersonalizzatiPanel.setVisible(false);
        finePermessiPersonalizzatiPanel.setVisible(false);

        btnAvanti.setEnabled(true);
        btnAvanti.setVisible(true);
        btnIndietro.setEnabled(true);
        // btnVaiAllaListaDeiPermessiPersonalizzati.setVisible(true);
        break;

      case 4:
        inserimentoDomandaPanel.setVisible(false);
        soggettiCoinvoltiPanel.setVisible(false);
        ubicazioneParcheggioPanel.setVisible(false);

        dichiarazioniPermessiPersonalizzatiPanel.setVisible(true);
        documentiAllegatiPermessiPersonalizzatiPanel.setVisible(false);
        riepilogoPermessiPersonalizzatiPanel.setVisible(false);
        finePermessiPersonalizzatiPanel.setVisible(false);

        btnAvanti.setEnabled(true);
        btnAvanti.setVisible(true);
        btnIndietro.setEnabled(true);
        // btnVaiAllaListaDeiPermessiPersonalizzati.setVisible(true);
        break;
      case 5:
        inserimentoDomandaPanel.setVisible(false);
        soggettiCoinvoltiPanel.setVisible(false);
        ubicazioneParcheggioPanel.setVisible(false);

        dichiarazioniPermessiPersonalizzatiPanel.setVisible(false);
        documentiAllegatiPermessiPersonalizzatiPanel.setVisible(true);
        riepilogoPermessiPersonalizzatiPanel.setVisible(false);
        finePermessiPersonalizzatiPanel.setVisible(false);

        btnAvanti.setEnabled(true);
        btnAvanti.setVisible(true);
        btnIndietro.setEnabled(true);
        // btnVaiAllaListaDeiPermessiPersonalizzati.setVisible(true);
        btnAvanti.setLabel(
            Model.of(
                Application.get()
                    .getResourceSettings()
                    .getLocalizer()
                    .getString(
                        "RichiestaPermessiPersonalizzatiPage.avanti",
                        RichiestaPermessiPersonalizzatiPage.this)));

        break;
      case 6:
        inserimentoDomandaPanel.setVisible(false);
        soggettiCoinvoltiPanel.setVisible(false);
        ubicazioneParcheggioPanel.setVisible(false);

        dichiarazioniPermessiPersonalizzatiPanel.setVisible(false);
        documentiAllegatiPermessiPersonalizzatiPanel.setVisible(false);
        riepilogoPermessiPersonalizzatiPanel.setVisible(true);
        finePermessiPersonalizzatiPanel.setVisible(false);

        btnAvanti.setEnabled(true);
        btnAvanti.setVisible(true);
        btnAvanti.setLabel(
            Model.of(
                Application.get()
                    .getResourceSettings()
                    .getLocalizer()
                    .getString(
                        "RichiestaPermessiPersonalizzatiPage.invia",
                        RichiestaPermessiPersonalizzatiPage.this)));
        btnIndietro.setEnabled(true);
        // btnVaiAllaListaDeiPermessiPersonalizzati.setVisible(false);
        break;
      case 7:
        inserimentoDomandaPanel.setVisible(false);
        soggettiCoinvoltiPanel.setVisible(false);
        ubicazioneParcheggioPanel.setVisible(false);

        dichiarazioniPermessiPersonalizzatiPanel.setVisible(false);
        documentiAllegatiPermessiPersonalizzatiPanel.setVisible(false);
        riepilogoPermessiPersonalizzatiPanel.setVisible(false);
        finePermessiPersonalizzatiPanel.setVisible(true);

        btnAvanti.setVisible(false);

        btnIndietro.setVisible(false);
        btnAnnulla.setVisible(false);
        btnVaiAllaListaDeiPermessiPersonalizzati.setVisible(true);
        break;

      default:
        break;
    }
  }

  private boolean checkValidation() {
    RichiestaPermessoPersonalizzato rpp = form.getModelObject();

    List<String> lm = messagePanel.getListaMessaggi();
    lm.clear();

    boolean isValid = true;

    switch (indice) {
      case 1:
        if (rpp.getTipoDomanda() == null) {
          lm.add("Selezionare il tipo domanda.");
          isValid = false;
        } else {
          rpp.setSoggettiCoinvolti(new SoggettiCoinvolti());
          rpp.setGeoserverUbicazioneParcheggio(null);
        }
        break;
      case 2:
        switch (EnumTipoDomandaPermessoPersonalizzato.getById(rpp.getTipoDomanda().getCodice())) {
          case DISABILE_GUIDATORE_RESIDENZA:
          case DISABILE_GUIDATORE_LAVORO:
            isValid = validazioneFormDisabile(rpp, lm, isValid);
            break;
          case DISABILE_ACCOMPAGNATO:
            isValid = validazioneFormDisabile(rpp, lm, isValid);
            isValid = validazioneFormAccompagnatore(rpp, lm, isValid);
            break;
          case DISABILE_MINORE:
          case DISABILE_TUTORE:
            isValid = validazioneFormDisabile(rpp, lm, isValid);
            isValid = validazioneFormTutore(rpp, lm, isValid);

            if (!rpp.getSoggettiCoinvolti().isTutoreCoincideConAccompagnatore())
              isValid = validazioneFormAccompagnatore(rpp, lm, isValid);

            break;
          case UNKNOWN:
            log.error("SoggettiCoinvoltiPanel: E' stata selezionata un'opzione non prevista");
        }

        if (rpp.getTipoDomanda() == null) {
          lm.add("Selezionare il tipo domanda.");
          isValid = false;
        }

        break;

      case 3:
        if (rpp.getGeoserverUbicazioneParcheggio() == null
            || rpp.getGeoserverUbicazioneParcheggio().getCODICE_INDIRIZZO() == null) {
          lm.add("Selezionare l'ubicazione del parcheggio.");
          isValid = false;
        }

        break;

      case 4:
        boolean dichiatazioneAlternativaPresente = false;

        int count = 0;
        for (Iterator<DichiarazionePermessiPersonalizzati> iterator =
                rpp.getDichiarazioni().getListaDichiarazioni().iterator();
            iterator.hasNext(); ) {
          DichiarazionePermessiPersonalizzati dichiarazionePermessiPersonalizzati = iterator.next();

          if (dichiarazionePermessiPersonalizzati.getDichiarazioneAlternativa() != null
              && dichiarazionePermessiPersonalizzati
                  .getDichiarazioneAlternativa()
                  .equalsIgnoreCase("1")
              && (dichiarazionePermessiPersonalizzati.getValoreDichiarazione() == null
                  || dichiarazionePermessiPersonalizzati.getValoreDichiarazione().equals(true))) {
            count++;

            dichiatazioneAlternativaPresente = true;
          }
        }

        if (count > 1) {
          lm.add(
              "Sono state selezionate entrambe le dichiarazioni alternative: selezionarne una sola.");
          isValid = false;
        }

        boolean tutteDichiarazioniObbligatoriePresenti = true;

        boolean codiceFiscaleAutoveicoloProprioODelFamiliarePresente = true;
        boolean codiceFiscaleAutoveicoloProprioODelFamiliareValido = true;
        boolean cognomeProprietarioAutoVeicoloProprioODelFamiliarePresente = true;
        boolean nomeProprietarioAutoVeicoloProprioODelFamiliarePresente = true;
        boolean targaAutoVeicoloProprioODelFamiliarePresente = true;

        for (Iterator<DichiarazionePermessiPersonalizzati> iterator =
                rpp.getDichiarazioni().getListaDichiarazioni().iterator();
            iterator.hasNext(); ) {
          DichiarazionePermessiPersonalizzati dichiarazionePermessiPersonalizzati = iterator.next();

          if (dichiarazionePermessiPersonalizzati.getCodiceDichiarazione().equalsIgnoreCase("VCL")
              && dichiarazionePermessiPersonalizzati.getValoreDichiarazione() != null
              && dichiarazionePermessiPersonalizzati.getValoreDichiarazione() == true) {

            if (dichiarazionePermessiPersonalizzati.getVeicolo() == null
                || dichiarazionePermessiPersonalizzati
                        .getVeicolo()
                        .getCodiceFiscaleAutoVeicoloProprioODelFamiliare()
                    == null
                || dichiarazionePermessiPersonalizzati
                    .getVeicolo()
                    .getCodiceFiscaleAutoVeicoloProprioODelFamiliare()
                    .equalsIgnoreCase(""))
              codiceFiscaleAutoveicoloProprioODelFamiliarePresente = false;
            else if (!ValidatorUtil.codiceFiscaleValido(
                dichiarazionePermessiPersonalizzati
                    .getVeicolo()
                    .getCodiceFiscaleAutoVeicoloProprioODelFamiliare())) {
              codiceFiscaleAutoveicoloProprioODelFamiliareValido = false;
            }

            if (dichiarazionePermessiPersonalizzati.getVeicolo() == null
                || dichiarazionePermessiPersonalizzati
                        .getVeicolo()
                        .getCognomeProprietarioAutoVeicoloProprioODelFamiliare()
                    == null
                || dichiarazionePermessiPersonalizzati
                    .getVeicolo()
                    .getCognomeProprietarioAutoVeicoloProprioODelFamiliare()
                    .equalsIgnoreCase(""))
              cognomeProprietarioAutoVeicoloProprioODelFamiliarePresente = false;

            if (dichiarazionePermessiPersonalizzati.getVeicolo() == null
                || dichiarazionePermessiPersonalizzati
                        .getVeicolo()
                        .getNomeProprietarioAutoVeicoloProprioODelFamiliare()
                    == null
                || dichiarazionePermessiPersonalizzati
                    .getVeicolo()
                    .getNomeProprietarioAutoVeicoloProprioODelFamiliare()
                    .equalsIgnoreCase(""))
              nomeProprietarioAutoVeicoloProprioODelFamiliarePresente = false;

            if (dichiarazionePermessiPersonalizzati.getVeicolo() == null
                || dichiarazionePermessiPersonalizzati
                        .getVeicolo()
                        .getTargaAutoVeicoloProprioODelFamiliare()
                    == null
                || dichiarazionePermessiPersonalizzati
                    .getVeicolo()
                    .getTargaAutoVeicoloProprioODelFamiliare()
                    .equalsIgnoreCase("")) targaAutoVeicoloProprioODelFamiliarePresente = false;
          }

          if (dichiarazionePermessiPersonalizzati.getDichiarazioneAlternativa() == null
              // &&
              // !dichiarazionePermessiPersonalizzati.getDichiarazioneAlternativa().equalsIgnoreCase("1")
              // && (dichiarazionePermessiPersonalizzati.getValoreDichiarazione() == null
              && dichiarazionePermessiPersonalizzati.getValoreDichiarazione().equals(false)) {
            tutteDichiarazioniObbligatoriePresenti = false;
            break;
          }
        }

        if (!dichiatazioneAlternativaPresente || !tutteDichiarazioniObbligatoriePresenti) {
          lm.add("Selezionare le dichiarazioni.");
          isValid = false;
        }

        if (!codiceFiscaleAutoveicoloProprioODelFamiliarePresente) {
          lm.add("Inserire il codice fiscale proprietario.");
          isValid = false;
        } else if (!codiceFiscaleAutoveicoloProprioODelFamiliareValido) {
          lm.add("Il codice fiscale proprietario non &egrave; valido.");
          isValid = false;
        }

        if (!cognomeProprietarioAutoVeicoloProprioODelFamiliarePresente) {
          lm.add("Inserire il cognome proprietario.");
          isValid = false;
        }

        if (!nomeProprietarioAutoVeicoloProprioODelFamiliarePresente) {
          lm.add("Inserire il nome proprietario.");
          isValid = false;
        }

        if (!targaAutoVeicoloProprioODelFamiliarePresente) {
          lm.add("Inserire la targa.");
          isValid = false;
        }

        break;
      case 5:
        boolean tuttiAllegatiPresenti = true;
        for (Iterator<AllegatoPermessiPersonalizzati> iterator =
                rpp.getDocumentiAllegati().getListaAllegatoPermessiPersonalizzati().iterator();
            iterator.hasNext(); ) {
          AllegatoPermessiPersonalizzati allegatoPermessiPersonalizzati = iterator.next();

          if (allegatoPermessiPersonalizzati.getByteFile() == null) {
            tuttiAllegatiPresenti = false;
            break;
          }
        }

        if (!tuttiAllegatiPresenti) {
          lm.add("Non &egrave; possibile procedere. E' necessario caricare un file per ogni voce.");
          isValid = false;
        }

        break;
      case 6:
        break;
      case 7:
        break;

      default:
        break;
    }

    return isValid;
  }

  private boolean validazioneFormDisabile(
      RichiestaPermessoPersonalizzato rpp, List<String> lm, boolean isValid) {

    if (rpp.getSoggettiCoinvolti().getDisabile().getCodiceFiscaleDisabile() == null) {
      lm.add("Inserire i dati del disabile.");
      isValid = false;
    } else {

      if (rpp.getSoggettiCoinvolti().getDisabile().getLuogoNascitaDisabile() == null) {

        lm.add("Inserire il luogo di nascita del disabile.");
        isValid = false;
      }

      if (rpp.getSoggettiCoinvolti().getDisabile().getProvinciaNascitaDisabile() == null) {

        lm.add("Inserire la provincia di nascita del disabile.");
        isValid = false;
      }

      if (rpp.getSoggettiCoinvolti().getDisabile().getIndirizzoResidenzaDisabile() == null) {

        lm.add("Inserire l'indirizzo di residenza del disabile.");
        isValid = false;
      }

      if (rpp.getSoggettiCoinvolti().getDisabile().getCapResidenzaDisabile() == null) {

        lm.add("Inserire il CAP di residenza del disabile.");
        isValid = false;
      }

      if (rpp.getSoggettiCoinvolti().getDisabile().getCittaResidenzaDisabile() == null) {

        lm.add("Inserire la citt&agrave; di residenza del disabile.");
        isValid = false;
      }

      if (rpp.getSoggettiCoinvolti().getDisabile().getProvinciaResidenzaDisabile() == null) {

        lm.add("Inserire la provincia di residenza del disabile.");
        isValid = false;
      }

      if (EnumTipoDomandaPermessoPersonalizzato.getById(rpp.getTipoDomanda().getCodice())
              .equals(EnumTipoDomandaPermessoPersonalizzato.DISABILE_GUIDATORE_RESIDENZA)
          || EnumTipoDomandaPermessoPersonalizzato.getById(rpp.getTipoDomanda().getCodice())
              .equals(EnumTipoDomandaPermessoPersonalizzato.DISABILE_GUIDATORE_LAVORO)) {

        isValid =
            validazioneDatiPatente(
                lm,
                isValid,
                "disabile",
                rpp.getSoggettiCoinvolti().getDisabile().getTipoPatenteDisabile(),
                rpp.getSoggettiCoinvolti().getDisabile().getNumeroPantenteDisabile(),
                rpp.getSoggettiCoinvolti().getDisabile().getDataRilascioPatenteDisabile(),
                rpp.getSoggettiCoinvolti().getDisabile().getDataScadenzaPatenteDisabile());
      }

      if (rpp.getSoggettiCoinvolti().getDisabile().isResidenteInGenova()) {
        if (soggettiCoinvoltiPanel
            .getDisabilePanel()
            .getComboPermessoDisabile()
            .getChoices()
            .isEmpty()) {
          lm.add("Non risultano CUDE validi presso genova parcheggi.");
          isValid = false;
        } else if (rpp.getSoggettiCoinvolti().getDisabile().getDatiPermessoDisabile() == null) {
          lm.add("Selezionare il permesso del disabile.");
          isValid = false;
        }
      } else {
        if (rpp.getSoggettiCoinvolti().getDisabile().getNumeroContrassegnoH() == null) {
          lm.add("Inserire il numero del contrassegno CUDE.");
          isValid = false;
        }
        if (rpp.getSoggettiCoinvolti().getDisabile().getDataRilascioContrassegnoH() == null) {
          lm.add("Inserire la data di rilascio del contrassegno CUDE");
          isValid = false;
        } else if (rpp.getSoggettiCoinvolti()
            .getDisabile()
            .getDataRilascioContrassegnoH()
            .isAfter(LocalDate.now())) {
          lm.add(
              "La data di rilascio del contrassegno CUDE del disabile &egrave; successiva a quella odierna.");
          isValid = false;
        } else if (rpp.getSoggettiCoinvolti().getDisabile().getDataScadenzaContrassegnoH()
            == null) {
          lm.add("Inserire la data di scadenza del contrassegno CUDE");
          isValid = false;
        } else if (rpp.getSoggettiCoinvolti()
            .getDisabile()
            .getDataScadenzaContrassegnoH()
            .isBefore(LocalDate.now())) {
          lm.add(
              "La data di scadenza del contrassegno CUDE deve essere successiva a quella odierna");
          isValid = false;
        } else if (rpp.getSoggettiCoinvolti()
            .getDisabile()
            .getDataScadenzaContrassegnoH()
            .isBefore(rpp.getSoggettiCoinvolti().getDisabile().getDataRilascioContrassegnoH())) {
          lm.add(
              "La data di scadenza del contrassegno CUDE deve essere successiva a quella a quella di rilascio.");
          isValid = false;
        }
      }

      /*
       * if (soggettiCoinvoltiPanel.getDisabilePanel().getComboPermessoDisabile().
       * getChoices().isEmpty()) {
       *
       * if (rpp.getSoggettiCoinvolti().getDisabile().getNumeroContrassegnoH() ==
       * null) { lm.add("Inserire il numero del contrassegno CUDE."); isValid = false;
       * } if (rpp.getSoggettiCoinvolti().getDisabile().getDataRilascioContrassegnoH()
       * == null) { lm.add("Inserire la data di rilascio del contrassegno CUDE");
       * isValid = false; } else if
       * (rpp.getSoggettiCoinvolti().getDisabile().getDataRilascioContrassegnoH()
       * .isAfter(LocalDate.now())) { lm.
       * add("La data di rilascio del contrassegno CUDE del disabile &egrave; successiva a quella odierna."
       * ); isValid = false; } else if
       * (rpp.getSoggettiCoinvolti().getDisabile().getDataScadenzaContrassegnoH() ==
       * null) { lm.add("Inserire la data di scadenza del contrassegno CUDE"); isValid
       * = false; } else if
       * (rpp.getSoggettiCoinvolti().getDisabile().getDataScadenzaContrassegnoH()
       * .isBefore(LocalDate.now())) { lm.
       * add("La data di scadenza del contrassegno CUDE deve essere successiva a quella odierna"
       * ); isValid = false; } else if
       * (rpp.getSoggettiCoinvolti().getDisabile().getDataScadenzaContrassegnoH()
       * .isBefore(rpp.getSoggettiCoinvolti().getDisabile().
       * getDataRilascioContrassegnoH())) { lm.
       * add("La data di scadenza del contrassegno CUDE deve essere successiva a quella a quella di rilascio."
       * ); isValid = false; }
       *
       * } else if (rpp.getSoggettiCoinvolti().getDisabile().getDatiPermessoDisabile()
       * == null) { lm.add("Selezionare il permesso del disabile."); isValid = false;
       * }
       */

    }
    return isValid;
  }

  private boolean validazioneDatiPatente(
      List<String> lm,
      boolean isValid,
      String soggetto,
      String tipoPatente,
      String numeroPatente,
      LocalDate dataRilascioPatente,
      LocalDate dataScadenzaPatente) {
    if (tipoPatente == null) {
      lm.add("Inserire il tipo della patente del " + soggetto + ".");
      isValid = false;
    }

    if (numeroPatente == null) {
      lm.add("Inserire il numero della patente del " + soggetto + ".");
      isValid = false;
    }

    if (dataRilascioPatente == null) {
      lm.add("Inserire la data di rilascio della patente del " + soggetto + ".");
      isValid = false;
    } else if (dataRilascioPatente.isAfter(LocalDate.now())) {
      lm.add(
          "La data di rilascio della patente del "
              + soggetto
              + " &egrave; successiva a quella odierna.");
      isValid = false;
    }

    if (dataScadenzaPatente == null) {
      lm.add("Inserire la data di scadenza patente del " + soggetto + ".");
      isValid = false;
    } else if (dataScadenzaPatente.isBefore(LocalDate.now())) {
      lm.add(
          "La data di scadenza della patente del "
              + soggetto
              + " &egrave; antecendente a quella odierna.");
      isValid = false;
    }
    return isValid;
  }

  private boolean validazioneFormTutore(
      RichiestaPermessoPersonalizzato rpp, List<String> lm, boolean isValid) {

    if (rpp.getSoggettiCoinvolti().getTutore().getLuogoNascitaTutore() == null) {

      lm.add("Inserire il luogo di nascita del tutore.");
      isValid = false;
    }

    if (rpp.getSoggettiCoinvolti().getTutore().getProvinciaNascitaTutore() == null) {

      lm.add("Inserire la provincia di nascita del tutore.");
      isValid = false;
    }

    if (rpp.getSoggettiCoinvolti().getTutore().getIndirizzoResidenzaTutore() == null) {

      lm.add("Inserire l'indirizzo di residenza del tutore.");
      isValid = false;
    }

    if (rpp.getSoggettiCoinvolti().getTutore().getCapResidenzaTutore() == null) {

      lm.add("Inserire il CAP di residenza del tutore.");
      isValid = false;
    }

    if (rpp.getSoggettiCoinvolti().getTutore().getCittaResidenzaTutore() == null) {

      lm.add("Inserire la citt&agrave; di residenza del tutore.");
      isValid = false;
    }

    if (rpp.getSoggettiCoinvolti().getTutore().getProvinciaResidenzaTutore() == null) {

      lm.add("Inserire la provincia di residenza del tutore.");
      isValid = false;
    }

    if (rpp.getSoggettiCoinvolti().isTutoreCoincideConAccompagnatore()) {

      if (rpp.getSoggettiCoinvolti().getTutore().getTipoPatenteTutore() == null) {
        lm.add("Inserire il tipo della patente del tutore.");
        isValid = false;
      }

      if (rpp.getSoggettiCoinvolti().getTutore().getNumeroPantenteTutore() == null) {
        lm.add("Inserire il numero della patente del tutore.");
        isValid = false;
      }

      if (rpp.getSoggettiCoinvolti().getTutore().getDataRilascioPatenteTutore() == null) {
        lm.add("Inserire la data di rilascio della patente del tutore.");
        isValid = false;
      }

      if (rpp.getSoggettiCoinvolti().getTutore().getDataScadenzaPatenteTutore() == null) {
        lm.add("Inserire la data di scadenza del tutore.");
        isValid = false;
      }
    }

    return isValid;
  }

  private boolean validazioneFormAccompagnatore(
      RichiestaPermessoPersonalizzato rpp, List<String> lm, boolean isValid) {

    if (rpp.getSoggettiCoinvolti().getAccompagnatore().getCodiceFiscaleAccompagnatore() == null) {
      lm.add("Inserire i dati dell'accompagnatore.");
      isValid = false;
    } else {

      boolean isResidente = getUtente().isResidente();
      if (rpp.getSoggettiCoinvolti().getAccompagnatore().isFaParteDelNucleoFamigliare()
          && rpp.getSoggettiCoinvolti().getAccompagnatore().getComponenteNucleoFamigliare()
              == null) {
        lm.add("Selezionare il componente del nucleo familiare.");
        isValid = false;
      }

      if (EnumTipoDomandaPermessoPersonalizzato.getById(rpp.getTipoDomanda().getCodice())
              .equals(EnumTipoDomandaPermessoPersonalizzato.DISABILE_GUIDATORE_RESIDENZA)
          || EnumTipoDomandaPermessoPersonalizzato.getById(rpp.getTipoDomanda().getCodice())
              .equals(EnumTipoDomandaPermessoPersonalizzato.DISABILE_GUIDATORE_LAVORO)) {

        if (isResidente
            && !rpp.getSoggettiCoinvolti().getAccompagnatore().isFaParteDelNucleoFamigliare()
            && rpp.getSoggettiCoinvolti()
                    .getAccompagnatore()
                    .getCodiceFiscalePerRicercaAccompagnatore()
                == null) {
          lm.add("Inserire il codice fiscale dell'accompagnatore.");
          isValid = false;
        }

        if (isResidente
            && !rpp.getSoggettiCoinvolti().getAccompagnatore().isFaParteDelNucleoFamigliare()
            && rpp.getSoggettiCoinvolti()
                    .getAccompagnatore()
                    .getCartaIdentitaPerRicercaAccompagnatore()
                == null) {
          lm.add("Inserire la carta di identit&agrave; dell'accompagnatore.");
          isValid = false;
        }
      }
      if (rpp.getSoggettiCoinvolti().getAccompagnatore().getCodiceFiscaleAccompagnatore() != null
          && !rpp.getSoggettiCoinvolti()
              .getAccompagnatore()
              .getCodiceFiscaleAccompagnatore()
              .equalsIgnoreCase("")) {

        isValid =
            validazioneDatiPatente(
                lm,
                isValid,
                "accompagnatore",
                rpp.getSoggettiCoinvolti().getAccompagnatore().getTipoPatenteAccompagnatore(),
                rpp.getSoggettiCoinvolti().getAccompagnatore().getNumeroPantenteAccompagnatore(),
                rpp.getSoggettiCoinvolti()
                    .getAccompagnatore()
                    .getDataRilascioPatenteAccompagnatore(),
                rpp.getSoggettiCoinvolti()
                    .getAccompagnatore()
                    .getDataScadenzaPatenteAccompagnatore());
        /*
         * if
         * (rpp.getSoggettiCoinvolti().getAccompagnatore().getTipoPatenteAccompagnatore(
         * ) == null) { lm.add("Inserire il tipo della patente dell'accompagnatore.");
         * isValid = false; }
         *
         * if (rpp.getSoggettiCoinvolti().getAccompagnatore().
         * getNumeroPantenteAccompagnatore() == null) {
         * lm.add("Inserire il numero della patente dell'accomapagnatore."); isValid =
         * false; }
         *
         * if (rpp.getSoggettiCoinvolti().getAccompagnatore().
         * getDataRilascioPatenteAccompagnatore() == null) {
         * lm.add("Inserire la data di rilascio della patente dell'accompagnatore.");
         * isValid = false; }
         *
         * if (rpp.getSoggettiCoinvolti().getAccompagnatore().
         * getDataScadenzaPatenteAccompagnatore() == null) {
         * lm.add("Inserire la data di scadenza dell'accompagnatore."); isValid = false;
         * }
         */
      }
    }

    return isValid;
  }

  /*
   * @Override public void renderHead(IHeaderResponse response) {
   *
   * final ApplicationSettings settings = ApplicationSettings.get();
   *
   * // Include Wicket's provided jQuery reference //
   * response.render(JavaScriptHeaderItem //
   * .forReference(Application.get().getJavaScriptLibrarySettings().
   * getJQueryReference()));
   *
   * if (settings.isIncludeMouseWheel()) {
   * response.render(JavaScriptHeaderItem.forReference(settings.
   * getMouseWheelReference())); }
   *
   * if (settings.isIncludeJavascript()) {
   * response.render(JavaScriptHeaderItem.forReference(settings.
   * getJavaScriptReference())); }
   *
   * if (settings.isIncludeCss()) {
   * response.render(CssHeaderItem.forReference(settings.getCssReference())); } }
   */
  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    response.render(JavaScriptHeaderItem.forReference(Select2Reference.instance()));
  }
}
