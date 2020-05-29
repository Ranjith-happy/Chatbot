/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.chatbot1905.fulfilmentprocess.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import org.chatbot1905.fulfilmentprocess.constants.Chatbot1905FulfilmentProcessConstants;

public class Chatbot1905FulfilmentProcessManager extends GeneratedChatbot1905FulfilmentProcessManager
{
	public static final Chatbot1905FulfilmentProcessManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (Chatbot1905FulfilmentProcessManager) em.getExtension(Chatbot1905FulfilmentProcessConstants.EXTENSIONNAME);
	}
	
}
