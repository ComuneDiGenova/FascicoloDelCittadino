package it.liguriadigitale.ponmetro.portale.presentation.pages.virtuoso.plastipremia.panel.coupon;

import it.liguriadigitale.ponmetro.plastipremia.model.Coupon;
import it.liguriadigitale.ponmetro.plastipremia.model.PlastiCoupon;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.CardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class CouponPlasticaPanel extends BasePanel {

  private static final long serialVersionUID = -2565165590667616385L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public CouponPlasticaPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  public CouponPlasticaPanel(String panelId, PlastiCoupon popolaCouponPlatica) {
    super(panelId);
    fillDati(popolaCouponPlatica);

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {
    PlastiCoupon couponPlastica = (PlastiCoupon) dati;

    List<Coupon> listaCoupon = new ArrayList<Coupon>();
    if (LabelFdCUtil.checkIfNotNull(couponPlastica)
        && LabelFdCUtil.checkIfNotNull(couponPlastica.getCoupon())) {
      listaCoupon = couponPlastica.getCoupon();
    }

    Label codiceFiscale = new Label("codiceFiscale", couponPlastica.getCodiceFiscale());
    codiceFiscale.setVisible(LabelFdCUtil.checkIfNotNull(couponPlastica.getCodiceFiscale()));
    addOrReplace(codiceFiscale);

    Label anno = new Label("anno", couponPlastica.getAnno());
    anno.setVisible(LabelFdCUtil.checkIfNotNull(couponPlastica.getAnno()));
    addOrReplace(anno);

    String puntiValue = "";
    if (LabelFdCUtil.checkIfNotNull(couponPlastica.getPunti())) {
      puntiValue = String.valueOf(couponPlastica.getPunti());
    }
    Label punti = new Label("punti", puntiValue);
    punti.setVisible(PageUtil.isStringValid(puntiValue));
    addOrReplace(punti);

    PageableListView<Coupon> listView =
        new PageableListView<Coupon>("box", listaCoupon, 6) {

          private static final long serialVersionUID = -5431178297608422762L;

          @Override
          protected void populateItem(ListItem<Coupon> item) {
            final Coupon coupon = item.getModelObject();

            Label titolo = new Label("titolo", coupon.getTitolo());
            titolo.setVisible(LabelFdCUtil.checkIfNotNull(coupon.getTitolo()));
            item.addOrReplace(titolo);

            item.addOrReplace(
                new CardLabel<>("annoCoupon", coupon.getAnno(), CouponPlasticaPanel.this));

            item.addOrReplace(
                new CardLabel<>("stato", coupon.getStato(), CouponPlasticaPanel.this));

            item.addOrReplace(
                new CardLabel<>(
                    "validoFinoAl", coupon.getValidoFinoAl(), CouponPlasticaPanel.this));

            item.addOrReplace(
                new CardLabel<>("descrizione", coupon.getDescrizione(), CouponPlasticaPanel.this));

            Label codice = new Label("codice", coupon.getCodice());
            codice.setVisible(PageUtil.isStringValid(coupon.getCodice()));
            item.addOrReplace(codice);
          }
        };

    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("paginationPlasTiCoupon", listView);
    paginazioneFascicolo.setVisible(
        LabelFdCUtil.checkIfNotNull(listaCoupon)
            && !LabelFdCUtil.checkEmptyList(listaCoupon)
            && listaCoupon.size() > 6);
    addOrReplace(paginazioneFascicolo);
  }
}
