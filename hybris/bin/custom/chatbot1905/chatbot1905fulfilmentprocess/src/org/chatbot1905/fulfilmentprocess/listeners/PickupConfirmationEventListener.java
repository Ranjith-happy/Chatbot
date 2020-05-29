/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.chatbot1905.fulfilmentprocess.listeners;

import de.hybris.platform.orderprocessing.events.PickupConfirmationEvent;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.ordersplitting.model.ConsignmentProcessModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import org.chatbot1905.fulfilmentprocess.constants.Chatbot1905FulfilmentProcessConstants;

import org.springframework.beans.factory.annotation.Required;


/**
 * Listener for pickup confirmation events.
 */
public class PickupConfirmationEventListener extends AbstractEventListener<PickupConfirmationEvent>
{

	private BusinessProcessService businessProcessService;

	protected BusinessProcessService getBusinessProcessService()
	{
		return businessProcessService;
	}

	@Required
	public void setBusinessProcessService(final BusinessProcessService businessProcessService)
	{
		this.businessProcessService = businessProcessService;
	}

	@Override
	protected void onEvent(final PickupConfirmationEvent pickupConfirmationEvent)
	{
		final ConsignmentModel consignmentModel = pickupConfirmationEvent.getProcess().getConsignment();
		for (final ConsignmentProcessModel process : consignmentModel.getConsignmentProcesses())
		{
			getBusinessProcessService().triggerEvent(
					process.getCode() + "_" + Chatbot1905FulfilmentProcessConstants.CONSIGNMENT_PICKUP);
		}
	}
}
