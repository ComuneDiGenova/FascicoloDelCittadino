package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.riepilogo;

import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.RichiestaPermessoPersonalizzato;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.panel.SintesiPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.dichiarazioni.DichiarazioniPermessiPersonalizzatiPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.documentiallegati.DocumentiAllegatiPermessiPersonalizzatiPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.soggetticoinvolti.SoggettiCoinvoltiPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.ubicazioneparcheggio.UbicazioneParcheggioPanel;
import org.apache.wicket.model.CompoundPropertyModel;

public class RiepilogoPermessiPersonalizzatiPanel extends BasePanel {

  private static final long serialVersionUID = -3021289773196275267L;

  SintesiPanel sintesiPanel;
  SoggettiCoinvoltiPanel soggettiCoinvoltiPanel;
  UbicazioneParcheggioPanel ubicazioneParcheggioPanel;
  DichiarazioniPermessiPersonalizzatiPanel dichiarazioniPermessiPersonalizzatiPanel;
  DocumentiAllegatiPermessiPersonalizzatiPanel documentiAllegatiPermessiPersonalizzatiPanel;

  private int idDomanda;

  public RiepilogoPermessiPersonalizzatiPanel(
      String id,
      CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel,
      int idDomanda) {
    super(id);
    // getSession().removeAttribute("listaPermessiDisabili");
    this.idDomanda = idDomanda;
    fillDati(richiestaPermessoPersonalizzatoModel);
    setOutputMarkupId(true);
  }

  @Override
  public void fillDati(Object dati) {

    boolean attivo = false;

    CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel =
        (CompoundPropertyModel<RichiestaPermessoPersonalizzato>) dati;

    sintesiPanel = new SintesiPanel("sintesiPanel", richiestaPermessoPersonalizzatoModel, attivo);
    addOrReplace(sintesiPanel);

    soggettiCoinvoltiPanel =
        new SoggettiCoinvoltiPanel(
            "soggettiCoinvoltiPanel", richiestaPermessoPersonalizzatoModel, attivo);
    soggettiCoinvoltiPanel.setEnabled(false);
    addOrReplace(soggettiCoinvoltiPanel);

    ubicazioneParcheggioPanel =
        new UbicazioneParcheggioPanel(
            "ubicazioneParcheggioPanel", richiestaPermessoPersonalizzatoModel, attivo);
    ubicazioneParcheggioPanel.setEnabled(false);
    addOrReplace(ubicazioneParcheggioPanel);

    // richiestaPermessoPersonalizzatoModel.getObject().setInSolaLettura(true);
    dichiarazioniPermessiPersonalizzatiPanel =
        new DichiarazioniPermessiPersonalizzatiPanel(
            "dichiarazioniPermessiPersonalizzatiPanel", richiestaPermessoPersonalizzatoModel);
    dichiarazioniPermessiPersonalizzatiPanel.setEnabled(false);
    addOrReplace(dichiarazioniPermessiPersonalizzatiPanel);

    documentiAllegatiPermessiPersonalizzatiPanel =
        new DocumentiAllegatiPermessiPersonalizzatiPanel(
            "documentiAllegatiPermessiPersonalizzatiPanel",
            richiestaPermessoPersonalizzatoModel,
            idDomanda,
            attivo);
    documentiAllegatiPermessiPersonalizzatiPanel.setEnabled(true);
    addOrReplace(documentiAllegatiPermessiPersonalizzatiPanel);
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();
  }
}
