/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.chatbot1905.core.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import org.chatbot1905.core.constants.Chatbot1905CoreConstants;
import org.chatbot1905.core.setup.CoreSystemSetup;


/**
 * Do not use, please use {@link CoreSystemSetup} instead.
 * 
 */
public class Chatbot1905CoreManager extends GeneratedChatbot1905CoreManager
{
	public static final Chatbot1905CoreManager getInstance()
	{
		final ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (Chatbot1905CoreManager) em.getExtension(Chatbot1905CoreConstants.EXTENSIONNAME);
	}
}
