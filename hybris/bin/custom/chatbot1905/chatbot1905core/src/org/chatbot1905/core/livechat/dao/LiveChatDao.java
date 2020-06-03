/**
 *
 */
package org.chatbot1905.core.livechat.dao;

import de.hybris.platform.core.model.user.UserModel;

import java.util.List;

import org.happybot.model.ActivityQuestionsModel;


/**
 * @author Pooja
 *
 */
public interface LiveChatDao
{
	public List<UserModel> getActiveCustomerList(final String uid);

	List<ActivityQuestionsModel> getLast24HoursPostedQuestions(final String uid);

}
