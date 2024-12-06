package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.pagamenti.panel;

import it.liguriadigitale.ponmetro.portale.pojo.pagamentiristorazione.PartitarioMensa;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiPagamentiPartitario;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiStatoPagamenti;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class PartitarioIscrittoMensaRisultatiRicercaPanel extends BasePanel {

  private static final long serialVersionUID = 1136007992174690185L;

  @SuppressWarnings("unused")
  private PartitarioMensa partitarioCompleto;

  public PartitarioIscrittoMensaRisultatiRicercaPanel(String id, final PartitarioMensa partitario) {
    super(id);
    setOutputMarkupId(true);

    partitarioCompleto = partitario;

    fillDati(partitario);
  }

  public PartitarioIscrittoMensaRisultatiRicercaPanel(String id) {
    this(id, null);
  }

  @SuppressWarnings("unused")
  @Override
  public void fillDati(Object dati) {

    PartitarioMensa partitario = (PartitarioMensa) dati;

    // FDCOMGE-61
    /*
     * WebMarkupContainer containerNonImpegnato = new
     * WebMarkupContainer("containerNonImpegnato"); boolean
     * containerNonImpegnatoVisible = false; if
     * (partitario.getIscritto().getCodiceFiscaleImpegnato() != null) { if
     * (partitario.getIscritto().getCodiceFiscaleImpegnato()
     * .equalsIgnoreCase(getUtente().getCodiceFiscaleOperatore())) {
     * containerNonImpegnatoVisible = false; } else {
     * containerNonImpegnatoVisible = true; } } else {
     * containerNonImpegnatoVisible = true; }
     * containerNonImpegnato.setVisible(containerNonImpegnatoVisible);
     * add(containerNonImpegnato);
     */

    WebMarkupContainer containerDati = new WebMarkupContainer("containerDati");
    String bambino =
        partitario
            .getIscritto()
            .getNome()
            .concat(" ")
            .concat(partitario.getIscritto().getCognome());
    Label nomeBambino = new Label("nomeBambino", bambino);
    nomeBambino.setVisible(!bambino.isEmpty());
    containerDati.addOrReplace(nomeBambino);
    String impegnato = "";
    if (partitario.getIscritto().getNomeImpegnato() != null
        && partitario.getIscritto().getCognomeImpegnato() != null) {
      impegnato =
          partitario
              .getIscritto()
              .getNomeImpegnato()
              .concat(" ")
              .concat(partitario.getIscritto().getCognomeImpegnato());
    }
    Label nomeImpegnato = new Label("nomeImpegnato", impegnato);
    nomeImpegnato.setVisible(!impegnato.isEmpty());
    containerDati.addOrReplace(nomeImpegnato);

    // FDCOMGE-61
    /*
     * boolean visibilitaContainerDati = false; if
     * (partitario.getIscritto().getCodiceFiscaleImpegnato() != null) { if
     * (partitario.getIscritto().getCodiceFiscaleImpegnato()
     * .equalsIgnoreCase(getUtente().getCodiceFiscaleOperatore())) {
     * visibilitaContainerDati = true; } }
     * containerDati.setVisible(visibilitaContainerDati);
     */

    addOrReplace(containerDati);

    List<DatiPagamentiPartitario> listaDatiPagamentiPartitario =
        partitario != null
            ? partitario.getListDatiPagamentiPartitarioAnnoDaVisualizzareNeiRisultati()
            : new ArrayList<>();

    Integer annoCorrente = LocalDateUtil.today().getYear();
    final Integer anniDaMostrare = 5;
    Integer ultimoAnnoDaMostrare = annoCorrente - anniDaMostrare;
    List<ITab> listaTabsAnni = new ArrayList<>();

    log.debug("CP anno corrente = " + annoCorrente);
    log.debug("CP ultimo anno da mostrare = " + ultimoAnnoDaMostrare);

    while (annoCorrente > ultimoAnnoDaMostrare) {
      final String anno = annoCorrente.toString();

      List<DatiPagamentiPartitario> listaDatiPagamentoPartitarioFiltrataPerAnno =
          partitario.getListDatiPagamentiPartitario().stream()
              .filter(elem -> elem.getAnno().equalsIgnoreCase(anno))
              .collect(Collectors.toList());

      DatiStatoPagamenti statoPagamentoAnnoPartitario =
          partitario.getListaStatoPagamentiPartitario().stream()
              .filter(elem -> elem.getAnno().equalsIgnoreCase(anno))
              .findAny()
              .orElse(null);
      log.debug("CP statoPagamentoAnnoPartitario = " + statoPagamentoAnnoPartitario);
      String inRegola = "";
      if (statoPagamentoAnnoPartitario != null && statoPagamentoAnnoPartitario.getStato() != null) {
        inRegola = statoPagamentoAnnoPartitario.getStato();
      }

      /*
       * BigDecimal creditiTotali = BigDecimal.ZERO; BigDecimal
       * debitiTotali = BigDecimal.ZERO; BigDecimal calcoloDebitiCrediti =
       * BigDecimal.ZERO; for (DatiPagamentiPartitario elem :
       * listaDatiPagamentoPartitarioFiltrataPerAnno) { creditiTotali =
       * creditiTotali.add(elem.getCrediti()); debitiTotali =
       * debitiTotali.add(elem.getDebiti()); } calcoloDebitiCrediti =
       * debitiTotali.subtract(creditiTotali); String inRegola = ""; if
       * (calcoloDebitiCrediti.compareTo(BigDecimal.ZERO) > 0) { inRegola
       * = "Non in regola"; } else { inRegola = "In regola"; }
       */

      String annoTab = "";
      Integer auxAnnoScolastico = annoCorrente + 1;
      String annoScolastico = anno.concat("/").concat(String.valueOf(auxAnnoScolastico));
      if (!listaDatiPagamentoPartitarioFiltrataPerAnno.isEmpty()) {
        annoTab = annoScolastico.concat(" - ").concat(inRegola);
      } else {
        annoTab = annoScolastico;
      }

      AbstractTab tab =
          new AbstractTab(new Model<>(annoTab)) {

            private static final long serialVersionUID = -6472076907400482070L;

            @Override
            public Panel getPanel(String panelId) {
              return new FascicoloTabContentPartitarioPanel(
                  panelId, listaDatiPagamentoPartitarioFiltrataPerAnno);
            }
          };
      if (listaDatiPagamentoPartitarioFiltrataPerAnno != null
          && !listaDatiPagamentoPartitarioFiltrataPerAnno.isEmpty()) {
        listaTabsAnni.add(tab);
      }
      annoCorrente = annoCorrente - 1;
    }

    FascicoloTabPanel<ITab> fascicoloTabPanel =
        new FascicoloTabPanel<ITab>("tabsPanel", listaTabsAnni);
    fascicoloTabPanel.setOutputMarkupId(true);

    // FDCOMGE-61
    /*
     * boolean visibilitaPartitario = false; if
     * (partitario.getIscritto().getCodiceFiscaleImpegnato() != null) { if
     * (partitario.getIscritto().getCodiceFiscaleImpegnato()
     * .equalsIgnoreCase(getUtente().getCodiceFiscaleOperatore())) {
     * visibilitaPartitario = true; } }
     * fascicoloTabPanel.setVisible(visibilitaPartitario);
     */

    addOrReplace(fascicoloTabPanel);

    Label labelListaTabsPartitarioVuota =
        new Label(
            "listaTabsPartitarioVuota",
            getString(
                "PartitarioIscrittoMensaRisultatiRicercaPanel.messaggioRisultatiRicercaListaVuota"));
    labelListaTabsPartitarioVuota.setVisible(listaTabsAnni.isEmpty());
    addOrReplace(labelListaTabsPartitarioVuota);
  }
}
