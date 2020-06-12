/**
 *
 */
package org.chatbot1905.core.livechat.service;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.UserModel;

import java.util.List;

import org.happybot.model.ActivityAnswersModel;
import org.happybot.model.ActivityQuestionsModel;


/**
 * @author Pooja
 *
 */
public interface LiveChatService
{
	List<UserModel> getActiveCustomerList();

	SearchPageData<ActivityQuestionsModel> getLast24HoursPostedQuestions(final PageableData pageableData);

	boolean questionIsAnswered();

	boolean updateIActiveFlagOfAnswer();

	SearchPageData<ActivityQuestionsModel> getActivityAnswers(final PageableData pageableData);

	Integer updateLikescountModels(ActivityAnswersModel activityAnswersModel);

	/**
	 * @param activityAnswers
	 * @return
	 */
	ActivityAnswersModel getspecificAnswer(String activityAnswers);

	List<ActivityQuestionsModel> getQuestionPk(final String code);

}
