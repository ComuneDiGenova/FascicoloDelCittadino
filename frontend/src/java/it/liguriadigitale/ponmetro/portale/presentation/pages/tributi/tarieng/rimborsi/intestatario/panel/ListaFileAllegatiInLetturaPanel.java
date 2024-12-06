package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.rimborsi.intestatario.panel;

import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarieng.DatiRichiestaRimborsoTariEng;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.taririmborsieng.model.FileAllegato;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class ListaFileAllegatiInLetturaPanel extends BasePanel {

  private static final long serialVersionUID = -6613816840389332493L;
  private int index;

  public ListaFileAllegatiInLetturaPanel(String id) {
    super(id);
  }

  public ListaFileAllegatiInLetturaPanel(
      String id, DatiRichiestaRimborsoTariEng datiRimborso, int index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.setIndex(index);

    fillDati(datiRimborso);
  }

  @Override
  public void fillDati(Object dati) {

    DatiRichiestaRimborsoTariEng datiRimborso = (DatiRichiestaRimborsoTariEng) dati;

    WebMarkupContainer containerAllegatiUpload = new WebMarkupContainer("containerAllegatiUpload");
    containerAllegatiUpload.setOutputMarkupId(true);
    containerAllegatiUpload.setOutputMarkupPlaceholderTag(true);
    addOrReplace(containerAllegatiUpload);

    List<FileAllegato> listaFileAllegato = new ArrayList<FileAllegato>();

    if (LabelFdCUtil.checkIfNotNull(datiRimborso)
        && LabelFdCUtil.checkIfNotNull(datiRimborso.getListaAllegati())) {
      listaFileAllegato = datiRimborso.getListaAllegati();
    }

    ListView<FileAllegato> listaAllegati =
        new ListView<FileAllegato>("listaAllegati", listaFileAllegato) {

          private static final long serialVersionUID = -2228349777076543098L;

          @Override
          protected void populateItem(ListItem<FileAllegato> itemAllegato) {

            FileAllegato allegato = itemAllegato.getModelObject();

            itemAllegato.setOutputMarkupId(true);

            Label nomeFileAllegato = new Label("nomeFileAllegato", allegato.getNomeFile());
            itemAllegato.addOrReplace(nomeFileAllegato);

            Label dimensioneFileAllegato =
                new Label(
                    "dimensioneFileAllegato", FileFdCUtil.getSizeFile(allegato.getFile().length));
            itemAllegato.addOrReplace(dimensioneFileAllegato);
          }
        };

    listaAllegati.setOutputMarkupId(true);
    listaAllegati.setOutputMarkupPlaceholderTag(true);

    containerAllegatiUpload.addOrReplace(listaAllegati);
    containerAllegatiUpload.setVisible(
        LabelFdCUtil.checkIfNotNull(listaFileAllegato)
            && !LabelFdCUtil.checkEmptyList(listaFileAllegato)
            && listaFileAllegato.size() > 0);
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    StringBuilder sb = new StringBuilder();

    sb.append(
        "$('html,body').animate({\r\n"
            + " scrollTop: $('#indicator').offset().top,\r\n"
            + " }, 650);");

    response.render(OnLoadHeaderItem.forScript(sb.toString()));
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}
