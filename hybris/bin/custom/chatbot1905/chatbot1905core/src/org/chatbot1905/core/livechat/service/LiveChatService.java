/**
 *
 */
package org.chatbot1905.core.livechat.service;

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

	List<ActivityQuestionsModel> getLast24HoursPostedQuestions();

	List<ActivityQuestionsModel> getActivityAnswers();
}
