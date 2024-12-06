package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mionucleo.panel;

import it.liguriadigitale.ponmetro.demografico.model.ItemRelazioneParentale;
import it.liguriadigitale.ponmetro.portale.pojo.enums.CpvParentTypeEnum;
import it.liguriadigitale.ponmetro.portale.pojo.enums.SorgenteDatiNucleoEnum;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mionucleo.panel.pojo.DatiParenteleCompleti;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mionucleo.panel.pojo.DatiParenteleSoloDaDemografico;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.time.LocalDate;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;

public class MioFamigliarePanel extends BasePanel {

  private static final long serialVersionUID = 7639460618247974239L;

  private static final String ICON_UOMO = "color-yellow col-3 icon-uomo";
  private static final String ICON_DONNA = "color-yellow col-3 icon-donna";
  private static final String ICON_BIMBA = "color-yellow col-3 icon-bimba";
  private static final String ICON_BIMBO = "color-yellow col-3 icon-bimbo";

  public MioFamigliarePanel(
      ItemRelazioneParentale famigliare,
      boolean figlioAutodichiarato,
      SorgenteDatiNucleoEnum sorgente) {
    super("mioFamigliare");

    fillDati(famigliare);
  }

  public MioFamigliarePanel(DatiParenteleCompleti datiParentele) {
    super("mioFamigliare");

    fillDati(datiParentele);
  }

  public MioFamigliarePanel(DatiParenteleSoloDaDemografico datiParenteleDaDemografico) {
    super("mioFamigliare");

    fillDati(datiParenteleDaDemografico);
  }

  @Override
  public void fillDati(Object dati) {
    DatiParenteleSoloDaDemografico datiParentele = (DatiParenteleSoloDaDemografico) dati;

    String codiceFiscale = datiParentele.getCodiceFiscale();
    String nome = datiParentele.getNomeCognome();
    LocalDate dataNascita = datiParentele.getDataNascita();
    String genere = datiParentele.getGenere();
    String natoA = datiParentele.getLuogoNascita();

    String parentelaDaDemografico =
        getDecodificaCodiceParentela(datiParentele.getItemRelazioneParentale());
    if (PageUtil.isStringValid(parentelaDaDemografico)
        && !datiParentele.getRelazione().equalsIgnoreCase("IS")) {
      parentelaDaDemografico = parentelaDaDemografico.concat(" dell' intestatario scheda");
    }

    addOrReplace(new NotEmptyLabel("parentela", parentelaDaDemografico).setRenderBodyOnly(true));

    addOrReplace(new NotEmptyLabel("cf", codiceFiscale));

    addOrReplace(new LocalDateLabel("natoIl", dataNascita));

    if (PageUtil.isStringValid(genere)) {
      if (genere.equalsIgnoreCase("M")) {
        addOrReplace(new Label("labelNato", getString("MioFamigliarePanel.natoA")));
      } else {
        addOrReplace(new Label("labelNato", getString("MioFamigliarePanel.nataA")));
      }
    } else {
      addOrReplace(new Label("labelNato", getString("MioFamigliarePanel.natoA")));
    }

    addOrReplace(new NotEmptyLabel("natoA", natoA));

    addOrReplace(new NotEmptyLabel("nomeParente", nome));

    WebMarkupContainer icona = new WebMarkupContainer("icona");
    icona.add(getCssIconaGenere(genere, dataNascita));
    addOrReplace(icona);
  }

  //	@Override
  //	public void fillDati(Object dati) {
  //		DatiParenteleCompleti datiParentele = (DatiParenteleCompleti) dati;
  //
  //		String codiceFiscale = "";
  //		String nome = "";
  //		LocalDate dataNascita = null;
  //		String genere = "";
  //		String natoA = "";
  //
  //		if(LabelFdCUtil.checkIfNotNull(datiParentele) &&
  // LabelFdCUtil.checkIfNotNull(datiParentele.getDatiFamigliare())
  //				&& LabelFdCUtil.checkIfNotNull(datiParentele.getDatiFamigliare().getDatiCittadino())) {
  //			codiceFiscale = datiParentele.getDatiFamigliare().getDatiCittadino().getCpvTaxCode();
  //			nome = datiParentele.getDatiFamigliare().getDatiCittadino().getRdfsLabel();
  //			dataNascita = datiParentele.getDatiFamigliare().getDatiCittadino().getCpvDateOfBirth();
  //			genere = datiParentele.getDatiFamigliare().getDatiCittadino().getCpvHasSex();
  //
  //
  //	if(LabelFdCUtil.checkIfNotNull(datiParentele.getDatiFamigliare().getDatiCittadino().getCpvHasBirthPlace()) && LabelFdCUtil.checkIfNotNull(datiParentele.getDatiFamigliare().getDatiCittadino().getCpvHasBirthPlace().getClvCity())) {
  //				natoA =
  // datiParentele.getDatiFamigliare().getDatiCittadino().getCpvHasBirthPlace().getClvCity().toLowerCase();
  //			}
  //		}
  //
  //		String parentelaDaDemografico =
  // getDecodificaCodiceParentela(datiParentele.getParentelaDaDemografico());
  //		if(PageUtil.isStringValid(parentelaDaDemografico) &&
  //				LabelFdCUtil.checkIfNotNull(datiParentele.getParentelaDaDemografico())
  //				&&
  //				!datiParentele.getParentelaDaDemografico().getCpvParentType().equalsIgnoreCase("IS")) {
  //			parentelaDaDemografico = parentelaDaDemografico.concat(" dell' intestatario scheda");
  //		}
  //
  //		addOrReplace(new NotEmptyLabel("parentela", parentelaDaDemografico)
  //				.setRenderBodyOnly(true));
  //
  //		addOrReplace(new NotEmptyLabel("cf", codiceFiscale));
  //
  //		addOrReplace(new LocalDateLabel("natoIl", dataNascita));
  //
  //		if(PageUtil.isStringValid(genere)) {
  //			if (genere.equalsIgnoreCase("M")) {
  //				addOrReplace(new Label("labelNato", getString("MioFamigliarePanel.natoA")));
  //			} else {
  //				addOrReplace(new Label("labelNato", getString("MioFamigliarePanel.nataA")));
  //			}
  //		}else {
  //			addOrReplace(new Label("labelNato", getString("MioFamigliarePanel.natoA")));
  //		}
  //
  //		addOrReplace(new NotEmptyLabel("natoA", natoA));
  //
  //		addOrReplace(new NotEmptyLabel("nomeParente",
  //				nome));
  //
  //		WebMarkupContainer icona = new WebMarkupContainer("icona");
  //		icona.add(getCssIconaGenere(genere, dataNascita));
  //		addOrReplace(icona);
  //
  //		String relazione =
  // getDecodificaCodiceParentela(datiParentele.getRelazioneConCittadinoLoggato());
  //
  //		String sorgenteStatoCivile = "";
  //		String sorgenteAutodichiarazione = "";
  //
  //		if(LabelFdCUtil.checkIfNotNull(datiParentele.getSorgente())) {
  //			if(datiParentele.getSorgente().equals(SorgenteDatiNucleoEnum.STATO_CIVILE)) {
  //				sorgenteStatoCivile = getTuoTua(genere).concat(" ").concat(relazione).concat("
  // ").concat(getString("MioFamigliarePanel.sorgenteStatoCivile"));
  //			}
  //		}
  //
  //		if(LabelFdCUtil.checkIfNotNull(datiParentele.isFiglioAutodichiarato()) &&
  // datiParentele.isFiglioAutodichiarato()) {
  //			sorgenteAutodichiarazione = getTuoTua(genere).concat(" ").concat(relazione).concat("
  // ").concat(getString("MioFamigliarePanel.sorgenteAutodichiarazione"));
  //		}
  //
  //		NotEmptyLabel	sorgenteStatoCivileLabel = new NotEmptyLabel("sorgenteStatoCivile",
  //				sorgenteStatoCivile);
  //		sorgenteStatoCivileLabel.setVisible(PageUtil.isStringValid(sorgenteStatoCivile));
  //		addOrReplace(sorgenteStatoCivileLabel);
  //
  //		NotEmptyLabel	sorgenteAutodichiarazioneLabel = new NotEmptyLabel("sorgenteAutodichiarazione",
  //				sorgenteAutodichiarazione);
  //		sorgenteAutodichiarazioneLabel.setVisible(PageUtil.isStringValid(sorgenteAutodichiarazione));
  //		addOrReplace(sorgenteAutodichiarazioneLabel);
  //
  //	}

  private String getDecodificaCodiceParentela(ItemRelazioneParentale famigliare) {
    CpvParentTypeEnum parentela = CpvParentTypeEnum.fromValue(famigliare.getCpvParentType());

    String descrizioneParentela = parentela.getDescription();
    String cfSex = famigliare.getCpvComponentTaxCode().substring(9, 11);
    if (parentela.name().equals(CpvParentTypeEnum.FG.name()) && Integer.parseInt(cfSex) >= 41) {
      descrizioneParentela = descrizioneParentela.replace("o", "a");
    }

    return descrizioneParentela;
  }

  private AttributeAppender getCssIconaGenere(String genere, LocalDate dataNascita) {
    String css = "";

    if (genere.equalsIgnoreCase("F")) {
      if (LocalDateUtil.isMaggiorenne(dataNascita)) {
        css = ICON_DONNA;
      } else {
        css = ICON_BIMBA;
      }
    } else if (genere.equalsIgnoreCase("M")) {
      if (LocalDateUtil.isMaggiorenne(dataNascita)) {
        css = ICON_UOMO;
      } else {
        css = ICON_BIMBO;
      }
    }
    return new AttributeAppender("class", " " + css);
  }

  private String getTuoTua(String genere) {
    String tuoTua = "";

    if (PageUtil.isStringValid(genere)) {
      if (genere.equalsIgnoreCase("F")) {
        tuoTua = "Tua";
      } else if (genere.equalsIgnoreCase("M")) {
        tuoTua = "Tuo";
      }
    } else {
      tuoTua = "Tuo";
    }

    return tuoTua;
  }
}
