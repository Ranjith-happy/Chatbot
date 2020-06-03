/**
 *
 */
package org.chatbot1905.facades.livechat;

import de.hybris.platform.commercefacades.user.data.CustomerData;

import java.util.List;

import org.chatbot1905.facades.product.data.ActivityQuestions;


/**
 * @author Pooja
 *
 */
public interface LiveChatFacades
{
	boolean updateLikesCount(String userId);

	boolean updateActiveFlag();

	List<CustomerData> getActiveCustomerList();

	boolean saveActivityQuestions(ActivityQuestions activityQuestions);

	List<ActivityQuestions> getLast24HoursPostedQuestions();
}
