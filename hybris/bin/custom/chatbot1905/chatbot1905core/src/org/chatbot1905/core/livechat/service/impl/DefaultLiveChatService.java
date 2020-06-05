/**
 *
 */
package org.chatbot1905.core.livechat.service.impl;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;

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
	public SearchPageData<ActivityQuestionsModel> getLast24HoursPostedQuestions(final PageableData pageableData)
	{
		// XXX Auto-generated method stub
		return liveChatDao.getLast24HoursPostedQuestions(userService.getCurrentUser(), pageableData);
	}


}
