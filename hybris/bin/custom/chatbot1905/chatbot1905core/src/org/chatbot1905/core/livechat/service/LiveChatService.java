/**
 *
 */
package org.chatbot1905.core.livechat.service;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.UserModel;

import java.util.List;

import org.happybot.model.ActivityQuestionsModel;


/**
 * @author Pooja
 *
 */
public interface LiveChatService
{
	List<UserModel> getActiveCustomerList();

	SearchPageData<ActivityQuestionsModel> getLast24HoursPostedQuestions(final PageableData pageableData);

	List<ActivityQuestionsModel> getActivityAnswers();
}
