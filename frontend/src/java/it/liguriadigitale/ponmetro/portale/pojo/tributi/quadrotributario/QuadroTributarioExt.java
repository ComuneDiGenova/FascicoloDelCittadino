package it.liguriadigitale.ponmetro.portale.pojo.tributi.quadrotributario;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import it.liguriadigitale.ponmetro.tributi.model.QuadroTributario;
import java.util.HashMap;

public class QuadroTributarioExt extends QuadroTributario {

  private static final long serialVersionUID = 1L;

  private HashMap<String, String> hashEsitoVerifica;

  String nomeTributo;

  // Bianco, Grigio, Rosso e Verde
  //
  // - come arriva
  // - Testo da fare vedere
  // - css badge legenda colore
  // - css colore icona pannello

  public static String[][] stati = {
    {
      "G",
      "POSIZIONE NON VERIFICATA",
      BasePanelGenericContent.CSS_BADGE_FDC_SECONDARY,
      BasePanelGenericContent.CSS_COLOR_FDC_SECONDARY
    },
    {
      "V",
      "SITUAZIONE CORRETTA",
      BasePanelGenericContent.CSS_BADGE_FDC_SUCCESS,
      BasePanelGenericContent.CSS_COLOR_FDC_SUCCESS
    },
    {
      "B",
      "POSIZIONE ASSENTE",
      BasePanelGenericContent.CSS_BADGE_FDC_SUCCESS,
      BasePanelGenericContent.CSS_COLOR_FDC_SUCCESS
    }, // was
    // CSS_COLOR_FDC_DARK
    {
      "R",
      "POSIZIONE IN ACCERTAMENTO",
      BasePanelGenericContent.CSS_BADGE_FDC_DANGER,
      BasePanelGenericContent.CSS_COLOR_FDC_DANGER
    }
  };

  public QuadroTributarioExt() {
    super();
    // init mapping
    hashEsitoVerifica = new HashMap<>();
    // Non esiste enum, so solo che arriva R G V o B
    hashEsitoVerifica.put(stati[0][0], stati[0][1]);
    hashEsitoVerifica.put(stati[1][0], stati[1][1]);
    hashEsitoVerifica.put(stati[2][0], stati[2][1]);
    hashEsitoVerifica.put(stati[3][0], stati[3][1]);
  }

  public String getStato() {
    return hashEsitoVerifica.get(super.getEsitoVerifica());
  }

  public Boolean isVerde() {
    return super.getEsitoVerifica().equalsIgnoreCase(stati[1][0]);
  }

  public Boolean isRosso() {
    return super.getEsitoVerifica().equalsIgnoreCase(stati[3][0]);
  }

  public Boolean isGrigio() {
    return super.getEsitoVerifica().equalsIgnoreCase(stati[0][0]);
  }

  public Boolean isBianco() {
    return super.getEsitoVerifica().equalsIgnoreCase(stati[2][0]);
  }

  public String getClasseCssVerde() {
    return stati[1][3];
  }

  public String getClasseCssRosso() {
    return stati[3][3];
  }

  public String getClasseCssGrigio() {
    return stati[0][3];
  }

  public String getClasseCssBianco() {
    return stati[2][3];
  }

  public Boolean hasValidUri() {
    return isVerde() || isRosso();
  }

  public void setNomeTributo(String nomeTributo) {
    this.nomeTributo = nomeTributo;
  }

  public String getNomeTributo() {
    return this.nomeTributo;
  }

  public String getColorForIcon() {
    if (isVerde()) {
      return getClasseCssVerde();
    }
    if (isRosso()) {
      return getClasseCssRosso();
    }
    if (isBianco()) {
      return getClasseCssBianco();
    }
    return getClasseCssGrigio();
  }
}
