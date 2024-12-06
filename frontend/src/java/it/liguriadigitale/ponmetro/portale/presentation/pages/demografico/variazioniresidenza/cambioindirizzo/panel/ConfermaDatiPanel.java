package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.IndividuiCollegati;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.IndividuiCollegatiImmigrazione;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniResidenza;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.VariazioniDiResidenzaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.CambioIndirizzoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.form.ConfermaDatiForm;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.richiestaresidenza.RichiestaResidenzaPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PutCambioStatoPraticaResponseGenericResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

public class ConfermaDatiPanel extends BasePanel {

  private static final long serialVersionUID = 2911237082877800827L;

  private ConfermaDatiForm form = null;

  private Integer index;

  private VariazioniResidenza variazione;

  private FeedbackPanel feedbackPanel;

  public ConfermaDatiPanel(String id, Integer index, VariazioniResidenza variazioniResidenza) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = index;
    this.setVariazione(variazioniResidenza);

    fillDati(variazioniResidenza);
  }

  @Override
  public void fillDati(Object dati) {
    VariazioniResidenza variazioniResidenza = (VariazioniResidenza) dati;

    createFeedBackStep5Panel();

    form = new ConfermaDatiForm("form", variazioniResidenza);

    form.setOutputMarkupId(true);
    form.setMultiPart(true);

    form.addOrReplace(creaBottoneAvanti(variazioniResidenza));
    form.addOrReplace(creaBottoneSospendi(variazioniResidenza));

    addOrReplace(form);

    Label idPratica = new Label("idPratica", variazioniResidenza.getIdPratica());
    idPratica.setVisible(LabelFdCUtil.checkIfNotNull(variazioniResidenza.getIdPratica()));
    addOrReplace(idPratica);

    String tipoPraticaValue = "";
    if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
        && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getTipoVariazioneDiResidenza())) {
      tipoPraticaValue = variazioniResidenza.getTipoVariazioneDiResidenza().getDescrizione();
    } else {
      if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getDatiGenerali())) {
        tipoPraticaValue = variazioniResidenza.getDatiGenerali().getTipo();
      }
    }
    Label tipoPratica = new Label("tipoPratica", tipoPraticaValue);
    tipoPratica.setVisible(
        LabelFdCUtil.checkIfNotNull(variazioniResidenza)
            && LabelFdCUtil.checkIfNotNull(tipoPraticaValue));
    addOrReplace(tipoPratica);

    String indirizzo = "";

    if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)) {

      if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getDatiGenerali())) {
        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getViario())) {
          indirizzo = indirizzo.concat(variazioniResidenza.getViario()).concat(" ");
        }

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getDatiGenerali().getNumeroCivico())) {
          indirizzo =
              indirizzo.concat(variazioniResidenza.getDatiGenerali().getNumeroCivico()).concat(" ");
        }

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getDatiGenerali().getEsponente())) {
          indirizzo =
              indirizzo.concat(variazioniResidenza.getDatiGenerali().getEsponente()).concat(" ");
        }

        if (PageUtil.isStringValid(variazioniResidenza.getDatiGenerali().getInterno())) {
          indirizzo =
              indirizzo
                  .concat(getString("ConfermaDatiPanel.interno"))
                  .concat(" ")
                  .concat(variazioniResidenza.getDatiGenerali().getInterno())
                  .concat(" ");
        }

        if (PageUtil.isStringValid(variazioniResidenza.getDatiGenerali().getInternoEsponente())) {
          indirizzo =
              indirizzo
                  .concat(getString("ConfermaDatiPanel.internoEsponente"))
                  .concat(" ")
                  .concat(variazioniResidenza.getDatiGenerali().getInternoEsponente())
                  .concat(" ");
        }

        if (PageUtil.isStringValid(variazioniResidenza.getDatiGenerali().getScala())) {
          indirizzo =
              indirizzo
                  .concat(getString("ConfermaDatiPanel.scala"))
                  .concat(" ")
                  .concat(variazioniResidenza.getDatiGenerali().getScala())
                  .concat(" ");
        }

        if (PageUtil.isStringValid(variazioniResidenza.getDatiGenerali().getColore())) {
          indirizzo =
              indirizzo
                  .concat(getString("ConfermaDatiPanel.colore"))
                  .concat(" ")
                  .concat(variazioniResidenza.getDatiGenerali().getColore());
        }

      } else {

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getTipoVia())) {
          indirizzo = indirizzo.concat(variazioniResidenza.getTipoVia()).concat(" ");
        }

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getViario())) {
          indirizzo = indirizzo.concat(variazioniResidenza.getViario()).concat(" ");
        }

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getCivico())) {
          indirizzo = indirizzo.concat(String.valueOf(variazioniResidenza.getCivico())).concat(" ");
        }

        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getEsponente())) {
          indirizzo = indirizzo.concat(variazioniResidenza.getEsponente()).concat(" ");
        }

        if (PageUtil.isStringValid(variazioniResidenza.getInterno())) {
          indirizzo =
              indirizzo
                  .concat(getString("ConfermaDatiPanel.interno"))
                  .concat(" ")
                  .concat(String.valueOf(variazioniResidenza.getInterno()))
                  .concat(" ");
        }

        if (PageUtil.isStringValid(variazioniResidenza.getInternoEsponente())) {
          indirizzo =
              indirizzo
                  .concat(getString("ConfermaDatiPanel.internoEsponente"))
                  .concat(" ")
                  .concat(String.valueOf(variazioniResidenza.getInternoEsponente()))
                  .concat(" ");
        }

        if (PageUtil.isStringValid(variazioniResidenza.getScala())) {
          indirizzo =
              indirizzo
                  .concat(getString("ConfermaDatiPanel.scala"))
                  .concat(" ")
                  .concat(String.valueOf(variazioniResidenza.getScala()))
                  .concat(" ");
        }

        if (PageUtil.isStringValid(variazioniResidenza.getColore())) {
          indirizzo =
              indirizzo
                  .concat(getString("ConfermaDatiPanel.colore"))
                  .concat(" ")
                  .concat(String.valueOf(variazioniResidenza.getColore()));
        }
      }
    }
    Label nuovoIndirizzo = new Label("nuovoIndirizzo", indirizzo);
    nuovoIndirizzo.setVisible(PageUtil.isStringValid(indirizzo));
    addOrReplace(nuovoIndirizzo);

    LocalDateLabel dataRichiesta =
        new LocalDateLabel("dataRichiesta", variazioniResidenza.getDataDecorrenza());
    dataRichiesta.setVisible(LabelFdCUtil.checkIfNotNull(variazioniResidenza.getDataDecorrenza()));
    addOrReplace(dataRichiesta);

    List<IndividuiCollegati> listaPersone = new ArrayList<IndividuiCollegati>();
    if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)) {
      if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getListaIndividuiCollegati())) {
        listaPersone = variazioniResidenza.getListaIndividuiCollegati();
      } else if (LabelFdCUtil.checkIfNotNull(
          variazioniResidenza.getListaIndividuiCollegatiImmigrazione())) {
        for (IndividuiCollegatiImmigrazione immi :
            variazioniResidenza.getListaIndividuiCollegatiImmigrazione()) {
          IndividuiCollegati individuo = new IndividuiCollegati();
          individuo.setNominativo(immi.getNominativo().toUpperCase());
          listaPersone.add(individuo);
        }
      }
    }

    ListView<IndividuiCollegati> listaIndividui =
        new ListView<IndividuiCollegati>("listaIndividui", listaPersone) {

          private static final long serialVersionUID = -4619443193292752426L;

          @Override
          protected void populateItem(ListItem<IndividuiCollegati> item) {
            IndividuiCollegati individuo = item.getModelObject();

            Label nominativoIndividuo = new Label("nominativoIndividuo", individuo.getNominativo());
            nominativoIndividuo.setVisible(PageUtil.isStringValid(individuo.getNominativo()));
            item.addOrReplace(nominativoIndividuo);
          }
        };
    addOrReplace(listaIndividui);
  }

  private LaddaAjaxButton creaBottoneAvanti(VariazioniResidenza variazioniResidenza) {
    LaddaAjaxButton avanti =
        new LaddaAjaxButton("avanti", Type.Primary) {

          private static final long serialVersionUID = 6110881638082993903L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            index = index + 1;

            try {
              PutCambioStatoPraticaResponseGenericResponse inviaPratica =
                  ServiceLocator.getInstance()
                      .getServiziAnagrafici()
                      .inviaPratica(variazioniResidenza.getIdPratica());

              if (LabelFdCUtil.checkIfNotNull(inviaPratica)
                  && LabelFdCUtil.checkIfNotNull(inviaPratica.getStatus())
                  && inviaPratica.getStatus().equalsIgnoreCase("OK")) {

                if (LabelFdCUtil.checkIfNotNull(getVariazione())
                    && LabelFdCUtil.checkIfNotNull(
                        getVariazione().getTipoVariazioneDiResidenza())) {
                  if (getVariazione().getTipoVariazioneDiResidenza().getId() == 1) {
                    setResponsePage(
                        new CambioIndirizzoPage(
                            index, "Cambio indirizzo", variazioniResidenza, true));

                  } else {
                    setResponsePage(
                        new RichiestaResidenzaPage(
                            index, "Cambio indirizzo", variazioniResidenza, true));
                  }
                }

                if (LabelFdCUtil.checkIfNotNull(getVariazione())
                    && LabelFdCUtil.checkIfNotNull(getVariazione().getIdTipoPratica())) {
                  if (getVariazione().getIdTipoPratica().equalsIgnoreCase("1")) {
                    setResponsePage(
                        new RichiestaResidenzaPage(
                            index, "Richiesta residenza", variazioniResidenza, true));

                  } else if (getVariazione().getIdTipoPratica().equalsIgnoreCase("3")
                      || getVariazione().getIdTipoPratica().equalsIgnoreCase("4")) {
                    setResponsePage(
                        new CambioIndirizzoPage(
                            index, "Cambio indirizzo", variazioniResidenza, true));
                  }
                }

              } else {

                if (LabelFdCUtil.checkIfNotNull(getVariazione())
                    && LabelFdCUtil.checkIfNotNull(
                        getVariazione().getTipoVariazioneDiResidenza())) {
                  if (getVariazione().getTipoVariazioneDiResidenza().getId() == 1) {
                    setResponsePage(
                        new CambioIndirizzoPage(
                            index, "Cambio indirizzo", variazioniResidenza, false));

                  } else {
                    setResponsePage(
                        new RichiestaResidenzaPage(
                            index, "Richiesta residenza", variazioniResidenza, false));
                  }
                }

                if (LabelFdCUtil.checkIfNotNull(getVariazione())
                    && LabelFdCUtil.checkIfNotNull(getVariazione().getIdTipoPratica())) {
                  if (getVariazione().getIdTipoPratica().equalsIgnoreCase("1")) {
                    setResponsePage(
                        new RichiestaResidenzaPage(
                            index, "Richiesta residenza", variazioniResidenza, false));

                  } else if (getVariazione().getIdTipoPratica().equalsIgnoreCase("3")
                      || getVariazione().getIdTipoPratica().equalsIgnoreCase("4")) {
                    setResponsePage(
                        new CambioIndirizzoPage(
                            index, "Cambio indirizzo", variazioniResidenza, false));
                  }
                }
              }

            } catch (BusinessException | ApiException | IOException e) {
              log.error("CP errore post inserimento pratica " + e.getMessage());
            }

            target.add(ConfermaDatiPanel.this);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(ConfermaDatiPanel.this);

            log.error("CP errore step 5 cambio indirizzo");
          }
        };
    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("ConfermaDatiPanel.avanti", ConfermaDatiPanel.this)));

    avanti.setOutputMarkupId(true);

    return avanti;
  }

  private LaddaAjaxLink<Object> creaBottoneSospendi(VariazioniResidenza variazioniResidenza) {
    LaddaAjaxLink<Object> sospendi =
        new LaddaAjaxLink<Object>("sospendi", Type.Primary) {

          private static final long serialVersionUID = 4404957875507618975L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(ConfermaDatiPanel.this);

            setResponsePage(VariazioniDiResidenzaPage.class);
          }
        };

    sospendi.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("ConfermaDatiPanel.sospendi", ConfermaDatiPanel.this)));

    return sospendi;
  }

  public Integer getIndex() {
    return index;
  }

  public void setIndex(Integer index) {
    this.index = index;
  }

  public VariazioniResidenza getVariazione() {
    return variazione;
  }

  public void setVariazione(VariazioniResidenza variazione) {
    this.variazione = variazione;
  }

  public ConfermaDatiForm getForm() {
    return form;
  }

  public void setForm(ConfermaDatiForm form) {
    this.form = form;
  }

  public FeedbackPanel getFeedbackPanel() {
    return feedbackPanel;
  }

  public void setFeedbackPanel(FeedbackPanel feedbackPanel) {
    this.feedbackPanel = feedbackPanel;
  }

  protected FeedbackPanel createFeedBackStep5Panel() {

    NotificationPanel feedback =
        new NotificationPanel("feedback5") {

          private static final long serialVersionUID = 508260048470107083L;

          @Override
          protected boolean isCloseButtonVisible() {
            return false;
          }
        };
    feedback.setOutputMarkupId(true);
    this.add(feedback);
    return feedback;
  }
}
