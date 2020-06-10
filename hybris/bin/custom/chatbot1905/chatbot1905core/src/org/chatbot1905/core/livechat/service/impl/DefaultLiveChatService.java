/**
 *
 */
package org.chatbot1905.core.livechat.service.impl;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
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

	@Resource(name = "modelService")
	private ModelService modelService;

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

	@SuppressWarnings("removal")
	@Override
	public SearchPageData<ActivityQuestionsModel> getActivityAnswers(final PageableData pageableData)
	{
		final SearchPageData<ActivityQuestionsModel> listQuestionModel = new SearchPageData<ActivityQuestionsModel>();
		final List<ActivityQuestionsModel> listQuestionModel3 = new ArrayList<ActivityQuestionsModel>();
		final List<ActivityQuestionsModel> listQuestionModel2 = liveChatDao
				.getActivityAnswers(userService.getCurrentUser().getUid(), pageableData);
		for (final ActivityQuestionsModel activityQuestionsModel : listQuestionModel2)
		{
			//if (activityQuestionsModel.getAnswers() != null)
			if (CollectionUtils.isNotEmpty(activityQuestionsModel.getAnswers()))
			{
				listQuestionModel3.add(activityQuestionsModel);
				listQuestionModel.setResults(listQuestionModel3);
				final PaginationData pagination = new PaginationData();
				pagination.setTotalNumberOfResults(listQuestionModel3.size());
				pagination.setPageSize(5);
				listQuestionModel.setPagination(pagination);
			}
		}
		return listQuestionModel;
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



	@Override
	public boolean questionIsAnswered()
	{
		final UserModel userModel = userService.getCurrentUser();
		if (userModel.getIsNotificationShown() == true)
		{
			final List<ActivityQuestionsModel> questionList = liveChatDao.getAllQuestionList(userModel);
			for (final ActivityQuestionsModel question : questionList)
			{
				final List<ActivityAnswersModel> answerList = new ArrayList<ActivityAnswersModel>(question.getAnswers());
				if (CollectionUtils.isNotEmpty(answerList))
				{
					for (final ActivityAnswersModel ans : answerList)
					{
						if (ans.getIsActive() == true)
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean updateIActiveFlagOfAnswer()
	{
		final UserModel userModel = userService.getCurrentUser();
		final List<ActivityQuestionsModel> questionList = liveChatDao.getAllQuestionList(userModel);
		for (final ActivityQuestionsModel question : questionList)
		{
			final List<ActivityAnswersModel> answerList = new ArrayList<ActivityAnswersModel>(question.getAnswers());
			for (final ActivityAnswersModel ans : answerList)
			{
				ans.setIsActive(false);
				modelService.save(ans);
			}
		}
		userModel.setIsNotificationShown(false);
		modelService.save(userModel);
		return true;
	}
}
