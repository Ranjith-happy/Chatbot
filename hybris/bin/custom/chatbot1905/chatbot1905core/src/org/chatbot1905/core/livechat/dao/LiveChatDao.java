/**
 *
 */
package org.chatbot1905.core.livechat.dao;

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
public interface LiveChatDao
{
	public List<UserModel> getActiveCustomerList(final String uid);

	SearchPageData<ActivityQuestionsModel> getLast24HoursPostedQuestions(UserModel user, final PageableData pageableData);


	List<ActivityQuestionsModel> getActivityAnswers(final String uid, PageableData pageableData);

	List<ActivityQuestionsModel> getAllQuestionList(UserModel user);

	/**
	 * @return
	 */

	Integer getlikescount(final String string);

	/**
	 * @param string
	 * @return
	 */
	ActivityAnswersModel getspecificAnswer(String string);

	List<ActivityQuestionsModel> getQuestionPk(final String code);

}
