/**
 *
 */
package org.chatbot1905.facades.livechat.impl;

import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.List;

import javax.annotation.Resource;

import org.chatbot1905.core.livechat.service.LiveChatService;
import org.chatbot1905.facades.livechat.LiveChatFacades;
import org.chatbot1905.facades.product.data.ActivityAnswers;
import org.chatbot1905.facades.product.data.ActivityQuestions;
import org.happybot.model.ActivityAnswersModel;
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

	@Resource(name = "activityAnswersConverter")
	private Converter<ActivityAnswers, ActivityAnswersModel> activityAnswersConverter;

	@Resource(name = "getActivityAnswersConverter")
	private Converter<ActivityQuestionsModel, ActivityQuestions> getActivityAnswersConverter;

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
	public SearchPageData<ActivityQuestions> getPostedQuestions(final PageableData pageableData)
	{

		final SearchPageData<ActivityQuestionsModel> questionResults = liveChatService.getLast24HoursPostedQuestions(pageableData);

		return convertPageData(questionResults, getActivityQuestionsConverter);
	}

	protected <S, T> SearchPageData<T> convertPageData(final SearchPageData<S> source, final Converter<S, T> converter)
	{
		final SearchPageData<T> result = new SearchPageData<T>();
		result.setPagination(source.getPagination());
		result.setSorts(source.getSorts());
		result.setResults(Converters.convertAll(source.getResults(), converter));
		return result;
	}

	@Override
	public boolean saveActivityAnswers(final ActivityAnswers activityAnswers)
	{
		final ActivityAnswersModel activityAnswersModel = activityAnswersConverter.convert(activityAnswers);
		modelService.save(activityAnswersModel);
		return false;
	}

	@Override
	public List<ActivityQuestions> getActivityAnswers()
	{
		return getActivityAnswersConverter.convertAll(liveChatService.getActivityAnswers());
	}

}
