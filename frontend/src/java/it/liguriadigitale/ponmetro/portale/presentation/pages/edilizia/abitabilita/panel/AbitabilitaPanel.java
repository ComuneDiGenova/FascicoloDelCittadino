package it.liguriadigitale.ponmetro.portale.presentation.pages.edilizia.abitabilita.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.edilizia.abitabilita.model.AbitabilitaResponse;
import it.liguriadigitale.ponmetro.edilizia.abitabilita.model.AllegatoBody;
import it.liguriadigitale.ponmetro.edilizia.abitabilita.model.AllegatoMetadata;
import it.liguriadigitale.ponmetro.edilizia.abitabilita.model.IndirizzoMetadata;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.edilizia.abitabilita.Abitabilita;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.ColoriDropDownChoise;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.BottoneAJAXDownloadWithError;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.pojo.FileDaScaricare;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCNumberField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.FdCMultiLineLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.edilizia.abitabilita.AbitabilitaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

public class AbitabilitaPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = 7839074536215070929L;

  private static final String ICON_ABITABILITA = "color-fc-secondary col-3 icon-rimborsi-imu";

  private WebMarkupContainer containerDecretiAbitabilita =
      new WebMarkupContainer("containerDecretiAbitabilita") {

        private static final long serialVersionUID = -1520355406872242915L;

        @Override
        protected void onBeforeRender() {
          super.onBeforeRender();

          ListView<AbitabilitaResponse> listView =
              new ListView<AbitabilitaResponse>("listview", getDecreto()) {

                private static final long serialVersionUID = -1949303234023054323L;

                @Override
                protected void populateItem(ListItem<AbitabilitaResponse> item) {
                  AbitabilitaResponse abitabilitaResponse = item.getModelObject();

                  item.addOrReplace(
                      new AmtCardLabel<>(
                          "unitaDocumentaria",
                          abitabilitaResponse.getUnitaDocumentaria(),
                          AbitabilitaPanel.this));

                  item.addOrReplace(
                      new AmtCardLabel<>(
                          "annoDecretoTrovato",
                          abitabilitaResponse.getAnnoDecreto(),
                          AbitabilitaPanel.this));

                  item.addOrReplace(
                      new AmtCardLabel<>(
                          "numeroDecreto",
                          abitabilitaResponse.getNumeroDecreto(),
                          AbitabilitaPanel.this));

                  ListView<AllegatoMetadata> listViewPDF =
                      new ListView<AllegatoMetadata>(
                          "listviewPDF", abitabilitaResponse.getAllegati()) {

                        private static final long serialVersionUID = 4019851551319808792L;

                        @Override
                        protected void populateItem(ListItem<AllegatoMetadata> item) {
                          @SuppressWarnings("rawtypes")
                          AllegatoMetadata allegatoMetadata = item.getModelObject();
                          BottoneAJAXDownloadWithError btnDownloadPDF =
                              creaDownloadPDF(
                                  allegatoMetadata, abitabilitaResponse.getUnitaDocumentaria());
                          item.addOrReplace(
                              new Label("nomeFile", Model.of(allegatoMetadata.getNome())));
                          item.addOrReplace(btnDownloadPDF);
                        }
                      };

                  item.addOrReplace(listViewPDF);

                  List<String> listaIndirizzi = new ArrayList<>();
                  for (IndirizzoMetadata allegatoMetadata : abitabilitaResponse.getIndirizzi()) {
                    listaIndirizzi.add(allegatoMetadata.getIndirizzo());
                  }

                  @SuppressWarnings("unchecked")
                  FdCMultiLineLabel fdCMultiLineLabel =
                      new FdCMultiLineLabel(
                          "listaIndirizzi", listaIndirizzi, AbitabilitaPanel.this);

                  item.add(fdCMultiLineLabel);

                  item.addOrReplace(
                      new AmtCardLabel<>(
                          "autocertificata",
                          abitabilitaResponse.getAutocertificata(),
                          AbitabilitaPanel.this));
                }
              };
          containerDecretiAbitabilita.addOrReplace(listView);

          setOutputMarkupId(true);
          setOutputMarkupPlaceholderTag(true);
          setVisible(true);
          form.addOrReplace(containerDecretiAbitabilita);
        }
      };

  private List<AbitabilitaResponse> decreto;

  public AbitabilitaPanel(String id, Abitabilita abitabilitaBody) {
    super(id, abitabilitaBody);

    // form.add(null) createFeedBackPanel();

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    List<AbitabilitaResponse> listaAbitabilita = new ArrayList<>();

    AbitabilitaResponse abitabilitaResponse = new AbitabilitaResponse();
    listaAbitabilita.add(abitabilitaResponse);
    setDecreto(listaAbitabilita);

    fillDati(abitabilitaBody);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    form.addOrReplace(new FdCTitoloPanel("titolo", getString("AbitabilitaPanel.titolo")));

    form.addOrReplace(
        new FdCTitoloPanel("titoloRicerca", getString("AbitabilitaPanel.titoloRicerca")));

    FdCTextField via =
        new FdCTextField(
            "via", new PropertyModel<String>(form.getModelObject(), "via"), AbitabilitaPanel.this);
    via.setRequired(true);
    form.addOrReplace(via);

    FdCNumberField civicoDa =
        new FdCNumberField(
            "civicoDa",
            new PropertyModel<Integer>(form.getModelObject(), "civicoDa"),
            AbitabilitaPanel.this);
    civicoDa.setRequired(true);
    form.addOrReplace(civicoDa);

    FdCNumberField civicoA =
        new FdCNumberField(
            "civicoA",
            new PropertyModel<Integer>(form.getModelObject(), "civicoA"),
            AbitabilitaPanel.this);
    civicoA.setRequired(true);
    form.addOrReplace(civicoA);

    FdCTextField letteraInterno =
        new FdCTextField(
            "letteraInterno",
            new PropertyModel<String>(form.getModelObject(), "letteraInterno"),
            AbitabilitaPanel.this);
    form.addOrReplace(letteraInterno);

    ColoriDropDownChoise colore =
        new ColoriDropDownChoise<>(
            "colore", new PropertyModel<String>(form.getModelObject(), "colore"));
    form.addOrReplace(colore);

    FdCNumberField numeroCartellinoDecreto =
        new FdCNumberField(
            "numeroCartellinoDecreto",
            new PropertyModel<Integer>(form.getModelObject(), "numeroCartellinoDecreto"),
            AbitabilitaPanel.this);
    form.addOrReplace(numeroCartellinoDecreto);

    FdCNumberField annoDecreto =
        new FdCNumberField(
            "annoDecreto",
            new PropertyModel<Integer>(form.getModelObject(), "annoDecreto"),
            AbitabilitaPanel.this);
    annoDecreto.add(StringValidator.exactLength(4));
    form.addOrReplace(annoDecreto);

    NotEmptyLabel numeroDecreto =
        new NotEmptyLabel("numeroDecreto", getDecreto().get(0).getNumeroDecreto());
    containerDecretiAbitabilita.addOrReplace(numeroDecreto);

    NotEmptyLabel annoDecretoTrovato =
        new NotEmptyLabel("annoDecretoTrovato", getDecreto().get(0).getAnnoDecreto());
    containerDecretiAbitabilita.addOrReplace(annoDecretoTrovato);

    ListView<IndirizzoMetadata> listView =
        new ListView<IndirizzoMetadata>("listview", getDecreto().get(0).getIndirizzi()) {

          private static final long serialVersionUID = -1949303234023054323L;

          @Override
          protected void populateItem(ListItem<IndirizzoMetadata> item) {
            IndirizzoMetadata indirizzoMetadata = item.getModelObject();

            item.addOrReplace(
                new AmtCardLabel<>(
                    "indirizzo", indirizzoMetadata.getIndirizzo(), AbitabilitaPanel.this));
          }
        };
    containerDecretiAbitabilita.add(listView);

    //		containerDecretiAbitabilita.addOrReplace(
    //				new AmtCardLabel<>("autocertificata", getDecreto().get(0).getAutocertificata(),
    // AbitabilitaPanel.this));

    containerDecretiAbitabilita.setOutputMarkupId(true);
    containerDecretiAbitabilita.setOutputMarkupPlaceholderTag(true);
    containerDecretiAbitabilita.setVisible(false);

    form.addOrReplace(containerDecretiAbitabilita);

    form.addOrReplace(creaBtnCerca((Abitabilita) form.getModelObject()));

    form.addOrReplace(creaBtnAnnulla());
  }

  private FdcAjaxButton creaBtnCerca(Abitabilita abitabilitaBody) {
    FdcAjaxButton cerca =
        new FdcAjaxButton("cerca") {

          private static final long serialVersionUID = 1042731796774104183L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            log.debug("CP click cerca = " + abitabilitaBody);

            // decreto = popolaAbitabilita(abitabilitaBody);
            List<AbitabilitaResponse> lista = popolaAbitabilita(abitabilitaBody);
            setDecreto(lista);

            containerDecretiAbitabilita.setVisible(true);
            if (LabelFdCUtil.checkIfNull(lista) || LabelFdCUtil.checkEmptyList(lista)) {
              error("Nessun certificato trovato");
            }
            target.add(containerDecretiAbitabilita, feedbackPanel);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(AbitabilitaPanel.this);

            log.error("CP errore abitabilita ricerca");
          }
        };
    cerca.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("AbitabilitaPanel.cerca", AbitabilitaPanel.this)));

    return cerca;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnAnnulla() {
    FdCButtonBootstrapAjaxLink<Object> annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {

          private static final long serialVersionUID = 5153099400561495577L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            setResponsePage(new AbitabilitaPage());
          }
        };

    annulla.setLabel(Model.of(getString("AbitabilitaPanel.annulla")));

    return annulla;
  }

  private List<AbitabilitaResponse> popolaAbitabilita(Abitabilita abitabilita) {
    try {
      return ServiceLocator.getInstance()
          .getServiziEdiliziaAbitabilita()
          .getDecretiAbitabilita(abitabilita);
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Certificati di Abitabilità"));
    }
  }

  public List<AbitabilitaResponse> getDecreto() {
    return decreto;
  }

  public void setDecreto(List<AbitabilitaResponse> decreto) {
    this.decreto = decreto;
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError creaDownloadPDF(
      AllegatoMetadata allegatoMetadata, int unitaDocumentaria) {

    BottoneAJAXDownloadWithError btnScaricaRicevuta =
        new BottoneAJAXDownloadWithError("btnScaricaPDF", AbitabilitaPanel.this) {

          private static final long serialVersionUID = -8998456801171600346L;

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {
            AllegatoBody response;
            try {

              response =
                  ServiceLocator.getInstance()
                      .getServiziEdiliziaAbitabilita()
                      .getdecretoIdUdAllegatoNomeFile(
                          unitaDocumentaria, allegatoMetadata.getNome());

              FileDaScaricare fileDaScaricare = new FileDaScaricare();

              fileDaScaricare.setFileBytes(response.getAllegatoFile().getFile());
              fileDaScaricare.setFileName(response.getMetadati().getNome());
              fileDaScaricare.setEsitoOK(true);
              return fileDaScaricare;
            } catch (ApiException | BusinessException | IOException e) {

              String prefisso = "GNC-000-Server was unable to process request. --->";
              String message = e.getMessage();
              FileDaScaricare fileDaScaricare = new FileDaScaricare();
              if (message.contains(prefisso)) {
                log.error("ERRORE API: " + e);
                fileDaScaricare.setMessaggioErrore(message.replace(prefisso, ""));
              } else {
                fileDaScaricare.setMessaggioErrore(message);
              }
              fileDaScaricare.setEsitoOK(false);
              return fileDaScaricare;
            }
          }
        };

    btnScaricaRicevuta.setVisibileDopoDownload(true);

    return btnScaricaRicevuta;
  }
}
