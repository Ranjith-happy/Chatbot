/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.chatbot1905.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;

import org.chatbot1905.storefront.controllers.ControllerConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Controller for Chatbot functionality.
 */
@Controller
@RequestMapping(value = "/chatbot")
public class ChatBotPageController extends AbstractPageController
{

	@RequestMapping(method = RequestMethod.GET)
	public String getChatText(final Model model)
	{
		final String response = "Hi there!!! How are you??";
		return ControllerConstants.Views.Pages.Cart.CartPage;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String postChatText(final Model model)
	{
		final String response = "Hi there!!! How are you??";
		return ControllerConstants.Views.Pages.Cart.CartPage;
	}
}
