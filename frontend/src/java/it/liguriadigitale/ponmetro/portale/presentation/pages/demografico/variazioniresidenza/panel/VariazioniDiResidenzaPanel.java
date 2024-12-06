package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneDataBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneLinkEsterni;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.DettaglioVariazioniPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.util.VariazioniResidenzaUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Pratica;
import java.util.Collections;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;

public class VariazioniDiResidenzaPanel extends BasePanel {

  private static final long serialVersionUID = 5968129763983369063L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public VariazioniDiResidenzaPanel(String id) {
    super(id);

    createFeedBackPanel();
    setOutputMarkupId(true);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {

    LinkDinamicoLaddaFunzione<Object> btnCambioIndirizzo =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnCambioIndirizzo",
            new LinkDinamicoFunzioneData(
                "VariazioniDiResidenzaPanel.cambioIndirizzo",
                "CambioIndirizzoPage",
                "VariazioniDiResidenzaPanel.cambioIndirizzo"),
            null,
            VariazioniDiResidenzaPanel.this,
            "color-yellow col-auto icon-cambio-indirizzo");
    addOrReplace(btnCambioIndirizzo);

    LinkDinamicoLaddaFunzione<Object> btnRichiestaResidenza =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnRichiestaResidenza",
            new LinkDinamicoFunzioneData(
                "VariazioniDiResidenzaPanel.richiestaResidenza",
                "RichiestaResidenzaPage",
                "VariazioniDiResidenzaPanel.richiestaResidenza"),
            null,
            VariazioniDiResidenzaPanel.this,
            "color-yellow col-auto icon-cambio-residenza");
    addOrReplace(btnRichiestaResidenza);

    LinkDinamicoFunzioneLinkEsterni<Object> videoTutorialCambioIndirizzo =
        new LinkDinamicoFunzioneLinkEsterni<Object>(
            "videoTutorialCambioIndirizzo",
            LinkDinamicoFunzioneDataBuilder.getInstance()
                .setWicketLabelKeyText("VariazioniDiResidenzaPanel.videoTutorialCambioIndirizzo")
                .setWicketClassName("VideoTutorialCambioIndirizzoPage")
                .build(),
            null,
            VariazioniDiResidenzaPanel.this,
            "icon-video-tutorial",
            "variazioneDiResidenza",
            true);
    addOrReplace(videoTutorialCambioIndirizzo);

    LinkDinamicoFunzioneLinkEsterni<Object> videoTutorialRichiestaResidenza =
        new LinkDinamicoFunzioneLinkEsterni<Object>(
            "videoTutorialRichiestaResidenza",
            LinkDinamicoFunzioneDataBuilder.getInstance()
                .setWicketLabelKeyText("VariazioniDiResidenzaPanel.videoTutorialRichiestaResidenza")
                .setWicketClassName("VideoTutorialRichiestaResidenzaPage")
                .build(),
            null,
            VariazioniDiResidenzaPanel.this,
            "icon-video-tutorial",
            "variazioneDiResidenza",
            true);
    addOrReplace(videoTutorialRichiestaResidenza);

    List<Pratica> listaPratiche = (List<Pratica>) dati;

    Label listaIterPraticheVuota =
        new Label("listaIterPraticheVuota", getString("VariazioniDiResidenzaPanel.listaVuota"));
    listaIterPraticheVuota.setVisible(listaPratiche.isEmpty());
    addOrReplace(listaIterPraticheVuota);
    ordinaListaPratiche(listaPratiche);
    PageableListView<Pratica> listView =
        new PageableListView<Pratica>("box", listaPratiche, 4) {

          private static final long serialVersionUID = 4633679094471871484L;

          @Override
          protected void populateItem(ListItem<Pratica> item) {
            final Pratica pratica = item.getModelObject();

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(VariazioniResidenzaUtil.getCssIconaVariazione(pratica.getIdTipoPratica()));
            item.addOrReplace(icona);

            Label tipoPratica = new Label("tipoPratica", pratica.getTipoPratica());
            tipoPratica.setVisible(PageUtil.isStringValid(pratica.getTipoPratica()));
            item.addOrReplace(tipoPratica);

            Label id = new Label("id", pratica.getId());
            id.setVisible(LabelFdCUtil.checkIfNotNull(pratica.getId()));
            item.addOrReplace(id);

            Label anno = new Label("anno", pratica.getAnno());
            anno.setVisible(
                LabelFdCUtil.checkIfNotNull(pratica.getAnno())
                    && LabelFdCUtil.checkIfNotNull(pratica.getNumero())
                    && pratica.getNumero() != 0);
            item.addOrReplace(anno);

            Label numero = new Label("numero", pratica.getNumero());
            numero.setVisible(
                LabelFdCUtil.checkIfNotNull(pratica.getNumero()) && pratica.getNumero() != 0);
            item.addOrReplace(numero);

            LocalDateLabel dataInizio = new LocalDateLabel("dataInizio", pratica.getDataInizio());

            dataInizio.setVisible(LabelFdCUtil.checkIfNotNull(pratica.getDataInizio()));
            item.addOrReplace(dataInizio);

            Label stato = new Label("stato", pratica.getStatoDescrizione());
            stato.setVisible(PageUtil.isStringValid(pratica.getStatoDescrizione()));
            item.addOrReplace(stato);

            item.addOrReplace(creaBtnDettagli(pratica));
          }
        };
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(listaPratiche.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private void ordinaListaPratiche(List<Pratica> listaPratiche) {

    Collections.sort(listaPratiche, (b1, b2) -> (b2.getId() - b1.getId()));
  }

  private LaddaAjaxLink<Object> creaBtnDettagli(Pratica pratica) {
    LaddaAjaxLink<Object> btnDettagli =
        new LaddaAjaxLink<Object>("btnDettagli", Type.Primary) {

          private static final long serialVersionUID = -693729400447309744L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(VariazioniDiResidenzaPanel.this);

            setResponsePage(new DettaglioVariazioniPage(pratica));
          }
        };
    btnDettagli.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "VariazioniDiResidenzaPanel.btnDettagli", VariazioniDiResidenzaPanel.this)));

    return btnDettagli;
  }
}
