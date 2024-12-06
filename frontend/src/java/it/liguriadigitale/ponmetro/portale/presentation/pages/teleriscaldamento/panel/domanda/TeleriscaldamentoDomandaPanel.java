package it.liguriadigitale.ponmetro.portale.presentation.pages.teleriscaldamento.panel.domanda;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.teleriscaldamento.DatiDomandaTeleriscaldamento;
import it.liguriadigitale.ponmetro.portale.pojo.teleriscaldamento.TipoUtenzaEnum;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.teleriscaldamento.TeleriscaldamentoPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.RicercaDato;
import java.io.IOException;
import java.math.BigDecimal;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;

public class TeleriscaldamentoDomandaPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = 1253595682203769431L;

  private int index;

  private StepPanel stepPanel;

  private IseePanel iseePanel;

  private DatiRichiedentePanel datiRichiedentePanel;

  private DatiUtenzaPanel datiUtenzaPanel;

  private DatiContrattoPanel datiContrattoPanel;

  private ConsensiPanel consensiPanel;

  private WebMarkupContainer containerInfo;

  private EsitoPanel esitoPanel;

  private FdcAjaxButton avanti;
  private FdCButtonBootstrapAjaxLink<Object> indietro;
  private FdCButtonBootstrapAjaxLink<Object> annulla;

  public TeleriscaldamentoDomandaPanel(String id, DatiDomandaTeleriscaldamento datiDomanda) {
    super(id, datiDomanda);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = 1;

    fillDati(datiDomanda);
  }

  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    stepPanel =
        new StepPanel(
            "stepPanel",
            index,
            ServiceLocator.getInstance().getServiziTeleriscaldamento().getListaStep());
    form.addOrReplace(stepPanel);

    DatiDomandaTeleriscaldamento datiDomanda = (DatiDomandaTeleriscaldamento) dati;

    boolean isVisibleCampoDatiContratti = false;
    if (LabelFdCUtil.checkIfNotNull(datiDomanda)
        && LabelFdCUtil.checkIfNotNull(datiDomanda.getTelefonoAmministratore())
        && datiDomanda.getTipoUtenza().equals(TipoUtenzaEnum.CENTRALIZZATA)) {
      isVisibleCampoDatiContratti = true;
    }

    iseePanel = new IseePanel("iseePanel", datiDomanda, index);
    iseePanel.setVisible(index == 1);
    form.addOrReplace(iseePanel);

    datiRichiedentePanel = new DatiRichiedentePanel("datiRichiedentePanel", datiDomanda, index);
    datiRichiedentePanel.setVisible(index == 2);
    form.addOrReplace(datiRichiedentePanel);

    datiUtenzaPanel = new DatiUtenzaPanel("datiUtenzaPanel", datiDomanda, index);
    datiUtenzaPanel.setVisible(index == 3);
    form.addOrReplace(datiUtenzaPanel);

    datiContrattoPanel = new DatiContrattoPanel("datiContrattoPanel", datiDomanda, index);
    datiContrattoPanel.setVisible(index == 4 && isVisibleCampoDatiContratti);
    form.addOrReplace(datiContrattoPanel);

    consensiPanel = new ConsensiPanel("consensiPanel", datiDomanda, index);
    consensiPanel.setVisible(index == 5);
    form.addOrReplace(consensiPanel);

    containerInfo = new WebMarkupContainer("containerInfo");
    containerInfo.setVisible(index == 6);
    form.addOrReplace(containerInfo);

    esitoPanel = new EsitoPanel("esitoPanel", datiDomanda, index);
    esitoPanel.setVisible(index == 7);
    form.addOrReplace(esitoPanel);

    form.addOrReplace(creaBottoneAvanti(datiDomanda));
    form.addOrReplace(creaBottoneIndietro(datiDomanda));
    form.addOrReplace(creaBottoneAnnulla());
  }

  private FdcAjaxButton creaBottoneAvanti(DatiDomandaTeleriscaldamento datiDomanda) {
    avanti =
        new FdcAjaxButton("avanti") {

          private static final long serialVersionUID = -2172372902334828712L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            index = index + 1;

            if (index == 3
                && LabelFdCUtil.checkIfNotNull(datiDomanda)
                && !PageUtil.isStringValid(datiDomanda.getTelefono())
                && !PageUtil.isStringValid(datiDomanda.getCellulare())) {

              error("Attenzione, è necessario inserire almeno un recapito telefonico");

              index = index - 1;
            }

            if (index == 4 && LabelFdCUtil.checkIfNotNull(datiDomanda)) {

              boolean erroriInNumeroClienteNumeroContratto = false;

              // TODO per ora non usiamo la GET sui clienti, non togliere questo codice

              //
              //					if (PageUtil.isStringValid(datiDomanda.getNumeroCliente())) {
              //
              //						BigDecimal numeroClienteBigDecimal = new
              // BigDecimal(datiDomanda.getNumeroCliente());
              //
              //						String numeroClienteMassimo =
              // ServiceLocator.getInstance().getServiziTeleriscaldamento()
              //								.getValoreDaDbByChiave("TELERISCALDAMENTO_MAX_CLIENTE");
              //						BigDecimal numeroClienteMassimoBigDecimal = new
              // BigDecimal(numeroClienteMassimo);
              //
              //						if (LabelFdCUtil.checkIfNotNull(numeroClienteBigDecimal)
              //								&& LabelFdCUtil.checkIfNotNull(numeroClienteMassimoBigDecimal)
              //								&& numeroClienteBigDecimal.compareTo(numeroClienteMassimoBigDecimal) == 1) {
              //
              //							erroriInNumeroClienteNumeroContratto = false;
              //						} else {
              //							try {
              //								RicercaDato ricercaCliente =
              // ServiceLocator.getInstance().getServiziTeleriscaldamento()
              //										.isClientePresenteInLista(datiDomanda.getNumeroCliente());
              //
              //								if (LabelFdCUtil.checkIfNotNull(ricercaCliente)
              //										&& LabelFdCUtil.checkIfNotNull(ricercaCliente.getDatiRicerca())
              //										&& !ricercaCliente.getDatiRicerca().getPresente()) {
              //
              //									error("Attenzione, il numero cliente non corrisponde ad un contratto IREN
              // relativo al Teleriscaldamento");
              //
              //									erroriInNumeroClienteNumeroContratto = true;
              //
              //								}
              //							} catch (BusinessException | ApiException | IOException e) {
              //								log.error("Errore durante ricerca cliente: " + e.getMessage(), e);
              //							}
              //
              //						}
              //
              //					}

              if (PageUtil.isStringValid(datiDomanda.getNumeroContratto())) {

                BigDecimal numeroContrattoBigDecimal =
                    new BigDecimal(datiDomanda.getNumeroContratto());

                String numeroContrattoMassimo =
                    ServiceLocator.getInstance()
                        .getServiziTeleriscaldamento()
                        .getValoreDaDbByChiave("TELERISCALDAMENTO_MAX_CONTRATTO");
                BigDecimal numeroContrattoMassimoBigDecimal =
                    new BigDecimal(numeroContrattoMassimo);

                if (LabelFdCUtil.checkIfNotNull(numeroContrattoBigDecimal)
                    && LabelFdCUtil.checkIfNotNull(numeroContrattoMassimoBigDecimal)
                    && numeroContrattoBigDecimal.compareTo(numeroContrattoMassimoBigDecimal) == 1) {

                  erroriInNumeroClienteNumeroContratto = false;
                } else {
                  try {
                    RicercaDato ricercaContratto =
                        ServiceLocator.getInstance()
                            .getServiziTeleriscaldamento()
                            .isContrattoPresenteInLista(datiDomanda.getNumeroContratto());

                    if (LabelFdCUtil.checkIfNotNull(ricercaContratto)
                        && LabelFdCUtil.checkIfNotNull(ricercaContratto.getDatiRicerca())
                        && !ricercaContratto.getDatiRicerca().getPresente()) {

                      error(
                          "Attenzione, il numero contratto non corrisponde ad un contratto IREN relativo al Teleriscaldamento");

                      erroriInNumeroClienteNumeroContratto = true;
                    }
                  } catch (BusinessException | ApiException | IOException e) {
                    log.error("Errore durante ricerca cliente: " + e.getMessage(), e);
                  }
                }
              }

              if (erroriInNumeroClienteNumeroContratto) {
                index = index - 1;
              }
            }

            if (index == 5
                && LabelFdCUtil.checkIfNotNull(datiDomanda)
                && !PageUtil.isStringValid(datiDomanda.getTelefonoAmministratore())
                && !PageUtil.isStringValid(datiDomanda.getCellulareAmministratore())) {

              error(
                  "Attenzione, è necessario inserire almeno un recapito telefonico dell'aministratore");

              index = index - 1;
            }

            if (index == 4
                && LabelFdCUtil.checkIfNotNull(datiDomanda)
                && LabelFdCUtil.checkIfNotNull(datiDomanda.getTipoUtenza())
                && datiDomanda.getTipoUtenza().equals(TipoUtenzaEnum.DIRETTA_RIPARTITA)) {

              index = index + 1;
            }

            if (index == 6
                && LabelFdCUtil.checkIfNotNull(datiDomanda)
                && PageUtil.isStringValid(datiDomanda.getPrivacy())
                && PageUtil.isStringValid(datiDomanda.getArt43())
                && (datiDomanda.getPrivacy().equalsIgnoreCase("No")
                    || datiDomanda.getArt43().equalsIgnoreCase("No"))) {
              error("Attenzione, devi accettare entrambi");

              index = index - 1;
            }

            if (index == 7) {
              // chiamo i servizi

              try {
                if (LabelFdCUtil.checkIfNotNull(datiDomanda)) {
                  ServiceLocator.getInstance()
                      .getServiziTeleriscaldamento()
                      .inviaDomandaTeleriscaldamento(datiDomanda);

                  // TODO per ora non serve ma prima serviva

                  //							if (LabelFdCUtil.checkIfNotNull(datiDomanda.getIdStato())
                  //									&& datiDomanda.getIdStato().compareTo(new BigDecimal(1)) == 0) {
                  //
                  //								index = index - 1;
                  //
                  //								error("Errore iren");
                  //							}
                }

              } catch (ApiException e) {

                index = index - 1;

                String myMessage = e.getMyMessage();
                String eccezione = "javax.ws.rs.WebApplicationException:";
                String messaggioRicevuto = myMessage;
                if (myMessage.contains(eccezione)) {
                  messaggioRicevuto = myMessage.substring(eccezione.length(), myMessage.length());
                } else {
                  messaggioRicevuto =
                      "Servizio momentaneamente non disponibile, riprovare più tardi";
                }
                log.error("Errore gestito durante la chiamata delle API:" + myMessage, e);

                if (messaggioRicevuto.contains(": null")) {
                  messaggioRicevuto = messaggioRicevuto.replace(": null", "");
                }

                error(messaggioRicevuto);

              } catch (IOException | BusinessException e) {
                log.error("BusinessException gestito durante la chiamata delle API:", e);

                error("Errore durante invio domanda");
              }
            }

            getTargetPanel(target, datiDomanda);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {

            target.add(TeleriscaldamentoDomandaPanel.this);

            log.error("CP errore domanda teleriscaldamento ");
          }
        };

    avanti.setVisible(
        LabelFdCUtil.checkIfNotNull(datiDomanda) && datiDomanda.isDomandaPresentabile());

    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "TeleriscaldamentoDomandaPanel.avanti", TeleriscaldamentoDomandaPanel.this)));

    return avanti;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneAnnulla() {
    annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {

          private static final long serialVersionUID = 7767561269519813096L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(TeleriscaldamentoDomandaPanel.this);

            form.clearInput();

            setResponsePage(new TeleriscaldamentoPage());
          }
        };

    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "TeleriscaldamentoDomandaPanel.annulla", TeleriscaldamentoDomandaPanel.this)));

    return annulla;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneIndietro(
      DatiDomandaTeleriscaldamento datiDomanda) {
    indietro =
        new FdCButtonBootstrapAjaxLink<Object>("indietro", Type.Default) {

          private static final long serialVersionUID = -3790457331718709385L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            // target.add(DatiCompletiRegistrazioneUtenteAllerteZonaRossaPanel.this);

            index = index - 1;

            if (index == 4
                && LabelFdCUtil.checkIfNotNull(datiDomanda)
                && LabelFdCUtil.checkIfNotNull(datiDomanda.getTipoUtenza())
                && datiDomanda.getTipoUtenza().equals(TipoUtenzaEnum.DIRETTA_RIPARTITA)) {
              index = index - 1;
            }

            getTargetPanel(target, datiDomanda);

            // setResponsePage(new RegistrazioneAllertePage(datiCompleti, index));
          }
        };

    indietro.setVisible(false);
    indietro.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "TeleriscaldamentoDomandaPanel.indietro", TeleriscaldamentoDomandaPanel.this)));

    return indietro;
  }

  private AjaxRequestTarget getTargetPanel(
      AjaxRequestTarget target, DatiDomandaTeleriscaldamento datiDomanda) {
    stepPanel.setIndexStep(index);
    target.add(stepPanel, feedbackPanel, indietro, avanti, annulla);

    log.debug("CP getTargetPanel = " + index);

    boolean pannelloDatiContrattoVisibile =
        LabelFdCUtil.checkIfNotNull(datiDomanda)
            && LabelFdCUtil.checkIfNotNull(datiDomanda.getTipoUtenza())
            && datiDomanda.getTipoUtenza().equals(TipoUtenzaEnum.CENTRALIZZATA);

    if (index == 1) {
      iseePanel.setVisible(true);
      datiRichiedentePanel.setVisible(false);
      datiUtenzaPanel.setVisible(false);
      datiContrattoPanel.setVisible(false);
      consensiPanel.setVisible(false);
      containerInfo.setVisible(false);
      esitoPanel.setVisible(false);

      iseePanel.setEnabled(true);

      indietro.setVisible(false);

      target.add(this);

    } else if (index == 2) {
      iseePanel.setVisible(false);
      datiRichiedentePanel.setVisible(true);
      datiUtenzaPanel.setVisible(false);
      datiContrattoPanel.setVisible(false);
      consensiPanel.setVisible(false);
      containerInfo.setVisible(false);
      esitoPanel.setVisible(false);

      datiRichiedentePanel.setEnabled(true);

      indietro.setVisible(true);

      target.add(this);

    } else if (index == 3) {
      iseePanel.setVisible(false);
      datiRichiedentePanel.setVisible(false);
      datiUtenzaPanel.setVisible(true);
      datiContrattoPanel.setVisible(false);
      consensiPanel.setVisible(false);
      containerInfo.setVisible(false);
      esitoPanel.setVisible(false);

      indietro.setVisible(true);

      datiUtenzaPanel.setEnabled(true);

      target.add(this);

    } else if (index == 4) {
      iseePanel.setVisible(false);
      datiRichiedentePanel.setVisible(false);
      datiUtenzaPanel.setVisible(false);
      datiContrattoPanel.setVisible(true);
      consensiPanel.setVisible(false);
      containerInfo.setVisible(false);
      esitoPanel.setVisible(false);

      indietro.setVisible(true);

      datiContrattoPanel.setEnabled(true);

      target.add(this);

    } else if (index == 5) {
      iseePanel.setVisible(false);
      datiRichiedentePanel.setVisible(false);
      datiUtenzaPanel.setVisible(false);
      datiContrattoPanel.setVisible(false);
      consensiPanel.setVisible(true);
      containerInfo.setVisible(false);
      esitoPanel.setVisible(false);

      avanti.setVisible(true);
      indietro.setVisible(true);

      consensiPanel.setEnabled(true);

      target.add(this);

    } else if (index == 6) {
      iseePanel.setVisible(true);
      datiRichiedentePanel.setVisible(true);
      datiUtenzaPanel.setVisible(true);
      datiContrattoPanel.setVisible(pannelloDatiContrattoVisibile);
      consensiPanel.setVisible(true);
      containerInfo.setVisible(true);
      esitoPanel.setVisible(false);

      iseePanel.setEnabled(false);
      datiRichiedentePanel.setEnabled(false);
      datiUtenzaPanel.setEnabled(false);
      datiContrattoPanel.setEnabled(false);
      consensiPanel.setEnabled(false);
      containerInfo.setEnabled(false);

      avanti.setVisible(true);
      indietro.setVisible(true);

      target.add(this);

    } else if (index == 7) {
      iseePanel.setVisible(false);
      datiRichiedentePanel.setVisible(false);
      datiUtenzaPanel.setVisible(false);
      datiContrattoPanel.setVisible(false);
      consensiPanel.setVisible(false);
      containerInfo.setVisible(false);
      esitoPanel.setVisible(true);

      avanti.setVisible(false);
      indietro.setVisible(false);
      annulla.setVisible(false);

      target.add(this);
    }

    return target;
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    StringBuilder sb = new StringBuilder();

    sb.append(
        "$('html,body').animate({\r\n"
            + " scrollTop: $('#indicator').offset().top,\r\n"
            + " }, 650);");

    response.render(OnLoadHeaderItem.forScript(sb.toString()));
  }
}
