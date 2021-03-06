/**
 *
 */
package org.chatbot1905.facades.livechat.populator;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import org.chatbot1905.facades.product.data.ActivityAnswers;
import org.happybot.model.ActivityAnswersModel;

/**
 * @author Achyuth.guddeti
 *
 */
public class GetActivityAnswersPopulator implements Populator<ActivityAnswersModel, ActivityAnswers>
{
	@Override
	public void populate(final ActivityAnswersModel source, final ActivityAnswers target) throws ConversionException
	{
		target.setDescription(source.getDescription());
		if (source.getProduct() != null)
		{
			target.setProductCode(source.getProduct().getCode());
		}
		target.setPostedBy(source.getCreatedBy().getName());
		target.setCode(source.getCode());

	}

}
