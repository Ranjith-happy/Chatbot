/**
 *
 */
package org.happybot.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.commercefacades.user.data.CustomerData;

import java.util.List;

import javax.annotation.Resource;

import org.chatbot1905.facades.livechat.LiveChatFacades;
import org.chatbot1905.facades.product.data.ActivityQuestions;
import org.chatbot1905.facades.product.data.rest.CommonRestResponseObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author Ranjith.s
 *
 */
@Controller
@RequestMapping(value = "/chat")
public class LiveChatPageController extends AbstractPageController
{

	@Resource(name = "liveChatFacades")
	private LiveChatFacades liveChatFacades;

	@ResponseBody
	@GetMapping(value = "/resetflag")
	public CommonRestResponseObject updateActiveFlag()
	{
		liveChatFacades.updateActiveFlag();
		return WebUtils.createResponseObject("success");
	}

	@ResponseBody
	@PostMapping(value = "/updatelikes")
	public CommonRestResponseObject updateLikesCount(@RequestParam("userId") final String userId)
	{
		liveChatFacades.updateLikesCount(userId);
		return WebUtils.createResponseObject("success");
	}

	@ResponseBody
	@GetMapping(value = "/active")
	public CommonRestResponseObject getCurrentlyActiveUser()
	{
		final List<CustomerData> userList = liveChatFacades.getActiveCustomerList();
		return WebUtils.createResponseObject(userList);
	}



	@ResponseBody
	@PostMapping(value = "/saveActivityQuestions")
	public CommonRestResponseObject saveQuestions(@RequestBody
	final ActivityQuestions activityQuestions)
	{
		liveChatFacades.saveActivityQuestions(activityQuestions);
		return WebUtils.createResponseObject("success");
	}

	@ResponseBody
	@GetMapping(value = "/getActivityQuestions")
	public CommonRestResponseObject getLast24hoursQuestions()
	{
		final List<ActivityQuestions> questionsList = liveChatFacades.getPostedQuestions();
		return WebUtils.createResponseObject(questionsList);
	}
}
