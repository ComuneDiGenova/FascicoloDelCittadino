package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mionucleo.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.ItemRelazioneParentale;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.LoadData;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mionucleo.panel.pojo.DatiParenteleSoloDaDemografico;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;

public class MioNucleoPanel extends BasePanel {

  private static final long serialVersionUID = 7299381914252709748L;

  public MioNucleoPanel(String id) {
    super(id);
    @SuppressWarnings("unchecked")
    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);
    fillDati(LoadData.caricaMieiDatiResidente(getSession()));
  }

  @Override
  public void fillDati(Object dati) {
    Residente residente = (Residente) dati;

    // versione con persone da demografico
    List<ItemRelazioneParentale> listaPersoneDaDemografico =
        new ArrayList<ItemRelazioneParentale>();

    if (LabelFdCUtil.checkIfNotNull(getUtente().getDatiCittadinoResidente())
        && LabelFdCUtil.checkIfNotNull(
            getUtente().getDatiCittadinoResidente().getCpvInRegisteredFamily())
        && LabelFdCUtil.checkIfNotNull(
            getUtente()
                .getDatiCittadinoResidente()
                .getCpvInRegisteredFamily()
                .getCpvBelongsToFamily())) {
      listaPersoneDaDemografico =
          getUtente()
              .getDatiCittadinoResidente()
              .getCpvInRegisteredFamily()
              .getCpvBelongsToFamily();

      listaPersoneDaDemografico.removeIf(
          elem ->
              PageUtil.isStringValid(elem.getCpvParentType())
                  && elem.getCpvParentType().equalsIgnoreCase("IC")
                  && PageUtil.isStringValid(elem.getCpvComponentTaxCode())
                  && !elem.getCpvComponentTaxCode()
                      .equalsIgnoreCase(getUtente().getCodiceFiscaleOperatore()));
    }

    ListView<ItemRelazioneParentale> listViewConPersoneDaDemografico =
        new ListView<ItemRelazioneParentale>("nucleo", listaPersoneDaDemografico) {

          private static final long serialVersionUID = 7005458124768335776L;

          @Override
          protected void populateItem(ListItem<ItemRelazioneParentale> item) {
            ItemRelazioneParentale famigliare = item.getModelObject();

            String relazione = famigliare.getCpvParentType();
            String codiceFiscale = famigliare.getCpvComponentTaxCode();

            DatiParenteleSoloDaDemografico datiParenteleDaDemografico =
                new DatiParenteleSoloDaDemografico();
            datiParenteleDaDemografico.setCodiceFiscale(codiceFiscale);
            datiParenteleDaDemografico.setRelazione(relazione);
            datiParenteleDaDemografico.setItemRelazioneParentale(famigliare);

            Residente datiResidente = null;
            try {
              datiResidente =
                  ServiceLocator.getInstance()
                      .getServizioDemografico()
                      .getDatiResidente(codiceFiscale);
            } catch (BusinessException | ApiException e1) {
              log.error("Errore dati nucleo in mio nucleo: " + e1.getMessage());
            }

            if (LabelFdCUtil.checkIfNotNull(datiResidente)) {
              datiParenteleDaDemografico.setNomeCognome(datiResidente.getRdfsLabel());
              datiParenteleDaDemografico.setDataNascita(datiResidente.getCpvDateOfBirth());
              datiParenteleDaDemografico.setGenere(datiResidente.getCpvHasSex());

              if (LabelFdCUtil.checkIfNotNull(datiResidente.getCpvHasBirthPlace())) {
                datiParenteleDaDemografico.setLuogoNascita(
                    datiResidente.getCpvHasBirthPlace().getClvCity());
              }
            }

            try {
              MioFamigliarePanel panel = new MioFamigliarePanel(datiParenteleDaDemografico);

              item.add(panel);
            } catch (Exception e) {
              EmptyPanel emptyPanel = new EmptyPanel("mioFamigliare");
              emptyPanel.setVisible(false);
              item.add(emptyPanel);
            }
          }
        };
    addOrReplace(listViewConPersoneDaDemografico);

    // versione con persone da demografico, da stato civile e da autodichirazioni

    //		List<ComponenteNucleo> listaNucleo = getUtente().getNucleoFamiliareAllargato();
    //
    //		ListView<ComponenteNucleo> listView = new ListView<ComponenteNucleo>("nucleo", listaNucleo)
    // {
    //
    //			private static final long serialVersionUID = 7005458124768335776L;
    //
    //			@Override
    //			protected void populateItem(ListItem<ComponenteNucleo> item) {
    //				ComponenteNucleo famigliare = item.getModelObject();
    //
    //				ItemRelazioneParentale relazione = famigliare.getRelazione();
    //				SorgenteDatiNucleoEnum	sorgente = 	famigliare.getSorgente();
    //				boolean figlioAutodichiarato = false;
    //
    //				if(LabelFdCUtil.checkIfNotNull(famigliare.getAutodichiarazioneFiglio())) {
    //					figlioAutodichiarato = true;
    //				}
    //
    //				ItemRelazioneParentale parentelaDaDemografico = null;
    //				if(LabelFdCUtil.checkIfNotNull(famigliare.getDatiCittadino()) &&
    // LabelFdCUtil.checkIfNotNull(famigliare.getDatiCittadino().getCpvInRegisteredFamily()) &&
    //
    //	LabelFdCUtil.checkIfNotNull(famigliare.getDatiCittadino().getCpvInRegisteredFamily().getCpvBelongsToFamily())) {
    //					parentelaDaDemografico =
    // famigliare.getDatiCittadino().getCpvInRegisteredFamily().getCpvBelongsToFamily().stream()
    //							.filter(elem -> PageUtil.isStringValid(elem.getCpvComponentTaxCode()) &&
    // elem.getCpvComponentTaxCode().equalsIgnoreCase(famigliare.getCodiceFiscale()))
    //							.findAny()
    //							.orElse(null);
    //				}
    //
    //				DatiParenteleCompleti datiParentele = new DatiParenteleCompleti();
    //				datiParentele.setDatiFamigliare(famigliare);
    //				datiParentele.setParentelaDaDemografico(parentelaDaDemografico);
    //				datiParentele.setRelazioneConCittadinoLoggato(relazione);
    //				datiParentele.setSorgente(sorgente);
    //				datiParentele.setFiglioAutodichiarato(figlioAutodichiarato);
    //
    //				log.debug("CP datiParentele = " + datiParentele);
    //
    //				try {
    //					MioFamigliarePanel panel = new MioFamigliarePanel(datiParentele);
    //
    //					item.add(panel);
    //				} catch (Exception e) {
    //					EmptyPanel emptyPanel = new EmptyPanel("mioFamigliare");
    //					emptyPanel.setVisible(false);
    //					item.add(emptyPanel);
    //				}
    //
    //			}
    //		};
    //		add(listView);

    String clvFullAddress = "";
    if (residente.getCpvHasAddress() != null)
      clvFullAddress = residente.getCpvHasAddress().getClvFullAddress();
    add(new Label("indirizzo", clvFullAddress));
  }
}
