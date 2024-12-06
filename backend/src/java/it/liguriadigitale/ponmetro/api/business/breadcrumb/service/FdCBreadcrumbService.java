package it.liguriadigitale.ponmetro.api.business.breadcrumb.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.breadcrumb.VCfgTCatBreadcrumb;
import it.liguriadigitale.ponmetro.breadcrumbfdc.model.BreadcrumbList;
import java.util.List;

public interface FdCBreadcrumbService {

  List<VCfgTCatBreadcrumb> selectViewBreadcrumb(Long idFcitt, Long idFunzFiglio)
      throws BusinessException;

  BreadcrumbList getBreadcrumb(Long idFcitt, Long idFunzFiglio) throws BusinessException;
}
