/**
 *
 */
package org.chatbot1905.facades.livechat;

import de.hybris.platform.commercefacades.user.data.CustomerData;

import java.util.List;


/**
 * @author Pooja
 *
 */
public interface LiveChatFacades
{
	boolean updateLikesCount(String userId);

	boolean updateActiveFlag();

	List<CustomerData> getActiveCustomerList();
}
