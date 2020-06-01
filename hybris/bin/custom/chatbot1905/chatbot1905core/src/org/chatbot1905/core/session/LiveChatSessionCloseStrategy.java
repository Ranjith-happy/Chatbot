/**
 *
 */
package org.chatbot1905.core.session;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.servicelayer.web.DefaultSessionCloseStrategy;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


/**
 * @author Pooja
 *
 */
public class LiveChatSessionCloseStrategy extends DefaultSessionCloseStrategy
{
	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "modelService")
	private ModelService modelService;

	@Override
	public void closeSessionInHttpSession(final HttpSession httpSession)
	{
		final UserModel userModel = userService.getCurrentUser();
		userModel.setIsCurrentlyActive(false);
		modelService.save(userModel);
		modelService.refresh(userModel);
		super.closeSessionInHttpSession(httpSession);
	}

}
