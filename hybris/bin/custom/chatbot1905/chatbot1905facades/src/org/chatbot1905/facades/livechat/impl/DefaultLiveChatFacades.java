/**
 *
 */
package org.chatbot1905.facades.livechat.impl;

import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.List;

import javax.annotation.Resource;

import org.chatbot1905.core.livechat.service.LiveChatService;
import org.chatbot1905.facades.livechat.LiveChatFacades;
import org.chatbot1905.facades.product.data.ActivityQuestions;
import org.happybot.model.ActivityQuestionsModel;


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

	@Resource(name = "customerConverter")
	private Converter<UserModel, CustomerData> customerConverter;

	@Resource(name = "activityQuestionsConverter")
	private Converter<ActivityQuestions, ActivityQuestionsModel> activityQuestionsConverter;

	@Resource(name = "getActivityQuestionsConverter")
	private Converter<ActivityQuestionsModel, ActivityQuestions> getActivityQuestionsConverter;

	@Override
	public boolean updateLikesCount(final String userId)
	{
		final UserModel userModel = userService.getUserForUID(userId);
		final int noOfLikes = userModel.getLikes() == null ? 0 : userModel.getLikes();
		userModel.setLikes(noOfLikes + 1);
		modelService.save(userModel);
		modelService.refresh(userModel);
		return true;
	}

	@Override
	public boolean updateActiveFlag()
	{
		final UserModel userModel = userService.getCurrentUser();
		userModel.setIsCurrentlyActive(false);
		modelService.save(userModel);
		modelService.refresh(userModel);
		return true;
	}

	@Override
	public List<CustomerData> getActiveCustomerList()
	{
		return customerConverter.convertAll(liveChatService.getActiveCustomerList());
	}

	@Override
	public boolean saveActivityQuestions(final ActivityQuestions activityQuestions)
	{
		final ActivityQuestionsModel activityQuestionsModel = activityQuestionsConverter.convert(activityQuestions);
		modelService.save(activityQuestionsModel);
		return true;
	}

	@Override
	public List<ActivityQuestions> getLast24HoursPostedQuestions()
	{
		return getActivityQuestionsConverter.convertAll(liveChatService.getLast24HoursPostedQuestions());
	}

}
