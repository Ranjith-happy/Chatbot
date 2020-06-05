/**
 *
 */
package org.chatbot1905.facades.livechat.populator;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.user.UserService;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
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

	public void populate(final ActivityQuestions source, final ActivityQuestionsModel target) throws ConversionException
	{

		target.setDescription(source.getDescription());
		if (StringUtils.isNotBlank(source.getProductCode()))
		{
			target.setProduct(productService.getProductForCode(source.getProductCode()));
		}
		target.setCreatedBy(userService.getCurrentUser());
		target.setIsActive(true);
	}

}
