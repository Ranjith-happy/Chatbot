/**
 *
 */
package org.chatbot1905.facades.livechat;

import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

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

	SearchPageData<ActivityQuestions> getPostedQuestions(final PageableData pageableData);
}
