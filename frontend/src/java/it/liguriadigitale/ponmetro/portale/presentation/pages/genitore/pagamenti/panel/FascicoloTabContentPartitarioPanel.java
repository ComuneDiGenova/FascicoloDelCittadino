package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.pagamenti.panel;

import it.liguriadigitale.ponmetro.portale.presentation.components.label.ImportoEuroLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiPagamentiPartitario;
import java.math.BigDecimal;
import java.util.List;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class FascicoloTabContentPartitarioPanel extends BasePanel {

  private static final long serialVersionUID = 841998179909061713L;

  List<DatiPagamentiPartitario> datiPagamentiPartitario;

  public FascicoloTabContentPartitarioPanel(String id) {
    super(id);

    setOutputMarkupId(true);
  }

  public FascicoloTabContentPartitarioPanel(
      String id, List<DatiPagamentiPartitario> datiPagamentiPartitario) {
    super(id);

    this.datiPagamentiPartitario = datiPagamentiPartitario;

    fillDati(datiPagamentiPartitario);

    setOutputMarkupId(true);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {

    List<DatiPagamentiPartitario> listaDatiPagamentiPartitario =
        (List<DatiPagamentiPartitario>) dati;

    Label listaPartitarioVuota =
        new Label(
            "listaPartitarioVuota",
            getString("FascicoloTabContentPartitarioPanel.messaggioRisultatiRicercaListaVuota"));
    listaPartitarioVuota.setVisible(listaDatiPagamentiPartitario.isEmpty());
    addOrReplace(listaPartitarioVuota);

    WebMarkupContainer tabellaPartitario = new WebMarkupContainer("tabellaPartitario");
    tabellaPartitario.setVisible(!listaDatiPagamentiPartitario.isEmpty());
    tabellaPartitario.setOutputMarkupId(true);
    addOrReplace(tabellaPartitario);

    ListView<DatiPagamentiPartitario> listView =
        new ListView<DatiPagamentiPartitario>("listaPartitario", listaDatiPagamentiPartitario) {

          private static final long serialVersionUID = 1L;

          @Override
          protected void populateItem(ListItem<DatiPagamentiPartitario> item) {
            DatiPagamentiPartitario datiPagamento = item.getModelObject();

            Integer annoAux = Integer.parseInt(datiPagamento.getAnno()) + 1;
            String anno = datiPagamento.getAnno().concat("/").concat(String.valueOf(annoAux));
            item.add(new Label("valoreAnno", anno));
            item.add(new Label("valorePeriodo", datiPagamento.getPeriodo()));
            item.add(new Label("valoreDescrizione", datiPagamento.getDescrizione()));

            String valoreDataOperazione = "";
            if (LabelFdCUtil.checkIfNotNull(datiPagamento.getDataOperazione())) {
              valoreDataOperazione =
                  LocalDateUtil.getDataFormatoEuropeo(datiPagamento.getDataOperazione());
            }

            item.add(new Label("valoreDataOperazione", valoreDataOperazione));
            item.add(new ImportoEuroLabel("valoreCredito", datiPagamento.getCrediti()));
            item.add(new ImportoEuroLabel("valoreDebito", datiPagamento.getDebiti()));
          }
        };
    tabellaPartitario.add(listView);

    LinkDinamicoLaddaFunzione<Object> btnVaiPagamenti =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnVaiPagamenti",
            new LinkDinamicoFunzioneData(
                "FascicoloTabContentPartitarioPanel.vaiPagamenti",
                "PagamentiBollettiniMensaPage",
                "FascicoloTabContentPartitarioPanel.vaiPagamenti"),
            null,
            FascicoloTabContentPartitarioPanel.this,
            "color-orange col-auto icon-tributi");
    btnVaiPagamenti.setVisible(!calcolaInRegola(listaDatiPagamentiPartitario));
    addOrReplace(btnVaiPagamenti);
  }

  private boolean calcolaInRegola(List<DatiPagamentiPartitario> listaDatiPagamentiPartitario) {
    boolean inRegola;

    BigDecimal creditiTotali = BigDecimal.ZERO;
    BigDecimal debitiTotali = BigDecimal.ZERO;
    BigDecimal calcoloDebitiCrediti = BigDecimal.ZERO;
    for (DatiPagamentiPartitario elem : listaDatiPagamentiPartitario) {
      creditiTotali = creditiTotali.add(elem.getCrediti());
      debitiTotali = debitiTotali.add(elem.getDebiti());
    }
    calcoloDebitiCrediti = debitiTotali.subtract(creditiTotali);
    if (calcoloDebitiCrediti.compareTo(BigDecimal.ZERO) > 0) {
      inRegola = false;
    } else {
      inRegola = true;
    }

    return inRegola;
  }
}
