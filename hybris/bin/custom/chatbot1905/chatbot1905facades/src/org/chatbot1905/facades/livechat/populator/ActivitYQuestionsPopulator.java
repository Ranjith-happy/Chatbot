/**
 *
 */
package org.chatbot1905.facades.livechat.populator;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.user.UserService;

import javax.annotation.Resource;

import org.chatbot1905.facades.product.data.ActivityQuestions;
import org.happybot.model.ActivityQuestionsModel;


/**
 * @author Pooja
 *
 */
public class ActivitYQuestionsPopulator implements Populator<ActivityQuestions, ActivityQuestionsModel>
{
	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "userService")
	private UserService userService;

	@Override
	public void populate(final ActivityQuestions source, final ActivityQuestionsModel target) throws ConversionException
	{
		// XXX Auto-generated method stub
		target.setDescription(source.getDescription());
		target.setProduct(productService.getProductForCode(source.getProductCode()));
		target.setCreatedBy(userService.getCurrentUser());
		target.setIsActive(true);
	}

}
