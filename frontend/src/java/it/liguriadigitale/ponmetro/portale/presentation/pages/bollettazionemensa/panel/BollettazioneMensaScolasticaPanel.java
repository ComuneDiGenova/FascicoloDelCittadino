package it.liguriadigitale.ponmetro.portale.presentation.pages.bollettazionemensa.panel;

import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizionenonesidente.enums.ModalitaBollettazioneEnum;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiInfoBollettazione;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class BollettazioneMensaScolasticaPanel extends BasePanel {

  private static final long serialVersionUID = 8686365065263224675L;

  public BollettazioneMensaScolasticaPanel(String id, DatiInfoBollettazione datiBollettazione) {
    super(id);

    setOutputMarkupId(true);

    fillDati(datiBollettazione);
  }

  @Override
  public void fillDati(Object dati) {

    DatiInfoBollettazione datiBollettazione = (DatiInfoBollettazione) dati;

    String emailBollettazione = "";
    ModalitaBollettazioneEnum modalita;
    String modalitaBollettazione = "";

    if (LabelFdCUtil.checkIfNotNull(datiBollettazione)) {
      emailBollettazione = datiBollettazione.getEmailBollettazione();

      if (LabelFdCUtil.checkIfNotNull(datiBollettazione.getIsCartaceo())) {
        modalita =
            datiBollettazione.getIsCartaceo() == true
                ? ModalitaBollettazioneEnum.CARTACEA
                : ModalitaBollettazioneEnum.DEMATERIALIZZATA;
        modalitaBollettazione = modalita.name();
      }
    }

    WebMarkupContainer nonSeiUnImpegnatoAlPagamento =
        new WebMarkupContainer("nonSeiUnImpegnatoAlPagamento");
    nonSeiUnImpegnatoAlPagamento.setVisible(LabelFdCUtil.checkIfNull(datiBollettazione));

    //		LinkDinamicoLaddaFunzione<Object> btnIscrizioneMensaResidente = new
    // LinkDinamicoLaddaFunzione<Object>(
    //				"btnIscrizioneMensaResidente",
    //				new
    // LinkDinamicoFunzioneData("BollettazioneMensaScolasticaPanel.btnIscrizioneMensaResidente",
    // "DomandaIscrizioneMensaResidentePage",
    //						"BollettazioneMensaScolasticaPanel.btnIscrizioneMensaResidente"),
    //				null, BollettazioneMensaScolasticaPanel.this, "color-orange col-auto icon-studenti",
    // getUtente().isResidente());
    //		nonSeiUnImpegnatoAlPagamento.addOrReplace(btnIscrizioneMensaResidente);
    //
    //		log.debug("CP btnIscrizioneMensaResidente = " + btnIscrizioneMensaResidente.isVisible());

    //		LinkDinamicoLaddaFunzione<Object> btnIscrizioneMensaNonResidente = new
    // LinkDinamicoLaddaFunzione<Object>(
    //				"btnIscrizioneMensaNonResidente",
    //				new
    // LinkDinamicoFunzioneData("BollettazioneMensaScolasticaPanel.btnIscrizioneMensaNonResidente",
    // "DomandaIscrizioneMensaResidentePage",
    //						"BollettazioneMensaScolasticaPanel.btnIscrizioneMensaNonResidente"),
    //				null, BollettazioneMensaScolasticaPanel.this, "color-orange col-auto icon-studenti",
    // !getUtente().isResidente());
    //		nonSeiUnImpegnatoAlPagamento.addOrReplace(btnIscrizioneMensaNonResidente);
    //
    //		log.debug("CP btnIscrizioneMensaNonResidente = " +
    // btnIscrizioneMensaNonResidente.isVisible());

    LinkDinamicoLaddaFunzione<Object> btnBambiniAScuola =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnBambiniAScuola",
            new LinkDinamicoFunzioneData(
                "BollettazioneMensaScolasticaPanel.btnBambiniAScuola",
                "RiepilogoIscrizioniPage",
                "BollettazioneMensaScolasticaPanel.btnBambiniAScuola"),
            null,
            BollettazioneMensaScolasticaPanel.this,
            "color-orange col-auto icon-studenti");
    nonSeiUnImpegnatoAlPagamento.addOrReplace(btnBambiniAScuola);

    addOrReplace(nonSeiUnImpegnatoAlPagamento);

    WebMarkupContainer seiUnImpegnatoAlPagamento =
        new WebMarkupContainer("seiUnImpegnatoAlPagamento");
    seiUnImpegnatoAlPagamento.setVisible(
        LabelFdCUtil.checkIfNotNull(datiBollettazione)
            && LabelFdCUtil.checkIfNull(datiBollettazione.getIsCartaceo()));
    addOrReplace(seiUnImpegnatoAlPagamento);

    WebMarkupContainer bollettazione = new WebMarkupContainer("bollettazione");
    bollettazione.setVisible(
        LabelFdCUtil.checkIfNotNull(datiBollettazione)
            && LabelFdCUtil.checkIfNotNull(datiBollettazione.getIsCartaceo()));

    bollettazione.addOrReplace(
        new AmtCardLabel<>(
            "emailBollettazione", emailBollettazione, BollettazioneMensaScolasticaPanel.this));

    bollettazione.addOrReplace(
        new AmtCardLabel<>(
            "modalitaBollettazione",
            modalitaBollettazione,
            BollettazioneMensaScolasticaPanel.this));

    addOrReplace(bollettazione);
  }
}
