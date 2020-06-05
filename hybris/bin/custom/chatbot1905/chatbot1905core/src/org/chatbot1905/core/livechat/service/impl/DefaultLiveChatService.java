/**
 *
 */
package org.chatbot1905.core.livechat.service.impl;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.chatbot1905.core.livechat.dao.LiveChatDao;
import org.chatbot1905.core.livechat.service.LiveChatService;
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
	public List<ActivityQuestionsModel> getLast24HoursPostedQuestions()
	{
		// XXX Auto-generated method stub
		/*
		 * // return liveChatDao.getLast24HoursPostedQuestions(userService.getCurrentUser().getUid());
		 */ return null;
	}

	@Override
	public List<ActivityQuestionsModel> getActivityAnswers()
	{
		final List<ActivityQuestionsModel> listQuestionModel = new ArrayList<ActivityQuestionsModel>();
		final List<ActivityQuestionsModel> listQuestionModel2 = liveChatDao.getActivityAnswers(userService.getCurrentUser().getUid());
		for (final ActivityQuestionsModel activityQuestionsModel : listQuestionModel2)
		{
			if (activityQuestionsModel.getAnswers() != null)
			{
				listQuestionModel.add(activityQuestionsModel);
			}
		}
		return listQuestionModel;

	}


}
