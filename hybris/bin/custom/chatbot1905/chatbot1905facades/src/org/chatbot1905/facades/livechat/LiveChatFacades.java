/**
 *
 */
package org.chatbot1905.facades.livechat;

import de.hybris.platform.core.model.user.UserModel;

import java.util.List;


/**
 * @author Pooja
 *
 */
public interface LiveChatFacades
{
	boolean noOfLikes(String userId);

	boolean isCurrenntlyActiveFlgReset();

	List<UserModel> getActiveCustomerList();
}
