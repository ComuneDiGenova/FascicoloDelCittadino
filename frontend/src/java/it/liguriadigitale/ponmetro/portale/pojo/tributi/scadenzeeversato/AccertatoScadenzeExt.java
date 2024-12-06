package it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato;

import it.liguriadigitale.ponmetro.portale.pojo.util.PojoUtils;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tributi.model.Accertato;
import it.liguriadigitale.ponmetro.tributi.model.VersamentiAtto;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AccertatoScadenzeExt extends Accertato {

  private static final long serialVersionUID = 1L;

  public static String[][] stati = {
    {
      Accertato.StatoPagamentoEnum.NESSUN_PAGAMENTO.value(),
      "NESSUN PAGAMENTO",
      BasePanelGenericContent.CSS_BADGE_FDC_DARK,
      BasePanelGenericContent.CSS_COLOR_FDC_DARK
    },
    {
      Accertato.StatoPagamentoEnum.PAGATO_CORRETTAMENTE.value(),
      " PAGATO CORRETTAMENTE",
      BasePanelGenericContent.CSS_BADGE_FDC_SUCCESS,
      BasePanelGenericContent.CSS_COLOR_FDC_SUCCESS
    },
    {
      Accertato.StatoPagamentoEnum.PAGATO_IN_ECCEDENZA.value(),
      "PAGATO IN ECCEDENZA",
      BasePanelGenericContent.CSS_BADGE_FDC_WARNING,
      BasePanelGenericContent.CSS_COLOR_FDC_WARNING
    },
    {
      Accertato.StatoPagamentoEnum.PAGATO_PARZIALMENTE.value(),
      "PAGATO PARZIALMENTE",
      BasePanelGenericContent.CSS_BADGE_FDC_PRIMARY,
      BasePanelGenericContent.CSS_COLOR_FDC_PRIMARY
    }
  };

  public List<VersamentiAttoScadenzeExt> getVersamentiAttoScadenzeExt() {
    if (super.getVersamentiAtto() == null) return null;

    Stream<VersamentiAtto> stream = super.getVersamentiAtto().stream();

    return stream
        .map(parent -> PojoUtils.copyAndReturn(new VersamentiAttoScadenzeExt(), parent))
        .collect(Collectors.toList());
  }

  public String getTitolo() {
    return "Accertamento";
  }

  @Override
  public String getDataScadenza() {
    return PageUtil.convertiAAAAminusMMminusDDFormatoData(super.getDataScadenza());
  }

  @Override
  public String getDataScadenza90gg() {
    return PageUtil.convertiAAAAminusMMminusDDFormatoData(super.getDataScadenza90gg());
  }

  public String getDovutoAttoPienoL() {
    return PageUtil.convertiImportoToEuroZeroWantNull(super.getDovutoAttoPieno());
  }

  public String getDovutoAttoRidottoL() {
    return PageUtil.convertiImportoToEuroZeroWantNull(super.getDovutoAttoRidotto());
  }

  public String getDataNotificaAttoString() {
    return super.getDataNotificaAtto();
  }

  // Devo ridefinire la set perche la copy properties usa la get del parent
  // che ritorna una stringa, e la set del parent la sua enum
  public void setStatoPagamento(String stato) {
    super.setStatoPagamento(Accertato.StatoPagamentoEnum.fromValue(stato));
  }

  @Override
  public String getStatoPagamento() {
    if (isNessunPagamento()) {
      return stati[0][1];
    }
    if (isPagatoCorrettamente()) {
      return stati[1][1];
    }
    if (isPagatoInEccedenza()) {
      return stati[2][1];
    }
    return stati[3][1];
  }

  public Boolean isNessunPagamento() {
    return Accertato.StatoPagamentoEnum.NESSUN_PAGAMENTO
        .value()
        .equalsIgnoreCase(super.getStatoPagamento());
  }

  public Boolean isPagatoCorrettamente() {
    return Accertato.StatoPagamentoEnum.PAGATO_CORRETTAMENTE
        .value()
        .equalsIgnoreCase(super.getStatoPagamento());
  }

  public Boolean isPagatoInEccedenza() {
    return Accertato.StatoPagamentoEnum.PAGATO_IN_ECCEDENZA
        .value()
        .equalsIgnoreCase(super.getStatoPagamento());
  }

  public Boolean isPagatoParzialmente() {
    return Accertato.StatoPagamentoEnum.PAGATO_PARZIALMENTE
        .value()
        .equalsIgnoreCase(super.getStatoPagamento());
  }

  public String getColorForIcon() {
    if (isNessunPagamento()) {
      return getClasseCssNessunPagamento();
    }
    if (isPagatoCorrettamente()) {
      return getClasseCssPagatoCorrettamente();
    }
    if (isPagatoInEccedenza()) {
      return getClasseCssPagatoInEccedenza();
    }
    return getClasseCssPagatoParzialmente();
  }

  public String getClasseCssNessunPagamento() {
    return stati[0][3];
  }

  public String getClasseCssPagatoCorrettamente() {
    return stati[1][3];
  }

  public String getClasseCssPagatoInEccedenza() {
    return stati[2][3];
  }

  public String getClasseCssPagatoParzialmente() {
    return stati[3][3];
  }

  public String getBaseIcon() {
    return BasePanelGenericContent.ALL_ICON_IOCON_SCADENZE_ACCER;
  }
}
