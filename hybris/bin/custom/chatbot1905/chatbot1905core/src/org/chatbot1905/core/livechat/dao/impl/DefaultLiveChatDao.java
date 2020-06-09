/**
 *
 */
package org.chatbot1905.core.livechat.dao.impl;

import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.commerceservices.search.flexiblesearch.data.SortQueryData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.util.Config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.chatbot1905.core.livechat.dao.LiveChatDao;
import org.happybot.model.ActivityAnswersModel;
import org.happybot.model.ActivityQuestionsModel;
import org.joda.time.DateTime;


/**
 * @author Pooja
 *
 */
public class DefaultLiveChatDao implements LiveChatDao
{

	private FlexibleSearchService flexibleSearchService;

	@Resource(name = "pagedFlexibleSearchService")
	private PagedFlexibleSearchService pagedFlexibleSearchService;

	/**
	 * @return the flexibleSearchService
	 */
	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	/**
	 * @param flexibleSearchService
	 *           the flexibleSearchService to set
	 */
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

	private static final String CUSTOMER_ActiveList = "SELECT {pk} FROM {user} where {isCurrentlyActive}=1 and {user.uid} !=?uid ";

	private static final String CUSTOMER_LAST_24_HR_ASKED_QUESTIONS = "SELECT {pk} from {ActivityQuestions} where {creationtime} >=?hours and {createdBy} !=?currentUser";

	private static final String SORT_QUESTIONS_BY_DATE = " ORDER BY {" + ActivityQuestionsModel.CREATIONTIME + "} DESC, {"
			+ ActivityQuestionsModel.PK + "}";

   private static final String CUSTOMER_ANSWERED_ASKED_QUESTIONS = "SELECT {pk} from {ActivityQuestions} where {createdby} ='currentUser'";

	private static final String CUSTOMER_ANSWERED_LIKES = "SELECT {pk} FROM {ActivityAnswers}";

	private static final String SPECIFIC_ANSWER = "SELECT {pk} FROM {ActivityAnswers} where {description}=?desc";

	private static final String CUSTOMER_ANSWERED_LIKES_COUNT = "select {likes} from {Likescount} where {answer}=?answer";

	@Override
	public List<UserModel> getActiveCustomerList(final String uid)
	{
		final Map<String, Object> params = new HashMap<String, Object>();
		final StringBuilder builder = new StringBuilder(CUSTOMER_ActiveList);
		params.put("uid", uid);
		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		query.addQueryParameters(params);
		final SearchResult<UserModel> result = flexibleSearchService.search(query);
		final List<UserModel> propList = new ArrayList<>(result.getResult());
		return propList;
	}


	@Override
	public SearchPageData<ActivityQuestionsModel> getLast24HoursPostedQuestions(final UserModel user,
			final PageableData pageableData)
	{
		final List<SortQueryData> sortQueries;
		final Map<String, Object> params = new HashMap<String, Object>();
		final StringBuilder builder = new StringBuilder(CUSTOMER_LAST_24_HR_ASKED_QUESTIONS + SORT_QUESTIONS_BY_DATE);
		params.put("currentUser", user);
		params.put("hours", subtractDaysFromCurrentDate());
		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		query.addQueryParameters(params);
		final SearchPageData<ActivityQuestionsModel> result = pagedFlexibleSearchService.search(query, pageableData);
		return result;
	}



	private String subtractDaysFromCurrentDate()
	{
		final DateTime dateTime = new DateTime().minusHours(Integer.parseInt(Config.getParameter("last.twenty.four.hour")));
		final Date datenew = dateTime.toDate();
		final SimpleDateFormat formatnew = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatnew.format(datenew);
	}

   private String subtractMonthsFromCurrentDate()
   {
       final DateTime dateTime = new DateTime().minusMonths(1);
       final Date datenew = dateTime.toDate();
       final SimpleDateFormat formatnew = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
       return formatnew.format(datenew);
   }

	@Override
	public List<ActivityQuestionsModel> getActivityAnswers(final String uid, final PageableData pageableData)
   {
       final Map<String, Object> params = new HashMap<String, Object>();
       final StringBuilder builder = new StringBuilder(CUSTOMER_ANSWERED_ASKED_QUESTIONS);
       params.put("uid", uid);
       params.put("creationTime", subtractMonthsFromCurrentDate());
       final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
       query.addQueryParameters(params);
       final SearchResult<ActivityQuestionsModel> result = flexibleSearchService.search(query);
       final List<ActivityQuestionsModel> propList = new ArrayList<>(result.getResult());
       return propList;

   }

	@Override
	public Integer getlikescount(final String string)
	{
		final Map<String, Object> params = new HashMap<String, Object>();
		final StringBuilder builder = new StringBuilder(CUSTOMER_ANSWERED_LIKES_COUNT);
		params.put("answer", string);
		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		query.addQueryParameters(params);
		final SearchResult<Integer> result = flexibleSearchService.search(query);
		final List<Integer> propList = result.getResult();
		return propList.get(0);
	}

	@Override
	public ActivityAnswersModel getspecificAnswer(final String string)
	{
		final Map<String, Object> params = new HashMap<String, Object>();
		final StringBuilder builder = new StringBuilder(SPECIFIC_ANSWER);
		params.put("desc", string);
		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		query.addQueryParameters(params);
		final SearchResult<ActivityAnswersModel> result = flexibleSearchService.search(query);
		final List<ActivityAnswersModel> propList = result.getResult();
		return propList.get(0);
	}


}

