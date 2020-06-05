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
}
