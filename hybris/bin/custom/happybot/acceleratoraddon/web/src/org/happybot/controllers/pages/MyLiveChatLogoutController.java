/**
 *
 */
package org.happybot.controllers.pages;

import de.hybris.platform.core.model.user.UserModel;

import java.util.List;

import javax.annotation.Resource;

import org.chatbot1905.facades.livechat.LiveChatFacades;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Pooja
 *
 */
@Controller
@RequestMapping(value = "/liveChat")
public class MyLiveChatLogoutController
{

	@Resource(name = "liveChatFacades")
	private LiveChatFacades liveChatFacades;

	@ResponseBody
	@RequestMapping(value = "/resetFlag", method = RequestMethod.GET)
	public String isCurrentlyActiveFlagReset()
	{
		liveChatFacades.isCurrenntlyActiveFlgReset();
		return "success";
	}

	@ResponseBody
	@RequestMapping(value = "/noOfLikes", method = RequestMethod.POST)
	public String noOfLikesCount(@RequestParam("userId")
	final String userId)
	{
		liveChatFacades.noOfLikes(userId);
		return "success";
	}

	@ResponseBody
	@RequestMapping(value = "/active", method = RequestMethod.GET)
	public String ListOfCurrentlyActiveUser()
	{
		final List<UserModel> userList = liveChatFacades.getActiveCustomerList();
		return "success";
	}
}
