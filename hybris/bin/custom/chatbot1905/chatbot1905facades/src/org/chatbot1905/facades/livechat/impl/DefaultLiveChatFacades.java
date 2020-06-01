/**
 *
 */
package org.chatbot1905.facades.livechat.impl;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.List;

import javax.annotation.Resource;

import org.chatbot1905.core.livechat.service.LiveChatService;
import org.chatbot1905.facades.livechat.LiveChatFacades;


/**
 * @author Pooja
 *
 */
public class DefaultLiveChatFacades implements LiveChatFacades
{

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "liveChatService")
	private LiveChatService liveChatService;

	@Override
	public boolean noOfLikes(final String userId)
	{
		final UserModel userModel = userService.getUserForUID(userId);
		final int noOfLikes = userModel.getLikes() == null ? 0 : userModel.getLikes();
		userModel.setLikes(noOfLikes + 1);
		modelService.save(userModel);
		modelService.refresh(userModel);
		return true;
	}

	@Override
	public boolean isCurrenntlyActiveFlgReset()
	{
		final UserModel userModel = userService.getCurrentUser();
		userModel.setIsCurrentlyActive(false);
		modelService.save(userModel);
		modelService.refresh(userModel);
		return true;
	}

	@Override
	public List<UserModel> getActiveCustomerList()
	{
		return liveChatService.getActiveCustomerList();
	}

}
