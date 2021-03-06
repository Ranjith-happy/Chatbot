/**
 *
 */
package org.chatbot1905.facades.livechat.populator;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.chatbot1905.facades.product.data.ActivityAnswers;
import org.chatbot1905.facades.product.data.ActivityQuestions;
import org.happybot.model.ActivityAnswersModel;
import org.happybot.model.ActivityQuestionsModel;


/**
 * @author Pooja
 *
 */
public class GetActivityQuestionsPopulator implements Populator<ActivityQuestionsModel, ActivityQuestions>
{


	@Resource(name = "getActivityAnswersConverter")
	private Converter<ActivityAnswersModel, ActivityAnswers> getActivityAnswersConverter;

	public String postedDuration(final ActivityQuestionsModel activityQuestionsModel)
	{
		String msg = "";
		int hour;
		final Date createdDate = activityQuestionsModel.getCreationtime();
		final Date currentTime = new Date();
		int minute = (int) TimeUnit.MILLISECONDS.toMinutes(currentTime.getTime() - createdDate.getTime());
		if (minute < 60)
		{
			msg = minute + " minutes ago";
		}
		else
		{
			hour = minute / 60;
			minute = minute % 60;
			msg = hour + " hour " + minute + " minutes ago";
		}
		return msg;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	public void populate(final ActivityQuestionsModel source, final ActivityQuestions target) throws ConversionException
	{
		target.setDescription(source.getDescription());
		if (source.getProduct() != null)
		{
			target.setProductCode(source.getProduct().getCode());
		}
		target.setPostedduration(postedDuration(source));
		target.setPostedBy(source.getCreatedBy().getName());
		if (CollectionUtils.isNotEmpty(source.getAnswers()))
		{
			target.setAnswers(getActivityAnswersConverter.convertAll(source.getAnswers()));
		}
		target.setCode(source.getCode());
	}

}
