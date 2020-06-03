/**
 *
 */
package org.chatbot1905.facades.livechat.populator;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

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
		// XXX Auto-generated method stub
		target.setDescription(source.getDescription());
		target.setProductCode(source.getProduct().getCode());
	}

}
