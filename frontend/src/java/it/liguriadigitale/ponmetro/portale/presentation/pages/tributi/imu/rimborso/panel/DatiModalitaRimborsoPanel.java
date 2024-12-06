package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.panel;

import it.liguriadigitale.ponmetro.portale.pojo.imu.BonificoBancario;
import it.liguriadigitale.ponmetro.portale.pojo.imu.Delegato;
import it.liguriadigitale.ponmetro.portale.pojo.imu.RimborsoImu;
import it.liguriadigitale.ponmetro.portale.presentation.components.modalitapagamento.FdCModalitaPagamentoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.modalitapagamento.FdCPagamentoPressoTesoreriaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.modalitapagamento.FdCPagamentoSuContoCorrentePanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.modalitapagamento.ModalitaPagametoTariEngRender;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiIstanza.ModalitaPagamentoEnum;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class DatiModalitaRimborsoPanel extends BasePanel {
  private static final long serialVersionUID = 18979651212316548L;

  RimborsoImu rimborso;

  boolean check1;
  boolean check2;
  boolean check3;
  boolean check4;
  boolean check5;

  private BonificoBancario bonificoBancario;
  private Delegato delegato;

  WebMarkupContainer accreditoSuContoCorrentePanel;
  WebMarkupContainer ritiroPressoTesoreriaPanel;

  public DatiModalitaRimborsoPanel(String id) {
    super(id);
    // TODO Auto-generated constructor stub
  }

  public DatiModalitaRimborsoPanel(String id, RimborsoImu rimborso) {
    this(id);
    // TODO Auto-generated constructor stub

    fillDati(rimborso);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void fillDati(Object dati) {
    // TODO Auto-generated method stub
    rimborso = (RimborsoImu) dati;
    rimborso.setBonificoBancario(new BonificoBancario());
    rimborso.setDelegato(new Delegato());

    CheckBox ck1 = new CheckBox("ckUno", new PropertyModel(rimborso, "ck1"));
    ck1.setRequired(true);
    CheckBox ck2 = new CheckBox("ckDue", new PropertyModel(rimborso, "ck2"));
    ck2.setRequired(true);
    CheckBox ck3 = new CheckBox("ckTre", new PropertyModel(rimborso, "ck3"));
    ck3.setRequired(true);
    CheckBox ck4 = new CheckBox("ckQuattro", new PropertyModel(rimborso, "ck4"));
    ck4.setRequired(true);

    List<ModalitaPagamentoEnum> listaModalitaPagamento =
        ServiceLocator.getInstance().getServiziTariEng().getListaModalitaPagamento();

    FdCModalitaPagamentoDropDownChoice modalitaDiPagamento =
        new FdCModalitaPagamentoDropDownChoice(
            "modalitaDiPagamento",
            new PropertyModel<ModalitaPagamentoEnum>(rimborso, "modalitaPagamento"),
            new ModalitaPagametoTariEngRender(),
            listaModalitaPagamento);

    modalitaDiPagamento.setRequired(true);
    modalitaDiPagamento.setLabel(Model.of("Modalità di rimborso"));
    modalitaDiPagamento.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = 131232144987465498L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            log.debug(
                "[onUpdateModalitaPagamento] Modalita pagamento: "
                    + modalitaDiPagamento.getValue());

            accreditoSuContoCorrentePanel.setVisible(
                modalitaDiPagamento.getValue().equals(ModalitaPagamentoEnum.CAB.name()));
            ritiroPressoTesoreriaPanel.setVisible(
                modalitaDiPagamento.getValue().equals(ModalitaPagamentoEnum.CAS.name()));
            target.add(accreditoSuContoCorrentePanel, ritiroPressoTesoreriaPanel);
          }
        });

    addOrReplace(modalitaDiPagamento);

    accreditoSuContoCorrentePanel =
        new FdCPagamentoSuContoCorrentePanel(
            "accreditoSuContoCorrentePanel", rimborso.getBonificoBancario(), true, false);
    accreditoSuContoCorrentePanel.setVisible(false);
    addOrReplace(accreditoSuContoCorrentePanel);

    ritiroPressoTesoreriaPanel =
        new FdCPagamentoPressoTesoreriaPanel(
            "ritiroPressoTesoreriaPanel", rimborso.getDelegato(), false);
    ritiroPressoTesoreriaPanel.setVisible(false);
    addOrReplace(ritiroPressoTesoreriaPanel);

    addOrReplace(ck1);
    addOrReplace(ck2);
    addOrReplace(ck3);
    addOrReplace(ck4);

    addOrReplace(modalitaDiPagamento);

    TextArea<String> eventualiAnnotazioni =
        new TextArea<String>("annotazioni", new PropertyModel<String>(rimborso, "annotazioni"));
    eventualiAnnotazioni.setLabel(Model.of("Annotazioni"));

    addOrReplace(eventualiAnnotazioni);
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
}
