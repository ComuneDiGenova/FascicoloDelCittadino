package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.ricorsialprefetto.RicorsiAlPrefettoPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

public class RicorsiPanel extends BasePanel {

  private static final long serialVersionUID = 2786766912456067993L;

  public RicorsiPanel(String id, DettaglioVerbale dettaglioVerbale) {
    super(id);
    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {

    DettaglioVerbale dettaglioVerbale = (DettaglioVerbale) dati;

    Label titoloRicorsi = new Label("titoloRicorsi", getString("RicorsiPanel.titolo"));
    add(titoloRicorsi);

    WebMarkupContainer nonPuoiPresentareRicorsoAlPrefetto =
        new WebMarkupContainer("nonPuoiPresentareRicorsoAlPrefetto");
    nonPuoiPresentareRicorsoAlPrefetto.setOutputMarkupId(true);
    nonPuoiPresentareRicorsoAlPrefetto.setOutputMarkupPlaceholderTag(true);
    nonPuoiPresentareRicorsoAlPrefetto.setVisible(
        !isRicorsoAlPrefettoRichiedibile(dettaglioVerbale));
    addOrReplace(nonPuoiPresentareRicorsoAlPrefetto);

    WebMarkupContainer puoiPresentareRicorsoAlPrefetto =
        new WebMarkupContainer("puoiPresentareRicorsoAlPrefetto");
    puoiPresentareRicorsoAlPrefetto.setOutputMarkupId(true);
    puoiPresentareRicorsoAlPrefetto.setOutputMarkupPlaceholderTag(true);
    puoiPresentareRicorsoAlPrefetto.setVisible(isRicorsoAlPrefettoRichiedibile(dettaglioVerbale));
    addOrReplace(puoiPresentareRicorsoAlPrefetto);

    // TODO per ora mettiamo non visibile
    // boolean isVisibileBtnDomandaRicorsoAlPrefetto = !isPresenteUnRicorso(dettaglioVerbale);
    boolean isVisibileBtnDomandaRicorsoAlPrefetto = !isPresenteUnRicorso(dettaglioVerbale);

    LinkDinamicoLaddaFunzione<Object> btnDomandaRicorsoAlPrefetto =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnDomandaRicorsoAlPrefetto",
            new LinkDinamicoFunzioneData(
                "RicorsiPanel.btnDomandaRicorsoAlPrefetto",
                "RichiestaRicorsoAlPrefettoPage",
                "RicorsiPanel.btnDomandaRicorsoAlPrefetto"),
            dettaglioVerbale,
            RicorsiPanel.this,
            "color-cyan col-auto icon-fogli",
            isRicorsoAlPrefettoRichiedibile(dettaglioVerbale));
    addOrReplace(btnDomandaRicorsoAlPrefetto);

    String identificativoRicorso = "";
    String statoRicorso = "";
    String dataRichiestaRicorso = "";
    if (LabelFdCUtil.checkIfNotNull(dettaglioVerbale.getRicorsoAlPrefetto())) {

      if (LabelFdCUtil.checkIfNotNull(
          dettaglioVerbale.getRicorsoAlPrefetto().getProtocolloRicorso())) {
        identificativoRicorso =
            dettaglioVerbale.getRicorsoAlPrefetto().getProtocolloRicorso().toString().concat(" ");
      }
      if (LabelFdCUtil.checkIfNotNull(dettaglioVerbale.getRicorsoAlPrefetto().getAnnoRicorso())) {
        identificativoRicorso =
            identificativoRicorso.concat(
                dettaglioVerbale.getRicorsoAlPrefetto().getAnnoRicorso().toString());
      }

      statoRicorso = dettaglioVerbale.getRicorsoAlPrefetto().getStato();

      if (LabelFdCUtil.checkIfNotNull(
          dettaglioVerbale.getRicorsoAlPrefetto().getDataRichiestaRicorso())) {
        dataRichiestaRicorso =
            LocalDateUtil.getDataFormatoEuropeo(
                dettaglioVerbale.getRicorsoAlPrefetto().getDataRichiestaRicorso());
      }
    }

    WebMarkupContainer datiRicorsiAlPrefetto = new WebMarkupContainer("datiRicorsiAlPrefetto");
    datiRicorsiAlPrefetto.setOutputMarkupId(true);
    datiRicorsiAlPrefetto.setOutputMarkupPlaceholderTag(true);

    NotEmptyLabel datiRicorso =
        new NotEmptyLabel(
            "datiRicorso",
            new StringResourceModel("RicorsiPanel.datiRicorso", this)
                .setParameters(identificativoRicorso, dataRichiestaRicorso, statoRicorso));
    datiRicorsiAlPrefetto.addOrReplace(datiRicorso);

    datiRicorsiAlPrefetto.addOrReplace(creaLinkDettagli());

    datiRicorsiAlPrefetto.setVisible(isPresenteUnRicorso(dettaglioVerbale));
    addOrReplace(datiRicorsiAlPrefetto);
  }

  private boolean isRicorsoAlPrefettoRichiedibile(DettaglioVerbale dettaglioVerbale) {
    return LabelFdCUtil.checkIfNotNull(dettaglioVerbale.getRicorsoAlPrefettoRichiedibile())
        && dettaglioVerbale.getRicorsoAlPrefettoRichiedibile();
  }

  private boolean isPresenteUnRicorso(DettaglioVerbale dettaglioVerbale) {
    return LabelFdCUtil.checkIfNotNull(dettaglioVerbale.getRicorsoAlPrefetto());
  }

  private LaddaAjaxLink<Object> creaLinkDettagli() {
    LaddaAjaxLink<Object> btnDettagli =
        new LaddaAjaxLink<Object>("btnDettagli", Type.Primary) {

          private static final long serialVersionUID = 9178194178332086287L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            target.add(RicorsiPanel.this);

            setResponsePage(new RicorsiAlPrefettoPage());
          }
        };
    btnDettagli.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("RicorsiPanel.btnDettagli", RicorsiPanel.this)));

    return btnDettagli;
  }
}
