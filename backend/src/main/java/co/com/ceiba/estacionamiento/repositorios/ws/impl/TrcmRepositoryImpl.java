package co.com.ceiba.estacionamiento.repositorios.ws.impl;

import java.time.LocalDateTime;

import javax.xml.bind.JAXBElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ws.client.core.WebServiceTemplate;

import co.com.ceiba.estacionamiento.modelos.TcrmModel;
import co.com.ceiba.estacionamiento.repositorios.ws.TrcmRepository;
import co.com.ceiba.estacionamiento.ws.client.modelo.ObjectFactory;
import co.com.ceiba.estacionamiento.ws.client.modelo.QueryTCRM;
import co.com.ceiba.estacionamiento.ws.client.modelo.QueryTCRMResponse;

@Repository
public class TrcmRepositoryImpl implements TrcmRepository {

	private static final ObjectFactory objectFactory = new ObjectFactory();

	@Autowired
	private WebServiceTemplate webServiceTemplate;

	@Override
	public TcrmModel consultarTrcm() {
		QueryTCRM queryTcrm = objectFactory.createQueryTCRM();
		Object marshalSendAndReceive = webServiceTemplate.marshalSendAndReceive(queryTcrm);
		QueryTCRMResponse response = ((JAXBElement<QueryTCRMResponse>) marshalSendAndReceive).getValue();

		LocalDateTime fechaVigenciaInical = LocalDateTime.of(response.getReturn().getValidityFrom().getYear(),
				response.getReturn().getValidityFrom().getMonth(), response.getReturn().getValidityFrom().getDay(),
				response.getReturn().getValidityFrom().getHour(), response.getReturn().getValidityFrom().getMinute(),
				response.getReturn().getValidityFrom().getSecond());

		LocalDateTime fechaVigenciaFinal = LocalDateTime.of(response.getReturn().getValidityTo().getYear(),
				response.getReturn().getValidityTo().getMonth(), response.getReturn().getValidityTo().getDay(),
				response.getReturn().getValidityTo().getHour(), response.getReturn().getValidityTo().getMinute(),
				response.getReturn().getValidityTo().getSecond());

		return new TcrmModel(response.getReturn().getId(), fechaVigenciaInical, fechaVigenciaFinal,
				response.getReturn().getUnit(), response.getReturn().getValue());
	}

}
