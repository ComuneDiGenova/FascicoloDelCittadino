package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.dettagli;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Civico;
import it.liguriadigitale.ponmetro.allertezonarossa.model.ComponenteNucleo;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Contatto;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.CancellaComponenteZonaRossa;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.ComponenteNucleoZonaRossa;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.ContattoTelefonicoZonaRossa;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.AggiungiComponenteZonaRossaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.AggiungiTelefonoZonaRossaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.CancellaComponenteAllerteRossePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.util.AllerteRosseUtil;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

public class DettagliCivicoZonaRossaPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = 6974494217935210713L;

  List<ComponenteNucleo> componentiNucleo;

  public DettagliCivicoZonaRossaPanel(String id, Civico dettagliCivico) {
    super(id, dettagliCivico);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(dettagliCivico);
  }

  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    Civico civico = (Civico) dati;

    componentiNucleo = new ArrayList<>();
    if (LabelFdCUtil.checkIfNotNull(civico)
        && LabelFdCUtil.checkIfNotNull(civico.getListaComponentiNucleo())
        && !LabelFdCUtil.checkEmptyList(civico.getListaComponentiNucleo())) {
      componentiNucleo = civico.getListaComponentiNucleo();
    }

    form.add(new FdCTitoloPanel("titolo", getString("DettagliCivicoZonaRossaPanel.titolo")));

    form.addOrReplace(creaBtnAggiungiComponente(civico.getId().intValue()));

    NotEmptyLabel indirizzoCompleto =
        new NotEmptyLabel("indirizzoCompleto", civico.getIndirizzoCompleto());
    form.addOrReplace(indirizzoCompleto);

    form.addOrReplace(
        new AmtCardLabel<>("idCivico", civico.getId(), DettagliCivicoZonaRossaPanel.this));

    form.addOrReplace(
        new AmtCardLabel<>("idVia", civico.getIdVia(), DettagliCivicoZonaRossaPanel.this));

    form.addOrReplace(
        new AmtCardLabel<>(
            "numeroCivico", civico.getNumeroCivico(), DettagliCivicoZonaRossaPanel.this));

    form.addOrReplace(
        new AmtCardLabel<>("esponente", civico.getEsponente(), DettagliCivicoZonaRossaPanel.this));

    form.addOrReplace(
        new AmtCardLabel<>("colore", civico.getColore(), DettagliCivicoZonaRossaPanel.this));

    form.addOrReplace(
        new AmtCardLabel<>("interno", civico.getInterno(), DettagliCivicoZonaRossaPanel.this));

    form.addOrReplace(
        new AmtCardLabel<>(
            "internoLettera", civico.getInternoLettera(), DettagliCivicoZonaRossaPanel.this));

    form.addOrReplace(
        new AmtCardLabel<>("scala", civico.getScala(), DettagliCivicoZonaRossaPanel.this));

    form.addOrReplace(
        new AmtCardLabel<>("posizione", civico.getPosizione(), DettagliCivicoZonaRossaPanel.this));

    form.addOrReplace(
        new AmtCardLabel<>(
            "vulnerabilita", civico.getVulnerabilita(), DettagliCivicoZonaRossaPanel.this));

    form.addOrReplace(
        new AmtCardLabel<>(
            "amministratore", civico.getAmministratore(), DettagliCivicoZonaRossaPanel.this));

    form.addOrReplace(
        new AmtCardLabel<>(
            "proprietario", civico.getProprietario(), DettagliCivicoZonaRossaPanel.this));

    List<ComponenteNucleo> listaComponentiNucleoDaVisualizzare = new ArrayList<>();

    for (Iterator<ComponenteNucleo> iterator = componentiNucleo.iterator(); iterator.hasNext(); ) {
      ComponenteNucleo componenteNucleo = iterator.next();
      if (!componenteNucleo
          .getCodiceFiscale()
          .equalsIgnoreCase(getUtente().getCodiceFiscaleOperatore())) {
        listaComponentiNucleoDaVisualizzare.add(componenteNucleo);
      }
    }

    ListView<ComponenteNucleo> listaComponentiNucleo =
        new ListView<ComponenteNucleo>("componentiNucleo", componentiNucleo) {

          private static final long serialVersionUID = -8632033718957382899L;

          @Override
          protected void populateItem(ListItem<ComponenteNucleo> itemComponenteNucleo) {
            final ComponenteNucleo componenteNucleo = itemComponenteNucleo.getModelObject();

            WebMarkupContainer iconaComponente = new WebMarkupContainer("iconaComponente");
            iconaComponente.add(
                AllerteRosseUtil.getCssIconaComponenteNucleo(componenteNucleo.getCodiceFiscale()));
            itemComponenteNucleo.addOrReplace(iconaComponente);

            itemComponenteNucleo.addOrReplace(
                new AmtCardLabel<>(
                    "nome", componenteNucleo.getNome(), DettagliCivicoZonaRossaPanel.this));

            itemComponenteNucleo.addOrReplace(
                new AmtCardLabel<>(
                    "cognome", componenteNucleo.getCognome(), DettagliCivicoZonaRossaPanel.this));

            itemComponenteNucleo.addOrReplace(
                new AmtCardLabel<>(
                    "email", componenteNucleo.geteMail(), DettagliCivicoZonaRossaPanel.this));

            itemComponenteNucleo.addOrReplace(
                new AmtCardLabel<>(
                    "codiceFiscale",
                    componenteNucleo.getCodiceFiscale(),
                    DettagliCivicoZonaRossaPanel.this));

            itemComponenteNucleo.addOrReplace(
                new AmtCardLabel<>(
                    "dataRegistrazione",
                    componenteNucleo.getDataRegistrazione(),
                    DettagliCivicoZonaRossaPanel.this));

            itemComponenteNucleo.addOrReplace(
                new AmtCardLabel<>(
                    "tipo", componenteNucleo.getTipo(), DettagliCivicoZonaRossaPanel.this));

            itemComponenteNucleo.addOrReplace(
                new AmtCardLabel<>(
                    "vulnerabilitaPersonale",
                    componenteNucleo.getVulnerabilitaPersonale(),
                    DettagliCivicoZonaRossaPanel.this));

            itemComponenteNucleo.addOrReplace(
                new AmtCardLabel<>(
                    "iscrizione",
                    componenteNucleo.getIscrizione(),
                    DettagliCivicoZonaRossaPanel.this));

            itemComponenteNucleo.addOrReplace(
                creaBtnAggiungiTelefonoComponente(componenteNucleo.getId().intValue()));

            itemComponenteNucleo.addOrReplace(
                creaBtnCancellaComponente(componenteNucleo, civico.getId()));

            List<Contatto> listaContattiTelefoniciDelComponente = new ArrayList<>();
            if (LabelFdCUtil.checkIfNotNull(componenteNucleo)
                && LabelFdCUtil.checkIfNotNull(componenteNucleo.getListaContattiTelefonici())
                && !LabelFdCUtil.checkEmptyList(componenteNucleo.getListaContattiTelefonici())) {
              listaContattiTelefoniciDelComponente = componenteNucleo.getListaContattiTelefonici();
            }
            ListView<Contatto> contattiTelefoniciDelComponente =
                new ListView<Contatto>(
                    "contattiTelefoniciDelComponente", listaContattiTelefoniciDelComponente) {

                  private static final long serialVersionUID = -6274796022015924149L;

                  @Override
                  protected void populateItem(ListItem<Contatto> itemContatto) {
                    final Contatto contatto = itemContatto.getModelObject();

                    WebMarkupContainer icona = new WebMarkupContainer("iconaTelefono");
                    icona.add(AllerteRosseUtil.getCssIconaTelefono(contatto.getTipo()));
                    itemContatto.addOrReplace(icona);

                    NotEmptyLabel numero = new NotEmptyLabel("numero", contatto.getNumero());
                    itemContatto.addOrReplace(numero);

                    itemContatto.addOrReplace(
                        new AmtCardLabel<>(
                            "idUtente", contatto.getId(), DettagliCivicoZonaRossaPanel.this));

                    itemContatto.addOrReplace(
                        new AmtCardLabel<>(
                            "lingua", contatto.getLingua(), DettagliCivicoZonaRossaPanel.this));

                    itemContatto.addOrReplace(
                        new AmtCardLabel<>(
                            "linguaNoItalia",
                            contatto.getLinguaNoItalia(),
                            DettagliCivicoZonaRossaPanel.this));

                    itemContatto.addOrReplace(
                        new AmtCardLabel<>(
                            "tipo", contatto.getTipo(), DettagliCivicoZonaRossaPanel.this));

                    itemContatto.addOrReplace(creaBtnCancellaTelefonoComponente(contatto));
                  }
                };
            contattiTelefoniciDelComponente.setVisible(
                LabelFdCUtil.checkIfNotNull(listaContattiTelefoniciDelComponente)
                    && !LabelFdCUtil.checkEmptyList(listaContattiTelefoniciDelComponente));
            itemComponenteNucleo.addOrReplace(contattiTelefoniciDelComponente);
          }
        };
    listaComponentiNucleo.setVisible(
        LabelFdCUtil.checkIfNotNull(componentiNucleo)
            && !LabelFdCUtil.checkEmptyList(componentiNucleo));
    form.addOrReplace(listaComponentiNucleo);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private FdCButtonBootstrapAjaxLink creaBtnCancellaComponente(
      ComponenteNucleo componenteNucleo, BigDecimal idCivico) {
    FdCButtonBootstrapAjaxLink btnCancellaComponente =
        new FdCButtonBootstrapAjaxLink("btnCancellaComponente", Type.Primary) {

          private static final long serialVersionUID = 8212511267081123448L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            log.debug("CP clic elimina compomente");

            CancellaComponenteZonaRossa cancellaComponente = new CancellaComponenteZonaRossa();
            cancellaComponente.setIdUtente(componenteNucleo.getId().intValue());
            cancellaComponente.setNome(componenteNucleo.getNome());
            cancellaComponente.setCognome(componenteNucleo.getCognome());
            cancellaComponente.setIdCivico(idCivico);

            setResponsePage(new CancellaComponenteAllerteRossePage(cancellaComponente));
          }
        };

    btnCancellaComponente.setLabel(
        Model.of(getString("DettagliCivicoZonaRossaPanel.btnCancellaComponente")));

    return btnCancellaComponente;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnAggiungiComponente(int idCivico) {
    FdCButtonBootstrapAjaxLink<Object> btnAggiungiComponente =
        new FdCButtonBootstrapAjaxLink<Object>("btnAggiungiComponente", Type.Primary) {

          private static final long serialVersionUID = 577744077934299036L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            log.debug("CP clic aggiungi componente");

            ComponenteNucleoZonaRossa componente = new ComponenteNucleoZonaRossa();
            componente.setIdCivico(idCivico);
            setResponsePage(new AggiungiComponenteZonaRossaPage(componente));
          }
        };

    btnAggiungiComponente.setLabel(
        Model.of(getString("DettagliCivicoZonaRossaPanel.btnAggiungiComponente")));
    btnAggiungiComponente.setVisible(isCapofamiglia());
    return btnAggiungiComponente;
  }

  private boolean isCapofamiglia() {

    if (componentiNucleo != null) {
      for (Iterator<ComponenteNucleo> iterator = componentiNucleo.iterator();
          iterator.hasNext(); ) {
        ComponenteNucleo componenteNucleo = iterator.next();
        if ("CAPO FAMIGLIA".equalsIgnoreCase(componenteNucleo.getTipo())
            && getUtente()
                .getCodiceFiscaleOperatore()
                .equalsIgnoreCase(componenteNucleo.getCodiceFiscale())) {
          return true;
        }
      }
    }

    return false;
  }

  private FdcAjaxButton creaBtnCancellaTelefonoComponente(Contatto contatto) {
    FdcAjaxButton btnCancellaTelefonoComponente =
        new FdcAjaxButton("btnCancellaTelefonoComponente") {

          private static final long serialVersionUID = 5650338475877167351L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            log.debug("CP clic elimina telefono componente = " + contatto);

            try {
              if (LabelFdCUtil.checkIfNotNull(contatto)
                  && LabelFdCUtil.checkIfNotNull(contatto.getId())) {
                ServiceLocator.getInstance()
                    .getServiziAllerteZonaRossa()
                    .deleteTelefono(contatto.getId().intValue());
              }
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

              error("Errore cancellazione telefono");
            }

            target.add(getPage());
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(DettagliCivicoZonaRossaPanel.this);
          }
        };

    btnCancellaTelefonoComponente.setLabel(
        Model.of(getString("DettagliCivicoZonaRossaPanel.btnCancellaTelefonoComponente")));

    return btnCancellaTelefonoComponente;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnAggiungiTelefonoComponente(int idUtente) {
    FdCButtonBootstrapAjaxLink<Object> btnAggiungiTelefonoComponente =
        new FdCButtonBootstrapAjaxLink<Object>("btnAggiungiTelefonoComponente", Type.Primary) {

          /** */
          private static final long serialVersionUID = -7102614460809052911L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            log.debug("CP clic aggiungi tel componente");

            ContattoTelefonicoZonaRossa contattoTelefonico = new ContattoTelefonicoZonaRossa();
            contattoTelefonico.setIdUtente(idUtente);
            setResponsePage(new AggiungiTelefonoZonaRossaPage(contattoTelefonico));
          }
        };

    btnAggiungiTelefonoComponente.setLabel(
        Model.of(getString("DettagliCivicoZonaRossaPanel.btnAggiungiTelefonoComponente")));

    return btnAggiungiTelefonoComponente;
  }
}
