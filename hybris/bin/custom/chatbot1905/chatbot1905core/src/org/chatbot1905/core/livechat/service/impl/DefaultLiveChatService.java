/**
 *
 */
package org.chatbot1905.core.livechat.service.impl;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.chatbot1905.core.livechat.dao.LiveChatDao;
import org.chatbot1905.core.livechat.service.LiveChatService;
import org.happybot.model.ActivityAnswersModel;
import org.happybot.model.ActivityQuestionsModel;


/**
 * @author Pooja
 *
 */
public class DefaultLiveChatService implements LiveChatService
{

	@Resource(name = "liveChatDao")
	private LiveChatDao liveChatDao;

	@Resource(name = "userService")
	private UserService userService;

	@Override
	public List<UserModel> getActiveCustomerList()
	{
		return liveChatDao.getActiveCustomerList(userService.getCurrentUser().getUid());
	}

	@Override
	public SearchPageData<ActivityQuestionsModel> getLast24HoursPostedQuestions(final PageableData pageableData)
	{
		// XXX Auto-generated method stub
		return liveChatDao.getLast24HoursPostedQuestions(userService.getCurrentUser(), pageableData);
	}

	@Override
	public SearchPageData<ActivityQuestionsModel> getActivityAnswers(final PageableData pageableData)
	{
		final List<ActivityQuestionsModel> listQuestionModel = new ArrayList<ActivityQuestionsModel>();
		final List<ActivityQuestionsModel> listQuestionModel2 = liveChatDao
				.getActivityAnswers(userService.getCurrentUser().getUid(), pageableData);
		for (final ActivityQuestionsModel activityQuestionsModel : listQuestionModel2)
		{
			if (activityQuestionsModel.getAnswers() != null)
			{
				listQuestionModel.add(activityQuestionsModel);
			}
		}
		final SearchPageData<ActivityQuestionsModel> listQuestionModel1 = (SearchPageData<ActivityQuestionsModel>) listQuestionModel;
		return listQuestionModel1;

	}

	@Override
	public Integer updateLikescountModels(final ActivityAnswersModel activityAnswersModel)
	{
		final Integer likecount = liveChatDao.getlikescount(activityAnswersModel.getPk().toString());
		return likecount;
	}

	@Override
	public ActivityAnswersModel getspecificAnswer(final String activityAnswers)
	{
		return liveChatDao.getspecificAnswer(activityAnswers);
	}
}
