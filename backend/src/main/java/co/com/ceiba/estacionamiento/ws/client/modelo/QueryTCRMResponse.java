
package co.com.ceiba.estacionamiento.ws.client.modelo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Clase Java para queryTCRMResponse complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="queryTCRMResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://action.trm.services.generic.action.superfinanciera.nexura.sc.com.co/}tcrmResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryTCRMResponse", propOrder = { "_return" })
public class QueryTCRMResponse {

	@XmlElement(name = "return")
	protected TcrmResponse _return;

	/**
	 * Obtiene el valor de la propiedad return.
	 * 
	 * @return possible object is {@link TcrmResponse }
	 * 
	 */
	public TcrmResponse getReturn() {
		return _return;
	}

}
