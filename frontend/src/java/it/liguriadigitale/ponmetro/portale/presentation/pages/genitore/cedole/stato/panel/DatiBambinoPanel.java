package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.panel;

import it.liguriadigitale.ponmetro.portale.pojo.cedole.CedoleMinore;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.CedoleLibrarieConPrivacyPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class DatiBambinoPanel extends BasePanel {

  private static final long serialVersionUID = -3021289773196275267L;

  private static final String ICON_BIMBA = "color-orange col-3 icon-bimba";
  private static final String ICON_BIMBO = "color-orange col-3 icon-bimbo";

  public DatiBambinoPanel(String id, CedoleMinore bambino) {
    super(id);
    log.debug("DatiBambinoPanel INIZIO");
    setOutputMarkupId(true);
    fillDati(bambino);
  }

  @Override
  public void fillDati(Object dati) {
    CedoleMinore minore = (CedoleMinore) dati;
    UtenteServiziRistorazione iscritto = minore.getMinore();

    WebMarkupContainer icona = new WebMarkupContainer("icona");
    icona.add(getCssIconaMaschioFemmina(iscritto));
    Label nomeCognome = new Label("nome", iscritto.getNome() + " " + iscritto.getCognome());
    LocalDateLabel dataNascita = new LocalDateLabel("data", iscritto.getDataNascita());

    int calcoloEta = LocalDateUtil.getMesiEta(iscritto.getDataNascita());
    String etaFiglio = "";
    String mesi = "";
    if (calcoloEta < 1) {
      etaFiglio = calcoloEta + " ";
      mesi = getString("RiepilogoIscrizioniPanel.mese");
    } else if (calcoloEta < 12) {
      etaFiglio = calcoloEta + " ";
      mesi = getString("RiepilogoIscrizioniPanel.mesi");
    } else if (calcoloEta < 24) {
      etaFiglio = calcoloEta / 12 + " ";
      mesi = getString("RiepilogoIscrizioniPanel.anno");
    } else {
      etaFiglio = calcoloEta / 12 + " ";
      mesi = getString("RiepilogoIscrizioniPanel.anni");
    }
    Label eta = new Label("eta", etaFiglio);
    Label mesiAnni = new Label("mesiAnni", mesi);
    Label scuola;
    Label classe;
    Label sezione;
    Label iscrizioneLabel;

    scuola =
        new Label(
            "scuola",
            iscritto.getStrutturaScolastica() == null
                ? null
                : StringUtils.lowerCase(iscritto.getCategoriaStrutturaScolastica())
                    + "  "
                    + StringUtils.lowerCase(iscritto.getStrutturaScolastica().toLowerCase()));

    classe =
        new Label(
            "classe", iscritto.getClasse() == null ? null : iscritto.getClasse().toLowerCase());

    sezione =
        new Label(
            "sezione", iscritto.getSezione() == null ? null : iscritto.getSezione().toLowerCase());

    iscrizioneLabel = new Label("iscritto", getTextIscrizione(iscritto));

    Label annoScolasticoLabel = new Label("annoScolastico", ricavaAnnoScolastico());

    scuola.setVisible(minore.getDatiIscrizioneAnnoCorrente());

    addOrReplace(eta);
    addOrReplace(mesiAnni);
    addOrReplace(icona);
    addOrReplace(scuola);
    addOrReplace(classe);
    addOrReplace(sezione);
    addOrReplace(icona);
    addOrReplace(dataNascita);
    addOrReplace(nomeCognome);
    addOrReplace(iscrizioneLabel).setRenderBodyOnly(true);
    addOrReplace(annoScolasticoLabel);
  }

  public static String ricavaAnnoScolastico() {

    Integer anno = CedoleLibrarieConPrivacyPage.ricavaAnnoScolasticoCorretto();
    Integer annoProssimo = anno + 1;
    return anno.toString() + "-" + annoProssimo;
  }

  private AttributeAppender getCssIconaMaschioFemmina(UtenteServiziRistorazione iscritto) {
    String css = "";
    if (iscritto.getSesso().equalsIgnoreCase(UtenteServiziRistorazione.SessoEnum.M.value())) {
      css = ICON_BIMBO;
    } else {
      css = ICON_BIMBA;
    }
    return new AttributeAppender("class", " " + css);
  }

  private IModel<?> getTextIscrizione(UtenteServiziRistorazione iscritto) {
    String testo = "";
    if (iscritto.getSesso().equalsIgnoreCase(UtenteServiziRistorazione.SessoEnum.M.value())) {
      testo = getString("RiepilogoIscrizioniPanel.iscritto.m") + ":";
    } else {
      testo = getString("RiepilogoIscrizioniPanel.iscritto.f") + ":";
    }
    return new Model<>(testo);
  }
}
