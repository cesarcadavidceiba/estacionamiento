
package co.com.ceiba.estacionamiento.ws.client.modelo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Clase Java para tcrm complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="tcrm">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="displayToUser" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="unit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="validityFrom" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="validityTo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcrm", propOrder = { "displayToUser", "id", "unit", "validityFrom", "validityTo", "value" })
@XmlSeeAlso({ TcrmResponse.class })
public class Tcrm {

	protected Boolean displayToUser;
	protected Long id;
	protected String unit;
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar validityFrom;
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar validityTo;
	protected Float value;

	/**
	 * Obtiene el valor de la propiedad id.
	 * 
	 * @return possible object is {@link Long }
	 * 
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Obtiene el valor de la propiedad unit.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * Obtiene el valor de la propiedad validityFrom.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getValidityFrom() {
		return validityFrom;
	}

	/**
	 * Obtiene el valor de la propiedad validityTo.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getValidityTo() {
		return validityTo;
	}

	/**
	 * Obtiene el valor de la propiedad value.
	 * 
	 * @return possible object is {@link Float }
	 * 
	 */
	public Float getValue() {
		return value;
	}

}
