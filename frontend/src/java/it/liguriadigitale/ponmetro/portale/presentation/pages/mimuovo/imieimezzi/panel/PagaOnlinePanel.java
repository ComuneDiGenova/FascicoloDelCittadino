package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutBasePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.IMieiMezziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.pagopa.DatiAvvioPagoPa;
import it.liguriadigitale.ponmetro.tassaauto.model.Bollo;
import it.liguriadigitale.ponmetro.tassaauto.model.DatiAvvioPagamento;
import it.liguriadigitale.ponmetro.tassaauto.model.DettaglioCalcoloBollo;
import it.liguriadigitale.ponmetro.tassaauto.model.EsitoAnnullamentoPagamento;
import it.liguriadigitale.ponmetro.tassaauto.model.Veicolo;
import javax.ws.rs.WebApplicationException;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;

public class PagaOnlinePanel extends BasePanel {

  private static final long serialVersionUID = 6183708833728318165L;

  private DettaglioCalcoloBollo dettaglioCalcoloBollo;

  private Bollo bollo;

  private Veicolo veicolo;

  private String messaggioErrore;

  public PagaOnlinePanel(String id) {
    super(id);

    createFeedBackPanel();
  }

  public PagaOnlinePanel(
      String id, Bollo bollo, DettaglioCalcoloBollo dettaglioCalcoloBollo, Veicolo veicolo) {
    super(id);

    this.bollo = bollo;
    this.dettaglioCalcoloBollo = dettaglioCalcoloBollo;
    this.veicolo = veicolo;

    createFeedBackPanel();
  }

  private DatiAvvioPagamento getRichiestaPagamentoPagoPa(Veicolo veicolo, Bollo bollo) {
    try {
      return ServiceLocator.getInstance()
          .getServiziMieiMezzi()
          .creaRichiestaPagamentoPagoPa(veicolo, bollo, getUtente());
    } catch (BusinessException | ApiException | WebApplicationException e) {
      log.error("Errore getRichiestaPagamentoPagoPa " + e.getMessage(), e);
      messaggioErrore = e.getMessage();
      return null;
    }
  }

  private EsitoAnnullamentoPagamento annullaPrenotazionePagamentoPagoPa(
      DatiAvvioPagamento datiAvvioPagamento) {
    try {
      EsitoAnnullamentoPagamento esitoAnnullamentoPagamento =
          ServiceLocator.getInstance()
              .getServiziMieiMezzi()
              .annullaPrenotazionePagamentoPagoPa(datiAvvioPagamento);

      return esitoAnnullamentoPagamento;
    } catch (BusinessException | ApiException e) {
      log.error("Errore annullaPrenotazionePagamentoPagoPa " + e.getMessage(), e);
      return null;
    }
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void fillDati(Object dati) {

    DatiAvvioPagamento datiAvvioPagamento = getRichiestaPagamentoPagoPa(veicolo, bollo);
    Form<DatiAvvioPagoPa> form = null;

    if (datiAvvioPagamento == null) {
      form =
          new Form<DatiAvvioPagoPa>(
              "form", new CompoundPropertyModel<DatiAvvioPagoPa>(new DatiAvvioPagoPa()));
      Label messaggioErrorePagamento = new Label("messaggioErrorePagamento", messaggioErrore);
      add(messaggioErrorePagamento);
      messaggioErrorePagamento.setVisible(true);
      form.setVisible(false);
    } else {
      Label messaggioErrorePagamento =
          new Label(
              "messaggioErrorePagamento", getString("PagaOnlinePanel.messaggioErrorePagamento"));
      messaggioErrorePagamento.setVisible(false);
      add(messaggioErrorePagamento);

      form =
          new Form<DatiAvvioPagoPa>(
              "form",
              new CompoundPropertyModel<DatiAvvioPagoPa>(new DatiAvvioPagoPa(datiAvvioPagamento))) {

            private static final long serialVersionUID = 6945256023044148017L;

            @Override
            protected void onSubmit() {
              log.debug("CP onsubmit form pagaonline");
            }

            @Override
            protected String getActionUrl() {
              return datiAvvioPagamento.getUrlAction();
            }
          };

      Label nomeCognome =
          new Label(
              "nomeCognome", getUtente().getNome().concat(" ").concat(getUtente().getCognome()));
      form.add(nomeCognome);

      Label mailUtente;
      if (getUtente().getMail() == null && LayoutBasePage.ambienteInternoDiTest()) {
        mailUtente = new Label("mailUtente", "d.stellardo@liguriadigitale.it");
      } else {
        mailUtente = new Label("mailUtente", getUtente().getMail());
      }
      form.add(mailUtente);

      Label targa = new Label("targa", bollo.getTarga());
      form.add(targa);

      Label tipoVeicolo = new Label("tipoVeicolo", bollo.getTipoVeicolo());
      form.add(tipoVeicolo);

      String scadenzaBollo =
          dettaglioCalcoloBollo
              .getDataScadenza()
              .getMese()
              .getDescrizione()
              .concat(" ")
              .concat(dettaglioCalcoloBollo.getDataScadenza().getAnno());
      Label scadenza = new Label("scadenza", scadenzaBollo);
      form.add(scadenza);

      Label ultimoGiornoUtile =
          new Label("ultimoGiornoUtile", dettaglioCalcoloBollo.getDataUltimoGiornoPagamento());
      form.add(ultimoGiornoUtile);

      Label importoTotale =
          new Label("importoTotale", dettaglioCalcoloBollo.getImporto().getTotale().getImporto());
      form.add(importoTotale);

      Label tassa = new Label("tassa", dettaglioCalcoloBollo.getImporto().getTassa().getImporto());
      form.add(tassa);

      Label sanzioni =
          new Label("sanzioni", dettaglioCalcoloBollo.getImporto().getSanzioni().getImporto());
      form.add(sanzioni);

      Label interessi =
          new Label("interessi", dettaglioCalcoloBollo.getImporto().getInteressi().getImporto());
      form.add(interessi);

      HiddenField alias = new HiddenField("alias");
      form.add(alias);

      HiddenField importo = new HiddenField("importo");
      form.add(importo);

      HiddenField codTrans = new HiddenField("codTrans");
      form.add(codTrans);

      HiddenField divisa = new HiddenField("divisa");
      form.add(divisa);

      HiddenField url = new HiddenField("url");
      form.add(url);

      HiddenField urlback = new HiddenField("url_back");
      form.add(urlback);

      HiddenField urlpost = new HiddenField("urlpost");
      form.add(urlpost);

      HiddenField mac = new HiddenField("mac");
      form.add(mac);

      HiddenField identificativoFiscalePagatore = new HiddenField("identificativoFiscalePagatore");
      form.add(identificativoFiscalePagatore);

      HiddenField tipoPagatore = new HiddenField("tipoPagatore");
      form.add(tipoPagatore);

      HiddenField anagraficaPagatore = new HiddenField("anagraficaPagatore");
      form.add(anagraficaPagatore);

      HiddenField mail = new HiddenField("mail");
      form.add(mail);

      form.setVisible(true);
    }

    form.add(creaBottoneAnnulla(datiAvvioPagamento));
    add(form);
  }

  private Component creaBottoneAnnulla(DatiAvvioPagamento datiAvvioPagamento) {
    return (new Link<Void>("annulla") {

      private static final long serialVersionUID = 1802532275878600465L;

      @Override
      public void onClick() {
        EsitoAnnullamentoPagamento esitoAnnullamentoPagamento =
            annullaPrenotazionePagamentoPagoPa(datiAvvioPagamento);

        if (esitoAnnullamentoPagamento.getCodiceOperazione().equalsIgnoreCase("OK")) {
          setResponsePage(IMieiMezziPage.class);
        } else {
          setResponsePage(ErrorPage.class);
        }
      }
    });
  }
}
