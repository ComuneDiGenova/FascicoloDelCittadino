package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.cancellacomponente;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.CancellaComponenteZonaRossa;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.AllertePage;
import java.io.IOException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class CancellaComponenteAllerteRossePanel extends FdcCardFormPanel {

  private static final long serialVersionUID = 88620181735868573L;

  private FdcAjaxButton avanti;
  private FdCButtonBootstrapAjaxLink<Object> annulla;
  private FdcAjaxButton conferma;

  @SuppressWarnings("rawtypes")
  private FdCTextField motivo;

  private WebMarkupContainer containerSeiSicuro = new WebMarkupContainer("containerSeiSicuro");

  public CancellaComponenteAllerteRossePanel(
      String id, CancellaComponenteZonaRossa cancellaComponente) {
    super(id, cancellaComponente);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(cancellaComponente);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    CancellaComponenteZonaRossa cancellaComponente = (CancellaComponenteZonaRossa) dati;

    log.debug("CP fill dati cancella componente = " + cancellaComponente);

    form.add(new FdCTitoloPanel("titolo", getString("CancellaComponenteAllerteRossePanel.titolo")));

    motivo =
        new FdCTextField(
            "motivo",
            new PropertyModel(cancellaComponente, "motivo"),
            CancellaComponenteAllerteRossePanel.this);
    motivo.getTextField().setRequired(true);
    form.addOrReplace(motivo);

    FdCTextField nome =
        new FdCTextField(
            "nome",
            new PropertyModel(cancellaComponente, "nome"),
            CancellaComponenteAllerteRossePanel.this);
    nome.setEnabled(false);
    form.addOrReplace(nome);

    FdCTextField cognome =
        new FdCTextField(
            "cognome",
            new PropertyModel(cancellaComponente, "cognome"),
            CancellaComponenteAllerteRossePanel.this);
    cognome.setEnabled(false);
    form.addOrReplace(cognome);

    form.addOrReplace(creaBtnAvanti(cancellaComponente));
    form.addOrReplace(creaBtnAnnulla());

    containerSeiSicuro.setOutputMarkupId(true);
    containerSeiSicuro.setOutputMarkupPlaceholderTag(true);
    containerSeiSicuro.setVisible(false);

    containerSeiSicuro.addOrReplace(
        new FdCTitoloPanel(
            "seiSicuro", getString("CancellaComponenteAllerteRossePanel.seiSicuro")));

    containerSeiSicuro.addOrReplace(creaBtnConferma(cancellaComponente));

    form.addOrReplace(containerSeiSicuro);
  }

  private FdcAjaxButton creaBtnAvanti(CancellaComponenteZonaRossa cancellaComponente) {
    avanti =
        new FdcAjaxButton("avanti") {

          private static final long serialVersionUID = -6336667252584564096L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {
            log.debug("CP click cancella componente ");

            containerSeiSicuro.setVisible(true);

            target.add(CancellaComponenteAllerteRossePanel.this);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(CancellaComponenteAllerteRossePanel.this);
          }
        };

    return avanti;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnAnnulla() {
    annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {

          private static final long serialVersionUID = -6862355684270108819L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            setResponsePage(new AllertePage());
          }
        };

    annulla.setLabel(Model.of(getString("CancellaComponenteAllerteRossePanel.annulla")));

    return annulla;
  }

  private FdcAjaxButton creaBtnConferma(CancellaComponenteZonaRossa cancellaComponente) {
    conferma =
        new FdcAjaxButton("conferma") {

          private static final long serialVersionUID = 4003853190343248175L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {
            log.debug("CP click conferma componente ");

            try {

              ServiceLocator.getInstance()
                  .getServiziAllerteZonaRossa()
                  .deleteComponente(
                      cancellaComponente.getIdUtente(),
                      cancellaComponente.getIdCivico().intValue(),
                      cancellaComponente.getMotivo());

              avanti.setVisible(false);
              annulla.setVisible(false);
              motivo.setEnabled(false);
              containerSeiSicuro.setVisible(false);
              containerSeiSicuro.setEnabled(false);

              success("Componente cancellato");

            } catch (ApiException e) {
              String myMessage = e.getMyMessage();
              String eccezione = "javax.ws.rs.WebApplicationException:";
              String messaggioRicevuto = myMessage;
              if (myMessage.contains(eccezione)) {
                messaggioRicevuto = myMessage.substring(eccezione.length(), myMessage.length());
              } else {
                messaggioRicevuto = "Servizio momentaneamente non disponibile, riprovare più tardi";
              }
              log.error("Errore gestito durante la chiamata delle API:" + myMessage, e);

              Integer indexOf = messaggioRicevuto.indexOf(":");
              String messaggioDaVisualizzare =
                  messaggioRicevuto.substring(indexOf + 1, messaggioRicevuto.length());

              error(messaggioDaVisualizzare);

            } catch (IOException | BusinessException e) {
              log.error("BusinessException gestito durante la chiamata delle API:", e);

              error("Errore cancellazione componente");
            }

            target.add(CancellaComponenteAllerteRossePanel.this);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(CancellaComponenteAllerteRossePanel.this);
          }
        };

    return conferma;
  }
}
