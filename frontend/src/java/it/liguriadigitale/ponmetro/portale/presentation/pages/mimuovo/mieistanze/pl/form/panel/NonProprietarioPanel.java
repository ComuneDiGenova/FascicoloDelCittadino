package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.form.panel;

import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.SiNoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCLocalDateField;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import java.time.LocalDate;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.PropertyModel;

public class NonProprietarioPanel extends BasePanel {

  private static final long serialVersionUID = 2781955826041290508L;

  SiNoDropDownChoice maiMia;
  FdCLocalDateField dataInizioProprieta;
  FdCLocalDateField dataFineProprieta;

  WebMarkupContainer wmkDataInizioFineProrieta;

  public NonProprietarioPanel(String id, DatiRichiestaIstanzaPl datiRichiestaIstanza) {
    super(id);
    fillDati(datiRichiestaIstanza);
  }

  @Override
  public void fillDati(Object dati) {

    DatiRichiestaIstanzaPl datiRichiestaIstanza = (DatiRichiestaIstanzaPl) dati;

    /*
    String veicolo = "";
    if(datiRichiestaIstanza.getDatiCompletiVerbale().getDettaglioVerbale().getTipoVeicolo() != null) {
    	veicolo = datiRichiestaIstanza.getDatiCompletiVerbale().getDettaglioVerbale().getTipoVeicolo();
    }


    if(datiRichiestaIstanza.getDatiCompletiVerbale().getDettaglioVerbale().getModello() != null) {
    	veicolo =  veicolo + " " + datiRichiestaIstanza.getDatiCompletiVerbale().getDettaglioVerbale().getModello();
    }

    if(datiRichiestaIstanza.getDatiCompletiVerbale().getDettaglioVerbale().getTarga() != null) {
    	veicolo = veicolo + " targata: " + datiRichiestaIstanza.getDatiCompletiVerbale().getDettaglioVerbale().getTarga();
    }*/

    wmkDataInizioFineProrieta = new WebMarkupContainer("wmkDataInizioFineProrieta");
    wmkDataInizioFineProrieta.setOutputMarkupId(true);
    wmkDataInizioFineProrieta.setOutputMarkupPlaceholderTag(true);
    addOrReplace(wmkDataInizioFineProrieta);

    datiRichiestaIstanza.setMaiMia("NO");
    /*
    Label lblMaiMia =  new Label("lblMaiMia",Model.of("E' tua di tua proprietà la " + veicolo +" ?"));
    addOrReplace(lblMaiMia);

    maiMia = new SiNoDropDownChoice<>("maiMia", new PropertyModel<String>(datiRichiestaIstanza, "maiMia"));
    addOrReplace(maiMia);


    maiMia.add(new AjaxFormComponentUpdatingBehavior("change") {

    	private static final long serialVersionUID = 4567199981239849845L;

    	@Override
    	protected void onUpdate(AjaxRequestTarget target) {

    		dataInizioProprieta = new FdCLocalDateField("dataInizioProprieta",
    				new PropertyModel<LocalDate>(datiRichiestaIstanza, "dataInizioProprieta"), NonProprietarioPanel.this);
    		wmkDataInizioFineProrieta.addOrReplace(dataInizioProprieta);

    		dataFineProprieta = new FdCLocalDateField("dataFineProprieta",
    				new PropertyModel<LocalDate>(datiRichiestaIstanza, "dataFineProprieta"), NonProprietarioPanel.this);
    		wmkDataInizioFineProrieta.addOrReplace(dataFineProprieta);

    		//dataInizioProprieta.getTextField().setRequired(maiMia.getValue().equalsIgnoreCase("1"));
    		//dataFineProprieta.getTextField().setRequired(maiMia.getValue().equalsIgnoreCase("1"));




    		target.add(dataInizioProprieta,dataFineProprieta);

    	}
    });
    */

    dataInizioProprieta =
        new FdCLocalDateField(
            "dataInizioProprieta",
            new PropertyModel<LocalDate>(datiRichiestaIstanza, "dataInizioProprieta"),
            NonProprietarioPanel.this);
    wmkDataInizioFineProrieta.addOrReplace(dataInizioProprieta);

    dataFineProprieta =
        new FdCLocalDateField(
            "dataFineProprieta",
            new PropertyModel<LocalDate>(datiRichiestaIstanza, "dataFineProprieta"),
            NonProprietarioPanel.this);
    wmkDataInizioFineProrieta.addOrReplace(dataFineProprieta);
  }
  /*
  @Override
  protected void onBeforeRender() {
  	super.onBeforeRender();
  	//dataInizioProprieta.getTextField().setRequired(maiMia.getValue().equalsIgnoreCase("1"));
  	//dataFineProprieta.getTextField().setRequired(maiMia.getValue().equalsIgnoreCase("1"));
  }*/

}
