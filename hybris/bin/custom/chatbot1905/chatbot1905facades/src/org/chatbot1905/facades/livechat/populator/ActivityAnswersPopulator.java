/**
 *
 */
package org.chatbot1905.facades.livechat.populator;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.user.UserService;

import javax.annotation.Resource;

import org.chatbot1905.facades.product.data.ActivityAnswers;
import org.happybot.model.ActivityAnswersModel;

/**
 * @author Achyuth.guddeti
 *
 */
public class ActivityAnswersPopulator implements Populator<ActivityAnswers, ActivityAnswersModel>
{
	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "userService")
	private UserService userService;

	@Override
	public void populate(final ActivityAnswers source, final ActivityAnswersModel target) throws ConversionException
	{
		target.setDescription(source.getDescription());
		target.setProduct(productService.getProductForCode(source.getProductCode()));
		target.setCreatedBy(userService.getCurrentUser());
		target.setIsActive(true);

	}

}
