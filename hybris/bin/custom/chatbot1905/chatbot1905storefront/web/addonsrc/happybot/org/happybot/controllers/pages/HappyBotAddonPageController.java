/**
 *
 */
package org.happybot.controllers.pages;

import de.hybris.platform.acceleratorfacades.order.AcceleratorCheckoutFacade;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.catalog.CatalogService;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogModel;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.OrderFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderHistoryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.ProductSearchFacade;
import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.enums.SearchQueryContext;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.store.data.GeoPoint;
import de.hybris.platform.commerceservices.storefinder.data.StoreFinderSearchPageData;
import de.hybris.platform.commerceservices.strategies.CheckoutCustomerStrategy;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.order.CartService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.chatbot1905.facades.coupon.ChatbotCouponFacade;
import org.happybot.search.data.ChatBotResponseData;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import opennlp.tools.doccat.BagOfWordsFeatureGenerator;
import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.doccat.FeatureGenerator;
import opennlp.tools.lemmatizer.LemmatizerME;
import opennlp.tools.lemmatizer.LemmatizerModel;
import opennlp.tools.namefind.BioCodec;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderFactory;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;
import opennlp.tools.util.TrainingParameters;
import opennlp.tools.util.model.ModelUtil;

@Controller
@RequestMapping(value = "/addon")
public class HappyBotAddonPageController extends AbstractSearchPageController
{

	private static final String CHAT_CATEGORY = "chat_category";
	@Resource(name = "productSearchFacade")
	private ProductSearchFacade<ProductData> productSearchFacade;

	@Resource(name = "cmsSiteService")
	private CMSSiteService cmsSiteService;

	@Resource(name = "categoryService")
	private CategoryService categoryService;

	private CatalogService catalogService;
	private CatalogVersionService catalogVersionService;

	@Resource(name = "storeFinderFacade")
	private StoreFinderFacade storeFinderFacade;


	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;


	@Resource(name = "orderFacade")
	private OrderFacade orderFacade;

	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	@Resource(name = "cartService")
	private CartService cartService;

	@Resource(name = "chatbotCouponFacade")
	private ChatbotCouponFacade chatbotCouponFacade;

	@Resource(name = "acceleratorCheckoutFacade")
	private AcceleratorCheckoutFacade checkoutFacade;

	private ModelService modelService;

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	protected AcceleratorCheckoutFacade getCheckoutFacade()
	{
		return checkoutFacade;
	}

	private static Map<String, String> questionAnswer = new HashMap<>();



	@Required
	public void setCartService(final CartService cartService)
	{
		this.cartService = cartService;
	}

	private CheckoutCustomerStrategy checkoutCustomerStrategy;
	/*
	 * Define answers for each given category.
	 */
	static
	{
		questionAnswer.put("greeting", "Hello, how can I help you?");
		questionAnswer.put("product-inquiry", "We have found some results! Check if these are relevant!!");
		questionAnswer.put("price-inquiry", "Price is $300");
		questionAnswer.put("conversation-continue", "What else can I help you with?");
		questionAnswer.put("conversation-complete", "Nice chatting with you. Bbye.");
		questionAnswer.put("store_locator", "your nereast store is..");
		questionAnswer.put("place-order", "Thank you. We have received your Order. Here the order number");
		questionAnswer.put("addto-cart",
				"Your product is added to cart. You can still add more products. If you are done please confirm to");
	}
	private static final Logger LOG = Logger.getLogger(HappyBotAddonPageController.class);




	@RequestMapping(value = "/happbot", method = RequestMethod.GET)
	public String getProductDetails(final Model model, @RequestParam("term")
	final String term, @RequestParam(value = "page", defaultValue = "0")
	final int page, // NOSONAR
			@RequestParam(value = "show", defaultValue = "Page")
			final AbstractSearchPageController.ShowMode showMode, @RequestParam(value = "sort", required = false)
			final String sortCode, @RequestParam(value = "question", required = false)
			final String question, @RequestParam(value = "latitude", required = false)
			final Double latitude, @RequestParam(value = "longitude", required = false)
			final Double longitude, @RequestParam(value = "contextCategory", required = false)
			final String contextCategory, final String productCode, final boolean addToCartFlag)
			throws CMSItemNotFoundException, UnknownIdentifierException, FileNotFoundException, IOException
	{
		LOG.info("########## ProductDetailsController updateOldPassword() method #####");


		String answer = "";

		final ChatBotResponseData chatBotResponseData = new ChatBotResponseData();

		final JaloSession session = JaloSession.getCurrentSession();

		final User user = session.getUser();

		final BaseSiteModel currentSite = getBaseSiteService().getCurrentBaseSite();
		final List<CatalogModel> productCatalogs = getBaseSiteService().getProductCatalogs(currentSite);

		final DoccatModel models = trainCategorizerModel();

		while (true)
		{

			final String userInput = term;
			List<String> sentanceList = new ArrayList<String>();

			if (productCode.equalsIgnoreCase("TestProduct"))
			{
				sentanceList = breakSentences(userInput);

			}
			else
			{
				if (addToCartFlag)
				{
					sentanceList.add("add to cart " + productCode);


				}
				else
				{
					sentanceList.add("place order for " + productCode);
				}


			}

			boolean conversationComplete = true;


			for (final String sentence : sentanceList)
			{

				// Separate words from each sentence using tokenizer.
				final String[] tokens = tokenizeSentence(sentence);

				// Tag separated words with POS tags to understand their gramatical structure.
				final String[] posTags = detectPOSTags(tokens);

				// Lemmatize each word so that its easy to categorize.
				final String[] lemmas = lemmatizeTokens(tokens, posTags);

				// Determine BEST category using lemmatized tokens used a mode that we trained
				// at start.
				final String category = detectCategory(models, lemmas);
				answer = findResults(category, chatBotResponseData, tokens, sentence, model, page, sortCode, showMode, latitude,
						longitude, contextCategory, productCode);


				// If category conversation-complete, we will end chat conversation.
				if ("conversation-complete".equals(category))
				{
					conversationComplete = true;
				}

				if (answer != null && answer.equals(""))
				{
					answer = Config.getParameter("happybot.chatbot.question.category." + category);
				}
			}

			// Print answer back to user. If conversation is marked as complete, then end
			// loop & program.
			LOG.info("##### Chat Bot: " + answer);
			if (conversationComplete)
			{
				break;
			}

		}
		chatBotResponseData.setResponseText(answer);
		model.addAttribute("chatBotResponseData", chatBotResponseData);
		return "addon:/happybot/pages/bot/chatbotResponse";
	}



	/**
	 * @throws IOException
	 * @throws FileNotFoundException
	 *
	 */
	private String findResults(final String category, final ChatBotResponseData chatBotResponseData, final String[] tokens,
			final String sentence, final Model model, final int page, final String sortCode,
			final AbstractSearchPageController.ShowMode showMode, final Double latitude, final Double longitude,
			final String contextCategory, final String productCode) throws FileNotFoundException, IOException
	{
		String answer = "";
		final Random randomObj = new Random();

		if (category.isEmpty())
		{

			answer = "Did you really mean" + sentence;
		}

		if (category.equalsIgnoreCase("search-yes"))
		{
			model.addAttribute(CHAT_CATEGORY, "search-yes");
			answer = "Please select the category of which you want the product";
		}

		if (category.equalsIgnoreCase("go-for"))
		{
			model.addAttribute(CHAT_CATEGORY, "go-for");
			answer = "Kindly provide the specification";
		}

		if (category.equalsIgnoreCase("search-no"))
		{
			model.addAttribute(CHAT_CATEGORY, "search-no");
			answer = "Sure !We are Here to assist you.kindly provide your query";
		}

		if (category.equalsIgnoreCase("product-inquiry"))
		{
			final TokenNameFinderModel productDataModels = trainProductDataModel();
			final String prdSearchTerm = findSearchTerm(productDataModels, tokens, sentence);
			LOG.info("Search term : " + prdSearchTerm);
			final List<ProductData> solrProductResult = findProductSolr(prdSearchTerm);
			if (solrProductResult.isEmpty())
			{
				answer = "Sorry we could not find the product you are searching for.. dont worry we will let you know once we find it our store back.";
			}
			else
			{
				chatBotResponseData.setProducts(subList(solrProductResult, 5));
				model.addAttribute("carousalIndex", randomObj.nextInt());

				answer = answer + " " + questionAnswer.get(category);


			}
		}
		if (category.equalsIgnoreCase("order-status"))
		{

			List<OrderData> orderDataList = null;
			final TokenNameFinderModel orderDataModels = trainOrderDataModel();
			final String ordSearchTerm = findSearchTerm(orderDataModels, tokens, sentence);
			LOG.info("Search term : " + ordSearchTerm);
			OrderData orderData = null;
			List<OrderHistoryData> orderHistoryData = null;
			if (!sentence.equals(ordSearchTerm))
			{
				orderData = orderFacade.getOrderDetailsForCode(ordSearchTerm);
				if (orderData != null)
				{
					final List<OrderData> ordData = new ArrayList<OrderData>();
					ordData.add(orderData);
					chatBotResponseData.setOrderData(ordData);
				}
			}
			else
			{
				final PageableData pageableData = createPageableData(page, 5, sortCode, showMode);
				orderHistoryData = orderFacade.getPagedOrderHistoryForStatuses(pageableData).getResults();
				if (orderHistoryData != null)
				{
					chatBotResponseData.setOrderHistoryData(orderHistoryData);
				}
			}

			if (null != orderData)
			{
				orderDataList = new ArrayList<OrderData>();
				orderDataList.add(orderData);
				chatBotResponseData.setOrderData(orderDataList);

			}
			model.addAttribute(CHAT_CATEGORY, "order-status");
		}
		if (category.equalsIgnoreCase("place-order"))
		{

			final long qty = 1;
			OrderData orderData = null;
			try
			{
				final String[] parts = sentence.split(" ");
				String lastWord = parts[parts.length - 1];

				if (null == lastWord || lastWord.isEmpty())
				{
					lastWord = productCode;
				}
				final CartData cartData = cartFacade.getSessionCart();
				LOG.info("Cart" + cartData);

				final CartModel cartModel = cartService.getSessionCart();
				if (null == cartModel.getEntries() || cartModel.getEntries().isEmpty())
				{
					final CartModificationData cartModification = cartFacade.addToCart(lastWord, qty);
					LOG.info("cartModification" + cartModification);
				}

				try
				{
					orderData = getCheckoutFacade().placeOrder();
				}
				catch (final Exception e)
				{
					answer = "Sorry you can not place order here for this product .Contact customer service Team";
				}
				cartService.removeSessionCart();
			}
			catch (final CommerceCartModificationException ex)
			{

				answer = "Sorry you can not place order here for this product. Contact customer service Team";
			}

			answer = questionAnswer.get(category) + " " + orderData.getCode();
		}
		if (category.equalsIgnoreCase("addto-cart"))
		{

			final long qty = 1;
			final String[] parts = sentence.split(" ");
			String lastWord = parts[parts.length - 1];

			try
			{

				if (null == lastWord || lastWord.isEmpty())
				{
					lastWord = productCode;
				}
				final CartData cartData = cartFacade.getSessionCart();
				final CartModificationData cartModification = cartFacade.addToCart(lastWord, qty);
				final CartModel cartModel = cartService.getSessionCart();
				cartService.setSessionCart(cartModel);

			}
			catch (final CommerceCartModificationException ex)
			{

				answer = "Sorry you can not add this product to cart Contact customer service Team";
			}
			answer = questionAnswer.get(category) + "<a href=\"#\" class=\"chatBotAllOrder\" data-value=\"" + lastWord
					+ "\">Place order</a>";

		}
		if (category.equalsIgnoreCase("store-finder"))
		{

			if (latitude != null & longitude != null)
			{
				final GeoPoint geoPoint = new GeoPoint();
				geoPoint.setLatitude(latitude);
				geoPoint.setLongitude(longitude);
				final StoreFinderSearchPageData<PointOfServiceData> searchResult = storeFinderFacade.positionSearch(geoPoint,
						createPageableData(page, 1, sortCode, showMode));

				if (null != searchResult)
				{
					final List<PointOfServiceData> pointOfServiceData = searchResult.getResults();

					chatBotResponseData.setPointOfServices(pointOfServiceData);
					model.addAttribute("mapIndex", randomObj.nextInt());

				}
				else
				{
					answer = "We could not find any store near to your GPS location... Well you can still search by entering zipcode or town";
				}
			}
			else
			{
				answer = "We could not find your GPS location... Well you can still search by zipcode or town";
			}
		}
		if (category.equalsIgnoreCase("my-promotions"))
		{
			try
			{
				chatBotResponseData.setCouponData(subList(chatbotCouponFacade.getAllVouchers(), 6));
			}
			catch (final Exception exception)
			{
				exception.printStackTrace();
				answer = "We could not find any promotions at this time. Please try after some time";
			}
		}
		if (category.equalsIgnoreCase("faq-reset-password"))
		{

			model.addAttribute("contextCategory", category);
		}
		if (contextCategory != null && contextCategory.equalsIgnoreCase("faq-reset-password"))
		{

			model.addAttribute("contextCategory", "");
			try
			{
				customerFacade.forgottenPassword(sentence);
				answer = "Your password reset mail has been sent to your mail id. Click the link provided to reset.";
			}
			catch (final UnknownIdentifierException unknownIdentifierException)
			{
				LOG.warn("Email: " + sentence + " does not exist in the database.");
				answer = "We could not find your email... Please recheck if this is your email" + sentence;
				model.addAttribute("contextCategory", "faq-reset-password");
			}
		}

		return answer;
	}

	protected <E> List<E> subList(final List<E> list, final int maxElements)
	{
		if (CollectionUtils.isEmpty(list))
		{
			return Collections.emptyList();
		}

		if (list.size() > maxElements)
		{
			return list.subList(0, maxElements);
		}

		return list;
	}

	public static DoccatModel trainCategorizerModel() throws FileNotFoundException, IOException
	{
		// faq-categorizer.txt is a custom training data with categories as per our chat
		// requirements.
		final File faqFile = new File("D:\\Blitz\\Blitz\\chatbot_help_folder\\faq-categorizer.txt");
		final InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(faqFile);

		final ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, StandardCharsets.UTF_8);
		final ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

		final DoccatFactory factory = new DoccatFactory(new FeatureGenerator[]
		{ new BagOfWordsFeatureGenerator() });

		final TrainingParameters params = ModelUtil.createDefaultTrainingParameters();
		params.put(TrainingParameters.CUTOFF_PARAM, 0);

		// Train a model with classifications from above file.
		final DoccatModel model = DocumentCategorizerME.train("en", sampleStream, params, factory);
		return model;
	}

	private static List<String> breakSentences(final String data) throws FileNotFoundException, IOException
	{
		// Better to read file once at start of program & store model in instance
		// variable. but keeping here for simplicity in understanding.
		try (InputStream modelIn = new FileInputStream("D:\\Blitz\\Blitz\\chatbot_help_folder\\OpenNLP_bins\\en-sent.bin"))
		{

			final SentenceDetectorME myCategorizer = new SentenceDetectorME(new SentenceModel(modelIn));

			final String[] sentences = myCategorizer.sentDetect(data);
			LOG.info("Sentence Detection: " + Arrays.stream(sentences).collect(Collectors.joining(" | ")));
			final List sentencelist = Arrays.asList(sentences);
			return sentencelist;
		}
	}

	private static String[] tokenizeSentence(final String sentence) throws FileNotFoundException, IOException
	{
		// Better to read file once at start of program & store model in instance
		// variable. but keeping here for simplicity in understanding.
		try (InputStream modelIn = new FileInputStream("D:\\Blitz\\Blitz\\chatbot_help_folder\\OpenNLP_bins\\en-token.bin"))
		{
			// Initialize tokenizer tool
			final TokenizerME myCategorizer = new TokenizerME(new TokenizerModel(modelIn));

			// Tokenize sentence.
			final String[] tokens = myCategorizer.tokenize(sentence);
			LOG.info("Tokenizer : " + Arrays.stream(tokens).collect(Collectors.joining(" | ")));

			return tokens;
		}
	}

	private static String[] detectPOSTags(final String[] tokens) throws IOException
	{
		// Better to read file once at start of program & store model in instance
		// variable. but keeping here for simplicity in understanding.
		try (InputStream modelIn = new FileInputStream("D:\\Blitz\\Blitz\\chatbot_help_folder\\OpenNLP_bins\\en-pos-maxent.bin"))
		{
			// Initialize POS tagger tool
			final POSTaggerME myCategorizer = new POSTaggerME(new POSModel(modelIn));

			// Tag sentence.
			final String[] posTokens = myCategorizer.tag(tokens);
			LOG.info("POS Tags : " + Arrays.stream(posTokens).collect(Collectors.joining(" | ")));

			return posTokens;
		}
	}

	private static String[] lemmatizeTokens(final String[] tokens, final String[] posTags)
			throws InvalidFormatException, IOException
	{
		// Better to read file once at start of program & store model in instance
		// variable. but keeping here for simplicity in understanding.
		try (InputStream modelIn = new FileInputStream("D:\\Blitz\\Blitz\\chatbot_help_folder\\OpenNLP_bins\\en-lemmatizer.bin"))
		{

			// Tag sentence.
			final LemmatizerME myCategorizer = new LemmatizerME(new LemmatizerModel(modelIn));
			final String[] lemmaTokens = myCategorizer.lemmatize(tokens, posTags);
			LOG.info("Lemmatizer : " + Arrays.stream(lemmaTokens).collect(Collectors.joining(" | ")));

			return lemmaTokens;
		}
	}

	private static String detectCategory(final DoccatModel model, final String[] finalTokens) throws IOException
	{
		// Initialize document categorizer tool
		String category = "";
		final DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);

		// Get best possible category.
		final double[] probabilitiesOfOutcomes = myCategorizer.categorize(finalTokens);
		category = myCategorizer.getBestCategory(probabilitiesOfOutcomes);
		LOG.info("Category: " + category);

		return category;
	}

	public static TokenNameFinderModel trainProductDataModel() throws FileNotFoundException, IOException
	{
		// faq-categorizer.txt is a custom training data with categories as per our chat
		// requirements.
		final File prodFaqFile = new File("D:\\Blitz\\Blitz\\chatbot_help_folder\\TrainingData.txt");
		final InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(prodFaqFile);
		final ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, StandardCharsets.UTF_8);

		TokenNameFinderModel model;

		try (ObjectStream<NameSample> sampleStream = new NameSampleDataStream(lineStream))
		{
			model = NameFinderME.train("en", "productData", sampleStream, TrainingParameters.defaultParams(),
					TokenNameFinderFactory.create(null, null, Collections.emptyMap(), new BioCodec()));
		}

		return model;
	}

	public static TokenNameFinderModel trainOrderDataModel() throws FileNotFoundException, IOException
	{
		// faq-categorizer.txt is a custom training data with categories as per our chat
		// requirements.
		final File prodFaqFile = new File("D:\\Blitz\\Blitz\\chatbot_help_folder\\TrainingData-Order.txt");
		final InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(prodFaqFile);
		final ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, StandardCharsets.UTF_8);

		TokenNameFinderModel model;

		try (ObjectStream<NameSample> sampleStream = new NameSampleDataStream(lineStream))
		{
			model = NameFinderME.train("en", "orderData", sampleStream, TrainingParameters.defaultParams(),
					TokenNameFinderFactory.create(null, null, Collections.emptyMap(), new BioCodec()));
		}

		return model;
	}

	private static String findSearchTerm(final TokenNameFinderModel model, final String[] tokens, final String sentence)
			throws IOException
	{
		// Initialize Name Finder tool
		String searchTerm = sentence;
		final NameFinderME nameFinder = new NameFinderME(model);
		final Span nameSpans[] = nameFinder.find(tokens);
		for (final Span s : nameSpans)
		{
			LOG.info(s.toString() + "  " + tokens[s.getStart()]);
			searchTerm = tokens[s.getStart()];
		}

		return searchTerm;
	}

	protected int getStoreLocatorPageSize()
	{
		return getSiteConfigService().getInt("storefront.storelocator.pageSize", 0);
	}

	protected List<ProductData> findProductSolr(final String productName)
	{
		return productSearchFacade.textSearch(productName, SearchQueryContext.SUGGESTIONS).getResults();
	}

	protected CustomerModel getCurrentUserForCheckout()
	{
		return getCheckoutCustomerStrategy().getCurrentUserForCheckout();
	}

	protected CheckoutCustomerStrategy getCheckoutCustomerStrategy()
	{
		return checkoutCustomerStrategy;
	}

	@Required
	public void setCheckoutCustomerStrategy(final CheckoutCustomerStrategy checkoutCustomerStrategy)
	{
		this.checkoutCustomerStrategy = checkoutCustomerStrategy;
	}

}
