package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.form.panel;

import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.SiNoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.verbali.MarcheAutoveicoliDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.PropertyModel;

public class TargaErrataPanel extends BasePanel {

  private static final long serialVersionUID = 2781955826041290508L;

  WebMarkupContainer wmkMarcaModello;
  MarcheAutoveicoliDropDownChoice marcheAutoveicoli;
  FdCTextField modello;
  SiNoDropDownChoice miaMacchina;
  DatiRichiestaIstanzaPl datiRichiestaIstanza;

  public TargaErrataPanel(String id, DatiRichiestaIstanzaPl datiRichiestaIstanza) {
    super(id);
    this.datiRichiestaIstanza = datiRichiestaIstanza;
    fillDati(null);
  }

  @Override
  public void fillDati(Object dati) {

    String veicolo = "";
    //		if(datiRichiestaIstanza.getDatiCompletiVerbale().getDettaglioVerbale().getTipoVeicolo() !=
    // null) {
    //			veicolo =
    // datiRichiestaIstanza.getDatiCompletiVerbale().getDettaglioVerbale().getTipoVeicolo();
    //		}
    //
    //		if(datiRichiestaIstanza.getDatiCompletiVerbale().getDettaglioVerbale().getModello() != null)
    // {
    //			veicolo =  veicolo + " " +
    // datiRichiestaIstanza.getDatiCompletiVerbale().getDettaglioVerbale().getModello();
    //		}

    if (datiRichiestaIstanza.getDatiCompletiVerbale().getDettaglioVerbale().getTarga() != null) {
      veicolo =
          veicolo
              + " targato: "
              + datiRichiestaIstanza.getDatiCompletiVerbale().getDettaglioVerbale().getTarga();
    }

    wmkMarcaModello = new WebMarkupContainer("wmkMarcaModello");
    wmkMarcaModello.setOutputMarkupId(true);
    wmkMarcaModello.setOutputMarkupPlaceholderTag(true);
    addOrReplace(wmkMarcaModello);

    /*
    		Label lblMiaMacchina =  new Label("lblMiaMacchina",Model.of("E' di tua proprietà il veicolo" + veicolo +" ?"));
    		addOrReplace(lblMiaMacchina);

    		miaMacchina = new SiNoDropDownChoice<>("miaMacchina",new PropertyModel<String>(datiRichiestaIstanza, "miaMacchina"));
    		addOrReplace(miaMacchina);

    		miaMacchina.add(new AjaxFormComponentUpdatingBehavior("change") {

    			private static final long serialVersionUID = 4567199981239849845L;

    			@Override
    			protected void onUpdate(AjaxRequestTarget target) {

    				marcheAutoveicoli.setRequired(miaMacchina.getValue().equalsIgnoreCase("1"));
    				modello.setRequired(miaMacchina.getValue().equalsIgnoreCase("1"));
    				target.add(wmkMarcaModello);

    			}
    		});
    */
    datiRichiestaIstanza.setMiaMacchina("SI");
    marcheAutoveicoli =
        new MarcheAutoveicoliDropDownChoice(
            "marcheAutoveicoli", new PropertyModel<String>(datiRichiestaIstanza, "marca"));
    marcheAutoveicoli.setRequired(true);
    marcheAutoveicoli.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            // TODO Auto-generated method stub

            datiRichiestaIstanza.setMarca(marcheAutoveicoli.getModelObject());
            target.add(wmkMarcaModello);
          }
        });

    // datiRichiestaIstanza.setMarca(marcheAutoveicoli.getgetModelObject());

    wmkMarcaModello.addOrReplace(marcheAutoveicoli);

    modello =
        new FdCTextField(
            "modello", new PropertyModel(datiRichiestaIstanza, "modello"), TargaErrataPanel.this);
    modello.setRequired(true);
    wmkMarcaModello.addOrReplace(modello);

    TextArea<String> note =
        new TextArea<String>(
            "note", new PropertyModel<String>(datiRichiestaIstanza, "noteTargaErrata"));
    addOrReplace(note);
  }
}
