package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.pagamenti.panel;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import java.util.ArrayList;
import java.util.List;

public class PagamentiBollettiniMensaRicercaPanel extends BasePanel {

  private static final long serialVersionUID = 4531051170079261035L;

  public PagamentiBollettiniMensaRicercaPanel(String id) {
    super(id);
    setOutputMarkupId(true);
    initPanel();
  }

  private void initPanel() {
    fillDati(null);
  }

  @Override
  public void fillDati(Object dati) {
    // initAndAddComboSceltaAnnoPagamentoBollettini();
    // creaAndAddBottoneCerca();
  }

  /*
   * Lista anni selezionabili da combo box per scelta dell'anno
   */
  @SuppressWarnings("unused")
  private List<Integer> getListaAnniSelezionabili(Integer quantiAnni) {
    Integer annoCorrente = LocalDateUtil.today().getYear();
    Integer annoPrimoAnno = annoCorrente - quantiAnni;
    List<Integer> listaAnni = new ArrayList<>();
    while (annoCorrente > annoPrimoAnno++) {
      listaAnni.add(annoPrimoAnno);
    }
    return listaAnni;
  }

  /*
   * private Component refreshAndGetPannelloRicerca(boolean reset) { Integer
   * annoScelto = comboSceltaAnno.getModelObject();
   * log.debug("[formComboSceltaAnnoPartitario] newSelection=" + annoScelto);
   * PagamentiBollettiniMensaPage parent = (PagamentiBollettiniMensaPage)
   * getPage(); if (reset) {
   * parent.getPanelRisultati().resetPanel(annoScelto); } else {
   * parent.getPanelRisultati().updatePanel(annoScelto); } return
   * parent.getPanelRisultati(); }
   *
   * private void initAndAddComboSceltaAnnoPagamentoBollettini() {
   * IModel<Integer> modello = new Model<Integer>();
   *
   * comboSceltaAnno = new DropDownChoice<Integer>(
   * "comboSceltaAnnoPagamenti", modello, getListaAnniSelezionabili(5), new
   * AnnoPagamentiRenderer());
   *
   * comboSceltaAnno.setOutputMarkupId(true); comboSceltaAnno.add(new
   * AjaxFormComponentUpdatingBehavior("onchange") { private static final long
   * serialVersionUID = 1L;
   *
   * @Override protected void onUpdate(AjaxRequestTarget target) {
   * target.add(refreshAndGetPannelloRicerca(true)); } });
   * add(comboSceltaAnno); }
   *
   * private void creaAndAddBottoneCerca() { AjaxLink<Void> bottoneCerca = new
   * AjaxLink<Void>("bottoneCerca") { private static final long
   * serialVersionUID = 1L;
   *
   * @Override public void onClick(final AjaxRequestTarget target) {
   * target.add(refreshAndGetPannelloRicerca(false)); } };
   * bottoneCerca.setOutputMarkupId(true); add(bottoneCerca); }
   */

}
