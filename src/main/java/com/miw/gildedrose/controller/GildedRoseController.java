package com.miw.gildedrose.controller;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.miw.gildedrose.domain.BuyItemDetails;
import com.miw.gildedrose.domain.Item;
import com.miw.gildedrose.domain.messaging.BuyGildedRoseInvenotryRequest;
import com.miw.gildedrose.domain.messaging.GetGildedRoseInvenotriesResponse;
import com.miw.gildedrose.domain.messaging.GetGildedRoseInvenotryResponse;
import com.miw.gildedrose.service.GildedRoseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 * The Class GildedRoseController.
 * App Controller Implementations.
 *
 */
@RestController
@SecurityRequirement(name = "GlidedRoseInventory")
public class GildedRoseController {

	/** The Constant serialVersionUID. */
	private static final Logger logger = LoggerFactory.getLogger(GildedRoseController.class);

	/** The GildedRose Service. */
	@Autowired
	private GildedRoseService gildedRoseService;

	/**
	 * Get All the inventories.
	 * 
	 * @param headers
	 * @param queryParams
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = { "/getAllInventories" }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Get All the inventories.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = GetGildedRoseInvenotriesResponse.class))),
			@ApiResponse(responseCode = "500", description = "System error, unable to perform operation. Please contact MIW.', <br />", content = @Content(schema = @Schema(implementation = Item.class))),
			@ApiResponse(responseCode = "400", description = "BAD_REQUEST: 'Bad Request', <br />", content = @Content(schema = @Schema(implementation = Item.class))) })
	public ResponseEntity<GetGildedRoseInvenotriesResponse> getAll(
			@RequestHeader(name = "Generic Headers", required = false) Map<String, String> headers,
			@RequestParam(defaultValue = "false", required = false) Map<String, String> queryParams) throws Exception {
		StopWatch watch = new StopWatch();
		watch.start();
		try {
			logger.info("Entering GildedRoseController for retrieving all the inventories");
			List<Item> itemEntities = gildedRoseService.getAllGlideRoseInventories();
			GetGildedRoseInvenotriesResponse getItemEntities = new GetGildedRoseInvenotriesResponse(itemEntities);
			System.out.println("getItemEntities : ");
			return new ResponseEntity<GetGildedRoseInvenotriesResponse>(getItemEntities, HttpStatus.OK);
		} finally {
			watch.stop();
			logger.info("Time taken to get all the inventories request :{} ms ", watch.getTotalTimeMillis());
		}
	}

	/**
	 * Get the requested inventory.
	 * 
	 * @param name
	 * @param headers
	 * @param queryParams
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = { "/getInventory/{id}" }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Get the requested inventory.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = GetGildedRoseInvenotriesResponse.class))),
			@ApiResponse(responseCode = "500", description = "System error, unable to perform operation. Please contact MIW.', <br />", content = @Content(schema = @Schema(implementation = Item.class))),
			@ApiResponse(responseCode = "400", description = "BAD_REQUEST: 'Bad Request', <br />", content = @Content(schema = @Schema(implementation = Item.class))) })
	public ResponseEntity<GetGildedRoseInvenotryResponse> get(@PathVariable(name = "id", required = true) String name,
			@RequestHeader(name = "Generic Headers", required = false) Map<String, String> headers,
			@RequestParam(defaultValue = "false", required = false) Map<String, String> queryParams) throws Exception {

		StopWatch watch = new StopWatch();
		watch.start();
		try {
			logger.info("Entering GildedRoseController for getting the details of a single inventory");
			Item item = gildedRoseService.getGlideRoseInventory(name);
			GetGildedRoseInvenotryResponse getItemEntity = new GetGildedRoseInvenotryResponse(item);
			return new ResponseEntity<GetGildedRoseInvenotryResponse>(getItemEntity, HttpStatus.OK);
		} finally {
			watch.stop();
			logger.info("Time taken to Get the requested inventory :{} ms ", watch.getTotalTimeMillis());
		}
	}

	/**
	 * Buy the requested Inventory.
	 * 
	 * @param request
	 * @param headers
	 * @param queryParams
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = { "/buyInventory" }, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Buy the requested Inventory.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = GetGildedRoseInvenotriesResponse.class))),
			@ApiResponse(responseCode = "500", description = "System error, unable to perform operation. Please contact MIW.', <br />", content = @Content(schema = @Schema(implementation = Item.class))),
			@ApiResponse(responseCode = "400", description = "BAD_REQUEST: 'Bad Request', <br />", content = @Content(schema = @Schema(implementation = Item.class))) })
	public ResponseEntity<BuyItemDetails> buyInventory(@RequestBody BuyGildedRoseInvenotryRequest request,
			@RequestHeader(name = "Generic Headers", required = false) Map<String, String> headers,
			@RequestParam(defaultValue = "false", required = false) Map<String, String> queryParams) throws Exception {

		StopWatch watch = new StopWatch();
		watch.start();
		try {
			logger.info("Entering GildedRoseController for buying all the requested inventories");
			BuyItemDetails buyItemDetails = gildedRoseService.buyGlideRoseInventory(request);
			return new ResponseEntity<BuyItemDetails>(buyItemDetails, HttpStatus.OK);

		} finally {
			watch.stop();
			logger.info("Time taken to buy the requested Inventory :{} ms ", watch.getTotalTimeMillis());
		}
	}

	/**
	 * Register the items for testing. Testing scope only. Can be removed. Wrote it
	 * as a simple function. Does not needed also, since the items are inserted via
	 * data.sql scripts on loading the application.
	 * 
	 * @param item
	 * @return
	 */
	@PostMapping(value = { "/saveInvetory" }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public Item saveInventories(@RequestBody Item item) {
		return gildedRoseService.saveGlideRoseInventory(item);
	}
}
