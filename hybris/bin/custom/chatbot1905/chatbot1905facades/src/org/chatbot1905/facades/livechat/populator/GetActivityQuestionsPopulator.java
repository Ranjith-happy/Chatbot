/**
 *
 */
package org.chatbot1905.facades.livechat.populator;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.chatbot1905.facades.product.data.ActivityQuestions;
import org.happybot.model.ActivityQuestionsModel;


/**
 * @author Pooja
 *
 */
public class GetActivityQuestionsPopulator implements Populator<ActivityQuestionsModel, ActivityQuestions>
{

	@Override
	public void populate(final ActivityQuestionsModel source, final ActivityQuestions target) throws ConversionException
	{
		target.setDescription(source.getDescription());
		target.setProductCode(source.getProduct().getCode());
		target.setPostedduration(postedDuration(source));
		target.setPostedBy(source.getCreatedBy().getName());
	}

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

}
