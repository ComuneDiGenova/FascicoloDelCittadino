package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.DettaglioVerbaliPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.utilverbali.UtilVerbali;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.ArticoloViolato;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale.StatoEnum;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;

public class MieiVerbaliPanel extends BasePanel {

  private static final long serialVersionUID = -4930390410614798571L;

  private static final String ICON_AUTOVEICOLO = "color-cyan col-3 icon-autoveicolo";
  private static final String ICON_MOTOVEICOLO = "color-cyan col-3 icon-motoveicolo";
  private static final String ICON_MOTOCICLO = "color-cyan col-3 icon-ciclomotore";
  private static final String ICON_VEICOLI = "color-cyan col-3 icon-mezzi";

  @SuppressWarnings("unused")
  private List<Verbale> listaVerbali;

  private String stato;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  private boolean isArticoliDaVisualizzare = false;

  public MieiVerbaliPanel(String id, List<Verbale> listaVerbali, String stato) {
    super(id);

    this.listaVerbali = listaVerbali;
    this.stato = stato;

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  public MieiVerbaliPanel(String id) {
    this(id, null, null);
  }

  // ?? non usato
  // public MieiVerbaliPanel(String id, Verbale verbale) {
  // super(id);
  // setOutputMarkupId(true);
  // createFeedBackPanel();
  // }

  public MieiVerbaliPanel(String id, List<Verbale> listaVerbali) {
    this(id, listaVerbali, null);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {

    List<Verbale> listaVerbali = (List<Verbale>) dati;

    String statoVerbale = "";
    String messaggioListaVuotaDaBadge = getString("MieiVerbaliPanel.listaVerbaliVuotaDaBadge");
    if (stato != null && !stato.isEmpty()) {
      statoVerbale = stato;
      messaggioListaVuotaDaBadge = messaggioListaVuotaDaBadge.concat(" ").concat(statoVerbale);
    }
    String messaggioListaVuota = "";
    if (stato == null) {
      messaggioListaVuota = getString("MieiVerbaliPanel.listaVerbaliVuota");
    } else {
      messaggioListaVuota = messaggioListaVuotaDaBadge;
    }
    Label listaVerbaliVuota = new Label("listaVerbaliVuota", messaggioListaVuota);
    listaVerbaliVuota.setVisible(listaVerbali.isEmpty());
    add(listaVerbaliVuota);

    PageableListView<Verbale> listView =
        new PageableListView<Verbale>("box", listaVerbali, 4) {

          private static final long serialVersionUID = 6684733405639816072L;

          @Override
          protected void populateItem(ListItem<Verbale> item) {

            final Verbale verbale = item.getModelObject();

            String targaVerbale = "";
            if (verbale.getTarga() != null) {
              targaVerbale = verbale.getTarga();
            }
            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(getCssIconaVerbale(targaVerbale));
            icona.add(coloreIcona(verbale));
            item.addOrReplace(icona);

            Label targa = new Label("targa", verbale.getTarga());
            targa.setVisible(PageUtil.isStringValid(verbale.getTarga()));
            item.addOrReplace(targa);

            Label numeroAvviso = new Label("numeroAvviso", verbale.getNumeroAvviso());
            numeroAvviso.setVisible(PageUtil.isStringValid(verbale.getNumeroAvviso()));
            item.addOrReplace(numeroAvviso);

            Label numeroProtocollo = new Label("numeroProtocollo", verbale.getNumeroProtocollo());
            numeroProtocollo.setVisible(PageUtil.isStringValid(verbale.getNumeroProtocollo()));
            item.addOrReplace(numeroProtocollo);

            LocalDateLabel dataAccertamento =
                new LocalDateLabel("dataAccertamento", verbale.getDataAccertamento());
            dataAccertamento.setVisible(verbale.getDataAccertamento() != null);
            item.addOrReplace(dataAccertamento);

            Label localita = new Label("localita", verbale.getLocalita());
            localita.setVisible(PageUtil.isStringValid(verbale.getLocalita()));
            item.addOrReplace(localita);

            String articoloViolato = "";
            List<ArticoloViolato> listaArticoliViolati = verbale.getArticoliViolati();
            boolean articoliViolatiVisibile = true;
            if (UtilVerbali.checkIfNotNull(listaArticoliViolati)
                && !listaArticoliViolati.isEmpty()) {
              for (ArticoloViolato articoloViolatoElem : listaArticoliViolati) {
                String numero = "";
                String comma = "";
                String articoloDaVisualizzare = "";

                if (articoloViolatoElem != null) {
                  if (articoloViolatoElem.getArticoloDaVisualizzare() != null) {

                    articoloDaVisualizzare =
                        articoloDaVisualizzare.concat(
                            articoloViolatoElem.getArticoloDaVisualizzare());
                    isArticoliDaVisualizzare = true;
                  }

                  articoloViolato = articoloDaVisualizzare.concat("\n");
                }
              }
            }
            MultiLineLabel articoliViolati = new MultiLineLabel("articoliViolati", articoloViolato);
            articoliViolati.setVisible(
                verbale.getArticoliViolati() != null
                    && articoliViolatiVisibile
                    && isArticoliDaVisualizzare);
            articoliViolati.setEscapeModelStrings(false);
            item.addOrReplace(articoliViolati);

            String importoDaPagare = "";
            int compareToImporto = verbale.getImporto().compareTo(BigDecimal.ZERO);
            if (LabelFdCUtil.checkIfNotNull(verbale.getStato())
                && verbale.getStato().equalsIgnoreCase(StatoEnum.ARCHIVIATO.value())) {
              importoDaPagare = getString("MieiVerbaliPanel.nienteDaPagare");
            } else {
              if (compareToImporto == 0 || compareToImporto == -1) {
                importoDaPagare = getString("MieiVerbaliPanel.nienteDaPagare");
              } else {
                NumberFormat numberFormatEuro = NumberFormat.getCurrencyInstance(Locale.ITALY);
                importoDaPagare = numberFormatEuro.format(verbale.getImporto());
              }
            }

            Label importo = new Label("importo", importoDaPagare);

            importo.add(coloreImporto(verbale));
            importo.setVisible(verbale.getImporto() != null);
            item.addOrReplace(importo);

            String statoValue = "";
            if (verbale.getStato() != null) {
              statoValue = verbale.getStato();
            }
            Label stato = new Label("stato", statoValue);
            stato.setVisible(PageUtil.isStringValid(verbale.getStato()));
            item.addOrReplace(stato);

            LocalDateLabel dataSconto = new LocalDateLabel("dataSconto", verbale.getDataSconto());
            dataSconto.setVisible(verbale.getDataSconto() != null);
            item.addOrReplace(dataSconto);

            Integer totalePuntiPatente = 0;
            if (UtilVerbali.checkIfNotNull(listaArticoliViolati)
                && !listaArticoliViolati.isEmpty()) {
              for (ArticoloViolato articoloViolatoElem : listaArticoliViolati) {
                if (articoloViolatoElem != null) {
                  totalePuntiPatente += articoloViolatoElem.getPuntiPatente();
                }
              }
            }
            Label puntiPatente = new Label("puntiPatente", totalePuntiPatente);
            puntiPatente.setVisible(totalePuntiPatente > 0);
            item.addOrReplace(puntiPatente);

            WebMarkupContainer alertPuntiPatente = new WebMarkupContainer("alertPuntiPatente");
            alertPuntiPatente.setVisible(totalePuntiPatente > 0);
            item.addOrReplace(alertPuntiPatente);

            WebMarkupContainer alertIscrittoARuolo = new WebMarkupContainer("alertIscrittoARuolo");
            alertIscrittoARuolo.setVisible(
                LabelFdCUtil.checkIfNotNull(verbale)
                    && PageUtil.isStringValid(verbale.getStato())
                    && verbale.getStato().equalsIgnoreCase(StatoEnum.ISCRITTO_A_RUOLO.value()));
            item.addOrReplace(alertIscrittoARuolo);

            item.addOrReplace(creaLinkDettagliVerbale(verbale));
          }

          private AttributeModifier coloreImporto(final Verbale verbale) {

            AttributeModifier attributo;

            if (LabelFdCUtil.checkIfNull(verbale.getStato())) {
              return new AttributeModifier("style", "color: #d9364f; ");
            }

            StatoEnum statoEnum = StatoEnum.fromValue(verbale.getStato());

            switch (statoEnum) {
              case ARCHIVIATO:
                attributo =
                    new AttributeModifier("style", "color: white;background-color: #008758");
                break;
              case IN_ATTESA_DI_VERIFICA:
                attributo =
                    new AttributeModifier("style", "color: white;background-color: #a66300");
                break;

              case OGGETTO_DI_RICORSO:
              case INVIO_AD_AUTORITA_COMPETENTE:
              case ISCRITTO_A_RUOLO:
                attributo = new AttributeModifier("style", "color: white;background-color: #06c");
                break;
              default:
                attributo =
                    new AttributeModifier("style", "color: white;background-color: #d9364f");
                break;
            }

            return attributo;
          }

          private AttributeModifier coloreIcona(final Verbale verbale) {

            AttributeModifier attributo;

            if (LabelFdCUtil.checkIfNull(verbale.getStato())) {
              return new AttributeModifier("style", "color: #d9364f");
            }

            StatoEnum statoEnum = StatoEnum.fromValue(verbale.getStato());

            switch (statoEnum) {
              case ARCHIVIATO:
                attributo = new AttributeModifier("style", "color: #008758");
                break;
              case IN_ATTESA_DI_VERIFICA:
                attributo = new AttributeModifier("style", "color: #a66300");
                break;

              case OGGETTO_DI_RICORSO:
              case INVIO_AD_AUTORITA_COMPETENTE:
              case ISCRITTO_A_RUOLO:
                attributo = new AttributeModifier("style", "color: #06c");
                break;
              default:
                attributo = new AttributeModifier("style", "color: #d9364f");
                break;
            }

            return attributo;
          }

          private LaddaAjaxLink<Object> creaLinkDettagliVerbale(Verbale verbale) {
            LaddaAjaxLink<Object> linkDettagliVerbale =
                new LaddaAjaxLink<Object>("btnDettagliVerbale", Type.Primary) {

                  private static final long serialVersionUID = 4181405244105128930L;

                  @Override
                  public void onClick(AjaxRequestTarget target) {
                    target.add(MieiVerbaliPanel.this);
                    setResponsePage(new DettaglioVerbaliPage(verbale));
                  }
                };
            linkDettagliVerbale.setLabel(
                Model.of(
                    Application.get()
                        .getResourceSettings()
                        .getLocalizer()
                        .getString("MieiVerbaliPanel.dettagliVerbale", MieiVerbaliPanel.this)));

            return linkDettagliVerbale;
          }

          private AttributeAppender getCssIconaVerbale(String targaVerbale) {
            String css = "";
            String regex = "[a-zA-Z]+";

            if (targaVerbale.length() == 8) {
              if (targaVerbale.substring(0, 3).matches(regex)) {
                css = ICON_AUTOVEICOLO;
              } else {
                css = ICON_MOTOVEICOLO;
              }
            } else if (targaVerbale.length() == 7) {
              if (targaVerbale.substring(5, 6).matches(regex)) {
                css = ICON_AUTOVEICOLO;
              } else {
                css = ICON_MOTOVEICOLO;
              }
            } else if (targaVerbale.length() == 5 || targaVerbale.length() == 6) {
              css = ICON_MOTOCICLO;
            } else {
              css = ICON_VEICOLI;
            }

            return new AttributeAppender("class", " " + css);
          }
        };

    listView.setRenderBodyOnly(true);
    listView.setVisible(!listaVerbali.isEmpty());
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("paginationMieiVerbali", listView);
    paginazioneFascicolo.setVisible(listaVerbali.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }
}
