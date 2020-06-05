/**
 *
 */
package org.happybot.controllers.pages;

import de.hybris.platform.acceleratorfacades.order.AcceleratorCheckoutFacade;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.catalog.CatalogService;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.OrderFacade;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.ProductSearchFacade;
import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commerceservices.enums.SearchQueryContext;
import de.hybris.platform.commerceservices.strategies.CheckoutCustomerStrategy;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.model.ModelService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
@RequestMapping(value = "/order")
public class HappyBotOrderAddonPageController extends AbstractSearchPageController
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
		questionAnswer.put("order-inquiry", "We have found some results! Check if these are relevant!!");

	}
	private static final Logger LOG = Logger.getLogger(HappyBotOrderAddonPageController.class);



	@RequestMapping(value = "/happbot", method = RequestMethod.GET)
	public String getDetails(final Model model, @RequestParam("term")
	final String term, @RequestParam(value = "page", defaultValue = "0")
	final int page, // NOSONAR
			@RequestParam(value = "show", defaultValue = "Page")
			final AbstractSearchPageController.ShowMode showMode, @RequestParam(value = "sort", required = false)
			final String sortCode, @RequestParam(value = "question", required = false)
			final String question, @RequestParam(value = "latitude", required = false)
			final Double latitude, @RequestParam(value = "longitude", required = false)
			final Double longitude, @RequestParam(value = "contextCategory", required = false)
			final String contextCategory, final String productCode, final String orderCode)
			throws CMSItemNotFoundException, UnknownIdentifierException, FileNotFoundException, IOException
	{

		String answer = "";

		final ChatBotResponseData chatBotResponseData = new ChatBotResponseData();

		final DoccatModel models = trainCategorizerModel();

		while (true)
		{

			final String userInput = term;
			List<String> sentanceList = new ArrayList<String>();

			if (productCode.equalsIgnoreCase("TestProduct"))
			{
				sentanceList = breakSentences(userInput);

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
						longitude, contextCategory, orderCode);


				// If category conversation-complete, we will end chat conversation.
				if ("conversation-complete".equals(category))
				{
					conversationComplete = true;
				}

				if (answer != null && answer.equals(""))
				{
					answer = "Did you mean?";
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
		return "addon:/happybot/pages/bot/chatbotResponseOrder";
	}

	/**
	 * @throws IOException
	 * @throws FileNotFoundException
	 *
	 */
	private String findResults(final String category, final ChatBotResponseData chatBotResponseData, final String[] tokens,
			final String sentence, final Model model, final int page, final String sortCode,
			final AbstractSearchPageController.ShowMode showMode, final Double latitude, final Double longitude,
			final String contextCategory, final String orderCode) throws FileNotFoundException, IOException
	{
		String answer = "";
		final Random randomObj = new Random();

		if (category.isEmpty())
		{

			answer = "Did you really mean" + sentence;
		}
		if (category.equalsIgnoreCase("order-inquiry") || category.equalsIgnoreCase("delivery-info"))
		{
			OrderData orderData = null;
			List<OrderData> orderDataList = null;
			orderData = orderFacade.getOrderDetailsForCode(orderCode);
			orderDataList = new ArrayList<OrderData>();
			orderDataList.add(orderData);
			chatBotResponseData.setOrderData(orderDataList);
			if (category.equalsIgnoreCase("order-inquiry"))
			{


				answer = "Thank you for contacting us. We will defnitely provide you the update";
				model.addAttribute(CHAT_CATEGORY, "order-inquiry");
			}
			if (category.equalsIgnoreCase("delivery-info"))
			{

				model.addAttribute(CHAT_CATEGORY, "delivery-info");
				answer = "Thank you for contacting us. Dont worry. We are here to serve you better";
			}
		}

		if (category.equalsIgnoreCase("expadite-delivery"))
		{

			model.addAttribute(CHAT_CATEGORY, "expadite-delivery");
			openConnection(orderCode);
			answer = "Dont worry. We will help you to expidite your order delivery process";

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
		final File faqFile = new File("D:\\Blitz\\Blitz\\chatbot_help_folder\\faq-categorizer-order.txt");
		final InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(faqFile);

		final ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, StandardCharsets.UTF_8);
		final ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

		final DoccatFactory factory = new DoccatFactory(new FeatureGenerator[]
		{ new BagOfWordsFeatureGenerator() });

		final TrainingParameters params = ModelUtil.createDefaultTrainingParameters();
		params.put(TrainingParameters.CUTOFF_PARAM, 0);

		// Train a model with classifications from above file.
		final DoccatModel models = DocumentCategorizerME.train("en", sampleStream, params, factory);
		return models;
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

	public void openConnection(final String orderCode) throws IOException
	{
		try
		{
			final URL url = new URL(
					"http://electronics.local:9001/happyWebservices/v2/electronics/orders/expediteDelivery/" + orderCode);

			final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("PUT");
			final InputStreamReader in = new InputStreamReader(conn.getInputStream());
			LOG.info("connection Response Code" + conn.getResponseCode());
			conn.disconnect();
		}
		catch (final Exception e)
		{
			LOG.info("Error While Connecting" + e);
		}


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
