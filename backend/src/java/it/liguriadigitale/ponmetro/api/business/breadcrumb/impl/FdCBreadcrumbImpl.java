package it.liguriadigitale.ponmetro.api.business.breadcrumb.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.breadcrumb.service.FdCBreadcrumbService;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroBusinessHelper;
import it.liguriadigitale.ponmetro.api.business.entitaconfigurazioneutente.impl.ServiziHomePageImpl;
import it.liguriadigitale.ponmetro.api.integration.dao.view.VCfgTCatBreadcrumbDAO;
import it.liguriadigitale.ponmetro.api.pojo.breadcrumb.VCfgTCatBreadcrumb;
import it.liguriadigitale.ponmetro.breadcrumbfdc.model.Breadcrumb;
import it.liguriadigitale.ponmetro.breadcrumbfdc.model.BreadcrumbList;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FdCBreadcrumbImpl implements FdCBreadcrumbService {

  private static Log log = LogFactory.getLog(FdCBreadcrumbImpl.class);

  @SuppressWarnings("unchecked")
  @Override
  public List<VCfgTCatBreadcrumb> selectViewBreadcrumb(Long idFcitt, Long idFunzFiglio)
      throws BusinessException {
    log.debug("selectViewBreadcrumb");

    VCfgTCatBreadcrumb funzioni = new VCfgTCatBreadcrumb();
    funzioni.setFigliofunz(idFunzFiglio);
    VCfgTCatBreadcrumbDAO dao = new VCfgTCatBreadcrumbDAO(funzioni);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    List<VCfgTCatBreadcrumb> lista = helper.cercaOggetti();

    log.debug("Select breadcrumb = " + lista);
    return lista;
  }

  @Override
  public BreadcrumbList getBreadcrumb(Long idFcitt, Long idFunzFiglio) throws BusinessException {
    List<VCfgTCatBreadcrumb> select = selectViewBreadcrumb(idFcitt, idFunzFiglio);

    ServiziHomePageImpl serviziHome = new ServiziHomePageImpl();
    String host = serviziHome.getValoreByChiave("FDC");

    BreadcrumbList breadcrumbList = new BreadcrumbList();
    List<Breadcrumb> breadcrumbfilter = new ArrayList<Breadcrumb>();

    Breadcrumb home = new Breadcrumb();
    home.setDisplayName("Home");
    home.setHref(String.format("%s/homepage/home", host));
    home.setTitle("Home");
    home.setOrder(new BigDecimal(0));

    breadcrumbfilter.add(home);

    if (select != null) {

      Comparator<Breadcrumb> comparator =
          Comparator.comparing(Breadcrumb::getOrder, Comparator.naturalOrder());

      breadcrumbfilter.addAll(
          select.stream()
              .filter(y -> y.getFigliofunz().equals(idFunzFiglio))
              .map(
                  y -> {
                    Breadcrumb elem = new Breadcrumb();
                    // Inserisco la sezione0
                    if (y.isfiglio.compareTo(BigDecimal.ZERO) == 0
                        && y.issezione.compareTo(BigDecimal.ZERO) == 1) {

                      elem =
                          createBreadcrumbElement(
                              y.getDescrizioneSez(),
                              y.getDescrizioneSez(),
                              String.format(
                                  "%s/%s",
                                  String.format("%s/%s", host, getDelegabile(y.getIsdelegabile())),
                                  y.getUriSez()),
                              new BigDecimal(1));

                    } else if (y.isfiglio.compareTo(BigDecimal.ZERO) == 0
                        && y.issezione.compareTo(BigDecimal.ZERO) == 0) {
                      elem =
                          createBreadcrumbElement(
                              y.getDescrfunz(),
                              y.getDescrfunz(),
                              String.format(
                                  "%s/%s",
                                  String.format("%s/%s", host, getDelegabile(y.getIsdelegabile())),
                                  y.getIdpagina()),
                              new BigDecimal(2));
                    } else {
                      elem =
                          createBreadcrumbElement(
                              y.getDescrfunz(), y.getDescrfunz(), null, new BigDecimal(3));
                    }

                    return elem;
                  })
              .sorted(comparator)
              .collect(Collectors.toList()));
    }

    breadcrumbList.setBreadcrumbs(breadcrumbfilter);

    return breadcrumbList;
  }

  private String getDelegabile(BigDecimal isdelegabile) {
    return isdelegabile.compareTo(BigDecimal.ZERO) == 1 ? "delegabili" : "nondelegabili";
  }

  private Breadcrumb createBreadcrumbElement(
      String displayName, String title, String href, BigDecimal order) {
    Breadcrumb elem = new Breadcrumb();
    elem.setDisplayName(displayName);
    elem.setHref(href);
    elem.setTitle(title);
    elem.setOrder(order);
    return elem;
  }
}
