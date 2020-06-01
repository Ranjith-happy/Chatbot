/**
 *
 */
package org.chatbot1905.core.livechat.service;

import de.hybris.platform.core.model.user.UserModel;

import java.util.List;


/**
 * @author Pooja
 *
 */
public interface LiveChatService
{
	List<UserModel> getActiveCustomerList();

}
