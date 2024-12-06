@XmlJavaTypeAdapters({
  @XmlJavaTypeAdapter(type = LocalDateTime.class, value = LocalDateTimeAdapter.class),
  @XmlJavaTypeAdapter(type = LocalDate.class, value = LocalDateAdapter.class)
})
package it.liguriadigitale.ponmetro.api.pojo.globo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;

import it.liguriadigitale.ponmetro.api.pojo.adapter.LocalDateAdapter;
import it.liguriadigitale.ponmetro.api.pojo.adapter.LocalDateTimeAdapter;
