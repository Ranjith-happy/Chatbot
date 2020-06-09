/**
 *
 */
package org.chatbot1905.facades.livechat;

import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.List;

import org.chatbot1905.facades.product.data.ActivityAnswers;
import org.chatbot1905.facades.product.data.ActivityQuestions;
import org.happybot.model.ActivityAnswersModel;


/**
 * @author Pooja
 *
 */
@SuppressWarnings("removal")
public interface LiveChatFacades
{
	Integer updateLikesCount(ActivityAnswersModel activityAnswerModel);

	boolean updateActiveFlag();

	List<CustomerData> getActiveCustomerList();

	boolean saveActivityQuestions(ActivityQuestions activityQuestions);

	SearchPageData<ActivityQuestions> getPostedQuestions(final PageableData pageableData);

	boolean saveActivityAnswers(ActivityAnswers activityAnswers);

	SearchPageData<ActivityQuestions> getActivityAnswers(final PageableData pageableData);

	/**
	 * @param activityAnswers
	 * @return
	 */
	ActivityAnswersModel specificAnswer(String activityAnswers);
}
