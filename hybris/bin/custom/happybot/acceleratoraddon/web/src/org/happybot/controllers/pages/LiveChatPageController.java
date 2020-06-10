/**
 *
 */
package org.happybot.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.util.Config;

import java.util.List;

import javax.annotation.Resource;

import org.chatbot1905.facades.livechat.LiveChatFacades;
import org.chatbot1905.facades.product.data.ActivityAnswers;
import org.chatbot1905.facades.product.data.ActivityQuestions;
import org.chatbot1905.facades.product.data.rest.CommonRestResponseObject;
import org.happybot.model.ActivityAnswersModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class LiveChatPageController extends AbstractSearchPageController
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
	public CommonRestResponseObject updateLikesCount(@RequestBody final String activityAnswers)
	{
		final ActivityAnswersModel activityAnswer = liveChatFacades.specificAnswer(activityAnswers);
		liveChatFacades.updateLikesCount(activityAnswer);
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
	public CommonRestResponseObject saveQuestions(@RequestBody final ActivityQuestions activityQuestions)
	{
		liveChatFacades.saveActivityQuestions(activityQuestions);
		return WebUtils.createResponseObject("success");
	}


	@ResponseBody
	@GetMapping(value = "/getActivityQuestions")
	public CommonRestResponseObject getLast24hoursQuestions(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode, final Model model)
	{
		final int noIfRecordsinOnePage = Integer.parseInt(Config.getParameter("number.of.records.in.one.page"));
		final PageableData pageableData = createPageableData(page, noIfRecordsinOnePage, null, showMode);
		final SearchPageData<ActivityQuestions> searchPageData = liveChatFacades.getPostedQuestions(pageableData);
		populateModel(model, searchPageData, showMode);
		return WebUtils.createResponseObject(searchPageData);
	}

	@ResponseBody
	@PostMapping(value = "/saveActivityAnswers")
	public CommonRestResponseObject saveAnswers(@RequestParam("activityAnswers") final ActivityAnswers activityAnswers)
	{
		liveChatFacades.saveActivityAnswers(activityAnswers);
		return WebUtils.createResponseObject("success");
	}

	@ResponseBody
	@GetMapping(value = "/getActivityAnswers")
	public CommonRestResponseObject getActivityAnswers(@RequestParam(value = "page", defaultValue = "0")
	final int page, @RequestParam(value = "show", defaultValue = "Page")
	final ShowMode showMode, final Model model)
	{
		final int noIfRecordsinOnePage = Integer.parseInt(Config.getParameter("number.of.records.in.one.page"));
		final PageableData pageableData = createPageableData(page, noIfRecordsinOnePage, null, showMode);
		final SearchPageData<ActivityQuestions> searchPageData = liveChatFacades.getActivityAnswers(pageableData);
		populateModel(model, searchPageData, showMode);
		return WebUtils.createResponseObject(searchPageData);
	}

	@ResponseBody
	@GetMapping(value = "/questionIsAnsweredPopup")
	public CommonRestResponseObject questionIAnsweredAnswers()
	{
		final boolean result = liveChatFacades.questionIsAnswered();
		return WebUtils.createResponseObject(result);
	}

	@ResponseBody
	@PostMapping(value = "/closePopup")
	public CommonRestResponseObject updateIActiveFlagOfAnswer()
	{
		final boolean result = liveChatFacades.updateIActiveFlagOfAnswer();
		return WebUtils.createResponseObject(result);
	}
}
