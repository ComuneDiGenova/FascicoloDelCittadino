package it.liguriadigitale.ponmetro.portale.presentation.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JaxbMarshaller<T> {

  protected Log log = LogFactory.getLog(this.getClass());

  private static HashMap<Class<?>, JAXBContext> contesti;

  private Class<?> classeRequest;

  public JaxbMarshaller(Class<T> classeRequest) throws JAXBException {
    super();
    this.classeRequest = classeRequest;

    if (contesti == null) {
      contesti = new HashMap<Class<?>, JAXBContext>();
    }

    if (!contesti.containsKey(classeRequest)) {
      contesti.put(classeRequest, JAXBContext.newInstance(classeRequest));
    }
  }

  public String marshallRequest(final T xmlObject) throws JAXBException {
    final JAXBContext context = contesti.get(classeRequest);
    final Marshaller marshaller = context.createMarshaller();
    final StringWriter writer = new StringWriter();
    marshaller.marshal(xmlObject, writer);
    final String xml = writer.toString();
    return xml;
  }

  @SuppressWarnings("unchecked")
  public T unMarshallResponse(final String result) throws JAXBException {
    final Charset iso88591charset = Charset.forName("ISO-8859-1");
    final ByteBuffer outputBuffer = iso88591charset.encode(result);
    final String resultISO = new String(outputBuffer.array(), Charset.forName("ISO-8859-1"));
    final JAXBContext context = contesti.get(classeRequest);
    final Unmarshaller unMarshaller = context.createUnmarshaller();
    return (T)
        unMarshaller
            .unmarshal(new StreamSource(new StringReader(resultISO)), classeRequest)
            .getValue();
  }
}
