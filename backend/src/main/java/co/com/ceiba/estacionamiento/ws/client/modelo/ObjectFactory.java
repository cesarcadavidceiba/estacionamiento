
package co.com.ceiba.estacionamiento.ws.client.modelo;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the co.com.sc.nexura.superfinanciera.action.generic.services.trm.action package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _QueryTCRMResponse_QNAME = new QName("http://action.trm.services.generic.action.superfinanciera.nexura.sc.com.co/", "queryTCRMResponse");
    private final static QName _QueryTCRM_QNAME = new QName("http://action.trm.services.generic.action.superfinanciera.nexura.sc.com.co/", "queryTCRM");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: co.com.sc.nexura.superfinanciera.action.generic.services.trm.action
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryTCRMResponse }
     * 
     */
    public QueryTCRMResponse createQueryTCRMResponse() {
        return new QueryTCRMResponse();
    }

    /**
     * Create an instance of {@link QueryTCRM }
     * 
     */
    public QueryTCRM createQueryTCRM() {
        return new QueryTCRM();
    }

    /**
     * Create an instance of {@link TcrmResponse }
     * 
     */
    public TcrmResponse createTcrmResponse() {
        return new TcrmResponse();
    }

    /**
     * Create an instance of {@link Tcrm }
     * 
     */
    public Tcrm createTcrm() {
        return new Tcrm();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryTCRMResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://action.trm.services.generic.action.superfinanciera.nexura.sc.com.co/", name = "queryTCRMResponse")
    public JAXBElement<QueryTCRMResponse> createQueryTCRMResponse(QueryTCRMResponse value) {
        return new JAXBElement<>(_QueryTCRMResponse_QNAME, QueryTCRMResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryTCRM }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://action.trm.services.generic.action.superfinanciera.nexura.sc.com.co/", name = "queryTCRM")
    public JAXBElement<QueryTCRM> createQueryTCRM(QueryTCRM value) {
        return new JAXBElement<>(_QueryTCRM_QNAME, QueryTCRM.class, null, value);
    }

}
